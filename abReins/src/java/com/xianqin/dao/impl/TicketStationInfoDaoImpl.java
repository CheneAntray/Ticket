package com.xianqin.dao.impl;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TicketStationInfoDao;
import com.xianqin.domain.TicketStationInfo;
/**
 * 售票站信息对象数据访问层接口实现类
 * 该接口实现了ticketStationInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("ticketStationInfoDao")
public class TicketStationInfoDaoImpl extends CommonDaoImpl<TicketStationInfo, String> implements TicketStationInfoDao{
	@Override
	public void saveTicketStationInfo(TicketStationInfo ticketStationInfo) {
		this.save(ticketStationInfo);
	}

	@Override
	public void updateTicketStationInfo(TicketStationInfo ticketStationInfo) {
		this.update(ticketStationInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TicketStationInfo> queryTicketStationInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteTicketStationInfoById(String ticketStationInfoId) {
		String hql="DELETE FROM TicketStationInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, ticketStationInfoId).executeUpdate();
	}
	
	@Override
	public TicketStationInfo getTicketStationInfoByCondition(QueryRule queryRule) {
		List<TicketStationInfo> ticketStationInfos=this.find(queryRule);
		TicketStationInfo ticketStationInfo=null;
		if (ticketStationInfos!=null && !ticketStationInfos.isEmpty()) {
			ticketStationInfo=ticketStationInfos.get(0);
		}
		return ticketStationInfo;
	}

	@Override
	public List<TicketStationInfo> getTicketStationInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}
}
