package com.xianqin.dao.impl;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TrainStationInfoDao;
import com.xianqin.domain.TrainStationInfo;
/**
 * 车次到始站信息对象数据访问层接口实现类
 * 该接口实现了trainStationInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("trainStationInfoDao")
public class TrainStationInfoDaoImpl extends CommonDaoImpl<TrainStationInfo, String> implements TrainStationInfoDao{
	@Override
	public void saveTrainStationInfo(TrainStationInfo trainStationInfo) {
		this.save(trainStationInfo);
	}

	@Override
	public void updateTrainStationInfo(TrainStationInfo trainStationInfo) {
		this.update(trainStationInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TrainStationInfo> queryTrainStationInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteTrainStationInfoById(String trainStationInfoId) {
		String hql="DELETE FROM TrainStationInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, trainStationInfoId).executeUpdate();
	}
	
	@Override
	public TrainStationInfo getTrainStationInfoByCondition(QueryRule queryRule) {
		List<TrainStationInfo> trainStationInfos=this.find(queryRule);
		TrainStationInfo trainStationInfo=null;
		if (trainStationInfos!=null && !trainStationInfos.isEmpty()) {
			trainStationInfo=trainStationInfos.get(0);
		}
		return trainStationInfo;
	}

	@Override
	public List<TrainStationInfo> getTrainStationInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}
}
