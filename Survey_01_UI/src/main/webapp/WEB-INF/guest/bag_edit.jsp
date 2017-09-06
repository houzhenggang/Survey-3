<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    [更新包裹]

    <form:form action="guest/bag/updateBag" method="post" modelAttribute="bag">

        <!-- 修改页面：一定会有隐含域 -->
        <form:hidden path="bagId"/>
        <form:hidden path="surveyId"/>

        <table class="formTable">

            <tr>
                <td>包裹名称</td>
                <td>
                    <form:input path="bagName" cssClass="longInput"/>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <input type="submit" value="更新"/>
                </td>
            </tr>

        </table>

    </form:form>

</div>

<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>