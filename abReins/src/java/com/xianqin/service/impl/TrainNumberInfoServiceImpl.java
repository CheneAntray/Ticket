package com.xianqin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TrainNumberInfoDao;
import com.xianqin.domain.TrainNumberInfo;
import com.xianqin.service.InitDataService;
import com.xianqin.service.TrainNumberInfoService;

@Service("trainNumberInfoService")
public class TrainNumberInfoServiceImpl implements TrainNumberInfoService {
	
	@Autowired
	private InitDataService initDataService;

	@Resource
	private TrainNumberInfoDao trainNumberInfoDao;
	
	private Map<String, TrainNumberInfo> trainNumberMap=null;

	@SuppressWarnings("unchecked")
	@Override
	public ReturnMap saveTrainNumberInfo(TrainNumberInfo trainNumberInfo) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		trainNumberMap = (Map<String, TrainNumberInfo>) initDataService
				.getEhcacheMapByTableName(TrainNumberInfo.TABLE_NAME);
		trainNumberInfoDao.saveTrainNumberInfo(trainNumberInfo);
		trainNumberMap.put(trainNumberInfo.getTrainNo(), trainNumberInfo);
		returnMap.setSucc();
		returnMap.setMsg("添加车次信息成功！");
		return returnMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReturnMap updateTrainNumberInfo(TrainNumberInfo trainNumberInfo) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		QueryRule queryRule = QueryRule.getInstance();
		trainNumberMap = (Map<String, TrainNumberInfo>) initDataService
				.getEhcacheMapByTableName(TrainNumberInfo.TABLE_NAME);
		queryRule.addEqual(TrainNumberInfo._trainNo, trainNumberInfo.getTrainNo());
		returnMap = getTrainNumberInfoByCondition(queryRule);
		TrainNumberInfo trainNumberInfo2 = (TrainNumberInfo) returnMap.getObjContext();
		if(trainNumberInfo2!=null){
			trainNumberInfo2.setStartStationId(trainNumberInfo.getStartStationId());
			trainNumberInfo2.setEndStationId(trainNumberInfo.getEndStationId());
			trainNumberInfo2.setUnderEnterId(trainNumberInfo.getUnderEnterId());
			trainNumberInfoDao.updateTrainNumberInfo(trainNumberInfo2);
			trainNumberMap.put(trainNumberInfo2.getTrainNo(), trainNumberInfo2);
			returnMap.setSucc();
			returnMap.setMsg("修改车次信息成功！");
		}
		
		return returnMap;
	}

	@Override
	public ReturnMap queryTrainNumberInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnMap ret = new ReturnMap();
		ret.setSucc();
		ret.setObjContext(trainNumberInfoDao.queryTrainNumberInfoByPage(queryRule, pageIndex, pageSize));
		ret.setMsg("查询车次信息成功");
		return ret;
	}

	@Override
	public ReturnMap deleteTrainNumberInfoById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnMap getTrainNumberInfoByCondition(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap ret = new ReturnMap();
		TrainNumberInfo trainNumberInfo = trainNumberInfoDao.getTrainNumberInfoByCondition(queryRule);
		ret.setObjContext(trainNumberInfo);
		ret.setSucc();
		ret.setMsg("查询车次信息成功");
		return ret;
	}

	@Override
	public ReturnMap queryTrainNumberInfoByCondition(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		List<TrainNumberInfo> list = trainNumberInfoDao.getTrainNumberInfoListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(list);
		returnMap.setMsg("查询车次信息成功");
		return returnMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReturnMap updateTrainNumberByDirectionId(Long directionId, Long[] trainnumberId) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		TrainNumberInfo trainNumberInfo;
		trainNumberMap = (Map<String, TrainNumberInfo>) initDataService
				.getEhcacheMapByTableName(TrainNumberInfo.TABLE_NAME);
		for (int i = 0; i < trainnumberId.length; i++) {
			trainNumberInfoDao.updateTrainNumberByDirectionId(directionId, trainnumberId[i]);
			for (Map.Entry<String, TrainNumberInfo> m1 : trainNumberMap.entrySet()) {
				trainNumberInfo=m1.getValue();
				if (trainNumberInfo.getId()==trainnumberId[i]) {
					trainNumberInfo.setDirectionId(directionId);
				}
				trainNumberMap.put(trainNumberInfo.getTrainNo(), trainNumberInfo);
				break;
			}
		}
		returnMap.setSucc();
		returnMap.setMsg("移除车次成功");
		return returnMap;
	}

}
