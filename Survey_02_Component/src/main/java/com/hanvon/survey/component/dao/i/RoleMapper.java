package com.hanvon.survey.component.dao.i;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hanvon.survey.entities.manager.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    Role selectByPrimaryKey(Integer roleId);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
    
    
    
	void batchDelete(@Param("roleIdList") List<Integer> roleIdList);

	List<Integer> getCurrentAuthIdList(Integer roleId);

	void removeOldRelationship(Integer roleId);

	void saveNewRelationship(@Param("roleId") Integer roleId, @Param("authIdList") List<Integer> authIdList);

	Role getRoleByRoleName(String roleName);
    
	
    
}