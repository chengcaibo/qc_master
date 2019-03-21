$(function() {
	function changeColor() {
		$(".stars i").removeClass("icon-like-xing-2");
		$(".stars i").addClass("icon-like-xing-1");
	}
	$(".stars i:nth-child(1)").mouseover(function() {
		changeColor();
		$(".stars i:nth-child(1)").removeClass("icon-like-xing-1");
		$(".stars i:nth-child(1)").addClass("icon-like-xing-2");
		//			$(".icon-like-xing-2").css("color","#6495ED");
		$(".score").html("1星");
	});
	$(".stars i:nth-child(2)").mouseover(function() {
		changeColor();
		$(".stars i:nth-child(1)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(2)").removeClass("icon-like-xing-1");
		$(".stars i:nth-child(2)").addClass("icon-like-xing-2");
		//			$(".icon-like-xing-2").css("color","#6495ED");
		$(".score").html("2星");
	});
	$(".stars i:nth-child(3)").mouseover(function() {
		changeColor();
		$(".stars i:nth-child(1)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(2)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(3)").removeClass("icon-like-xing-1");
		$(".stars i:nth-child(3)").addClass("icon-like-xing-2");
		//			$(".icon-like-xing-2").css("color","#6495ED");
		$(".score").html("3星");
	});
	$(".stars i:nth-child(4)").mouseover(function() {
		changeColor();
		$(".stars i:nth-child(1)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(2)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(3)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(4)").removeClass("icon-like-xing-1");
		$(".stars i:nth-child(4)").addClass("icon-like-xing-2");
		//			$(".icon-like-xing-2").css("color","#6495ED");
		$(".score").html("4星");
	});
	$(".stars i:nth-child(5)").mouseover(function() {
		changeColor();
		$(".stars i:nth-child(1)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(2)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(3)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(4)").addClass("icon-like-xing-2");
		$(".stars i:nth-child(5)").removeClass("icon-like-xing-1");
		$(".stars i:nth-child(5)").addClass("icon-like-xing-2");
		//			$(".icon-like-xing-2").css("color","#6495ED");
		$(".score").html("5星");
	});
});