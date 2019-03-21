
var endTime;

$(".down-time").trigger("click");


function leftTimer(s) {
	endTime = s;
	var setTimeId = setInterval(setTime, 10);
}



function checkTime(i) { //将0-9的数字前面加上0，例1变为01 
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}


var jieshu = false;

function setTime() {
	var obj = $("#timeScope");
	obj.empty();
	var t = calcTime(new Date(), endTime);
	if (t != "") {
		var str = "<b>" + t.day + "</b>天 <b>" + t.hours + "</b>时 <b>";
		str += t.minutes + "</b>分 <b>" + t.ms + "</b>秒";
		obj.html(str);
	} else {
		obj.html("<b>已结束！</b>");
		guoshi();
	}
}

function guoshi() {
	clearInterval(setTimeId);
}

function calcTime(startTime, endTime) {
	var date1 = startTime; //开始时间
	var date2 = endTime; //结束时间
	var date3 = date2.getTime() - date1.getTime() //时间差的毫秒数
	//计算出相差天数
	var days = Math.floor(date3 / (24 * 3600 * 1000))
	//计算出小时数
	var leave1 = date3 % (24 * 3600 * 1000) //计算天数后剩余的毫秒数
	var hours = Math.floor(leave1 / (3600 * 1000))
	//计算相差分钟数
	var leave2 = leave1 % (3600 * 1000) //计算小时数后剩余的毫秒数
	var minutes = Math.floor(leave2 / (60 * 1000))
	//计算相差秒数
	var leave3 = leave2 % (60 * 1000) //计算分钟数后剩余的毫秒数
	var seconds = Math.round(leave3 / 1000)

	// 判断是否过时
	if (days <= 0 && hours <= 0 && minutes <= 0 && seconds <= 0) {
		return "";
	} else {
		var ms = leave3 / 1000;
		if (ms < 10) {
			ms = "0" + ms;
		}
		ms = ms + "";
		ms = ms.substring(0, 5);
		return {
			day : checkTime(days),
			hours : checkTime(hours),
			minutes : checkTime(minutes),
			ms : ms
		};
	}
}