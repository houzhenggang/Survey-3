<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
	 
		[统计大纲:${survey.surveyName }]		
		
		<table class="dataTable">
		
			<c:if test="${empty survey.bagSet }">
				<tr>
					<td colspan="2">当前调查下不存在包裹，数据是不合法!</td>
				</tr>
			</c:if>
			
			<c:if test="${!empty survey.bagSet }">
				<tr>
					<td colspan="2">包裹列表</td>
				</tr>
				<c:forEach items="${survey.bagSet }" var="bag">
					<tr>
						<td>${bag.bagName }</td>
						<td>问题统计信息</td>
					</tr>
					<c:if test="${empty bag.questionSet }">
						<tr>
							<td colspan="2">当前包裹下不存在问题，数据是不合法!</td>
						</tr>						
					</c:if>
					<c:if test="${!empty bag.questionSet }">
						<tr>
							<td><!-- 缩进一个单元格，显示问题列表 --></td>
							<td>
								<table class="dataTable">
									<c:forEach items="${bag.questionSet }" var="question">
										<tr>
											<td>${question.questionName }</td>
											<td>
												<c:if test="${question.questionType==0 || question.questionType==1}">
													<a target="_blank" href="manager/statistics/showChartStatistics/${question.questionId }">查看统计图表</a>
												</c:if>
												<c:if test="${question.questionType==2}">
													<a href="manager/statistics/showTextStatistics/${question.questionId }">显示简答题答案列表</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>								
								</table>							
							</td>
						</tr>						
					</c:if>
				</c:forEach>
			</c:if>
			
		
		</table>
		
	
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>