package com.ebase.report.core.utils;

import com.ebase.report.core.zk.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * string 字符串
 * </p>
 *
 * @project core
 * @class StringUtil
 */
@Configuration
public class StringUtil extends StringUtils {
	@Value("image.url.pre")
	private String urlPre;




	private static final String INIT_PARAM_DELIMITERS = ",; \t\n";

	/**
	 * 字符串转换为float
	 * @param value
	 * @return
	 */
	public static Float stringToFloat(String value){
		try
		{
			if(StringHelper.isEmpty(value)){
				return null;
			}
			Float f=Float.valueOf(value);
			return f;
		}
		catch(Exception ex){
			return null;
		}
	}

	/**
	 * 是否为小数
	 *
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("\\d+\\.\\d+");
		return pattern.matcher(str).matches();
	}

	/**
	 * 是否是数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 将 config split 成数组 ,; \t\n 支持5中拆分方式，自动trim
	 *
	 * @param config
	 * @return
	 */
	public static String[] splitToArray(String config) {
		return org.springframework.util.StringUtils.tokenizeToStringArray(config, INIT_PARAM_DELIMITERS);
	}

	/**
	 * 去掉字符中所有的换行和空格
	 *
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 整型数字转人民币大写 987654321 -> 九亿八千七百六十五万四千三百二十一
	 *
	 * @param num
	 * @return
	 */
	public static String numToStr(int num) {
		String[] chinese = { "", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千", "万" };
		String[] numChinese = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String str = String.valueOf(num);
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			sb.append(numChinese[Integer.valueOf(String.valueOf(chars[i]))]).append(chinese[chars.length - i - 1]);
		}
		return sb.toString();
	}

	/**
	 * 整型数字转中文人民币大写 987654321 -> 玖亿捌仟柒佰陆拾伍万肆仟叁佰贰拾壹元整
	 *
	 * @param num
	 * @return
	 */
	public static String numToLocalStr(int num) {
		String[] chinese = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万" };
		String[] numChinese = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String str = String.valueOf(num);
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			sb.append(numChinese[Integer.valueOf(String.valueOf(chars[i]))]).append(chinese[chars.length - i - 1]);
		}
		return sb.toString() + "元整";
	}

	/**
	 * BigDecimal数字转中文人民币大写 987654321.12 -> 玖亿捌仟柒佰陆拾伍万肆仟叁佰贰拾壹元壹角贰分 987654321 -> 玖亿捌仟柒佰陆拾伍万肆仟叁佰贰拾壹元整
	 *
	 * @param bigDecimal
	 * @return
	 */
	public static String bigDecimalToLocalStr(BigDecimal bigDecimal) {
		String[] chinese = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万" };
		String[] numChinese = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String[] afterChinese = { "角", "分" };
		String str = String.valueOf(bigDecimal);
		String[] arr = str.split("\\.");
		char[] chars = arr[0].toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			sb.append(numChinese[Integer.valueOf(String.valueOf(chars[i]))]).append(chinese[chars.length - i - 1]);
		}
		if (arr.length == 1) {
			return sb.toString() + "元整";
		} else {
			if (arr[1].length() > 2) {
				throw new IllegalArgumentException("人民币大写转换BigDecimal只能保留2位小数");
			} else {
				sb.append("元");
				char[] chars1 = arr[1].toCharArray();
				for (int i = 0; i < chars1.length; i++) {
					sb.append(numChinese[Integer.valueOf(String.valueOf(chars1[i]))]).append(afterChinese[i]);
				}
				return sb.toString();
			}
		}
	}

	/**
	 * 图片路径截取
	 *
	 * @param filPath		图片访问路径		(http://10.5.210.201:7777/group1/M00/00/00/CgXSyVtr4ouAYU8qAAE1l0Pf05Y241.png)
	 * @return
	 */
	public static String interceptPath(String filPath) {
		String path="";
		for(int i = 0; i < 3; i++){
			path = filPath.substring(filPath.indexOf("/")+1 );
		}
		return path;
	}


	/**
	 * 图片路径添加
	 *
	 * @param filPath		图片访问路径		(http://10.5.210.201:7777/group1/M00/00/00/CgXSyVtr4ouAYU8qAAE1l0Pf05Y241.png)
	 * @return
	 */
	public static String addPath(String filPath) {
		String path=filPath;
		return path;
	}


	//报表core 拼装前端返回参数
	public static String assemblingView(String code,String name){
		if(code != null && name != null){
			return name + "[" + code + "]";
		}
		return null;
	}
}
