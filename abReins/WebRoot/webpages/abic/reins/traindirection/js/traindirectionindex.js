/*------------------------------页面属性集 -------------------------------- */
// main 包含jqgridEditUrl
/** 用户查询页面 */
var showQueryUrl = "abic/reins/traindirection/html/traindirectionquery.html";
/** 数据字典_是否 */
var dict_yesorno;
/** 数据字典_性别 */
var dict_sex; // 性别
/** 数据字典_证件类型 */
var dict_idtype; // 证件类型

/*----------------------------------end-------------------------------------*/
/*---------------------------------函数集-----------------------------------*/
/**
 * 初始化数据字典方法
 */
function dictInit()
{
    // 初始化数据字典信息
    var dict_defineList = new Array(3);
    dict_defineList[0] = dictEnum.getCodeValue('DEFINE_YESORNO');
    dict_defineList[1] = dictEnum.getCodeValue('DEFINE_SEX');
    dict_defineList[2] = dictEnum.getCodeValue('DEFINE_ID_TYPE');
    var dict_dictionary = restfulDictionaryList(dict_defineList);// 查询数据字典信息
    dict_yesorno = dict_dictionary[dictEnum.getCodeValue('DEFINE_YESORNO')];
    dict_sex = dict_dictionary[dictEnum.getCodeValue('DEFINE_SEX')];
    dict_idtype = dict_dictionary[dictEnum.getCodeValue('DEFINE_ID_TYPE')];
}
/*----------------------------------end-------------------------------------*/
/*---------------------------------事件-------------------------------------*/

/*----------------------------------end-------------------------------------*/
/*---------------------------------初始化-----------------------------------*/
$(document).ready(function()
{
    // 初始化数据字典
//    dictInit();
    // 加载查询页面
    $("#query-div").load(showQueryUrl);
});

/*----------------------------------end-------------------------------------*/

