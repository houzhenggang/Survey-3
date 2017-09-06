package com.hanvon.survey.component.handler.guest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanvon.survey.component.service.i.EngageService;
import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.page.Page;
import com.hanvon.survey.utils.GlobalSettings;

@Controller
public class EngageHandler {

	@Autowired
	private EngageService engageService ;
	
	@RequestMapping("/guest/engage/engage")
	public String engage(
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="currentIndex") Integer currentIndex,
			@RequestParam("bagId") Integer bagId){

		
		//===合并答案===========================
		//从session域中获取allBagMap集合
		Map<Integer,Map<String,String[]>> allBagMap = (Map<Integer,Map<String,String[]>>)session.getAttribute(GlobalSettings.SESSION_NAME_ALL_BAG_MAP);
		
		//获取当前包裹:bagId
		//@RequestParam("bagId") Integer bagId
		
		//获取当前表单参数==》当前包裹答案
		//获取所有的请求参数
		Map<String,String[]> parameterMap = request.getParameterMap();
		System.out.println("====>>>>>>>>parameterMap="+parameterMap.hashCode());
				
		//避免存储数据有问题。
		Map<String,String[]> newParameterMap = new HashMap(parameterMap); //使用自己创建map集合来接收请求过来参数
		
		//合并答案
		//allBagMap.put(bagId, parameterMap);
		allBagMap.put(bagId, newParameterMap);
		
		//将allBagMap集合信息打印：观察浏览器提交的数据，和我们接受到的数据是否一致？
		Set<Entry<Integer, Map<String, String[]>>> entrySet = allBagMap.entrySet();
		for (Entry<Integer, Map<String, String[]>> entry : entrySet) {
			Integer paramBagId = entry.getKey();
			System.out.println("paramBagId="+paramBagId);
			
			Map<String, String[]> paramEntry = entry.getValue();
			Set<Entry<String, String[]>> entrySet2 = paramEntry.entrySet();
			for (Entry<String, String[]> entry2 : entrySet2) {
				String paramName = entry2.getKey();//q54
				String[] paramValues = entry2.getValue(); // String[] paramValues = [] ;
				
				System.out.println(paramName + " = " + Arrays.asList(paramValues));
			}
			
		}		
		
		if(parameterMap.containsKey("submit_prev")){
				
			//根据当前包裹索引，计算上一个包裹的索引
			int prevIndex = currentIndex - 1 ;
			
			//根据上一个包裹的索引，获取上一个包裹
			List<Bag> bagList = (List<Bag>)session.getAttribute(GlobalSettings.SESSION_NAME_BAG_LIST);
			Bag prevBag = bagList.get(prevIndex);
			
			//将上一个包裹存放到request域中
			request.setAttribute(GlobalSettings.REQUEST_NAME_CURRENT_BAG, prevBag);
			
			//将上一个索引(当前索引)存放到request域中
			request.setAttribute(GlobalSettings.REQUEST_NAME_CURRENT_INDEX, prevIndex);
			
			//跳转到下一个包裹参与调查页面
			return "guest/engage_engage";
		}
		
		if(parameterMap.containsKey("submit_next")){
			
			//根据当前包裹索引，计算下一个包裹的索引
			int nextIndex = currentIndex + 1 ;
			
			//根据下一个包裹的索引，获取下一个包裹
			List<Bag> bagList = (List<Bag>)session.getAttribute(GlobalSettings.SESSION_NAME_BAG_LIST);
			Bag nextBag = bagList.get(nextIndex);
			
			//将下一个包裹存放到request域中
			request.setAttribute(GlobalSettings.REQUEST_NAME_CURRENT_BAG, nextBag);
			
			//将下一个索引(当前索引)存放到request域中
			request.setAttribute(GlobalSettings.REQUEST_NAME_CURRENT_INDEX, nextIndex);
			
			//跳转到下一个包裹参与调查页面
			return "guest/engage_engage";
		}
		
		if(parameterMap.containsKey("submit_quit")){
			
			//就是清理所有之前保存过数据
			session.removeAttribute(GlobalSettings.SESSION_NAME_SURVEY);
			session.removeAttribute(GlobalSettings.SESSION_NAME_BAG_LIST);
			session.removeAttribute(GlobalSettings.SESSION_NAME_LASTINDEX);
			session.removeAttribute(GlobalSettings.SESSION_NAME_ALL_BAG_MAP);	
			
			return "redirect:/guest/engage/showAllAvailableSurvey";
		}
		
		if(parameterMap.containsKey("submit_done")){
			//解析答案和保存答案
			Map<Integer,Map<String,String[]>> allBagMapForAnswer = 
					(Map<Integer,Map<String,String[]>>)session.getAttribute(GlobalSettings.SESSION_NAME_ALL_BAG_MAP);
			
			Survey survey = (Survey)session.getAttribute(GlobalSettings.SESSION_NAME_SURVEY);
			
			engageService.saveAnswer(allBagMapForAnswer,survey);
			
			//就是清理所有之前保存过数据
			session.removeAttribute(GlobalSettings.SESSION_NAME_SURVEY);
			session.removeAttribute(GlobalSettings.SESSION_NAME_BAG_LIST);
			session.removeAttribute(GlobalSettings.SESSION_NAME_LASTINDEX);
			session.removeAttribute(GlobalSettings.SESSION_NAME_ALL_BAG_MAP);	
			
			return "redirect:/guest/engage/showAllAvailableSurvey";
		}
		
		return "redirect:/index.jsp";
	}
	
	
	@RequestMapping("/guest/engage/entry/{surveyId}")
	public String entry(
				@PathVariable("surveyId") Integer surveyId,
				HttpSession session,
				HttpServletRequest request){

		//深度加载survey对象
		Survey survey = engageService.getSurveyDeeply(surveyId);
		
		// 将survey对象存放到session域
		session.setAttribute(GlobalSettings.SESSION_NAME_SURVEY, survey);
		
		//从survey对象中获取bagSet集合
		Set<Bag> bagSet = survey.getBagSet();
		
		// 将bagSet集合转换为bagList
		List<Bag> bagList = new ArrayList(bagSet);
		
		//将bagList集合存放到session域中
		session.setAttribute(GlobalSettings.SESSION_NAME_BAG_LIST, bagList);
		
		// 获取bagList.size()=>lastIndex
		int size = bagList.size();
		int lastIndex = size -1 ;
		
		//将lastIndex存放到session域中
		session.setAttribute(GlobalSettings.SESSION_NAME_LASTINDEX, lastIndex);
		
		// 创建allBagMap集合,为了接收表单数据
		Map<Integer,Map<String,String[]>> allBagMap = new HashMap<Integer,Map<String,String[]>>();
		
		//将allBagMap存放到session域中
		session.setAttribute(GlobalSettings.SESSION_NAME_ALL_BAG_MAP, allBagMap);
		
		//准备需要展示数据的第一个包裹信息
		//获取当前索引currentIndex=0;
		int currentIndex = 0;
		
		//获取第一个currentBag包裹对象bagList.get(currentIndex);
		Bag currentBag = bagList.get(0);
		
		//将currentBag对象存放到request域中
		request.setAttribute(GlobalSettings.REQUEST_NAME_CURRENT_BAG, currentBag);
		
		//当前索引currentIndex存放到request域中
		request.setAttribute(GlobalSettings.REQUEST_NAME_CURRENT_INDEX,currentIndex);
		
		return "/guest/engage_engage";
	}
	
	
	@RequestMapping("/guest/engage/showAllAvailableSurvey")
	public String showAllAvailableSurvey(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,Map map){
		
		Page<Survey> page=  engageService.queryAllAvailableSurvey(pageNoStr);
		map.put(GlobalSettings.PAGE, page);
		
		return "/guest/engage_available";
	}
	
}
