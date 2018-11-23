package com.ebase.report;

import com.ebase.report.common.*;
import com.ebase.report.core.db.DataDetailSQL;
import com.ebase.report.core.db.handler.ReportAccessorDb2;
import com.ebase.report.core.db.handler.ReportAccessorMySql;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.cube.Dimension;
import com.ebase.report.model.dynamic.*;

import java.util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlTest {

    public static void main(String[] args) {
        ReportEchoBody reportEchoBody = new ReportEchoBody();

        reportEchoBody.setSubjectName("123");
        String s = "{\"personalSubjectId\":1,\"datasourceName\":\"reports\",\"databaseType\":\"mysql\",\"subjectType\":\"datatable\",\"subjectName\":\"测试主题\",\"subjectSourceEnum\":\"datatable\",\"reportTables\":[{\"tableId\":5,\"tableCode\":\"acct_info\",\"tableName\":\"测试表名\",\"demo\":null,\"tableType\":null,\"datasourceName\":null,\"personalSubjectId\":null,\"pageSize\":10,\"pageNum\":1}],\"rptDataFields\":[{\"fieldId\":16,\"tableId\":5,\"tableName\":null,\"tableCode\":null,\"fieldCode\":\"LOGIN_SOURCE\",\"fieldName\":\"登录来源\",\"fieldType\":\"VARCHAR\",\"dimensionIndex\":\"dimension\",\"accuracy\":0,\"rowLevelAuth\":1,\"remark\":\"13213\",\"metadataCount\":null,\"createdBy\":\"123\",\"createdDt\":\"2018-10-24 16:06:45\",\"updatedBy\":null,\"updatedDt\":null,\"datasourceName\":null,\"rptDataDicts\":[{\"dataDictId\":3,\"fieldId\":16,\"tableId\":2,\"fieldValue\":\"pc\",\"createdBy\":\"2\",\"createdDt\":\"2018-10-09 16:14:30\",\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":6,\"fieldId\":16,\"tableId\":2,\"fieldValue\":\"app\",\"createdBy\":\"2\",\"createdDt\":\"2018-10-08 16:14:37\",\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":7,\"fieldId\":16,\"tableId\":2,\"fieldValue\":\"app\",\"createdBy\":\"2\",\"createdDt\":\"2018-10-07 16:14:40\",\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":8,\"fieldId\":16,\"tableId\":2,\"fieldValue\":\"app\",\"createdBy\":\"2\",\"createdDt\":\"2018-10-16 16:14:42\",\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":12,\"fieldId\":16,\"tableId\":5,\"fieldValue\":\"pc\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":13,\"fieldId\":16,\"tableId\":5,\"fieldValue\":\"app\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1}],\"pageSize\":3,\"pageNum\":1,\"ids\":[]},{\"fieldId\":17,\"tableId\":5,\"tableName\":null,\"tableCode\":null,\"fieldCode\":\"ACCT_TITLE\",\"fieldName\":\"账户名称\",\"fieldType\":\"VARCHAR\",\"dimensionIndex\":\"dimension\",\"accuracy\":1,\"rowLevelAuth\":1,\"remark\":\"123213\",\"metadataCount\":null,\"createdBy\":\"231\",\"createdDt\":\"2018-10-24 16:06:42\",\"updatedBy\":\"2\",\"updatedDt\":\"2018-10-26 16:06:48\",\"datasourceName\":null,\"rptDataDicts\":[{\"dataDictId\":14,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"首自信\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":15,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"cjgly\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":16,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100001\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":17,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100002\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":18,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100009\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":19,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100027\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":20,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100032\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":21,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100039\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":22,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100064\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":23,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100083\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":24,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100101\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":25,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100108\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":26,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100222\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":27,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100419\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":28,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100450\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":29,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"100466\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":30,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"迁钢管理员\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":31,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"liuzhitao\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":32,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"zhangying\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":33,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"yangtong\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":34,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"weihao\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":35,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"wushanbing\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":36,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"wangxin\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":37,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"zhangwenhao\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":38,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"test123\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":39,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"lifengfeng\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":40,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"xiaoze\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":41,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"xiaoze1\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":42,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"xiaoze2\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":43,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"xiaoze3\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":44,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"lifeng\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":45,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"haoyou\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":46,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"haoyou1\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":47,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"haoyou3\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":48,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"haoyou4\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":49,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"dyf测试账号\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":50,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"121212\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":51,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"jiyuhao\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":52,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"111\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":53,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"系统管理员-测试\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":54,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"test\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":55,\"fieldId\":17,\"tableId\":5,\"fieldValue\":\"dyf测试-管理员\",\"createdBy\":null,\"createdDt\":null,\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1}],\"pageSize\":3,\"pageNum\":1,\"ids\":[]},{\"fieldId\":18,\"tableId\":5,\"tableName\":null,\"tableCode\":null,\"fieldCode\":\"ACCT_PASSWORD\",\"fieldName\":\"账户密码\",\"fieldType\":\"VARCHAR\",\"dimensionIndex\":\"dimension\",\"accuracy\":1,\"rowLevelAuth\":0,\"remark\":\"2\",\"metadataCount\":null,\"createdBy\":\"12\",\"createdDt\":\"2018-10-24 16:07:35\",\"updatedBy\":\"2\",\"updatedDt\":\"2018-10-25 16:07:38\",\"datasourceName\":null,\"rptDataDicts\":[{\"dataDictId\":4,\"fieldId\":18,\"tableId\":2,\"fieldValue\":\"e10adc3949ba59abbe56e057f20f883e\",\"createdBy\":\"2\",\"createdDt\":\"2018-10-08 16:14:32\",\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":0},{\"dataDictId\":11,\"fieldId\":18,\"tableId\":2,\"fieldValue\":\"e9f5c5240c0bb39488e6dbfbdb1517e0\",\"createdBy\":\"2\",\"createdDt\":\"2018-10-16 16:14:50\",\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1}],\"pageSize\":3,\"pageNum\":1,\"ids\":[]},{\"fieldId\":19,\"tableId\":5,\"tableName\":null,\"tableCode\":null,\"fieldCode\":\"O_INFO_ID\",\"fieldName\":\"组织ID\",\"fieldType\":\"BIGINT\",\"dimensionIndex\":\"dimension\",\"accuracy\":1,\"rowLevelAuth\":0,\"remark\":\"231\",\"metadataCount\":null,\"createdBy\":\"112\",\"createdDt\":\"2018-10-15 16:08:27\",\"updatedBy\":\"2\",\"updatedDt\":\"2018-10-26 16:08:31\",\"datasourceName\":null,\"rptDataDicts\":[{\"dataDictId\":5,\"fieldId\":19,\"tableId\":2,\"fieldValue\":\"4\",\"createdBy\":\"2\",\"createdDt\":\"2018-10-15 16:14:35\",\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1},{\"dataDictId\":9,\"fieldId\":19,\"tableId\":2,\"fieldValue\":\"2\",\"createdBy\":\"2\",\"createdDt\":\"2018-10-15 16:14:45\",\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":0},{\"dataDictId\":10,\"fieldId\":19,\"tableId\":2,\"fieldValue\":\"3\",\"createdBy\":\"2\",\"createdDt\":\"2018-10-15 16:14:47\",\"updatedBy\":null,\"updatedDt\":null,\"isChecked\":1}],\"pageSize\":3,\"pageNum\":1,\"ids\":[]},{\"fieldId\":20,\"tableId\":5,\"tableName\":null,\"tableCode\":null,\"fieldCode\":\"REGISTER_TIME\",\"fieldName\":\"注册时间\",\"fieldType\":\"DATETIME\",\"dimensionIndex\":\"dimension\",\"accuracy\":1,\"rowLevelAuth\":0,\"remark\":\"123\",\"metadataCount\":null,\"createdBy\":\"1\",\"createdDt\":\"2018-10-15 16:09:09\",\"updatedBy\":null,\"updatedDt\":null,\"datasourceName\":null,\"rptDataDicts\":[],\"pageSize\":3,\"pageNum\":1,\"ids\":[]}],\"rptDataIndexs\":[{\"fieldId\":21,\"tableId\":5,\"tableName\":null,\"tableCode\":null,\"fieldCode\":\"EMAIL\",\"fieldName\":\"电子邮件\",\"fieldType\":\"VARCHAR\",\"dimensionIndex\":\"measures\",\"accuracy\":0,\"rowLevelAuth\":0,\"remark\":\"213\",\"metadataCount\":null,\"createdBy\":\"123\",\"createdDt\":\"2018-10-16 16:09:56\",\"updatedBy\":\"2\",\"updatedDt\":\"2018-10-25 16:10:01\",\"datasourceName\":null,\"rptDataDicts\":[],\"pageSize\":3,\"pageNum\":1,\"ids\":[]},{\"fieldId\":22,\"tableId\":5,\"tableName\":null,\"tableCode\":null,\"fieldCode\":\"MOBILE_PHONE\",\"fieldName\":\"手机号\",\"fieldType\":\"VARCHAR\",\"dimensionIndex\":\"measures\",\"accuracy\":0,\"rowLevelAuth\":0,\"remark\":\"213\",\"metadataCount\":null,\"createdBy\":\"321\",\"createdDt\":\"2018-10-22 16:10:42\",\"updatedBy\":\"2\",\"updatedDt\":\"2018-10-26 16:10:46\",\"datasourceName\":null,\"rptDataDicts\":[],\"pageSize\":3,\"pageNum\":1,\"ids\":[]}],\"rptMeasures\":[{\"measureId\":30,\"measuresName\":\"q\",\"fieldId\":21,\"fieldCode\":null,\"measureType\":\"count\",\"subjectId\":1,\"removeStatus\":0,\"createdBy\":\"1\",\"createdDt\":\"2018-11-01 13:54:28\",\"updatedBy\":null,\"updatedDt\":null,\"measureRule\":null,\"rptMeasuresBody\":null,\"isChecked\":1},{\"measureId\":32,\"measuresName\":\"x\",\"fieldId\":21,\"fieldCode\":null,\"measureType\":\"count\",\"subjectId\":1,\"removeStatus\":0,\"createdBy\":\"1\",\"createdDt\":\"2018-11-01 14:03:39\",\"updatedBy\":null,\"updatedDt\":null,\"measureRule\":null,\"rptMeasuresBody\":null,\"isChecked\":0},{\"measureId\":33,\"measuresName\":\"带娃大无——1\",\"fieldId\":null,\"fieldCode\":null,\"measureType\":\"custom\",\"subjectId\":1,\"removeStatus\":0,\"createdBy\":\"1\",\"createdDt\":\"2018-11-01 14:22:53\",\"updatedBy\":null,\"updatedDt\":null,\"measureRule\":\"{\\\"expressionChinese\\\":\\\"[{\\\\\\\"measureId\\\\\\\":30,\\\\\\\"measuresName\\\\\\\":\\\\\\\"q\\\\\\\",\\\\\\\"fieldId\\\\\\\":21,\\\\\\\"fieldCode\\\\\\\":\\\\\\\"EMAIL\\\\\\\",\\\\\\\"measureType\\\\\\\":\\\\\\\"count\\\\\\\",\\\\\\\"subjectId\\\\\\\":1,\\\\\\\"removeStatus\\\\\\\":0,\\\\\\\"createdBy\\\\\\\":\\\\\\\"?????\\\\\\\",\\\\\\\"createdDt\\\\\\\":1541051668000,\\\\\\\"updatedBy\\\\\\\":null,\\\\\\\"updatedDt\\\\\\\":null,\\\\\\\"measureRule\\\\\\\":null,\\\\\\\"rptMeasuresBody\\\\\\\":null},{\\\\\\\"fieldCode\\\\\\\":\\\\\\\"*\\\\\\\",\\\\\\\"measuresName\\\\\\\":\\\\\\\"*\\\\\\\",\\\\\\\"operators\\\\\\\":1},{\\\\\\\"measureId\\\\\\\":32,\\\\\\\"measuresName\\\\\\\":\\\\\\\"x\\\\\\\",\\\\\\\"fieldId\\\\\\\":21,\\\\\\\"fieldCode\\\\\\\":\\\\\\\"EMAIL\\\\\\\",\\\\\\\"measureType\\\\\\\":\\\\\\\"count\\\\\\\",\\\\\\\"subjectId\\\\\\\":1,\\\\\\\"removeStatus\\\\\\\":0,\\\\\\\"createdBy\\\\\\\":\\\\\\\"?????\\\\\\\",\\\\\\\"createdDt\\\\\\\":1541052219000,\\\\\\\"updatedBy\\\\\\\":null,\\\\\\\"updatedDt\\\\\\\":null,\\\\\\\"measureRule\\\\\\\":null,\\\\\\\"rptMeasuresBody\\\\\\\":null},{\\\\\\\"fieldCode\\\\\\\":\\\\\\\"-\\\\\\\",\\\\\\\"measuresName\\\\\\\":\\\\\\\"-\\\\\\\",\\\\\\\"operators\\\\\\\":1},{\\\\\\\"measureId\\\\\\\":30,\\\\\\\"measuresName\\\\\\\":\\\\\\\"q\\\\\\\",\\\\\\\"fieldId\\\\\\\":21,\\\\\\\"fieldCode\\\\\\\":\\\\\\\"EMAIL\\\\\\\",\\\\\\\"measureType\\\\\\\":\\\\\\\"count\\\\\\\",\\\\\\\"subjectId\\\\\\\":1,\\\\\\\"removeStatus\\\\\\\":0,\\\\\\\"createdBy\\\\\\\":\\\\\\\"?????\\\\\\\",\\\\\\\"createdDt\\\\\\\":1541051668000,\\\\\\\"updatedBy\\\\\\\":null,\\\\\\\"updatedDt\\\\\\\":null,\\\\\\\"measureRule\\\\\\\":null,\\\\\\\"rptMeasuresBody\\\\\\\":null}]\\\",\\\"expressionEnglish\\\":\\\"count(EMAIL)*count(EMAIL)-count(EMAIL)\\\",\\\"customIndexTmp\\\":[{\\\"fieldId\\\":\\\"21\\\",\\\"fieldCode\\\":\\\"EMAIL\\\",\\\"measureTypeEnum\\\":\\\"COUNT\\\",\\\"measuresName\\\":\\\"q\\\"}]}\",\"rptMeasuresBody\":{\"expressionChinese\":\"[{\\\"measureId\\\":30,\\\"measuresName\\\":\\\"q\\\",\\\"fieldId\\\":21,\\\"fieldCode\\\":\\\"EMAIL\\\",\\\"measureType\\\":\\\"count\\\",\\\"subjectId\\\":1,\\\"removeStatus\\\":0,\\\"createdBy\\\":\\\"?????\\\",\\\"createdDt\\\":1541051668000,\\\"updatedBy\\\":null,\\\"updatedDt\\\":null,\\\"measureRule\\\":null,\\\"rptMeasuresBody\\\":null},{\\\"fieldCode\\\":\\\"*\\\",\\\"measuresName\\\":\\\"*\\\",\\\"operators\\\":1},{\\\"measureId\\\":32,\\\"measuresName\\\":\\\"x\\\",\\\"fieldId\\\":21,\\\"fieldCode\\\":\\\"EMAIL\\\",\\\"measureType\\\":\\\"count\\\",\\\"subjectId\\\":1,\\\"removeStatus\\\":0,\\\"createdBy\\\":\\\"?????\\\",\\\"createdDt\\\":1541052219000,\\\"updatedBy\\\":null,\\\"updatedDt\\\":null,\\\"measureRule\\\":null,\\\"rptMeasuresBody\\\":null},{\\\"fieldCode\\\":\\\"-\\\",\\\"measuresName\\\":\\\"-\\\",\\\"operators\\\":1},{\\\"measureId\\\":30,\\\"measuresName\\\":\\\"q\\\",\\\"fieldId\\\":21,\\\"fieldCode\\\":\\\"EMAIL\\\",\\\"measureType\\\":\\\"count\\\",\\\"subjectId\\\":1,\\\"removeStatus\\\":0,\\\"createdBy\\\":\\\"?????\\\",\\\"createdDt\\\":1541051668000,\\\"updatedBy\\\":null,\\\"updatedDt\\\":null,\\\"measureRule\\\":null,\\\"rptMeasuresBody\\\":null}]\",\"expressionEnglish\":\"count(EMAIL)*count(EMAIL)-count(EMAIL)\",\"customIndexTmp\":[{\"fieldId\":\"21\",\"fieldCode\":\"EMAIL\",\"measureTypeEnum\":\"COUNT\",\"measuresName\":\"q\"}]},\"isChecked\":null}],\"isChecked\":1,\"reportDynamicParam\":{\"personalSubjectId\":1,\"line\":[{\"fieldId\":\"18\",\"fieldCode\":\"ACCT_PASSWORD\",\"fieldName\":\"账户密码\",\"position\":null,\"subTotal\":false,\"demandType\":\"DIMENSION\",\"lev\":0,\"reportMeasures\":null,\"dbFieldTypeEnum\":\"VARCHAR\",\"rowLevelAuth\":0,\"expressionTmp\":{},\"reportDimensions\":null,\"key\":\"F18\"}],\"column\":[{\"fieldId\":\"19\",\"fieldCode\":\"O_INFO_ID\",\"fieldName\":\"组织ID\",\"position\":null,\"subTotal\":false,\"demandType\":\"DIMENSION\",\"lev\":0,\"reportMeasures\":null,\"dbFieldTypeEnum\":\"VARCHAR\",\"rowLevelAuth\":0,\"expressionTmp\":{},\"reportDimensions\":null,\"key\":\"F19\"},{\"fieldId\":null,\"fieldCode\":null,\"fieldName\":\"Measures\",\"position\":null,\"subTotal\":false,\"demandType\":\"DIMENSION\",\"lev\":0,\"reportMeasures\":null,\"dbFieldTypeEnum\":\"VARCHAR\",\"rowLevelAuth\":0,\"expressionTmp\":{},\"reportDimensions\":null,\"key\":\"Fnull\"}],\"filter\":[{\"code\":\"REGISTER_TIME\",\"name\":\"注册时间\",\"dbFieldTypeEnum\":\"DATETIME\",\"tmp\":[{\"isChecked\":0,\"fieldVal\":{\"RG\":\"\"}},{\"isChecked\":1,\"fieldVal\":{\"EQ\":\"\"}},{\"isChecked\":0,\"fieldVal\":{\"GT\":\"\",\"LT\":\"\"}}]}],\"measures\":[],\"tbs\":[{\"fieldId\":\"16\",\"fieldCode\":\"LOGIN_SOURCE\",\"fieldName\":\"登录来源\",\"position\":null,\"subTotal\":false,\"demandType\":\"DIMENSION\",\"lev\":0,\"reportMeasures\":null,\"dbFieldTypeEnum\":\"VARCHAR\",\"rowLevelAuth\":1,\"expressionTmp\":{},\"reportDimensions\":null,\"key\":\"F16\"},{\"fieldId\":\"17\",\"fieldCode\":\"ACCT_TITLE\",\"fieldName\":\"账户名称\",\"position\":null,\"subTotal\":false,\"demandType\":\"DIMENSION\",\"lev\":0,\"reportMeasures\":null,\"dbFieldTypeEnum\":\"VARCHAR\",\"rowLevelAuth\":1,\"expressionTmp\":{},\"reportDimensions\":null,\"key\":\"F17\"}]}}";
//        System.out.println(JsonUtil.fromJson(s,ReportEchoBody.class));
//        demo();

        ReportDatasource reportDatasource = new ReportDatasource();
        reportDatasource.setDatasourceName("测试数据源名称");
        reportDatasource.setDatabaseType("mysql");
        reportDatasource.setSubjectType("datatable");  //主题类型

        List<ReportTable> arr = new ArrayList<>();
        ReportTable reportTable = new ReportTable();
        reportTable.setTableCode("movie_data_10w");
        reportTable.setTableName("测试表");
        reportTable.setTableId(1L);
        arr.add(reportTable);
        reportDatasource.setReportTables(arr);


        ReportDynamicParam reportDynamicParam = new ReportDynamicParam();

        reportDatasource.setReportDynamicParam(reportDynamicParam);


        List<Dimension> column = new ArrayList<>();
        Dimension dimension2 = new Dimension();
        dimension2.setFieldId("2");
        dimension2.setFieldCode("movie_emotion");
        dimension2.setFieldName("类型");
        dimension2.setDemandType(DemandType.DIMENSION);
        dimension2.setDbFieldTypeEnum(DBFieldTypeEnum.VARCHAR);
        dimension2.setRowLevelAuth((byte) 1);


//        expressionTmp.put()
        List<ReportDimension> reportDimensions = new ArrayList<>();
        ReportDimension reportDimension = new ReportDimension();
        reportDimension.setFieldValue("冒险");
//        reportDimension.setValue("动作");
////        reportDimension.setValue("喜剧");
        reportDimensions.add(reportDimension);

        ReportDimension reportDimension2 = new ReportDimension();
        reportDimension2.setFieldValue("动作");
        reportDimensions.add(reportDimension2);

        ReportDimension reportDimension3 = new ReportDimension();
        reportDimension3.setFieldValue("喜剧");
        reportDimensions.add(reportDimension3);

        dimension2.setRptDataDicts(reportDimensions);

        column.add(dimension2);

        Dimension dimension3 = new Dimension();
        dimension3.setFieldId("1");
        dimension3.setFieldName("指标");
        dimension3.setDemandType(DemandType.MEASURES);

        List<ReportMeasure> reportMeasures = new ArrayList<>();
        ReportMeasure reportMeasure = new ReportMeasure();
        reportMeasure.setExpressionEnglish("(#M21_COUNT#+#M20_COUNT#)-(#M20_COUNT#+#M21_COUNT#)");
        reportMeasure.setMeasureType(MeasureTypeEnum.CUSTOM);
        reportMeasure.setFieldId("9");
        reportMeasure.setFieldName("自定义指标");
        reportMeasure.setDemandType(DemandType.MEASURES);
//        reportMeasure.setIsChecked();
        List<ReportMeasure> customIndexTmp = new ArrayList<>(); //这个是指标
        ReportMeasure reportMeasure1 = new ReportMeasure();
        reportMeasure1.setFieldId("21");
        reportMeasure1.setFieldCode("movie_age");
        reportMeasure1.setFieldName("年龄数量");
        reportMeasure1.setDemandType(DemandType.MEASURES);
        reportMeasure1.setMeasureType(MeasureTypeEnum.COUNT);

        customIndexTmp.add(reportMeasure1);


        ReportMeasure reportMeasure2 = new ReportMeasure();
        reportMeasure2.setFieldId("20");
        reportMeasure2.setFieldCode("movie_title");
        reportMeasure2.setFieldName("标题数量");
        reportMeasure2.setDemandType(DemandType.MEASURES);
        reportMeasure2.setMeasureType(MeasureTypeEnum.COUNT);

        customIndexTmp.add(reportMeasure2);

        reportMeasure.setCustomIndexTmp(customIndexTmp);
        reportMeasures.add(reportMeasure);
        dimension3.setRptMeasures(reportMeasures);
        column.add(dimension3);



        reportDynamicParam.setColumn(column);


        Set<ReportMeasure> measures = new HashSet<>();
//        ReportMeasure reportMeasure = new ReportMeasure();
//        reportMeasure.setMeasureEnum(MeasureTypeEnum.COUNT);
//        reportMeasure.setFieldCode("1");
//        reportMeasure.setFieldId("00");
//        reportMeasure.setDemandType(DemandType.MEASURES);
//        measures.add(reportMeasure);


//        ReportMeasure reportMeasure2 = new ReportMeasure();
//        reportMeasure2.setMeasureEnum(MeasureTypeEnum.CUSTOM);
////        reportMeasure2.setCode("1");
//        reportMeasure2.setFieldId("01");
//        reportMeasure2.setDemandType(DemandType.MEASURES);
//        reportMeasure2.setExpression("(#a.movie_title#+#a.movie_age#)-(#a.movie_age#+#a.movie_title#)");

//        List<ReportMeasure> customIndexTmp = new ArrayList<>();  //自定义指标下的 指标
//        ReportMeasure reportMeasure1 = new ReportMeasure();
////        customIndex.setFieldId();
//        reportMeasure1.setMeasureEnum(MeasureTypeEnum.MIN);
//        reportMeasure1.setExpression(""); //这个维度没有表达式
//        reportMeasure1.setFieldId("99");
//        reportMeasure1.setFieldCode("movie_age");
//
//
//        customIndexTmp.add(reportMeasure1);
//        reportMeasure2.setCustomIndexTmp(customIndexTmp);

//        measures.add(reportMeasure2);


        ReportMeasure reportMeasure3 = new ReportMeasure();
//        reportMeasure3.setMeasureEnum();
//        measures.add(reportMeasure3);
        reportDynamicParam.setMeasures(measures);



        List<FilterArea> filter = new ArrayList<>();
        FilterArea filterArea = new FilterArea();
        filterArea.setCode("movie_age");
        filterArea.setName("字段美女");
        filterArea.setDbFieldTypeEnum("String");

        List<FilterAreaValue> areaValues = new ArrayList<>();
        FilterAreaValue filterAreaValue = new FilterAreaValue();
        filterAreaValue.setIsChecked(1);
        Map<FilterTypeEnum,String> tmp = new HashMap<>();
        tmp.put(FilterTypeEnum.GT,"R级");
        tmp.put(FilterTypeEnum.LT,"");
        filterAreaValue.setFieldVal(tmp);
//        filterAreaValue.setFilterTypeEnum(FilterTypeEnum.EQ);
        areaValues.add(filterAreaValue);

        FilterAreaValue filterAreaValue2 = new FilterAreaValue();
        filterAreaValue2.setIsChecked(0);
        Map<FilterTypeEnum,String> tmp2 = new HashMap<>();
        tmp2.put(FilterTypeEnum.RG,"G3");
        filterAreaValue2.setFieldVal(tmp2);
        areaValues.add(filterAreaValue2);

//        FilterAreaValue filterAreaValue2 = new FilterAreaValue();
////        filterAreaValue2.setIsCHecked((byte)1);
//        filterAreaValue2.setFieldValue("R级");
//        filterAreaValue2.setFilterTypeEnum(FilterTypeEnum.LT);
//        areaValues.add(filterAreaValue2);

//        filterArea.setTmp(areaValues);
//
//        filter.add(filterArea);
////        reportDynamicParam.setFilter(filter);
//        ReportAccessorDb2 reportAccessorMySql = new ReportAccessorDb2();
//        String reportSql = reportAccessorMySql.getReportSql(reportDatasource);
//
//        DataDetailSQL reportFromDetailSql = reportAccessorMySql.getReportFromDetailSql(reportDatasource);
//        System.out.println(JsonUtil.toJson(reportFromDetailSql));
//
//        String reportSql1 = reportAccessorMySql.getReportSql(reportDatasource);
//        System.out.println(reportSql1);

//        System.out.println(Class.class.getResource("/").getPath());


        demo();

    }

    private static void demo() {
        ReportAccessorMySql reportAccessorMySql = new ReportAccessorMySql();
        ReportDatasource reportDatasource = new ReportDatasource();
        reportDatasource.setDatasourceName("测试数据源名称");
        reportDatasource.setDatabaseType("mysql");
        reportDatasource.setSubjectType("datatable");  //主题类型

        List<ReportTable> arr = new ArrayList<>();
        ReportTable reportTable = new ReportTable();
        reportTable.setTableCode("movie_data_10w");
        reportTable.setTableName("测试表");
        reportTable.setTableId(1L);
        arr.add(reportTable);
        reportDatasource.setReportTables(arr);

        ReportDynamicParam reportDynamicParam = new ReportDynamicParam();
        reportDynamicParam.setPersonalSubjectId(null);  //未null 就是单表的

        //拼装前台参数
        List<Dimension> column = new ArrayList<>();

//        Dimension dimension = new Dimension();
//        dimension.setFieldId("1");
//        dimension.setCode("movie_area");
//        dimension.setName("地区");
//        dimension.setDemandType(DemandType.DIMENSION);
//        dimension.setDbFieldTypeEnum(DBFieldTypeEnum.VARCHAR);
//        dimension.setRowLevelAuth((byte)1);
////        dimension.set
//        column.add(dimension);


        Dimension dimension2 = new Dimension();
        dimension2.setFieldId("2");
        dimension2.setFieldCode("movie_emotion");
        dimension2.setFieldName("类型");
        dimension2.setDemandType(DemandType.DIMENSION);
        dimension2.setDbFieldTypeEnum(DBFieldTypeEnum.VARCHAR);
        dimension2.setRowLevelAuth((byte) 1);


//        expressionTmp.put()
        List<ReportDimension> reportDimensions = new ArrayList<>();
        ReportDimension reportDimension = new ReportDimension();
        reportDimension.setFieldValue("冒险");
//        reportDimension.setValue("动作");
////        reportDimension.setValue("喜剧");
        reportDimensions.add(reportDimension);

        ReportDimension reportDimension2 = new ReportDimension();
        reportDimension2.setFieldValue("动作");
        reportDimensions.add(reportDimension2);

        ReportDimension reportDimension3 = new ReportDimension();
        reportDimension3.setFieldValue("喜剧");
        reportDimensions.add(reportDimension3);

        dimension2.setRptDataDicts(reportDimensions);

        column.add(dimension2);

//        Dimension dimension3 = new Dimension();
//        dimension3.setFieldId("3");
//        dimension3.setCode("movie_score");
//        dimension3.setName("年龄");
//        dimension3.setDemandType(DemandType.DIMENSION);
//        dimension3.setDbFieldTypeEnum(DBFieldTypeEnum.VARCHAR);
//
//        Map<FilterType,List<String>> expressionTmp = new HashMap<>();
//
//        expressionTmp.put(FilterType.LESS_THAN,Arrays.asList(new String[]{"2"}));
//        expressionTmp.put(FilterType.GREATER_THAN,Arrays.asList(new String[]{"1.5"}));
//        dimension3.setExpressionTmp(expressionTmp);
//        dimension3.setRowLevelAuth((byte)1);
//        column.add(dimension3);


//        column.add(dimension);
        //指标 如果是度量值时 会深一级
        Dimension dimension4 = new Dimension();
        dimension4.setFieldId("4");
//        dimension4.setCode("movie_score");  //也是库里的某个字段
//        dimension4.setName("sum(movie_score)");           //某个字段名
        dimension4.setDemandType(DemandType.MEASURES);

        List<Dimension> measures = new ArrayList<>();
        Dimension measure = new Dimension();
        measure.setFieldId("4");  //id没有id
//        measure.setMeasureEnum(MeasureTypeEnum.SUM);
        measure.setFieldCode("movie_score");
        measures.add(measure);

        Dimension measure2 = new Dimension();
        measure2.setFieldId("5");  //id没有id
//        measure2.setMeasureEnum(MeasureTypeEnum.COUNT);
        measure2.setFieldCode("movie_score");
        measures.add(measure2);

        Dimension measure3 = new Dimension();
//        measure3.setFieldId("6");  //id没有id
//        measure3.setMeasureEnum(MeasureTypeEnum.CUSTOM);
//        measure3.setCode("movie_score");
        List<CustomIndex> customIndexTmp = new ArrayList<>();
        CustomIndex customIndex = new CustomIndex();
        customIndex.setFieldId("6");
        customIndex.setFieldCode("movie_score");
        customIndexTmp.add(customIndex);

//        CustomIndex customIndex2 = new CustomIndex();
//        customIndex2.setFieldId("7");
//        customIndex2.setCode("movie_score");
//        customIndexTmp.add(customIndex2);
//        measure3.setCustomIndexTmp(customIndexTmp);
        measures.add(measure3);


//        dimension4.setMeasures(measures);


        dimension4.setDbFieldTypeEnum(DBFieldTypeEnum.VARCHAR);

        Map<FilterTypeEnum, List<String>> expressionTmp = new HashMap<>();

        expressionTmp.put(FilterTypeEnum.LT, Arrays.asList(new String[]{"2"}));
        expressionTmp.put(FilterTypeEnum.GT, Arrays.asList(new String[]{"1.5"}));
        dimension4.setExpressionTmp(expressionTmp);
        dimension4.setRowLevelAuth((byte) 1);

        column.add(dimension4);


        reportDynamicParam.setLine(column);
//        reportDynamicParam.setColumn(column);
        // 列
        // 行维度/度量 集合
//        List<Dimension> line = new ArrayList<>();
//        Dimension dimension1 = new Dimension();
//        dimension1.setFieldId("4");
//        dimension1.setCode("movie_type");
//        dimension1.setName("题材");
//        dimension1.setDemandType(DemandType.DIMENSION);
//        dimension1.setDbFieldTypeEnum(DBFieldTypeEnum.VARCHAR);
//        dimension1.setRowLevelAuth((byte)1);
//        line.add(dimension1);
//
//        reportDynamicParam.setLine(line);

        //过滤区
        List<FilterArea> filter = new ArrayList<>();
        FilterArea filterArea = new FilterArea();
        filterArea.setCode("movie_type");
        filterArea.setName("题材");
        filterArea.setDbFieldTypeEnum("String");

        Map<FilterTypeEnum, List<String>> tmp = new HashMap<>();
        tmp.put(FilterTypeEnum.EQ, Arrays.asList(new String[]{"体育", "战争"}));
//        filterArea.setTmp(tmp);

        filter.add(filterArea);

        FilterArea filterArea2 = new FilterArea();
        filterArea2.setCode("movie_score");
        filterArea2.setName("??");
        filterArea2.setDbFieldTypeEnum("String");

        Map<FilterTypeEnum, List<String>> tmp2 = new HashMap<>();
        tmp2.put(FilterTypeEnum.GT, Arrays.asList(new String[]{"0.01"}));
        tmp2.put(FilterTypeEnum.LT, Arrays.asList(new String[]{"4.5"}));
//        filterArea2.setTmp(tmp2);

        filter.add(filterArea2);

        reportDynamicParam.setFilter(filter);


        reportDatasource.setReportDynamicParam(reportDynamicParam);

//        System.out.println(JsonUtil.toJson(reportDatasource));
//        DataDetailSQL reportFromDetailSql = reportAccessorMySql.getReportFromDetailSql(reportDatasource);

//        System.out.println(reportFromDetailSql);


        //10 1       20   11   30  21
//        int count = 1;
//        int ding = 10000;
////        String limit = " limit ";
//        String prefix = " WHERE ROWNUM <= ";
//        String suffix = " ) WHERE RN >= ";
//
//        int i1 = count / ding;
//        i1 = count % ding == 0 ? i1 - 1 : i1;
//        for (int i = 0; i <= i1; i++) {
//            String sqlP = prefix + (i + 1) * ding;
//            String sqlS = suffix + (i * ding + 1);
//
//        }

//        System.out.println(ding%count);


        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:db2://10.68.28.25:50049/sgdw:currentSchema=REPORT;";

        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.ibm.db2.jcc.DB2Driver");// 动态加载mysql驱动


            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url,"db2inst1","db2inst1");

            sql = "\n" +
                    "SELECT * FROM MOVIE_DATA_10W ";

            PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
//            ResultSet resultSet = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
//            // 获得列的总数
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
//                    String columnName = rsmd.getColumnName(i + 1);
//                    String tableName = rsmd.getTableName(i + 1);
//                    String columnClassName = rsmd.getColumnClassName(i + 1);
//                    String catalogName = rsmd.getCatalogName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
//                    Object columnValue = rs.getObject(columnName);
                    String string = rs.getString(columnLabel);
                    System.out.println(columnLabel + string);
//
                }
//                // 根据dimensionKey 初始化树结点
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误" + e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            }catch (Exception e){

            }

        }
    }
}
