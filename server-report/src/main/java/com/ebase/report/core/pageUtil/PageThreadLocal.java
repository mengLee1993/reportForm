package com.ebase.report.core.pageUtil;


public class PageThreadLocal {
	
	static ThreadLocal<PageDTO<?>> threadlocal = new ThreadLocal<>();

	public static void init(PageDTO<?> page){
		threadlocal.set(page);
	}
	
	public static void remove(){
		threadlocal.remove();;
	}
	
	public static PageDTO<?> get(){
		return threadlocal.get();
	}

}
