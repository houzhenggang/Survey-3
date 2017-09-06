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

            //获取页面上一组radio,再获取这一组中哪一个被选中，从选中radio对象上获取value属性，用于题型判断；控制选项的显示和隐藏
            var questionType = $(":radio:checked").val();
            if(questionType==2){
                //页面加载完成，让选项文本域隐藏
                $("#questionOptionsTr").hide();
            }

            //给所有题型单向按钮增加单击响应事件
            $(":radio").click(function(){

                //获取题型
                var qType = this.value ; //this属于dom对象     $(this).val();

                //如果选择单选题:0;或多选题:1,显示选项的文本域

                if(qType==0 || qType==1){
                    $("#questionOptionsTr").show();
                }

                //如果是简答题，隐藏
                if(qType==2){
                    $("#questionOptionsTr").hide();
                }

            });

            //将文本域中选项的开始和结束的空白行和空格都去掉
            $("#questionOptionsTextArea").change(function(){

                var value = this.value ;
                this.value = $.trim(value) ;

            });

        });

    </script>


</head>
<body>

<%@ include file="/res_jsp/guest_top.jsp" %>

<div id="mainDiv" class="borderDiv">

    [编辑问题]

    <form:form action="guest/question/updateQuestion" method="post" modelAttribute="question">

        <%--
        不需要更新包裹外键：
        <input type="hidden" name="bagId" value="${bagId }"/> --%>
        <!-- 用于更新问题后，跳转到当前调查的设计页面 -->
        <input type="hidden" name="surveyId" value="${surveyId }"/>

        <form:hidden path="questionId"/>

        <table class="formTable">

            <tr>
                <td>问题名称</td>
                <td>
                    <!-- 使用spring标签自动回显功能
                    <input type="text" name="questionName" class="longInput"/> -->
                    <form:input path="questionName" cssClass="longInput"/>
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
                    <!-- 提交表单时，question对象中没有questionOptionsForEdit属性的set方法；所以，回显没有问题，但是提交有问题 -->
                        <%-- <form:textarea path="questionOptionsForEdit"/> --%>

                        <%-- 使用使用普通文本域进行回显；
                        没有value属性；需要将回显数据存放到标签体中进行回显;
                        选项在文本域中赋值时，不能折行，否则，选项中会出现空白行
                            <textarea class="multiInput" name="questionOptions">
                                ${requestScope.question.questionOptionsForEdit }
                            </textarea>
                         --%>
                    <textarea id="questionOptionsTextArea" class="multiInput" name="questionOptions">${requestScope.question.questionOptionsForEdit }</textarea>
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