package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.SpzOfmonthDao;
import com.xianqin.domain.SpzOfMonth;
/**
 * 售票站月统计对象数据访问层接口实现类
 * 该接口实现了spzOfmonthDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("spzOfmonthDao")
public class SpzOfmonthDaoImpl extends CommonDaoImpl<SpzOfMonth, String> implements SpzOfmonthDao{
	@Override
	public void saveSpzOfmonth(SpzOfMonth spzOfmonth) {
		spzOfmonth.setId(UUIDGenerator.getUUID()); //生成主键
		spzOfmonth.setCreateDate(new Date());    //创建时间
		this.save(spzOfmonth);
	}

	@Override
	public void updateSpzOfmonth(SpzOfMonth spzOfmonth) {
		this.update(spzOfmonth);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<SpzOfMonth> querySpzOfmonthByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteSpzOfmonthById(String spzOfmonthId) {
		String hql="DELETE FROM SpzOfMonth WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, spzOfmonthId).executeUpdate();
	}
	
	@Override
	public SpzOfMonth getSpzOfmonthByCondition(QueryRule queryRule) {
		List<SpzOfMonth> spzOfmonths=this.find(queryRule);
		SpzOfMonth spzOfmonth=null;
		if (spzOfmonths!=null && !spzOfmonths.isEmpty()) {
			spzOfmonth=spzOfmonths.get(0);
		}
		return spzOfmonth;
	}

	@Override
	public double getSumIncome(Long ticketStationId, String year) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(INCOME) FROM SPZ_OFMONTH WHERE SPZ_ID=:spzId AND MONTH LIKE :year";
		Object object=  this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("spzId", ticketStationId)
				.setParameter("year", year+"%")
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  (Double) object;
		}
		return (double) 0;
	}

	@Override
	public Long getSumPeopleCount(Long ticketStationId, String year) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(PEOPLE_COUNT) FROM SPZ_OFMONTH WHERE SPZ_ID=:spzId AND MONTH LIKE :year";
		Object object=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("spzId", ticketStationId)
				.setParameter("year", year+"%")
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return new Long("0");
	}

	@Override
	public Integer getIsNullByDataDate(String year) {
		// TODO Auto-generated method stub
		String sql="SELECT COUNT(ID) FROM SPZ_OFMONTH WHERE MONTH LIKE :year";
		return Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("year", year+"%")
				.uniqueResult().toString());
	}

	@Override
	public void deleteSpzOfmonthByMonth(String month) {
		// TODO Auto-generated method stub
		String hql="DELETE FROM SpzOfMonth WHERE month=:month";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter("month", month).executeUpdate();
	}

	@Override
	public Double getIncomeBySpzIdAndMonth(Long spzId, String month) {
		// TODO Auto-generated method stub
		String hql="SELECT income FROM SpzOfMonth WHERE "+SpzOfMonth._spzId+" = :spzId AND "+SpzOfMonth._month+" = :month";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("spzId", spzId)
				.setParameter("month", month)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getPeopleCountBySpzIdAndMonth(Long spzId, String month) {
		// TODO Auto-generated method stub
		String hql="SELECT "+SpzOfMonth._peopleCount+" FROM SpzOfMonth WHERE "+SpzOfMonth._spzId+" = :spzId AND "+SpzOfMonth._month+" = :month";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("spzId", spzId)
				.setParameter("month", month)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}

	@Override
	public Double getSumIncomeBySpzIdAndYear(Long spzId,String year) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM(income) FROM SpzOfMonth WHERE "+SpzOfMonth._spzId+" = :spzId AND "+SpzOfMonth._month+" LIKE :year";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("spzId", spzId)
				.setParameter("year", year+"%")
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getSumPeopleCountBySpzIdAndYear(Long spzId, String year) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM("+SpzOfMonth._peopleCount+") FROM SpzOfMonth WHERE "+SpzOfMonth._spzId+" = :spzId AND "+SpzOfMonth._month+" LIKE :year";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("spzId", spzId)
				.setParameter("year", year)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}
}
