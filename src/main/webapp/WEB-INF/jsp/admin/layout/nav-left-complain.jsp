<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- begin 左侧导航栏 -->
<div id="left-nav" class="layui-side layui-bg-black">
	<div class="layui-side-scroll">
		<ul class="layui-nav layui-nav-tree" lay-filter="left-nav">
			<li class="layui-nav-item">
				<a href="/admin/blacklist/detail/${userId}">投诉信息</a>
			</li>
			<li class="layui-nav-item">
				<a href="/admin/Allege/AllegeInfo/${userId}">申诉信息</a>
			</li>
		</ul>
	</div>
</div>
<!-- end 左侧导航栏 -->