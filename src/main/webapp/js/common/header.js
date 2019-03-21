(() => {
	// AJAX轮询请求是否已登录
	function isLogin() {
		$.ajax({
			url : "/user/is_login",
			success : (res) => {
				if (res.ret != qc.retEnum.SUCCESS) {
					$(".nav-sign").removeClass("common-hide");
					$(".nav-uesr-info").removeClass("common-show-inline-block")
					layer.alert("检测到您的账号已在另一台电脑上登录，您已被强制下线！如果不是您本人操作，说明您的账号密码可能已经泄露，请尽快修改密码！", {
						icon : 0
					}, () => {
						window.location.href = "/logoff";
					});
				} else {
					isLogin(); // 重新进行查询
				}
			},
			error : () => {
				setTimeout(function() {
					isLogin();
				}, 10000)
			}
		});
	}

	$(function() {
		if ($(".nav-sign").hasClass("common-hide")) {
			isLogin();
		}
	})
})();