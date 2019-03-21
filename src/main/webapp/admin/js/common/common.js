function sayNoOpen() {
	renderLeftSelect();
	layer.alert('很抱歉，该功能暂未开放！');
}

function renderLeftSelect() {
	if (httpStatus === 404) return;

	const pathname = location.pathname;
	// 设置左侧选中态
	$(`#left-nav a`).parent().removeClass("layui-this");
	$(`#left-nav a[href='${pathname}']`).parent().addClass("layui-this");

	// 设置顶部选中态
	$("ul#top-nav li a").each((index, item) => {
		let this_ = $(item);
		let href = this_.attr("href");
		if (pathname.indexOf(href) != -1) {
			this_.parent().addClass("layui-this");
		}
	})
	// 隐藏滑块
	$("span.layui-nav-bar").css("display", "none");
	$("[hidden='not-open']").css("display", "none");

	// 排除隐藏的滑块
	$(".layui-layout-right span.layui-nav-bar").css("display", "block");
}

if (typeof httpStatus === "undefined") {
	var httpStatus = 200;
}

(() => {
	element.render();

	renderLeftSelect();

	/** 注销登录按钮点击事件 */
	$("#logoff").click(function() {
		layer.confirm("确定要退出吗？", function(index) {
			$.ajax({
				url : "/admin/logoff",
				type : "POST",
				complete () {
					window.location.reload();
				}
			});
		})
	});

})();