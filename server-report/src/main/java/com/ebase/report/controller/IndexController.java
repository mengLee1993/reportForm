package com.ebase.report.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebase.report.core.TokenUtil;
import com.ebase.report.core.session.AcctSession;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.CookieUtil;
import com.ebase.report.core.utils.StringUtil;
import com.ebase.report.core.utils.secret.Md5Util;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@Controller
public class IndexController {
    private static Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Value("${login.success.url}")
    private String loginSuccessUrl;

    @RequestMapping({"/", "/index", ""})
    public String redirection(HttpServletRequest request, HttpServletResponse response) {

        //验证用户是否登录..
        String sessionId = CookieUtil.getSessionIdRequest(request);

        Object attribute = request.getSession().getAttribute(Md5Util.encrpt(sessionId));

        if(attribute == null) {
            // 判断是否portal登录
            String token = request.getHeader("Authorization");
            if (StringUtil.isEmpty(token)) {
                token = request.getParameter("Authorization");
                if (StringUtil.isEmpty(token)) {
                    token = (String) request.getAttribute("Authorization");
                }
            }

            if(StringUtil.isNotEmpty(token)) {
                System.out.println("--- sso token ---"+token);

                // portal已登录，解析token是否正确
                Claims claims = TokenUtil.parseTokenClaims(token);
                JSONObject jsonObj = JSON.parseObject(claims.getSubject());

                //先写cookie
                CookieUtil.setCookie(response,"reportName",jsonObj.getString("userNameCn"));
                CookieUtil.setCookie(response, "token",token);

                return "homePage";
            }
            //获得缓存失败或登录状态无效 调到登录页面
            LOG.info("跳转到登录页!");
            return "login";
        }
        return "homePage";
    }
}
