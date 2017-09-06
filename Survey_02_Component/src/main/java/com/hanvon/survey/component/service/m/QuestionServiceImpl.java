package com.hanvon.survey.component.service.m;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanvon.survey.component.dao.i.QuestionMapper;
import com.hanvon.survey.component.service.i.QuestionService;
import com.hanvon.survey.entities.guest.Question;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionMapper questionMapper ;

	@Override
	public void saveQuestion(Question question) {
		questionMapper.insert(question);
	}

	@Override
	public void deleteQuestion(Integer questionId) {
		questionMapper.deleteByPrimaryKey(questionId);
	}

	@Override
	public Question getQuestion(Integer questionId) {
		return questionMapper.selectByPrimaryKey(questionId);
	}

	@Override
	public void updateQuestion(Question question) {
		questionMapper.updateByPrimaryKey(question);
	}
	
}
