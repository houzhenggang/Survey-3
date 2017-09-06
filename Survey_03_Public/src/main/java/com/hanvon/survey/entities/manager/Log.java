package com.hanvon.survey.entities.manager;

public class Log {
    private Integer logId;

    private String operator; //操作人

    private String operateTime; //操作时间

    private String methodName; //被操作方法名称

    private String typeName;//被操作方法所在类类型名称

    private String methodArgs;//被操作方参数

    private String methodReturnValue; //被操作方法返回结果

    private String exceptionType; //被操作方法产生异常类型

    private String exceptionMessage; //被操作方法产生异常的异常消息 

    public Log() {
	}

	public Log(Integer logId, String operator, String operateTime,
			String methodName, String typeName, String methodArgs,
			String methodReturnValue, String exceptionType,
			String exceptionMessage) {
		super();
		this.logId = logId;
		this.operator = operator;
		this.operateTime = operateTime;
		this.methodName = methodName;
		this.typeName = typeName;
		this.methodArgs = methodArgs;
		this.methodReturnValue = methodReturnValue;
		this.exceptionType = exceptionType;
		this.exceptionMessage = exceptionMessage;
	}

	public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime == null ? null : operateTime.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getMethodArgs() {
        return methodArgs;
    }

    public void setMethodArgs(String methodArgs) {
        this.methodArgs = methodArgs == null ? null : methodArgs.trim();
    }

    public String getMethodReturnValue() {
        return methodReturnValue;
    }

    public void setMethodReturnValue(String methodReturnValue) {
        this.methodReturnValue = methodReturnValue == null ? null : methodReturnValue.trim();
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType == null ? null : exceptionType.trim();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage == null ? null : exceptionMessage.trim();
    }
}