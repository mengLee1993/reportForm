package com.ebase.report.common;

/**
 * 删除状态的枚举类
 * @Auther:     lujiawei
 */
//用于RptDatasourceHandler的状态转换器枚举
public enum RemoveStatusEnum {

        //每一个类型都是一个枚举类<RemoveStatusEnum>的实例
        REMOVE(1, "已移除"),
        NOREMOVE(0, "未移除");

        //用于保存在数据库
        private int removeStatus;
        //用于UI展示
        private String removeStatusName;

    RemoveStatusEnum(int removeStatus, String removeStatusName) {
        this.removeStatus = removeStatus;
        this.removeStatusName = removeStatusName;
        }

        public int getRemoveStatus() {
            return removeStatus;
        }

        //通过removeStatus的值来获取RemoveStatusEnum中不同的枚举类型，数据库只需保存code，通过代码解析成RemoveStatusEnum类型
        public static RemoveStatusEnum getRemoveStatusEnumFromCode(int code) {
            for (RemoveStatusEnum rptDs : RemoveStatusEnum.values()) {
                if (rptDs.getRemoveStatus() == code) {
                    return rptDs;
                }
            }
            return null;
        }

}
