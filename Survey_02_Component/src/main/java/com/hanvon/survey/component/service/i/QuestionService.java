package com.hanvon.survey.component.service.i;

import com.hanvon.survey.entities.guest.Question;

public interface QuestionService {

	void saveQuestion(Question question);

	void deleteQuestion(Integer questionId);

	Question getQuestion(Integer questionId);

	void updateQuestion(Question question);

}
