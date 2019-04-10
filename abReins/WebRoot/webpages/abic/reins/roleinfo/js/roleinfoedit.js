/*------------------------------页面属性集 -------------------------------- */

/** 源用户查询URL */
var viewQueryRole = "api/role/getRoleById";
var editRole="api/role/editRoleById";

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/

/**
 * 获取角色数据
 * 
 */
function viewQuerySuccess() {
	var roleView = new Object();
	roleView.id = $("#select_userid").val();
	$.ajax({
		url : viewQueryRole,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(roleView),
		success : function(res) {
			if(res.code==8){
				alertMsgERROR("权限校验失败!");
			}else{
				$("#edit_role_name").val(res.data.obj.roleName);
				$("#edit_createTime").html(res.data.obj.createTime);
				$("#edit_createOper").html(res.data.obj.createOper);
				$.fn.zTree.init($("#treeDemo"), setting, res.data.list);
			}
		}
	})
}
/*
 * 树状菜单数据填充
 */
var setting = {
		view: {
			selectedMulti: false
		},
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	var zNodes =[
		{ id:1, pId:0, name:"菜单管理", open:true},
		{ id:100001, pId:1, name:"显示菜单"},
		{ id:2, pId:0, name:"用户管理", open:true},
		{ id:100003, pId:2, name:"用户查询",checked:true},
		{ id:100002, pId:2, name:"用户添加"},
		{ id:3, pId:0, name:"角色管理", open:true},
		{ id:100005, pId:3, name:"角色查询",doCheck:false},
		{ id:100006, pId:3, name:"角色添加"}
	];
	
/**
 * 保存角色信息
 */
function editRoleInfo(){
	var roleView = new Object();
	roleView.id = $("#select_userid").val();
	roleView.roleName=$("#edit_role_name").val();
	roleView.createTime=$("#edit_createTime").val();
	roleView.createOper=$("#edit_createOper").val();
	var resourceId ="";
	var t = $.fn.zTree.getZTreeObj("treeDemo");
	var count=t.getCheckedNodes(true);
	for(var i=0;i<count.length;i++){
		resourceId+=count[i].id+",";
	}
	roleView.resourceId=resourceId;
	$.ajax({
		url : editRole,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(roleView),
		success : function(res) {
			if(res.code==0){
				alertMsgOK("角色信息修改成功！");
			}else if(res.code == 1){
				alertMsgERROR("您修改的角色名称已经存在，请您重新填写！");
			}else if(res.code==8){
				alertMsgERROR("权限校验失败!");
			}else{
				alertMsgERROR("修改角色异常,请联系管理员！");
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
    edit_role_name : 'required',
    edit_createTime : 'required',
    edit_createOper :"required"
    };
    var validateMessages =
    {
    edit_role_name : "角色姓名不能为空！",
    edit_createTime : "创建时间不能为空！",
    edit_createOper :"创建者姓名不能为空"
    };
    editValidate = buildValidate("edit-form", validateRules, validateMessages);
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
function editSaveButton_click()
{
    if (editValidate.form())
    {
    	editRoleInfo();
    }
}
/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function() {
	// 查询表单信息
	viewQuerySuccess();
	//初始化树状图
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	//初始化非空
	editValidataInit(); 
	initDateInput("edit_createTime");//初始化日期控件
});

/*----------------------------------end-------------------------------------*/
