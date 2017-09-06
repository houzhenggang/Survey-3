package com.hanvon.survey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.hanvon.survey.component.service.i.ResService;
import com.hanvon.survey.entities.manager.Res;
import com.hanvon.survey.utils.DataProcessUtils;

//保存资源
public class ResInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ResService resService ;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//如果是静态资源则放行：DefaultServletHttpRequestHandler
		if(handler instanceof DefaultServletHttpRequestHandler){
			return true ;
		}		
		//获取ServletPath值
		String servletPath = request.getServletPath();
		//去掉后面数据部分
		servletPath = DataProcessUtils.cutServletPath(servletPath);
		//检查当前ServletPath值是否已经保存到了数据库中
		boolean exists = resService.servletPathExists(servletPath);
		
		//如果已经保存则直接放行
		if(exists){
			return true ;
		}

		//创建Res对象
		//声明两个变量，用来保存最后使用的resCode和resPos
		Integer resCode = null ;
		Integer resPos = null ;
		//声明一个变量，用来保存系统允许的最大权限码的值
		int systemAllowedMaxCode = 1073741824;  //1 << 30 ;
		
		//查询系统中现在最大的权限位数值
		Integer maxPos = resService.getMaxPos();
		
		//查询系统中在最大权限位范围内最大的权限码数值
		Integer maxCode = (maxPos==null) ? null: resService.getMaxCode(maxPos);
		
		//判断最大权限码或权限位是否存在，如果不存在，说明现在是第一次保存
		if(maxCode==null){	
			//如果不存在，则将最终resCode和resPos设置为初始值
			resCode = 1 ;
			resPos = 0 ;
		}else if(maxCode!=null){
			//如果存在，则检查权限码是否达到了最大值，如果达到了最大值；权限位在最大权限位的基础上+1；权限码设置为1
			if(maxCode == systemAllowedMaxCode){
				resCode = 1 ;
				resPos = maxPos + 1 ;
			}else if (maxCode < systemAllowedMaxCode ){				
				//⑧如果没有达到最大值，则权限码左移1位，权限位沿用最大值	
				resCode = maxCode << 1;
				resPos =  maxPos ;
			}
		}

		//保存资源
		boolean publicStatus = false;
		Res res = new Res(null, servletPath, publicStatus, resCode, resPos);
		resService.saveRes(res);
		
		return true;
	}
	
}
