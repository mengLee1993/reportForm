package com.ebase.report.core.db.conn;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 数据库连接池类型
 */
public class ConnPoolType {
    private static Map<String, ConnPoolType> typeMap = new HashMap<String, ConnPoolType>();

    public static String TYPE_NAME_HIKARI = "hikari";
    public static String TYPE_NAME_BONECP = "BoneCP";

    public static ConnPoolType CONN_POOL_TYPE_HIKARI = new ConnPoolType(TYPE_NAME_HIKARI);
    public static ConnPoolType CONN_POOL_TYPE_BONECP = new ConnPoolType(TYPE_NAME_BONECP);

    private String name;

    private ConnPoolType(String n) {
        name = n.toLowerCase();

        typeMap.put(name.toLowerCase(), this);
    }

    public static ConnPoolType getConnPoolType(String name) {
        if (StringUtils.isEmpty(name)) {
            return CONN_POOL_TYPE_HIKARI;
        }

        ConnPoolType connPoolType = typeMap.get(name);

        if (connPoolType == null) {
            connPoolType = CONN_POOL_TYPE_HIKARI;
        }

        return connPoolType;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ConnPoolType)) {
            return false;
        }

        return name.equals(((ConnPoolType) obj).getName());
    }

    public int hashCode() {
        return name.hashCode();
    }
}
