(function() {
	/** input Object 的缩写，意思是各个输入框都集中在这里 */
	var io = {
		/** 
		 * 每发送一次ajax就 加1 ，每一个ajax请求结束后，无论是成功还是失败都要 减1
		 * 当此值为0的时候才进行表单提交操作
		 *  */
		ajaxCount : 0,
		/** 返回方法的类型是否等于传入的类型 */
		functionType : function(event, type) {
			return event && event.type && event.type == type;
		},
		getDialog : function(ioObj) {
			return ioObj.obj.parent().parent();
		},
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

		}, // 公用发送 ajax 请求验证方法
		ajax : function(_this, url, type, data, functions) {
			if (_this.flag != 2) return;
			_this.flag = 1;
			$.ajax({
				url : url,
				type : type,
				dataType : "JSON",
				data : data,
				beforeSend : function() {
					io.ajaxCount++;
					if (typeof functions.beforeSend == "function") {
						functions.beforeSend();
					}
				},
				success : function(r) {
					if (typeof functions.success == "function") {
						functions.success(r);
					}
				},
				error : function() {
					io.showDialog(io.getDialog(_this), "发生了未知错误，请稍后重试...", "error");
					if (typeof functions.error == "function") {
						functions.error();
					}
				},
				complete : function() {
					io.ajaxCount--;
					if (typeof functions.complete == "function") {
						functions.complete();
					}
				}
			});
		},
		/** 企业注册 */
		enterprise : {
			// 检查是否全部通过验证，true通过
			check : function() {
				return io.enterprise.checkStep1() && io.enterprise.checkStep2();
			},
			checkStep : function(step) {
				$("div[class^='step-']").removeClass("common-show");
				$(".step-4").removeClass("common-hide");
				switch (thisStep) {
				case 1:
					io.enterprise.checkStep1();
					break;
				case 2:
					io.enterprise.checkStep2();
					break;
				case 3:
					$(".step-4").addClass("common-hide");
					break;
				}
				$(".step-" + thisStep).addClass("common-show");
			},
			checkStep1 : function() {
				var parent = io.enterprise;
				var count = parent.name.flag;
				count += parent.password.flag;
				count += parent.logoFile.check();
				count += parent.licenseFile.check();
				var flag = (count == 0);
				$(".step-last-button").attr("disabled", true);
				$(".step-next-button").attr("disabled", true);
				if (flag) {
					$(".step-next-button").attr("disabled", false);
				}
				return flag;
			},
			checkStep2 : function() {
				var parent = io.enterprise;
				var count = parent.faName.flag;
				count = parent.faName.flag;
				count += parent.fixedTelephone.flag;
				count += parent.faPhone.flag;
				if (typeof ignoreVCode == "undefined")
					count += parent.vCode.flag;
				count += parent.luoTest.flag;
				var flag = (count == 0);
				$(".step-last-button").attr("disabled", false);
				$(".step-next-button").attr("disabled", false);
				if (!flag) {
					$(".step-next-button").attr("disabled", true);
				}
				return flag;
			},
			/** 企业名称 */
			name : {
				obj : $(".sign-enterprise input[name=name]"),
				/** 验证是否通过，0通过，1未通过  */
				flag : 1,
				/** 验证这个输入框 */
				check : function(event) {
					// 获取当前验证对象， to = this object
					var _this = io.enterprise.name;
					_this.flag = 1;
					// 进行表单验证
					if (!_this.obj.val()) {
						// 输入为空
						io.showDialog(io.getDialog(_this), "请输入您企业名称", "error");
					} else if (_this.obj.val().length < 2) {
						io.showDialog(io.getDialog(_this), "企业名称最低两个字符", "error");
					} else if (io.functionType(event, "blur")) {
						io.showDialog(io.getDialog(_this), "验证中...", "warn");
						// 2 表示待验证
						_this.flag = 2;
						// 进行ajax验证
						io.ajax(_this, "/api/validation", "GET", {
							type : "enterprise-name",
							value : _this.obj.val()
						}, {
							success : function(r) {
								if (r.ret == qc.retEnum.VALUE_NOT_EXIST) {
									_this.flag = 0;
									io.enterprise.checkStep1();
									io.showDialog(io.getDialog(_this), "企业名称可以使用", "success");
								} else if (r.ret == qc.retEnum.VALUE_EXIST) {
									io.showDialog(io.getDialog(_this), "这个企业已经注册过了！", "error");
								} else {
									io.showDialog(io.getDialog(_this), "出现错误：" + r.msg, "error");
								}
							}
						})
					} else {
						io.showDialog(io.getDialog(_this), "输入正确", "success");
					}
					io.enterprise.checkStep1();
				}
			},
			// 密码
			password : {
				obj : $(".sign-enterprise input[name=password]"),
				flag : 1,
				check : function() {
					var _this = io.enterprise.password;
					_this.flag = 1;
					// 密码长度为6-18位，可以使用（！@#￥%&*-+=._）特殊符号
					var reg = /^[\w\d!@#\$%\^&\*\(\)\[\]\-\+=\.\,_~\?]{6,18}$/;
					if (_this.obj.val() == "") {
						io.showDialog(io.getDialog(_this), "请输入您的密码", "error");
					} else if (!reg.test(_this.obj.val())) {
						io.showDialog(io.getDialog(_this), "密码格式有误，长度为6-18位，可以使用!@#$%^&*()[]-+=.,_~特殊符号", "error");
					} else {
						// 验证通过
						_this.flag = 0;
						io.showDialog(io.getDialog(_this), "密码可以使用，请牢记", "success");
					}
					io.enterprise.checkStep1();
				}
			},
			// logo
			logoFile : {
				obj : $(".sign-enterprise input[name=logoFile]"),
				flag : 1,
				check : function() {
					var _this = io.enterprise.logoFile;
					_this.flag = 1;
					if (_this.obj.val()) {
						_this.flag = 0;
					}
					return _this.flag;
				}
			},
			// 营业执照
			licenseFile : {
				obj : $(".sign-enterprise input[name=licenseFile]"),
				flag : 1,
				check : function() {
					var _this = io.enterprise.licenseFile;
					_this.flag = 1;
					if (_this.obj.val()) {
						_this.flag = 0;
					}
					return _this.flag;
				}
			},
			// 法人名称
			faName : {
				obj : $(".sign-enterprise input[name=faName]"),
				flag : 1,
				check : function() {
					var _this = io.enterprise.faName;
					_this.flag = 1;
					if (_this.obj.val() == "") {
						io.showDialog(io.getDialog(_this), "请输入法定代表人的姓名", "error");
					} else {
						// 验证通过
						_this.flag = 0;
						io.showDialog(io.getDialog(_this), "输入正确", "success");
					}
					io.enterprise.checkStep2();

				}
			},
			// 企业固定电话
			fixedTelephone : {
				obj : $(".sign-enterprise input[name=fixedTelephone]"),
				flag : 1,
				check : function() {
					var _this = io.enterprise.fixedTelephone;

					var reg = /^\d{3,4}[\s,-]?\d{7,8}$/;
					// 进行表单验证
					if (_this.obj.val() != "") {
						_this.flag = 1;
						if (!reg.test(_this.obj.val())) {
							// 格式有误
							io.showDialog(io.getDialog(_this), "请输入正确的企业固定电话，若没有可不填", "error");
						} else {
							// 验证通过
							_this.flag = 0;
							io.showDialog(io.getDialog(_this), "输入正确", "success");
						}
					} else {
						_this.flag = 0;
						io.showDialog(io.getDialog(_this), "请输入企业固定电话，若没有可不填", "default");
					}
				}
			},
			// 法人手机号码
			faPhone : {
				obj : $(".sign-enterprise input[name=faPhone]"),
				flag : 1,
				check : function() {
					var _this = io.enterprise.faPhone;
					_this.flag = 1;

					var reg = /^1[345678]\d{9}$/;
					// 进行表单验证
					if (_this.obj.val() == "") {
						// 输入为空
						io.showDialog(io.getDialog(_this), "请输入法定代表人的手机号", "error");
					} else if (!reg.test(_this.obj.val())) {
						// 格式有误
						io.showDialog(io.getDialog(_this), "请输入正确的手机号", "error");
					} else {
						// 验证通过
						_this.flag = 0;
						$("#get-personal-vCode").attr("disabled", false);
						io.showDialog(io.getDialog(_this), "输入正确", "success");
					}
				}
			},
			// 法人手机验证码
			vCode : {
				obj : $(".sign-enterprise input[name=vCode]"),
				flag : 1,

				// 验证码私有属性，是否已发送
				send : false,
				check : function(event) {
					var _this = io.enterprise.vCode;
					_this.flag = 1;
					if (_this.send) {
						_this.flag = 1;
						// 验证短信验证码格式的正则表达式
						var reg = /^\d{6}$/;
						if (_this.obj.val() == "") {
							io.showDialog(io.getDialog(_this), "请输入您的验证码", "error");
						} else if (!reg.test(_this.obj.val())) {
							io.showDialog(io.getDialog(_this), "验证码为六位数字", "error");

						} else if (io.functionType(event, "blur")) {
							// 发送 ajax 判断验证码是否正确
							_this.flag = 2;
							io.ajax(_this, "/api/validation", "GET", {
								type : "vCode",
								value : _this.obj.val(),
								phone : io.enterprise.faPhone.obj.val()
							}, {
								beforeSend : function() {
									_this.obj.attr("disabled", true);
									io.showDialog(io.getDialog(_this), "正在验证...", "warn");
								},
								success : function(r) {
									if (r.ret == qc.retEnum.VCODE_ERROR) {
										io.showDialog(io.getDialog(_this), "验证码不正确", "error");
									} else if (r.ret == qc.retEnum.VCODE_NOT_EFFECTIVE) {
										io.showDialog(io.getDialog(_this), "验证码已过期，请重新获取", "error");
									} else if (r.ret == qc.retEnum.SUCCESS) {
										_this.flag = 0;
										io.enterprise.checkStep2();
										io.showDialog(io.getDialog(_this), "验证码输入正确", "success");
									} else {
										io.showDialog(io.getDialog(_this), "发生了未知错误，请稍后重试...", "error");
									}
								},
								error : function() {
									io.showDialog(io.getDialog(_this), "发生了未知错误，请稍后重试...", "error");
								},
								complete : function() {
									_this.obj.attr("disabled", false);
								}
							});
						} else {
							// 验证通过
							_this.flag = 0;
							io.showDialog(io.getDialog(_this), "验证码输入正确", "success");
						}
					}
				}
			},
			// 螺丝帽人机验证
			luoTest : {
				obj : $(".sign-enterprise input[name=luotest_response]"),
				flag : 1,
				check : function() {
					if (!luoFlag) return;
					var _this = io.enterprise.luoTest;
					_this.flag = 2;
					io.ajax(_this, "/api/validation", "POST", {
						type : "luo-validation",
						value : _this.obj.val()
					}, {
						beforeSend : function() {
							io.showDialog($("#luoTestDialog"), "正在验证...", "warn");
						},
						success : function(r) {
							if (r.ret == qc.retEnum.SUCCESS) {
								_this.flag = 0;
								io.enterprise.checkStep2();
								io.showDialog($("#luoTestDialog"), "验证通过", "success");
							} else {
								io.showDialog($("#luoTestDialog"), "验证失败，请重新完成人机验证", "error");
								LUOCAPTCHA.reset();
								luoFlag = false;
							}
						},
						error : function() {
							io.showDialog($("#luoTestDialog"), "发生了未知错误，请稍后重试...", "error");
						}
					});
				}
			},
			bindEvent : function(name) {
				var temp = io.enterprise[name];
				temp.obj.blur(temp.check);
				temp.obj.bind("input propertychange", temp.check);
			},
		}
	};

	// 绑定[企业-名称]事件
	io.enterprise.bindEvent("name");
	// 绑定[企业-密码]事件
	io.enterprise.bindEvent("password");
	//绑定[企业-法人姓名]事件
	io.enterprise.bindEvent("faName");
	//绑定[企业-固定电话]事件
	io.enterprise.bindEvent("fixedTelephone");
	//绑定[企业-法人手机号]事件
	io.enterprise.bindEvent("faPhone");
	//绑定[企业-手机验证码]事件
	io.enterprise.bindEvent("vCode");
	//绑定[企业-螺丝帽人机验证]事件
	//io.enterprise.bindEvent("luoTest");

	// 由于 螺丝帽 需要一段时间的加载，所以最初获取不到它的对象，需要等待1秒左右再次获取
	(function bindLuoTest() {
		setTimeout(function() {
			io.enterprise.luoTest.obj = $(".sign-enterprise input[name=luotest_response]");
			if (io.enterprise.luoTest.obj.length == 0) {
				// 如果还是没有获取到就递归
				bindLuoTest();
			} else {
				// 检测 螺丝帽 input 改变
				let to = io.enterprise.luoTest;
				let val = to.obj.val();
				setInterval(function() {
					if (val != to.obj.val()) {
						val = to.obj.val();
						to.check();
					}
				}, 100);
			}
			;
		}, 1000);
	})();

	// 绑定文件选择图片事件
	$("input[type=file]").change(function(event) {
		var file = this;
		if (file.files && file.files[0]) {
			// 判断后缀名
			var suffix = qc.util.getFileSuffix(file.value);
			if (!".JPG".equalsIgnoreCase(suffix) && !".JPEG".equalsIgnoreCase(suffix) && !".PNG".equalsIgnoreCase(suffix)) {
				layer.alert("仅限于 jpg/jpeg/png 格式的图片！");
				return;
			}
			// 读取图片
			var reader = new FileReader();
			reader.onload = function(evt) {
				io.enterprise.checkStep1();
				$(file).prev().attr("src", evt.target.result);
			}
			reader.readAsDataURL(file.files[0]);
		} else {
			io.enterprise.checkStep1();
			$(file).prev().attr("src", "/img/file-img-default.png");
		}
	});

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

	//绑定获取验证码事件
	$("#get-personal-vCode").click(function() {
		var to = io.enterprise;
		var jqThis = $(this);
		// 判断手机号是否正确
		if (to.faPhone.flag > 0) {
			jqThis.attr("disabled", true);
			return;
		}
		to.vCode.flag = 2;
		// 发送ajax请求验证码
		io.ajax(to.vCode, "/api/sms/send_vcode_sms", "POST", {
			phone : to.faPhone.obj.val()
		}, {
			beforeSend : function() {
				to.vCode.send = true;
				io.showDialog(io.getDialog(to.vCode), "验证码发送中...", "warn");
				jqThis.attr("disabled", true);
			},
			success : function(r) {
				if (r.ret == qc.retEnum.SUCCESS) {
					io.showDialog(io.getDialog(to.vCode), "验证码发送成功");
					// 60秒后重试
					jqThis.attr("disabled", true).text("请在60秒后重试");
					var time = 60;
					var emmm = setInterval(function() {
						time--;
						if (time <= 0) {
							jqThis.attr("disabled", false).text("重新获取");
							// 关闭定时器
							clearInterval(emmm);
						} else {
							jqThis.attr("disabled", true).text("请在" + time + "秒后重试");
						}
					}, 1000);
				} else {
					io.showDialog(io.getDialog(to.vCode), "验证码发送失败，请稍后重试...", "error");
					jqThis.attr("disabled", false);
				}
			},
			error : function() {
				io.showDialog(io.getDialog(to.vCode), "发生了未知错误，请稍后重试...", "error");
				jqThis.attr("disabled", false);
			}
		})

	});

	// 绑定个人注册按钮
	$(".step-3 .submit-button").click(function() {
		if (io.enterprise.check()) {
			var jQThis = $(this);
			if (io.ajaxCount == 0) {
				signupEnterprise(jQThis);
			} else {
				jQThis.html("<i class='icon-loading common-icon-loading'></i>请稍后...").attr("disabled", true);
				var emmm = setInterval(function() {
					if (io.ajaxCount <= 0) {
						clearInterval(emmm);
						signupEnterprise(jQThis);
					}
				}, 300);
			}
		}
	});

	// 阻止form表单提交
	$("#form").submit(function() {
		return false;
	});

	// 企业注册
	function signupEnterprise(subBtn) {
		let loading = layer.load(1);
		$.ajax({
			url : "/sign/enterprise",
			type : "POST",
			async : false, // 这是一个同步请求
			cache : false, // 上传文件不需要缓存
			processData : false, // 因为data值是FormData对象，不需要对数据做处理。
			contentType : false, // 使用 form 表单构造的contentType
			dataType : "JSON",
			data : getFormData(), // 获取 data
			beforeSend : function() {
				io.ajaxCount++;

				subBtn.html("<i class='icon-loading'></i>正在注册...").attr("disabled", true);
			},
			success : function(r) {
				if (r.ret == qc.retEnum.PAR_LACK) {
					layer.alert("注册失败，系统监测到您恶意更改了网页，需刷新后重新注册！");
					window.location.reload();
				} else if (r.ret == qc.retEnum.SUCCESS) {
					$(".sign-main").addClass("common-hide");
					$("#sign-ok").removeClass("common-hide");
				} else {
					layer.alert("发生了未知错误，请稍后重试...(-1)");
				}
			},
			error : function() {
				layer.alert("发生了未知错误，请稍后重试...(-2)");
			},
			complete : function() {
				io.ajaxCount--;
				layer.close(loading);
				subBtn.html("<i class=''>&nbsp;</i>立即注册").attr("disabled", false);
			}
		});
	}
	function getFormData() {
		//<form>标签需要添加 enctype="multipart/form-data"属性。
		let ent = io.enterprise;
		let data = new FormData($("#form")[0]);
		data.append("user.password", ent.password.obj.val())
		data.append("enterpriseName", ent.name.obj.val())
		data.append("personName", ent.faName.obj.val())
		data.append("telephone", ent.faPhone.obj.val())
		//		data.append("fixedTelephone", ent.fixedTelephone.obj.val())
		return data;
	}
	// 步骤 
	var thisStep = 1;

	$(".step-next-button").click(nextStep);
	$(".step-last-button").click(lastStep);
	function lastStep() {
		if (--thisStep < 1) {
			return ++thisStep;
		}
		$(".step-percent").removeClass("step-percent-" + (thisStep + 1));
		$($(".step-item").get(thisStep)).removeClass("step-current");
		io.enterprise.checkStep(thisStep);
	}
	function nextStep() {
		if (++thisStep > 3) {
			return --thisStep;
		}
		$(".step-percent").addClass("step-percent-" + thisStep);
		$($(".step-item").get((thisStep - 1))).addClass("step-current");
		io.enterprise.checkStep(thisStep);
	}
	$(".btn-back").click(function() {
		lastStep();
	});
})();
var luoFlag = false;
function personalCallback(event) {
	luoFlag = true;
}