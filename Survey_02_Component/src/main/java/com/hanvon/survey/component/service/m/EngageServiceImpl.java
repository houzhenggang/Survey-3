package com.hanvon.survey.component.service.m;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanvon.survey.component.dao.i.AnswerMapper;
import com.hanvon.survey.component.dao.i.SurveyMapper;
import com.hanvon.survey.component.service.i.EngageService;
import com.hanvon.survey.component.service.i.SurveyService;
import com.hanvon.survey.entities.guest.Answer;
import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.page.Page;

@Service
public class EngageServiceImpl implements EngageService {

	@Autowired
	private SurveyMapper surveyMapper ;
	
	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private AnswerMapper answerMapper ;
	
	
	//改进之前进行分页查询的方法，多个业务方法，可以共享同一个私有方法的代码
	@Override
	public Page<Survey> queryAllAvailableSurvey(String pageNoStr) {		
		return surveyService.getSurveyList(null, true, pageNoStr);		
	}

	@Override
	public Survey getSurveyDeeply(Integer surveyId) {
		return surveyMapper.getSurveyDeeply(surveyId);
	}

	@Override
	public void saveAnswer(Map<Integer, Map<String, String[]>> allBagMapForAnswer, Survey survey) {
		//一.解析答案
		List<Answer> answerList = new ArrayList<Answer>();
		
		Integer surveyId = survey.getSurveyId();
		
		Collection<Map<String, String[]>> values = allBagMapForAnswer.values();
		
		String uuid = UUID.randomUUID().toString();
		
		for (Map<String, String[]> paramMap : values) {
			Set<Entry<String, String[]>> paramEntries = paramMap.entrySet();
			for (Entry<String, String[]> paramEntry : paramEntries) {
				String paramName = paramEntry.getKey(); //q40
				if(!paramName.startsWith("q")){
					continue ;
				}
				Integer questionId = Integer.parseInt(paramName.substring(1));
				
				String[] paramValues = paramEntry.getValue(); //[1,2,4]
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < paramValues.length; i++) {
					builder.append(",").append(paramValues[i]);
				}
				String answerContent = builder.substring(1) ;
				Answer answer = new Answer(null,answerContent,uuid,questionId,surveyId);
				answerList.add(answer);
			}
		}
		
		//二.保存答案
		answerMapper.insertBatchAnswer(answerList);
		
	}
	
}
