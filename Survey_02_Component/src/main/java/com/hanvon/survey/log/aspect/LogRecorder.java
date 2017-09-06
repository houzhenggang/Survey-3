package com.hanvon.survey.log.aspect;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanvon.survey.component.service.i.LogService;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.entities.manager.Admin;
import com.hanvon.survey.entities.manager.Log;
import com.hanvon.survey.log.thread.local.RoutingKeyBinder;
import com.hanvon.survey.log.thread.local.WebBinder;
import com.hanvon.survey.utils.GlobalSettings;

/**
 * 切面类：用于记录系统操作日志
 * @author zhangyu
 */
//@Aspect 
public class LogRecorder {
	
	@Autowired
	private LogService logService ;

	//环绕通知
	public Object logRecord(ProceedingJoinPoint joinPoint) throws Throwable{
		//收集常用信息
		Object[] args = joinPoint.getArgs();
		Object resultObject = null ;
		
		String operator = null ;
		String operateTime = null ;
		
		String methodName = null; 
		String typeName = null;
		
		String methodArgs = null;
		String methodReturnValue = null ;
		
		String exceptionType = null;
		String exceptionMessage = null ;

		try {
			operateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			methodName = joinPoint.getSignature().getName();
			typeName = joinPoint.getSignature().getDeclaringTypeName();
			methodArgs = Arrays.asList(args).toString();
			
			//执行目标方法，将目标方法的所有参数，以Object[]数组形式传递
			resultObject = joinPoint.proceed(args); //调用目标方法
			methodReturnValue = (resultObject==null)?null:resultObject.toString();
		} catch (Throwable e) {
			e.printStackTrace();
			
			//收集异常日志信息
			Throwable cause = e.getCause();
			while(cause!=null){
				exceptionType = cause.getClass().getName();
				exceptionMessage = cause.getMessage();
				
				//获取原因的原因
				cause = cause.getCause();
				//cause = e.getCause(); //错误的
			}			
			
			//通知方法在捕获到异常后，一般需要抛出；原因：需要让外层切面类通知可以获取到相关异常；
			throw e ;
		} finally{			
			
			//要获取用户信息,如果登录用户，可以从session中获取用户对象，获取用户对象用户名称；
			//session从何处获取？从request中获取
			//request从何处获取 ？
			
			HttpServletRequest request = WebBinder.getBinderRequest();
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(GlobalSettings.LOGIN_USER);
			Admin admin = (Admin)session.getAttribute(GlobalSettings.LOGIN_ADMIN);
			
			String userPart = (user==null)?"User未登录":user.getUserName();
			String adminPart = (admin==null)?"Admin未登录":admin.getAdminName();
			operator = userPart + " / " + adminPart ;
			
			Log log = new Log(null, operator, operateTime, methodName, typeName, methodArgs, methodReturnValue, exceptionType, exceptionMessage);
			
			RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
			
			logService.saveLog(log);
		}
		
		//通知方法一定将目标方法结果返回；因为:需要让外层切面类通知也可以获取到目标方法的返回结果；
		return resultObject ;
	}
	
}
