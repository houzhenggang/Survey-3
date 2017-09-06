package com.hanvon.survey.component.service.i;

import java.util.Map;

import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.page.Page;

public interface EngageService {
	Page<Survey> queryAllAvailableSurvey(String pageNoStr);

	Survey getSurveyDeeply(Integer surveyId);

	void saveAnswer(Map<Integer, Map<String, String[]>> allBagMapForAnswer, Survey survey);

}
