package com.hanvon.survey.component.handler.guest;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hanvon.survey.component.service.i.QuestionService;
import com.hanvon.survey.entities.guest.Question;

@Controller
public class QuestionHandler {

	@Autowired
	private QuestionService questionService ;
	
	@RequestMapping("/guest/question/updateQuestion")
	public String updateQuestion(Question question,
					@RequestParam("surveyId") Integer surveyId){
		
		questionService.updateQuestion(question);
		
		return "redirect:/guest/survey/surveyDesign/"+surveyId;
	}
	
	
	@RequestMapping("/guest/question/toUpdateUI/{questionId}/{surveyId}")
	public String toUpdateUI(
			@PathVariable("questionId") Integer questionId,
			@PathVariable("surveyId") Integer surveyId,
			Map map){
		
		
		Map<String,String> questionTypeMap = new LinkedHashMap<String,String>();
		questionTypeMap.put("0", "单选题");
		questionTypeMap.put("1", "多选题");
		questionTypeMap.put("2", "简答题");
		
		map.put("questionTypeMap", questionTypeMap);
		
		Question question = questionService.getQuestion(questionId);
		map.put("question", question);
		
		return "guest/question_edit";
	}
	
	
	@RequestMapping("guest/question/deleteQuestion/{questionId}/{surveyId}")
	public String deleteQuestion(@PathVariable("questionId") Integer questionId,
			@PathVariable("surveyId") Integer surveyId){
		
		questionService.deleteQuestion(questionId);
		
		return  "redirect:/guest/survey/surveyDesign/"+surveyId;
	}
	
	
	@RequestMapping("guest/question/saveQuestion")
	public String saveQuestion(Question question,@RequestParam("surveyId") Integer surveyId){
		
		questionService.saveQuestion(question);
		
		return "redirect:/guest/survey/surveyDesign/"+surveyId;
	}
	
	
	@RequestMapping("guest/question/toAddQuestion/{bagId}/{surveyId}")
	public String toAddUI(Map map,
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId
			){
		
		Map<String,String> questionTypeMap = new LinkedHashMap<String,String>();
		questionTypeMap.put("0", "单选题");
		questionTypeMap.put("1", "多选题");
		questionTypeMap.put("2", "简答题");
		
		map.put("questionTypeMap", questionTypeMap);
		
		map.put("command", new Question());
		
		return "guest/question_add";
	}
	
}
