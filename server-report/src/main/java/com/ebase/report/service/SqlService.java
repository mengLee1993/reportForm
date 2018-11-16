package com.ebase.report.service;

import com.ebase.report.common.DemandType;
import com.ebase.report.common.ExcelAttribute;
import com.ebase.report.cube.CubeTree;
import com.ebase.report.cube.CubeData;
import com.ebase.report.cube.DimensionData;
import com.ebase.report.cube.DimensionKey;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wangyu
 */
public class SqlService {


    private static String driver = "com.mysql.jdbc.Driver";//
    //private static String url = "jdbc:mysql://10.5.210.201:3306/reports?useUnicode=true&amp;byteacterEncoding=utf-8&amp;useOldAliasMetadataBehavior=true";//
    private static String url = "jdbc:mysql://localhost:3306/reports?useUnicode=true&amp;byteacterEncoding=utf-8&amp;useOldAliasMetadataBehavior=true";//
    private static String username = "root";//
    //private static String password = "root";//
    private static String password = "cjkaka";//

    /**
     * 获得连接对象
     *
     * @return 连接对象
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException,
            SQLException {
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }

    /**
     * @throws SQLException
     */
    public static void close(ResultSet rs, PreparedStatement pstmt, Connection con) {

        try {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    /**
     * 执行查询
     *
     * @param sql
     * @param params
     * @return
     */
    public static <T> List<T> execQueryClass(String sql, Object[] params, Class<T> clazz) {

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);

            if (params != null) {
                // 设置参数列表
                for (int i = 0; i < params.length; i++) {
                    // 因为问号参数的索引是从1开始，所以是i+1，将所有值都转为字符串形式
                    pstmt.setObject(i + 1, params[i] + "");
                }
            }

            // 执行查询
            rs = pstmt.executeQuery();

            // 获得结果集元数据
            ResultSetMetaData rsmd = rs.getMetaData();

            // 获得列的总数
            int columnCount = rsmd.getColumnCount();

            Map<String, String> tmp = new HashMap<>();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                ExcelAttribute annotation = field.getAnnotation(ExcelAttribute.class);
                String t_name = annotation.name(); //表
                String b_name = field.getName(); //字段
                tmp.put(t_name, b_name);

            }
            List<T> arr = new ArrayList<>();
            // 遍历结果集
            while (rs.next()) {
                T t = clazz.newInstance();
                Class<?> aClass = t.getClass();

                for (int i = 0; i < columnCount; i++) {
                    // 根据列索引取得每一列的列名,索引从1开始
                    String columnName = rsmd.getColumnName(i + 1);
                    // 根据列名获得列值
                    Object columnValue = rs.getObject(columnName);

                    String b_name = tmp.get(columnName);
                    Field f = aClass.getDeclaredField(b_name);
                    f.setAccessible(true);

                    Object obj = setFieldType(f, columnValue);

                    f.set(t, obj);

                }
                arr.add(t);
            }
            return arr;

        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            close(rs, pstmt, connection);
        }
        return null;
    }


    /**
     * 执行查询
     *
     * @param sql    传入的预设的sql语句
     * @param params 问号参数列表
     * @return 查询后的结果
     */
    public static CubeData execQuery(String sql, Object[] params, CubeData cubeData) {

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {


            connection = getConnection();
            pstmt = connection.prepareStatement(sql);

            if (params != null) {
                // 设置参数列表
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i] + "");
                }
            }

            rs = pstmt.executeQuery();

            // 获得结果集元数据（元数据就是描述数据的数据，比如把表的列类型列名等作为数据）
            ResultSetMetaData rsmd = rs.getMetaData();

            // 获得列的总数
            int columnCount = rsmd.getColumnCount();

            // 遍历结果集
            while (rs.next()) {
                DimensionKey dimensionKey = new DimensionKey();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    Object columnValue = rs.getObject(columnName);

                    DimensionData dimensionData = cubeData.getDimensionDataMap().get(columnName);

                    String value = String.valueOf(columnValue);
                    if (DemandType.DIMENSION.getCode().equals(dimensionData.getDemandType().getCode())) {

                        dimensionData.getValues().put(value, value);

                        dimensionKey.addDimension(columnName, value);
                    } else {
                        // 初始化当前结果成对应的cell里中
                        cubeData.getCells().put(dimensionKey.toString() + columnName, String.valueOf(columnValue));
                    }
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rs, pstmt, connection);
        }

        return cubeData;
    }

    public static CubeTree execQuery2CubeTree(String sql, Object[] params, CubeTree cubeTree) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);

            if (params != null) {
                // 设置参数列表
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i] + "");
                }
            }

            rs = pstmt.executeQuery();


        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rs, pstmt, connection);
        }

        return cubeTree;
    }

//    public static CubeTree execAccessor(String sql, Object[] params, CubeTree cubeTree){
//        // test accessor
//        Connection conn = null;
//        try {
//            // 数据库名称
//            conn = DbConnFactory.factory("report");
//            ReportAccessor reportAccessor = AccessorFactory.get().factoryAccessor(ReportAccessor.class, DataBaseType.DB_TYPE_MYSQL);
//            reportAccessor.query(sql, conn, cubeTree);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//
//        return cubeTree;
//    }

    private static Object setFieldType(Field f, Object columnValue) {
        Class<?> type = f.getType();

        if (String.class.isAssignableFrom(type)) {
            return columnValue.toString();
        }

        return null;
    }


}
