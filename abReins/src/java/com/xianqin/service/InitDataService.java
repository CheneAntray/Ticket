package com.xianqin.service;

import java.util.Map;

public interface InitDataService {
	
	/**
	 * 将站段信息加入到缓存
	 * @param stationSection
	 * @param urlSet
	 */
	void addEhcacheMapByTableName(String tableName, Map<String, ?> stationSectionMap) throws Exception;

	/**
	 * 在缓存中根据TableName取出Map集合
	 */
	Map<String, ?> getEhcacheMapByTableName(String tableName) throws Exception;
}
