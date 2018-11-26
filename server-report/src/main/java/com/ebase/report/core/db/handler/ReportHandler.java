package com.ebase.report.core.db.handler;

import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.DataBaseUtil;
import com.ebase.report.core.db.DataDetailSQL;
import com.ebase.report.core.db.conn.ConnPoolType;
import com.ebase.report.core.db.conn.DataSourceConfig;
import com.ebase.report.core.db.conn.DataSourceManager;
import com.ebase.report.core.db.conn.DbConnFactory;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.pageUtil.PageReportDetail;
import com.ebase.report.core.utils.ReportExportUtil;
import com.ebase.report.cube.CubeTree;
import com.ebase.report.dao.RptDataFieldMapper;
import com.ebase.report.model.*;
import com.ebase.report.model.dynamic.ReportDatasource;
import com.ebase.report.service.RptAnalyseLogService;

import com.ebase.report.service.RptDataFieldService;
import com.ebase.report.vo.RptPersionalDownloadVO;

import com.ebase.report.vo.RptDataFieldVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 报表数据库连接处理类
 */
@Repository
public class ReportHandler {
    private static Logger logger = LoggerFactory.getLogger(ReportHandler.class);

    @Value("${excelDetailCount}")
    private Integer excelDetailCount; //一个文件多少条数据

    @Value("${excelLimitCount}")
    private Integer excelLimitCount; //多少数据查询一次

    @Autowired
    private RptAnalyseLogService rptAnalyseLogService;

    @Autowired
    private RptDataFieldMapper rptDataFieldMapper;


    public List<RptDataTable> queryAllTables(String dataSourceName) {

        DataSourceConfig dataSourceConfig = DataSourceManager.get().getDataSourceConfig(dataSourceName);
        DataBaseType dataBaseType = dataSourceConfig.getDataBaseType();

        Connection conn = null;

        try {
            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, dataBaseType);
            conn = DbConnFactory.factory(dataSourceName);

            return reportAccessor.queryAllTables(conn, dataBaseType);

        } catch (DbException e) {
            logger.error("Occurred DbException.", e);
            // todo throw exception
        } finally {
            DataBaseUtil.closeConnection(conn);
        }

        return null;
    }

    public List<RptDataField> queryFields(String dataSourceName, String tableName) {
        DataSourceConfig dataSourceConfig = DataSourceManager.get().getDataSourceConfig(dataSourceName);
        DataBaseType dataBaseType = dataSourceConfig.getDataBaseType();

        Connection conn = null;

        try {
            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, dataBaseType);
            conn = DbConnFactory.factory(dataSourceName);

            return reportAccessor.queryColumns(conn, tableName);
        } catch (DbException e) {
            logger.error("Occurred DbException.", e);
            // todo throw exception
        } finally {
            DataBaseUtil.closeConnection(conn);
        }

        return null;
    }

    /**
     * 查询sql 并封装树
     * @param reportDatasource
     * @param cubeTree
     * @return
     */
    public CubeTree report(ReportDatasource reportDatasource, CubeTree cubeTree){
        String dataSourceName = reportDatasource.getDatasourceName();

        Connection conn = null;

        try {
            DataSourceConfig dataSourceConfig = DataSourceManager.get().getDataSourceConfig(dataSourceName);
            DataBaseType dataBaseType = dataSourceConfig.getDataBaseType();

            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, dataBaseType);
            conn = DbConnFactory.factory(dataSourceName);

            //生成sql
            String sql = reportAccessor.getReportSql(reportDatasource);

            //查询
            Long time = reportAccessor.query(sql, conn , cubeTree);

            //插入sql
            addRptLog(reportDatasource, sql,time);
        } catch (DbException e) {
            logger.error("Occurred DbException.", e);
            // todo throw exception
        } finally {
            DataBaseUtil.closeConnection(conn);
        }

        return cubeTree;
    }

    /**
     *插入动态sql 日志
     * @param reportDatasource 参数对象
     * @param sql  执行sql
     * @param time sql执行时间
     */
    private void addRptLog(ReportDatasource reportDatasource, String sql,Long time) {
        RptAnalyseLog rptAnalyseLog = new RptAnalyseLog();
        rptAnalyseLog.setAnalyseSql(sql);
        rptAnalyseLog.setDatasourceName(reportDatasource.getDatasourceName());
        rptAnalyseLog.setTableId(reportDatasource.getReportTables().get(0).getTableId());
        rptAnalyseLog.setTableName(reportDatasource.getReportTables().get(0).getTableName());
        rptAnalyseLog.setPersonalSubjectId(reportDatasource.getPersonalSubjectId());
        rptAnalyseLog.setSqlExecutionTime(time);

        //执行sql 查询
        rptAnalyseLogService.addReportLog(rptAnalyseLog);
    }

    /**
     * 查询明细sql
     * @param reportDatasource
     * @return
     */
    public List<File> reportFromDetail(ReportDatasource reportDatasource, RptPersionalDownloadVO rptPersionalDownloadVO) {
        String datasourceName = reportDatasource.getDatasourceName(); //数据源名称

        Connection conn = null;

        List<File> files = new ArrayList<>();

        try {
            DataSourceConfig dataSourceConfig = DataSourceManager.get().getDataSourceConfig(datasourceName);
            DataBaseType dataBaseType = dataSourceConfig.getDataBaseType();

            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, dataBaseType);
            conn = DbConnFactory.factory(datasourceName);

            //生成sql对象
            DataDetailSQL dataDetailSQL = reportAccessor.getReportFromDetailSql(reportDatasource);

//            //先看一下总count
            Integer count = reportAccessor.queryCount(dataDetailSQL.getSqlCount(),conn);
//            //查询详细
            files = reportAccessor.queryFromDetail(count,dataDetailSQL.getSql(),conn);
            rptPersionalDownloadVO.setDownloadSql(dataDetailSQL.getSql());
        } catch (DbException e) {
            logger.error("Occurred DbException.", e);
            // todo throw exception
        } finally {
            DataBaseUtil.closeConnection(conn);
        }

        return files;
    }

    /**
     * @param:
     * @return:
     * @description:  创建数据库连接
     * @author: lirunze
     * @Date: 2018/11/2
     */
    public Boolean createConn(RptDatasource datasource) {
        Boolean flag = false;
        try {
            // 创建数据库连接池
            DataSourceConfig sourceConfig = new DataSourceConfig();
            sourceConfig.setConnPoolType(ConnPoolType.getConnPoolType(datasource.getConnpoolType()));
            sourceConfig.setDataBaseType(DataBaseType.getDbType(datasource.getDatabaseType()));
            sourceConfig.setDataSourceName(datasource.getDatasourceName());
            sourceConfig.setDataSourceURL(datasource.getDatasourceUrl());
            sourceConfig.setDataSourceUserName(datasource.getUserName());
            sourceConfig.setDataSourcePassword(datasource.getPassword());
            sourceConfig.setDataSourceInitialSize(datasource.getInitialSize());
            sourceConfig.setDataSourceMaxActive(datasource.getMaxActive());
            sourceConfig.setDataSourceMaxWait(datasource.getMaxWait());
            sourceConfig.setDataSourceMaxIdle(datasource.getMaxIdle());

            // 初始化数据库连接
            DataSourceManager.get().append(sourceConfig);
            flag = true;
        } catch (DbException e) {
            e.printStackTrace();
            logger.error("创建数据库连接失败 = {}", e.getMessage());
        }

        return flag;
    }

    /**
     * @param:
     * @return:
     * @description:  查询元数据数量
     * @author: lirunze
     * @Date: 2018/11/5
     */
    public Integer queryDistinctCount(String dataSourceName, String sql) {

        DataSourceConfig dataSourceConfig = DataSourceManager.get().getDataSourceConfig(dataSourceName);
        DataBaseType dataBaseType = dataSourceConfig.getDataBaseType();

        Connection conn = null;

        try {
            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, dataBaseType);
            conn = DbConnFactory.factory(dataSourceName);

            return reportAccessor.queryDistinctCount(sql, conn);

        } catch (DbException e) {
            logger.error("Occurred DbException.", e);
            // todo throw exception
        } finally {
            DataBaseUtil.closeConnection(conn);
        }

        return null;
    }

    /**
     * @param:
     * @return:
     * @description:  抽取元数据
     * @author: lirunze
     * @Date: 2018/11/7
     */
    public List<RptDataFieldVO> queryDistinctCountBatch(String dataSourceName, List<RptDataFieldVO> list) {
        DataSourceConfig dataSourceConfig = DataSourceManager.get().getDataSourceConfig(dataSourceName);
        DataBaseType dataBaseType = dataSourceConfig.getDataBaseType();

        Connection conn = null;

        try {
            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, dataBaseType);
            conn = DbConnFactory.factory(dataSourceName);

            for (RptDataFieldVO vo : list) {
                StringBuffer sql = new StringBuffer();
                sql.append("select COUNT(distinct(");
                sql.append(vo.getFieldCode());
                sql.append(")) from ");
                sql.append(vo.getTableCode());
                vo.setMetadataCount(reportAccessor.queryDistinctCount(sql.toString(), conn));
            }


            return list;

        } catch (DbException e) {
            logger.error("Occurred DbException.", e);
            // todo throw exception
        } finally {
            DataBaseUtil.closeConnection(conn);
        }

        return null;
    }

    /**
     * @param:
     * @return:
     * @description:  抽取元数据
     * @author: lirunze
     * @Date: 2018/11/7
     */
    public List<RptDataDict> queryDistinct(String dataSourceName, List<RptDataFieldVO> list) {
        DataSourceConfig dataSourceConfig = DataSourceManager.get().getDataSourceConfig(dataSourceName);
        DataBaseType dataBaseType = dataSourceConfig.getDataBaseType();

        Connection conn = null;

        try {
            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, dataBaseType);
            conn = DbConnFactory.factory(dataSourceName);
            List<RptDataDict> dataDicts = new ArrayList<>();
            List<RptDataDict> rptDataDicts = new ArrayList<>();
            for (RptDataFieldVO vo : list) {
                StringBuffer sql = new StringBuffer();
                sql.append("select distinct(");
                sql.append(vo.getFieldCode());
                sql.append(") field from ");
                sql.append(vo.getTableCode());
                rptDataDicts = reportAccessor.queryDistinctFeild(sql.toString(), conn);
                for (RptDataDict dataDict : rptDataDicts) {
                    dataDict.setTableId(vo.getTableId());
                    dataDict.setFieldId(vo.getFieldId());
                }
                dataDicts.addAll(rptDataDicts);
            }


            return dataDicts;

        } catch (DbException e) {
            logger.error("Occurred DbException.", e);
            // todo throw exception
        } finally {
            DataBaseUtil.closeConnection(conn);
        }

        return null;
    }

    /**
     * 动态
     * @param reportDatasource
     * @return
     */
    public PageDTO<ReportRespDetail> reportCoreDetail(ReportDatasource reportDatasource, CubeTree cubeTree) {
        PageDTO<ReportRespDetail> pageDTO = new PageDTO<ReportRespDetail>(reportDatasource.getPageNum(),reportDatasource.getPageSize());

        String dataSourceName = reportDatasource.getDatasourceName();

        Connection conn = null;

        try {
            DataSourceConfig dataSourceConfig = DataSourceManager.get().getDataSourceConfig(dataSourceName);
            DataBaseType dataBaseType = dataSourceConfig.getDataBaseType();

            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, dataBaseType);
            conn = DbConnFactory.factory(dataSourceName);

            //生成sql
            Map<String,Object> tmpMap = reportAccessor.reportCoreDetail(reportDatasource);

            ReportDetail reportDetail = getFieldsByMap(tmpMap);

            //算total
            String sql = reportDetail.getSql();

            StringBuilder s = new StringBuilder(sql);
            String selectCount = s.substring(s.lastIndexOf("from"), s.length() );
            selectCount = "select count(1) " + selectCount;
            //先看一下总count
            //select DATASOURCE_ID, DATASOURCE_NAME, DATABASE_TYPE, CONNPOOL_TYPE, DATASOURCE_URL, USER_NAME, PASSWORD, INITIAL_SIZE, MAX_ACTIVE, MAX_WAIT, MAX_IDLE, REMOVE_STATUS, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT, DATASOURCE_CHINESE_NAME from `rpt_datasource` where REMOVE_STATUS = 0 order by DATASOURCE_ID desc


            Integer count = reportAccessor.queryCount(selectCount,conn);
            pageDTO.setTotal(count);

            //分页sql
            String detailSql = PageReportDetail.getDetailSql(sql, reportDatasource.getDatabaseType(), pageDTO, count);

            ReportRespDetail reportRespDetail = reportAccessor.reportPageList(detailSql, conn, cubeTree, reportDetail.getFieldList());

            List<ReportRespDetail> list = new ArrayList<>();
            list.add(reportRespDetail);
            pageDTO.setResultData(list);

        } catch (DbException e) {
            logger.error("Occurred DbException.", e);
            // todo throw exception
        } finally {
            DataBaseUtil.closeConnection(conn);
        }

        return pageDTO;

    }

    private ReportDetail getFieldsByMap(Map<String, Object> tmpMap) {

        ReportDetail reportDetail = new ReportDetail();

        String sql = tmpMap.get(AbstractAccessor.KEY_SQL).toString();

        List<Long> lds = (ArrayList)tmpMap.get(AbstractAccessor.KEY_FIELD_IDS);

        if(!CollectionUtils.isEmpty(lds)){
            List<RptDataField> rptDataFields = rptDataFieldMapper.selectByPrimaryKeys(lds);
            reportDetail.setFieldList(rptDataFields);
        }


        reportDetail.setSql(sql);
        return reportDetail;
    }

    /**
     * 数据表核心导出，当拖入行当时候导出
     * @param reportDatasource
     * @param cubeTree
     * @return
     */
    public List<ReportRespDetail> reportCoreDetailExcel(ReportDatasource reportDatasource, CubeTree cubeTree,RptPersionalDownloadVO rptPersionalDownloadVO) {
        String dataSourceName = reportDatasource.getDatasourceName();

        Connection conn = null;
        List<ReportRespDetail> list = new ArrayList<>();
        try {
            DataSourceConfig dataSourceConfig = DataSourceManager.get().getDataSourceConfig(dataSourceName);
            DataBaseType dataBaseType = dataSourceConfig.getDataBaseType();

            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, dataBaseType);
            conn = DbConnFactory.factory(dataSourceName);

            //生成sql
            Map<String,Object> tmpMap = reportAccessor.reportCoreDetail(reportDatasource);

            ReportDetail reportDetail = getFieldsByMap(tmpMap);

            //算total
            String sql = reportDetail.getSql();
            //设置sql
            rptPersionalDownloadVO.setDownloadSql(sql);

            StringBuilder s = new StringBuilder(sql);
            String selectCount = s.substring(s.lastIndexOf("from"), s.length() );
            selectCount = "select count(1) " + selectCount;


            //先看一下总count
            Integer count = reportAccessor.queryCount(selectCount,conn);
            List<PageDTO> pages = PageReportDetail.getPages(excelLimitCount, count);

            //几个未一级文件要合并
            Integer page = PageReportDetail.getPage(excelDetailCount, count);

            List<ReportRespDetail> reportRespDetails = new ArrayList<>(pages.size());
            List<List<String>> dataList = new ArrayList<>();
            for(PageDTO pageDTO:pages){
                //分页sql
                String detailSql = PageReportDetail.getDetailSql(sql, reportDatasource.getDatabaseType(), pageDTO, count);

                ReportRespDetail reportRespDetail = reportAccessor.reportPageList(detailSql, conn, cubeTree, reportDetail.getFieldList());

                reportRespDetails.add(reportRespDetail);
                dataList.addAll(reportRespDetail.getDataList());
            }

            List<List<List<String>>> lists = null;
            List<String> headers = null;
            //转换文件
            if(reportRespDetails.size() < page ){
                if(!CollectionUtils.isEmpty(reportRespDetails)){
                    headers = reportRespDetails.get(0).getHeaders(); //header 都是一样的
                    lists = ReportExportUtil.averageAssign(dataList, page);

                }
            }else{
                if(!CollectionUtils.isEmpty(reportRespDetails)){
                    headers = reportRespDetails.get(0).getHeaders(); //header 都是一样的
                    lists = ReportExportUtil.averageDivision(dataList, excelDetailCount);

                }
            }
            for(List<List<String>> listList:lists){
                ReportRespDetail respDetail = new ReportRespDetail();
                respDetail.setHeaders(headers);
                respDetail.setDataList(listList);
                list.add(respDetail);
            }

        } catch (DbException e) {
            logger.error("Occurred DbException.", e);
            // todo throw exception
        } finally {
            DataBaseUtil.closeConnection(conn);
        }

        return list;

    }


}
