package com.ebase.report.common;


import java.util.HashMap;
import java.util.Map;

/**
 * 字段类型枚举
 */
public enum DBFieldTypeEnum {

    /**
     * mysql
     */
    //数值类型
    TINYINT("Long"), //小整数值
    SMALLINT("Long"), //大整数值
    MEDIUMINT("Long"), //大整数值
    INT("Long"),      //大整数值
    INTEGER("Long"),  //大整数值
    BIGINT("BigDecimal"),   //极大整数值
    FLOAT("BigDecimal"),     //单精度浮点数值
    DOUBLE("BigDecimal"),   //双精度浮点数值

    //字符串
    CHAR("String"), //定长字符串
    VARCHAR("String"), //变长字符串
    TINYBLOB("String"), //不超过 255 个字符的二进制字符串
    TINYTEXT("String"), //短文本字符串
    BLOB("String"), //二进制形式的长文本数据
    TEXT("String"), //	长文本数据
    MEDIUMBLOB("String"), //二进制形式的中等长度文本数据
    MEDIUMTEXT("String"), //	中等长度文本数据
    LONGBLOB("String"), //	二进制形式的极大文本数据
    LONGTEXT("String"), //	极大文本数据

    //日期时间类型
    DATE("Date"), //1000-01-01/9999-12-31	YYYY-MM-DD	日期值
    TIME("Date"), //'-838:59:59'/'838:59:59'	HH:MM:SS	时间值或持续时间
    YEAR("Date"), //1901/2155	YYYY	年份值
    DATETIME("Date"), //1000-01-01 00:00:00/9999-12-31 23:59:59	YYYY-MM-DD HH:MM:SS	混合日期和时间值
    TIMESTAMP("Date"), // 1970-01-01 00:00:00/2038 结束时间是第 2147483647 秒，北京时间 2038-1-19 11:14:07，格林尼治时间 2038年1月19日 凌晨 03:14:07
    YYYYMMDD("Date"), //混合日期和时间值，时间戳


    /**
     * oracle
     */
    VARCHAR2("String"),  //可变长的字符串，具体定义时指明最大长度n，
    NUMBER("String"),    //可变长的数值列，允许0、正值及负值，m是所有有效数字的位数，n是小数点以后的位数。
    LONG("String"),       //可变长字符列，最大长度限制是2GB，用于不需要作字符串搜索的长串数据，如果要进行字符搜索就要用varchar2类型。
    RAW("Byte"),      // 固定长度的二进制数据    最大长度2000    bytes      可存放多媒体图象声音等
    LONGRAW("Byte"),  //可变长二进制数据，最大长度是2GB。Oracle 8i用这种格式来保存较大的图形文件或带格式的文本文件，如Miceosoft Word文档，以及音频、视频等非文本文件。
    CLOB("Byte"),       //字符数据    最大长度4G
    NCLOB("Byte"),      //根据字符集而定的字符数据    最大长度4G
    BFILE("Byte"),      //存放在数据库外的二进制数据    最大长度4G
    NCHAR("Byte"),
    NVARCHAR2("String"), //根据字符集而定的可变长度字符串    最大长度4000    bytes
    NUMBERIC("String"),
    ROWID("Long"),     //数据表中记录的唯一行号    10    bytes    ********.****.****格式，*为0或1
    NROWID("Long"),    //二进制数据表中记录的唯一行号    最大长度4000    bytes
    DECIMAL("BigDecimal"),   //  数字类型    P为整数位，S为小数位
    REAL("BigDecimal"),     //  实数类型    NUMBER(63)，精度更高

    /**
     * sqlServer
     */
    BIT("BigDecimal"),        //整型，取值范围[0,1,null]，用于存取布尔值
    NUMERIC("BigDecimal"),        //精确数值型 ，示例：decimal(8,4); //共8位，小数点右4位
    SMALLMONEY("BigDecimal"),         //货币型
    MONEY("BigDecimal"),          //货币型
    SMALLDATETIME("Date"),  //日期时间型，表示从1900年1月1日到2079年6月6日间的日期和时间，精确到一分钟
    CURSOR("String"),         //特殊数据型，包含一个对游标的引用。用在存储过程中，创建表时不能用
    UNIQUEIDENTIFIER("String"),   //特殊数据型，存储一个全局唯一标识符，即GUID
    NVARCHAR("Byte"),           //统一编码字符型，用作变长的统一编码字符型数据
    NTEXT("Byte"),      //统一编码字符型，用来存储大量的统一编码字符型数据
    BINARY("Byte"),         //二进制数据类型，存储可达8000 字节长的定长的二进制数据
    VARBINARY("Byte"),     //二进制数据类型，用来存储可达8000 字节长的变长的二进制数据
    IMAGE("Byte"),        //二进制数据类型，用来存储变长的二进制数据

    /**
     * db2
     */
    LONGVARCHAR("String"),    //用于保存变长的字符串数据。
    GRAPHICS("String"),       //双字节字符串。
    VARGRAPHICS("String"),        //可变长，双字节字符串。
    LONGVARGRAPHIC("String");     //双字节字符串




    private static Map<String,DBFieldTypeEnum> tmp = new HashMap<>(DBFieldTypeEnum.values().length);

    static {
        for (DBFieldTypeEnum value : DBFieldTypeEnum.values()) {
            tmp.put(value.getName(),value);
        }

    }


    public static DBFieldTypeEnum getByName(String name){
        return tmp.get(name);
    }



//    private int code;
    private String name;
//
    DBFieldTypeEnum( String name){
        this.name = name;
    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 是否是数字类型
     * @param dbFieldTypeEnum
     * @return
     */
    public static Boolean isNumber(DBFieldTypeEnum dbFieldTypeEnum){
        boolean boo = false;
        switch(dbFieldTypeEnum){
            case TINYINT:
                boo = true;
                break;
            case SMALLINT:
                boo = true;
                break;
            case MEDIUMINT: //大整数值
                boo = true;
                break;
            case INT:
                boo = true;
                break;
            case INTEGER:
                boo = true;
                break;
            case BIGINT:
                boo = true;
                break;
            case FLOAT:
                boo = true;
                break;
            case DOUBLE:
                boo = true;
                break;
            case ROWID:
                boo = true;
                break;
            case NROWID:
                boo = true;
                break;
            case DECIMAL:
                boo = true;
                break;
            case REAL:
                boo = true;
                break;
            case BIT:
                boo = true;
                break;
            case NUMERIC:
                boo = true;
                break;
            case SMALLMONEY:
                boo = true;
                break;
            case MONEY:
                boo = true;
                break;
            case CURSOR:
                boo = true;
                break;
        }

        return boo;
    }

    /**
     * 是否是时间类型
     * @param dbFieldTypeEnum
     * @return
     */
    public static boolean isDate(DBFieldTypeEnum dbFieldTypeEnum){
        boolean boo = false;
        switch(dbFieldTypeEnum){
            case DATE:
                boo = true;
                break;
            case TIME:
                boo = true;
                break;
            case YEAR:
                boo = true;
                break;
            case DATETIME:
                boo = true;
                break;
            case TIMESTAMP:
                boo = true;
                break;
            case YYYYMMDD:
                boo = true;
                break;
            case TINYINT:
                boo = true;
                break;
        }
        return boo;
    }

}
