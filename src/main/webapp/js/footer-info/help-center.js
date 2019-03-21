$(".computer-use").click(function() {
	$(".computer-box").show();
	$(".computer-use").css({"background":"#007ded","color":"#fff"});
	$(".small-program-use").css({"background":"#dedede","color":"#000"});
	$(".small-program-box").hide();
})

$(".small-program-use").click(function() {
	$(".small-program-box").show();
	$(".small-program-use").css({"background":"#007ded","color":"#fff"});
	$(".computer-use").css({"background":"#dedede","color":"#000"});
	$(".computer-box").hide();
})