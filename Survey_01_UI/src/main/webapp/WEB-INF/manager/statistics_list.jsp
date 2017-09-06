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
	 
		[统计调查列表]
		
		<table class="dataTable">
		
			<c:if test="${empty page.dataList}">
				<tr>
					<td colspan="4">没有可以进行统计的调查</td>
				</tr>				
			</c:if>
			
			<c:if test="${!empty page.dataList}">
				<tr>
					<td>ID</td>
					<td>调查名称</td>
					<td colspan="2">操作</td>
				</tr>
				<c:forEach items="${page.dataList }" var="survey">
					<tr>
						<td>${survey.surveyId }</td>
						<td>${survey.surveyName }</td>
						<td><a href="manager/statistics/statisticsSummary/${survey.surveyId }">统计大纲</a></td>
						<td><a href="manager/statistics/exportExcel/${survey.surveyId }">导出Excel</a></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4">
						<c:set var="targetUrl" value="manager/statistics/showStatisticsList" scope="page"></c:set>
						<%@ include file="/res_jsp/navigator.jsp" %>
					</td>
				</tr>
			</c:if>
		
		</table>
		
	
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>