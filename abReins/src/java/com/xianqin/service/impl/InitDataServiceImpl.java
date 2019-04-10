package com.xianqin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import com.xianqin.service.InitDataService;
@Service("initDataService")
public class InitDataServiceImpl implements InitDataService {
	
	private Cache cache;
	
	@Autowired
    public InitDataServiceImpl(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("init-data");
    }

	@Override
	public void addEhcacheMapByTableName(String tableName, Map<String, ?> map) throws Exception {
		// TODO Auto-generated method stub
		cache.put(tableName, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ?> getEhcacheMapByTableName(String tableName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, ?> map=null;
		if(cache.get(tableName)!=null){
			map=new HashMap<String, String>();
    		map = (Map<String, ?>)cache.get(tableName).get();
    	}
		return map;
	}

	

}
