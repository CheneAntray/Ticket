package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TrainDirectionDao;
import com.xianqin.domain.TrainDirection;
import com.xianqin.service.TrainDirectionService;

@Service("trainDirectionService")
public class TrainDirectionServiceImpl implements TrainDirectionService {

	@Resource
	private TrainDirectionDao trainDirectionDao;

	@Override
	public ReturnMap queryTrainDirectionByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		Page<TrainDirection> page = trainDirectionDao.queryTrainDirectionInfo(queryRule, pageIndex, pageSize);
		returnMap.setSucc();
		returnMap.setObjContext(page);
		returnMap.setMsg("分页查询用户成功");
		return returnMap;
	}

	@Override
	public ReturnMap saveTrainDirection(TrainDirection trainDirection) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		if(queryDirectionIfExist(trainDirection.getDirectionName())){
			trainDirectionDao.saveTrainDirectionInfo(trainDirection);
			returnMap.setSucc();
			returnMap.setMsg("添加车次方向信息成功");
		}else {
			returnMap.setMsg("添加车次方向信息已存在，请重新添加！");
		}
		return returnMap;
	}

	@Override
	public ReturnMap getDirectionById(Long id) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(TrainDirection._id, id);
		TrainDirection trainDirection=trainDirectionDao.getTrainDirectionInfoByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setObjContext(trainDirection);
		returnMap.setMsg("查询车次方向信息成功");
		return returnMap;
	}

	@Override
	public ReturnMap editDirectionById(TrainDirection trainDirection) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(TrainDirection._id, trainDirection.getId());
		TrainDirection trainDirections=trainDirectionDao.getTrainDirectionInfoByCondition(queryRule);
		if (trainDirections!=null) {
			trainDirections.setDirectionName(trainDirection.getDirectionName());
			trainDirectionDao.updateTrainDirectionInfo(trainDirections);
			returnMap.setSucc();
			returnMap.setMsg("查询车次方向信息成功");
		}
		return returnMap;
	}

	@Override
	public boolean queryDirectionIfExist(String name) throws Exception {
		// TODO Auto-generated method stub
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(TrainDirection._directionName, name);
		TrainDirection trainDirection = trainDirectionDao.getTrainDirectionInfoByCondition(queryRule);
		if (trainDirection != null && trainDirection.getDirectionName().equals(name)) {
			return false;
		}
		return true;
	}

	@Override
	public ReturnMap queryDirectionSelect(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap =new ReturnMap();
		List<TrainDirection> list=trainDirectionDao.getTrainDirectionInfoListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(list);
		returnMap.setMsg("查询全部车次方向信息成功");
		return returnMap;
	}

}
