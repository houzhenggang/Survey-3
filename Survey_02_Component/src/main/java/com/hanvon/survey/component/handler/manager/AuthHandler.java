package com.hanvon.survey.component.handler.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanvon.survey.component.service.i.AuthService;
import com.hanvon.survey.component.service.i.ResService;
import com.hanvon.survey.e.RemoveAuthFailedException;
import com.hanvon.survey.entities.manager.Auth;
import com.hanvon.survey.entities.manager.Res;
import com.hanvon.survey.utils.GlobalSettings;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class AuthHandler {

	@Autowired
	private AuthService authService;

	@Autowired
	private ResService resService;

	@RequestMapping("/manager/auth/dispatcher")
	public String doDispatcher(
			@RequestParam(value = "resIdList", required = false) List<Integer> resIdList,
			@RequestParam("authId") Integer authId) {

		authService.dispatcher(authId, resIdList);

		return "redirect:/manager/auth/showList";
	}

	@RequestMapping("/manager/auth/toDispatcherUI/{authId}")
	public String toDispatcherUI(@PathVariable("authId") Integer authId,Map<String, Object> map) {
		
		List<Res> resList = resService.getAll();
		map.put("resList", resList);
		
		List<Integer> currentIdList = authService.getCurrentResIdList(authId);
		map.put("currentIdList", currentIdList);
		
		return "manager/dispatcher_auth_res";
	}
	
	@ResponseBody
	@RequestMapping("/manager/auth/updateAuthName")
	public Map<String, String> updateAuthName(Auth auth) {

		Map<String, String> jsonMap = new HashMap<>();

		try {
			authService.updateAuth(auth);
			jsonMap.put("message", "success");
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("message", "failed");
		}

		return jsonMap;
	}

	@RequestMapping("/manager/auth/batchDelete")
	public String batchDelete(
			@RequestParam(value = "authIdList", required = false) List<Integer> authIdList) {

		if (authIdList != null && authIdList.size() > 0) {

			try {
				authService.batchDelete(authIdList);
			} catch (Exception e) {
				Throwable cause = e.getCause();

				if (cause != null) {
					if (cause instanceof MySQLIntegrityConstraintViolationException) {
						throw new RemoveAuthFailedException(
								GlobalSettings.MESSAGE_REMOVE_AUTH_FAILED);
					}
				}

				throw e;
			}

		}

		return "redirect:/manager/auth/showList";
	}

	@RequestMapping("/manager/auth/showList")
	public String showList(Map<String, Object> map) {

		List<Auth> authList = authService.getAuthList();
		map.put("authList", authList);

		return "manager/auth_list";
	}

	@RequestMapping("/manager/auth/saveAuth")
	public String saveAuth(Auth auth) {

		authService.saveEntity(auth);

		return "redirect:/manager/auth/showList";
	}

}
