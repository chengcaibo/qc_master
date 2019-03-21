/**
 * 奇虫的 util
 */
qc.util = {

	/** 截取字符串 */
	overflowString (text, maxLength) {
		if (typeof text == 'undefined') {
			return "";
		} else if (text.length != maxLength) {
			if (text.length > (maxLength - 1)) {
				return text.substring(0, (maxLength - 1)) + "…";
			}
		}
		return text;
	},

	// 滚动条被改变时触发的方法
	//
	// 使用方法：
	// 1、nums：填一个数组，存储当滚动条滚动到的位置，
	// 举个栗子： nums = [0,100,200];
	// 
	// 2、funcs：填一个数组，数组里存储JSON对象，其中有两个方法，ge()和lt()
	// ge() 会在 滚动条滚动到大于等于指定位置时执行，否者执行ltFunc()
	// 举个栗子： funcs =[{ge:FUNC,lt:FUNC},{ge:FUNC,lt:FUNC}];
	onScrollTop (nums, funcs) {
		$(document).scroll(function() {
			var top = $(document).scrollTop();
			for (var i = 0; i < nums.length; i++) {
				if (top >= nums[i]) {
					if (typeof funcs[i].ge == "function") funcs[i].ge(top);
				} else {
					if (typeof funcs[i].lt == "function") funcs[i].lt(top);
				}
			}
		});
		$(document).scroll();
	},

	// 获取文件后缀名
	getFileSuffix : function(name) {
		var extStart = name.lastIndexOf(".");
		return name.substring(extStart, name.length).toUpperCase();
	},

	/** 根据评分计算星级，返回 icon 的名称数组 */
	calcScoreView : function(score) {
		var view = [];
		var before = "icon-like-xing-";
		if (score != -1) {
			var scoreInt = Math.round(score);
			var num1 = scoreInt / 2;
			var num2 = parseInt(num1);
			var num3 = num1 - num2;
			// 填充实星
			for (var i = 0; i < num2; i++) {
				view[i] = before + "2"; // 2 = 实星
			}
			// 填充半星和空星
			for (var i = num2; i < 5; i++) {
				if (num3 > 0 && i == num2) {
					view[i] = before + "3"; // 3 = 半星
				} else {
					view[i] = before + "1"; // 1 = 空星
				}
			}
		}
		return view;
	},

	/** 根据生日计算年龄 */
	calcAgeByBirthday : function(birthday) {
		var age = -1;
		var birthdayYear = birthday.getFullYear(); // 获取出生年
		var birthdayMonth = birthday.getMonth(); // 获取出生日
		var birthdayDay = birthday.getDate(); // 获取出生日
		// 获取当前时间
		var today = new Date();
		var todayYear = today.getFullYear();
		var todayMonth = today.getMonth() + 1;
		var todayDay = today.getDate();

		if (todayMonth * 1 - birthdayMonth * 1 < 0) {
			age = (todayYear * 1 - birthdayYear * 1) - 1;
		} else {
			if (todayDay - birthdayDay >= 0) {
				age = (todayYear * 1 - birthdayYear * 1);
			} else {
				age = (todayYear * 1 - birthdayYear * 1) - 1;
			}
		}
		return age * 1;
	},

	calcTime : function(startTime, endTime) {
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
	},
	/** 倒计时 */
	downTime : function(jqobj, date, guoshi) {
		let endTime = date;
		var setInterId = setInterval(function() {
			let obj = jqobj;
			obj.empty();
			let t = qc.util.calcTime(new Date(), endTime);
			if (t != "") {
				let str = `<b>${t.day}</b>天 <b>${t.hours}</b>时 <b>${t.minutes}</b>分 <b>${t.ms}</b>秒`;
				obj.html(str);
			} else {
				obj.html("<b>已结束！</b>");
				clearInterval(setInterId);
				if (typeof guoshi == 'function') guoshi();
			}
		}, 10);
	},
};