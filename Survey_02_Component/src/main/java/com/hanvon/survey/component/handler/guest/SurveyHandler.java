package com.hanvon.survey.component.handler.guest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.hanvon.survey.component.service.i.SurveyService;
import com.hanvon.survey.e.DeleteSurveyException;
import com.hanvon.survey.e.EditFileTooLargeException;
import com.hanvon.survey.e.EditFileTypeErrorException;
import com.hanvon.survey.e.FileTooLargeException;
import com.hanvon.survey.e.FileTypeErrorException;
import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.page.Page;
import com.hanvon.survey.utils.DataProcessUtils;
import com.hanvon.survey.utils.GlobalSettings;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class SurveyHandler {

	@Autowired
	private SurveyService surveyService;

	@RequestMapping("/guest/survey/completeSurvey/{surveyId}")
	public String complete(
			@PathVariable("surveyId") Integer surveyId){
		
		surveyService.updateSurveyCompleted(surveyId);
		
		return "redirect:/index.jsp";
	}

	@RequestMapping("/guest/survey/deleteDeeplySurvey/{surveyId}/{pageNo}")
	public String deleteDeeplySurvey(
			@PathVariable("surveyId") Integer surveyId,
			@PathVariable("pageNo") Integer pageNo){
		
		surveyService.deleteDeeplySurvey(surveyId);
		
		return "redirect:/guest/survey/showMyUnCompletedSurvey?pageNoStr="+pageNo;
	}

	@RequestMapping("/guest/survey/surveyDesign/{surveyId}")
	public String surveyDesign(@PathVariable("surveyId") Integer surveyId,
			Map<String,Object> map){
		Survey survey = surveyService.getSurveyDeeply(surveyId);
		map.put("survey", survey);
		return "guest/survey_design";
	}

	@RequestMapping("guest/survey/updateSurvey")
	public String updateSurvey(
			Survey survey,
			@RequestParam("logoFile") MultipartFile file,
			HttpServletRequest request,
			@RequestParam("pageNoStr") String pageNoStr
			) throws IOException{
			//获取上传文件MultipartFile对象是否为空：即使没有上传图片，对象并不是null
			boolean empty = file.isEmpty();
			
			if(!empty){
				
				//---------验证文件上传大小，以及文件类型---------------------------
				//获取文件大小
				long size = file.getSize();
				if(size > 102400){
					request.setAttribute("survey", survey);
					throw new EditFileTooLargeException(GlobalSettings.FILE_TOO_LARGE);
				}
				String contentType = file.getContentType();
				if(!contentType.startsWith("image")){
					request.setAttribute("survey", survey);
					throw new EditFileTypeErrorException(GlobalSettings.FILE_TYPE_ERROR);
				}				

				//虚拟路径
				String virtualPath = "/surveyLogos";
				//获取ServeltContext
				ServletContext servletContext = request.getSession().getServletContext();
				//获取真实路径
				String realPath = servletContext.getRealPath(virtualPath);
				
				//获取上传文件的输入流
				InputStream inputStream = file.getInputStream();
				
				String logoPath = DataProcessUtils.resizeImages(inputStream, realPath);
				System.out.println("logoPath = "+logoPath);
				
				survey.setLogoPath(logoPath); //设置新图片；不设置，保留原来图片
			}
		
			surveyService.updateSurvey(survey);
			
		return "redirect:/guest/survey/showMyUnCompletedSurvey?pageNoStr="+pageNoStr;
	}

	@RequestMapping("guest/survey/editSurveyUI/{surveyId}/{pageNo}")
	public String editSurveyUI(
				@PathVariable("surveyId") Integer surveyId,
				@PathVariable("pageNo") Integer pageNo, //@PathVariable修改的入参，会自动存放到request域
				Map map
				){
		Survey survey = surveyService.getSurvey(surveyId);
		
		map.put("survey",survey);
		
		return "guest/survey_edit";
	}
	
	@RequestMapping(value="guest/survey/deleteSurvey/{surveyId}/{pageNo}") //占位符名称两端不能有空格，否则，报错
	public String deleteSurvey(
				@PathVariable("surveyId") Integer surveyId,
				@PathVariable("pageNo") Integer pageNo) throws Exception {
		try {
			//com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: 
			//Cannot delete or update a parent row: a foreign key constraint fails (`survey_main_160808`.`guest_bag`, CONSTRAINT `guest_bag_ibfk_1` FOREIGN KEY (`SURVEY_ID`) REFERENCES `guest_survey` (`SURVEY_ID`))
			surveyService.deleteSurvey(surveyId);
		} catch (Exception e) {
			e.printStackTrace();
			
			Throwable cause = e.getCause();
			
			//import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ;
			if(cause instanceof MySQLIntegrityConstraintViolationException){
				throw new DeleteSurveyException(GlobalSettings.DELETE_SURVEY);
			}
			//自己处理不了异常，继续抛出；
			throw e;
		}
		return "redirect:/guest/survey/showMyUnCompletedSurvey?pageNoStr="+pageNo;
	}

	@RequestMapping("guest/survey/showMyUnCompletedSurvey")
	public String showMyUnCompletedSurvey(
				@RequestParam(value="pageNoStr",required=false) String pageNoStr,
				HttpSession session,Map map){
		//获取当前系统用户对象
		User user = (User)session.getAttribute(GlobalSettings.LOGIN_USER);
		
		//分页查询，我未完成调查
		Page<Survey> page = surveyService.queryMyUnCompletedSurvey(pageNoStr,user.getUserId());
		
		map.put(GlobalSettings.PAGE, page);
		
		return "guest/survey_uncompletedlist";
	}
	
	@RequestMapping("guest/survey/save")
	public String saveSurvey(Survey survey,
			HttpServletRequest request,
			@RequestParam("logoFile") MultipartFile file) throws IOException{
		
		//获取上传文件MultipartFile对象是否为空：即使没有上传图片，对象并不是null
		boolean empty = file.isEmpty();
		if(!empty){
			
			//---------验证文件上传大小，以及文件类型---------------------------
			//获取文件大小
			long size = file.getSize();
			if(size > 102400){
				throw new FileTooLargeException(GlobalSettings.FILE_TOO_LARGE);
			}
			
			String contentType = file.getContentType();
			if(!contentType.startsWith("image/")){
				throw new FileTypeErrorException(GlobalSettings.FILE_TYPE_ERROR);
			}

			//准备虚拟路径
			String virtualPath = "/surveyLogos";
			
			//获取ServeltContext
			ServletContext servletContext = request.getSession().getServletContext();
			
			//获取真实路径
			String realPath = servletContext.getRealPath(virtualPath);
			
			//获取上传文件的输入流
			InputStream inputStream = file.getInputStream();
			
			String logoPath = DataProcessUtils.resizeImages(inputStream, realPath);
			System.out.println("logoPath = "+logoPath);
			
			survey.setLogoPath(logoPath);
		}
		
		//从session中获取当前用户对象的userId属性值
		User user = (User)request.getSession().getAttribute(GlobalSettings.LOGIN_USER);
		
		survey.setUserId(user.getUserId()); //外键值
		
		surveyService.saveSurvey(survey);
		
		return "redirect:/guest/survey/showMyUnCompletedSurvey";
	}
	
	public String saveFile(HttpServletRequest request,@RequestParam("logoFile") MultipartFile file) throws IOException{
	
		//获取上传文件MultipartFile对象是否为空：即使没有上传图片，对象并不是null
		boolean empty = file.isEmpty();
		
		if(!empty){
			
			//准备虚拟路径
			String virtualPath = "/surveyLogos";
			
			//获取ServeltContext
			ServletContext servletContext = request.getSession().getServletContext();
			
			//获取真实路径
			String realPath = servletContext.getRealPath(virtualPath);
			
			//获取上传文件的输入流
			InputStream inputStream = file.getInputStream();
			
			String logoPath = DataProcessUtils.resizeImages(inputStream, realPath);
			System.out.println("logoPath = "+logoPath);
		}
		
		return "redirect:/index.jsp"; // TODO 应该跳转到未完成调查列表
	}

	public String realPath(HttpServletRequest request) throws IOException{
		
		//虚拟路径
		String logoPath = "/surveyLogos";
		
		System.out.println("virtualLogoPath="+logoPath);
		
		//根据虚拟路径获取真实的文件上传路径
		String realPath = request.getSession().getServletContext().getRealPath(logoPath);
		
		System.out.println("realPath="+realPath);
		
		return "redirect:/index.jsp"; // TODO 应该跳转到未完成调查列表
	}

	public String saveFile(Survey survey,@RequestParam("logoFile") MultipartFile file) throws IOException{
		
		String contentType = file.getContentType();
		System.out.println("文件上传内容类型：contentType="+contentType);
		
		long size = file.getSize();
		System.out.println("文件上传大小：size="+size);
		
		InputStream inputStream = file.getInputStream();
		System.out.println("文件输入流：inputStream="+inputStream);
		
		String name = file.getName();
		System.out.println("输入项参数名称：name="+name);
		
		String originalFilename = file.getOriginalFilename();
		System.out.println("文件原始名称：originalFilename="+originalFilename);

		return "redirect:/index.jsp"; // TODO 应该跳转到未完成调查列表
	}
	
}
