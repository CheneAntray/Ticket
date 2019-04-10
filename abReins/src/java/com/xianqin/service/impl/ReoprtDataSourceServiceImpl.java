package com.xianqin.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xianqin.common.QueryRule;
import com.xianqin.dao.IncomeInfoDao;
import com.xianqin.dao.SpzOfdayDao;
import com.xianqin.dao.SpzOfmonthDao;
import com.xianqin.dao.SpzOfyearDao;
import com.xianqin.dao.TicketstationTrainnumberOfmonthDao;
import com.xianqin.dao.TicketstationTrainnumberOfyearDao;
import com.xianqin.domain.SpzOfMonth;
import com.xianqin.domain.SpzOfYear;
import com.xianqin.domain.SpzOfday;
import com.xianqin.domain.StationSectionInfo;
import com.xianqin.domain.TicketStationInfo;
import com.xianqin.domain.TrainNumberInfo;
import com.xianqin.domain.TrainNumberReport;
import com.xianqin.domain.TrainStationInfo;
import com.xianqin.domain.TrainStationReport;
import com.xianqin.domain.UndertakeEnterpriseInfo;
import com.xianqin.service.InitDataService;
import com.xianqin.service.ReoprtDataSourceService;
import com.xianqin.service.RoleInfoService;
import com.xianqin.service.TrainNumberInfoService;
import com.xianqin.service.UserInfoService;

@Service(value = "reoprtDataSourceService")
public class ReoprtDataSourceServiceImpl implements ReoprtDataSourceService {

	@Autowired
	private InitDataService initDataService;

	@Resource
	private UserInfoService userInfoservice;

	@Resource
	private RoleInfoService roleInfoService;
	// 车次service接口
	@Resource
	private TrainNumberInfoService trainNumberInfoService;
	// 车次天dao接口
	@Resource
	private IncomeInfoDao trainnumberOfdayDao;
	// 车次年dao接口
	@Resource
	private TicketstationTrainnumberOfmonthDao trainnumberOfmonthDao;
	// 车次月dao接口
	@Resource
	private TicketstationTrainnumberOfyearDao trainnumberOfyearDao;

	// 售票站天dao接口
	@Resource
	private SpzOfdayDao spzOfdayDao;
	// 售票站月dao接口
	@Resource
	private SpzOfmonthDao spzOfmonthDao;
	// 售票站年dao接口
	@Resource
	private SpzOfyearDao spzOfyearDao;

	// 车次map
	private Map<String, TrainNumberInfo> trainNumberMap = null;
	// 车站map
	private Map<String, String> trainStationMap = null;
	// 担当企业map
	private Map<String, String> undertakeEnterpriseMap = null;
	// 车站map
	private Map<String, String> stationSectionMap = null;
	// 售票站map
	private Map<String, TicketStationInfo> ticketStationMap = null;

	private final static int FIRST = 1;

	// 车次编号
	private final static String trainNumberCode = "001";
	// 车站编号
	private final static String trainStationCode = "002";

	@Override
	public List<?> getListByReportCode(String reportCode, Map<String, ?> parmsMap) throws Exception {
		// TODO Auto-generated method stub
		List<?> list = null;
			if (trainNumberCode.equals(reportCode) && !"".equals(reportCode) && reportCode != null) {
				list = geTrainNumberInfosToReport(parmsMap);
			} else if (trainStationCode.equals(reportCode) && !"".equals(reportCode) && reportCode != null) {
				list = getTrainStationReport(parmsMap);
			}
		return list;
	}

	// 拼接整天，整月，整年
	private static String getSQL(int bigOne, int smallOne, String str) {
		String end = "";
		for (;; smallOne++) {
			if (smallOne == bigOne - FIRST) {
				end += str + smallOne;
				break;
			}
			end += str + smallOne + ",";
		}
		return end;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private List<TrainNumberReport> geTrainNumberInfosToReport(Map<String, ?> parmsMap) throws Exception {
		// 车次报表实体
		TrainNumberReport trainNumberReport = null;
		// 封装车次报表数据
		List<TrainNumberReport> reportList = null;
		// 组装车次报表数据
		List<TrainNumberInfo> trainNumberInfos = null;
		// 车次实体
		TrainNumberInfo trainNumberInfo = null;
		// 开始日期
		String startDateStr = null;
		// 结束日期
		String endDateStr = null;
		// 整年数据 加
		double yearIncomSum = 0;
		long yearPeopleSum = 0l;
		// 整月数据 加
		double monthIncomSum = 0;
		long monthPeopleSum = 0l;
		// 整日数据 加
		double dayIncomSum = 0;
		long dayPeopleSum = 0l;
		// 整月数据 减
		double monthIncomMinus = 0;
		long monthPeopleMinus = 0l;
		// 整日数据 减
		double dayIncomMinus = 0;
		long dayPeopleMinus = 0l;
		// 收入总和
		double incomSum = 0;
		// 人数总和
		long peopleCountSum = 0;
		// 总收入
		double incomeSums = 0;
		// 总人数
		int peopleCountSums = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 开始处理查询参数
		// 获取开始和结束时间
		if (parmsMap.containsKey("startDate")) {
			// 开始时间不为空
			if (parmsMap.get("startDate") != null && !"0".equals(parmsMap.get("startDate").toString())) {
				startDateStr = parmsMap.get("startDate").toString();
			} else {
				startDateStr = "2015-01-01";// 由于系统与2017年搭建，所以不太可能出现2017年之前的数据，由于数据统计的时间参数是必须的(如果前台页面传入参数为空，则时间范围为数据库中所有数据的合集，但明细数据统计会影响查询效率，所以在起始时间参数为空时，将起始时间设置为2015年1月1日)
			}
		}
		if (parmsMap.containsKey("endDate")) {
			// 结束时间不为空
			if (parmsMap.get("endDate") != null && !"0".equals(parmsMap.get("endDate").toString())) {
				endDateStr = parmsMap.get("endDate").toString();
			} else {
				endDateStr = sdf.format(new Date());
				// 如果结束时间为空，则遵循查询起始与结束时间不空为空的原则，结束时间为当前日期
			}
		}

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = sdf.parse(startDateStr);
		Date eDate = sdf.parse(endDateStr);

		if (eDate.compareTo(new Date()) > 0) {
			eDate = new Date();
			eDate.setDate(new Date().getDate() - 1);
		}

		int startYear = sDate.getYear() + 1900;
		int endYear = eDate.getYear() + 1900;

		int startMonth = sDate.getMonth() + 1;
		int endMonth = eDate.getMonth() + 1;

		int startDay = sDate.getDate();
		int endDay = eDate.getDate();

		reportList = new ArrayList<TrainNumberReport>();
		trainNumberInfos = new ArrayList<TrainNumberInfo>();
		// 从map集合中获取车次信息
		trainNumberMap = (Map<String, TrainNumberInfo>) initDataService.getEhcacheMapByTableName(TrainNumberInfo.TABLE_NAME);
		if(trainNumberMap!=null){
			for (Map.Entry<String, TrainNumberInfo> m : trainNumberMap.entrySet()) {
				//当车次的方向为零是不参与数据组装
				if (!"0".equals(m.getValue().getDirectionId().toString())&&m.getValue().getDirectionId()!=null) {
					//放前台数据返回结果不为零时 且 方向id与集合中的方向id相同时 封装数据
					if (parmsMap.get("directionCode")!=null&&!"0".equals(parmsMap.get("directionCode"))) {
						if (parmsMap.get("directionCode").equals(m.getValue().getDirectionId().toString())) {
							trainNumberInfo = new TrainNumberInfo();
							trainNumberInfo.setId(m.getValue().getId());
							trainNumberInfo.setTrainNo(m.getValue().getTrainNo());
							trainNumberInfo.setStartStationId(m.getValue().getStartStationId());
							trainNumberInfo.setEndStationId(m.getValue().getEndStationId());
							trainNumberInfo.setUnderEnterId(m.getValue().getUnderEnterId());
							trainNumberInfos.add(trainNumberInfo);
						}
						//当前台车次编号不为零 且 车次编号与集合中的车次编号相同时 封装数据
					} else if (!"0".equals(parmsMap.get("trainNumber"))) {
						if (parmsMap.get("trainNumber").equals(m.getValue().getTrainNo())) {
							trainNumberInfo = new TrainNumberInfo();
							trainNumberInfo.setId(m.getValue().getId());
							trainNumberInfo.setTrainNo(m.getValue().getTrainNo());
							trainNumberInfo.setStartStationId(m.getValue().getStartStationId());
							trainNumberInfo.setEndStationId(m.getValue().getEndStationId());
							trainNumberInfo.setUnderEnterId(m.getValue().getUnderEnterId());
							trainNumberInfos.add(trainNumberInfo);
						}
						//否组都不符合 封装所有数据
					} else {
						trainNumberInfo = new TrainNumberInfo();
						trainNumberInfo.setId(m.getValue().getId());
						trainNumberInfo.setTrainNo(m.getValue().getTrainNo());
						trainNumberInfo.setStartStationId(m.getValue().getStartStationId());
						trainNumberInfo.setEndStationId(m.getValue().getEndStationId());
						trainNumberInfo.setUnderEnterId(m.getValue().getUnderEnterId());
						trainNumberInfos.add(trainNumberInfo);
					}
				}

			}
		}
		// 从缓存中获取担当企业信息
		undertakeEnterpriseMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(UndertakeEnterpriseInfo.TABLE_NAME);

		// 从缓存中获取车站起始站 终点站信息
		trainStationMap = (Map<String, String>) initDataService.getEhcacheMapByTableName(TrainStationInfo.TABLE_NAME);
		if (trainStationMap != null) {
			// 组装所有一封装的车次数据
			for (TrainNumberInfo trainNumberInfo2 : trainNumberInfos) {
				// 初始化数据
				incomSum = 0;
				peopleCountSum = 0;
				yearIncomSum = 0;
				monthIncomSum = 0;
				dayIncomSum = 0;
				monthIncomMinus = 0;
				dayIncomMinus = 0;
				yearPeopleSum = 0;
				monthPeopleSum = 0;
				dayPeopleSum = 0;
				monthPeopleMinus = 0;
				dayPeopleMinus = 0;
				// 初始化车次信息报表
				trainNumberReport = new TrainNumberReport();
				for (Entry<String, String> station : trainStationMap.entrySet()) {
					// 获取车辆编号
					trainNumberReport.setTrainNo(trainNumberInfo2.getTrainNo());
					// 获取始发站
					if (trainNumberInfo2.getStartStationId().toString().equals(station.getValue())) {
						trainNumberReport.setStartStationId(station.getKey());
					}
					// 获取终点站
					if (trainNumberInfo2.getEndStationId().toString().equals(station.getValue())) {
						trainNumberReport.setEndStationId(station.getKey());
					}
				}
				// 获取担当企业
				for (Map.Entry<String, String> under : undertakeEnterpriseMap.entrySet()) {
					if (trainNumberInfo2.getUnderEnterId().toString().equals(under.getValue())) {
						trainNumberReport.setUnderEnterId(under.getKey());
					}
				}
				// 计算每个车次的人数及收入
				// 起始年不等于终止年 2015-12-31 2017-12-31
				if (endYear - startYear > 0) {
					// 起始年到终止年前一年总计
					String yearDate = getSQL(endYear, startYear, "");
					String[] year = yearDate.split(",");
					for (String string : year) {
						List<Object[]> trainNumberOfYear = trainnumberOfyearDao
								.getTicketstationTrainnumberOfyearByHql(string, trainNumberInfo2.getId().toString());
						if (trainNumberOfYear != null && trainNumberOfYear.size() > 0) {
							// 整年人数及收入累计
							for (int i = 0; i < trainNumberOfYear.size(); i++) {
								Object[] objects = (Object[]) trainNumberOfYear.get(i);
								yearIncomSum += (Double) objects[0];
								yearPeopleSum += new BigDecimal(objects[1].toString()).longValue();

							}
						}
					}
					// 起始年1月到起始年终止月上一个月的总计（减）
					if (startMonth > FIRST) {
						String monthDate = getSQL(startMonth, FIRST, "");
						String[] month = monthDate.split(",");
						for (String string : month) {
							List<Object[]> trainNumberOfMonth = null;
							if (string.length() == 1) {
								trainNumberOfMonth = trainnumberOfmonthDao.getIncomePeopleCountGroouByHql(
										endYear + "-0" + string, trainNumberInfo2.getId().toString());
							} else {
								trainNumberOfMonth = trainnumberOfmonthDao.getIncomePeopleCountGroouByHql(
										endYear + "-" + string, trainNumberInfo2.getId().toString());
							}
							if (trainNumberOfMonth != null && trainNumberOfMonth.size() > 0) {
								// 整月人数及收入累计
								for (int i = 0; i < trainNumberOfMonth.size(); i++) {
									Object[] objects = (Object[]) trainNumberOfMonth.get(i);
									monthIncomMinus += (Double) objects[0];
									monthPeopleMinus += new BigDecimal(objects[1].toString()).longValue();
								}
							}
						}
					}
					// 起始年当前月1日到起始年终止月前一天的总计（减）
					if (startDay > FIRST) {
						String date = startYear + "-" + startMonth + "-";
						String dataday = getSQL(startDay + 1, FIRST, date);
						String[] day = dataday.split(",");
						for (String string : day) {
							List<Object[]> trainNumberOfDay = trainnumberOfdayDao.getIncomePeopleCountGroouByHql(string,
									trainNumberInfo2.getId().toString());
							if (trainNumberOfDay != null && trainNumberOfDay.size() > 0) {
								for (int i = 0; i < trainNumberOfDay.size(); i++) {
									Object[] objects = (Object[]) trainNumberOfDay.get(i);
									dayIncomMinus += (Double) objects[0];
									dayPeopleMinus += new BigDecimal(objects[1].toString()).longValue();
								}
							}
						}
					}
					// 终止年1月到终止年终止月上一个月的总计（加）
					if (endMonth > FIRST) {
						// String sqlM = CalCount.getMonthSQL(endMonth, FIRST,
						// endYear);
						String monthDate = getSQL(endMonth, FIRST, "");
						String[] month = monthDate.split(",");
						for (String string : month) {
							List<Object[]> trainNumberOfMonth = null;
							if (string.length() == 1) {
								trainNumberOfMonth = trainnumberOfmonthDao.getIncomePeopleCountGroouByHql(
										endYear + "-0" + string, trainNumberInfo2.getId().toString());
							} else {
								trainNumberOfMonth = trainnumberOfmonthDao.getIncomePeopleCountGroouByHql(
										endYear + "-" + string, trainNumberInfo2.getId().toString());
							}
							if (trainNumberOfMonth != null && trainNumberOfMonth.size() > 0) {
								// 整月人数及收入累计
								for (int i = 0; i < trainNumberOfMonth.size(); i++) {
									Object[] objects = (Object[]) trainNumberOfMonth.get(i);
									monthIncomSum += (Double) objects[0];
									monthPeopleSum += new BigDecimal(objects[1].toString()).longValue();
								}
							}
						}
					}
					// 终止年终止月1日到终止年终止月终止日的总计（加）
					String date = endYear + "-" + endMonth + "-";
					String dataday = getSQL(endDay + 1, endMonth, date);
					String[] day = dataday.split(",");
					for (String string : day) {
						List<Object[]> trainNumberOfDay = trainnumberOfdayDao.getIncomePeopleCountGroouByHql(string,
								trainNumberInfo2.getId().toString());
						if (trainNumberOfDay != null && trainNumberOfDay.size() > 0) {
							for (int i = 0; i < trainNumberOfDay.size(); i++) {
								Object[] objects = (Object[]) trainNumberOfDay.get(i);
								// 整天人数及收入累计
								dayIncomSum += (Double) objects[0];
								dayPeopleSum += new BigDecimal(objects[1].toString()).longValue();
							}
						}
					}
					// 起始年等于终止年 && 起始月不等于终止月
				} else if (endMonth - startMonth > 0) {
					// 起始月到终止月上一个月的总计
					String monthDate = getSQL(endMonth, startMonth, "");
					String[] month = monthDate.split(",");
					for (String string : month) {
						List<Object[]> trainNumberOfMonth = null;
						if (string.length() == 1) {
							trainNumberOfMonth = trainnumberOfmonthDao.getIncomePeopleCountGroouByHql(
									endYear + "-0" + string, trainNumberInfo2.getId().toString());
						} else {
							trainNumberOfMonth = trainnumberOfmonthDao.getIncomePeopleCountGroouByHql(
									endYear + "-" + string, trainNumberInfo2.getId().toString());
						}
						if (trainNumberOfMonth != null && trainNumberOfMonth.size() > 0) {
							// 整月人数及收入累计
							for (int i = 0; i < trainNumberOfMonth.size(); i++) {
								Object[] objects = (Object[]) trainNumberOfMonth.get(i);
								monthIncomSum += (Double) objects[0];
								monthPeopleSum += new BigDecimal(objects[1].toString()).longValue();
							}
						}
					}
					// 起始月1日到起始月起始日的总计（减）
					if (startDay > FIRST) {
						String date = startYear + "-" + startMonth + "-";
						String dataday = getSQL(startDay + 1, startMonth, date);
						String[] day = dataday.split(",");
						for (String string : day) {
							List<Object[]> trainNumberOfDay = trainnumberOfdayDao.getIncomePeopleCountGroouByHql(string,
									trainNumberInfo2.getId().toString());
							if (trainNumberOfDay != null && trainNumberOfDay.size() > 0) {
								for (int i = 0; i < trainNumberOfDay.size(); i++) {
									Object[] objects = (Object[]) trainNumberOfDay.get(i);
									dayIncomMinus += (Double) objects[0];
									dayPeopleMinus += new BigDecimal(objects[1].toString()).longValue();
								}
							}
						}
					}
					// 终止月1日到终止月终止日的总计（加）
					String date = endYear + "-" + endMonth + "-";
					String dataday = getSQL(endDay + 1, 1, date);
					String[] day = dataday.split(",");
					for (String string : day) {
						List<Object[]> trainNumberOfDay = trainnumberOfdayDao.getIncomePeopleCountGroouByHql(string,
								trainNumberInfo2.getId().toString());
						if (trainNumberOfDay != null && trainNumberOfDay.size() > 0) {
							for (int i = 0; i < trainNumberOfDay.size(); i++) {
								Object[] objects = (Object[]) trainNumberOfDay.get(i);
								// 整天人数及收入累计
								dayIncomSum += (Double) objects[0];
								dayPeopleSum += new BigDecimal(objects[1].toString()).longValue();
							}
						}
					}
				} else {
					// 起始日到终止日的总计
					String date = endYear + "-" + endMonth + "-";
					String dataday = getSQL(endDay + 1, startDay, date);
					String[] day = dataday.split(",");
					for (String string : day) {
						List<Object[]> trainNumberOfDay = trainnumberOfdayDao.getIncomePeopleCountGroouByHql(string,
								trainNumberInfo2.getId().toString());
						if (trainNumberOfDay != null && trainNumberOfDay.size() > 0) {
							for (int i = 0; i < trainNumberOfDay.size(); i++) {
								Object[] objects = (Object[]) trainNumberOfDay.get(i);
								// 整天人数及收入累计
								dayIncomSum += (Double) objects[0];
								dayPeopleSum += new BigDecimal(objects[1].toString()).longValue();
							}
						}
					}
				}
				// 收入
				incomSum = yearIncomSum + monthIncomSum + dayIncomSum - monthIncomMinus - dayIncomMinus;
				trainNumberReport.setIncome(incomSum);
				// 人数
				peopleCountSum = yearPeopleSum + monthPeopleSum + dayPeopleSum - monthPeopleMinus - dayPeopleMinus;
				trainNumberReport.setPeopleCount(peopleCountSum);
				// 设置查询日期
				trainNumberReport.setStartDate(sDate);
				trainNumberReport.setEndDate(eDate);
				// 设置报表标题
				trainNumberReport.setReportName(parmsMap.get("reportName").toString());
				// 总收入
				incomeSums += incomSum;
				trainNumberReport.setIncomeSum(incomeSums);
				// 总人数
				peopleCountSums += peopleCountSum;
				trainNumberReport.setPeopleCountSum(peopleCountSums);
				// 总售票率
				if (incomeSums != 0 && peopleCountSums != 0) {
					BigDecimal bigDecimal = new BigDecimal(incomeSums / peopleCountSums);
					BigDecimal ticketRateSum = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);
					trainNumberReport.setTicketRateSum(ticketRateSum);
				} else {
					trainNumberReport.setTicketRateSum(BigDecimal.ZERO);
				}
				// 组装数据
				reportList.add(trainNumberReport);
			}
		}
		return reportList;
	}

	/**
	 * 根据车次方向 编号 获取车次统计信息
	 * 
	 * @param parmsMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	private List<TrainStationReport> getTrainStationReport(Map<String, ?> parmsMap) throws Exception {
		QueryRule queryRule = null;
		// 封装报表最后数据
		List<TrainStationReport> reportList = null;
		// 组装报表所需数据
		List<TrainStationReport> trainStationReports = null;
		// 报表数据实体
		TrainStationReport trainStationReport = null;
		// 开始日期
		String startDateStr = null;
		// 结束日期
		String endDateStr = null;
		// 整年数据 加
		double yearIncomSum = 0;
		long yearPeopleSum = 0l;
		// 整月数据 加
		double monthIncomSum = 0;
		long monthPeopleSum = 0l;
		// 整日数据 加
		double dayIncomSum = 0;
		long dayPeopleSum = 0l;
		// 整月数据 减
		double monthIncomMinus = 0;
		long monthPeopleMinus = 0l;
		// 整日数据 减
		double dayIncomMinus = 0;
		long dayPeopleMinus = 0l;
		// 收入总和
		double incomSum = 0;
		// 人数总和
		long peopleCountSum = 0;
		// 总收入
		double incomeSums = 0;
		// 总人数
		int peopleCountSums = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 开始处理查询参数
		// 获取开始和结束时间
		if (parmsMap.containsKey("startDate")) {
			// 开始时间不为空
			if (parmsMap.get("startDate") != null && !"0".equals(parmsMap.get("startDate").toString())) {
				startDateStr = parmsMap.get("startDate").toString();
			} else {
				startDateStr = "2015-01-01";// 由于系统与2017年搭建，所以不太可能出现2017年之前的数据，由于数据统计的时间参数是必须的(如果前台页面传入参数为空，则时间范围为数据库中所有数据的合集，但明细数据统计会影响查询效率，所以在起始时间参数为空时，将起始时间设置为2015年1月1日)
			}
		}
		if (parmsMap.containsKey("endDate")) {
			// 开始时间不为空
			if (parmsMap.get("endDate") != null && !"0".equals(parmsMap.get("endDate").toString())) {
				endDateStr = parmsMap.get("endDate").toString();
			} else {
				 endDateStr = sdf.format(new Date());
				// 如果结束时间为空，则遵循查询起始与结束时间不空为空的原则，结束时间为当前日期
			}
		}

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = sdf.parse(startDateStr);
		Date eDate = sdf.parse(endDateStr);

		if (eDate.compareTo(new Date()) > 0) {
			eDate = new Date();
			eDate.setDate(new Date().getDate() - 1);
		}

		int startYear = sDate.getYear() + 1900;
		int endYear = eDate.getYear() + 1900;

		int startMonth = sDate.getMonth() + 1;
		int endMonth = eDate.getMonth() + 1;

		int startDay = sDate.getDate();
		int endDay = eDate.getDate();

		trainStationReports = new ArrayList<TrainStationReport>();
		// 从缓存中取出车站信息
		stationSectionMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(StationSectionInfo.TABLE_NAME);
		// 从缓存中取出售票站信息
		ticketStationMap = (Map<String, TicketStationInfo>) initDataService
				.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
		// 当车站id为零 并且 售票站id为零时 查询出所有车站下的售票站
		if (parmsMap.get("stationId").equals("0") && parmsMap.get("tickStationId").equals("0")) {
			for (Map.Entry<String, String> stationMap : stationSectionMap.entrySet()) {
				for (Map.Entry<String, TicketStationInfo> ticketMap : ticketStationMap.entrySet()) {
					if (ticketMap.getValue().getStationSectionId().toString().equals(stationMap.getValue())) {
						trainStationReport = new TrainStationReport();
						trainStationReport.setTicketId(ticketMap.getValue().getId());
						trainStationReport.setStationName(stationMap.getKey());
						trainStationReport.setTicketName(ticketMap.getValue().getName());
						trainStationReports.add(trainStationReport);
					}
				}
			}
			// 当车站ID不为零 并且 售票站为零时 查处当前车站下的所有售票站
		} else if (!parmsMap.get("stationId").equals("0") && parmsMap.get("tickStationId").equals("0")) {
			for (Map.Entry<String, String> stationMap : stationSectionMap.entrySet()) {
				for (Map.Entry<String, TicketStationInfo> ticketMap : ticketStationMap.entrySet()) {
					if (ticketMap.getValue().getStationSectionId().toString().equals(stationMap.getValue())) {
						if (parmsMap.get("stationId").equals(stationMap.getValue())) {
							trainStationReport = new TrainStationReport();
							trainStationReport.setTicketId(ticketMap.getValue().getId());
							trainStationReport.setStationName(stationMap.getKey());
							trainStationReport.setTicketName(ticketMap.getValue().getName());
							trainStationReports.add(trainStationReport);
						}
					}
				}
			}
			// 当车站ID为零 并且 售票站ID不为零时 查处当前售票站所对应的信息
		} else if (!parmsMap.get("tickStationId").equals("0") && parmsMap.get("stationId").equals("0")
				|| !parmsMap.get("stationId").equals("0")) {
			for (Map.Entry<String, String> stationMap : stationSectionMap.entrySet()) {
				for (Map.Entry<String, TicketStationInfo> ticketMap : ticketStationMap.entrySet()) {
					if (parmsMap.get("tickStationId").equals(ticketMap.getValue().getId().toString())
							&& ticketMap.getValue().getStationSectionId().toString().equals(stationMap.getValue())) {
						trainStationReport = new TrainStationReport();
						trainStationReport.setTicketId(ticketMap.getValue().getId());
						trainStationReport.setStationName(stationMap.getKey());
						trainStationReport.setTicketName(ticketMap.getValue().getName());
						trainStationReports.add(trainStationReport);
					}
				}
			}
		}

		reportList = new ArrayList<TrainStationReport>();
		// 获取收入及人数
		if (trainStationReports != null) {
			for (TrainStationReport trainStationReport2 : trainStationReports) {
				// 初始化数据
				incomSum = 0;
				peopleCountSum = 0;
				yearIncomSum = 0;
				monthIncomSum = 0;
				dayIncomSum = 0;
				monthIncomMinus = 0;
				dayIncomMinus = 0;
				yearPeopleSum = 0;
				monthPeopleSum = 0;
				dayPeopleSum = 0;
				monthPeopleMinus = 0;
				dayPeopleMinus = 0;

				// 计算每个车次的人数及收入
				// 起始年不等于终止年 2015-12-31 2017-12-31
				if (endYear - startYear > 0) {
					// 起始年到终止年前一年总计
					String yearDate = getSQL(endYear, startYear, "");
					String[] year = yearDate.split(",");
					for (String string : year) {
						queryRule = QueryRule.getInstance();
						queryRule.addEqual(SpzOfYear._spzId, trainStationReport2.getTicketId());
						queryRule.addEqual(SpzOfYear._year, Long.parseLong(string));
						SpzOfYear spzOfYear = spzOfyearDao.getSpzOfyearByCondition(queryRule);
						if (spzOfYear != null) {
							// 整年人数及收入累计
							yearIncomSum += spzOfYear.getIncome();
							yearPeopleSum += spzOfYear.getPeopleCount();
						}
					}
					// 起始年1月到起始年终止月上一个月的总计（减）
					if (startMonth > FIRST) {
						String monthDate = getSQL(startMonth, FIRST, "");
						String[] month = monthDate.split(",");
						for (String string : month) {
							queryRule = QueryRule.getInstance();
							if (string.length() == 1) {
								queryRule.addEqual(SpzOfMonth._month, endYear + "-0" + string);
								queryRule.addEqual(SpzOfMonth._spzId, trainStationReport2.getTicketId());
							} else {
								queryRule.addEqual(SpzOfMonth._month, endYear + "-" + string);
								queryRule.addEqual(SpzOfMonth._spzId, trainStationReport2.getTicketId());
							}
							SpzOfMonth spzOfMonth = spzOfmonthDao.getSpzOfmonthByCondition(queryRule);
							if (spzOfMonth != null) {
								// 整月人数及收入累计
								monthIncomMinus += spzOfMonth.getIncome();
								monthPeopleMinus += spzOfMonth.getPeopleCount();
							}
						}
					}
					// 起始年当前月1日到起始年终止月前一天的总计（减）
					if (startDay > FIRST) {
						String date = startYear + "-" + startMonth + "-";
						String dataday = getSQL(startDay + 1, FIRST, date);
						String[] day = dataday.split(",");
						for (String string : day) {
							queryRule = QueryRule.getInstance();
							queryRule.addEqual(SpzOfday._spzId, trainStationReport2.getTicketId());
							queryRule.addEqual(SpzOfday._dataDate, sdf.parseObject(string));
							SpzOfday spzOfday = spzOfdayDao.getSpzOfdayByCondition(queryRule);
							if (spzOfday != null) {
								dayIncomMinus += spzOfday.getIncome();
								dayPeopleMinus += spzOfday.getPeopleCount();
							}
						}
					}
					// 终止年1月到终止年终止月上一个月的总计（加）
					if (endMonth > FIRST) {
						// String sqlM = CalCount.getMonthSQL(endMonth, FIRST,
						// endYear);
						String monthDate = getSQL(endMonth, FIRST, "");
						String[] month = monthDate.split(",");
						for (String string : month) {
							queryRule = QueryRule.getInstance();
							if (string.length() == 1) {
								queryRule.addEqual(SpzOfMonth._month, endYear + "-0" + string);
								queryRule.addEqual(SpzOfMonth._spzId, trainStationReport2.getTicketId());
							} else {
								queryRule.addEqual(SpzOfMonth._month, endYear + "-" + string);
								queryRule.addEqual(SpzOfMonth._spzId, trainStationReport2.getTicketId());
							}
							SpzOfMonth spzOfMonth = spzOfmonthDao.getSpzOfmonthByCondition(queryRule);
							if (spzOfMonth != null) {
								// 整月人数及收入累计
								monthIncomSum += spzOfMonth.getIncome();
								monthPeopleSum += spzOfMonth.getPeopleCount();
							}
						}
					}
					// 终止年终止月1日到终止年终止月终止日的总计（加）
					// String sqlD = CalCount.getDaySQL(endDay + 1, FIRST,
					// endYear, endMonth);
					String date = endYear + "-" + endMonth + "-";
					String dataday = getSQL(endDay + 1, endMonth, date);
					String[] day = dataday.split(",");
					for (String string : day) {
						queryRule = QueryRule.getInstance();
						queryRule.addEqual(SpzOfday._dataDate, sdf.parseObject(string));
						queryRule.addEqual(SpzOfday._spzId, trainStationReport2.getTicketId());
						SpzOfday spzOfday = spzOfdayDao.getSpzOfdayByCondition(queryRule);
						if (spzOfday != null) {
							// 整天人数及收入累计
							dayIncomSum += spzOfday.getIncome();
							dayPeopleSum += spzOfday.getPeopleCount();
						}
					}
					// 起始年等于终止年 && 起始月不等于终止月
				} else if (endMonth - startMonth > 0) {
					// 起始月到终止月上一个月的总计
					String monthDate = getSQL(endMonth, startMonth, "");
					String[] month = monthDate.split(",");
					for (String string : month) {
						queryRule = QueryRule.getInstance();
						if (string.length() == 1) {
							queryRule.addEqual(SpzOfMonth._month, endYear + "-0" + string);
							queryRule.addEqual(SpzOfMonth._spzId, trainStationReport2.getTicketId());
						} else {
							queryRule.addEqual(SpzOfMonth._month, endYear + "-" + string);
							queryRule.addEqual(SpzOfMonth._spzId, trainStationReport2.getTicketId());
						}
						SpzOfMonth spzOfMonth = spzOfmonthDao.getSpzOfmonthByCondition(queryRule);
						if (spzOfMonth != null) {
							// 整月人数及收入累计
							monthIncomSum += spzOfMonth.getIncome();
							monthPeopleSum += spzOfMonth.getPeopleCount();
						}
					}
					// 起始月1日到起始月起始日的总计（减）
					if (startDay > FIRST) {
						// String sqlD = CalCount.getDaySQL(startDay, FIRST,
						// startYear,
						// startMonth);
						String date = startYear + "-" + startMonth + "-";
						String dataday = getSQL(startDay + 1, startMonth, date);
						String[] day = dataday.split(",");
						for (String string : day) {
							queryRule = QueryRule.getInstance();
							queryRule.addEqual(SpzOfday._dataDate, sdf.parseObject(string));
							queryRule.addEqual(SpzOfday._spzId, trainStationReport2.getTicketId());
							SpzOfday spzOfday = spzOfdayDao.getSpzOfdayByCondition(queryRule);
							if (spzOfday != null) {
								// 整天人数及收入累计
								dayIncomMinus += spzOfday.getIncome();
								dayPeopleMinus += spzOfday.getPeopleCount();
							}
						}
					}
					// 终止月1日到终止月终止日的总计（加）
					// String sqlD = CalCount.getDaySQL(endDay + 1, FIRST,
					// endYear,
					// endMonth);
					String date = endYear + "-" + endMonth + "-";
					String dataday = getSQL(endDay + 1, endMonth, date);
					String[] day = dataday.split(",");
					for (String string : day) {
						queryRule = QueryRule.getInstance();
						queryRule.addEqual(SpzOfday._dataDate, sdf.parseObject(string));
						queryRule.addEqual(SpzOfday._spzId, trainStationReport2.getTicketId());
						SpzOfday spzOfday = spzOfdayDao.getSpzOfdayByCondition(queryRule);
						if (spzOfday != null) {
							// 整天人数及收入累计
							dayIncomSum += spzOfday.getIncome();
							dayPeopleSum += spzOfday.getPeopleCount();
						}
					}
				} else {
					// 起始日到终止日的总计
					String date = endYear + "-" + endMonth + "-";
					String dataday = getSQL(endDay + 1, startDay, date);
					String[] day = dataday.split(",");
					for (String string : day) {
						queryRule = QueryRule.getInstance();
						queryRule.addEqual(SpzOfday._dataDate, sdf.parseObject(string));
						queryRule.addEqual(SpzOfday._spzId, trainStationReport2.getTicketId());
						SpzOfday spzOfday = spzOfdayDao.getSpzOfdayByCondition(queryRule);
						if (spzOfday != null) {
							// 整天人数及收入累计
							dayIncomSum += spzOfday.getIncome();
							dayPeopleSum += spzOfday.getPeopleCount();
						}
					}
				}
				// 统计总数
				incomSum = yearIncomSum + monthIncomSum + dayIncomSum - monthIncomMinus - dayIncomMinus;
				peopleCountSum = yearPeopleSum + monthPeopleSum + dayPeopleSum - monthPeopleMinus - dayPeopleMinus;
				// 填充数据
				trainStationReport2.setStartDate(startDateStr);
				trainStationReport2.setEndDate(endDateStr);
				trainStationReport2.setReportName(parmsMap.get("reportName").toString());
				trainStationReport2.setIncome(incomSum);
				trainStationReport2.setPeopleCount((int) peopleCountSum);
				// 单次车站售票率
				if (incomSum != 0 && peopleCountSum != 0) {
					BigDecimal bigDecimal = new BigDecimal(incomSum / peopleCountSum);
					BigDecimal ticketRate = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);
					trainStationReport2.setTicketRate(ticketRate);
				} else {
					trainStationReport2.setTicketRate(BigDecimal.ZERO);
				}
				// 总人数及总收入
				incomeSums += incomSum;
				peopleCountSums += peopleCountSum;
				trainStationReport2.setIncomeSum(incomeSums);
				trainStationReport2.setPeopleCountSum(peopleCountSums);
				// 总售票率
				if (incomeSums != 0 && peopleCountSums != 0) {
					BigDecimal bigDecimal = new BigDecimal(incomeSums / peopleCountSums);
					BigDecimal ticketRateSum = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);
					trainStationReport2.setTicketRateSum(ticketRateSum);
				} else {
					trainStationReport2.setTicketRateSum(BigDecimal.ZERO);
				}
				reportList.add(trainStationReport2);
			}
		}
		return reportList;
	}
}
