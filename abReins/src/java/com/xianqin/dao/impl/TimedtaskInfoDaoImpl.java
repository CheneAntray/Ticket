package com.xianqin.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TimedtaskInfoDao;
import com.xianqin.domain.TimedtaskInfo;
/**
 * 定时任务表对象数据访问层接口实现类
 * 该接口实现了timedtaskInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("timedtaskInfoDao")
public class TimedtaskInfoDaoImpl extends CommonDaoImpl<TimedtaskInfo, String> implements TimedtaskInfoDao{
	@Override
	public void saveTimedtaskInfo(TimedtaskInfo timedtaskInfo) {
		timedtaskInfo.setId(UUIDGenerator.getUUID()); //生成主键
		this.save(timedtaskInfo);
	}

	@Override
	public void updateTimedtaskInfo(TimedtaskInfo timedtaskInfo) {
		this.update(timedtaskInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TimedtaskInfo> queryTimedtaskInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteTimedtaskInfoById(String timedtaskInfoId) {
		String hql="DELETE FROM TimedtaskInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, timedtaskInfoId).executeUpdate();
	}
	
	@Override
	public TimedtaskInfo getTimedtaskInfoByCondition(QueryRule queryRule) {
		List<TimedtaskInfo> timedtaskInfos=this.find(queryRule);
		TimedtaskInfo timedtaskInfo=null;
		if (timedtaskInfos!=null && !timedtaskInfos.isEmpty()) {
			timedtaskInfo=timedtaskInfos.get(0);
		}
		return timedtaskInfo;
	}

	@Override
	public List<TimedtaskInfo> getTimedtaskInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}
}
