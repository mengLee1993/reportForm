package com.ebase.report.cube;

import com.ebase.report.common.DemandPositionType;
import com.ebase.report.common.DemandType;
import com.ebase.report.common.MeasureTypeEnum;
import com.ebase.report.core.calculate.ExpressionCalculator;
import com.ebase.report.core.calculate.ExpressionFormatException;
import com.ebase.report.model.dynamic.ReportMeasure;
import org.apache.commons.collections.set.ListOrderedSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 数据集转tree树型结构
 */
public class CubeTree {
    private static Logger logger = LoggerFactory.getLogger(CubeTree.class);

    private List<Dimension> lineDimension = new LinkedList<Dimension>(); // 行维度
    private List<Dimension> columnDimension = new LinkedList<Dimension>(); // 列维度
    private List<ReportMeasure> measures = new LinkedList<ReportMeasure>(); //度量值
    private ReportMeasure defReportMeasure;

    // 存放维度对象
    private Map<String, Dimension> dimensionMap = new LinkedHashMap<String, Dimension>();

    // 单元格的数据（key: [movie_area.中国][movie_type.战争][movie_avg.13]）
    private Map<String, String> cells = new LinkedHashMap<String, String>();

    // 行的树形结构
    private ManyNodeTree lineTree = new ManyNodeTree();
    // 列的树形结构
    private ManyNodeTree columnTree = new ManyNodeTree();

    // 叶结点
    List<ManyTreeNode> lineLeafList = new ArrayList<>();
    List<ManyTreeNode> columnLeafList = new ArrayList<>();

    // 保存结点数据
    private Map<String, TreeNode> treeNodeMap = new HashMap<String, TreeNode>();
    private Map<String, TreeNode> lineSubTotalTreeNode = new HashMap<String, TreeNode>();
    private Map<String, TreeNode> columnSubTotalTreeNode = new HashMap<String, TreeNode>();

    //返回行
    List<List<AxesxData>> axesxData = new LinkedList<List<AxesxData>>();
    //返回列
    List<List<AxesyData>> axesyData = new LinkedList<List<AxesyData>>();
    // axes-x轴无数据时 设置单元格数据
    private List<Object> cellList;

    // 根据一行rs结果，初始化行列树
    public void addDimensionKey(DimensionKey dimensionKey) {
        // 自定义指标（计算）
        calcCustomMeasures(dimensionKey);
        // 初始化行-tree
        addNodeToTree(dimensionKey, lineDimension, lineTree);
        // 初始化列-tree
        addNodeToTree(dimensionKey, columnDimension, columnTree);
    }

    // 根据树型结构转前端需要的json
    public void toJson() {
        // 小计节点初始
        lineTree.addChildes(new ArrayList<>(lineSubTotalTreeNode.values()));
        columnTree.addChildes(new ArrayList<>(columnSubTotalTreeNode.values()));

        // 计算节点length
        List<ManyTreeNode> lineElderNodeList = lineTree.elderNodeList(lineTree.getRoot());
        List<ManyTreeNode> columnElderNodeList = columnTree.elderNodeList(columnTree.getRoot());

        for (ManyTreeNode treeNode : lineElderNodeList) {
            int len = lineTree.leafNodeSize(treeNode, 0);
            treeNode.getTreeNode().setLength(len);
        }

        for (ManyTreeNode treeNode : columnElderNodeList) {
            int len = columnTree.leafNodeSize(treeNode, 0);
            treeNode.getTreeNode().setLength(len);
        }

        // 设置cells单元格的数据list
        lineLeafList = lineTree.leafNodeList(lineTree.getRoot());
        columnLeafList = columnTree.leafNodeList(columnTree.getRoot());
        setCellData();

        // 行json
        axisxData();
        // 列json
        axisyData();
    }

    // 根据树型结构转前端需要的json
    public void toCharts() {
        // 设置cells单元格的数据list
        lineLeafList = lineTree.leafNodeList(lineTree.getRoot());
        columnLeafList = columnTree.leafNodeList(columnTree.getRoot());
        setCellData();
    }


    /**
     * 自定义指标计算
     *
     * @param dimensionKey
     */
    private void calcCustomMeasures(DimensionKey dimensionKey) {

        try {
            for(ReportMeasure reportMeasure : this.measures){
                if(MeasureTypeEnum.CUSTOM.equals(reportMeasure.getMeasureType())){

                    // 如果是自定义指标
                    String expression = reportMeasure.getExpressionEnglish();

                    // 替换【指标标识】为对应的指标数值
                    for(ReportMeasure customMeasure : reportMeasure.getCustomIndexTmp()){
                        String measureValue = dimensionKey.getDimensionMap().get(dimensionKey.getMeasuresKey(customMeasure.getKey()));
                        expression = expression.replaceAll("#"+customMeasure.getKey()+"#", null == measureValue ? "0" : measureValue);

                    }

                    logger.info("--- 计算 expression ---"+expression);
                    String customValue = String.valueOf(ExpressionCalculator.cal(expression));

                    getCells().put(dimensionKey.getMeasuresKey(reportMeasure.getKey()), String.valueOf(customValue));
                }

            }
        } catch (ExpressionFormatException e) {
            logger.error("自定义指标表达式计算异常，{}", e);
            // todo throw exception
        }catch (Exception e){
            logger.error("自定义指标处理异常, {}", e);
            // todo throw exception
        }
    }

    /**
     * 初始树的节点
     * 将一行的数据(rs)处理为dimensionKey.map<rs.key,rs.value>
     * 遍历维度，增加节点
     *
     * @param dimensionKey
     * @param dimensionList
     * @param tree
     */
    private void addNodeToTree(DimensionKey dimensionKey, List<Dimension> dimensionList, ManyNodeTree tree) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        // 度量值的key
        List<StringBuffer> measuresBufferList = new ArrayList<StringBuffer>();

        StringBuffer stringBuffer = new StringBuffer();
        String parentNodeId = "root";

        for (Dimension dimension : dimensionList) {
            if (DemandType.DIMENSION.equals(dimension.getDemandType())) {
                // 维度节点的初始
                if (CollectionUtils.isEmpty(measuresBufferList)) {
                    // 无度量值的key
                    dimensionToTreeNode(stringBuffer, parentNodeId, dimension, treeNodes, dimensionKey);
                } else {
                    // 有度量值的key
                    for (StringBuffer buffer : measuresBufferList) {
                        dimensionToTreeNode(buffer, parentNodeId, dimension, treeNodes, dimensionKey);
                    }
                }
            } else {
                // 度量值节点的初始
                for (ReportMeasure measuresDimension : this.measures) {
                    if (!StringUtils.isEmpty(stringBuffer.toString())) {
                        parentNodeId = stringBuffer.toString();
                    }

                    String key = "";
                    if (!StringUtils.isEmpty(stringBuffer.toString())) {
                        key = stringBuffer.toString() + "#";
                    }

                    key = key + DemandType.MEASURES.getCode() + "." + measuresDimension.getKey();

                    // 判断是否已经添加过
                    if (null == treeNodeMap.get(key)) {
                        // 添加节点数据
                        TreeNode treeNode = new TreeNode(key, parentNodeId);
                        treeNode.setLev(dimension.getLev());
                        treeNode.setName(measuresDimension.getFieldName());
                        treeNode.setCode(measuresDimension.getKey());
                        treeNodes.add(treeNode);

                        treeNodeMap.put(key, treeNode);
                    }

                    measuresBufferList.add(new StringBuffer(key));
                }
            }
        }

        tree.addChildes(treeNodes);
    }

    /**
     * 添加树节点
     *
     * @param stringBuffer key
     * @param parentNodeId
     * @param dimension    当前维度对象
     * @param treeNodes
     * @param dimensionKey 一行数据rs.map
     */
    private void dimensionToTreeNode(StringBuffer stringBuffer, String parentNodeId, Dimension dimension, List<TreeNode> treeNodes, DimensionKey dimensionKey) {
        // 维度
        if (!StringUtils.isEmpty(stringBuffer.toString())) {
            parentNodeId = stringBuffer.toString();
            stringBuffer.append("#");
        }

        stringBuffer.append(dimension.getKey() + "," + dimensionKey.getDimensionMap().get(dimension.getKey()));

        // 判断是否已经添加过
        if (null == treeNodeMap.get(stringBuffer.toString())) {
            // 添加节点数据
            TreeNode treeNode = new TreeNode(stringBuffer.toString(), parentNodeId);
            treeNode.setLev(dimension.getLev());
            treeNode.setName(dimensionKey.getDimensionMap().get(dimension.getKey()));
            treeNode.setCode(dimension.getKey());
            treeNodes.add(treeNode);

            treeNodeMap.put(stringBuffer.toString(), treeNode);
        }

        // 判断是否小计/合计
        if (dimension.isSubTotal()) {
            Map<String, TreeNode> subTotalTreeNodeMap = null;
            int maxLev;
            String nodeName = "小计";
            if (DemandPositionType.LINE.getType().equals(dimension.getPosition())) {
                subTotalTreeNodeMap = lineSubTotalTreeNode;
                maxLev = this.lineDimension.size();
            } else {
                subTotalTreeNodeMap = columnSubTotalTreeNode;
                maxLev = this.columnDimension.size();
            }

            String subTotalKey = null;
            if (maxLev == dimension.getLev()) {
                // 合计
                nodeName = "合计";
                // 去掉
                subTotalKey = stringBuffer.toString().split(",")[0] + "#subTotal";
            } else {
                subTotalKey = parentNodeId + "#subTotal";
            }

            if (null == subTotalTreeNodeMap.get(subTotalKey)) {
                TreeNode treeNode = new TreeNode(subTotalKey, parentNodeId);
                treeNode.setSubTotal(true);
                treeNode.setSubKey(subTotalKey);
                treeNode.setLev(dimension.getLev());
                treeNode.setLength(1);
                treeNode.setName(nodeName);
                treeNode.setSubTotal(true);
                treeNode.setCode(dimension.getKey());
                subTotalTreeNodeMap.put(subTotalKey, treeNode);
            }
        }
    }

    /**
     * 计算单元格的数据
     * 就这个方法 慢
     * // todo... todo...
     */
    private void setCellData() {
        if (CollectionUtils.isEmpty(lineLeafList)) {
            // x-轴为空，无维度
            List<Object> cells = new ArrayList<>();
            for (ManyTreeNode columnLeafNode : columnLeafList) {
                cells.add(getCellValue(columnLeafNode.getTreeNode().getNodeId()));
            }

            setCellList(cells);
        } else {
            for (ManyTreeNode lineLeafNode : lineLeafList) {
                List<Object> cells = new ArrayList<>();
                for (ManyTreeNode columnLeafNode : columnLeafList) {
                    cells.add(getCellValue(lineLeafNode.getTreeNode().getNodeId() + "#" + columnLeafNode.getTreeNode().getNodeId()));
                }
                lineLeafNode.getTreeNode().setCells(cells);
            }
        }
    }


    /**
     * 根据行的叶节点及列的叶节点获取对应cell的值
     *
     * @param key
     * @return
     */
    public String getCellValue(String key) {
        String returnValue = "0";

        boolean subTotal = false;
        if (key.indexOf("subTotal") != -1) {
            // 小计叶节点
            key.replaceAll("#?subTotal", "");
            subTotal = true;
        }

        String[] dimensions = key.split("#");
        StringBuffer keyBuffer = new StringBuffer();
        List<String> dimensionList = new ArrayList<String>();
        String measures = "";
        for (int i = 0; i < dimensions.length; i++) {
            String str = dimensions[i];
            if (!str.contains("subTotal")) {
                if (str.contains(DemandType.MEASURES.getCode())) {
                    measures = dimensions[i];
                } else {
                    if (!StringUtils.isEmpty(keyBuffer.toString())) {
                        keyBuffer.append("#");
                    }
                    keyBuffer.append(str);
                    dimensionList.add(str);
                }
            }
        }

        if (StringUtils.isEmpty(measures)) {
            // 无指标时，需要设置默认指标
            keyBuffer.append(new DimensionKey().getMeasuresKey(getDefReportMeasure().getKey()));
        }else {
            keyBuffer.append("#"+measures);
        }

        if (subTotal) {
            returnValue = getSubTotal(dimensionList, measures);
        } else {
            returnValue = cells.get(keyBuffer.toString()) == null ? "0" : cells.get(keyBuffer.toString());
        }

        return returnValue;
    }

    /**
     * 封装行数据json
     */
    private void axisxData() {
        for (ManyTreeNode manyTreeNode : lineLeafList) {
            List<AxesxData> dataList = new ArrayList<AxesxData>();
            AxesxData leafData = new AxesxData();
            leafData.setLev(manyTreeNode.getTreeNode().getLev());
            leafData.setLength(manyTreeNode.getTreeNode().getLength());
            leafData.setCode(manyTreeNode.getTreeNode().getCode());
            leafData.setName(manyTreeNode.getTreeNode().getName());
            leafData.setVal(manyTreeNode.getTreeNode().getCells());
            leafData.setType(getTitle(manyTreeNode));

            if (manyTreeNode.getTreeNode().isSubTotal()) {
                leafData.setSubTotal(1);
            }

            if (!manyTreeNode.getTreeNode().getParentId().equals("root")) {
                // 不是根结点,得到所有父节点
                List<ManyTreeNode> elderList = manyTreeNode.getElders(lineTree);

                for (int i = elderList.size() - 1; i >= 0; i--) {
                    ManyTreeNode elderNode = elderList.get(i);
                    if (!elderNode.getTreeNode().getNodeId().equals("root")) {
                        AxesxData axesxData = new AxesxData();
                        axesxData.setLength(elderNode.getTreeNode().getLength());
                        axesxData.setCode(elderNode.getTreeNode().getCode());
                        axesxData.setName(elderNode.getTreeNode().getName());
                        axesxData.setLev(elderNode.getTreeNode().getLev());
                        // type 前端用来初始化行标题，如有度量值需做统一处理
                        axesxData.setType(getTitle(elderNode));
                        if (elderNode.getTreeNode().isSubTotal()) {
                            axesxData.setSubTotal(1);
                        }

                        dataList.add(axesxData);
                    }
                }
            }

            dataList.add(leafData);

            this.axesxData.add(dataList);
        }

    }

    /**
     * type 前端用来初始化行标题，如有度量值需做统一处理
     *
     * @param treeNode
     * @return
     */
    private String getTitle(ManyTreeNode treeNode) {
        String title = "";

        Dimension dimension = dimensionMap.get(treeNode.getTreeNode().getCode());
        if (DemandType.MEASURES.getCode().equals(dimension.getDemandType().getCode())) {
            title = DemandType.MEASURES.getCode();
        } else {
            title = dimension.getFieldName();
        }

        return title;
    }

    /**
     * 计算小计
     *
     * @param dimensionList
     * @param measures
     * @return
     */
    private String getSubTotal(List<String> dimensionList, String measures) {
        BigDecimal returnValue = new BigDecimal(0);

        int index = 0;
        for (String cellsKey : cells.keySet()) {
            if (requireSubTotal(cellsKey, dimensionList) && cellsKey.indexOf(measures) != -1) {
                index++;
                // 默认求和
                returnValue = returnValue.add(new BigDecimal(cells.get(cellsKey)));
            }
        }

        return returnValue.toString();
    }

    /**
     * 判断是否需要小计
     *
     * @param cellsKey
     * @param dimensionList
     * @return
     */
    private boolean requireSubTotal(String cellsKey, List<String> dimensionList) {
        boolean returnValue = true;

        for (String key : dimensionList) {
            if (cellsKey.indexOf(key) == -1) {
                returnValue = false;
                break;
            }
        }

        return returnValue;
    }

    /**
     * 封装列json
     *
     * @param columnLeafList
     */
    private void axisyData(List<ManyTreeNode> columnLeafList) {
        ArrayList<AxesyData>[] arrayLists = new ArrayList[this.columnDimension.size()];
        // 预置列的结果集
        for (Dimension dimension : this.columnDimension) {
            arrayLists[dimension.getLev() - 1] = new ArrayList<AxesyData>();
        }

        // 列的叶子节点
        for (ManyTreeNode manyTreeNode : columnLeafList) {
            AxesyData axesyData = new AxesyData();
            axesyData.setLength(manyTreeNode.getTreeNode().getLength());
            axesyData.setLev(manyTreeNode.getTreeNode().getLev());
            axesyData.setName(manyTreeNode.getTreeNode().getName());
            axesyData.setCode(manyTreeNode.getTreeNode().getCode());
            if (manyTreeNode.getTreeNode().isSubTotal()) {
                axesyData.setSubTotal(1);
            }

            arrayLists[this.columnDimension.size() - axesyData.getLev()].add(axesyData);
        }

        // 非叶子节点数据处理
        if (this.columnDimension.size() > 1) {
            // 不是根结点,得到所有父节点
            List<ManyTreeNode> elderList = columnTree.elderNodeList(columnTree.getRoot());
            for (ManyTreeNode treeNode : elderList) {
                if (!treeNode.getTreeNode().getNodeId().equals("root")) {
                    AxesyData elderAxesyData = new AxesyData();
                    elderAxesyData.setName(treeNode.getTreeNode().getName());
                    elderAxesyData.setLength(treeNode.getTreeNode().getLength());
                    elderAxesyData.setLev(treeNode.getTreeNode().getLev());
                    elderAxesyData.setCode(treeNode.getTreeNode().getCode());
                    if (elderAxesyData.getSubTotal() == 1) {
                        elderAxesyData.setSubTotal(1);
                        arrayLists[this.columnDimension.size() - elderAxesyData.getLev()].add(elderAxesyData);
                    } else {
                        arrayLists[this.columnDimension.size() - elderAxesyData.getLev()].add(elderAxesyData);
                    }
                }
            }
        }

        for (int i = 0; i < arrayLists.length; i++) {
            axesyData.add(arrayLists[i]);
        }
    }

    private void axisyData() {
        ArrayList<AxesyData>[] arrayLists = new ArrayList[this.columnDimension.size()];
        // 预置列的结果集
        for (Dimension dimension : this.columnDimension) {
            arrayLists[dimension.getLev() - 1] = new ArrayList<AxesyData>();
        }

        columnTree.treeNodeToLevList(columnTree.getRoot(), arrayLists);

        for (int i = 0; i < arrayLists.length; i++) {
            axesyData.add(arrayLists[i]);
        }
    }

    // getter and setter

    public List<Dimension> getLineDimension() {
        return lineDimension;
    }

    // set 行维度
    public void setLineDimension(List<Dimension> lineDimension) {
        this.lineDimension = lineDimension;
        for (Dimension dimension : lineDimension) {
            if (DemandType.DIMENSION.equals(dimension.getDemandType())) {
                dimension.setPosition(DemandPositionType.LINE.getType());
                dimensionMap.put(dimension.getKey(), dimension);
            }
        }
    }

    public List<Dimension> getColumnDimension() {
        return columnDimension;
    }

    // set 列维度
    public void setColumnDimension(List<Dimension> columnDimension) {
        this.columnDimension = columnDimension;
        for (Dimension dimension : columnDimension) {
            if (DemandType.DIMENSION.equals(dimension.getDemandType())) {
                dimension.setPosition(DemandPositionType.COLUMN.getType());
                dimensionMap.put(dimension.getKey(), dimension);
            }
        }
    }

    public List<ReportMeasure> getMeasures() {
        return measures;
    }

    /**
     * 只拖到行区时，明细页的title名称
     * @return
     */
    public List<String> getHeaders(){
        List<String> list = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        for(Dimension dimension : lineDimension){
            if(DemandType.DIMENSION.equals(dimension.getDemandType())){
                // 维度
                if(map.get(dimension.getFieldName()) == null){
                    list.add(dimension.getFieldName());
                }
                map.put(dimension.getFieldName(), dimension.getFieldName());

            } else if(DemandType.MEASURES.equals(dimension.getDemandType())){
                // 指标
                for(ReportMeasure reportMeasure : dimension.getRptMeasures()){
                    if(reportMeasure.getIsChecked() == 1){
                        if(reportMeasure.getMeasureType().equals(MeasureTypeEnum.CUSTOM)){
                            // 自定义指标
                            list.add(reportMeasure.getCombinationName());
                        }else{
                            // 系统指标
                            if(map.get(reportMeasure.getFieldName()) == null){
                                list.add(reportMeasure.getFieldName());
                            }
                            map.put(reportMeasure.getFieldName(), reportMeasure.getFieldName());
                        }
                    }
                }
            }
        }

        return list;
    }

    /**
     * 只拖到行区时，明细页的data cell list
     * @return
     */
    public List<String> getDataList(Map<String, String> rsMap){
        List<String> list = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        for(Dimension dimension : lineDimension){
            if(DemandType.DIMENSION.equals(dimension.getDemandType())){
                // 维度
                if(map.get(dimension.getFieldName()) == null){
                    list.add(rsMap.get(dimension.getKey()));
                }
                map.put(dimension.getFieldName(), dimension.getFieldName());

            } else if(DemandType.MEASURES.equals(dimension.getDemandType())){
                // 指标
                for(ReportMeasure reportMeasure : dimension.getRptMeasures()){
                    if(reportMeasure.getIsChecked()==1){
                        if(reportMeasure.getMeasureType().equals(MeasureTypeEnum.CUSTOM)){
                            // 自定义指标
                            String expression = reportMeasure.getExpressionEnglish();

                            // 替换【指标标识】为对应的指标数值
                            for(ReportMeasure customMeasure : reportMeasure.getCustomIndexTmp()){
                                String measureValue = rsMap.get(customMeasure.getKey()) ==null ? "0" : rsMap.get(customMeasure.getKey());
                                expression = expression.replaceAll("#"+customMeasure.getKey()+"#", null == measureValue ? "0" : measureValue);
                            }
                            String customValue = null;
                            try {
                                customValue = String.valueOf(ExpressionCalculator.cal(expression));
                            } catch (ExpressionFormatException e) {
                                logger.error("计算自定义指标异常", e);
                            }
                            list.add(customValue);
                        }else{
                            // 系统指标
                            if(map.get(reportMeasure.getFieldName()) == null){
                                list.add(rsMap.get(reportMeasure.getKey()));
                            }
                            map.put(reportMeasure.getFieldName(), reportMeasure.getFieldName());
                        }
                    }

                }
            }
        }

        return list;
    }

    // 度量值维度
    public void setMeasures(List<ReportMeasure> measures) {
        if (CollectionUtils.isEmpty(measures)) {
            ReportMeasure reportMeasure = new ReportMeasure();
            reportMeasure.setMeasureType(MeasureTypeEnum.COUNT);
            reportMeasure.setFieldCode("1");
            reportMeasure.setFieldName("数量");
            reportMeasure.setFieldId("00");
            reportMeasure.setDemandType(DemandType.MEASURES);

            measures.add(reportMeasure);

            this.defReportMeasure = reportMeasure;
        }

        this.measures = measures;
        for (ReportMeasure measure : measures) {
            if (MeasureTypeEnum.CUSTOM.equals(measure.getMeasureType())) {
                // 自定义指标
                for (ReportMeasure reportMeasure : measure.getCustomIndexTmp()) {
                    dimensionMap.put(reportMeasure.getKey(), reportMeasure);
                }
            } else {
                dimensionMap.put(measure.getKey(), measure);
            }

        }

    }

    public Map<String, Dimension> getDimensionMap() {
        return dimensionMap;
    }

    public void setDimensionMap(Map<String, Dimension> dimensionMap) {
        this.dimensionMap = dimensionMap;
    }

    public Map<String, String> getCells() {
        return cells;
    }

    public void setCells(Map<String, String> cells) {
        this.cells = cells;
    }

    public ManyNodeTree getLineTree() {
        return lineTree;
    }

    public void setLineTree(ManyNodeTree lineTree) {
        this.lineTree = lineTree;
    }

    public ManyNodeTree getColumnTree() {
        return columnTree;
    }

    public void setColumnTree(ManyNodeTree columnTree) {
        this.columnTree = columnTree;
    }

    public Map<String, TreeNode> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<String, TreeNode> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }

    public Map<String, TreeNode> getLineSubTotalTreeNode() {
        return lineSubTotalTreeNode;
    }

    public void setLineSubTotalTreeNode(Map<String, TreeNode> lineSubTotalTreeNode) {
        this.lineSubTotalTreeNode = lineSubTotalTreeNode;
    }

    public Map<String, TreeNode> getColumnSubTotalTreeNode() {
        return columnSubTotalTreeNode;
    }

    public void setColumnSubTotalTreNode(Map<String, TreeNode> columnSubTotalTreeNode) {
        this.columnSubTotalTreeNode = columnSubTotalTreeNode;
    }

    public void setColumnSubTotalTreeNode(Map<String, TreeNode> columnSubTotalTreeNode) {
        this.columnSubTotalTreeNode = columnSubTotalTreeNode;
    }

    public List<List<AxesxData>> getAxesxData() {
        return axesxData;
    }

    public void setAxesxData(List<List<AxesxData>> axesxData) {
        this.axesxData = axesxData;
    }

    public List<List<AxesyData>> getAxesyData() {
        return axesyData;
    }

    public void setAxesyData(List<List<AxesyData>> axesyData) {
        this.axesyData = axesyData;
    }

    public List<Object> getCellList() {
        return cellList;
    }

    public void setCellList(List<Object> cellList) {
        this.cellList = cellList;
    }

    public ReportMeasure getDefReportMeasure() {
        return defReportMeasure;
    }

    public void setDefReportMeasure(ReportMeasure defReportMeasure) {
        this.defReportMeasure = defReportMeasure;
    }

    public List<ManyTreeNode> getLineLeafList() {
        return lineLeafList;
    }

    public void setLineLeafList(List<ManyTreeNode> lineLeafList) {
        this.lineLeafList = lineLeafList;
    }

    public List<ManyTreeNode> getColumnLeafList() {
        return columnLeafList;
    }

    public void setColumnLeafList(List<ManyTreeNode> columnLeafList) {
        this.columnLeafList = columnLeafList;
    }
}
