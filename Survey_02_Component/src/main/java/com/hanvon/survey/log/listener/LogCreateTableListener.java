package com.hanvon.survey.log.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.hanvon.survey.component.service.i.LogService;
import com.hanvon.survey.log.thread.local.RoutingKeyBinder;

//在服务器启动时，创建当月和下三个月的表；
public class LogCreateTableListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private LogService logService ;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		ApplicationContext applicationContext = event.getApplicationContext();
		
		//根据当前容器来获取父容器，如果父容器为null,那就说明，当前容器就是父容器；
		ApplicationContext parent = applicationContext.getParent();
		if(parent==null){
			
			RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
			
			//在监听器到容器刷新时，来创建表
			logService.createTable(0);
			
			RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
			logService.createTable(1);
			
			RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
			logService.createTable(2);
			
			RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
			logService.createTable(3);			
			
		}
		
	}

}
