package com.xianqin.dao.impl;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.SpzOfdayDao;
import com.xianqin.domain.SpzOfday;
/**
 * 售票站日统计对象数据访问层接口实现类
 * 该接口实现了spzOfdayDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("spzOfdayDao")
public class SpzOfdayDaoImpl extends CommonDaoImpl<SpzOfday, String> implements SpzOfdayDao{
	@Override
	public void saveSpzOfday(SpzOfday spzOfday) {
		spzOfday.setId(UUIDGenerator.getUUID()); //生成主键
		spzOfday.setCreateDate(new Date());
		this.save(spzOfday);
	}

	@Override
	public void updateSpzOfday(SpzOfday spzOfday) {
		this.update(spzOfday);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<SpzOfday> querySpzOfdayByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteSpzOfdayById(String spzOfdayId) {
		String hql="DELETE FROM SpzOfday WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, spzOfdayId).executeUpdate();
	}
	
	@Override
	public SpzOfday getSpzOfdayByCondition(QueryRule queryRule) {
		List<SpzOfday> spzOfdays=this.find(queryRule);
		SpzOfday spzOfday=null;
		if (spzOfdays!=null && !spzOfdays.isEmpty()) {
			spzOfday=spzOfdays.get(0);
		}
		return spzOfday;
	}

	@Override
	public double getSumIncome(Long ticketStationId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(INCOME) FROM SPZ_OFDAY WHERE SPZ_ID=:spzId AND DATA_DATE BETWEEN :startDate AND :endDate";
		Object object= this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("spzId", ticketStationId)
				.setParameter("startDate",startDate)
				.setParameter("endDate", endDate)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  (Double) object;
		}
		return (double) 0;
	}

	@Override
	public Long getSumPeopleCount(Long ticketStationId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(PEOPLE_COUNT) FROM SPZ_OFDAY WHERE SPZ_ID=:spzId AND DATA_DATE BETWEEN :startDate AND :endDate";
		Object object= this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("spzId", ticketStationId)
				.setParameter("startDate",startDate)
				.setParameter("endDate", endDate)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return new Long("0");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getIncomPeopleCountByZdId(String stationSectionId, String dataDate) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(INCOME),SUM(PEOPLE_COUNT) FROM SPZ_OFDAY WHERE SPZ_ID IN (SELECT ID FROM TICKET_STATION_INFO WHERE STATION_SECTION_ID =:stationSectionId) AND DATA_DATE =:dataDate";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
		.setParameter("stationSectionId", stationSectionId)
		.setParameter("dataDate", dataDate)
		.list();
	}

	@Override
	public Integer getIsNullByDataDate(String startDate,String endDate) {
		// TODO Auto-generated method stub
		String sql="SELECT COUNT(ID) FROM SPZ_OFDAY WHERE DATA_DATE BETWEEN :startDate AND :endDate";
		return Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.uniqueResult().toString());
	}

	@Override
	public Integer getIsNullByDataDateOfDay(String dataDate) {
		// TODO Auto-generated method stub
		String sql="SELECT COUNT(ID) FROM SPZ_OFDAY WHERE DATA_DATE =:dataDate";
		return Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("dataDate", dataDate)
				.uniqueResult().toString());
	}

	@Override
	public void deleteSpzOfdayByDataDate(Date dataDate) {
		// TODO Auto-generated method stub
		String hql="DELETE FROM SpzOfday WHERE dataDate=:dataDate";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter("dataDate", dataDate).executeUpdate();
	}

	@Override
	public Double getSumIncomeBySpzIdAndDate(Long spzId, Date date) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM(income) FROM SpzOfday WHERE "+SpzOfday._spzId+" = :spzId AND "+SpzOfday._dataDate+" >= :day";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("spzId", spzId)
				.setParameter("day", date)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getSumPeopleCountBySpzIdAndDate(Long spzId,Date date) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM("+SpzOfday._peopleCount+") FROM SpzOfday WHERE "+SpzOfday._spzId+" = :spzId AND "+SpzOfday._dataDate+" >= :day";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("spzId", spzId)
				.setParameter("day", date)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}
	
	
}
