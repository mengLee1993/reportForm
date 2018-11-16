package com.ebase.report.core.zk;

import org.apache.commons.lang3.StringUtils;

public class StringHelper extends StringUtils {


	/**
	 * 字符串拼接
	 * 
	 * @param strs
	 */
	public static String concat(String... strs) {
		if (null == strs) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (String str : strs) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String trim(String sour, String f) {
		if (StringUtils.isBlank(sour)) {
			return sour;
		}
		if (sour.startsWith(f)) {
			sour = sour.substring(f.length());
		}
		if (sour.endsWith(f)) {
			sour = sour.substring(0, sour.length() - f.length());
		}
		return sour;
	}

	/**
	 * 根据main函数args参数 获取spring.profiles.active
	 * @param args
	 * @return
	 */
	public static String getProfilesByArgs(String[] args, String keyType) {
		String profile = "";
		for (String str : args) {
			if (str.contains(keyType)) {
				profile = str;
				break;
			}
		}
		if (StringUtils.isNotBlank(profile)) {
			String trim = profile.split("=")[1].trim();
			return trim;
		} else {
			return null;
		}
	}
	
	/**
	 * 根据main函数args参数 获取spring.config.location
	 * @param args
	 * @return
	 */
	public static String getConfigLocationByArgs(String[] args) {
		String profile = "";
		for (String str : args) {
			if (str.contains(EnvConstant.ENV_PROFILE_KEY)) {
				profile = str;
				break;
			}
		}
		if (StringUtils.isNotBlank(profile)) {
			String trim = profile.split("=")[1].trim();
			return trim;
		} else {
			return null;
		}
	}
}
