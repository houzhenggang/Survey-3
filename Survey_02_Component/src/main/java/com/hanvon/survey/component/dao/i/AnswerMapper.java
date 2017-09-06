package com.hanvon.survey.component.dao.i;

import com.hanvon.survey.entities.guest.Answer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AnswerMapper {
    int deleteByPrimaryKey(Integer answerId);

    int insert(Answer record);

    Answer selectByPrimaryKey(Integer answerId);

    List<Answer> selectAll();

    int updateByPrimaryKey(Answer record);

	void insertBatchAnswer(@Param("answerList") List<Answer> answerList);
	
	
	List<String> queryTextStatistics(Integer questionId);

	
	int getQuestionEngagedCount(Integer questionId);

	int getOptionEngagedCount(@Param("paramIndex") String paramIndex,@Param("questionId") Integer questionId);

	int getSurveyEngageCount(Integer surveyId);

	List<Answer> getAllAnswerBySurveyId(Integer surveyId);

}