<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- begin 左侧导航栏 -->
<div id="left-nav" class="layui-side layui-bg-black">
	<div class="layui-side-scroll">
		<ul class="layui-nav layui-nav-tree" lay-filter="test">
			<li class="layui-nav-item layui-nav-itemed">
				<a href="javascript:;">个人用户管理</a>
				<dl class="layui-nav-child">
					<dd>
						<a href="/admin/users/personal">管理个人用户</a>
					</dd>
					<dd>
						<a href="/admin/users/personal/addto">添加个人用户</a>
					</dd>
				</dl>
			</li>
			<li class="layui-nav-item">
				<a href="javascript:;">企业用户管理</a>
				<dl class="layui-nav-child">
					<dd>
						<a href="javascript:sayNoOpen();">管理企业用户</a>
					</dd>
					<dd>
						<a href="javascript:sayNoOpen();">添加企业用户</a>
					</dd>
				</dl>
			</li>
		</ul>
	</div>
</div>
<!-- end 左侧导航栏 -->