package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.SpzOfyearDao;
import com.xianqin.domain.SpzOfYear;
/**
 * 售票站年统计对象数据访问层接口实现类
 * 该接口实现了spzOfyearDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("spzOfyearDao")
public class SpzOfyearDaoImpl extends CommonDaoImpl<SpzOfYear, String> implements SpzOfyearDao{
	@Override
	public void saveSpzOfyear(SpzOfYear spzOfyear) {
		spzOfyear.setId(UUIDGenerator.getUUID()); //生成主键
		spzOfyear.setCreateTime(new Date());    //创建时间
		this.save(spzOfyear);
	}

	@Override
	public void updateSpzOfyear(SpzOfYear spzOfyear) {
		this.update(spzOfyear);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<SpzOfYear> querySpzOfyearByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteSpzOfyearById(String spzOfyearId) {
		String hql="DELETE FROM SpzOfYear WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, spzOfyearId).executeUpdate();
	}
	
	@Override
	public SpzOfYear getSpzOfyearByCondition(QueryRule queryRule) {
		List<SpzOfYear> spzOfyears=this.find(queryRule);
		SpzOfYear spzOfyear=null;
		if (spzOfyears!=null && !spzOfyears.isEmpty()) {
			spzOfyear=spzOfyears.get(0);
		}
		return spzOfyear;
	}

	@Override
	public void deleteSpzOfyearByYear(Long year) {
		// TODO Auto-generated method stub
		String hql="DELETE FROM SpzOfYear WHERE year=:year";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter("year", year).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getYearList() {
		// TODO Auto-generated method stub
		String hql="SELECT year FROM SpzOfYear GROUP BY year";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).list();
	}

	@Override
	public Double getIncomeBySpzIdAndYear(Long spzId, Long year) {
		// TODO Auto-generated method stub
		String hql="SELECT income FROM SpzOfYear WHERE "+SpzOfYear._spzId+" = :spzId AND "+SpzOfYear._year+" = :year";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("spzId", spzId)
				.setParameter("year", year)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getPeopleCountBySpzIdAndYear(Long spzId, Long year) {
		// TODO Auto-generated method stub
		String hql="SELECT "+SpzOfYear._peopleCount+" FROM SpzOfYear WHERE "+SpzOfYear._spzId+" = :zdId AND "+SpzOfYear._year+" = :year";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", spzId)
				.setParameter("year", year)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}

	@Override
	public Double getSumIncomeBySpzId(Long spzId) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM(income) FROM SpzOfYear WHERE "+SpzOfYear._spzId+" = :spzId";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("spzId", spzId)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Double((object.toString()));
		}
		return 0D;
	}

	@Override
	public Long getSumPeopleCountBySpzId(Long spzId) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM("+SpzOfYear._peopleCount+") FROM SpzOfYear WHERE "+SpzOfYear._spzId+" = :zdId";
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("zdId", spzId)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return 0L;
	}
}
