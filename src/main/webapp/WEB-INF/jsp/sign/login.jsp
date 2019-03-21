<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ZH">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>登录</title>
<link rel="stylesheet" href="/css/sign/login.css" />
</head>
<body>
	<jsp:include page="/header" />

	<div class="type-area">
		<form action="login(this.from)" method="post">
			<div class="image-collection" style="position: relative;">
				<img src="img/login-sign/img/biao.png" alt="" class="qichong" /> <img
					src="img/login-sign/img/beng.png" alt="" class="beng"
					style="position: absolute;left: 0;top: 53px;" />
			</div>

			<div class="denglu">
				<p class="dengluP">欢&nbsp;迎&nbsp;登&nbsp;录</p>
				<input type="text" name="username" placeholder="手机号/邮箱/用户名/企业名"
					class="name" id="namea"/> <input
					type="password" name="password" placeholder="密码" class="password"
					id="passworda" />

				<!-- 	<button class="dl">登&nbsp;&nbsp;&nbsp;&nbsp;录</button> -->
				<input class="dl" type="submit" value="登&nbsp;&nbsp;&nbsp;&nbsp;录" />
				<a class="zc" href="/signup-personal">立即注册</a> <a class="wj">忘记密码</a>

				<!-- <div class="xiaobiao2">
					<a href="###"> <img src="img/login-sign/img/QQ.png" alt="" />
					</a> <a href="###"> <img src="img/login-sign/img/weibo.png" alt="" />
					</a> <a href="###" style="margin:0"> <img
						src="img/login-sign/img/weixin.png" alt="" />
					</a>
				</div>
 -->
				<div class="wt" style="display: none;" id="zhwt">账号有问题</div>
				<div class="mmcc" style="display: none;" id="mmcc">密码出错</div>
			</div>
		</form>
	</div>

	<div style="display:none" class="password-bounced">
		<div class="dialog-child" data-type="phone">
			<div class="form-item">
				<div class="pass_h3">
					<span>找回密码</span><span class="shut-down">x</span>
				</div>
				<ul class="modify_phone">
					<li>
						<input type="hidden" name="userId" value="" id="id">
						<span class="phone_span">手机号：</span> 
						<input id="phone" type="text" name="telephone" placeholder="请输入手机号" onblur="checkIsExist();" /> 
						<span id="phoneResult"></span>
						<!-- <p class="phone-prompt-one">输入有误</p>
						<p class="phone-prompt-two">不能为空</p> -->
					</li>
					<li>
						<span class="phone_span" style="padding-right: 5px;">验证码：</span>
						<input type="text" name="vCode" id="code" style="width: 141px;margin-left: -5px;" placeholder="请输入验证码" autocomplete="off" onclick="vCodes()" /> 
						<span id="codeResult"></span>
						<button class="code_btn" id="btn_code" type="button">免费获取验证码</button>
					</li>
					<li>
						<span class="pass_span">新密码：</span> 
						<input id="new_pwd" type="password" name="password" placeholder="请输入新密码"> 
						<span class="prompt_n"></span>
						<!-- <p class="prompt-one">输入有误</p>
						<p class="prompt-two">密码不能空</p></li> -->
					<li>
						<button class="cinfirm-change" type="button">确认修改</button>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
<script src="/js/login-sign/login.js"></script>
</html>