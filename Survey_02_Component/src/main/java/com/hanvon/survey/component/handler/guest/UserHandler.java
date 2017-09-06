package com.hanvon.survey.component.handler.guest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hanvon.survey.component.service.i.UserService;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.utils.GlobalSettings;

@Controller
public class UserHandler {

	@Autowired
	private UserService userService ;

	Logger  logger = Logger.getLogger(this.getClass().getName());
	
	public UserHandler(){
		System.out.println("************UserHandler********************");
	}

	/**
	 * Created by MJF
	 *
	 * @Description:登出,跳转登录页面
	 */
	@RequestMapping("guest/user/logout")
	public String logout(HttpSession session){
		//销毁session
		session.invalidate();

		return "redirect:/index.jsp";
	}

	@RequestMapping(value="/guest/user/login",method=RequestMethod.POST)
	public String login(User user,HttpSession session,HttpServletRequest request){
		
		User loginUser = userService.login(user);
		
		session.setAttribute(GlobalSettings.LOGIN_USER, loginUser);
		
		logger.debug("有人正在登录系统："+user.getUserName() + " time="+new Date() + " ip="+request.getRemoteHost());
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/user/regist")
	public String regist(User user) throws Exception {

		//保存用户信息
		boolean flag = userService.regist(user);
		
		if(flag){
			return "redirect:/index.jsp";
		}
	
		return "guest/user_regist";
	}
	
}
