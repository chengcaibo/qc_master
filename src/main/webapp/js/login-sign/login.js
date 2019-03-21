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
				obj : $("input[name=telephone]"),
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
			url : "/api/sms/send_password_vcode_sms",
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




$(function() {
	$(".cinfirm-change").click(function() {
		var userId = $("#id").val();
		var password = $("#new_pwd").val();
		var vCode = $("#code").val();

		if (!password) {
			layer.alert("请输入您的新密码");
		} else if (!vCode) {
			layer.alert("请获取并输入验证码");
		} else if (vCodeCanUse == false) {
			layer.alert("验证码错误或已过期！");
		} else {
			changePassword(userId, password, vCode);
		}
	});
});


function changePassword(userId, password, vCode) {
	$.ajax({
		url : "/user-password",
		type : "POST",
		dataType : "JSON",
		data : {
			userId : userId,
			password : password,
			vCode : vCode
		},
		success : function(r) {
			if (r.ret == 0) {
				layer.msg("密码修改成功！");
				var mask = $(".dialog-mask").css("opacity", "0");
				setTimeout(function() {
					mask.css("display", "none")
				}, 300);
				// 隐藏所有child
				$(".dialog-child").css("display", "none");
				window.location.reload();
			} else {
				layer.alert('修改失败：' + r.msg);
			}
		}
	});
}





function vCodes() {
	var value = $("#code").val();
	var phone = $("input[name=telephone]").val();
	if (value == "") {
		$("#codeResult").css({
			"color" : "red"
		}).html("验证码不能为空");return false;
	} else {
		$("#codeResult").css({
			"color" : "red"
		}).html("");
	}
	checkVcode(value, phone);
}

// 定义 验证码 是否可以使用，true 代表可以
var vCodeCanUse = false;

//判断验证码是否相同
function checkVcode(value, phone) {
	$.ajax({
		url : "/api/validation", //发送给服务器的url  
		//type : "GET", //http请求方式  
		data : {
			type : "vCode",
			value : value,
			phone : phone
		},
		dataType : "JSON",
		success : function(r) {
			vCodeCanUse = false;
			if (r.ret == qc.retEnum.VCODE_ERROR) {
				$("#codeResult").css({
					"color" : "red"
				}).html("验证码不正确");
			} else if (r.ret == qc.retEnum.VCODE_NOT_EFFECTIVE) {
				$("#codeResult").css({
					"color" : "red"
				}).html("验证码已过期，请重新获取");
			} else if (r.ret == qc.retEnum.SUCCESS) {
				$("#codeResult").css({
					"color" : "green"
				}).html("验证码正确");
				vCodeCanUse = true;
			} else {
				$("#codeResult").css({
					"color" : "red"
				}).html("发生错误");
			}

		}
	});
}

var userId;

//判断手机号是否存在
function checkIsExist() {
	var telephone = $("#phone").val();
	if (telephone == "") {
		$("#phoneResult").css({
			"color" : "red"
		}).html("请输入手机号");return false;
	}
	$.ajax({
		url : "/account/forget/check_phone", //发送给服务器的url  
		type : "GET", //http请求方式  
		data : {
			telephone : telephone
		},
		success : function(res) {
			if (res.ret == qc.retEnum.SUCCESS) {
				userId = res.object.user.id;
				$("#id").val(userId);
				$("#phoneResult").css({
					"color" : "green"
				}).html("手机号可用");
				layer
			} else if (res.ret == qc.retEnum.VALUE_NOT_EXIST) {
				layer.confirm("这个手机还没有注册，无法修改密码，是否立即去注册？", function(confirmIndex) {
					layer.close(confirmIndex);
					window.open("/signup-personal");
				})
			} else {
				layer.alert("发生了未知错误，请检查您的网络连接是否正常");
			}
		}
	});
}





function login(login) { //传入表单参数

	console.log("进入了login方法：");
	console.log(login);

	if (login.username.value == "") { //验证用户名是否为空
		layer.alert("请输入用户名！");login.username.focus();return false;
	}
	if (login.password.value == "") { //验证密码是否为空
		layer.alert("请输入密码！");login.password.focus();return false;
	}
	$.ajax({
		url : "/login", //将登录的 url
		type : "POST",
		dataType : "json",
		data : {
			username : login.username.value,
			password : login.password.value
		},
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8', //防止乱码
		success : function(data) {
			if (data.ret != 0) {
				layer.alert("您输入的用户名或密码有错！");
				login.username.focus();
				return false;
			} else {
				window.location.href = "/"; //跳转到主页
			}
		}
	});
}

$(function() {
	
	//判断验证
	$("#phone").blur(function(){
		checkIsExist();
	});
	$("#code").blur(function(){
		vCodes();
	});

	$("form").submit(function(event) {
		event.preventDefault();

		var username = $("input[name=username]").get(0);
		var password = $("input[name=password]").get(0);
		login({
			username : username,
			password : password
		});
	});
	$(".wj").click(function() {
		$(".password-bounced").css("display", "block");
	});
	$(".shut-down").click(function() {
		$(".password-bounced").css("display", "none");
	});
});

$(function() {
	var retrievePassword = {
		collective : {
			check : function() {
				var p = retrievePassword.collective;
				var count = p.telephone.flag;
				count += p.passWord.flag;
				var flag = (count == 0);
				$("#btn_pwd").attr("disabled", !flag);
				console.log(flag);
			},
			//手机号判断
			telephone : {
				obj : $("input[name='telephone']"),
				flag : 1,
				validation : function() {
					var p = retrievePassword.collective.telephone;
					var reg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".phone-prompt-two").css("display", "block");
						$(".phone-prompt-one").css("display", "none");
					} else if (!(reg.test(p.obj.val()))) {
						$(".phone-prompt-one").css("display", "block");
						$(".phone-prompt-two").css("display", "none");
					} else {
						p.flag = 0;
						$(".phone-prompt-one").css("display", "none");
						$(".phone-prompt-two").css("display", "none");
					}
					retrievePassword.collective.check();
				}
			},
			//密码判断
			passWord : {
				obj : $("#new_pwd"),
				flag : 1,
				validation : function() {
					var p = retrievePassword.collective.passWord;
					var reg = /[\u4e00-\u9fa5]/;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".prompt-two").css("display", "block");
						$(".prompt-one").css("display", "none");
					} else if (reg.test(p.obj.val())) {
						$(".prompt-one").css("display", "block");
						$(".prompt-two").css("display", "none");
					} else {
						p.flag = 0;
						$(".prompt-one").css("display", "none");
						$(".prompt-two").css("display", "none");
					}
					retrievePassword.collective.check();
				}
			}
		}
	}

	var temp = {};
	temp = retrievePassword.collective.telephone;
	temp.obj.blur(temp.validation);

	temp = retrievePassword.collective.passWord;
	temp.obj.blur(temp.validation);
})