<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
  <%@ include file="/res_jsp/base.jsp"%>
</head>
<body>
<%@include file="/res_jsp/guest_top.jsp"%>

<div id="mainDiv" class="borderDiv">

    [编辑调查]
    <!--表单回显,需要从request域中查询叫做command属性对象,modelAttribute的默认是值是command -->
    <form:form action="guest/survey/updateSurvey" method="post"  modelAttribute="survey" enctype="multipart/form-data">
        <form:hidden path="userId"></form:hidden>
        <form:hidden path="surveyId"></form:hidden>
        <form:hidden path="logoPath"></form:hidden>

        <input type="hidden" name="pageNoStr" value="${pageNo}">

        <table class="formTable">
            <c:if test="${exception != null}">
                <tr>
                    <td colspan="2"  bgcolor="#a52a2a">
                            ${exception.message}
                    </td>
                </tr>
            </c:if>
            <tr>
                <td>调查名称</td>
                <td>
                    <form:input path="surveyName"/>
                </td>
            </tr>
            <tr>
                <td>调查图片</td>
                <td>
                   <img src=' ${survey.logoPath}'>
                </td>
            </tr>
            <tr>
                <td>上传新图片</td>
                <td>
                    <!-- multpatyfile-->
                   <input type="file" name="logoFile">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="更新">
                </td>
            </tr>
        </table>
    </form:form>
</div>
<%@include file="/res_jsp/guest_bottom.jsp"%>
</body>
</html>