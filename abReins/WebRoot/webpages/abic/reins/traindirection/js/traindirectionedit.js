/*------------------------------页面属性集 -------------------------------- */

/** 源用户查询URL */
var viewQueryDirection = "api/direction/getDirectionById";
var editDirection="api/direction/editDirectionById";

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/

/**
 * 获取方向数据
 * 
 */
function viewQuerySuccess() {
	var TrainDirection = new Object();
	TrainDirection.id = $("#select_userid").val();
	$.ajax({
		url : viewQueryDirection,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(TrainDirection),
		success : function(res) {
			if(res.code==8){
				alertMsgERROR("权限校验失败!");
			}else{
				$("#edit_direction_name").val(res.data.obj.directionName);
			}
			
		}
	})
}
	
/**
 * 保存车次方向信息
 */
function editDirectionInfo(){
	var TrainDirection = new Object();
	TrainDirection.id = $("#select_userid").val();
	TrainDirection.directionName=$("#edit_direction_name").val();
	$.ajax({
		url : editDirection,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(TrainDirection),
		success : function(res) {
			if(res.code==0){
				alertMsgOK("方向信息修改成功！");
			}else if(res.code == 1){
				alertMsgERROR("您修改的名称已经存在，请您重新填写！");
			}else if(code==8){
				alertMsgERROR("权限校验失败!");
			}else{
				alertMsgERROR("修改方向信息异常,请联系管理员！");
			}
		}
	})
}

/**
 * 查验是否为空
 * @returns
 */
function editValidataInit()
{
    var validateRules =
    {
    		edit_direction_name : 'required'
    };
    var validateMessages =
    {
    		edit_direction_name : "方向信息不能为空！"
    };
    editValidate = buildValidate("edit-form", validateRules, validateMessages);
}
/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 返回按钮操作，返回查询页面
 */
$("#edit-back-button").on("click", editBackButton_click);
function editBackButton_click() {
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
function editSaveButton_click()
{
    if (editValidate.form())
    {
    	editDirectionInfo();
    }
}
/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function() {
	// 查询表单信息
	viewQuerySuccess();
	//初始化非空
	editValidataInit(); 
});

/*----------------------------------end-------------------------------------*/
