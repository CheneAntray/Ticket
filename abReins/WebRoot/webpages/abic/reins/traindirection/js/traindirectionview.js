/*------------------------------页面属性集 -------------------------------- */

var viewQueryDirection = "api/direction/getDirectionById";
/** 查询方向已有的车次 */
var queryLoadUrl = "api/trainnumber/queryTrainNumberByInDirection";
/** 查询方向没有的车次 */
var queryNotLoadUrl = "api/trainnumber/queryTrainNumberByNotInDirection";
/** 查询TABLE的ID */
var jqgridTableYes = "#viewquery-grid-table";
/** 查询PAGER的ID */
var jqgridPager = "#viewquery-grid-pager";
/** 查询TABLE1的ID */
var jqgridTableNot = "#querynot-grid-table";
/** 查询PAGER1的ID */
var jqgridPagerNot = "#querynot-grid-pager";
/** 查询下拉框的=URL */
var querySelectDataURL = "api/querySelectData/queryTrainStationSelectData";
/** 下拉框数据 */
var selectData = null;

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
				$(".view_direction_name").html(res.data.obj.directionName);
			}
		}
	})
}

/** 查询下拉框数据 */
function querySelectData() {
	restfulAjax(querySelectDataURL, "", succFunction);
}
function succFunction(data) {
	selectData = data;
	querySelectInit(data);
	querySelectInit1(data);
}

/**
 * 绑定头次下拉框
 * 
 * @param selectid
 * @param data
 * @param multiple
 * @param notNullOption
 * @returns
 */
function chosenTitleNoInitData(selectid, data, multiple, notNullOption) {
	var multiple = multiple || false;
	$("#" + selectid).html("");
	$("#" + selectid).chosen("destroy");
	if (!notNullOption) {
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
	$("#" + selectid).chosen({
		allow_single_deselect : true,
		// inherit_select_classes : true,
		no_results_text : "未找到此选项!",
		search_contains : true,
		width : '100%'
	});
	$("#" + selectid).trigger("chosen:updated");
	// 校验添加样式
	$("#" + selectid + "_chosen").addClass("tooltip-warning");
	$("#" + selectid + "_chosen").attr("data-rel", "tooltip");
	// 对齐表单
	$("#" + selectid + "_chosen").css("margin-bottom", "12px");
	if ($("#" + selectid).attr("name")) {
		$("#" + selectid + "_chosen").attr("name",
				$("#" + selectid).attr("name") + "_chosen");
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
function querySelectInit1(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	chosenInitData("query_startstation1", data.data.list);
	chosenInitData("query_endstation1", data.data.list);
	chosenTitleNoInitData("query_titleno1");
}

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
	cgrid.initCudGrid(jqgridTableYes, jqgridPager, queryLoadUrl, jqgridEditUrl,
			colNames, colModel, true, "id6");
	/*--添加编辑、删除、查看功能图标--*/
	jQuery(jqgridTableYes).navButtonAdd(jqgridPager, {
		caption : "",
		buttonicon : "icon-trash red",
		title : "移除",
		onClickButton : delTrainNo,
		position : "last"
	});
	cgrid.resize(jqgridTableYes);
}

/**
 * 移除已有车次
 */
function delTrainNo() {
	var radioValue;
	var trainNumberView = new Object();
	var obj = document.getElementsByName("radio"); // 这个是以标签的name来取控件
	for (i = 0; i < obj.length; i++) {
		if (obj[i].checked) {
			radioValue = obj[i].value;
		}
	}
	if (radioValue == 1) {
		// 获取多选到的id集合
		var rowData = jQuery(jqgridTableYes)
				.jqGrid('getGridParam', 'selarrrow');
		var trainNumberIds = new Array();
		if (rowData.length) {
			for (var i = 0; i < rowData.length; i++) {
				trainNumberIds[i] = jQuery(jqgridTableYes).jqGrid('getCell',
						rowData[i], 'id');// name是colModel中的一属性
			}
		} else {
			alertMsgERROR("请选择一条数据！");
			return;
		}
	}
	trainNumberView.trainNumberIds = trainNumberIds;
	trainNumberView.directionId = null;
	restfulAjax("api/trainnumber/updateTrainNumberByDirectionId", JSON
			.stringify(trainNumberView), succFunction);
	function succFunction(data) {
		if (data.code == 0) {
			alertMsgOK(data.msg);
		} else {
			alertMsgERROR(data.msg);
		}
		cgrid.queryGrid(jqgridTableYes, queryLoadUrl,
				serializeObject('viewquery-form'));
	}

}

/**
 * 点击查询按钮操作，展示查询结果列表
 */
$("#viewquery-button").on("click", queryButton_click);
function queryButton_click() {
	// serializeObject('query_form') 获取查询表单的查询条件参数
	cgrid.queryGrid(jqgridTableYes, queryLoadUrl,
			serializeObject('viewquery-form'));
}

/**
 * 重置按钮
 */
$("#viewquery-reset-button").on("click", queryResetButton_click);
function queryResetButton_click() {
	// 初始化SELECT信息
	querySelectInit(selectData);
}

/*---------------------------------方向没有的车次-------------------------------------*/

/**
 * 初始化JQGRID 使用本地数据类型，默认不加载数据 datatype : 'local'
 */
function queryNotJqgridInit() {
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
	cgrid.initCudGrid(jqgridTableNot, jqgridPagerNot, queryNotLoadUrl,
			jqgridEditUrl, colNames, colModel, true, "id12");
	/*--添加编辑、删除、查看功能图标--*/
	jQuery(jqgridTableNot).navButtonAdd(jqgridPagerNot, {
		caption : "",
		buttonicon : "icon-plus-sign purple",
		title : "添加",
		onClickButton : addTrainNo,
		position : "last"
	});
	cgrid.resize(jqgridTableNot);
}

/**
 * 添加没有车次
 */
function addTrainNo() {
	var radioValue;
	var trainNumberView = new Object();
	var obj = document.getElementsByName("notInRadio"); // 这个是以标签的name来取控件
	for (i = 0; i < obj.length; i++) {
		if (obj[i].checked) {
			radioValue = obj[i].value;
		}
	}
	if (radioValue == 1) {
		// 获取多选到的id集合
		var rowData = jQuery(jqgridTableNot)
				.jqGrid('getGridParam', 'selarrrow');
		var trainNumberIds = new Array();
		if (rowData.length) {
			for (var i = 0; i < rowData.length; i++) {
				trainNumberIds[i] = jQuery(jqgridTableNot).jqGrid('getCell',
						rowData[i], 'id');// name是colModel中的一属性
			}
		} else {
			alertMsgERROR("请选择一条数据！");
			return;
		}
	}
	trainNumberView.trainNumberIds = trainNumberIds;
	trainNumberView.directionId = $("#directionId").val();
	restfulAjax("api/trainnumber/updateTrainNumberByDirectionId", JSON
			.stringify(trainNumberView), succFunction);
	function succFunction(data) {
		if (data.code == 0) {
			alertMsgOK(data.msg);
		} else {
			alertMsgERROR(data.msg);
		}
		cgrid.queryGrid(jqgridTableNot, queryNotLoadUrl,
				serializeObject('viewquery-form'));
	}

}


/**
 * 点击查询按钮操作，展示查询结果列表
 */
$("#querynot-button").on("click", queryButtonNot_click);
function queryButtonNot_click() {
	// serializeObject('query_form') 获取查询表单的查询条件参数
	cgrid.queryGrid(jqgridTableNot, queryNotLoadUrl,
			serializeObject('viewquery-form'));
}

/**
 * 重置按钮
 */
$("#querynot-reset-button").on("click", queryResetButtonNot_click);
function queryResetButtonNot_click() {
	// 初始化SELECT信息
	querySelectInit1(selectData);
}

/*---------------------------------事件-------------------------------------*/

/**
 * 返回按钮操作，返回查询页面
 */
$("#view-back-button").on("click", viewBackButton_click);
function viewBackButton_click() {
	$("#select_userid").val(""); //清空userid
	$('#mng-div').addClass('hide');
	$('#mng-div').empty();
	$('#query-div').show();
	cgrid.resize("#query-grid-table");
}
/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function() {
	// 查询表单数据
	$("#directionId").val($("#select_userid").val());
	viewQuerySuccess();
	querySelectData(); // 初始化SELECT信息
	queryJqgridInit();// 查询已有GRID初始化
	queryNotJqgridInit();// 查询没有GRID初始化
});

/*----------------------------------end-------------------------------------*/
