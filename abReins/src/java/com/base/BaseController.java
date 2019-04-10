package com.base;

import org.apache.log4j.Logger;

import com.base.utils.LogUtils;

/**
 * 所有控制器层的基类
 * 只提供了日志功能
 * 类其他功能待扩展
 * @author xianqin-bill
 *
 */
public class BaseController {
	
	/**
	 * 日志对象初始化
	 */
	protected Logger logger = LogUtils.getConsoleLogIns();
}
