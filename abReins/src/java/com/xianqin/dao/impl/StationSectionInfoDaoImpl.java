package com.xianqin.dao.impl;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.StationSectionInfoDao;
import com.xianqin.domain.StationSectionInfo;
/**
 * 站段信息对象数据访问层接口实现类
 * 该接口实现了stationSectionInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("stationSectionInfoDao")
public class StationSectionInfoDaoImpl extends CommonDaoImpl<StationSectionInfo, String> implements StationSectionInfoDao{
	@Override
	public void saveStationSectionInfo(StationSectionInfo stationSectionInfo) {
		this.save(stationSectionInfo);
	}

	@Override
	public void updateStationSectionInfo(StationSectionInfo stationSectionInfo) {
		this.update(stationSectionInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<StationSectionInfo> queryStationSectionInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteStationSectionInfoById(String stationSectionInfoId) {
		String hql="DELETE FROM StationSectionInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, stationSectionInfoId).executeUpdate();
	}
	
	@Override
	public StationSectionInfo getStationSectionInfoByCondition(QueryRule queryRule) {
		List<StationSectionInfo> stationSectionInfos=this.find(queryRule);
		StationSectionInfo stationSectionInfo=null;
		if (stationSectionInfos!=null && !stationSectionInfos.isEmpty()) {
			stationSectionInfo=stationSectionInfos.get(0);
		}
		return stationSectionInfo;
	}

	@Override
	public List<StationSectionInfo> getStationSectionInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}
}
