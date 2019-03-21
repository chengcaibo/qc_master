<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="/js/city.js"></script>
<script src="/js/common/header.js"></script>


<div class="header">
	<div class="header-top">
		<div class="header-top-heart">
			<div class="content">
				目前城市：
				<p id="city"></p>
				<div class="switch-city">
					<!-- [<p id="switchCity">切换城市</p>
					<div class="triangle" id="triangle"></div>
					<ul class="switch-city-ul" id="switchCityUl">
												<li><a>北京</a></li>
						<li><a>上海</a></li>
						<li><a>天津</a></li>
						<li><a>重庆</a></li>
							

						<li class="province">河北省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>承德</a></li>
						<li><a>张家口</a></li>
						<li><a>衡水</a></li>
						<li><a>雄安新区</a></li>
						<li><a>邢台</a></li>
						<li><a>沧州</a></li>
						<li><a>邯郸</a></li>
						<li><a>秦皇岛</a></li>
						<li><a>廊坊</a></li>
						<li><a>保定</a></li>
						<li><a>石家庄</a></li>
						<li><a>涿州</a></li>
						<li><a>燕郊</a></li>
						<li><a>武安</a></li>
						<li><a>三河</a></li>
						<li><a>任丘</a></li>
						<li><a>迁安市</a></li>
						<li><a>正定</a></li>
						<li><a>赵县</a></li>
						<li><a>张北</a></li>
						<li><a>馆陶</a></li>
						<li><a>定州</a></li>
						<li class="province">山西省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>太原</a></li>
						<li><a>朔州</a></li>
						<li><a>临汾</a></li>
						<li><a>忻州</a></li>
						<li><a>大同</a></li>
						<li><a>吕梁</a></li>
						<li><a>运城</a></li>
						<li><a>阳泉</a></li>
						<li><a>晋中</a></li>
						<li><a>晋城</a></li>
						<li><a>临猗</a></li>
						<li><a>清徐</a></li>
						<li><a>长治</a></li>

						<li class="province">内蒙古自治区：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>呼和浩特</a></li>
						<li><a>锡林郭勒</a></li>
						<li><a>包头</a></li>
						<li><a>乌兰察布</a></li>
						<li><a>赤峰</a></li>
						<li><a>巴彦淖尔市</a></li>
						<li><a>鄂尔多斯</a></li>
						<li><a>呼伦贝尔</a></li>
						<li><a>通辽</a></li>
						<li><a>兴安盟</a></li>
						<li><a>乌海</a></li>
						<li><a>阿拉善盟</a></li>
						<li><a>海拉尔</a></li>

						<li class="province">辽宁省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>盘锦</a></li>
						<li><a>沈阳</a></li>
						<li><a>朝阳</a></li>
						<li><a>大连</a></li>
						<li><a>丹东</a></li>
						<li><a>鞍山</a></li>
						<li><a>辽阳</a></li>
						<li><a>锦州</a></li>
						<li><a>本溪</a></li>
						<li><a>抚顺</a></li>
						<li><a>葫芦岛</a></li>
						<li><a>营口</a></li>
						<li><a>铁岭</a></li>
						<li><a>阜新</a></li>
						<li><a>庄河</a></li>
						<li><a>瓦房店</a></li>

						<li class="province">吉林省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>长春</a></li>
						<li><a>吉林</a></li>
						<li><a>公主岭</a></li>
						<li><a>辽源</a></li>
						<li><a>白山</a></li>
						<li><a>通化</a></li>
						<li><a>白城</a></li>
						<li><a>松原</a></li>
						<li><a>延边</a></li>
						<li><a>四平</a></li>
						
						<li class="province">黑龙江省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>哈尔滨</a></li>
						<li><a>大庆</a></li>
						<li><a>齐齐哈尔</a></li>
						<li><a>牡丹江</a></li>
						<li><a>绥化</a></li>
						<li><a>佳木斯</a></li>
						<li><a>大兴安岭</a></li>
						<li><a>七台河</a></li>
						<li><a>伊春</a></li>
						<li><a>黑河</a></li>
						<li><a>鹤岗</a></li>
						<li><a>双鸭山</a></li>
						<li><a>鸡西</a></li>

						<li class="province">江苏省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>苏州</a></li>
						<li><a>南京</a></li>
						<li><a>无锡</a></li>
						<li><a>常州 </a></li>
						<li><a>徐州</a></li>
						<li><a>南通</a></li>
						<li><a>扬州</a></li>
						<li><a>盐城</a></li>
						<li><a>淮安</a></li>
						<li><a>连云港</a></li>
						<li><a>泰州</a></li>
						<li><a>宿迁</a></li>
						<li><a>镇江</a></li>
						<li><a>泰兴 </a></li>
						<li><a>新沂</a></li>
						<li><a>兴化</a></li>
						<li><a>扬中 </a></li>
						<li><a>东海</a></li>
						<li><a>海门</a></li>
						<li><a>溧阳</a></li>
						<li><a>启东</a></li>
						<li><a>如皋</a></li>
						<li><a>大丰</a></li>
						<li><a>沭阳</a></li>
						<li><a>泗阳</a></li>
						<li><a>泗洪</a></li>
						<li><a>昆山</a></li>
						<li><a>如东</a></li>
						<li><a>金坛</a></li>
						<li><a>姜堰</a></li>
						<li><a>灌云</a></li>
						<li><a>灌南</a></li>
						<li><a>宝应县</a></li>
						<li><a>丹阳</a></li>
						<li><a>东台</a></li>
						<li><a>海安</a></li>
						<li><a>建湖</a></li>
						<li><a>靖江</a></li>
						<li><a>沛县</a></li>
						<li><a>邳州</a></li>

						<li class="province">浙江省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>杭州</a></li>
						<li><a>宁波</a></li>
						<li><a>温州</a></li>
						<li><a>金华</a></li>
						<li><a>嘉兴</a></li>
						<li><a>台州</a></li>
						<li><a>绍兴</a></li>
						<li><a>湖州</a></li>
						<li><a>丽水</a></li>
						<li><a>衢州</a></li>
						<li><a>舟山</a></li>
						<li><a>乐清</a></li>
						<li><a>东阳</a></li>
						<li><a>德清</a></li>
						<li><a>海宁</a></li>
						<li><a>嘉善</a></li>
						<li><a>长兴</a></li>
						<li><a>慈溪</a></li>
						<li><a>桐乡</a></li>
						<li><a>温岭</a></li>
						<li><a>象山</a></li>
						<li><a>诸暨</a></li>
						<li><a>余姚</a></li>
						<li><a>义乌</a></li>
						<li><a>瑞安</a></li>
						<li><a>安吉</a></li>
						<li><a>苍南</a></li>
						<li><a>临海</a></li>
						<li><a>永康</a></li>
						<li><a>玉环</a></li>
						
						<li class="province">安徽省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>合肥</a></li>
						<li><a>芜湖</a></li>
						<li><a>蚌埠</a></li>
						<li><a>阜阳</a></li>
						<li><a>淮南</a></li>
						<li><a>安庆</a></li>
						<li><a>宿州</a></li>
						<li><a>六安</a></li>
						<li><a>淮北</a></li>
						<li><a>滁州</a></li>
						<li><a>马鞍山</a></li>
						<li><a>天长</a></li>
						<li><a>宁国</a></li>
						<li><a>桐城</a></li>
						<li><a>霍邱</a></li>
						<li><a>和县</a></li>
						<li><a>巢湖</a></li>
						<li><a>池州</a></li>
						<li><a>黄山</a></li>
						<li><a>亳州</a></li>
						<li><a>宣城</a></li>
						<li><a>铜陵</a></li>


						<li class="province">福建省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>福州</a></li>
						<li><a>厦门</a></li>
						<li><a>泉州</a></li>
						<li><a>莆田</a></li>
						<li><a>漳州</a></li>
						<li><a>宁德</a></li>
						<li><a>三明</a></li>
						<li><a>南平 </a></li>
						<li><a>龙岩</a></li>
						<li><a>武夷山</a></li>
						<li><a>石狮 </a></li>
						<li><a>晋江</a></li>
						<li><a>南安</a></li>
						<li><a>龙海 </a></li>

						<li class="province">江西省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>乐平</a></li>
						<li><a>永新</a></li>
						<li><a>鹰潭</a></li>
						<li><a>新余</a></li>
						<li><a>景德镇</a></li>
						<li><a>抚州</a></li>
						<li><a>萍乡</a></li>
						<li><a>上饶</a></li>
						<li><a>吉安</a></li>
						<li><a>宜春</a></li>
						<li><a>九江</a></li>
						<li><a>赣州</a></li>
						<li><a>南昌</a></li>

						<li class="province">山东省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>青岛</a></li>
						<li><a>济南</a></li>
						<li><a>烟台</a></li>
						<li><a>潍坊</a></li>
						<li><a>临沂</a></li>
						<li><a>淄博</a></li>
						<li><a>济宁</a></li>
						<li><a>泰安</a></li>
						<li><a>聊城</a></li>
						<li><a>威海</a></li>
						<li><a>枣庄</a></li>
						<li><a>德州</a></li>
						<li><a>日照</a></li>
						<li><a>东营</a></li>
						<li><a>菏泽</a></li>
						<li><a>邹平</a></li>
						<li><a>邹城</a></li>
						<li><a>招远</a></li>
						<li><a>新泰</a></li>
						<li><a>滕州</a></li>
						<li><a>乳山</a></li>
						<li><a>荣成</a></li>
						<li><a>青州</a></li>
						<li><a>蓬莱</a></li>
						<li><a>莱州</a></li>
						<li><a>莒县</a></li>
						<li><a>桓台</a></li>
						<li><a>广饶</a></li>
						<li><a>高密</a></li>
						<li><a>肥城</a></li>
						<li><a>单县</a></li>
						<li><a>曹县</a></li>
						<li><a>龙口</a></li>
						<li><a>寿光</a></li>
						<li><a>诸城</a></li>
						<li><a>垦利</a></li>
						<li><a>章丘</a></li>
						<li><a>莱芜</a></li>
						<li><a>滨州</a></li>
						
						<li class="province">河南省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>郑州</a></li>
						<li><a>洛阳</a></li>
						<li><a>新乡</a></li>
						<li><a>南阳</a></li>
						<li><a>许昌</a></li>
						<li><a>平顶山</a></li>
						<li><a>安阳</a></li>
						<li><a>焦作</a></li>
						<li><a>商丘</a></li>
						<li><a>开封</a></li>
						<li><a>濮阳</a></li>
						<li><a>长垣</a></li>
						<li><a>偃师</a></li>
						<li><a>项城</a></li>
						<li><a>汝州</a></li>
						<li><a>杞县</a></li>
						<li><a>灵宝</a></li>
						<li><a>禹州</a></li>
						<li><a>鄢陵</a></li>
						<li><a>明港</a></li>
						<li><a>济源</a></li>
						<li><a>鹤壁</a></li>
						<li><a>三门峡</a></li>
						<li><a>漯河</a></li>
						<li><a>驻马店</a></li>
						<li><a>信阳</a></li>
						<li><a>周口</a></li>

						<li class="province">湖北省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>武汉</a></li>
						<li><a>宜昌</a></li>
						<li><a>襄阳</a></li>
						<li><a>荆州</a></li>
						<li><a>十堰</a></li>
						<li><a>黄石</a></li>
						<li><a>孝感</a></li>
						<li><a>黄冈</a></li>
						<li><a>恩施</a></li>
						<li><a>荆门</a></li>
						<li><a>咸宁</a></li>
						<li><a>枣阳</a></li>
						<li><a>汉川</a></li>
						<li><a>宜都</a></li>
						<li><a>神农架</a></li>
						<li><a>仙桃</a></li>
						<li><a>天门</a></li>
						<li><a>潜江</a></li>
						<li><a>随州</a></li>
						<li><a>鄂州</a></li>

						<li class="province">湖南省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>长沙</a></li>
						<li><a>株洲</a></li>
						<li><a>益阳</a></li>
						<li><a>常德</a></li>
						<li><a>衡阳</a></li>
						<li><a>湘潭</a></li>
						<li><a>岳阳</a></li>
						<li><a>郴州</a></li>
						<li><a>邵阳</a></li>
						<li><a>怀化</a></li>
						<li><a>永州</a></li>
						<li><a>娄底</a></li>
						<li><a>湘西</a></li>
						<li><a>张家界</a></li>
						<li><a>醴陵</a></li>

						<li class="province">广东省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>深圳</a></li>
						<li><a>广州</a></li>
						<li><a>东莞</a></li>
						<li><a>佛山</a></li>
						<li><a>中山</a></li>
						<li><a>珠海</a></li>
						<li><a>惠州</a></li>
						<li><a>江门</a></li>
						<li><a>汕头</a></li>
						<li><a>湛江</a></li>
						<li><a>肇庆</a></li>
						<li><a>茂名</a></li>
						<li><a>海丰</a></li>
						<li><a>博罗</a></li>
						<li><a>惠东</a></li>
						<li><a>顺德</a></li>
						<li><a>阳春</a></li>
						<li><a>台山</a></li>
						<li><a>潮州</a></li>
						<li><a>汕尾</a></li>
						<li><a>云浮</a></li>
						<li><a>河源</a></li>
						<li><a>韶关</a></li>
						<li><a>阳江</a></li>
						<li><a>清远</a></li>
						<li><a>梅州</a></li>
						<li><a>揭阳</a></li>

						<li class="province">广西省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>南宁</a></li>
						<li><a>柳州</a></li>
						<li><a>桂林</a></li>
						<li><a>玉林</a></li>
						<li><a>梧州</a></li>
						<li><a>北海</a></li>
						<li><a>贵港</a></li>
						<li><a>钦州</a></li>
						<li><a>百色</a></li>
						<li><a>河池</a></li>
						<li><a>来宾</a></li>
						<li><a>贺州</a></li>
						<li><a>防城港</a></li>
						<li><a>崇左</a></li>

						<li class="province">海南省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>海口</a></li>
						<li><a>三亚</a></li>
						<li><a>五指山</a></li>
						<li><a>三沙</a></li>
						<li><a>琼海</a></li>
						<li><a>文昌</a></li>
						<li><a>万宁</a></li>
						<li><a>屯昌</a></li>
						<li><a>琼中</a></li>
						<li><a>陵水</a></li>
						<li><a>东方</a></li>
						<li><a>定安</a></li>
						<li><a>澄迈</a></li>
						<li><a>保亭</a></li>
						<li><a>白沙</a></li>
						<li><a>儋州</a></li>

						<li class="province">四川省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>成都</a></li>
						<li><a>简阳</a></li>
						<li><a>广汉</a></li>
						<li><a>安岳</a></li>
						<li><a>甘孜</a></li>
						<li><a>阿坝</a></li>
						<li><a>巴中</a></li>
						<li><a>雅安</a></li>
						<li><a>广元</a></li>
						<li><a>凉山</a></li>
						<li><a>资阳</a></li>
						<li><a>广安</a></li>
						<li><a>眉山</a></li>
						<li><a>攀枝花</a></li>
						<li><a>遂宁</a></li>
						<li><a>内江</a></li>
						<li><a>达州</a></li>
						<li><a>泸州</a></li>
						<li><a>乐山</a></li>
						<li><a>自贡</a></li>
						<li><a>宜宾</a></li>
						<li><a>南充</a></li>
						<li><a>德阳</a></li>
						<li><a>绵阳</a></li>

						<li class="province">贵州省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>贵阳</a></li>
						<li><a>遵义</a></li>
						<li><a> 黔东南（凯里）</a></li>
						<li><a> 黔南（都匀）</a></li>
						<li><a>六盘水</a></li>
						<li><a>毕节</a></li>
						<li><a> 安顺</a></li>
						<li><a>黔西南（兴义）</a></li>
						<li><a>仁怀</a></li>
						<li><a>黔南（都匀）</a></li>


						<li class="province">云南省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>昆明</a></li>
						<li><a>曲靖</a></li>
						<li><a>大理</a></li>
						<li><a>怒江 </a></li>
						<li><a>迪庆</a></li>
						<li><a>临沧</a></li>
						<li><a>保山</a></li>
						<li><a>普洱</a></li>
						<li><a>德宏</a></li>
						<li><a>昭通</a></li>
						<li><a>西双版纳</a></li>
						<li><a>楚雄</a></li>
						<li><a>文山</a></li>
						<li><a>丽江</a></li>
						<li><a>玉溪</a></li>
						<li><a>红河</a></li>

						<li class="province">西藏：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>拉萨</a></li>
						<li><a>日喀则</a></li>
						<li><a>山南</a></li>
						<li><a>林芝</a></li>
						<li><a>昌都</a></li>
						<li><a>那曲</a></li>
						<li><a>阿里</a></li>
						<li><a>日土</a></li>
						<li><a>改则</a></li>

						<li class="province">陕西省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>西安</a></li>
						<li><a>咸阳</a></li>
						<li><a>宝鸡</a></li>
						<li><a>神木</a></li>
						<li><a>铜川</a></li>
						<li><a>商洛</a></li>
						<li><a>安康</a></li>
						<li><a>延安</a></li>
						<li><a>榆林</a></li>
						<li><a>汉中</a></li>
						<li><a>渭南</a></li>

						<li class="province">甘肃省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>兰州</a></li>
						<li><a>天水</a></li>
						<li><a>白银</a></li>
						<li><a>庆阳</a></li>
						<li><a>甘南</a></li>
						<li><a>嘉峪关</a></li>
						<li><a>临夏</a></li>
						<li><a>陇南</a></li>
						<li><a>金昌</a></li>
						<li><a>定西</a></li>
						<li><a>武威</a></li>
						<li><a>张掖</a></li>
						<li><a>酒泉</a></li>
						<li><a>平凉</a></li>


						<li class="province">青海省：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>西宁</a></li>
						<li><a>海西</a></li>
						<li><a>海北</a></li>
						<li><a>果洛</a></li>
						<li><a>海东</a></li>
						<li><a>黄南</a></li>
						<li><a>玉树</a></li>
						<li><a>海南</a></li>


						<li class="province">宁夏：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>银川</a></li>
						<li><a>吴忠</a></li>
						<li><a>石嘴山</a></li>
						<li><a>中卫</a></li>
						<li><a>固原</a></li>

						<li class="province">新疆：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>	
						<li><a>乌鲁木齐</a></li>
						<li><a>昌吉</a></li>
						<li><a>巴音郭楞</a></li>
						<li><a>伊犁</a></li>
						<li><a>阿克苏</a></li>
						<li><a>喀什</a></li>
						<li><a>哈密</a></li>
						<li><a>克拉</a></li>
						<li><a>玛依</a></li>
						<li><a>博尔塔拉</a></li>
						<li><a>吐鲁番</a></li>
						<li><a>和田</a></li>
						<li><a>石河子</a></li>
						<li><a>克孜勒苏</a></li>
						<li><a>阿拉尔</a></li>
						<li><a>五家渠</a></li>
						<li><a>图木舒克</a></li>
						<li><a>库尔勒</a></li>
						<li><a>阿勒泰</a></li>
						<li><a>塔城</a></li>

						<li class="province">其他：</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>
						<li><a>香港</a></li>
						<li><a>澳门</a></li>
						<li><a>台湾</a></li>
						<li><a>其他</a></li>
					</ul>-->
				</div>
				<!-- <a href="#">深圳</a> <a href="#">香港</a> <a href="#">东莞</a> <a href="#">北京</a> ]-->
			</div>
		</div>
	</div>
	<div class="nav">
		<div class="nav-item nav-logo">
			<a href="/" title="点击返回主页" class="nav-logo-a"> <img
				src="/images/logo-2.png" alt="奇虫Logo">
			</a>
		</div>

		<ul class="nav-item nav-list">
			<li><a href="/" title="首页" class="index-hover">首页</a></li>
			<li class="nav-list-search">
				<div class="search">
					<form id="header-search-form" action="/journalism">
						<a href="javascript:;" title="搜索" class="nav-item-child-a">
							<button class="search_btn">
								<i class="icon-search"></i>
							</button>
						</a> <input type="text" name="s" value="${s}" placeholder="职业技能搜索" />
						<input type="hidden" id="region-city" value="" name="regionCity" />
						<!-- <input type="hidden" value="20" name="pageSize">
							<input type="hidden" value="1" name="pageNum"> -->
						<!--<button class="btn btn-search">搜一搜</button>-->
					</form>
				</div>
			</li>
			<li class="information-box" style="left: 30px;">
				<a href="/user/messg">
					<span class="messgSum">0</span><i class="icon-youxiang"></i>
				</a>
			</li>
		</ul>

		<ul
			class="nav-item nav-sign <c:if test="${isLogin}">common-hide</c:if>">
			<li class="login-page"><a class="btn btn-default" href="/login"
				title=""><i class="icon-denglu"></i>登录</a></li>
			<li><a class="btn btn-default" href="/signup-personal">注册</a></li>
		</ul>
		<c:if test="${isLogin}">
			<%-- Set用户信息 --%>
			<c:if test="${!empty currentUserInfo}">
				<c:set var="genderClass"
					value="icon-gender-${currentUserInfo.gender.id}" />
				<c:set var="avatar" value="${currentUserInfo.avatar}" />
				<c:set var="nickName" value="${currentUserInfo.nickName}" />
				<c:set var="introduce" value="${currentUserInfo.introduce}" />
			</c:if>
			<%-- Set企业信息 --%>
			<c:if test="${!empty currentEntepriseInfo}">
				<c:set var="genderClass" value="" />
				<c:set var="avatar" value="${currentEntepriseInfo.logo}" />
				<c:set var="nickName" value="${currentEntepriseInfo.enterpriseName}" />
				<c:set var="introduce" value="${currentEntepriseInfo.introduce}" />
			</c:if>
			<ul class="nav-item nav-uesr-info common-show-inline-block"
				style="position: relative;">
				<li><a href="/center" title="点击进入个人中心"><img src="${avatar}"
						alt="头像"></a>
					<div class="nav-item-child">
						<div class="user-position"></div>
						<div class="user-username">
							<p title="${nickName}">${nickName}<i class="${genderClass}"></i>
							</p>
						</div>
						<hr
							style="border-bottom: 1px solid #ccc;width: 100px;margin: 0 auto;">
						<div class="user-introduce">
							<p title="${introduce}">${introduce}</p>
						</div>
						<a class="btn btn-default btn-user-info" href="/center"
							title="点击进入个人中心">个人中心</a> <a
							class="btn btn-warning btn-user-info btn-double-1" href="/login"
							title="切换账号">切换账号</a> <a
							class="btn btn-danger btn-user-info btn-double-2" href="/logoff"
							title="退出登录">退出登录</a>
						<div class="common-clear-both"></div>
					</div></li>
			</ul>
		</c:if>
	</div>
</div>