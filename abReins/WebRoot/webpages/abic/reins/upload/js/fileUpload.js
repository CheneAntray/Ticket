/** * 属性 ** */
var uploadAccUrl = "api/income/intoIncome";
// 按钮
var btnImportOk = "#startFileUploadBtn";// 导入按钮

/**
 * 上传成功
 */
function uploadSuccess(data) {
	$("#fileToUpload").val("");
	if(data.code==0){
		alertMsgOK(data.msg);
	}else if(data.code==6){
		alertMsgERROR("系统执行异常，请联系系统管理员");
	}else{
		alertMsgERROR("失败");
	}
	
}

/**
 * 导入按钮确认点击事件
 */
$(btnImportOk).on("click", btnImportOk_onclick);
function btnImportOk_onclick() {
	// 校验
	var filedate=$("#fileDate").val();
	if (filedate == "") {
		alertMsgERROR("请选择日期!");
		return;
	}
	var filename = $("#fileToUpload").val();
	if (filename == "") {
		alertMsgERROR("请选择一个文件!");
		return;
	}
	var suffix = filename.substring(filename.lastIndexOf(".") + 1);
	if (suffix != "xls" && suffix != "xlsx") {
		alertMsgERROR("只能选择Excel文件！");
		return;
	}
	var uploaddata = new FormData();
	var file = document.getElementById("fileToUpload").files[0];
	uploaddata.append("file", file);
	var date = $("#fileDate").val();
	uploaddata.append("date", date);
	restfulUpload(uploadAccUrl, uploaddata, uploadSuccess);
}

/*---------------------------------初始化-----------------------------------*/
$(document).ready(function() {
//	initDateInput("fileDate");// 初始化日期控件
});

/*----------------------------------end-------------------------------------*/