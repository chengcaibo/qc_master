/**
 * Created by Administrator on 2017/12/5 0005.
 */

$(function() {
	//swiper的js
	//	var swiper = new Swiper('.swiper-container', {
	//		pagination : '.swiper-pagination',
	//		nextButton : '.swiper-button-next',
	//		prevButton : '.swiper-button-prev',
	//		slidesPerView : 1,
	//		paginationClickable : true,
	//		spaceBetween : 30,
	//		autoplay : 2000,
	//		loop : true
	//	});

	// 无缝文字轮播
	//Marquee
	/*  $('#marquee6').kxbdSuperMarquee({
	      isMarquee:true,
	      isEqual:false,
	      scrollDelay:20,
	      controlBtn:{up:'#goUM',down:'#goDM'},
	      direction:'up'
	  });*/

	$('#marquee6').kxbdSuperMarquee({
		isMarquee : true,
		isEqual : false,
		scrollDelay : 20,
		controlBtn : {
			up : '#goUM',
			down : '#goDM'
		},
		direction : 'up'
	});
	$("#btnTop").click(function() {
		$(".Industry-choose").css({
			"top" : "-53px"
		});
	});

	$("#btnBottom").click(function() {
		$(".Industry-choose").css({
			"top" : "0px"
		});
	});

	// 监听页面滚动
	qc.util.onScrollTop([ 1450 ], [ {
		ge (top) {
			$(".advertising-title").css({
				"position" : "fixed",
				"top" : "0",
				"z-index" : 1,
			});
		},
		lt (top) {
			$(".advertising-title").css("position", "static");
		}
	} ]);

})
//个人顶置信息
function printData(data) {
	if (data.ret == 0) {
		var result = data.object;
		if (result != null) {
			var imgA = document.getElementById("img-a");
			var userImg = document.getElementById("user-img");
			var a = document.getElementById("user-a");
			imgA.href = "/user/" + result.userId;
			userImg.src = result.avatar.url;
			a.textContent = result.introduce;
			a.href = "/user/" + result.userId;
			qc.util.downTime($("#person-top-down-time"), new Date(result.endTime));
		} else {
			var str = '<a href="/userPosition?locationId=1" class="top-prompt">我也要展示</a>';
			$("#personal-top .placed-top-img").remove();
			$("#personal-top .placed-top-right").remove();
			$("#personal-top").append(str);
			$("#personal-top").css("text-align", "center");
			$(".top-prompt").css("line-height", "200px");
		}

	}
}
//十个人
function printQueryTopTen(data) {

	//	<li class="dong">
	//    <p class="individual">黄老铁</p>
	//    <p class="individual">游泳教练</p>
	//	</li>

	if (data.ret == 0) {
		var list = data.list;
		$("#user-thirteen").empty();
		/*var ul = $("<ul class='optimization-content'></ul>");
		var li = $("<li></li>");
		var aaa = $("#marquee6");*/
		for (var i in list) {
			$("#user-thirteen").append('<li><span><a href="/user/' + list[i].userId + '" target="_blank">' + list[i].jobInfo.jobName + '</a></span><span class="span-overflow"><a href="/user/' + list[i].userId + '" target="_blank">' + list[i].introduce + '</a></span><div class="add-details"></div></li>');
		}
		/*$("#marquee6").append(ul);*/

	}

}
;

//十个团体
function querythirteengroup(data) {

	//	<li class="dong">
	//    <p class="individual">黄老铁</p>
	//    <p class="individual">游泳教练</p>
	//	</li>

	if (data.ret == 0) {
		var list = data.list;
		$("#group-thirteen").empty();
		/*var ul = $("<ul class='optimization-content'></ul>");
		var li = $("<li></li>");
		var aaa = $("#marquee6");*/
		for (var i in list) {
			$("#group-thirteen").append('<li><span><a href="/group/' + list[i].gi.id + '" target="_blank">' + list[i].gi.industry.industryName + '</a></span><span class="span-overflow"><a href="/group/' + list[i].gi.id + '" target="_blank">' + list[i].gi.introduce + '</a></span></li>');
		}
		/*$("#marquee6").append(ul);*/

	}

}
//十个企业
function enterPriseThirteen(data) {
	if (data.ret == 0) {
		var list = data.list;
		$("#enterprise-thirteen").empty();
		for (var i in list) {
			if (list[i].ei.website != "#" && list[i].ei.website != "" && list[i].ei.website != null) {
				if (list[i].ei.website.length < 4) {
					list[i].ei.website = "http://" + list[i].ei.website;
				} else if (list[i].ei.website.substr(0, 4) != "http") {
					list[i].ei.website = "http://" + list[i].ei.website;
				}
				$("#enterprise-thirteen").append('<li><span><a href="' + list[i].ei.website + '" target="_blank">' + list[i].industryType.industryName + '</a></span><span class="span-overflow"><a href="' + list[i].ei.website + '" target="_blank">' + list[i].ei.introduce + '</a></span></li>');
			} else {
				$("#enterprise-thirteen").append('<li><span><a href="/by/enterprise?id=' + list[i].ei.id + '" target="_blank">' + list[i].industryType.industryName + '</a></span><span class="span-overflow"><a href="by/enterprise?id=' + list[i].ei.id + '" target="_blank">' + list[i].ei.introduce + '</a></span></li>');
			}
		}

	}
}
//一个团队
function queryOneGroup(data) {
	if (data.ret == 0) {
		var result = data.object;
		//个人图片
		/*var img = $("<img>");
		img.attr("src", result.gi.picture.url);
		img.attr("alt", "置顶选手");
		img.attr("title", "置顶选手");
		img.addClass("info-img");
		$(".placed-grop-img").append(img);
		//个人信息
		var person = $("<li></li>");
		person.append('<h4><a href="/group/' + result.gi.id + '">' + result.gi.groupName + '</a></h4>');
		person.append('<p class="spot">【行业】' + result.gi.industry.industryName + '</p>');
		person.append('<p>【地区】' + result.gi.region.regionName + '</p>');
		person.append('<p>【介绍】' + result.gi.introduce + '</p>');
		$(".placed-group-content").append(person);*/

		var groupImg = document.getElementById("group-img");
		var groupImgA = document.getElementById("group-img-a");
		var a = document.getElementById("group-content");
		groupImgA.href = "/group/" + result.gi.id;
		groupImg.src = result.gi.picture.url;
		a.textContent = result.gi.introduce;
		a.href = "/group/" + result.gi.id;


	}
}

//企业置顶
function printEnterprise(data) {
	if (data.ret == 0) {
		var result = data.object;
		if (result != null) {

			/*var result = data.object;
			if (result != null) {
				var imgA = document.getElementById("img-a");
				var userImg = document.getElementById("user-img");
				var a = document.getElementById("user-a");
				imgA.href = "/user/" + result.userId;
				userImg.src = result.avatar.url;
				a.textContent = result.introduce;
				a.href = "/user/" + result.userId;
			} else {
				var str = '<a href="/userPosition?locationId=1" class="top-prompt">我也要展示</a>';
				$("#personal-top .placed-top-img").remove();
				$("#personal-top .placed-top-right").remove();
				$("#personal-top").append(str);
				$("#personal-top").css("text-align", "center");
				$(".top-prompt").css("line-height", "200px");
			}
*/
			var enterpriseImgA = document.getElementById("enterprise-img-a");
			var img = document.getElementById("enterprise-img")
			var a = document.getElementById("enterprise-content");
			var time = document.getElementById("down-time");

			if (result.ei.website != null && result.ei.website != "#" && result.ei.website != "") {
				enterpriseImgA.href = result.ei.website;
			} else {
				enterpriseImgA.href = "by/enterprise?id=" + result.ei.id;
			}

			img.src = result.ei.logo.url;
			a.textContent = result.ei.introduce;
			a.href = result.ei.website;
			//			time.textContent = leftTimer();
			qc.util.downTime($("#ent-top-down-time"), new Date(result.dd.endTime));


		/*
		var enterpriseImg = document.getElementById("enterprise-img");
		var a = document.getElementById("enterprise-content");
		var enterpriseImgA = document.getElementById("enterprise-img-a");
		if (result.ei.website != null && result.ei.website != "#" && result.ei.website != "") {
			enterpriseImgA.href = result.ei.website;
		} else {
			enterpriseImgA.href = "by/enterprise?id=" + result.ei.id;
		}
		enterpriseImg.src = result.ei.logo.url;
		a.textContent = result.ei.introduce;
		a.href = result.ei.website;*/
		} else {
			var str = '<a href="/locationId?locationId=2" class="top-prompt">我也要展示</a>';
			$("#enterprise-top .placed-top-img").remove();
			$("#enterprise-top .placed-top-right").remove();
			$("#enterprise-top").append(str);
			$("#enterprise-top").css("text-align", "center");
			$(".top-prompt").css("line-height", "200px");
		}
		/*		var img = $("<img>");
				img.attr("src", result.ei.logo.url);
				img.attr("alt", "置顶选手");
				img.attr("title", "置顶选手");
				img.addClass("info-img");
				$(".placed-enterprise-img").append(img);

				var enterprise = $("<li></li>");
				enterprise.append('<h4><a href="' + result.ei.website + '">' + result.ei.enterpriseName + '</a></h4>');
				enterprise.append('<p class="spot">【行业】' + result.industryType.industryName + '</p>');
				enterprise.append('<p>【地区】' + result.ei.region.regionName + '</p>');
				$(".placed-enterprise-content").append(enterprise);*/

	}
}

function printAdPublicOne(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".advertising-personal-layer-one").empty();
		for (var i in list) {
			$(".advertising-personal-layer-one").append(
				'<div class="advertising-box">' +
				'<div class="advertising-img common-img-hover-bigger-2">' +
				'<a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
				'alt=""></a>' +
				'</div>' +
				'<div class="advertising-content">' +
				'<p><a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
				'<p class="personal-details">个人详细信息</p>' +
				'<p>电话：' + list[i].phone + '</p>' +
				'<p>发布时间：' + FormatDate(list[i].time) + '</p>' +
				'</div>' +
				'</div>');
		}

	}
}

function printAdPublicTwo(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".advertising-personal-layer-two").empty();
		for (var i in list) {
			$(".advertising-personal-layer-two").append(
				'<div class="advertising-box">' +
				'<div class="advertising-img common-img-hover-bigger-2">' +
				'<a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
				'alt=""></a>' +
				'</div>' +
				'<div class="advertising-content">' +
				'<p><a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
				'<p class="personal-details">个人详细信息</p>' +
				'<p>电话：' + list[i].phone + '</p>' +
				'<p>发布时间：' + FormatDate(list[i].time) + '</p>' +
				'</div>' +
				'</div>');
		}

	}
}
function printAdPublicThree(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".advertising-personal-layer-three").empty();
		for (var i in list) {
			$(".advertising-personal-layer-three").append(
				'<div class="advertising-box">' +
				'<div class="advertising-img common-img-hover-bigger-2">' +
				'<a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
				'alt=""></a>' +
				'</div>' +
				'<div class="advertising-content">' +
				'<p><a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
				'<p class="personal-details">个人详细信息</p>' +
				'<p>电话：' + list[i].phone + '</p>' +
				'<p>发布时间：' + FormatDate(list[i].time) + '</p>' +
				'</div>' +
				'</div>');
		}

	}
}


function printAdpublicInfo(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".personal-adpublic-info").empty();
		for (var i in list) {
			$(".personal-adpublic-info").append(`
				<ul>
					<li>
						<a href="/query/one/adpublic?adPublicId=${list[i].id}" target="_blank">
							<img src="${list[i].picture.url}" alt="" >
						</a>
						<p><a href="/query/one/adpublic?adPublicId=${list[i].id}" target="_blank">${list[i].content}</a></p> 
						<b>个人详情信息</b>
						<p>电话：${list[i].phone}</p>
						<p>发布时间：${FormatDate(list[i].time)}</p>
					</li>
				</ul>
			`);

		}
	}
}

function printAdpublicInfoMore(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".personal-adpublic-again").empty();
		for (var i in list) {
			$(".personal-adpublic-again").append(`
				<ul>
					<li>
						<a href="/query/one/adpublic?adPublicId=${list[i].id}" target="_blank">
							<img src="${list[i].picture.url}" alt="" >
						</a>
						<p><a href="/query/one/adpublic?adPublicId=${list[i].id}" target="_blank">${list[i].content}</a></p> 
						<b>个人详情信息</b>
						<p>电话：${list[i].phone}</p>
						<p>发布时间：${FormatDate(list[i].time)}</p>
					</li>
				</ul>
			`);

		}
	}
}

function printEnterpriseAdPublicOne(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".advertising-personal-layer-enterprise-one").empty();
		for (var i in list) {
			$(".advertising-personal-layer-enterprise-one").append(
				'<div class="advertising-box">' +
				'<div class="advertising-img common-img-hover-bigger-2">' +
				'<a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
				'alt=""></a>' +
				'</div>' +
				'<div class="advertising-content">' +
				'<p><a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
				'<p class="personal-details">个人详细信息</p>' +
				'<p>电话：' + list[i].phone + '</p>' +
				'<p>发布时间：' + FormatDate(list[i].time) + '</p>' +
				'</div>' +
				'</div>');
		}

	}
}
function printEnterpriseAdPublicTwo(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".advertising-personal-layer-enterprise-two").empty();
		for (var i in list) {
			$(".advertising-personal-layer-enterprise-two").append(
				'<div class="advertising-box">' +
				'<div class="advertising-img common-img-hover-bigger-2">' +
				'<a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
				'alt=""></a>' +
				'</div>' +
				'<div class="advertising-content">' +
				'<p><a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
				'<p class="personal-details">个人详细信息</p>' +
				'<p>电话：' + list[i].phone + '</p>' +
				'<p>发布时间：' + FormatDate(list[i].time) + '</p>' +
				'</div>' +
				'</div>');
		}

	}
}
function printEnterpriseAdPublicThree(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".advertising-personal-layer-enterprise-three").empty();
		for (var i in list) {
			$(".advertising-personal-layer-enterprise-three").append(
				'<div class="advertising-box">' +
				'<div class="advertising-img common-img-hover-bigger-2">' +
				'<a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
				'alt=""></a>' +
				'</div>' +
				'<div class="advertising-content">' +
				'<p><a href="/query/one/adpublic?adPublicId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
				'<p class="personal-details">个人详细信息</p>' +
				'<p>电话：' + list[i].phone + '</p>' +
				'<p>发布时间：' + FormatDate(list[i].time) + '</p>' +
				'</div>' +
				'</div>');
		}

	}
}


function enterpriseMoreInfo(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".enterprise-adpublic-info").empty();
		for (var i in list) {
			$(".enterprise-adpublic-info").append(`
				<ul>
					<li>
						<a href="/query/one/adpublic?adPublicId=${list[i].id}" target="_blank">
							<img src="${list[i].picture.url}" alt="">
						</a>
						<p><a href="/query/one/adpublic?adPublicId=${list[i].id}" target="_blank"></a>${list[i].content}</p> 
						<b>企业详情信息</b>
						<p>电话：${list[i].phone}</p>
						<p>发布时间：${FormatDate(list[i].time)}</p>
					</li>
				</ul>
			`)
		}
	}
}


function enterpriseAdpublicMore(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".enterpriseMore").empty();
		for (var i in list) {
			$(".enterpriseMore").append(`
				<ul>
					<li>
						<a href="/query/one/adpublic?adPublicId=${list[i].id}" target="_blank">
							<img src="${list[i].picture.url}" alt="">
						</a>
						<p><a href="/query/one/adpublic?adPublicId=${list[i].id}" target="_blank"></a>${list[i].content}</p> 
						<b>企业详情信息</b>
						<p>电话：${list[i].phone}</p>
						<p>发布时间：${FormatDate(list[i].time)}</p>
					</li>
				</ul>
			`)
		}
	}
}

function demandOne(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".advertising-personal-layer-demand-one").empty();
		for (var i in list) {
			if (list[i].reward > 0) {
				$(".advertising-personal-layer-demand-one").append(
					'<div class="advertising-box">' +
					'<div class="advertising-img common-img-hover-bigger-2">' +
					'<a href="/one/demand?demandId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
					'alt=""></a>' +
					'</div>' +
					'<div class="advertising-content">' +
					'<p><a href="/one/demand?demandId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
					'<p class="personal-details">个人详细信息</p>' +
					'<p>酬劳：' + list[i].reward + ' — ' + list[i].endReward + '元</p>' +
					'<p>地址：' + list[i].address + '</p>' +
					'</div>' +
					'</div>');
			} else {
				$(".advertising-personal-layer-demand-one").append(
					'<div class="advertising-box">' +
					'<div class="advertising-img common-img-hover-bigger-2">' +
					'<a href="/one/demand?demandId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
					'alt=""></a>' +
					'</div>' +
					'<div class="advertising-content">' +
					'<p><a href="/one/demand?demandId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
					'<p class="personal-details">个人详细信息</p>' +
					'<p>酬劳：面议</p>' +
					'<p>地址：' + list[i].address + '</p>' +
					'</div>' +
					'</div>');
			}

		}

	}
}

function demandTwo(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".advertising-personal-layer-demand-two").empty();
		for (var i in list) {
			if (list[i].reward > 0) {
				$(".advertising-personal-layer-demand-two").append(
					'<div class="advertising-box">' +
					'<div class="advertising-img common-img-hover-bigger-2">' +
					'<a href="/one/demand?demandId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
					'alt=""></a>' +
					'</div>' +
					'<div class="advertising-content">' +
					'<p><a href="/one/demand?demandId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
					'<p class="personal-details">个人详细信息</p>' +
					'<p>酬劳：' + list[i].reward + ' — ' + list[i].endReward + '元</p>' +
					'<p>地址：' + list[i].address + '</p>' +
					'</div>' +
					'</div>');
			} else {
				$(".advertising-personal-layer-demand-two").append(
					'<div class="advertising-box">' +
					'<div class="advertising-img common-img-hover-bigger-2">' +
					'<a href="/one/demand?demandId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
					'alt=""></a>' +
					'</div>' +
					'<div class="advertising-content">' +
					'<p><a href="/one/demand?demandId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
					'<p class="personal-details">个人详细信息</p>' +
					'<p>酬劳：面议</p>' +
					'<p>地址：' + list[i].address + '</p>' +
					'</div>' +
					'</div>');
			}

		}

	}
}
function demandThree(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".advertising-personal-layer-demand-three").empty();
		for (var i in list) {
			if (list[i].reward > 0) {

				$(".advertising-personal-layer-demand-three").append(
					'<div class="advertising-box">' +
					'<div class="advertising-img common-img-hover-bigger-2">' +
					'<a href="/one/demand?demandId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
					'alt=""></a>' +
					'</div>' +
					'<div class="advertising-content">' +
					'<p><a href="/one/demand?demandId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
					'<p class="personal-details">个人详细信息</p>' +
					'<p>酬劳：' + list[i].reward + ' — ' + list[i].endReward + '元</p>' +
					'<p>地址：' + list[i].address + '</p>' +
					'</div>' +
					'</div>');
			} else {
				$(".advertising-personal-layer-demand-three").append(
					'<div class="advertising-box">' +
					'<div class="advertising-img common-img-hover-bigger-2">' +
					'<a href="/one/demand?demandId=' + list[i].id + '" target="_blank"><img src="' + list[i].picture.url + '"' +
					'alt=""></a>' +
					'</div>' +
					'<div class="advertising-content">' +
					'<p><a href="/one/demand?demandId=' + list[i].id + '" target="_blank">' + list[i].content + '</a></p>' +
					'<p class="personal-details">个人详细信息</p>' +
					'<p>酬劳：面议</p>' +
					'<p>地址：' + list[i].address + '</p>' +
					'</div>' +
					'</div>');
			}
		}

	}
}


function demandInfoMore(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".demand-adpublic-info").empty();
		for (var i in list) {
			$(".demand-adpublic-info").append(`
				<ul>
					<li>
						<a href="/one/demand?demandId=${list[i].id}" target="_blank"><img src="${list[i].picture.url}"></a>
						<p><a href="/one/demand?demandId=${list[i].id}" target="_blank">${list[i].content}</a></p> 
						<b>需求详情信息</b>
						<p>酬劳：${ifReward(list[i].reward, list[i].endReward)}</p>
						<p>地址：${list[i].address}</p>
					</li>
				</ul>
			`)

		}
	}

}
// 判断reward是否是面议
function ifReward(beginReward, endReward) {
	if (beginReward == 0 && endReward == 0) return "面议"
	else return `${beginReward}-${endReward}`
}

function demandAdpublicMore(data) {
	if (data.ret == 0) {
		var list = data.list;
		$(".demandMore").empty();
		for (var i in list) {
			$(".demandMore").append(`
				<ul>
					<li>
						<a href="/one/demand?demandId=${list[i].id}" target="_blank"><img src="${list[i].picture.url}"></a>
						<p><a href="/one/demand?demandId=${list[i].id}" target="_blank">${list[i].content}</a></p> 
						<b>需求详情信息</b>
						<p>酬劳：${ifReward(list[i].reward, list[i].endReward)}</p>
						<p>地址：${list[i].address}</p>
					</li>
				</ul>
			`)

		}
	}

}




function userCount(data) {
	var userCount = document.getElementById("userCount")
	userCount.textContent = data.object;
}
function FormatDate(strTime) {
	var date = new Date(strTime);
	return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
}

function FormatDate1(strTime) {
	var date = new Date(strTime);
	return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + "-"
		+ date.getHours() + "-" + date.getMinutes() + "-" + date.getSeconds();
}


function onlondeWindow(city) {
	city = "";
	//查询个人置顶
	$.ajax({
		url : "/personal/query/top",
		type : "GET",
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : printData
	});
	//查询个人ID最大的13个
	$.ajax({
		url : "/oderByMaxIdUserInfo",
		type : "GET",
		data : {
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : printQueryTopTen
	});
	//查询团体ID最大的13个
	$.ajax({
		url : "/querythirteengroup",
		type : "GET",
		data : {
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : querythirteengroup
	});
	//查询ID最大的13个企业
	$.ajax({
		url : "/enterPriseThirteen",
		type : "GET",
		data : {
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : enterPriseThirteen
	});
	//查询单个团体
	$.ajax({
		url : "/queryOneGroup",
		type : "GET",
		data : {
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : queryOneGroup
	});
	//查询企业置顶
	$.ajax({
		url : "/enterprise/query/top",
		type : "get",
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : printEnterprise
	});
	//查询个人发布广告0-2
	$.ajax({
		url : "/ad/query/personal",
		type : "GET",
		data : {
			limit : 0,
			offset : 2,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : printAdPublicOne
	});

	//查询个人发布广告2-4
	$.ajax({
		url : "/ad/query/personal",
		type : "GET",
		data : {
			limit : 2,
			offset : 2,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : printAdPublicTwo
	});

	//查询个人发布广告4-6
	//	$.ajax({
	//		url : "/ad/query/personal",
	//		type : "GET",
	//		data : {
	//			limit : 4,
	//			offset : 2,
	//			city : city
	//		},
	//		dataType : "json",
	//		error : erryFunction, //错误执行方法    
	//		success : printAdPublicThree
	//	});


	//查询个人广告d--手机端


	//查询个人广告d--更多
	$.ajax({
		url : "/ad/query/personal",
		type : "GET",
		data : {
			limit : 4,
			offset : 44,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : printAdpublicInfo
	});

	//查询个人广告--更多--加载后
	$.ajax({
		url : "/ad/query/personal",
		type : "GET",
		data : {
			limit : 45,
			offset : 100, //有数据后改成51-100
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : printAdpublicInfoMore
	});


	//查询企业发布广告2-4
	$.ajax({
		url : "/ad/query/enterprise",
		type : "GET",
		data : {
			limit : 0,
			offset : 2,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : printEnterpriseAdPublicOne
	});
	//查询企业发布广告2-4
	$.ajax({
		url : "/ad/query/enterprise",
		type : "GET",
		data : {
			limit : 2,
			offset : 2,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : printEnterpriseAdPublicTwo
	});
	//查询企业发布广告2-4
	//	$.ajax({
	//		url : "/ad/query/enterprise",
	//		type : "GET",
	//		data : {
	//			limit : 4,
	//			offset : 2,
	//			city : city
	//		},
	//		dataType : "json",
	//		error : erryFunction, //错误执行方法    
	//		success : printEnterpriseAdPublicThree
	//	});

	//企业更多
	$.ajax({
		url : "/ad/query/enterprise",
		type : "GET",
		data : {
			limit : 4,
			offset : 44,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : enterpriseMoreInfo
	});


	//企业更多
	$.ajax({
		url : "/ad/query/enterprise",
		type : "GET",
		data : {
			limit : 45,
			offset : 100,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : enterpriseAdpublicMore
	});



	//查询需求0-2
	$.ajax({
		url : "/demand/query/new",
		type : "GET",
		data : {
			limit : 0,
			offset : 2,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : demandOne
	});
	//查询需求2-4
	$.ajax({
		url : "/demand/query/new",
		type : "GET",
		data : {
			limit : 2,
			offset : 2,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : demandTwo
	});
	//查询需求2-4
	//	$.ajax({
	//		url : "/demand/query/new",
	//		type : "GET",
	//		data : {
	//			limit : 4,
	//			offset : 2,
	//			city : city
	//		},
	//		dataType : "json",
	//		error : erryFunction, //错误执行方法    
	//		success : demandThree
	//	});
	//需求更多
	$.ajax({
		url : "/demand/query/new",
		type : "GET",
		data : {
			limit : 4,
			offset : 44,
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : demandInfoMore
	});

	//需求更多--加载
	$.ajax({
		url : "/demand/query/new",
		type : "GET",
		data : {
			limit : 45,
			offset : 100, //有数据后改成50-100
			city : city
		},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : demandAdpublicMore
	});




	//region();
	//查询已注册的人数
	$.ajax({
		url : "/userCount",
		type : "GET",
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : userCount
	});

	$(".reservation").click(function() {
		$.ajax({
			url : "insert/reservation",
			type : "get",
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function(data) {
				if (data.ret == 0) {
					layer.alert("预定成功！");
				} else if (data.ret == 100) {
					layer.alert("预定失败！");
				} else if (data.ret == 102) {
					layer.alert("您不是企业用户不能预定当前位置！");
				} else if (data.ret == 111) {
					layer.alert("此预订还未开启，请耐心等待！");
				} else if (data.ret == 101) {
					layer.alert("已过预定时间，请耐心等待下一轮预定！");
				} else if (data.ret == 300) {
					layer.alert("您还没有登录！请前去登录。");
				}
			}
		});
	});

	function erryFunction(e) {
		console.log(e);
	}
}
;
//function region(){
//	mapObj = new AMap.Map('iCenter');
//	mapObj.plugin('AMap.Geolocation', function () {
//	    geolocation = new AMap.Geolocation({
//	        enableHighAccuracy: true,//是否使用高精度定位，默认:true
//	        timeout: 10000,          //超过10秒后停止定位，默认：无穷大
//	        maximumAge: 0,           //定位结果缓存0毫秒，默认：0
//	        convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
//	        showButton: true,        //显示定位按钮，默认：true
//	        buttonPosition: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
//	        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
//	        showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
//	        showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
//	        panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
//	        zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
//	    });
//	    mapObj.addControl(geolocation);
//	    geolocation.getCurrentPosition();
//	    AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
//	    AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
//	});
//}
function erryFunction(e) {
	console.log(e);
}


function onlodCity() {
	//	$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
	//		if (remote_ip_info.ret == '1') {
	//			//layer.alert('国家：' + remote_ip_info.county + '\n省：' + remote_ip_info.province + '\n市：' + remote_ip_info.city + '\n区：' + remote_ip_info.district + '\nISP：' + remote_ip_info.isp + '\n类型：' + remote_ip_info.type + '\n其他：' + remote_ip_info.desc);
	//			onlondeWindow(remote_ip_info.city);
	//		} else {
	//			layer.alert('没有找到匹配的IP地址信息！');
	//		}
	//	});
}
$(function() {
	//	onlodCity()
	onlondeWindow("北京市");
})