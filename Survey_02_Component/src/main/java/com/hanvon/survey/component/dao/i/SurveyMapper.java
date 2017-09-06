package com.hanvon.survey.component.dao.i;

import com.hanvon.survey.entities.guest.Survey;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SurveyMapper {
    int deleteByPrimaryKey(Integer surveyId);

    int insert(Survey record);

    Survey selectByPrimaryKey(Integer surveyId);

    List<Survey> selectAll();

    int updateByPrimaryKey(Survey record);

	int countSurvey(@Param("userId") Integer userId,@Param("isCompleted") boolean isCompleted);

	List<Survey> querySurveyList(
			@Param("userId") Integer userId,
			@Param("isCompleted") boolean isCompleted, 
			@Param("index") int index, 
			@Param("pageSize") int pageSize);
	
	Survey getSurveyDeeply(Integer surveyId);

	//深度删除
	void deleteAllQuestionBySurveyId(Integer surveyId);
	void deleteAllBagBySurveyId(Integer surveyId);

	void updateSurveyStatus(Integer surveyId);

	
	
}