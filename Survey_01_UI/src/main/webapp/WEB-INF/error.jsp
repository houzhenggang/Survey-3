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

<div id="mainDiv" class="borderDiv">
    <br><br>
    [出错了]
    <c:if test="${exception!=null}">
        ${exception.message}
    </c:if>

    <%--button:后退按钮--%>
    <button onclick="javascript:history.back();">返回</button>
    <br><br><br>
</div>
</body>
</html>