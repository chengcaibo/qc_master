
var pageSize = 12;
$(function() {
	var s = $("input[name='s']").val();

	$("#header-search-form").submit(function() {
		s = $("input[name='s']").val();
		checkJournalism(1, true, s);
		$(".filterBox").css("display", "none");
		return false;
	});
	
	$(".group-filters-btn").click(function() {
		checkGroupInfo(1,true,s);
		$(".filterBox").css("display","none");
	}).click();
	function checkGroupInfo(pageNum, changePgae,s) {
		/* var city = document.getElementById("city").value; */
		var city = "";
		
		var beginQuantity = $("input[name=beginQuantity]").val();
		var endQuantity = $("input[name=endQuantity]").val();
		var regionCode = $("#regioncode-group").val();
		if (regionCode == "-1") {
			regionCode = undefined;
		}
		var industryCode = $("#industryType-code-group").val();
		$.ajax({
			url : "/query/all/group",
			type : "GET",
			dataType : "json",
			data : {
				pageSize : pageSize,
				pageNum : pageNum,
				beginQuantity : beginQuantity,
				endQuantity : endQuantity,
				regionCode : regionCode,
				industryCode : industryCode,
				city :city,
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

					$(".groupInfo-ul").empty();
					$(".groupInfo-no-result").css("display", "none");
					if (list.length == 0) {
						$(".groupInfo-no-result").css("display", "block");
						return;
					}

					$("li[data-index-group]").removeClass("active");
					$("li[data-index-group='" + pageNum + "']").addClass("active");
					for (var i in data.list) {
						/*list[i].avatar.url*/ /*picture*/
						var img = $('<a href="/group/' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '" alt="团体封面" class="hezi-img-img"></a>');
						var imgdiv = $('<div class="li-img"></div>');
						imgdiv.append(img);

						var h1 = $('<h1><a href="/group/' + list[i].id + '" class="biaotia" target="_blank">' + list[i].groupName + '</a></h1>');
						var p1 = $('<p>行业：' + list[i].industry.industryName + '</p>');
						var p2 = $('<p>地址：' + list[i].region.regionName + '</p>');
						var p3 = $('<p class="group-in">介绍：' + list[i].introduce + '</p>');
						var div2 = $('<div class="li-content"></div>');
						div2.append(h1);
						div2.append(p1);
						div2.append(p2);
						div2.append(p3);
						var li = $('<li class="group-li"></li>');
						li.append(imgdiv);
						li.append(div2);
						$('.groupInfo-ul').append(li);
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
				elem : 'paging-group',
				limit : pageSize,
				count ,
				theme : '#1E9FFF',
				jump : function(obj, first) {
					if (!first) {
						checkGroupInfo(obj.curr);
					}
				}
			});
		});
	}

	/*checkGroupInfo();*/
	
//	function page(){
//		// 查询 个人total，并计算总页数
//		$.ajax({
//			url : "/query/total",
//			data : {
//				type : "group"
//			},
//			// arrow function
//			success : res => {
//				if (res.ret == qc.retEnum.SUCCESS) {
//					var total = res.object;
//					// 总条目数除以页条目数得出的结果就是总页数
//					var dobule1 = (total / pageSize);
//					// 但是有可能除不尽，就说明还有数据
//					var int1 = parseInt(dobule1);
//					// 判断一下是否这样
//					if (dobule1 > int1) {
//						int1++;
//					}
//					var pageNumCount = int1;
//					$("ul[data-count-group]").empty();
//					for (var i = 1; i <= pageNumCount; i++) {
//						var li = $("<li><a>" + i + "</a></li>");
//						li.attr("data-index-group", i);
//
//						$("ul[data-count-group]").append(li);
//					}
//
//					$("li[data-index-group]").click(function() {
//						if ($(this).hasClass("active")) {
//							return;
//						}
//						pageNum = $(this).attr("data-index-group");
//						checkGroupInfo();
//					});
//				}
//			},
//			error : function() {
//				console.log("b");
//			}
//		});
//	}
})
$(function (){
	
	/*$.getScript('iplookup/iplookup.php?format=js', function(_result) {
		if (remote_ip_info.ret == '1') {*/
			//console.log('国家：' + remote_ip_info.county + '\n省：' + remote_ip_info.province + '\n市：' + remote_ip_info.city + '\n区：' + remote_ip_info.district + '\nISP：' + remote_ip_info.isp + '\n类型：' + remote_ip_info.type + '\n其他：' + remote_ip_info.desc);
			//groupInfo(remote_ip_info.city) ;
		
	groupInfo();
		/*} else {
			layer.alert('没有找到匹配的IP地址信息！');
		}
	});*/
})


/* var city ; 
		 $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
				if (remote_ip_info.ret == '1') {
					//console.log('国家：' + remote_ip_info.county + '\n省：' + remote_ip_info.province + '\n市：' + remote_ip_info.city + '\n区：' + remote_ip_info.district + '\nISP：' + remote_ip_info.isp + '\n类型：' + remote_ip_info.type + '\n其他：' + remote_ip_info.desc);
					city=remote_ip_info.city;
				} else {
					layer.alert('没有找到匹配的IP地址信息！');
				}
			});*/