function clsMethodLee(){
    this.requestUrl = {
        "path1":"/report/getReportForm",//左侧父子关系列表
        "path2":"/report/getRptDataField",//添加指标弹框——系统指标——指标字段选择下拉框
        "path3":"/report/getRptMeasuresSystem",//添加指标弹框——自定义指标——待选指标选择
        "path4":"/report/addRptMeasures",//添加指标弹框确认接口
        "path5":"/report/delRptMeasures",//删除指标接口
        "path6":"/report/reportCore",//生成table数据
        "path7":"/report/addCustomReport",//保存提交接口
        "path8":"/report/getCustomReport",//回显接口
        "path9":"/report/reportCoreExcel",//导出接口
        "path10":"/report/listShareReport",//分享人员列表
        "path11":"/report/listShareReport",//分享角色列表
        "path12":"/report/addShareReport",//分享提交列表
        "path13":"/report/reportFromDetail",//提交列表
        "path14":"/report/charts/hcharts",//highchart柱图/饼图
        "path15":""//只拖拽行区，生成带分页的详情列表
    };
    this.jsonAll = {"reportDynamicParam":{"column":[],"filter":[],"line":[],"tbs":[]}};//提交如参
    this.documentLee = null;
    this.subjectId = "";//主题id
    this.measureId = "";//指标id（编辑操作）
    this.measureEditJson = {};//编辑指标复现信息（编辑操作）
    this.checkedAll = [];//勾选待选维度和指标check
    this.dom = null;//操作行列过滤区节点
    this.chartOptions = {};//生成highchart缓存行列信息
    this.chartTrue = false;//操作图形，table切换，是否刷新图形 false——需要刷新  true——不刷新
    this.personalSubjectId = GetQueryString("personalSubjectId") == null ? "" : GetQueryString("personalSubjectId");//主题id
    this.personalAnalysisId = GetQueryString("personalAnalysisId") == null ? "" : GetQueryString("personalAnalysisId");//自定义报表id
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //添加measure弹框确认
    this.createSure = $("#createSure");
    //添加measure弹框取消
    this.createCancel = $("#createCancel");
    //保存操作
    this.saveOperate = $("#saveOperate");
    //保存弹框确认操作
    this.savePopSure = $("#savePopSure");
    //保存弹框取消操作
    this.savePopCancel = $("#savePopCancel");
    //提交操作
    this.submitOperate = $("#submitOperate");
    //刷新操作
    this.refreshOperate = $("#refreshOperate");
    //实时刷新
    this.immediatelyRefresh = $("#immediatelyRefresh");
    //行列转换
    this.rowColTranslate = $("#rowColTranslate");
    //面板
    this.boardOperate = $("#boardOperate");
    //导出
    this.exportOperate = $("#exportOperate");
    //资源树
    this.resourceTreeOperate = $("#resourceTreeOperate");
    //分享
    this.shareOperate = $("#shareOperate");
    //分享弹框人员/角色选择
    this.shareType = $("#shareType");
    //分享确认操作
    this.shareSure = $(".shareSure");
    //勾选是否为过滤条件
    this.searchSet = $("*[id=searchSet]");
    //图形和table切换
    this.chartShow = $("#chartShow");
    //柱图和饼图切换
    this.chartTypeTab = $("input[name=tableChart]");
    //图形设置按行列显示
    this.setUpChart = $("#setUpChart");
    //图形设置按行列显示确定
    this.wayShowSure = $("#wayShowSure");
    //图形设置按行列显示取消
    this.wayShowCancel = $("#wayShowCancel");

    this.parse();

}
function clsMethodLee$parse(){
    //初始化左侧父子table
    if(this.personalAnalysisId){
        getAjaxResultLee(document.body.jsLee.requestUrl.path8,"POST",{"personalAnalysisId":this.personalAnalysisId},"initHtmlCallBack(data)",function(){
            $("#ajaxWaiting").show();
        },function () {
            $("#ajaxWaiting").hide();
        });
    }else{
        initplugPath($("#parentChildTableList")[0],"parentChildTableCtrl",this.requestUrl.path1,{"personalSubjectId":this.personalSubjectId},"POST");
    }
    if(this.personalSubjectId){
        $("#childShow").show();
    }
    //初始化fieldId的下拉
    $("#measureType").chosen({
        no_results_text: "暂无结果",
        width: "200PX",
        enable_split_word_search: false,
        placeholder_text_single: '邮箱验证',
    });
    $("#shareType").chosen({
        no_results_text: "暂无结果",
        width: "200PX",
        enable_split_word_search: false,
        placeholder_text_single: '邮箱验证',
    });
    //生成图形下拉框初始化
    $("#wayShowType").chosen({
        no_results_text: "暂无结果",
        width: "200PX",
        enable_split_word_search: false,
        placeholder_text_single: '邮箱验证',
    });

    //设置人员列表和角色列表缓存数组为空
    $("#userShare")[0].cacheArr = [];
    $("#roleShare")[0].cacheArr = [];
    this.operate();
    if(this.personalAnalysisId){
        this.resourceTreeOperate.click().remove();
        this.boardOperate.click().remove();
        this.setUpChart.remove();
        this.refreshOperate.remove();
        this.saveOperate.remove();
        this.shareOperate.remove();
        this.rowColTranslate.remove();
    }
}

function clsMethodLee$operate(){
    //添加measure弹框确认
    this.createSure.unbind();
    this.createSure.bind("click",function(){
        if(checkTrue()){
            var jsonParam = jsonParamJoin();
            getAjaxResultLee(document.body.jsLee.requestUrl.path4,"POST",jsonParam,"createMeasureCallBack(data)");
        }
    });
    //添加measure弹框取消
    this.createCancel.on("click",function(){
        closePopupWin();
    });

    //保存操作
    this.saveOperate.on("click",function(){
        openWin('360', '245', 'reportNamePop', true);
        $("#reportName").val("");

    });

    //保存确认
    this.savePopSure.on("click",function(){
        initValidate($("#reportNamePop")[0]);
        var valiClass=new clsValidateCtrl();
        if(valiClass.validateAll4Ctrl($("#reportNamePop")[0])){
            var jsonParam = {"reportEchoBody":document.body.jsLee.jsonAll,"reportName":$("#reportName").val()};
            getAjaxResultLee(document.body.jsLee.requestUrl.path7,"POST",jsonParam,"saveCallBack(data)");
        }
    });
    //保存取消
    this.savePopCancel.on("click",function(){
        closePopupWin();
    });

    //提交操作
    this.submitOperate.on("click",function(){
        if(!document.body.jsLee.personalAnalysisId) {//是否保存自定义报表
            var alertBox = new clsAlertBoxCtrl();
            alertBox.Alert("请保存报表", "失败提示");
        }else{
            getAjaxResultLee(document.body.jsLee.requestUrl.path13,"POST",{"personalAnalysisId":document.body.jsLee.personalAnalysisId},"submitCallBack(data)",function(){
                $("#ajaxWaiting").show();
            },function () {
                $("#ajaxWaiting").hide();
            });
        }

    });

    //刷新操作
    this.refreshOperate.on("click",function(){
        initTableOrChart();
    });

    //立即刷新
    this.immediatelyRefresh.on("click",function(){
        if($(this).is(":checked")){
            initTableOrChart();
        }
    });

    //行列转换
    this.rowColTranslate.on("click",function(){
        translateTable(this);
    });
    //面板操作
    this.boardOperate.on("click",function(){
        if($(".formcontainer-right").is(":visible")){
            $(this).css("color","#0375D7");
            $(this).attr("clickMark",1);
            $(".formcontainer-right").hide();
        }else{
            $(this).css("color","#666");
            $(this).removeAttr("clickMark");
            $(".formcontainer-right").show();
        }
        widthTranslate();
    });

    //资源树操作
    this.resourceTreeOperate.on("click",function(){
        if($(".formcontainer-nav").is(":visible")){
            $(this).css("color","#0375D7");
            $(this).attr("clickMark",1);
            $(".formcontainer-nav").hide();
        }else{
            $(this).css("color","#666");
            $(this).removeAttr("clickMark");
            $(".formcontainer-nav").show();
        }
        widthTranslate();
    });

    //导出
    this.exportOperate.on("click",function(){
        var jsonParam = {"reportDynamicParam":deepCopy(document.body.jsLee.jsonAll.reportDynamicParam),"personalSubjectId":document.body.jsLee.jsonAll.personalSubjectId,"datasourceName":document.body.jsLee.jsonAll.datasourceName,"databaseType":document.body.jsLee.jsonAll.databaseType,"reportTables":document.body.jsLee.jsonAll.reportTables,"subjectType":document.body.jsLee.jsonAll.subjectType};
        delete jsonParam.reportDynamicParam.tbs;
        for(var nI = 0; nI < jsonParam.reportDynamicParam.line.length ; nI++ ){
            if(jsonParam.reportDynamicParam.line[nI].fieldName == "Measures"){
                delete jsonParam.reportDynamicParam.line[nI].Measures;
                for(var mI = 0; mI < jsonParam.reportDynamicParam.line[nI].rptMeasures.length; mI++ ){
                    delete jsonParam.reportDynamicParam.line[nI].rptMeasures[mI].measureId;
                }
            }
            jsonParam.reportDynamicParam.line[nI].position = null;
        }
        for(var nI = 0; nI < jsonParam.reportDynamicParam.column.length ; nI++ ){
            if(jsonParam.reportDynamicParam.column[nI].combinationName == "Measures"){
                delete jsonParam.reportDynamicParam.column[nI].Measures;
                for(var mI = 0; mI < jsonParam.reportDynamicParam.column[nI].rptMeasures.length; mI++ ){
                    delete jsonParam.reportDynamicParam.column[nI].rptMeasures[mI].measureId;
                }
            }
            jsonParam.reportDynamicParam.column[nI].position = null;
        }
        var importParam = "name=" + JSON.stringify(jsonParam);
        if(jsonParam.reportDynamicParam.column.length > 0){//自己刷新报表表格
            $.download(requestUrl + document.body.jsLee.requestUrl.path9, importParam, "POST");
            //getAjaxResultLee(document.body.jsLee.requestUrl.path9,"POST",importParam,"abc(data)")
        }else{
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("请生成table","失败提示");
        };

    });
    //分享操作
    this.shareOperate.on("click",function(){
        openWin("800", "600", "personSharePop", true);
        //初始化弹框
        document.body.jsLee.shareType.find("option[value=1]").attr("selected",true);
        document.body.jsLee.shareType.trigger('chosen:updated');
        $(".userRole").hide();
        $(".userRole:first").show();
        //设置人员列表和角色列表缓存数组为空
        $("#userShare")[0].cacheArr = [];
        $("#roleShare")[0].cacheArr = [];
        initplugPath($("#userShare")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path10,{},"POST");

    });
    //分享类型选择
    this.shareType.on("change",function(){
        shareTypeClick(this);
    });
    //分享确认操作
    this.shareSure.on("click",function(){
        shareSureOperate();
    });

    /*//指标添加tab切换
    $("#measureTab li").on("click",function () {

    })*/
    //图形和table切换操作
    this.chartShow.on("click",function(){
        if($(".formcontainer-mainover").eq(0).is(":visible")){
            if(document.body.jsLee.jsonAll.reportDynamicParam.column.length > 0){//拖拽列区，生成表格
                $(this).css("color","#0375D7");
                $(this).attr("clickMark",1);
                $(".formcontainer-mainover").eq(0).hide();
                $(".formcontainer-mainover").eq(1).show();
                //图形文字显示
                $(this).find("i").removeClass("tableCss").addClass("chartCss");
                $(this).find("span").html("表格");
                initChart();
            }else if(document.body.jsLee.jsonAll.reportDynamicParam.column.length == 0 && document.body.jsLee.jsonAll.reportDynamicParam.line.length > 0){//只拖拽行区，出现详情列表（分页）
                var alertBox=new clsAlertBoxCtrl();
                alertBox.Alert("请拖拽列区，否则不能生成图形！","失败提示");
            }else{
                var alertBox=new clsAlertBoxCtrl();
                alertBox.Alert("请将待选区拖拽到行列区域！","失败提示");
            }
        }else{
            $(this).css("color","#666");
            $(this).removeAttr("clickMark");
            $(".formcontainer-mainover").eq(1).hide();
            $(".formcontainer-mainover").eq(0).show();
            //图形文字显示
            $(this).find("i").removeClass("chartCss").addClass("tableCss");
            $(this).find("span").html("图形");
            initTable();
        }
    });
    //柱图和饼图切换
    this.chartTypeTab.on("click",function(){
        initChart();
    });
    //图形设置按行列显示
    this.setUpChart.on("click",function(){
        openWin('360', '200','wayShow',true);
    });

    //图形设置按行列显示确定
    this.wayShowSure.on("click",function(){
        initChart();
        closePopupWin();
    });
    //图形设置按行列显示取消
    this.wayShowCancel.on("click",function(){
        closePopupWin();
    });
}
function clsMethodLee$refresh(){

}

//插件循环函数
function clsParentChildTableCtrl$progress(jsonItem, cloneRow) {
    //渲染Measures
    measureInit(cloneRow,jsonItem);
    //如果有报表id，说明只有一个主题。直接显示
    if(document.body.jsLee.personalAnalysisId){
        $(cloneRow).find("#childShow").show();
        document.body.jsLee.jsonAll = jsonItem;
        initHtmlData(jsonItem.reportDynamicParam);
    }

    //点击父dom展示子dom操作
    $(cloneRow).find("#parentClick").on("click",function () {
        document.body.jsLee.subjectId = $(this).parents("#cloneParentRow")[0].jsonData.personalSubjectId;
        childListShow(this,cloneRow,jsonItem);
        initRightShow();//重置勾选，刷新右侧区域
        initTableOrChart();
    });

    //指标Measures勾选刷新右侧待选列
    $(cloneRow).find("#cloneChildRow .chkC").on("click",function(){
        initWaitSelect(cloneRow,this);
    });

    //指标添加
    $(cloneRow).find("#createMeasure").on("click",function(){
        openWin('550', '600', 'createMeasurePop', true);
        document.body.jsLee.measureId = "";
        clearpopup();
    });
    /*if(document.body.jsLee.subjectId && document.body.jsLee.subjectId == jsonItem.personalSubjectId && !document.body.jsLee.personalAnalysisId){
        //$(cloneRow).find("#parentClick").click();
        $(cloneRow).find("#childShow").show();
        document.body.jsLee.jsonAll.rptMeasures = jsonItem.rptMeasures;
        /!*document.body.jsLee.jsonAll.reportDynamicParam = {
            "personalSubjectId":jsonItem.personalSubjectId,
                "column":[],
                "line":[],
                "tbs":[],
                "filter":[]
        }*!/
        initTableOrChart();
    }*/
}

function clsParentChildTableCtrl$childProgress(jsonCItem, childCloneRow, jsonItem, cloneRow) {
    //添加维度标示
    jsonCItem.dimensionIndex = "DIMENSION";
    //点击左侧主题维度刷新右侧待选列
    $(childCloneRow).find(".chkC").on("click",function(){
        initWaitSelect(cloneRow,this);
    });
    if(jsonCItem.isChecked == 1){
        $(childCloneRow).find(".chkC").attr("checked",true);
    }
    $(childCloneRow).find("#combinationName").attr("title",jsonCItem.combinationName);
}

function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    if(this.ctrl.id == "parentChildTableCopy"){
        $(cloneRow).find("#combinationName").addClass("content").attr("jsonData",JSON.stringify(jsonItem));
        if(jsonItem.isChecked == 1){
            $(cloneRow).find(".chkC").attr("checked",true);
        }
    }else if(this.ctrl.id == "tablePopupList"){//点击行列编辑弹框渲染
        //过滤区赋值fieldName
        /*if($(document.body.jsLee.dom).parents(".connectedSortable").hasClass("selDimensionSearch")){
            if(jsonItem.fieldVal.RG != undefined){//输入框
                $(cloneRow).find("#fieldValue").html("输入框");
            }else if(jsonItem.fieldVal.EQ != undefined){//选择框
                $(cloneRow).find("#fieldValue").html("选择框");
            }else if(jsonItem.fieldVal.GT != undefined){//起止时间框
                $(cloneRow).find("#fieldValue").html("起止时间框");
            }
        }*/
        if(jsonItem.isChecked == 1){
            $(cloneRow).find("input").attr("checked",true);
        }
        $(cloneRow).find("input").on("click",function(){
            /*if($(document.body.jsLee.dom).parents(".connectedSortable").hasClass("selDimensionSearch")){//过滤区操作
                tablePopupListJson1(this);//
                rightJsonJoin();
            }else{
                if(checkIsFinal(this)){
                    //修改行列隐藏信息jsonData
                    tablePopupListJson(this);
                    rightJsonJoin();
                }
            }*/
            if(checkIsFinal(this)){
                //修改行列隐藏信息jsonData
                tablePopupListJson(this);
                rightJsonJoin();
            }
        });
    }else if(this.ctrl.id == "userShare"){//人员列表
        checkboxIsTrue(jsonItem,cloneRow,$("#userShare")[0],"acctId");
    }else if(this.ctrl.id == "roleShare"){//角色列表
        checkboxIsTrue(jsonItem,cloneRow,$("#roleShare")[0],"acctId");
    }else if(this.ctrl.id == "searchTable"){//过滤区列表
        //编辑搜索方式
        $(cloneRow).find("#nameEdit").on("click",function(){
            document.body.jsLee.dom = cloneRow;
            openWin("508", "388", "searchSetPopup", true);
            initplugData($("#searchSetDeatil")[0],"standardTableCtrl",jsonItem.tmp);
            rightJsonJoin();
        });
    }else if(this.ctrl.id == "searchSetDeatil"){//点击编辑后，过滤区弹框渲染
        //赋值fieldNameA
        if(jsonItem.fieldVal.RG != undefined){//输入框被选择
            $(cloneRow).find("#fieldValA").html("输入框");
        }else if(jsonItem.fieldVal.EQ != undefined){//选择框被选择
            $(cloneRow).find("#fieldValA").html("选择框");
        }else if(jsonItem.fieldVal.GT != undefined){//起止时间框被选择
            $(cloneRow).find("#fieldValA").html("起止时间框");
        }
        //赋值勾选框
        if(jsonItem.isChecked){
            $(cloneRow).find("input[type=checkbox]").attr("checked",true);
        }else{
            $(cloneRow).find("input[type=checkbox]").attr("checked",false);
        }
        //勾选搜索方式
        $(cloneRow).find("input[type=checkbox]").on("click",function(){

            searchSetDeatilCheck(this)
            rightJsonJoin();
        });
    }
}

//插件渲染前操作
function clsStandardTableCtrl$before() {
    if(this.ctrl.id == "userShare"){
        $("#userShare #checkAll1").removeAttr("checked");
    }else if(this.ctrl.id == "roleShare"){
        $("#roleShare #checkAll2").removeAttr("checked");
    }
}

function clsParentChildTableCtrl$after() {
    //搜索操作父子树状结构
    $(".btnSearch").on("click",function(){
        var inputSearchText = $(this).prev().val();
        var cloneRows = $(this).parents("#childShow").find("*[id=cloneChildRow]");
        cloneRows.each(function(){
            if($(this).find("#combinationName").html().indexOf(inputSearchText) == -1){
                $(this).hide();
            }else{
                $(this).show();
            }
        });

    });
}

function clsStandardTableCtrl$after() {
    if(this.ctrl.id == "userShare"){//人员列表
        isAllCheck($("#userShare")[0],$("#userShare #checkAll1"));
    }else if(this.ctrl.id == "roleShare"){//角色列表
        isAllCheck($("#roleShare")[0],$("#roleShare #checkAll2"));
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

//如果是自定义数据报表回显数据
function initHtmlData(data){
    $(".selDimensionRows").html("");
    $(".selDimensionCols").html("");
    document.body.jsLee.subjectId = data.personalSubjectId;
    //初始化待选列
    initplugData($("#parentChildTableCopy")[0],"standardTableCtrl",data.tbs);
    //初始化过滤区
    initplugData($("#searchTable")[0],"standardTableCtrl",data.filter);
    /*for(var nI = 0 ; nI < data.filter.length; nI++ ){
        var cloneRowF = $("#parentChildTableCopy #templateRow").clone(true);
        cloneRowF.attr("id","cloneRow").show();
        cloneRowF.find("#fieldNameEdit").show();
        cloneRowF.find("#fieldName").addClass("content").html(JSON.parse(data.filter[nI].filterJson).fieldName);
        cloneRowF.find(".content").attr("jsonData",data.filter[nI].filterJson);
        cloneRowF[0].jsonData = JSON.parse(data.filter[nI].filterJson);
        cloneRowF[0].jsonDataA = data.filter[nI];
        cloneRowF.find("#fieldNameEdit").bind();
        cloneRowF.find("#fieldNameEdit").bind("click",function(){
            editOpeSearch(this);
        });

        $(".selDimensionSearch").append(cloneRowF);
    }*/
    //初始化行
    for(var mI = 0 ; mI < data.line.length; mI++ ){
        var cloneRowL = $("#parentChildTableCopy #templateRow").clone(true);
        cloneRowL.attr("id","cloneRow").show();
        cloneRowL.find("#combinationName").addClass("content").html(data.line[mI].combinationName);
        //编辑操作
        cloneRowL.find("#fieldNameEdit").show();
        cloneRowL.find(".content").attr("jsonData",JSON.stringify(data.line[mI]));
        cloneRowL[0].jsonData = data.line[mI];
        cloneRowL.find("#fieldNameEdit").bind();
        cloneRowL.find("#fieldNameEdit").bind("click",function(){
            editOpeRow(this);
        });

        //小计操作
        cloneRowL.find("#subtotalBox").show();
        if(data.line[mI].subTotal){
            cloneRowL.find("#subtotal").addClass("activeOpe");
        }else{
            cloneRowL.find("#subtotal").removeClass("activeOpe");
        }
        cloneRowL.find("#subtotal").bind();
        cloneRowL.find("#subtotal").bind("click",function(){
            subTotalIsTrue(this);
        });
        //是否显示设置过滤
        if(data.line[mI].combinationName != "Measures"){
            cloneRowL.find("#searchSetBox").show();
        }else{
            cloneRowL.find("#searchSetBox").hide();
        }
        if(data.line[mI].searchTrue){
            cloneRowL.find("#searchSet").addClass("activeOpe");
        }else{
            cloneRowL.find("#searchSet").removeClass("activeOpe");
        }
        cloneRowL.find("#searchSet").unbind();
        cloneRowL.find("#searchSet").bind("click",function(){
            searchSetTrue(this);

        });

        $(".selDimensionRows").append(cloneRowL);
    }
    //初始化列
    for(var oI = 0 ; oI < data.column.length; oI++ ){
        var cloneRowC = $("#parentChildTableCopy #templateRow").clone(true);
        cloneRowC.attr("id","cloneRow").show();
        cloneRowC.find("#combinationName").addClass("content").html(data.column[oI].combinationName);
        cloneRowC.find(".content").attr("jsonData",JSON.stringify(data.column[oI]));

        //编辑操作
        cloneRowC.find("#fieldNameEdit").show();
        cloneRowC[0].jsonData = data.column[oI];
        cloneRowC.find("#fieldNameEdit").bind();
        cloneRowC.find("#fieldNameEdit").bind("click",function(){
            editOpeCol(this);
        });
        //小计操作
        cloneRowC.find("#subtotalBox").show();
        if(data.column[oI].subTotal){
            cloneRowC.find("#subtotal").addClass("activeOpe");
        }else{
            cloneRowC.find("#subtotal").removeClass("activeOpe");
        }
        cloneRowC.find("#subtotal").bind();
        cloneRowC.find("#subtotal").bind("click",function(){
            subTotalIsTrue(this);
        });
        //是否显示设置过滤
        if(data.column[oI].combinationName != "Measures"){
            cloneRowC.find("#searchSetBox").show();
        }else{
            cloneRowC.find("#searchSetBox").hide();
        }
        if(data.column[oI].searchTrue){
            cloneRowC.find("#searchSet").addClass("activeOpe");
        }else{
            cloneRowC.find("#searchSet").removeClass("activeOpe");
        }
        cloneRowC.find("#searchSet").unbind();
        cloneRowC.find("#searchSet").bind("click",function(){
            searchSetTrue(this);

        });
        $(".selDimensionCols").append(cloneRowC);
    }
    //初始化拖拽
    var obj = new clsRptCtrl();
    obj.ctrl= $("#rptList")[0];
    document.body.jsRptCtrl = obj;
    obj.initLayout();

    if($("#immediatelyRefresh").is(":checked")){
        initTableOrChart()
    }

    //
}

//行列转换刷新行列
function initRowCol(data){
    $(".selDimensionRows").html("");
    $(".selDimensionCols").html("");
    //初始化行
    for(var mI = 0 ; mI < data.line.length; mI++ ){
        var cloneRowL = $("#parentChildTableCopy #templateRow").clone(true);
        cloneRowL.attr("id","cloneRow").show();
        cloneRowL.find("#combinationName").addClass("content").html(data.line[mI].combinationName);
        //编辑操作
        cloneRowL.find("#fieldNameEdit").show();
        cloneRowL.find(".content").attr("jsonData",JSON.stringify(data.line[mI]));
        cloneRowL[0].jsonData = data.line[mI];
        cloneRowL.find("#fieldNameEdit").bind();
        cloneRowL.find("#fieldNameEdit").bind("click",function(){
            editOpeRow(this);
        });

        //小计操作
        cloneRowL.find("#subtotalBox").show();
        if(data.line[mI].subTotal){
            cloneRowL.find("#subtotal").addClass("activeOpe");
        }else{
            cloneRowL.find("#subtotal").removeClass("activeOpe");
        }
        cloneRowL.find("#subtotal").bind();
        cloneRowL.find("#subtotal").bind("click",function(){
            subTotalIsTrue(this);
        });
        //是否显示设置过滤
        if(data.line[mI].combinationName != "Measures"){
            cloneRowL.find("#searchSetBox").show();
        }else{
            cloneRowL.find("#searchSetBox").hide();
        }
        if(data.line[mI].searchTrue){
            cloneRowL.find("#searchSet").addClass("activeOpe");
        }else{
            cloneRowL.find("#searchSet").removeClass("activeOpe");
        }
        cloneRowL.find("#searchSet").unbind();
        cloneRowL.find("#searchSet").bind("click",function(){
            searchSetTrue(this);

        });

        $(".selDimensionRows").append(cloneRowL);
    }
    //初始化列
    for(var oI = 0 ; oI < data.column.length; oI++ ){
        var cloneRowC = $("#parentChildTableCopy #templateRow").clone(true);
        cloneRowC.attr("id","cloneRow").show();
        cloneRowC.find("#combinationName").addClass("content").html(data.column[oI].combinationName);
        cloneRowC.find(".content").attr("jsonData",JSON.stringify(data.column[oI]));

        //编辑操作
        cloneRowC.find("#fieldNameEdit").show();
        cloneRowC[0].jsonData = data.column[oI];
        cloneRowC.find("#fieldNameEdit").bind();
        cloneRowC.find("#fieldNameEdit").bind("click",function(){
            editOpeCol(this);
        });
        //小计操作
        cloneRowC.find("#subtotalBox").show();
        if(data.column[oI].subTotal){
            cloneRowC.find("#subtotal").addClass("activeOpe");
        }else{
            cloneRowC.find("#subtotal").removeClass("activeOpe");
        }
        cloneRowC.find("#subtotal").bind();
        cloneRowC.find("#subtotal").bind("click",function(){
            subTotalIsTrue(this);
        });
        //是否显示设置过滤
        if(data.column[oI].combinationName != "Measures"){
            cloneRowC.find("#searchSetBox").show();
        }else{
            cloneRowC.find("#searchSetBox").hide();
        }
        if(data.column[oI].searchTrue){
            cloneRowC.find("#searchSet").addClass("activeOpe");
        }else{
            cloneRowC.find("#searchSet").removeClass("activeOpe");
        }
        cloneRowC.find("#searchSet").unbind();
        cloneRowC.find("#searchSet").bind("click",function(){
            searchSetTrue(this);

        });
        $(".selDimensionCols").append(cloneRowC);
    }
    //初始化拖拽
    var obj = new clsRptCtrl();
    obj.ctrl= $("#rptList")[0];
    document.body.jsRptCtrl = obj;
    obj.initLayout();

    if($("#immediatelyRefresh").is(":checked")){
        initTableOrChart()
    }
}

//渲染Measures
function measureInit(cloneRow,jsonItem){
    $(cloneRow).find("#cloneChildRow")[0].jsonData = {"fieldName":"Measures","combinationName":"Measures","rptMeasures":jsonItem.rptMeasures,"Measures":"1","demandType":"MEASURES"};//缓存当前行jsonData
    if(jsonItem.isChecked == 1){
        $(cloneRow).find("#cloneChildRow .chkC").attr("checked",true);
    }
    if(jsonItem.rptMeasures.length > 0){
        for(var nI = 0 ; nI < jsonItem.rptMeasures.length ; nI++ ){
            var cloneSpecialRow = $(cloneRow).find("#templateSpecialRow").clone(true);
            cloneSpecialRow.attr("id","cloneSpecialRow").show();
            cloneSpecialRow.find("#measuresName").html(jsonItem.rptMeasures[nI].measuresName);
            cloneSpecialRow[0].jsonData = jsonItem.rptMeasures[nI];
            if(!document.body.jsLee.personalAnalysisId){
                cloneSpecialRow.find("#measuresName").on("click",function () {//指标编辑操作
                    document.body.jsLee.measureEditJson = $(this).parents("#cloneSpecialRow")[0].jsonData;
                    document.body.jsLee.measureId = $(this).parents("#cloneSpecialRow")[0].jsonData.measureId;
                    openWin('550', '600', 'createMeasurePop', true);
                    clearpopup();//初始化弹框
                    measureEditJsonAssign($(this).parents("#cloneSpecialRow")[0].jsonData,jsonItem.rptMeasures);//进行数据复现
                });
            }
            cloneSpecialRow.find("#measureDeleteOpe").on("click",function () {//指标删除操作
                var jsonParam = {"measureId":$(this).parents("#cloneSpecialRow")[0].jsonData.measureId};
                jsonParam.personalAnalysisId = document.body.jsLee.personalAnalysisId;
                getAjaxResultLee(document.body.jsLee.requestUrl.path5,"POST",jsonParam,"measureDeleteCallBack(data)")
            });
            $(cloneRow).find("#templateSpecialRow").before(cloneSpecialRow);
        }
    }
}

//点击左侧主题维度或者指标刷新右侧待选列
function initWaitSelect(cloneRow,that){
    /*document.body.jsLee.checkedAll = [];//置空缓存checked数组
    document.body.jsLee.jsonAll.reportDynamicParam.filter = [];
    $(cloneRow).find("*[id=cloneChildRow] .chkC").each(function(index,val){
        if(index != $(cloneRow).find("*[id=cloneChildRow] .chkC").length - 1 && $(this).is(":checked")) {//最后一个cloneRow，measures操作
            document.body.jsLee.checkedAll.push($(this).parents("#cloneChildRow")[0].jsonData);
            document.body.jsLee.jsonAll.rptDataFields[index].isChecked = 1;
        }else if(index != $(cloneRow).find("*[id=cloneChildRow] .chkC").length - 1 && !$(this).is(":checked")){
            document.body.jsLee.jsonAll.rptDataFields[index].isChecked = 0;
        }else if(index == $(cloneRow).find("*[id=cloneChildRow] .chkC").length - 1 && $(this).is(":checked")){
            for(var nI = 0; nI < $(this).parents("#cloneChildRow")[0].jsonData.rptMeasures.length; nI++ ){
                var jsonA = {};
                jsonA = $(this).parents("#cloneChildRow")[0].jsonData.rptMeasures[nI];
                jsonA.fieldName = $(this).parents("#cloneChildRow")[0].jsonData.rptMeasures[nI].measuresName;
                jsonA.combinationName = $(this).parents("#cloneChildRow")[0].jsonData.rptMeasures[nI].measuresName;
                //jsonA.fieldCode = $(this).parents("#cloneChildRow")[0].jsonData.rptMeasures[nI].fieldCode;
            }
            document.body.jsLee.checkedAll.push($(this).parents("#cloneChildRow")[0].jsonData);
            document.body.jsLee.jsonAll.isChecked = 1;
        }else if(index == $(cloneRow).find("*[id=cloneChildRow] .chkC").length - 1 && !$(this).is(":checked")){
            document.body.jsLee.jsonAll.isChecked = 0;
        }
    });
    //刷新右侧待选列
    initRightShow();*/


    //修改需求左右勾选同步数据
    if($(that).is(":checked") && $(that).parents("#cloneChildRow")[0].jsonData.fieldName == "Measures"){//选中指标
        document.body.jsLee.jsonAll.isChecked = 1;
        for(var nI = 0; nI < $(that).parents("#cloneChildRow")[0].jsonData.rptMeasures.length; nI++ ){
            var jsonA = {};
            jsonA = $(that).parents("#cloneChildRow")[0].jsonData.rptMeasures[nI];
            jsonA.fieldName = $(that).parents("#cloneChildRow")[0].jsonData.rptMeasures[nI].measuresName;
            jsonA.combinationName = $(that).parents("#cloneChildRow")[0].jsonData.rptMeasures[nI].measuresName;
            //jsonA.fieldCode = $(that).parents("#cloneChildRow")[0].jsonData.rptMeasures[nI].fieldCode;
        }
        document.body.jsLee.jsonAll.reportDynamicParam.tbs.push($(that).parents("#cloneChildRow")[0].jsonData);
        document.body.jsLee.jsonAll.isChecked = 1;
    }else if(!$(that).is(":checked") && $(that).parents("#cloneChildRow")[0].jsonData.fieldName == "Measures"){//取消勾选指标
        document.body.jsLee.jsonAll.isChecked = 0;
        soliceRightArr(that,"fieldName");
    }else if($(that).is(":checked") && $(that).parents("#cloneChildRow")[0].jsonData.fieldName != "Measures"){//选中维度
        //左侧isChecked标记
        for(var nI = 0; nI < document.body.jsLee.jsonAll.rptDataFields.length; nI++){
            if(document.body.jsLee.jsonAll.rptDataFields[nI].fieldId == $(that).parents("#cloneChildRow")[0].jsonData.fieldId){
                document.body.jsLee.jsonAll.rptDataFields[nI].isChecked = 1;
            }
        }
        document.body.jsLee.jsonAll.reportDynamicParam.tbs.push($(that).parents("#cloneChildRow")[0].jsonData);
    }else if(!$(that).is(":checked") && $(that).parents("#cloneChildRow")[0].jsonData.fieldName != "Measures"){//取消勾选维度
        //取消左侧isChecked标记
        for(var nI = 0; nI < document.body.jsLee.jsonAll.rptDataFields.length; nI++){
            if(document.body.jsLee.jsonAll.rptDataFields[nI].fieldId == $(that).parents("#cloneChildRow")[0].jsonData.fieldId){
                document.body.jsLee.jsonAll.rptDataFields[nI].isChecked = 0;
            }
        }
        soliceRightArr(that,"fieldId");
    }
    initHtmlData(document.body.jsLee.jsonAll.reportDynamicParam);
}

//
function soliceRightArr(that,idMark){
    //匹配是否在待选列
    for(var nI = 0; nI < document.body.jsLee.jsonAll.reportDynamicParam.tbs.length; nI++ ){
        if(document.body.jsLee.jsonAll.reportDynamicParam.tbs[nI][idMark] == $(that).parents("#cloneChildRow")[0].jsonData[idMark]){
            document.body.jsLee.jsonAll.reportDynamicParam.tbs.splice(nI,1);
        }
    }
    //匹配是否在列
    for(var nI = 0; nI < document.body.jsLee.jsonAll.reportDynamicParam.column.length; nI++ ){
        if(document.body.jsLee.jsonAll.reportDynamicParam.column[nI][idMark] == $(that).parents("#cloneChildRow")[0].jsonData[idMark]){
            if(document.body.jsLee.jsonAll.reportDynamicParam.column[nI].searchTrue){
                for(var mI = 0; mI < document.body.jsLee.jsonAll.reportDynamicParam.filter.length; mI++ ){
                    if(document.body.jsLee.jsonAll.reportDynamicParam.filter[mI].code == document.body.jsLee.jsonAll.reportDynamicParam.column[nI].fieldCode){
                        document.body.jsLee.jsonAll.reportDynamicParam.filter.splice(mI,1);
                    }
                }
            }
            document.body.jsLee.jsonAll.reportDynamicParam.column.splice(nI,1);
        }
    }
    //匹配是否在行
    for(var nI = 0; nI < document.body.jsLee.jsonAll.reportDynamicParam.line.length; nI++ ){
        if(document.body.jsLee.jsonAll.reportDynamicParam.line[nI][idMark] == $(that).parents("#cloneChildRow")[0].jsonData[idMark]){
            if(document.body.jsLee.jsonAll.reportDynamicParam.line[nI].searchTrue){
                for(var mI = 0; mI < document.body.jsLee.jsonAll.reportDynamicParam.filter.length; mI++ ){
                    if(document.body.jsLee.jsonAll.reportDynamicParam.filter[mI].code == document.body.jsLee.jsonAll.reportDynamicParam.line[nI].fieldCode){
                        document.body.jsLee.jsonAll.reportDynamicParam.filter.splice(mI,1);
                    }
                }
            }
            document.body.jsLee.jsonAll.reportDynamicParam.line.splice(nI,1);
        }
    }
}
//刷新右侧展示拖拽区域
function initRightShow(){
    //置空拖拽的box，重新初始化
    $(".selDimensionRows").html("");
    $(".selDimensionCols").html("");
    //$(".selDimensionSearch").html("");
    initplugData($("#parentChildTableCopy")[0],"standardTableCtrl",document.body.jsLee.checkedAll);
    if(document.body.jsLee.jsonAll.reportDynamicParam){
        initplugData($("#searchTable")[0],"standardTableCtrl",document.body.jsLee.jsonAll.reportDynamicParam.filter);
    }else{
        initplugData($("#searchTable")[0],"standardTableCtrl",[]);
    }
    rightJsonJoin();//缓存提交数组
    var obj = new clsRptCtrl();
    obj.ctrl= $("#rptList")[0];
    document.body.jsRptCtrl = obj;
    obj.initLayout();
}

//左侧主题列表点击父元素展示子列表
function childListShow(that,cloneRow,jsonItem){
    document.body.jsLee.checkedAll = [];//重置缓存checked数组
    if($(that).attr("clickMark") == 0){//目前操作为展开字内容。
        document.body.jsLee.jsonAll = jsonItem;
        document.body.jsLee.jsonAll.reportDynamicParam = {
            "personalSubjectId":jsonItem.personalSubjectId,
            "column":[],
            "line":[],
            "tbs":[],
            "filter":[]
        }
        //初始化table
        $("#parentChildTableList *[id=cloneChildRow] .chkC").attr("checked",false);//字内容所有checkbox
        $("#parentChildTableList *[id=childShow]").hide();//子内容父级盒子
        $("*[id=parentClick] i").removeClass("firstParent__icon1");
        $("#parentChildTableList *[id=parentClick]").attr("clickMark",0);//父级点击盒子

        $(that).attr("clickMark",1);//改变标示状态 0-收起子内容  1-展开子内容
        $(cloneRow).find("*[id=childShow]").show();
        $(cloneRow).find("#parentClick i").addClass("firstParent__icon1");
    }else{
        document.body.jsLee.jsonAll.reportDynamicParam = {
            "personalSubjectId":"",
            "column":[],
            "line":[],
            "tbs":[],
            "filter":[]
        }
        $("#parentChildTableList *[id=cloneChildRow] .chkC").attr("checked",false);
        $("#parentChildTableList *[id=childShow]").hide();
        $("*[id=parentClick] i").removeClass("firstParent__icon1");
        $("#parentChildTableList *[id=parentClick]").attr("clickMark",0);
    }
    $(cloneRow).find("#inputSearch").val("");
    $(cloneRow).find(".btnSearch").click();
}

//添加或者编辑指标，初始化弹框
function clearpopup(){
    //初始化下拉框
    $("#createMeasurePop #fieldId").attr("initValue","");
    initplugPath($("#createMeasurePop #fieldId")[0],"singleSelectCtrl",document.body.jsLee.requestUrl.path2,{"personalSubjectId":document.body.jsLee.subjectId},"POST");
    $("#createMeasurePop #measureType option:first").attr("selected",true);
    $("#createMeasurePop #measureType").trigger("chosen:updated");
    //初始化input
    $("#createMeasurePop *[id=measuresName]").val("");
    //初始化tab页
    $("#measureTab li:first").click();
    //初始化待选指标path3
    getAjaxResultLee(document.body.jsLee.requestUrl.path3,"POST",{"personalSubjectId":document.body.jsLee.subjectId},"waitMeasureInit(data)");
    $("#formulaContent").html("");
}

//弹框中待选指标赋值
function waitMeasureInit(data){
    data = JSON.parse(data);
    //data = {"retCode":"0000000","retDesc":"操作成功!","timestamp":"2018-10-31 20:31:41.451","rspBody":{"resultData":[{"measureId":1,"measuresName":"测试指标name","fieldId":16,"fieldCode":"LOGIN_SOURCE","measureType":"sum","subjectId":1,"removeStatus":0,"createdBy":"?????","createdDt":1540382592000,"updatedBy":null,"updatedDt":null,"measureRule":null,"rptMeasuresBody":null},{"measureId":2,"measuresName":"测试name","fieldId":22,"fieldCode":"MOBILE_PHONE","measureType":"sum","subjectId":1,"removeStatus":0,"createdBy":"?????","createdDt":1540791202000,"updatedBy":null,"updatedDt":null,"measureRule":null,"rptMeasuresBody":null}]}};
    if(data.retCode == "0000000"){
        var jsonDataParse = null;
        if(document.body.jsLee.measureEditJson.measureType == "custom"){
            jsonDataParse = document.body.jsLee.measureEditJson.rptMeasuresBody;
        }
        document.body.jsFCtrl.init(data.rspBody.resultData,jsonDataParse);
    }
}

//编辑指标赋值
function measureEditJsonAssign(jsonEdit,jsonArr){
    if(jsonEdit.measureType == "custom"){//说明是自定义指标
        $("#measureTab li:last").click();
        $("#mineMeasure #measuresName").val(jsonEdit.measuresName);
        document.body.jsFCtrl.parse(jsonEdit.reportMeasure);//初始化计算器
    }else{//系统指标
        //setValue4Desc(jsonEdit,$("#systemMeasure")[0])//赋值
        $("#systemMeasure #fieldId").attr("initValue",jsonEdit.fieldId);
        initplugPath($("#createMeasurePop #fieldId")[0],"singleSelectCtrl",document.body.jsLee.requestUrl.path2,{"personalSubjectId":1},"POST");
        $("#createMeasurePop #measureType option[value="+ jsonEdit.measureType +"]").attr("selected",true);
        $("#createMeasurePop #measureType").trigger("chosen:updated");
    }
}

//校验新增指标
function checkTrue(){
    var boolLock = true;

    if($("#measureTab li:first").hasClass("tabTitLi")){//系统指标
        /*initValidate($("#systemMeasure")[0]);
        var valiClass=new clsValidateCtrl();*/
        if($("#systemMeasure #fieldId option:selected").val() == "" || $("#systemMeasure #measureType option:selected").val() == ""){
            if($("#systemMeasure #fieldId option:selected").val() == ""){
                showErrInfoByCustomDiv($("#systemMeasure #fieldId").next()[0],"请选择指标字段!");
            }
            if($("#systemMeasure #measureType option:selected").val() == ""){
                showErrInfoByCustomDiv($("#systemMeasure #measureType").next()[0],"请选择函数名称!");
            }
            boolLock = false;
        }
    }else{//自定义指标
        initValidate($("#mineMeasure")[0]);
        var valiClass=new clsValidateCtrl();
        if(!valiClass.validateAll4Ctrl($("#mineMeasure")[0]) || $("#mineMeasure #formulaContent").html() == ""){
            if($("#mineMeasure #formulaContent").html() == ""){
                showErrInfoByCustomDiv($("#mineMeasure #formulaContent")[0],"请组合公式!");
            }
            boolLock = false;
        }
    }
    return boolLock;
}

//添加修改指标拼接参数
function jsonParamJoin(){
    if($("#measureTab li:first").hasClass("tabTitLi")) {//系统指标
        var jsonParam = {/*"measuresName":"",*/"fieldId":"","measureType":""};
        jsonParam.subjectId = document.body.jsLee.subjectId;
        jsonParam.measureId = document.body.jsLee.measureId;
        jsonParam.personalAnalysisId = document.body.jsLee.personalAnalysisId;
        getValue4Desc(jsonParam,$("#systemMeasure")[0]);
    }else{//自定义指标
        var jsonParam = {"measuresName":""};
        jsonParam.subjectId = document.body.jsLee.subjectId;
        jsonParam.measureId = document.body.jsLee.measureId;
        jsonParam.measureType = "custom";
        jsonParam.reportMeasure = document.body.jsFCtrl.save();
        jsonParam.personalAnalysisId = document.body.jsLee.personalAnalysisId;
        getValue4Desc(jsonParam,$("#mineMeasure")[0]);
    }
    return jsonParam;

}

//添加修改指标回调函数
function createMeasureCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        //document.body.jsLee.init();

        if(this.personalAnalysisId){
            getAjaxResultLee(document.body.jsLee.requestUrl.path8,"POST",{"personalAnalysisId":document.body.jsLee.personalAnalysisId},"initLeftAginHtml(data)",function(){
                $("#ajaxWaiting").show();
            },function () {
                $("#ajaxWaiting").hide();
            });
        }else{
            getAjaxResultLee(document.body.jsLee.requestUrl.path1,"POST",{"personalSubjectId":document.body.jsLee.personalSubjectId},"initLeftAginHtml(data)",function(){
                $("#ajaxWaiting").show();
            },function () {
                $("#ajaxWaiting").hide();
            });
        }
    }
}

//添加，删除指标操作
function initLeftAginHtml(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        if(document.body.jsLee.personalAnalysisId){

        }else{
            //拼接json
            for(var nI = 0 ; nI < data.rspBody.resultData.length; nI++ ){
                if(document.body.jsLee.subjectId == data.rspBody.resultData[nI].personalSubjectId){
                    document.body.jsLee.jsonAll.rptMeasures = data.rspBody.resultData[nI].rptMeasures;
                    $("*[id=cloneParentRow]").eq(nI).find("*[id=cloneSpecialRow]").remove();
                    measureInit($("*[id=cloneParentRow]").eq(nI),data.rspBody.resultData[nI]);
                    //当前展开主题下的指标勾选
                    if($("*[id=cloneParentRow]").eq(nI).find("*[id=cloneChildRow]:last .chkC").is(":checked")){
                        //同步待选列或者行列过滤区的值
                        for(var mI = 0; mI < document.body.jsLee.jsonAll.reportDynamicParam.tbs.length ; mI++ ){
                            if(document.body.jsLee.jsonAll.reportDynamicParam.tbs[mI].fieldName == "Measures"){
                                document.body.jsLee.jsonAll.reportDynamicParam.tbs[mI].rptMeasures = document.body.jsLee.jsonAll.rptMeasures;
                            }
                        }
                        for(var mI = 0; mI < document.body.jsLee.jsonAll.reportDynamicParam.column.length ; mI++ ){
                            if(document.body.jsLee.jsonAll.reportDynamicParam.column[mI].fieldName == "Measures"){
                                document.body.jsLee.jsonAll.reportDynamicParam.column[mI].rptMeasures = document.body.jsLee.jsonAll.rptMeasures;
                            }
                        }
                        for(var mI = 0; mI < document.body.jsLee.jsonAll.reportDynamicParam.line.length ; mI++ ){
                            if(document.body.jsLee.jsonAll.reportDynamicParam.line[mI].fieldName == "Measures"){
                                document.body.jsLee.jsonAll.reportDynamicParam.line[mI].rptMeasures = document.body.jsLee.jsonAll.rptMeasures;
                            }
                        }
                    }
                }
            }
            initHtmlData(document.body.jsLee.jsonAll.reportDynamicParam);
        }
    }
}

//删除指标回调函数
function measureDeleteCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        if(this.personalAnalysisId){
            getAjaxResultLee(document.body.jsLee.requestUrl.path8,"POST",{"personalAnalysisId":document.body.jsLee.personalAnalysisId},"initLeftAginHtml(data)",function(){
                $("#ajaxWaiting").show();
            },function () {
                $("#ajaxWaiting").hide();
            });
        }else{
            getAjaxResultLee(document.body.jsLee.requestUrl.path1,"POST",{"personalSubjectId":document.body.jsLee.personalSubjectId},"initLeftAginHtml(data)",function(){
                $("#ajaxWaiting").show();
            },function () {
                $("#ajaxWaiting").hide();
            });
        }
    }
}

//缓存右侧提交json
function rightJsonJoin(){
    //初始化缓存数组
    document.body.jsLee.jsonAll.reportDynamicParam.line = [];
    document.body.jsLee.jsonAll.reportDynamicParam.column = [];
    /*document.body.jsLee.jsonAll.reportDynamicParam.filter = [];*/
    document.body.jsLee.jsonAll.reportDynamicParam.tbs = [];
    for(var i=0;i<$(".selDimensionRows *[id=cloneRow]").length;i++)//行
    {
        //$(".selDimensionRows *[id=cloneRow]").eq(i).find(".content")[0].jsonData.isChecked = true;
        document.body.jsLee.jsonAll.reportDynamicParam.line.push($(".selDimensionRows *[id=cloneRow]")[i].jsonData);
    }
    for(var i=0;i<$(".selDimensionCols *[id=cloneRow]").length;i++)//列
    {
        document.body.jsLee.jsonAll.reportDynamicParam.column.push($(".selDimensionCols *[id=cloneRow]")[i].jsonData);
    }

    /*for(var i=0;i<$(".selDimensionSearch li").length;i++)//过滤区
    {
        $(".selDimensionSearch li")[i].jsonDataA.filterJson = JSON.stringify($(".selDimensionSearch li")[i].jsonData);
        document.body.jsLee.jsonAll.reportDynamicParam.filter.push($(".selDimensionSearch li")[i].jsonDataA);

    }*/
    for(var i=0;i<$(".selDimensionContent *[id=cloneRow]").length;i++)//待选区
    {
        $(".selDimensionContent *[id=cloneRow]")[i].jsonData.isChecked = 1;
        document.body.jsLee.jsonAll.reportDynamicParam.tbs.push($(".selDimensionContent *[id=cloneRow]")[i].jsonData);

    }
    //初始化生成拖拽table页面
    if($("#immediatelyRefresh").is(":checked")){
        initTableOrChart()
    }
}

//搜索条件初始化页面
function searchParse(jsonData,doms){//jsonData自生成提交回显json   doms中doms[i].jsonData是主题表中的维度json，获取下拉框
    $("#searchBox").html("");
    for(var nI = 0 ; nI < jsonData.length; nI++ ){
        for(var mI = 0 ; mI < jsonData[nI].tmp.length; mI++ ){
            if(jsonData[nI].tmp[mI].isChecked == 1 && jsonData[nI].tmp[mI].fieldVal.RG != undefined){//输入框被选择
                $("#searchBox").append("<li class='searchContent fl'><span style='margin-right: 5px'>"+ jsonData[nI].name +"</span>"+ "<input class='inputBase' type='text' value='" + jsonData[nI].tmp[mI].fieldVal.RG + "'></li>");
            }else if(jsonData[nI].tmp[mI].isChecked == 1 && jsonData[nI].tmp[mI].fieldVal.EQ != undefined){//选择框被选择
                $("#searchBox").append("<li class='searchContent fl'><span style='margin-right: 5px'>"+ jsonData[nI].name +"</span>"+ "<select class='inputBase' type='text'  emptyValue='请选择' initValue='" + jsonData[nI].tmp[mI].fieldVal.EQ + "' selCode='fieldValue' selValue='fieldValue'></select></li>")
                //initplugData($("#searchBox li").eq(nI).find("select")[0],"singleSelectCtrl",doms[nI].jsonData.rptDataDicts);
                $("#searchBox .searchContent").eq(nI).find("select").attr("comType","singleSelectCtrl");
                $("#searchBox .searchContent").eq(nI).find("select")[0].data = {"rspBody":JSON.parse(doms[nI].jsonData.filterJson).rptDataDicts};
                document.body.jsCtrl.ctrl = $("#searchBox li").eq(nI).find("select")[0];
                document.body.jsCtrl.init();
            }else if(jsonData[nI].tmp[mI].isChecked == 1 && jsonData[nI].tmp[mI].fieldVal.GT != undefined){//起止时间框被选择
                $("#searchBox").append("<li class='fl searchContent'><span style='margin-right: 5px'>"+ jsonData[nI].name +"</span>"+ "<input class='inputBase' type='text' name='startDate' id='startDate'>-<input class='inputBase' type='text' name='endDate' id='endDate'></li>")
                $("#searchBox .searchContent").eq(nI).find("input:first").attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\\'endDate\\')}'})").val(jsonData[nI].tmp[mI].fieldVal.GT);
                $("#searchBox .searchContent").eq(nI).find("input:last").attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\\'startDate\\')}'})").val(jsonData[nI].tmp[mI].fieldVal.LT);
            }
        }
        $("#searchBox li")[nI].jsonData = jsonData;
    }
    if(jsonData.length > 0 ){
        $("#searchBox").append("<li class='fl'><div class='btn_kind_6' id='searchBtn'>搜索</div></li>");
        //搜索按钮点击，拼接json参数，搜索条件
        $("#searchBtn").on("click",function(){
            $(".selDimensionSearch *[id=cloneRow]").each(function(index,val){
                var that = $(val);
                for(var oI = 0; oI < val.jsonData.tmp.length; oI++ ){
                    if(val.jsonData.tmp[oI].isChecked == 1 && val.jsonData.tmp[oI].fieldVal.RG != undefined){//输入框
                        val.jsonData.tmp[oI].fieldVal.RG = $("#searchBox .searchContent").eq(index).find("input").val();
                    }else if(val.jsonData.tmp[oI].isChecked == 1 && val.jsonData.tmp[oI].fieldVal.EQ != undefined){//选择框
                        val.jsonData.tmp[oI].fieldVal.EQ = $("#searchBox .searchContent").eq(index).find("select option:selected").val();
                    }else if(val.jsonData.tmp[oI].isChecked == 1 && val.jsonData.tmp[oI].fieldVal.GT != undefined){//时间框
                        val.jsonData.tmp[oI].fieldVal.GT = $("#searchBox .searchContent").eq(index).find("input:first").val();
                        val.jsonData.tmp[oI].fieldVal.LT = $("#searchBox .searchContent").eq(index).find("input:last").val();
                    }
                }
            });
            initTableOrChart();
        });
    }
}

//刷新生成行列table表格
function initTable(){
    $("#rptList").html("");
    $("#searchBox").html("");
    if(document.body.jsLee.jsonAll.reportDynamicParam.filter.length > 0){
        searchParse(document.body.jsLee.jsonAll.reportDynamicParam.filter,$(".selDimensionSearch li"));//搜索条件初始化页面
    }
    var jsonParam = {"reportDynamicParam":document.body.jsLee.jsonAll.reportDynamicParam,"personalSubjectId":document.body.jsLee.jsonAll.personalSubjectId,"datasourceName":document.body.jsLee.jsonAll.datasourceName,"databaseType":document.body.jsLee.jsonAll.databaseType,"reportTables":document.body.jsLee.jsonAll.reportTables,"subjectType":document.body.jsLee.jsonAll.subjectType};
    if(jsonParam.reportDynamicParam.column.length > 0){//自己刷新报表表格
        $("#pageTableBox").hide();
        getAjaxResultLee(document.body.jsLee.requestUrl.path6,"POST",jsonParam,"initMainTableCallBack(data)",function(){
            $("#ajaxWaiting").show();
        },function () {
            $("#ajaxWaiting").hide();
            //
        });
    }else if(jsonParam.reportDynamicParam.column.length == 0 && jsonParam.reportDynamicParam.line.length > 0){//刷新分页表格
        $("#pageTableBox").show();
        //getAjaxResultLee(document.body.jsLee.requestUrl.path15,"POST",document.body.jsLee.jsonAll,"pageTableInitCallBack(data)");
        //初始化插件th tr模版行
        var data = {"retCode":"0000000","retDesc":"操作成功!","timestamp":"2018-11-23 09:26:53.116","rspBody":{"pageNum":3,"pageSize":10,"startRow":0,"total":100,"pages":10,"resultData":[{"headers":["标题A","标题B","标题C","标题D","标题E"],"dataList":[["a11111","b11111","c11111","d11111","e11111"],["a22222","b22222","c22222","d22222","e22222"],["a33333","b33333","c33333","d33333","e33333"],["a44444","b44444","c44444","d44444","e44444"],["a55555","b55555","c55555","d55555","e55555"],["a66666","b66666","c66666","d66666","e66666"]],"pageNum":0,"pageSize":0,"total":0,"pages":0}],"requestData":null,"firstPage":false,"lastPage":false}};
        var titleArr = data.rspBody.resultData[0].headers;
        var headerTh;
        var bodyTd;
        for(var nI = 0 ; nI < titleArr.length ; nI++ ){
            headerTh += '<th class="formcontainer-table__thead">'+ titleArr[nI] +'</th>';
            bodyTd += '<td class="formcontainer-table__td" id="name'+ nI +'"></td>';
        }
        var templateContent = '<tr>'+ headerTh +'</tr><tr id="templateRow" style="display: none;">'+ bodyTd +'</tr>';
        $("#tableList").append(templateContent);
        //拼接数据
        var contentArr = data.rspBody.resultData[0].dataList;
        var dataResult = {"pageNum":data.rspBody.pageNum,"pageSize":data.rspBody.pageSize,"startRow":data.rspBody.startRow,"total":data.rspBody.total,"pages":data.rspBody.pages,"resultData":[]};
        for(var mI = 0; mI < contentArr.length ; mI++ ){
            var jsonItem = [];
            for(var oI = 0; oI < contentArr[mI].length ; oI++ ){
                var keyName = "name" + oI;
                var jsonA = {};
                jsonA[keyName] = contentArr[mI][oI];
                jsonItem.push(jsonA);
            }
            dataResult.resultData.push(jsonItem);
        }
        var $("#tableList")[0].data = dataResult;
        document.body.jsCtrl.ctrl = $("tableList")[0];
        document.body.jsCtrl.init();
    }
    /*//行列转换判断
    if(!$("#rowColTranslate").attr("clickMark")){
        var jsonParam = {"reportDynamicParam":document.body.jsLee.jsonAll.reportDynamicParam,"personalSubjectId":document.body.jsLee.jsonAll.personalSubjectId,"datasourceName":document.body.jsLee.jsonAll.datasourceName,"databaseType":document.body.jsLee.jsonAll.databaseType,"reportTables":document.body.jsLee.jsonAll.reportTables,"subjectType":document.body.jsLee.jsonAll.subjectType};
        if(jsonParam.reportDynamicParam.column.length > 0){//自己刷新报表表格
            getAjaxResultLee(document.body.jsLee.requestUrl.path6,"POST",jsonParam,"initMainTableCallBack(data)");
        }
    }else{
        var jsonParamA = deepCopy(document.body.jsLee.jsonAll);
        var rowJson = deepCopy(document.body.jsLee.jsonAll.reportDynamicParam.line);
        var colJson = deepCopy(document.body.jsLee.jsonAll.reportDynamicParam.column);
        jsonParamA.reportDynamicParam.column = rowJson;
        jsonParamA.reportDynamicParam.line = colJson;
        var jsonParam = {"reportDynamicParam":jsonParamA.reportDynamicParam,"personalSubjectId":jsonParamA.personalSubjectId,"datasourceName":jsonParamA.datasourceName,"databaseType":jsonParamA.databaseType,"reportTables":jsonParamA.reportTables,"subjectType":jsonParamA.subjectType};
        if(jsonParam.reportDynamicParam.column.length > 0){//自己刷新报表表格
            getAjaxResultLee(document.body.jsLee.requestUrl.path6,"POST",jsonParam,"initMainTableCallBack(data)");
        }
    }*/

}

//只拖拽行区生成详情分页列表
function pageTableInitCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){

    }
}


//行列生成数据报表
function initMainTableCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        document.body.jsRptCtrl.jsonData = data.rspBody;
        document.body.jsRptCtrl.parse();
        document.body.jsLee.chartOptions = data.rspBody.chartOptions;
        document.body.jsLee.chartTrue = false;
    }
}

//校验弹框中checkbox是否是最后一个选择;
function checkIsFinal(that){
    var lock = true;
    var num = 0;
    $("#tablePopupList *[id=cloneRow]").each(function(){
        if($(this).find("input[type=checkbox]").is(":checked")){
            num++;
        }
    })
    if(num == 0){
        $(that).attr("checked",true);
        lock = false;
    }
    return lock;
}

//修改弹框中行列jsonData
function tablePopupListJson(that){
    //改变当前行缓存状态值
    if($(that).is(":checked")){
        $(that).parents("#cloneRow")[0].jsonData.isChecked = 1;
    }else{
        $(that).parents("#cloneRow")[0].jsonData.isChecked = 0;
    }
    //拼接json
    var arrs = [];
    $("#tablePopupList *[id=cloneRow]").each(function(){
        arrs.push(this.jsonData);
    });
    if($(that).parents("td:first").next().attr("id") == "measuresName"){
        document.body.jsLee.dom.jsonData.rptMeasures = arrs;
        $(document.body.jsLee.dom).find("#measuresName").attr("jsonData",JSON.stringify(document.body.jsLee.dom.jsonData.rptMeasures));
    }else{
        document.body.jsLee.dom.jsonData.rptDataDicts = arrs;
        $(document.body.jsLee.dom).find("#fieldName").attr("jsonData",JSON.stringify(document.body.jsLee.dom.jsonData.rptDataDicts));
    }
}

//修改弹框中过滤区隐藏jsonData
function searchSetDeatilCheck(that){
    $("#searchSetDeatil *[id=cloneRow] input[type=checkbox]").each(function(){
        $(this).attr("checked",false);
        $(this).parents("#cloneRow")[0].jsonData.isChecked = 0;
    });
    $(that).attr("checked",true);
    $(that).parents("#cloneRow")[0].jsonData.isChecked = 1;

    var arrs = [];
    $("#searchSetPopup *[id=cloneRow]").each(function(){
        arrs.push(this.jsonData);
    });
    document.body.jsLee.dom.jsonData.tmp = arrs;
    //$(document.body.jsLee.dom).find("#fieldName").attr("jsonData",JSON.stringify(document.body.jsLee.dom.jsonDataA.tmp));
    //修改checkAll缓存数组
    for(var nI = 0 ; nI < document.body.jsLee.jsonAll.reportDynamicParam.filter.length ; nI++ ){
        var fieldIdMark = JSON.parse(document.body.jsLee.jsonAll.reportDynamicParam.filter[nI].filterJson).fieldId;
        if(document.body.jsLee.dom.jsonData.fieldId == fieldIdMark)
            document.body.jsLee.jsonAll.reportDynamicParam.filter[nI].tmp = arrs;
    };
}

//保存操作回调函数
function saveCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        document.body.jsLee.personalAnalysisId = data.rspBody;
        closePopupWin();
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"成功提示");
    }
}

//深拷贝
function deepCopy(obj) {
    // 只拷贝对象
    if (typeof obj !== 'object') return;
    // 根据obj的类型判断是新建一个数组还是一个对象
    if(obj instanceof Array){
        var newObj = []
    }else if(obj instanceof Object){
        var newObj = {}
    }else if(obj == null){
        var newObj = null;
    }
    //var newObj = obj instanceof Array ? [] : {};
    for (var key in obj) {
        // 遍历obj,并且判断是obj的属性才拷贝
        if (obj.hasOwnProperty(key)) {
            // 判断属性值的类型，如果是对象递归调用深拷贝
            newObj[key] = typeof obj[key] === 'object' ? deepCopy(obj[key]) : obj[key];
        }
    }
    return newObj;
}

//translate行列转换
function translateTable(that) {
    /*if(!$(that).attr("clickMark")){
        $(that).css("color","#0375D7");
        $(that).attr("clickMark",1);
    }else{
        $(that).css("color","#666");
        $(that).removeAttr("clickMark");
    };*/
    //转换图表需要的chartOptions
    var columnSeriesJson = deepCopy(document.body.jsLee.chartOptions.columnSeries);
    var lineSeriesJson = deepCopy(document.body.jsLee.chartOptions.lineSeries);
    document.body.jsLee.chartOptions.columnSeries = lineSeriesJson;
    document.body.jsLee.chartOptions.lineSeries = columnSeriesJson;

    //转换行列
    var rowJson = deepCopy(document.body.jsLee.jsonAll.reportDynamicParam.line);
    var colJson = deepCopy(document.body.jsLee.jsonAll.reportDynamicParam.column);
    document.body.jsLee.jsonAll.reportDynamicParam.line = colJson;
    document.body.jsLee.jsonAll.reportDynamicParam.column = rowJson;
    initRowCol(document.body.jsLee.jsonAll.reportDynamicParam);
};

//回显接口回调
function initHtmlCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        document.body.jsLee.chartOptions = data.rspBody.reportEchoBody.chartOptions;
        var arrA = [];
        arrA.push(data.rspBody.reportEchoBody);
        $("#parentChildTableList #createMeasure").remove();
        $("#parentChildTableList #measureDeleteOpe").remove();
        initplugData($("#parentChildTableList")[0],"parentChildTableCtrl",arrA);
        $("#parentChildTableList").find("#parentClick").attr("clickmark",1);
        initTableOrChart();
    }
}

//分享切换人员和角色点击
function shareTypeClick(that){
    if($(that).find("option:selected").val() == 1){//人员
        $("#searchBox1 #condacctTitle").val("");
        $("#userShare")[0].cacheArr = [];
        $(".userRole:first").show();
        $(".userRole:last").hide();
        $(".userRole:first input[type=checkbox]").attr("checked",false);
        initplugPath($("#userShare")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path10,{},"POST");
    }else{//角色
        $("#searchBox2 #condacctTitle").val("");
        $("#roleShare")[0].cacheArr = [];
        $(".userRole:first").hide();
        $(".userRole:last").show();
        $(".userRole:last input[type=checkbox]").attr("checked",false);
        initplugPath($("#roleShare")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path11,{},"POST");

    }
}

//刷新列表数据勾选初始化
function checkboxIsTrue(jsonItem,cloneRow,tableDom,mark){//tableDom——table节点     mark——主键id
    var arrCache = $(tableDom)[0].cacheArr;
    for(var nI = 0; nI < arrCache.length; nI++ ){
        if(jsonItem[mark] == arrCache[nI][mark]){
            $(cloneRow).find("#chkCoding").attr("checked",true);
        }
    }
}

//判断插件是否全选
function isAllCheck(tableDom,checkDom){
//判断是否全选
    var listCheck = $(tableDom).find("*[id=cloneRow] #chkCoding");
    var numLength = 0;
    for (var mI = 0; mI < listCheck.length; mI++){
        if(listCheck.eq(mI).is(":checked")){
            numLength++;
        }
    }
    if(numLength == listCheck.length && numLength > 0){
        $(checkDom).attr("checked",true);
    }
}

//点击分享确认操作
function shareSureOperate(){
    if(!document.body.jsLee.personalAnalysisId){//是否保存自定义报表
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("请保存报表","失败提示");
    }else if($("#shareType option:selected").val() == 1 && $("#userShare")[0].cacheArr.length == 0){//人员
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("请勾选分享人员","失败提示");
    }else if($("#shareType option:selected").val() == 0 && $("#roleShare")[0].cacheArr.length == 0){//角色
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("请勾选分享角色","失败提示");
    }else{
        var jsonParam = {"type":$("#shareType option:selected").val(),"personalAnalysisId":document.body.jsLee.personalAnalysisId,"list":[]};
        if($("#shareType option:selected").val() == 1){//人员
            //jsonParam.list = $("#userShare")[0].cacheArr;
            for(var nI = 0 ; nI < $("#userShare")[0].cacheArr.length ; nI++ ){
                jsonParam.list.push($("#userShare")[0].cacheArr[nI].acctId);
            }
        }else{//角色
            //jsonParam.list = $("#roleShare")[0].cacheArr;
            for(var nI = 0 ; nI < $("#roleShare")[0].cacheArr.length ; nI++ ){
                jsonParam.list.push($("#roleShare")[0].cacheArr[nI].acctId);
            }
        }
        getAjaxResultLee(document.body.jsLee.requestUrl.path12,"POST",jsonParam,"shareCallBack(data)");
    }
}

//分享成功回调
function shareCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"成功提示");
    }
}

//stop中每一个维度指标  编辑操作
function editOpeSearch(that){
    document.body.jsLee.dom = $(that).parents("#cloneRow")[0];//缓存当前cloneRow节点，便于check修改后修改数组
    openWin("508", "388", "tablePopupListPopup", true);
    $("#tablePopupList #templateRow td:last").attr("id","fieldValue");
    initplugData($("#tablePopupList")[0],"standardTableCtrl",$(that).parents("#cloneRow")[0].jsonDataA.tmp);

}

function editOpeCol(that){
    document.body.jsLee.dom = $(that).parents("#cloneRow")[0];//缓存当前cloneRow节点，便于check修改后修改数组
    openWin("508", "388", "tablePopupListPopup", true);
    //if($(that).parents("#cloneRow")[0].jsonData.Measures == 1){//指标
    if($(that).parents("#cloneRow")[0].jsonData.fieldName == "Measures"){//指标
        $("#tablePopupList #templateRow td:last").attr("id","fieldName");
        initplugData($("#tablePopupList")[0],"standardTableCtrl",$(that).parents("#cloneRow")[0].jsonData.rptMeasures);
    }else {//维度
        $("#tablePopupList #templateRow td:last").attr("id","fieldValue");
        initplugData($("#tablePopupList")[0],"standardTableCtrl",$(that).parents("#cloneRow")[0].jsonData.rptDataDicts);
    }
}

function editOpeRow(that){
    document.body.jsLee.dom = $(that).parents("#cloneRow")[0];//缓存当前cloneRow节点，便于check修改后修改数组
    openWin("508", "388", "tablePopupListPopup", true);
    if($(that).parents("#cloneRow")[0].jsonData.fieldName == "Measures"){//指标
        $("#tablePopupList #templateRow td:last").attr("id","fieldName");
        initplugData($("#tablePopupList")[0],"standardTableCtrl",$(that).parents("#cloneRow")[0].jsonData.rptMeasures);
    }else {//维度
        $("#tablePopupList #templateRow td:last").attr("id","fieldValue");
        initplugData($("#tablePopupList")[0],"standardTableCtrl",$(that).parents("#cloneRow")[0].jsonData.rptDataDicts);
    }
}

//小计勾选函数
function subTotalIsTrue(that){
    if(!$(that).hasClass("activeOpe")){
        $(that).addClass("activeOpe");
        $(that).parents("#cloneRow")[0].jsonData.subTotal = true;
    }else{
        $(that).removeClass("activeOpe");
        $(that).parents("#cloneRow")[0].jsonData.subTotal = false;
    }
    rightJsonJoin();
}

//搜索条件设置
function searchSetTrue(that){
    if(!$(that).hasClass("activeOpe")){//被选中
        $(that).addClass("activeOpe");
        $(that).parents("#cloneRow")[0].jsonData.searchTrue = true;
        //设置初始jsonData
        if(!$(that).parents("#cloneRow")[0].jsonDataA){
            document.body.jsLee.jsonAll.reportDynamicParam.filter.push({
                "code":$(that).parents("#cloneRow")[0].jsonData.fieldCode,
                "name":$(that).parents("#cloneRow")[0].jsonData.fieldName,
                "dbFieldTypeEnum":$(that).parents("#cloneRow")[0].jsonData.fieldType,
                "tmp":[
                    {fieldVal:{"RG":""},"isChecked":1},
                    {fieldVal:{"EQ":""},"isChecked":0},
                    {fieldVal:{"GT":"","LT":""},"isChecked":0}],
                "filterJson":JSON.stringify($(that).parents("#cloneRow")[0].jsonData)
            });
        }else{
            document.body.jsLee.jsonAll.reportDynamicParam.filter.push($(that).parents("#cloneRow")[0].jsonDataA);
        }
    }else{
        $(that).removeClass("activeOpe");
        $(that).parents("#cloneRow")[0].jsonData.searchTrue = false;
        for(var nI = 0 ; nI < document.body.jsLee.jsonAll.reportDynamicParam.filter.length ; nI++ ){
            var fieldIdMark = JSON.parse(document.body.jsLee.jsonAll.reportDynamicParam.filter[nI].filterJson).fieldId;
            if($(that).parents("#cloneRow")[0].jsonData.fieldId == fieldIdMark)
                document.body.jsLee.jsonAll.reportDynamicParam.filter.splice(nI,1);
        };
    }
    //初始化
    initplugData($("#searchTable")[0],"standardTableCtrl",document.body.jsLee.jsonAll.reportDynamicParam.filter);
    rightJsonJoin();
}

//提交成功回调函数
function submitCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox = new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc, "成功提示");
    }
}

//资源树，面板隐藏
function widthTranslate(){
    if($(".formcontainer-nav").is(":visible") && $(".formcontainer-right").is(":visible")){//资源树，面板都显示
        $(".formcontainer-tool").css("width","85%");
        $(".formcontainer-main").css("width","65%");
    }else if(!$(".formcontainer-nav").is(":visible") && $(".formcontainer-right").is(":visible")){//资源树隐藏，面板显示
        $(".formcontainer-tool").css("width","100%");
        $(".formcontainer-main").css("width","79%");
    }if($(".formcontainer-nav").is(":visible") && !$(".formcontainer-right").is(":visible")){//资源树显示，面板隐藏
        $(".formcontainer-tool").css("width","85%");
        $(".formcontainer-main").css("width","85%");
    }else if(!$(".formcontainer-nav").is(":visible") && !$(".formcontainer-right").is(":visible")){//资源树隐藏，面板隐藏
        $(".formcontainer-tool").css("width","100%");
        $(".formcontainer-main").css("width","100%");
    }
}

//初始化图表
function initChart(){
    if(!document.body.jsLee.chartTrue){//false  说明刷新table，进行初始化highchart
        $(".tableChart-input").attr("checked",false);
        $(".tableChart-input").eq(0).attr("checked",true);
        $("#wayShowType option:first").attr("selected",true);
        $("#wayShowType").trigger("chosen:updated");
        document.body.jsLee.chartTrue = true;
    }
    var jsonParam = document.body.jsLee.jsonAll;
    jsonParam.chartOptions = deepCopy(document.body.jsLee.chartOptions);
    jsonParam.chartOptions.chartType = $("input[name=tableChart]:first").is(":checked") ? "BAR" : "PIE";
    jsonParam.chartOptions.positionType = $("#wayShowType option:selected").val() == 0 ? "LINE" : "COLUMN";
    if(jsonParam.reportDynamicParam.column.length > 0){//自己刷新报表图形
        getAjaxResultLee(document.body.jsLee.requestUrl.path14,"POST",jsonParam,"initChartCallBack(data)",function(){
            $("#ajaxWaiting").show();
        },function () {
            $("#ajaxWaiting").hide();
        });
    }else{
        $("#container").parent().html('<div id="container" style="min-width:400px;height:400px;width: 100%;display: none"></div>');
    }
    /*if(!$("#rowColTranslate").attr("clickMark")){
        var jsonParam = document.body.jsLee.jsonAll;
        jsonParam.chartOptions = deepCopy(document.body.jsLee.chartOptions);
        jsonParam.chartOptions.chartType = $("input[name=tableChart]:first").is(":checked") ? "BAR" : "PIE";
        jsonParam.chartOptions.positionType = $("#wayShowType option:selected").val() == 0 ? "LINE" : "COLUMN";
        if(jsonParam.reportDynamicParam.column.length > 0){//自己刷新报表图形
            getAjaxResultLee(document.body.jsLee.requestUrl.path14,"POST",jsonParam,"initChartCallBack(data)");
        }
    }else{
        var jsonParam = deepCopy(document.body.jsLee.jsonAll);
        var rowJson = deepCopy(document.body.jsLee.jsonAll.reportDynamicParam.line);
        var colJson = deepCopy(document.body.jsLee.jsonAll.reportDynamicParam.column);
        jsonParam.reportDynamicParam.column = rowJson;
        jsonParam.reportDynamicParam.line = colJson;
        jsonParam.chartOptions = deepCopy(document.body.jsLee.chartOptions);
        jsonParam.chartOptions.chartType = $("input[name=tableChart]:first").is(":checked") ? "BAR" : "PIE";
        jsonParam.chartOptions.positionType = $("#wayShowType option:selected").val() == 0 ? "LINE" : "COLUMN";
        if(jsonParam.reportDynamicParam.column.length > 0){//自己刷新报表图形
            getAjaxResultLee(document.body.jsLee.requestUrl.path14,"POST",jsonParam,"initChartCallBack(data)");
        }
    }*/
}

//饼状图和柱状图回调函数
function initChartCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        $("#container").parent().html('<div id="container" style="min-width:400px;height:400px;width: 100%;display: none"></div>');
        if($("input[name=tableChart]:first").is(":checked")){//柱图
            var jsonChart = {
                chart: {
                    type: 'column'
                },
                title: {
                    text: '主题报表'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: [
                        '中国','俄罗斯','加拿大','法国','美国'
                    ],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '降雨量 (mm)'
                    }
                },
                tooltip: {
                    // head + 每个 point + footer 拼接成完整的 table
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}<!----></b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        borderWidth: 0
                    }
                },
                series: [{
                    name: '1990年',
                    data: [49.9, 71.5, 106.4, 129.2, 144.0]
                }, {
                    name: '1991年',
                    data: [83.6, 78.8, 98.5, 93.4, 106.0]
                }, {
                    name: '1992年',
                    data: [48.9, 38.8, 39.3, 41.4, 47.0]
                }, {
                    name: '1993年',
                    data: [42.4, 33.2, 34.5, 39.7, 52.6]
                }]
            }
            jsonChart.xAxis = data.rspBody.xAxis;
            jsonChart.series = data.rspBody.series;
            jsonChart.title.text = data.rspBody.title.text;
            jsonChart.yAxis.title.text = data.rspBody.yAxis.title.text;
            var containerClone = $("#container").clone(true);
            containerClone.attr("id","container1").show();
            $("#container").before(containerClone);
            var chart = Highcharts.chart('container1',jsonChart);
        }else{
            for(var nI = 0 ; nI < data.rspBody.length; nI++ ){
                var jsonChart = {
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type: 'pie'
                    },
                    title: {
                        text: ''
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                style: {
                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                }
                            }
                        }
                    },
                    series: [{
                        name: '',
                        colorByPoint: true,
                        data: [{
                            name: 'Chrome',
                            y: 61.41,
                            sliced: true,
                            selected: true
                        }, {
                            name: 'Internet Explorer',
                            y: 11.84
                        }, {
                            name: 'Firefox',
                            y: 10.85
                        }, {
                            name: 'Edge',
                            y: 4.67
                        }, {
                            name: 'Safari',
                            y: 4.18
                        }, {
                            name: 'Sogou Explorer',
                            y: 1.64
                        }, {
                            name: 'Opera',
                            y: 1.6
                        }, {
                            name: 'QQ',
                            y: 1.2
                        }, {
                            name: 'Other',
                            y: 2.61
                        }]
                    }]
                };
                jsonChart.series[0] = data.rspBody[nI];
                var containerClone = $("#container").clone(true);
                var idName = "'container"+ nI +"'";
                containerClone.attr("id",idName).show().css({"float":"left","min-width":"180px","width":"50%","height":"180px"});
                $("#container").before(containerClone);
                var chart = Highcharts.chart(idName,jsonChart);
            }

        }
    }
}

//操作后改变拖拽table或者highChart
function initTableOrChart(){
    if($(".formcontainer-mainover").eq(0).is(":visible")){
        initTable();
    }else{
        initChart();
    }
}

//公用方法
function getAjaxResultLee(strPath, method, param, callbackMethod, beforeSendFunc,complete , asyncType) {
    var strPath = (requestUrl == null) ? strPath : requestUrl + strPath;
    var operId = (param.operId == null) ? "" : param.operId;
    jsonReqHeaderData.operTitle = operId;
    var reqParam = {"reqHeader": jsonReqHeaderData};
    reqParam["reqBody"] = param;
    asyncType = asyncType || false;
    $.ajax({
        url: strPath,
        type: method,
        async: asyncType,
        cache: false,
        data: JSON.stringify(reqParam),
        dataType: 'text',
        contentType: 'application/json',
        beforeSend: beforeSendFunc ? beforeSendFunc : function () {
        },
        success: function (data) {
            if (typeof(callbackMethod) == "string") {
                eval(callbackMethod);
            } else if (typeof(callbackMethod) == "function") {
                callbackMethod(data);
            }
            var jsonResultData = JSON.parse(data);
            jumpUrl(null, jsonResultData.retCode, null, jsonResultData);
        },
        complete: complete ? complete :function(){

        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});
