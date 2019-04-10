package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.ZdOfdayDao;
import com.xianqin.domain.ZdOfDay;
/**
 * 站段日统计对象数据访问层接口实现类
 * 该接口实现了zdOfdayDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("zdOfdayDao")
public class ZdOfdayDaoImpl extends CommonDaoImpl<ZdOfDay, String> implements ZdOfdayDao{
	@Override
	public void saveZdOfday(ZdOfDay zdOfday) {
		zdOfday.setId(UUIDGenerator.getUUID()); //生成主键
		zdOfday.setCreateDate(new Date());    //创建时间
		this.save(zdOfday);
	}

	@Override
	public void updateZdOfday(ZdOfDay zdOfday) {
		this.update(zdOfday);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<ZdOfDay> queryZdOfdayByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteZdOfdayById(String zdOfdayId) {
		String hql="DELETE FROM ZdOfDay WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, zdOfdayId).executeUpdate();
	}
	
	@Override
	public ZdOfDay getZdOfdayByCondition(QueryRule queryRule) {
		List<ZdOfDay> zdOfdays=this.find(queryRule);
		ZdOfDay zdOfday=null;
		if (zdOfdays!=null && !zdOfdays.isEmpty()) {
			zdOfday=zdOfdays.get(0);
		}
		return zdOfday;
	}

	@Override
	public double getSumIncome(String stationSectionId, String startDate,String endDate) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(INCOME) FROM ZD_OFDAY WHERE ZD_ID=:zdId AND DATA_DATE BETWEEN :startDate AND :endDate";
		Object object= this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("zdId", stationSectionId)
				.setParameter("startDate",startDate)
				.setParameter("endDate", endDate)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  (Double) object;
		}
		return (double) 0;
	}

	@Override
	public Long getSumPeopleCount(String stationSectionId,  String startDate,String endDate) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(PEOPLE_COUNT) FROM ZD_OFDAY WHERE ZD_ID=:zdId AND DATA_DATE BETWEEN :startDate AND :endDate";
		Object object=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("zdId", stationSectionId)
				.setParameter("startDate",startDate)
				.setParameter("endDate", endDate)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return new Long("0");
	}

	@Override
	public Integer getIsNullByDataDate(String startDate, String endDate) {
		// TODO Auto-generated method stub
		String sql="SELECT COUNT(ID) FROM ZD_OFDAY WHERE DATA_DATE BETWEEN :startDate AND :endDate";
		return Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.uniqueResult().toString());
	}

	@Override
	public void deleteZdOfdayByDataDate(Date dataDate) {
		// TODO Auto-generated method stub
		String hql="DELETE FROM ZdOfDay WHERE dataDate=:dataDate";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter("dataDate", dataDate).executeUpdate();
	}

	@Override
	public Double getSumIncomeByZdIdIdAndDate(Long zdId, Date date) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM(income) FROM ZdOfDay WHERE "+ZdOfDay._zdId+" = :zdId AND "+ZdOfDay._dataDate+" >= :day";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.setParameter("day", date)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getSumPeopleCountByZdIdAndDate(Long zdId, Date date) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM("+ZdOfDay._peopleCount+") FROM ZdOfDay WHERE "+ZdOfDay._zdId+" = :zdId AND "+ZdOfDay._dataDate+" >= :day";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.setParameter("day", date)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}

	
	
	
}
