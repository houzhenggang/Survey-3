package com.hanvon.survey.interceptors;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.hanvon.survey.e.AdminOperationForbiddenException;
import com.hanvon.survey.e.UserOperationForbiddenException;
import com.hanvon.survey.utils.GlobalSettings;

/**
 * 作用：
 * 		验证哪些资源必须登录后才允许访问。
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		/**
		 * 拦截器都对那些资源进行拦截：
		 * 	①对mvc-controller路径
		 *  ②对Handler类中方法(@RequestMapper("路径"))
		 *  ③对静态资源访问
		 *  	org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler
		 *  	由SpringMVC框架提供的默认处理器进行处理的。
		 */	
		
		//对静态资源访问需要放行
		if(handler instanceof DefaultServletHttpRequestHandler){
			System.out.println("正在访问静态资源:"+request.getRequestURL());
			return true ;
		}
		
		//对公共资源访问需要放行
		Set<String> publicSet = new HashSet<String>();
		publicSet.add("/guest/user/toLoginUI");
		publicSet.add("/guest/user/toRegistUI");
		publicSet.add("/guest/user/login");
		publicSet.add("/guest/user/regist");
		//正常来讲，注销功能属于非公共资源，必须登录后才能访问资源；但是，有时由于session失效，用户点了注销，系统给予提示：操作是不允许的，请先登录！
		publicSet.add("/guest/user/logout"); 
		
		
		
		publicSet.add("/manager/admin/toMainUI");
		publicSet.add("/manager/admin/toLoginUI");
		publicSet.add("/manager/admin/login");
		publicSet.add("/manager/admin/logout");
		
		//获取请求路径
		//      /Survey_01_UI/guest/user/toRegistUI
		//String contextPath = request.getContextPath();
		//int contextPathLength = contextPath.length();
		// TODO 使用request.getServletPath();可以直接获取公共路径
		/*String uri = request.getRequestURI(); 		
		uri = uri.substring(contextPathLength);*/
		
		String servletPath = request.getServletPath();
		
		//判断请求路径是否为公共资源
		if(publicSet.contains(servletPath)){
			//System.out.println("正在访问公共资源："+request.getRequestURL());
			return true ;
		}
		
		
		//检查用户是否登录，登录就具有访问权限
		HttpSession session = request.getSession();
		
		if(session!=null){			
		
			if(servletPath.startsWith("/guest")){			
				if(session.getAttribute(GlobalSettings.LOGIN_USER)==null){
					throw new UserOperationForbiddenException(GlobalSettings.USER_OPERATION_FORBIDDEN);
				}
				return true ;
			}			
			
			
			if(servletPath.startsWith("/manager")){				
				if(session.getAttribute(GlobalSettings.LOGIN_ADMIN)==null){
					throw new AdminOperationForbiddenException(GlobalSettings.ADMIN_OPERATION_FORBIDDEN);
				}
				return true ;
			}
		}

		return true;
	}
	
}
