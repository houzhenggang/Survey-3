package com.hanvon.survey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.hanvon.survey.component.service.i.ResService;
import com.hanvon.survey.e.AdminLoginNeededException;
import com.hanvon.survey.e.HasNoAuthorityException;
import com.hanvon.survey.e.UserLoginNeededException;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.entities.manager.Admin;
import com.hanvon.survey.entities.manager.Res;
import com.hanvon.survey.utils.DataProcessUtils;
import com.hanvon.survey.utils.GlobalSettings;

//代理登录拦截器
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ResService resService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//静态资源放过
		if(handler instanceof DefaultServletHttpRequestHandler){
			return true ;
		}
	

		//获取Session对象
		HttpSession session = request.getSession();
		//获取ServletPath
		String servletPath = request.getServletPath();
		//将ServletPath中的多余部分剪掉
		servletPath = DataProcessUtils.cutServletPath(servletPath);
		//根据ServletPath获取对应的Res对象
		Res res = resService.getResByServletPath(servletPath);
		//检查是否是公共资源
		Boolean publicStatus = res.getPublicStatus();
		if(publicStatus){
			//如果是公共资源则放行
			return true ;
		}
		
		//如果当前请求的目标地址是User部分的：/guest
		if(servletPath.startsWith("/guest")){
			//检查User是否登录
			User user = (User)session.getAttribute(GlobalSettings.LOGIN_USER);
			//如果没有登录则抛出自定义异常：UserLoginNeededException
			if(user==null){
				throw new UserLoginNeededException(GlobalSettings.USER_LOGIN_NEEDED);
			}
			//需要验证企业用户还是个人用户，验证用户权限码是否对资源有权限；
			//已登录则检查权限
			String codeArrStr = user.getCodeArrStr();
			
			boolean hasAuth = DataProcessUtils.checkedAuthority(codeArrStr, res);
			if(hasAuth){
				//有权限则放行
				return true ;	
			}else{
				//没有权限则抛出自定义异常：HasNoRightException
				throw new HasNoAuthorityException(GlobalSettings.HAS_NO_AUTHORITY);
			}
		}
		
		
		//如果当前请求的目标地址是Admin部分的：/manager
		if(servletPath.startsWith("/manager")){
			//检查Admin是否登录
			Admin admin = (Admin)session.getAttribute(GlobalSettings.LOGIN_ADMIN);
			if(admin==null){
				//如果没有登录则抛出自定义异常：AdminLoginNeededException
				throw new AdminLoginNeededException(GlobalSettings.ADMIN_LOGIN_NEEDED);
			}
			
			//如果已登录则检查是否是超级管理员
			if("SuperAdmin".equals(admin.getAdminName())){
				return true ;
			}
			
			//如果不是超级管理员，则检查是否具备访问目标资源的权限：checkedAuthority(codeArr,res)
			String codeArrStr = admin.getCodeArrStr();
			boolean hasAuth = DataProcessUtils.checkedAuthority(codeArrStr,res);
			
			if(hasAuth){
				//有权限则放行
				return true ;
			}else{
				//没有权限则抛出自定义异常：HasNoAuthorityException
				throw new HasNoAuthorityException(GlobalSettings.HAS_NO_AUTHORITY);
			}
		}
		
		return true;
	}
	
}
