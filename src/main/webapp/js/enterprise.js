


//--------------------------------------企业--------------------------------------------------------


//当离开光标后的手机正则
function phone(){
	var PhoneErrorId = document.getElementById("PhoneErrorId");
	var WebsiteErrorId = document.getElementById("WebsiteErrorId");

	var Phone = document.getElementById("phone").value;
//	var reg =/^1[3|4|5|7|8][0-9]\d{4,8}$/; 
	var reg = /^1[3|4|5|7|8][0-9]{9}$/;
	
	if(reg.test(Phone)||Phone.length == 11){
		PhoneErrorId.style.display ="none";
	}else{
		PhoneErrorId.style.display="block";
	}
}

//当离开光标后的网址正则
function TheUrl(){
	
	var TheUrl = document.getElementById("TheUrl").value;
	// var regX =/^http:\/\/([\w-]+\.)+[\w-]+(\/[\w-./?%&=]*)+([a-z]{2,4}?$/; 
	
	var regX =/(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
	
	if(regX.test(TheUrl)){
		WebsiteErrorId.style.display = "none";
	}else{
		TheUrl = "http://"+TheUrl;
		if (regX.test(TheUrl)) {
			document.getElementById("TheUrl").value=TheUrl;
		}else {
			WebsiteErrorId.style.display = "block";	
		}
	}
}


//提交按钮点击
function tijiao(){
	
	var Phone = document.getElementById("phone").value;
//	var reg =/^1[3|4|5|7|8][0-9]\d{4,8}$/; 
	var reg = /^1[3|4|5|7|8][0-9]{9}$/;
	
	if(reg.test(Phone)||Phone.length == 11){
		PhoneErrorId.style.display = "none";
	}else{
		PhoneErrorId.style.display = "block";
	}
	
	var TheUrl = document.getElementById("TheUrl").value;
	// var regX =/^http:\/\/([\w-]+\.)+[\w-]+(\/[\w-./?%&=]*)+([a-z]{2,4}?$/; 
	
	var regX =/(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
	
	if(regX.test(TheUrl)){
		WebsiteErrorId.style.display = "none";
	}else{
		TheUrl = "http://"+TheUrl;
		if (regX.test(TheUrl)) {
			document.getElementById("TheUrl").value=TheUrl;
		}else {
			WebsiteErrorId.style.display = "block";	
		}
	}
	
	var	 Mail = document.getElementById("Mail");
	var  regMail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	var  MailId = document.getElementById("MailId").value;
	
	if(regMail.test(MailId)){
		Mail.style.display = "none";
	}else{
		Mail.style.display = "block";
	}
	
	var LegalPerson = document.getElementById("LegalPerson");
	var reglegalPerson = /^[^%\*\^~\'\"\/\\\<\>\|]+$/g;
	var LegalPersonId = document.getElementById("LegalPersonId").value;
	
	if(reglegalPerson.test(LegalPersonId)){
		LegalPerson.style.display = "none";
	}else{
		LegalPerson.style.display = "block";
	}
	
	
	var LegalPersonphone = document.getElementById("LegalPersonphone");
	var regLegalPersonphone = /^1[3|4|5|8][0-9]\d{4,8}$/;
	var LegalPersonphoneId = document.getElementById("LegalPersonphoneId").value;
	
	if(regLegalPersonphone.test(LegalPersonphoneId)){
		LegalPersonphone.style.display = "none";
	}else{
		LegalPersonphone.style.display = "block";
	}
	
	var DetailedAddressId = document.getElementById("DetailedAddressId").value;
	var DetailedAddress = document.getElementById("DetailedAddress");
	if(DetailedAddressId.length == 0){
		DetailedAddress.style.display = "block";
	}else{
		DetailedAddress.style.display = "none";
	}
	
	var WrongAddressId = document.getElementById("WrongAddressId").value;
	var WrongAddress = document.getElementById("WrongAddress");
	if(WrongAddressId.length == 0){
		WrongAddress.style.display = "block";
	}else{
		WrongAddress.style.display = "none";
	}
	
	
}
/*

$(function (){
	
	// 当 input file 文件被改变的时候触发的事件
	$("#file-logo-parent input[type=file]").change(function(e){
		
		// new 一个 FileReader 对象
		var fr1 = new FileReader();
		
		// 获取 文件列表中的第一个文件
		var creentFile1 = $(this).get(0).files[0];
		
		// 添加进fr中
		fr1.readAsDataURL(creentFile1);
		
		// 当文件读取完毕的时候调用的方法
		fr1.onload=function  (e) {
			var img = $("#file-logo-parent img.LOGO-img").attr("src",this.result);
			img.css("display","inline-block");
		};
	});
	
	
	// 当 input file 文件被改变的时候触发的事件
	$("#file-headPortrait-parent input[type=file]").change(function(e){
		
		// new 一个 FileReader 对象
		var fr2 = new FileReader();
		
		// 获取 文件列表中的第一个文件
		var creentFile2 = $(this).get(0).files[0];
		
		// 添加进fr中
		fr2.readAsDataURL(creentFile2);
		
		// 当文件读取完毕的时候调用的方法
		fr2.onload=function  (e) {
			var img = $("#file-headPortrait-parent img.headPortrait-img").attr("src",this.result);
			img.css("display","inline-block");
		};
	});

	
	//营业执照
	// 当 input file 文件被改变的时候触发的事件
	$("#file-sub-parent input[type=file]").change(function(e){
		
		// new 一个 FileReader 对象
		var fr = new FileReader();
		
		// 获取 文件列表中的第一个文件
		var creentFile = $(this).get(0).files[0];
		
		// 添加进fr中
		fr.readAsDataURL(creentFile);
		
		// 当文件读取完毕的时候调用的方法
		fr.onload=function  (e) {
			var img = $("#file-sub-parent img.BusinessLicense-img").attr("src",this.result);
			img.css("display","inline-block");
			
		};
	});
});

	*/


function mail(){
	var	 Mail = document.getElementById("Mail");
	var  regMail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	var  MailId = document.getElementById("MailId").value;
	
	if(regMail.test(MailId)){
		Mail.style.display = "none";
	}else{
		Mail.style.display = "block";
	}
}

function legalPerson(){
	var LegalPerson = document.getElementById("LegalPerson");
	var reglegalPerson = /^[^%\*\^~\'\"\/\\\<\>\|]+$/g;
	var LegalPersonId = document.getElementById("LegalPersonId").value;
	
	if(reglegalPerson.test(LegalPersonId)){
		LegalPerson.style.display = "none";
	}else{
		LegalPerson.style.display = "block";
	}
}


function LegalPersonphone(){
	var LegalPersonphone = document.getElementById("LegalPersonphone");
	var regLegalPersonphone = /^1[3|4|5|8|7][0-9]\d{4,8}$/;
	var LegalPersonphoneId = document.getElementById("LegalPersonphoneId").value;
	
	if(regLegalPersonphone.test(LegalPersonphoneId)){
		LegalPersonphone.style.display = "none";
	}else{
		LegalPersonphone.style.display = "block";
	}
}


function AnEnterpriseName(){
	var anEnterpriseName = document.getElementById("anEnterpriseName");
	var anEnterpriseNameId = document.getElementById("anEnterpriseNameId").value;
	if((anEnterpriseNameId == "") || (anEnterpriseNameId.length > 43)){
		anEnterpriseName.style.display = "block";
	}else{
		anEnterpriseName.style.display = "none";
	}
}


//------------------------------------------------------------------个人----------------------------------------------------------

 

function increase(){
	var text = '<input type="text" class="leftInput leftInputOne"/><p class="leftP">--</p><input type="text" class="leftInput leftInputTwo"/>';
	$("#money").append(text);
}
function reducess(){
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
	iPhone = document.getElementById("iPhone"),
	realName = document.getElementById("realName"),
	realNameWrong = document.getElementById("realNameWrong");
	
function GerenTijiao(){
	//邮箱正则
	var EmailReg =/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	//电话的正则
	var regphone = /^1[3|4|5|8|7|][0-9]\d{4,8}$/;
	//真实姓名中不可出标点符号正则
	var regrealName = /^[^%\*\^~\'\"\/\\\<\>\.\。\、\’\‘\；\：\=\+\）\（\!\@\#\$\&\|]+$/g;
	//身份证正则（1）15位身份证  （2）18位身份证
	var IdentityReg = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}[0-9Xx]$)/;
	//用户名
	if((Name.value.length !== 0)&&(Name.value.length < 12)) {
		NameWrong.style.display = "none";	
	}else{
		NameWrong.style.display = "block";
	}
	//邮箱
	if(EmailReg.test(Email.value)){
		passWord.style.display = "none";
	}else{
		passWord.style.display = "block";		
	}
	//身份证
	if(IdentityReg.test(IdentityInput.value)){
		Identity.style.display="none"
	}else{
		Identity.style.display="block"				
	}
	//真实姓名
	if((regrealName.test(realName.value)) && (realName.value.length !==0) && (realName.value.length < 12)){
		realNameWrong.style.display = "none";
	}else{
		realNameWrong.style.display = "block";
	}
	//电话
	if(regphone.test(Phone.value) && (Phone.value.length < 12)){
		iPhone.style.display = "none"
	}else{
		iPhone.style.display = "block"
	}
}



	
	
// window.onload = function() {
   		var Banner = document.getElementById("Banner"),
			About  = document.getElementById("About"),
			Group  = document.getElementById("Group"),
			Synthesise = document.getElementById("synthesise"),
			Anniu1 = document.getElementById("anniu1"),
			Anniu2 = document.getElementById("anniu2"),
			Anniu3 = document.getElementById("anniu3");


//$(function() {
//	var bannerSlider = new Slider($('#Synthesise'), {
//		time: 5000,
//		delay: 400,
//		event: 'hover',
//		auto: true,
//		mode: 'fade',
//		controller: $('#zong'),
//		activeControllerCls: 'active'
//	});
////	$('#banner_tabs .flex-prev').click(function() {
////		bannerSlider.prev()
////	});
////	$('#banner_tabs .flex-next').click(function() {
////		bannerSlider.next()
////	});
//})


function classAllDiv() {
	
	$("#Banner").removeClass("show");
	$("#Group").removeClass("show");
	$("#About").removeClass("show");
}

function selectBanner() {
	// 首先隐藏所有的 div
	classAllDiv();
	// 单独显示出自己
	$("#Banner").addClass("show");
}

function selectGroupt() {
	// 首先隐藏所有的 div
	classAllDiv();
	// 单独显示出自己
	$("#Group").addClass("show");
}


function selectAbouty() {
	// 首先隐藏所有的 div
	classAllDiv();
	// 单独显示出自己
	$("#About").addClass("show");
}





//--------------------------时间------------------------------
var lq_datetimepick = false;
$.fn.lqdatetimepicker = function (options) {
    lq_datetimepick = true;
    return this.each(function () {
        var $this = $(this);
        if ($("#lq-datetimepick").length > 0) $("#lq-datetimepick").remove();
        var _this = {
            css: "datetime-day", //datetime-hour 时分样式 ，datetime-day 日期样式
            offset: {
                left : 0,
                top : 10
            },
            dateType: 'H', //H选择时分，D选择日期
            date: {
                'H' : {
                    begin : '8:00', //开始时分
                    end : '23:30', //结束时分
                    step : "30" //时分步长
                },
                'D' : {
                    month : new Date(), //日期默认时间
                    selected : (new Date()).getDate()
                },
                'M' : {
                    begin : 1, //月份开始
                    end : 12, //月份结束
                    selected : (new Date()).getMonth()+1  //月份初始
                },
                'Y' : {
                    begin : 1900, //年份开始
                    end : (new Date()).getFullYear(), //年份结束
                    selected : (new Date()).getFullYear() //年份初始
                }
            },
            selectback : function(){}, //选择时间的事件回调
            callback : function () { } //初始化时间事件回调
        };
        $.extend(_this, options);

        var _obj = $("<div class=\"lq-datetimepick\" id=\"lq-datetimepick\" />");
        var _arr = $("<div class=\"datetime-arr\" />");
        var _container = $("<div class=\"select-datetime\" />");
        var _selectitem = $("<div class=\"datetime-select\" />");
        var _header = $("<dl class=\"datetime-time\"></dl>");
        var _item = $("<dl class=\"datetime-time\"></dl>");


        var _day;

        var _datevalue = $this.val() == '' ? new Date() : new Date($this.val().replace(/-/g, "/"));
        var _dateyear = _datevalue.getFullYear();
        var _datemonth = _datevalue.getMonth()+1;
        var _datedate = _datevalue.getDate();

        var _x = $this.offset().left + _this.offset.left,
            _y = $this.offset().top + $this.outerHeight() + _this.offset.top;

        if(_this.css != undefined || _this.css != ''){
            _header.addClass(_this.css);
            _item.addClass(_this.css);
        }

        if($this.val() != ''){
            _this.date.D.month = new Date($this.val().replace(/-/g, "/"));
        }


        $.fn.lqdatetimepicker.setDateData($this,_obj,_item,_this);

        //日期
        if(_this.dateType == 'D'){

            //年份
            var _select_year = $.fn.lqdatetimepicker.setSelectData(_this,'Y');
            var _selectul_year = $("<div class=\"selectul\" id=\"lqyear\" />");
            var _select_year_t = $("<div class=\"selectfocus\"></div>");
            if(_dateyear != '') _select_year_t.attr("data-value",_dateyear);
            _select_year_t.html("<em>选择年份</em>");
            _select_year_t.appendTo(_selectul_year);
            _select_year.appendTo(_selectul_year);
            _selectul_year.appendTo(_selectitem);
            _selectitem.appendTo(_container);


            //月份
            var _select_month = $.fn.lqdatetimepicker.setSelectData(_this,'M');
            var _selectul_month = $("<div class=\"selectul\" id=\"lqmonth\" />");
            var _select_month_t = $("<div class=\"selectfocus\"></div>");
            if(_datemonth != '') _select_month_t.attr("data-value",_datemonth);
            _select_month_t.html("<em>选择月份</em>");
            _select_month_t.appendTo(_selectul_month);
            _select_month.appendTo(_selectul_month);
            _selectul_month.appendTo(_selectitem);
            _selectitem.appendTo(_container);

            //星期
            _week = $.fn.lqdatetimepicker.intWeek();
            for(var i=0; i<7; i++){
                _day = $("<dt><span>"+_week[i]+"</span></dt>");
                _day.appendTo(_header);
            }
            _header.appendTo(_container);
        }

        _arr.appendTo(_obj);
        _container.appendTo(_obj);
        _item.appendTo(_container);
        _obj.appendTo("body").css({
            left : _x + 'px',
            top : _y  + 'px'
        }).show();
        _this.callback();

        LQ.selectUi.show({
            id:"lqyear",
            hiddenInput:"selectYear",
            pulldown:function(){
                var _year = $("#selectYear").val();
                var _month = $("#selectMonth").val();
                var _day = $(".datetime-time>dd.selected").attr("data-value");
                _day = _day==undefined ? _this.date.D.selected : _day;
				
				_day = parseInt(_day) + 1;
				do {
					_day--;
					var tempMonth = new Date(_year + '/' + _month + '/' + _day).getMonth() + 1;
				} while (tempMonth != _month);
				
                _this.date.D.month = new Date(_year+'/'+_month+'/'+_day);
                $.fn.lqdatetimepicker.setDateData($this,_obj,_item,_this);
            }
        });
        LQ.selectUi.show({
            id:"lqmonth",
            hiddenInput:"selectMonth",
            pulldown:function(){
                var _year = $("#selectYear").val();
                var _month = $("#selectMonth").val();
                var _day = $(".datetime-time>dd.selected").attr("data-value");
                _day = _day==undefined ? _this.date.D.selected : _day;
				
				_day = parseInt(_day) + 1;
				do {
					_day--;
					var tempMonth = new Date(_year + '/' + _month + '/' + _day).getMonth() + 1;
				} while (tempMonth != _month);
				
                _this.date.D.month = new Date(_year+'/'+_month+'/'+_day);
                $.fn.lqdatetimepicker.setDateData($this,_obj,_item,_this);
            }
        });

        _obj.on("click",function(e){
            e.stopPropagation();
        });

        $(document).on("click",function(e){
            if(lq_datetimepick){
                _obj.remove();
            }
        });

    })

};

$.fn.lqdatetimepicker.setDateData = function($this,_obj,_item,_this){
    var _time;
    var _datetime = $.fn.lqdatetimepicker.setDateTime(_this);
    if (typeof(_datetime) == 'object'){
        _item.empty();
        for (var i=0; i<_datetime.length; i++){
            _time = $("<dd data-value="+_datetime[i]+"><em>"+_datetime[i]+"</em></dd>");
            if(_this.dateType == 'D'){
                _time.attr('data-value',_this.date.D.month.getFullYear()+'-'+(_this.date.D.month.getMonth()+1)+'-'+_time.attr('data-value'))
            }
            _time.on("click",function(){
                $this.val($(this).attr("data-value"));
                _obj.remove();
                _this.selectback();
            });
            _time.hover(function(){
                $(this).addClass('over');
            },function(){
                $(this).removeClass('over');
            });
            if($this.val() == _datetime[i]){
                _time.addClass('selected')
            }
            if(($this.val() == '') && (_this.dateType == 'D') && (_this.date.D.month.getDate() == _datetime[i])){
                _time.addClass('current');
            }
            if((new Date($this.val()).getDate() == _datetime[i]) && (_this.dateType == 'D')){
                _time.addClass('current');
            }
            if(_datetime[i] == ''){
                _time.addClass('blank');
            }
            _time.appendTo(_item);
        }
    }
}

$.fn.lqdatetimepicker.setDateTime = function(_this){
    var dateTime;
    if(_this.dateType == 'H'){
        dateTime = $.fn.lqdatetimepicker.intHourTime(_this);
    }else if(_this.dateType == 'D') {
        dateTime = $.fn.lqdatetimepicker.intDayTime(_this);
    }
     return dateTime;
};

$.fn.lqdatetimepicker.setSelectData = function(_this,type){
    var _data;
    var _cell;
    var _select = $("<select></select>");
    if(type == 'Y'){
        _data = $.fn.lqdatetimepicker.intYearTime(_this);
        _cell = '年';
    }
    if(type == 'M'){
        _data = $.fn.lqdatetimepicker.intMonthTime(_this);
        _cell = '月'
    }
    for(var i=0; i<_data.length; i++){
        $("<option></option>").text(_data[i]+_cell).attr("value",_data[i]).appendTo(_select);
    }
    return _select;

};

/*时分*/
$.fn.lqdatetimepicker.intHourTime = function(_this){
    var begindate = _this.date.H.begin;
    var enddate = _this.date.H.end;
    var stepdate = _this.date.H.step;
    var _a = [];
    var _date = new Date();
    var _now = _date.getFullYear()+"/"+(_date.getMonth()+1)+"/"+_date.getDate();
    var _begindate = new Date(_now+" "+begindate);
    var _enddate = new Date(_now+" "+enddate);

    var _hours = _enddate.getHours() - _begindate.getHours();
    var _minutes = _enddate.getMinutes() - _begindate.getMinutes();
    var _len = (_hours*60 + _minutes)/stepdate;

    for(var i=0; i<=_len; i++){
        var _t = $.fn.lqdatetimepicker.dateAdd('M',stepdate*i,_begindate);
        _a.push(_t.getHours()+":"+(_t.getMinutes() >= 10 ? _t.getMinutes() : '0'+_t.getMinutes()));
    }
    return _a;
};

/*日期*/
$.fn.lqdatetimepicker.intDayTime = function(_this){
    var _a = [];
    var _date = _this.date.D.month;
    var _year = _date.getFullYear();
    var _month = _date.getMonth();
    var _week = new Date(_year+'/'+(_month+1)+'/1').getDay(); // 当前月的第一天
    var _day = 32-new Date(_year,_month,32).getDate();
    var _cell = Math.ceil((_week + _day)/7)*7 - (_week + _day);

    for(var w=0; w<_week; w++){
        _a.push('');
    }
    for(var i=0; i<_day; i++){
        _a.push(i+1);
    }
    for(var w=0; w<_cell; w++){
        _a.push('');
    }
    return _a;
};

/*月份*/
$.fn.lqdatetimepicker.intMonthTime = function(_this){
    var _a = [];
    var _month_begin = _this.date.M.begin;
    var _month_end = _this.date.M.end;
    for(var i=_month_begin,j=_month_end+1; i<j; i++){
        _a.push(i);
    }
    return _a;
}

/*年份*/
$.fn.lqdatetimepicker.intYearTime = function(_this){
    var _a = [];
    var _year_begin = _this.date.Y.begin;
    var _year_end = _this.date.Y.end;
    for(var i=_year_begin,j=_year_end+1; i<j; i++){
        _a.push(i);
    }
    return _a;
}

$.fn.lqdatetimepicker.intWeek = function(){
    return ['星期日','星期一','星期二','星期三','星期四','星期五','星期六']
}

$.fn.lqdatetimepicker.dateAdd = function(interval, NumDay, dtDate){
    var dtTmp = new Date(dtDate);
    if(isNaN(dtTmp)) dtTmp = new Date();
    switch(interval.toUpperCase())  {
        case "S" : return new Date(Date.parse(dtTmp) + (1000 * NumDay));
        case "M" : return new Date(Date.parse(dtTmp) + (60000 * NumDay));
        case "H" : return new Date(Date.parse(dtTmp) + (3600000 * NumDay));
        case "D" : return new Date(Date.parse(dtTmp) + (86400000 * NumDay));
        case "W" : return new Date(Date.parse(dtTmp) + ((86400000 * 7) * NumDay));
        case "M" : return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + NumDay, dtTmp.getDate(),  dtTmp.getHours(),  dtTmp.getMinutes(),  dtTmp.getSeconds());
        case "Y" : return new Date((dtTmp.getFullYear()  +  NumDay),  dtTmp.getMonth(),  dtTmp.getDate(),  dtTmp.getHours(),  dtTmp.getMinutes(),  dtTmp.getSeconds());
    }
};








var LQ=jQuery;
LQ.selectUi={
	show:function(options){
		var def={
			id:"", //selectUi的ID值
			hiddenInput:"", //设置下拉列表获取值保存的字段；
			selectInit:"", //接收下拉表数据，数组[{name:name,value:value}]
			selectNext:"", //二级菜单值
			pulldown:function(){},//下拉列表选择后返回函数
			callback:function(){} //callback 初始插件时返回函数
		};
		var ini=$.extend(def,options);
		
		var $this=$("#"+ini.id);
		if($this.length<=0) return;
		
		$this.css({zIndex:10});
		$this.addClass("select_ul_ui");
		
		if(ini.selectInit){
			if(typeof(ini.selectInit)=='object'){
				var option='';
				if(typeof(ini.selectInit[0]) != 'object' && ini.selectInit[0]=='') return;
				for(var i=0; i<ini.selectInit.length; i++){
					option+="<option value="+ini.selectInit[i]['AID']+">"+ini.selectInit[i]['AName']+"</option>";
					//option+="<option value="+ini.selectInit[i]['value']+">"+ini.selectInit[i]['name']+"</option>";
				}
				$this.children('select').remove();
				$this.append('<select>'+option+'</select>');
			}
			
		}
		
		var sel=$this.children("select");
		if(sel.length<=0) return;
		var sellen=sel.children("option").length;
		var ulid;
		var html="";
		sel.hide();
		
		if(ini.hiddenInput){
			$u=ini.hiddenInput;
		}else{
			$u=ini.id;
		}
		
		/*是否传值过来*/
		$val=$this.children(".selectfocus").attr("data-value");
		$options=sel.find("option");
		if($val != undefined){
			if($options.length==0){
				//获取同类值
				val=LQ.selectUi.getPreventData($val);
				LQ.selectUi.appendData(ini.id,val);
				LQ.selectUi.selectOption(sel,$val);
				
			}else{
				LQ.selectUi.selectOption(sel,$val);
				
			}
		}
		
		var items=0;
		for(var i=0; i<sel.children("option").length; i++){
			html+="<li data-val="+sel.children("option").eq(i).val()+">"+sel.children("option").eq(i).html()+"</li>";
			items++;
			
		}
		ulid="ul_"+$u;
		$this.append('<input type=\"hidden\" name=\"'+$u+'\" id=\"'+$u+'\" />');
		
		/*传值后设置hidden值*/
		
		if($val != undefined){
			LQ.selectUi.setHiddenValue($this,$val);
		}
		
		/*是否已经选定值*/
		if(sel.find("option[selected]").text() != ''){
			$this.children(".selectfocus").html('<em>'+sel.find("option[selected]").text()+'</em>');
			$("#"+ini.hiddenInput).val(sel.find("option[selected]").val());
		}
		
		$this.children("#"+ulid+"").remove();
		$this.append('<ul id='+ulid+'>'+html+'</ul>');
		
		var ul=$this.children("ul");
		if(items>10){
			ul.height(200);
		}
		
		ini.callback();
		
		ul.css({zIndex:11}).width($this.width()-2);
		ul.children("li").bind("click",function(e){
			e.stopPropagation();
			$(this).parent().siblings('.selectfocus').html('<em>'+$(this).text()+'</em>');
			$(this).parent().siblings('input[type="hidden"]').val($(this).attr("data-val"));
			ulStatus();
			ini.pulldown();
			
			if(ini.selectNext){
				LQ.selectUi.selectFun(ini.selectNext,$(this).attr("data-val"));
			}
		});
		
		$this.bind("click",function(e){
			e.stopPropagation();
			var id=$(".select_ul_ui").index($this); //获取索引值
			ulhide(id);
			ulStatus();
		});
		
		$(document).bind("click",function(e){
			var status=ul.css("display");
			if(status!="none"){
				ul.hide();
				ul.parent().css({zIndex:11});
			}
		})
		
		function ulStatus(){
			if(ul.css("display")=="none"){
				ul.show();
				ul.parent().css({zIndex:13});
			}else{
				ul.hide();
				ul.parent().css({zIndex:11});
			}
		}
		
		function ulhide(id){
			var len=$(".select_ul_ui").length;
			if(len==0) return;
			for(var i=0; i<len; i++){
				if(i!=id){
					$(".select_ul_ui").eq(i).children("ul").hide().parent().css({zIndex:11});
				}
			}
		}
		
	},
	selectOption:function($sel,$val){
		if($val==undefined) return;
		$sel.find("option").each(function(index,element){
			if($(element).val()==$val){
				$(element).attr("selected","true");
			}
		})
	},
	setHiddenValue:function($this,$val){
		if($val==undefined) return;
		$this.children('input[type="hidden"]').val($val);
	},
	selectFun:function(obj,value){
		var id = obj['selectid'];
		$("#"+id).show();
		$("#"+id).children('.selectfocus').html('<em>'+obj['selectTxt']+'</em>');
		$("#"+id).children('input[type="hidden"]').val('');
		LQ.selectUi.appendData(id,value);
		$("#"+id).find("li").bind("click",function(e){
			e.stopPropagation();
			$(this).parent().siblings('.selectfocus').html('<em>'+$(this).text()+'</em>');
			$(this).parent().siblings('input[type="hidden"]').val($(this).attr("data-val"));
			$(this).parent().hide().css({zIndex:11});
		});
	},
	appendData:function(id,val){
		var items=0;
		var value=LQ.selectUi.getData(val);
		if(value){
			if(typeof(value)=='object'){
				var option='';
				var listr='';
				if(typeof(value[0]) != 'object' && value[0]=='') return;
				for(var i=0; i<value.length; i++){
					option+="<option value="+value[i]['AID']+">"+value[i]['AName']+"</option>";
					listr+="<li data-val="+value[i]['AID']+">"+value[i]['AName']+"</li>";
					items++;
				}
				$("#"+id).children("select").empty();
				$("#"+id).children("select").append(option);
				$("#"+id).children("ul").empty();
				$("#"+id).children("ul").append(listr);
			}
		}
		
		var ul=$("#"+id).children("ul");
		if(items>10){
			ul.height(200);
		}else{
			ul.height('');
		}
		
	},
	getData:function(value){
		var html=[];
		for(var i=0; i<areas.length; i++){
			if(areas[i]["APid"]==value){
				html.push(areas[i]);
			}
		}
		return html;
	},
	getPreventData:function(value){
		var $id=null;
		for(var i=0; i<areas.length; i++){
			if(areas[i]["AID"]==value){
				$id=areas[i]["APid"];
			}
		}
		return $id;
	}
}