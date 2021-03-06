window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            //支付页面对象{money:支付金额,out_trade_no:支付单号}
            entity:{money:0,out_trade_no:''}
        },
        methods:{
            /**
             * 生成二维码
             */
            createNavite:function () {
                axios.get("/pay/createNavite.do").then(function (response) {
                    //将分转换为元
                    app.entity.money = (response.data.total_fee /100).toFixed(2);
                    //绑定订单号
                    app.entity.out_trade_no = response.data.out_trade_no;
                    //生成二维码
                    var qrcode = new QRCode(document.getElementById("qrcode"), {
                        text: response.data.code_url,
                        width: 200,
                        height: 200,
                        colorDark : "#000000",
                        colorLight : "#ffffff",
                        correctLevel : QRCode.CorrectLevel.H
                    });

                    //开始检测支付状态
                    app.queryPayStatus(app.entity.out_trade_no);
                })
            },
            /**
             * 查询微信支付状态
             */
            queryPayStatus:function (out_trade_no) {
                axios.get("/pay/queryPayStatus.do?out_trade_no="+out_trade_no).then(function (response) {
                    if (response.data.success){
                        window.location.href = "paysuccess.html?money="+app.entity.money;
                    } else{
                        window.location.href = "payfail.html?message="+response.data.message;
                    }
                })
            },
            //获取其它页面传入的金额
            getMoney:function(){
              return this.getUrlParam()["money"];
            },
            //获取其他页面传入的提示信息
            getTips:function(){
              return decodeURI(this.getUrlParam()["message"]);
            },
            /**
             * 解析一个url中所有的参数
             * @return {参数名:参数值}
             */
            getUrlParam:function() {
                //url上的所有参数
                var paramMap = {};
                //获取当前页面的url
                var url = document.location.toString();
                //获取问号后面的参数
                var arrObj = url.split("?");
                //如果有参数
                if (arrObj.length > 1) {
                    //解析问号后的参数
                    var arrParam = arrObj[1].split("&");
                    //读取到的每一个参数,解析成数组
                    var arr;
                    for (var i = 0; i < arrParam.length; i++) {
                        //以等于号解析参数：[0]是参数名，[1]是参数值
                        arr = arrParam[i].split("=");
                        if (arr != null) {
                            paramMap[arr[0]] = arr[1];
                        }
                    }
                }
                return paramMap;
            }

        },
        created:function () {
            var url = document.location.href;
            //只有在二维码页面，才生成二维码
            if (url.indexOf("pay.html") >-1){
                this.createNavite();
            }
            this.getUrlParam();
        }
    })
};