package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.utils.DateUtil;
import com.xianqin.dao.ZdOfdayDao;
import com.xianqin.dao.ZdOfmonthDao;
import com.xianqin.dao.ZdOfyearDao;
import com.xianqin.service.ZdOfyearService;

@Service(value="zdOfyearServiceImpl")
public class ZdOfyearServiceImpl implements ZdOfyearService {
	
	@Resource
	private ZdOfyearDao zdOfyearDao;
	
	@Resource
	private ZdOfmonthDao ZdOfmonthDao;
	
	@Resource
	private ZdOfdayDao ZdOfdayDao;

	@Override
	public List<Long> getYearList() throws Exception{
		// TODO Auto-generated method stub
		return zdOfyearDao.getYearList();
	}

	@Override
	public Double getIncomeByZdIdAndYear(Long zdId, Long year) throws Exception {
		// TODO Auto-generated method stub
		return zdOfyearDao.getIncomeByZdIdAndYear(zdId, year);
	}

	@Override
	public Long getPeopleCountByZdIdAndYear(Long zdId, Long year) throws Exception {
		// TODO Auto-generated method stub
		return zdOfyearDao.getPeopleCountByZdIdAndYear(zdId, year);
	}

	@Override
	public Double getSumIncomeByZdId(Long zdId, String date) throws Exception {
		// TODO Auto-generated method stub
		String[] splitDate=date.split("-");
		Double income=0D;
		income+=zdOfyearDao.getSumIncomeByZdId(zdId);
		income+=ZdOfmonthDao.getSumIncomeByzdIdAndYear(zdId, splitDate[0]);
		income+=ZdOfdayDao.getSumIncomeByZdIdIdAndDate(zdId,DateUtil.parseStringToDate(splitDate[0]+"-"+splitDate[1]+"-01"));
		return income;
	}

	@Override
	public Long getSumPeopleCountByZdId(Long zdId, String date) throws Exception {
		// TODO Auto-generated method stub
		String[] splitDate=date.split("-");
		Long peopleCount=0L;
		peopleCount+=zdOfyearDao.getSumPeopleCountByZdId(zdId);
		peopleCount+=ZdOfmonthDao.getSumPeopleCountByzdIdAndYear(zdId, splitDate[0]);
		peopleCount+=ZdOfdayDao.getSumPeopleCountByZdIdAndDate(zdId, DateUtil.parseStringToDate(splitDate[0]+"-"+splitDate[1]+"-01"));
		return peopleCount;
	}

}
