package com.hanvon.survey.component.service.i;

import java.util.List;

import com.hanvon.survey.entities.manager.Auth;

public interface AuthService {

	List<Auth> getAuthList();

	void batchDelete(List<Integer> authIdList);

	void updateAuth(Auth auth);

	List<Integer> getCurrentResIdList(Integer authId);

	void dispatcher(Integer authId, List<Integer> resIdList);

	void saveEntity(Auth auth);

}
