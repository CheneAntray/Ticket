package com.xianqin.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.base.ApplicationDefined;
import com.base.BaseController;
import com.base.ResponseData;
import com.base.ReturnMap;
import com.base.ServiceRespond;
import com.base.ServiceRespondData;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.TrainNumberInfo;
import com.xianqin.domain.TrainStationInfo;
import com.xianqin.domain.UndertakeEnterpriseInfo;
import com.xianqin.service.InitDataService;
import com.xianqin.service.TrainNumberInfoService;
import com.xianqin.view.trainNumber.TrainNumberView;

@RequestMapping("/trainnumber")
@RestController
public class TrainNumberInfoController extends BaseController {

	@Autowired
	private TrainNumberInfoService trainNumberInfoService;
	@Autowired
	private InitDataService initDataService;

	private Map<String, String> trainStationInfoMap = null;
	private Map<String, String> undertakeEnterpriseMap = null;
	private List<TrainNumberInfo> inTrainNumbers = null;
	private List<TrainNumberInfo> notIfnTrainNumbers = null;

	/**
	 * 根据条件查询并分页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryTrainNumberByPage", method = RequestMethod.POST)
	public @ResponseBody ResponseData queryTrainNumberByPage(@RequestBody TrainNumberView trainNumberView)
			throws Exception {
		ServiceRespond res = null;
		ResponseData rpd = null;
		trainStationInfoMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(TrainStationInfo.TABLE_NAME);
		undertakeEnterpriseMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(UndertakeEnterpriseInfo.TABLE_NAME);
		QueryRule queryRule = QueryRule.getInstance();
		try {
			if (trainNumberView.getTitleNo() != null && !trainNumberView.getTitleNo().equals("")) {
				if (trainNumberView.getTitleNo().trim().equals("数字")) {
					queryRule.addSql("TRAIN_NO RLIKE '^[0-9]'");
				} else {
					queryRule.addLike(TrainNumberInfo._trainNo, trainNumberView.getTitleNo().trim() + "%");
				}
			}
			if (trainNumberView.getStartStationId() != null && !trainNumberView.getStartStationId().equals("")) {
				queryRule.addEqual(TrainNumberInfo._startStationId, trainNumberView.getStartStationId());
			}
			if (trainNumberView.getEndStationId() != null && !trainNumberView.getEndStationId().equals("")) {
				queryRule.addEqual(TrainNumberInfo._endStationId, trainNumberView.getEndStationId());
			}
			ReturnMap ret = trainNumberInfoService.queryTrainNumberInfoByPage(queryRule, trainNumberView.getPage(),
					trainNumberView.getRows());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				Page<TrainNumberInfo> page = (Page<TrainNumberInfo>) ret.getObjContext();
				// 循环收入持久化对象集合将持久化对象转换为视图对象
				TrainNumberView trainNumberView2 = null;
				List<TrainNumberView> listTrainNumberView = new ArrayList<TrainNumberView>(page.getResult().size());
				for (TrainNumberInfo trainNumberInfo : page.getResult()) {
					trainNumberView2 = new TrainNumberView();
					for (Map.Entry<String, String> m1 : trainStationInfoMap.entrySet()) {
						if (trainNumberInfo.getStartStationId().toString().equals(m1.getValue())) {
							trainNumberView2.setStartStationName(m1.getKey());
							break;
						}
					}
					for (Map.Entry<String, String> m1 : trainStationInfoMap.entrySet()) {
						if (trainNumberInfo.getEndStationId().toString().equals(m1.getValue())) {
							trainNumberView2.setEndStationName(m1.getKey());
							break;
						}
					}
					for (Map.Entry<String, String> m1 : undertakeEnterpriseMap.entrySet()) {
						if (trainNumberInfo.getUnderEnterId().toString().equals(m1.getValue())) {
							trainNumberView2.setUepName(m1.getKey());
							break;
						}
					}
					trainNumberView2.setId(trainNumberInfo.getId());
					trainNumberView2.setTrainNo(trainNumberInfo.getTrainNo());
					listTrainNumberView.add(trainNumberView2);
				}
				Page<TrainNumberView> page2 = new Page<TrainNumberView>(page.getStart(), page.getTotalCount(),
						page.getPageSize(), listTrainNumberView);
				rpd = ResponseData.ok();
				rpd.putDataValue("page", page2);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("查询车次信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询车次信息失败");
			logger.error("查询车次信息失败", e);
		}
		return rpd;
	}

	/**
	 * 根据车次方向条件查询并分页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryTrainNumberByInDirection", method = RequestMethod.POST)
	public @ResponseBody ResponseData queryTrainNumberByInDirection(@RequestBody TrainNumberView trainNumberView)
			throws Exception {
		ServiceRespond res = null;
		ResponseData rpd = null;
		trainStationInfoMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(TrainStationInfo.TABLE_NAME);
		undertakeEnterpriseMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(UndertakeEnterpriseInfo.TABLE_NAME);
		QueryRule queryRule = QueryRule.getInstance();
		try {
			queryRule.addEqual(TrainNumberInfo._directionId, trainNumberView.getDirectionId());
			if (trainNumberView.getTitleNo() != null && !trainNumberView.getTitleNo().equals("")) {
				if (trainNumberView.getTitleNo().trim().equals("数字")) {
					queryRule.addSql("TRAIN_NO RLIKE '^[0-9]'");
				} else {
					queryRule.addLike(TrainNumberInfo._trainNo, trainNumberView.getTitleNo().trim() + "%");
				}
			}
			if (trainNumberView.getStartStationId() != null && !trainNumberView.getStartStationId().equals("")) {
				queryRule.addEqual(TrainNumberInfo._startStationId, trainNumberView.getStartStationId());
			}
			if (trainNumberView.getEndStationId() != null && !trainNumberView.getEndStationId().equals("")) {
				queryRule.addEqual(TrainNumberInfo._endStationId, trainNumberView.getEndStationId());
			}

			ReturnMap ret = trainNumberInfoService.queryTrainNumberInfoByPage(queryRule, trainNumberView.getPage(),
					trainNumberView.getRows());
			inTrainNumbers = trainNumberInfoService.queryTrainNumberInfoByCondition(queryRule).getListContext();
			res = new ServiceRespond();
			if (ret.isSucc()) {
				Page<TrainNumberInfo> page = (Page<TrainNumberInfo>) ret.getObjContext();
				// 循环收入持久化对象集合将持久化对象转换为视图对象
				TrainNumberView trainNumberView2 = null;
				List<TrainNumberView> listTrainNumberView = new ArrayList<TrainNumberView>(page.getResult().size());
				for (TrainNumberInfo trainNumberInfo : page.getResult()) {
					trainNumberView2 = new TrainNumberView();
					for (Map.Entry<String, String> m1 : trainStationInfoMap.entrySet()) {
						if (trainNumberInfo.getStartStationId().toString().equals(m1.getValue())) {
							trainNumberView2.setStartStationName(m1.getKey());
							break;
						}
					}
					for (Map.Entry<String, String> m1 : trainStationInfoMap.entrySet()) {
						if (trainNumberInfo.getEndStationId().toString().equals(m1.getValue())) {
							trainNumberView2.setEndStationName(m1.getKey());
							break;
						}
					}
					for (Map.Entry<String, String> m1 : undertakeEnterpriseMap.entrySet()) {
						if (trainNumberInfo.getUnderEnterId().toString().equals(m1.getValue())) {
							trainNumberView2.setUepName(m1.getKey());
							break;
						}
					}
					trainNumberView2.setId(trainNumberInfo.getId());
					trainNumberView2.setTrainNo(trainNumberInfo.getTrainNo());
					listTrainNumberView.add(trainNumberView2);
				}
				Page<TrainNumberView> page2 = new Page<TrainNumberView>(page.getStart(), page.getTotalCount(),
						page.getPageSize(), listTrainNumberView);
				rpd = ResponseData.ok();
				rpd.putDataValue("page", page2);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("查询车次信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询车次信息失败");
			logger.error("查询车次信息失败", e);
		}
		return rpd;
	}

	/**
	 * 根据车次方向条件查询没有的车次并分页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryTrainNumberByNotInDirection", method = RequestMethod.POST)
	public @ResponseBody ResponseData queryTrainNumberByNotInDirection(@RequestBody TrainNumberView trainNumberView)
			throws Exception {
		ServiceRespond res = null;
		ResponseData rpd = null;
		trainStationInfoMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(TrainStationInfo.TABLE_NAME);
		undertakeEnterpriseMap = (Map<String, String>) initDataService
				.getEhcacheMapByTableName(UndertakeEnterpriseInfo.TABLE_NAME);
		QueryRule queryRule = QueryRule.getInstance();
		try {
			queryRule.addSql("ifnull(DIRECTION_ID,'0')<>" + trainNumberView.getDirectionId() + "");
			if (trainNumberView.getNotInTitleNo() != null && !trainNumberView.getNotInTitleNo().equals("")) {
				if (trainNumberView.getNotInTitleNo().trim().equals("数字")) {
					queryRule.addSql("TRAIN_NO RLIKE '^[0-9]'");
				} else {
					queryRule.addLike(TrainNumberInfo._trainNo, trainNumberView.getNotInTitleNo().trim() + "%");
				}
			}
			if (trainNumberView.getNotInStartStation() != null && !trainNumberView.getNotInStartStation().equals("")) {
				queryRule.addEqual(TrainNumberInfo._startStationId, trainNumberView.getNotInStartStation());
			}
			if (trainNumberView.getNotInEndStation() != null && !trainNumberView.getNotInEndStation().equals("")) {
				queryRule.addEqual(TrainNumberInfo._endStationId, trainNumberView.getNotInEndStation());
			}

			ReturnMap ret = trainNumberInfoService.queryTrainNumberInfoByPage(queryRule, trainNumberView.getPage(),
					trainNumberView.getRows());
			notIfnTrainNumbers = trainNumberInfoService.queryTrainNumberInfoByCondition(queryRule).getListContext();
			res = new ServiceRespond();
			if (ret.isSucc()) {
				Page<TrainNumberInfo> page = (Page<TrainNumberInfo>) ret.getObjContext();
				// 循环收入持久化对象集合将持久化对象转换为视图对象
				TrainNumberView trainNumberView2 = null;
				List<TrainNumberView> listTrainNumberView = new ArrayList<TrainNumberView>(page.getResult().size());
				for (TrainNumberInfo trainNumberInfo : page.getResult()) {
					trainNumberView2 = new TrainNumberView();
					for (Map.Entry<String, String> m1 : trainStationInfoMap.entrySet()) {
						if (trainNumberInfo.getStartStationId().toString().equals(m1.getValue())) {
							trainNumberView2.setStartStationName(m1.getKey());
							break;
						}
					}
					for (Map.Entry<String, String> m1 : trainStationInfoMap.entrySet()) {
						if (trainNumberInfo.getEndStationId().toString().equals(m1.getValue())) {
							trainNumberView2.setEndStationName(m1.getKey());
							break;
						}
					}
					for (Map.Entry<String, String> m1 : undertakeEnterpriseMap.entrySet()) {
						if (trainNumberInfo.getUnderEnterId().toString().equals(m1.getValue())) {
							trainNumberView2.setUepName(m1.getKey());
							break;
						}
					}
					trainNumberView2.setId(trainNumberInfo.getId());
					trainNumberView2.setTrainNo(trainNumberInfo.getTrainNo());
					listTrainNumberView.add(trainNumberView2);
				}
				Page<TrainNumberView> page2 = new Page<TrainNumberView>(page.getStart(), page.getTotalCount(),
						page.getPageSize(), listTrainNumberView);
				rpd = ResponseData.ok();
				rpd.putDataValue("page", page2);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("查询车次信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询车次信息失败");
			logger.error("查询车次信息失败", e);
		}
		return rpd;
	}

	/**
	 * 根据车次ID修改车次方向 方向已有车次移除或添加操作
	 */
	@RequestMapping(value = "/updateTrainNumberByDirectionId", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond updateTrainNumberByDirectionId(@RequestBody TrainNumberView trainNumberView)
			throws Exception {
		ServiceRespond res = null;
		Long[] trainNumberIds;
		String msg = "";
		try {
			if (trainNumberView.getTrainNumberIds() != null && trainNumberView.getTrainNumberIds().length > 0) {
				trainNumberIds = trainNumberView.getTrainNumberIds();
				if (trainNumberView.getDirectionId() == null) {
					msg = "移除车次";
				} else {
					msg = "添加车次";
				}
			} else {
				if (trainNumberView.getDirectionId() == null) {
					msg = "移除车次";
					trainNumberIds = new Long[inTrainNumbers.size()];
					for (int i = 0; i < inTrainNumbers.size(); i++) {
						trainNumberIds[i] = inTrainNumbers.get(i).getId();
					}
				} else {
					msg = "添加车次";
					trainNumberIds = new Long[notIfnTrainNumbers.size()];
					for (int i = 0; i < notIfnTrainNumbers.size(); i++) {
						trainNumberIds[i] = notIfnTrainNumbers.get(i).getId();
					}
				}

			}
			res = new ServiceRespond();
			ReturnMap ret = trainNumberInfoService.updateTrainNumberByDirectionId(trainNumberView.getDirectionId(),
					trainNumberIds);
			res = new ServiceRespond();
			if (ret.isSucc()) {
				res.setMsg(msg + "成功");
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg(msg + "失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg(msg + "异常");
			logger.error(msg + "异常", e);
		}
		return res;
	}
	
	@RequestMapping(value = "/queryTrainNumberById", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond queryTrainNumberById(@RequestBody TrainNumberView trainNumberView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret=null;
		QueryRule queryRule=null;
		try {
			queryRule=QueryRule.getInstance();
			queryRule.addEqual(TrainNumberInfo._id, trainNumberView.getId());
			ret=trainNumberInfoService.getTrainNumberInfoByCondition(queryRule);
			res=new ServiceRespond();
			if (ret.isSucc()) {
				TrainNumberInfo trainNumberInfo=(TrainNumberInfo) ret.getObjContext();
				ServiceRespondData data=new ServiceRespondData(trainNumberInfo);
				res.setMsg(ret.getMsg());
				res.setData(data);
			}else{
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("查询车次信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询车次信息失败");
			logger.error("查询车次信息失败", e);
		}
		return res;
	}
	
	/*
	 * 修改车次信息
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/editTrainNumberUrl", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond editTrainNumber(@RequestBody TrainNumberView trainNumberView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret=null;
		TrainNumberInfo trainNumberInfo=new TrainNumberInfo();
		trainNumberInfo=trainNumberView.processTrainNumberViewToTrainNumberInfo(trainNumberView, trainNumberInfo);
		try {
			ret=trainNumberInfoService.updateTrainNumberInfo(trainNumberInfo);
			res=new ServiceRespond();
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			}else{
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("修改车次信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("修改车次信息失败");
			logger.error("修改车次信息失败", e);
		}
		return res;
	}
	/*
	 * 添加车次信息
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/addTrainNumberInfo", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond addTrainNumberInfo(@RequestBody TrainNumberView trainNumberView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret=null;
		TrainNumberInfo trainNumberInfo=new TrainNumberInfo();
		trainNumberInfo=trainNumberView.processTrainNumberViewToTrainNumberInfo(trainNumberView, trainNumberInfo);
		try {
			ret=trainNumberInfoService.saveTrainNumberInfo(trainNumberInfo);
			res=new ServiceRespond();
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			}else{
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("添加车次信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("添加车次信息失败");
			logger.error("添加车次信息失败", e);
		}
		return res;
	}

}
