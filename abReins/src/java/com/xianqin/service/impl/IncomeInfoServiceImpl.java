package com.xianqin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.ReturnMap;
import com.base.utils.DateUtil;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.IncomeInfoDao;
import com.xianqin.dao.StationSectionInfoDao;
import com.xianqin.dao.TicketStationInfoDao;
import com.xianqin.dao.TrainNumberInfoDao;
import com.xianqin.dao.TrainStationInfoDao;
import com.xianqin.dao.UndertakeEnterpriseInfoDao;
import com.xianqin.domain.IncomeInfo;
import com.xianqin.domain.StationSectionInfo;
import com.xianqin.domain.TicketStationInfo;
import com.xianqin.domain.TrainNumberInfo;
import com.xianqin.domain.TrainStationInfo;
import com.xianqin.domain.UndertakeEnterpriseInfo;
import com.xianqin.security.service.AccountService;
import com.xianqin.service.IncomeInfoService;
import com.xianqin.service.InitDataService;

@Service("incomeInfoService")
public class IncomeInfoServiceImpl implements IncomeInfoService {

	@Autowired
	private InitDataService initDataService;
	@Resource
	private IncomeInfoDao incomeInfoDao;
	@Resource
	private StationSectionInfoDao stationSectionInfoDao;
	@Resource
	private TicketStationInfoDao ticketStationInfoDao;
	@Resource
	private TrainNumberInfoDao trainNumberInfoDao;
	@Resource
	private TrainStationInfoDao trainStationInfoDao;
	@Resource
	private UndertakeEnterpriseInfoDao undertakeEnterpriseInfoDao;
	@Autowired
	private AccountService accountService;
	private Subject subject;
	private Map<String, String> stationSectionMap = null;
	private Map<String, TicketStationInfo> ticketStationMap = null;
	private Map<String, String> trainStationInfoMap = null;
	private Map<String, String> undertakeEnterpriseMap = null;
	private Map<String, TrainNumberInfo> trainNumberMap = null;

	@Override
	public ReturnMap saveIncomeInfo(IncomeInfo incomeInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnMap updateIncomeInfo(IncomeInfo incomeInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnMap queryIncomeInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap ret=new ReturnMap();
		ret.setSucc();
		ret.setObjContext(incomeInfoDao.queryIncomeInfoByPage(queryRule, pageIndex, pageSize));
		ret.setMsg("查询收入信息成功");
		return ret;
	}

	@Override
	public ReturnMap deleteIncomeInfoById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IncomeInfo getIncomeInfoByCondition(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> saveBatch(List<Map<Integer, String>> list,String dataDate) throws Exception {
		// TODO Auto-generated method stub
		// 获取当前登录用户的Id
		subject = SecurityUtils.getSubject();
		String userId = accountService.getUserIdBySubjec(subject);
		Map<String, String> map = new HashMap<String, String>(2);
		map = saveExclePerson(list, userId,dataDate);
		return map;
	}

	/**
	 * 通过解析Excle，保存收入信息、站段信息、车次信息、车次始发站信息、担当企业信息
	 * 
	 * @param list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> saveExclePerson(List<Map<Integer, String>> list, String createOper,String dataDate) throws Exception {
		IncomeInfo incomeInfo = null;
		Integer errorCount = 0;
		int rows = 0;
		Map<String, String> map = new HashMap<String, String>(2);
		String str = "";
		stationSectionMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(StationSectionInfo.TABLE_NAME);
		ticketStationMap = (Map<String, TicketStationInfo>) initDataService
				.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
		trainStationInfoMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(TrainStationInfo.TABLE_NAME);
		undertakeEnterpriseMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(UndertakeEnterpriseInfo.TABLE_NAME);
		trainNumberMap = (Map<String, TrainNumberInfo>) initDataService
				.getEhcacheMapByTableName(TrainNumberInfo.TABLE_NAME);
		for (Map<Integer, String> contextColMap : list) {
			incomeInfo = new IncomeInfo();
			rows++;
			if (contextColMap != null && !contextColMap.isEmpty()) {
				if (contextColMap.get(0).equals("站段")) {
					continue;
				}
			}
			// 1.判断站段是否为空，为空累加错误信息；不为空则到查询缓存数据中是否有该站段信息，没有则进行添加操作
			String stationSectionName = contextColMap.get(0);
			StationSectionInfo stationSectionInfo = null;
			if (stationSectionMap==null) {
				stationSectionMap=new HashMap<String, String>();
			}
			if (stationSectionName != null && !stationSectionName.equals("") && !stationSectionName.equals(" ")) {
				if (!stationSectionMap.containsKey(stationSectionName)) {
					stationSectionInfo=new StationSectionInfo();
					stationSectionInfo.setName(stationSectionName);
					stationSectionInfoDao.saveStationSectionInfo(stationSectionInfo);
					stationSectionMap.put(stationSectionInfo.getName(), stationSectionInfo.getId().toString());
				}
			} else {
				errorCount++;
				str += "第" + rows + "行【站段】信息为空<br/>";
				continue;
			}
			// 2.判断售票站是否为空，为空累加错误信息；不为空则到缓存数据判断是否有该站段信息，没有则进行添加操作
			String ticketStationName = contextColMap.get(1);
			TicketStationInfo ticketStationInfo = null;
			if (ticketStationName != null && !ticketStationName.equals(" ") && !ticketStationName.equals("")) {
				if (ticketStationMap==null) {
					ticketStationMap=new HashMap<String, TicketStationInfo>();
				}
				if (!ticketStationMap.containsKey(ticketStationName)) {
					ticketStationInfo = new TicketStationInfo();
					ticketStationInfo.setName(ticketStationName);
					ticketStationInfo.setStationSectionId(new Long(stationSectionMap.get(stationSectionName)));
					ticketStationInfoDao.saveTicketStationInfo(ticketStationInfo);
					ticketStationMap.put(ticketStationInfo.getName(), ticketStationInfo);
				} else {
					ticketStationInfo=ticketStationMap.get(ticketStationName);
					String stationSectionId=stationSectionMap.get(stationSectionName);
					if (!ticketStationInfo.getStationSectionId().equals(stationSectionId)) {
						ticketStationInfo.setStationSectionId(new Long(stationSectionId));
						ticketStationInfoDao.updateTicketStationInfo(ticketStationInfo);
						ticketStationMap.put(ticketStationInfo.getName(), ticketStationInfo);
					}
				}
			} else {
				errorCount++;
				str += "第" + rows + "行【售票站】信息为空<br/>";
				continue;
			}
			// 3.判断始发站是否为空，为空累加错误信息；不为空则到缓存数据判断是否有该车站信息，没有则进行添加操作
			String startStationName = contextColMap.get(3);
			TrainStationInfo startTrainStation = null;
			if (startStationName != null && !startStationName.equals("") && !startStationName.equals(" ")) {
				if (trainStationInfoMap==null) {
					trainStationInfoMap=new HashMap<String, String>();
				}
				if (!trainStationInfoMap.containsKey(startStationName)) {
					startTrainStation=new TrainStationInfo();
					startTrainStation.setName(startStationName);
					trainStationInfoDao.saveTrainStationInfo(startTrainStation);
					trainStationInfoMap.put(startTrainStation.getName(), startTrainStation.getId().toString());
				}
			} else {
				errorCount++;
				str += "第" + rows + "行【始发站】信息为空<br/>";
				continue;
			}
			// 4.判断终点站是否为空，为空累加错误信息；不为空则到缓存数据判断是否有该车站信息，没有则进行添加操作
			String endStationName = contextColMap.get(4);
			TrainStationInfo endTrainStation = null;
			if (endStationName != null && !endStationName.equals("") && !endStationName.equals(" ")) {
				if (!trainStationInfoMap.containsKey(endStationName)) {
					endTrainStation=new TrainStationInfo();
					endTrainStation.setName(endStationName);
					trainStationInfoDao.saveTrainStationInfo(endTrainStation);
					trainStationInfoMap.put(endTrainStation.getName(), endTrainStation.getId().toString());
				}
			} else {
				errorCount++;
				str += "第" + rows + "行【终到站】信息为空<br/>";
				continue;
			}
			// 5.判断担当企业是否为空，为空累加错误信息；不为空则到缓存数据判断是否有该担当企业信息，没有则进行添加操作
			String uepName = contextColMap.get(5);
			UndertakeEnterpriseInfo uepInfo = null;
			if (uepName != null && !uepName.equals("") && !uepName.equals(" ")) {
				if (undertakeEnterpriseMap==null) {
					undertakeEnterpriseMap=new HashMap<String, String>();
				}
				if (!undertakeEnterpriseMap.containsKey(uepName)) {
					uepInfo=new UndertakeEnterpriseInfo();
					uepInfo.setName(uepName);
					undertakeEnterpriseInfoDao.saveUndertakeEnterpriseInfo(uepInfo);
					undertakeEnterpriseMap.put(uepInfo.getName(), uepInfo.getId().toString());
				}
			} else {
				errorCount++;
				str += "第" + rows + "行【担当企业】信息为空<br/>";
				continue;
			}
			// 6.判断车次是否为空，为空累加错误信息；不为空则到缓存数据判断是否有该车次信息，没有则进行添加操作，有则进行各个属性比对是否一致
			String trainNo = contextColMap.get(2);
			TrainNumberInfo trainNumberInfo = null;
			if (trainNo != null && !trainNo.equals("")) {
				if (trainNumberMap==null) {
					trainNumberMap=new HashMap<String, TrainNumberInfo>();
				}
				if (!trainNumberMap.containsKey(trainNo)) { // 没有此车次就进行保存操作
					trainNumberInfo = new TrainNumberInfo();
					trainNumberInfo.setTrainNo(trainNo);
					trainNumberInfo.setStartStationId(new Long(trainStationInfoMap.get(startStationName)));
					trainNumberInfo.setEndStationId(new Long(trainStationInfoMap.get(endStationName)));
					trainNumberInfo.setUnderEnterId(new Long(undertakeEnterpriseMap.get(uepName)));
					trainNumberInfoDao.saveTrainNumberInfo(trainNumberInfo);
					trainNumberMap.put(trainNumberInfo.getTrainNo(), trainNumberInfo);
				} else { // 有此车次，比对各属性是否一致，不一致则修改车次信息
					trainNumberInfo=trainNumberMap.get(trainNo);
					if (!trainStationInfoMap.get(startStationName).equals(trainNumberInfo.getStartStationId())) {
						trainNumberInfo.setStartStationId(new Long(trainStationInfoMap.get(startStationName)));
						trainNumberInfoDao.updateTrainNumberInfo(trainNumberInfo);
						trainNumberMap.put(trainNumberInfo.getTrainNo(), trainNumberInfo);
					}
					if (!trainStationInfoMap.get(endStationName).equals(trainNumberInfo.getEndStationId())) {
						trainNumberInfo.setEndStationId(new Long(trainStationInfoMap.get(endStationName)));
						trainNumberInfoDao.updateTrainNumberInfo(trainNumberInfo);
						trainNumberMap.put(trainNumberInfo.getTrainNo(), trainNumberInfo);
					}
					if (!undertakeEnterpriseMap.get(uepName).equals(trainNumberInfo.getUnderEnterId())) {
						trainNumberInfo.setUnderEnterId(new Long(undertakeEnterpriseMap.get(uepName)));
						trainNumberInfoDao.updateTrainNumberInfo(trainNumberInfo);
						trainNumberMap.put(trainNumberInfo.getTrainNo(), trainNumberInfo);
					}
				}
			} else {
				errorCount++;
				str += "第" + rows + "行【车次】信息为空<br/>";
				continue;
			}
			String income = contextColMap.get(6);
			if (income != null && !income.equals("") && !income.equals(" ")) {
				incomeInfo.setIncome(Double.parseDouble(income));
			} else {
				incomeInfo.setIncome(0D);
			}
			String peopleCount = contextColMap.get(7);
			if (peopleCount != null && !peopleCount.equals("") && !peopleCount.equals(" ")) {
				incomeInfo.setPeopleCount(new Long(peopleCount));
			} else {
				incomeInfo.setPeopleCount(0L);
			}
			// 保存收入信息
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			incomeInfo.setDataDate(sdf.parse(dataDate));
			incomeInfo.setTicketStationId(ticketStationMap.get(ticketStationName).getId());
			incomeInfo.setTrainNumberId(trainNumberMap.get(trainNo).getId());
			incomeInfoDao.saveIncomeInfo(incomeInfo);
		}
		map.put("errorStr", str);
		map.put("errorCount", errorCount.toString());
		return map;
	}

	

	@Override
	public List<IncomeInfo> getIncomeInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return incomeInfoDao.getIncomeInfoListByCondition(queryRule);
	}

	@Override
	public Map<String, Object> getSumIncomePeopleCountByTicketStationIdAndDate(Long ticketStationId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>(2);
		map.put("income", incomeInfoDao.getSumIncomeByTicketStationIdAndDate(ticketStationId,DateUtil.shortTime(startDate) ,DateUtil.shortTime(endDate)));
		map.put("peopleCount", incomeInfoDao.getSumPeopleCountByTicketStationIdAndDate(ticketStationId, DateUtil.shortTime(startDate) ,DateUtil.shortTime(endDate)));
		return map;
	}

}
