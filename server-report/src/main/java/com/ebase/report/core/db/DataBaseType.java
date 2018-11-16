package com.ebase.report.core.db;

import com.ebase.report.core.db.exception.DbException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class DataBaseType {
    private static Map<String, DataBaseType> typeMap = new HashMap<String, DataBaseType>();

    public static String TYPE_NAME_ORACLE = "oracle";
    public static String TYPE_NAME_MYSQL = "mysql";
    public static String TYPE_NAME_HIVE = "hive";

    public static DataBaseType DB_TYPE_ORACLE = new DataBaseType(TYPE_NAME_ORACLE, "Oracle", "oracle.jdbc.driver.OracleDriver", "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual");
    public static DataBaseType DB_TYPE_MYSQL = new DataBaseType(TYPE_NAME_MYSQL, "MySql", "com.mysql.jdbc.Driver", "select now()");
    public static DataBaseType DB_TYPE_HIVE = new DataBaseType(TYPE_NAME_HIVE, "Hive", "org.apache.hive.jdbc.HiveDriver", "select now()");
    private String name;
    private String suffix;
    private String driverClassName;
    private String validateSql;

    private DataBaseType(String name, String suffix, String driverClassName, String validateSql) {
        this.name = name.toLowerCase();
        this.suffix = suffix;
        this.driverClassName = driverClassName;
        this.validateSql = validateSql;

        typeMap.put(this.name, this);
    }

    public static DataBaseType getDbType(String name) throws DbException {
        DataBaseType dataBaseType = null;

        dataBaseType = typeMap.get(name.toLowerCase());

        if (dataBaseType == null) {
//            throw new DbException("")
            // todo
        }

        return dataBaseType;
    }

    public String getName() {
        return name;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public String getValidateSql() {
        return this.validateSql;
    }

    public String toString() {
        return "DataBaseType: name=" + name + ", suffix=" + suffix;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof DataBaseType) {
            return name.equalsIgnoreCase(((DataBaseType) obj).getName());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return name.hashCode();
    }
}