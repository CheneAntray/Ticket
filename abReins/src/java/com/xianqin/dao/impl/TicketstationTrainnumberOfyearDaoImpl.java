package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.TicketstationTrainnumberOfyearDao;
import com.xianqin.domain.TicketstationTrainnumberOfyear;

/**
 * 售票站车次年统计信息表对象数据访问层接口实现类 该接口实现了ticketstationTrainnumberOfyearDao接口常用的数据访问层方法
 * 
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */

@Repository("ticketstationTrainnumberOfyearDao")
public class TicketstationTrainnumberOfyearDaoImpl extends CommonDaoImpl<TicketstationTrainnumberOfyear, String>
		implements TicketstationTrainnumberOfyearDao {
	@Override
	public void saveTicketstationTrainnumberOfyear(TicketstationTrainnumberOfyear ticketstationTrainnumberOfyear) {
		ticketstationTrainnumberOfyear.setId(UUIDGenerator.getUUID()); // 生成主键
		ticketstationTrainnumberOfyear.setCreateDate(new Date()); // 创建时间
		this.save(ticketstationTrainnumberOfyear);
	}

	@Override
	public void updateTicketstationTrainnumberOfyear(TicketstationTrainnumberOfyear ticketstationTrainnumberOfyear) {
		this.update(ticketstationTrainnumberOfyear);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TicketstationTrainnumberOfyear> queryTicketstationTrainnumberOfyearByPage(QueryRule queryRule,
			Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return this.find(queryRule, pageIndex, pageSize);
	}

	@Override
	public void deleteTicketstationTrainnumberOfyearById(String ticketstationTrainnumberOfyearId) {
		String hql = "DELETE FROM TicketstationTrainnumberOfyear WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, ticketstationTrainnumberOfyearId)
				.executeUpdate();
	}

	@Override
	public TicketstationTrainnumberOfyear getTicketstationTrainnumberOfyearByCondition(QueryRule queryRule) {
		List<TicketstationTrainnumberOfyear> ticketstationTrainnumberOfyears = this.find(queryRule);
		TicketstationTrainnumberOfyear ticketstationTrainnumberOfyear = null;
		if (ticketstationTrainnumberOfyears != null && !ticketstationTrainnumberOfyears.isEmpty()) {
			ticketstationTrainnumberOfyear = ticketstationTrainnumberOfyears.get(0);
		}
		return ticketstationTrainnumberOfyear;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTicketstationTrainnumberOfyearByHql(String year,String trainId) {
		// TODO Auto-generated method stub
		String sql = "SELECT SUM(INCOME) AS income,SUM(PEOPLE_COUNT) AS peopleCount FROM TICKETSTATION_TRAINNUMBER_OFYEAR WHERE YEAR LIKE :year AND TRAIN_NUMBER_ID=:trainId GROUP BY TRAIN_NUMBER_ID ";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("year", year + "%").setParameter("trainId", trainId).list();
	}

	@Override
	public void deleteTicketTrainnumberOfyearByYear(String year) {
		// TODO Auto-generated method stub
		String hql = "DELETE FROM TicketstationTrainnumberOfyear WHERE year=:year";
		this.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("year", new Long(year))
				.executeUpdate();
	}
}
