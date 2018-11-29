package com.ebase.report.controller;

import com.ebase.report.core.utils.CookieUtil;
import com.ebase.report.core.utils.secret.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
        String sessionId = CookieUtil.getSessionId();

        Object attribute = request.getSession().getAttribute(Md5Util.encrpt(sessionId));

        if(attribute == null) {
            //获得缓存失败或登录状态无效 调到登录页面
            LOG.info("跳转到登录页!");
            return "login";
        } else {

            return "homePage";
        }
    }
}
