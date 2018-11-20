
var reqType = 1;//保存时传递的类型 0  角色   1  用户
var reqId = null;//保存时传递的角色或者用户 id
$(function(){
    $("#js-save").click(function(){
        var alertBox=new clsAlertBoxCtrl();
        if(!reqId){
            alertBox.Alert("请选择授权对象","提示",0,"","tipPopu");
            return false;
        }
        
        alertBox.Alert("确认授权？","提示",1,"","saveBtn");
    });
    $("#tableBox").on("click",".js-accredit",function(){
        $("*[id='cloneRow']").removeClass("seleted");
        $(this).parents("#cloneRow").addClass("seleted");
    });
    $("#switchType").change(function(){
        if($(this).val() == 0){
            $("#tableUserBox").show();
            $("#tableRoleBox").hide();
        }else{
            $("#tableUserBox").hide();
            $("#tableRoleBox").show();
        }
        //置空树形表
        var jsJson={rspBody:{resultData:[]}}
        $("#treeList1")[0].data=jsJson;
        $("#treeList1").attr("comType","treeListCtrl");
        document.body.jsCtrl.ctrl = $("#treeList1")[0];
        document.body.jsCtrl.init();
        $("*[id='cloneRow']").removeClass("seleted");
        reqId = null;//置空操作id
    })
})
function clsAlertBoxCtrl$sure() {//成功弹框确定
    if (this.id == "saveBtn") {
        closePopupWin();
        var reqObj = {
            "id":reqId,
            "type":reqType
        };
        var selectInput = $("*[level = '1']").find("input");
        var authArr = [];//选中的最末级tableId
        for(var i = 0;i < selectInput.length; i++){
            if($(selectInput[i]).is(':checked')){
                
                authArr.push({
                    "tableId":$(selectInput[i]).parents("#cloneTreeRow")[0].jsonData.tableId,
                    "tableName":$(selectInput[i]).parents("#cloneTreeRow")[0].jsonData.tableName
                });
            }
        }
        reqObj.tableIds = authArr;
        getAjaxResult("/dataAuth/keepDataAuth", "post",reqObj,function(data){
            data = JSON.parse(data);
            if(data.retCode == "0000000"){
                alert("保存成功！")
            }else{
                alert(data.retDesc);
            }
        });
    }
}
function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    var ctrlID = this.ctrl.id;//当前选然的表格id
    $(cloneRow).on("click",".js-accredit",function(){//点击授权操作
        
        var reqObj = {}
        if(ctrlID == "tableUser"){
            reqObj.acctId = jsonItem.acctId;
            reqId = jsonItem.acctId;
            reqType = 1;
        }else{
            /**
             * 角色待改
             * @type {[type]}
             */
            reqType = 0;
            reqObj.roleId = jsonItem.roleId;
            reqId = jsonItem.roleId;
        }
        getAjaxResult("/dataAuth/dataAuthTree", "post",reqObj,"treeListFun(data)");
    })
    if(this.ctrl.id == "tableRole"){
        if(jsonItem.status == 1){// status;          //0:停用;1:启用
            jsonItem.status = "启用";
        }else if(jsonItem.status == 0){
            $(cloneRow).find(".js-accredit").hide();
            jsonItem.status = "停用";
        }
    }
}
var treeListFun = function(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var jsJson={rspBody:{resultData:data.rspBody}}
        $("#treeList1")[0].data=jsJson;
        $("#treeList1").attr("comType","treeListCtrl");
        document.body.jsCtrl.ctrl = $("#treeList1")[0];
        document.body.jsCtrl.init();
    }
}

//三级树process
function clsTreeListCtrl$process(cloneRow, jsonItem,level)
{
    var clsThis = this;
    //箭头点击展开收起
    $(cloneRow).find(".colmain_icon1").click(function() {
        if($(this).hasClass("colmain_icon1")) {
            $(this).removeClass("colmain_icon1").addClass("colmain_icon01");
            var childNodes = clsThis.ctrl.jsCtrl.getAllChild(this);
            for(var nI = 0; nI < childNodes.length; nI++) {
                $(childNodes[nI]).hide();
            }
        } else {
            $(this).removeClass("colmain_icon01").addClass("colmain_icon1");
            var childNodes = clsThis.ctrl.jsCtrl.getNextChild(this);
            for(var nI = 0; nI < childNodes.length; nI++) {
                $(childNodes[nI]).show();
            }
        }
    });
    //当下面子集为空箭头隐藏
    if(!jsonItem.rptDataTables || jsonItem.rptDataTables.length == 0){
        $(cloneRow).find(".colmain_icon1").css({
            "visibility":"hidden"
        })
    }
    if(level == 1 && jsonItem.isChecked == 1){
        $(cloneRow).find(".datareport-treeListTable-tr__input").attr("checked", true);
        //clsTreeListCtrl$getParent(obj)
        $(this.getParent(cloneRow)).find(".datareport-treeListTable-tr__input").attr("checked", true);
    }
}
function clsTreeListCtrl$after() {
}
// function initBefore()
// {
//     var jsonPropData = {
//         "retCode":"0000000",
//         "retDesc":"操作成功!",
//         "timestamp":"2018-03-20 13:49:00.302",
//         "rspBody":{
//             "resultData":[{
//                 "categoryId":1,
//                 "createTime":1521094594417,
//                 "dictCategoryDTO":[{
//                     "categoryId":11,
//                     "categoryName":"类目名称11",
//                     "categoryNamePath":"一级类目",
//                     "dictCategoryDTO":[
//                         {
//                             "categoryId":111,
//                             "categoryName":"类目名称111",
//                             "categoryNamePath":"二级类目1",
                            
//                         },
//                         {
//                             "categoryId":112,
//                             "categoryName":"类目名称112",
//                             "categoryNamePath":"二级类目2",
                            
//                         }
//                     ]
//                 }],
//                 "iconUrl":"物料图标地址",
//                 "materialCode":123,
//                 "materialDesc":"物料描述",
//                 "materialId":1,
//                 "materialName":"物料A",
//                 "status":1
//             }]
//         }
//     };

//    document.getElementById("treeList1").data = jsonPropData;
// }