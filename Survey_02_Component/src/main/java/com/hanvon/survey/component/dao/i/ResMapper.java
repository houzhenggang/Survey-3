package com.hanvon.survey.component.dao.i;

import com.hanvon.survey.entities.manager.Res;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ResMapper {
    int deleteByPrimaryKey(Integer resId);

    int insert(Res record);

    Res selectByPrimaryKey(Integer resId);

    List<Res> selectAll();

    int updateByPrimaryKey(Res record);

	int servletPathExists(String servletPath);

	Integer getMaxPos();

	Integer getMaxCode(int maxPos);

	void deleteBatch(@Param("resIdList") List<Integer> resIdList);

	void updateStatus(@Param("finalStatus") boolean finalStatus, @Param("resId") Integer resId);

	Res getResByServletPath(String servletPath);
}