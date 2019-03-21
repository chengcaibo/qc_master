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
		personal : {
			// 检查是否全部通过验证，true通过
			check : function() {
				var p = io.personal;
				var count = p.phone.flag;
				count += p.email.flag;
				count += p.vCode.flag;
				var flag = (count == 0);
				$(".code_btn").attr("disabled", !flag);
				layer.alert(flag);
				return flag;
			},
			phone : {
				obj : $("input[name=phoneOld]"),
				/** 验证是否通过，0通过，1未通过  */
				flag : 1,
				/** 验证这个输入框 */
				validation : function() {
					// 获取当前验证对象
					var p = io.personal.phone;
					p.flag = 1;
					// 验证手机号的正则表达式
					var reg = /^1[34578]\d{9}$/;
					// 进行表单验证
					if (p.obj.val() == "") {
						// 输入为空
						io.showDialog(p.obj, "请输入您的手机号", "error");
					} else if (!reg.test(p.obj.val())) {
						// 格式有误
						io.showDialog(p.obj, "请输入正确的手机号", "error");
					} else {
						// 验证通过
						p.flag = 0;
						io.showDialog(p.obj, "手机号输入正确", "success");
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
								io.showDialog(p.obj.parent().parent(), "这个手机号已存在，换一个吧~", "error");
							} else {
								p.flag = 0;
								$("#btn_code").attr("disabled", false);
								io.personal.check();
								io.showDialog(p.obj.parent().parent(), "手机号可以修改", "success");
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
			
			email : {
				obj : $("#email").text(),
				flag : 1,
				validation : function() {
					var p = io.personal.email;
					p.flag = 1;
					// 验证邮箱格式的正则表达式
					var reg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
					if (p.obj.val() == "") {
						io.showDialog(p.obj.parent().parent(), "请输入您的邮箱地址", "error");
					} else if (!reg.test(p.obj.val())) {
						io.showDialog(p.obj.parent().parent(), "请输入正确的邮箱地址", "error");
					} else {
						// 验证通过
						p.flag = 0;
						io.showDialog(p.obj.parent().parent(), "邮箱地址输入正确", "success");
						return true;
					}
					return false;
				},
				ajax : function() {
					// 获取当前验证对象
					var p = io.personal.email;
					if (p.flag > 0) return;
					p.flag = 1;
					$.ajax({
						url : "/api/validation",
						dataType : "JSON",
						data : {
							type : "personal-email",
							value : p.obj.val()
						},
						beforeSend : function() {
							io.ajaxCount++;
							p.obj.attr("disabled", true);
							io.showDialog(p.obj.parent().parent(), "正在验证...", "warn");
						},
						success : function(r) {
							if (r.ret == qc.retEnum.VALUE_EXIST) {
								io.showDialog(p.obj.parent().parent(), "这个邮箱地址已经被使用了，换一个吧~", "error");
							} else {
								p.flag = 0;
								io.personal.check();
								io.showDialog(p.obj.parent().parent(), "邮箱地址可以使用", "success");
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
				obj : $("input[name=vCode]"),
				getDialog : function() {
					return io.personal.vCode.obj.parent().parent()
				},
				flag : 1,
				// 验证码私有属性，是否以获取
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
							console.info(r.ret);
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
		},
	};

	
	
	// 绑定获取验证码事件
	$("#btn_code").click(function() {
		var p = io.personal;
		var obj = $(this);
		// 发送ajax请求验证码
		$.ajax({
			url : "/api/sms/send_vcode_sms",
			dataType : "JSON",
			type : "POST",
			data : {
				phone : p.phone.obj.val()
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

})();