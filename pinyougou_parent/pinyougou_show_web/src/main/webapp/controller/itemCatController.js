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
			searchEntity:{},
			//记录当前面包屑的级别
			grade:1,
			//一级分类
			entity_1:{id:0,name:"顶级列表"},
			//二级分类
			entity_2:{},
			//三级分类
			entity_3:{},
			//记录父ID
			parentId:0,
            // 当前商家的分类列表
            itemCatList: [],

            status: [ '申请', '已申请','审核通过']
		},
		methods:{
			//查询所有
			findAll:function () {
				axios.get("../itemCat/findAll.do").then(function (response) {
					//vue把数据列表包装在data属性中
					app.list = response.data;
				}).catch(function (err) {
					console.log(err);
				});
			},
			//分页查询
			findPage:function (pageNo) {
				axios.post("../itemCat/findPage.do?pageNo="+pageNo+"&pageSize="+10,this.searchEntity)
					.then(function (response) {
						app.pages = response.data.pages;  //总页数
						app.list = response.data.rows;  //数据列表
						app.pageNo = pageNo;  //更新当前页
					});
			},
			//让分页插件跳转到指定页
			goPage:function (page) {
				app.$children[0].goPage(page);
			},
			//新增
			add:function () {
				var url = "../itemCat/add.do";
				//记录父ID
				this.entity.parentId = this.parentId;
				if(this.entity.id != null){
					url = "../itemCat/update.do";
				}
				axios.post(url, this.entity).then(function (response) {
					if (response.data.success) {

                        app.findItemCat();
					} else {
						//失败时显示失败消息
						alert(response.data.message);
					}
				});
			},


            // 查询当前商家品牌列表
            findItemCat: function () {
                axios.get("../itemCat/findItemCat.do").then(function (response) {
                    app.itemCatList = response.data;
                });
            },
            updateStatus: function (status,id) {
                axios.get("../itemCat/updateStatus.do?status=" + status+"&id="+id).then(function (response) {
                    if (response.data.message) {
                        // alert("已提交申请");
                        app.findItemCat();
                    }else {
                        alert("申请失败");

                    }
                });
            }
		},
		//Vue对象初始化后，调用此逻辑
		created:function () {

            this.findItemCat();
		}
	});
};
