function clsMethodLee(){
    this.requestUrl = {
        "path1":"/acct/login",//登陆接口
        "path2":"/product/info/index/list",//轮播图接口
        "path3":"//auth/list"//权限code集
    };
    this.documentLee = null;
    this.checkMark = false;//滑动校验标示
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
    this.checkSure = false;
}

function clsMethodLee$init(){
    //$(".login__index").css("height",window.innerHeight+"px");
    $('#dragMark').drag();
    this.userName = $("#js-input--username");//用户名input节点
    this.password = $("#js-input-password");//密码input节点
    this.errorHint = $("#js-loginFrom__errorHint--hide");//错误提示节点
    this.errorHintText = $("#js-loginFrom__hintContent");//错误提示错误信息展示节点
    this.hideErrContent = $("#hideErrContent");//关闭错误提示按钮节点
    this.loginBtn = $("#js-login__btn--login");//登录按钮节点
    this.hideErrContentText = {"msg1":"用户名不能为空","msg2":"密码不能为空"};
    this.btnLeftOpe = $("#btnLeftOpe");//轮播图向左滑动
    this.btnRightOpe = $("#btnRightOpe");//轮播图向右滑动
    this.parse();
}
function clsMethodLee$parse(){
    $("#quitOperate").remove();
    $("body").css("height","100%");
    if(getCookie("isTrue") == 1){//判断是否记住密码
        //$("#isSavePassword").attr("checked",true);
        $("#js-input--username").val(unescape(getCookie("acctTitle")));
        $("#js-input-password").val(unescape(getCookie("acctPassword")));
    }
    initplugPath($("#imgListLun")[0],"standardTableCtrl",this.requestUrl.path2,{},"POST");
    $("#titleNameHead").html("| 客户登录")
    $(".tradebase-topnav").html("登陆").css({"color":"#fff","height":"34px","line-height":"32px","top":"-5px","font-size":"14px"});
    this.operate();
}

function clsMethodLee$operate(){
    /*this.userName.on("focus",function(){//用户名获取焦点操作
        document.body.jsLee.errorHint.hide();
    });
    this.userName.on("blur",function(){//用户名失去焦点操作
        if($(this).val() == ""){
            document.body.jsLee.errorHintText.html(document.body.jsLee.hideErrContentText.msg1);
            document.body.jsLee.errorHint.show();
            checkInput();
        }
    });
    this.password.on("focus",function(){//密码获取焦点操作
        document.body.jsLee.errorHint.hide();
    });
    this.password.on("blur",function(){//密码失去焦点操作
        if($(this).val() == ""){
            document.body.jsLee.errorHintText.html(document.body.jsLee.hideErrContentText.msg2);
            document.body.jsLee.errorHint.show();
        }
        checkInput();
    });*/
    this.loginBtn.on("click",function(){//提交按钮操作
        initValidate($(".loginFrom")[0]);
        var valiClass=new clsValidateCtrl();
        if(valiClass.validateAll4Ctrl($(".loginFrom")[0]) && document.body.jsLee.checkMark){
            var jsonParam = {"acctId":document.body.jsLee.userName.val(),"password":document.body.jsLee.password.val()};
            getAjaxResult(document.body.jsLee.requestUrl.path1,"POST",jsonParam,"submitCallBack(data)")
        }else {
            if(!document.body.jsLee.checkMark){
                showErrInfoByCustomDiv($("#dragMark")[0],"请通过滑动校验!");
            }
        }

    });
    /*this.hideErrContent.on("click",function(){
        document.body.jsLee.errorHint.hide();
    });*/

    $(document).keyup(function(event){
        if(event.keyCode ==13){
            document.body.jsLee.loginBtn.trigger("click");
        }
    });

    //轮播图
    this.btnLeftOpe.on("click",function(){
        $("#imgListLun *[id=cloneRow]").each(function(idx,val){
            if(idx < 5){
                $(val).show();
            }else{
                $(val).hide();
            }
        });
        $(this).attr("disabled",true).addClass("changeGary");
        $("#btnRightOpe").removeAttr("disabled").removeClass("changeGary")
    });
    this.btnRightOpe.on("click",function(){
        $("#imgListLun *[id=cloneRow]").each(function(idx,val){
            if(idx >= 5){
                $(val).show();
            }else{
                $(val).hide();
            }
        });
        $(this).attr("checked",true).addClass("changeGary");
        $("#btnLeftOpe").removeAttr("disabled").removeClass("changeGary")
    });
    $(".login-tab-list").on("click",function(){
        $(".login-tab-list").removeClass("login-tab-listActive");
        $(this).addClass("login-tab-listActive");
    });


}
function clsMethodLee$refresh(){

}

function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    //$(cloneRow).css("background","url('"+ jsonItem.thumbnail +"')");
    $(cloneRow).on("click",function () {
        jumpUrl("project/recommond/html-gulp-www/productDetail1.html?pid=" + jsonItem.pid,"0000000",1);
    });
}
function clsStandardTableCtrl$after() {
    if($("#imgListLun *[id=cloneRow]").length <=5){
        $("#btnRightOpe").attr("disabled",true).addClass("changeGary")
    }
}


//已有数组，初始化插件;
function initplugData(docm,comType,data){
    $(docm).attr("comType",comType);
    docm.data = {"rspBody":{"resultData":data}};
    document.body.jsCtrl.ctrl = docm;
    document.body.jsCtrl.init();
}
//未知数组，已有接口，初始化插件;
function initplugPath(docm,comType,reqPath,reqParam,reqMethod){
    if(reqPath != null){
        $(docm).attr("reqPath",reqPath);
    }
    if(reqParam != null){
        $(docm).attr("reqParam",JSON.stringify(reqParam));
    }
    if(reqMethod != null){
        $(docm).attr("reqMethod",reqMethod);
    }
    $(docm).attr("comType",comType);
    document.body.jsCtrl.ctrl = docm;
    document.body.jsCtrl.init();
}

function validateBefore(){//滑动校验前的操作
    document.body.jsLee.errorHint.hide();
}

function validatePass(handler,text,drag){//滑动校验成功返回函数
    document.body.jsLee.checkMark = true;
}

function checkInput(){//校验输入信息
    if(checkInput && $("#js-input--username").val() && $("#js-input-password").val() && document.body.jsLee.checkMark){
        $("#js-login__btn--login").removeAttr("disabled").css("background","red");
    }else{
        $("#js-login__btn--login").attr("disabled",true).css("background","#e5e5e5");
    }
}

function submitCallBack(data){
    jsData = JSON.parse(data);
    if(jsData.retCode == "0000000"){
        /*var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("登陆成功","成功提示",1,"","loginTip");*/
        /*if($("#isSavePassword").is(":checked")){
            setCookie("isTrue",1);
            setCookie("acctTitle",escape($("#js-input--username").val()));
            setCookie("acctPassword",escape($("#js-input-password").val()));
        }else{
            setCookie("isTrue",0);
            setCookie("acctTitle",escape($("#js-input--username").val()));
            setCookie("acctPassword",escape($("#js-input-password").val()));
        }*/

        /**************自定义报表原有代码注释开始******************/
        // if(window.Storage && window.localStorage && window.localStorage instanceof Storage){
        //     window.localStorage.limitCode = JSON.stringify(data.rspBody.permissions);
        // }else{
        //     setCookie("limitCode",escape(JSON.stringify(data.rspBody.permissions)));
        // }
        // setCookie("acctType",escape(data.rspBody.acct.acctType));
        /**************自定义报表原有代码注释结束******************/

        /**************权限新增代码开始******************/
        var expiresDate= new Date();
        expiresDate.setTime(expiresDate.getTime() + (30*60*1000));
        $.cookie('acctId',jsData.rspBody.acctId,{ path: '/' });
        $.cookie('orgId',jsData.rspBody.oInfoId,{ path: '/' });
        $.cookie('acctType',jsData.rspBody.acctType,{ path: '/' });//0超级管理员， 1管理员， 2用户，3系统管理员

        var limitCodeArr = jsData.rspBody.permissions
        //登陆时，登陆成功后，把后台返回的权限集合存储在localStorage中
        if(window.Storage && window.localStorage && window.localStorage instanceof Storage){
            window.localStorage.limitCode = JSON.stringify(jsData.rspBody.permissions);
        }else{
            setCookie("limitCode",escape(JSON.stringify(jsData.rspBody.permissions)));
        }
        //登陆人姓名
        setCookie("name",escape(JSON.stringify(jsData.rspBody.name)));
        /**************权限新增代码结束******************/




        //存cookie，已经登陆状态
        setCookie("isLogin","1");
        /* setCookie("orgId",data.rspBody.acct.orgId);*/
        if(GetQueryString("overtime") == 1){
            jumpUrl(unescape(getCookie("urlPath")),"0000000",0);
        }else{
            jumpUrl("reportForm/report-mine/html-gulp-www/myInfoList.html","0000000",0);
        }
    }/*else {
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"失败提示",1,"","loginTipErrTip");
    }*/
}

function clsAlertBoxCtrl$sure() {//登陆成功弹框确定
    if (this.id == "loginTip") {//登陆成功
        closePopupWin();
        if(GetQueryString("overtime") == 1){
            jumpUrl(unescape(getCookie("urlPath")),"0000000",0);
        }else{
            jumpUrl("../../homePage/html-gulp-www/homePage.html","0000000",0);
        }
    }else if(this.id == "loginTipErrTip"){//登陆失败
        closePopupWin();
    }
}

//按照组件重新编写div校验
function showErrInfoByCustomDiv(elem,error)
{
    $(elem).poshytip({
        className: 'tip-twitter',
        showOn: 'none',
        alignTo: 'target',
        alignX: 'right',
        alignY: 'inner-right',
        content:error,
        offsetX: 5,
        offsetY: -30,
        autoHide:true,
        hideTimeout:5000
    });
    $(elem).poshytip('hide');
    $(elem).poshytip('show');
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();

});