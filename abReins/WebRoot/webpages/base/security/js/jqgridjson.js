/**
 * cgrid依赖jqgrid
 */
 (function($_self, jQuery)
 {

    // jQuery.ajaxSetup({cache: false});

    var _this = $_self;

    /**
     * 底层核心列表
     * 
     * @param grid_selector
     *                gird选择器
     * @param pager_selector
     *                分页器选择器
     * @param loadUrl
     *                数据加载url
     * @param editUrl
     *                数据操作url
     * @param colNames
     *                列名数组
     * @param colModel
     *                列模型数组
     * @param gridComplete
     *                数据完成后函数
     * @param subGrid
     *                是否开启子grid
     * @param subGridOptions
     *                子grid配置
     * @param subGridRowExpanded
     *                zigrid Row展开
     * @param caption
     *                标题
     * 
     * 
     * var colModel = [{ name: 'name', //是否可编辑 editable: true, //编辑的一系列选项
     * //edittype:'select', editoptions:{value:{1:'One',2:'Two'}} //editoptions:
     * {multiple:true, size:3... } //editoptions: {size:10, maxlength: 8}
     * //editoptions: { size: 10, readonly: 'readonly'}, editoptions: {
     * //readonly: 'readonly', disabled: true }, //编辑规则 // edithidden:true只在Form
     * Editing模式下有效，设置为true，就可以让隐藏字段也可以修改 //
     * required:true,number:true,integer:true,minValue:10,maxValue:100,email:true,url:true,date:true,time:true,custom:true,custom_func:function(){}}
     * editrules: {}, //编辑类型 text, textarea, select, checkbox, password, button,
     * image and file. edittype: 'text', //列宽度是否要固定不可变 fixed: false, hidedlg:
     * true, //在初始化表格时是否要隐藏此列 hidden: false, //是否可以被resizable //resizable: true,
     * //在搜索模式下，定义此列是否可以作为搜索列 search: false, //是否可排序 sortable: false, width:
     * 150, datefmt: 'yyyy-MM-dd', //left,center,right align: 'left' } ];
     * 
     */
    var _coreGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, gridComplete, subGrid, subGridOptions, subGridRowExpanded, caption, treeGrid, expandColumn, multiselect, onSelectRow, searchid, pgbuttonsinput)
    {
	var _searchid = 'id';
	if(searchid){
	    _searchid=searchid;
	}
	var _hiddenPGbuttonsinput = false;
	var _rowList = [ 10, 20, 30 ];
	var _rowNum = 10;
	if(pgbuttonsinput){
	    _hiddenPGbuttonsinput = pgbuttonsinput;
	    _rowList = undefined;
	    _rowNum = undefined;
	}
	
	jQuery(grid_selector).jqGrid(
		{
		    url : loadUrl,
		    ajaxGridOptions :
		    {
			contentType : 'application/json; charset=utf-8'
		    },
		    editoptions :
		    {
			contentType : 'application/json; charset=utf-8'
		    },
		    loadBeforeSend : function(jqXHR)
		    {
			jqXHR.setRequestHeader("oauth-token", ReadCookie("oauth-token"));
			jqXHR.setRequestHeader("access-url", $("#main-access-url").val());
			jqXHR.setRequestHeader("X-Auth-Token", 'open-sesame');
		    },
		    serializeGridData : function(postData)
		    {
			return JSON.stringify(postData);
		    },
		    datatype : "local",
		    height : '100%',
		    mtype : "POST",
		    autowidth : true,
		    colNames : colNames,
		    colModel : colModel,
		    viewrecords : true,
		    rowNum : _rowNum,
		    rowList : _rowList,
		    rownumbers : true,
		    pager : pager_selector,
		    pgbuttons : !_hiddenPGbuttonsinput,// 上下按钮
		    pginput : !_hiddenPGbuttonsinput,// 输入框
		    altRows : true,
		    // toppager: true,
		    shrinkToFit : false,
		    autoScroll: true,  //* forceFit:true, */
		    multiselect : multiselect || false,
		    multiboxonly : multiselect || false,
		    beforeSelectRow : function(rowid, e)
		    {
			if (multiselect)
			{
			    var $myGrid = jQuery(this), i = jQuery.jgrid.getCellIndex(jQuery(e.target).closest('td')[0]), cm = $myGrid.jqGrid('getGridParam', 'colModel');
			    return (cm[i].name === 'cb');
			}
			return true;
		    },
		    jsonReader :
		    {
			root : "data.page.result", // root : "dataList",
			page : "data.page.currentPageNo", // page :"currPage",
			total : "data.page.totalPageCount", // total :
							    // "totalpages",
			records : "data.page.totalCount", // records :
							    // "totalCount",
			repeatitems : false,
			id : _searchid
		    },
		    emptyrecords : '没有记录显示',
		    loadComplete : function(data)
		    {
			if(data.code){
			    //数据加载异常
			    opErrorMsg(data);
			    return;
			}
			var table = this;
			setTimeout(function()
			{
			    styleCheckbox(table);
			    updateActionIcons(table);
			    updatePagerIcons(table);
			    enableTooltips(table);
			}, 0);
		    },
		    gridComplete : gridComplete || function()
		    {
		    },
		    editurl : editUrl||'api/jqgrid/',
		    onSelectRow : onSelectRow || function()
		    {
		    },

		    // subGrid
		    subGrid : subGrid || false,
		    subGridOptions : subGridOptions || {},
		    subGridRowExpanded : subGridRowExpanded || function()
		    {
		    },
		    caption : caption || undefined,

		    // treeGrid
		    treeGrid : treeGrid || false,
		    treeGridModel : 'adjacency',
		    ExpandColClick : true,
		    ExpandColumn : expandColumn || 'id',
		    treeIcons :
		    {
			plus : 'ace-icon fa fa-caret-right bigger-160 blue',
			minus : 'ace-icon fa fa-caret-down bigger-160 blue',
			leaf : 'ace-icon fa fa-tags   orange'
		    }
		// 树图标
		});

	// 自适应
	_this.resize(grid_selector);
	 //_this.addFun(grid_selector);

    };

    /**
     * 选中数据
     * 
     * @param grid
     * @param id
     */
    _this.setSelectData = function(grid_selector, ids)
    {
	var _ids = ids || [];
	var grid = jQuery(grid_selector);
	for (var i = 0; i < _ids.length; i++)
	{
	    grid.jqGrid("setSelection", _ids[i]);
	}
    };

    _this.reloadGrid = function(grid_selector)
    {
	$(grid_selector).trigger('reloadGrid');
    };

    /**
     * 设置表单不排序
     * 
     * @param grid
     */
    _this.noSortTable = function(grid_selector)
    {
	//disable sorting of grid
	var grid = jQuery(grid_selector);
	 //get all column names
	var columnNames = grid.jqGrid('getGridParam','colModel');
	 //iterate through each and disable
	for (i = 0; i < columnNames.length; i++) {
	    grid.setColProp(columnNames[i].index, { sortable: false });
	}
    }
    
    /**
     * 初始化列表
     */
    _this.initCudGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel,multiselect,searchid,onSelectRow, pgbuttonsinput, caption,navButtons)
    {
	// grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel,
	// gridComplete, subGrid, subGridOptions, subGridRowExpanded, caption,
	// treeGrid, expandColumn, multiselect, onSelectRow,searchid
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, '', '', '', '', caption, '', '', multiselect,onSelectRow,searchid, pgbuttonsinput);
//	var navButtons ={ add : false, del : false, edit : false, view : false,  search : false,  refresh : false };
	_this.navButtons(grid_selector, pager_selector, colModel, navButtons);
	_this.noSortTable(grid_selector);
    };
    
    // 查询操作
    _this.queryGrid = function(grid_selector,loadUrl,queryData){
	jQuery(grid_selector).jqGrid('setGridParam',{
	    page:1,
	    url:loadUrl,
	    datatype:"json",
	    postData:queryData
	}).trigger("reloadGrid"); // 重新载入
    };
    
    
    /**
     * 简单crud列表
     */
    _this.crudGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel)
    {
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel);
	_this.navButtons(grid_selector, pager_selector, colModel);
    };

    /**
     * 树列表
     */
    _this.treeGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, expandColumn)
    {
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, '', false, '', '', '', true, expandColumn);
	_this.navButtons(grid_selector, pager_selector, colModel);
    };

    /**
     * 定制操作列表 navButtons : {add: true, del: true, edit: true, view: true,
     * search: true};
     */
    _this.treeNavGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, expandColumn)
    {
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, '', false, '', '', '', true, expandColumn);
	_this.navButtons(grid_selector, pager_selector, colModel, navButtons);
    };

    /**
     * 定制操作tree列表 navButtons : {add: true, del: true, edit: true, view: true,
     * search: true};
     */
    _this.operTreeNavGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, expandColumn, opers)
    {
	var gridComplete = function()
	{
	    var grid = jQuery(grid_selector);
	    var ids = grid.jqGrid('getDataIDs');
	    for (var i = 0, j = ids.length; i < j; i++)
	    {
		var cl = ids[i];
		var opers_button = '';
		for (var m = 0, n = opers.length; m < n; m++)
		{
		    opers_button += "<input type='button' value='" + opers[m].oper + "' onclick=\"(" + opers[m].action + ")(" + cl + ")\">";
		}
		grid.jqGrid('setRowData', cl,
		{
		    operation : opers_button
		});
	    }
	};
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, gridComplete, false, '', '', '', true, expandColumn);
	_this.navButtons(grid_selector, pager_selector, colModel, navButtons);
    };

    /**
     * 数据行带操作按钮的列表 { name: 'operation', width: 80, fixed: true, sortable: false,
     * resize: false } opers : [{oper:'发布',action:function}]
     */
    _this.operGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, opers)
    {
	var gridComplete = function()
	{
	    var grid = jQuery(grid_selector);
	    var ids = grid.jqGrid('getDataIDs');
	    for (var i = 0, j = ids.length; i < j; i++)
	    {
		var cl = ids[i];
		var opers_button = '';
		for (var m = 0, n = opers.length; m < n; m++)
		{
		    opers_button += "<input type='button' value='" + opers[m].oper + "' onclick=\"(" + opers[m].action + ")(" + cl + ")\">";
		}
		grid.jqGrid('setRowData', cl,
		{
		    operation : opers_button
		});
	    }
	};
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, gridComplete);
	_this.navButtons(grid_selector, pager_selector, colModel);
    };

    /**
     * 数据行带操作按钮的列表并定制操作
     */
    _this.operNavGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, opers, navButtons)
    {
	var gridComplete = function()
	{
	    var grid = jQuery(grid_selector);
	    var ids = grid.jqGrid('getDataIDs');
	    for (var i = 0, j = ids.length; i < j; i++)
	    {
		var cl = ids[i];
		var opers_button = '';
		for (var m = 0, n = opers.length; m < n; m++)
		{
		    opers_button += "<input type='button' value='" + opers[m].oper + "' onclick=\"(" + opers[m].action + ")(" + cl + ")\">";
		}
		grid.jqGrid('setRowData', cl,
		{
		    operation : opers_button
		});
	    }
	};
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, gridComplete);
	_this.navButtons(grid_selector, pager_selector, colModel, navButtons);
    };

    /**
     * 定制操作列表 navButtons : {add: true, del: true, edit: true, view: true,
     * search: true};
     */
    _this.navGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, gridComplete)
    {
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, gridComplete);
	_this.navButtons(grid_selector, pager_selector, colModel, navButtons);
    };

    /**
     * 定制多选操作列表 navButtons : {add: true, del: true, edit: true, view: true,
     * search: true};
     */
    _this.navSelectGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, gridComplete, onSelectRow)
    {
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, gridComplete, '', '', '', '', '', '', true, onSelectRow);
	_this.navButtons(grid_selector, pager_selector, colModel, navButtons);
    };

    /**
     * 定制多选操作父子列表 navButtons : {add: true, del: true, edit: true, view: true,
     * search: true};
     */
    _this.navSelectHasSubGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, subLoadUrl, subEditUrl, subColNames, subColModel, subNavButtons, isShowSubHeader, gridComplete, onSelectRow)
    {
	_this.hasNavSubGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, subLoadUrl, subEditUrl, subColNames, subColModel, subNavButtons, gridComplete, isShowSubHeader, true, onSelectRow);
	_this.navButtons(grid_selector, pager_selector, colModel, navButtons);
    };

    /**
     * 父数据行带操作按钮的列表 { name: 'operation', width: 80, fixed: true, sortable:
     * false, resize: false } opers : [{oper:'发布',action:function}]
     */
    _this.operHasSubGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, subLoadUrl, subEditUrl, subColNames, subColModel, opers, isShowSubHeader)
    {
	_this.operHasNavSubGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, '', subLoadUrl, subEditUrl, subColNames, subColModel, '', opers, isShowSubHeader);
    };

    /**
     * 定制操作列表[父数据行带操作按钮的列表] navButtons : {add: true, del: true, edit: true,
     * view: true, search: true}; subNavButtons : {add: true, del: true, edit:
     * true, view: true, search: true};
     */
    _this.operHasNavSubGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, subLoadUrl, subEditUrl, subColNames, subColModel, subNavButtons, opers, isShowSubHeader)
    {
	var gridComplete = function()
	{
	    var grid = jQuery(grid_selector);
	    var ids = grid.jqGrid('getDataIDs');
	    for (var i = 0, j = ids.length; i < j; i++)
	    {
		var cl = ids[i];
		var opers_button = '';
		for (var m = 0, n = opers.length; m < n; m++)
		{
		    opers_button += "<input type='button' value='" + opers[m].oper + "' onclick=\"(" + opers[m].action + ")(" + cl + ")\">";
		}
		grid.jqGrid('setRowData', cl,
		{
		    operation : opers_button
		});
	    }
	};
	_this.hasNavSubGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, subLoadUrl, subEditUrl, subColNames, subColModel, subNavButtons, gridComplete, isShowSubHeader);
    };

    /**
     * 含有子列表的列表
     */
    _this.hasSubGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, subLoadUrl, subEditUrl, subColNames, subColModel)
    {
	_this.hasNavSubGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, '', subLoadUrl, subEditUrl, subColNames, subColModel);
    };

    /**
     * 定制操作列表 navButtons : {add: true, del: true, edit: true, view: true,
     * search: true}; subNavButtons : {add: true, del: true, edit: true, view:
     * true, search: true};
     */
    _this.hasNavSubGrid = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, subLoadUrl, subEditUrl, subColNames, subColModel, subNavButtons, gridComplete, isShowSubHeader, multiselect, onSelectRow)
    {
	var subGridOptions =
	{
	    plusicon : "ace-icon fa fa-plus center bigger-110 blue",
	    minusicon : "ace-icon fa fa-minus center bigger-110 blue",
	    openicon : "ace-icon fa fa-chevron-right center orange"
	};
	var subGridRowExpanded = function(subgrid_id, row_id)
	{
	    var subgrid_table_id = subgrid_id + "_t";
	    var subgrid_pager_id = subgrid_id + '_pgr';
	    jQuery("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='" + subgrid_pager_id + "' class='scroll'></div>");

	    var _subLoadUrl = subLoadUrl.indexOf('?') == -1 ? (subLoadUrl + '?rid=' + row_id) : (subLoadUrl + '&rid=' + row_id);
	    var _subEditUrl = subEditUrl.indexOf('?') == -1 ? (subEditUrl + '?rid=' + row_id) : (subEditUrl + '&rid=' + row_id);
	    _coreGrid('#' + subgrid_table_id, '#' + subgrid_pager_id, _subLoadUrl, _subEditUrl, subColNames, subColModel);

	    if (!isShowSubHeader)
	    {
		jQuery(".ui-subgrid").find(".ui-jqgrid-htable").hide();

	    }

	    _this.navButtons('#' + subgrid_table_id, '#' + subgrid_pager_id, subColModel, subNavButtons);
	};
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, gridComplete, true, subGridOptions, subGridRowExpanded, '', '', '', multiselect, onSelectRow);

	_this.navButtons(grid_selector, pager_selector, colModel, navButtons);
    };

    /**
     * 含有子列表
     */
    _this.hasSubList = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, subLoadUrl, subColModel, subColNames, isShowSubHeader)
    {
	_this.hasNavSubList(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, '', subLoadUrl, subColModel, subColNames, isShowSubHeader);
    };

    /**
     * 父数据行带操作按钮的列表 { name: 'operation', width: 80, fixed: true, sortable:
     * false, resize: false } opers : [{oper:'发布',action:function}]
     */
    _this.operHasNavSubList = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, subLoadUrl, subColModel, subColNames, opers, isShowSubHeader)
    {
	var gridComplete = function()
	{
	    var grid = jQuery(grid_selector);
	    var ids = grid.jqGrid('getDataIDs');
	    for (var i = 0, j = ids.length; i < j; i++)
	    {
		var cl = ids[i];
		var opers_button = '';
		for (var m = 0, n = opers.length; m < n; m++)
		{
		    opers_button += "<input type='button' value='" + opers[m].oper + "' onclick=\"(" + opers[m].action + ")(" + cl + ")\">";
		}
		grid.jqGrid('setRowData', cl,
		{
		    operation : opers_button
		});
	    }
	};
	_this.hasNavSubList(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, subLoadUrl, subColModel, subColNames, gridComplete, isShowSubHeader);
    };

    /**
     * 定制操作列表 navButtons : {add: true, del: true, edit: true, view: true,
     * search: true};
     */
    _this.hasNavSubList = function(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, navButtons, subLoadUrl, subColModel, subColNames, gridComplete, isShowSubHeader, multiselect, onSelectRow)
    {
	var subGridOptions =
	{
	    plusicon : "ace-icon fa fa-plus center bigger-110 blue",
	    minusicon : "ace-icon fa fa-minus center bigger-110 blue",
	    openicon : "ace-icon fa fa-chevron-right center orange"
	};
	var subGridRowExpanded = function(subgrid_id, row_id)
	{
	    var subgrid_table_id = subgrid_id + "_t";
	    jQuery("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table>");
	    var _subLoadUrl = subLoadUrl.indexOf('?') == -1 ? (subLoadUrl + '?rid=' + row_id) : (subLoadUrl + '&rid=' + row_id);

	    _coreGrid('#' + subgrid_table_id, '', _subLoadUrl, '', subColNames, subColModel);

	    if (!isShowSubHeader)
	    {
		jQuery(".ui-subgrid").find(".ui-jqgrid-htable").hide();

	    }
	};
	_coreGrid(grid_selector, pager_selector, loadUrl, editUrl, colNames, colModel, gridComplete, true, subGridOptions, subGridRowExpanded, '', '', '', multiselect, onSelectRow);
	_this.navButtons(grid_selector, pager_selector, colModel, navButtons);
    };

    // 操作按钮组函数
    _this.navButtons = function(grid_selector, pager_selector, colModel, navButtons)
    {
	// navButtons
	var _navButtons = navButtons ||
	{
	    add : false,
	    del : false,
	    edit : false,
	    view : false,
	    search : false,
	    refresh : false
	};
	jQuery(grid_selector).jqGrid('navGrid', pager_selector,
	{ // navbar options
	    edit : _navButtons.edit,
	    editicon : 'icon-pencil blue',
	    add : _navButtons.add,
	    addicon : 'icon-plus-sign purple',
	    del : _navButtons.del,
	    delicon : 'icon-trash red',
	    search : _navButtons.search,
	    searchicon : 'ace-icon fa fa-search orange',
	    refresh : _navButtons.refresh,
	    refreshicon : 'ace-icon fa fa-refresh green',
	    view : _navButtons.view,
	    viewicon : 'icon-zoom-in grey'
	},
	{
	    editCaption : "编辑",
	    closeAfterEdit : true,
	    recreateForm : true,
	    reloadAfterSubmit : false,
	    beforeShowForm : function(e)
	    {
		var form = $(e[0]);
		form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
		style_edit_form(form);
	    },
//	    afterSubmit : function(data, postdata, oper)
//	    {
//		var response = data.responseJSON;
//		if (response.hasOwnProperty("error"))
//		{
//		    if (response.error.length)
//		    {
//			return
//			[ false, response.error ];
//		    }
//		}
//		return
//		[ true, "", "" ];
//	    },
	    errorTextFormat : function(data)
	    {
		return 'Error: ' + data.responseText
	    }
	},
	{
	    addCaption : "添加",
	    closeAfterAdd : true,
	    recreateForm : true,
	    viewPagerButtons : false,
	    reloadAfterSubmit : false,
	    beforeShowForm : function(e)
	    {
		// 设置tree parent
		// var parent = jQuery('#parent');
		// if (parent && parent.length) {
		// var id = jQuery(grid_selector).jqGrid('getGridParam',
		// 'selrow');
		// if (id) {
		// jQuery('#parent').val(id);
		// }
		// }

		for (var i = 0, j = colModel.length; i < j; i++)
		{
		    e.find('#' + colModel[i].name).attr('disabled', false);
		}
		var form = $(e[0]);
		form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
		style_edit_form(form);
	    },
//	    afterSubmit : function(data, postdata, oper)
//	    {
//		alert(data);
//		var response = data.responseJSON;
//		if (response.hasOwnProperty("error"))
//		{
//		    if (response.error.length)
//		    {
//			return
//			[ false, response.error ];
//		    }
//		}
//		alert(response);
//		return
//		[ true, "", "" ];
//	    },
	    errorTextFormat : function(data)
	    {
		var response = jQuery.parseJSON(data.responseText);
		return '错误: ' + response.body;
	    }
	},
	{
	    caption : "删除",
	    recreateForm : true,
	    beforeShowForm : function(e)
	    {
		var form = $(e[0]);
		if (form.data('styled'))
		    return false;

		form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
		style_delete_form(form);

		form.data('styled', true);
	    },
//	    afterSubmit : function(data, postdata, oper)
//	    {
//		var response = data.responseJSON;
//		if (response.hasOwnProperty("error"))
//		{
//		    if (response.error.length)
//		    {
//			return
//			[ false, response.error ];
//		    }
//		}
//		return
//		[ true, "", "" ];
//	    },
	    errorTextFormat : function(data)
	    {
		return 'Error: ' + data.responseText
	    }
	},
	{
	    // 搜索 表单
	    recreateForm : true,
	    afterShowSearch : function(e)
	    {
		var form = $(e[0]);
		form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
		style_search_form(form);
	    },
	    afterRedraw : function()
	    {
		style_search_filters($(this));
	    },
	    multipleSearch : true
	/**
	 * multipleGroup:true, showQuery: true
	 */
	},
	{
	    // 查看记录 表单
	    recreateForm : true,
	    beforeShowForm : function(e)
	    {
		var form = $(e[0]);
		form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
	    },
	    afterSubmit : function(data, postdata, oper)
	    {
		var response = data.responseJSON;
		if (response.hasOwnProperty("error"))
		{
		    if (response.error.length)
		    {
			return [ false, response.error, ""  ];
		    }
		}
		return [ true, "", "" ];
	    },
	    errorTextFormat : function(data)
	    {
		return 'Error: ' + data.responseText
	    }
	});
    };

    // 自适应
    _this.resize = function(grid_selector)
    {
	// 拉伸缩小时适应当前页面
	$(window).on('resize.jqGrid', function()
	{
	    $(grid_selector).jqGrid('setGridWidth', $(grid_selector+"-div").width());
	})
	// 当前侧边栏隐藏显示/缩小时拉伸/缩小jqgrid
	var parent_column = $(grid_selector).closest('[class*="col-"]');
	$(document).on('settings.ace.jqGrid', function(ev, event_name, collapsed)
	{
	    alert(event_name);
	    if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed')
	    {
		// setTimeout is for webkit only to give time for DOM changes
		// and then redraw!!!
		setTimeout(function()
		{
		    $(grid_selector).jqGrid('setGridWidth', parent_column.width());
		}, 0);
	    }
	})

	// if your grid is inside another element, for example a tab pane, you
	// should use its parent's width:
	/**
	 * $(window).on('resize.jqGrid', function () { var parent_width =
	 * $(grid_selector).closest('.tab-pane').width();
	 * $(grid_selector).jqGrid( 'setGridWidth', parent_width ); }) //and
	 * also set width when tab pane becomes visible $('#myTab
	 * a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	 * if($(e.target).attr('href') == '#mygrid') { var parent_width =
	 * $(grid_selector).closest('.tab-pane').width();
	 * $(grid_selector).jqGrid( 'setGridWidth', parent_width ); } })
	 */

	$(window).triggerHandler('resize.jqGrid');// trigger window resize to
						    // make the grid get the
						    // correct size

    };

    // 添加辅助函数
    _this.addFun = function(grid_selector)
    {
	// enable search/filter toolbar
	// jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
	// jQuery(grid_selector).filterToolbar({});

	// var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

	$(document).one('ajaxloadstart.page', function(e)
	{
	    $(grid_selector).jqGrid('GridUnload');
	    $('.ui-jqdialog').remove();
	});
    };

    // datepicker 时间控件
    _this.datepicker = function(cellvalue, options, cell)
    {
	setTimeout(function()
	{

	    $.fn.datepicker.dates['zh'] =
	    {
		days :
		[ "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ],
		daysShort :
		[ "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ],
		daysMin :
		[ "天", "一", "二", "三", "四", "五", "六" ],
		months :
		[ "一月", "二月", "三月", "四月", "五岳", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
		monthsShort :
		[ "一月", "二月", "三月", "四月", "五岳", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
		today : "今天",
		clear : "Clear",
		format : "yyyy年mm月dd日",
		titleFormat : "yyyy MM",
		weekStart : 0
	    };

	    $(cell).find('input[type=text]').datepicker(
	    {
		format : 'yyyy-mm-dd',
		autoclose : 'true',
		startDate : '0d',
		disableTouchKeyboard : 'true',
		language : 'zh'
	    });
	}, 0);
    };

    // 样式定制函数开始

    // switch element when editing inline
    function aceSwitch(cellvalue, options, cell)
    {
	setTimeout(function()
	{
	    $(cell).find('input[type=checkbox]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
	}, 0);
    }

    function style_edit_form(form)
    {
	// enable datepicker on "sdate" field and switches for "stock" field
	form.find('input[name=sdate]').datepicker(
	{
	    format : 'yyyy-mm-dd',
	    autoclose : true
	})

	form.find('input[name=stock]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
	// don't wrap inside a label element, the checkbox value won't be
	// submitted (POST'ed)
	// .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline"
	// />').after('<span class="lbl"></span>');

	// update buttons classes
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();// ui-icon,
									// s-icon
	buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
	buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

	buttons = form.next().find('.navButton a');
	buttons.find('.ui-icon').hide();
	buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
	buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
    }

    function style_delete_form(form)
    {
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();// ui-icon,
											    // s-icon
	buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
	buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
    }

    function style_search_filters(form)
    {
	form.find('.delete-rule').val('X');
	form.find('.add-rule').addClass('btn btn-xs btn-primary');
	form.find('.add-group').addClass('btn btn-xs btn-success');
	form.find('.delete-group').addClass('btn btn-xs btn-danger');
    }

    function style_search_form(form)
    {
	var dialog = form.closest('.ui-jqdialog');
	var buttons = dialog.find('.EditTable')
	buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
	buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
	buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
    }

    function beforeDeleteCallback(e)
    {
	var form = $(e[0]);
	if (form.data('styled'))
	    return false;

	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
	style_delete_form(form);

	form.data('styled', true);
    }

    function beforeEditCallback(e)
    {
	var form = $(e[0]);
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
	style_edit_form(form);
    }

    // it causes some flicker when reloading or navigating grid
    // it may be possible to have some custom formatter to do this as the grid
    // is being created to prevent this
    // or go back to default browser checkbox styles for the grid
    function styleCheckbox(table)
    {
	/**
	 * $(table).find('input:checkbox').addClass('ace') .wrap('<label />')
	 * .after('<span class="lbl align-top" />')
	 * 
	 * 
	 * $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
	 * .find('input.cbox[type=checkbox]').addClass('ace') .wrap('<label
	 * />').after('<span class="lbl align-top" />');
	 */
    }

    // unlike navButtons icons, action icons in rows seem to be hard-coded
    // you can change them like this in here if you want
    function updateActionIcons(table)
    {
	/**
	 * var replacement = { 'ui-ace-icon fa fa-pencil' : 'ace-icon fa
	 * fa-pencil blue', 'ui-ace-icon fa fa-trash-o' : 'ace-icon fa
	 * fa-trash-o red', 'ui-icon-disk' : 'ace-icon fa fa-check green',
	 * 'ui-icon-cancel' : 'ace-icon fa fa-times red' };
	 * $(table).find('.ui-pg-div span.ui-icon').each(function(){ var icon =
	 * $(this); var $class = $.trim(icon.attr('class').replace('ui-icon',
	 * '')); if($class in replacement) icon.attr('class', 'ui-icon
	 * '+replacement[$class]); })
	 */
    }

 // replace icons with FontAwesome icons like above
    function updatePagerIcons(table)
    {
        var replacement =
        {
    	'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
    	'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
    	'ui-icon-seek-next' : 'icon-angle-right bigger-140',
    	'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function()
        {
    	var icon = $(this);
    	var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

    	if ($class in replacement)
    	    icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    }

    function enableTooltips(table)
    {
        $('.navtable .ui-pg-button').tooltip(
        {
    	container : 'body'
        });
        $(table).find('.ui-pg-div').tooltip(
        {
    	container : 'body'
        });
    }
    // 样式定制函数结束

    function opErrorMsg(data)
    {
	var code = data.code;
	switch (code)
	{
	case 200: // 正常返回
	    break;
	case 8:
		alertMsgERROR(data.msg);
	    // 没有页面
	    break;
	case 1:
	    // 错误的请求
		alertMsgERROR(data.msg);
	    break;
	case 6:
	    // 未经许可的请求
	    // 不管成功错误都返回登录页面
		alertMsgERROR(data.msg);
	    break;
	case 9:
	    // 业务类异常
		alertMsgERROR(data.msg);
	    break;
	default:
	    // 其他业务类自定义异常
		alertMsgERROR(data.msg);
	}
    }
 })(window.cgrid = {}, jQuery);

 
 
 