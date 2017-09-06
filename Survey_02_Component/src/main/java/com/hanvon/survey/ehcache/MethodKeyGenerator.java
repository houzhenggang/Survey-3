package com.hanvon.survey.ehcache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

//用于生成 缓存中Map的key;将来从缓存的Map中获取缓存数据时，根据key来获取；要求key必须唯一；
public class MethodKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		
		StringBuilder key = new StringBuilder() ;
		
		String typeName = target.getClass().getName(); //目标方法所在类的全类名
		
		String methodName = method.getName(); //目标方法
		
		key.append(typeName);
		key.append(".").append(methodName);
		
		if(params!=null){
			for (Object param : params) {
				key.append(".").append(param.toString());
			}
		}
		
		return key.toString();
	}

}
