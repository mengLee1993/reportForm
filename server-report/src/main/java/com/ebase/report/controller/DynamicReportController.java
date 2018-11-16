package com.ebase.report.controller;

import com.ebase.report.common.DemandType;
import com.ebase.report.common.ExcelAttribute;
import com.ebase.report.common.DemandPositionType;
import com.ebase.report.cube.AxesxData;
import com.ebase.report.cube.AxesyData;
import com.ebase.report.cube.CubeData;
import com.ebase.report.cube.DimensionData;
import com.ebase.report.model.*;
import com.ebase.report.service.SqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 动态报表
 * @Auther: wangyu
 */
@RestController
@RequestMapping("/report")
public class DynamicReportController {


    private static Logger LOG = LoggerFactory.getLogger(DynamicReportController.class);


    /**
     * 获得动态数据接口
     *
     * @return
     */
    @RequestMapping("/dynamicData")
    private ReportResp dynamicData(){
//    private ReportResp dynamicData(@RequestBody MoviePram moviePram){
        ReportResp movieResp = new ReportResp();

//        List<String> line = moviePram.getLine();
//        List<String> column = moviePram.getColumn();
        MoviePram moviePram = new MoviePram();

        List<String> line = new ArrayList();
        line.add("movie_area");
//        line.add("measures");
        line.add("movie_type");
        line.add("movie_emotion");

        //movie_age
        List<String> column = new ArrayList();
        column.add("movie_age");
//        column.add("movie_emotion");
        column.add("measures");

        moviePram.setLine(line);
        moviePram.setColumn(column);



        String sql = " SELECT * FROM movie_data_10w ";
        String[] pram = null;

        sql = generateSqlByTmp(moviePram);

        CubeData cubeData = new CubeData();

        int index = line.size();
        for(String str : line){
//            String[] split = str.split("_");//可能存在小计,合计等东西
//            str = split[0];
            cubeData.getLineDims().add(str);
            DimensionData dimensionData = new DimensionData();
            dimensionData.setPositionType(DemandPositionType.LINE); //行
            dimensionData.setCode(str);
            dimensionData.setLev(index--);

            if("movie_type".equals(str)){

            }
            cubeData.getDimensionDataMap().put(str,dimensionData);
        }

        index = column.size();
        for(String str : column){
            cubeData.getColumnDims().add(str);
            DimensionData dimensionData = new DimensionData();
            dimensionData.setPositionType(DemandPositionType.COLUMN); //列
            dimensionData.setCode(str);
            dimensionData.setLev(index--);
            cubeData.getDimensionDataMap().put(str,dimensionData);
        }

        // 对measures 需要进行特殊处理
        initMeasures(cubeData);

        cubeData = SqlService.execQuery(sql, pram, cubeData); //N个结果

//        movieResp.setCode("000000");
//
//        // 行数据json处理
//        List<AxesxData> axesxData = cubeData.axisxData();
//        movieResp.getResults().put("axesxData", axesxData);
//
//        // 列数据json处理
//        List<List<AxesyData>> axisyDatas = cubeData.axisyData();
//        movieResp.getResults().put("axisyData", axisyDatas);

        // 计算小计，合计信息


        return movieResp;
    }

    private Map<String,String> getTableField() {
        Map<String,String> tmp = new HashMap<>();
        Field[] declaredFields = MovieData.class.getDeclaredFields();
        for(Field field:declaredFields){
            ExcelAttribute annotation = field.getAnnotation(ExcelAttribute.class);
            String t_name = annotation.name(); //表
            String b_name = field.getName(); //字段
            tmp.put(b_name,t_name);

        }

        return tmp;
    }

    /**
     * 生成sql
     * @param
     * @return
     */
    private String generateSqlByTmp(MoviePram moviePram) {

        StringBuffer buffer = new StringBuffer();

        StringBuffer bufferSel = new StringBuffer(" select ");
        StringBuffer bufferSe2 = new StringBuffer(" COUNT(1) as movieCount, sum(movie_score) as movieScore, AVG(movie_score) as scoreAvg  ");
        StringBuffer bufferWhe = new StringBuffer(" from movie_data_10w group by ");

        for(String str:moviePram.getLine()){
            if(!"measures".equals(str)){  //measures 如果不是度量值
                bufferSel.append(str+",");
                bufferWhe.append(str+",");
            }
        }

        for(String str : moviePram.getColumn()){
            if(!"measures".equals(str)){
                bufferSel.append(str+",");
                bufferWhe.append(str+",");
            }
        }

        buffer.append(bufferSel);

        buffer.append(bufferSe2);
        buffer.append(bufferWhe.substring(0, bufferWhe.toString().lastIndexOf(",")));
        return buffer.toString();
    }

    /**
     * 区分那个字段有值 并标记出那个是度量值   k 非空的字段 v 那个是度量值  1 true 2 false
     *  度量值先这样写 demo 版 肯定要有地方维护
     * @param line
     * @return
     */
    private Map<String,Integer> getMetricIsNotNull(MovieData line) {
        Map<String,Integer> tmp = new TreeMap<>();

        if(line.getMovieCode() != null){
            tmp.put("movieCode",1);
        }
        if(line.getMovieTitle() != null){
            tmp.put("movieTitle",2);
        }
        if(line.getMovieAreaId() != null){
            tmp.put("movieAreaId",1);
        }
        if(line.getMovieArea() != null){
            tmp.put("movieArea",2);
        }
        if(line.getMovieDate() != null){
            tmp.put("movieDate",1);
        }
        if(line.getMovieTypeId() != null){
            tmp.put("movieTypeId",2);
        }
        if(line.getMovieType() != null){
            tmp.put("movieType",2);
        }
        if(line.getMovieEmotionId() != null){
            tmp.put("movieEmotionId",2);
        }
        if(line.getMovieEmotion() != null){
            tmp.put("movieEmotion",2);
        }
        if(line.getMovieAgeId() != null){
            tmp.put("movieAgeId",2);
        }
        if(line.getMovieAge() != null){
            tmp.put("movieAge",2);
        }
        if(line.getMovieScore() != null){
            tmp.put("movieScore",1);
        }
        return tmp;
    }

    // 初始化度量值
    private void initMeasures(CubeData cubeData){
        // as movieCount, sum(movie_score) as movieScore, scoreAvg
        cubeData.getMeasures().add("movieCount");
        cubeData.getMeasures().add("movieScore");
        cubeData.getMeasures().add("scoreAvg");


        DimensionData dimensionData = new DimensionData();
        dimensionData.setPositionType(DemandPositionType.LINE);
        dimensionData.setCode("movieCount");
        dimensionData.setDemandType(DemandType.MEASURES);
        cubeData.getDimensionDataMap().put("movieCount",dimensionData);

        DimensionData movieScore = new DimensionData();
        movieScore.setPositionType(DemandPositionType.LINE);
        movieScore.setCode("movieScore");
        movieScore.setDemandType(DemandType.MEASURES);
        cubeData.getDimensionDataMap().put("movieScore",movieScore);

        DimensionData scoreAvg = new DimensionData();
        scoreAvg.setPositionType(DemandPositionType.LINE);
        scoreAvg.setCode("scoreAvg");
        scoreAvg.setDemandType(DemandType.MEASURES);
        cubeData.getDimensionDataMap().put("scoreAvg",scoreAvg);

        // 度量值需要单独做为一个维度看待
        DimensionData measuresData = new DimensionData();
        measuresData.setPositionType(DemandPositionType.COLUMN);
        measuresData.setCode("measures");
        measuresData.setDemandType(DemandType.MEASURES);
        measuresData.getValues().put("movieCount","movieCount");
        measuresData.getValues().put("movieScore","movieScore");
        measuresData.getValues().put("scoreAvg","scoreAvg");

        cubeData.getDimensionDataMap().put("measures",measuresData);

    }



}
