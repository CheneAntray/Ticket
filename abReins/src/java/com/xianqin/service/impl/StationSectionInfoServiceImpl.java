package com.xianqin.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.StationSectionInfoDao;
import com.xianqin.domain.StationSectionInfo;
import com.xianqin.service.InitDataService;
import com.xianqin.service.StationSectionInfoService;

@Service("stationSectionInfoService")
public class StationSectionInfoServiceImpl implements StationSectionInfoService {

	@Resource
	private StationSectionInfoDao stationSectionInfoDao;
	@Autowired
	private InitDataService initDataService;
	@Override
	//@CacheEvict(value="init-data",key="STATION_SECTION_INFO",beforeInvocation=true)
	public ReturnMap saveStationSectionInfo(StationSectionInfo stationSectionInfo) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap =new ReturnMap();
		@SuppressWarnings("unchecked")
		Map<String, String> map=(Map<String, String>) initDataService.getEhcacheMapByTableName(StationSectionInfo.TABLE_NAME);
		String name=map.get(stationSectionInfo.getName());
		if (name==null) {
			stationSectionInfoDao.saveStationSectionInfo(stationSectionInfo);
			map.put(stationSectionInfo.getName(), stationSectionInfo.getId().toString());
		}
		returnMap.setSucc();
		returnMap.setMsg("成功");
		returnMap.setMapContext(map);
		return returnMap;
	}

	@Override
	public ReturnMap updateStationSectionInfo(StationSectionInfo stationSectionInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnMap queryStationSectionInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnMap deleteStationSectionInfoById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StationSectionInfo getStationSectionInfoByCondition(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
