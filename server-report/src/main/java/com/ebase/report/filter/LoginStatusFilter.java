package com.ebase.report.filter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebase.report.core.TokenUtil;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.session.AcctSession;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.CookieUtil;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.core.utils.StringUtil;
import com.ebase.report.core.utils.WebUtil;
import com.ebase.report.core.utils.secret.Md5Util;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: wangyu
 */
@Component
@ServletComponentScan("com.ebase.report.filter")
@WebFilter(value = {"/*"},filterName = "loginStatusFilter")
public class LoginStatusFilter implements Filter {

    private final static Logger LOG =LoggerFactory.getLogger(LoginStatusFilter.class);

    //不拦截的url
    private List<String> ALLOWED_PATHS = new ArrayList<>();

    //不拦截文件
    private List<String> ALLOWED_SUFFIX = new ArrayList<>();

//    @Autowired
//    private AcctAPI acctAPI;

    @Value("${login.url}")
    private String loginUrl;
//
    @Value("${allowed.paths}" )
    private String allowedUrl;
//
    @Value("${allowed.suffix}")
    private String allowedSuffix;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        LOG.info("初始化信息 --");
        ALLOWED_PATHS = Arrays.asList(allowedUrl.split(","));
        ALLOWED_SUFFIX = Arrays.asList(allowedSuffix.split(","));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest)request;
        HttpServletResponse servletResponse = (HttpServletResponse)response;

        //不拦截
        String requestURL = servletRequest.getRequestURI().substring(servletRequest.getContextPath().length()).replaceAll("[/]+$", "");

        if(allowedUrl(requestURL) || allowedSuffix(requestURL)){
            chain.doFilter(request, response);
            return ;
        }

       //验证用户是否登录
        String sessionId = CookieUtil.getSessionIdRequest(servletRequest);

        Object attribute = servletRequest.getSession().getAttribute(Md5Util.encrpt(sessionId));

        if(attribute == null){
            // 判断cookie是否存在token
            String token = CookieUtil.getCookieValueByName(servletRequest, "token");

            if(StringUtil.isNotEmpty(token)) {
                System.err.println("--- filter get token ---"+token);

                // portal已登录，解析token是否正确
                Claims claims = TokenUtil.parseTokenClaims(token);

                AcctSession acctSession = new AcctSession();
                JSONObject jsonObj = JSON.parseObject(claims.getSubject());
                acctSession.setOrgId(jsonObj.getString("orgId"));
                acctSession.setAcctTitle(jsonObj.getString("loginId"));
                acctSession.setAcctType(Long.parseLong(jsonObj.getString("userType")));
                acctSession.setAcctId(Long.parseLong(jsonObj.getString("userSid")));
                acctSession.setReAcctId(jsonObj.getString("loginId"));
                acctSession.setToken(token);

                acctSession.setName(jsonObj.getString("userNameCn"));

                //先写死角色
                List<String> reRoleId = new ArrayList<>();
                reRoleId.add("R1543383203888");
                acctSession.setReRoleId(reRoleId);

                attribute = acctSession;

            } else {
                //获得缓存失败或登录状态无效 调到登录页面
                LOG.info("登录状态无效!");
                if (WebUtil.isJsonRequest(servletRequest)) {
                    servletRequest.setCharacterEncoding("UTF-8");
                    JsonResponse jsonResponse = new JsonResponse();
                    servletResponse.setContentType("application/json; charset=UTF-8"); // 转码

                    jsonResponse.setRetCode("0200000");
                    jsonResponse.setRetDesc("");
                    jsonResponse.setRspBody(loginUrl);
                    servletResponse.getWriter().write(JsonUtil.toJson(jsonResponse));
                    return;
                }else{
                    servletRequest.setCharacterEncoding("UTF-8");
                    servletResponse.setContentType("text/html; charset=UTF-8"); // 转码
                    servletResponse.getWriter()
                            .println("<script language=\"javascript\">if(window.opener==null){window.top.location.href=\""
                                    + loginUrl + "\";}else{window.opener.top.location.href=\"" + loginUrl
                                    + "\";window.close();}</script>");
                    return;
                }
            }
        }

         LOG.info("用户登录成功!");

        //把用户数据放到session中 并初始化 数据
        String key = CookieUtil.getSessionIdRequest(servletRequest);

        servletRequest.getSession().setAttribute(Md5Util.encrpt(key),attribute);

        //并初始化 threadlocal
        AssertContext.init((AcctSession)attribute);

        //处理请求
        chain.doFilter(request, response);

        //清空threadlocal
        AssertContext.destroy();
    }




    @Override
    public void destroy() {
//        AssertContext.destroy();
    }


    /**
     * 不拦截请求
     * @param requestURL
     * @return
     */
    private boolean allowedSuffix(String requestURL) {

        if(requestURL.contains(".")){
            String suffix = requestURL.substring(requestURL.lastIndexOf("."));

            return ALLOWED_SUFFIX.stream().anyMatch(suf -> suf.equals(suffix));
        }

        return false;
    }

    /**
     * 不拦截 url
     * @param requestURL
     * @return
     */
    private boolean allowedUrl(String requestURL) {
        if(StringUtil.isEmpty(requestURL)){
            return true;
        }

        boolean allowed = ALLOWED_PATHS.contains(requestURL);

        if(!allowed){
            allowed = ALLOWED_PATHS.stream().filter(x -> x.contains("*"))
                    .map(x -> x.substring(0,x.lastIndexOf("*"))).anyMatch(x -> requestURL.contains(x));
        }

        return allowed;
    }
}
