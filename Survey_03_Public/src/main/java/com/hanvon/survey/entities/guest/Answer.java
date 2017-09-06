package com.hanvon.survey.entities.guest;

public class Answer {
    private Integer answerId;

    private String content; //答案=选项索引,选项索引,选项索引

    private String uuid; //区分每次参与调查

    private Integer questionId; //答案属于哪一个问题

    private Integer surveyId; //答案属于哪一个调查

    public Answer() {
		super();
	}

	public Answer(Integer answerId, String content, String uuid,
			Integer questionId, Integer surveyId) {
		super();
		this.answerId = answerId;
		this.content = content;
		this.uuid = uuid;
		this.questionId = questionId;
		this.surveyId = surveyId;
	}

	public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }
}