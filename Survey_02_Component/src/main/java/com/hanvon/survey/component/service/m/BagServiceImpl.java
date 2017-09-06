package com.hanvon.survey.component.service.m;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanvon.survey.component.dao.i.BagMapper;
import com.hanvon.survey.component.service.i.BagService;
import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Question;
import com.hanvon.survey.utils.DataProcessUtils;

@Service
public class BagServiceImpl implements BagService {

	@Autowired
	private BagMapper bagMapper ;

	@Override
	public void saveBag(Bag bag) {
		//MyBatis框架可以在执行insert语句后，将数据库分配的主键值获取到，然后，封装给指定属性（主键属性）
		//<insert id="insert" parameterType="com.hanvon.survey.entities.guest.Bag" useGeneratedKeys="true" keyProperty="bagId">
		bagMapper.insert(bag);
		
		//获取主键值
		Integer bagId = bag.getBagId();
		
		//使用主键值作为排序的序号
		bag.setBagOrder(bagId);
		
		//更新包裹序号字段
		bagMapper.updateByPrimaryKey(bag);
	}

	@Override
	public void deleteBag(Integer bagId) {
		bagMapper.deleteByPrimaryKey(bagId);
	}

	@Override
	public Bag getBag(Integer bagId) {		
		return bagMapper.selectByPrimaryKey(bagId);
	}

	@Override
	public void updateBag(Bag bag) {
		bagMapper.updateByPrimaryKey(bag);
	}

	@Override
	public void deleteDeeplyBag(Integer bagId) {
		//1.删除当前包裹下所有问题
		bagMapper.deleteCurrentQuestionByBagId(bagId);
		//2.删除当前包裹
		bagMapper.deleteByPrimaryKey(bagId);
	}

	@Override
	public List<Bag> queryBagBySurveyId(Integer surveyId) {		
		return bagMapper.queryBagBySurveyId(surveyId);		
	}

	@Override
	public void updateBatchBagOrder(List<Integer> bagIdList,
			List<Integer> bagOrderList) {
		//BagMapper中需要做批量更新，为了BagMapper获取参数方便，将集合数据获取后封装成Bag对象形式，存放到List中作为参数
		
		int size = bagIdList.size();
		
		List<Bag> bagList = new ArrayList<Bag>();
		
		for (int i = 0; i < size; i++) {
			Integer bagId = bagIdList.get(i);
			Integer bagOrder = bagOrderList.get(i);
			
			Bag bag = new Bag(bagId,bagOrder);
			
			bagList.add(bag);
		}
		
		bagMapper.updateBatchBagOrder(bagList);
		
	}

	@Override
	public void updateRelationshipMove(Integer bagId, Integer surveyId) {
		bagMapper.updateRelationshipMove(bagId,surveyId);
	}

	//深度赋值
	@Override
	public void updateRelationshipCopyDeeply(Integer bagId, Integer surveyId) {
		
		//1.深度加载包裹
		Bag bagSource = bagMapper.getBagDeeply(bagId);
		
		//2.深度赋值
		Bag bagTarget = (Bag)DataProcessUtils.copyDeeply(bagSource);
		
		//修改包裹所对应调查：改外键
		bagTarget.setSurveyId(surveyId); //目标调查
		
		////数据库分配主键，所以在执行insert操作时，不需要指定主键
		bagTarget.setBagId(null); 
		
		//3.保存新包裹对象
		bagMapper.insert(bagTarget); //useGeneratedKeys="true" keyProperty="bagId"
		
		//修改排序序号
		bagTarget.setBagOrder(bagTarget.getBagId());		
		bagMapper.updateByPrimaryKey(bagTarget);
		
		//4.包裹新包裹的所有问题对象
		Set<Question> questionSet = bagTarget.getQuestionSet();
		
		//问题-外键
		for (Question question : questionSet) {
			question.setQuestionId(null); //将问题对象的主键设置为null，由数据库重新分配主键；
			question.setBagId(bagTarget.getBagId()); //将问题对象的关联bagId属性修改为新包裹对象的id
		}
		//批量保存问题
		if(questionSet!=null && questionSet.size()>0)
			bagMapper.batchInsertQuestion(questionSet);
	}
	
}
