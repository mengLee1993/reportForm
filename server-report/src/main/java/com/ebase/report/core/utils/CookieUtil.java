package com.ebase.report.core.utils;

import com.ebase.report.core.session.CacheKeyConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * <p> cookie 操作工具类 </p> 
 *
 * @project 	core
 * @class 		CookieUtil
 */
public class CookieUtil {

	// 默认cookie maxAge
	public final static int DEFAULT_COOKIE_MAXAGE = 60 * 60 * 24 * 7 * 2;

	/**
	 * 读取cookie getCookieByName
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}
	
	/**
	 * 读取cookie getCookieByName
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValueByName(HttpServletRequest request, String name) {
		Cookie cookie = getCookieByName(request, name);
		if(cookie!=null){
			return cookie.getValue();
		}else{
			return "";
		}
	}

	/**
	 * 删除cookie 
	 * removeCookie
	 * @param response
	 * @param name
	 */
	public static void removeCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 删除所有cookie 
	 * removeCookie
	 * @param response
	 */
	public static void removeAllCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				removeCookie(response, cookie.getName());
			}
		}
	}
	
	
	/**
	 * 设置cookies
	 * setCookie
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(DEFAULT_COOKIE_MAXAGE);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 
	 * setCookie
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(DEFAULT_COOKIE_MAXAGE);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	
	/**
	 * 读取所有的cookies readCookieMap
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 拿到sessionid
	 * @param
	 * @return
	 */
	public static String getSessionId(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		StringBuilder sessionid = new StringBuilder(CacheKeyConstant.ACCT_SESSION);
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if("JSESSIONID".equals(cookie.getName())){
					sessionid.append(cookie.getValue());
				}
			}
		}
		String clientType = WebUtil.getClientType(request);
		sessionid.append(clientType);

		String remoteHost = HttpUtils.getRemoteHost();
		sessionid.append(remoteHost);

		return sessionid.toString();
	}


	public static String getSessionIdRequest(HttpServletRequest request){

		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();

		StringBuilder sessionId = new StringBuilder(CacheKeyConstant.ACCT_SESSION);
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if("JSESSIONID2".equals(cookie.getName())){
					sessionId.append(cookie.getValue());
				}
			}
		}else{
			String s = UUID.randomUUID().toString();
			sessionId.append(s);

			setCookie(response,"JSESSIONID2",s);

		}
		String clientType = WebUtil.getClientType(request);
		sessionId.append(clientType);
		String remoteHost = HttpUtils.getRemoteHost();
		sessionId.append(remoteHost);

		return sessionId.toString();
	}
}
