package com.xianqin.dao.impl;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TrainNumberInfoDao;
import com.xianqin.domain.TrainNumberInfo;
/**
 * 车次信息对象数据访问层接口实现类
 * 该接口实现了trainNumberInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("trainNumberInfoDao")
public class TrainNumberInfoDaoImpl extends CommonDaoImpl<TrainNumberInfo, String> implements TrainNumberInfoDao{
	@Override
	public void saveTrainNumberInfo(TrainNumberInfo trainNumberInfo) {
		this.save(trainNumberInfo);
	}

	@Override
	public void updateTrainNumberInfo(TrainNumberInfo trainNumberInfo) {
		this.update(trainNumberInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TrainNumberInfo> queryTrainNumberInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteTrainNumberInfoById(String trainNumberInfoId) {
		String hql="DELETE FROM TrainNumberInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, trainNumberInfoId).executeUpdate();
	}
	
	@Override
	public TrainNumberInfo getTrainNumberInfoByCondition(QueryRule queryRule) {
		List<TrainNumberInfo> trainNumberInfos=this.find(queryRule);
		TrainNumberInfo trainNumberInfo=null;
		if (trainNumberInfos!=null && !trainNumberInfos.isEmpty()) {
			trainNumberInfo=trainNumberInfos.get(0);
		}
		return trainNumberInfo;
	}

	@Override
	public List<TrainNumberInfo> getTrainNumberInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}

	@Override
	public void updateTrainNumberByDirectionId(Long directionId,Long trainnumberId) {
		// TODO Auto-generated method stub
	    String hql="UPDATE TrainNumberInfo SET "+TrainNumberInfo._directionId+" = :directionId WHERE "+TrainNumberInfo._id+" = :trainNumberId";
	    this.getSessionFactory().getCurrentSession().createQuery(hql)
	    .setParameter("directionId", directionId)
	    .setParameter("trainNumberId", trainnumberId)
	    .executeUpdate();
		
	}
}
