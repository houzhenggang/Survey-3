<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <%@ include file="/res_jsp/base.jsp" %>
</head>
<body>
<br>
<%@include file="/res_jsp/guest_top.jsp" %>

<div id="mainDiv" class="borderDiv">
    [显示所有已完成调查]
    <table class="dataTable">
        <c:if test="${empty page.dataList}">
            <tr>
                <td colspan="3">
                    没有已经完成的调查
                </td>
            </tr>
        </c:if>
        <c:if test="${!empty page.dataList}">
            <tr>
                <td></td>
            </tr>
            <tr>
                <td>Logo</td>
                <td>调查名称</td>
                <td>操作</td>
            </tr>
            <c:forEach items="${page.dataList}" var="survey">
                <tr>
                    <td><img alt="" src="${survey.logoPath}"></td>
                    <td>${survey.surveyName}</td>
                    <td>
                        <a href="engage/entry/${survey.surveyId}">参与调查</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="3">
                    <c:set var="targetUrl" value="guest/survey/showAllAvailableSurvey" scope="page"></c:set>
                    <%@include file="/res_jsp/navigator.jsp" %>
                </td>
            </tr>
        </c:if>
    </table>
</div>
<%@include file="/res_jsp/guest_bottom.jsp" %>
</body>
</html>