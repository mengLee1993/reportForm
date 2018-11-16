package com.ebase.report.core.utils;

import com.ebase.report.core.session.CacheKeyConstant;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * @description 
 * <p> web工具类 </p>
 * @project 	core
 * @date 		2017年5月27日 上午10:21:02
 */
public class WebUtil {

	private static PathMatcher pathMatcher = new AntPathMatcher();

	public final static String REQ_URL_PATH = "REQ_URL_PATH";

	private static UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	private WebUtil(){};

	/**
	 * 是否contentType为json请求或者ajax请求
	 * 
	 * @return
	 */
	public static boolean isJsonRequest(HttpServletRequest req) {
		String contentType = req.getContentType();
		if (StringUtil.contains(contentType, "json")) {
			return true;
		}
		String requestType = req.getHeader("X-Requested-With");
		if (StringUtil.isNotEmpty(requestType)) {
			return true;
		}
		// 上传文件是ajax 的请求， 对应的contentType 为  Content-Type:multipart/form-data; boundary=----WebKitFormBoundaryugckYFBKEyzA6etX
		if (StringUtil.contains(contentType, "multipart/form-data")) {
			return true;
		}
		return false;
	}


	/**
	 * 类似 ant的匹配模式 例如 /test/* 可以匹配所有 /test/a.do 等路径
	 * 支持**配置
	 * 支持*.jsp   
	 * 详细，查看  org.springframework.util.AntPathMatcher.AntPathMatcher()
	 * @param pattern
	 * @param lookupPath
	 * @return
	 */
	public static boolean matchPath(String pattern, String lookupPath) {
		return pathMatcher.match(pattern, lookupPath);
	}

	/**
	 * 获取真实ip ，经过f5 或者 nginx 分发后，获取实际的访问ip
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("HTTP_X_FORWARDED_FOR"); // X-Real-IP
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 多级反向代理
		if (null != ip && !"".equals(ip.trim())) {
			StringTokenizer st = new StringTokenizer(ip, ",");
			String ipTmp = "";
			if (st.countTokens() > 1) {
				while (st.hasMoreTokens()) {
					ipTmp = st.nextToken();
					if (ipTmp != null && ipTmp.length() != 0 && !"unknown".equalsIgnoreCase(ipTmp)) {
						ip = ipTmp;
						break;
					}
				}
			}
		}
		return ip;
	}
	
	/**
	 * 获取浏览器名称
	 * @param request
	 * @return
	 */
	public static String getBrowserName(HttpServletRequest request){
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		if(userAgent!=null&&userAgent.getBrowser()!=null){
			return userAgent.getBrowser().getName();
		}
		return "";
	}
	

	private static final String INIT_PARAM_DELIMITERS = ",; \t\n";
	/**
	 * 将 config split 成数组   ,; \t\n  支持5中拆分方式，自动trim
	 * @param config
	 * @return
	 */
	public static String[] splitToArray(String config){
		return org.springframework.util.StringUtils.tokenizeToStringArray(config, INIT_PARAM_DELIMITERS);
	}


	/**
	 * 客户端是 pc 还是 移动端
	 * @param
	 * @return
	 */
	public static String getClientType(HttpServletRequest request){


		String userAgent = request.getHeader("user-agent");

		if(Arrays.asList("android","mac os","windows phone").contains(userAgent)){
			return CacheKeyConstant.Cachekey_MOVE;
		}else {
			return CacheKeyConstant.Cachekey_PC;
		}

	}

	/**
	 * 获取用户访问 web访问path路径
	 * @return
	 */
	public static String getPath(HttpServletRequest request) {
		String path = (String) request.getAttribute(REQ_URL_PATH);
		if (StringUtil.isEmpty(path)) {
			path = urlPathHelper.getPathWithinApplication(request);
			request.setAttribute(REQ_URL_PATH, path);
		}
		return path;
	}
	
}
