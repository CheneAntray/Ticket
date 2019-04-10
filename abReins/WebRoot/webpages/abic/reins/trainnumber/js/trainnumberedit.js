/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 源用户查询URL */
var editQueryUrl = "api/security/queryuserbyid/";
/** 用户保存URL */
var editSaveUrl = "api/security/saveuser/";
/** 根据id查询车次信息*/
var queryTrainNumberByIdUrl="api/trainnumber/queryTrainNumberById";
/** 修改车次信息*/
var editTrainNumberUrl="api/trainnumber/editTrainNumberUrl";

/** 表单校验对象 */
var editValidate;

var startStationId;
var endStationId;
var uepId;


//担当企业下拉框
var querySelectUnderURL = "api/querySelectData/querySelectData";
//起始站及终点站下拉框
var querySelectStationURL = "api/querySelectData/queryTrainStationSelectData";

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/
//车站下拉框
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
	chosenInitUepData("edit_underTake", data.data.obj.uep);
}

function querySelectInit(data) {
	// dict_yesorno 在index页面初始化
	// common.js里的方法，详见common.js
	chosenInitStartStationData("edit_startStation", data.data.list);
	chosenInitEndStationData("edit_endStation", data.data.list);
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
		if (data[i].id != startStationId) {
			$("<option value='" + data[i].id + "'>" + data[i].name
					+ "</option>").appendTo("#" + selectid);
		}else{
			$("<option value='" + data[i].id + "' selected='selected'>" + data[i].name
					+ "</option>").appendTo("#" + selectid);
		}
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
		if (data[i].id != endStationId) {
			$("<option value='" + data[i].id + "'>" + data[i].name
					+ "</option>").appendTo("#" + selectid);
		}else{
			$("<option value='" + data[i].id + "' selected='selected'>" + data[i].name
					+ "</option>").appendTo("#" + selectid);
		}
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
		if (data[i].id != uepId) {
			$("<option value='" + data[i].id + "'>" + data[i].name
					+ "</option>").appendTo("#" + selectid);
		}else{
			$("<option value='" + data[i].id + "' selected='selected'>" + data[i].name
					+ "</option>").appendTo("#" + selectid);
		}
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
	});

}

/**
 * 调用查询单笔数据返回数据填充
 * 
 * @param data
 */
function queryTrainNumberById()
{
	var id=$("#select_trainNumberId").val();
	var trainNumberView=new Object();
	trainNumberView.id=id;
	restfulAjax(queryTrainNumberByIdUrl, JSON.stringify(trainNumberView), function(res) {
		$("#edit_trainNumber").html(res.data.obj.trainNo);
		startStationId = res.data.obj.startStationId;
		endStationId = res.data.obj.endStationId;
		uepId = res.data.obj.underEnterId;
	});
}

function editTrainNumber(){
	var trainNumberView=new Object();
	trainNumberView.trainNo=$("#edit_trainNumber").html();
	trainNumberView.startStationId=$("#edit_startStation").val();
	trainNumberView.endStationId=$("#edit_endStation").val();
	trainNumberView.underEnterId=$("#edit_underTake").val();
	$.ajax({
		url : editTrainNumberUrl,
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
			if(res.code==0){
				alertMsgOK("车次信息修改成功！");
			}
		}
	})
}

/**
 * 保存数据成功信息提示
 * 
 * @param data
 */
function editSaveSuccess(data)
{
    // 保存成功返回查询页面
    editBackButton_click();
    // 弹出提示 alertMsgOK (ajaxjson.js里)
    alertMsgOK("保存数据成功！");
    // query页面的方法
    queryButton_click();
}

/**
 * form表单必录项校验规则初始化
 */
function editValidataInit()
{
    var validateRules =
    {
		edit_startStation : 'required',
		edit_endStation : 'required',
		edit_underTake : 'required'
	};
	var validateMessages = {
		edit_startStation : '请选择起始站！',
		edit_endStation : '请选择终点站！',
		edit_underTake : "请选择担当企业！",
    };
    editValidate = buildValidate("edit-form", validateRules, validateMessages);
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 返回按钮操作，返回查询页面
 */
$("#edit-back-button").on("click", editBackButton_click);
function editBackButton_click()
{
    $("#select_userid").val(""); // 清空userid
    $('#mng-div').addClass('hide');
    $('#mng-div').empty();
    $('#query-div').show();
    cgrid.resize("#query-grid-table");
}


/**
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#edit-save-button").on("click", editSaveButton_click);
function editSaveButton_click()
{
    if (editValidate.form())
    {
	// 通过表单验证，以下编写自己的代码
	editTrainNumber();
    } else
    {
	// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
	$("#btn-scroll-up").click();
    }
}


/* 是否按钮变更值 */
$("#edit_activeflag_yncheck").on("click", editActiveflagYncheck_click);
function editActiveflagYncheck_click(){
    checkedYesNo_click("edit_activeflag");
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function()
{
	queryTrainNumberById(); // 编辑页面查询数据初始化
	querySelectUnder();
	querySelectStationData();
    editValidataInit(); // 初始化form表单必录项校验规则
});

/*----------------------------------end-------------------------------------*/
