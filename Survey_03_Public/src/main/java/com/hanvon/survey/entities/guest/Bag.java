package com.hanvon.survey.entities.guest;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Bag implements Serializable{
    private Integer bagId;

    private String bagName;

    private Integer bagOrder;

    private Integer surveyId; //表示多对一关联，关联主键属性值；
    
    private Set<Question> questionSet = new LinkedHashSet<Question>(); //包裹和问题的一对多关联

    public Bag() {
		super();
		System.out.println("======================");
	}

	public Bag(Integer bagId, Integer bagOrder) {
		super();
		this.bagId = bagId;
		this.bagOrder = bagOrder;
		System.out.println("======================");
	}
	
	

	public Bag(Integer bagId, String bagName, Integer bagOrder, Integer surveyId) {
		super();
		this.bagId = bagId;
		this.bagName = bagName;
		this.bagOrder = bagOrder;
		this.surveyId = surveyId;
		System.out.println("======================");
	}

	public Integer getBagId() {
        return bagId;
    }

    public void setBagId(Integer bagId) {
        this.bagId = bagId;
    }

    public String getBagName() {
        return bagName;
    }

    public void setBagName(String bagName) {
        this.bagName = bagName == null ? null : bagName.trim();
    }

    public Integer getBagOrder() {
        return bagOrder;
    }

    public void setBagOrder(Integer bagOrder) {
        this.bagOrder = bagOrder;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

	public Set<Question> getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(Set<Question> questionSet) {
		this.questionSet = questionSet;
	}

	@Override
	public String toString() {
		return "Bag [bagId=" + bagId + ", bagName=" + bagName + ", bagOrder="
				+ bagOrder + ", surveyId=" + surveyId + "]";
	}
    
    
}