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
<%@include file="/res_jsp/guest_top.jsp" %>

<div id="mainDiv" class="borderDiv">
    <form action="guest/user/login" method="post">
        <table class="formTable">

            <c:if test="${exception!=null}">
                <tr>
                    <td colspan="2">${exception.message}      </td>
                </tr>
            </c:if>
            <tr>
                <td> 用户名称</td>
                <td>
                    <input type="text" name="userName" value="test" class="longInput">
                </td>
            </tr>
            <tr>
                <td>用户密码</td>
                <td>
                    <input type="password" name="userPwd" value="123" class="longInput">
               </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="登录"></td>
            </tr>
        </table>

    </form>
</div>
<%@include file="/res_jsp/guest_bottom.jsp" %>
</body>
</html>