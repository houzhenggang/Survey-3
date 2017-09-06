package com.hanvon.survey.component.handler.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanvon.survey.component.service.i.AdminService;
import com.hanvon.survey.component.service.i.RoleService;
import com.hanvon.survey.entities.manager.Admin;
import com.hanvon.survey.entities.manager.Role;
import com.hanvon.survey.utils.GlobalSettings;

@Controller
public class AdminHandler {

	@Autowired
	private AdminService adminService ;
	
	@Autowired
	private RoleService roleService;	
	
	@RequestMapping("/manager/admin/updateRelationship")
	public String updateRelationship(
				@RequestParam(value="roleIdList",required=false) List<Integer> roleIdList,
				@RequestParam("adminId") Integer adminId){
		//roleIdList可能为null,因为，可能不给当前管理员分配任何角色
		//if(roleIdList!=null){ //判断不能写在表现层，因为，可能会删除旧的关系，但是，不保存新的关联；
		adminService.updateRelationship(roleIdList,adminId);
		//}
		return "redirect:/manager/admin/showList";
	}
	
	
	@RequestMapping("/manager/admin/toDispatcherUI/{adminId}")
	public String toDispatcherUI(@PathVariable("adminId") Integer adminId,Map map){
		
		//1.查询所有角色列表数据
		List<Role> roleList = roleService.getRoleList();
		map.put("roleList", roleList);
		
		//2.查询当前管理员之前被分配过角色,采用角色id进行判断是否回显
		List<Integer> currentRoleList =  adminService.getCurrentRoleList(adminId);
		map.put("currentRoleList", currentRoleList);
		
		return "manager/dispatcher_admin_role";
	}
	
	
	@RequestMapping("/manager/admin/showList")
	public String showList(Map<String, Object> map) {
		
		List<Admin> adminList = adminService.getAdminList();
		map.put("adminList", adminList);
		
		return "manager/admin_list";
	}
	
	@RequestMapping("/manager/admin/saveAdmin")
	public String saveAdmin(Admin admin) {
		
		adminService.saveAdmin(admin);
		
		return "redirect:/manager/admin/showList";
	}
	
	@RequestMapping("/manager/admin/logout")
	public String logout(HttpSession session){
		
		session.invalidate();
		
		return "redirect:/manager/admin/toMainUI";
	}
	
	
	@RequestMapping("/manager/admin/login")
	public String login(Admin admin,HttpSession session){
		
		Admin adminDB = adminService.login(admin);
		
		session.setAttribute(GlobalSettings.LOGIN_ADMIN, adminDB);
		
		return "redirect:/manager/admin/toMainUI";
	}
	
}
