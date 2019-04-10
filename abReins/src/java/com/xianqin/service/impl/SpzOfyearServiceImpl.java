package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.utils.DateUtil;
import com.xianqin.dao.SpzOfdayDao;
import com.xianqin.dao.SpzOfmonthDao;
import com.xianqin.dao.SpzOfyearDao;
import com.xianqin.service.SpzOfyearService;

@Service(value = "spzOfyearServiceImpl")
public class SpzOfyearServiceImpl implements SpzOfyearService {

	@Resource
	private SpzOfyearDao spzOfyearDao;

	@Resource
	private SpzOfmonthDao spzOfmonthDao;

	@Resource
	private SpzOfdayDao spzOfdayDao;

	@Override
	public List<Long> getYearList() throws Exception {
		// TODO Auto-generated method stub
		return spzOfyearDao.getYearList();
	}

	@Override
	public Double getIncomeBySpzIdAndYear(Long spzId, Long year) throws Exception {
		// TODO Auto-generated method stub
		return spzOfyearDao.getIncomeBySpzIdAndYear(spzId, year);
	}

	@Override
	public Long getPeopleCountBySpzIdAndYear(Long spzId, Long year) throws Exception {
		// TODO Auto-generated method stub
		return spzOfyearDao.getPeopleCountBySpzIdAndYear(spzId, year);
	}

	@Override
	public Double getSumIncomeBySpzId(Long ticketStationId, String date) throws Exception {
		// TODO Auto-generated method stub
		String[] splitDate=date.split("-");
		Double income=0D;
		income+=spzOfyearDao.getSumIncomeBySpzId(ticketStationId);
		income+=spzOfmonthDao.getSumIncomeBySpzIdAndYear(ticketStationId, splitDate[0]);
		income+=spzOfdayDao.getSumIncomeBySpzIdAndDate(ticketStationId,DateUtil.parseStringToDate(splitDate[0]+"-"+splitDate[1]+"-01"));
		return income;
	}

	@Override
	public Long getSumPeopleCountBySpzId(Long ticketStationId, String date) throws Exception {
		// TODO Auto-generated method stub
		String[] splitDate=date.split("-");
		Long peopleCount=0L;
		peopleCount+=spzOfyearDao.getSumPeopleCountBySpzId(ticketStationId);
		peopleCount+=spzOfmonthDao.getSumPeopleCountBySpzIdAndYear(ticketStationId, splitDate[0]);
		peopleCount+=spzOfdayDao.getSumPeopleCountBySpzIdAndDate(ticketStationId, DateUtil.parseStringToDate(splitDate[0]+"-"+splitDate[1]+"-01"));
		return peopleCount;
	}


	

}
