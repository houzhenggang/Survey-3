package com.hanvon.survey.component.service.i;

import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.page.Page;

public interface SurveyService {

	void saveSurvey(Survey survey);

	Page<Survey> queryMyUnCompletedSurvey(String pageNoStr, Integer userId);

	void deleteSurvey(Integer surveyId);

	Survey getSurvey(Integer surveyId);

	void updateSurvey(Survey survey);

	Survey getSurveyDeeply(Integer surveyId);

	void deleteDeeplySurvey(Integer surveyId);

	void updateSurveyCompleted(Integer surveyId);

	public Page<Survey> getSurveyList(Integer userId,boolean completed,String pageNoStr);
}
