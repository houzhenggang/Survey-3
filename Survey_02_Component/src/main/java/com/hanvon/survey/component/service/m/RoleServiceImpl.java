package com.hanvon.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hanvon.survey.component.dao.i.AdminMapper;
import com.hanvon.survey.component.dao.i.ResMapper;
import com.hanvon.survey.component.dao.i.RoleMapper;
import com.hanvon.survey.component.dao.i.UserMapper;
import com.hanvon.survey.component.service.i.RoleService;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.entities.manager.Admin;
import com.hanvon.survey.entities.manager.Role;
import com.hanvon.survey.utils.DataProcessUtils;

@Controller
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private ResMapper resMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<Role> getRoleList() {
		return roleMapper.selectAll();
	}

	@Override
	public void batchDelete(List<Integer> roleIdList) {
		roleMapper.batchDelete(roleIdList);
	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}

	@Override
	public List<Integer> getCurrentAuthIdList(Integer roleId) {
		return roleMapper.getCurrentAuthIdList(roleId);
	}

	@Override
	public void dispatcher(Integer roleId, List<Integer> authIdList) {
		roleMapper.removeOldRelationship(roleId);
		if(authIdList != null) {
			roleMapper.saveNewRelationship(roleId, authIdList);
		}	
		
		//----重新计算用户权限码2016年12月6日 ----

		//1.获取最大权限码
		Integer maxPos = resMapper.getMaxPos();
		
		//2.获取所有的管理员用户
		List<Admin> adminAll = adminMapper.selectAll();
		
		//3.重新计算所有管理员的权限码
		for (Admin admin : adminAll) {
			//超级管理员不需要重新计算权限
			if("SuperAdmin".equals(admin.getAdminName())){
				continue;
			}
			
			Integer adminId = admin.getAdminId();
			
			Set<Role> roleSet = adminMapper.getDeeplyRoleSet(adminId);
			
			String codeArrStr = DataProcessUtils.calculateCode(roleSet, maxPos);
			
			admin.setCodeArrStr(codeArrStr);
		}
		
		//4.更新所有管理员用户的权限码
		adminMapper.updateCodeForAdminAll(adminAll); //批量更新
		
		
		//5.获取所有前台用户
		List<User> userAll = userMapper.selectAll();
		
		for (User user : userAll) {
			Integer userId = user.getUserId();
			
			Set<Role> roleSet = userMapper.getDeeplyRoleSet(userId);
			
			String codeArrStr = DataProcessUtils.calculateCode(roleSet, maxPos);
			
			user.setCodeArrStr(codeArrStr);
		}
		
		userMapper.updateCodeArrStrForUserAll(userAll);//批量更新
		
		//-----------------------------------
		
	}

	@Override
	public void saveRole(Role role) {
		roleMapper.insert(role);
	}

}
