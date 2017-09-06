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

<script type="text/javascript">
  $(function(){
      $(".deleteDeeply").click(function(){
          var surveyName =   $(this).parents("tr").children("td:eq(2)").text();
          var flag =window.confirm("你确定要删除"+surveyName+"调查吗?")
//第一重
          if(flag){
              var flag2 =window.confirm("危险操作,你需要再次确定要删除"+surveyName+"调查?")
              if(flag2){
              }else{
                  return false;
              }

          } else{
              return false;
          }
      });
  });
</script>
<div id="mainDiv" class="borderDiv">
    <table class="dataTable">
        <c:if test="${empty page.dataList}">
            <tr>
                <td colspan="">没有未完成的调查</td>
            </tr>
        </c:if>
        <c:if test="${!empty page.dataList}">
            <tr>
                <td>ID</td>
                <td>调查名称</td>
                <td>LOGO图片</td>
                <td colspan="4">操作</td>
            </tr>
            <c:forEach items="${page.dataList}" var="survey">
                <tr>
                    <td>${survey.surveyId}</td>
                    <td>${survey.surveyName}</td>
                    <td><img src="${survey.logoPath}"></td>
                    <td><a href="guest/survey/survey_design/${survey.surveyId}">设计</a></td>
                    <td><a href="guest/survey/deleteSurvey/${survey.surveyId}/${page.pageNo}">删除</a></td>
                    <td><a href="guest/survey/editSurveyUI/${survey.surveyId}/${page.pageNo}">更新</a></td>
                    <td style="background-color: black;">
                        <a class="deleteDeeply" href="guest/survey/deleteDeeplySurvey/${survey.surveyId}/${page.pageNo}" style="color: yellow;">深度删除</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="7">
                    <c:set var="targetUrl" value="guest/survey/showMyUnCompletedSurvey" scope="page"/>
                    <%@ include file="/res_jsp/navigator.jsp" %>
                </td>
            </tr>
        </c:if>
    </table>

</div>
<%@include file="/res_jsp/guest_bottom.jsp" %>
</body>
</html>