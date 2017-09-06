<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<script type="text/javascript">

    $(function () {
        //给输入框增加change事件处理
        $("#go").change(function(){
            //获取用户输入的页码
            var pageNo = $.trim(this.value);

            if(pageNo=="" || isNaN(pageNo)){ //空串是否不是一个数字，需要单独判断
                this.value = this.defaultValue ;//赋值为初始值
                return ;
            }
            //跳转路径，分页查询
            window.location.href = "${pageContext.request.contextPath}/${targetUrl}?pageNoStr="+pageNo;
        });
    });
</script>



<c:if test="${page.hasPrev }">
    <a href="${targetUrl}">首页</a>
    <a href="${targetUrl}?pageNoStr=${page.prev}">上一页</a>
</c:if>

[当前第${page.pageNo}/${page.totalPageNo}页 共${page.totalRecordSize }条]
<input id="go" type="text" name="pageNoStr" class="shortInput">
<c:if test="${page.hasNext}">
    <a href="${targetUrl}?pageNoStr=${page.next}">下一页</a>
    <a href="${targetUrl}?pageNoStr=${page.totalPageNo}">末页</a>
</c:if>


