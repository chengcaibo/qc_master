function currentTime() {
	var now = new Date();
	var year = now.getFullYear(); //年  
	var month = now.getMonth() + 1; //月  
	var day = now.getDate(); //日  

	var hh = now.getHours(); //时  
	var mm = now.getMinutes(); //分  
	var ss = now.getSeconds(); //秒  

	var clock = year + "-";

	if (month < 10)
		clock += "0";

	clock += month + "-";

	if (day < 10)
		clock += "0";

	clock += day + " ";

	if (hh < 10)
		clock += "0";

	clock += hh + ":";
	if (mm < 10)
		clock += '0';
	clock += mm + ":";

	if (ss < 10)
		clock += '0';
	clock += ss;
	return (clock);
}
$(function() {
	var endTime1 = $("#endTime1").val();
	var endTime0 = $("#endTime0").val();
	var current = currentTime();
	$("#showe1").click(function() {
		if (current < endTime1) {
			layer.alert("时间未到，您还不能抢");
			return false;
		}
	});
	$("#show0").click(function() {
		if (current < endTime0) {
			layer.alert("时间未到，您还不能抢");
			return false;
		}
	});
	if (current > endTime1) {
		$(".contente").hide();
		$("#content_imge").hide();
		$(".h41").show();
	} else {
		$(".contente").show();
		$("#content_imge").show();
		$(".h41").hide();
	}
	if (current > endTime0) {
		$(".content").hide();
		$("#content_img").hide();
		$(".h4").show();
	} else {
		$(".content").show();
		$("#content_img").show();
		$(".h4").hide();
	}

	$(".checkbox-input").click(function() {
		if ($(".checkbox-input:checked").length > 5) {
			layer.alert("抱歉，一次只可以选择五个");
			return false;
		}
	});

	$("#personal-form").submit(function() {
		if ($(".checkbox-input:checked").length < 1) {
			layer.alert("啥都没选，咋看啊？");
			return false;
		}
		return true;
	});

	$(".age_li").click(function() {
		var arr = [ [ 16, 21 ], [ 21, 26 ], [ 26, 31 ],
			[ 31, 36 ], [ 36, 41 ], [ 41, 46 ],
			[ 46, 51 ], [ 51, 56 ] ];

		var ages = $(".age_li");
		var index = ages.index(this)
		var flag = !$(this).hasClass("active");
		ages.removeClass("active");
		if (flag) {
			if (!$(this).hasClass("other")) {
				$(this).addClass("active");
				$("input[name=beginAge]").val(arr[index][0]);
				$("input[name=endAge]").val(arr[index][1]);
			}
		} else {
			$("input[name=beginAge]").val(undefined);
			$("input[name=endAge]").val(undefined);
		}

	});

	$(".age_li .s-age").blur(function() {
		$("input[name=endAge]").val($(this).val());
	});

	$(".age_li .e-age").blur(function() {
		$("input[name=endAge]").val($(this).val());
	});

	$(".wage").click(function() {
		var arr = [ [ 100, 300 ], [ 300, 500 ], [ 500, 700 ],
			[ 700, 1000 ] ];
		var wages = $(".wage");
		var index = wages.index(this)
		var flag = !$(this).hasClass("active");
		wages.removeClass("active");
		if (flag) {
			if (!$(this).hasClass("other")) {
				$(this).addClass("active");
				$("input[name=beginHourlyWage]").val(arr[index][0]);
				$("input[name=endHourlyWage]").val(arr[index][1]);
			}
		} else {
			$("input[name=beginHourlyWage]").val(undefined);
			$("input[name=endHourlyWage]").val(undefined);
		}
	});

	$(".wage .s-wage").blur(function() {
		$("input[name=beginHourlyWage]").val($(this).val());
	});

	$(".wage .e-wage").blur(function() {
		$("input[name=endHourlyWage]").val($(this).val());
	});

	$(".skill").click(function() {
		var arr = [ [ "java开发" ], [ "C#" ], [ ".NET开发" ],
			[ "HTML5" ], [ "销售" ], [ "平面设计" ] ];
		var skills = $(".skill");
		var index = skills.index(this)
		var flag = !$(this).hasClass("active");
		skills.removeClass("active");
		if (flag) {
			if (!$(this).hasClass("other")) {
				$(this).addClass("active");
				$("input[name=skillName]").val(arr[index][0]);
			}
		} else {
			$("input[name=skillName]").val(undefined);
		}
	});

	$(".skill .skill_input").blur(function() {
		$("input[name=skillName]").val($(this).val());
	});

	$(".quantity").click(function() {
		var arr = [ [ 2, 5 ], [ 6, 10 ], [ 11, 15 ],
			[ 16, 20 ], [ 21, 25 ], [ 26, 30 ] ];
		var quantity = $(".quantity");
		var index = quantity.index(this)
		var flag = !$(this).hasClass("active");
		quantity.removeClass("active");
		if (flag) {
			if (!$(this).hasClass("other")) {
				$(this).addClass("active");
				$("input[name=beginQuantity]").val(arr[index][0]);
				$("input[name=endQuantity]").val(arr[index][1]);
			}
		} else {
			$("input[name=beginQuantity]").val(undefined);
			$("input[name=endQuantity]").val(undefined);
		}

	});

	$(".quantity .s-quantity").blur(function() {
		$("input[name=beginQuantity]").val($(this).val());
	});

	$(".quantity .e-quantity").blur(function() {
		$("input[name=endQuantity]").val($(this).val());
	});

	$(".reward").click(function() {
		var arr = [ [ 0, 200 ], [ 201, 400 ], [ 401, 600 ],
			[ 601, 800 ], [ 801, 1000 ] ];
		var reward = $(".reward");
		var index = reward.index(this)
		var flag = !$(this).hasClass("active");
		reward.removeClass("active");
		if (flag) {
			if (!$(this).hasClass("other")) {
				$(this).addClass("active");
				$("input[name=beginReward]").val(arr[index][0]);
				$("input[name=endReward]").val(arr[index][1]);
			}
		} else {
			$("input[name=beginReward]").val(undefined);
			$("input[name=endReward]").val(undefined);
		}
	});

	$(".reward .s-reward").blur(function() {
		$("input[name=beginReward]").val($(this).val());
	});

	$(".reward .e-reward").blur(function() {
		$("input[name=endReward]").val($(this).val());
	});
	//  layer.alert( $( "#act li" ).index( $(this)[0] ) );  

});


var pageSize = 12;

//var pageNum = 1;
$(function() {
	var s = $("input[name='s']").val();

	$("#header-search-form").submit(function() {
		s = $("input[name='s']").val();
		checkJournalism(1, true, s);
		$(".filterBox").css("display", "none");
		return false;
	});


	$(".user-filters-btn").click(function() {
		checkJournalism(1, true, s);
		$(".filterBox").css("display", "none");
	}).click();


	function checkJournalism(pageNum, changePgae, s) {
		var city = "";
		var beginAge = $("input[name=beginAge]").val();
		var endAge = $("input[name=endAge]").val();
		var beginHourlyWage = $("input[name=beginHourlyWage]").val();
		var endHourlyWage = $("input[name=endHourlyWage]").val();
		var skillName = $("input[name=skillName]").val();
		var regionCode = $("#regionCode-sel").val();
		//var s = $(".s-info").val();
		if (regionCode == "-1") {
			regionCode = undefined;
		}
		var jobCode = $("#jobCode-sel").val();
		$.ajax({
			url : "/query/all/personal",
			type : "GET",
			dataType : "json",
			data : {
				pageSize : pageSize,
				pageNum : pageNum,
				beginAge : beginAge,
				endAge : endAge,
				beginHourlyWage ,
				endHourlyWage : endHourlyWage,
				skillName : skillName,
				jobCode ,
				regionCode : regionCode,
				city : city,
				searchKeyword : s
			},
			success (data) {
				if (data.ret == qc.retEnum.SUCCESS) {
					if (changePgae) showPage(data.total);

					var list = data.list;
					if (list.length == 0 && pageNum > 1) {
						--pageNum;
						return;
					}

					$(".user-ul").empty();
					$(".personal-no-result").css("display", "none");
					if (list.length == 0) {
						$(".personal-no-result").css("display", "block");
						return;
					}

					$("li[data-index]").removeClass("active");
					$("li[data-index='" + pageNum + "']").addClass("active");

					for (var i in data.list) {

						var input1 = $('<input name="userId" class="checkbox-input" value="' + list[i].user.id + '" type="checkbox">');
						var div1 = $('	<div class="checkbox-mask"><i class="icon-checked"></i></div>');
						var div2 = $('<div class="checkbox-box"></div>');
						div2.append(input1);
						div2.append(div1);
						var img1 = $('<img alt="" src="' + list[i].avatar.url + '">');
						var h41 = $('<h4><a href="/user/' + list[i].user.id + '" class="biaotia">' + list[i].realName + '</a></h4>');
						var p1 = $('<p><span>职位：</span>' + list[i].jobInfo.jobName + '</p>');
						var p2 = $('<p><span>地址：</span>' + list[i].region.regionName + '</p>');
						var p3 = $('<p class="introduce_p"><span>介绍：</span>' + list[i].introduce + '</p>');
						var div3 = $('<div class="content-div"></div>');
						div3.append(img1);
						div3.append(h41);
						div3.append(p1);
						div3.append(p2);
						div3.append(p3);
						var li = $('<li class="user-li"></li>')
						li.append(div2);
						li.append(div3);
						$("ul.user-ul").append(li);
					}
				} else {
					layer.alert(data.msg)
				}
			},
			error : function() {
				layer.alert("服务器匆忙，请稍后重试...");
			}
		})
	}


	function showPage(count) {
		layui.use('laypage', function() {
			var laypage = layui.laypage;
			laypage.render({
				elem : 'paging-personal',
				limit : pageSize,
				count ,
				theme : '#1E9FFF',
				jump : function(obj, first) {
					if (!first) {
						checkJournalism(obj.curr);
					}
				}
			});
		});
	}



	/*$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
		if (remote_ip_info.ret == '1') {*/
	//console.log('国家：' + remote_ip_info.county + '\n省：' + remote_ip_info.province + '\n市：' + remote_ip_info.city + '\n区：' + remote_ip_info.district + '\nISP：' + remote_ip_info.isp + '\n类型：' + remote_ip_info.type + '\n其他：' + remote_ip_info.desc);
	//	var s = $("input[name='s']").val();
	//	console.log(s);
	/*} else {
		layer.alert('没有找到匹配的IP地址信息！');
	}
	});*/

});