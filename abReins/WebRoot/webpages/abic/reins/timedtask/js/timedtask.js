/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 源用户查询URL */
var queryDataUrl = "api/timedtask/querytimedtask";

var editDataUrl="api/timedtask/edittimedtask";

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/

/**
 * 根据ID 查询用户基本信息
 */
function queryDataInfo() {
	var timedTask=new Object();
	timedTask.id=1;
	$.ajax({
		url : queryDataUrl,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" :ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(timedTask),
		success : function(res) {
			var num=1;
			for(var i=0;i<res.data.list.length;i++){
				$("#data"+num).val(res.data.list[i].timedtaskDate);
				initDateInput("data"+num);
				num++;
			}
		}
	})
}

/**
 * 保存日期信息
 * 
 * @param data
 */
function editStationData(monthId,monthData,yearId,yearData) {
	var taskView=new Object();
	taskView.monthId=monthId;
	taskView.monthDate=monthData;
	taskView.yearId=yearId;
	taskView.yearDate=yearData;
	$.ajax({
		url:editDataUrl,
		headers : {
			"X-Auth-Token" : "open-sesame",
			"Content-Type" : "application/json",
			"Oauth-Token" :ReadCookie("oauth-token")
		},
		contentType : "application/json",
		type : "POST",
		dataType : "json",
		data : JSON.stringify(taskView),
		success : function(res) {
			if(res.code==0){
				alertMsgOK("定时日期修改成功！");
				queryDataInfo();
			}else if(res.code==8){
				alertMsgERROR("权限校验失败!");
			}
		}
	})
}

/**
 * form表单必录项校验规则初始化
 */
function editValidataInit() {
	var stationRules = {
		stationMonth : 'required',
		stationYear : 'required'
	};
	var stationMessages = {
		stationMonth : "站段月统计日期不能为空！",
		stationYear : "站段年统计日期不能为空！"
	};
	stationValidate = buildValidate("edit-station", stationRules,
			stationMessages);

	var TStationRules = {
		TStationMonth : 'required',
		TStationYear : 'required'
	};
	var TStationMessages = {
		TStationMonth : "售票站月统计日期不能为空！",
		TStationYear : "售票站年统计日期不能为空！"
	};
	TStationValidate = buildValidate("edit-TStation", TStationRules,
			TStationMessages);

	var trainRules = {
		trainMonth : 'required',
		trainYear : 'required'
	};
	var trainMessages = {
		trainMonth : "车次月统计日期不能为空！",
		trainYear : "车次年统计日期不能为空！"
	};
	trainValidate = buildValidate("edit-train", trainRules, trainMessages);
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 站段保存
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#edit-save-station").on("click", editSaveStation_click);
function editSaveStation_click() {
	if (stationValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var monthId=1;
		var monthDate=$("#data1").val();
		var yearId=2;
		var yearData=$("#data2").val();
		editStationData(monthId,monthDate,yearId,yearData);
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/**
 * 售票站保存
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#edit-save-TStation").on("click", editSaveTStation_click);
function editSaveTStation_click() {
	if (TStationValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var monthId=3;
		var monthDate=$("#data3").val();
		var yearId=4;
		var yearData=$("#data4").val();
		editStationData(monthId,monthDate,yearId,yearData);
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/**
 * 车次保存
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#edit-save-train").on("click", editSaveTrain_click);
function editSaveTrain_click() {
	if (trainValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var monthId=5;
		var monthDate=$("#data5").val();
		var yearId=6;
		var yearData=$("#data6").val();
		editStationData(monthId,monthDate,yearId,yearData);
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function() {
	queryDataInfo();
	// 初始化表单验证
	editValidataInit();
});

/*----------------------------------end-------------------------------------*/
