package com.hanvon.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanvon.survey.component.dao.i.AdminMapper;
import com.hanvon.survey.component.dao.i.ResMapper;
import com.hanvon.survey.component.service.i.AdminService;
import com.hanvon.survey.e.AdminLoginForbiddenException;
import com.hanvon.survey.e.AdminNameExistsException;
import com.hanvon.survey.entities.manager.Admin;
import com.hanvon.survey.entities.manager.Role;
import com.hanvon.survey.utils.DataProcessUtils;
import com.hanvon.survey.utils.GlobalSettings;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private ResMapper resMapper ;

	@Override
	public Admin login(Admin admin) {
		
		String adminName = admin.getAdminName();
		String adminPwd = admin.getAdminPwd();
		
		String adminPwdMD5 = DataProcessUtils.md5(adminPwd);
		
		Admin adminDB = adminMapper.login(adminName,adminPwdMD5);
		
		if(adminDB==null){
			throw new AdminLoginForbiddenException(GlobalSettings.ADMIN_LOGIN_FORBIDDEN);
		}		
		
		return adminDB;
	}
	
	@Override
	public void saveAdmin(Admin admin) {
		
		String adminName = admin.getAdminName();
		
		int adminCount = adminMapper.getAdminCountByAdminName(adminName);
		
		if(adminCount > 0) {
			
			throw new AdminNameExistsException(GlobalSettings.MSESSAGE_ADMIN_NAME_EXISTS);
			
		}
		
		String adminPwd = admin.getAdminPwd();
		adminPwd = DataProcessUtils.md5(adminPwd);
		admin.setAdminPwd(adminPwd);
		
		adminMapper.insert(admin);
	}

	@Override
	public List<Admin> getAdminList() {
		return adminMapper.selectAll();
	}

	@Override
	public List<Integer> getCurrentRoleList(Integer adminId) {
		return adminMapper.getCurrentRoleList(adminId);
	}

	@Override
	public void updateRelationship(List<Integer> roleIdList, Integer adminId) {
		
		//1.删除当前管理员之前分配的角色
		adminMapper.deleteOldRelationship(adminId);
		
		if(roleIdList!=null){
			//2.重新保存新的关联数据
			adminMapper.saveNewRelationship(roleIdList,adminId);
		}
		
		//3.重新计算当前管理员用户的权限码
		//---------------------------------
		//①获取资源最大权限位
		Integer maxPos = resMapper.getMaxPos();
		
		//②获取当前用户的角色集合（深度加载）
		Set<Role> roleSet = adminMapper.getDeeplyRoleSet(adminId);
		
		//③计算当前的用户的权限码数组->字符串
		String codeArrStr = DataProcessUtils.calculateCode(roleSet, maxPos) ;
		
		//④将当前用户权限码更新到code_arr_str字段上
		adminMapper.updateCodeArrStr(codeArrStr,adminId);
		//---------------------------------
	}
}
