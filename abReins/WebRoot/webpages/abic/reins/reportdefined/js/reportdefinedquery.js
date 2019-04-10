/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
//模版编号
var reportCode;
// 模版名称
var reportName;
// 起始时间
var startDate;
// 结束时间
var endDate;
// 车次方向
var directionValue;
// 车次编码
var trainNumber;
// 车站id
var stationId;
// 售票站 id
var tickStationId;

/** 查询下拉框的=URL */
// 车次方向下拉框
var querySelectDataURL = "api/querySelectData/queryTrainDirectionSelect";

// 车站下拉框
var querySelectDataStationURL = "api/querySelectData/querySelectData";

// 报表下拉框
var querySelectDataReportURL = "api/querySelectData/queryReportInfoSelect";

/** 下拉框数据 */
var directionSelect = null;
var stationSelect = null;
var reportSelect = null;

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/
function querySelectStationUrl() {
	restfulAjax(querySelectDataStationURL, "", succFunctionStation);
}
function succFunctionStation(data) {
	stationSelect = data;
	querySelectStation(data);
}

/**
 * 页面下拉框初始化赋值
 */
function querySelectStation(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	chosenInitData("select_stationId", data.data.obj.stationSection);
	chosenInitData("select_tickStationId", data.data.obj.ticketStation);
}

// 方向下拉框
function querySelectData() {
	restfulAjax(querySelectDataURL, "", succFunction);
}
function succFunction(data) {
	directionSelect = data;
	querySelectInit(data);
}
/**
 * 页面下拉框初始化赋值
 */
function querySelectInit(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	directionInitData("select_direction", data.data.list);
}
// 报表下拉框
function querySelectReport() {
	restfulAjax(querySelectDataReportURL, "", succFunctionReport);
}
function succFunctionReport(data) {
	reportSelect = data;
	querySelectReportInit(data);
}
/**
 * 页面下拉框初始化赋值
 */
function querySelectReportInit(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	reportInitData("report_code", data.data.list);
}

function directionInitData(selectid, data, multiple, notNullOption) {
	var multiple = multiple || false;
	$("#" + selectid).html("");
	$("#" + selectid).chosen("destroy");
	if (!notNullOption) {
		$("<option value=''> </option>").appendTo("#" + selectid);
	}

	$.each(data, function(i) {
		$(
				"<option value='" + data[i].id + "'>" + data[i].directionName
						+ "</option>").appendTo("#" + selectid);
	});
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

// 报表下拉框
function reportInitData(selectid, data, multiple, notNullOption) {
	var multiple = multiple || false;
	$("#" + selectid).html("");
	$("#" + selectid).chosen("destroy");
	if (!notNullOption) {
		$("<option value=''> </option>").appendTo("#" + selectid);
	}

	$.each(data, function(i) {
		$(
				"<option value='" + data[i].reportCode + "'>"
						+ data[i].reportName + "</option>").appendTo(
				"#" + selectid);
	});
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
 * 站段下拉框更改事件
 */
$("#select_stationId").on("change", query_stationsection_change);
function query_stationsection_change() {
	var id = $("#select_stationId").val();
	if (id == null || id == "") {
		id = "null";
	}
	$
			.ajax({
				url : "api/querySelectData/queryTicketListByStationSectionId",
				headers : {
					"X-Auth-Token" : "open-sesame",
					"Content-Type" : "application/json",
					"Oauth-Token" : ReadCookie("oauth-token")
				},
				contentType : "application/json",
				type : "POST",
				dataType : "json",
				data : id,
				success : function(data) {
					chosenInitData("select_tickStationId",
							data.data.obj.ticketStation);
				}
			})

}
/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/
/**
 * 当鼠标离开报表编号时
 */
function reportCode_Change() {
	var select = document.getElementById("report_code");
	var reportCode = select.options[select.selectedIndex].value;
	if (reportCode == "001") {
		$("#direction").show();
		$("#station").hide();
	} else if (reportCode == "002") {
		$("#station").show();
		$("#direction").hide();
	}
}

function checkAll(){
	
	var check=false;
	
	startDate = $("#start_date").val();
	if (startDate == null || startDate == "") {
		startDate = "0";
	}

	endDate = $("#end_date").val();
	if (endDate == null || endDate == "") {
		endDate = "0";
	}

	directionValue = $("#select_direction").val();
	if (directionValue == null || directionValue == "") {
		directionValue = "0";
	}

	trainNumber = $("#trainNumber_direction").val();
	if (trainNumber == null || trainNumber == "") {
		if ($("#trainNumber_station").val() == null
				|| $("#trainNumber_station").val() == "") {
			trainNumber = "0";
		} else {
			trainNumber = $("#trainNumber_station").val();
		}
	}

	stationId = $("#select_stationId").val();
	if (stationId == null || stationId == "") {
		stationId = "0";
	}

	tickStationId = $("#select_tickStationId").val();
	if (tickStationId == null || tickStationId == "") {
		tickStationId = "0";
	}

	reportName = $('#report_code option:selected').text();

	reportCode = $("#report_code").val();
	if (reportCode != null && reportCode != "") {
		check=true;
	} else {
		alertMsgERROR("请选择报表!");
	}
	return check;
}
/**
 * 点击查询按钮
 */
$("#query-button").on("click", queryButton_click);
function queryButton_click() {
		reportPDFButton_click();
		iframe();
}
/**
 * 点击pdf报表模版按钮
 */
$("#report-PDF-button").on("click", reportPDFButton_click);
function reportPDFButton_click() {
	if (checkAll()!=false) {
	document.getElementById('reportIframe').src = 'api/report/model/'
			+ reportCode + '/' + reportName + '/' + startDate + '/' + endDate
			+ '/' + directionValue + '/' + trainNumber + '/' + stationId + '/'
			+ tickStationId + '/pdf';
	}
}
/**
 * 点击html报表模版按钮
 */
$("#report-HTML-button").on("click", reportHTMLButton_click);
function reportHTMLButton_click() {
	if (checkAll()!=false) {
	document.getElementById('reportIframe').src = 'api/report/model/'
			+ reportCode + '/' + reportName + '/' + startDate + '/' + endDate
			+ '/' + directionValue + '/' + trainNumber + '/' + stationId + '/'
			+ tickStationId + '/html';
	}
}
/**
 * 点击execl报表模版下载按钮
 */
$("#report-EXECL-button").on("click", reportEXLButton_click);
function reportEXLButton_click() {
	if (checkAll()!=false) {
	document.getElementById('reportIframe').src = 'api/report/model/'
			+ reportCode + '/' + reportName + '/' + startDate + '/' + endDate
			+ '/' + directionValue + '/' + trainNumber + '/' + stationId + '/'
			+ tickStationId + '/xlsx';
	}
}
/**
 * 重置按钮
 */
$("#query-reset-button").on("click", queryResetButton_click);
function queryResetButton_click() {
	// 初始化SELECT信息
	querySelectInit(directionSelect);
	querySelectStation(stationSelect);
	querySelectReport();
}

function iframe() {
	var iframe = document.getElementById("reportIframe");
	if (!0) {//判断是不是IE
		iframe.onload = function() {
			alertMsgOK("数据加载完毕！");
		};
	} else {
		iframe.onreadystatechange = function() {
			if (iframe.readyState == "complete") {
				alertMsgOK("数据加载完毕！");
			}
		};
	}
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/
$(document).ready(function() {
	// 页面加载先隐藏条件
	$("#direction").hide();
	$("#station").hide();
	// 初始化SELECT信息
	querySelectData();
	querySelectStationUrl();
	querySelectReport();
	initDateInput("start_date");// 初始化日期控件
	initDateInput("end_date");// 初始化日期控件
});
/*----------------------------------end-------------------------------------*/
