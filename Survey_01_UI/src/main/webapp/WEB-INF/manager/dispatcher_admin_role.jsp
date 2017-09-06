<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
	 
		[给管理员分配角色]
		<form action="manager/admin/updateRelationship" method="post">
			<input type="hidden" name="adminId" value="${requestScope.adminId }">
			<table class="dataTable">
				
				<c:if test="${empty roleList }">
					<tr>					
						<td>尚未创建角色</td>			
					</tr>
				</c:if>
				<c:if test="${!empty roleList }">
					<c:forEach items="${roleList }" var="role">
						<tr>					
							<td>
								<input type="checkbox" name="roleIdList" value="${role.roleId }" 
									<c:forEach items="${currentRoleList }" var="roleId">
										<c:if test="${role.roleId == roleId }">checked="checked"</c:if>
									</c:forEach>
								>
								
								
								${role.roleName }
							</td>			
						</tr>
					</c:forEach>
					<tr>					
						<td>
							<input type="submit" value="提交">
						</td>			
					</tr>
				</c:if>		
				
			</table>
		</form>
		
		
	
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>