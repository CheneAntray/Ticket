/* -----------------------------页面属性集 -------------------------------- */
var jqgridEditUrl = dictEnum.getCodeValue('EMPTY_JQGRID_URL');
var _mainShowMenuUrl = "api/menu"; // 查询菜单
//var _mainShowVersionUrl = "api/base/parameter/"; // 查询版本信息你
var _mainShowLoginInfoUrl = "api/user/queryuserbyid"; // 查询用户登录信息
//var _mainrefreshCacheUrl = "api/base/refreshCache/"; // 刷新数据字典与菜单缓存
var _mainLogoutUrl = "api/logout"; // 登出用户系统
var _mainShowLoginUrl = "login.html"; // 登录页面
var _mainVersionInfo; // 版本信息
var _mainLoginInfo;// 登录用户信息
/*----------------------------------end-------------------------------------*/

/*---------------------------------函数集-----------------------------------*/
function mainVersionInfoInit(data) {

	_mainVersionInfo = data.parameter.name;
	$("#main_versionno").text(_mainVersionInfo);
}

function mainLoginInfoInit(data) {
	// main_useraccount main_comcode main_username
	$("#main_username").html(data.data.obj.name);
}

/**
 * 创建菜单信息
 * 
 * @param data
 */
function mainCreateMenu(data) {
	var jsonsData = data.data.list;
	var li=mainCreateTree(jsonsData);
	$(".nav-list").append(li);
}
/**
 * 创建菜单信息
 * 
 * @param jsonsData
 * @returns {String}
 */
function mainCreateTree(jsonsData) {
	var li = '';
	if (jsonsData != null) {
		var level = '1';
		for (var i = 0; i < jsonsData.length; i++) {
			if (level > jsonsData[i].parentid) {
				// 层级收尾
				var levellength = level - jsonsData[i].parentid;
				for (var j = 0; j < levellength; j++) {
					li += '</ul></li>';
				}
			}
			li += '<li';
			if (jsonsData[i].id == '0') {
				li += ' class="active"';
			}
			li += '>';
			if (jsonsData[i].isleaf) {
				li += '<a href="#" onclick=\'showPage("tab_' + jsonsData[i].id
						+ '","' + jsonsData[i].menuUrl + '")\'  >';

			} else {
				li += '<a href="#"  class="dropdown-toggle" >';
			}

			li += '<i class="' + jsonsData[i].icon + '"></i>';
			if (!jsonsData[i].isleaf) {
				li += '<span class="menu-text"> ' + jsonsData[i].title
						+ ' </span>';
				li += '<b class="arrow icon-angle-down"></b>';
			} else {
				li += jsonsData[i].title;
			}
			li += '</a>';
			if (jsonsData[i].isleaf) {
				li += '</li>';
			}
			if (!jsonsData[i].isleaf) {
				li += '<ul class="submenu">';
			}
			level = jsonsData[i].parentid;
		}
	}
	return li;
}

/**
 * @param jsonsData
 * @returns {String}
 */
function createDiv(jsonsData) {
	var subDiv = '';
	if (jsonsData != null) {
		for (var i = 0; i < jsonsData.length; i++) {
			if (jsonsData[i].isleaf) {
				// 叶子节点
				subDiv += '<div class="tab-pane fade ';
				if (jsonsData[i].id == '0') {
					subDiv += 'in active';
				}
				subDiv += '" id="tab_' + jsonsData[i].id + '" ></div>';
			}
		}
	}
	return subDiv;
}

/**
 * 显示页面链接
 * 
 * @param tabId
 * @param url
 */
function showPage(tabId, url) {
	if ('null' == url || null == url || '' == url) {
		url = 'about:blank';
	}
	var descontent = $("#divframe").attr("descontent");
	if (tabId != descontent) {
		$("#divframe").attr("descontent", tabId);
		$('#divframe').empty();
		// 设置访问功能URL
		$("#main-access-url").val(url);
		$("#divframe").load(url);
	}
}

/**
 * 退出登录
 * 
 * @param useraccount
 */
function logout(useraccount) {
	$.ajax({
		url : _mainLogoutUrl,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json"
		},
		type : "post",
		data : useraccount,
		contentType : 'text/html; charset=UTF-8',
		dataType : "json",
		success : function(data) {

			// 不管成功错误都返回登录页面
			document.location = _mainShowLoginUrl;
		},
		error : function(data) {
			// 不管成功错误都返回登录页面
			document.location = _mainShowLoginUrl;
		}
	});
}

/**
 * 刷新缓存
 * 
 * @param useraccount
 */
function refreshCache(useraccount) {
	restfulAjax(_mainrefreshCacheUrl, null, function() {
		alertMsgOK("刷新菜单与数据字典缓存成功！");
	});
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------事件集-----------------------------------*/
$("#main-logout-button").on("click", function() {
	var useraccount = $("#main_useraccount").val();
	alertConfirm("确定要退出登录吗?", logout, useraccount);

});

$("#main_versionno").on("dblclick", function() {
	var useraccount = $("#main_useraccount").val();
	if (useraccount == 'admin') {
		var refreshflag = $("#main_refreshflag").val();
		if (refreshflag < 5) {
			refreshflag = refreshflag - (-1);
			$("#main_refreshflag").val(refreshflag);
			return;
		} else {
			refreshflag = 0;
			$("#main_refreshflag").val(refreshflag);
			alertConfirm("确定要刷新缓存吗?", refreshCache, useraccount);
		}

	}
});

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/
$(document).ready(function() {
	debugger;
	// 获取菜单信息
	restfulAjax(_mainShowMenuUrl, "", mainCreateMenu);
	// 获取用户信息
	var userView=new Object();
	userView.id=ReadCookie("userid");
	restfulAjax(_mainShowLoginInfoUrl, JSON.stringify(userView), mainLoginInfoInit);
	// 获取版本信息
	// restfulAjax(_mainShowVersionUrl, dictEnum.getCodeValue("PARAM_VERSION"),
	// mainVersionInfoInit);
});
/*----------------------------------end-------------------------------------*/

