package com.hanvon.survey.log.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * 测试：MyStartEventListener监听ContextStartedEvent事件（表示IOC启动事件）。
 * @author zhangyu
 *
 */
public class MyStartEventListener implements ApplicationListener<ContextStartedEvent> {

	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
		
		System.out.println(this.getClass().getName() + " - " + event.getClass() +" ☆☆☆☆☆☆☆☆");
		
	}

}
