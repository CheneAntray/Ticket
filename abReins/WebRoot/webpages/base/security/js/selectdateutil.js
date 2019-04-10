function SelectDateUtil()
{
}

// 构建12个月的下拉列表
// @param 为select标签的id值
SelectDateUtil.buildAllMonth = function(_sel)
{
    SelectUtil.cleanOptions(_sel);

    for (var i = 1; i <= 12; i++)
    {
	SelectUtil.addOption(_sel, i, i + "月");
    }
};

// 构建1月到指定月的下拉列表，
// @param 为select标签的id值
// @param 0为当前月，1为上一月 ... (参数不允许大于当前月)
SelectDateUtil.buildPointMonth = function(_sel, num)
{
    SelectUtil.cleanOptions(_sel);

    var date = new Date();
    var thisMonth = date.getMonth() + 1;
    if (parseInt(num) >= parseInt(thisMonth))
    {
	alert("参数不允许大于当前月!");
	return;
    }
    var k = thisMonth - num;
    for (var i = 1; i <= k; i++)
    {
	if (i < 10)
	{
	    SelectUtil.addOption(_sel, "0" + i, i + "月");
	} else
	{
	    SelectUtil.addOption(_sel, i, i + "月");
	}
    }

    // $("select[name='"+_sel+"']
    // option[value='"+thisMonth+"']").attr("selected", "true");
};

// 构建当前年到指定年的下拉列表，
// @param 为select标签的id值
// @param 0为当前年，1为当前年和上一年 ... (参数不允许大于当前年)
SelectDateUtil.buildPointYear = function(_sel, num)
{
    SelectUtil.cleanOptions(_sel);

    var date = new Date();
    var thisYear = date.getFullYear();
    if (parseInt(num) >= parseInt(thisYear))
    {
	alert("参数不允许大于当前年!");
	return;
    }
    var k = thisYear - num;
    for (var i = parseInt(thisYear); i >= parseInt(k); i--)
    {
	SelectUtil.addOption(_sel, i, i);
    }
};
/**
 * 获取指定日期到当前日期的月集合(201701M)
 * 
 * @param date
 * @returns {Array}
 */
function buildPeriodMonth(date)
{
    var resultArray = new Array();
    // 计算
    var currentDate = new Date();
    var currentYear = currentDate.getFullYear();
    var currentMonth = currentDate.getMonth() + 1;
    var currentMonthNum = parseInt(currentYear + "" + (currentMonth < 10 ? "0" + currentMonth : currentMonth));
    while (true)
    {
	var thisYear = date.getFullYear();
	var thisMonth = date.getMonth() + 1;
	var thisMonthNum = parseInt(thisYear + "" + (thisMonth < 10 ? "0" + thisMonth : thisMonth));
	if (thisMonthNum < currentMonthNum)
	{
	    var obj =
	    {
		code : thisMonthNum + "M",
		name : thisYear + "年" + thisMonth + "月"
	    };
	    resultArray.push(obj);
	    date.setMonth(date.getMonth() + 1);
	} else
	{
	    break;
	}
    }
    return resultArray;
}
/**
 * 获取指定日期到当前日期的季度集合(201701Q)
 * 
 * @param date
 * @returns {Array}
 */
function buildPeriodQuarter(date)
{
    var resultArray = new Array();
    var quarterArray = new Array("一", "二", "三", "四");
    // 计算
    var currentDate = new Date();
    var currentYear = currentDate.getFullYear();
    var currentMonth = currentDate.getMonth() + 1;
    var currentQuarterNum = parseInt(currentYear + "0" + getQuarterNum(currentMonth));
    while (true)
    {
	var thisYear = date.getFullYear();
	var thisMonth = date.getMonth() + 1;
	var quarterNum = getQuarterNum(thisMonth);
	var thisMonthNum = parseInt(thisYear + "0" + quarterNum);
	if (thisMonthNum < currentQuarterNum)
	{
	    var obj =
	    {
		code : thisMonthNum + "Q",
		name : thisYear + "年第" + quarterArray[quarterNum - 1] + "季度"
	    };
	    resultArray.push(obj);
	    date.setMonth(date.getMonth() + 3);
	} else
	{
	    break;
	}
    }
    return resultArray;
}
/**
 * 根据指定月份判断所属季度 01-[1,2,3]、02-[4,5,6]、03-[7,8,9]、04-[10,11,12]
 * 
 * @param monthNum
 * @returns {String}
 */
function getQuarterNum(monthNum)
{
    var num = parseInt(monthNum);
    var resultNum = 1;
    if (num > 9)
    {
	resultNum = 4;
    } else if (num > 6)
    {
	resultNum = 3;
    } else if (num > 3)
    {
	resultNum = 2
    }
    return resultNum;
}
