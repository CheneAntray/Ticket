$(function()
{

    // 插入alertdiv
    $("body").append('<div id="js_alert" /><div id="js_confirm" />');

});

/**
 * 弹出信息提示模态窗
 * 
 * @param id
 *            模态窗插入DIVid
 * @param msg
 *            提示信息
 * @param level
 *            提示信息级别("danger"|"warning"|"ok"|"info")
 */
function showAlert(id, msg, level)
{
    var headTitle = "提示信息：";
    var levelclass = "";
    if ("danger" == level)
    {
	levelclass = "alert-danger";
    } else if ("warning" == level)
    {
	levelclass = "alert-warning";
    } else if ("ok" == level)
    {
	levelclass = "alert-block alert-success";
    } else if ("info" == level)
    {
	levelclass = "alert-info";
    }
    var modelDiv = "<div id='_main_alert' class='modal fade' tabindex='-1'><div class='modal-dialog'>";
    modelDiv += "<div class='modal-content'>";
    // modelDiv+="<div class='modal-header'><a class='close'
    // data-dismiss='modal'>×</a>"+headTitle+"</div>";
    modelDiv += "<div class='modal-body overflow-visible'>";
    // modelDiv+="<div class='row' style='border:1px #cccccc solid; margin:-1px
    // 0px 8px 0px; padding-top:12px'>";
    modelDiv += "<div class='alert " + levelclass + "' style='margin:-10px;position:relative;'>";
    modelDiv += "<strong>" + msg + "</strong><a class='close' data-dismiss='modal' style='font-size:25px;position:absolute;left:95%;top:10px;'>X</a></div>";
    modelDiv += "</div></div></div>";
    $("#" + id).html(modelDiv);
    $("#_main_alert").on('show.bs.modal', function(e)
    {
	$("#_main_alert").draggable();// 为模态对话框添加拖拽
	// 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
	$(this).css('display', 'block');
	var modalHeight = $(window).height() / 2 - $('#_main_alert .modal-dialog').height() / 2 - 80;
	$(this).find('.modal-dialog').css(
	{
	    'margin-top' : modalHeight
	});
    });
    $("#_main_alert").on('hidden.bs.modal', function(e)
    {
	$("#" + id).empty();
    });
    $("#_main_alert").modal('show');
}

/**
 * 弹出信息提示模态窗
 * 
 * @param id
 *            模态窗插入DIVid
 * @param msg
 *            提示信息
 * @param level
 *            提示信息级别("danger"|"warning"|"ok"|"info")
 */
/**
 * @param id
 * @param msg
 * @param level
 * @param callback
 */
function showConfirm(id, msg, callback, callbackparam)
{
    var headTitle = "提示信息：";
    var levelclass = "alert-block alert-info";
    var modelDiv = "<div id='_main_confirm' class='modal fade' tabindex='-1'><div class='modal-dialog'>";
    modelDiv += "<div class='modal-content'>";
    modelDiv += "<div class='modal-body overflow-visible'>";
    modelDiv += "<div class='alert " + levelclass + "' style='margin:-10px;'>";
    modelDiv += "<p class='text-info'><strong>" + msg + "</strong></p>";
    modelDiv += "<p style='text-align:right'><button id='_main_confirm_ok' class='btn btn-sm btn-info' ><i class='icon-ok' />确定</button><span>   </span><button class='btn btn-sm' data-dismiss='modal'><i class='icon-remove' />取消</button></p>";
    modelDiv += "</div></div>";
    // modelDiv +="<div class='modal-footer'>";
    // modelDiv+="<button id='_main_confirm_ok' class='btn btn-sm
    // btn-warning'><i class='icon-ok' />确定</button>";
    // modelDiv+="<button class='btn btn-sm' data-dismiss='modal'><i
    // class='icon-remove' />取消</button>";
    // modelDiv+="</div>";
    modelDiv += "</div></div>";
    // console.log("modelDiv===" + modelDiv);

    $("#" + id).html(modelDiv);
    $("#_main_confirm").on('show.bs.modal', function(e)
    {
	// 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
	$(this).css('display', 'block');
	var modalHeight = $(window).height() / 2 - $('#_main_confirm .modal-dialog').height() / 2 - 80;
	$(this).find('.modal-dialog').css(
	{
	    'margin-top' : modalHeight
	});
	$(this).find("#_main_confirm_ok").click(function()
	{
	    $("#_main_confirm").modal("hide");
	    if ($.isFunction(callback))
	    {
		callback(callbackparam);
	    }
	});
    });

    $("#_main_confirm").on('hidden.bs.modal', function(e)
    {
	$("#" + id).empty();
    });
    $("#_main_confirm").modal('show');
}

/**
 * 弹出待确认提示框
 * 
 * @param msg
 *            提示信息
 * @param callback
 *            点击确定回调方法
 * @param callbackparam
 */
function alertConfirm(msg, callback, callbackparam)
{
    showConfirm("js_confirm", msg, callback, callbackparam);
}

/**
 * 绿色OK提示信息
 * 
 * @param msg
 */
function alertMsgOK(msg)
{
    showAlert("js_alert", msg, "ok");
}

/**
 * 蓝色INFO提示信息
 * 
 * @param msg
 */
function alertMsgINFO(msg)
{
    showAlert("js_alert", msg, "info");
}

/**
 * 红色ERROR提示信息
 * 
 * @param msg
 */
function alertMsgERROR(msg)
{
    showAlert("js_alert", msg, "danger");
}

/**
 * 黄色WARN提示信息
 * 
 * @param msg
 */
function alertMsgWARN(msg)
{
    showAlert("js_alert", msg, "warning");
}

/**
 * 项目通用调用Restfule服务AJAX请求方法
 * 
 * @param url
 *            请求地址
 * @param jsonData
 *            需要传递后台的json字符串数据
 * @param succCallback
 *            成功回调函数
 * @param errorCallback
 *            失败回调函数(可以为空)
 * @param isHideLoading
 *            是否隐藏加载中(可以为空)
 */
function restfulAjax(url, jsonData, succCallback, errorCallback, isHideLoading)
{
    var isHideLoading = false;
    // 判断第四个参数是否undefined 如果 是 第四个第五个均未传
    if (!$.isFunction(errorCallback))
    {
	if (errorCallback == undefined)
	{
	    isHideLoading = false;
	} else
	{
	    isHideLoading = errorCallback;
	}
    } else
    {
	isHideLoading = isHideLoading || false;
    }
    $.ajax(
    {
	type : "post",
	url : url,
	data : jsonData,
	headers :
	{
	    "X-Auth-Token" : "open-sesame",
	    "Content-Type" : "application/json",
	    "oauth-token" : ReadCookie("oauth-token"),
	    "access-url" : $("#main-access-url").val()
	},
	contentType : 'text/html; charset=UTF-8',
	dataType : "json",
	beforeSend : function()
	{
	    if (!isHideLoading)
	    {
		// 开始loading
		$.bootstrapLoading.start(
		{
		    loadingTips : "正在处理数据，请稍候..."
		});
		isHideLoading = true;
	    }

	},

	success : function(res)
	{	
		var code = res.code;
	    switch (code)
	    {
	    case 0: // 正常返回
		if (succCallback)
		{
			succCallback(res);
		}
		break;
	    case 1:
		// 没有页面
	    	alertMsgERROR(res.msg);
		break;
	    case 6:
		// 错误的请求
	    	alertMsgERROR(res.msg);
		break;
	    case 8:
		// 未经许可的请求
		// 业务类异常
		// 不管成功错误都返回登录页面
		// document.location = "login.html";
	    	alertMsgERROR(res.msg);
		break;
	    case 9:
		// 业务类异常
		alertMsgERROR(res.msg);
		break;
	    default:
		// 其他业务类自定义异常
		alertMsgERROR(res.msg);
	    }
	},
	error : function(data)
	{
	    // #3这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
	    alert(XMLHttpRequest.status);
	    alert(XMLHttpRequest.readyState);
//	    alert(textStatus); // paser error;
	    //	     
	    // 
	    // var responseInfo = JSON.parse(data.responseText);
	    alertMsgERROR(data.responseText);
	},
	complete : function()
	{ // 结束loading
	    // $(".js_loading").remove();
	    $.bootstrapLoading.end();
	}
    });
}

/**
 * 根据传入数据字典定义码返回数据字典信息数据
 * 
 * @param definecodes
 * @returns 返回样例：{"reinsurerType":[{"code":"0","name":"经纪公司"},{"code":"1","name":"直接再保公司"}],"companyType":[{"code":"0","name":"境外"},{"code":"1","name":"境内"}],"yesOrNo":[{"code":"1","name":"是"},{"code":"0","name":"否"}]}
 */
function restfulDictionaryList(definecodeList)
{
    var returndata;
    $.ajax(
    {
	type : "post",
	url : "api/base/dictionaryList/",
	data : JSON.stringify(definecodeList),
	async : false,
	headers :
	{
	    "X-Auth-Token" : "open-sesame",
	    "Content-Type" : "application/json",
	    "oauth-token" : ReadCookie("oauth-token"),
	    "access-url" : $("#main-access-url").val()
	},
	contentType : 'text/html; charset=UTF-8',
	dataType : "json",
	beforeSend : function()
	{
	    // 开始loading
	    $.bootstrapLoading.start(
	    {
		loadingTips : "正在处理数据，请稍候..."
	    });
	},

	success : function(res)
	{
	    var code = res.code;
	    switch (code)
	    {
	    case 200: // 正常返回
		returndata = res.data.dictionaryList;
		break;
	    case 404:
		// 没有页面
		break;
	    case 400:
		// 错误的请求
		break;
	    case 401:
		// 未经许可的请求
		// 业务类异常
		// 不管成功错误都返回登录页面
		document.location = "login.html";
		break;
	    case 999:
		// 业务类异常
		alertMsgERROR(res.message);
		break;
	    default:
		// 其他业务类自定义异常
		alertMsgERROR(res.message);
		// ;
	    }
	},
	complete : function()
	{ // 结束loading
	    // $(".js_loading").remove();
	    $.bootstrapLoading.end();
	}
    });

    if (returndata)
    {
	return returndata;
    } else
    {
	return null;
    }
}

/**
 * 根据传入数据字典定义码返回数据字典信息数据
 * 
 * @param definecodes
 * @returns 返回样例：[{"code":"1","name":"是"},{"code":"0","name":"否"}]
 */
function restfulDictionary(definecode)
{
    var returndata;
    $.ajax(
    {
	type : "post",
	url : "api/base/dictionary/",
	data : definecode,
	async : false,
	headers :
	{
	    "X-Auth-Token" : "open-sesame",
	    "Content-Type" : "application/json",
	    "oauth-token" : ReadCookie("oauth-token"),
	    "access-url" : $("#main-access-url").val()
	},
	contentType : 'text/html; charset=UTF-8',
	dataType : "json",
	beforeSend : function()
	{
	    // 开始loading
	    $.bootstrapLoading.start(
	    {
		loadingTips : "正在处理数据，请稍候..."
	    });
	},

	success : function(res)
	{
	    var code = res.code;
	    switch (code)
	    {
	    case 200: // 正常返回
		returndata = res.data.dictionary;
		break;
	    case 404:
		// 没有页面
		break;
	    case 400:
		// 错误的请求
		break;
	    case 401:
		// 未经许可的请求
		// 业务类异常
		// 不管成功错误都返回登录页面
		document.location = "login.html";
		break;
	    case 999:
		// 业务类异常
		alertMsgERROR(res.message);
		break;
	    default:
		// 其他业务类自定义异常
		alertMsgERROR(res.message);
		// ;
	    }
	},
	complete : function()
	{ // 结束loading
	    // $(".js_loading").remove();
	    $.bootstrapLoading.end();
	}
    });

    if (returndata)
    {
	return returndata;
    } else
    {
	return null;
    }
}

/**
 * 项目通用上传文件调用Restfule服务AJAX请求方法
 * 
 * @param url
 *            请求地址
 * @param formData
 *            需要传递后台的表单对象formData
 * @param succCallback
 *            成功回调函数
 * @param errorCallback
 *            失败回调函数(可以为空)
 * @param isHideLoading
 *            是否隐藏加载中(可以为空)
 */
function restfulUpload(url, formData, succCallback, errorCallback, isHideLoading)
{
    var isHideLoading = false;
    // 判断第四个参数是否undefined 如果 是 第四个第五个均未传
    if (!$.isFunction(errorCallback))
    {
	if (errorCallback == undefined)
	{
	    isHideLoading = false;
	} else
	{
	    isHideLoading = errorCallback;
	}
    } else
    {
	isHideLoading = isHideLoading || false;
    }
    $.ajax(
    {
	type : "post",
	url : url,
	enctype: 'multipart/form-data',
	data : formData,
	processData: false, // prevent jQuery from automatically transforming the
						// data into a query string
        contentType: false,
	headers :
	{
	    "X-Auth-Token" : "open-sesame",
// "Content-Type" : "application/json",
	    "oauth-token" : ReadCookie("oauth-token"),
	    "access-url" : $("#main-access-url").val()
	},
	dataType : "json",
	beforeSend : function()
	{
	    if (!isHideLoading)
	    {
		// 开始loading
		$.bootstrapLoading.start(
		{
		    loadingTips : "正在处理数据，请稍候..."
		});
		isHideLoading = true;
	    }

	},
	success : function(res)
	{
	    var code = res.code;
	    if (code != 200 && errorCallback)
	    {
		errorCallback(res);
		return;
	    }
	    switch (code)
	    {
	    case 0: // 正常返回
		if (succCallback)
		{
		    succCallback(res);
		}
		break;
	    case 404:
		// 没有页面
		break;
	    case 400:
		// 错误的请求
		break;
	    case 401:
		// 未经许可的请求
		// 业务类异常
		// 不管成功错误都返回登录页面
		document.location = "login.html";
		break;
	    case 999:
		// 业务类异常
		alertMsgERROR(res.message);
		break;
	    default:
		// 其他业务类自定义异常
		alertMsgERROR(res.message);
	    }
	},
	error : function(data)
	{
	    
	    alertMsgERROR(data.responseText);
	},
	complete : function()
	{ // 结束loading
	    // $(".js_loading").remove();
	    $.bootstrapLoading.end();
	}
    });
}


