package com.ebase.report.core.utils.secret;

import com.ebase.report.core.utils.StringUtil;

import java.security.MessageDigest;

/**
 * 
 * <p>
 * md5 转换
 * </p>
 *
 * @project core
 * @class Md5Util
 */
public class Md5Util {

	public static String encrpt(String msg) {
		try {
			if (StringUtil.isEmpty(msg)) {
				return null;
			}
			byte[] bytes = msg.getBytes(SecretConstans.DEFAULT_CHARSET);
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			byte[] result = md.digest();
			return HexUtil.bytesToHexString(result);
		} catch (Exception ex) {
			throw new RuntimeException("md5 error!", ex);
		}
	}
	
	public static String encrptWithSalt(String password, String salt) {
		return Md5Util.encrpt(password+"["+salt+"]");
	}
}
