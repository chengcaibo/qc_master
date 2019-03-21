/**
 * Created by Administrator on 2017/12/7 0007.
 */
//swiper框架

function search() {
	$("#searchIndividual").css("display", "none");
	$("#searchGroup").css("display", "none");
	$("#searchEnterprise").css("display", "none");
	$("#searchDemand").css("display", "none");
	$(".individual").css("display", "none");
	$(".enterprise").css("display", "none");
	$(".team").css("display", "none");
	$(".demand").css("display", "none");
}
function Underline() {
	$("#individualButton").css("border-bottom", "0");
	$("#groupButton").css("border-bottom", "0");
	$("#enterpriselButton").css("border-bottom", "0");
	$("#demandButton").css("border-bottom", "0");
}



$(function() {
	var swiper = new Swiper('.swiper-container', {
		pagination : '.swiper-pagination',
		nextButton : '.swiper-button-next',
		prevButton : '.swiper-button-prev',
		paginationClickable : true,
		spaceBetween : 30,
		centeredSlides : true,
		autoplay : 2500,
		autoplayDisableOnInteraction : false
	});

	//筛选
	$(".screeningSwitch").click(function() {
		$(".filterBox").css({
			"display" : "block",
			"opacity" : "1"
		})
	})
	$(".screeningCancelButton").click(function() {
		$(".filterBox").css({
			"display" : "none",
			"opacity" : "0"
		})
	})
	//个人
	$("#individualButton").click(function() {
		search();
		Underline();
		$("#searchIndividual").css("display", "block");
		$(".individual").css("display", "block");
		$("#individualButton").css("border-bottom", "3px solid #000");
		$(".filterBox").css({
			"display" : "none",
			"opacity" : "0"
		})

	})

	//团体
	$("#groupButton").click(function() {
		search();
		Underline();
		$("#searchGroup").css("display", "block");
		$(".team").css("display", "block");
		$("#groupButton").css("border-bottom", "3px solid #000");
		$(".filterBox").css({
			"display" : "none",
			"opacity" : "0"
		})
	})
	//企业
	$("#enterpriselButton").click(function() {
		search();
		Underline();
		$("#searchEnterprise").css("display", "block");
		$(".enterprise").css("display", "block");
		$("#enterpriselButton").css("border-bottom", "3px solid #000");
		$(".filterBox").css({
			"display" : "none",
			"opacity" : "0"
		})
	})

	//需求
	$("#demandButton").click(function() {
		search();
		Underline();
		$("#searchDemand").css("display", "block");
		$(".demand").css("display", "block");
		$("#demandButton").css("border-bottom", "3px solid #000");
		$(".filterBox").css({
			"display" : "none",
			"opacity" : "0"
		})
	});



	//选择页面
	switch (window.location.hash) {
	case "#personal-info":
		$("#individualButton").click();
		break;
	case "#group-info":
		$("#groupButton").click();
		break;
	case "#enterprise-info":
		$("#enterpriselButton").click();
		break;
	default:
		$("#individualButton").click();
		break;
	}
})