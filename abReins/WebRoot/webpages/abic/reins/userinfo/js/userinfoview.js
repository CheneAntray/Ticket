/*------------------------------页面属性集 -------------------------------- */

/** 源用户查询URL */
var viewQueryUrl = "api/user/queryuserbyid";

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/
/**
 * 获取用户信息
 */
function getUserView(){
	var userview=new Object();
	userview.id=$("#select_userid").val();
	$.ajax({
		url : viewQueryUrl,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" :ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(userview),
		success : function(res) {
			if(res.code==8){
				alertMsgERROR("权限校验失败!");
			}else{
				$("#view_useraccount").html(res.data.obj.account);
				$("#view_userpassword").html(res.data.obj.password);
				$("#view_username").html(res.data.obj.name);
				$("#view_createTime").html(res.data.obj.createTime);
				$("#view_createOper").html(res.data.obj.createOper);
				$("#view_updateTime").html(res.data.obj.updateTime);
				$("#view_updateOper").html(res.data.obj.updateOper);
			}
		}
	})
}
/**
 * 页面加载显示角色权限信息
 */
function showRole() {
	var roleView = new Object();
	roleView.id=$("#select_userid").val();
	$.ajax({
		url : "api/role/getRoleByUserId",
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
		}
	})
}

/**
 * 拼接角色信息div
 */
function setRoleDiv(data) {
	var div = '';
	if (data != null) {
		for (var i = 0; i < data.data.list.length; i++) {
			div += "<div class='col-xs-12 col-sm-4'><label for='form-field-mask-4 ' class='lab1'> <input id='role_name' name='role_name' value='"
					+ data.data.list[i].roleName
					+ "' type='text' disabled style='width:100px;height:32px;margin-top: 4.5px;'></label><div class='col-sm-9 col-md-8' style='padding-left: 0px;'><input id='role_id"
					+ data.data.list[i].id
					+ "' name='role_id' value='"
					+ data.data.list[i].id
					+ "' style='width:45px;' type='checkbox' checked='checked' class='form-control tooltip-warning' data-rel='tooltip' data-placement='top' disabled /></div></div>";
		}
	}
	return div;
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 返回按钮操作，返回查询页面
 */
$("#view-back-button").on("click", viewBackButton_click);
function viewBackButton_click()
{
    $("#select_userid").val(""); // 清空userid
    $('#mng-div').addClass('hide');
    $('#mng-div').empty();
    $('#query-div').show();
    cgrid.resize("#query-grid-table");
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function()
{
	getUserView();
	showRole();
});

/*----------------------------------end-------------------------------------*/
