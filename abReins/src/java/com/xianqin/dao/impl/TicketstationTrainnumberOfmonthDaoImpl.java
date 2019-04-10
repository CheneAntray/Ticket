package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TicketstationTrainnumberOfmonthDao;
import com.xianqin.domain.TicketstationTrainnumberOfmonth;
/**
 * 售票站车次月统计信息表对象数据访问层接口实现类
 * 该接口实现了ticketstationTrainnumberOfmonthDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("ticketstationTrainnumberOfmonthDao")
public class TicketstationTrainnumberOfmonthDaoImpl extends CommonDaoImpl<TicketstationTrainnumberOfmonth, String> implements TicketstationTrainnumberOfmonthDao{
	@Override
	public void saveTicketstationTrainnumberOfmonth(TicketstationTrainnumberOfmonth ticketstationTrainnumberOfmonth) {
		ticketstationTrainnumberOfmonth.setId(UUIDGenerator.getUUID()); //生成主键
		ticketstationTrainnumberOfmonth.setCreateDate(new Date());    //创建时间
		this.save(ticketstationTrainnumberOfmonth);
	}

	@Override
	public void updateTicketstationTrainnumberOfmonth(TicketstationTrainnumberOfmonth ticketstationTrainnumberOfmonth) {
		this.update(ticketstationTrainnumberOfmonth);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TicketstationTrainnumberOfmonth> queryTicketstationTrainnumberOfmonthByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteTicketstationTrainnumberOfmonthById(String ticketstationTrainnumberOfmonthId) {
		String hql="DELETE FROM TicketstationTrainnumberOfmonth WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, ticketstationTrainnumberOfmonthId).executeUpdate();
	}
    
    public void deleteTicketTrainnumberOfmonthByMonth(String month){
    	String hql="DELETE FROM TicketstationTrainnumberOfmonth WHERE month=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, month).executeUpdate();
    }
	
	@Override
	public TicketstationTrainnumberOfmonth getTicketstationTrainnumberOfmonthByCondition(QueryRule queryRule) {
		List<TicketstationTrainnumberOfmonth> ticketstationTrainnumberOfmonths=this.find(queryRule);
		TicketstationTrainnumberOfmonth ticketstationTrainnumberOfmonth=null;
		if (ticketstationTrainnumberOfmonths!=null && !ticketstationTrainnumberOfmonths.isEmpty()) {
			ticketstationTrainnumberOfmonth=ticketstationTrainnumberOfmonths.get(0);
		}
		return ticketstationTrainnumberOfmonth;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getIncomePeopleCountGroouByTicketStationTrainnumber(String year) {
		// TODO Auto-generated method stub
		String sql="SELECT TICKET_STATION_ID AS ticketStationId,TRAIN_NUMBER_ID AS trainNumberId,SUM(INCOME) AS income,SUM(PEOPLE_COUNT) AS peopleCount FROM TICKETSTATION_TRAINNUMBER_OFMONTH WHERE MONTH LIKE :year GROUP BY TICKET_STATION_ID,TRAIN_NUMBER_ID ";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
		.setParameter("year", year+"%")
		.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getIncomePeopleCountGroouByHql(String year, String trainNumberId) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(INCOME) AS income,SUM(PEOPLE_COUNT) AS peopleCount FROM TICKETSTATION_TRAINNUMBER_OFMONTH WHERE MONTH LIKE :year AND TRAIN_NUMBER_ID=:trainNumberId GROUP BY TRAIN_NUMBER_ID ";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
		.setParameter("year", year+"%").setParameter("trainNumberId", trainNumberId)
		.list();
	}
	
	
}
