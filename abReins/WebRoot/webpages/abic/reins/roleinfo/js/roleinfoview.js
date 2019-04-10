/*------------------------------页面属性集 -------------------------------- */

/** 源用户查询URL */
var viewQueryRole = "api/role/getRoleById";

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
				$("#view_roleName").html(res.data.obj.roleName);
				$("#view_createTime").html(res.data.obj.createTime);
				$("#view_createOper").html(res.data.obj.createOper);
				$("#view_updateTime").html(res.data.obj.updateTime);
				$("#view_updateOper").html(res.data.obj.updateOper);
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
		},
		callback: {
			beforeCheck: beforeCheck,
			onCheck: onCheck
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
		
	var code, log, className = "dark";
	function beforeCheck(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
		return (treeNode.doCheck !== false);
	}
	function onCheck(e, treeId, treeNode) {
		showLog("[ "+getTime()+" onCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
	}	
/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 返回按钮操作，返回查询页面
 */
$("#view-back-button").on("click", viewBackButton_click);
function viewBackButton_click() {
	$("#select_userid").val(""); // 清空userid
	$('#mng-div').addClass('hide');
	$('#mng-div').empty();
	$('#query-div').show();
	cgrid.resize("#query-grid-table");
}
/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function() {
	// 查询表单信息
	viewQuerySuccess();
	//初始化树状图
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});

/*----------------------------------end-------------------------------------*/
