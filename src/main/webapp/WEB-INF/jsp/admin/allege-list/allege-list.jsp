<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>table模块快速使用</title>
	<jsp:include page="/WEB-INF/jsp/admin/common/head.jsp" />
	<title>个人用户管理 - 奇虫新管理后台</title>
	<link href="/admin/css/personal/manage.css" rel="stylesheet">
	<style type="text/css">
		.layui-table img{
			height: 100%;
			width: 100%;
		}
		.layui-table-cell{
			text-align: center;
			height: auto!important;
			white-space: normal;
		}
	</style>
</head>
<body>
<div class="layui-layout layui-layout-admin">
<jsp:include page="/WEB-INF/jsp/admin/layout/nav-top.jsp" />
	<jsp:include page="/WEB-INF/jsp/admin/layout/nav-left-complain.jsp" />


	<!-- layui 数据表格 -->
	<div class="layui-body">
		<div class="layui-card">
			<div class="layui-card-header" style="font-weight: bold;font-size: medium">用户 ${userId} 申诉信息(注意，图片找不到代表用户未上传图片)</div>
			<div class="layui-card-body">
				<table id="demo" lay-filter="test" ></table>
			</div>
			<div class="layui-card-header" style="font-weight: bold;font-size: medium">申诉成功的信息</div>
			<div class="layui-card-body">
				<table id="allegeResultSuccess" lay-filter="allege" ></table>
			</div>
			<div class="layui-card-header" style="font-weight: bold;font-size: medium">申诉失败的信息</div>
			<div class="layui-card-body">
				<table id="allegeResultFail" lay-filter="allege" ></table>
			</div>
		</div>
	</div>

<script src="/lib/layui/layui.js"></script>
<script type="text/html" id="templet-personal-action">
	<div class="layui-btn-container">
		<button class="layui-btn layui-btn-sm" lay-event="accept">接受申诉</button>
		<button class="layui-btn layui-btn-sm" lay-event="reject">驳回申诉</button>
	</div>
</script>
<script>
    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#demo'
            ,height: 200
            ,url: '/allege/allegeResult/${userId}' //数据接口
            ,cols: [[ //表头
                {field: 'id', title: 'ID', style:'height:100px;',sort: true, fixed: 'left'}
                ,{field: 'allegeContent', title: '申诉内容'}
                ,{field: 'allegeImage', title: '申诉图片',templet:'<div ><img onclick="previewImg(this)"  src="https://img.qc1318.com/img/allege/{{d.allegeImage}}" ></div>'}
                ,{field: 'userId', title: '申诉者的id'}
                ,{field: 'createdTime', title: '申诉时间', sort: true}
                ,{title : "操作", unresize : true, fixed : "right",width:100,templet : "#templet-personal-action"}
            ]],
            done: function (res, curr, count) {
                $('tr').css({'margin-left':'200px'});
            }

        });

    });
    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#allegeResultSuccess'
            ,height: 500
            ,url: '/allege/getAllegeInfoSuccess/${userId}' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'id', title: 'ID', style:'height:100px;',sort: true, fixed: 'left'}
                ,{field: 'allegeContent', title: '申诉内容'}
                ,{field: 'allegeImage', title: '申诉图片',templet:'<div ><img onclick="previewImg(this)"  src="https://img.qc1318.com/img/allege/{{d.allegeImage}}" ></div>'}
                ,{field: 'userId', title: '申诉者的id'}
                ,{field: 'createdTime', title: '申诉时间', sort: true}
            ]],
            done: function (res, curr, count) {
                $('tr').css({'margin-left':'200px'});
            }

        });

    });
    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#allegeResultFail'
            ,height: 500
            ,url: '/allege/getAllegeInfoFailed/${userId}' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'id', title: 'ID', style:'height:100px;',sort: true, fixed: 'left'}
                ,{field: 'allegeContent', title: '申诉内容'}
                ,{field: 'allegeImage', title: '申诉图片',templet:'<div ><img onclick="previewImg(this)"  src="https://img.qc1318.com/img/allege/{{d.allegeImage}}" ></div>'}
                ,{field: 'userId', title: '申诉者的id'}
                ,{field: 'createdTime', title: '申诉时间', sort: true}
                ,{field : "allegeResult", title: '失败原因',unresize : true, fixed : "right"}
            ]],
            done: function (res, curr, count) {
                $('tr').css({'margin-left':'200px'});
            }

        });
        //监听事件
        table.on('tool(test)', function(obj){
            console.log(obj)
            switch(obj.event){
                case 'accept':
                    layer.confirm('确认接受申诉吗',
                    	function(value, index){
                        //这里一般是发送修改的Ajax请求
                        $.ajax({
                            url: "/allege/accept",
                            type: "POST",
                            data:{"userId":${userId}},
                            dataType: "json",
                            success: function(res){

                                if(res.ret==1){
                                    //删除这一行
                                    //obj.del();
                                    location.reload();
                                    layer.msg("操作成功", {icon: 6});
                                }else{
                                    layer.msg("操作失败", {icon: 5});
                                }
                            }
                        });
                    });
                    break;
                case 'reject':
                    layer.prompt({
                        formType: 2
                        ,title: '驳回原因'
                    }, function(value, index){
                        //这里一般是发送修改的Ajax请求
                        $.ajax({
                            url: "/allege/allegeDisposeResult",
                            type: "POST",
                            data:{"userId":${userId},"allegeResult":value},
                            dataType: "json",
                            success: function(res){

                                if(res.ret==1){
                                    //删除这一行
                                    //obj.del();
                                    //关闭弹框
                                    location.reload();
                                    layer.msg("操作成功", {icon: 6});
                                }else{
                                    layer.msg("操作失败", {icon: 5});
                                }
                            }
                        });
                    });

                    break;
            }
        });

    });
    function previewImg(obj) {
        var img = new Image();
        img.src = obj.src;
        var imgHtml = "<img src='" + obj.src + "' width='500px' height='500px'/>";
        //弹出层
        layer.open({
            type: 1,
            shade: 0.8,
            offset: 'auto',
            area: [500 + 'px',550+'px'],
            shadeClose:true,
            scrollbar: false,
            title: "图片预览", //不显示标题
            content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function () {
                //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
            }
        });
    }
</script>

</div>
</body>
</html>