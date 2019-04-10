package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.ZdOfyearDao;
import com.xianqin.domain.ZdOfYear;
/**
 * 站段年统计对象数据访问层接口实现类
 * 该接口实现了zdOfyearDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("zdOfyearDao")
public class ZdOfyearDaoImpl extends CommonDaoImpl<ZdOfYear, String> implements ZdOfyearDao{
	@Override
	public void saveZdOfyear(ZdOfYear zdOfyear) {
		zdOfyear.setId(UUIDGenerator.getUUID()); //生成主键
		zdOfyear.setCreateDate(new Date());    //创建时间
		this.save(zdOfyear);
	}

	@Override
	public void updateZdOfyear(ZdOfYear zdOfyear) {
		this.update(zdOfyear);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<ZdOfYear> queryZdOfyearByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteZdOfyearById(String zdOfyearId) {
		String hql="DELETE FROM ZdOfYear WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, zdOfyearId).executeUpdate();
	}
	
	@Override
	public ZdOfYear getZdOfyearByCondition(QueryRule queryRule) {
		List<ZdOfYear> zdOfyears=this.find(queryRule);
		ZdOfYear zdOfyear=null;
		if (zdOfyears!=null && !zdOfyears.isEmpty()) {
			zdOfyear=zdOfyears.get(0);
		}
		return zdOfyear;
	}

	@Override
	public void deleteZdOfyearByYear(Long year) {
		// TODO Auto-generated method stub
		String hql="DELETE FROM ZdOfYear WHERE year=:year";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter("year", year).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getYearList() {
		// TODO Auto-generated method stub
		String hql="SELECT year FROM ZdOfYear GROUP BY year";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).list();
	}

	@Override
	public Double getIncomeByZdIdAndYear(Long zdId, Long year) {
		// TODO Auto-generated method stub
		String hql="SELECT income FROM ZdOfYear WHERE "+ZdOfYear._zdId+" = :zdId AND "+ZdOfYear._year+" = :year";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.setParameter("year", year)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getPeopleCountByZdIdAndYear(Long zdId, Long year) {
		// TODO Auto-generated method stub
		String hql="SELECT "+ZdOfYear._peopleCount+" FROM ZdOfYear WHERE "+ZdOfYear._zdId+" = :zdId AND "+ZdOfYear._year+" = :year";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.setParameter("year", year)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}

	@Override
	public Double getSumIncomeByZdId(Long zdId) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM(income) FROM ZdOfYear WHERE "+ZdOfYear._zdId+" = :zdId";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getSumPeopleCountByZdId(Long zdId) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM("+ZdOfYear._peopleCount+") FROM ZdOfYear WHERE "+ZdOfYear._zdId+" = :zdId";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", zdId)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}
}
