// window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            loginName:"",
            //用户个人信息
            userInfo:{birthday:''},
            //用户生日-年
            birthdayY:'',
            //用户生日-月
            birthdayM:'',
            //用户生日-日
            birthdayD:'',
            //用户地址
            address:{provinceId:'',cityId:'',townId:''},
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
             * 获取个人信息
             */
            getUserInfoByUserId:function () {
                axios.get("/user/getUserInfoByUserId.do").then(function (response) {
                    app.userInfo = response.data.user

                    //分割年月日
                    var strings = app.userInfo.birthday.split("-");
                    //1926-05-05 00:00:00

                    app.birthdayY=strings[0]//1926

                    if (strings[1]!='10' || strings[1]!='11'||strings[1]!='12'){//05
                        var strings2 = strings[1].split(0)
                        app.birthdayM=strings2[1]
                    }else {
                        app.birthdayM=strings[1]
                    }

                    //分割日和时间
                    var strings1 = strings[2].split(' ');//05 00:00:00
                    if (strings1[0].indexOf("0")==0) {
                        var strings3 = strings1[0].split('');
                        app.birthdayD=strings3[1]
                    }else {
                        app.birthdayD=strings1[0]
                    }


                    //所在地
                    // app.address=response.data.address;
                    app.address.provinceId = response.data.address.provinceId;
                    app.address.cityId = response.data.address.cityId;
                    app.address.townId = response.data.address.townId;

                })
            },
            /**
             * 修改个人信息
             */
            changeUserInfo:function () {
                //判断生日日期
              if(this.birthdayY==''){
                 alert("请输入年份")
              }else {
                  this.userInfo.birthday = this.birthdayY
                  if (this.birthdayM==''){
                      alert("请输入月份")
                  } else {
                      this.userInfo.birthday += "/"
                      this.userInfo.birthday += this.birthdayM
                      if (this.birthdayD==''){
                          alert("请输入日期")
                      } else {
                          this.userInfo.birthday += "/"
                          this.userInfo.birthday += this.birthdayD
                      }
                  }
              }

                this.userInfo.birthday = new Date(this.userInfo.birthday)

              //把用户信息传到后台
                axios.post("/user/changeUserInfo.do",this.userInfo).then(function (response) {

                })

                //把地址传到后台
                axios.post("/user/changeAddress.do",this.address).then(function (response) {

                })

            },
            /**
             * 上传图片
             */
            upload:function () {
                var formData = new FormData() // 声明一个FormData对象
                // 'file' 这个名字要和后台获取文件的名字一样;
                formData.append('file', document.querySelector('input[type=file]').files[0]);
                //post提交
                axios({
                    url: '/upload.do',
                    data: formData,
                    method: 'post',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then(function (response) {
                    if(response.data.success){
                        //上传成功
                        app.userInfo.headPic=response.data.message;
                    }else{
                        //上传失败
                        alert(response.data.message);
                    }
                })

            },

        },
        watch:{
            "address.provinceId":function (newValue, oldValue) {
                this.findcityList(newValue)
                app.town=[]
            },
            "address.cityId":function (newValue, oldValue) {
                this.findareaList(newValue)
            }

        },
        created:function () {
            //初始化调用 用户名
            this.loginNameInfo();
            //初始化调用查询个人信息
            this.getUserInfoByUserId()
            //初始化查询省
            this.findproviceList()
        }
    })
// };