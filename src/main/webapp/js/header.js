/**
 * 登录后的展示头像的方法
 */
function loginShow(data){
	var userInfo = data.object;
	if(userInfo == null){
		erryFunctionOne();
	}else{
		console.log(userInfo);
		$("nav-item nav-sign").css({"display":"none"});
		$("nav-item nav-avatar").append('<li><img src="'+userInfo.avatar.url+'"/></li>');
		$("nav-item nav-avatar").css({"display":"block"});
	}
}

	

/**
 * 测试是否登录
 */
$(function(){
	//打开页面时判断是否登录
		$.ajax({
			url : "/loginShow",
			type : "POST",
			dataType : "json",
			error : erryFunctionOne, //错误执行方法    
			success : loginShow
		});
		  
})


