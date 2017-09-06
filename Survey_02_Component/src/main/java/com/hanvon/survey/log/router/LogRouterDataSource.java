package com.hanvon.survey.log.router;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.hanvon.survey.log.thread.local.RoutingKeyBinder;

//自定义的路由器数据源：
public class LogRouterDataSource extends AbstractRoutingDataSource {

	//根据这个方法得到一个key，根据这个key找到对应的数据源，将来访问对应的数据源
	@Override
	protected Object determineCurrentLookupKey() {
		
		String key = RoutingKeyBinder.getKey(); // ?
		
		RoutingKeyBinder.removeKey();

		return key ;
	}

}
