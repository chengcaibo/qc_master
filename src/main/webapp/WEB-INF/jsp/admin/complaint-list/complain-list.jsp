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
<jsp:include page="/WEB-INF/jsp/admin/layout/nav-left-complain.jsp" />
	<!-- layui 数据表格 -->
	<div class="layui-body">
		<div class="layui-card">
			<div class="layui-card-header">用户被投诉信息</div>
			<div class="layui-card-body">
				<table id="demo" lay-filter="test" ></table>
			</div>
		</div>
	</div>

<script src="/lib/layui/layui.js"></script>
<script type="text/html" id="templet-personal-action">
	<div id="table-btns">
		<a href="" class="layui-btn layui-btn-normal layui-btn-xs" target="_blank">拉黑投诉人</a>
		<%--<a href="javascript:sayNoOpen();" class="layui-btn layui-btn-danger layui-btn-xs">拉黑被投诉人</a>--%>
		<%--<a href="javascript:sayNoOpen();" class="layui-btn layui-btn-danger layui-btn-xs">解除</a>--%>
	</div>
</script>
<script>
    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#demo'
            ,height: 500
            ,url: '/complain/complaintList/${userId}' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                /*{field: 'id', title: '投诉id', sort: true, fixed: 'left'}*/
                {field: 'complainant', width:100,title: '投诉者的id'}
                ,{field: 'complainantNickName',width:100, title: '投诉者昵称'}
                ,{field: 'respondent',width:150, title: '被投诉者的id'}
                ,{field: 'respondentNickName', width:150,title: '被投诉者昵称'}
                ,{field: 'complaintDetails', width:150,title: '投诉内容'}
                ,{field: 'demandId',width:150, title: '被投诉的需求id'}
                ,{field: 'content',width:150, title: '被投诉的需求内容'}
                ,{field: 'createdTime', title: '投诉的时间'}
            ]],
            done: function (res, curr, count) {
                $('tr').css({'margin-left':'200px'});
            }

        });

    });
</script>
</div>
</body>
</html>