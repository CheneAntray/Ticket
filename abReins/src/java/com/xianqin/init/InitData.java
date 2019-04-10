package com.xianqin.init;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.base.BaseController;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.StationSectionInfoDao;
import com.xianqin.dao.TicketStationInfoDao;
import com.xianqin.dao.TrainNumberInfoDao;
import com.xianqin.dao.TrainStationInfoDao;
import com.xianqin.dao.UndertakeEnterpriseInfoDao;
import com.xianqin.domain.StationSectionInfo;
import com.xianqin.domain.TicketStationInfo;
import com.xianqin.domain.TrainNumberInfo;
import com.xianqin.domain.TrainStationInfo;
import com.xianqin.domain.UndertakeEnterpriseInfo;
import com.xianqin.service.InitDataService;

@Configuration
public class InitData extends BaseController {

	@Autowired
	private InitDataService initDataService;
	@Resource
	private StationSectionInfoDao stationSectionInfoDao;
	@Resource
	private TicketStationInfoDao ticketStationInfoDao;
	@Resource
	private TrainStationInfoDao trainStationInfoDao;
	@Resource
	private UndertakeEnterpriseInfoDao undertakeEnterpriseInfoDao;
	@Resource
	private TrainNumberInfoDao trainNumberInfoDao;

	private List<StationSectionInfo> stationSectionInfolist = null;
	private List<TicketStationInfo> ticketStationList = null;
	private List<TrainStationInfo> trainStationList = null;
	private List<UndertakeEnterpriseInfo> uepList = null;
	private List<TrainNumberInfo> trainNumberList = null;

	@PostConstruct
	public void init() {
		logger.info("------------初始化数据开始-----------------------");
		QueryRule queryRule = QueryRule.getInstance();
		try {
			// 初始化站段信息
			stationSectionInfolist = stationSectionInfoDao.getStationSectionInfoListByCondition(queryRule);
			initDataService.addEhcacheMapByTableName(StationSectionInfo.TABLE_NAME,
					processStationSectionListToMap(stationSectionInfolist));
			// 初始化售票站信息
			ticketStationList = ticketStationInfoDao.getTicketStationInfoListByCondition(queryRule);
			initDataService.addEhcacheMapByTableName(TicketStationInfo.TABLE_NAME,
					processTicketStationListToMap(ticketStationList));
			// 初始化车次到发站信息
			trainStationList = trainStationInfoDao.getTrainStationInfoListByCondition(queryRule);
			initDataService.addEhcacheMapByTableName(TrainStationInfo.TABLE_NAME,
					processTrainStationListToMap(trainStationList));
			// 初始化担当企业信息
			uepList = undertakeEnterpriseInfoDao.getUndertakeEnterpriseInfoListByCondition(queryRule);
			initDataService.addEhcacheMapByTableName(UndertakeEnterpriseInfo.TABLE_NAME, processUepListToMap(uepList));
			// 初始化车次信息
			trainNumberList = trainNumberInfoDao.getTrainNumberInfoListByCondition(queryRule);
			initDataService.addEhcacheMapByTableName(TrainNumberInfo.TABLE_NAME,
					processTrainNumberListToMap(trainNumberList));
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("初始化数据异常:" + e);
		}
		logger.info("------------初始化数据结束-----------------------");	
	}

	// 将站段信息集合转换为map集合
	private Map<String, String> processStationSectionListToMap(List<StationSectionInfo> stationSectionList) {
		Map<String, String> map = null;
		if (stationSectionList != null && !stationSectionList.isEmpty()) {
			map = new HashMap<String, String>(stationSectionList.size());
			for (StationSectionInfo stationSectionInfo : stationSectionList) {
				map.put(stationSectionInfo.getName(), stationSectionInfo.getId().toString());
			}
		}
		return map;
	}

	// 将售票站息集合转换为map集合
	private Map<String, TicketStationInfo> processTicketStationListToMap(List<TicketStationInfo> ticketStationInfo) {
		Map<String, TicketStationInfo> map = null;
		if (ticketStationInfo != null && !ticketStationInfo.isEmpty()) {
			map = new HashMap<String, TicketStationInfo>(ticketStationInfo.size());
			for (TicketStationInfo tsInfo : ticketStationInfo) {
				map.put(tsInfo.getName(), tsInfo);
			}
		}
		return map;
	}

	// 将车次到发站集合转换为map集合
	private Map<String, String> processTrainStationListToMap(List<TrainStationInfo> trainStationInfo) {
		Map<String, String> map = null;
		if (trainStationInfo != null && !trainStationInfo.isEmpty()) {
			map = new HashMap<String, String>(trainStationInfo.size());
			for (TrainStationInfo tsInfo : trainStationInfo) {
				map.put(tsInfo.getName(), tsInfo.getId().toString());
			}
		}
		return map;
	}

	// 将担当企业集合转换为map集合
	private Map<String, String> processUepListToMap(List<UndertakeEnterpriseInfo> uepInfos) {
		Map<String, String> map = null;
		if (uepInfos != null && !uepInfos.isEmpty()) {
			map = new HashMap<String, String>(uepInfos.size());
			for (UndertakeEnterpriseInfo uep : uepInfos) {
				map.put(uep.getName(), uep.getId().toString());
			}
		}
		return map;
	}

	// 将车次信息集合转换为map集合
	private Map<String, TrainNumberInfo> processTrainNumberListToMap(List<TrainNumberInfo> trainNUmbers) {
		Map<String, TrainNumberInfo> map = null;
		if (trainNUmbers != null && !trainNUmbers.isEmpty()) {
			map = new HashMap<String, TrainNumberInfo>(trainNUmbers.size());
			for (TrainNumberInfo trainNumberInfo : trainNUmbers) {
				map.put(trainNumberInfo.getTrainNo(), trainNumberInfo);
			}
		}
		return map;
	}

}