/* -----------------------------页面属性集 -------------------------------- */
var saveUrl = "api/direction/saveDirection";
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
function addSaveRoleInfo() {
	// 组装前台页面数据
	//定义角色对象接收角色基本信息
	var TrainDirectionView = new Object();
	TrainDirectionView.directionName = $("#direction_name").val();
	restfulAjax(saveUrl, JSON.stringify(TrainDirectionView), mainLoginInfoInit);
	$.ajax({
		url : saveUrl,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(TrainDirectionView),
		success : function(res) {
			var code = res.code;
			if (code == 0) {
				alertMsgOK("信息添加成功！");
			}else if(code == 1){
				alertMsgERROR("您填写的方向名称已经存在，请您重新填写！");
			}if(code==8){
				alertMsgERROR("权限校验失败!");
			}else{
				alertMsgERROR("添加信息异常,请联系管理员！");
			}
		}
	})
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
					direction_name : 'required'
				},
				messages : {
					direction_name : "方向名称不能为空！"
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
		addSaveRoleInfo();
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
	
});
