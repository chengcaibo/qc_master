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


	var InputsWrapper = $("#InputsWrapper");
	var AddButton = $("#AddMoreFileBox");

	var FieldCount = 1;

	$(AddButton).click(function(e) {
		if ($("#InputsWrapper input[name=startTime]").length <= 3) {
			FieldCount++;
			var trd = $('<tr></tr>');
			var tdd = $('<td></td>');
			var div = $('<div class="addtime-div"></div>');
			var table = $('<table></table>');
			var tr = $('<tr></tr>');
			var td1 = $('<td><input type=\'text\' id=\'field_n' + " '" + FieldCount + "' " + '\' name=\'startTime\' readonly=\'readonly\' placeholder=\'开始时间\' class= \'laydate-icon startTime-info sInfo\' onClick=\"laydate({istime: true, format: \'YYYY-MM-DD hh:mm\'})\" /><p style="position: absolute; font-size: 12px; color: red;"class="start-time"></p></td>')
			var td_ = $('<td>--</td>');
			var td2 = $('<td><input type=\'text\' id=\'field_m' + " '" + FieldCount + "' " + '\' name=\'endTime\' readonly=\'readonly\'  placeholder=\'结束时间\' class=\'laydate-icon endTime-info\' onClick=\"laydate({istime: true, format: \'YYYY-MM-DD hh:mm\'})\" /><p style="position: absolute; font-size: 12px; color: red;"class="end-time"></p></td>')
			var a = $('<a class="removeclass">×</a>');
			td2.append(a);
			tr.append(td1);
			tr.append(td_);
			tr.append(td2);
			table.append(tr);
			div.append(table);
			tdd.append(div);
			trd.append(tdd);
			InputsWrapper.append(trd);
		}

		$(".startTime-info").unbind("blur").blur(bindBlurStartTime)
		$(".endTime-info").unbind("blur").blur(bindBlurEndTime)

		$("tr td .removeclass").unbind("click").click(removeOneTime);

		return false;
	});
});
function removeOneTime() {
	$(this).parent().parent().remove();
}


$(function() {
	$(".addtime-input").attr("readonly", "readonly");
});




function bindBlurStartTime(prompt) {
	var that = $(this);
	setTimeout(function() {
		var thisTime = new Date(that.val()).getTime();


		var minTime = new Date().getTime() - (86400000);
		var maxTime = new Date().getTime() + (86400000 * 14);
		if (thisTime < minTime) {
			layer.msg("不能选择今天以前的时间");
			that.val(formatDateTime(new Date(), "yyyy-MM-dd HH:mm"));
		} else if (thisTime > maxTime) {
			layer.msg("最多只能选择 14 天以前的时间");
			that.val(formatDateTime(new Date(), "yyyy-MM-dd HH:mm"));
		}

		var endObj = that.parent().parent().find("input[name=endTime]");
		var thisEndTime = formatDateTime(new Date(endObj.val()), "yyyy-MM-dd");
		var thisStartTime = formatDateTime(new Date(that.val()), "yyyy-MM-dd");

		if (thisEndTime != thisStartTime) {
			var s = new Date(that.val()).getTime() + 1000 * 60 * 60;
			endObj.val(formatDateTime(s, "yyyy-MM-dd HH:mm"));
		}
	}, 500)
}



function bindBlurEndTime() {
	var that = $(this);

	setTimeout(function() {

		var minTime = new Date().getTime() - (86400000);
		var maxTime = new Date().getTime() + (86400000 * 14);

		if (thisEndTime < minTime) {
			//layer.alert("不能选择今天以前的时间！");
			layer.msg("不能选择今天以前的时间");
			that.val(formatDateTime(new Date(), "yyyy-MM-dd HH:mm"));
		} else if (thisEndTime > maxTime) {
			//layer.alert("最多只能选择 14 天以前的时间");
			layer.msg("最多只能选择 14 天以前的时间");
			that.val(formatDateTime(new Date(), "yyyy-MM-dd HH:mm"));
		}

		var thisStartTime = new Date(that.parent().parent().find("input[name=startTime]").val()).getTime();
		var yyyyStart = formatDateTime(thisStartTime, "yyyy-MM-dd");

		var thisEndTime = new Date(that.val());
		thisEndTime = formatDateTime(thisEndTime, "yyyy-MM-dd");

		if (yyyyStart != thisEndTime) {
			layer.msg("请选择与“开始时间”相同的日期");
			thisStartTime = thisStartTime + 1000 * 60 * 60;
			that.val(formatDateTime(thisStartTime, "yyyy-MM-dd HH:mm"));
		}
	}, 500)
}

$(".startTime-info").blur(bindBlurStartTime);
$(".endTime-info").blur(bindBlurEndTime);


$(".sub-btn").click(function() {
	var thisStartTime = new Date($(".startTime-info").val()).getTime();
	var thisEndTime = new Date($(".endTime-info").val()).getTime();

	var s = $(".startTime-info");
	var e = $(".endTime-info");
	var count = 0; // 记录空值的数量
	for (var int = 0; int < s.length; int++) {
		if (s[int].value == "" || e[int].value == "") {
			count++; // 有一个空值，count就+1
		}
	}
	if (thisEndTime < thisStartTime) {
		layer.msg("结束时间不能小于开始时间");
		$(".thisEndTime").val(formatDateTime(thisStartTime, "yyyy-MM-dd HH:mm"));
		return false;
	} else {
		$(".endTime-info").text("");
	}
	if (count > 0) {
		layer.alert("请不要留空");
		return false;
	}
	return true;
})

//layer.confirm('是否删除？', {icon: 3, title:'提示'}, function(index){
//  layer.close(index); 
// layer.msg("删除成功",{icon:1})
//},()=>{layer.msg("你选择了取消")});


$(function() {
	//修改
	$(".modify").click(function() {
		$(this).parent().parent().parent().parent().next().css("display", "none");
		$(this).css("display", "none");
		$(this).next().css("display", "inline-block");
	});
	function erryFunction(e) {
		console.log(e);
	}
	//保存
	$(".save-on").click(function() {
		var appintmentId = $(this).parent().parent().find("input[name=AppintmentTimeId]").val();
		var startTime = $(this).parent().parent().find("input[name=startTime]").val();
		var endTime = $(this).parent().parent().find("input[name=endTime]").val();
		var thisStartTime = new Date(startTime).getTime();
		var thisEndTime = new Date(endTime).getTime();
		console.log(thisStartTime);
		console.log(thisEndTime);
		if (thisStartTime < thisEndTime && thisStartTime > new Date().getTime()) {
			$.ajax({
				url : "/updateATime",
				type : "get",
				data : {
					id : appintmentId,
					startTime : startTime,
					endTime : endTime
				},
				dataType : "json",
				error : erryFunction, //错误执行方法    
				success : function(data) {
					if (data.ret == 0) {
						/*layer.alert("成功");*/
						$("#bodyMask").css("display", "block");
						$("#icon-div").show().css("z-index:99");
					} else {
						$("#icon-div h3").html(data.msg)
						$("#bodyMask").css("display", "block");
						$("#icon-div").show().css("z-index:99");
					}

				}
			})
		} else {
			layer.alert("时间填写错误！！请重新填写。");
		}

		$(this).parent().parent().parent().parent().next().css("display", "block");
		$(this).css("display", "none");
		$(this).prev().css("display", "inline-block");
	});


	//	layer.confirm('是否删除？', {icon: 3, title:'提示'}, function(index){
	//	  layer.close(index); 
	//	 layer.msg("删除成功",{icon:1})
	//	},()=>{layer.msg("你选择了取消")});


	//删除
	$(".remove").click(function() {
		var this_ = this;
		layer.confirm('是否删除?', {
			icon : 3,
			title : '提示'
		}, function(index) {
			var appintmentId = $(this_).parent().parent().find("input[name=AppintmentTimeId]").val();
			$.ajax({
				url : `/time/delete/${appintmentId}`,
				type : "POST",
				dataType : "json",
				error : erryFunction, //错误执行方法    
				success : function(data) {
					if (data.ret == 0) {
						window.location.reload();
					} else {
						layer.msg("删除失败！");
					}
				}
			})
			$(this).parent().parent().parent().parent().remove();
			layer.close(index);
		});

	});


	$("#release-form").submit(function(event) {
		event.preventDefault();
		var form = $(this);

		if (!form.hasClass('fupload')) {
			//普通表单
			$.ajax({
				type : form.attr('method'),
				url : form.attr('action'),
				data : form.serialize(),
				success : (function() {
					//成功提交
					layer.msg("发布成功");
					window.location.reload();
				}),
				fail : (function() {
					//错误信息
					layer.msg("失败");
					window.location.reload();
				})
			});
		} else {
			// mulitipart form,如文件上传类
			var formData = new FormData(this);
			$.ajax({
				type : form.attr('method'),
				url : form.attr('action'),
				data : formData,
				mimeType : "multipart/form-data",
				contentType : false,
				cache : false,
				processData : false,
				success : (function() {
					//成功提交
					alert("成功");
				}),
				fail : (function() {
					//错误信息
					alert("失败");
				})
			})
		}
		;
	});


	$(".submit-button").click(function() {
		$("#box").css("display", "none");
		$("#bodyMask").css("display", "none");
	});

	$(".a-submit").click(function() {
		var score = $(".s-score").html();
		var comments = $("#comments").val();
		console.log(score);
		console.log(comments);
		window.location.reload();
	});
})