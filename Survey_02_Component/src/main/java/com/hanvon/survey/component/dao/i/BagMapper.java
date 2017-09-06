package com.hanvon.survey.component.dao.i;

import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Question;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface BagMapper {
    int deleteByPrimaryKey(Integer bagId);

    int insert(Bag record);

    Bag selectByPrimaryKey(Integer bagId);

    List<Bag> selectAll();

    int updateByPrimaryKey(Bag record);
    
    /**
     * 深度加载：在加载包裹的同时，将包裹问题也一并加载
     * @param bagId
     * @return
     */
    Bag getBagDeeply(Integer bagId);

	void deleteCurrentQuestionByBagId(Integer bagId);

	List<Bag> queryBagBySurveyId(Integer surveyId);

	void updateBatchBagOrder(@Param("bagList") List<Bag> bagList);

	void updateRelationshipMove(@Param("bagId") Integer bagId,@Param("surveyId") Integer surveyId);

	void batchInsertQuestion(@Param("questionSet") Set<Question> questionSet);
}