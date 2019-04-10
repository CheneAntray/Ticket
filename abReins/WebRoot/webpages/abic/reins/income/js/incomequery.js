/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 查询详细收入URL */
var queryLoadUrl = "api/income/queryIncomeByPage";
/** 查询统计收入URL */
var queryLoadUrl1 = "api/income/queryIncomeTotalGroupByTicketStationId";
/** 查询TABLE的ID */
var jqgridTable = "#query-grid-table";
/** 查询PAGER的ID */
var jqgridPager = "#query-grid-pager";
/** 查询TABLE1的ID */
var jqgridTable1 = "#query-grid-table1";
/** 查询PAGER1的ID */
var jqgridPager1 = "#query-grid-pager1";
/** 查询下拉框的=URL */
var querySelectDataURL = "api/querySelectData/querySelectData";
/** 下拉框数据 */
var selectData = null;

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/

function querySelectData() {
	restfulAjax(querySelectDataURL, "", succFunction);
}
function succFunction(data) {
	selectData = data;
	querySelectInit(data);
}

/**
 * 页面下拉框初始化赋值
 */
function querySelectInit(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	chosenInitData("query_stationsection", data.data.obj.stationSection);
	chosenInitData("query_ticketstation", data.data.obj.ticketStation);
	chosenInitData("query_uep", data.data.obj.uep);
}

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
/**
 * 初始化JQGRID 使用本地数据类型，默认不加载数据 datatype : 'local'
 */
function queryJqgridInit() {
	var colNames = [ "收入ID", "站段", "售票站", "车次", "始发站", "终到站", "担当企业", "票款",
			"售票人数", "售票日期" ];
	var colModel = [ {
		name : "id",
		index : "id",
		autowidth : true,
		editable : true,
		hidden : true,
		align : "left"
	}, {
		name : "stationSectionName",
		index : "stationSectionName",
		autowidth : true,
		editable : true,
		align : "left"
	}, {
		name : "ticketStationName",
		index : "ticketStationName",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "trainNo",
		index : "trainNo",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "startStation",
		index : "startStation",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "endStation",
		index : "endStation",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "uepName",
		index : "uepName",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "income",
		index : "income",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "peopleCount",
		index : "peopleCount",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "dataDate",
		index : "dataDate",
		editable : true,
		autowidth : true,
		align : "left"
	} ];
	// 简单初始化操作
	cgrid.initCudGrid(jqgridTable, jqgridPager, queryLoadUrl, jqgridEditUrl,
			colNames, colModel, false, "id");
	cgrid.resize(jqgridTable);
	/*--添加编辑、查看功能图标--
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
	 */
}
/**
 * 初始化售票站统计的jqgrid 使用本地数据类型，默认不加载数据 datatype : 'local'
 */
function queryJqgrid1Init() {
	var colNames = [ "售票站ID", "售票站", "总票款", "总售票人数", "票价率" ];
	var colModel = [ {
		name : "ticketStationId",
		index : "ticketStationId",
		autowidth : true,
		editable : true,
		hidden : true,
		align : "left"
	}, {
		name : "ticketStationName",
		index : "ticketStationName",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "income",
		index : "totalIncome",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "peopleCount",
		index : "totalPeopleCount",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "ticketRate",
		index : "ticketRate",
		editable : true,
		autowidth : true,
		align : "left"
	} ];
    //选中数据行事件
	var onSelectRow = function() {
		var id = $(jqgridTable1).jqGrid('getGridParam','selrow');
		var rowData = $(jqgridTable1).jqGrid("getRowData",id);//根据上面的id获得本行的所有数据
        var ticketStationId= rowData.ticketStationId; //获得制定列的值 （auditStatus 为colModel的name）
        //重新加载详细数据
        $("#query_ticketstation").val(ticketStationId);
        cgrid.queryGrid(jqgridTable, queryLoadUrl, serializeObject('query-form'));
	};
	// 简单初始化操作
	cgrid.initCudGrid(jqgridTable1, jqgridPager1, queryLoadUrl1, jqgridEditUrl,
			colNames, colModel, false, "id1",onSelectRow);
}
/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 点击查询按钮操作，展示查询结果列表
 */
$("#query-button").on("click", queryButton_click);
function queryButton_click() {
	// 判断查询起期是否大于查询止期
	var startDate = $("input[name='startdate']").val();
	var endDate = $("input[name='enddate']").val();
	if (endDate != null && endDate != "") {
		if (startDate > endDate) {
			alertMsgINFO("查询起期不能大于查询止期");
			return;
		}
	}
	// serializeObject('query_form') 获取查询表单的查询条件参数
	cgrid.queryGrid(jqgridTable, queryLoadUrl, serializeObject('query-form'));
	// 查询出总的统计数
	queryTotalIncome();
	// 查询详细统计信息
	// cgrid.queryGrid(jqgridTable1, queryLoadUrl1,
	// serializeObject('query-form'));

	jQuery(jqgridTable1).jqGrid('setGridParam', {
		page : 1,
		rowNum : 500,
		url : queryLoadUrl1,
		datatype : "json",
		postData : serializeObject('query-form')
	}).setGridHeight("250px").trigger("reloadGrid");
}

/**
 * 查询总的统计数值
 */
function queryTotalIncome() {
	restfulAjax("api/income/queryIncomeTotalByStationSectionId", JSON.stringify(serializeObject('query-form')), queryTotalIncomeSucc);
}

function queryTotalIncomeSucc(data) {
		$("#tongji1").html(data.data.obj.totalIncome);
		$("#tongji2").html(data.data.obj.totalPeopleCount);
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
 * 站段下拉框更改事件
 */
$("#query_stationsection").on("change", query_stationsection_change);
function query_stationsection_change() {
	var stationSectionId = $("select[name='stationSectionId']").val();
	if (stationSectionId == null || stationSectionId == "") {
		stationSectionId = "null";
	}
	$.ajax({
		url : "api/querySelectData/queryTicketListByStationSectionId",
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : stationSectionId,
		success : function(data) {
			chosenInitData("query_ticketstation", data.data.obj.ticketStation);
		}
	})

}

/**
 * 重置按钮
 */
$("#query-reset-button").on("click", queryResetButton_click);
function queryResetButton_click() {
	// 初始化SELECT信息
	querySelectInit(selectData);
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/
$(document).ready(function() {
	initDateInput("query_startdate");// 初始化日期控件
	initDateInput("query_enddate");// 初始化日期控件
	querySelectData(); // 初始化SELECT信息
	queryJqgridInit();// 查询GRID初始化
	queryJqgrid1Init();
});

/*----------------------------------end-------------------------------------*/
