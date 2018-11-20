
//得到维度数据
$(document).ready(function(){
    var obj = new clsRptCtrl();
    obj.ctrl= $("#rptList")[0];
    document.body.jsRptCtrl = obj;
    //obj.init();

    //getDimensionData();
});





$(function(){

    //出入允许拖拽节点的父容器，一般是ul外层的容器
    //drag.init('container');
    //$( "#rightLeft" ).sortable();
    //$( "#rightLeft" ).disableSelection();


});
$(".right-bottom").delegate(".arrow","click",function(){
    openWin(500,400,"askLeaveFPId",true);
});
$(".btn-popup-cancel").on("click",function(){
    closePopupWin();
})
$("#submitAsk").on("click",function(){
    closePopupWin();
})

function clsRptCtrl()
{
    this.ctrl		= null;
    this.jsonData	= null;
    this.cloneTd	= clsRptCtrl$cloneTd;
    this.cloneRow	= clsRptCtrl$cloneRow;
    this.init		= clsRptCtrl$init;
    this.parse		= clsRptCtrl$parse;
    this.parseData	= clsRptCtrl$parseData;
    this.parseTitle	= clsRptCtrl$parseTitle;
    this.parseTitleAfter	= clsRptCtrl$parseTitleAfter;
    this.getLastRow = clsRptCtrl$getLastRow;
    this.getDimensionData			= clsRptCtrl$getDimensionData;
    this.getDimensionDataCallBack	= clsRptCtrl$getDimensionDataCallBack;
    this.initLayout					= clsRptCtrl$initLayout;
    this.refresh	= clsRptCtrl$refresh;
}

function clsRptCtrl$init()
{
    this.getDimensionData();
    //if(this.jsonData.code == "000000")
    //	this.parse();
}

function clsRptCtrl$parse()
{
    $(this.ctrl).html("");
    this.parseTitle();
    this.parseData();

}

function clsRptCtrl$parseData()
{
    if(this.jsonData.axisxData.length == 0)
    {
        var oRow = this.cloneRow();
        $(oRow).attr("rowType","data");
        oRow.jsonData = this.jsonData.cellList;
        for(var nI=0; nI<this.jsonData.cellList.length; nI++)
        {
            var jsonCellData= this.jsonData.cellList[nI];
            var oCell		= this.cloneTd(1);
            $(oCell).text(jsonCellData);
            oRow.appendChild(oCell);
        }
        this.ctrl.appendChild(oRow);
    }
    else
    {
        for(var nI=0; nI<this.jsonData.axisxData.length; nI++)
        {
            var jsonRowData = this.jsonData.axisxData[nI];
            var oRow = this.cloneRow();
            $(oRow).attr("rowType","data");
            oRow.jsonData = jsonRowData;
            var jsonTabData = [];
            oRow.tabData = jsonTabData;
            for(var mI=0; mI<jsonRowData.length; mI++)
            {
                var jsonCellData= jsonRowData[mI];
                var lastRow = this.getLastRow();
                if(lastRow != null)
                {
                    if(lastRow.tabData[mI] == 0 || lastRow.tabData[mI] == null)
                    {
                        var oCell		= this.cloneTd(1);
                        $(oCell).text(jsonCellData.name);
                        oCell.rowSpan	= jsonCellData.length;
                        if(jsonCellData.val != null && jsonCellData.lev > 1)
                            oCell.colSpan = jsonCellData.lev;
                        jsonTabData.push(parseInt(jsonCellData.length)-1);
                        oRow.appendChild(oCell);
                    }
                    else
                    {
                        jsonTabData.push(lastRow.tabData[mI]-1);
                    }
                }
                else
                {
                    var oCell		= this.cloneTd(1);
                    $(oCell).text(jsonCellData.name);
                    oCell.rowSpan	= jsonCellData.length;
                    if(jsonCellData.val != null && jsonCellData.lev > 1)
                        oCell.colSpan = jsonCellData.lev;
                    jsonTabData.push(parseInt(jsonCellData.length)-1);
                    oRow.appendChild(oCell);
                }
                if(jsonCellData.val != null)
                {
                    for(key in jsonCellData.val)
                    {
                        var oCell = this.cloneTd(0);
                        $(oCell).text(jsonCellData.val[key]);
                        oRow.appendChild(oCell);
                    }
                }
            }
            this.ctrl.appendChild(oRow);
        }
    }
}

function clsRptCtrl$parseTitle()
{
    if(this.jsonData.axisxData.length > 0)
    {
        var oRow = this.cloneRow(1);
        $(oRow).attr("rowType","title");
        for(var nI=0; nI<this.jsonData.axisxData[0].length; nI++)
        {
            var oCell = this.cloneTd(1);
            oCell.jsonData = this.jsonData.axisxData[0][nI];
            $(oCell).text(this.jsonData.axisxData[0][nI].type);
            oRow.appendChild(oCell);
            if(this.jsonData.axisyData.length == 0)
            {
                var nLen = this.jsonData.axisxData[0][this.jsonData.axisxData[0].length-1].val.length;
                for(var mI=0; mI<nLen; mI++)
                {
                    var oCell = this.cloneTd(1);
                    oCell.jsonData = null;
                    $(oCell).html("&nbsp;")
                    oRow.appendChild(oCell);
                }
            }
            else
            {
                oCell.rowSpan = this.jsonData.axisyData[0][0].lev;
            }
        }
        this.ctrl.appendChild(oRow);
        for(var nI=0; nI<this.jsonData.axisyData.length; nI++)
        {
            var jsonRowData = this.jsonData.axisyData[nI];
            if(nI > 0)
            {
                var oRow = this.cloneRow(1);
                $(oRow).attr("rowType","title");
            }
            for(var mI=0; mI<jsonRowData.length; mI++)
            {
                var oCell		= this.cloneTd(1);
                oCell.jsonData	= jsonRowData[mI];
                oCell.colSpan	= jsonRowData[mI].length;
                $(oCell).text(jsonRowData[mI].name);
                if(jsonRowData[mI].subTotal == "1" && jsonRowData[mI].lev > 1)
                    oCell.rowSpan = jsonRowData[mI].lev;
                oRow.appendChild(oCell);
            }
            this.ctrl.appendChild(oRow);
        }

    }
    else
    {

        for(var nI=0; nI<this.jsonData.axisyData.length; nI++)
        {
            var jsonRowData = this.jsonData.axisyData[nI];
            var oRow = this.cloneRow(1);
            $(oRow).attr("rowType","title");
            for(var mI=0; mI<jsonRowData.length; mI++)
            {
                var oCell		= this.cloneTd(1);
                oCell.jsonData	= jsonRowData[mI];
                oCell.colSpan	= jsonRowData[mI].length;
                $(oCell).text(jsonRowData[mI].name);
                if(jsonRowData[mI].subTotal == "1" && jsonRowData[mI].lev > 1)
                    oCell.rowSpan = jsonRowData[mI].lev;
                oRow.appendChild(oCell);
            }
            this.ctrl.appendChild(oRow);
        }
    }
    this.parseTitleAfter();
}

function clsRptCtrl$parseTitleAfter(){
    var numTh = $(this.ctrl).find("tr[rowtype=title] td").length;
    $(this.ctrl).css("width",(100 * numTh) + "px");
}

function clsRptCtrl$cloneTd(type)
{
    //type:0表示数据单元格 1表示标题单元格
    var cloneTd = document.createElement("td");
    if(!type){//0
        $(cloneTd).addClass("formcontainer-table__td");
    }else{
        $(cloneTd).addClass("formcontainer-table__thead");
    }
    return cloneTd;
}

function clsRptCtrl$cloneRow(type)
{
    var cloneRow = document.createElement("tr");
    //type:0表示数据单元行 1表示标题单元行
    return cloneRow;
}

function clsRptCtrl$getLastRow()
{
    if(this.ctrl != null)
    {
        return $(this.ctrl).find("tr[rowType='data']:last")[0];
    }
}

function clsRptCtrl$getDimensionData()
{
    //var reqParam = {"personalSubjectId":1};
    //getAjaxResult("/report/getReportForm","POST",reqParam,"initCallBack(data)");
    /*$.ajax({
        url: "/report/getReportForm",
        type: "POST",
        async: false,
        cache: false,
        data: JSON.stringify(reqParam),
        dataType: 'text',
        contentType: 'application/json',
        success: function (data) {
            document.body.jsRptCtrl.getDimensionDataCallBack(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });*/

    //getAjaxResult("report/dimension","POST",jsonParam,"getDimensionDataCallBack(data)");
}

function initCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        document.body.jsRptCtrl.getDimensionDataCallBack(data);
    }
}

function clsRptCtrl$refresh(reqParam)
{
    /*$.ajax({
        url:"/report/demo",
        dataType:"text",
        data:JSON.stringify(reqParam),
        type:"post",
        contentType:"application/json",
        success:function(data){
            var jsonData = JSON.parse(data);
            if(jsonData.code == "000000")
            {
                document.body.jsRptCtrl.jsonData = jsonData;
                document.body.jsRptCtrl.parse();
            }
        }
    });*/
}


function clsRptCtrl$getDimensionDataCallBack(data)
{
    var jsonData = JSON.parse(data);
    $(".selDimensionContent")[0].jsonData = jsonData;
    $(".selDimensionContent").html("");
    for(var nI=0; nI<jsonData.dimensionList.length; nI++)
    {
        $(".selDimensionContent").append("<li><span class='content'>"+ jsonData.dimensionList[nI].name +"</span><input type='checkbox'><i></i></li>");
        $(".selDimensionContent").find(".content")[$(".selDimensionContent").find(".content").length-1].setAttribute("jsonData",JSON.stringify(jsonData.dimensionList[nI]));
    }

    this.initLayout();
}


function clsRptCtrl$initLayout()
{
    //$( ".selDimensionCols, .selDimensionRows, .selDimensionSearch, .selDimensionContent" ).sortable({
    $( ".selDimensionCols, .selDimensionRows, .selDimensionContent" ).sortable({
        connectWith: ".connectedSortable",
        helper: "clone",
        stop: function(event,ui){
            //重置缓存数组
            if(ui.item.parents(".selDimensionSearch").length == 1 && ui.item[0].jsonData.combinationName == "Measures"){
                return false;

                /*var cloneDom = ui.item.clone(true);
                cloneDom[0].jsonData = JSON.parse(cloneDom.find(".content").attr("jsonData"));

                $(".selDimensionContent").append(cloneDom);
                ui.item.remove();*/
            }
            document.body.jsRptCtrl.jsonDataJoin = {"column":[],"line":[],"tbs":[],"filter":[]};
            var reqParam = {"schema":"movie_data_10w","tableName":"","line":[],"column":[],"total":false,"measures":[]};
            var blnMeasure = false;
            //var this.jsonDataJoin = {"column":[],"line":[],"tbs":[],"filter":[]};
            for(var i=0;i<$(".selDimensionRows li").length;i++)
            {
                //编辑按钮设置可用   小计按钮
                $(".selDimensionRows li").eq(i).find("#fieldNameEdit").show();
                $(".selDimensionRows li").eq(i).find("#fieldNameEdit").unbind();
                $(".selDimensionRows li").eq(i).find("#fieldNameEdit").bind("click",function(){
                    editOpeRow(this);
                });
                //小计按钮显示
                $(".selDimensionRows li").eq(i).find("#subtotalBox").show();
                if(ui.item[0].jsonData.subTotal){
                    $(".selDimensionRows li").eq(i).find("#subtotal").addClass("activeOpe");
                }else{
                    $(".selDimensionRows li").eq(i).find("#subtotal").removeClass("activeOpe");
                }
                $(".selDimensionRows li").eq(i).find("#subtotal").unbind();
                $(".selDimensionRows li").eq(i).find("#subtotal").bind("click",function(){
                    subTotalIsTrue(this);
                });
                //设置搜索条件
                if($(".selDimensionRows li")[i].jsonData.combinationName != "Measures"){
                    $(".selDimensionRows li").eq(i).find("#searchSetBox").show();
                }
                if($(".selDimensionRows li")[i].jsonData.searchTrue){
                    $(".selDimensionRows li").eq(i).find("#searchSet").addClass("activeOpe");
                }
                $(".selDimensionRows li").eq(i).find("#searchSet").unbind();
                $(".selDimensionRows li").eq(i).find("#searchSet").bind("click",function(){
                    searchSetTrue(this);
                });



                $(".selDimensionRows li").eq(i).find(".content")[0].jsonData = JSON.parse($(".selDimensionRows li").eq(i).find(".content").attr("jsonData"));

               /* $(".selDimensionRows li").find("input").unbind("onclick");
                $(".selDimensionRows li").find("input").click(function(){
                    $(this).parents("li:first").find(".content")[0].jsonData.subTotal = that.checked;
                    reqParam = $(".selDimensionRows")[0].reqParam;
                    if(reqParam.column.length == 0)return;
                    document.body.jsRptCtrl.refresh(reqParam);
                });*/
                //if(!table.letData.contains(reqParam.line,$(".selDimensionRows li").eq(i).find(".content").html())){
                $(".selDimensionRows li").eq(i).find(".content")[0].jsonData.subTotal = $(".selDimensionRows li").eq(i).find("input").prop("checked");
                reqParam.line.push($(".selDimensionRows li").eq(i).find(".content")[0].jsonData);
                //}
                if($(".selDimensionRows li").eq(i).find(".content")[0].jsonData.code == "measures")
                    blnMeasure = true;

            }
            for(var i=0;i<$(".selDimensionCols li").length;i++)
            {
                //编辑按钮设置可用  小计按钮
                $(".selDimensionCols li").eq(i).find("#fieldNameEdit").show();
                $(".selDimensionCols li").eq(i).find("#fieldNameEdit").unbind();
                $(".selDimensionCols li").eq(i).find("#fieldNameEdit").bind("click",function(){
                    editOpeCol(this);
                });
                //小计操作
                $(".selDimensionCols li").eq(i).find("#subtotalBox").show();
                if(ui.item[0].jsonData.subTotal){
                    $(".selDimensionCols li").eq(i).find("#subtotal").addClass("activeOpe");
                }else{
                    $(".selDimensionCols li").eq(i).find("#subtotal").removeClass("activeOpe");
                }
                $(".selDimensionCols li").eq(i).find("#subtotal").unbind();
                $(".selDimensionCols li").eq(i).find("#subtotal").bind("click",function(){
                    subTotalIsTrue(this);
                });
                //设置搜索条件
                if($(".selDimensionCols li").eq(i).find("#fieldNameEdit") != "Measures"){
                    $(".selDimensionCols li").eq(i).find("#searchSetBox").show();
                }else{
                    $(".selDimensionCols li").eq(i).find("#searchSetBox").hide();
                }
                if($(".selDimensionCols li")[i].jsonData.searchTrue){
                    $(".selDimensionCols li").eq(i).find("#searchSet").addClass("activeOpe");
                }
                $(".selDimensionCols li").eq(i).find("#searchSet").unbind();
                $(".selDimensionCols li").eq(i).find("#searchSet").bind("click",function(){
                    searchSetTrue(this);

                });


                $(".selDimensionCols li").eq(i).find(".content")[0].jsonData = JSON.parse($(".selDimensionCols li").eq(i).find(".content").attr("jsonData"));


               /* $(".selDimensionCols li").find("input").click(function(){
                    $(this).parents("li:first").find(".content")[0].jsonData.subTotal = this.checked;
                    reqParam = $(".selDimensionRows")[0].reqParam;
                    if(reqParam.column.length == 0)return;
                    document.body.jsRptCtrl.refresh(reqParam);
                });*/

                //暂时
                //if(i == 0)
                //	$(".selDimensionCols li").eq(i).find(".content")[0].jsonData.subTotal = true;

                //if(!table.letData.contains(reqParam.column,$(".selDimensionCols li").eq(i).find(".content").html())){
                $(".selDimensionCols li").eq(i).find(".content")[0].jsonData.subTotal = $(".selDimensionCols li").eq(i).find("input").prop("checked");
                reqParam.column.push($(".selDimensionCols li").eq(i).find(".content")[0].jsonData);
                //}

                if($(".selDimensionCols li").eq(i).find(".content")[0].jsonData.code == "measures")
                    blnMeasure = true;

            }
            /*for(var i=0;i<$(".selDimensionSearch li").length;i++)
            {
                //设置初始jsonData
                if(!$(".selDimensionSearch li")[i].jsonDataA){
                    $(".selDimensionSearch li")[i].jsonDataA = {
                        "code":$(".selDimensionSearch li")[i].jsonData.fieldCode,
                        "name":$(".selDimensionSearch li")[i].jsonData.fieldName,
                        "dbFieldTypeEnum":$(".selDimensionSearch li")[i].jsonData.fieldType,
                        "tmp":[
                            {fieldVal:{"RG":""},"isChecked":1},
                            {fieldVal:{"EQ":""},"isChecked":0},
                            {fieldVal:{"GT":"","LT":""},"isChecked":0}]
                    };
                }
                //编辑按钮设置可用  小计隐藏
                $(".selDimensionSearch li").eq(i).find("#subtotalBox").hide();

                $(".selDimensionSearch li").eq(i).find("#fieldNameEdit").show();
                $(".selDimensionSearch li").eq(i).find("#fieldNameEdit").unbind();
                $(".selDimensionSearch li").eq(i).find("#fieldNameEdit").bind("click",function(){
                    editOpeSearch(this);
                });

                //复现搜索条件



                $(".selDimensionSearch li").eq(i).find(".content")[0].jsonData = JSON.parse($(".selDimensionSearch li").eq(i).find(".content").attr("jsonData"));


                $(".selDimensionSearch li").find("input").click(function(){
                    $(this).parents("li:first").find(".content")[0].jsonData.subTotal = this.checked;
                    reqParam = $(".selDimensionRows")[0].reqParam;
                    if(reqParam.column.length == 0)return;
                    document.body.jsRptCtrl.refresh(reqParam);
                });

                //暂时
                //if(i == 0)
                //	$(".selDimensionSearch li").eq(i).find(".content")[0].jsonData.subTotal = true;

                //if(!table.letData.contains(reqParam.column,$(".selDimensionSearch li").eq(i).find(".content").html())){
                $(".selDimensionSearch li").eq(i).find(".content")[0].jsonData.subTotal = $(".selDimensionSearch li").eq(i).find("input").prop("checked");
                reqParam.column.push($(".selDimensionSearch li").eq(i).find(".content")[0].jsonData);
                //}

                if($(".selDimensionSearch li").eq(i).find(".content")[0].jsonData.code == "measures")
                    blnMeasure = true;

            }*/

            for(var i = 0; i < $(".selDimensionContent li").length; i++ ){
                //编辑按钮设置可用
                $(".selDimensionContent li").eq(i).find("#fieldNameEdit").hide();
                $(".selDimensionContent li").eq(i).find("#subtotalBox").hide();
                $(".selDimensionContent li").eq(i).find("#searchSetBox").hide();
            }

            //缓存的数据放进保存接口入参json中
            rightJsonJoin();
            if(blnMeasure)
                reqParam.measures = $(".selDimensionContent")[0].jsonData.measures;
            if(reqParam.column.length == 0)return;
            $(".selDimensionRows")[0].reqParam = reqParam;
            //table.init("/report/demo",reqParam);

            document.body.jsRptCtrl.refresh(reqParam);

            if($(event.toElement).parent().hasClass("selDimensionRows")||$(event.toElement).parent().attr("id")=="selDimensionCols" || $(event.toElement).parent().attr("id")=="selDimensionSearch"){
                if(!$(event.toElement).find(".arrow").length){
                    $($(event.toElement)).append("<span class='arrow'>箭头</span>");
                }
                if(!$(event.toElement).find(".close").length){
                    $($(event.toElement)).append("<span class='close'>关闭</span>");
                }


            }else if($(event.toElement).parent().hasClass("selDimensionContent")){
                if($(event.toElement).find(".arrow").length){
                    $($(event.toElement)).find(".arrow").remove();
                }
                if($(event.toElement).find(".close").length){
                    $($(event.toElement)).find(".close").remove();;
                }
            }
        }
    }).disableSelection();

    $(".right-bottom").delegate(".close","click",function(){
        $(".selDimensionContent").append("<li><span class='content'>"+$(this).parent().find(".content").text()+"</span></li>");
        $(this).parent().remove();

    })


}
/*计算公式*/
function clsFormula()
{
    this.waitDimensionBoxId = "waitDimensionBox";
    this.symbolBoxId = "symbolBox";
    this.formulaId= "formulaContent";
    this.currentCtrl = null;
    this.init = clsFormula$init;
    this.save = clsFormula$save;
    this.parse= clsFormula$parse;
}

function clsFormula$init(jsonData,jsonDataParse)//jsonData 初始化待选维度或者指标   jsonDataParse  初始化公式
{
    //var jsonData = [{"fieldCode":"a.name","measuresName":"姓名"},{"fieldCode":"a.age","measuresName":"年龄"}];
    var jsonSymbolData = [{"fieldCode":"+","measuresName":"+","operators":1},{"fieldCode":"-","measuresName":"-","operators":1},{"fieldCode":"*","measuresName":"*","operators":1},{"fieldCode":"/","measuresName":"/","operators":1},{"fieldCode":"(","measuresName":"(","operators":1},{"fieldCode":")","measuresName":")","operators":1},{"fieldCode":"del","measuresName":"←"}];
    $("#"+this.waitDimensionBoxId).html("");
    for(var nI=0; nI<jsonData.length; nI++)
    {
        var jsonItem= jsonData[nI];
        //<li class="userNewPopup-ulist-list-nameWait fl">哈哈</li>
        var oInput	= document.createElement("li");
        $(oInput).addClass("userNewPopup-ulist-list-nameWait fl");
        //oInput.type	= "button";
        $(oInput).html(jsonItem.measuresName);
        oInput.jsonData	= jsonItem;
        $("#"+this.waitDimensionBoxId)[0].appendChild(oInput);
        oInput.jsCtrl = this;
        $(oInput).click(function(){
            var oCtrl		= document.createElement("span");
            oCtrl.innerHTML = this.jsonData.measuresName;
            oCtrl.jsonData	= this.jsonData;
            if(this.jsCtrl.currentCtrl != null)
                this.jsCtrl.currentCtrl.appendChild(oCtrl)
            else
                $("#"+this.jsCtrl.formulaId)[0].appendChild(oCtrl);

        });
    }
    $("#"+this.symbolBoxId).html("");
    for(var nI=0; nI<jsonSymbolData.length; nI++)
    {
        var jsonItem= jsonSymbolData[nI];
        var oInput	= document.createElement("span");
        //<span class="userNewPopup-ulist-list-calculate fl">+</span>
        //oInput.type	= "button";
        $(oInput).addClass("userNewPopup-ulist-list-calculate fl");
        //oInput.type	= "button";
        $(oInput).html(jsonItem.measuresName);
       // oInput.value= jsonItem.measuresName;
        oInput.jsonData	= jsonItem;
        $("#"+this.symbolBoxId)[0].appendChild(oInput);
        oInput.jsCtrl = this;
        $(oInput).click(function(){
            switch(this.jsonData.fieldCode)
            {
                case "(":

                    var oCtrl		= document.createElement("span");
                    oCtrl.innerHTML = this.jsonData.measuresName;
                    oCtrl.jsonData	= this.jsonData;

                    var oParentCtrl = document.createElement("span");
                    oParentCtrl.appendChild(oCtrl);
                    if(this.jsCtrl.currentCtrl != null)
                        $(this.jsCtrl.currentCtrl)[0].appendChild(oParentCtrl);
                    else
                        $("#"+this.jsCtrl.formulaId)[0].appendChild(oParentCtrl);
                    this.jsCtrl.currentCtrl = oParentCtrl;
                    break;
                case ")":
                    var oCtrl		= document.createElement("span");
                    oCtrl.innerHTML = this.jsonData.measuresName;
                    oCtrl.jsonData	= this.jsonData;

                    //if(this.jsCtrl.currentCtrl != null)
                    $(this.jsCtrl.currentCtrl)[0].appendChild(oCtrl);
                    //else
                    //	$("#"+this.jsCtrl.formulaId)[0].appendChild(oCtrl);
                    if($(this.jsCtrl.currentCtrl)[0].parentNode == $("#"+this.jsCtrl.formulaId)[0])
                        this.jsCtrl.currentCtrl = null;
                    else
                        this.jsCtrl.currentCtrl = $(this.jsCtrl.currentCtrl)[0].parentNode;
                    break;
                case "del":
                    if(this.jsCtrl.currentCtrl != null)
                    {
                        if(this.jsCtrl.currentCtrl.parentNode == $("#"+this.jsCtrl.formulaId)[0])
                        {
                            this.jsCtrl.currentCtrl.parentNode.removeChild(this.jsCtrl.currentCtrl);
                            this.jsCtrl.currentCtrl = null;
                        }
                        else
                        {
                            var parentNode = this.jsCtrl.currentCtrl.parentNode;
                            this.jsCtrl.currentCtrl.parentNode.removeChild(this.jsCtrl.currentCtrl);
                            this.jsCtrl.currentCtrl = this.jsCtrl.currentCtrl.parentNode;
                        }
                    }
                    else
                    {
                        $('#'+this.jsCtrl.formulaId+'>span:last')[0].parentNode.removeChild($('#'+this.jsCtrl.formulaId+'>span:last')[0]);
                    }
                    break;
                default:
                    var oCtrl		= document.createElement("span");
                    oCtrl.innerHTML = this.jsonData.measuresName;
                    oCtrl.jsonData	= this.jsonData;
                    if(this.jsCtrl.currentCtrl != null)
                        this.jsCtrl.currentCtrl.appendChild(oCtrl)
                    else
                        $("#"+this.jsCtrl.formulaId)[0].appendChild(oCtrl);
                    break;
            }
        });
    }

    this.parse(jsonDataParse);
}

//解析json生成公式
function clsFormula$parse(jsonData)
{
    //var jsonData = {"expressionEnglish":"(a.name+a.age)-(a.age+a.name)","expressionChinese":[{"fieldCode":"(","measuresName":"("},{"fieldCode":"a.name","measuresName":"姓名"},{"fieldCode":"+","measuresName":"+"},{"fieldCode":"a.age","measuresName":"年龄"},{"fieldCode":")","measuresName":")"},{"fieldCode":"-","measuresName":"-"},{"fieldCode":"(","measuresName":"("},{"fieldCode":"a.age","measuresName":"年龄"},{"fieldCode":"+","measuresName":"+"},{"fieldCode":"a.name","measuresName":"姓名"},{"fieldCode":")","measuresName":")"}]};
    if(jsonData){
        $("#"+this.formulaId).html("");
        var jsonDataExpressionChinese = JSON.parse(jsonData.expressionChinese);
        for(var nI=0; nI<jsonDataExpressionChinese.length; nI++)
        {
            var jsonItem = jsonDataExpressionChinese[nI];
            switch(jsonItem.fieldCode)
            {
                case "(":
                    var oCtrl		= document.createElement("span");
                    oCtrl.innerHTML = jsonItem.measuresName;
                    oCtrl.jsonData	= jsonItem;

                    var oParentCtrl = document.createElement("span");
                    oParentCtrl.appendChild(oCtrl);
                    if(this.currentCtrl != null)
                        $(this.currentCtrl)[0].appendChild(oParentCtrl);
                    else
                        $("#"+this.formulaId)[0].appendChild(oParentCtrl);
                    this.currentCtrl = oParentCtrl;
                    break;
                case ")":
                    var oCtrl		= document.createElement("span");
                    oCtrl.innerHTML = jsonItem.measuresName;
                    oCtrl.jsonData	= jsonItem;
                    $(this.currentCtrl)[0].appendChild(oCtrl);
                    if($(this.currentCtrl)[0].parentNode == $("#"+this.formulaId)[0])
                        this.currentCtrl = null;
                    else
                        this.currentCtrl = $(this.currentCtrl)[0].parentNode;
                    break;
                default:
                    var oCtrl		= document.createElement("span");
                    oCtrl.innerHTML = jsonItem.measuresName;
                    oCtrl.jsonData	= jsonItem;
                    if(this.currentCtrl != null)
                        this.currentCtrl.appendChild(oCtrl)
                    else
                        $("#"+this.formulaId)[0].appendChild(oCtrl);

                    break;
            }
        }
    }
}

function clsFormula$save()
{
    var expressionEnglish = "";
    var expressionChinese = [];
    var customIndexTmp = [];
    $("#"+this.formulaId).find("span").each(function(){
        if(this.jsonData != null)
        {
            if(this.jsonData.operators != 1){
                expressionEnglish = (expressionEnglish == "") ? "#M" + this.jsonData.fieldId + "_" + this.jsonData.measureType + "#" : expressionEnglish + "#M" + this.jsonData.fieldId + "_" + this.jsonData.measureType + "#";
            }else{//运算符
                expressionEnglish = (expressionEnglish == "") ? this.jsonData.fieldCode : expressionEnglish + this.jsonData.fieldCode;
            }
            expressionChinese.push(this.jsonData);
            if(this.jsonData.operators != 1){//不是运算符
                customIndexTmp.push(this.jsonData);
            }
        }
    });
    return {"expressionEnglish":expressionEnglish,"expressionChinese":JSON.stringify(expressionChinese),"customIndexTmp":customIndexTmp};
}

$(document).ready(function(){
    var obj = new clsFormula();
    //obj.init();
    document.body.jsFCtrl = obj;
});

function onSave()
{
    //
    alert(JSON.stringify(document.body.jsFCtrl.save()));
}


