package com.ebase.report.core.zk;

public final class EnvConstant {

	//相应环境配置文件地址
	public static final String ENV_PROFILE_KEY = "spring.config.location";
	
	//注册中心one、two、three等标识
	public static final String ACTIVE_KEY = "spring.profiles.active";
	
	//HBase使用的环境变量key
	public static final String ENV_KEY = "env";
	
	//服务类型标识
	public static final String SERVER_TYPE_KEY = "server.TYPE";
	
	
	//各个环境
	public static final String LOCAL = "local";
	public static final String DEV = "dev";
	public static final String FT = "ft";
	public static final String SIT = "sit";
	public static final String TPS = "tps";
	public static final String PROD = "prod";
	public static final String BETA = "beta";
	public static final String DEMO = "demo";
	
}
