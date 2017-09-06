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
    <form action="guest/user/regist" method="post">
        <table class="formTable">
            <c:if test="${exception != null}">
                <tr>
                    <td colspan="2"  bgcolor="#a52a2a">
                        ${exception.message}
                    </td>
                </tr>
            </c:if>
            <tr>
                <td>
                    用户名称: <input type="text" name="userName" class="longInput">
                </td>
            </tr>
            <tr>
                <td>
                    用户密码: <input type="password" name="userPwd" class="longInput">
                </td>
            </tr>
            <tr>
                <td>
                    确认密码: <input type="password" name="confirPwd" class="longInput">
                </td>
            </tr>
            <tr>
                <td>
                    <input id="companyTrue" type="radio" name="company" value="true" checked="checked"/>
                    <label for="companyTrue">企业用户</label>
                    <input id="companyFalse" type="radio" name="company" value="false"/>
                    <label for="companyTrue">个人用户</label><br>
                </td>
            </tr>
            <tr colspan="2">
                <td>
                    <input type="submit" value="注册"/>
                </td>
            </tr>
        </table>
    </form>
</div>


<%@include file="/res_jsp/guest_bottom.jsp" %>
</body>
</html>
