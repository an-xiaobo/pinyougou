window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            loginName:"",
            //订单列表
            userOrderList:[],
            //未付款订单列表
            unPayOrderList:[],
            //用户个人信息+所有地址+默认地址
            userInfo:{},
            //单个收货地址
            addressInfo:{provinceId:'',cityId:'',townId:''},
            //省
            province:[],
            //市
            city:[],
            //区
            town:[],
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
            //查询用户订单
            findOrderByUserId:function(){
                axios.get("/user/findOrderByUserId.do").then(function (response) {
                    app.userOrderList=response.data
                })
            },
            /**
             * 获取个人信息包含所有地址
             */
            getUserInfoByUserId:function () {
                axios.get("/user/getUserInfoByUserId.do").then(function (response) {
                    app.userInfo = response.data
                })
            },
            /**
             * 增加收货地址
             */
            addAddress:function () {

                var url = "/user/addAddress.do"
                if (this.addressInfo.id!=null){
                    url="/user/modifyAddress.do"
                }
                axios.post(url,this.addressInfo).then(function (response) {
                    if (response.data.success) {
                        alert(response.data.message)
                    }else {
                        alert(response.data.message)
                    }

                })

                //this.getUserInfoByUserId()
                window.location.reload()
            },
            /**
             * 编辑收货地址--回显
             */
            findAddressById:function (id) {

                axios.get("/user/findAddressById.do?id="+id).then(function (response) {
                    app.addressInfo=response.data

                })
            },
            /**
             * 删除收货地址
             */
            deleteAddressById:function (id) {

                axios.get("/user/deleteAddressById.do?id="+id).then(function (response) {
                    if (response.data.success) {
                        alert(response.data.message)
                    }else {
                        alert(response.data.message)
                    }
                })

                this.getUserInfoByUserId()
            },
            /**
             *
             *查询省
             */
            findproviceList:function(){
                axios.get("/provinces/findAll.do").then(function (response) {
                    app.province = response.data
                })
            },/**
             *
             *根据省provinceid查询市
             */
            findcityList:function(provinceid){
                axios.get("/cities/findCitiesByProvinceId.do?provinceid="+provinceid).then(function (response) {
                    app.city = response.data
                })
            },
            /**
             *
             *根据省cityid查询区
             */
            findareaList:function(cityid){
                axios.get("/areas/findAreaByProvinceId.do?cityid="+cityid).then(function (response) {
                    app.town = response.data
                })
            },
            /**
             * 生成二维码
             */
            createNavite:function (orderId,totalFee) {

                window.location.href="pay.html?orderId="+orderId+"&totalFee="+totalFee
                // axios.get("/pay/createNavite.do?orderId="+orderId+"&totalFee="+totalFee).then(function (response) {
                //     // alert(response.data.code_url)
                //     // alert(response.data.out_trade_no)
                //     // alert(response.data.total_fee)
                //     //将分转换为元
                //     app.entity.money = (response.data.total_fee /100).toFixed(2);
                //     //绑定订单号
                //     app.entity.out_trade_no = response.data.out_trade_no;
                //     //生成二维码
                //     var qrcode = new QRCode(document.getElementById("qrcode"), {
                //         text: response.data.code_url,
                //         width: 200,
                //         height: 200,
                //         colorDark : "#000000",
                //         colorLight : "#ffffff",
                //         correctLevel : QRCode.CorrectLevel.H
                //     });
                //
                //     //开始检测支付状态
                //     app.queryPayStatus(app.entity.out_trade_no);
                // })
            }

        },
        watch:{
            "addressInfo.provinceId":function (newValue, oldValue) {
                this.findcityList(newValue)
                app.town=[]
            },
            "addressInfo.cityId":function (newValue, oldValue) {
                this.findareaList(newValue)
            }

        },
        created:function () {
            //初始化调用 用户名
            this.loginNameInfo();
            //初始化调用查询用户订单
            this.findOrderByUserId();
            //初始化调用查询个人信息
            this.getUserInfoByUserId()
            //初始化查询省
            this.findproviceList()
        }
    })
};