//=========自定义变量===================================================================================
//生成的颜色
var COLOR_VALUES = ["#D5F5E3", "#F9EBEA", "#F5EEF8", "#E8F8F5", "#FEF9E7", "#82E0AA", "#D7BDE2", "#A3E4D7", "#F9E79F"
];
//首页按钮颜色
var COLOR_VALUES_BTN = ["linear-gradient(to right, #4cbf30 0%, #00FFCC 100%)", "linear-gradient(to right, #55EFCB 0%, #5BCAFF 100%)", "linear-gradient(to right, #1AD6FD 0%, #1D62F0 100%)", "linear-gradient(to right, #FF5E3A 0%, #FF2A68 100%)",
    "linear-gradient(to right, #EF4DB6 0%, #C643FC 100%)", "linear-gradient(to right, #FFCC00 0%, #FF9500 100%)", "linear-gradient(to right, #C644FC 0%, #5856D6 100%)", "linear-gradient(to right, #55EFCB 0%, #5BCAFF 100%",
    "linear-gradient(to bottom right, #FF5E3A 0%, #FF2A68 100%)"
];

//获取blog-label.html所有标签
var labels = $(".labels .chip");
//获取blog-label.html所有标签中的统计数量的标签
var labels_num = $(".labels .tag-length");

//获取blog-label.html所有标签
var category_labels = $(".category .chip");
//获取blog-label.html所有标签中的统计数量的标签
var category_labels_num = $(".category .tag-length");

//格式化日期(日期，时间，星期)
Date.prototype.toLocaleDateWeekTime = function () {
    let weeks = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
    let m = this.getMonth() + 1, d = this.getDate(), h = this.getHours(), M = this.getMinutes();
    m = m < 10 ? '0' + m : m;
    d = d < 10 ? '0' + d : d;
    h = h < 10 ? '0' + h : h;
    M = M < 10 ? '0' + M : M;
    return this.getFullYear() + "年" + m + "月" + d + "日 " + h + ":" + M + " " + weeks[this.getDay()];
};
//格式化日期（2020-10-01日期）
Date.prototype.toLocaleDateString = function () {
    let m = this.getMonth() + 1, d = this.getDate();
    m = m < 10 ? '0' + m : m;
    d = d < 10 ? '0' + d : d;
    return this.getFullYear() + "-" + m + "-" + d;
};

//=========自定义事件监听===================================================================================
/**
 * 手机端切换菜单
 */
$("#m-mobile-btn").click(function () {
    if ($(".m-mobile-item").is(".m-mobile-hide")) {
        $("#header").addClass("basic black").css({opacity: 0.5, transition: "1s", height: "380px"});
    } else {
        $("#header").removeClass("basic black").css({opacity: 1, transition: "1s", height: "70px"});
    }
    $(".m-mobile-item").toggleClass('m-mobile-hide');
});

/**
 * 监听滚动条事件
 */
$(window).scroll(function () {
    let scroH = $(window).scrollTop() //滚动高度
        , b_l_1 = $(".blog-latest-1")
        , b_l_2 = $(".blog-latest-2")
        , b_l_3 = $(".blog-latest-3")
        , b_l_4 = $(".blog-latest-4")
        , header = $("#header")
        , top_btn = $("#toTop-button");
    //头部菜单透明
    if (scroH > 1) {
        if (header != null) {
            header.addClass("m-bg-blog").css({opacity: 0.9});
        }
    }
    if (scroH > 100) {
        if (top_btn != null) {
            top_btn.css({display: "block"});
        }
        if (b_l_1 != null && b_l_2 != null) {
            b_l_1.addClass("animated fadeInRight").css({display: "block"});
            b_l_2.addClass("animated fadeInUp").css({display: "block"});
        }
    }
    if (scroH > 390) {
        if (b_l_3 != null && b_l_4 != null) {
            b_l_3.addClass("animated fadeInLeft").css({display: "block"});
            b_l_4.addClass("animated fadeInDown").css({display: "block"});
        }
    }
    if (scroH <= 100) {
        if (top_btn != null) {
            top_btn.css({display: "none"});
        }
        if (b_l_1 != null && b_l_2 != null && b_l_3 != null && b_l_4 != null) {
            b_l_1.css({display: "none"});
            b_l_2.css({display: "none"});
            b_l_3.css({display: "none"});
            b_l_4.css({display: "none"});
        }
    }
    //恢复头部菜单透明
    if (scroH <= 0) {
        if (header != null) {
            header.removeClass("m-bg-blog").css({opacity: 1});
        }
    }
});


//=========自定义方法===================================================================================
/**
 * 标签背景颜色随机生成
 * @param _this 传入指定对象
 * @param _flag 1:COLOR_VALUES 0:COLOR_VALUES_BTN
 */
function randomColor(_this, _flag) {
    let r_color;
    if (_flag == 1) {
        r_color = COLOR_VALUES[randomNumber(COLOR_VALUES.length - 1)];
    } else {
        r_color = COLOR_VALUES_BTN[randomNumber(COLOR_VALUES_BTN.length - 1)];
    }
    console.log(r_color);
    _this.css({"background": r_color});
}

/**
 * 循环函数封装
 * @param objs 遍历对象
 * @param flag 1:COLOR_VALUES 0:COLOR_VALUES_BTN
 */
function forEach(objs, flag) {
    $.each(objs, function (index) {
        randomColor(objs.eq(index), flag);
    });
}

/**
 * 随机生成一个[0,n]之间的数
 * @param n
 */
function randomNumber(n) {
    return parseInt(Math.random() * (n + 1));//[0,n+1)
}

/**
 * 随机生成一个指定范围[min,max]之间的数
 * @param min 左区间值
 * @param max 右区间值
 */
function randomRangeNumber(min, max) { //5 10
    return parseInt(Math.random() * (max - min + 1) + min);//[0*6+5,1*6+5)
}

/**
 * 关键字搜索
 * @param obj
 */
function keywordSearch(obj) {
    let _this = $(obj);
    let keyword = _this.siblings().eq(0).val();
    window.location.href = "/index/search/" + keyword;
}

//浏览文章
function look(obj) {
    let _this = $(obj);
    window.open("/detailed/" + _this.attr("data-id") + "/" + _this.attr("data-title"));
}

//加载博客分类
function loadCategory(obj) {
    let _this = $(obj);
    window.location.href = "/category?categoryId=" + _this.attr("data-categoryId");
}

$(document).on("keyup", "#search", function (e) {
    if (e.keyCode == 13) {
        keywordSearch($(this).siblings().eq(0));
    }
});
//加载blog-label.html中所有标签的样式
forEach(labels, 1);
//加载index.html中所有标签的样式
forEach($(".blog .blog-label > .chip"), 1);
//加载blog-detailed.html中所有标签的样式
forEach($(".blog-ml > .chip"), 1);
//加载blog-category.html中所有标签的样式
forEach(category_labels, 1);
//原创标签样式
randomColor($(".blog-link > a").eq(0), 1);
//加载首页中所有按钮的样式
forEach($(".m-article-read"), 0);
//加载首页时间按钮的样式
forEach($(".post-date"), 0);
//为blog-labels页下的所有DOM注册事件监听
// setLabelEvents();
//随机选中标签
// randomSelectedLabel();