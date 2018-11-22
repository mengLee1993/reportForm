var titleJson = {
    "project":{
        /******************************************************************************
         * 采购端
         ***************************************************************************/
        "report-home":{//首页
            "homePage.html":"首页"
        },
        "report-mine":{//个人主题数据
            "myInfoList.html":"我的数据报表",
            "selfSearch.html":"我的自定义查询",
            "myInfoDown.html":"我的数据下载",
        },
        "report-analyze":{//自定义分析
            "dataAnalyze.html":"自定义分析"
        },
        "report-dictionaries":{//数据字典
            "dataSourceList":"数据源管理列表",
            "dictionaries.html":"数据字典",
            "fieldList.html":"元数据字段列表",
            "fieldGather.html":"元数据列表"
        },
        "report-authorization":{//数据授权
            "authorization.html":"数据授权"
        },
        "report-logAnalyze":{//分析日志
            "logAnalyze.html":"分析日志"
        },
        "report-login":{//登陆
            "login.html":"登陆"
        },
        "report-limit":{//登陆
            "orgFrame.html":"组织管理",
            "roleManage.html":"角色管理",
            "resourceManage.html":"资源管理",
            "userManage.html":"人员管理"
        }
    }
}

function titleEval(titleJson){//赋值每一个页面的title
    var urlText = document.location.href;
    var titleVar = {};
    titleVar = titleJson.project;
    for(var key in titleVar){
        if(urlText.indexOf(key) != -1){
            var titleVarJson = titleVar[key];
            for(var key2 in titleVarJson){
                if(urlText.indexOf(key2) != -1) {
                    // $("title").eq(0).html("数据报表-" + titleVarJson[key2]);ie8及一下不支持用这种方法给title赋值
                    document.title = "数据报表-" + titleVarJson[key2];
                    if(titleVarJson[key2] == ""){
                        document.title = "数据报表";
                    }
                    if(key == "report-analyze" || key == "report-authorization" || key == "report-logAnalyze" || key == "report-login" || key == "report-home"){//不用加侧边栏
                        $("#bodyLeft").remove();
                        $("#bodyRight").css("padding-left",0);
                    }
                    //异议提报新建。详情。销售审核等
                    /*if(urlText.indexOf("htmlType=0") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "数据报表-" +"异议提报-新建";
                    }else if(urlText.indexOf("htmlType=1") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "数据报表-" +"异议提报-修改";
                    }else if(urlText.indexOf("htmlType=2") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "数据报表-" +"异议提报-详情";
                    }else if(urlText.indexOf("htmlType=3") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "数据报表-" +"销售审核";
                    }else if(urlText.indexOf("htmlType=4") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "数据报表-" +"外部调查-录入界面";
                    }else if(urlText.indexOf("htmlType=5") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "数据报表-" +"内部调查";
                    }else if(urlText.indexOf("htmlType=6") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "数据报表-" +"确认书审核";
                    }else if(urlText.indexOf("htmlType=7") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "数据报表-" +"销售审核详情";
                    }
                    //异议处理协议书
                    if(urlText.indexOf("htmlType=1") != -1 && key2 == "agreementBook.html"){
                        document.title = "数据报表-" +"异议处理-协议书编辑";
                    }else if(urlText.indexOf("htmlType=2") != -1 && key2 == "agreementBook.html"){
                        document.title = "数据报表-" +"协议书审核";
                    }

                    //产品信息维护新增。修改。
                    if(urlText.indexOf("htmlType=1") != -1 && key2 == "productUpdate.html"){
                        document.title = "数据报表-" +"产品推介信息-新增";
                    }else if(urlText.indexOf("htmlType=2") != -1 && key2 == "productUpdate.html"){
                        document.title = "数据报表-" +"产品推介信息-修改";
                    }*/

                }
            }
        }
    }
}

$(function(){
    titleEval(titleJson)
});