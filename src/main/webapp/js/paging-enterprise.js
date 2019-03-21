var pageSize = 12;
$(function() {
	
	var s = $("input[name='s']").val();

	$("#header-search-form").submit(function() {
		s = $("input[name='s']").val();
		checkJournalism(1, true, s);
		$(".filterBox").css("display", "none");
		return false;
	});
	
	$(".enterprise-filters-btn").click(function() {
		checkEnterprise(1,true,s);
		$(".filterBox").css("display","none");
	}).click();
	


	function checkEnterprise(pageNum, changePgae) {
		/* var city = document.getElementById("city").value; */
		var city = "";
		/* var city ; 
		 $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
				if (remote_ip_info.ret == '1') {
					//console.log('国家：' + remote_ip_info.county + '\n省：' + remote_ip_info.province + '\n市：' + remote_ip_info.city + '\n区：' + remote_ip_info.district + '\nISP：' + remote_ip_info.isp + '\n类型：' + remote_ip_info.type + '\n其他：' + remote_ip_info.desc);
					city=remote_ip_info.city;
				} else {
					layer.alert('没有找到匹配的IP地址信息！');
				}
			});*/
		var regionCode = $("#regioncode-enterprise").val();
		if (regionCode == "-1") {
			regionCode = undefined;
		}
		var industryCode = $("#industryType-code-enterprise").val();
		$.ajax({
			url : "/query/all/enterprise",
			type : "GET",
			dataType : "json",
			data : {
				pageSize : pageSize,
				pageNum : pageNum,
				regionCode : regionCode,
				industryCode : industryCode,
				city : city,
				searchKeyword : s
			},
			success (data) {
				
				if (changePgae) showPage(data.total);
				
				if (data.ret == qc.retEnum.SUCCESS) {
					var list = data.list;
					if (list.length == 0 && pageNum > 1) {

						--pageNum;
						return;
					}

					$(".enterprise-ul").empty();
					$(".enterprise-no-result").css("display", "none");
					if (list.length == 0) {
						$(".enterprise-no-result").css("display", "block");
						return;
					}

					$("li[data-index-enterprise]").removeClass("active");
					$("li[data-index-enterprise='" + pageNum + "']").addClass("active");
					
					for (var i in data.list) {
						if (list[i].entInfo.website != "#" && list[i].entInfo.website != "" && list[i].entInfo.website != null) {
							if (list[i].entInfo.website.length < 4) {
								list[i].entInfo.website = "http://" + list[i].entInfo.website;
							} else if (list[i].entInfo.website.substr(0, 4) != "http") {
								list[i].entInfo.website = "http://" + list[i].entInfo.website;
							}
							var divImg = $('<div class="li-img"><a href="' + list[i].entInfo.website + '" target="_blank"><img src="' + list[i].entInfo.logo.url + '" alt="图片在此"></a></div>');
							var h1 = $('<h1><a href="' + list[i].entInfo.website + '" class="biaotia" target="_blank">' + list[i].entInfo.enterpriseName + '</a></h1>');
						}else{
							var divImg = $('<div class="li-img"><a href="/by/enterprise?id=' + list[i].entInfo.id + '" target="_blank"><img src="' + list[i].entInfo.logo.url + '" alt="图片在此"></a></div>');
							var h1 = $('<h1><a href="/by/enterprise?id=' + list[i].entInfo.id + '" class="biaotia" target="_blank">' + list[i].entInfo.enterpriseName + '</a></h1>');
						}
						
						var p1 = $('<p>介绍：' + list[i].entInfo.introduce + '</p>');
						var p2 = $('<p>行业：' + list[i].indNexus.industryType.industryName + '</p>');
						var p3 = $('<p>地址：' + list[i].entInfo.region.regionName + '</p>');
						var divContent = $('<div class="li-content"></div>');
						divContent.append(h1);
						divContent.append(p1);
						divContent.append(p2);
						divContent.append(p3);
						var li = $('<li class="enterprise-info-div"></li>');
						li.append(divImg);
						li.append(divContent);
						$('.enterprise-ul').append(li);
					}
				} else {
					$(".enterprise-no-result").css("display", "block");
				}
			},
			error : function() {
				layer.alert("服务器匆忙，请稍后重试...");
			}
		})
	}
	;
	
	
	function showPage(count) {
		layui.use('laypage', function() {
			var laypage = layui.laypage;
			laypage.render({
				elem : 'paging-enterprise',
				limit : pageSize,
				count ,
				theme : '#1E9FFF',
				jump : function(obj, first) {
					if (!first) {
						checkEnterprise(obj.curr);
					}
				}
			});
		});
	}
	
	/*function page(){
		// 查询 个人total，并计算总页数
		$.ajax({
			url : "/query/total",
			data : {
				type : "enterprise"
			},
			// arrow function
			success : res => {
				if (res.ret == qc.retEnum.SUCCESS) {
					var total = res.object;
					// 总条目数除以页条目数得出的结果就是总页数
					var dobule1 = (total / pageSize);
					// 但是有可能除不尽，就说明还有数据
					var int1 = parseInt(dobule1);
					// 判断一下是否这样
					if (dobule1 > int1) {
						int1++;
					}
					var pageNumCount = int1;
					$("ul[data-count-enterprise]").empty();
					for (var i = 1; i <= pageNumCount; i++) {
						var li = $("<li><a>" + i + "</a></li>");
						li.attr("data-index-enterprise", i);

						$("ul[data-count-enterprise]").append(li);
					}

					$("li[data-index-enterprise]").click(function() {
						if ($(this).hasClass("active")) {
							return;
						}
						pageNum = $(this).attr("data-index-enterprise");
						checkEnterprise();
					});
				}
			},
			error : function() {
				console.log("b");
			}
		});
	}*/
	
})
$(function (){
/*	$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
		if (remote_ip_info.ret == '1') {*/
			//console.log('国家：' + remote_ip_info.county + '\n省：' + remote_ip_info.province + '\n市：' + remote_ip_info.city + '\n区：' + remote_ip_info.district + '\nISP：' + remote_ip_info.isp + '\n类型：' + remote_ip_info.type + '\n其他：' + remote_ip_info.desc);
			enterpriseInfo() ;
		/*} else {
			layer.alert('没有找到匹配的IP地址信息！');
		}
	});*/
});