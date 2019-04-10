package com.xianqin.service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;

public interface MenuInfoService {
	
	/**
	 * 查询菜单信息
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	ReturnMap getMenuInfo(QueryRule queryRule) throws Exception;
}
