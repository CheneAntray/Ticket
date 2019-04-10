/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 查询用户URL */
var queryLoadUrl = "api/direction/getDirectionByPage";
/** 显示添加用户页面URL */
var showAddUrl = "abic/reins/traindirection/html/traindirectionadd.html";
/** 显示编辑用户页面URL */
var showEditUrl = "abic/reins/traindirection/html/traindirectionedit.html";
/** 显示查看用户页面URL */
var showViewUrl = "abic/reins/traindirection/html/traindirectionview.html";
/** 查询TABLE的ID */
var jqgridTable = "#query-grid-table";
/** 查询PAGER的ID */
var jqgridPager = "#query-grid-pager";
/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/

/**
 * 显示编辑方向信息页面
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
 * 显示方向查看页面
 */
function showViewUser() {
	var id = $(jqgridTable).jqGrid('getGridParam', 'selrow');
	debugger;
	if (id == null) {
		// 弹出提示 必须要有隐藏DIV id 为第一个参数 alertmsg (common.js里)
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
	var colNames = [ "方向ID", "方向名称"];
	var colModel = [ {
		name : "id",
		index : "id",
		autowidth : true,
		editable : true,
		hidden : true,
		align : "left"
	}, {
		name : "directionName",
		index : "directionName",
		autowidth : true,
		editable : true,
		align : "left"
	}];
	// 简单初始化操作
	cgrid.initCudGrid(jqgridTable, jqgridPager, queryLoadUrl, jqgridEditUrl,
			colNames, colModel, false, "id");
	/*--添加编辑、删除、查看功能图标--*/
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
	var trainDirectionView=new Object();
	trainDirectionView.directionName = $("#query_directionname").val();
    cgrid.queryGrid(jqgridTable, queryLoadUrl, trainDirectionView);
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
	$("#query_directionname").val("");
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/
$(document).ready(function() {
	// 查询GRID初始化
	queryJqgridInit();
});

/*----------------------------------end-------------------------------------*/
