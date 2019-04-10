package com.base;

/**
 * 该接口用于定义系统中所有需要使用到的数据常量
 * @author xianqin-bill
 */
public interface ApplicationDefined {
	
	/**
	 * 默认的每页数据显示数量
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 控制器客户端业务处理请求状态-成功
	 * 业务请求正常响应，请注意，查询无结果，也算是是成功
	 */
	public static final int PROCESS_CODE_SUCCESS = 0;
	
	/**
	 * 控制器客户端业务处理请求状态-失败
	 * 失败包含多种：数据校验、业务规则校验等导致的操作响应，都是失败
	 */
	public static final int PROCESS_CODE_FAIL = 1;
	
	/**
	 * 控制器客户端业务处理请求状态-异常
	 * 这里指业务处理产生了runTime异常，一般为设计缺陷或软件BUG所致
	 */
	public static final int PROCESS_CODE_ERROR = 6;
	
	/**
	 * 用户权限校验失败
	 */
	public static final int USER_PWR_CHECK_FAIL = 8; 
	
	/**
	 * 用户身份校验失败
	 */
	public static final int USER_CHECK_FAIL = 9;
	
	/**
	 * HTTP请求成功
	 */
	public static final int HTTP_STATUS_SUCC=0;
	
	/**
	 * HTTP请求失败
	 */
	public static final int HTTP_STATUS_FAIL=1;
}
