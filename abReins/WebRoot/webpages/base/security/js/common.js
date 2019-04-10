/**********************************************
****************cookie设置与获取***************
**********************************************/
/***
* @param {string} cookieName Cookie名称
* @param {string} cookieValue Cookie值
* @param {number} nDays Cookie过期天数
*/
function SetCookie(cookieName,cookieValue,nDays) {
    /*当前日期*/
    var today = new Date();
    /*Cookie过期时间*/
    var expire = new Date();
    /*如果未设置nDays参数或者nDays为0，取默认值1*/
    if(nDays == null || nDays == 0) nDays = 1;
    /*计算Cookie过期时间*/
    expire.setTime(today.getTime() + 3600000 * 24 * nDays);
    /*设置Cookie值*/
    document.cookie = cookieName + "=" + escape(cookieValue)
        + ";expires=" + expire.toGMTString();
}

/***
*读取指定的Cookie值
*@param {string} cookieName Cookie名称
*/
function ReadCookie(cookieName) {
    var theCookie = "" + document.cookie;
    var ind = theCookie.indexOf(cookieName);
    if(ind==-1 || cookieName=="") return "";
    var ind1 = theCookie.indexOf(';',ind);
    if(ind1==-1) ind1 = theCookie.length;
    /*读取Cookie值*/
    return unescape(theCookie.substring(ind+cookieName.length+1,ind1));
}

/**********************************************
****************URL参数解读***************
**********************************************/
UrlParm = function() { // url参数    
  var data, index;    
  (function init() {    
    data = [];    
    index = {};    
    var u = window.location.search.substr(1);    
    if (u != '') {    
      var parms = decodeURIComponent(u).split('&');    
      for (var i = 0, len = parms.length; i < len; i++) {    
        if (parms[i] != '') {    
          var p = parms[i].split("=");    
          if (p.length == 1 || (p.length == 2 && p[1] == '')) {// p | p=    
            data.push(['']);    
            index[p[0]] = data.length - 1;    
          } else if (typeof(p[0]) == 'undefined' || p[0] == '') { // =c | =    
            data[0] = [p[1]];    
          } else if (typeof(index[p[0]]) == 'undefined') { // c=aaa    
            data.push([p[1]]);    
            index[p[0]] = data.length - 1;    
          } else {// c=aaa    
            data[index[p[0]]].push(p[1]);    
          }    
        }    
      }    
    }    
  })();    
  return {    
    // 获得参数,类似request.getParameter()    
    parm : function(o) { // o: 参数名或者参数次序    
      try {    
        return (typeof(o) == 'number' ? data[o][0] : data[index[o]][0]);    
      } catch (e) {    
      }    
    },    
    //获得参数组, 类似request.getParameterValues()    
    parmValues : function(o) { //  o: 参数名或者参数次序    
      try {    
        return (typeof(o) == 'number' ? data[o] : data[index[o]]);    
      } catch (e) {}    
    },    
    //是否含有parmName参数    
    hasParm : function(parmName) {    
      return typeof(parmName) == 'string' ? typeof(index[parmName]) != 'undefined' : false;    
    },    
    // 获得参数Map ,类似request.getParameterMap()    
    parmMap : function() {    
      var map = {};    
      try {    
        for (var p in index) {  map[p] = data[index[p]];  }    
      } catch (e) {}    
      return map;    
    }    
  }    
}(); 

/**
 * 弹出信息提示模态窗
 * @param id	模态窗插入DIVid
 * @param msg	提示信息
 * @param title	标题头信息 如果不填，自动显示“提示信息：”
 * @param level	提示信息级别("danger"|"warning"|"ok"|"info")
 */
function showAlertModelInfo(id, msg, title,level)
{
    var headTitle="提示信息：";
    if(title){
	headTitle=title;
    }
    var levelclass = "";
    if ("danger" == level)
    {
	levelclass = "alert-danger";
    } else if ("warning" == level)
    {
	levelclass = "alert-warning";
    } else if ("ok" == level)
    {
	levelclass = "alert-block alert-success";
    } else if ("info" == level)
    {
	levelclass = "alert-info";
    }
    var modelDiv ="<div id='_sys_alert' class='modal fade' tabindex='-1'><div class='modal-dialog'>";
    modelDiv+="<div class='modal-content'>";
//    modelDiv+="<div class='modal-header'><a class='close' data-dismiss='modal'>×</a>"+headTitle+"</div>";
    modelDiv+="<div class='modal-body overflow-visible'>";
//    modelDiv+="<div class='row' style='border:1px #cccccc solid; margin:-1px 0px 8px 0px; padding-top:12px'>";
    modelDiv+="<div class='alert "+levelclass+"'>";
    modelDiv+="<strong>" + msg + "</strong><a class='close' data-dismiss='modal'>×</a></div>";
    modelDiv+="</div></div></div>";
    $("#"+id).html(modelDiv);
    $("#_sys_alert").modal('show').on('shown.bs.modal', function (e) {
	// 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#_sys_alert .modal-dialog').height() / 2;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight
        });
    });
}


/**
 * 弹出信息提示模态窗
 * @param id	提示信息DIVid
 * @param msg	提示信息
 * @param level	提示信息级别("danger"|"warning"|"ok"|"info")
 */
function showInnerMessageInfo(id, msg, level)
{
    var levelclass = "";
    if ("danger" == level)
    {
	levelclass = "alert-danger";
    } else if ("warning" == level)
    {
	levelclass = "alert-warning";
    } else if ("ok" == level)
    {
	levelclass = "alert-block alert-success";
    } else if ("info" == level)
    {
	levelclass = "alert-info";
    }
    modelDiv="<div class='alert "+levelclass+"'>";
    modelDiv+="<strong>" + msg + "</strong><a class='close' data-dismiss='alert'>×</a></div>";
    modelDiv+="</div>";
    $("#"+id).html(modelDiv);
}


/**
 * 抓取FORM表单数据转换成JSON数据 仅支持一层FORM表单数据转JSON
 * 
 * @param formid
 * @returns JSON数据
 */
function serializeObject(formid)
{
    var serializeObj = {}; // 目标对象
    var tempObj = {};// 临时对象
    var array = $("#"+formid).serializeArray(); // 转换数组格式
    $(array).each(function()
    { // 遍历数组的每个元素 {name : xx , value : xxx}
	if (serializeObj[this.name])
	{ // 判断对象中是否已经存在 name，如果存在name
	    if ($.isArray(serializeObj[this.name]))
	    {
		serializeObj[this.name].push(this.value); // 追加一个值hobby:['音乐','体育']
	    } else
	    {
		// 将元素变为 数组 ，hobby : ['音乐','体育']
		serializeObj[this.name] =[ serializeObj[this.name], this.value ];
	    }
	} else
	{
	    serializeObj[this.name] = this.value; // 如果元素name不存在，添加一个属性name:value
	}
    });
    return serializeObj;
}

/**
 * 获取JSON数据并简单填充至FORM表单中 仅支持一层JSON数据
 * 
 * @param formid
 *                form表单ID
 * @param jsondata
 *                需要填充至form的json数据
 */
function jsonToFormInput(formid, jsondata)
{
    // 展示FORM
    Object.keys(jsondata).map(function(key)
    {
	$('#' + formid + ' input').filter(function()
	{
	    return key == this.name;
	}).val(jsondata[key]);
	$('#' + formid + ' textarea').filter(function()
	{
	    return key == this.name;
	}).val(jsondata[key]);
	$('#' + formid + ' label[name]').filter(function()
	{
	    return key == $(this).attr("name");
	}).text(jsondata[key]||'');
    });
}

/**
 * 将数值四舍五入后格式化.
 * 
 * @param num
 *                数值(Number或者String)
 * @param cent
 *                要保留的小数位(Number)
 * @param isThousand
 *                是否需要千分位 0:不需要,1:需要(数值类型);
 * @return 格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatNum(num, cent, isThousand)
{
    // 检查传入数值为数值类型
    if (!num){
	num = "0";
    }

    num = num.toString().replace(/\$|\,/g, '');

    // 检查传入数值为数值类型
    if (isNaN(num)){
	num = "0";
    }

    // 获取符号(正/负数)
    sign = (num == (num = Math.abs(num)));

    num = Math.floor(num * Math.pow(10, cent) + 0.50000000001); // 把指定的小数位先转换成整数.多余的小数位四舍五入
    cents = num % Math.pow(10, cent); // 求出小数位数值
    num = Math.floor(num / Math.pow(10, cent)).toString(); // 求出整数位数值
    cents = cents.toString(); // 把小数位转换成字符串,以便求小数位长度

    // 补足小数位到指定的位数
    while (cents.length < cent)
	cents = "0" + cents;

    if (isThousand)
    {
	// 对整数部分进行千分位格式化.
	for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
	    num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
    }

    if (cent > 0)
	return (((sign) ? '' : '-') + num + '.' + cents);
    else
	return (((sign) ? '' : '-') + num);
}

/**
 * 去除千分符号
 */
function unNumSymbol(val)
{
    var x = val.split(',');
    var returnV = parseFloat(x.join(""));
    return returnV;
}

/**
 * 限制只能录入数字和一个小数点
 */
function clearNoNum(obj)
{
    // 先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d.]/g, "");
    // 必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g, "");
    // 保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\.{2,}/g, ".");
    // 保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
}


/**
 * 设置是否按钮显示值
 * 
 * @param id
 * @param val
 *                控件值为yes时的值
 */
function setYesNoChecked(id, val)
{
    var setVal = val;
    if(setVal==undefined){
	setVal = dictEnum.getCodeValue('YESORNO_YES');
    }
    if ($("#"+id).val() == setVal)
    {
	$("#"+id+"_yncheck").prop("checked", true);// 选中
    } else
    {
	$("#"+id+"_yncheck").prop("checked", false);// 未选中
    }
}

/**
 * 是否按钮方法
 */
function checkedYesNo_click(id)
{
    if ($("#"+id+"_yncheck").prop('checked'))
    {
	$("#"+id).val(dictEnum.getCodeValue('YESORNO_YES'));
    } else
    {
	$("#"+id).val(dictEnum.getCodeValue('YESORNO_NO'));
    }
}

/**
 * 初始化chosen select框数据信息
 * @param selectid	选择框ID
 * @param data 选择框数据
 * @param multiple	是否复选框
 * @param notNullOption	是否取消第一行空值数据(会影响单选选中取消功能)
 */
function chosenInitData(selectid, data, multiple, notNullOption)
{
    var multiple = multiple||false;
    $("#" + selectid).html("");
    $("#" + selectid).chosen("destroy");
    if (!notNullOption)
    {
	$("<option value=''> </option>").appendTo("#" + selectid);
    }

    $.each(data, function(i)
    {
	$("<option value='" + data[i].id + "'>" +data[i].name + "</option>").appendTo("#" + selectid);
    });
    $("#" + selectid).attr("multiple", multiple);
    $("#" + selectid).chosen(
    {
	allow_single_deselect : true,
	// inherit_select_classes : true,
	no_results_text : "未找到此选项!",
	search_contains : true,
	width : '100%'
    });
     $("#"+selectid).trigger("chosen:updated");
     //校验添加样式
     $("#" + selectid+"_chosen").addClass("tooltip-warning");
     $("#" + selectid+"_chosen").attr("data-rel","tooltip");
     //对齐表单
     $("#" + selectid+"_chosen").css("margin-bottom","12px");
     if($("#"+selectid).attr("name")){
	 $("#" + selectid+"_chosen").attr("name",$("#"+selectid).attr("name")+"_chosen");
     }
}

/**
 * JQGRID里初始化SELECT数据
 * @param data	数据字典下拉数据
 * @returns {Object}
 */
function jqgridSelectData(data)
{
    var jqgridData = new Object();
    $.each(data, function(i)
    {
	jqgridData[data[i].code]=data[i].code + "-" + data[i].name;
    });
    return jqgridData;
}

/**
 * 根据数据字典数据 及代码值 获取显示值NAME
 * @param data  数据字典下拉数据
 * @param code
 * @returns {String}
 */
function getSelectNameByCode(data,code)
{
    var selectName = "";
    $.each(data, function(i)
    {
	if(data[i].code==code){
	    selectName = data[i].code + "-" + data[i].name;
	}
    });
    return selectName;
}

/**
 * 
 * 带查询条件及结果的弹出模态框公共调用方法
 * 
 * @param indexDivId 父页面存放模态框的DIV-ID
 * @param modalDivId 弹出的模态框DIV-ID
 * @param modalUrl 弹出的模态框URL地址
 * @param funcName 弹出的模态框中初始化函数名称
 * @param callback 回调函数，模态框关闭时在父页面执行函数
 * @param callbackparam 模态框入参
 */
function showReinsurerModal(indexDivId, modalDivId, modalUrl, funcName, callback, callbackparam)
{
    $("#" + indexDivId).load(modalUrl, function()
    {
	$("#" + modalDivId).on('show.bs.modal', function(e)
	{
	    // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
	    $(this).css('display', 'block');
	    var modalHeight = $(window).height() / 2 - $("#" + modalDivId + " .modal-dialog").height() / 2 - 80;
	    $(this).find('.modal-dialog').css(
	    {
		'margin-top' : modalHeight
	    });
	});

	$("#" + modalDivId).on('hide.bs.modal', function(e)
	{
	    // 设置回调函数
	    if (isAddClose)
	    {
		if ($.isFunction(callback))
		{
		    callback(selectedData);
		}
	    }
	    isAddClose = false;
	    selectedData = "";
	});
	$("#" + modalDivId).on('hidden.bs.modal', function(e)
	{
	    $("#divframe").css("overflow", "auto");
	    $("#" + indexDivId).addClass("hide");
	    $("#" + indexDivId).empty();
	});
	
	// 为模态对话框添加拖拽
	$("#" + modalDivId).draggable();
	// 禁止模态对话框的半透明背景滚动
	$("#" + modalDivId).css("overflow", "hidden");

	$("#" + modalDivId).modal('show');
	// 加载传入的模态框初始化函数-jqgrid查询结果列表
	if (typeof (eval(funcName)) == "function")
	{
	    var initFunc = eval(funcName + "();");
	    initFunc;
	}
    });
    $("#" + indexDivId).removeClass('hide');
}

/**
 * 初始化 日期控件
 * @param id
 */
function initDateInput(id){
    $("#"+id).datepicker(
    {
        language : 'zh-CN',
        format : 'yyyy-mm-dd',
        autoclose : true,
        todayBtn : true,
        todayHighlight : true
    }).next().on(ace.click_event, function()
    {
        $(this).prev().focus();
    });
}


/**
 *  构建jquery validate
 * @param id	需校验的表单FORM id
 * @param validateRules	校验规则
 * @param validateMessages	校验信息
 * @returns
 */
function buildValidate(id, validateRules, validateMessages)
{
    return $("#" + id).validate(
    {
	//选择框隐藏的不校验 去除
	ignore : ":hidden:not(select)",
	rules : validateRules,
	messages : validateMessages,
	errorClass : 'error',
	success : 'valid',
	unhighlight : function(element, errorClass, validClass)
	{ // 验证通过
	    $(element).tooltip('destroy').removeClass(errorClass);
	},
	highlight : function(element, errorClass, validClass)
	{ // 未通过验证
	    // 不处理任何操作
	},
	errorPlacement : function(error, element)
	{
	    //判断为下拉选择框 默认认为是 CHOSEN 选择框，给CHOSEN提示
	    if ($(element)[0].tagName == "SELECT")
	    {
		var chosenid = $(element).attr("id") + "_chosen";
		$("#" + chosenid).attr("data-original-title", $(error).text()).tooltip("show");
	    } else if ($(element).next("div").hasClass("tooltip"))
	    {
		$(element).attr("data-original-title", $(error).text()).tooltip("show");
	    } else
	    {
		$(element).attr("title", $(error).text()).tooltip("show");
	    }
	}
    });
}


/**
 * 根据险类代码获取险种下拉数据
 * 
 * @param classCode
 *                险类代码
 * @param riskCodes
 *                原险种下拉数据
 * @returns 与险类相关的险种下拉数据
 */
function choseClassRiskCode(classCode, riskCodes)
{
    if (classCode)
    {
	var initRiskCodes = [];
	if ($.isArray(riskCodes))
	{
	    $(riskCodes).each(function()
	    {
		if (this.parentcode && classCode == this.parentcode)
		{
		    initRiskCodes.push(this);
		}
	    });
	}
	return initRiskCodes;
    } else
    {
	return riskCodes;
    }
}

/**
 * 险类险种级联方法
 * 
 * @param classId
 *                险类下拉框ID
 * @param prdProdId
 *                险种下拉框ID
 * @param prdProdValues
 *                原所有险种数据
 */
function cascadeRiskCode(classId, riskId, riskCodes)
{
    $("#" + classId).on("change", function()
    {
	var classCode = $("#" + classId).val();
	chosenInitData(riskId, choseClassRiskCode(classCode, riskCodes));
    });
}

/**
 * 显示模态框的公共方法
 * @param modalParam
 */
function showModal(modalParam)
{
    var indexDivId = modalParam.indexDivId;
    var modalDivId = modalParam.modalDivId;
    var modalUrl = modalParam.modalUrl;
    var modalWidth = modalParam.modalWidth;
    var modalHeight = modalParam.modalHeight;
    var funcName = modalParam.funcName;
    var callback = modalParam.callback;
    parame = modalParam.parame;
    isAddClose = false;
    selectedData = "";
    $("#" + indexDivId).load(modalUrl, function()
    {
	$("#" + modalDivId).on('show.bs.modal', function(e)
	{
	    // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
	    $(this).css('display', 'block');
	    if(!modalHeight)
	    {
		modalHeight = $(window).height() / 2 - $("#" + modalDivId + " .modal-dialog").height() / 2 - 80;
	    }
	    $(this).find('.modal-dialog').css(
	    {
		'margin-top' : modalHeight,
		'width' : modalWidth
	    });
	});
	$("#" + modalDivId).on('hide.bs.modal', function(e)
	{
	    // 设置回调函数
	    if (isAddClose)
	    {
		if ($.isFunction(callback))
		{
		    callback(selectedData);
		}
	    }
	    isAddClose = false;
	    selectedData = "";
	});
	$("#" + modalDivId).on('hidden.bs.modal', function(e)
	{
	    $("#divframe").css("overflow", "auto");
	    $("#" + indexDivId).addClass("hide");
	    $("#" + indexDivId).empty();
	});

	// 为模态对话框添加拖拽
	$("#" + modalDivId).draggable();
	// 禁止模态对话框的半透明背景滚动
	$("#" + modalDivId).css("overflow", "hidden");
	$("#" + modalDivId).modal('show');
	// 加载传入的模态框初始化函数-jqgrid查询结果列表
	if (typeof (eval(funcName)) == "function")
	{
	    var initFunc = eval(funcName + "();");
	    initFunc;
	}
    });
    $("#" + indexDivId).removeClass('hide');
}

/**
 * 日期格式化,fmt为格式如:yyyy-MM-dd的字符串
 */
Date.prototype.Format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}