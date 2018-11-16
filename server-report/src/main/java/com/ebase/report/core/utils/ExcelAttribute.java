package com.ebase.report.core.utils;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: ExcelAttribute1
 * Description: excel 导出注解
 * Author: wangyu
 * Date: 2018/4/11 20:45
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target( { java.lang.annotation.ElementType.FIELD }) 
public @interface ExcelAttribute {

	/**
	 * 对应的列名
	 * @return
	 */
	public String name();

//	/**
//	 * 此字段是否需要导出
//	 * @return
//	 */
//	public boolean isExport();

	/**
	 * 字段数据默认 ""
	 * @return
	 */
	public abstract String isDefault() 
	 	default "";

	
	
	
}
