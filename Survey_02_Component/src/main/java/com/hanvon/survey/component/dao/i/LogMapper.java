package com.hanvon.survey.component.dao.i;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.hanvon.survey.entities.manager.Log;

public interface LogMapper {
    int deleteByPrimaryKey(Integer logId);

    //int insert(Log record);

    Log selectByPrimaryKey(Integer logId);

    List<Log> selectAll();

    int updateByPrimaryKey(Log record);

	void createTable(@Param("tableName") String tableName);

	void insert(@Param("log") Log log, @Param("tableName") String tableName);
	
	//获取所有日志表名称
	Set<String> getLogTables();
	
	//统计合并表的记录数
	int getTotalCountForLog(@Param("logTables") Set<String> logTables);
	
	//分页查询log
	List<Log> getPage(@Param("logTables") Set<String> logTables,	
				@Param("index")  int index,
				@Param("pageSize")  int pageSize);
}