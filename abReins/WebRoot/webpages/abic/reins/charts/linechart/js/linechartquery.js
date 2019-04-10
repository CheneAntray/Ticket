/*------------------------------页面属性集 -------------------------------- */
/** 查询折线图数据 */
var charDataUrl = "api/chart/queryLineChart";
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
	chosenInitData("ticketStation", data.data.obj.ticketStation);
}

/**
 * 站段下拉框更改事件
 */
$("#sectionStation").on("change", sectionStation_change);
function sectionStation_change() {
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
			chosenInitData("ticketStation", data.data.obj.ticketStation);
		}
	})

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

function getCharsData() {
	var dataArray = new Array();
	var conditionView = new Object();
	var chartsDataView = new Object();
	conditionView.conditionId = 1;
	conditionView.startTime = $("#starttime_1").val();
	conditionView.endTime = $("#endtime_1").val();
	dataArray.push(conditionView);
	if (document.getElementById('div_2') != null) {
		conditionView = new Object();
		conditionView.conditionId = 2;
		conditionView.startTime = $("#starttime_2").val();
		conditionView.endTime = $("#endtime_2").val();
		dataArray.push(conditionView);
		if (document.getElementById('div_3') != null) {
			conditionView = new Object();
			conditionView.conditionId = 3;
			conditionView.startTime = $("#starttime_3").val();
			conditionView.endTime = $("#endtime_3").val();
			dataArray.push(conditionView);
		}
	}
	chartsDataView.conditionViews = dataArray;
	$.ajax({
		url : charDataUrl,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" : ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(chartsDataView),
		traditional : true,
		success : function(res) {
			alert("成功");
		}
	})
}

/**
 * 添加查询条件
 * 
 * @param i
 * @returns
 */
function addSelect(i) {
	var div = "";
	div += "<div class='col-xs-12 ' id='div_"
			+ i
			+ "'>"
			+ "<div class='col-xs-12 col-sm-4 '>"
			+ "<label for='form-field-mask-4 ' class='lab1'> 条件：</label>"
			+ "<div class='col-xs-12 col-sm-12 col-md-8'>"
			+ "<select class='form-control' id='tx_"
			+ i
			+ "'"
			+ " name='stationSectionId' data-placeholder='请选择...' />"
			+ "</div>"
			+ "</div>"
			+ "<div class='col-xs-12 col-sm-4'>"
			+ "<label for='form-field-mask-4 ' class='lab1'>查询时间： </label>"
			+ "<div class='col-xs-12 col-sm-12 col-md-8' style='padding-left:0px;'>"
			+ "<div class='input-group'>"
			+ "<input class='form-control date-picker' id='starttime_"
			+ i
			+ "'"
			+ " name='starttime_"
			+ i
			+ "'"
			+ " type='text' data-date-format='yyyy-mm-dd'  style='width:95%;margin-left:4.5%'/>"
			+ "<span class='input-group-addon'>	"
			+ "<i class='icon-calendar bigger-110'></i>"
			+ "</span>"
			+ "</div>"
			+ "</div>"
			+ "</div>"
			+ "<div class='col-xs-12 col-sm-4'> "
			+ "<label for='form-field-mask-4 ' class='lab1'>至 </label>"
			+ "<div class='col-xs-12 col-sm-12 col-md-8' style='padding-left:0px;'>"
			+ "<div class='input-group'>"
			+ "<input class='form-control date-picker' id='endtime_"
			+ i
			+ "'"
			+ " name='endtime_"
			+ i
			+ "'"
			+ " type='text' data-date-format='yyyy-mm-dd' style='width:95%;margin-left:4.5%'/>"
			+ "<span class='input-group-addon'>"
			+ "<i class='icon-calendar bigger-110'></i>" + "</span>" + "</div>"
			+ "</div>" + "<button id='remove-select-button'"
			+ "class='btn btn-primary btn-sm ' type='button'"
			+ "style='height: 36px; width: 36px;' onclick=\"$('#div_" + i
			+ "').remove();\">" + "<i class='icon-remove bigger-110'></i>"
			+ "</button>" + "</div> " + "</div> ";
	$("#select").append(div);
}
/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/
function initCharts(data) {
	$("#aa").html("");
	$("#aa").append("<div id='legend'></div><canvas id='bar'></canvas>");
	var ctx = document.getElementById("bar").getContext("2d");
	//判断y轴刻度是否带小数
	var avgNum;
	var radioValue;
	var trainNumberView = new Object();
	var maxNum = 0; // 用于保存数据最大值
	var obj = document.getElementsByName("radio"); // 这个是以标签的name来取控件
	var array = data.datasets;
	for(let item of array) {  
		for (var i = 0; i < item.data.length; i++) {
			if(maxNum<item.data[i]){
				maxNum=item.data[i];
			}
		}
	}
	for (i = 0; i < obj.length; i++) {
		if (obj[i].checked) {
			radioValue = obj[i].value;
		}
	}
	if (radioValue == 2) {
		avgNum=Math.ceil(maxNum/10);
	}else{
		avgNum=maxNum/10;
	}
	var myNewChart = new Chart(ctx).Line(data,
			{
		datasetFill : false, // 是否显示填充色
		scaleShowGridLines : true, // 是否显示网格线
		scaleGridLineColor : "rgba(0,0,0,.05)", // 网格线的颜色
		bezierCurve : false, // 是否是曲线
		scaleSteps: 10,     //y轴刻度个数
		scaleStepWidth: avgNum,    //每次增长值
		scaleStartValue: 0,    //y轴起始值
		legendTemplate : '<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><%if(datasets[i].label){%><span style=\"background-color:<%=datasets[i].fillColor%>\"></span><%=datasets[i].label%><%}%></li><%}%></ul>'
	});
	// 图例
	var legend = myNewChart.generateLegend();
	document.getElementById('legend').innerHTML = legend;
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
    if(startDate!="" &&startmonth!=""&&endmonth==""){
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
		$("#aa").html("");
		alertMsgINFO("没有数据显示");
		return;
	}
	initCharts(data.data.obj);
}
/**
 * 重置按钮
 */
$("#query-reset-button").on("click", queryResetButton_click);
function queryResetButton_click() {
	// // 初始化SELECT信息 
	initDateControl();
	querySelectData(); // 初始化SELECT信息
}
/**
 * 点击添加条件按钮
 */
$("#add-select-button").on("click", addSelectButton_click);
function addSelectButton_click() {
	if (document.getElementById('div_2') == null) {
		addSelect(2);
		initDateInput("starttime_" + 2);// // 初始化日期控件 i
		initDateInput("endtime_" + 2);// // 初始化日期控件 i
	} else if (document.getElementById('div_3') == null) {
		addSelect(3);
		initDateInput("starttime_" + 3);// // 初始化日期控件 i
		initDateInput("endtime_" + 3);// // 初始化日期控件 i
	} else {
		alertMsgINFO("无法增加太多查询条件！");
	}
}

/*----------------------------------end------------------------------------- */


/* ---------------------------------初始化-----------------------------------*/
$(document).ready(function() {
	initDateControl();
	// initCharts();
	querySelectData(); // 初始化SELECT信息
});

/*----------------------------------end------------------------------------- */

