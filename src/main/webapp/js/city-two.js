
$(function(){
	
	var city= document.getElementById("city");
	 var region = document.getElementById("region-city")
	 region.value = city.value;
	/*$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js',function(_result){
        if(remote_ip_info.ret=='1'){
            layer.alert('国家：'+remote_ip_info.county+'\n省：'+remote_ip_info.province+'\n市：'+remote_ip_info.city+'\n区：'+remote_ip_info.district+'\nISP：'+remote_ip_info.isp+'\n类型：'+remote_ip_info.type+'\n其他：'+remote_ip_info.desc);
            //layer.alert(remote_ip_info.city);
        	var city= document.getElementById("city");
            city.textContent = remote_ip_info.city;
            var region = $("#region-city").val()document.getElementById("region-city");
            region.value = remote_ip_info.city;
        }else{
            layer.alert('没有找到匹配的IP地址信息！');
        }
    });*/

	$("#switchCity").click(function(){
		window.location.href="http://localhost:8080";
		/*if($("#triangle").hasClass("add-display") && $("#switchCityUl").hasClass("add-display")){
			$("#triangle").removeClass("add-display");
			$("#switchCityUl").removeClass("add-display")
		}else{
			$("#triangle").addClass("add-display");
			$("#switchCityUl").addClass("add-display")
		}*/
	});
	
	/*$("#switchCityUl li").click(function(){
		if($("#triangle").hasClass("add-display") && $("#switchCityUl").hasClass("add-display")){
			$("#triangle").removeClass("add-display");
			$("#switchCityUl").removeClass("add-display");
			
			var text = $(this).find("a").text();
			var city= document.getElementById("city");
                city.textContent = text;
		}
	});*/
})