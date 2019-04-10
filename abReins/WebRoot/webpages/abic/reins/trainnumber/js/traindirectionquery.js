/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 查询详细车次URL */
var queryLoadUrl = "api/trainnumber/queryTrainNumberByPage";
/** 查询TABLE的ID */
var jqgridTable = "#query-grid-table";
/** 查询PAGER的ID */
var jqgridPager = "#query-grid-pager";
/** 查询下拉框的URL */
var querySelectDataURL = "api/querySelectData/queryTrainStationSelectData";
/** 查询详细车次URL */
var queryLoadUrl = "api/trainnumber/queryTrainNumberByPage";
/** 编辑详细车次URL */
var showEditUrl = "abic/reins/trainnumber/html/trainnumberedit.html";
/** 查看详细车次URL */
var showViewUrl = "abic/reins/trainnumber/html/trainnumberview.html";
/** 添加详细车次URL */
var showAddUrl = "abic/reins/trainnumber/html/trainnumberadd.html";
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
 * 绑定头次下拉框
 * @param selectid
 * @param data
 * @param multiple
 * @param notNullOption
 * @returns
 */
function chosenTitleNoInitData(selectid, data, multiple, notNullOption)
{
    var multiple = multiple||false;
    $("#" + selectid).html("");
    $("#" + selectid).chosen("destroy");
    if (!notNullOption)
    {
	$("<option value=''> </option>").appendTo("#" + selectid);
    }
	$("<option value=' C '>C</option>").appendTo("#" + selectid);
	$("<option value=' D '>D</option>").appendTo("#" + selectid);
	$("<option value=' G '>G</option>").appendTo("#" + selectid);
	$("<option value=' Z '>Z</option>").appendTo("#" + selectid);
	$("<option value=' T '>T</option>").appendTo("#" + selectid);
	$("<option value=' K '>K</option>").appendTo("#" + selectid);
	$("<option value=' L '>L</option>").appendTo("#" + selectid);
	$("<option value=' A '>A</option>").appendTo("#" + selectid);
	$("<option value=' Y '>Y</option>").appendTo("#" + selectid);
	$("<option value=' 数字 '>数字</option>").appendTo("#" + selectid);
    $("#" + selectid).attr("multiple", multiple);
    $("#" + selectid).chosen(
    {
	allow_single_deselect : true,
	// inherit_select_classes : true,
	no_results_text : "未找到此选项!",
	search_contains : true,
	width : '100%'
    });
     $("#"+selectid).trigger("chosen:updated");
     //校验添加样式
     $("#" + selectid+"_chosen").addClass("tooltip-warning");
     $("#" + selectid+"_chosen").attr("data-rel","tooltip");
     //对齐表单
     $("#" + selectid+"_chosen").css("margin-bottom","12px");
     if($("#"+selectid).attr("name")){
	 $("#" + selectid+"_chosen").attr("name",$("#"+selectid).attr("name")+"_chosen");
     }
}

/**
 * 页面下拉框初始化赋值
 */
function querySelectInit(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	chosenInitData("query_startstation", data.data.list);
	chosenInitData("query_endstation", data.data.list);
	chosenTitleNoInitData("query_titleno");
}


/**
 * 显示编辑用户页面
 */
function showEditTrainNumber() {
	var id = $(jqgridTable).jqGrid('getGridParam', 'selrow');
	if (id == null) {
		// 弹出提示 必须要有隐藏DIV id 为第一个参数 alertmsg (common.js里)
		alertMsgERROR("请选择一条数据！");
	} else {
		$("#select_trainNumberId").val(id);
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
		$("#select_trainNumberId").val(id);
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
	var colNames = [ "车次ID", "车次", "起始站", "终到站", "担当企业" ];
	var colModel = [ {
		name : "id",
		index : "id",
		autowidth : true,
		editable : true,
		hidden : true,
		align : "left"
	}, {
		name : "trainNo",
		index : "trainNo",
		autowidth : true,
		editable : true,
		align : "left"
	}, {
		name : "startStationName",
		index : "startStationName",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "endStationName",
		index : "endStationName",
		editable : true,
		autowidth : true,
		align : "left"
	}, {
		name : "uepName",
		index : "uepName",
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
		onClickButton : showEditTrainNumber,
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
	// serializeObject('query_form') 获取查询表单的查询条件参数
	cgrid.queryGrid(jqgridTable, queryLoadUrl, serializeObject('query-form'));
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
	querySelectInit(selectData);
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/
$(document).ready(function() {
	querySelectData(); // 初始化SELECT信息
	queryJqgridInit();// 查询GRID初始化
});

/*----------------------------------end-------------------------------------*/
