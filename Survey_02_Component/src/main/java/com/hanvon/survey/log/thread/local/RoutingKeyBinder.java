package com.hanvon.survey.log.thread.local;


//用于绑定字符串key;决定使用哪一个数据源访问数据库
public class RoutingKeyBinder {

	private static ThreadLocal<String> local = new ThreadLocal<String>();

	public static String DATA_SOURCE_LOG = "DATA_SOURCE_LOG";
	
	public static void setKey(String key){
		local.set(key);
	}
	
	public static String getKey(){
		return local.get();
	}
	
	public static void removeKey(){
		local.remove();
	}
}
