package com.hanvon.survey.component.dao.i;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.hanvon.survey.entities.manager.Admin;
import com.hanvon.survey.entities.manager.Role;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer adminId);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);

	Admin login(@Param("adminName") String adminName,@Param("adminPwdMD5")  String adminPwdMD5);
	
	
	int getAdminCountByAdminName(String adminName);

	List<Integer> getCurrentRoleList(Integer adminId);

	void deleteOldRelationship(Integer adminId);
	void saveNewRelationship(@Param("roleIdList") List<Integer> roleIdList,@Param("adminId") Integer adminId);
	
	
	
	Set<Role> getDeeplyRoleSet(Integer adminId);//深度加载roleSet集合

	void updateCodeArrStr(@Param("codeArrStr") String codeArrStr, @Param("adminId") Integer adminId);

	void updateCodeForAdminAll(@Param("adminAll") List<Admin> adminAll);
	
	
	
	
	
	
	
	
	
	
}