//表单验证
//封装抖动式提示框
function verifyMsg(msg, obj, styleObj) {
    let params = {
        offset: obj.offset || ["40%", "68%"],
        area: obj.area || "200px",
        time: obj.time || 3000,
        shift: obj.shift || 6,
        skin: obj.skin || "error"
    };
    console.log(params);
    layer.msg(msg, params);
    if (styleObj != null) {
        setBoderColorStyle(styleObj, "red");
    }
}

//设置样式
function setBoderColorStyle(styleObj, styleName) {
    styleObj.style.borderColor = styleName;
}

//验证用户名
function verifyUserName(username) {
    if (username.value == null || username.value.length == 0) {
        verifyMsg("必填项不能为空", {}, username);
        return false;
    } else if (!/^[\S]{2,20}$/.test(username.value)) {
        verifyMsg("用户名必须是2到20位", {area: "230px"}, username);
        return false;
    }
    setBoderColorStyle(username, "#e6e6e6");
    return true;
}

//验证密码
function verifyPsd(psd) {
    if (psd.value == null || psd.value.length == 0) {
        verifyMsg("必填项不能为空", {}, psd);
        return false;
    } else if (!/^[\S]{6,16}$/.test(psd.value)) {
        verifyMsg("密码必须是6到16位", {area: "210px"}, psd);
        return false;
    }
    setBoderColorStyle(psd, "#e6e6e6");
    return true;
}

//验证验证码
function verifyValid(valid) {
    if (valid.value == null || valid.value.length == 0) {
        verifyMsg("必填项不能为空", {}, valid);
        return false;
    } else if (!/^[a-zA-Z0-9]{5}$/.test(valid.value)) {
        verifyMsg("验证码格式不正确", {}, valid);
        return false;
    }
    setBoderColorStyle(valid, "#e6e6e6");
    return true;
}

//验证再次密码输入
function verifyRpsd(psd, rpass) {
    if (psd.value != rpass.value) {
        verifyMsg("两次密码输入不一致", {area: "230px"}, rpass)
        return false;
    }
    setBoderColorStyle(rpass, "#e6e6e6");
    return true;
}

//验证手机
function verifyPhone(phone) {
    if (phone.value == null || phone.value.length == 0) {
        verifyMsg("必填项不能为空", {}, phone);
        return false;
    } else if (!/^1\d{10}$/.test(phone.value)) {
        verifyMsg("手机号格式不正确", {}, phone);
        return false;
    }
    setBoderColorStyle(phone, "#e6e6e6");
    return true;
}
//验证邮箱
function verifyEmail(email) {
    if (email.value == null || email.value.length == 0) {
        verifyMsg("必填项不能为空", {}, email);
        return false;
    } else if (!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(email.value)) {
        verifyMsg("邮箱格式不正确", {}, email);
        return false;
    }
    setBoderColorStyle(email, "#e6e6e6");
    return true;
}

//密码登录验证
function logOnVerify(flag) {
    if(flag == "PSD"){
        let form = document.querySelectorAll('.login-form')[0];
        console.log(form)
        let username = document.getElementById("login-user");
        let password = document.getElementById("login-psd");
        let valid = document.getElementById("login-valid");
        if (verifyUserName(username) && verifyPsd(password) && verifyValid(valid)) {
            layer.load(0, {
                time: 800,
                offset: ["68%", "73%"],
                shade: [0.4, '#def']
            });
            setTimeout(function () {
                form.submit();
            }, 800);
        }
    }else if(flag == "EMAIL"){
        let form = document.querySelectorAll('.login-form')[1];
        console.log(form)
        let username = document.getElementById("login-email");
        let valid = document.getElementById("login-email-valid");
        if (verifyEmail(username) && verifyValid(valid)) {
            layer.load(0, {
                time: 800,
                offset: ["68%", "73%"],
                shade: [0.4, '#def']
            });
            setTimeout(function () {
                form.submit();
            }, 800);
        }
    }
}

//注册验证
function registerVerify() {
    let form = document.getElementById('registerForm');
    let rname = document.getElementById("rName");
    let rphone = document.getElementById("rPhone");
    let rpass = document.getElementById("rpass");
    let rrpass = document.getElementById("rrpass");
    let rvalid = document.getElementById("rValid");
    if (verifyUserName(rname) && verifyPhone(rphone) && verifyPsd(rpass) && verifyRpsd(rpass, rrpass) && verifyValid(rvalid)) {
        form.submit();
    }
}

//登录与注册选项卡事件
document.querySelector("#li1").addEventListener("click", function () {
    toggle(document.querySelector("#valid_image_1"));
});
document.querySelector("#li2").addEventListener("click", function () {
    toggle(document.querySelector("#valid_image_2"));
});
//动态改变高度
function changeHeight(obj) {
    obj.removeAttribute("onclick");
    var div_layui_tab = obj.parentNode.parentNode;
    var li1 = document.querySelector("#li1");
    var li2 = document.querySelector("#li2");
    if (obj == li1) {
        li2.setAttribute("onclick", "changeHeight(this)");
        div_layui_tab.classList.toggle("enter");
        div_layui_tab.classList.toggle("email-enter");
    } else if(obj == li2){
        li1.setAttribute("onclick", "changeHeight(this)");
        div_layui_tab.classList.toggle("email-enter");
        div_layui_tab.classList.toggle("enter");
    }
}

//提示信息
var VALID_ERROR = document.querySelector("#VALID_ERROR").value;
var REGISTER_SUCCESS = document.querySelector("#REGISTER_SUCCESS").value;
var USER_PASSWORD_ERROR = document.querySelector("#USER_PASSWORD_ERROR").value;

if (VALID_ERROR == "true") {
    //1:正确；2:错误；3:询问；4:锁定；5:失败；6：成功；7:警告；16：加载
    verifyMsg("验证码输入有误", {}, null);
}
if (USER_PASSWORD_ERROR == "true") {
    verifyMsg("用户名或密码输入有误", {area: "230px"}, null)
}
if (REGISTER_SUCCESS == "true") {
    layer.msg('注册成功', {
        offset: "30px",
        area: "250px",
        time: 3000
    });
}
if (getCookie("PASSWORD_MODIFY_SUCCESS") != null) {
    layer.msg("密码修改成功,请登录", {
        offset: "30px",
        area: "250px",
        time: 3000
    });
    lazyDelCookie("PASSWORD_MODIFY_SUCCESS", 5)
}
if (localStorage.getItem("USER_NOT_LOGIN") != null) {
    localStorage.removeItem("USER_NOT_LOGIN");
    layer.msg('请登录后再进行操作', {
        time: 3000,
        area: "250px",
        offset: "30px",
        skin: "error"
    });
}
if (getCookie("LOGOUT_STATUS") != null) {
    layer.msg('已退出系统', {
        offset: "30px",
        area: "250px",
        time: 3000
    });
    lazyDelCookie("LOGOUT_STATUS", 5)
}

// second秒后删除指定cookie
function lazyDelCookie(key, second) {
    setTimeout(delCookie(key), second * 1000);
}

//获取指定的cookie值
function getCookie(key) {
    //获取所有cookie值,通过 "; " 截断得到 [name=zsh,pwd=123456]
    var cookies = document.cookie.split("; ");
    for (let i = 0; i < cookies.length; i++) {
        let arr = cookies[i].split("=");
        if (arr[0] == key) {
            return decodeURI(arr[1]);
        }
    }
}

//删除指定cookie值
function delCookie(key) {
    let date = new Date();
    date.setTime(date.getTime() - 1);
    document.cookie = key.concat("=OK;expires=" + date.toGMTString());
}
