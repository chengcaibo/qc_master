


//--------------------------------------企业--------------------------------------------------------


/*//当离开光标后的手机正则
var PhoneErrorId = document.getElementById("PhoneErrorId");
var WebsiteErrorId = document.getElementById("WebsiteErrorId");

function phone() {
	var Phone = document.getElementById("phone").value;
	//	var reg =/^1[3|4|5|7|8][0-9]\d{4,8}$/; 
	var reg = /^1[3|4|5|7|8][0-9]{9}$/;

	if (reg.test(Phone) || Phone.length == 11) {
		PhoneErrorId.style.display = "none";
	} else {
		PhoneErrorId.style.display = "block";
	}
}

//当离开光标后的网址正则
function TheUrl() {
	var TheUrl = document.getElementById("TheUrl").value;
	// var regX =/^http:\/\/([\w-]+\.)+[\w-]+(\/[\w-./?%&=]*)+([a-z]{2,4}?$/; 

	var regX = /(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;

	if (regX.test(TheUrl)) {
		WebsiteErrorId.style.display = "none";
	} else {
		TheUrl = "http://" + TheUrl;
		if (regX.test(TheUrl)) {
			document.getElementById("TheUrl").value = TheUrl;
		} else {
			WebsiteErrorId.style.display = "block";
		}
	}
}


//提交按钮点击
function tijiao() {
	var Phone = document.getElementById("phone").value;
	//	var reg =/^1[3|4|5|7|8][0-9]\d{4,8}$/; 
	var reg = /^1[3|4|5|7|8][0-9]{9}$/;

	if (reg.test(Phone) || Phone.length == 11) {
		PhoneErrorId.style.display = "none";
	} else {
		PhoneErrorId.style.display = "block";
	}

	var TheUrl = document.getElementById("TheUrl").value;
	// var regX =/^http:\/\/([\w-]+\.)+[\w-]+(\/[\w-./?%&=]*)+([a-z]{2,4}?$/; 

	var regX = /(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;

	if (regX.test(TheUrl)) {
		WebsiteErrorId.style.display = "none";
	} else {
		TheUrl = "http://" + TheUrl;
		if (regX.test(TheUrl)) {
			document.getElementById("TheUrl").value = TheUrl;
		} else {
			WebsiteErrorId.style.display = "block";
		}
	}

	var Mail = document.getElementById("Mail");
	var regMail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	var MailId = document.getElementById("MailId").value;

	if (regMail.test(MailId)) {
		Mail.style.display = "none";
	} else {
		Mail.style.display = "block";
	}

	var LegalPerson = document.getElementById("LegalPerson");
	var reglegalPerson = /^[^%\*\^~\'\"\/\\\<\>\|]+$/g;
	var LegalPersonId = document.getElementById("LegalPersonId").value;

	if (reglegalPerson.test(LegalPersonId)) {
		LegalPerson.style.display = "none";
	} else {
		LegalPerson.style.display = "block";
	}


	var LegalPersonphone = document.getElementById("LegalPersonphone");
	var regLegalPersonphone = /^1[3|4|5|8][0-9]\d{4,8}$/;
	var LegalPersonphoneId = document.getElementById("LegalPersonphoneId").value;

	if (regLegalPersonphone.test(LegalPersonphoneId)) {
		LegalPersonphone.style.display = "none";
	} else {
		LegalPersonphone.style.display = "block";
	}

	var DetailedAddressId = document.getElementById("DetailedAddressId").value;
	var DetailedAddress = document.getElementById("DetailedAddress");
	if (DetailedAddressId.length == 0) {
		DetailedAddress.style.display = "block";
	} else {
		DetailedAddress.style.display = "none";
	}

	var WrongAddressId = document.getElementById("WrongAddressId").value;
	var WrongAddress = document.getElementById("WrongAddress");
	if (WrongAddressId.length == 0) {
		WrongAddress.style.display = "block";
	} else {
		WrongAddress.style.display = "none";
	}


}
*/

function uploadImg1(source) {
	var file1 = source.files[0];
	if (window.FileReader) {
		var fr = new FileReader();
		var img1 = document.getElementById('img1');
		fr.onloadend = function(e) {
			img1.src = e.target.result;
		};
		fr.readAsDataURL(file1);
		img1.style.display = 'block';

	}
}

function uploadImg2(source) {
	var file2 = source.files[0];
	if (window.FileReader) {
		var fr = new FileReader();
		var img2 = document.getElementById('img2');
		fr.onloadend = function(e) {
			img2.src = e.target.result;
		};
		fr.readAsDataURL(file2);
		img2.style.display = 'block';

	}
}

function uploadImg3(source) {
	var file3 = source.files[0];
	if (window.FileReader) {
		var fr = new FileReader();
		var img3 = document.getElementById('img3');
		fr.onloadend = function(e) {
			img3.src = e.target.result;
		};
		fr.readAsDataURL(file3);
		img3.style.display = 'block';

	}
}

function uploadImg4(source) {
	var file4 = source.files[0];
	if (window.FileReader) {
		var fr = new FileReader();
		var img4 = document.getElementById('img4');
		fr.onloadend = function(e) {
			img4.src = e.target.result;
		};
		fr.readAsDataURL(file4);
		img4.style.display = 'block';

	}
}

$(function() {


	//营业执照
	// 当 input file 文件被改变的时候触发的事件
	$("#file-sub-parent input[type=file]").change(function(e) {

		// new 一个 FileReader 对象
		var fr = new FileReader();

		// 获取 文件列表中的第一个文件
		var creentFile = $(this).get(0).files[0];

		// 添加进fr中
		fr.readAsDataURL(creentFile);

		// 当文件读取完毕的时候调用的方法
		fr.onload = function(e) {
			var img = $("#file-sub-parent img.BusinessLicense-img").attr("src", this.result);
			img.css("display", "inline-block");

		};
	});
});




//function mail() {
//	var Mail = document.getElementById("Mail");
//	var regMail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
//	var MailId = document.getElementById("MailId").value;
//
//	if (regMail.test(MailId)) {
//		Mail.style.display = "none";
//	} else {
//		Mail.style.display = "block";
//	}
//}
//
//function legalPerson() {
//	var LegalPerson = document.getElementById("LegalPerson");
//	var reglegalPerson = /^[^%\*\^~\'\"\/\\\<\>\|]+$/g;
//	var LegalPersonId = document.getElementById("LegalPersonId").value;
//
//	if (reglegalPerson.test(LegalPersonId)) {
//		LegalPerson.style.display = "none";
//	} else {
//		LegalPerson.style.display = "block";
//	}
//}
//
//
//function LegalPersonphone() {
//	var LegalPersonphone = document.getElementById("LegalPersonphone");
//	var regLegalPersonphone = /^1[3|4|5|8][0-9]\d{4,8}$/;
//	var LegalPersonphoneId = document.getElementById("LegalPersonphoneId").value;
//
//	if (regLegalPersonphone.test(LegalPersonphoneId)) {
//		LegalPersonphone.style.display = "none";
//	} else {
//		LegalPersonphone.style.display = "block";
//	}
//}
//
//function AnEnterpriseName() {
//	var anEnterpriseName = document.getElementById("anEnterpriseName");
//	var anEnterpriseNameId = document.getElementById("anEnterpriseNameId").value;
//	if (anEnterpriseNameId.length < 1) {
//		anEnterpriseName.style.display = "block";
//	} else {
//		anEnterpriseName.style.display = "none";
//	}
//}
$(".basic-form").submit(function() {
	return io.collective.check();
});




$(function() {

	let flag = $("#input-website").val() == "#";
	$("input[name='no-website']").change(function() {
		let flag = $(this).prop("checked");
		$('input[name="website"]').attr("readonly", flag).val(flag ? "#" : "");
	}).attr("checked", flag);
	$('input[name="website"]').attr("readonly", flag);


	/** input Object 的缩写，意思是各个输入框都集中在这里 */
	var io = {
		collective : {
			check : function() {
				var p = io.collective;
				p.enterpriseName.validation(true);
				var count = p.enterpriseName.flag;

				p.introduce.validation(true);
				count += p.introduce.flag;

				p.email.validation(true);
				count += p.email.flag;

				p.address.validation(true);
				count += p.address.flag;

				p.personName.validation(true);
				count += p.personName.flag;

				p.telephone.validation(true);
				count += p.telephone.flag;

				p.scope.validation(true);
				count += p.scope.flag;

				p.theUrl.validation(true);
				count += p.theUrl.flag;

				var flag = (count == 0);
				$("#disabled").attr("disabled", !flag);
				return flag;
			},
			//企业名称
			enterpriseName : {
				obj : $("input[name='enterpriseName']"),
				flag : 1,
				validation : function(isCheck) {
					var p = io.collective.enterpriseName;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".WebsiteError-one").css("display", "block");
					} else if (p.obj.val() > 15) {
						$(".WebsiteError-one").css("display", "block");
					} else {
						p.flag = 0;
						$(".WebsiteError-one").css("display", "none");
					}
					if (typeof isCheck != "boolean" || !isCheck) io.collective.check();
				}
			},
			//企业介绍
			introduce : {
				obj : $("textarea[name='introduce']"),
				flag : 1,
				validation : function(isCheck) {
					var p = io.collective.introduce;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".WebsiteError-two").css("display", "block");
					} else if (p.obj.val() > 1000) {
						$(".WebsiteError-two").css("display", "block");
					} else {
						p.flag = 0;
						$(".WebsiteError-two").css("display", "none");
					}
					if (typeof isCheck != "boolean" || !isCheck) io.collective.check();
				}
			},
			//邮箱
			email : {
				obj : $("input[name='email']"),
				flag : 1,
				validation : function(isCheck) {
					var p = io.collective.email;
					var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".WebsiteError-three").css("display", "block");
					} else if (!(reg.test(p.obj.val()))) {
						$(".WebsiteError-three").css("display", "block");
					} else {
						p.flag = 0;
						$(".WebsiteError-three").css("display", "none");
					}
					if (typeof isCheck != "boolean" || !isCheck) io.collective.check();
				}
			},
			//详细地址
			address : {
				obj : $("input[name='address']"),
				flag : 1,
				validation : function(isCheck) {
					var p = io.collective.address;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".WebsiteError-four").css("display", "block");
					} else if (p.obj.val().length > 200) {
						$(".WebsiteError-four").css("display", "block");
					} else {
						p.flag = 0;
						$(".WebsiteError-four").css("display", "none");
					}
					if (typeof isCheck != "boolean" || !isCheck) io.collective.check();
				}
			},
			//法人名称
			personName : {
				obj : $("input[name='personName']"),
				flag : 1,
				validation : function(isCheck) {
					var p = io.collective.personName;
					var reg = /^[^%\*\^~\'\"\/\\\<\>\|]+$/g;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".WebsiteError-five").css("display", "block");
					} else if (!(reg.test(p.obj.val()))) {
						$(".WebsiteError-five").css("display", "block");
					} else {
						p.flag = 0;
						$(".WebsiteError-five").css("display", "none");
					}
					if (typeof isCheck != "boolean" || !isCheck) io.collective.check();
				}
			},
			//法人电话
			telephone : {
				obj : $("input[name='telephone']"),
				flag : 1,
				validation : function(isCheck) {
					var p = io.collective.telephone;
					var reg = /^1[3|4|5|8|7][0-9]\d{4,8}$/;
					var regTel = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".WebsiteError-six").css("display", "block");
					} else if (!(reg.test(p.obj.val()) || regTel.test(p.obj.val()))) {
						$(".WebsiteError-six").css("display", "block");
					} else {
						p.flag = 0;
						$(".WebsiteError-six").css("display", "none");
					}
					if (typeof isCheck != "boolean" || !isCheck) io.collective.check();
				}
			},
			//范围
			scope : {
				obj : $("textarea[name='businessScope']"),
				flag : 1,
				validation : function(isCheck) {
					var p = io.collective.scope;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".WebsiteError-scope").css("display", "block");
					} else if (p.obj.val() > 1024) {
						$(".WebsiteError-scope").css("display", "block");
					} else {
						p.flag = 0;
						$(".WebsiteError-scope").css("display", "none");
					}
					if (typeof isCheck != "boolean" || !isCheck) io.collective.check();
				}
			},
			//网址
			theUrl : {
				obj : $("#input-website"),
				flag : 1,
				validation : function(isCheck) {
					var p = io.collective.theUrl;
					var reg = /[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
					p.flag = 1;
					if (p.obj.val() == "") {
						$(".WebsiteError-seven").css("display", "block");
					} else if (!(reg.test(p.obj.val())) && p.obj.val() != "#") {
						$(".WebsiteError-seven").css("display", "block");
					} else {
						p.flag = 0;
						$(".WebsiteError-seven").css("display", "none");
					}
					if (typeof isCheck != "boolean" || !isCheck) io.collective.check();
				}
			}
		}
	}

	var temp = {};
	temp = io.collective.enterpriseName;
	temp.obj.blur(temp.validation);

	temp = io.collective.introduce;
	temp.obj.blur(temp.validation);

	temp = io.collective.email;
	temp.obj.blur(temp.validation);

	temp = io.collective.address;
	temp.obj.blur(temp.validation);

	temp = io.collective.personName;
	temp.obj.blur(temp.validation);

	temp = io.collective.telephone;
	temp.obj.blur(temp.validation);

	temp = io.collective.scope;
	temp.obj.blur(temp.validation);

	temp = io.collective.theUrl;
	temp.obj.blur(temp.validation);

})







//------------------------------------------------------------------个人----------------------------------------------------------



function increase() {
	var text = '<input type="text" class="leftInput leftInputOne"/><p class="leftP">--</p><input type="text" class="leftInput leftInputTwo"/>';
	$("#money").append(text);
}
function reducess() {
	var textT = '<input type="text" class="leftInput leftInputOne"/><p class="leftP">--</p><input type="text" class="leftInput leftInputTwo"/>';
	$("#money").remove(textT);
}

var Name = document.getElementById("Name"),
	Email = document.getElementById("email"),
	IdentityInput = document.getElementById("identity"),
	Phone = document.getElementById("phone"),
	NameWrong = document.getElementById("NameWrong"),
	passWord = document.getElementById("passWord"),
	Identity = document.getElementById("Identity"),
	iPhone = document.getElementById("iPhone");

function GerenTijiao() {
	//邮箱正则
	var EmailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	//身份证正则（1）15位身份证  （2）18位身份证
	var IdentityReg = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}[0-9Xx]$)/;
	//用户名
	if (Name.value.length !== 0) {
		NameWrong.style.display = "none";
	} else {
		NameWrong.style.display = "block";
	}
	//邮箱
	if (EmailReg.test(Email.value)) {
		passWord.style.display = "none";
	} else {
		passWord.style.display = "block";
	}
	//身份证
	if (IdentityReg.test(IdentityInput.value)) {
		Identity.style.display = "none"
	} else {
		Identity.style.display = "block"
	}
}





// window.onload = function() {
var Banner = document.getElementById("Banner"),
	About = document.getElementById("About"),
	Group = document.getElementById("Group"),
	Synthesise = document.getElementById("synthesise"),
	Anniu1 = document.getElementById("anniu1"),
	Anniu2 = document.getElementById("anniu2"),
	Anniu3 = document.getElementById("anniu3");
	// 		
	//          function animate(offset) {
	//              //获取的是style.left，是相对左边获取距离，所以第一张图后style.left都为负值，
	//              //且style.left获取的是字符串，需要用parseInt()取整转化为数字。
	//              var newLeft = parseInt(Synthesise.style.left) + offset;
	//              Synthesise.style.left = newLeft + 'px';
	//              console.log(parseInt(Synthesise.style.left))
	//          }
	//
	//          Anniu1.onclick = function() {             
	//              animate(1000);
	//          }
	//          Anniu3.onclick = function() {  
	//              animate(-1000);
	//          }
	//          
	//          if(newLeft<-2000){
	//    		Synthesise.style.left = -600 + 'px';
	// 		}
	// 		if(newLeft>-600){
	//   		Synthesise.style.left = -3000 + 'px';
	// 		}
	//
	//}
	//	


function classAllDiv() {
	$("#Banner").removeClass("show");
	$("#Group").removeClass("show");
	$("#About").removeClass("show");
}

function selectBanner() {
	$(Anniu1).css({
		"border-bottom" : "3px solid #6495ED",
	});

	Anniu2.style = "border-bottom:0";
	Anniu3.style = "border-bottom:0";
	// 首先隐藏所有的 div

	classAllDiv();
	// 单独显示出自己
	$("#Banner").addClass("show");
}

function selectGroupt() {
	Anniu2.style = "border-bottom:3px solid #6495ED;";
	Anniu1.style = "border-bottom:0";
	Anniu3.style = "border-bottom:0";
	// 首先隐藏所有的 div
	classAllDiv();
	// 单独显示出自己
	$("#Group").addClass("show");
}


function selectAbouty() {
	Anniu3.style = "border-bottom:3px solid #6495ED;";
	Anniu1.style = "border-bottom:0";
	Anniu2.style = "border-bottom:0";
	// 首先隐藏所有的 div
	classAllDiv();
	// 单独显示出自己
	$("#About").addClass("show");
}