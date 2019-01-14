package com.ebase.report.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
		String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJCNzg3OUJDRjAyOEI0NTIzQjY3RUY3NEI3MDQ5NjUxNSIsImlzcyI6IkFVVEguU0VSVkVSIiwiaWF0IjoxNTQ3MTg3NjAxLCJhdWQiOiJBVVRILkNMSUVOVCIsIm5iZiI6MTU0NzE4NzYwMSwic3ViIjoie1wiYnVzaW5lc3NJRHNcIjp7XCJDQVNcIjp7XCJiZWdnaW5BZHZpY2VTdGF0ZVByaXZpbGVnZVwiOlwiJzIyJywnMjEnLCcyMCcsJzExJywnMTInLCcxMCdcIixcImJlZ2dpbkFwcGx5QXBwcm92ZVByaXZpbGVnZVwiOlwiJzEwJywnNDEnLCczMCcsJzMxJywnMjAnLCc1MCcsJzQwJywnNzAnLCc2MCcsJzIxJ1wiLFwiYmVnZ2luQXBwbHlQcml2aWxlZ2VcIjpcIidPUklFTlRFRCcsJ05PTl9PUklFTlRFRCdcIixcImJlZ2dpbkFwcGx5U3RhdGVQcml2aWxlZ2VcIjpcIicwMycsJzAyJ1wiLFwiYmVnZ2luU2FsZU9yZ0NvZGVcIjpcIic4MDEwJywnODAwMCdcIixcIm9yZGVyQXBwcm92YWxTdGF0dXNcIjpcIicyOCcsJzI3JywnMjUnLCcyNidcIn0sXCJDUFBTXCI6e1wiZmFjaWxpdHlJZFwiOlwiJ0NSWjInLCdDUloxJ1wiLFwicmVzSWRcIjpcIidSQ00zJywnUkNNMScsJ1NDVEwyJywnUkFGMicsJ1NDVEwxJywnUkNNMicsJ1JDTTQnLCdQTF9UQ00nLCdSQUYxJywnUEEyJywnQ1M2JywnQ1MzJywnQ1MxJywnQ1M3JywnUEExJywnRENMMicsJ0FQTDInLCdDUzUnLCdEQ0w0JywnQ1M4JywnQ0ExJywnRkNMMycsJ0RDTDMnLCdEQ0wxJywnTUJBRjMnLCdDQTMnLCdDQTInLCdNQkFGMScsJ0FQTDEnLCdDQTYnLCdDUzQnLCdNQkFGMicsJ0NTMicsJ1BBMycsJ0NBNScsJ1BBNCcsJ0NBNCcsJ0ZDTDEnLCdGQ0wyJ1wifSxcIlBFU1wiOntcImVxcHRQcml2aWxlZ2VcIjpcIidBUEwxJywnQVBMMicsJ0NBMScsJ0NBMicsJ0NBMycsJ0NBNCcsJ0NBNScsJ0NBNicsJ0NTMScsJ0NTMicsJ0NTMycsJ0NTNCcsJ0NTNScsJ0NTNicsJ0NTNycsJ0NTOCcsJ0NXMScsJ0NXMicsJ0NXMycsJ0RDTDEnLCdEQ0wyJywnRENMMycsJ0ZDTDEnLCdGQ0wyJywnSFNNJywnTUJBRjEnLCdNQkFGMicsJ01CQUYzJywnUEExJywnUEEyJywnUEEzJywnUEE0JywnUEhMMycsJ1BMLVRDTScsJ1JBMScsJ1JBMicsJ1JBRjEnLCdSQUYyJywnUkNNMScsJ1JDTTInLCdSQ00zJywnUkNNNCcsJ1NDVEwxJywnU0NUTDInXCIsXCJ3dG1XYXJlaG91c2VcIjpcIidDUDUnLCdDSk0nLCdTUUwnLCdaUUEnLCdTTkUnLCdTTlMnLCdDUDcnLCdFWkgnLCdFWlcnLCdDUDQnLCdFWkonLCdFV0snLCdTWUgnLCdFTVgnLCdDUDYnLCdTQ1EnLCdUSDEnLCdUUTEnLCdSRjEnLCdSTTEnLCdBUDEnLCdDUFQnLCdDUFMnLCdDUFEnLCdDUEcnLCdDUDknLCdDUDgnLCdDUDMnLCdDUDInLCdDUDEnLCdKUTQnLCdKUTMnLCdKUTInLCdKUTEnLCdMVDEnLCdaSDEnLCdaWTEnXCJ9fSxcImdyb3VwSWRcIjpcIjBcIixcImlkZW50aXR5SWRcIjpcIlhRc0xpdlpLdlZMQW9FQ0F0Zk85OGRCTnJnT2FWT3NFeFA5YnN3V1U2ejZiZy9WeUUwTmtWQT09XCIsXCJsb2dpbklkXCI6XCJsaXEwNDE2XCIsXCJtb2JpbGVQaG9uZVwiOlwiMTM2NzEwMjA2NzBcIixcIm9yZ0Z1bGxOYW1lXCI6XCJNRVPkuK3lv4NcIixcIm9yZ0lkXCI6XCJYWFNZQk1FU1pYXCIsXCJvcmdTaG9ydE5hbWVcIjpcIk1FU-S4reW_g1wiLFwic2V4XCI6XCIxXCIsXCJzaGlmdElkXCI6XCIwXCIsXCJ1c2VyRHV0eVwiOlwiRDAwMTFcIixcInVzZXJGdWxsTmFtZVB5XCI6XCJsaXEwNDE2XCIsXCJ1c2VyTmFtZUNuXCI6XCLmnY7lvLpcIixcInVzZXJQb3N0TmFtZVwiOlwi5b6F5a6a5bKX5L2NXCIsXCJ1c2VyU2lkXCI6MTE2NjYsXCJ1c2VyVHlwZVwiOlwiMFwiLFwidXNlclR5cGUyXCI6XCIxMFwiLFwidmFsaWRGbGFnXCI6XCIxXCJ9In0.ZNgJvQ9yR7p1yNGUsjrRv2_MGbv-_Q7tlq08a3crzVk";
		Claims claims = TokenUtil.parseTokenClaims(token);
		System.out.println(claims.getSubject());
		JSONObject jsonObj = JSON.parseObject(claims.getSubject());
		System.out.println(jsonObj.getString("userNameCn"));

	}
}
