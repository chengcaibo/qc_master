<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>table模块快速使用</title>
	<jsp:include page="/WEB-INF/jsp/admin/common/head.jsp" />
	<title>个人用户管理 - 奇虫新管理后台</title>
	<link href="/admin/css/personal/manage.css" rel="stylesheet">
</head>
<body>
<div class="layui-layout layui-layout-admin">
<jsp:include page="/WEB-INF/jsp/admin/layout/nav-top.jsp" />
	<%--<jsp:include page="/WEB-INF/jsp/admin/layout/nav-left-complain.jsp" />--%>
	<div class="layui-card-header">黑名单</div>
	<table id="demo" lay-filter="test" ></table>
	<!-- layui 数据表格 -->
<%--	<div class="layui-body">
		<div class="layui-card">
			<div class="layui-card-header">黑名单管理</div>
			<div class="layui-card-body">

			</div>
		</div>
	</div>--%>

<script src="/lib/layui/layui.js"></script>
	<script type="text/html" id="templet-personal-action">
		<div id="table-btns">
			<a href="/admin/blacklist/detail/{{d.userId}}" class="layui-btn layui-btn-normal layui-btn-xs" target="_blank">详情</a>
		</div>
	</script>
<script>
    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#demo'
            ,height: 500
            ,url: '/complain/blackList' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'userId', title: 'ID', sort: true, fixed: 'left'}
                ,{field: 'nickName', title: '用户名'}
                ,{field: 'telephone', title: '电话号'}
                ,{field: 'reportedTimes', title: '被举报次数', sort: true}
                ,{field: 'reason', title: '拉黑原因'}
                ,{title : "操作", unresize : true, fixed : "right",width:100,templet : "#templet-personal-action",}
            ]],
            done: function (res, curr, count) {
                $('tr').css({'margin-left':'200px'});
            }

        });
        table.on('tool(test)', function(obj){
         /*   $.ajax({
                url: "/admin/blacklist/detail",
                type: "GET",
                data:{"userId":obj.data.userId},
                success: function(res){
					console.log(res)
                    if(res.ret==1){
                        //删除这一行
                        obj.del();
                        //关闭弹框
                        layer.close(index);
                        layer.msg("拉黑成功", {icon: 6});
                    }else{
                        layer.msg("拉黑失败", {icon: 5});
                    }
                }
            });*/
        });

    });
</script>
</div>
</body>
</html>