package com.hanvon.survey.component.dao.i;

import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.entities.manager.Role;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	int isExistsUserName(String userName);

	User checkLogin(@Param("userName") String userName,@Param("md5UserPwd") String md5UserPwd);
	
	
	Set<Role> getDeeplyRoleSet(Integer userId);//深度加载roleSet集合

	void saveUserRoleRelationShip(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

	void updateCodeArrStrByUserId(@Param("codeArrStr") String codeArrStr,@Param("userId")  Integer userId);

	void updateCodeArrStrForUserAll(@Param("userAll") List<User> userAll);
}