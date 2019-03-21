/**
 * Created by Administrator on 2017/12/26 0026.
 */

/*日期转换*/
var formatDateTime = function(time, format) {
	var t = new Date(time);
	var tf = function(i) {
		return (i < 10 ? '0' : '') + i
	};
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
		switch (a) {
		case 'yyyy':
			return tf(t.getFullYear());
			break;
		case 'MM':
			return tf(t.getMonth() + 1);
			break;
		case 'mm':
			return tf(t.getMinutes());
			break;
		case 'dd':
			return tf(t.getDate());
			break;
		case 'HH':
			return tf(t.getHours());
			break;
		case 'ss':
			return tf(t.getSeconds());
			break;
		}
	})
};


$(function() {
	$("#add-to-case").click(function() {
		$("#bodyMask").css("display", "block");
		$("#add-case-div").show().css("z-index:99");

	});
	$("#close-case").click(function() {
		$("#bodyMask").css("display", "none");
		$("#add-case-div").hide();
		window.location.reload();
	});
	$("input[name ='caseName']").blur(function() {
		filter($(this).val(), $(this).next());
		console.log($(this).next());
	});

	$("input[name ='clientName']").blur(function() {
		filter($(this).val(), $(this).next());
		console.log($(this).next());
	});

	$("#input[name ='clientAddress']").blur(function() {
		filter($(this).val(), $(this).next());
		console.log($(this).next());
	});

	$("#input[name ='caseContent']").blur(function() {
		filter($(this).val(), $(this).next());
		console.log($(this).next());
	});
});


$(function() {
	// 绑定删除案例的方法


	$(".case-content-edit a").click(function() {
		var this_ = this;
		var id = $(this_).attr("data-id");
		layer.confirm('是否删除?', {
			icon : 3,
			title : '提示'
		}, function(index) {
			// 这里执行 Ajax 进行删除
			$.ajax({
				url : "/center/case/delete",
				type : "POST",
				dataType : "JSON",
				data : {
					id
				},
				success : function(data) {
					if (data.ret == qc.retEnum.SUCCESS) {
						window.location.reload();
					} else {
						layer.alert(data.msg);
					}
				},
				error : function() {
					layer.alert("服务器匆忙，请稍后重试...");
				}
			})
			layer.close(index);
		});

	});

});



$(".addTime").attr("readonly", "readonly");


/**
 * 新增案例
 */
$(function() {
	$("#pi-caseInfo").click(function() {
		var uk = $(this).attr("data-uk");
		var data = getFormDataCase();
		var caseCount = $(".case-add-fill .caseInfoName").length;
		if (caseCount >= 3) {
			$("#icon-div-error").show();
			return false;
		}
		$.ajax({
			url : "/center/case/insert",
			type : "POST",
			dataType : "JSON",
			async : false, // 这是一个同步请求
			cache : false, // 上传文件不需要缓存
			processData : false, // 因为data值是FormData对象，不需要对数据做处理。
			contentType : false, // 使用 form 表单构造的contentType
			data : data, // 获取 data
			success : function(data) {
				if (data.ret == qc.retEnum.PAR_LACK) {
					layer.alert("请填写完整！");
				} else if (data.ret == qc.retEnum.SUCCESS) {
					layer.msg("保存成功");
					setTimeout(function() {
						window.location.reload()
					}, 500);
				} else {
					layer.alert(data.msg)
				}
			}
		});
	});
	$("#pi-caseInfo").click(function() {
		$("#add-case-div").hide();
		$("#bodyMask").hide();
	});
	$("#close-div").click(function() {
		$("#icon-div").hide();
		window.location.reload();
	});
	$("#close-div-error").click(function() {
		$("#icon-div-error").hide();
		window.location.reload();
	});
});




function getFormDataCase() {
	//<form>标签需要添加 enctype="multipart/form-data"属性。
	return new FormData($("#form-case")[0]);
}


/**
 * 修改案例
 */
$(function() {
	$("button[name='case-add-ok']").click(function() {
		var uk = $(this).attr("data-uk");
		var data = getFormData("form[data-uk=" + uk + "]");

		$.ajax({
			url : "/center/case/change",
			type : "POST",
			dataType : "JSON",
			async : false, // 这是一个同步请求
			cache : false, // 上传文件不需要缓存
			processData : false, // 因为data值是FormData对象，不需要对数据做处理。
			contentType : false, // 使用 form 表单构造的contentType
			data : data, // 获取 data
			success : function(data) {
				if (data.ret == qc.retEnum.PAR_LACK) {
					layer.alert("请填写完整！");
				} else if (data.ret == qc.retEnum.SUCCESS) {
					layer.msg("保存成功");
					//window.location.reload(3000);
					setTimeout(function() {
						window.location.reload()
					}, 500);
				} else {
					layer.alert(data.msg)
				}
			}
		});
	});
});


function getFormData(name) {
	//<form>标签需要添加 enctype="multipart/form-data"属性。
	console.log($(name));
	return new FormData($(name)[0]);
}


//判断开始时间不能小于结束时间
function bindBlurStartTime() {
	var that = $(this);
	setTimeout(function() {
		var thisTime = new Date(that.val()).getTime();

		var endObj = that.parent().parent().find("input[name=endTime]");
		var thisEndTime = formatDateTime(new Date(endObj.val()), "yyyy-MM-dd");
		var thisStartTime = formatDateTime(new Date(that.val()), "yyyy-MM-dd");

		if (thisEndTime < thisStartTime) {
			layer.msg("请选择比“结束时间”大的日期");
			endObj.val(formatDateTime(thisStartTime, "yyyy-MM-dd"));
		}
	}, 500)
}
//判断结束时间不能大于开始时间
function bindBlurEndTime() {
	var that = $(this);

	setTimeout(function() {
		var thisStartTime = new Date(that.parent().parent().find("input[name=startTime]").val()).getTime();
		var yyyyStart = formatDateTime(thisStartTime, "yyyy-MM-dd");

		var thisEndTime = new Date(that.val());
		thisEndTime = formatDateTime(thisEndTime, "yyyy-MM-dd");

		if (yyyyStart > thisEndTime) {
			layer.msg("请选择比“开始时间”大的日期");
			//thisStartTime = thisStartTime + 1000 * 60 * 60;
			that.val(formatDateTime(thisStartTime, "yyyy-MM-dd"));
		}
	}, 500)
}

$(".startTimer").blur(bindBlurStartTime);
$(".endTimer").blur(bindBlurEndTime);



$(function() {
	$("button[name='edit-btn']").click(function() {
		$(this).parent().parent().parent().next().slideToggle();
	});

	$("button[name='case-add-cancel']").click(function() {
		$(this).parent().parent().parent().slideUp();
	});


	$("button[name='delete-btn']").click(function() {
		layer.confirm("确定要删除吗？", () => {
			$(this).parent().parent().parent().get(0).outerHTML = "";
		});
	});






	//新建案例
	var caseobj = {
		collective : {
			check : function() {
				var p = caseobj.collective;
				var count = p.caseName.flag;
				count += p.clientName.flag;
				count += p.clientTelephone.flag
				count += p.clientAddress.flag;
				var flag = (count == 0);
				$("button[name='button-preservation']").attr("disabled", !flag);

				return flag;
			},
			//案例名称判断
			caseName : {
				obj : $("input[name='caseName']"),
				flag : 1,
				validation : function(isCheck) {
					var p = caseobj.collective.caseName;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".add-caseInfo-div li:first-child .empty-prompt").css("display", "block");
						$(".add-caseInfo-div li:first-child .empty-error").css("display", "none");
					} else if (p.obj.val().length > 20) {
						$(".add-caseInfo-div li:first-child .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:first-child .empty-error").css("display", "block");
					} else {
						p.flag = 0;
						$(".add-caseInfo-div li:first-child .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:first-child .empty-error").css("display", "none");
					}
					caseobj.collective.check();
				}
			},
			//客户名称判断
			clientName : {
				obj : $("input[name='clientName']"),
				flag : 1,
				validation : function(isCheck) {
					var p = caseobj.collective.clientName;
					var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
						regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".add-caseInfo-div li:nth-child(3) .empty-prompt").css("display", "block");
						$(".add-caseInfo-div li:nth-child(3) .empty-error").css("display", "none");
						$(".add-caseInfo-div li:nth-child(3) .text-restriction").css("display", "none");
					} else if (p.obj.val().length > 12) {
						p.obj.val().length == 10;
						$(".add-caseInfo-div li:nth-child(3) .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:nth-child(3) .empty-error").css("display", "none");
						$(".add-caseInfo-div li:nth-child(3) .text-restriction").css("display", "block");
					} else if (regEn.test(p.obj.val()) && regCn.test(p.obj.val())) {
						p.obj.val().length == 10;
						$(".add-caseInfo-div li:nth-child(3) .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:nth-child(3) .empty-error").css("display", "block");
						$(".add-caseInfo-div li:nth-child(3) .text-restriction").css("display", "none");
					} else {
						p.flag = 0;
						$(".add-caseInfo-div li:nth-child(3) .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:nth-child(3) .empty-error").css("display", "none");
						$(".add-caseInfo-div li:nth-child(3) .text-restriction").css("display", "none");
					}
					caseobj.collective.check();
				}
			},
			//客户电话判断
			clientTelephone : {
				obj : $("input[name='clientTelephone']"),
				flag : 1,
				validation : function(isCheck) {
					var p = caseobj.collective.clientTelephone;
					var reg = /^1[3|4|7|5|8][0-9]\d{4,8}$/;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".add-caseInfo-div li:nth-child(6) .empty-prompt").css("display", "block");
						$(".add-caseInfo-div li:nth-child(6) .empty-error").css("display", "none");
					} else if (!(reg.test(p.obj.val()))) {
						$(".add-caseInfo-div li:nth-child(6) .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:nth-child(6) .empty-error").css("display", "block");
					} else {
						p.flag = 0;
						$(".add-caseInfo-div li:nth-child(6) .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:nth-child(6) .empty-error").css("display", "none");
					}
					caseobj.collective.check();
				}
			},
			//详情地址判断
			clientAddress : {
				obj : $("input[name='clientAddress']"),
				flag : 1,
				validation : function(isCheck) {
					var p = caseobj.collective.clientAddress;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".add-caseInfo-div li:nth-child(7) .empty-prompt").css("display", "block");
						$(".add-caseInfo-div li:nth-child(7) .text-restriction").css("display", "none");
					} else if (p.obj.val().length > 50) {
						$(".add-caseInfo-div li:nth-child(7) .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:nth-child(7) .text-restriction").css("display", "block");
					} else {
						p.flag = 0;
						$(".add-caseInfo-div li:nth-child(7) .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:nth-child(7) .text-restriction").css("display", "none");
					}
					caseobj.collective.check();
				}
			},
			//项目介绍判断
			caseContent : {
				obj : $("textarea[name='caseContent']"),
				flag : 1,
				validation : function(isCheck) {
					var p = caseobj.collective.caseContent;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".add-caseInfo-div li:nth-child(8) .empty-prompt").css("display", "block");
						$(".add-caseInfo-div li:nth-child(8) .text-restriction").css("display", "none");
					} else if (p.obj.val().length > 200) {
						$(".add-caseInfo-div li:nth-child(8) .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:nth-child(8) .text-restriction").css("display", "block");
					} else {
						p.flag = 0;
						$(".add-caseInfo-div li:nth-child(8) .empty-prompt").css("display", "none");
						$(".add-caseInfo-div li:nth-child(8) .text-restriction").css("display", "none");
					}
					caseobj.collective.check();
				}
			}
		}
	}

	var temp = {};
	temp = caseobj.collective.caseName;
	temp.obj.blur(temp.validation);

	temp = caseobj.collective.clientName;
	temp.obj.blur(temp.validation);

	temp = caseobj.collective.clientTelephone;
	temp.obj.blur(temp.validation);

	temp = caseobj.collective.clientAddress;
	temp.obj.blur(temp.validation);

	temp = caseobj.collective.caseContent;
	temp.obj.blur(temp.validation);
});