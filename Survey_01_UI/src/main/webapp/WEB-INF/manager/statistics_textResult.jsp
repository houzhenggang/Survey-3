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
<%@include file="/res_jsp/manager_top.jsp" %>

<div id="mainDiv" class="borderDiv">
    <br><br>
    [简答题答案列表]
    <table class="dataTable">
        <c:if test="${empty answerList }">
            <tr>
                <td>该问题没有参与的答案</td>
            </tr>
        </c:if>
        <c:if test="${!empty answerList }">
            <c:forEach items="${answerList }" var="content">
                <tr>
                    <td>${content }</td>
                </tr>
            </c:forEach>
        </c:if>

    </table>

    <br><br><br>
</div>
<%@include file="/res_jsp/manager_bottom.jsp" %>
</body>
</html>