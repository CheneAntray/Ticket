package com.xianqin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TrainDirectionDao;
import com.xianqin.domain.TrainDirection;
@Repository("trainDirectionDao")
public class TrainDirectionDaoImpl  extends CommonDaoImpl<TrainDirection, String> implements TrainDirectionDao{

	@Override
	public void saveTrainDirectionInfo(TrainDirection trainDirection) {
		// TODO Auto-generated method stub
		this.save(trainDirection);
	}

	@Override
	public void updateTrainDirectionInfo(TrainDirection trainDirection) {
		// TODO Auto-generated method stub
		this.update(trainDirection);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TrainDirection> queryTrainDirectionInfo(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		return  this.find(queryRule, pageIndex, pageSize);
	}

	@Override
	public void deleteTrainDirectionInfo(String id) {
		// TODO Auto-generated method stub
		String hql="DELETE FROM TrainDirection WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, id).executeUpdate();
	}

	@Override
	public TrainDirection getTrainDirectionInfoByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		List<TrainDirection> trainDirections=this.find(queryRule);
		TrainDirection trainDirection=null;
		if (trainDirections!=null && !trainDirections.isEmpty()) {
			trainDirection=trainDirections.get(0);
		}
		return trainDirection;
	}

	@Override
	public List<TrainDirection> getTrainDirectionInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}

}
