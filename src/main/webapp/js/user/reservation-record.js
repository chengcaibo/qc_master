function toggle() {
	$("#myAbout").css("display", "none");
	$("#aboutMy").css("display", "none");
	$("#myReservation").css("border-bottom", "0");
	$("#myBooking").css("border-bottom", "0");
}
function secondswitch() {
	$("#myStayConfirm").css("display", "none");
	$("#myStayCancel").css("display", "none");
	$("#myHasConfirm").css("display", "none");
	$("#bookingStayConfirm").css("display", "none");
	$("#bookingStayCancel").css("display", "none");
	$("#bookingHasConfirm").css("display", "none");
	$("#myStayConfirmButton").css("color", "#ccc");
	$("#myStayConfirmButton").removeClass("add-bg");
	$("#myStayCancelButton").css("color", "#ccc");
	$("#myStayCancelButton").removeClass("add-bg");
	$("#myHasConfirmButton").css("color", "#ccc");
	$("#myHasConfirmButton").removeClass("add-bg");
	$("#bookingStayConfirmButton").css("color", "#ccc");
	$("#bookingStayConfirmButton").removeClass("add-bg");
	$("#bookingStayCancelButton").css("color", "#ccc");
	$("#bookingStayCancelButton").removeClass("add-bg");
	$("#bookingHasConfirmButton").css("color", "#ccc");
	$("#bookingHasConfirmButton").removeClass("add-bg");
}
$(function() {
	//我预约的
	$("#myReservation").click(function() {
		toggle();
		secondswitch();
		$("#myStayConfirm").css("display", "block");
		$(this).css("border-bottom", "2px solid #4192ff");
		$("#myAbout").css("display", "block");
		$("#myStayConfirmButton").css("color", "#fff");
		$("#myStayConfirmButton").addClass("add-bg");
	});
	//预约我的
	$("#myBooking").click(function() {
		toggle();
		secondswitch();
		$("#bookingStayConfirm").css("display", "block");
		$(this).css("border-bottom", "2px solid #4192ff");
		$("#aboutMy").css("display", "block");
		$("#bookingStayConfirmButton").css("color", "#fff");
		$("#bookingStayConfirmButton").addClass("add-bg");
	});
	
});