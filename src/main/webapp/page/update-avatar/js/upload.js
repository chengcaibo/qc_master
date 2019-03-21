$(function() {

	$("[data-upload]").click(function() {
		getData();
		$("#form-upload").submit();
	});

	$("[data-quit]").click(function() {
		top.avaterClose();
	});

});


function getData() {
	var target = $("[data-method=getData]").click().attr("data-target");
	var value = $(target).val();
	return value;
}