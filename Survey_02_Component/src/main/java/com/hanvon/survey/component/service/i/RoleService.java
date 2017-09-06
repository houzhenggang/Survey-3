package com.hanvon.survey.component.service.i;

import java.util.List;

import com.hanvon.survey.entities.manager.Role;

public interface RoleService {

	List<Role> getRoleList();

	void batchDelete(List<Integer> roleIdList);

	void updateRole(Role role);

	List<Integer> getCurrentAuthIdList(Integer roleId);

	void dispatcher(Integer roleId, List<Integer> authIdList);

	void saveRole(Role role);
 
}
