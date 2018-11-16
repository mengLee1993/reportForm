package com.ebase.report.core;

import java.math.BigDecimal;

/**
 * 计算类
 * @author Kim
 *
 */
public class MathHelper {
	/** 默认除法运算精度 **/
	private static final int DEF_DIV_SCALE = 2;

	/**
	 * String类型的字符串加法
	 * @param numStr
	 * @return
	 */
	public static String add(String numStr) {
		StringBuilder result = new StringBuilder(numStr.substring(0, numStr.length() - 3));
//		String begin = ;
		String last = numStr.substring(numStr.length() - 3);
		char[] charArray = last.toCharArray();
		char hundredPlace = charArray[0];
		char tenPlace = charArray[1];
		char theUnit = charArray[2];
		if (theUnit == '9') {
			theUnit = '0';
			if (tenPlace == '9') {
				tenPlace = '0';
				hundredPlace++;
			} else
				tenPlace++;
		} else 
			theUnit++;
		result.append(hundredPlace).append(tenPlace).append(theUnit);
		return result.toString();
	}
	
	/**
	 * 加法运算
	 * 
	 * @param decimal_add
	 *            被加数
	 * @param decimal
	 *            加数
	 * @return 两个参数的和
	 */
	public static BigDecimal add(BigDecimal decimal_add, BigDecimal decimal) {
		return decimal_add.add(decimal);
	}

	/**
	 * 减法运算
	 * 
	 * @param decimal_sub
	 *            被减数
	 * @param decimal
	 *            减数
	 * @return 两个参数的差
	 */
	public static BigDecimal sub(BigDecimal decimal_sub, BigDecimal decimal) {
		return decimal_sub.subtract(decimal);
	}

	/**
	 * 乘法运算
	 * 
	 * @param decimal_mul
	 *            被乘数
	 * @param decimal
	 *            乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal mul(BigDecimal decimal_mul, BigDecimal decimal) {
		//return decimal_mul.multiply(decimal);
		return decimal_mul;
	}

	/**
	 * 除法运算，当发生除不尽的情况时，精确到 小数点以后2位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static BigDecimal div(BigDecimal decimal_div, BigDecimal decimal) {
		return div(decimal_div, decimal, DEF_DIV_SCALE);
	}

	/**
	 * 除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param decimal_div
	 *            被除数
	 * @param decimal
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static BigDecimal div(BigDecimal decimal_div, BigDecimal decimal, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
		}
		//return decimal_div.divide(decimal, scale, BigDecimal.ROUND_HALF_UP);
		return decimal_div.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP);
	}
}