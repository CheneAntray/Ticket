/* ------------------------ 属性 --------------------------------------  */
var divMainView = "#query-div";
var pageOptionUrl = "abic/reins/timedtask/html/timedtask.html";// 首页

/* ------------------------ 函数 -------------------------------------- */


/**
 * 初始化页面
 */
function page_load()
{
    // 加载首页
    $(divMainView).load(pageOptionUrl);
}
/* ------------------------ 事件 -------------------------------------- */
/* ------------------------ 初始化 ------------------------------------ */
$(document).ready(function()
{
    page_load();// 初始化操作
});