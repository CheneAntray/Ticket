package com.xianqin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xianqin.dao.ZdOfmonthDao;
import com.xianqin.service.ZdOfmonthService;
@Service(value="zdOfmonthServiceImpl")
public class ZdOfmonthServiceImpl implements ZdOfmonthService {
	
	@Resource
	private ZdOfmonthDao zdOfmonthDao;

	@Override
	public Double getIncomeByZdIdAndMonth(Long zdId, String month) throws Exception {
		// TODO Auto-generated method stub
		return zdOfmonthDao.getIncomeByZdIdAndMonth(zdId, month);
	}

	@Override
	public Long getPeopleCountByZdIdAndMonth(Long zdId, String month) throws Exception {
		// TODO Auto-generated method stub
		return zdOfmonthDao.getPeopleCountByZdIdAndMonth(zdId, month);
	}

}
