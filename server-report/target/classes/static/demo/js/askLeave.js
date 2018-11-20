var lockStatus = false;//false页面未进行修改  true 页面已经进行修改
$(function(){
    var arrA = [{"initA":"initA","initB":"initB","initC":"initC","childArr":[{"intA":"inA","inB":"inB","inC":"inC"}]},{"initA":"initA2","initB":"initB2","initC":"initC2","childArr":[{"intA":"inA","inB":"inB","inC":"inC"}]}];
    //初始化页面表格
    initplugData($("#tableSecond")[0],"parentChildTableCtrl",arrA);


    //保存操作
    $(".button-ssign").on("click",function(){
        var boxAlert=new clsAlertBoxCtrl();
        boxAlert.Alert("如需加班实际提报，请点击提报按钮，完成实际提报。当前操作仅为保存！","保存信息提示",1,"","saveId")
    });
    //删除操作
    $(".button-signb").on("click",function(){
        var boxAlert=new clsAlertBoxCtrl();
        var deleteOnly = true;
        if(deleteOnly){
            boxAlert.Alert("是否确认删除此信息？","删除提示",1,"","deleteIdYes");
        }else{
            boxAlert.Alert("此状态不允许删除，仅驳回状态可以！","删除异常提示",1,"","deleteIdNo")
        }

    });
    //加班实际
    $(".button-check").on("click",function(){
        alert("加班实际操作中");
    });
    //请假计划
    $("#askLeaveBtn").on("click",function(){
        //jsonItem={"rspBody":{"resultData":[]}};
        jsonItem.rspBody.resultData=[{"startDt":"","endDt":"","handleBy":"","leaveReason":""}];
        clearVal()
        openWin(1058,600,"askLeaveFPId",true);
        
        document.body.jsCtrl.ctrl=$("#templateParent")[0]
        document.body.jsCtrl.init();
        initValidate($("#contentPopub")[0]);

    });
    //加班计划
    $(".button-signi").on("click",function(){
        var paramSigni = {};
        openWin(850,500,"messageWork",true);
        initValidate($("#contentPopub")[0]);
    });
    //模糊搜索操作
    $("#fuzzySearch").on("click",function(){
        alert("模糊搜索进行中");
        var param = {};
        param.globalParam = $("#fuzzySearchVal").val();
        param.limit = 10;
        param.page = 1;
        document.body.jsCtrl.ctrl = $("#tableSecond")[0];
        document.body.init();
    });
    //高级搜索
    $(".searchHigh").on("click",function(){
        openWin(400,700,"searchHighMes",true);
    });
    //高级搜索弹框内按钮操作
    $(".searchSure").on("click",function(){
        //确认搜索
        closePopupWin();
        alert("高级搜索确认中");
    });
    $(".searchReset").on("click",function(){
        //重置搜索
        $(".selectList-list").each(function(){
            $(this).find("option:first").attr("selected","selected");
        });
    });
    $(".searchCancel").on("click",function(){
        //取消搜索
        closePopupWin();
    });

    //加班计划弹框操作
    limitWordNumber("#leftLast",0,300);
    initValidate($("#contentPopub")[0]);
    $(".btn-popup-sure").on("click",function(){
        if(!valiClass.validateAll4Ctrl("#contentPopub")){
            return;
        }
        if($("#rightUlInput input[type=checkbox]:checked").length<=0){
            showErrInfoByCustomC($("#rightUlInput"),"选择人数")
            return;
        }
        closePopupWin();
        var boxAlert=new clsAlertBoxCtrl();
        boxAlert.Alert("是否确认提交此加班计划？","提报加班计划",1,"","confirmId")
    })
    $(".btn-popup-cancel").on("click",function(){
        closePopupWin();
    });
    //子集添加加班类型行
    $("span[id=holidayCreate]").on("click",function(){
        var cloneList = $(this).parents("#childContent").find("#templateChildRow").clone(true);
        cloneList.attr("id","cloneChildRow").show();
        $(this).parents("#childContent").find("#templateChildRow").before(cloneList);
        sortChild();
    });
    //子集删除加班类型行
    $("span[id=holidayRest]").on("click",function(){
        $(this).parents("#cloneChildRow").remove();
        sortChild();
    });
    //页面是否修改
    $("span[id=lookChild]").on("click",function(){
        lockStatus = true;
    });
});

//已有数组，初始化插件;
function initplugData(docm,comType,data){
    $(docm).attr("comType",comType);
    docm.data = {"rspBody":{"resultData":data}};
    document.body.jsCtrl.ctrl = docm;
    document.body.jsCtrl.init();
}

function clsParentChildTableCtrl$progress(jsonItem, cloneRow) {
    $(cloneRow).find("#lookChild").on("click",function(){
        if($(this).attr("attrMark") == 0){
            $(this).attr("attrMark",1);
            $(cloneRow).find("#childContent").show();
            $(this).html("-");
        }else{
            $(this).attr("attrMark",0);
            $(cloneRow).find("#childContent").hide();
            $(this).html("+");
        };

    });
}
function clsParentChildTableCtrl$childProgress(jsonItem, cloneRow) {
    /*$(cloneRow).find("#holidayRest").on("click",function(){
        $(this).parents("#cloneChildRow").remove();
        sortChild();
    });*/
}
function clsParentChildTableCtrl$after() {
    sortChild();
}

var valiClass=new clsValidateCtrl();
function showErrInfoByCustomC(elem,error,lev)
{
    if(lev){
        $(elem).poshytip({
            className: 'tip-twitter',
            showOn: 'none',
            alignTo: 'target',
            alignX: 'bottom',
            alignY: 'inner-center',
            content:error,
            offsetX: -110,
            offsetY:10,
            autoHide:true,
            hideTimeout:2000
        });
    }else{
        $(elem).poshytip({
            className: 'tip-twitter',
            showOn: 'none',
            alignTo: 'target',
            alignX: 'left',
            alignY: 'inner-right',
            content:error,
            offsetX: 5,
            offsetY:-150,
            autoHide:true,
            hideTimeout:2000
        });
    }
    $(elem).poshytip('hide');
    $(elem).poshytip('show');
}
//加班计划填写成功
function clsAlertBoxCtrl$sure() {
    if(this.id == "confirmId"){
        //计划加班提示
        alert("计划加班操作中");
    }else if(this.id == "saveId"){
        //保存提示
        alert("保存成功操作中");
    }else if(this.id == "deleteIdNo"){
        //删除异常提示
    }else if(this.id == "deleteIdYes"){
        //删除成功提示
        alert("删除成功操作中");
    }else if(this.id =="submitAsk"){
        //调用ajax 把数据传输过去
        //
        //$("#endData").val("");
        //ajax 提交的数据
        var jsonItemS=
        {
        "applyBy":"测试6",
        "startDt":$("#startData").val(),
        "endDt":$("#endData").val(),
        "leaveDays":$("#askForLDate").val(),
        "leaveReason":$("#askReason").val(),
        "handleBy":$("#theAgent").val(),
        "entityLst":[]
        }

        for(var i=0;i<$("#templateParent #cloneRow").length;i++){
            jsonItemS.entityLst.push({
            "recordType":$("#templateParent #cloneRow").eq(i).find("#recordType").val(),
            "startDt":$("#templateParent #cloneRow").eq(i).find("#startDt").val(),
            "endDt":$("#templateParent #cloneRow").eq(i).find("#startDt").val(),
            "days":5//这是个死数据  具体数据等确定好是按具体时间去计算的还是其他的
            })
            
        }
        alert("提交请假成功")
    }
}
//子集序号排序
function sortChild(){
    $("div[id=childContent]").each(function(){
        $(this).find("li[id=childIndex]").each(function(index){
            $(this).html(index + 1);
        });
    });

}
