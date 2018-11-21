package com.ebase.report.common;


import java.util.HashMap;
import java.util.Map;

/**
 * 字段类型枚举
 * byte 类型不用做操作  字符型、日期型的是维度，数值型为指标。
 */
public enum DBFieldTypeEnum {

    /**
     * mysql
     */
    //数值类型
    TINYINT("tinyint","Long","measures"), //小整数值
    SMALLINT("smallint","Long","measures"), //大整数值
    MEDIUMINT("mediumint","Long","measures"), //大整数值
    INT("int","Long","measures"),      //大整数值
    INTEGER("integer","Long","measures"),  //大整数值
    BIGINT("bigint","Long","measures"),   //极大整数值
    FLOAT("float","BigDecimal","measures"),     //单精度浮点数值
    DOUBLE("double","BigDecimal","measures"),   //双精度浮点数值

    //字符串
    CHAR("char","String","dimension"), //定长字符串
    VARCHAR("varchar","String","dimension"), //变长字符串
    TINYBLOB("tinyblob","String","dimension"), //不超过 255 个字符的二进制字符串
    TINYTEXT("tinytext","String","dimension"), //短文本字符串
    BLOB("blob","String","dimension"), //二进制形式的长文本数据
    TEXT("text","String","dimension"), //	长文本数据
    MEDIUMBLOB("mediumblob","String","dimension"), //二进制形式的中等长度文本数据
    MEDIUMTEXT("mediumtext","String","dimension"), //	中等长度文本数据
    LONGBLOB("longblob","String","dimension"), //	二进制形式的极大文本数据
    LONGTEXT("longtext","String","dimension"), //	极大文本数据

    //日期时间类型
    DATE("date","Date","dimension"), //1000-01-01/9999-12-31	YYYY-MM-DD	日期值
    TIME("time","Date","dimension"), //'-838:59:59'/'838:59:59'	HH:MM:SS	时间值或持续时间
    YEAR("year","Date","dimension"), //1901/2155	YYYY	年份值
    DATETIME("datetime","Date","dimension"), //1000-01-01 00:00:00/9999-12-31 23:59:59	YYYY-MM-DD HH:MM:SS	混合日期和时间值
    TIMESTAMP("timestamp","Date","dimension"), // 1970-01-01 00:00:00/2038 结束时间是第 2147483647 秒，北京时间 2038-1-19 11:14:07，格林尼治时间 2038年1月19日 凌晨 03:14:07
    YYYYMMDD("yyyymmdd","Date","dimension"), //混合日期和时间值，时间戳


    /**
     * oracle
     */
    VARCHAR2("varchar2","String","dimension"),  //可变长的字符串，具体定义时指明最大长度n，
    NUMBER("number","String","dimension"),    //可变长的数值列，允许0、正值及负值，m是所有有效数字的位数，n是小数点以后的位数。
    LONG("long","String","dimension"),       //可变长字符列，最大长度限制是2GB，用于不需要作字符串搜索的长串数据，如果要进行字符搜索就要用varchar2类型。
    RAW("raw","Byte","none"),      // 固定长度的二进制数据    最大长度2000    bytes      可存放多媒体图象声音等
    LONGRAW("longraw","Byte","none"),  //可变长二进制数据，最大长度是2GB。Oracle 8i用这种格式来保存较大的图形文件或带格式的文本文件，如Miceosoft Word文档，以及音频、视频等非文本文件。
    CLOB("clob","Byte","none"),       //字符数据    最大长度4G
    NCLOB("nclob","Byte","none"),      //根据字符集而定的字符数据    最大长度4G
    BFILE("bfile","Byte","none"),      //存放在数据库外的二进制数据    最大长度4G
    NCHAR("nchar","Byte","none"),
    NVARCHAR2("nvarchar2","String","dimension"), //根据字符集而定的可变长度字符串    最大长度4000    bytes
    NUMBERIC("numberic","String","dimension"),
    ROWID("rowid","Long","measures"),     //数据表中记录的唯一行号    10    bytes    ********.****.****格式，*为0或1
    NROWID("nrowid","Long","measures"),    //二进制数据表中记录的唯一行号    最大长度4000    bytes
    DECIMAL("decimal","BigDecimal","measures"),   //  数字类型    P为整数位，S为小数位
    REAL("real","BigDecimal","measures"),     //  实数类型    NUMBER(63)，精度更高

    /**
     * sqlServer
     */
    BIT("bit","BigDecimal","measures"),        //整型，取值范围[0,1,null]，用于存取布尔值
    NUMERIC("numeric","BigDecimal","measures"),        //精确数值型 ，示例：decimal(8,4); //共8位，小数点右4位
    SMALLMONEY("smallmoney","BigDecimal","measures"),         //货币型
    MONEY("money","BigDecimal","measures"),          //货币型
    SMALLDATETIME("smalldatetime","Date","dimension"),  //日期时间型，表示从1900年1月1日到2079年6月6日间的日期和时间，精确到一分钟
    CURSOR("cursor","String","dimension"),         //特殊数据型，包含一个对游标的引用。用在存储过程中，创建表时不能用
    UNIQUEIDENTIFIER("uniqueidentifier","String","dimension"),   //特殊数据型，存储一个全局唯一标识符，即GUID
    NVARCHAR("nvarchar","Byte","none"),           //统一编码字符型，用作变长的统一编码字符型数据
    NTEXT("ntext","Byte","none"),      //统一编码字符型，用来存储大量的统一编码字符型数据
    BINARY("binary","Byte","none"),         //二进制数据类型，存储可达8000 字节长的定长的二进制数据
    VARBINARY("varbinary","Byte","none"),     //二进制数据类型，用来存储可达8000 字节长的变长的二进制数据
    IMAGE("image","Byte","none"),        //二进制数据类型，用来存储变长的二进制数据

    /**
     * db2
     */
    LONGVARCHAR("longvarchar","String","dimension"),    //用于保存变长的字符串数据。
    GRAPHICS("graphics","String","dimension"),       //双字节字符串。
    VARGRAPHICS("vargraphics","String","dimension"),        //可变长，双字节字符串。
    LONGVARGRAPHIC("longvargraphic","String","dimension"),     //双字节字符串

    BOOLEAN("boolean","String","dimension"),
    ENUM("enum","String","dimension"),
    SET("set","String","dimension"),
    BOOL("bool","Byte","none");

    private static Map<String,DBFieldTypeEnum> tmpName = new HashMap<>(DBFieldTypeEnum.values().length);

    private static Map<String,DBFieldTypeEnum> tmpCode = new HashMap<>(DBFieldTypeEnum.values().length);

    static {
        for (DBFieldTypeEnum value : DBFieldTypeEnum.values()) {
            tmpName.put(value.getName(),value);
            tmpCode.put(value.getCode(),value);
        }

    }

    public static DBFieldTypeEnum getByName(String name){
        return tmpName.get(name);
    }


    public static DBFieldTypeEnum getByCode(String code){
        return tmpCode.get(code);
    }



    private String code;

    private String name;

    private String demandType; //可以做的指标类型
//
    DBFieldTypeEnum(String code,String name,String demandType){
        this.code = code;
        this.name = name;
        this.demandType = demandType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(String demandType) {
        this.demandType = demandType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
