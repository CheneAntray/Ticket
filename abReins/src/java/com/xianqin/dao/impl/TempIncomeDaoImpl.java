package com.xianqin.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TempIncomeDao;
import com.xianqin.domain.TempIncome;
/**
 * 收入信息临时表对象数据访问层接口实现类
 * 该接口实现了tempIncomeDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("tempIncomeDao")
public class TempIncomeDaoImpl extends CommonDaoImpl<TempIncome, String> implements TempIncomeDao{
	@Override
	public void saveTempIncome(TempIncome tempIncome) {
		tempIncome.setId(UUIDGenerator.getUUID()); //生成主键
		this.save(tempIncome);
	}

	@Override
	public void updateTempIncome(TempIncome tempIncome) {
		this.update(tempIncome);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TempIncome> queryTempIncomeByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteTempIncomeById(String tempIncomeId) {
		String hql="DELETE FROM TempIncome WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, tempIncomeId).executeUpdate();
	}
	
	@Override
	public TempIncome getTempIncomeByCondition(QueryRule queryRule) {
		List<TempIncome> tempIncomes=this.find(queryRule);
		TempIncome tempIncome=null;
		if (tempIncomes!=null && !tempIncomes.isEmpty()) {
			tempIncome=tempIncomes.get(0);
		}
		return tempIncome;
	}

	@Override
	public void truncateAllData() {
		// TODO Auto-generated method stub
		String sql="TRUNCATE TEMP_INCOME";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public Double getSumIncome() {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(INCOME) FROM TEMP_INCOME";
		Object object=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  (Double) object;
		}
		return (double) 0;
	}

	@Override
	public Long getSumPeopleCount() {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(PEOPLE_COUNT) FROM TEMP_INCOME";
		Object object=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return new Long("0");
	}

}
