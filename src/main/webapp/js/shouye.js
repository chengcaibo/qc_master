$(function() {
	//	$("#form-search").submit(function() {
	//		var value = $("input[name=s]").val();
	//		if (value == "" || value == undefined) {
	//			layer.alert("请输入搜索内容！");
	//			return false;
	//		}
	//		return true;
	//	});
	var imgSrc = document.getElementById("imgSrc");
	//

	var Lun = document.getElementById("Lun");
	var i = 0;
	var arr = new Array();
	arr[0] = "img/lun1.png";
	arr[1] = "img/lun2.png";
	arr[2] = "img/lun3.jpg";
	arr[3]="img/home/banner3.jpg";
	setInterval(function() {
		if (i >= arr.length) {
			i = 0;
		} else {
			imgSrc.src = arr[i];
			i = i + 1;
		}
	}, 2000);


	var imgSrcTwo = document.getElementById("imgSrcTwo");
	var g = 0;
	var arrTwo = [ "img/home/l1.jpg", "img/home/l2.jpg", "img/home/l3.jpg" ];
	setInterval(function() {

		if (g >= arrTwo.length) {
			g = 0;
		} else {
			imgSrcTwo.src = arrTwo[g];
			g = g + 1;
		}
	}, 2000);
})