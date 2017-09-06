package com.hanvon.survey.component.service.i;

import java.util.List;

import com.hanvon.survey.entities.manager.Admin;

public interface AdminService {

	Admin login(Admin admin);
	
	
	
	void saveAdmin(Admin admin);

	List<Admin> getAdminList();



	List<Integer> getCurrentRoleList(Integer adminId);



	void updateRelationship(List<Integer> roleIdList, Integer adminId);
	
}
