<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div id="logoDiv" class="borderDiv">汉王蓝天在线调查系统</div>
<div id="topDiv" class="borderDiv">
    <c:if test="${sessionScope.loginUser == null}">
    [<a href="guest/user/toLoginUI"> 登录 </a>]
    [<a href="guest/user/toRegistUI"> 注册 </a>]
    </c:if>
    <c:if test="${sessionScope.loginUser != null}">
        [欢迎,${sessionScope.loginUser.userName}]
        [<a href="guest/user/logout">退出</a>]

        <c:if test="${sessionScope.loginUser.company}">
            [<a href="guest/user/toSurveyUI">创建调查</a> ]
            [<a href="guest/survey/showMyUnCompletedSurvey?pageNoStr=1">查询未完成调查</a> ]
        </c:if>

        [<a href="guest/engage/showAllAvailableSurvey">参与调查</a>]
    </c:if>
    [<a href="index.jsp">首页</a>]
</div>