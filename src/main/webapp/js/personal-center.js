/**
 * Created by Administrator on 2017/12/6 0006.
 */

function none() {
	$(".personal-center-bottom-right-one").css({
		"display" : "none"
	});
	$(".personal-center-bottom-right-two").css({
		"display" : "none"
	});
	$(".personal-center-bottom-right-three").css({
		"display" : "none"
	});
	$(".personal-center-bottom-right-four").css({
		"display" : "none"
	});
	$(".personal-center-bottom-right-five").css({
		"display" : "none"
	});
}

function reservation() {
	$(".reservation-content-reservation").css({
		"display" : "none"
	});
	$(".reservation-content-beReserved").css({
		"display" : "none"
	});
}

function mask() {
	$("#mask").css("opacity", "1").addClass("addclass");

	$("#bombBoxTop").css({
		"opacity" : "0"
	});

	$("#bombBoxBottom").css({
		"opacity" : "0"
	});
}

function color(){
	$("#basicInformation").css("color","#333");
	$("#cipher").css("color","#333");
	$("#advertisingRecord").css("color","#333");
	$("#teamPublished").css("color","#333");
	$("#about").css("color","#333");
}



$(function() {

	$("#basicInformation").click(function() {
		none();
		color();
		$(".personal-center-bottom-right-one").css({
			"display" : "block"
		});
		$("#basicInformation").css("color","#84b3ff");
	});
	
	
	
	$("#cipher").click(function() {
		none();
		color();
		$(".personal-center-bottom-right-two").css({
			"display" : "block"
		});
		$("#cipher").css("color","#84b3ff");
	})
	
	
	

	$("#advertisingRecord").click(function() {
		none();
		color();
		$(".personal-center-bottom-right-three").css({
			"display" : "block"
		});
		$("#advertisingRecord").css("color","#84b3ff");
	})
	
	

	$("#teamPublished").click(function() {
		none();
		color();
		$(".personal-center-bottom-right-four").css({
			"display" : "block"
		});
		$("#teamPublished").css("color","#84b3ff");
	})
	
	

	$("#about").click(function() {
		none();
		color();
		$(".personal-center-bottom-right-five").css({
			"display" : "block"
		});
		$("#about").css("color","#84b3ff");
	})
	
	

	$("#reservation").click(function() {
		reservation();
		$("#reservation").css("border-bottom","3px solid #4192ff");
		$("#beReserved").css("border-bottom","0");
		$(".reservation-content-reservation").css({
			"display" : "block"
		});
	})

	$("#beReserved").click(function() {
		reservation();
		$("#beReserved").css("border-bottom","3px solid #4192ff");
		$("#reservation").css("border-bottom","0px");
		$(".reservation-content-beReserved").css({
			"display" : "block"
		});
	})

	$(".modify_btn").click(function() {
		// 显示遮罩层
		var mask = $(".dialog-mask").css("display", "block");
		setTimeout(function() {
			mask.css("opacity", "1");
		}, 10);

		// 判断要显示哪一个 对话框
		var type = $(this).attr("data-type");
		var show = $(".dialog-child[data-type=" + type + "]");
		show.css("display", "block");
	});

	$(".dialog-close").click(function() {
		// 隐藏遮罩层
		var mask = $(".dialog-mask").css("opacity", "0");
		setTimeout(function() {
			mask.css("display", "none")
		}, 300);
		// 隐藏所有child
		$(".dialog-child").css("display", "none");
	});



});