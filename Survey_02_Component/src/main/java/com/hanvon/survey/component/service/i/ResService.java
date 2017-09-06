package com.hanvon.survey.component.service.i;

import java.util.List;

import com.hanvon.survey.entities.manager.Res;

public interface ResService {

	List<Res> getAll();

	boolean servletPathExists(String servletPath);

	Integer getMaxPos();

	Integer getMaxCode(int maxPos);

	void saveRes(Res res);

	void deleteBatch(List<Integer> resIdList);

	boolean updateStatus(Integer resId);

	Res getResByServletPath(String servletPath);
	
}
