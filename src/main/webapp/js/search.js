$(function() {
	var screeningOfContent = document.getElementById("screeningOfContent");
	shutDown = document.getElementById("shutDown"),
	screening = document.getElementById("screening"),
	determine = document.getElementById("determine"),
	cancel = document.getElementById("cancel"),
	bigScreen = document.getElementById("bigScreen"),
	individualButton = document.getElementById("individualButton"),
	enterpriseButton = document.getElementById("enterpriseButton"),
	individual = document.getElementById("individualId"),
	enterprise = document.getElementById("enterpriseId"),
	teamButton = document.getElementById("teamButton"),
	demandButton = document.getElementById("demandButton"),
	team = document.getElementById("team"),
	demand = document.getElementById("demand");

	//筛选
	screening.onclick = function() {
		screeningOfContent.style.display = "block";
		bigScreen.style = "height:200px";
		shutDown.style.display = "block";
	}

	//关闭筛选
	shutDown.onclick = function() {
		screeningOfContent.style.display = "none";
		shutDown.style.display = "none";
		bigScreen.style = "height:26px";
	}

	//确定
	determine.onclick = function() {
		screeningOfContent.style.display = "none";
		shutDown.style.display = "none";
		bigScreen.style = "height:26px";
	}

	//取消
	cancel.onclick = function() {
		screeningOfContent.style.display = "none";
		shutDown.style.display = "none";
		bigScreen.style = "height:26px";
	}

	function closeAll() {
		// 设置所有
		individualButton.style = "background:#fff;color:#666";
		enterpriseButton.style = "background:#fff;color:#666";
		teamButton.style = "background:#fff;color:#666";
		demandButton.style = "background:#fff;color:#666";

		individual.style.display = "none";
		enterprise.style.display = "none";
		team.style.display = "none";
		demand.style.display = "none";
	}
	;

	//个人
	individualButton.onclick = function() {
		closeAll();
		individualButton.style = "background:#39c0fb;color:#fff";
		individual.style.display = "block";
	}

	//企业
	enterpriseButton.onclick = function() {
		closeAll();
		enterpriseButton.style = "background:#39c0fb;color:#fff";
		enterprise.style.display = "block";
	}

	//团队
	teamButton.onclick = function() {
		closeAll();
		teamButton.style = "background:#39c0fb;color:#fff";
		team.style.display = "block";
	}

	//需求
	demandButton.onclick = function() {
		closeAll();
		demandButton.style = "background:#39c0fb;color:#fff";
		demand.style.display = "block";
	}


});