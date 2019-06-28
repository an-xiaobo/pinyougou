window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            loginName:"",
            //收藏商品对象
            goodsItemList:[]
        },
        methods:{
            /**
             * 获取用户名
             */
            loginNameInfo:function () {
                axios.get("/login/name.do").then(function (response) {
                    app.loginName = response.data.username;
                })
            },
            /**
             * 查询收藏商品
             */
            findGoodsCollect:function () {
                axios.get("/user/findGoodsCollect.do").then(function (response) {
                    app.goodsItemList = response.data
                })
            }

        },
        created:function () {
            //初始化调用 用户名
            this.loginNameInfo();
            //初始化调用
            this.findGoodsCollect();
        }
    })
};