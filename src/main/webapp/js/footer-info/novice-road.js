$(".enterprise-li").click(function() {
	$(".enterprise-box").show();
	$(".enterprise-li").css({"background":"#007ded","color":"#fff"});
	$(".personal-li").css({"background":"#dedede","color":"#000"});
	$(".personal-box").hide();
})

$(".personal-li").click(function() {
	$(".personal-box").show();
	$(".personal-li").css({"background":"#007ded","color":"#fff"});
	$(".enterprise-li").css({"background":"#dedede","color":"#000"});
	$(".enterprise-box").hide();
})