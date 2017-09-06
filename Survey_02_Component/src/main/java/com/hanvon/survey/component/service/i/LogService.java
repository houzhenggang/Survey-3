package com.hanvon.survey.component.service.i;

import com.hanvon.survey.entities.manager.Log;
import com.hanvon.survey.page.Page;

public interface LogService {

	void saveLog(Log log);

	void createTable(int i);

	Page<Log> queryLogPage(String pageNoStr);

}
