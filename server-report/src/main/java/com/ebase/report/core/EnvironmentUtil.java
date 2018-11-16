package com.ebase.report.core;

import com.ebase.report.core.zk.EnvConstant;
import com.ebase.report.core.zk.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

@Configuration
@Order(1)
public class EnvironmentUtil implements EnvironmentAware {
    private static Environment env;

    /**
     * 通过key 获取配置值
     * @param key
     * @return
     */
    public static String getProperties(String key) {
        if (null == env || StringUtils.isBlank(key)) {
            return null;
        }
        String val = env.getProperty(key);
        return val;
    }

    @SuppressWarnings("static-access")
    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    //spring.config.location
    /**
     * 从main函数的参数里面截取spring.config.location的值放入系统变量(Hbase获取链接时要用)
     * @param args
     */
	public static void setSystemEnv(String[] args) {
		if (args != null && args.length > 0) {
			String profile = StringHelper.getConfigLocationByArgs(args);
			System.setProperty(EnvConstant.ENV_PROFILE_KEY, profile);
		}
	}
}
