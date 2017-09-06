<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div id="logoDiv" class="borderDiv">汉王蓝天在线调查系统 </div>
<div id="topDiv" class="borderDiv">
    <c:if test="${sessionScope.loginAdmin == null }">
        [<a href="manager/admin/toLoginUI">登录</a>]
    </c:if>
    <c:if test="${sessionScope.loginAdmin != null }">
        [欢迎管理员,${sessionScope.loginAdmin.adminName}]
        [<a href="manager/admin/logout">退出</a>]

        [<a href="manager/statistics/showStatisticsList">统计调查</a>]
        [<a href="#">资源列表</a>]
        [<a href="#">创建权限</a>]
        [<a href="#">查看权限</a>]
        [<a href="#">创建角色</a>]
        [<a href="#">查看角色</a>]
        [<a href="#">创建账号</a>]
        [<a href="#">查看账号</a>]
        [<a href="#">查看日志</a>]

    </c:if>

    [<a href="manager/admin/toMainUI">首页</a>]
</div>

