package com.ebase.report.cube;

import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class CubeData {

    private List<String> lineDims = new LinkedList<String>(); // 行维度
    private List<String> columnDims = new LinkedList<String>(); // 列维度
    private List<String> measures = new LinkedList<String>(); //度量值

    // 存放每个维度 group 之后的数据（？个值，code name ? ）
    private Map<String, DimensionData> dimensionDataMap = new LinkedHashMap<String, DimensionData>();

    // 单元格的数据（key: [movie_area.中国][movie_type.战争][movie_avg.13]）
    private Map<String, String> cells = new LinkedHashMap<String, String>();

    //返回行
    List<AxesxData> axesxData = new LinkedList<AxesxData>();
    //返回列
    List<List<AxesyData>> axisyData = new LinkedList<List<AxesyData>>();

    // 最后一级的小计处理（只能处理最后一级的小计）
    public void subtotal(){
        // 行维度至少要有两个
        if(lineDims.size()>1){
            // 倒数第二个是否需要小计
            String dimension = lineDims.get(lineDims.size()-2);
            DimensionData dimensionData = dimensionDataMap.get(dimension);
            if(dimensionData.isSubtotal()){
                dimensionDataMap.get(lineDims.size()-1).getValues().put("小计","小计");
            }

        }

        // 列维度至少要有两个
        if(columnDims.size()>1){
            // 倒数第二个是否需要小计
            String dimension = lineDims.get(lineDims.size()-2);

        }
    }

    // 行数据json处理
    public List<AxesxData> axisxData() {
        // 行维度数组
        String[] lineDimArr = getDimArr(this.lineDims);

        // 不存在行维度的json封装
        if(0 == this.lineDims.size()){
            AxesxData data = new AxesxData();
            // column cells value
            List<Object> valList = getAxisxDataValList("");
            data.setVal(valList);

            axesxData.add(data);
        }

        // 循环维度值集合的迪卡尔积，封装行对象json
        for (int i = 0; i < lineDimArr.length; i++) {
            String key = lineDimArr[i];

            AxesxData data = new AxesxData();

            data.setLev(this.lineDims.size());
            data.setLength(getDataLengthByLevel(data.getLev(), this.lineDims));
            data.setType(getAxisxDataType(key, data.getLev()));
            data.setName(getAxisxDataName(key, data.getLev()));

            if (this.lineDims.size() == 1) {
                // get column cells list value
                List<Object> valList = getAxisxDataValList(key);
                data.setVal(valList);

            } else if (this.lineDims.size() > 1) {
                //sublist初始
                List<AxesxData> sublist = new ArrayList<AxesxData>();
                for (int lev = data.getLev() - 1; lev > 0; lev--) {
                    AxesxData subData = new AxesxData();
                    subData.setLev(lev);
                    subData.setLength(getDataLengthByLevel(subData.getLev(), this.lineDims));
                    subData.setType(getAxisxDataType(key, subData.getLev()));
                    subData.setName(getAxisxDataName(key, subData.getLev()));

                    if (lev == 1) {
                        // column cells value
                        List<Object> valList = getAxisxDataValList(key);
                        subData.setVal(valList);
                    }

                    sublist.add(subData);
                }

//                data.setList(sublist);
            }

            axesxData.add(data);
        }

        return axesxData;
    }

    // 列数据json处理
    public List<List<AxesyData>> axisyData() {
        // 列维度 迪卡尔积 数组
        String[] columnDimArr = getDimArr(this.columnDims);

        //列表
//        if()


        int lev = this.columnDims.size();
        for (String dim : this.columnDims) {
            List<AxesyData> axesyDataList = new LinkedList<AxesyData>();
            DimensionData columnDimData = this.dimensionDataMap.get(dim);
            for (String val : columnDimData.getValues().keySet()) {
                AxesyData axesyData = new AxesyData();
                axesyData.setLev(lev);
                axesyData.setName(val);
                axesyData.setLength(getDataLengthByLevel(axesyData.getLev(), this.columnDims));

                axesyDataList.add(axesyData);
            }


            int copyNum = getColumnCopyNum(lev);
            if (copyNum > 1) {
                List<AxesyData> copyList = new LinkedList<AxesyData>();
                for (int i = 1; i < copyNum; i++) {
                    List<AxesyData> deepCopy = deepCopy(axesyDataList);
                    copyList.addAll(deepCopy);
                }
                axesyDataList.addAll(copyList);
            }

            axisyData.add(axesyDataList);
            lev--;
        }

        return axisyData;
    }

    // 获取 AxesxData.type的值 根据 dimension的迪卡尔积 key和lev
    private String getAxisxDataType(String key, int lev) {
        String[] arr = key.split("#");
        String dimValue = arr[lineDims.size() - lev];
        dimValue = dimValue.split(",")[0];

        return dimValue;
    }

    // 获取 AxesxData.name的值 根据 dimension的迪卡尔积 key和lev
    private String getAxisxDataName(String key, int lev) {
        String[] arr = key.split("#");
        String dimValue = arr[lineDims.size() - lev];
        dimValue = dimValue.split(",")[1];

        return dimValue;
    }

    /**
     * 获取当前层的数据总行数
     *
     * @param level
     * @return
     */
    private int getDataLengthByLevel(int level, List<String> dimList) {
        int length = 1;
        int index = dimList.size() - level + 1;
        for (int i = index; i < dimList.size(); i++) {
            DimensionData dimensionData = dimensionDataMap.get(dimList.get(i));
            int valueSize = dimensionData.getValues().size() == 0 ? 1 : dimensionData.getValues().size();
            length = length * valueSize;
        }

        return length;
    }

    private int getColumnCopyNum(int level) {
        int length = 1;
        for (int i = 0; i < (columnDims.size() - level); i++) {
            DimensionData dimensionData = dimensionDataMap.get(columnDims.get(i));
            int valueSize = dimensionData.getValues().size() == 0 ? 1 : dimensionData.getValues().size();
            length = length * valueSize;
        }

        return length;
    }

    /**
     * line dimension 迪卡尔积 key
     * @return
     */
    private List<Object> getAxisxDataValList(String key) {
        List<Object> val = new ArrayList<Object>();

        // 列维度值的迪卡尔积
        String[] dimArr = getDimArr(this.columnDims);
        for (int i = 0; i < dimArr.length; i++) {
            String columnKey = dimArr[i];
            val.add(getCellsValByKey(key + columnKey));
        }

        return val;
    }

    // 得到维度与对应值的迪卡尔积结果，放入数组中
    private String[] getDimArr(List<String> dimensions) {
        // 维度对应的结果list
        List<List<String>> dimList = new ArrayList<List<String>>();

        // 行维度的集合
        for (String key : dimensions) {
            DimensionData dimensionData = dimensionDataMap.get(key);

            Map<String, String> values = dimensionData.getValues();

            List<String> list = new ArrayList<>();

            for (String str : values.keySet()) {
                String s = key + "," + str;
                list.add(s);
            }
            dimList.add(list);
        }

        // 存放维度结果的迪卡尔积结果
        List<List<String>> dimResult = new ArrayList<List<String>>();

        dimensionDescartes(dimList, dimResult, 0, new ArrayList<>());

        // 行维度的数组
        String[] dimArr = new String[dimResult.size()];
        int index = 0;
        for (List<String> list : dimResult) {
            String key = "";
            for (String str : list) {
                key = key + str + "#";
            }
            dimArr[index++] = key;
        }
        return dimArr;
    }

    /**
     * 递归实现 维度的笛卡尔积，结果放在result中
     *
     * @param dimValue 原始数据
     * @param result   结果数据
     * @param layer    dimValue的层数
     * @param curList  每次笛卡尔积的结果
     */
    private void dimensionDescartes(List<List<String>> dimValue, List<List<String>> result, int layer, List<String> curList) {
        if (layer < dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                dimensionDescartes(dimValue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimValue.get(layer).get(i));
                    dimensionDescartes(dimValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

    /**
     * @param key 包括measures维度的key
     * @return
     */
    private String getCellsValByKey(String key) {
        // 需要处理成cells map中的key(过滤掉measures.COUNT ... etc)
        String[] keyArr = key.split("#");
        StringBuilder stringBuilder = new StringBuilder();
        String measuresName = "";

        for (int i = 0; i < keyArr.length; i++) {
            if (!StringUtils.isEmpty(keyArr[i])) {
                if (keyArr[i].indexOf("measures") != -1) {
                    measuresName = keyArr[i].split(",")[1];
                } else {
                    stringBuilder.append(keyArr[i]);
                    stringBuilder.append("#");
                }
            }
        }

        stringBuilder.append(measuresName);
        return cells.get(stringBuilder.toString());
    }

    public static <T> List<T> deepCopy(List<T> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return src;
    }

    // get and setter
    public List<String> getLineDims() {
        return lineDims;
    }

    public void setLineDims(List<String> lineDims) {
        this.lineDims = lineDims;
    }

    public List<String> getColumnDims() {
        return columnDims;
    }

    public void setColumnDims(List<String> columnDims) {
        this.columnDims = columnDims;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    public Map<String, DimensionData> getDimensionDataMap() {
        return dimensionDataMap;
    }

    public void setDimensionDataMap(Map<String, DimensionData> dimensionDataMap) {
        this.dimensionDataMap = dimensionDataMap;
    }

    public Map<String, String> getCells() {
        return cells;
    }

    public void setCells(Map<String, String> cells) {
        this.cells = cells;
    }

    public List<AxesxData> getAxesxData() {
        return axesxData;
    }

    public void setAxesxData(List<AxesxData> axesxData) {
        this.axesxData = axesxData;
    }

    public List<List<AxesyData>> getAxisyData() {
        return axisyData;
    }

    public void setAxisyData(List<List<AxesyData>> axisyData) {
        this.axisyData = axisyData;
    }

}
