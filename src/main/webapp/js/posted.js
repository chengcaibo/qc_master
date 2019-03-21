$(function(){
	$("#advertising").click(function(){
//		$("#bounces").css({"position":"absolute","right":"-200px","transition":"1s"});
		if($("#bounces").hasClass("to-add-a-class")){
			$("#bounces").removeClass("to-add-a-class");
		}else{
			$("#bounces").addClass("to-add-a-class");
		}
	});
	
//	$("#advertising").click(function(){
//			$("#bounces").css({"position":"absolute","right":"0px","transition":"1s"});
//	});
	
})


$(function(){
	$("#specialLabel").blur(function(){
		var regphone = /^1[3|4|5|7|8][0-9]{9}$/;
		var phone = $("#specialLabel").val();
		if(regphone.test(phone)){
			$("#wrongCall").css({"display":"none"})
		}else{ 
			$("#wrongCall").css({"display":"block"})
		}
	})
})



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
});




