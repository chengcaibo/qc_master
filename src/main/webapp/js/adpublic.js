/*用js限制字数，超出部分以省略号...显示*/


(function(jqObj) {
	jqObj.each(function(index, obj) {
		var str = $(obj).html();
		if (str.length > 65+42)
			str = str.substr(0, 65+42) + '…';
		$(obj).html(str);
	});

})($(".advertising-content-particulars-li-p"));
