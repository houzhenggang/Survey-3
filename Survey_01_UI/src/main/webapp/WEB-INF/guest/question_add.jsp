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

    <script type="text/javascript">

        $(function(){

            //页面加载完成，默认让简单题型被选中
            $("#questionType3").attr("checked","checked");

            //页面加载完成，让选项文本域隐藏
            $("#questionOptionsTr").hide();

            //给所有题型单向按钮增加单击响应事件
            $(":radio").click(function(){

                //获取题型
                var questionType = this.value ; //this属于dom对象     $(this).val();

                //如果选择单选题:0;或多选题:1,显示选项的文本域

                if(questionType==0 || questionType==1){
                    $("#questionOptionsTr").show();
                }

                //如果是简答题，隐藏
                if(questionType==2){
                    $("#questionOptionsTr").hide();
                }

            });

        });

    </script>


</head>
<body>

<%@ include file="/res_jsp/guest_top.jsp" %>

<div id="mainDiv" class="borderDiv">

    [创建问题]

    <form:form action="guest/question/saveQuestion" method="post" >

        <input type="hidden" name="bagId" value="${bagId }"/>
        <input type="hidden" name="surveyId" value="${surveyId }"/>

        <table class="formTable">

            <tr>
                <td>问题名称</td>
                <td>
                    <input type="text" name="questionName" class="longInput"/>
                </td>
            </tr>
            <tr>
                <td>问题类型</td>
                <td>
                        <%-- Spring标签库动态生成的
                            <input id="questionType1" name="questionType" type="radio" value="0"/>
                            <label for="questionType1">单选题</label>
                            <input id="questionType2" name="questionType" type="radio" value="1"/>
                            <label for="questionType2">多选题</label>
                            <input id="questionType3" name="questionType" type="radio" value="2" checked="checked"/>
                            <label for="questionType3">简答题</label>
                         --%>
                    <form:radiobuttons path="questionType" items="${questionTypeMap }" />

                </td>
            </tr>
            <tr id="questionOptionsTr">
                <td>选项</td>
                <td>
                    <textarea class="multiInput" name="questionOptions"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="保存"/>
                </td>
            </tr>
        </table>
    </form:form>

</div>

<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>