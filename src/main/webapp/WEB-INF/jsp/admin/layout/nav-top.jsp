<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- begin 头部导航栏 -->
<div class="layui-header">
	<div class="layui-logo">
		<a href="/admin" title="返回主页">
			<img alt="奇虫logo" src="https://img.qc1318.com/logo-qc-mini-white.png?x-oss-process=image/resize,h_30">
		</a>
	</div>
	<!-- 头部区域（可配合layui已有的水平导航） -->
	<ul id="top-nav" class="layui-nav layui-layout-left">
		<li class="layui-nav-item">
			<a href="/admin/home">管理主页</a>
		</li>
		<li class="layui-nav-item">
			<a href="/admin/users">用户管理</a>
		</li>
		<li class="layui-nav-item">
			<a href="/admin/audits">审核信息</a>
		</li>
		<li class="layui-nav-item">
			<a href="/admin/blacklist">黑名单</a>
		</li>
		<li class="layui-nav-item">
			<a href="/admin/complain/complainlist">投诉名单</a>
		</li>
	</ul>
	<ul class="layui-nav layui-layout-right">
		<li class="layui-nav-item" style="display: none;">
			<a id="btn-reload" href="javascript:;" title="刷新">刷新</a>
		</li>
		<li class="layui-nav-item">
			<a href="/">返回前台</a>
		</li>
		<li class="layui-nav-item">
			<a href="javascript:;">
				<img src="https://img.qc1318.com/logo-qc-avatar.png?x-oss-process=image/resize,w_50" class="layui-nav-img"> 管理员
			</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="javascript:layer.alert('暂未开放');">修改密码</a>
				</dd>
				<dd>
					<a id="logoff" href="javascript:;">退出登录</a>
				</dd>
			</dl>
		</li>
	</ul>
</div>
<!-- end 顶部导航栏 -->