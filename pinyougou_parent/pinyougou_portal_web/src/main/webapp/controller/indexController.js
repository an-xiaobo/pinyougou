var app = new Vue({
    el: "#app",
    data: {
        //广告列表
<<<<<<< HEAD
        contentList: [],
        keyword:''
    },
    methods: {
=======
        seen: false,
        contentList: [],
        keyword:'',
        //声明数据列表变量，供v-for使用
        list:[]
    },
    methods: {
        //跟据父id查询数据列表
        findByParentId:function (parentId) {
            axios.get("/conweb/findByParentId.do?parentId="+parentId).then(function (response) {
                app.list = response.data;
            })
        },

        //查询所有
        findAll:function () {
            axios.get("/conweb/findAll.do").then(function (response) {
                //vue把数据列表包装在data属性中
                app.list = response.data;
            }).catch(function (err) {
                console.log(err);
            });
        },
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
        //查询广告列表
        findContentList: function () {
            //查询广告轮播图
            axios.get("/content/findByCategoryId.do?categoryId=1").then(function (response) {
                app.$set(app.contentList,0,response.data);
            })
        },
        /**
         * 搜索跳转
         */
        search:function () {
            window.location.href = "http://localhost:8083/search.html?keyword="+this.keyword;
<<<<<<< HEAD
=======
        },
        onMouseOver:function () {
            //alert("移入");
            this.seen = true;
        },
        onMouseOut:function () {
            //alert("移除");
            this.seen = false;
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
        }
    },
    //初始化调用
    created: function () {
        //查询广告列表
        this.findContentList();
<<<<<<< HEAD
=======
        this.findByParentId(0);
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
    }
});
