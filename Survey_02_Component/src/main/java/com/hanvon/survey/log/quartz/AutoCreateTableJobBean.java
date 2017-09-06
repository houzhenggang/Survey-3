package com.hanvon.survey.log.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.hanvon.survey.component.service.i.LogService;
import com.hanvon.survey.log.thread.local.RoutingKeyBinder;

/**
 * 自动建表定时任务：要求每个月15号创建下三个月的表
 * @author zhangyu
 */
public class AutoCreateTableJobBean extends QuartzJobBean {


	private LogService logService ;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {	
		
		RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
		
		//创建下三个月的表
		logService.createTable(1);
		
		RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
		logService.createTable(2);
		
		RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
		logService.createTable(3);
		
	}
	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
