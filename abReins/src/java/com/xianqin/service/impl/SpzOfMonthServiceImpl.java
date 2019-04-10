package com.xianqin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xianqin.dao.SpzOfmonthDao;
import com.xianqin.service.SpzOfMonthService;
@Service(value="spzOfmonthServiceImpl")
public class SpzOfMonthServiceImpl implements SpzOfMonthService {
	
	@Resource
	private SpzOfmonthDao spzOfmonthDao;

	@Override
	public Double getIncomeBySpzIdAndMonth(Long spzId, String month) throws Exception {
		// TODO Auto-generated method stub
		return spzOfmonthDao.getIncomeBySpzIdAndMonth(spzId, month);
	}

	@Override
	public Long getPeopleCountBySpzIdAndMonth(Long spzId, String month) throws Exception {
		// TODO Auto-generated method stub
		return spzOfmonthDao.getPeopleCountBySpzIdAndMonth(spzId, month);
	}
	

}
