package com.hanvon.survey.component.handler.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanvon.survey.component.service.i.LogService;
import com.hanvon.survey.entities.manager.Log;
import com.hanvon.survey.log.thread.local.RoutingKeyBinder;
import com.hanvon.survey.page.Page;
import com.hanvon.survey.utils.GlobalSettings;

@Controller
public class LogHandler {

	@Autowired
	private LogService logService ;
	
	@RequestMapping("/manager/log/showLogList")
	public String showLogList(
				Map map,
				@RequestParam(value="pageNoStr",required=false) String pageNoStr){
		
		RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);

		Page<Log> page = logService.queryLogPage(pageNoStr);
		map.put(GlobalSettings.PAGE,page);		
		
		return "manager/log_list";
	}
	
}
