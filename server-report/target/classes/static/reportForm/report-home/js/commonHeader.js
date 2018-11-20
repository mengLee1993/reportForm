	var width = $(window).width();
	var height = $(window).height();
	$(function(){
		//isMine
		//模块名字截取
        var modelString = window.location.href.substring(window.location.href.indexOf("reportForm") + 11,window.location.href.indexOf("html-gulp-www") - 1);
        //页面名字截取
        var htmlString = window.location.href.indexOf("?") == -1 ? window.location.href.substring(window.location.href.indexOf("html-gulp-www") + 14,window.location.href.length - 1) :  window.location.href.substring(window.location.href.indexOf("html-gulp-www") + 14,window.location.href.indexOf("?"));
		if(htmlString){
			//页面名字截取
			//var htmlString = getCookie("jumpUrl").substring(getCookie("jumpUrl").indexOf("html-gulp-www")+14,getCookie("jumpUrl").length-1);
            $(".tradebase-topnavinner__text").removeClass("tradebase-topnavinner__text--active");
			$(".tradebase-topnavinner__text").each(function () {
				if($(this).attr("jumpUrl").indexOf(htmlString) != -1 && $(this).attr("jumpUrl").indexOf(modelString) != -1){//找到点击操作的1级菜单
					$(this).addClass("tradebase-topnavinner__text--active");
					var firstMark = $(this).attr("firstMark");//找到点击的1级菜单标示，进行2级菜单匹配是否隐藏展示
					if(firstMark){
                        $(".asideP").each(function(){
                            if($(this).find("*[firstMark]").attr("firstMark") == firstMark){//匹配到2级菜单，进行显示
								$(this).show();
                            }else{
                            	$(this).remove();
							}
                        });
					}
				}
            });
            $(".asideP").each(function(){
                if($(this).find("*[firstMark]").attr("jumpUrl").indexOf(htmlString) != -1 && $(this).find("*[firstMark]").attr("jumpUrl").indexOf(modelString) != -1) {//找到点击操作的2级菜单
                    $(this).find("*[firstMark]").css("color","#fff");
                    var firstMark = $(this).find("*[firstMark]").attr("firstMark");//找到点击的1级菜单标示，进行2级菜单匹配是否隐藏展示
                    $(".tradebase-topnavinner__text").each(function () {//找到点击的1级菜单标示，进行1级菜单匹配是否隐藏展示
						if($(this).attr("firstMark") == firstMark){
                            $(this).addClass("tradebase-topnavinner__text--active");
						}
                    })

                    $(".asideP").each(function(){
                        if($(this).find("*[firstMark]").attr("firstMark") == firstMark){//匹配到2级菜单，进行显示
                            $(this).show();
                        }else{
                            $(this).remove();
                        }
                    });
                }
			});
		}else{
            $(".tradebase-topnavinner__text")[0].addClass("tradebase-topnavinner__text--active");
		}
		$(".tradebase-topnavinner__text").on("click",function(){//1级菜单点击
			//setCookie("jumpUrl",$(this).attr("jumpUrl"));
			jumpUrl($(this).attr("jumpUrl"),"0000000",0);
		});

		$(".asideP").on("click",function(){
            //setCookie("jumpUrl",$(this).find("*[firstMark]").attr("jumpUrl"));
            jumpUrl($(this).find("*[firstMark]").attr("jumpUrl"),"0000000",0);
		});

		$("#quitOperate").on("click",function () {
			getAjaxResult("/acct/delCacheUser","POST",{},"quitOpeCallBack(data)");
			//jumpUrl("../../../login.html","0000000",0)
        });

		//hexiaojuan
		var navH = $(".tradebase-top").height();
		var asidH = height - navH;
		$(".tradebase-btm").height(asidH);
		var asideInerH = height - $(".tradebase-side-all").height();
		$(".tradebase-innerside").height(asideInerH-80);//80容错
		//左侧 导航展开收缩
		$(".tradebase-side-nav").on("click",".tradebase-side-nav__item",function(e){
			//console.log(e.target);
			if($(e.target).hasClass("tradebase-side-navSon__link")){
				return;
			}
			var currBtn = $(this).find(".tradebase-side-nav__icon");
			var currSon = $(this).find(".tradebase-side-navSon");
			var flagStatus = currBtn.hasClass("upstatus");
			if(flagStatus){
				currBtn.removeClass("upstatus").addClass("downstatus");
				currSon.slideDown();
			}else{
				currBtn.removeClass("downstatus").addClass("upstatus");
				$(".menu-hover").hide();
				currSon.slideUp();
			}
		})
		//三级导航 鼠标悬浮效果
		document.body.onmousemove = function(e){
			if($(e.target).parents(".tradebase-innerside").length > 0){
				var _this = e.target;
				// console.log(e.target) 
				if($(e.target).hasClass("tradebase-side-navSon__link")){
					$(".menu-hover").show();
					var dataNum = $(_this).attr("data-num");
					$(".menu-hover").find(".menu-box").hide();
					$(".menu-hover").attr("currNum",dataNum).find(".menu-box[boxnum='"+dataNum+"']").show();
				}
			}else{
				$(".menu-hover").hide();
			}
		}
		//四级导航宽高控制
		$(".menu-hover").css({"width":width*0.78+"px","height":height*0.73+"px","position":"absolute","right":-width*0.78+"px","top":"60px"});
		changeW(width,height);
		//导航条
		// $(".tradebase-topnavinner").css({"minWidth":"2000px","height":"60px"});//"position":"absolute",
		// $(".effectBox[effectType='1']").find(".allItemB").css({"minWidth":"2000px","height":"30px"});//"position":"absolute",
		//所有的navItem 项
		var parentBox = $(".effectBox[effectType='1']");
		operNav(parentBox);
		//标签项
		var parentBoxlabel = $(".effectBox[effectType='2']");
		operNav(parentBoxlabel);
		//侧边栏  每一项编号标识 初始化
		//markItem(".asideP",".asideSon","sigNum");
		//点击侧边栏
		// page-sidebar-submenu__item
		/*selectItem(".tradebase-sidebox",".asideSon",".itemS","sigNum",function(){
			//console.log("刷新数据1");
			var parentBoxlabel = $(".effectBox[effectType='2']");
			operNav(parentBoxlabel);
		});*/
		// //标签页删除
		/*delItem(".tradebase-singals","i");*/
	})
	//删除标签项--删除
	function delItem(obj,item){
		$(obj).on("click",item,function(){
			$(this).parents(".itemS:first").remove();
			var parentBox = $(".effectBox[effectType='2']");
			operNav(parentBox);
		})
	}
	function resizeTableH(type){//type  1:浏览器刷新   2 窗口大小发生变化
  		//表格高度控制
	      var width = $(window).width();
	      var height = $(window).height();
	      var headH = $(".main-head").height();//导航的高度
	      var labelH = $(".headerColum-labelbox").height();
	      var bannerH = $("#iframeId").contents().find(".mainbody-top").height();
	      //表格格式区
	      var tableSH = height - headH - labelH - bannerH - 100-80;//80预留分页的位置
	      var tableH = $("#iframeId").contents().find("#tableFix").height();//table的高度
	      var minH = 768 - headH - labelH - bannerH - 100-80;
	  //     if(tableH < minH){
	  //     	tableH = minH;
			// $("#iframeId").contents().find(".mainbody-table").css({"overflow-y":"auto","minHeight":minH+"px"});
	  //     }
	      if(tableSH <= minH){
	      	// $("#outerBody").height("768px");//.css({"overflow-y":"auto"});
	      	$("#iframeId").contents().find("html").height("768px").css({"overflow-y":"auto"});
	        $("#iframeId").contents().find(".mainbody-table").css({"height":minH+"px"});
	        //表格出现滚动条
	      }else{
			$("#iframeId").contents().find(".mainbody-table").css({"minHeight":minH+"px","maxHeight":tableSH+"px","overflow-y":"auto"});
	      }
	      if(minH <= tableH){
	      	$("#iframeId").contents().find(".mainbody-table").css({"height":minH+"px","overflow-y":"auto"});
	      }
  	}
	//设置页面最小宽度和最小高
	$(window).resize(function(){
		var width = $(window).width();
		var height = $(window).height();
		changeW(width,height);

		//表格高度
		// resizeTableH(2);
		//所有的navItem 项
		var parentBox = $(".effectBox[effectType='1']");
		operNav(parentBox);
		//标签项
		var parentBoxlabel = $(".effectBox[effectType='2']");
		operNav(parentBoxlabel);
	})
	function changeW(width,height){
		if(width <= 1200){
			//当窗口宽度小于1000px body出现滚动条 body宽为1200px
			$("#outerBody").width("1200px");
			$("html").css({"overflow-x":"auto"});
		}else{
			//当窗口宽度大于1000px  body宽为100%
			$("#outerBody").width("100%");
			$("html").css({"overflow-x":"hidden"});
		}
		// if(height <= 768){
		// 	//当窗口高度小于600px body出现滚动条 body高为768pxhttp://localhost:8051/static/tpl/homePage/html-gulp-www/trade_homeMain.html#
		// 	// $("#outerBody").height("768px");
		// 	$("#iframeId").contents().find(".mainbody-table").css({"overflow-y":"auto","minHeight":"300px"});
		// 	$("#iframeId").contents().find("html").height("768px").css({"overflow-y":"auto"});
		// }else{
		// 	//当窗口高度大于600px  body高为100%
		// 	// $("#outerBody").height("100%");
		// 	// $("html").css({"overflow-y":"hidden"});
		// 	$("#iframeId").contents().find("html").height("100%").css({"overflow-y":"hidden"});
		// }
	}
	//导航宽度控制 type 1-导航  2-标签
	function contolW(width,nav_leftW,nav_rightW,type){
		if(nav_leftW == "" || nav_leftW == null){
			nav_leftW = 0;
		} 
		if(nav_rightW == "" || nav_rightW == null){
			nav_rightW = 0;
		}
		if(type == 1){
			return width - nav_leftW - nav_rightW-30;
		}
		if(type == 2){
			return width - nav_leftW - nav_rightW-119;
		}		
	}
	
	function operNav(parentBox){
		var fromHideItemIndex = null;//后边开始隐藏导航项的下标
		var initfromHideItemIndex = null;
		//navinnerContainer  存放导航元素的盒子
		//navContainer   存放导航的可视盒子
		//leftBtn 左侧按钮
		//rightBtn 右侧按钮
		//type  1:导航  2：标签页
		var rightBtn = null;
		var leftBtn  = null;
		var nav_leftW = 0;
		var nav_rightW = 0;
		if(parentBox.find(".itemW").length == 0)return;
		var lis = parentBox.find(".itemW");
		if(parentBox.find(".leftW").length > 0){
			nav_leftW = parentBox.find(".leftW").outerWidth();
		}
		if(parentBox.find(".rightW").length > 0){
			nav_rightW = parentBox.find(".rightW").outerWidth();
		}
		if(parentBox.find(".maxVisbleB").length == 0)return;
		var navContainer = parentBox.find(".maxVisbleB");
		if(parentBox.find(".allItemB").length == 0)return;
		var navinnerContainer = parentBox.find(".allItemB");
		if(parentBox.find(".lookMoreBtn").length > 0)
			rightBtn = parentBox.find(".lookMoreBtn");
		if(parentBox.find(".lookprevBtn").length > 0)
			leftBtn = parentBox.find(".lookprevBtn");
		var type = parentBox.attr("effectType") ? parentBox.attr("effectType") : "0";
		var navW = allItemW(lis);//导航部分实际宽度
		var marginLeftW = 0;
		width = $(window).width();
		if(type == 1){
			$(leftBtn).hide();
		}
		$(navinnerContainer).css({"marginLeft":-marginLeftW+"px"});
		// var navW = $(".tradebase-topnav").width();
		if(type == 1){
			var minWidth = 1200 - nav_leftW - nav_rightW-30;//最小控制宽度
		}else{
			var minWidth = 1200 - nav_leftW - nav_rightW-119;//最小控制宽度			
		}
		var nav_maxW = contolW(width,nav_leftW,nav_rightW,type);//导航部分可视最大宽度  
		if(minWidth < nav_maxW){
			$(navContainer).width(nav_maxW - 50);//存放导航的可视盒子宽度限制
			minWidth = nav_maxW;
		}else{
			$(navContainer).width(minWidth);//存放导航的可视盒子宽度限制
		}
		
		if(minWidth > navW){
			$(rightBtn).hide();
			// return;
		}else{
			$(rightBtn).show();
			nav_maxW = minWidth;
		}
		if(type == 1){
			nav_maxW = nav_maxW;//(100为留存左右按钮的位置)
		}else{
			nav_maxW = nav_maxW;//(100为留存左右按钮的位置)
		}
		if(nav_maxW < navW){
			//获取开始隐藏的nav项
			var indexResult = eachItem(nav_maxW, lis);
			if(indexResult != false){
				fromHideItemIndex = indexResult();//后边开始隐藏导航项的下标
				// if(type == 1){
					$(rightBtn).show();
				// }
			}else{
				fromHideItemIndex = lis.length - 1;
			}
			//console.log(fromHideItemIndex+"fromHideItemIndex+初始值");
			//超出项隐藏
			// if(type == 1){
			// 	if(fromHideItemIndex != lis.length){
			// 		HideItem(fromHideItemIndex,lis);//隐藏不在可视区的元素
			// 	}
			// }
			// fromHideItemIndex -= 1;
		}else{
			fromHideItemIndex = lis.length;
			$(leftBtn).addClass("disableC");
			//console.log(fromHideItemIndex+"fromHideItemIndex+初始值");
			lis.css({"display":"inline-block"});
			if(type==1){
				$(leftBtn).hide();
			}
			$(rightBtn).hide();
		}
		initfromHideItemIndex = fromHideItemIndex;
		$(navContainer).css({"boxSizing":"borderBox"});
		//导航栏 更多按钮点击事件
		$(rightBtn).unbind("click");
		$(rightBtn).click(function(){
			var width = $(lis[fromHideItemIndex]).outerWidth();
			marginLeftW += width;
			$(navinnerContainer).css({"marginLeft":-marginLeftW+"px"});
			$(lis[fromHideItemIndex]).css({"display":"inline-block"});
			fromHideItemIndex = fromHideItemIndex + 1;
			// if(type == 1){
				if(fromHideItemIndex > lis.length -1){
					$(rightBtn).hide();
				}else{
					$(rightBtn).show();
				}
				if(marginLeftW <= 10){
					if(type==1){
						$(leftBtn).hide();
					}
				}else{
					$(leftBtn).show();
				}			
			// }
			//console.log(fromHideItemIndex+"fromHideItemIndex"+marginLeftW);
		})
		//导航栏 向前导航项按钮点击事件
		$(leftBtn).unbind("click");
		$(leftBtn).click(function(){
			if($(this).hasClass("disableC")){
				return false;
			}
			var width = $(lis[fromHideItemIndex-1]).outerWidth();
			marginLeftW -= width;
			$(navinnerContainer).css({"marginLeft":-marginLeftW+"px"});
			// if(type == 1){
				// $(lis[fromHideItemIndex-1]).css({"display":"none"});
			// }
			fromHideItemIndex = fromHideItemIndex - 1;
			// $(leftBtn).show();
			// if(type == 1){
				if(fromHideItemIndex < initfromHideItemIndex){
					if(type==1)
						$(leftBtn).hide();
					return;
				}
				if(fromHideItemIndex > lis.length -1){
					$(rightBtn).hide();
				}else{
					$(rightBtn).show();
				}
				if(marginLeftW <= 10){
					if(type == 1)
						$(leftBtn).hide();
				}else{
					$(leftBtn).show();
				}			
			// }
			//console.log(fromHideItemIndex+"firstItemIndex"+marginLeftW);
		})
	}
	//点击侧边栏---添加项
	function selectItem(objp,obj,itembox,attrkey,callback){
		$(objp).on("click",obj,function(){
			var isprocced = checkItem(this,itembox,attrkey);//判断是否是已选项
			if(isprocced){
				//导航栏中添加一个
				var itemDom = $($(itembox)[0]).clone(true);
				$(itemDom).attr(attrkey,$(this).attr(attrkey));
				$(itemDom).find("p").text($(this).text());
				itemDom.appendTo($(itembox).parent());
			}
			callback();
		})
	}

	//侧边栏  每一项编号标识 初始化
  	function markItem(parent,son,attrKey){
  		$(parent).each(function(index,item){
  			$(item).find(son).each(function(_index,_item){
  				$(_item).attr(attrKey,index+"_"+_index);
  			})
  		})
  	}
  	//判断 所点栏目是否已在标签栏
  	function checkItem(item,itembox,attrkey){
  		var attrVal = $(item).attr(attrkey);
  		var flag = true;
  		for(var i=0;i<$(itembox).length;i++){
  			if($($(itembox)[i]).attr(attrkey) == attrVal){
  				$($(itembox)[i]).click();
  				flag = false;
  				return false;
  			}
  		}
  		return flag;
  	}
  	window.onload = function(){
  		resizeTableH(1);
  	}
	//循环每一个tab，计算宽度和  获取开始隐藏的nav项
	function eachItem(maxNum, lis){
		var sumW = 0;
		var flag = true;
		for(var i=0;i<lis.length;i++){
			sumW += $(lis[i]).outerWidth();
			if(sumW >= maxNum){
				flag = false;
				var _index = i;
				return function (){
					return _index;
				}
			}
		}
		if(flag){
			return false;
		}
	}
	//隐藏的元素
	function HideItem(index,obj){
		if(typeof(obj) == "string"){
			obj = $(obj);
		}
		obj.css({"display":"block","float":"left"});
		for(var i=index;i<obj.length;i++){
			$(obj[i]).css({"display":"none"});
		}
	}
	//所有元素的宽和
	function allItemW(obj){
		var sum = 0;
		for(var i=0;i<obj.length;i++){
			sum += $(obj[i]).outerWidth();
		}
		return sum;
	}

	//退出接口
	function quitOpeCallBack(data){
		data = JSON.parse(data);
		if(data.retCode == "0000000"){
            jumpUrl("../../../login.html","0000000",0);
		}
    }

	