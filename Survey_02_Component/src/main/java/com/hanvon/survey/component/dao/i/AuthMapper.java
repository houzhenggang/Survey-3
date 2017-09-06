package com.hanvon.survey.component.dao.i;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hanvon.survey.entities.manager.Auth;

public interface AuthMapper {
    int deleteByPrimaryKey(Integer authId);

    int insert(Auth record);

    Auth selectByPrimaryKey(Integer authId);

    List<Auth> selectAll();

    int updateByPrimaryKey(Auth record);
    
    
    
    void batchDelete(@Param("authIdList") List<Integer> authIdList);
    
	List<Integer> getCurrentResIdList(Integer authId);
	
	void removeOldRelationship(Integer authId);
	
	void saveNewRelationship(@Param("authId") Integer authId, @Param("resIdList") List<Integer> resIdList);
}