
/*日期转换*/
var formatDateTime = function(time, format) {
	var t = new Date(time);
	var tf = function(i, single) {
		if (single) {
			return i;
		}
		return (i < 10 ? '0' : '') + i
	};
	return format.replace(/yyyy|MM|M|dd|d|HH|mm|ss/g, function(a) {
		switch (a) {
		case 'yyyy':
			return tf(t.getFullYear());
			break;
		case 'MM':
			return tf(t.getMonth() + 1);
			break;
		case 'M':
			return tf(t.getMonth() + 1, true);
			break;
		case 'mm':
			return tf(t.getMinutes());
			break;
		case 'dd':
			return tf(t.getDate());
			break;
		case 'd':
			return tf(t.getDate(), true);
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



function datetimeCallBack(e) {
	printCanUseDate(); // 渲染可预约时间
	printCanUseTime();

	var thisTime = new Date($("#datetimepicker3").val()).getTime();
	var minTime = new Date().getTime() - (86400000);
	var maxTime = new Date().getTime() + (86400000 * 14);

	if (thisTime < minTime) {
		layer.alert("不能选择今天以前的时间！");
		$("#datetimepicker3").val(formatDateTime(new Date(), "yyyy-MM-dd"))
		$(".close-calendar").css("display", "none");
	} else if (thisTime > maxTime) {
		layer.alert("最多只能选择 14 天以前的时间");
		$("#datetimepicker3").val(formatDateTime(new Date(), "yyyy-MM-dd"))
	}
}





//加载事件
$(function() {

	$(".form-control").val(formatDateTime(new Date(), "yyyy-MM-dd"));

	$(".appointment-ul .time-in .checkbox-box").append("<input type='checkbox' name='check-input' class='checkbox-input'>");
	$(".appointment-ul .time-in .checkbox-box").append("<div class='checkbox-mask'><i class='icon-checked'></i><i class='icon-chuyidong'></i></div>");

	var checkedList = $("input[name=check-input]");

	// 选择内容被改变
	checkedList.change(function() {

		var thisVal = $(this).val();
		var thisChecked = $(this).prop("checked")

		var start = -1;
		var end = -1;

		checkedList.parent().removeClass("start").removeClass("end");

		for (var i = 0; i < checkedList.length; i++) {
			if ($(checkedList.get(i)).prop("checked") && start == -1) {
				start = i;
			}
			if ($(checkedList.get(i)).val() == thisVal && !thisChecked && start == -1) {
				end = -1;
				break;
			} else if ($(checkedList.get(i)).val() == thisVal && !thisChecked) {
				end = i - 1;
				break;

			} else if ($(checkedList.get(i)).prop("checked") && start != -1) {
				end = i;
			}
		}

		for (var j = 0; j < checkedList.length; j++) {
			if ((j >= start && start != -1) && j <= end) {
				$(checkedList.get(j)).prop("checked", true);
			} else {
				$(checkedList.get(j)).prop("checked", false);
			}
		}


		if (end - start >= 1) {
			if (start != -1) {
				$(checkedList.get(start)).parent().addClass("start");
			}
			if (end != -1) {
				$(checkedList.get(end)).parent().addClass("end");
			}

		}

		var times = $("input[name=timeList]").val();
		var obj = $("input[name=check-input]:checked");
		var startTime = $(obj[0]);
		var endTime = $(obj[obj.length - 1]);

		$("input[name='startTime']").val(formatDateTime((times + "  " + startTime.parent().parent().find("span").text()), ("yyyy-MM-dd HH:mm:ss")));
		$("input[name='endTime']").val(formatDateTime((times + "  " + endTime.parent().parent().find("span").text()), ("yyyy-MM-dd HH:59:59")));
		if (!$('.checkbox-input').is(':checked')) {
			$(".times-on").val(undefined);
		}

	});

	$("#appointment-btn").click(function() {

		var boxs = $("input[name='check-input']:checked");
		if (boxs.length == 0) {
			layer.alert("请选择预约时间");
			return;
		}

		var ids = [];
		boxs.each(function(index, obj) {
			var t = {};
			t.id = $(obj).val();
			t.value = $(obj).attr("data-time");
			ids.push(t)
		});
		
		var userB = $("input[name='userB']").val();
		var startTime = $("input[name='startTime']").val();
		var endTime = $("input[name='endTime']").val();
		var telephone = $("#user-telephone").val();
		var realName = $("#user-realName").val();
		var list = new Array();


		$.ajax({
			url : "/insertReservationTwo",
			type : "POST",
			dataType : "json",
			data : {
				userB : userB,
				startTime : startTime,
				endTime : endTime,
				ids : JSON.stringify(ids),
				all : JSON.stringify(allDateTimes)
			},
			success (data) {
				var sTimeY = formatDateTime(startTime, "yyyy-MM-dd");
				var sTime = formatDateTime(startTime, "HH");
				var eTime = formatDateTime(endTime, "HH")
				var arry = new Array();
				if (data.ret == qc.retEnum.PAR_ERROR) {
					layer.alert(data.msg)
				}
				if (data.ret == qc.retEnum.SUCCESS) {
					for (var j = sTime; j <= eTime; j++) {
						arry.push(sTimeY + " " + j + ":00");
					}
					list.push(arry);
					// 当前选择的时间
					var selectTime = formatDateTime($("input[name=timeList]").val(), "yyyy-MM-dd"); //页面日期
					for (item1 in list) {
						for (item2 in list[item1]) {
							var flagTime1 = list[item1][item2];
							$(".appointment-ul .time-in span").each((index, obj) => {
								var text = obj.innerText;
								var flagTime2 = selectTime + " " + text;
								if (flagTime1 == flagTime2) {
									// 时间相等，选中checkbox
									$(".checkbox-input").eq(index).attr("disabled", true);
									$("#bodyMask").css("display", "block");
									$(".make-info").show().css("z-index:99");
									$(".make-info .p1").text("您预约的时间是:");
									$(".make-info .p1-s-time").text("开始时间:" + startTime);
									$(".make-info .p1-e-time").text("结束时间:" + endTime);

									$(".make-info .p2").text("用户" + realName + "的联系方式是:" + telephone);

								}
							});
						}
					}
				}
			}
		});
	});

	$(".return-close ").click(function() {
		$(".make-info").hide();
		$("#bodyMask").css("display", "none");
		window.location.reload();
	});

	// 关闭日历按钮的点击事件
	$(".close-calendar").click(function(event) {
		$("#lq-datetimepick").css("display", "none");
		$(".close-calendar").css("display", "none");
	});

	// 默认展开日历
	$("#datetimepicker3").click();
});


/*禁止在文本框内输入值*/
$(function() {
	$("input[name='startTime']").attr("readonly", "readonly");
	$("input[name='endTime']").attr("readonly", "readonly");
	$("input[name='timeList']").attr("readonly", "readonly");
})

// 可预约日期
var canUseDates = [];
// 可预约时间
var canUseTimes = [];

// 渲染可预约日期
function printCanUseDate() {
	console.log("渲染Date成功");
	// 先将所有的日期禁止掉
	$("dd[data-value]").addClass("disabled");
	$("dd[data-value] em span").text("");
	for (var i in canUseDates) {
		var dd = $("dd[data-value='" + canUseDates[i] + "']").removeClass("disabled");
		dd.find("em span").text("可预约");
	}
}

// 渲染可预约时间
function printCanUseTime(e) {
	console.log("渲染Time成功");
	// 当前选择的时间
	var selectTime = formatDateTime($("input[name=timeList]").val(), "yyyy-MM-dd");
	$(".checkbox-input").attr("disabled", true);
	// 循环遍历
	for (var i in canUseTimes) {
		//		[{id: ?, value: [{date: ?, time: ?}]}]

		for (var j in canUseTimes[i].value) {
			var temp = canUseTimes[i].value[j];
			$(".appointment-ul .time-in span").each((index, obj) => {
				var text = obj.innerText;
				if ((temp.date + " " + temp.time) == (selectTime + " " + text)) {
					// 时间相等，选中checkbox
					$(".checkbox-input").eq(index).attr("disabled", false).val(canUseTimes[i].id).attr("data-time", temp.time);
				}
			});
		}
	}
}

var allDateTimes = {};

/** 查询可预约时间 */
(function queryTime(userId) {
	$.ajax({
		url : "/query/time_byid",
		type : "GET",
		dataType : "json",
		data : {
			userId : userId
		},
		success (data) {
			if (data.ret == qc.retEnum.SUCCESS) {
				var list = data.list;

				// 计算出时差
				for (var i in list) {
					console.log(list[i].id)
					var thisDateMM = formatDateTime(list[i].startTime, "yyyy-MM-dd"); //数据库里的开始时间  年/月/日
					var thisDateM = formatDateTime(list[i].startTime, "yyyy-M-d");
					// 判断当前时间是否小于今天, 小于则不给予展示
					var flag = new Date(thisDateM) < new Date(formatDateTime(new Date(), "yyyy-M-d"));
					if (!flag) {
						canUseDates.push(thisDateM);

						// 计算时间间隔
						var thisDateTime = new Array();
						var beginHour = formatDateTime(list[i].startTime, "HH"); //数据库里的开始时间  时/HH
						var endHour = formatDateTime(list[i].endTime, "HH"); //数据库里的结束时间

						var selecteds = JSON.parse(list[i].selectedTime);
						var allDateTime = [];
						for (var j = beginHour; j <= endHour; j++) {
							var jj = parseInt(j);
							var str = jj < 10 ? "0" + jj : jj;

							var t = str + ":00";
							allDateTime.push(t);

							var flag = false;
							for (var k in selecteds) {
								if (selecteds[k] == t) {
									flag = true;
									break;
								}
							}

							if (!flag) thisDateTime.push({
									date : thisDateMM,
									time : t
								});
						}

						canUseTimes.push({
							id : list[i].id,
							value : thisDateTime
						});

						allDateTimes[list[i].id] = allDateTime;
						console.log(thisDateTime)
						
					}
				}

				setTimeout(function() {
					printCanUseDate(); // 渲染可预约时间
					printCanUseTime();
				}, 100)
			}
		}
	});
})($("#userBId").val());