<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <!-- 静态包含，路径必须以斜杠开头。斜杠表示上下文路径request.getContextPath();  pageContext.request.contextPath -->
    <%@ include file="/res_jsp/base.jsp" %>
</head>
<body>

<%@ include file="/res_jsp/guest_top.jsp" %>

<div id="mainDiv" class="borderDiv">

    [移动/复制包裹]

    <table class="dataTable">
        <tr>
            <td>ID</td>
            <td>调查名称</td>
            <td>操作</td>
        </tr>

        <c:forEach items="${page.dataList }" var="survey">

            <tr>
                <td>${survey.surveyId }</td>
                <td>${survey.surveyName }</td>
                <td>
                    <c:if test="${survey.surveyId == requestScope.surveyId}">
                        当前调查
                    </c:if>
                    <c:if test="${survey.surveyId != requestScope.surveyId}">
                        <!--
                        ${requestScope.bagId} : 被移动包裹id
                        ${survey.surveyId } : 目标调查id
                        -->
                        <a href="guest/bag/updateRelationshipMove/${requestScope.bagId}/${survey.surveyId }">移动包裹</a> |
                        <a href="guest/bag/updateRelationshipCopy/${requestScope.bagId}/${survey.surveyId }">复制包裹</a>
                    </c:if>
                </td>

            </tr>

        </c:forEach>

    </table>

    <c:set var="targetUrl" value="guest/bag/showMoveAndCopySurveyList/${requestScope.bagId}/${requestScope.surveyId}" scope="page"></c:set>
    <%@ include file="/res_jsp/navigator.jsp" %>

</div>

<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>