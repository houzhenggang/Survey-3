package com.hanvon.survey.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hanvon.survey.component.service.i.ResService;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.entities.manager.Admin;
import com.hanvon.survey.entities.manager.Res;
import com.hanvon.survey.utils.DataProcessUtils;
import com.hanvon.survey.utils.GlobalSettings;

/**
 * 细粒度权限控制：控制指定servlet路径的资源是否有权限，如果有权限则显示连接，否不显示连接
 * 	<hanvon:auth servletPath="manager/statistics/showStatisticsList">
		[<a href="manager/statistics/showStatisticsList">统计调查</a>]
	</hanvon:auth>
 */
public class AuthTag extends SimpleTagSupport{

	private String servletPath ;
	
	//服务器启动时，监听器已经创建IOC容器了，那标签库类就不应该再创建IOC容器啦，否则IOC容器对象就不是单例的；
	//ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-context.xml");
	
	//标签库是在加载和解析jsp时，解析标签库，创建标签库类的对象，是由web容器来创建；无法使用@Autowired注入service
	//@Autowired
	//private ResService resService ;
	
	@SuppressWarnings("null")
	@Override
	public void doTag() throws JspException, IOException {
	
		//验证连接到底是否显示	
		
		//准备
		PageContext pageContext = (PageContext) getJspContext() ;
		HttpSession session = pageContext.getSession();
		ServletContext application = pageContext.getServletContext() ;
		
		//ApplicationContext ioc = (ApplicationContext)application.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application);
		
		//ResService resService = new ResServiceImpl() ;	//Service对象不能自己创建，mapper无法自动注入	
		ResService resService = ioc.getBean(ResService.class) ;//可以从IOC容器中获取
		
		Res res = resService.getResByServletPath(servletPath);
		
		//验证是否为公共资源
		Boolean publicStatus = res.getPublicStatus();
		if(publicStatus){
			//可以在调用invoke方法时，传StringWriter字符输出流，框架会将标签体内容写入到输出流中，在标签类方法中就可以获取到标签体的内容；
			//我们这个标签无需使用到标签体的内容，直接传递null就可以了
			getJspBody().invoke(null);
			return ;
		}
		
		//验证servletPath是否以/guest开头
		if(servletPath.startsWith("/guest")){
			
			User user = (User) session.getAttribute(GlobalSettings.LOGIN_USER);
			
			if(user!=null){
				
				String codeArrStr = user.getCodeArrStr();
				
				boolean hasAuth = DataProcessUtils.checkedAuthority(codeArrStr, res);
				if(hasAuth){
					getJspBody().invoke(null);
					return ;					
				}
				
			}			
		}
		
		
		//验证servletPath是否以/manager开头
		if(servletPath.startsWith("/manager")){
			Admin admin = (Admin) session.getAttribute(GlobalSettings.LOGIN_ADMIN);
			
			if("SuperAdmin".equals(admin.getAdminName())){
				getJspBody().invoke(null);
				return ;	
			}
						
			if(admin!=null){
				
				String codeArrStr = admin.getCodeArrStr();
				
				boolean hasAuth = DataProcessUtils.checkedAuthority(codeArrStr, res);
				if(hasAuth){
					getJspBody().invoke(null);
					return ;					
				}
				
			}	
		}		
		
	}

	public void setServletPath(String servletPath) {
		if(servletPath.startsWith("/")){
			this.servletPath = servletPath;
		}else{
			this.servletPath = "/"+servletPath;
		}		
	}
	
	
	
}
