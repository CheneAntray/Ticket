/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 执行补跑url */
var runupOfTicketTrainByMonthURL = "api/timedtask/ticketTrainNoOfMonth";
var runupOfTicketTrainByYearURL = "api/timedtask/ticketTrainNoOfYear";
var runupOfTicketByDayURL = "api/timedtask/ticketOfDay";
var runupOfTicketByMonthURL = "api/timedtask/ticketOfMonth";
var runupOfTicketByYearURL = "api/timedtask/ticketOfYear";
var runupOfSectionByDayURL = "api/timedtask/sectionOfDay";
var runupOfSectionByMonthURL = "api/timedtask/sectionOfMonth";
var runupOfSectionByYearURL = "api/timedtask/sectionOfYear";

/** form表单ID */
var ticktrainnumberOfmonthForm = "ticktrainnumber_ofmonth_form";
var ticktrainnumberOfyearForm = "ticktrainnumber_ofyear_form";
var ticketOfdayForm = "ticket_ofday_form";
var ticketOfmonthForm = "ticket_ofmonth_form";
var ticketOfyearForm = "ticket_ofyear_form";
var sectionOfdayForm = "section_ofday_form";
var sectionOfmonthForm = "section_ofmonth_form";
var sectionOfyearForm = "section_ofyear_form";

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/

function initDateControl() {
	//初始化售票站车次日期
	initDateInputOfMonth("ticktrainnumber_ofmonth_startDate");
	initDateInputOfMonth("ticktrainnumber_ofmonth_endDate");
	initDateInputOfYear("ticktrainnumber_ofyear_startDate");
	initDateInputOfYear("ticktrainnumber_ofyear_endDate");
	//初始化售票站日期
	initDateInput("ticket_ofday_startDate");
	initDateInput("ticket_ofday_endDate");
	initDateInputOfMonth("ticket_ofmonth_startDate");
	initDateInputOfMonth("ticket_ofmonth_endDate");
	initDateInputOfYear("ticket_ofyear_startDate");
	initDateInputOfYear("ticket_ofyear_endDate");
	//初始化站段日期
	initDateInput("section_ofday_startDate");
	initDateInput("section_ofday_endDate");
	initDateInputOfMonth("section_ofmonth_startDate");
	initDateInputOfMonth("section_ofmonth_endDate");
	initDateInputOfYear("section_ofyear_startDate");
	initDateInputOfYear("section_ofyear_endDate");
}

/**
 * 初始化月日期控件
 * @param id
 */
function initDateInputOfMonth(id) {
	$("#" + id).datepicker({
		autoclose : true,
		format : "yyyy-mm",
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

/**
 * 执行补跑方法
 * 
 * @param data
 */
function runupMain(url, formId) {
	restfulAjax(url, JSON.stringify(serializeObject(formId)), succFunction);
}

function succFunction(data) {
	alertMsgOK(data.msg);
}

/**
 * form表单必录项校验规则初始化
 */
function ValidataInit() {
	/** 补跑售票站车次月统计校验*/
	var ticketTrainMonthRules = {
		ticketTrainMonthOfStartDate : 'required'
	};
	var ticketTrainMonthMessages = {
		ticketTrainMonthOfStartDate : "补跑售票站车次月统计起始日期不能为空！"
	};
	ticketTrainMonthValidate = buildValidate("ticktrainnumber_ofmonth_form",
			ticketTrainMonthRules, ticketTrainMonthMessages);
	/** 补跑售票站车次年统计校验*/
	var ticketTrainYearRules = {
		ticketTrainYearOfStartDate : 'required'
	};
	var ticketTrainYearMessages = {
		ticketTrainYearOfStartDate : "补跑售票站车次年统计起始日期不能为空！"
	};
	ticketTrainYearValidate = buildValidate("ticktrainnumber_ofyear_form",
			ticketTrainYearRules, ticketTrainYearMessages);
	/** 补跑售票站日统计统计校验*/
	var ticketDayRules = {
		ticketDayOfStartDate : 'required'
	};
	var ticketDayMessages = {
		ticketDayOfStartDate : "补跑售票站日统计起始日期不能为空！"
	};
	ticketDayValidate = buildValidate("ticket_ofday_form", ticketDayRules,
			ticketDayMessages);
	/** 补跑售票站月统计统计校验*/
	var ticketMonthRules = {
		ticketMonthOfStartDate : 'required'
	};
	var ticketMonthMessages = {
		ticketMonthOfStartDate : "补跑售票站月统计起始日期不能为空！"
	};
	ticketMonthValidate = buildValidate("ticket_ofmonth_form",
			ticketMonthRules, ticketMonthMessages);
	/** 补跑售票站年统计统计校验*/
	var ticketYearRules = {
		ticketYearOfStartDate : 'required'
	};
	var ticketYearMessages = {
		ticketYearOfStartDate : "补跑售票站年统计起始日期不能为空！"
	};
	ticketYearValidate = buildValidate("ticket_ofyear_form", ticketYearRules,
			ticketYearMessages);
	/** 补跑站段日统计统计校验*/
	var sectionDayRules = {
		sectionDayOfStartDate : 'required'
	};
	var sectionDayMessages = {
		sectionDayOfStartDate : "补跑站段日统计起始日期不能为空！"
	};
	sectionDayValidate = buildValidate("section_ofday_form", sectionDayRules,
			sectionDayMessages);
	/** 补跑站段月统计统计校验*/
	var sectionMonthRules = {
		sectiontMonthOfStartDate : 'required'
	};
	var sectionMonthMessages = {
		sectiontMonthOfStartDate : "补跑站段月统计起始月份不能为空！"
	};
	sectionMonthValidate = buildValidate("section_ofmonth_form",
			sectionMonthRules, sectionMonthMessages);
	/** 补跑站段年统计统计校验*/
	var sectionYearRules = {
		sectionYearOfStartDate : 'required'
	};
	var sectionYearMessages = {
		sectionYearOfStartDate : "补跑站段年统计起始日期不能为空！"
	};
	sectionYearValidate = buildValidate("section_ofyear_form",
			sectionYearRules, sectionYearMessages);
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/**
 * 执行售票站车次月补跑
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#ticktrainnumber_ofmonth_button")
		.on("click", ticktrainnumber_ofmonth_button);
function ticktrainnumber_ofmonth_button() {
	if (ticketTrainMonthValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var startDate=$("#ticktrainnumber_ofmonth_startDate").val();
		var endDate=$("#ticktrainnumber_ofmonth_endDate").val();
		if (endDate != null && endDate != "") {
			if (startDate > endDate) {
				alertMsgINFO("查询起期不能大于查询止期");
				return;
			}
		}
		runupMain(runupOfTicketTrainByMonthURL, ticktrainnumberOfmonthForm);
		$("#ticktrainnumber_ofmonth_startDate").val("");
		$("#ticktrainnumber_ofmonth_endDate").val("");
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/**
 * 执行售票站车次年补跑
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#ticktrainnumber_ofyear_button")
		.on("click", ticktrainnumber_ofyear_button);
function ticktrainnumber_ofyear_button() {
	if (ticketTrainYearValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var startDate=$("#ticktrainnumber_ofyear_startDate").val();
		var endDate=$("#ticktrainnumber_ofyear_endDate").val();
		if (endDate != null && endDate != "") {
			if (startDate > endDate) {
				alertMsgINFO("查询起期不能大于查询止期");
				return;
			}
		}
		runupMain(runupOfTicketTrainByYearURL, ticktrainnumberOfyearForm);
		$("#ticktrainnumber_ofyear_startDate").val("");
		$("#ticktrainnumber_ofyear_endDate").val("");
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/**
 * 执行售票站日补跑
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#ticket_ofday_button")
		.on("click", ticket_ofday_button);
function ticket_ofday_button() {
	if (ticketDayValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var startDate=$("#ticket_ofday_startDate").val();
		var endDate=$("#ticket_ofday_endDate").val();
		if (endDate != null && endDate != "") {
			if (startDate > endDate) {
				alertMsgINFO("查询起期不能大于查询止期");
				return;
			}
		}
		runupMain(runupOfTicketByDayURL, ticketOfdayForm);
		$("#ticket_ofday_startDate").val("");
		$("#ticket_ofday_endDate").val("");
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/**
 * 执行售票站月补跑
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#ticket_ofmonth_button")
		.on("click", ticket_ofmonth_button);
function ticket_ofmonth_button() {
	if (ticketMonthValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var startDate=$("#ticket_ofmonth_startDate").val();
		var endDate=$("#ticket_ofmonth_endDate").val();
		if (endDate != null && endDate != "") {
			if (startDate > endDate) {
				alertMsgINFO("查询起期不能大于查询止期");
				return;
			}
		}
		runupMain(runupOfTicketByMonthURL, ticketOfmonthForm);
		$("#ticket_ofmonth_startDate").val("");
		$("#ticket_ofmonth_endDate").val("");
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/**
 * 执行售票站年补跑
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#ticket_ofyear_button")
		.on("click", ticket_ofyear_button);
function ticket_ofyear_button() {
	if (ticketYearValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var startDate=$("#ticket_ofyear_startDate").val();
		var endDate=$("#ticket_ofyear_endDate").val();
		if (endDate != null && endDate != "") {
			if (startDate > endDate) {
				alertMsgINFO("查询起期不能大于查询止期");
				return;
			}
		}
		runupMain(runupOfTicketByYearURL, ticketOfyearForm);
		$("#ticket_ofyear_startDate").val("");
		$("#ticket_ofyear_endDate").val("");
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/**
 * 执行站段日补跑
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#section_ofday_button")
		.on("click", section_ofday_button);
function section_ofday_button() {
	if (sectionDayValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var startDate=$("#section_ofday_startDate").val();
		var endDate=$("#section_ofday_endDate").val();
		if (endDate != null && endDate != "") {
			if (startDate > endDate) {
				alertMsgINFO("查询起期不能大于查询止期");
				return;
			}
		}
		runupMain(runupOfSectionByDayURL, sectionOfdayForm);
		$("#section_ofday_startDate").val("");
		$("#section_ofday_endDate").val("");
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/**
 * 执行站段月补跑
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#section_ofmonth_button")
		.on("click", section_ofmonth_button);
function section_ofmonth_button() {
	if (sectionMonthValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var startDate=$("#section_ofmonth_startDate").val();
		var endDate=$("#section_ofmonth_endDate").val();
		if (endDate != null && endDate != "") {
			if (startDate > endDate) {
				alertMsgINFO("查询起期不能大于查询止期");
				return;
			}
		}
		runupMain(runupOfSectionByMonthURL, sectionOfmonthForm);
		$("#section_ofmonth_startDate").val("");
		$("#section_ofmonth_endDate").val("");
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/**
 * 执行站段月补跑
 * 保存按钮操作，保存页面数据 先进行form表单必录项校验
 */
$("#section_ofyear_button")
		.on("click", section_ofyear_button);
function section_ofyear_button() {
	if (sectionYearValidate.form()) {
		// 通过表单验证，以下编写自己的代码
		var startDate=$("#section_ofyear_startDate").val();
		var endDate=$("#section_ofyear_endDate").val();
		if (endDate != null && endDate != "") {
			if (startDate > endDate) {
				alertMsgINFO("查询起期不能大于查询止期");
				return;
			}
		}
		runupMain(runupOfSectionByYearURL, sectionOfyearForm);
		$("#section_ofyear_startDate").val("");
		$("#section_ofyear_endDate").val("");
	} else {
		// 校验不通过，什么都不用做，校验信息已经正常显示在表单上
		$("#btn-scroll-up").click();
	}
}

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/

$(document).ready(function() {
	initDateControl();// 初始化日期控件
	ValidataInit();// 初始化表单验证
});

/*----------------------------------end-------------------------------------*/
