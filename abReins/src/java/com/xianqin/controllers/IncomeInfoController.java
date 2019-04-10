package com.xianqin.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.base.ApplicationDefined;
import com.base.BaseController;
import com.base.ResponseData;
import com.base.ReturnMap;
import com.base.ServiceRespond;
import com.base.ServiceRespondData;
import com.base.confread.SystemSetting;
import com.base.utils.ExcelUtils;
import com.base.utils.RandomColor;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.IncomeInfo;
import com.xianqin.domain.StationSectionInfo;
import com.xianqin.domain.TicketStationInfo;
import com.xianqin.domain.TrainNumberInfo;
import com.xianqin.domain.TrainStationInfo;
import com.xianqin.domain.UndertakeEnterpriseInfo;
import com.xianqin.service.IncomeInfoService;
import com.xianqin.service.InitDataService;
import com.xianqin.view.charts.LineChartsDataSets;
import com.xianqin.view.charts.LineChartsDataView;
import com.xianqin.view.income.IncomeView;

@RequestMapping("/income")
@RestController
public class IncomeInfoController extends BaseController {
	/**
	 * 系统公用配置文件
	 */
	@Autowired
	private SystemSetting systemSetting;

	@Autowired
	private IncomeInfoService incomeInfoService;
	@Autowired
	private InitDataService initDataService;

	private Map<String, String> stationSectionMap = null;
	private Map<String, TicketStationInfo> ticketStationMap = null;
	private Map<String, String> trainStationInfoMap = null;
	private Map<String, String> undertakeEnterpriseMap = null;
	private Map<String, TrainNumberInfo> trainNumberMap = null;

	/**
	 * 根据条件查询并分页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryIncomeByPage", method = RequestMethod.POST)
	public @ResponseBody ResponseData queryIncomeByPage(@RequestBody IncomeView incomeView) throws Exception {
		ServiceRespond res = null;
		ResponseData rpd = null;
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
		QueryRule queryRule = QueryRule.getInstance();
		try {
			// 保存前端查询条件传回的站段Id
			String stationSectionId = null;
			// 保存前端查询条件传回的担当企业Id
			String uepId = null;
			// 用于保存售票站Id
			List<Long> ticketStationIdList = null;
			// 用于保存车次Id
			List<Long> trainNoIdList = null;
			if (incomeView.getStationSectionId() != null && !incomeView.getStationSectionId().equals("")) {
				stationSectionId = incomeView.getStationSectionId();
				ticketStationIdList = new ArrayList<Long>();
				// 获取该站段下的售票站Id
				for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
					if (m.getValue().getStationSectionId().toString().equals(stationSectionId)) {
						ticketStationIdList.add(m.getValue().getId());
					}
				}
				// 放入queryRule对象进行条件查询
				queryRule.addIn(IncomeInfo._ticketStationId, ticketStationIdList);
			}
			if (incomeView.getTicketStationId() != null && !incomeView.getTicketStationId().equals("")) {
				queryRule.addEqual(IncomeInfo._ticketStationId, Long.parseLong(incomeView.getTicketStationId()));
			}
			if (incomeView.getUepId() != null && !incomeView.getUepId().equals("")) {
				uepId = incomeView.getUepId();
				trainNoIdList = new ArrayList<Long>();
				// 获取该担当企业下的车次Id
				for (Map.Entry<String, TrainNumberInfo> m : trainNumberMap.entrySet()) {
					if (m.getValue().getUnderEnterId().toString().equals(uepId)) {
						trainNoIdList.add(m.getValue().getId());
					}
				}
				// 放入queryRule对象进行条件查询
				queryRule.addIn(IncomeInfo._trainNumberId, trainNoIdList);
			}
			// 根据前端传回的时间参数做条件判断，1起期不为空，止期不为空则查询起期至止期。2.起期不为空，止期为空则查询起期至今天，3起期为空，止期不为空则查询止期之前的数据
			if (incomeView.getStartdate() != null && !incomeView.getStartdate().equals("")) {
				if (incomeView.getEnddate() != null && !incomeView.getEnddate().equals("")) {
					Date[] dates = { incomeView.getStartdate(), incomeView.getEnddate() };
					queryRule.addBetween(IncomeInfo._dataDate, dates);
				} else {
					Date[] dates = { incomeView.getStartdate(), new Date() };
					queryRule.addBetween(IncomeInfo._dataDate, dates);
				}
			} else {
				if (incomeView.getEnddate() != null && !incomeView.getEnddate().equals("")) {
					queryRule.addLessEqual(IncomeInfo._dataDate, incomeView.getEnddate());
				}
			}
			ReturnMap ret = incomeInfoService.queryIncomeInfoByPage(queryRule, incomeView.getPage(),
					incomeView.getRows());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				Page<IncomeInfo> page = (Page<IncomeInfo>) ret.getObjContext();
				// 循环收入持久化对象集合将持久化对象转换为视图对象
				IncomeView incomeView2 = null;
				List<IncomeView> listIncomView = new ArrayList<IncomeView>(page.getResult().size());
				for (IncomeInfo incomeInfo : page.getResult()) {
					incomeView2 = new IncomeView();
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getId().equals(incomeInfo.getTicketStationId())) {
							incomeView2.setTicketStationName(m.getValue().getName());
							for (Map.Entry<String, String> m1 : stationSectionMap.entrySet()) {
								if (m1.getValue().equals(m.getValue().getStationSectionId().toString())) {
									incomeView2.setStationSectionName(m1.getKey());
									break;
								}
							}
							break;
						}
					}
					for (Map.Entry<String, TrainNumberInfo> m : trainNumberMap.entrySet()) {
						if (incomeInfo.getTrainNumberId().equals(m.getValue().getId())) {
							incomeView2.setTrainNo(m.getValue().getTrainNo());
							for (Map.Entry<String, String> m1 : trainStationInfoMap.entrySet()) {
								if (m.getValue().getStartStationId().toString().equals(m1.getValue())) {
									incomeView2.setStartStation(m1.getKey());
									break;
								}
							}
							for (Map.Entry<String, String> m1 : trainStationInfoMap.entrySet()) {
								if (m.getValue().getEndStationId().toString().equals(m1.getValue())) {
									incomeView2.setEndStation(m1.getKey());
									break;
								}
							}
							for (Map.Entry<String, String> m1 : undertakeEnterpriseMap.entrySet()) {
								if (m.getValue().getUnderEnterId().toString().equals(m1.getValue())) {
									incomeView2.setUepName(m1.getKey());
									break;
								}
							}
							break;
						}
					}
					incomeView2.setId(incomeInfo.getId());
					incomeView2.setIncome(incomeInfo.getIncome());
					incomeView2.setPeopleCount(incomeInfo.getPeopleCount());
					incomeView2.setDataDate(incomeInfo.getDataDate());
					listIncomView.add(incomeView2);
				}
				Page<IncomeView> page2 = new Page<IncomeView>(page.getStart(), page.getTotalCount(), page.getPageSize(),
						listIncomView);
				rpd = ResponseData.ok();
				rpd.putDataValue("page", page2);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("查询收入信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询收入异常");
			logger.error("查询收入信息发生异常", e);
		}
		return rpd;
	}

	/**
	 * 根据站段Id查询该站段下的各个售票站的总票款，总票数及票率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryIncomeTotalGroupByTicketStationId", method = RequestMethod.POST)
	public @ResponseBody ResponseData queryIncomeTotalGroupByTicketStationId(@RequestBody IncomeView incomeView)
			throws Exception {
		ResponseData rpd = null;
		ticketStationMap = (Map<String, TicketStationInfo>) initDataService
				.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
		List<IncomeView> incomeViews = null;
		IncomeView incomeView2 = null;
		DecimalFormat df = new DecimalFormat("######0.00");
		try {
			// 保存前端查询条件传回的站段Id
			String stationSectionId = null;
			if (incomeView.getStationSectionId() != null && !incomeView.getStationSectionId().equals("")) {
				stationSectionId = incomeView.getStationSectionId();
				if (incomeView.getTicketStationId() != null && !incomeView.getTicketStationId().equals("")) {
					incomeViews = new ArrayList<IncomeView>();
					incomeView2 = new IncomeView();
					incomeView2.setTicketStationId(incomeView.getTicketStationId());
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getId().toString().equals(incomeView.getTicketStationId())) {
							incomeView2.setTicketStationName(m.getKey());
						}
					}
				    System.out.println(incomeView2.getTicketStationName());
					Map<String, Object> map = incomeInfoService.getSumIncomePeopleCountByTicketStationIdAndDate(
							new Long(incomeView.getTicketStationId()), incomeView.getStartdate(),
							incomeView.getEnddate());
					Double income = Double.parseDouble((map.get("income").toString()));
					incomeView2.setIncome(income);
					Long peopleCount = Long.parseLong((map.get("peopleCount").toString()));
					incomeView2.setPeopleCount(peopleCount);
					if (peopleCount == 0) {
						incomeView2.setTicketRate(df.format(0));
					} else {
						incomeView2.setTicketRate(df.format(income / peopleCount));
					}
					incomeViews.add(incomeView2);
				} else {
					// 获取该站段下的售票站Id
					incomeViews = new ArrayList<IncomeView>();
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getStationSectionId().toString().equals(stationSectionId)) {
							incomeView2 = new IncomeView();
							incomeView2.setTicketStationId(m.getValue().getId().toString());
							incomeView2.setTicketStationName(m.getValue().getName());
							Map<String, Object> map = incomeInfoService.getSumIncomePeopleCountByTicketStationIdAndDate(
									m.getValue().getId(), incomeView.getStartdate(), incomeView.getEnddate());
							Double income = Double.parseDouble((map.get("income").toString()));
							incomeView2.setIncome(income);
							Long peopleCount = Long.parseLong((map.get("peopleCount").toString()));
							incomeView2.setPeopleCount(peopleCount);
							if (peopleCount == 0) {
								incomeView2.setTicketRate(df.format(0));
							} else {
								incomeView2.setTicketRate(df.format(income / peopleCount));
							}
							incomeViews.add(incomeView2);
						}
					}
				}
			} else {
				incomeViews = new ArrayList<IncomeView>();
				for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
					incomeView2 = new IncomeView();
					incomeView2.setTicketStationId(m.getValue().getId().toString());
					incomeView2.setTicketStationName(m.getValue().getName());
					Map<String, Object> map = incomeInfoService.getSumIncomePeopleCountByTicketStationIdAndDate(
							m.getValue().getId(), incomeView.getStartdate(), incomeView.getEnddate());
					Double income = Double.parseDouble((map.get("income").toString()));
					incomeView2.setIncome(income);
					Long peopleCount = Long.parseLong((map.get("peopleCount").toString()));
					incomeView2.setPeopleCount(peopleCount);
					if (peopleCount == 0) {
						incomeView2.setTicketRate(df.format(0));
					} else {
						incomeView2.setTicketRate(df.format(income / peopleCount));
					}
					incomeViews.add(incomeView2);
				}
			}
			Page<IncomeView> page = new Page<IncomeView>(1, incomeViews.size(), 500, incomeViews);
			rpd = ResponseData.ok();
			rpd.putDataValue("page", page);
		} catch (Exception e) {
			// TODO: handle exception
			// res=new ServiceRespond();
			// res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			// res.setMsg("查询总收入异常");
			logger.error("查询总收入信息发生异常", e);
		}
		return rpd;
	}

	/**
	 * 根据站段Id查询该站段下的总票款，总票数及票率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryIncomeTotalByStationSectionId", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond queryIncomeTotalByStationSectionId(@RequestBody IncomeView incomeView)
			throws Exception {
		ServiceRespond res = null;
		ticketStationMap = (Map<String, TicketStationInfo>) initDataService
				.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
		QueryRule queryRule = QueryRule.getInstance();
		try {
			// 保存前端查询条件传回的站段Id
			String stationSectionId = null;
			// 用于保存售票站Id
			List<Long> ticketStationIdList = null;
			if (incomeView.getStationSectionId() != null && !incomeView.getStationSectionId().equals("")) {
				stationSectionId = incomeView.getStationSectionId();
				ticketStationIdList = new ArrayList<Long>();
				// 获取该站段下的售票站Id
				for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
					if (m.getValue().getStationSectionId().toString().equals(stationSectionId)) {
						ticketStationIdList.add(m.getValue().getId());
					}
				}
				// 放入queryRule对象进行条件查询
				queryRule.addIn(IncomeInfo._ticketStationId, ticketStationIdList);
			}
			// 根据前端传回的时间参数做条件判断，1起期不为空，止期不为空则查询起期至止期。2.起期不为空，止期为空则查询起期至今天，3起期为空，止期不为空则查询止期之前的数据
			if (incomeView.getStartdate() != null && !incomeView.getStartdate().equals("")) {
				if (incomeView.getEnddate() != null && !incomeView.getEnddate().equals("")) {
					Date[] dates = { incomeView.getStartdate(), incomeView.getEnddate() };
					queryRule.addBetween(IncomeInfo._dataDate, dates);
				} else {
					Date[] dates = { incomeView.getStartdate(), new Date() };
					queryRule.addBetween(IncomeInfo._dataDate, dates);
				}
			} else {
				if (incomeView.getEnddate() != null && !incomeView.getEnddate().equals("")) {
					queryRule.addLessEqual(IncomeInfo._dataDate, incomeView.getEnddate());
				}
			}
			List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeInfoListByCondition(queryRule);
			double totalIncom = 0;
			Long totalPeopleCount = new Long("0");
			for (IncomeInfo incomeInfo : incomeInfos) {
				totalIncom += incomeInfo.getIncome();
				totalPeopleCount += incomeInfo.getPeopleCount();
			}
			IncomeView incomeView2 = new IncomeView();
			incomeView2.setTotalIncome(totalIncom);
			incomeView2.setTotalPeopleCount(totalPeopleCount);
			res = new ServiceRespond();
			res.setData(new ServiceRespondData(incomeView2));
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询总收入异常");
			logger.error("查询总收入信息发生异常", e);
		}
		return res;
	}

	/**
	 * 导入数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/intoIncome", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond intoIncome(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "date", required = false) String date, HttpServletRequest request, ModelMap model)
			throws Exception {
		ServiceRespond res = new ServiceRespond();
		String fileName = file.getOriginalFilename();
		String[] splitFileName = fileName.split("[.]");
		fileName = splitFileName[0] + "-" + date + "." + splitFileName[1];
		File targetFile = new File(systemSetting.UPLOAD_FILE_TEMP_PATH, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			Integer totalCount = 0; // 总条数
			Integer successCount = 0; // 成功条数
			Integer errorCount = 0; // 失败条数
			String errorStr = "";// 失败的原因
			String str = "";
			XSSFWorkbook wb = null;
			HSSFWorkbook hwb = null;
			Map<String, String> map = new HashMap<String, String>(2);
			String[] name = fileName.split("[.]");
			if (name[1].equals("xlsx")) {
				wb = new XSSFWorkbook(new FileInputStream(systemSetting.UPLOAD_FILE_TEMP_PATH + fileName));
				int sheetIndex = wb.getNumberOfSheets();
				str += "共" + sheetIndex + "个工作簿<br/>";
				for (int i = 0; i < 1; i++) {
					errorCount = 0;
					List<Map<Integer, String>> list = ExcelUtils
							.get2007ExcelContextByFileSheetIndex(systemSetting.UPLOAD_FILE_TEMP_PATH, fileName, i);
					if (list != null && list.size() > 0) {
						totalCount = list.size() - 1;
						map = incomeInfoService.saveBatch(list, date);
						errorCount = Integer.parseInt(map.get("errorCount"));
						errorStr = map.get("errorStr");
					} else {
						totalCount = 0;
					}
					successCount = totalCount - errorCount;
					str += "第" + (i) + "个工作簿共" + totalCount + "条数据,成功" + successCount + "条,失败" + errorCount + "条<br/>";
					if (errorCount > 0) {
						// str += "失败原因如下：<br/>" + errorStr;
					}
				}
			} else {
				hwb = new HSSFWorkbook(new FileInputStream(systemSetting.UPLOAD_FILE_TEMP_PATH + fileName));
				int sheetIndex = hwb.getNumberOfSheets();
				str += "共" + sheetIndex + "个工作簿<br/>";
				for (int i = 3; i < 4; i++) {
					errorCount = 0;
					List<Map<Integer, String>> list = ExcelUtils
							.get2003ExcelContextByFileSheetIndex(systemSetting.UPLOAD_FILE_TEMP_PATH, fileName, i);
					if (list != null && list.size() > 0) {
						totalCount = list.size() - 1;
						map = incomeInfoService.saveBatch(list, date);
						errorCount = Integer.parseInt(map.get("errorCount"));
						errorStr = map.get("errorStr");
					} else {
						totalCount = 0;
					}
					successCount = totalCount - errorCount;
					str += "第" + (i) + "个工作簿共" + totalCount + "条数据,成功" + successCount + "条,失败" + errorCount + "条<br/>";
					if (errorCount > 0) {
						// str += "失败原因如下：<br/>" + errorStr;
					}
				}
			}
			res.setMsg(str);
		} catch (Exception e) {
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("导入Excel异常,请联系系统管理员");
			logger.debug("导入Excel发生异常:" + e.getMessage());
		}
		return res;
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond test() throws Exception {
		ServiceRespond res = new ServiceRespond();
		LineChartsDataSets lineChartsDataSets = new LineChartsDataSets();
		LineChartsDataView lineChartsDataView = new LineChartsDataView();
		String[] labels = new String[5];
		labels[0] = "1月";
		labels[1] = "2月";
		labels[2] = "3月";
		labels[3] = "4月";
		labels[4] = "5月";
		lineChartsDataView.setLabels(labels);
		lineChartsDataSets.setFillColor(RandomColor.getRandomColor());
		lineChartsDataSets.setStrokeColor(RandomColor.getRandomColor());
		lineChartsDataSets.setPointColor(RandomColor.getRandomColor());
		lineChartsDataSets.setPointStrokeColor(RandomColor.getRandomColor());
		Object[] data = new Integer[5];
		data[0] = 100;
		data[1] = 100;
		data[2] = 100;
		data[3] = 100;
		data[4] = 100;
		lineChartsDataSets.setData(data);
		LineChartsDataSets lineChartsDataSets1 = new LineChartsDataSets();
		lineChartsDataSets1.setFillColor(RandomColor.getRandomColor());
		lineChartsDataSets1.setStrokeColor(RandomColor.getRandomColor());
		lineChartsDataSets1.setPointColor(RandomColor.getRandomColor());
		lineChartsDataSets1.setPointStrokeColor(RandomColor.getRandomColor());
		Object[] data1 = new Integer[5];
		data1[0] = 100;
		data1[1] = 100;
		data1[2] = 100;
		data1[3] = 100;
		data1[4] = 100;
		LineChartsDataSets[] datasets = new LineChartsDataSets[2];
		datasets[0] = lineChartsDataSets;
		datasets[1] = lineChartsDataSets1;
		lineChartsDataView.setDatasets(datasets);
		res.setData(new ServiceRespondData(lineChartsDataView));
		return res;
	}
}
