package com.hanvon.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanvon.survey.component.dao.i.LogMapper;
import com.hanvon.survey.component.service.i.LogService;
import com.hanvon.survey.entities.manager.Log;
import com.hanvon.survey.page.Page;
import com.hanvon.survey.utils.DataProcessUtils;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper ;

	@Override
	public void saveLog(Log log) {

		//logMapper.insert(log);
		
		//修改保存操作代码
		String tableName = DataProcessUtils.getGenerateTableName(0);
		logMapper.insert(log,tableName);
	}

	@Override
	public void createTable(int i) {
		
		String tableName = DataProcessUtils.getGenerateTableName(i);
		
		logMapper.createTable(tableName);
	}

	@Override
	public Page<Log> queryLogPage(String pageNoStr) {
		
		Set<String> logTables = logMapper.getLogTables();
		
		int totalCountForLog = logMapper.getTotalCountForLog(logTables);
		
		Page<Log> page = new Page(pageNoStr,totalCountForLog);
		
		int index = page.getIndex();
		
		int pageSize = page.getPageSize();
		
		List<Log> list = logMapper.getPage(logTables, index, pageSize);
		
		page.setDataList(list);
		
		return page;
	}
	
}
