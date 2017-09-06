package com.hanvon.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanvon.survey.component.dao.i.SurveyMapper;
import com.hanvon.survey.component.service.i.SurveyService;
import com.hanvon.survey.e.BagEmptyException;
import com.hanvon.survey.e.QuestionEmptyException;
import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Question;
import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.page.Page;
import com.hanvon.survey.utils.GlobalSettings;

@Service
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private SurveyMapper surveyMapper ;

	@Override
	public void saveSurvey(Survey survey) {
		surveyMapper.insert(survey);
	}

	@Override
	public Page<Survey> queryMyUnCompletedSurvey(String pageNoStr, Integer userId) {		
		return getSurveyList(userId, false, pageNoStr);
	}
	
	public Page<Survey> getSurveyList(Integer userId,boolean completed,String pageNoStr){
		//获取总记录数
		int totalRecordSizeParam = surveyMapper.countSurvey(userId,completed);		
		
		//创建Page对象
		Page<Survey> page = new Page(pageNoStr,totalRecordSizeParam) ;
		
		//获取计算好的开始索引
		int index = page.getIndex() ;
		
		//获取pageSize
		int pageSize = page.getPageSize();
		
		//查询分页集合
		List<Survey> dataList = surveyMapper.querySurveyList(userId,completed,index,pageSize);
		
		//将分页集合封装到page对象中
		page.setDataList(dataList);
		
		//将分页对象返回
		return page;
	}

	@Override
	public void deleteSurvey(Integer surveyId) {
		surveyMapper.deleteByPrimaryKey(surveyId);
	}

	@Override
	public Survey getSurvey(Integer surveyId) {		
		return surveyMapper.selectByPrimaryKey(surveyId);
	}

	@Override
	public void updateSurvey(Survey survey) {
		surveyMapper.updateByPrimaryKey(survey);
	}

	@Override
	public Survey getSurveyDeeply(Integer surveyId) {
		return surveyMapper.getSurveyDeeply(surveyId);
	}

	@Override
	public void deleteDeeplySurvey(Integer surveyId) {
		//先删除所有问题
		surveyMapper.deleteAllQuestionBySurveyId(surveyId);
		
		//删除所有包裹
		surveyMapper.deleteAllBagBySurveyId(surveyId);
		
		//删除调查本身
		surveyMapper.deleteByPrimaryKey(surveyId);
	}

	@Override
	public void updateSurveyCompleted(Integer surveyId) {
		//验证调查数据合法性
		Survey survey = surveyMapper.getSurveyDeeply(surveyId);
		
		//验证调查下是否有包裹
		Set<Bag> bagSet = survey.getBagSet();
		if(bagSet==null || bagSet.size()==0){
			throw new BagEmptyException(GlobalSettings.BAG_EMPTY);
		}
		
		for (Bag bag : bagSet) {
			Set<Question> questionSet = bag.getQuestionSet();
			if(questionSet==null || questionSet.size()==0){
				throw new QuestionEmptyException(GlobalSettings.QUESTION_EMPTY);
			}
		}
		//更新调查状态，变为已完成状态
		surveyMapper.updateSurveyStatus(surveyId);
	}


}
