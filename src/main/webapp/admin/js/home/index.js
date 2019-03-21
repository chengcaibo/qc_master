var app;
(() => {

	/** 图标数字框 */
	Vue.component("icon-number", {
		props : [ 'iconName', 'number', 'textName', 'color', 'clickable', 'idx', 'type' ],
		template : `
			<div class="icon-number-box" :style="boxStyle" >
				<div class="icon-number-content">
					<i :class="iconName"></i>
					<span>{{number}}</span>
				</div>
				<div class="icon-number-detail clickable" :style="textStyle" @click="onClick" v-if='clickable===true'>
					<div class="text">{{textName}}</div>
					<div class="qc-icon icon-you1"></div>
				</div>
				<div class="icon-number-detail" :style="textStyle" v-else>{{textName}}</div>
			</div>
		`,
		computed : {
			boxStyle : function() {
				return `background-color: ${this.color}; border-color: ${this.color};`;
			},
			textStyle : function() {
				return `color: ${this.color};`;
			}
		},
		methods : {
			onClick () {
				this.$emit("click-detail", {
					index : this.idx,
					type : this.type
				});
			}
		}
	});

	const vm = new Vue({
		el : "#app",
		components : {},
		data : {
			hideLoading : false,
			demands : [],
			personalSignArray : [],
			enterpriseSignArray : [],
			demandThreeDaysArray : [],
		},
		mounted () {},
		methods : {
			onClickDetail (event) {
				let index = event.index;
				if (event.type === 'personal') {
					let item = this.personalSignArray[index];
					this[item.eventName]();

				} else if (event.type === 'enterprise') {
					let item = this.enterpriseSignArray[index];
					this[item.eventName]();

				} else {
					console.error("未识别的type：" + event.type)
				}
			},
			/*--begin --*/
			onClickPersonalTotal () {
				// window.open("/admin/users/personal", '_blank').location;
				window.location.href = "/admin/users/personal";
			},
			onClickEnterpriseTotal () {
				// window.open("/admin/users/enterprise", '_blank').location;
			},
		/*-- end --*/
		}
	});

	/** 刷新按钮 */
	let reloadButton = $("#btn-reload");
	reloadButton.click(function() {
		try {
			vm.hideLoading = false;
			Promise.all([
				$.ajax({
					url : "/admin/count/sign-personal",
					dataType : "JSON"
				}),
				$.ajax({
					url : "/admin/count/sign-enterprise",
					dataType : "JSON"
				}),
				$.ajax({
					url : "/admin/count/three-days-demand",
					dataType : "JSON"
				})
			]).then((values) => {

				let [{result: personalSign}, {result: enterpriseSign}, {result: demandThree}] = values;

				let plus = (obj, name) => {
					if (obj[name] > 0)
						obj[name] = "+" + obj[name];
				}

				[ personalSign, enterpriseSign, demandThree ].forEach((value, index) => {
					let list = [];
					let eventName = 'Personal';
					let displayName = "注册";
					let colors = [ "#337ab7", "#5cb85c", "#E9967A", "#f0ad4e" ];
					let icons = [ "icon-yonghu", "icon-xinyonghu1", "icon-xinyonghu", "icon-daimabijiao" ];
					if (index === 1)
						eventName = 'Enterprise',
						colors = [ "#B06E4F", "#D4AE50", "#6495ED", "#389C65" ],
						icons[0] = "icon-enterprise";
					if (index === 2)
						eventName = 'Demand',
						displayName = "发布",
						colors = [ "#BA61BA", "#1E90FF", "#808000", "#DE5A4B" ],
						icons = [ "icon-xuqiu", "icon-jiahao2", "icon-jiahao", "icon-daimabijiao" ];
					;

					list.push({
						number : value.total,
						iconName : icons[0],
						textName : `总${displayName}量`,
						color : colors[0],
						clickable : index === 0 ? true : false,
						eventName : `onClick${eventName}Total`,
					});

					plus(value, "yesterday");
					list.push({
						number : value.yesterday,
						iconName : icons[1],
						textName : `昨日新${displayName}量`,
						color : colors[1],
						clickable : false,
						eventName : `onClick${eventName}Yesterday`,
					});

					plus(value, "beforeYesterday");
					list.push({
						number : value.beforeYesterday,
						iconName : icons[2],
						textName : `前日新${displayName}量`,
						color : colors[2],
						clickable : false,
						eventName : `onClick${eventName}BeforeYesterday`,
					});

					plus(value, "diff");
					list.push({
						number : value.diff,
						iconName : icons[3],
						textName : `昨日较前日${displayName}量差`,
						color : colors[3],
					});
					if (index === 0)
						vm.personalSignArray = list;
					else if (index === 1)
						vm.enterpriseSignArray = list;
					else if (index === 2)
						vm.demandThreeDaysArray = list;
				});

				return true;
			}).catch(error => {

				if (error) {
					if (error["status"] === 401) {
						window.location.reload();
						return;
					}
				}

				console.error(error)
			}).then((flag) => {
				if (flag === true)
					vm.hideLoading = true;
				else layer.alert("查询失败，请稍后刷新重试……");
			});


			// 重载数据表格
			layui.table.render({
				elem : '#demands',
				height : 324,
				url : '/search/demand?pageSize=10&pageNum=1&orderCreateTime=DESC',
				page : false,
				parseData (result) {
					return {
						"code" : result.ret,
						"msg" : result.msg,
						"data" : result.list
					};
				},
				cols : [ [ //表头
					{
						field : 'id',
						title : 'ID',
						width : 60,
						sort : true
					},
					{
						field : 'picture',
						title : '封面',
						width : 80,
						templet (d) {
							return `<img src="${d.picture.url}?&x-oss-process=image/resize,m_fixed,h_50,w_50"
									style="width:50px;height:50px;">`;
						}
					},
					{
						field : 'content',
						title : '需求内容'
					},
					{
						field : 'sharedTypeCode',
						title : '共享分类',
						width : 120,
						templet (d) {
							return d.sharedType.name;
						}
					},
					{
						field : 'contact',
						title : '发布人',
						width : 80
					},
					{
						field : 'releaseTime',
						title : '发布时间',
						width : 180,
						sort : true
					},
				] ]
			});
		} catch (e) {
			layer.alert("加载失败，请稍后刷新重试……");
			console.error(e);
		}

		// 去掉本身的layui-this
		$(this).parent().removeClass("layui-this");
	}).parent().css("display", "inline-block");
	reloadButton.click();

	app = vm;

})();