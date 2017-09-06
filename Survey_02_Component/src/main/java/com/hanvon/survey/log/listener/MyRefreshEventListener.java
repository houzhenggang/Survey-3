package com.hanvon.survey.log.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 测试：MyRefreshEventListener监听器监听ContextRefreshedEvent事件（表示IOC容器被刷新）；
 * @author zhangyu
 *
 */
public class MyRefreshEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		System.out.println(this.getClass().getName() + " - " + event.getClass() +" ★★★★★★★★★★ ");
		
		ApplicationContext applicationContext = event.getApplicationContext();
		
		ApplicationContext parent = applicationContext.getParent();
		if(parent!=null){
			System.out.println("父IOC容器---------->>>>>>>"+ parent.getClass());
		}else{
			System.out.println("子IOC容器========》》》》》》"+applicationContext.getClass());
		}
	}

}
