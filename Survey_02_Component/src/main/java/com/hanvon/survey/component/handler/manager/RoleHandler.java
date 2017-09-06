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
import com.hanvon.survey.component.service.i.RoleService;
import com.hanvon.survey.e.RemoveRoleFailedException;
import com.hanvon.survey.entities.manager.Auth;
import com.hanvon.survey.entities.manager.Role;
import com.hanvon.survey.utils.GlobalSettings;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class RoleHandler {

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthService authService;

	@RequestMapping("/manager/role/dispatcher")
	public String doDispatcher(
			@RequestParam("roleId") Integer roleId,
			@RequestParam(value = "authIdList", required = false) List<Integer> authIdList) {

		roleService.dispatcher(roleId, authIdList);

		return "redirect:/manager/role/showList";
	}

	@RequestMapping("/manager/role/toDispatcherUI/{roleId}")
	public String toDispathcerUI(@PathVariable("roleId") Integer roleId,
			Map<String, Object> map) {

		List<Auth> authList = authService.getAuthList();
		map.put("authList", authList);

		List<Integer> currentAuthIdList = roleService.getCurrentAuthIdList(roleId);
		map.put("currentAuthIdList", currentAuthIdList);

		return "manager/dispatcher_role_auth";
	}

	@ResponseBody
	@RequestMapping("/manager/role/updateRoleName")
	public Map<String, Object> updateRole(Role role) {

		Map<String, Object> jsonMap = new HashMap<>();

		try {
			roleService.updateRole(role);
			jsonMap.put("message", "success");
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("message", "failed");
		}

		return jsonMap;
	}

	@RequestMapping("/manager/role/batchDelete")
	public String batchDelete(
			@RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {

		if (roleIdList != null && roleIdList.size() > 0) {
			try {
				roleService.batchDelete(roleIdList);
			} catch (Exception e) {
				Throwable cause = e.getCause();

				if (cause != null
						&& cause instanceof MySQLIntegrityConstraintViolationException) {
					throw new RemoveRoleFailedException(
							GlobalSettings.MESSAGE_REMOVE_ROLE_FAILED);
				}

				throw e;
			}

		}

		return "redirect:/manager/role/showList";
	}

	@RequestMapping("/manager/role/showList")
	public String showList(Map<String, Object> map) {

		List<Role> roleList = roleService.getRoleList();
		map.put("roleList", roleList);

		return "manager/role_list";
	}

	@RequestMapping("/manager/role/saveRole")
	public String saveRole(Role role) {

		roleService.saveRole(role);

		return "redirect:/manager/role/showList";
	}

}
