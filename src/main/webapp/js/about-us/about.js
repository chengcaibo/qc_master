
$(function() {

	$(".show-info").click(function() {
		// 变换 +  -
		if ($(this).text() == "+") $(this).text("－");
		else $(this).text("+");

		// 展开数据
		$(this).parent().next().slideToggle();
	});

});