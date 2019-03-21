<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="ZH">

<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>个人注册 - 奇虫</title>
<link rel="stylesheet" href="/css/sign/signup-common.css">

<!-- 螺丝帽的验证 -->
<script src="//captcha.luosimao.com/static/dist/api.js"></script>
</head>

<body>
	<!-- 导航栏 -->
	<jsp:include page="/header" />
	<div class="body-main">
		<div class="sign-main">
			<div class="sign-welcome">欢迎注册奇虫</div>
			<div class="goto-login">
				已有奇虫账号？点击立即去<a href="/login" title="点我去登录"> 登录 &gt;</a>
			</div>
			<div class="toggle toggle-sign">
				<a class="btn toggle-button toggle-this" href="javascript:;"><i class="icon-personal"></i><span>个人账户</span></a>
				<a class="btn toggle-button" href="/signup-enterprise"><i class="icon-enterprise"></i><span>企业账户</span></a>
			</div>
			<!-- 用户名长度为3-24位，不能包含除下划线（_）以外的特殊符号 -->
			<!-- 昵称长度为2-16位，可用中文，不能包含除下划线（_）以外的特殊符号 -->
			<div class="sign-item sign-personal common-show">
				<!-- 手机号码表单 -->
				<div class="form-item">
					<div class="input-box">
						<i class="icon-phone"></i><input class="input-master" name="phone" type="text" placeholder="请输入您的手机号码"
							autocomplete="off">
					</div>
					<div class="input-dialog">
						<i class="icon-info">&nbsp;</i>请输入您的手机号码，将用作登录
					</div>
				</div>
				<!-- 密码表单 -->
				<div class="form-item">
					<div class="input-box">
						<i class="icon-pwd"></i><input class="input-master" name="password" type="password" placeholder="请设置密码"
							autocomplete="off"><i class="toggle-pwd icon-pwd-hide"></i>
					</div>
					<div class="input-dialog">
						<i class="icon-info">&nbsp;</i>密码长度为6-18位，可以使用!@#$%^&amp;*()[]-+=.,_~特殊符号
					</div>
				</div>
				<!-- 邮箱地址表单 -->
				<div class="form-item">
					<div class="input-box">
						<i class="icon-email"></i><input class="input-master" name="email" type="text" placeholder="请输入您的邮箱地址"
							autocomplete="off">
					</div>
					<div class="input-dialog">
						<i class="icon-info">&nbsp;</i>请输入您的邮箱地址，可用作登陆
					</div>
				</div>
				<!-- 手机验证码表单 -->
				<div class="form-item">
					<div class="input-box input-mini">
						<i class="icon-sms-code"></i><input class="input-master" name="vCode" type="text" placeholder="请输入您的验证码"
							autocomplete="off">

					</div>
					<button disabled class="input-button btn btn-default" id="get-personal-vCode">点击获取验证码</button>
					<div class="input-dialog">
						<i class="icon-info">&nbsp;</i>请点击获取验证码
					</div>
				</div>
				<!-- 人机验证 -->
				<div class="form-item">
					<div class="input-box input-no-border">
						<div class="l-captcha" data-site-key="19a8ea866d6aecfb391e7a48e76c0c6f" data-width="100%"
							data-callback="personalCallback"></div>
					</div>
					<div class="input-dialog">
						<i class="">&nbsp;</i>
					</div>
				</div>
				<!-- 立即注册 -->
				<div class="form-item">
					<button disabled class="btn submit-button">
						<i class="">&nbsp;</i>立即注册
					</button>
					<div class="input-dialog"></div>
				</div>
				<!-- 服务条款 -->
				<div class="form-item form-item-left">
					<label><input class="input-protocol" type="checkbox" checked name="protocol" />我已阅读并同意《<a href="/protocol/signup.html">奇虫平台使用服务条款</a>》</label>
				</div>
			</div>
		</div>
		<div id="sign-ok" class="sign-main common-hide">
			<div class="sign-welcome">恭喜您注册成功！</div>
			<div class="sign-ok">
				<p>
					欢迎加入奇虫，现在您可以在这里尽情的发布需求了，如果您想要“接单”、“接需求”，您需要立即去<a class="bigger-a" href="#">完善资料</a>！
				</p>
				<i class="icon-success bigger-ok"></i><a href="/login" class="btn submit-button">立即登录</a>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="/js/sign/personal.js"></script>
</body>
</html>