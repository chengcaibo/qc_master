(function() {
	/** input Object 的缩写，意思是各个输入框都集中在这里 */
	var io = {
		/** 
		 * 每发送一次ajax就 加1 ，每一个ajax请求结束后，无论是成功还是失败都要 减1
		 * 当此值为0的时候才进行表单提交操作
		 *  */
		ajaxCount : 0,
		showDialog : function(obj, text, cls) {
			var parent = "form-item-"
			obj.removeClass(parent + "success").removeClass(parent + "error").removeClass(parent + "warn")
			if (cls) {
				obj.addClass(parent + cls);
				if (cls == "warn") {
					obj.find(".input-dialog").html("<i class='icon-loading common-icon-loading'>&nbsp;</i>" + text);
				} else {
					obj.find(".input-dialog").html("<i class='icon-" + cls + "'>&nbsp;</i>" + text);
				}
			} else {
				obj.find(".input-dialog").html("<i class='icon-info'>&nbsp;</i>" + text);
			}
		},
		/** 个人注册 */
		personal : {
			// 检查是否全部通过验证，true通过
			check : function() {
				var p = io.personal;
				var count = p.phone.flag;
				if (typeof ignoreVCode == "undefined")
					count += p.vCode.flag;
				var flag = (count == 0);
				$(".sign-personal .submit-button").attr("disabled", !flag);
				return flag;
			},
			phone : {
				obj : $(".sign-personal input[name=phone]"),
				/** 验证是否通过，0通过，1未通过  */
				flag : 1,
				/** 验证这个输入框 */
				validation : function() {
					// 获取当前验证对象
					var p = io.personal.phone;
					p.flag = 1;
					// 验证手机号的正则表达式
					var reg = /^1[345678]\d{9}$/;
					// 进行表单验证
					if (p.obj.val() == "") {
						// 输入为空
						io.showDialog(p.obj.parent().parent(), "请输入您的手机号", "error");
					} else if (!reg.test(p.obj.val())) {
						// 格式有误
						io.showDialog(p.obj.parent().parent(), "请输入正确的手机号", "error");
					} else {
						// 验证通过
						p.flag = 0;
						io.showDialog(p.obj.parent().parent(), "手机号输入正确", "success");
						return true;
					}
					return false;
				},
				/** 发送ajax请求，验证值是否存在 */
				ajax : function() {
					// 获取当前验证对象
					var p = io.personal.phone;
					if (p.flag > 0) return;
					p.flag = 1;
					$.ajax({
						url : "/api/validation",
						dataType : "JSON",
						data : {
							type : "personal-phone",
							value : p.obj.val()
						},
						beforeSend : function() {
							io.ajaxCount++;
							p.obj.attr("disabled", true);
							io.showDialog(p.obj.parent().parent(), "正在验证...", "warn");
						},
						success : function(r) {
							if (r.ret == qc.retEnum.VALUE_EXIST) {
								io.showDialog(p.obj.parent().parent(), "这个手机号已经被注册了，换一个吧~", "error");
							} else {
								p.flag = 0;
								$("#get-personal-vCode").attr("disabled", false);
								io.personal.check();
								io.showDialog(p.obj.parent().parent(), "手机号可以注册", "success");
							}
						},
						error : function() {
							io.showDialog(p.obj.parent().parent(), "发生了未知错误，请稍后重试...", "error");
						},
						complete : function() {
							io.ajaxCount--;
							p.obj.attr("disabled", false);
						}
					});
				}
			},
			vCode : {
				obj : $(".sign-personal input[name=vCode]"),
				getDialog : function() {
					return io.personal.vCode.obj.parent().parent()
				},
				flag : 1,
				// 验证码私有属性，是否已获取
				get : false,
				validation : function() {
					var p = io.personal.vCode;
					if (!p.get) return;
					p.flag = 1;
					// 验证短信验证码格式的正则表达式
					var reg = /^\d{6}$/;
					if (p.obj.val() == "") {
						io.showDialog(p.getDialog(), "请输入您的验证码", "error");
					} else if (!reg.test(p.obj.val())) {
						io.showDialog(p.getDialog(), "验证码为六位数字", "error");
					} else {
						// 验证通过
						p.flag = 0;
						io.showDialog(p.getDialog(), "验证码格式正确", "success");
						return true;
					}
					return false;
				},
				ajax : function() {
					// 获取当前验证对象
					var p = io.personal.vCode;
					if (p.flag > 0) return;
					p.flag = 1;
					$.ajax({
						url : "/api/validation",
						dataType : "JSON",
						data : {
							type : "vCode",
							value : p.obj.val(),
							phone : io.personal.phone.obj.val()
						},
						beforeSend : function() {
							io.ajaxCount++;
							p.obj.attr("disabled", true);
							io.showDialog(p.getDialog(), "正在验证...", "warn");
						},
						success : function(r) {
							if (r.ret == qc.retEnum.VCODE_ERROR) {
								io.showDialog(p.getDialog(), "验证码不正确", "error");
							} else if (r.ret == qc.retEnum.VCODE_NOT_EFFECTIVE) {
								io.showDialog(p.getDialog(), "验证码已过期，请重新获取", "error");
							} else if (r.ret == qc.retEnum.SUCCESS) {
								p.flag = 0;
								io.personal.check();
								io.showDialog(p.getDialog(), "验证码输入正确", "success");
							} else {
								io.showDialog(p.getDialog(), "发生了未知错误，请稍后重试...", "error");
							}
						},
						error : function() {
							io.showDialog(p.getDialog(), "发生了未知错误，请稍后重试...", "error");
						},
						complete : function() {
							io.ajaxCount--;
							p.obj.attr("disabled", false);
						}
					});
				}
			},
		/** 企业注册 */
		enterprise : {}
	};

	var temp = {};
	// 绑定[个人-手机]事件
	temp = io.personal.phone;
	temp.obj.blur(temp.validation).blur(temp.ajax);
	temp.obj.bind("input propertychange", temp.validation);
	// 绑定[个人-手机验证码]事件
	temp = io.personal.vCode;
	temp.obj.blur(temp.validation).blur(temp.ajax);
	temp.obj.bind("input propertychange", temp.validation);

	// 绑定[个人-协议]事件
	io.personal.protocol.obj.change(io.personal.protocol.validation);


	// 绑定 查看密码 事件
	$(".toggle-pwd").click(function() {
		if ($(this).hasClass("icon-pwd-hide")) {
			$(this).prev().attr("type", "text");
			$(this).removeClass("icon-pwd-hide");
			$(this).addClass("icon-pwd-show");
		} else {
			$(this).prev().attr("type", "password");
			$(this).removeClass("icon-pwd-show");
			$(this).addClass("icon-pwd-hide");
		}
	});

	// 绑定获取验证码事件
	$("#get-personal-vCode").click(function() {
		var p = io.personal;
		var obj = $(this);
		// 判断手机号是否正确
		if (p.phone.flag > 0) {
			obj.attr("disabled", true);
			return;
		}
		// 发送ajax请求验证码
		$.ajax({
			url : "/api/sms/send_vcode_sms",
			dataType : "JSON",
			type : "POST",
			data : {
                phoneNumber : p.phone.obj.val()
			},
			beforeSend : function() {
				io.ajaxCount++;
				p.vCode.get = true;
				io.showDialog(p.vCode.getDialog(), "验证码发送中...", "warn");
				obj.attr("disabled", true);
			},
			success : function(r) {
				if (r.ret == qc.retEnum.SUCCESS) {
					io.showDialog(p.vCode.getDialog(), "验证码发送成功");
					// 60秒后重试
					obj.attr("disabled", true).text("请在60秒后重试");
					var time = 60;
					var emmm = setInterval(function() {
						time--;
						if (time <= 0) {
							obj.attr("disabled", false).text("重新获取");
							// 关闭定时器
							clearInterval(emmm);
						} else {
							obj.attr("disabled", true).text("请在" + time + "秒后重试");
						}
					}, 1000);
				} else {
					io.showDialog(p.vCode.getDialog(), "验证码发送失败，请稍后重试...", "error");
					obj.attr("disabled", false);
					// TODO:console.log
					console.log(r.msg);
				}
			},
			error : function() {
				io.showDialog(p.vCode.getDialog(), "发生了未知错误，请稍后重试...", "error");
				obj.attr("disabled", false);
			},
			complete : function() {
				io.ajaxCount--;
			}
		});
	});


	var personalSign = function() {
		// 注册
		var p = io.personal;
		var obj = $(".sign-personal .submit-button");
		$.ajax({
			url : "/sign/personal",
			type : "POST",
			async : false, // 这是一个同步请求
			dataType : "JSON",
			data : {
				"telephone" : p.phone.obj.val(),
				"user.password" : p.password.obj.val(),
				"email" : p.email.obj.val()
			},
			beforeSend : function() {
				io.ajaxCount++;
				obj.html("<i class='icon-loading'></i>正在注册...");
			},
			success : function(r) {
				if (r.ret == qc.retEnum.PAR_LACK) {
					layer.alert("注册失败，系统监测到您恶意更改了网页，需刷新后重新注册！");
					window.location.reload();
				} else if (r.ret == qc.retEnum.SUCCESS) {
					$(".sign-main").addClass("common-hide");
					$("#sign-ok").removeClass("common-hide");
				} else {
					layer.alert("发生了未知错误，请稍后重试...");
				}
			},
			error : function() {
				layer.alert("发生了未知错误，请稍后重试...");
			},
			complete : function() {
				io.ajaxCount--;
				obj.html("<i class=''>&nbsp;</i>立即注册");
			}
		});
	}

	// 绑定个人注册按钮
	$(".sign-personal .submit-button").click(function() {
		if (io.personal.check()) {
			if (io.ajaxCount != 0) {
				var obj = $(this).html("<i class='icon-loading'></i>请稍后...");
				var emmm = setInterval(function() {
					if (io.ajaxCount <= 0) {
						obj.html("<i class='icon-loading'></i>正在注册...");
						clearInterval(emmm);
						personalSign();
					}
				}, 300);
			} else {
				personalSign();
			}
		}
	});



})();

var luoFlag = false;
function personalCallback(event) {
	luoFlag = true;
}