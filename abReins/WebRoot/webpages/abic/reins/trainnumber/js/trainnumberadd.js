/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 表单校验对象 */
var addValidate;
var addTrainNumberUrl="api/trainnumber/addTrainNumberInfo";

// 担当企业下拉框
var querySelectUnderURL = "api/querySelectData/querySelectData";
// 起始站及终点站下拉框
var querySelectStationURL = "api/querySelectData/queryTrainStationSelectData";

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
	chosenInitData("add_underTake", data.data.obj.uep);
}

function querySelectInit(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	chosenInitData("add_startStation", data.data.list);
	chosenInitData("add_endStation", data.data.list);
}

function addTrainNumber() {
	var trainNumberView = new Object();
	trainNumberView.trainNo = $("#add_trainNumber").val();
	trainNumberView.startStationId = $("#add_startStation").val();
	trainNumberView.endStationId = $("#add_endStation").val();
	trainNumberView.underEnterId = $("#add_underTake").val();
	$.ajax({
		url : addTrainNumberUrl,
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
			if (res.code == 0) {
				alertMsgOK("车次信息添加成功！");
			}
		}
	})
}

/**
 * form表单必录项校验规则初始化
 */
function addValidataInit() {
	var validateRules = {
		add_trainNumber : 'required',
		add_startStation : 'required',
		add_endStation : 'required',
		add_underTake : 'required'
	};
	var validateMessages = {
		add_trainNumber : '请填写车次编号！',
		add_startStation : '请选择起始站！',
		add_endStation : '请选择终点站！',
		add_underTake : "请选择担当企业！",
	};
	addValidate = buildValidate("add-form", validateRules, validateMessages);
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 返回按钮操作，返回查询页面
 */
$("#add-back-button").on("click", addBackButton_click);
function addBackButton_click() {
	$("#select_userid").val(""); // 清空userid
	$('#mng-div').addClass('hide');
	$('#mng-div').empty();
	$('#query-div').show();
	cgrid.resize("#query-grid-table");
}

/**
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#add-save-button").on("click", addSaveButton_click);
function addSaveButton_click() {
	if (addValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		 addTrainNumber();
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function() {
	querySelectUnder();
	querySelectStationData();
	addValidataInit(); // 初始化form表单必录项校验规则
});

/*----------------------------------end-------------------------------------*/
