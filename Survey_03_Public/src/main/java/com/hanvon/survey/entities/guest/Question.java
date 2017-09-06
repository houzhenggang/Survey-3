package com.hanvon.survey.entities.guest;

import java.io.Serializable;

public class Question implements Serializable {
	private Integer questionId;

	private String questionName;

	private Integer questionType;

	private String questionOptions = "";

	private Integer bagId; // 表示多对一关系

	public Question(Integer questionId, String questionName,
			Integer questionType, String questionOptions, Integer bagId) {
		super();
		this.questionId = questionId;
		this.questionName = questionName;
		this.questionType = questionType;
		this.questionOptions = questionOptions;
		this.bagId = bagId;
	}

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName == null ? null : questionName.trim();
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	// ----------------问题，选项，特殊设置----------------------------------

	// 将数据库中保存选项（以逗号分隔保存），将逗号替换为\r\n
	public String getQuestionOptionsForEdit() {
		if (questionOptions != null) {
			questionOptions = questionOptions.replaceAll(",", "\r\n");
		}
		return questionOptions;
	}

	// 将数据库中保存选项(以逗号分隔保存)，以逗号分隔为数组
	public String[] getQuestionOptionsToArr() {
		return questionOptions.split(",");
	}

	// 将多行文本框提交多个选项中的\r\n替换为逗号
	// “xxx, , ddddd , ,,,,,yyyy , ,ffff”
	// “xxx,ddddd,yyyy,ffff”
	// 去掉连续逗号,逗号之间有空白选项
	public void setQuestionOptions(String questionOptions) {

		this.questionOptions = questionOptions == null ? "" : questionOptions
				.replaceAll("\r\n", ",");

		String[] optionArr = this.questionOptions.split(",");

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < optionArr.length; i++) {
			String option = optionArr[i].trim(); // 去掉选项的首尾空格

			if ("".equals(option)) {
				continue;
			}

			builder = builder.append(option).append(",");
		}
		// 简答题，没有选项，所以不需要截取最后的逗号
		if (builder.length() < 1)
			return;
		// 去掉最末尾逗号
		this.questionOptions = builder.substring(0, builder.length() - 1);
	}

	// --------------------------------------------------
	/*public String getQuestionOptions() {
		return questionOptions;
	}*/
	public Integer getBagId() {
		return bagId;
	}

	public void setBagId(Integer bagId) {
		this.bagId = bagId;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionName="
				+ questionName + ", questionType=" + questionType
				+ ", questionOptions=" + questionOptions + ", bagId=" + bagId
				+ "]";
	}

}