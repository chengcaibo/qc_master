/**
 * Created by Administrator on 2017/12/24 0024.
 */
$(function() {
	$("#advantageOk").click(function() {
		$(".my-advantage-top").css("display", "none");
		$(".my-advantage-bottom").css("display", "block");
	});
	$("#advantageError").click(function() {
		$(".my-advantage-top").css("display", "none");
		$(".my-advantage-bottom").css("display", "block");
	});
	$("#advantageCancel").click(function() {
		$(".my-advantage-top").css("display", "block");
		$(".my-advantage-bottom").css("display", "none");
	});

	var TheInitial = $("#InputBox").val().length;
	$(".limit").html(TheInitial);


	$("#InputBox").bind("propertychange input", function() {
		var InputBox = $("#InputBox").val();
		var receive = $("#receive").val().length;
		$(".limit").html(InputBox.length);
		$("#receive").html(InputBox);
	});

	$("#InputBox").keyup(function() {
		filter($("#InputBox").val(), $(".advantage"))
	});
});