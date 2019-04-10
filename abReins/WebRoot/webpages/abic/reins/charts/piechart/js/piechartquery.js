/*------------------------------页面属性集 -------------------------------- */
/** 查询折线图数据 */
var charDataUrl = "api/chart/queryPieChart";
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
	chosenInitData("sectionStation", data.data.obj.stationSection);
}
/**
 * 初始化月日期控件
 * 
 * @param id
 */
function initDateInputOfMonth(id) {
	$("#" + id).datepicker({
		autoclose : true,
		format : "mm",
		datepicker : false,
		minViewMode : 1,
		maxViewMode : 1,
		minViewMode : 1,
		todayBtn : true,
		todayHighlight : true,
		language : 'zh-CN'
	}).next().on(ace.click_event, function() {
		$(this).prev().focus();
	});
}

/**
 * 初始化年日期控件
 * 
 * @param id
 */
function initDateInputOfYear(id) {
	$("#" + id).datepicker({
		autoclose : true,
		format : "yyyy",
		minViewMode : 2,
		todayBtn : true,
		todayHighlight : true,
		language : 'zh-CN'
	}).next().on(ace.click_event, function() {
		$(this).prev().focus();
	});
}

function initDateControl() {
	// 初始化售票站车次日期
	initDateInputOfMonth("startmonth");
	initDateInputOfMonth("endmonth");
	initDateInputOfYear("startdate");
	initDateInputOfYear("enddate");
}



/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/
function initCharts(data) {
	$("#myTable").html("");
	var beforeStr="";
	var endStr="<tbody><tr><th></th>";
	beforeStr+="<thead><tr><th></th>";
	for(var i=0; i<Object.keys(data).length; i++){
		beforeStr+="<th>"+data[i].label+"</th>";
		endStr+="<td>"+data[i].value+"</td>";
	}
	beforeStr+="</tr></thead>";
	endStr+="</tr></tbody>";
	document.getElementById('myTable').innerHTML =beforeStr+endStr;
	gvChartInit();
	$('#myTable').gvChart({
		chartType: 'PieChart',
		gvSettings: {
			vAxis: {title: 'No of players'},
			hAxis: {title: 'Month'},
			width: 900,
			height: 530
		}
	});
	
}

/**
 * 点击查询按钮操作，展示查询结果列表
 */
$("#query-button").on("click", queryButton_click);
function queryButton_click() {
	// // 判断查询起期是否大于查询止期
	var startDate = $("#startdate").val();
	var endDate = $("#enddate").val();
	var startmonth = $("#startmonth").val();
	var endmonth = $("#endmonth").val();
    if(startDate!="" && endDate=="" &&startmonth==""&&endmonth==""){
    	alertMsgINFO("终止年份不能为空");
		return;
    }
    if(startDate!="" && startmonth!=""&& endmonth==""){
    	alertMsgINFO("终止月份不能为空");
		return;
    }
    if(startDate!="" &&endmonth!=""&&startmonth==""){
    	alertMsgINFO("起始月份不能为空");
		return;
    }
	if (endDate != null && endDate != "") {
		if (startDate > endDate) {
			alertMsgINFO("起始年份不能大于终止年份");
			return;
		}
	}
	if (endmonth != null && endmonth != "") {
		if (startmonth > endmonth) {
			alertMsgINFO("起始月份份不能大于终止月份");
			return;
		}
	}
	queryChartsSets();

}
/**
 * 查询统计值
 */
function queryChartsSets() {
	restfulAjax(charDataUrl, JSON.stringify(serializeObject('query-form')),
			queryChartsSetsSucc);
}

function queryChartsSetsSucc(data) {
	if(data.httpStatus==1){
		$("#myTable").html("");
		alertMsgINFO("没有数据显示");
		
	}
	initCharts(data.data.list);
}

/**
 * 重置按钮
 */
$("#query-reset-button").on("click", queryResetButton_click);
function queryResetButton_click() {
	// 初始化SELECT信息
	querySelectInit(selectData);
	initDateControl();
}

/*----------------------------------end------------------------------------- */


/* ---------------------------------初始化----------------------------------- */
$(document).ready(function() {
	initDateControl();
	querySelectData(); // 初始化SELECT信息
});

/*----------------------------------end------------------------------------- */


