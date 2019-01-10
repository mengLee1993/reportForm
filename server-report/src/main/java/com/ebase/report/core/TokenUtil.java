package com.ebase.report.core;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class TokenUtil {
	
	public static final String APP_ID="AUTH";
	public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
	public static final String DEFAULT_TOKEN_NAME = "Authorization";
	
	/**
	 * 由字符串生成加密key
	 * @return
	 */
	public static SecretKey generalKey(){
		String stringKey = APP_ID + JWT_SECRET;
		byte[] encodedKey = Base64.decodeBase64(stringKey);
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}
	
	/**
	 * 解析token的Payload部分
	 * @param token
	 * @return
	 */
	public static Claims parseTokenClaims(String token) {
		if (isValidToken(token)) {
			SecretKey key = generalKey();
			Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
			return claims;
		} else {
			return null;
		}
	}

	/**
	 * 检验token是否合法
	 * @param token
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public static boolean isValidToken(String token) {
		if (token!=null) {
			try {
				SecretKey key = generalKey();
				Jwt jwt = Jwts.parser().setSigningKey(key).parse(token);
				return true;
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
				return false;
			} catch (MalformedJwtException e) {
				e.printStackTrace();
				return false;
			} catch (SignatureException e) {
				e.printStackTrace();
				return false;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0QTIyQTFFNUYzMUE0QkJCQjc5RDhBMTY1MzczNDBDOCIsImlzcyI6IkFVVEguU0VSVkVSIiwiaWF0IjoxNTQyNjc5Mjk5LCJhdWQiOiJBVVRILkNMSUVOVCIsIm5iZiI6MTU0MjY3OTI5OSwic3ViIjoie1wiZW1haWxBZGRyXCI6XCJsaXFpYW5nQG1haWwuc2hvdWdhbmcuY29tLmNuXCIsXCJncm91cElkXCI6XCIxXCIsXCJpZGVudGl0eUlkXCI6XCIxMTAxMDIxOTc1MDgxODA0MTZcIixcImxvZ2luSWRcIjpcImxpcWlhbmdcIixcIm1vYmlsZVBob25lXCI6XCIxMzY3MTAyMDY3MFwiLFwib3JnRnVsbE5hbWVcIjpcIuWMl-S6rOmmlumSouiHquWKqOWMluS_oeaBr-aKgOacr-aciemZkOWFrOWPuFwiLFwib3JnSWRcIjpcIjEwMDAxMTAwMTEwXCIsXCJvcmdTaG9ydE5hbWVcIjpcIummluiHquS_oVwiLFwicGFydHRpbWVPcmdGdWxsTmFtZVwiOlwi6L-B5a6J6aaW6Ieq5L-h5L-h5oGv5oqA5pyv5pyJ6ZmQ5YWs5Y-4XCIsXCJzZXhcIjpcIjFcIixcInNoaWZ0SWRcIjpcIjFcIixcInVzZXJEdXR5XCI6XCJTQVwiLFwidXNlckZ1bGxOYW1lUHlcIjpcImxpcWlhbmdcIixcInVzZXJOYW1lQ25cIjpcIuadjuW8ulwiLFwidXNlclBvc3ROYW1lXCI6XCLmioDmnK_lspdcIixcInVzZXJTaWRcIjoxMDAwLFwidXNlclR5cGVcIjpcIjBcIixcInZhbGlkRmxhZ1wiOlwiMVwifSJ9.Ii-QA3i-WjWoi2flKpeDIYNDvUqbHC15_SyRDoz5pBg";
		Claims claims2 = TokenUtil.parseTokenClaims(token);
		System.out.println(claims2.getSubject());
	}
}
