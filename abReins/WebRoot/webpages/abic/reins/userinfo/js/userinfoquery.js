/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 查询用户URL */
var queryLoadUrl = "api/user/queryuser";
/** 显示添加用户页面URL */
var showAddUrl = "abic/reins/userinfo/html/userinfoadd.html";
/** 显示编辑用户页面URL */
var showEditUrl = "abic/reins/userinfo/html/userinfoedit.html";
/** 显示查看用户页面URL */
var showViewUrl = "abic/reins/userinfo/html/userinfoview.html";
/** 查询TABLE的ID */
var jqgridTable = "#query-grid-table";
/** 查询PAGER的ID */
var jqgridPager = "#query-grid-pager";
/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/

/**
 * 显示编辑用户页面
 */
function showEditUser() {
	var id = $(jqgridTable).jqGrid('getGridParam', 'selrow');
	if (id == null) {
		// 弹出提示 必须要有隐藏DIV id 为第一个参数 alertmsg (common.js里)
		alertMsgERROR("请选择一条数据！");
	} else {
		$("#select_userid").val(id);
		// 加载编辑页面
		$("#mng-div").load(showEditUrl);
		$('#query-div').hide();
		$('#mng-div').removeClass('hide');
		// 显示页面置顶
		$("#btn-scroll-up").click();
	}
}

/**
 * 显示查看用户页面
 */
function showViewUser() {
	var id = $(jqgridTable).jqGrid('getGridParam', 'selrow');
	if (id == null) {
		// 弹出提示 alertMsgWARN (ajaxjson.js里)
		alertMsgERROR("请选择一条数据！");
	} else {
		$("#select_userid").val(id);
		// 加载编辑页面
		$("#mng-div").load(showViewUrl);
		$('#query-div').hide();
		$('#mng-div').removeClass('hide');
		// 显示页面置顶
		$("#btn-scroll-up").click();
	}
}
/**
 * 初始化JQGRID 使用本地数据类型，默认不加载数据 datatype : 'local'
 */
function queryJqgridInit() {
	var colNames = [ "人员ID", "人员账号", "人员密码", "人员姓名", "创建时间", "创建者", "修改时间",
			"修改者" ];
	var colModel = [ {
		name : "id",
		index : "id",
		autowidth : true,
		editable : true,
		hidden : true,
		align : "left"
	}, {
		name : "account",
		index : "account",
		autowidth : true,
		editable : true,
		align : "left"
	}, {
		name : "password",
		index : "password",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "name",
		index : "name",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "createTime",
		index : "createTime",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "createOper",
		index : "createOper",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "updateTime",
		index : "updateTime",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "updateOper",
		index : "updateOper",
		editable : true,
		autowidth : true,
		align : "left"
	} ];
	// 简单初始化操作
	cgrid.initCudGrid(jqgridTable, jqgridPager, queryLoadUrl, jqgridEditUrl,
			colNames, colModel, false, "id");
	/*--添加编辑、查看功能图标--*/
	jQuery(jqgridTable).navButtonAdd(jqgridPager, {
		caption : "",
		buttonicon : "icon-pencil blue",
		title : "编辑",
		onClickButton : showEditUser,
		position : "last"
	}).navButtonAdd(jqgridPager, {
		caption : "",
		buttonicon : "icon-zoom-in grey",
		title : "查看",
		onClickButton : showViewUser,
		position : "last"
	});
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 点击查询按钮操作，展示查询结果列表
 */
$("#query-button").on("click", queryButton_click);
function queryButton_click() {
	var searchData=new Object();
    searchData.account = $("#user_account").val();
    searchData.name = $("#user_name").val();
    cgrid.queryGrid(jqgridTable, queryLoadUrl, searchData);
}

/**
 * 点击新增按钮
 */
$("#query-add-button").on("click", queryAddButton_click);
function queryAddButton_click() {
	$("#mng-div").load(showAddUrl);
	$('#query-div').hide();
	$('#mng-div').removeClass('hide');
	// 显示页面置顶
	$("#btn-scroll-up").click();
}
/**
 * 重置按钮
 */
$("#query-reset-button").on("click", queryResetButton_click);
function queryResetButton_click() {
	// 初始化SELECT信息
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/
$(document).ready(function() {
	// 查询GRID初始化
	queryJqgridInit();
});

/*----------------------------------end-------------------------------------*/
