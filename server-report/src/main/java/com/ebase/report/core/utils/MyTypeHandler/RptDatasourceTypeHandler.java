package com.ebase.report.core.utils.MyTypeHandler;

import com.ebase.report.common.RemoveStatusEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RptDatasourceTypeHandler implements TypeHandler<RemoveStatusEnum> {

    //----------------------------------用于DML操作----------------------------------
    public void setParameter(PreparedStatement preparedStatement, int i, RemoveStatusEnum rptDsEnum, JdbcType jdbcType) throws SQLException {
        //设置第i个参数的值为传入RemoveStatusEnum的removeStateCode值，preparedStatement为执行数据库操纵的对象
        //传值的时候是一个RemoveStatusEnum对象，但是当进行映射插入的时候就会转化为RemoveStatusEnum的removeStateCode值进行存储
        preparedStatement.setInt(i, rptDsEnum.getRemoveStatus());
    }

    //----------------------------------用于DQL操作----------------------------------
    //s:数据库中的表的列名
    public RemoveStatusEnum getResult(ResultSet resultSet, String s) throws SQLException {
        //获取数据库存储的RemoveStatusEnum的removeStateCode值
        int result = resultSet.getInt(s);
        return RemoveStatusEnum.getRemoveStatusEnumFromCode(result);
    }

    public RemoveStatusEnum getResult(ResultSet resultSet, int i) throws SQLException {
        int result = resultSet.getInt(i);
        return RemoveStatusEnum.getRemoveStatusEnumFromCode(result);
    }

    public RemoveStatusEnum getResult(CallableStatement callableStatement, int i) throws SQLException {
        int result = callableStatement.getInt(i);
        return RemoveStatusEnum.getRemoveStatusEnumFromCode(result);
    }

}
