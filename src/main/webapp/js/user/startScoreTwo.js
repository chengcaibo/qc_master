function scoreFun(object, opts) {
	var defaults = {
		fen_d : 16,
		ScoreGrade : 10,
		types : [],
		nameScore : "fenshu",
		parent : "star_score",
		attitude : "attitude"
	};
	options = $.extend({}, defaults, opts);
	var countScore = object.find("." + options.nameScore);
	var startParent = object.find("." + options.parent);
	var atti = object.find("." + options.attitude);
	var now_cli;
	var fen_cli;
	var atu;
	var fen_d = options.fen_d;
	var len = options.ScoreGrade;
	startParent.width(fen_d * len);
	var preA = (10 / len);
	for (var i = 0; i < len; i++) {
		var newSpan = $("<a href='javascript:void(0)'></a>");
		newSpan.css({
			"left" : 0,
			"width" : fen_d * (i + 1),
			"z-index" : len - i
		});
		newSpan.appendTo(startParent)
	}
	startParent.find("a").each(function(index, element) {
		$(this).click(function() {
			now_cli = index;
			show(index, $(this))
		});
		$(this).mouseenter(function() {
			show(index, $(this))
		});
		$(this).mouseleave(function() {
			if (now_cli >= 0) {
				var scor = preA * (parseInt(now_cli) + 1);
				startParent.find("a").removeClass("clibg");
				startParent.find("a").eq(now_cli).addClass("clibg");
				var ww = fen_d * (parseInt(now_cli) + 1);
				startParent.find("a").eq(now_cli).css({
					"width" : ww,
					"left" : "0"
				});
				if (countScore) {
					countScore.text(scor)
				}
			} else {
				startParent.find("a").removeClass("clibg");
				if (countScore) {
					countScore.text("")
				}
			}
		})
	});

	function show(num, obj) {
		var n = parseInt(num) + 1;
		var lefta = num * fen_d;
		var ww = fen_d * n;
		var scor = preA * n;
		atu = options.types[parseInt(num)];
		object.find("a").removeClass("clibg");
		obj.addClass("clibg");
		obj.css({
			"width" : ww,
			"left" : "0"
		});
		countScore.text(scor);
		atti.text(atu)
	}
	
};


$(function (){
	
	function erryFunction() {
	}
	var uiAId ;
	var startTime;
	var endTime;
		$(".assess").click(function(){
			uiAId = $(this).parent().parent().find("input[name=uiAId]").val();
			startTime = $(this).parent().parent().find("input[name=item-startTime]").val();
			endTime =  $(this).parent().parent().find("input[name=item-endTime]").val();
			$("#bodyMask").css({"display":"block","z-index":"0"});
			$(".box").show().css("z-index:99");
		});
		$(".assess-btn").click(function(){
			uiAId = $(this).parent().parent().find("input[name=uiAId]").val();
			$(".box").css({"display":"none"});
			$("#bodyMask").css("display", "none");
//			window.location.reload();
		})
	$(".shut-down-button").click(function() {
		$("#box").css("display", "none");
		$("#bodyMask").css("display", "none")
	})
	$(".submit-button").click(function() {
		alert("121");
		var evaluation = $("textarea[name='evaluation']").val();
		var score = $(".fenshu").html();
		$.ajax({
			url : "/insert/commentaries",
			type : "post",
			data : {
				reviewersId : uiAId,
				commentariesScore : score,
				commentariesContent : evaluation,
				startTime : startTime.trim(),
				endTime : endTime.trim()
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function(data) {
				if(data.ret == 0){
					layer.alert("评价成功");
				}
				if(data.ret == 110){
					layer.alert("您已评价过此条数据，感谢您的参与！");
				}
				
			}
		});
	});
})

