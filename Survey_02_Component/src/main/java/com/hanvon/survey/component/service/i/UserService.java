package com.hanvon.survey.component.service.i;

import com.hanvon.survey.entities.guest.User;

public interface UserService {
	
	public abstract boolean regist(User user) throws Exception;

	public abstract boolean existsUserName(String userName);

	public abstract User login(User user);
	
}
