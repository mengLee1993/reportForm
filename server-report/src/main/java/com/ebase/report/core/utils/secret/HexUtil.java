package com.ebase.report.core.utils.secret;

/**
 * 
 * <p>
 * 字符串 转换成 二进制字符串
 * </p>
 *
 * @project core
 * @class HexUtil
 */
public class HexUtil {

	/**
	 * 二进制 和 16进制字符串的转换
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHexString(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString().toLowerCase();
	}

	/**
	 * 16进制字符串的转换成二进制
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toLowerCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789abcdef".indexOf(c);
	}

}
