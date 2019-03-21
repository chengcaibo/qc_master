(function() {

	/** 初始化 */
	form.render();

	/** 记录当前的排序状态 */
	var sortType = "desc";

	/* 注册事件 */

	/** 刷新按钮点击事件 */
	$("#personal-reload").click(function() {
		// 重载layui.table
		layui.table.reload('personal-list', {});
	})

	let searchInput = $("input[name='search-input']");
	let searchButton = $("#personal-search-button");

	/** 立即搜索按钮点击事件 */
	searchButton.click(function() {
		let keyword = searchInput.val();
		// 重载 layui.table
		layui.table.reload('personal-list', {
			page : {
				curr : 1
			},
			where : {
				keyword
			}
		});
	})

	/** 监听文本框按键事件 */
	searchInput.keydown(function(event) {
		var curKey = event.which;
		if (curKey == 13) { // 回车键
			searchButton.click();
			return false;
		}
	});

	/** 监听筛选选择框 */
	form.on('select(filter-select)', function(data) {
		let this_ = $(data.elem);
		let field = this_.attr("data-field");
		let where = {};
		where[field] = data.value != -1 ? data.value : null;
		// 重载数据表格
		layui.table.reload('personal-list', {
			where
		});
	});


	/** 注册layui.table */
	try {
		layui.table.render({
			elem : '#personal-list',
			url : "/search/personal",
			where : {
				total : true,
				orderCreateTime : sortType //排序方式
			},
			request : {
				pageName : 'pageNum', //页码的参数名称，默认：page
				limitName : 'pageSize' //每页数据量的参数名，默认：limit
			},
			page : true,
			limit : 13,
			limits : [ 13, 23, 33, 43, 53 ],
			loading : true,
			height : 610,
			cols : [ [
				{
					field : "checkboxs",
					width : 40,
					type : "checkbox",
					fixed : true,
				},
				{
					title : "头像",
					width : 60,
					fixed : true,
					unresize : true, // 是否禁止拖动列宽
					templet : "#templet-personal-avatar",
				},
				{
					field : "nickName",
					title : "昵称",
					width : 200,
					minWidth : 120,
					fixed : true,
				},
				{
					field : "gender",
					title : "性别",
					width : 60,
					templet : "#templet-personal-sex",
				},
				{
					field : "age",
					title : "年龄",
					width : 80,
					templet : "#templet-personal-age",
				},
				{
					field : "birthday",
					title : "生日",
					width : 140,
					templet : "#templet-personal-birthday",
				},
				{
					field : "introduce",
					title : "介绍",
					minWidth : 120,
				},
				{
					title : "地区",
					width : 160,
					templet : "#templet-personal-region",
				},
				{
					title : "职业",
					width : 160,
					templet : "#templet-personal-job",
				},
				{
					field : "hourlyWage",
					title : "时薪",
					width : 80,
				},
				{
					field : "createTime",
					title : "注册时间",
					width : 160,
					sort : true,
					unresize : true,
					templet : "#templet-personal-createTime",
				},
				{
					title : "账号状态",
					width : 100,
					unresize : true,
					templet : "#templet-personal-state",
				},
				{
					title : "操作",
					width : 100,
					unresize : true,
					fixed : "right",
					templet : "#templet-personal-action",
				}
			] ],
			parseData : function(res) { //将原始数据解析成 table 组件所规定的数据
				return {
					"code" : res.ret, //解析接口状态
					"msg" : res.msg, //解析提示文本
					"count" : res.total, //解析数据长度
					"data" : res.list //解析数据列表
				};
			},
			done () {
				if (sortType == "desc") {
					$(".layui-table-sort-desc").css("border-top-color", "#000");
				} else {
					$(".layui-table-sort-asc").css("border-bottom-color", "#000");
				}
			}
		});

	} catch (err) {
		console.error("layui.table.render() 报错：", err)
	}

	/** 监听排序更改事件 */
	try {
		layui.table.on('sort(personal-list)', function(obj) {
			if (sortType == "desc")
				sortType = "asc";
			else
				sortType = "desc";

			layui.table.reload('personal-list', {
				initSort : {
					field : obj.field,
					type : null
				},
				where : {
					orderCreateTime : sortType
				}
			});
		});


	} catch (err) {
		console.error("layui.table.on() 报错：", err)
	}


	//认证certification刷新按钮
	$("#btn-reload").click(function() {
		searchButton.click();

		// 去掉本身的layui-this
		$(this).parent().removeClass("layui-this");
	}).parent().css("display", "inline-block");

})();