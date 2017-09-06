package com.hanvon.survey.component.service.i;

import java.util.List;

import com.hanvon.survey.entities.guest.Bag;

public interface BagService {

	void saveBag(Bag bag);

	void deleteBag(Integer bagId);

	Bag getBag(Integer bagId);

	void updateBag(Bag bag);

	void deleteDeeplyBag(Integer bagId);

	List<Bag> queryBagBySurveyId(Integer surveyId);

	void updateBatchBagOrder(List<Integer> bagIdList, List<Integer> bagOrderList);

	void updateRelationshipMove(Integer bagId, Integer surveyId);

	void updateRelationshipCopyDeeply(Integer bagId, Integer surveyId);

}
