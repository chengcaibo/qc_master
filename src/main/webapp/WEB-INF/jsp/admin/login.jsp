<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >
<html>
<head>
<jsp:include page="../common/head.jsp" />
<title>请登录</title>
<style type="text/css">
.main {
	width: 800px;
	margin: 100px auto 0;
	background-color: white;
	padding: 100px 50px;
}

input, button {
	width: 100%;
	height: 32px;
}

input {
	margin-bottom: 30px;
	font-size: 32px;
	text-align: center;
	padding: 0 20px;
}

button {
	height: 42px;
}

.btn-login {
	background-color: #09f;
}
</style>
</head>
<body>
	<div class="main">
		<img alt="奇虫Logo" src="/img/qichong-new-logo.png">
		<h1 style="text-align: center;line-height: 100px">管理后台 | 请输入口令</h1>
		<input id="password" type="password" autocomplete="off">
		<button id="login" class="btn btn-primary btn-login">立即登入</button>
	</div>
</body>
<script>
	(function() {
		let input = $("#password").focus();
		let index = -1;

		$("#login").click(function() {
			if (!input.val()) {
				index = layer.alert("请输入口令", (index) => {
					layer.close(index);
					input.focus();
				});
			} else {
				var loadingIndex = layer.load(1);

				$.ajax({
					url : "/admin/login",
					type : "POST",
					data : {
						loginKey : input.val()
					},
					success (res) {
						if (res.ret == 0) {
							window.location.reload();
						} else {
							input.val("");
							index = layer.alert("口令错误", (index) => {
								layer.close(index);
								input.focus();
							});
						}
					},
					error () {
						index = layer.alert("发生异常...");
					},
					complete () {
						layer.close(loadingIndex);
					}
				});
			//关闭
			}
		});

		// 监听文本框回车事件
		input.keydown(function(e) {
			var curKey = e.which;
			if (curKey == 13) {
				$("#login").click();
				return false;
			}
		});
	})();
</script>
</html>
