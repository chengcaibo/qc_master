
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

	$(".demand-filters-btn").click(function() {
		checkdemandInfo(1, true, s);
		$(".filterBox").css("display", "none");
	}).click();

	function checkdemandInfo(pageNum, changePgae, s) {
		/*var city = document.getElementById("city").value; */
		var city = "";
		var regionCode = $("#regioncode-demand").val();
		if (regionCode == "-1") {
			regionCode = undefined;
		}
		var beginReward = $('input[name=beginReward]').val();
		var endReward = $('input[name=endReward]').val();
		var beginReleaseTime = $('.s-releaseTime').val();
		var endReleaseTime = $('.e-releaseTime').val();
		$.ajax({
			url : "/demand/query",
			type : "GET",
			dataType : "json",
			data : {
				pageSize : pageSize,
				pageNum : pageNum,
				beginReward : beginReward,
				endReward : endReward,
				beginReleaseTime : beginReleaseTime,
				endReleaseTime : endReleaseTime,
				regionCode : regionCode,
				city : city,
				keyword : s
			},
			success (data) {
				if (data.ret == qc.retEnum.SUCCESS) {
					if (changePgae) showPage(data.total);

					var list = data.list;
					// console.log(list);
					if (list.length == 0 && pageNum > 1) {
						--pageNum;
						return;
					}

					$(".demand-no-result").css("display", "none");
					if (list.length == 0) {
						$(".demand-no-result").css("display", "block");
						$(".demand-ul").hide();
						return;
					}

					$("li[data-index-demand]").removeClass("active");
					$("li[data-index-demand='" + pageNum + "']").addClass("active");
					$(".demand-ul").empty();
					for (var i in data.list) {
						var img = $('<a href="#"><img src="' + list[i].picture.url + '" alt="头像"class="hezi-img-img"></a>');
						var divImg = $('<div class="li-img"></div>');
						divImg.append(img);
						var h1 = $('<h1>' + list[i].content + '</h1>');
						var p1 = $('<p>发布人员：' + list[i].contact + '</p>');
						var p2 = $('<p>地址:' + list[i].address + '</p>');
						var p3 = $('<p>发布时间：' + FormatDate(list[i].releaseTime) + '</p>');
						var div2 = $('<div class="li-content"></div>');
						div2.append(h1);
						div2.append(p1);
						div2.append(p2);
						div2.append(p3);
						var li = $('<li></li>');
						li.append(divImg);
						li.append(div2);

						$('.demand-ul').append(li);
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
				elem : 'paging-demand',
				limit : pageSize,
				count ,
				theme : '#1E9FFF',
				jump : function(obj, first) {
					if (!first) {
						checkdemandInfo(obj.curr);
					}
				}
			});
		});
	}

	//	$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
	//		if (remote_ip_info.ret == '1') {
	//			//console.log('国家：' + remote_ip_info.county + '\n省：' + remote_ip_info.province + '\n市：' + remote_ip_info.city + '\n区：' + remote_ip_info.district + '\nISP：' + remote_ip_info.isp + '\n类型：' + remote_ip_info.type + '\n其他：' + remote_ip_info.desc);
	//			demandInfo(remote_ip_info.city);
	//		} else {
	//			layer.alert('没有找到匹配的IP地址信息！');
	//		}
	//	});

	function FormatDate(strTime) {
		var date = new Date(strTime);
		return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
	}
});