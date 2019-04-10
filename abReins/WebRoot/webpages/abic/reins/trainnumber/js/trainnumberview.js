/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 根据id查询车次信息 */
var queryTrainNumberByIdUrl = "api/trainnumber/queryTrainNumberById";

// 担当企业下拉框
var querySelectUnderURL = "api/querySelectData/querySelectData";
// 起始站及终点站下拉框
var querySelectStationURL = "api/querySelectData/queryTrainStationSelectData";
var startStationId;
var endStationId;
var uepId;

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/
// 车站下拉框
function querySelectUnder() {
	restfulAjax(querySelectUnderURL, "", succFunctionUnder);
}
function succFunctionUnder(data) {
	selectData = data;
	querySelectInitUnder(data);
}

function querySelectStationData() {
	restfulAjax(querySelectStationURL, "", succFunctionStation);
}
function succFunctionStation(data) {
	selectData = data;
	querySelectInit(data);
}

/**
 * 页面下拉框初始化赋值
 */
function querySelectInitUnder(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	chosenInitUepData("view_underTake", data.data.obj.uep);
}

function querySelectInit(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	chosenInitStartStationData("view_startStation", data.data.list);
	chosenInitEndStationData("view_endStation", data.data.list);
}

/**
 * 初始化起始站框数据信息
 * 
 */
function chosenInitStartStationData(selectid, data, multiple, notNullOption) {
	var multiple = multiple || false;
	$("#" + selectid).html("");
	$("#" + selectid).chosen("destroy");
	if (!notNullOption) {
		$("<option value=''> </option>").appendTo("#" + selectid);
	}

	$.each(data, function(i) {
		if (data[i].id == startStationId) {
			$("<option value='" + data[i].id + "' selected='selected'>" + data[i].name
							+ "</option>").appendTo("#" + selectid);
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
			return;
		}
	});

}

/**
 * 初始化终到站框数据信息
 * 
 */
function chosenInitEndStationData(selectid, data, multiple, notNullOption) {
	var multiple = multiple || false;
	$("#" + selectid).html("");
	$("#" + selectid).chosen("destroy");
	if (!notNullOption) {
		$("<option value=''> </option>").appendTo("#" + selectid);
	}

	$.each(data, function(i) {
		if (data[i].id == endStationId) {
			$("<option value='" + data[i].id + "' selected='selected'>" + data[i].name
							+ "</option>").appendTo("#" + selectid);
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
			return;
		}
	});

}

/**
 * 初始化担当企业框数据信息
 * 
 */
function chosenInitUepData(selectid, data, multiple, notNullOption) {
	var multiple = multiple || false;
	$("#" + selectid).html("");
	$("#" + selectid).chosen("destroy");
	if (!notNullOption) {
		$("<option value=''> </option>").appendTo("#" + selectid);
	}

	$.each(data, function(i) {
		if (data[i].id == uepId) {
			$("<option value='" + data[i].id + "' selected='selected'>" + data[i].name
							+ "</option>").appendTo("#" + selectid);
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
			return;
		}
	});

}

/**
 * 调用查询单笔数据返回数据填充
 * 
 * @param data
 */
function queryTrainNumberById() {
	var id = $("#select_trainNumberId").val();
	var trainNumberView = new Object();
	trainNumberView.id = id;
	$.ajax({
		url : queryTrainNumberByIdUrl,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(trainNumberView),
		success : function(res) {
			$("#view_trainNumber").html(res.data.obj.trainNo);
			startStationId = res.data.obj.startStationId;
			endStationId = res.data.obj.endStationId;
			uepId = res.data.obj.underEnterId;
		}
	})
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
	//为下拉框添加不可更改样式
	$("#view_startStation").attr("disabled","disabled").css("background-color","#EEEEEE;");
	$("#view_endStation").attr("disabled","disabled").css("background-color","#EEEEEE;");
	$("#view_underTake").attr("disabled","disabled").css("background-color","#EEEEEE;");
	queryTrainNumberById(); // 编辑页面查询数据初始化
	querySelectUnder();
	querySelectStationData();
});

/*----------------------------------end-------------------------------------*/
