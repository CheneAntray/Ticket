package com.xianqin.dao.impl;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.DateUtil;
import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.IncomeInfoDao;
import com.xianqin.domain.IncomeInfo;
/**
 * 收入信息对象数据访问层接口实现类
 * 该接口实现了incomeInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("incomeInfoDao")
public class IncomeInfoDaoImpl extends CommonDaoImpl<IncomeInfo, String> implements IncomeInfoDao{
	@Override
	public void saveIncomeInfo(IncomeInfo incomeInfo) {
		incomeInfo.setId(UUIDGenerator.getUUID()); //生成主键
		incomeInfo.setCreateDate(new Date());
		this.save(incomeInfo);
	}

	@Override
	public void updateIncomeInfo(IncomeInfo incomeInfo) {
		this.update(incomeInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<IncomeInfo> queryIncomeInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder(IncomeInfo._createDate);
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteIncomeInfoById(String incomeInfoId) {
		String hql="DELETE FROM IncomeInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, incomeInfoId).executeUpdate();
	}
	
	@Override
	public IncomeInfo getIncomeInfoByCondition(QueryRule queryRule) {
		List<IncomeInfo> incomeInfos=this.find(queryRule);
		IncomeInfo incomeInfo=null;
		if (incomeInfos!=null && !incomeInfos.isEmpty()) {
			incomeInfo=incomeInfos.get(0);
		}
		return incomeInfo;
	}

	@Override
	public List<IncomeInfo> getIncomeInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}

	@Override
	public double getSumIncomeByTicketStationIdAndDate(Long ticketStationId, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM(INCOME) FROM INCOME_INFO WHERE TICKET_STATION_ID =:ticketStationId";
		if (startDate!= null && !startDate.equals("")) {
			if (endDate!=null && !endDate.equals("")) {
				hql+=" AND DATA_DATE >='"+startDate+"' AND DATA_DATE <= '"+endDate+"'";
			}else{
				hql+=" AND DATA_DATE >='"+startDate+"' AND DATA_DATE <= '"+DateUtil.shortTime(new Date())+"'";
			}
		}else {
			if (endDate !=null && !endDate.equals("")) {
				hql+=" AND DATA_DATE <= '"+endDate+"'";
			}
		}
		Object object =  this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql)
				.setParameter("ticketStationId", ticketStationId)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  (Double) object;
		}
		return (double) 0;
	}

	@Override
	public Long getSumPeopleCountByTicketStationIdAndDate(Long ticketStationId, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		String hql="SELECT SUM(PEOPLE_COUNT) FROM INCOME_INFO WHERE TICKET_STATION_ID =:ticketStationId";
		if (startDate!= null && !startDate.equals("")) {
			if (endDate!=null && !endDate.equals("")) {
				hql+=" AND DATA_DATE >='"+startDate+"' AND DATA_DATE <= '"+endDate+"'";
			}else{
				hql+=" AND DATA_DATE >='"+startDate+"' AND DATA_DATE <= '"+DateUtil.shortTime(new Date())+"'";
			}
		}else {
			if (endDate !=null && !endDate.equals("")) {
				hql+=" AND DATA_DATE <= '"+endDate+"'";
			}
		}
		Object object = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql)
				.setParameter("ticketStationId", ticketStationId)
				.uniqueResult();
		if (object!=null && !object.equals("null")) {
			return  new Long((object.toString()));
		}
		return new Long("0");
	}

	@Override
	public void queryIncomByZdIdForTempIncome(Long stationSection, String dataDate) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO TEMP_INCOME (ID ,TRAIN_NUMBER_ID,TICKET_STATION_ID,INCOME,PEOPLE_COUNT,CREATE_DATE,DATA_DATE) SELECT ID , TRAIN_NUMBER_ID , TICKET_STATION_ID , INCOME , PEOPLE_COUNT , CREATE_DATE , DATA_DATE FROM INCOME_INFO WHERE TICKET_STATION_ID IN( SELECT ID FROM TICKET_STATION_INFO WHERE STATION_SECTION_ID = :stationSectonId) AND DATA_DATE = :dataDate";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
		.setParameter("stationSectonId", stationSection)
		.setParameter("dataDate", dataDate).executeUpdate();
	}

	@Override
	public void queryIncomBySpzIdForTempIncome(Long ticketStationId, String dataDate) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO TEMP_INCOME (ID ,TRAIN_NUMBER_ID,TICKET_STATION_ID,INCOME,PEOPLE_COUNT,CREATE_DATE,DATA_DATE) SELECT ID , TRAIN_NUMBER_ID , TICKET_STATION_ID , INCOME , PEOPLE_COUNT , CREATE_DATE , DATA_DATE FROM INCOME_INFO WHERE TICKET_STATION_ID =:ticketStationId AND DATA_DATE = :dataDate";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
		.setParameter("ticketStationId", ticketStationId)
		.setParameter("dataDate", dataDate).executeUpdate();
	}

	@Override
	public void queryIncomByTrainNumberIdForTempIncome(Long trainNumberId, String dataDate) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO TEMP_INCOME (ID ,TRAIN_NUMBER_ID,INCOME,PEOPLE_COUNT) SELECT ID , TRAIN_NUMBER_ID  , INCOME , PEOPLE_COUNT FROM INCOME_INFO WHERE TRAIN_NUMBER_ID =:trainNumberId AND DATA_DATE = :dataDate";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
		.setParameter("trainNumberId", trainNumberId)
		.setParameter("dataDate", dataDate)
		.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getIncomePeopleCountGroouByTicketStationTrainnumber(String startDate,String endDate) {
		// TODO Auto-generated method stub
		String sql="SELECT TICKET_STATION_ID AS ticketStationId,TRAIN_NUMBER_ID AS trainNumberId,SUM(INCOME) AS income,SUM(PEOPLE_COUNT) AS peopleCount FROM INCOME_INFO WHERE  DATA_DATE>=:startDate AND DATA_DATE<=:endDate GROUP BY TICKET_STATION_ID,TRAIN_NUMBER_ID ";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
		.setParameter("startDate", startDate)
		.setParameter("endDate", endDate)
		.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getIncomePeopleCountGroouByHql(String day, String trainId) {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(INCOME) AS income,SUM(PEOPLE_COUNT) AS peopleCount FROM INCOME_INFO WHERE  DATA_DATE=:day AND TRAIN_NUMBER_ID=:trainId GROUP BY TRAIN_NUMBER_ID ";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
		.setParameter("day", day)
		.setParameter("trainId", trainId)
		.list();
	}

	@Override
	public Integer getIsNullByDataDate(String dataDate) {
		// TODO Auto-generated method stub
		String sql="SELECT COUNT(ID) FROM INCOME_INFO WHERE DATA_DATE = :dataDate";
		return Integer.parseInt(this.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setParameter("dataDate", dataDate)
				.uniqueResult().toString());
	}

}
