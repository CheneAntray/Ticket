/* -----------------------------页面属性集 -------------------------------- */
var changePasswordUrl = "api/password"; // 修改用户密码
var succeedUrl = "main.html"; // 用户查询页面
/*----------------------------------end-------------------------------------*/

/*---------------------------------函数集-----------------------------------*/

/**
 * @param id
 */
function show_box(id)
{
    jQuery('.widget-box.visible').removeClass('visible');
    jQuery('#' + id).addClass('visible');
}

/**
 * 组装认证数据
 * 
 * @param user
 * @param password
 * @returns {String}
 */
function make_base_auth(user, password)
{
    var tok = user + ':' + password;
    var hash = $.base64.atob(tok, true);
    return "Basic " + hash;
}

/*******************************************************************************
 * @param {string}
 *                cookieName Cookie名称
 * @param {string}
 *                cookieValue Cookie值
 * @param {number}
 *                expiretime Cookie有效时长(秒)
 */
function setCookie(cookieName, cookieValue, expiretime)
{

    /* 当前日期 */
    var today = new Date();
    /* Cookie过期时间 */
    var expire = new Date();
    /* 如果未设置nDays参数或者nDays为0，取默认值1 */
    var expiretime = expiretime * 1000 || 3600000 * 24;
    /* 计算Cookie过期时间 */
    expire.setTime(today.getTime() + expiretime);
    /* 设置Cookie值 */
    document.cookie = cookieName + "=" + escape(cookieValue) + ";expires=" + expire.toGMTString();
}

/**
 * 用户登录
 */
function loginSubmit()
{
	var userView=new Object();
	userView.account= $("#login_username").val();
	userView.password=$("#login_password").val();
	$.ajax({
		url : "api/login",
		contentType: "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(userView),
		success : function(res) {
			 var code = res.code;
			    switch (code)
			    {
			    case 0: // 正常返回
				// 获取到data.access_token "oauth-token"
				setCookie("oauth-token", res.data.obj.access_token, res.data.expires_in);
				setCookie("userid", res.data.obj.userId, res.data.userId);
				document.location = succeedUrl;
				break;
			    case 404:
				// 没有页面
				break;
			    case 400:
				// 错误的请求
				alertMsgERROR(res.message);
				break;
			    case 401:
				// 未经许可的请求
				alertMsgERROR(res.message);
				break;
			    case 9:
				// 用户账号和密码不匹配
				alertMsgERROR(res.msg);
				break;
			    default:
				// 其他业务类自定义异常
				alertMsgERROR(res.message);
				// ;
			    }
		},
		error: function(data) {  //#3这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
//            alert(XMLHttpRequest.status);
//            alert(XMLHttpRequest.readyState);
//            alert(textStatus); // paser error;
			alert(JSON.stringify(data));
        }
	})
}

/**
 * 用户修改密码
 */
function changePassword()
{
    var username = $("#changepd_username").val();
    var password = $("#changepd_password").val();
    var newpassword = $("#changepd_newpassword").val();
    var loginVO = new Object();
    loginVO.username = username;
    loginVO.password = password;
    loginVO.newpassword = newpassword;
    // 用户修改密码
    $.ajax(
    {
	url : changePasswordUrl,
	beforeSend : function(xhr)
	{
	    xhr.setRequestHeader('Content-Type', 'application/json');// 设置消息头
	},
	contentType : 'text/html; charset=UTF-8',
	type : "post",
	data : JSON.stringify(loginVO),
	dataType : "json",
	success : function(res)
	{
	    var code = res.code;
	    switch (code)
	    {
	    case 200: // 正常返回
		alertMsgOK("密码修改成功，请重新登录！");
		show_box('login-box');
		break;
	    case 400:
		// 错误的请求
		alertMsgERROR(res.message);
		break;
	    case 401:
		// 未经许可的请求
		alertMsgERROR(res.message);
		break;
	    }
	},
	error : function(data)
	{
	    alert(JSON.stringify(data));
	    alertMsgERROR("其他未知异常，请联系管理员");
	}
    });
}

/**
 * form表单必录项校验规则
 * 
 * @returns
 */
function loginValidataFunc()
{
    return $("#login-form").validate(
    {
	rules :
	{
	    username : 'required',
	    password : 'required'
	},
	messages :
	{
	    username : "用户账号不能为空！",
	    password : "密码不能为空！"
	},
	errorClass : 'error',
	success : 'valid',
	unhighlight : function(element, errorClass, validClass)
	{ // 验证通过
	    $(element).tooltip('destroy').removeClass(errorClass);
	},
	highlight : function(element, errorClass, validClass)
	{ // 未通过验证
	    // 不处理任何操作
	},
	errorPlacement : function(error, element)
	{
	    if ($(element).next("div").hasClass("tooltip"))
	    {
		$(element).attr("data-original-title", $(error).text()).tooltip("show");
	    } else
	    {
		$(element).attr("title", $(error).text()).tooltip("show");
	    }
	}
    });
}

/**
 * 修改密码form表单必录项校验规则
 * 
 * @returns
 */
function changepdValidataFunc()
{
    return $("#changepd-form").validate(
    {
	rules :
	{
	    username : 'required',
	    password : 'required',
	    newpassword : 'required',
	    repeatpassword :
	    {
		equalTo : "#changepd_newpassword"
	    }
	},
	messages :
	{
	    username : "用户不能为空！",
	    password : "原密码不能为空！",
	    newpassword : "新密码不能为空！",
	    repeatpassword : "两次输入新密码不一致！"
	},
	errorClass : 'error',
	success : 'valid',
	unhighlight : function(element, errorClass, validClass)
	{ // 验证通过
	    $(element).tooltip('destroy').removeClass(errorClass);
	},
	highlight : function(element, errorClass, validClass)
	{ // 未通过验证
	    // 不处理任何操作
	},
	errorPlacement : function(error, element)
	{
	    if ($(element).next("div").hasClass("tooltip"))
	    {
		$(element).attr("data-original-title", $(error).text()).tooltip("show");
	    } else
	    {
		$(element).attr("title", $(error).text()).tooltip("show");
	    }
	}
    });
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------事件集-----------------------------------*/

$("#login-signin-submit").on("click", function()
{
    if (loginValidataFunc().form())
    {
	// 通过表单验证，以下编写自己的代码
	loginSubmit();
    } else
    {
	// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
	$("#btn-scroll-up").click();
    }
    return false;
});

$("#changepd-save-button").on("click", function()
{
    if (changepdValidataFunc().form())
    {
	// 通过表单验证，以下编写自己的代码
	changePassword();
    } else
    {
	// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
    }
    return false;
});

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/
$(document).ready(function()
{
    window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>" + "<" + "/script>");
    if ("ontouchend" in document)
    {
	document.write("<script src='assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
    }

    loginValidataFunc();
    changepdValidataFunc();
});
$(document).keydown(function(event)
{
    if (event.keyCode == 13)
    {
	$("#login-signin-submit").click();
    }
});
/*----------------------------------end-------------------------------------*/

