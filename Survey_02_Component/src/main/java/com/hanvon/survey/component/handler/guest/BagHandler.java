package com.hanvon.survey.component.handler.guest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanvon.survey.component.service.i.BagService;
import com.hanvon.survey.component.service.i.SurveyService;
import com.hanvon.survey.e.BagOrderDuplicateException;
import com.hanvon.survey.e.DeleteBagException;
import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.page.Page;
import com.hanvon.survey.utils.GlobalSettings;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class BagHandler {

	@Autowired
	private BagService bagService ;
	
	@Autowired
	private SurveyService surveyService ;
	
	@RequestMapping("/guest/bag/updateRelationshipCopy/{bagId}/{surveyId}")
	public String updateRelationshipCopy(@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId){
		
		bagService.updateRelationshipCopyDeeply(bagId,surveyId);
		
		return "redirect:/guest/survey/surveyDesign/"+surveyId; //复制包裹后，回到目标调查中，可以看到多了一个新包裹
	}
	
	@RequestMapping("/guest/bag/updateRelationshipMove/{bagId}/{surveyId}")
	public String updateRelationshipMove(
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId){
		
		bagService.updateRelationshipMove(bagId,surveyId);
		
		return "redirect:/guest/survey/surveyDesign/"+surveyId;
	}
	
	
	@RequestMapping("/guest/bag/showMoveAndCopySurveyList/{bagId}/{surveyId}")
	public String showMoveAndCopySurveyList(
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId,
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			HttpSession session,Map map){
		
		User user = (User)session.getAttribute(GlobalSettings.LOGIN_USER);
		Integer userId = user.getUserId();
		
		Page<Survey> unCompletedSurveyList = surveyService.queryMyUnCompletedSurvey(pageNoStr, userId);

		map.put(GlobalSettings.PAGE, unCompletedSurveyList);
		
		return "guest/bag_move_copy_list";
	}
	
	
	
	@RequestMapping("/guest/bag/doAdjust")
	public String doAdjust(
				HttpServletRequest request,
				@RequestParam("surveyId") Integer surveyId ,
				@RequestParam("bagIdList") List<Integer> bagIdList,
				@RequestParam("bagOrderList") List<Integer> bagOrderList){
		
		/*for (int i = 0; i < bagIdList.size(); i++) {
			System.out.println(bagIdList.get(i) + " - " + bagOrderList.get(i));
		}*/
		
		Set<Integer> bagOrderSet = new HashSet<Integer>(bagOrderList);
		
		//两个集合长度不相等，说明集合有重复数值；抛一个异常
		if(bagOrderSet.size() != bagOrderList.size()){
			
			List<Bag> bagList =  bagService.queryBagBySurveyId(surveyId);			
			//map.put("bagList", bagList); //不能使用map传递；
			request.setAttribute("bagList", bagList);
			
			request.setAttribute("surveyId", surveyId); //出现400异常
			
			throw new BagOrderDuplicateException(GlobalSettings.BAG_ORDER_DUPLICATE);
		}
		
		bagService.updateBatchBagOrder(bagIdList,bagOrderList);
		
		return "redirect:/guest/survey/surveyDesign/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/toAdjustUI/{surveyId}")
	public String toAdjustUI(
			@PathVariable("surveyId") Integer surveyId,Map map){
		
		//只需要查询当前调查下的所有包裹；不需要对包裹进行深度加载。
		List<Bag> bagList =  bagService.queryBagBySurveyId(surveyId);
		
		map.put("bagList", bagList);
		
		return "guest/bag_adjust";
	}
	
	@RequestMapping("/guest/bag/deleteDeeplyBag/{bagId}/{surveyId}")
	public String deleteDeeplyBag(
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId
			){
		
		bagService.deleteDeeplyBag(bagId);
		
		return "redirect:/guest/survey/surveyDesign/"+surveyId;
				
	}
	
	@RequestMapping("/guest/bag/updateBag")
	public String updateBag(Bag bag){
		bagService.updateBag(bag);
		return "redirect:/guest/survey/surveyDesign/"+bag.getSurveyId();
	}
	
	
	@RequestMapping("/guest/bag/toEditBag/{bagId}")
	public String toEditUI(
			@PathVariable("bagId") Integer bagId,
			Map<String,Object> map ){
		Bag bag = bagService.getBag(bagId);
		map.put("bag",bag);
		return "guest/bag_edit";
	}
	
	
	
	
	
	@RequestMapping("/guest/bag/deleteBag/{bagId}/{surveyId}")
	public String deleteBag(
				@PathVariable("bagId") Integer bagId,
				@PathVariable("surveyId") Integer surveyId
				){
		
		try {
			bagService.deleteBag(bagId);
		} catch (Exception e) {
			e.printStackTrace();
			
			Throwable cause = e.getCause();
			
			if(cause!=null &&  cause instanceof MySQLIntegrityConstraintViolationException){
				throw new DeleteBagException(GlobalSettings.DELETE_BAG);
			}
			
			throw e ;
		}
		
		return "redirect:/guest/survey/surveyDesign/"+surveyId;
	}
	
	
	@RequestMapping("/guest/bag/saveBag")
	public String saveBag(Bag bag){
		
		bagService.saveBag(bag);
		
		return "redirect:/guest/survey/surveyDesign/"+bag.getSurveyId();
	}
	
	
	@RequestMapping("/guest/bag/toBagUI/{surveyId}")
	public String toBagUI(@PathVariable("surveyId") Integer surveyId ){
		return "guest/bag_add";
	}
	
}
