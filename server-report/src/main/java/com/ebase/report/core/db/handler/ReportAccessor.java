package com.ebase.report.core.db.handler;

import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.DataDetailSQL;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.cube.CubeTree;
import com.ebase.report.model.ReportDetail;
import com.ebase.report.model.ReportRespDetail;
import com.ebase.report.model.RptDataDict;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.dynamic.ReportDatasource;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 报表查询类
 */
interface ReportAccessor {

    public String getReportSql(ReportDatasource reportDatasource);

    public Long query(String sql, Connection conn, CubeTree cubeTree) throws DbException;

    public CubeTree rsToCubeTree(ResultSet rs, CubeTree cubeTree) throws DbException;

    public List<RptDataTable> queryAllTables(Connection conn, DataBaseType dataBaseType) throws DbException;

    public List<RptDataField> queryColumns(Connection conn, String tableName) throws DbException;

    public String typesConvert(String type);

    public DataDetailSQL getReportFromDetailSql(ReportDatasource reportDatasource);

    public Integer queryCount(String sqlCount, Connection conn);

    public Map<String, List<Object>> queryDateil(String sql, Connection conn, Map<String, List<Object>> tmpMap);

    public List<File> queryFromDetail(Integer count, String sql, Connection conn);

    public String toWhereSqlFtiler(ReportDatasource reportDatasource);

    public String toSelectMeasures(ReportDatasource reportDatasource);

    public String toSelectDetailMeasures(ReportDatasource reportDatasource);

    // distinct COUNT
    public Integer queryDistinctCount(String distinctSqlCount, Connection conn);

    // distinct value
    public List<RptDataDict> queryDistinctFeild(String distinctSqlCount, Connection conn) throws DbException;

    public Map<String,Object> reportCoreDetail(ReportDatasource reportDatasource);

    /***
     * 只拖到行区，查询明细列表
     * @param sql
     * @param conn
     * @param cubeTree
     * @param fieldList
     * @return
     * @throws DbException
     */
    public ReportRespDetail reportPageList(String sql, Connection conn, CubeTree cubeTree, List<RptDataField> fieldList) throws DbException;
}
