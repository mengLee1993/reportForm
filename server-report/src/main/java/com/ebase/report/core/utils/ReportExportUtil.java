package com.ebase.report.core.utils;

import com.ebase.report.cube.CubeTree;
import com.ebase.report.cube.AxesxData;
import com.ebase.report.cube.AxesyData;
import com.ebase.report.cube.Dimension;
import com.ebase.report.cube.DimensionKey;
import com.ebase.report.model.ReportRespDetail;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class ReportExportUtil {

    /**
     * 列默认宽度
     */
    private static final int DEFAULT_COLUMN_WIDTH = 4000;



    /**
     * 创建 workbook
     */
    private Workbook createWorkBook() {
        return new XSSFWorkbook();
    }

    /**
     * 创建 sheet
     */
    private Sheet createSheet(Workbook workbook, String sheetName) {
        return workbook.createSheet(sheetName);
    }

    public static Workbook createReportWorkbook(CubeTree cubeTree) {
        ReportExportUtil exportUtil = new ReportExportUtil();
        Workbook workbook = exportUtil.createWorkBook();
        Sheet sheet = exportUtil.createSheet(workbook, "数据报表");

        // excel头部（x轴+y轴）
        exportUtil.headersHandler(workbook, sheet, cubeTree);

        // excel内容（行区）
        if(CollectionUtils.isEmpty(cubeTree.getAxesxData())){
            // x轴无维度
            exportUtil.writeContent(workbook, sheet, cubeTree.getCellList().toArray(), cubeTree.getAxesyData().size(), 0);
        }else {
            // excel内容（行区）
            exportUtil.contentHandler(workbook, sheet, cubeTree);
        }

        return workbook;
    }

    /**
     * 处理表格头部
     *
     * @param sheet
     * @param cubeTree
     * @Param workbook
     */
    private void headersHandler(Workbook workbook, Sheet sheet, CubeTree cubeTree) {
        // 需要合并的单元格记录
        List<CellRangeAddress> regionList = new ArrayList<CellRangeAddress>();

        // y轴头部 rows行数
        int headerRows = cubeTree.getAxesyData().size();
        List<List<Object>> headerList = new ArrayList<List<Object>>();

        // 记录小计或合计，单元格的列的位置
        Map<Integer, AxesyData> subColumnIndexMap = new HashMap<Integer, AxesyData>();

        int rowPointer = 0;// rows指针
        int columnPointer = 0;// column指针
        for (List<AxesyData> axesyDataList : cubeTree.getAxesyData()) {
            List<Object> list = new ArrayList<Object>();

            // x轴头部数据
            for (Dimension dimension : cubeTree.getLineDimension()) {
                list.add(dimension.getFieldName());

                // 单元格合并
                if (headerRows > 1 && rowPointer < 1) {
                    CellRangeAddress region = new CellRangeAddress(rowPointer, headerRows - 1, columnPointer, columnPointer);
                    regionList.add(region);
                }
                // 一列结束，指针++
                columnPointer++;
            }

            // y轴头部数据
            for (AxesyData axesyData : axesyDataList) {
                for (int i = 0; i < axesyData.getLength(); i++) {
                    list.add(axesyData.getName());
                    if (subColumnIndexMap.get(list.size()) != null) {
                        AxesyData subAxesyData = subColumnIndexMap.get(list.size());
                        if (subAxesyData.getLev() > axesyData.getLev()) {
                            list.add(subAxesyData.getName());
                        }
                    }
                }

                // 单元格合并
                if (1 == axesyData.getSubTotal() && axesyData.getLev() > 1) {
                    // 小计或合计
                    CellRangeAddress region = new CellRangeAddress(rowPointer, headerRows - 1, columnPointer, columnPointer + axesyData.getLength() - 1);
                    regionList.add(region);
                    subColumnIndexMap.put(region.getLastColumn(), axesyData);
                }

                if (0 == axesyData.getSubTotal() && axesyData.getLength() > 1) {
                    CellRangeAddress region = new CellRangeAddress(rowPointer, rowPointer, columnPointer, columnPointer + axesyData.getLength() - 1);
                    regionList.add(region);
                }
                columnPointer = columnPointer + axesyData.getLength();
            }

            headerList.add(list);
            // 一行结束，指针++
            rowPointer++;
            // 一行结束，列记数归0
            columnPointer = 0;
        }

        //
        int startIndex = 0;
        for (List<Object> list : headerList) {
            // 数据写入sheet头部
            writeHeader(workbook, sheet, list.toArray(), startIndex);
            startIndex++;
        }

        // 处理合并单元格
        for (CellRangeAddress mergedRegion : regionList) {
            sheet.addMergedRegion(mergedRegion);
        }
    }

    /**
     * 写入头部
     *
     * @param workbook
     * @param Sheet
     * @param headers
     * @param startIndex
     */
    private void writeHeader(Workbook workbook, Sheet Sheet, Object[] headers, int startIndex) {
        CellStyle headerCellStyle = createTitleCellStyle(workbook);

        // 表头
        Row headRow = Sheet.createRow(startIndex);
        headRow.setHeight((short) 500);
        Cell headCell = null;
        // 处理excel表头
        for (int i = 0, len = headers.length; i < len; i++) {
            headCell = headRow.createCell(i);
            headCell.setCellValue(String.valueOf(headers[i]));
            headCell.setCellStyle(headerCellStyle);
            // 设置列宽度
            setColumnWidth(i, DEFAULT_COLUMN_WIDTH, Sheet);
        }
    }

    /**
     * 处理x-轴的表头及单元格数据
     *
     * @param workbook
     * @param sheet
     * @param cubeTree
     */
    private void contentHandler(Workbook workbook, Sheet sheet, CubeTree cubeTree) {
        // 需要合并的单元格记录

        Set<CellRangeAddress> regionSet = new HashSet<>();
        Map<String, String> regionMap = new HashMap<>();

        // y轴头部 rows行数
        int startIndex = cubeTree.getAxesyData().size();
        int rowIndex = startIndex;
        List<List<Object>> contentsList = new ArrayList<List<Object>>();
        for (List<AxesxData> axesxDataList : cubeTree.getAxesxData()) {
            List<Object> list = new ArrayList<Object>();

            DimensionKey dimensionKey = new DimensionKey();
            for (AxesxData axesxData : axesxDataList) {
                list.add(axesxData.getName());
                if (1 == axesxData.getSubTotal() && axesxData.getLev() > 1) {
                    // 小计或合并
                    for (int i = 1; i < axesxData.getLev(); i++) {
                        // 非底层的小计或合计，单元格内容重复增加，并且合并行单元格
                        list.add(axesxData.getName());

                        CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex, cubeTree.getLineDimension().size() - axesxData.getLev(), cubeTree.getLineDimension().size() - 1);
                        regionSet.add(region);
                    }
                }

                if (!CollectionUtils.isEmpty(axesxData.getVal())) {
                    for (Object obj : axesxData.getVal()) {
                        list.add(obj);
                    }
                }

                dimensionKey.addDimension(axesxData.getCode(), axesxData.getName());

                // 列合并
                if (axesxData.getLength() > 1 && regionMap.get(dimensionKey.getKey().toString()) == null) {
                    CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex + axesxData.getLength() - 1, cubeTree.getLineDimension().size() - axesxData.getLev(), cubeTree.getLineDimension().size() - axesxData.getLev());
                    regionSet.add(region);
                    regionMap.put(dimensionKey.getKey().toString(), axesxData.getName());
                }
            }

            contentsList.add(list);
            rowIndex++;
        }

        for (List<Object> contents : contentsList) {
            writeContent(workbook, sheet, contents.toArray(), startIndex,cubeTree.getLineDimension().size());
            startIndex++;
        }

        // 处理合并单元格
        for (CellRangeAddress mergedRegion : regionSet) {
            sheet.addMergedRegion(mergedRegion);
        }
    }

    /**
     * 写入x轴的表头及单元格数据
     *
     * @param workbook
     * @param Sheet
     * @param contents
     * @param startIndex
     * @Param headerSize 内容区的头部表头
     */
    private void writeContent(Workbook workbook, Sheet Sheet, Object[] contents, int startIndex, int headerSize) {
        CellStyle cellStyle = createContentCellStyle(workbook);
        CellStyle headCellStyle = createTitleCellStyle(workbook);

        Row contentRow = Sheet.createRow(startIndex);
        contentRow.setHeight((short) 500);
        Cell cell = null;
        // 处理excel表头
        for (int i = 0, len = contents.length; i < len; i++) {
            cell = contentRow.createCell(i);
            cell.setCellValue(String.valueOf(contents[i]));
            cell.setCellStyle(cellStyle);
            if (i < headerSize) {
                cell.setCellStyle(headCellStyle);
            }

            // 设置列宽
            setColumnWidth(i, DEFAULT_COLUMN_WIDTH, Sheet);
        }
    }

    /**
     * x轴无维度时的cells内容处理
     * @param workbook
     * @param Sheet
     * @param cubeTree
     */
    private void cellsHandler(Workbook workbook, Sheet Sheet, CubeTree cubeTree){

    }

    /**
     * 设置列宽度
     *
     * @param i 列的索引号
     *          用户需要设置的列宽
     */
    private void setColumnWidth(int i, int width, Sheet sheet) {
        // 使用用户设置的列宽进行设置
        sheet.setColumnWidth(i, width);
    }

    /**
     * 单元格写值处理器
     *
     * @param {{@link   Cell}
     * @param cellValue 单元格值
     */
    private void cellValueHandler(Cell cell, Object cellValue) {
        // 判断cellValue是否为空，否则在cellValue.toString()会出现空指针异常
        if (cellValue == null) {
            cell.setCellValue("");
            return;
        }
        if (cellValue instanceof String) {
            cell.setCellValue((String) cellValue);
        } else if (cellValue instanceof Boolean) {
            cell.setCellValue((Boolean) cellValue);
        } else if (cellValue instanceof Calendar) {
            Calendar c = (Calendar) cellValue;
            cell.setCellValue((DateUtil.formatDate(c.getTime(), DateUtil.TIME_PATTERN)));
        } else if (cellValue instanceof Date) {
            Date c = (Date) cellValue;
            cell.setCellValue((DateUtil.formatDate(c, DateUtil.TIME_PATTERN)));
        } else if (cellValue instanceof Double) {
            cell.setCellValue((Double) cellValue);
        } else if (cellValue instanceof Integer || cellValue instanceof Long || cellValue instanceof Short
                || cellValue instanceof Float) {
            cell.setCellValue((Double.parseDouble(cellValue.toString())));
        } else if (cellValue instanceof RichTextString) {
            cell.setCellValue((RichTextString) cellValue);
        } else {
            cell.setCellValue(cellValue.toString());
        }
    }

    /**
     * 创建标题和表头单元格样式
     * {@link Workbook}
     *
     * @return {@link CellStyle}
     */
    private CellStyle createTitleCellStyle(Workbook workbook) {
        // 单元格的样式
        CellStyle cellStyle = workbook.createCellStyle();
        // 设置字体样式，改为变粗
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        // 单元格垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 设置通用的单元格属性
        setCommonCellStyle(cellStyle);
        return cellStyle;
    }

    /**
     * 创建内容单元格样式
     *
     * @param Workbook {@link Workbook}
     * @return {@link CellStyle}
     */
    private CellStyle createContentCellStyle(Workbook Workbook) {
        // 单元格的样式
        CellStyle cellStyle = Workbook.createCellStyle();
        // 设置字体样式，改为不变粗
        Font font = Workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);
        // 设置单元格自动换行
        cellStyle.setWrapText(true);
        // 单元格垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 水平居中
        // cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置通用的单元格属性
        setCommonCellStyle(cellStyle);
        return cellStyle;
    }

    /**
     * 设置通用的单元格属性
     *
     * @param cellStyle 要设置属性的单元格
     */
    private void setCommonCellStyle(CellStyle cellStyle) {
        // 居中
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置边框
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
    }

    /**
     * 将生成的Excel输出到指定目录
     * {@link Workbook}
     * 文件输出目录，包括文件名（.xls）
     */
    public static String write2FilePath(Workbook workbook, String filePath) {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            return filePath;
        } catch (Exception e) {
            throw new RuntimeException("将生成的Excel输出到指定目录失败");
        } finally {
            IOUtils.closeQuietly(fileOut);
            IOUtils.closeQuietly(workbook);
        }
    }

    /**
     * 扔给浏览器
     *
     * @param fileName
     * @param workBook
     * @throws IOException
     */
    public static void OutPutWorkBookResponse(String fileName, Workbook workBook) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();

        setFileName(request, response, fileName + ".xls");
        response.setContentType("application/octet-stream");
        workBook.write(response.getOutputStream());
        workBook.close();
    }

    /**
     * 设置浏览器数据
     *
     * @param request
     * @param response
     * @param exportFileName
     */
    private static void setFileName(HttpServletRequest request, HttpServletResponse response, String exportFileName) {
        String agentLower = request.getHeader("User-Agent").toLowerCase(); // 获取浏览器
        try {
            if (agentLower.contains("firefox")) {// 火狐浏览器使用base64编码
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + new String(exportFileName.getBytes("UTF-8"), "iso-8859-1") + "\"");
            } else if (agentLower.contains("msie")) {// IE使用url编码
                response.addHeader("Content-Disposition",
                        "attachment;filename=" + java.net.URLEncoder.encode(exportFileName, "UTF-8"));
            } else {
                response.addHeader("Content-Disposition",
                        "attachment;filename=" + java.net.URLEncoder.encode(exportFileName, "UTF-8"));

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将一个list均分成n个list
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result=new ArrayList<List<T>>();
        if(source.size() < n){
            List<T> list = new ArrayList<>();
            for(T t:source){
                list.add(t);
            }
            result.add(list);
            return result;
        }

        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * list合并
     * @param source
     * @return
     */
    public static  List<ReportRespDetail> averageMerge(List<List<ReportRespDetail>> source){
        int size = source.size();
        List<ReportRespDetail> result=new ArrayList<ReportRespDetail>(size);

        for(List<ReportRespDetail> list:source){
            ReportRespDetail respDetail = new ReportRespDetail();
            int i = 0;
            for(ReportRespDetail t:list){
                if(i == 0){
                    BeanCopyUtil.copy(t,respDetail);
                }else{
                    respDetail.getDataList().addAll(t.getDataList());
                }
                i ++;
            };
            result.add(respDetail);
        }
        return result;
    }

    /**
     * 把list 按 excelDetailCount 数据分割
     * @param resList
     * @param count
     * @return
     */
//    public static <T> List<List<List<String>>> averageDivision(List<List<T>> resList, Integer count) {
    public static  <T> List<List<T>> averageDivision(List<T> resList,int count){
        if(resList==null ||count<1)
            return  null ;
        List<List<T>> ret=new ArrayList<List<T>>();
        int size=resList.size();
        if(size<=count){ //数据量不足count指定的大小
            ret.add(resList);
        }else{
            int pre=size/count;
            int last=size%count;
            //前面pre个集合，每个大小都是count个元素
            for(int i=0;i<pre;i++){
                List<T> itemList=new ArrayList<T>();
                for(int j=0;j<count;j++){
                    itemList.add(resList.get(i*count+j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if(last>0){
                List<T> itemList=new ArrayList<T>();
                for(int i=0;i<last;i++){
                    itemList.add(resList.get(pre*count+i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }
}
