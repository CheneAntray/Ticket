/* -----------------------------页面属性集 -------------------------------- */
var addLoadUrl = "api/saveReinsurerInf";
var addJqgridTable = "#add-grid-table";
var addJqgridPager = "#add-grid-pager";
var accountjqgrid_rowid = "";
var rolesize = 0;
var token = "";

/*-----------------------------------函数集合-------------------------------------------------*/
/**
 * 保存数据成功信息提示
 * 
 * @param data
 */
function addSaveSuccess(data) {
	// 保存成功返回查询页面
	$("#add-back-button").click();
	// 弹出提示 alertMsgOK (ajaxjson.js里)
	alertMsgOK("保存数据成功！");
	$("#query-button").click();
}

/**
 * 提交后台保存数据方法
 */
function addSaveUserInfo() {
	// 组装前台页面数据
	var userinfo = new Object();// 用户对象
	userinfo.account = $("#useraccount").val();
	userinfo.password = $("#user_userpassword").val();
	var userrepassword = $("#user_reuserpassword").val();
	userinfo.name = $("#user_username").val();
	var roleid = "";
	debugger;
	if (rolesize.data!= null) {
		for (var i = 1; i <= rolesize.data.list.length; i++) {
			if ($("#role_id" + rolesize.data.list[i - 1].id).is(':checked')) {
				roleid += $("#role_id" + rolesize.data.list[i - 1].id).val()
						+ ",";
			}
		}
	}
	userinfo.roleid = roleid;
	if (userinfo.password == userrepassword) {
		if (checkRoleBox() == true) {
			$.ajax({
				url : "api/user/addUser",
				headers : {
					"X-Auth-Token" : "open-sesame",
					"Content-Type" : "application/json",
					"Oauth-Token" : token
				},
				contentType : "application/json",
				type : "POST",
				dataType : "json",
				data : JSON.stringify(userinfo),
				success : function(res) {
					var code = res.code;
					if (code == 0) {
						alertMsgOK("用户信息添加成功！");
					} else if (code == 1) {
						alertMsgERROR("您添加的帐号已存在，请重新添加！");
					} else if (code == 8) {
						alertMsgERROR("权限校验失败!");
						return;
					} else {
						alertMsgERROR("添加用户异常,请联系管理员！");
					}
				}
			})
		} else {
			alertMsgERROR("至少勾选一种角色!");
		}
	} else {
		alertMsgERROR("两次密码不一致!");
	}
	// 保存表单信息
}
/**
 * 页面加载显示角色信息
 */
function showRole() {
	var roleView = new Object();
	roleView.id = "1";
	$.ajax({
		url : "api/role/getRole",
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : token
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(roleView),
		success : function(data) {
			var div = setRoleDiv(data);
			$("#rolediv").html(div);
			rolesize = data;
		}
	})
}

/**
 * 拼接角色信息div
 */
function setRoleDiv(data) {
	var div = '';
	if (data != null) {
		if (data.data!= null) {
			for (var i = 0; i < data.data.list.length; i++) {
				div += "<div class='col-xs-12 col-sm-4'><label for='form-field-mask-4 ' class='lab1'> <input id='role_name' name='role_name' value='"
						+ data.data.list[i].roleName
						+ "' type='text' style='width:100px;height:32px;margin-top: 4.5px;'></label><div class='col-sm-9 col-md-8' style='padding-left: 0px;'><input id='role_id"
						+ data.data.list[i].id
						+ "' name='role_id' value='"
						+ data.data.list[i].id
						+ "' style='width:45px;' type='checkbox' class='form-control tooltip-warning' data-rel='tooltip' data-placement='top' /></div></div>";
			}
		}
	}
	return div;
}
/**
 * form表单必录项校验规则
 * 
 * @returns
 */
function addValidataFunc() {
	return $("#add-form").validate(
			{
				rules : {
					user_userpassword : 'required',
					user_reuserpassword : 'required',
					user_account : 'required',
					user_username : 'required'
				},
				messages : {
					user_userpassword : "用户登录密码不能为空！",
					user_reuserpassword : "请再次输入密码！",
					user_account : "用户帐号不能为空！",
					user_username : "用户姓名不能为空！"
				},
				errorClass : 'error',
				success : 'valid',
				unhighlight : function(element, errorClass, validClass) { // 验证通过
					$(element).tooltip('destroy').removeClass(errorClass);
				},
				highlight : function(element, errorClass, validClass) { // 未通过验证
					// TODO
				},
				errorPlacement : function(error, element) {
					if ($(element).next("div").hasClass("tooltip")) {
						$(element).attr("data-original-title", $(error).text())
								.tooltip("show");
					} else {
						$(element).attr("title", $(error).text()).tooltip(
								"show");
					}
				}
			});
}
/**
 * 验证是否勾选用户权限
 */
function checkRoleBox() {
	var flag = false;
	if(rolesize.data!=null){
		for (var i = 0; i < rolesize.data.list.length; i++) {
			if ($("#role_id" + rolesize.data.list[i].id).get(0).checked) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
	}
	return flag;
}
/*----------------------------------------------事件-----------------------------------------------------*/

/**
 * 返回按钮操作，返回查询页面
 */
$("#add-back-button").on("click", function() {
	$('#mng-div').addClass('hide');
	$('#mng-div').empty();
	$('#query-div').show();
	cgrid.resize("#query-grid-table");
});

/**
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#add-save-button").on("click", function() {
	if (addValidataFunc().form()) {
		// 通过表单验证，以下编写自己的代码
		addSaveUserInfo();
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
	return false;
});

/**
 * 点击重置按钮操作，还原下拉选项查询条件
 */
$("#add-reset-button").on("click", function() {
	addSelectInit();
});

/*-----------------------------------------初始化操作----------------------------------------*/
$(document).ready(function() {
	token = ReadCookie("oauth-token");
	showRole();
});
