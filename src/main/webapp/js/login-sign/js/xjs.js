var Geren = document.getElementById("geren"),
	Geren2 = document.getElementById("geren2"),
	yx = document.getElementById("yx"),
	sm = document.getElementById("sm"),
	sj = document.getElementById("sj"),
	yxzc = document.getElementById("yxzc"),
	sjzc = document.getElementById("sjzc"),
	smzc = document.getElementById("smzc"),
	Qiye = document.getElementById("qiye"),
	qiye2 = document.getElementById("qiye2"),
	Zq = document.getElementById("zq"),
	Yxyw = document.getElementById("yxyw"),
	Mmyw = document.getElementById("mmyw"),
	Sjh = document.getElementById("sjh"),
	Sjhs = document.getElementById("sjhs"),
	Syzm = document.getElementById("syzm"),
	Mcgc = document.getElementById("mcgc"),
	Mz = document.getElementById("mz"),
	Xiaobiao = document.getElementById("xiaobiao"),
	qiyephone = document.getElementById("qiyephone"),
	YxzcRadio = document.getElementById("yxzcRadio");






function usernames() {
	var Name = document.getElementById("username").value;

	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;

	if (reg.test(Name)) {
		Yxyw.style.display = "none";
	} else {
		Yxyw.style.display = "block";
	}
}


// delete
function userpasswords() {
	var Userpassword = document.getElementById("userpassword").value;

	if (Userpassword.length < 6) {
		Mmyw.style.display = "block";
		Mmyw.style = "top:217px";
	} else {
		Mmyw.style.display = "none";
	}
}



function sjzcnames() {
	var Name = document.getElementById("sjname").value;

	var reg = /^1[3|4|5|7|8][0-9]{9}$/;

	if (reg.test(Name)) {
		Sjh.style.display = "none"
	} else {
		Sjh.style.display = "block";

	}
}

function sjzcpasswords() {
	var Sjpassword = document.getElementById("sjpassword").value;

	if (Sjpassword.length < 6) {
		Mmyw.style.display = "block";
		Mmyw.style = "top:217px;";
	} else {
		Mmyw.style.display = "none";
	}
}

function sjzcyxyzms() {
	var Sjyzm = document.getElementById("sjyzm").value;
	var yz = document.getElementById("slogin").innerHTML;

	if (Sjyzm === yz) {
		Syzm.style.display = "none";
	} else {
		Syzm.style.display = "block";

	}
}

var YxzcRadioInput = document.getElementById("yxzcRadioInput");
var flag = false;

function userzhuce() {
	if (YxzcRadioInput.checked !== flag) {
		console.log(YxzcRadioInput.checked == flag);
	} else {
		console.log(YxzcRadioInput.checked == flag);
	}
}

function sjzhuce() {
	if (YxzcRadioInput.checked !== flag) {
		layer.alert("1");
		console.log(YxzcRadioInput.checked == flag);
	} else {
		layer.alert("2");
		console.log(YxzcRadioInput.checked == flag);
	}
}

function qiyezhuce() {
	if (YxzcRadioInput.checked !== flag) {
		layer.alert("1");
		console.log(YxzcRadioInput.checked == flag);
	} else {
		layer.alert("2");
		console.log(YxzcRadioInput.checked == flag);
	}
}

function qiyenames() {
	var Qiyename = document.getElementById("qiyename").value;

	if (Qiyename.length < 46 && Qiyename.length > 0) {
		Mcgc.style.display = "none";
	} else {
		Mcgc.style.display = "block";
	}
}

function qiyenamebs() {
	var Qiyenameb = document.getElementById("qiyenameb").value;

	var reg = /^(?=[0-9a-zA-Z@_.]+$)/;

	if (!(reg.test(Qiyenameb)) && (Qiyenameb.length < 5) && (Qiyenameb.length > 0)) {
		Mz.style.display = "none";
	} else {
		Mz.style.display = "block";
	}
}

function qiyenamecs() {
	var Qiyenamec = document.getElementById("qiyenamec").value;

	var regb = /^1[3|4|5|7|8][0-9]{9}$/;

	if (regb.test(Qiyenamec)) {
		qiyephone.style.display = "none";

	} else {
		qiyephone.style.display = "block";
	}
}

function qiyepasswords() {
	var Qiyepassword = document.getElementById("qiyepassword").value;

	if (Qiyepassword.length < 6) {
		Mmyw.style.display = "block";
		Mmyw.style = "top:285px;left:284px;"
	} else {
		Mmyw.style.display = "none";
	}
}

function qiyeyzms() {
	var Qiyeyzm = document.getElementById("qiyeyzm").value;
	var yz = document.getElementById("slogin").innerHTML;

	if (Qiyeyzm === yz) {
		Syzm.style.display = "none";
	} else {
		Syzm.style.display = "block";
		Syzm.style = "top:336px;left:239px;"
	}
}


var SmzcGeren = document.getElementById("smzc").display == false;


$(function() {

	$("#qiye").click(function() {
		console.log("点击了企业");

		Geren.style.background = "#fff";
		Qiye.style.background = "#39c0fb";
		Geren.style.background = "";
		Geren2.style.display = "none";
		qiye2.style.display = "block";
		slogin.style.display = "block";
		slogin.style = "position:absolute;top:314px;left:258px";
		Yxyw.style.display = "none";
		Mmyw.style.display = "none";
		Sjh.style.display = "none;";
		qiyephone.display = "none";
		Syzm.style.display = "none";
		Mcgc.style.display = "none";
		Mz.style.display = "none";
		Xiaobiao.style.display = "none";
	});

	$("#geren").click(function() {
		Geren.style.background = "#39c0fb";
		Qiye.style.background = "";
		Geren2.style.display = "block";
		qiye2.style.display = "none";
		Yxyw.style.display = "none";
		Mmyw.style.display = "none";
		Sjh.style.display = "none";
		Syzm.style.display = "none";
		Mcgc.style.display = "none";
		Mz.style.display = "none";
		slogin.style = "top:242px;left:262px";
		Xiaobiao.style.display = "block";
		Qiye.style.background = "#fff";
		qiyephone.display = "none";
	});

});