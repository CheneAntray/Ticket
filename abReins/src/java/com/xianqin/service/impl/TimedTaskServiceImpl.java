package com.xianqin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.ReturnMap;
import com.base.utils.DateUtil;
import com.base.utils.LogUtils;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.IncomeInfoDao;
import com.xianqin.dao.SpzOfdayDao;
import com.xianqin.dao.SpzOfmonthDao;
import com.xianqin.dao.SpzOfyearDao;
import com.xianqin.dao.TempIncomeDao;
import com.xianqin.dao.TicketstationTrainnumberOfmonthDao;
import com.xianqin.dao.TicketstationTrainnumberOfyearDao;
import com.xianqin.dao.TimedtaskInfoDao;
import com.xianqin.dao.ZdOfdayDao;
import com.xianqin.dao.ZdOfmonthDao;
import com.xianqin.dao.ZdOfyearDao;
import com.xianqin.domain.SpzOfMonth;
import com.xianqin.domain.SpzOfYear;
import com.xianqin.domain.SpzOfday;
import com.xianqin.domain.StationSectionInfo;
import com.xianqin.domain.TicketStationInfo;
import com.xianqin.domain.TicketstationTrainnumberOfmonth;
import com.xianqin.domain.TicketstationTrainnumberOfyear;
import com.xianqin.domain.TimedtaskInfo;
import com.xianqin.domain.ZdOfDay;
import com.xianqin.domain.ZdOfMonth;
import com.xianqin.domain.ZdOfYear;
import com.xianqin.service.InitDataService;
import com.xianqin.service.TimedTaskService;

@Service("timedTaskService")
public class TimedTaskServiceImpl implements TimedTaskService {
	@Autowired
	private InitDataService initDataService;
	@Resource
	private IncomeInfoDao incomeInfoDao;
	@Resource
	private TempIncomeDao tempIncomeDao;
	@Resource
	private ZdOfdayDao zdOfdayDao;
	@Resource
	private ZdOfmonthDao zdOfmonthDao;
	@Resource
	private ZdOfyearDao zdOfyearDao;
	@Resource
	private SpzOfmonthDao spzOfmonthDao;
	@Resource
	private SpzOfyearDao spzOfyearDao;
	@Resource
	private TimedtaskInfoDao timedtaskInfoDao;
	@Resource
	private SpzOfdayDao spzOfdayDao;
	@Resource
	private TicketstationTrainnumberOfmonthDao ticketstationTrainnumberOfmonthDao;
	@Resource
	private TicketstationTrainnumberOfyearDao ticketstationTrainnumberOfyearDao;

	/**
	 * 日志对象初始化
	 */
	private Logger logger = LogUtils.getConsoleLogIns();
	// 用于保存缓存中站段数据
	private Map<String, String> stationSectionMap = null;
	// 用于保存缓存中售票站数据
	private Map<String, TicketStationInfo> ticketStationMap = null;

	@Override
	public void doTimedTask() {
		// TODO Auto-generated method stub
		// 1.执行日统计任务
		logger.info("------开始执行售票站日统计------");
		addSpzOfDayTask(getYesterday());
		logger.info("------售票站日统计结束------");
		logger.info("------开始执行站段日统计------");
		addZdOfDayTask(getYesterday());
		logger.info("------站段日统计结束------");
		// 2.判断当天日期是否为月统计日期或年统计日期，如果是则进行统计，不是则忽略
		List<TimedtaskInfo> timedtaskInfos = timedtaskInfoDao.getTimedtaskInfoListByCondition(QueryRule.getInstance());
		String toDaydd = dateTodd(new Date());
		String toDayMMdd = dateToMMdd(new Date());
		for (TimedtaskInfo timedtaskInfo : timedtaskInfos) {
			// 如果当前的车次月统计日期==今天日期，则执行车次的月统计
			if (timedtaskInfo.getTimedtaskType() == TimedtaskType.TRAINNUMBEROFMONTH.getType()) {
				if (dateTodd(timedtaskInfo.getTimedtaskDate()).equals(toDaydd)) {
					logger.info("------开始执行车次月统计统计------");
					addTrainNumberOfMonthTask(getLastMonth());
					logger.info("------车次月统计统计结束------");
					continue;
				}
			}
			// 如果当前的车次年统计日期==今天日期，则执行车次的年统计
			if (timedtaskInfo.getTimedtaskType() == TimedtaskType.TRAINNUMBEROFYEAR.getType()) {
				if (dateToMMdd(timedtaskInfo.getTimedtaskDate()).equals(toDayMMdd)) {
					logger.info("------开始执行车次年统计统计------");
					addTrainNumberOfYearTask(getLastYear());
					logger.info("------车次年统计统计结束------");
					continue;
				}
			}
			// 如果当前的站段月统计日期==今天日期，则执行站段的月统计
			if (timedtaskInfo.getTimedtaskType() == TimedtaskType.ZDOFMONTH.getType()) {
				if (dateTodd(timedtaskInfo.getTimedtaskDate()).equals(toDaydd)) {
					logger.info("------开始执行站段月统计------");
					addZdOfMonthTask(getLastMonth());
					logger.info("------站段月统计结束-----------");
					continue;
				}
			}
			// 如果当前的站段年统计日期==今天日期，则执行站段的年统计
			if (timedtaskInfo.getTimedtaskType() == TimedtaskType.ZDOFYEAR.getType()) {
				if (dateToMMdd(timedtaskInfo.getTimedtaskDate()).equals(toDayMMdd)) {
					logger.info("------开始执行站段年统计------");
					addZdOfYearTask(getLastYear());
					logger.info("------站段年统计结束------");
					continue;
				}
			}
			// 如果当前的售票站月统计日期==今天日期，则执行售票站的月统计
			if (timedtaskInfo.getTimedtaskType() == TimedtaskType.SPZOFMONTH.getType()) {
				if (dateTodd(timedtaskInfo.getTimedtaskDate()).equals(toDaydd)) {
					logger.info("------开始执行售票站月统计------");
					addSpzOfMonthTask(getLastMonth());
					logger.info("------售票站月统计结束------");
					continue;
				}
			}
			// 如果当前的售票站年统计日期==今天日期，则执行售票站的年统计
			if (timedtaskInfo.getTimedtaskType() == TimedtaskType.SPZOFYEAR.getType()) {
				if (dateToMMdd(timedtaskInfo.getTimedtaskDate()).equals(toDayMMdd)) {
					logger.info("------开始执行售票站年统计------");
					addSpzOfYearTask(getLastYear());
					logger.info("------售票站年统计结束------");
					continue;
				}
			}
		}
	}
	/**
	 * 添加站段日统计任务
	 */
	@SuppressWarnings("unchecked")
	private int addZdOfDayTask(String day) {
		// 1.查询数据到临时表
		// 2.在临时表sum
		// 3.将数据插入到日总结
		int result=0;
		try {
			Integer count=spzOfdayDao.getIsNullByDataDateOfDay(day);
			if (count>0) {
				//先删除当日的统计数据
				zdOfdayDao.deleteZdOfdayByDataDate(DateUtil.parseStringToDate(day));
				// 获取所有的站段信息
				stationSectionMap = (Map<String, String>) initDataService
						.getEhcacheMapByTableName(StationSectionInfo.TABLE_NAME);
				ZdOfDay zdOfDay = null;
				// 循环站段集合将每个站段的信息保存到临时表，将临时表里数据sum放在站段日结表
				for (Map.Entry<String, String> m : stationSectionMap.entrySet()) {
					List<Object[]> list = spzOfdayDao.getIncomPeopleCountByZdId(m.getValue(), day);
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							zdOfDay = new ZdOfDay();
							Object[] objects = (Object[]) list.get(i);
							zdOfDay.setZdId(new Long(m.getValue()));
							if (objects[0] != null) {
								zdOfDay.setIncome(Double.parseDouble(objects[0].toString()));
							} else {
								zdOfDay.setIncome(0D);
							}
							if (objects[1] != null) {
								zdOfDay.setPeopleCount(new Long(objects[1].toString()));
							} else {
								zdOfDay.setPeopleCount(0L);
							}
							zdOfDay.setDataDate(DateUtil.parseStringToDate(day));
							zdOfdayDao.saveZdOfday(zdOfDay);
						}
					}
				}
			}else {
				result=1;
			}
		} catch (Exception e) {
			result=6;
			logger.debug("站段日统计异常：" + e);
		}
		return result;
	}

	/**
	 * 添加站段月统计任务
	 */
	@SuppressWarnings("unchecked")
	private int addZdOfMonthTask(String month) {
		int result=0;
		try {
			String startDate = month + "-01";
			String endDate = month + "-31";
			int count=zdOfdayDao.getIsNullByDataDate(startDate, endDate);
			if (count>0) {
				//先删除当月的统计数据
				zdOfmonthDao.deleteZdOfmonthByMonth(month);
				// 获取所有的站段信息
				stationSectionMap = (Map<String, String>) initDataService
						.getEhcacheMapByTableName(StationSectionInfo.TABLE_NAME);
				ZdOfMonth zdOfMonth = null;
				// 循环站段集合将每个站段将信息保存到站段月结表
				for (Map.Entry<String, String> m : stationSectionMap.entrySet()) {
					zdOfMonth = new ZdOfMonth();
					zdOfMonth.setZdId(new Long(m.getValue()));
					zdOfMonth.setIncome(zdOfdayDao.getSumIncome(m.getValue(), startDate, endDate));
					zdOfMonth.setPeopleCount(zdOfdayDao.getSumPeopleCount(m.getValue(), startDate, endDate));
					zdOfMonth.setMonth(month);
					zdOfmonthDao.saveZdOfmonth(zdOfMonth);
				}
			}else {
				result=1;
			}
		} catch (Exception e) {
			result=6;
			logger.debug("站段月统计异常：" + e);
		}
		return result;
	}

	/**
	 * 添加站段年统计任务
	 */
	@SuppressWarnings("unchecked")
	private int addZdOfYearTask(String year) {
		int result=0;
		try {
			Integer count=zdOfmonthDao.getIsNullByDataDate(year);
			if (count>0) {
				//先删除当年的统计数据
				zdOfyearDao.deleteZdOfyearByYear(new Long(year));
				// 获取所有的站段信息
				stationSectionMap = (Map<String, String>) initDataService
						.getEhcacheMapByTableName(StationSectionInfo.TABLE_NAME);
				ZdOfYear zdOfYear = null;
				// 循环站段集合将每个站段将信息保存到站段月结表
				for (Map.Entry<String, String> m : stationSectionMap.entrySet()) {
					zdOfYear = new ZdOfYear();
					zdOfYear.setZdId(new Long(m.getValue()));
					zdOfYear.setIncome(zdOfmonthDao.getSumIncome(m.getValue(), year));
					zdOfYear.setPeopleCount(zdOfmonthDao.getSumPeopleCount(m.getValue(), year));
					zdOfYear.setYear(new Long(year));
					zdOfyearDao.saveZdOfyear(zdOfYear);
				}
			}else {
				result=1;
			}
		} catch (Exception e) {
			result=6;
			logger.debug("站段年统计异常：" + e);
		}
		return result;
	}

	/**
	 * 添加售票站日统计任务
	 */
	@SuppressWarnings("unchecked")
	private int addSpzOfDayTask(String day) {
		// 1.查询数据到临时表
		// 2.在临时表sum
		// 3.将数据插入到日总结
		int result=0;
		try {
			// 获取所有的售票站信息
			ticketStationMap = (Map<String, TicketStationInfo>) initDataService
					.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
			SpzOfday spzOfday = null;
			Integer count=incomeInfoDao.getIsNullByDataDate(day);
			if (count>0) {
				//先删除当日的统计数据
				spzOfdayDao.deleteSpzOfdayByDataDate(DateUtil.parseStringToDate(day));
				// 循环售票站集合将每个站段的信息保存到临时表，将临时表里数据sum放在站段日结表
				for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
					tempIncomeDao.truncateAllData();
					incomeInfoDao.queryIncomBySpzIdForTempIncome(m.getValue().getId(),day);
					spzOfday = new SpzOfday();
					spzOfday.setSpzId(m.getValue().getId());
					spzOfday.setIncome(tempIncomeDao.getSumIncome());
					spzOfday.setPeopleCount(tempIncomeDao.getSumPeopleCount());
					spzOfday.setDataDate(DateUtil.parseStringToDate(day));
					spzOfdayDao.saveSpzOfday(spzOfday);
				}
			}else {
				result=1;
			}
		} catch (Exception e) {
			result=6;
			logger.debug("售票站日统计异常：" + e);
		}
		return result;
	}

	/**
	 * 添加售票站月统计任务
	 */
	@SuppressWarnings("unchecked")
	private int addSpzOfMonthTask(String month) {
		int result=0;
		try {
			String startDate = month + "-01";
			String endDate = month + "-31";
			int count=spzOfdayDao.getIsNullByDataDate(startDate, endDate);
			if (count>0) {
				//先删除当月的统计数据
				spzOfmonthDao.deleteSpzOfmonthByMonth(month);
				// 获取所有的售票站信息
				ticketStationMap = (Map<String, TicketStationInfo>) initDataService
						.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
				SpzOfMonth spzOfMonth = null;
				// 循环售票站集合将每个售票站的信息保存到临时表，将临时表里数据sum放在售票站月结表
				for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
					spzOfMonth = new SpzOfMonth();
					spzOfMonth.setSpzId(m.getValue().getId());
					spzOfMonth.setIncome(spzOfdayDao.getSumIncome(m.getValue().getId(), startDate, endDate));
					spzOfMonth.setPeopleCount(spzOfdayDao.getSumPeopleCount(m.getValue().getId(), startDate, endDate));
					spzOfMonth.setMonth(month);
					spzOfmonthDao.saveSpzOfmonth(spzOfMonth);
				}
			}else {
				result=1;
			}
		} catch (Exception e) {
			result=6;
			logger.debug("售票站月统计异常：" + e);
		}
		return result;
	}

	/**
	 * 添加售票站年统计任务
	 */
	@SuppressWarnings("unchecked")
	private int addSpzOfYearTask(String year) {
		int result=0;
		try {
			Integer count=spzOfmonthDao.getIsNullByDataDate(year);
			if (count>0) {
				//先删除当年的统计数据
				spzOfyearDao.deleteSpzOfyearByYear(new Long(year));
				// 获取所有的售票站信息
				ticketStationMap = (Map<String, TicketStationInfo>) initDataService
						.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
				SpzOfYear spzOfYear = null;
				// 循环售票站集合
				for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
					spzOfYear = new SpzOfYear();
					spzOfYear.setSpzId(m.getValue().getId());
					spzOfYear.setIncome(spzOfmonthDao.getSumIncome(m.getValue().getId(), year));
					spzOfYear.setPeopleCount(spzOfmonthDao.getSumPeopleCount(m.getValue().getId(), year));
					spzOfYear.setYear(new Long(year));
					spzOfyearDao.saveSpzOfyear(spzOfYear);
				}
			}else {
				result=1;
			}
		} catch (Exception e) {
			result=6;
			logger.debug("售票站年统计异常：" + e);
		}
		return result;
	}

	/**
	 * 添加车次月统计任务
	 */
	@SuppressWarnings("unchecked")
	private int addTrainNumberOfMonthTask(String month) {
		int result=0;
		try {
			logger.info("----------开始执行售票站车次月统计------------");
			// 获取所有的售票站信息
			ticketStationMap = (Map<String, TicketStationInfo>) initDataService
					.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
			TicketstationTrainnumberOfmonth ticketstationTrainnumberOfmonth = null;
			String startDate = month + "-01";
			String endDate = month + "-31";
			List<Object[]> list = incomeInfoDao.getIncomePeopleCountGroouByTicketStationTrainnumber(startDate, endDate);
			if (list != null && list.size() > 0) {
				//先删除当月的统计数据
				ticketstationTrainnumberOfmonthDao.deleteTicketTrainnumberOfmonthByMonth(month);
				for (int i = 0; i < list.size(); i++) {
					ticketstationTrainnumberOfmonth = new TicketstationTrainnumberOfmonth();
					Object[] objects = (Object[]) list.get(i);
					ticketstationTrainnumberOfmonth.setTicketStationId(new Long(objects[0].toString()));
					ticketstationTrainnumberOfmonth.setTrainNumberId(new Long(objects[1].toString()));
					ticketstationTrainnumberOfmonth.setIncome(Double.parseDouble(objects[2].toString()));
					ticketstationTrainnumberOfmonth.setPeopleCount(new Long(objects[3].toString()));
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getId().equals(ticketstationTrainnumberOfmonth.getTicketStationId())) {
							ticketstationTrainnumberOfmonth.setStationSectionId(m.getValue().getStationSectionId());
							break;
						}
					}
					ticketstationTrainnumberOfmonth.setMonth(month);
					ticketstationTrainnumberOfmonthDao
							.saveTicketstationTrainnumberOfmonth(ticketstationTrainnumberOfmonth);
				}
			}else {
				result=1;
			}
			logger.info("----------结束执行售票站车次月统计------------");
		} catch (Exception e) {
			result=6;
			logger.debug("车次月统计异常：" + e);
		}
		return result;
	}

	/**
	 * 添加车次年统计任务
	 */
	@SuppressWarnings("unchecked")
	private int addTrainNumberOfYearTask(String year) {
		int result=0;
		try {
			// 获取所有的售票站信息
			ticketStationMap = (Map<String, TicketStationInfo>) initDataService
					.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
			TicketstationTrainnumberOfyear ticketstationTrainnumberOfyear = null;
			List<Object[]> list = ticketstationTrainnumberOfmonthDao.getIncomePeopleCountGroouByTicketStationTrainnumber(year);
			if (list != null && list.size() > 0) {
				//先删除当年的统计数据
				ticketstationTrainnumberOfyearDao.deleteTicketTrainnumberOfyearByYear(year);
				for (int i = 0; i < list.size(); i++) {
					ticketstationTrainnumberOfyear = new TicketstationTrainnumberOfyear();
					Object[] objects = (Object[]) list.get(i);
					ticketstationTrainnumberOfyear.setTicketStationId(new Long(objects[0].toString()));
					ticketstationTrainnumberOfyear.setTrainNumberId(new Long(objects[1].toString()));
					ticketstationTrainnumberOfyear.setIncome(Double.parseDouble(objects[2].toString()));
					ticketstationTrainnumberOfyear.setPeopleCount(new Long(objects[3].toString()));
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getId().equals(ticketstationTrainnumberOfyear.getTicketStationId())) {
							ticketstationTrainnumberOfyear.setStationSectionId(m.getValue().getStationSectionId());
							break;
						}
					}
					ticketstationTrainnumberOfyear.setYear(new Long(year));
					ticketstationTrainnumberOfyearDao
							.saveTicketstationTrainnumberOfyear(ticketstationTrainnumberOfyear);
				}
			}else {
				result=1;
			}
		} catch (Exception e) {
			result=6;
			logger.debug("车次年统计异常：" + e);
		}
		return result;
	}

	/**
	 * 获取昨天日期
	 * 
	 * @return
	 */
	private String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return DateUtil.shortTime(cal.getTime());
	}

	/**
	 * 获取上个月的年月
	 * 
	 * @return
	 */
	private String getLastMonth() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		date = calendar.getTime();
		return dateFormat.format(date);
	}

	/**
	 * 获取上一个年份
	 * 
	 * @return
	 */
	private String getLastYear() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1); // 设置为上一个年份
		date = calendar.getTime();
		return dateFormat.format(date);
	}

	/**
	 * 将日期转换成日
	 * 
	 * @return
	 */
	private String dateTodd(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		return dateFormat.format(date);
	}

	/**
	 * 将日期转换成月日
	 * 
	 * @return
	 */
	private String dateToMMdd(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
		return dateFormat.format(date);
	}

	public enum TimedtaskType {
		ZDOFMONTH("站段月统计", 1), ZDOFYEAR("站段年统计", 2), SPZOFMONTH("售票站月统计", 3), SPZOFYEAR("售票站年统计",
				4), TRAINNUMBEROFMONTH("车次月统计", 5), TRAINNUMBEROFYEAR("车次年统计", 6);
		private String name;
		private int type;

		private TimedtaskType(String name, int type) {
			this.name = name;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
	}

	@Override
	public ReturnMap queryTimedTaskInfo() throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		QueryRule queryRule = QueryRule.getInstance();
		List<TimedtaskInfo> list = timedtaskInfoDao.getTimedtaskInfoListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(list);
		returnMap.setMsg("查询定时日期信息成功");
		return returnMap;
	}

	@Override
	public ReturnMap editTimedTaskInfo(String dataId, Date date) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(TimedtaskInfo._timedtaskType, Long.valueOf(dataId));
		TimedtaskInfo timedtaskInfoins = timedtaskInfoDao.getTimedtaskInfoByCondition(queryRule);
		if (timedtaskInfoins != null) {
			timedtaskInfoins.setTimedtaskDate(date);
			timedtaskInfoins.setTimedtaskType(Long.valueOf(dataId));
			timedtaskInfoDao.updateTimedtaskInfo(timedtaskInfoins);
			returnMap.setSucc();
			returnMap.setMsg("修改定时日期成功");
		} else {
			returnMap.setFail();
			returnMap.setMsg("没有查询到该定时日期");
		}
		return returnMap;
	}
	@Override
	public ReturnMap runUpByTicketTrainnUmberOfMonth(Date startDate, Date endDate) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		String msg="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if (endDate==null) {
			//执行起始月份
		    int result=addTrainNumberOfMonthTask(sdf.format(startDate.getTime()));
		    if (result>0) {
				msg+=sdf.format(startDate)+"月份明细数据为空，请您先导入数据<br/>";
			}else {
				msg+=sdf.format(startDate)+"月份补跑成功<br/>";
			}
		    returnMap.setSucc();
			returnMap.setMsg(msg);
			return returnMap;
		}
		Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(startDate);
	    tempStart.add(Calendar.MONTH, 1);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(endDate);
	    //执行起始月份
	    int result=addTrainNumberOfMonthTask(sdf.format(startDate.getTime()));
	    if (result>0) {
			msg+=sdf.format(startDate)+"月份明细数据为空，请您先导入数据<br/>";
		}else {
			msg+=sdf.format(startDate)+"月份补跑成功<br/>";
		}
	    //执行中间月份
	    while (tempStart.before(tempEnd)) {
	        //result.add(tempStart.getTime());
	    	result= addTrainNumberOfMonthTask(sdf.format(tempStart.getTime()));
	    	if (result>0) {
	 			msg+=sdf.format(tempStart.getTime())+"月份明细数据为空，请您先导入数据<br/>";
	 		}else {
				msg+=sdf.format(startDate)+"月份补跑成功<br/>";
			}
	        tempStart.add(Calendar.MONTH, 1);
	    }
	    //执行终止月份
	    result= addTrainNumberOfMonthTask(sdf.format(endDate));
	    if (result>0) {
 			msg+=sdf.format(endDate)+"月份明细数据为空，请您先导入数据<br/>";
 		}else {
			msg+=sdf.format(endDate)+"月份补跑成功<br/>";
		}
		returnMap.setSucc();
		returnMap.setMsg(msg);
		return returnMap;
	}
	@Override
	public ReturnMap runUpByTicketTrainnUmberOfYear(Date startDate, Date endDate) throws Exception{
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		String msg="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		if (endDate==null) {
			//执行起始月份
		    int result=addTrainNumberOfYearTask(sdf.format(startDate));
		    if (result>0) {
				msg+=sdf.format(startDate)+"年份月统计数据为空，请您先补跑月统计<br/>";
			}else {
				msg+=sdf.format(startDate)+"年份补跑成功<br/>";
			}
		    returnMap.setSucc();
			returnMap.setMsg(msg);
			return returnMap;
		}
		Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(startDate);
	    tempStart.add(Calendar.YEAR, 1);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(endDate);
	    //执行起始年份
	    int result=addTrainNumberOfYearTask(sdf.format(startDate.getTime()));
	    if (result>0) {
			msg+=sdf.format(startDate)+"年份月统计数据为空，请您先补跑月统计<br/>";
		}else {
			msg+=sdf.format(startDate)+"年份补跑成功<br/>";
		}
	    //执行中间年份
	    while (tempStart.before(tempEnd)) {
	        //result.add(tempStart.getTime());
	    	result= addTrainNumberOfYearTask(sdf.format(tempStart.getTime()));
	    	if (result>0) {
	 			msg+=sdf.format(tempStart.getTime())+"年份月统计数据为空，请您先补跑月统计<br/>";
	 		}else {
				msg+=sdf.format(tempStart.getTime())+"年份补跑成功<br/>";
			}
	        tempStart.add(Calendar.YEAR, 1);
	    }
	    //执行终止年份
	    result= addTrainNumberOfYearTask(sdf.format(endDate));
	    if (result>0) {
 			msg+=sdf.format(endDate)+"年份月统计数据为空，请您先补跑月统计<br/>";
 		}else {
			msg+=sdf.format(endDate)+"年份补跑成功<br/>";
		}
		returnMap.setSucc();
		returnMap.setMsg(msg);
		return returnMap;
	}
	@Override
	public ReturnMap runUpByTicketStationOfDay(Date startDate, Date endDate) throws Exception{
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		String msg="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (endDate==null) {
			//执行起始日期
		    int result=addSpzOfDayTask(sdf.format(startDate));
		    if (result>0) {
				msg+=sdf.format(startDate)+"明细数据为空，请您先导入数据<br/>";
			}else {
				msg+=sdf.format(startDate)+"补跑成功<br/>";
			}
		    returnMap.setSucc();
			returnMap.setMsg(msg);
			return returnMap;
		}
		Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(startDate);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(endDate);
	    //执行起始日期
	    int result=addTrainNumberOfYearTask(sdf.format(startDate.getTime()));
	    if (result>0) {
			msg+=sdf.format(startDate)+"明细数据为空，请您先导入数据<br/>";
		}else {
			msg+=sdf.format(startDate)+"补跑成功<br/>";
		}
	    //执行中间日期
	    while (tempStart.before(tempEnd)) {
	        //result.add(tempStart.getTime());
	    	result= addTrainNumberOfYearTask(sdf.format(tempStart.getTime()));
	    	if (result>0) {
	 			msg+=sdf.format(tempStart.getTime())+"明细数据为空，请您先导入数据<br/>";
	 		}else {
				msg+=sdf.format(tempStart.getTime())+"补跑成功<br/>";
			}
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    //执行终止日期
	    result= addTrainNumberOfYearTask(sdf.format(endDate));
	    if (result>0) {
 			msg+=sdf.format(endDate)+"明细数据为空，请您先导入数据<br/>";
 		}else {
			msg+=sdf.format(endDate)+"补跑成功<br/>";
		}
		returnMap.setSucc();
		returnMap.setMsg(msg);
		return returnMap;
	}
	@Override
	public ReturnMap runUpByTicketStationOfMonth(Date startDate, Date endDate) throws Exception{
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		String msg="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if (endDate==null) {
			//执行起始月份
		    int result=addSpzOfMonthTask(sdf.format(startDate));
		    if (result>0) {
				msg+=sdf.format(startDate)+"日统计数据为空，请您先执行日统计<br/>";
			}else {
				msg+=sdf.format(startDate)+"月份补跑成功<br/>";
			}
		    returnMap.setSucc();
			returnMap.setMsg(msg);
			return returnMap;
		}
		Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(startDate);
	    tempStart.add(Calendar.MONTH, 1);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(endDate);
	    //执行起始月份
	    int result=addSpzOfMonthTask(sdf.format(startDate.getTime()));
	    if (result>0) {
			msg+=sdf.format(startDate)+"月份日统计数据为空，请您先执行日统计<br/>";
		}else {
			msg+=sdf.format(startDate)+"月份补跑成功<br/>";
		}
	    //执行中间月份
	    while (tempStart.before(tempEnd)) {
	        //result.add(tempStart.getTime());
	    	result= addSpzOfMonthTask(sdf.format(tempStart.getTime()));
	    	if (result>0) {
	 			msg+=sdf.format(tempStart.getTime())+"月份日统计数据为空，请您先执行日统计<br/>";
	 		}else {
				msg+=sdf.format(tempStart.getTime())+"月份补跑成功<br/>";
			}
	        tempStart.add(Calendar.MONTH, 1);
	    }
	    //执行终止月份
	    result= addSpzOfMonthTask(sdf.format(endDate));
	    if (result>0) {
 			msg+=sdf.format(endDate)+"月份日统计数据为空，请您先执行日统计<br/>";
 		}else {
			msg+=sdf.format(endDate)+"月份补跑成功<br/>";
		}
		returnMap.setSucc();
		returnMap.setMsg(msg);
		return returnMap;
	}
	
	@Override
	public ReturnMap runUpByTicketStationOfYear(Date startDate, Date endDate) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		String msg="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		if (endDate==null) {
			//执行起始月份
		    int result=addSpzOfYearTask(sdf.format(startDate));
		    if (result>0) {
				msg+=sdf.format(startDate)+"年份月统计数据为空，请您先补跑月统计<br/>";
			}else {
				msg+=sdf.format(startDate)+"年份补跑成功<br/>";
			}
		    returnMap.setSucc();
			returnMap.setMsg(msg);
			return returnMap;
		}
		Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(startDate);
	    tempStart.add(Calendar.YEAR, 1);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(endDate);
	    //执行起始年份
	    int result=addSpzOfYearTask(sdf.format(startDate.getTime()));
	    if (result>0) {
			msg+=sdf.format(startDate)+"年份月统计数据为空，请您先补跑月统计<br/>";
		}else {
			msg+=sdf.format(startDate)+"年份补跑成功<br/>";
		}
	    //执行中间年份
	    while (tempStart.before(tempEnd)) {
	        //result.add(tempStart.getTime());
	    	result= addSpzOfYearTask(sdf.format(tempStart.getTime()));
	    	if (result>0) {
	 			msg+=sdf.format(tempStart.getTime())+"年份月统计数据为空，请您先补跑月统计<br/>";
	 		}else {
				msg+=sdf.format(tempStart.getTime())+"年份补跑成功<br/>";
			}
	        tempStart.add(Calendar.YEAR, 1);
	    }
	    //执行终止年份
	    result= addSpzOfYearTask(sdf.format(endDate));
	    if (result>0) {
 			msg+=sdf.format(endDate)+"年份月统计数据为空，请您先补跑月统计<br/>";
 		}else {
			msg+=sdf.format(endDate)+"年份补跑成功<br/>";
		}
		returnMap.setSucc();
		returnMap.setMsg(msg);
		return returnMap;
	}
	@Override
	public ReturnMap runUpByStationSectionOfDay(Date startDate, Date endDate) throws Exception{
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		String msg="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (endDate==null) {
			//执行起始日期
		    int result=addZdOfDayTask(sdf.format(startDate));
		    if (result>0) {
				msg+=sdf.format(startDate)+"售票站日统计数据为空，请您先执行售票站日统计<br/>";
			}else {
				msg+=sdf.format(startDate)+"补跑成功<br/>";
			}
		    returnMap.setSucc();
			returnMap.setMsg(msg);
			return returnMap;
		}
		Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(startDate);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(endDate);
	    //执行起始日期
	    int result=addZdOfDayTask(sdf.format(startDate.getTime()));
	    if (result>0) {
			msg+=sdf.format(startDate)+"售票站日统计数据为空，请您先执行售票站日统计<br/>";
		}else {
			msg+=sdf.format(startDate)+"补跑成功<br/>";
		}
	    //执行中间日期
	    while (tempStart.before(tempEnd)) {
	        //result.add(tempStart.getTime());
	    	result= addZdOfDayTask(sdf.format(tempStart.getTime()));
	    	if (result>0) {
	 			msg+=sdf.format(tempStart.getTime())+"售票站日统计数据为空，请您先执行售票站日统计<br/>";
	 		}else {
				msg+=sdf.format(tempStart.getTime())+"补跑成功<br/>";
			}
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    //执行终止日期
	    result= addZdOfDayTask(sdf.format(endDate));
	    if (result>0) {
 			msg+=sdf.format(endDate)+"售票站日统计数据为空，请您先执行售票站日统计<br/>";
 		}else {
			msg+=sdf.format(endDate)+"补跑成功<br/>";
		}
		returnMap.setSucc();
		returnMap.setMsg(msg);
		return returnMap;
	}
	@Override
	public ReturnMap runUpByStationSectionOfMonth(Date startDate, Date endDate) throws Exception{
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		String msg="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if (endDate==null) {
			//执行起始月份
		    int result=addZdOfMonthTask(sdf.format(startDate));
		    if (result>0) {
				msg+=sdf.format(startDate)+"站段日统计数据为空，请您先执行站段日统计<br/>";
			}else {
				msg+=sdf.format(startDate)+"月份补跑成功<br/>";
			}
		    returnMap.setSucc();
			returnMap.setMsg(msg);
			return returnMap;
		}
		Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(startDate);
	    tempStart.add(Calendar.MONTH, 1);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(endDate);
	    //执行起始月份
	    int result=addZdOfMonthTask(sdf.format(startDate.getTime()));
	    if (result>0) {
			msg+=sdf.format(startDate)+"月份站段日统计数据为空，请您先执行站段日统计<br/>";
		}else {
			msg+=sdf.format(startDate)+"月份补跑成功<br/>";
		}
	    //执行中间月份
	    while (tempStart.before(tempEnd)) {
	        //result.add(tempStart.getTime());
	    	result= addZdOfMonthTask(sdf.format(tempStart.getTime()));
	    	if (result>0) {
	 			msg+=sdf.format(tempStart.getTime())+"月份站段日统计数据为空，请您先执行站段日统计<br/>";
	 		}else {
				msg+=sdf.format(tempStart.getTime())+"月份补跑成功<br/>";
			}
	        tempStart.add(Calendar.MONTH, 1);
	    }
	    //执行终止月份
	    result= addZdOfMonthTask(sdf.format(endDate));
	    if (result>0) {
 			msg+=sdf.format(endDate)+"月份站段日统计数据为空，请您先执行站段日统计<br/>";
 		}else {
			msg+=sdf.format(endDate)+"月份补跑成功<br/>";
		}
		returnMap.setSucc();
		returnMap.setMsg(msg);
		return returnMap;
	}
	
	@Override
	public ReturnMap runUpByStationSectionOfYear(Date startDate, Date endDate) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		String msg="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		if (endDate==null) {
			//执行起始月份
		    int result=addZdOfYearTask(sdf.format(startDate));
		    if (result>0) {
				msg+=sdf.format(startDate)+"年份站段月统计数据为空，请您先补跑站段月统计<br/>";
			}else {
				msg+=sdf.format(startDate)+"年份补跑成功<br/>";
			}
		    returnMap.setSucc();
			returnMap.setMsg(msg);
			return returnMap;
		}
		Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(startDate);
	    tempStart.add(Calendar.YEAR, 1);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(endDate);
	    //执行起始年份
	    int result=addZdOfYearTask(sdf.format(startDate.getTime()));
	    if (result>0) {
			msg+=sdf.format(startDate)+"年份站段月统计数据为空，请您先补跑站段月统计<br/>";
		}else {
			msg+=sdf.format(startDate)+"年份补跑成功<br/>";
		}
	    //执行中间年份
	    while (tempStart.before(tempEnd)) {
	        //result.add(tempStart.getTime());
	    	result= addZdOfYearTask(sdf.format(tempStart.getTime()));
	    	if (result>0) {
	 			msg+=sdf.format(tempStart.getTime())+"年份站段月统计数据为空，请您先补跑站段月统计<br/>";
	 		}else {
				msg+=sdf.format(tempStart.getTime())+"年份补跑成功<br/>";
			}
	        tempStart.add(Calendar.YEAR, 1);
	    }
	    //执行终止年份
	    result= addZdOfYearTask(sdf.format(endDate));
	    if (result>0) {
 			msg+=sdf.format(endDate)+"年份站段月统计数据为空，请您先补跑站段月统计<br/>";
 		}else {
			msg+=sdf.format(endDate)+"年份补跑成功<br/>";
		}
		returnMap.setSucc();
		returnMap.setMsg(msg);
		return returnMap;
	}
}
