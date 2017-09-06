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
	 
		[显示日志列表]
		
		<table class="dataTable">
		
			<c:if test="${empty page.dataList }">
				<tr>
					<td>暂无日志信息</td>
				</tr>
			</c:if>
			
			<c:if test="${!empty page.dataList }">
				<c:forEach items="${page.dataList }" var="log">
					<tr>
						<td>详细信息</td>
					</tr>
					<tr>
						<td>
							操作人：${log.operator }<br>
							操作时间：${log.operateTime }<br>
							被操作方法名称：${log.methodName }<br>
							被操作方法所在类类型：${log.typeName }<br>
							被操作方法参数：${log.methodArgs }<br>
							被操作方法返回值：${log.methodReturnValue }<br>
							被操作方法异常类型：${log.exceptionType }<br>
							被操作方法异常消息：${log.exceptionMessage }						
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td>
						<c:set var="targetUrl" value="manager/log/showLogList" scope="page"></c:set>
						<%@ include file="/res_jsp/navigator.jsp" %>
					</td>
				</tr>
			</c:if>
		
		</table>
	
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>