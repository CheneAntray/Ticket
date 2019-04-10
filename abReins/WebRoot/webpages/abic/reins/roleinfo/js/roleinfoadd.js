/* -----------------------------页面属性集 -------------------------------- */
var saveRoleInfo = "api/role/saveRole";
var getTreeView = "api/role/showTreeView";
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
	var roleview = new Object();
	roleview.roleName = $("#role_name").val();
	//定义接收角色资源信息
	var resourceId ="";
	var t = $.fn.zTree.getZTreeObj("treeDemo");
	var count=t.getCheckedNodes(true);
	for(var i=0;i<count.length;i++){
		resourceId+=count[i].id+",";
	}
	roleview.resourceId=resourceId;
	$.ajax({
		url : saveRoleInfo,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(roleview),
		success : function(res) {
			var code = res.code;
			if (code == 0) {
				alertMsgOK("角色信息添加成功！");
			}else if(code == 1){
				alertMsgERROR("您填写的角色名称已经存在，请您重新填写！");
			}else if(code==8){
				alertMsgERROR("权限校验失败!");
			}else{
				alertMsgERROR("添加角色异常,请联系管理员！");
			}
		}
	})
}
/**
 * 加载树形控件所需数据
 */
function showTreeView(){
	var roleview = new Object();// 角色对象
	roleview.id=0;
	$.ajax({
		url : getTreeView,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(roleview),
		success : function(res) {
			$.fn.zTree.init($("#treeDemo"), setting, res.data.list);
		}
	})
}
/**
 * 获取角色权限树形控件
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
 * form表单必录项校验规则
 * 
 * @returns
 */
function addValidataFunc() {
	return $("#add-form").validate(
			{
				rules : {
					role_name : 'required'
				},
				messages : {
					role_name : "角色名称不能为空！"
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
	showTreeView();
	//初始化树状图
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});
