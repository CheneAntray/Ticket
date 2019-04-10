var dictEnum = (function()
{
    var codeValues =
    {
	// DIFINE开头均为数据字典定义代码
	DEFINE_YESORNO : 'yesOrNo', // 是否标记
	DEFINE_SOURCE_TYPE : 'reinsurerType', // 业务来源性质
	DEFINE_COMPANY_TYPE : 'companyType', // 公司属性
	DEFINE_BILL_TYPE : 'billType', // 账单类型
	DEFINE_BUSINESS_TYPE : 'reinsBusinessType', // 接受人业务类型
	DEFINE_PAY_METHOD : 'payMethod', // 付款方式
	DEFINE_TREATY_TYPE : 'treatyType', // 合约类型
	DEFINE_TREATY_STATUS : 'treatyStatus', // 合约状态
	DEFINE_SEX : 'sex', // 性别
	DEFINE_ID_TYPE : 'idType', // 证件类型
	DEFINE_PE_OPTTYPE : 'optType', // 保批单类型
	DEFINE_COINSFLAG : 'coinsFlag', // 共保方式
	DEFINE_SPLITMERGEFLAG : 'splitMergeFlag', // 合单标志
	DEFINE_CLASSCODE : 'classCode', // 险类
	DEFINE_RISKCODE : 'riskCode', //险种
	DEFINE_CURRENCY : 'currency', // 币种
	DEFINE_BUSINESS_STATE : 'businessState',// 业务类型（赔案）
	DEFINE_BUSINESS_TYPE : 'businessType',// 业务类型（账单）
	DEFINE_REINSMODE : 'reinsMode',// 分保方式
	DEFINE_ACC_KIND : 'accKind',// 账单种类
	DEFINE_ACC_TYPE : 'accType',// 账单类型
	DEFINE_ACC_STATUS : 'accStatus',// 账单状态
	DEFINE_INDIVIDUAL_FLAG : 'individualFlag',// 个团险标识
	DEFINE_VAT_FLAG : 'vatFlag',// 增值税标识
	DEFINE_ACCITEM_CODE : 'accitemCode',// 帐单项目编码
	DEFINE_ITEM_PLACE : 'itemPlace',// 借贷方标志
	DEFINE_COLOSEORQUARTER :'closeOrQuarter',//账单关门类型：关门账/季度账
	DEFINE_WRITEOFF_FLAG : "writeOff",//冲销标识
	DEFINE_DETAILTYPE : "detailType",//临分清单类型
	DEFINE_DETAILKIND : "detailKind",//临分清单种类
	DEFINE_TREATYKIND : "treatyKind",//超赔合约种类
	DEFINE_STATEFLAG : "stateFlag",//超赔合约状态
	DEFINE_FHXOPTTYPE : "fhxOptType",//超赔合约业务类型
	DEFINE_FHXTREATYTYPE : "fhxTreatyType",//超赔合约类型
	DEFINE_BILLPERIOD : "billPeriod",//账单周期
	DEFINE_EXTENDFLAG : "extendFlag",//超赔合约产生
	DEFINE_REVIEWFLAG : "reviewFlag",//超赔临分复核标志
	DEFINE_FOXPOPTTYPE : "foxpOptType",//超赔临分业务类型
	DEFINE_FOXPCONNTYPE : "foxpConnType",//超赔临分关联类型
	DEFINE_FOCREVIEWFLAG : "fcoreviewflag",//分出复核状态
	// PARAM开头均为系统参数代码
	PARAM_VERSION : 'versionNo', // 系统版本号

	// 代码值
	YESORNO_YES : '1',
	YESORNO_NO : '0',
	TREATY_TEMP : '0',
	TREATY_VALID : '1',
	TREATY_CLOSE : '2',
	PE_OPTTYPE_P : 'P',
	PE_OPTTYPE_E : 'E',
	COINSFLAG_NO : '0', // 普通
	COINSFLAG_CO : '1', // 共保
	// COINSFLAG_JO : '2', //联保
	SPLITMERGEFLAG_NO : '00', // 非合单
	SPLITMERGEFLAG_MM : '11', // 合单主单
	SPLITMERGEFLAG_FP : '10', // 合单从单
	SOURCE_TYPE_REINSURER : '1', // 接受人
	SOURCE_TYPE_BROKER : '0', // 经纪人
	BUSINESS_STATE_W : 'W',// 已决
	BUSINESS_STATE_M : 'M',// 未决
	BUSINESS_TYPE_IN : "1",// 分入业务
	BUSINESS_TYPE_OUT : "0",// 分出业务
	ACC_TYPE_FACREINS : "10",// 对外临分账单
	ACC_TYPE_TREATY : "11",// 对外合同账单
	ACCITEM_CODE_REINS : "01",// 帐单项目编码-分保费
	ACCITEM_CODE_COMM : "02",// 帐单项目编码-手续费
	ACCITEM_CODE_TAX : "03",// 帐单项目编码-税
	ACCITEM_CODE_OTHER : "04",// 帐单项目编码-其他费用
	ITEM_PLACE_C : "C",// 贷方
	ITEM_PLACE_D : "D",// 借方
	ACC_STATUS_INIT : "0",//暂存
	ACC_STATUS_EFFECT : "1",//已生效
	ACC_STATUS_TOFIN : "2",//已转财务
	ACC_STATUS_TOPAY : "3",//已转结算
	// JQGRID编辑调用后台无处理URL
	EMPTY_JQGRID_URL : 'api/jqgrid/'
    }
    var Constructor = {};
    Constructor.getCodeValue = function(name)
    {
	return codeValues[name];
    }
    return Constructor
})();