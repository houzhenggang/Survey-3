<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/res_jsp/base.jsp" %>

<script type="text/javascript">

	$(function(){
		
		$(".resBtn").click(function(){
			//click事件函数中的this表示当前被单击按钮
			var resId = this.id ;
			
			var resBtn = this ;
			
			var url = "${pageContext.request.contextPath}/manager/res/updateStatus";
			//请求路径中带时间信息，不是为了给服务器端传参数，而是为了避免浏览器缓存路径
			var data = {"resId":resId,"time":new Date()};
			var callback = function(resultData){
				if(resultData.message=="success"){
					//this在回调函数表示是当前函数对象；
					if(resultData.finalStatus=="true"){						
						resBtn.value="公共资源";
					}else{						
						resBtn.value="受保护资源";
					}					
					//alert("操作成功!");
				}else if(resultData.message=="failed"){
					//alert("操作失败!");
				}
			};
			var type = "json";			
			$.post(url,data,callback,type);
		});
	});

</script>

</head>
<body>

	<%@include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		[资源列表]
		<form action="manager/res/batchDelete" method="post">
			<table class="dataTable">
				<c:if test="${empty resList }">
					<tr>
						<td>尚未捕获到资源</td>
					</tr>
				</c:if>
				<c:if test="${!empty resList }">
					<tr>
						<td>ID</td>
						<td>ServletPath</td>
						<td>资源状态</td>
						<td>删除</td>
					</tr>
					
					<c:forEach items="${resList }" var="res">
						<tr>
							<td>${res.resId }</td>
							<td>${res.servletPath }</td>
							<td>
								<!-- <button>在表单中有提交表单的效果</button> -->
								<c:if test="${res.publicStatus }">
									<input id="${res.resId }" class="resBtn" type="button" value="公共资源"/>
								</c:if>
								<c:if test="${!res.publicStatus }">
									<input id="${res.resId }" class="resBtn" type="button" value="受保护资源"/>
								</c:if>
							</td>
							<td>
								<input id="checkbox${res.resId }" type="checkbox" name="resIdList" value="${res.resId }"/>
								<label for="checkbox${res.resId }">点我更轻松</label>
							</td>
						</tr>
					</c:forEach>
					
					<tr>
						<td colspan="4">
							<input type="submit" value="批量删除"/>
						</td>
					</tr>
					
				</c:if>
			</table>
		</form>
	</div>
	
	<%@include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>