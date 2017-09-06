package com.hanvon.survey.entities.guest;

import java.util.LinkedHashSet;
import java.util.Set;

public class Survey {
	private Integer surveyId;

	private String surveyName;

	private Boolean completed = false; // 调查状态：true表示完成调查，false表示未完成

	// 调查图片统一存放在当前项目部署目录下的：surveyLogos
	private String logoPath = "surveyLogos/logo.gif"; // 没有上传图片，使用默认图片

	private Integer userId; // 多对一的关联关系

	
	private Set<Bag> bagSet = new LinkedHashSet<Bag>(); //调查和包裹一对多关联
	
	
	public Survey() {
		super();
	}

	public Survey(Integer surveyId, String surveyName, Boolean completed,
			String logoPath, Integer userId) {
		super();
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.completed = completed;
		this.logoPath = logoPath;
		this.userId = userId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName == null ? null : surveyName.trim();
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath == null ? null : logoPath.trim();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Set<Bag> getBagSet() {
		return bagSet;
	}

	public void setBagSet(Set<Bag> bagSet) {
		this.bagSet = bagSet;
	}

	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", surveyName=" + surveyName
				+ ", completed=" + completed + ", logoPath=" + logoPath
				+ ", userId=" + userId + "]";
	}
	
	
}