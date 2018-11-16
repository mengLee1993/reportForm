package com.ebase.report.core;


import java.nio.charset.Charset;

/**
 * <p> 常量 </p> 
 * @project 	core
 * @class 		Constants
 */
public class Constants {

    /**
     * i18n 异常 文件
     */
    public final static String I18N_EXCEPTION = "i18n/exception";

    /**
     *  i18n 消息 文件
     */
    public final static String I18N_MESSAGE = "i18n/message";

    /**
     * 默认编码
     */
    public final static String DEFAULT_CHARSET = "UTF-8";
    
    /**
     * 默认编码对象
     */
    public final static Charset CHARSET_UTF_8=Charset.forName(DEFAULT_CHARSET);
}
