package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.ZdOfmonthDao;
import com.xianqin.domain.ZdOfMonth;
/**
 * 站段月统计对象数据访问层接口实现类
 * 该接口实现了zdOfmonthDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("zdOfmonthDao")
public class ZdOfmonthDaoImpl extends CommonDaoImpl<ZdOfMonth, String> implements ZdOfmonthDao{
	@Override
	public void saveZdOfmonth(ZdOfMonth zdOfmonth) {
		zdOfmonth.setId(UUIDGenerator.getUUID()); //生成主键
		zdOfmonth.setCreateDate(new Date());    //创建时间
		this.save(zdOfmonth);
	}

	@Override
	public void updateZdOfmonth(ZdOfMonth zdOfmonth) {
		this.update(zdOfmonth);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<ZdOfMonth> queryZdOfmonthByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteZdOfmonthById(String zdOfmonthId) {
		String hql="DELETE FROM ZdOfMonth WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, zdOfmonthId).executeUpdate();
	}
	
	@Override
	public ZdOfMonth getZdOfmonthByCondition(QueryRule queryRule) {
		List<ZdOfMonth> zdOfmonths=this.find(queryRule);
		ZdOfMonth zdOfmonth=null;
		if (zdOfmonths!=null && !zdOfmonths.isEmpty()) {
			zdOfmonth=zdOfmonths.get(0);
		}
		return zdOfmonth;
	}

	@Override
	public double getSumIncome(String stationSectionId, String year) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(INCOME) FROM ZD_OFMONTH WHERE ZD_ID=:zdId AND MONTH LIKE :year";
		Object object= this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("zdId", stationSectionId)
				.setParameter("year", year+"%")
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  (Double) object;
		}
		return (double) 0;
	}

	@Override
	public Long getSumPeopleCount(String stationSectionId, String year) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(PEOPLE_COUNT) FROM ZD_OFMONTH WHERE ZD_ID=:zdId AND MONTH LIKE :year";
		Object object= this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("zdId", stationSectionId)
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
		String sql="SELECT COUNT(ID) FROM ZD_OFMONTH WHERE MONTH LIKE :year";
		return Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("year", year+"%")
				.uniqueResult().toString());
	}

	@Override
	public void deleteZdOfmonthByMonth(String month) {
		// TODO Auto-generated method stub
		String hql="DELETE FROM ZdOfMonth WHERE month=:month";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter("month", month).executeUpdate();
	}

	@Override
	public Double getIncomeByZdIdAndMonth(Long zdId, String month) {
		// TODO Auto-generated method stub
		String hql="SELECT income FROM ZdOfMonth WHERE "+ZdOfMonth._zdId+" = :zdId AND "+ZdOfMonth._month+" = :month";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.setParameter("month", month)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getPeopleCountByZdIdAndMonth(Long zdId, String month) {
		// TODO Auto-generated method stub
		String hql="SELECT "+ZdOfMonth._peopleCount+" FROM ZdOfMonth WHERE "+ZdOfMonth._zdId+" = :zdId AND "+ZdOfMonth._month+" = :month";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.setParameter("month", month)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}

	@Override
	public Double getSumIncomeByzdIdAndYear(Long zdId, String year) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM(income) FROM ZdOfMonth WHERE "+ZdOfMonth._zdId+" = :zdId AND "+ZdOfMonth._month+" LIKE :year";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.setParameter("year", year+"%")
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getSumPeopleCountByzdIdAndYear(Long zdId, String year) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM("+ZdOfMonth._peopleCount+") FROM ZdOfMonth WHERE "+ZdOfMonth._zdId+" = :zdId AND "+ZdOfMonth._month+" LIKE :year";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.setParameter("year", year)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}
}
