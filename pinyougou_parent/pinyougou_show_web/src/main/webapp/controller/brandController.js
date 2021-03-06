//页面初始化完成后再创建Vue对象
window.onload=function () {
	//创建Vue对象
	var app = new Vue({
		//接管id为app的区域
		el:"#app",
		data:{
			//声明数据列表变量，供v-for使用
			list:[],
			//总页数
			pages:1,
			//当前页
			pageNo:1,
			//声明对象
			entity:{},
			//将要删除的id列表
			ids:[],
			//搜索包装对象
			searchBrand:{},
			// 当前商家的品牌列表
			brandList: {},

			status: [ '申请', '已申请','审核通过']
		},
		methods:{
			//查询所有
			findAll:function () {
				axios.get("../brand/findAll.do").then(function (response) {
					//vue把数据列表包装在data属性中
					app.list = response.data;
				}).catch(function (err) {
					console.log(err);
				});
			},
			//分页查询
			findPage:function (pageNo) {
				axios.post("../brand/findPage.do?pageNo="+pageNo+"&pageSize="+10,this.searchBrand)
					.then(function (response) {
						app.pages = response.data.pages;  //总页数
						app.list = response.data.list;  //数据列表
						app.pageNo = pageNo;  //更新当前页
					});
			},
			//让分页插件跳转到指定页
			goPage:function (page) {
				app.$children[0].goPage(page);
			},
			//新增
			add:function () {
				var url = "../brand/add.do";
				if(this.entity.id != null){
					url = "../brand/update.do";
				}
				axios.post(url, this.entity).then(function (response) {
					if (response.data.success) {
						//刷新数据，刷新当前页
						// app.findPage(app.pageNo);
                        app.findBrand();
					} else {
						//失败时显示失败消息
						alert(response.data.message);
					}
				});
			},
			//跟据id查询
			getById:function (id) {
				axios.get("../brand/getById.do?id="+id).then(function (response) {
					app.entity = response.data;
				})
			},
			//批量删除数据
			dele:function () {
                axios.get("../brand/delete.do?ids=" + this.ids).then(function (response) {
                    if (response.data.success) {
                        //刷新数据
                        app.findPage(app.pageNo);
                        //清空勾选的ids
                        app.ids = [];
                    } else {
                        alert(response.data.message);
                    }
                });
			},
			// 查询当前商家品牌列表
			findBrand: function () {
                axios.get("../brand/findBrand.do").then(function (response) {
                    app.brandList = response.data;
                    console.log(response.data)
                });
            },
            updateStatus: function (status,id) {
                axios.get("../brand/updateStatus.do?status=" + status+"&id="+id).then(function (response) {
                    if (response.data.message) {
                        // alert("已提交申请");
                        app.findBrand();
                    }else {
                        alert("申请失败");

					}
                });
            }
		},
		//Vue对象初始化后，调用此逻辑
		created:function () {
			//调用用分页查询，初始化时从第1页开始查询
			// this.findPage(1);
            this.findBrand();
		}
	});
};
