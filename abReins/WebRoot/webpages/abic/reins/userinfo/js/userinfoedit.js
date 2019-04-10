/*------------------------------页面属性集 -------------------------------- */

/** 源用户查询URL */
var viewQueryUrl = "api/user/queryuserbyid";
var rolesize = 0;
var userid = $("#select_userid").val();
/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/

/**
 * 调用查询单笔数据返回数据填充
 * 
 * @param data
 */
function editUserInfo() {
	var userView = new Object();
	userView.id = userid;
	userView.account = $("#edit_account").html();
	userView.name = $("#edit_username").val();
	userView.password = $("#edit_password").val();
	userView.createTime = $("#edit_createTime").val();
	var roleid = "";
	if (rolesize.data != null) {
		for (var i = 1; i <= rolesize.data.list.length; i++) {
			if ($("#role_id" + rolesize.data.list[i - 1].id).is(':checked')) {
				roleid += $("#role_id" + rolesize.data.list[i - 1].id).val()
						+ ",";
			}
		}
	}
	userView.roleid = roleid;
	if (checkRoleBox() == true) {
		$.ajax({
			url : "api/user/editUser",
			headers : {
				"X-Auth-Token" : "open-sesame",
				"Content-Type" : "application/json",
				"Oauth-Token" : ReadCookie("oauth-token")
			},
			contentType : "application/json",
			type : "POST",
			dataType : "json",
			data : JSON.stringify(userView),
			success : function(res) {
				if (res.code == 0) {
					alertMsgOK("用户信息修改成功！");
				} else if (res.code == 8) {
					alertMsgERROR("权限校验失败!");
				}
			}
		})
	} else {
		alertMsgERROR("至少勾选一种角色!");
	}
}
/**
 * 获取用户信息
 */
function getUserView() {
	var userview = new Object();
	userview.id = userid;
	$.ajax({
		url : viewQueryUrl,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(userview),
		success : function(res) {
			if (res.code == 8) {
				alertMsgERROR("权限校验失败!");
			} else {
				$("#edit_account").html(res.data.obj.account);
				$("#edit_password").val(res.data.obj.password);
				$("#edit_username").val(res.data.obj.name);
				$("#edit_createTime").html(res.data.obj.createTime);
				$("#edit_createOper").html(res.data.obj.createOper);
			}
		}
	})
}
/**
 * 页面加载显示角色权限信息
 */
function showRole() {
	var roleView = new Object();
	roleView.id = userid;
	$.ajax({
		url : "api/role/getRole",
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
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
 * 根据用户id查询已有角色id
 */
function getRoleId() {
	var userRoleRel = new Object();
	userRoleRel.userId = userid;
	$.ajax({
		url : "api/userrole/getRoleId",
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(userRoleRel),
		success : function(data) {
			for (var i = 0; i < data.data.list.length; i++) {
				// 循环出当前用户拥有哪些角色 并默认勾选
				$("#role_id" + data.data.list[i].roleId).attr("checked",
						"checked");
			}
		}
	})
}

/**
 * 拼接角色信息div
 */
function setRoleDiv(data) {
	var div = '';
	if (data != null) {
		if (data.data != null) {
			for (var i = 0; i < data.data.list.length; i++) {
				div += "<div class='col-xs-12 col-sm-4'><label for='form-field-mask-4 ' class='lab1'> <input id='role_name' name='role_name' value='"
						+ data.data.list[i].roleName
						+ "' type='text' disabled style='width:100px;height:32px;margin-top: 4.5px;'></label><div class='col-sm-9 col-md-8' style='padding-left: 0px;'><input id='role_id"
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
 * 查验是否为空
 * 
 * @returns
 */
function editValidataInit() {
	var validateRules = {
		username : 'required',
		password : 'required'
	};
	var validateMessages = {
		username : "人员姓名不能为空！",
		password : "密码不能为空！",
	};
	editValidate = buildValidate("edit-form", validateRules, validateMessages);
}
/**
 * 验证是否勾选用户权限
 */
function checkRoleBox() {
	var flag = false;
	if (rolesize.data != null) {
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
/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 返回按钮操作，返回查询页面
 */
$("#edit-back-button").on("click", viewBackButton_click);
function viewBackButton_click() {
	$("#select_userid").val(""); // 清空userid
	$('#mng-div').addClass('hide');
	$('#mng-div').empty();
	$('#query-div').show();
	cgrid.resize("#query-grid-table");
}
/**
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#edit-save-button").on("click", editSaveButton_click);
function editSaveButton_click() {
	if (editValidate.form()) {
		editUserInfo();
	}
}
/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function() {
	getUserView();// 初始化获取用户信息
	showRole();// 初始化获取角色信息
	editValidataInit(); // 初始化form表单必录项校验规则
	initDateInput("edit_createTime");// 初始化日期控件
	getRoleId();// 初始化获用户关联取角色信息
});

/*----------------------------------end-------------------------------------*/
