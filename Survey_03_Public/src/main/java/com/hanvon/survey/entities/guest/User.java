package com.hanvon.survey.entities.guest;

public class User {
	private Integer userId; //主键

	private String userName; //用户名称

	private String userPwd; //用户密码

	private Boolean company; //用户类型：true表示企业用户（1）；false表示普通用户（0）

	private String codeArrStr;

	public User() {
		super();
	}

	public User(String userName, String userPwd) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
	}

	public User(Integer userId, String userName, String userPwd,
			Boolean company, String codeArrStr) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.company = company;
		this.codeArrStr = codeArrStr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd == null ? null : userPwd.trim();
	}

	public Boolean getCompany() {
		return company;
	}

	public void setCompany(Boolean company) {
		this.company = company;
	}

	public String getCodeArrStr() {
		return codeArrStr;
	}

	public void setCodeArrStr(String codeArrStr) {
		this.codeArrStr = codeArrStr == null ? null : codeArrStr.trim();
	}
}