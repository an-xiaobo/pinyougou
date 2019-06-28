var app = new Vue({
        el: "#app",
        data: {
            //声明数据列表变量，供v-for使用
            list: [],
            //总页数
            pages: 1,
            //当前页
            pageNo: 1
        },
        methods: {
            //查询全部商品列表
            findAll: function () {
                axios.get("../seckillgoods/findAll.do").then(function (response) {
                    //vue把数据列表包装在data属性中
                    app.list = response.data;
                    // alert(response.data);
                }).catch(function (err) {
                    console.log(err);
                });
            },

        },
        //vue对象初始化时，调用该钩子
        created: function () {
            this.findAll();
        }
    });


