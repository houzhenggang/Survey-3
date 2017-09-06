package com.hanvon.survey.component.service.m;

import java.io.FileNotFoundException;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanvon.survey.component.dao.i.ResMapper;
import com.hanvon.survey.component.dao.i.RoleMapper;
import com.hanvon.survey.component.dao.i.UserMapper;
import com.hanvon.survey.component.service.i.UserService;
import com.hanvon.survey.e.UserLoginForbiddenException;
import com.hanvon.survey.e.UserNameAlreadyExistsException;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.entities.manager.Role;
import com.hanvon.survey.utils.DataProcessUtils;
import com.hanvon.survey.utils.GlobalSettings;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper ;	
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private ResMapper resMapper;


	@Override
	public boolean regist(User user) throws FileNotFoundException {
		
		String userName = user.getUserName();
		String userPwd = user.getUserPwd();
		
		//验证用户名称是否存在，如果不存在，给予提示
		int exists = userMapper.isExistsUserName(userName);
		
		if(exists>0){
			throw new UserNameAlreadyExistsException(GlobalSettings.MESSAGE_USERNAME_EXISTS);
		}
		
		//对密码进行加密
		String md5UserPwd = DataProcessUtils.md5(userPwd);
		user.setUserPwd(md5UserPwd);
		
		int i = userMapper.insert(user);
		//事务回滚策略
		// TODO User对象在注册时计算权限码数组
		
		//将保存用户的insert标签设置获取自增主键值
		//	 useGeneratedKeys="true" keyProperty="userId"
		Integer userId = user.getUserId();
		//查询当前用户类别对应的Role对象
		Boolean company = user.getCompany();
		String roleName = "";
		//系统约定：
		if(company){
			roleName = "企业用户";
		}else{
			roleName = "个人用户";
		}
		Role role = roleMapper.getRoleByRoleName(roleName);
		//根据userId和roleId保存关联关系
		Integer roleId = role.getRoleId();
		userMapper.saveUserRoleRelationShip(userId,roleId);
		
		//根据userId深度加载Set<Role>
		Set<Role> roleSet = userMapper.getDeeplyRoleSet(userId);
		//查询最大权限位值
		Integer maxPos = resMapper.getMaxPos();
		//计算权限码数组
		String codeArrStr = DataProcessUtils.calculateCode(roleSet, maxPos);
		
		//更新User对象的权限码数组
		userMapper.updateCodeArrStrByUserId(codeArrStr,userId);

		return i > 0 ;
	}

	@Override
	public boolean existsUserName(String userName) {
		int count = userMapper.isExistsUserName(userName);
		return count>0;
	}

	@Override
	public User login(User user) {
		
		//增加事务通知方法
		//登录时，提交请求参数，密码是明文，需要加密，再与数据库密文进行比较。来查询数据
		String userPwd = user.getUserPwd();
		
		String md5UserPwd = DataProcessUtils.md5(userPwd);
		
		User userDB = userMapper.checkLogin(user.getUserName(),md5UserPwd);
		
		if(userDB == null){
			throw new UserLoginForbiddenException(GlobalSettings.USER_LOGIN_FORBIDDEN);
		}
		
		return userDB ;
	}

}
