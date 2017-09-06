<%@page import="com.hanvon.survey.utils.DataProcessUtils" %>
<%@ page import="com.hanvon.survey.utils.DataProcessUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

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

    [参与调查]

    <table class="dataTable">

        <tr>
            <td colspan="2">调查信息</td>
        </tr>

        <tr>
            <td>Logo</td>
            <td>调查名称</td>
        </tr>

        <tr>
            <td><img src="${sessionScope.survey.logoPath }"></td>
            <td>${sessionScope.survey.surveyName }</td>
        </tr>

    </table>

    <form action="guest/engage/engage" method="post">

        <input type="hidden" name="bagId" value="${requestScope.currentBag.bagId }">
        <input type="hidden" name="currentIndex" value="${requestScope.currentIndex }"/>

        <table class="dataTable">

            <tr>
                <td>当前包裹：${requestScope.currentBag.bagName }</td>
            </tr>

            <tr>

                <td>

                    <c:forEach items="${requestScope.currentBag.questionSet }" var="question">

                        ${question.questionName }

                        <c:if test="${question.questionType==0 }">
                            <!--
                            status : 用于表示当前集合在迭代时状态信息：可以通过status对象来获取当前元素在集合中的索引
                            javax.servlet.jsp.jstl.core.LoopTagSupport
                            -->
                            <c:forEach items="${question.questionOptionsToArr}" var="option" varStatus="status">
                                <input id="label${question.questionId}${status.index}"
                                       type="radio"
                                       name="q${question.questionId}"
                                       value="${status.index }"
                                        <%=DataProcessUtils.checkedReDisplay(pageContext) %>

                                >
                                <label for="label${question.questionId}${status.index}">${option }</label>
                            </c:forEach>
                        </c:if>
                        <br>
                        <c:if test="${question.questionType==1 }">
                            <c:forEach items="${question.questionOptionsToArr}" var="option" varStatus="status">
                                <input id="label${question.questionId}${status.index}"
                                       type="checkbox"
                                       name="q${question.questionId}"
                                       value="${status.index }" <%=DataProcessUtils.checkedReDisplay(pageContext) %>
                                >
                                <label for="label${question.questionId}${status.index}">${option }</label>
                            </c:forEach>
                        </c:if>
                        <br>
                        <c:if test="${question.questionType==2 }">
                            <input type="text"
                                   class="longInput"
                                   name="q${question.questionId}"
                                   value="<%=DataProcessUtils.textReDisplay(pageContext) %>"
                        </c:if>
                        <br>
                    </c:forEach>


                </td>
            </tr>

            <tr>
                <td>
                    <c:if test="${requestScope.currentIndex > 0 }">
                        <input type="submit" name="submit_prev" value="返回上一个包裹">
                    </c:if>
                    <c:if test="${requestScope.currentIndex < sessionScope.lastIndex }">
                        <input type="submit" name="submit_next" value="进入下一个包裹">
                    </c:if>

                    <c:if test="${requestScope.currentIndex == sessionScope.lastIndex }">
                        <input type="submit" name="submit_done" value="完成">
                    </c:if>
                    <input type="submit" name="submit_quit" value="放弃">

                </td>
            </tr>

        </table>
    </form>

</div>

<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>