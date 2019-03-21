
$(function() {

	var tel = $('#tel').text();
	var tel2 = $('#tels').val();
	var telPhone = tel2.substr(0, 3) + '****' + tel2.substr(7);
	var telphone = tel2.substr(0, 3) + '****' + tel2.substr(7);
	$('#tel').text(telphone);
	$('#tels').val(telPhone);
});

$(function() {
	$("#btn_pwd").click(function() {
		var oldPwd = $("#old_pwd").val();
		var newPwd = $("#new_pwd").val();
		var id = $("#id").val();
		var type = "password";

		if (oldPwd == "") {
			$(".prompt_j").focus().css({
				"color" : "red"
			}).html("请输入旧密码");return false;
		}
		$(".prompt_j").blur().html("");

		if (newPwd == "") {
			$(".prompt_n").focus().css({
				"color" : "red"
			}).html("请输入新密码");return false;
		}
		$(".prompt_n").blur().html("");

		modifyPassword(id, oldPwd, newPwd, type);
	});
});

// 修改密码
function modifyPassword(id, oldPassword, newPassword, type) {
	$.ajax({
		url : "/user/change-password",
		type : "POST",
		dataType : "JSON",
		data : {
			id : id,
			oldPassword : oldPassword,
			newPassword : newPassword,
			type : type
		},
		success : function(r) {
			if (r.ret == 0) {
				layer.alert("修改成功，请重新登陆！");
				window.location.href = "/login";
			} else {
				layer.alert('修改失败：' + r.msg);
			}
		}
	});
}

$(function() {
	$("#btn_phone").click(function() {
		var id = $("#id").val();
		var telephone = $("#phone").val(); // 获取新手机号
		var vCode = $("#code").val();

		if (!telephone) {
			layer.alert("请输入您的新手机号");
		} else if (!vCode) {
			layer.alert("请获取并输入验证码");
		} else if (vCodeCanUse == false) {
			layer.alert("验证码错误或已过期！");
		} else {
			modifyPhone(id, telephone, vCode);
		}
	});
});


function modifyPhone(id, telephone, vCode) {
	$.ajax({
		url : "/user/change-password",
		type : "POST",
		dataType : "JSON",
		data : {
			id : id,
			telephone : telephone,
			vCode : vCode,
			type : "phone"
		},
		success : function(r) {
			if (r.ret == 0) {
				var mask = $(".dialog-mask").css("opacity", "0");
				setTimeout(function() {
					mask.css("display", "none")
				}, 300);
				// 隐藏所有child
				$(".dialog-child").css("display", "none");
				location.reload();
			} else {
				layer.alert('修改失败：' + r.msg);
			}
		}
	});
}


//判断手机号是否存在
function checkIsExist() {
	var telephone = $("#phone").val();
	if (telephone == "") {
		$("#phoneResult").css({
			"color" : "red"
		}).html("请输入手机号");return false;
	}
	$.ajax({
		url : "/checkPhone", //发送给服务器的url  
		type : "GET", //http请求方式  
		data : {
			telephone : telephone
		}, //发送给服务器的参数  
		dataType : "JSON",
		success : function(r) {
			if (r.ret == qc.retEnum.VALUE_EXIST) {
				$("#phoneResult").css({
					"color" : "red"
				}).html("手机号已存在");
			}
			if (r.ret == qc.retEnum.VALUE_NOT_EXIST) {
				$("#phoneResult").css({
					"color" : "green"
				}).html("手机号可用");
			}
		}
	});
}


function vCodes() {
	var value = $("#code").val();
	var phone = $("input[name=phoneOld]").val();
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