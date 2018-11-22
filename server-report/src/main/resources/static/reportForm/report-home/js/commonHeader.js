	var width = $(window).width();
	var height = $(window).height();
	$(function() {
        limitCodeDeal($("*[limitCode]"), "limitCode");
        //isMine
        //模块名字截取
        var modelString = window.location.href.substring(window.location.href.indexOf("reportForm") + 11, window.location.href.indexOf("html-gulp-www") - 1);
        //页面名字截取
        var htmlString = window.location.href.indexOf("?") == -1 ? window.location.href.substring(window.location.href.indexOf("html-gulp-www") + 14, window.location.href.length - 1) : window.location.href.substring(window.location.href.indexOf("html-gulp-www") + 14, window.location.href.indexOf("?"));
        if (htmlString) {
            //页面名字截取
            //var htmlString = getCookie("jumpUrl").substring(getCookie("jumpUrl").indexOf("html-gulp-www")+14,getCookie("jumpUrl").length-1);
            $(".tradebase-topnavinner__text").removeClass("tradebase-topnavinner__text--active");
            $(".tradebase-topnavinner__text").each(function () {
                if ($(this).attr("jumpUrl").indexOf(htmlString) != -1 && $(this).attr("jumpUrl").indexOf(modelString) != -1) {//找到点击操作的1级菜单
                    $(this).addClass("tradebase-topnavinner__text--active");
                    var firstMark = $(this).attr("firstMark");//找到点击的1级菜单标示，进行2级菜单匹配是否隐藏展示
                    if (firstMark) {
                        $(".asideP").each(function () {
                            if ($(this).find("*[firstMark]").attr("firstMark") == firstMark) {//匹配到2级菜单，进行显示
                                $(this).show();
                            } else {
                                $(this).remove();
                            }
                        });
                    }
                }
            });
            $(".asideP").each(function () {
                if ($(this).find("*[firstMark]").attr("jumpUrl").indexOf(htmlString) != -1 && $(this).find("*[firstMark]").attr("jumpUrl").indexOf(modelString) != -1) {//找到点击操作的2级菜单
                    $(this).find("*[firstMark]").parents(".tradebase-side-nav__link").addClass("asidePActive");
                    var firstMark = $(this).find("*[firstMark]").attr("firstMark");//找到点击的1级菜单标示，进行2级菜单匹配是否隐藏展示
                    $(".tradebase-topnavinner__text").each(function () {//找到点击的1级菜单标示，进行1级菜单匹配是否隐藏展示
                        if ($(this).attr("firstMark") == firstMark) {
                            $(this).addClass("tradebase-topnavinner__text--active");
                        }
                    })

                    $(".asideP").each(function () {
                        if ($(this).find("*[firstMark]").attr("firstMark") == firstMark) {//匹配到2级菜单，进行显示
                            $(this).show();
                        } else {
                            $(this).remove();
                        }
                    });
                }
            });
        } else {
            $(".tradebase-topnavinner__text")[0].addClass("tradebase-topnavinner__text--active");
        }
        $(".tradebase-topnavinner__text").on("click", function () {//1级菜单点击
            //setCookie("jumpUrl",$(this).attr("jumpUrl"));
            jumpUrl($(this).attr("jumpUrl"), "0000000", 0);
        });

        $(".asideP").on("click", function () {
            //setCookie("jumpUrl",$(this).find("*[firstMark]").attr("jumpUrl"));
            jumpUrl($(this).find("*[firstMark]").attr("jumpUrl"), "0000000", 0);
        });

        $("#quitOperate").on("click", function () {
            getAjaxResult("/acct/delCacheUser", "POST", {}, "quitOpeCallBack(data)");
            //jumpUrl("../../../login.html","0000000",0)
        });
    });

	//退出接口
	function quitOpeCallBack(data){
		data = JSON.parse(data);
		if(data.retCode == "0000000"){
            jumpUrl("../../../login.html","0000000",0);
		}
    }

	