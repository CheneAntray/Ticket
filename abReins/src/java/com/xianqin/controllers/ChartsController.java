package com.xianqin.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.base.ServiceRespond;
import com.base.ServiceRespondData;
import com.base.utils.DateUtil;
import com.base.utils.RandomColor;
import com.xianqin.domain.StationSectionInfo;
import com.xianqin.domain.TicketStationInfo;
import com.xianqin.service.InitDataService;
import com.xianqin.service.SpzOfMonthService;
import com.xianqin.service.SpzOfyearService;
import com.xianqin.service.ZdOfmonthService;
import com.xianqin.service.ZdOfyearService;
import com.xianqin.view.charts.LineChartsDataSets;
import com.xianqin.view.charts.LineChartsDataView;
import com.xianqin.view.charts.LineChartsFormData;
import com.xianqin.view.charts.PieChartDataView;
import com.xianqin.view.charts.PieChartFormData;

@RequestMapping("/chart")
@RestController
public class ChartsController extends BaseController {

	@Autowired
	private InitDataService initDataService;

	@Autowired
	private ZdOfyearService zdOfyearService;

	@Autowired
	private ZdOfmonthService zdOfmonthService;

	@Autowired
	private SpzOfyearService spzOfyearService;

	@Autowired
	private SpzOfMonthService spzOfMonthService;

	private Map<String, String> stationSectionMap = null;

	private Map<String, TicketStationInfo> ticketStationMap = null;

	/**
	 * 获取折线图数据
	 * 
	 * @param chartsDataView
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryLineChart", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond queryLineChart(@RequestBody LineChartsFormData lineChartsFormData)
			throws Exception {
		int size = 0; // 用于保存查询对象个数
		ServiceRespond res = new ServiceRespond();
		try {
			// 判断站段是否为空，不为空，则继续判断售票站是否为空，为空则查询所有站段
			if (lineChartsFormData.getStationSectionId() != null
					&& !lineChartsFormData.getStationSectionId().equals("")) {
				// 判断售票站Id是否为空，不为空则查询该售票站的统计数据，为空查询该站段下的售票站的统计信息
				if (lineChartsFormData.getTicketStationId() != null
						&& !lineChartsFormData.getTicketStationId().equals("")) {
					/**
					 * **************************************************************************************************
					 * **************************************************************************************************
					 */
					ticketStationMap = (Map<String, TicketStationInfo>) initDataService
							.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
					String ticketStationName = null;
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getId() == lineChartsFormData.getTicketStationId()) {
							ticketStationName = m.getValue().getName();
							break;
						}
					}
					// 判断起始年份是否为空，为空则查询所有年份数据
					/**
					 * ***************************************************************************************
					 */
					if (lineChartsFormData.getStartdate().equals("")) {
						LineChartsDataSets lineChartsDataSets = null;
						LineChartsDataView lineChartsDataView = new LineChartsDataView();
						// 获取所有年份
						List<Long> yearList = spzOfyearService.getYearList();
						if (yearList != null && yearList.size() > 0) {
							size = yearList.size();
						}else {
							res.setHttpStatus(ApplicationDefined.HTTP_STATUS_FAIL);;
							res.setMsg("没有数据显示");
							return res;
						}
						// 封装x轴数据
						String[] labels = new String[size];
						for (int i = 0; i < size; i++) {
							labels[i] = yearList.get(i) + "年";
						}
						lineChartsDataView.setLabels(labels);
						// 根据售票站封装y轴数据
						Object[] data = null;
						LineChartsDataSets[] datasets = new LineChartsDataSets[1];
						int j = 0;
						String color = null;
						lineChartsDataSets = new LineChartsDataSets();
						// 封装颜色
						color = RandomColor.getRandomRGBColor();
						lineChartsDataSets.setLabel(ticketStationName);
						lineChartsDataSets.setFillColor(color + "0.5)");
						lineChartsDataSets.setStrokeColor(color + "1)");
						lineChartsDataSets.setPointColor(color + "1)");
						lineChartsDataSets.setPointStrokeColor(color + "1)");
						// 封装数据值
						// 判断前端是查票款还是人数或是上座率
						// 1.总票款 2.总人数 3.上座率
						if (lineChartsFormData.getRadio() == 1) {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								data[i] = spzOfyearService.getIncomeBySpzIdAndYear(
										lineChartsFormData.getTicketStationId(), yearList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else if (lineChartsFormData.getRadio() == 2) {
							// 查询数据
							data = new Long[size];
							for (int i = 0; i < size; i++) {
								data[i] = spzOfyearService.getPeopleCountBySpzIdAndYear(
										lineChartsFormData.getTicketStationId(), yearList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								Double income = spzOfyearService.getIncomeBySpzIdAndYear(
										lineChartsFormData.getTicketStationId(), yearList.get(i));
								Long peopleCount = spzOfyearService.getPeopleCountBySpzIdAndYear(
										lineChartsFormData.getTicketStationId(), yearList.get(i));
								if (peopleCount == 0) {
									data[i] = 0.0;
								} else {
									data[i] = income / peopleCount;
								}
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						}
						lineChartsDataView.setDatasets(datasets);
						res.setData(new ServiceRespondData(lineChartsDataView));
					} else if (!lineChartsFormData.getStartdate().equals("")
							&& !lineChartsFormData.getStartmonth().equals("")
							&& !lineChartsFormData.getEndmonth().equals("")) {
						/**
						 * ***************************************************************************************
						 */
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
						Date startDate = sdf
								.parse(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getStartmonth());
						Date endDate = sdf
								.parse(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getEndmonth());
						LineChartsDataSets lineChartsDataSets = null;
						LineChartsDataView lineChartsDataView = new LineChartsDataView();
						// 获取所有月份
						List<String> monthList = new ArrayList<String>();
						Calendar tempStart = Calendar.getInstance();
						tempStart.setTime(startDate);
						tempStart.add(Calendar.MONTH, 1);
						Calendar tempEnd = Calendar.getInstance();
						tempEnd.setTime(endDate);
						// 添加起始月份
						monthList.add(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getStartmonth());
						// 执行中间月份
						while (tempStart.before(tempEnd)) {
							monthList.add(sdf.format(tempStart.getTime()));
							tempStart.add(Calendar.MONTH, 1);
						}
						// 添加终止月份
						monthList.add(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getEndmonth());
						size = monthList.size();
						// 封装x轴数据
						String[] labels = new String[size];
						String[] yearMonth = new String[2];
						for (int i = 0; i < size; i++) {
							yearMonth = monthList.get(i).split("-");
							labels[i] = yearMonth[0] + "年" + yearMonth[1] + "月";
						}
						lineChartsDataView.setLabels(labels);
						// 根据售票站封装y轴数据
						Object[] data = null;
						LineChartsDataSets[] datasets = new LineChartsDataSets[1];
						int j = 0;
						lineChartsDataSets = new LineChartsDataSets();
						// 封装颜色
						String color = RandomColor.getRandomRGBColor();
						lineChartsDataSets.setLabel(ticketStationName);
						lineChartsDataSets.setFillColor(color + "0.5)");
						lineChartsDataSets.setStrokeColor(color + "1)");
						lineChartsDataSets.setPointColor(color + "1)");
						lineChartsDataSets.setPointStrokeColor(color + "1)");
						// 封装数据值
						// 判断前端是查票款还是人数或是上座率
						// 1.总票款 2.总人数 3.上座率
						if (lineChartsFormData.getRadio() == 1) {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								data[i] = spzOfMonthService.getIncomeBySpzIdAndMonth(
										lineChartsFormData.getTicketStationId(), monthList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else if (lineChartsFormData.getRadio() == 2) {
							// 查询数据
							data = new Long[size];
							for (int i = 0; i < size; i++) {
								data[i] = spzOfMonthService.getPeopleCountBySpzIdAndMonth(
										lineChartsFormData.getTicketStationId(), monthList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								Double income = spzOfMonthService.getIncomeBySpzIdAndMonth(
										lineChartsFormData.getTicketStationId(), monthList.get(i));
								Long peopleCount = spzOfMonthService.getPeopleCountBySpzIdAndMonth(
										lineChartsFormData.getTicketStationId(), monthList.get(i));
								if (peopleCount == 0) {
									data[i] = 0.0;
								} else {
									data[i] = income / peopleCount;
								}
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
						}
						lineChartsDataView.setDatasets(datasets);
						res.setData(new ServiceRespondData(lineChartsDataView));
					} else if (!lineChartsFormData.getStartdate().equals("")
							&& !lineChartsFormData.getEnddate().equals("")) {
						/**
						 * *****************************************************************************************************
						 */
						Long startDate = new Long(lineChartsFormData.getStartdate());
						Long endDate = new Long(lineChartsFormData.getEnddate());
						LineChartsDataSets lineChartsDataSets = null;
						LineChartsDataView lineChartsDataView = new LineChartsDataView();
						// 获取所有年份
						List<Long> yearList = new ArrayList<Long>();
						for (Long i = startDate; i <= endDate; i++) {
							yearList.add(i);
						}
						size = yearList.size();
						// 封装x轴数据
						String[] labels = new String[size];
						for (int i = 0; i < size; i++) {
							labels[i] = yearList.get(i) + "年";
						}
						lineChartsDataView.setLabels(labels);
						// 根据售票站封装y轴数据
						Object[] data = null;
						LineChartsDataSets[] datasets = new LineChartsDataSets[1];
						int j = 0;
						lineChartsDataSets = new LineChartsDataSets();
						// 封装颜色
						String color = RandomColor.getRandomRGBColor();
						lineChartsDataSets.setLabel(ticketStationName);
						lineChartsDataSets.setFillColor(color + "0.5)");
						lineChartsDataSets.setStrokeColor(color + "1)");
						lineChartsDataSets.setPointColor(color + "1)");
						lineChartsDataSets.setPointStrokeColor(color + "1)");
						// 封装数据值
						// 判断前端是查票款还是人数或是上座率
						// 1.总票款 2.总人数 3.上座率
						if (lineChartsFormData.getRadio() == 1) {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								data[i] = spzOfyearService.getIncomeBySpzIdAndYear(
										lineChartsFormData.getTicketStationId(), yearList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else if (lineChartsFormData.getRadio() == 2) {
							// 查询数据
							data = new Long[size];
							for (int i = 0; i < size; i++) {
								data[i] = spzOfyearService.getPeopleCountBySpzIdAndYear(
										lineChartsFormData.getTicketStationId(), yearList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								Double income = spzOfyearService.getIncomeBySpzIdAndYear(
										lineChartsFormData.getTicketStationId(), yearList.get(i));
								Long peopleCount = spzOfyearService.getPeopleCountBySpzIdAndYear(
										lineChartsFormData.getTicketStationId(), yearList.get(i));
								if (peopleCount == 0) {
									data[i] = 0.0;
								} else {
									data[i] = income / peopleCount;
								}
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
						}
						lineChartsDataView.setDatasets(datasets);
						res.setData(new ServiceRespondData(lineChartsDataView));
					} else {
						res = new ServiceRespond();
						res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
						res.setMsg("暂不支持该查询方式");
					}
					return res;

				} else {
					/**
					 * **************************************************************************************************
					 * **************************************************************************************************
					 */
					ticketStationMap = (Map<String, TicketStationInfo>) initDataService
							.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
					// 查询该站段的售票站个数
					int ticketStationCount = 0;
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getStationSectionId() == lineChartsFormData.getStationSectionId()) {
							ticketStationCount++;
						}
					}
					// 判断起始年份是否为空，为空则查询所有年份数据
					/**
					 * ***************************************************************************************
					 */
					if (lineChartsFormData.getStartdate().equals("")) {
						LineChartsDataSets lineChartsDataSets = null;
						LineChartsDataView lineChartsDataView = new LineChartsDataView();
						// 获取所有年份
						List<Long> yearList = spzOfyearService.getYearList();
						if (yearList != null && yearList.size() > 0) {
							size = yearList.size();
						}else {
							res.setHttpStatus(ApplicationDefined.HTTP_STATUS_FAIL);;
							res.setMsg("没有数据显示");
							return res;
						}
						// 封装x轴数据
						String[] labels = new String[size];
						for (int i = 0; i < size; i++) {
							labels[i] = yearList.get(i) + "年";
						}
						lineChartsDataView.setLabels(labels);
						// 根据售票站封装y轴数据
						Object[] data = null;
						LineChartsDataSets[] datasets = new LineChartsDataSets[ticketStationCount];
						int j = 0;
						String color = null;
						for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
							if (m.getValue().getStationSectionId() == lineChartsFormData.getStationSectionId()) {
								lineChartsDataSets = new LineChartsDataSets();
								// 封装颜色
								color = RandomColor.getRandomRGBColor();
								lineChartsDataSets.setLabel(m.getKey());
								lineChartsDataSets.setFillColor(color + "0.5)");
								lineChartsDataSets.setStrokeColor(color + "1)");
								lineChartsDataSets.setPointColor(color + "1)");
								lineChartsDataSets.setPointStrokeColor(color + "1)");
								// 封装数据值
								// 判断前端是查票款还是人数或是上座率
								// 1.总票款 2.总人数 3.上座率
								if (lineChartsFormData.getRadio() == 1) {
									// 查询数据
									data = new Double[size];
									for (int i = 0; i < size; i++) {
										data[i] = spzOfyearService.getIncomeBySpzIdAndYear(m.getValue().getId(),
												yearList.get(i));
									}
									lineChartsDataSets.setData(data);
									datasets[j] = lineChartsDataSets;
									j++;
								} else if (lineChartsFormData.getRadio() == 2) {
									// 查询数据
									data = new Long[size];
									for (int i = 0; i < size; i++) {
										data[i] = spzOfyearService.getPeopleCountBySpzIdAndYear(m.getValue().getId(),
												yearList.get(i));
									}
									lineChartsDataSets.setData(data);
									datasets[j] = lineChartsDataSets;
									j++;
								} else {
									// 查询数据
									data = new Double[size];
									for (int i = 0; i < size; i++) {
										Double income = spzOfyearService.getIncomeBySpzIdAndYear(m.getValue().getId(),
												yearList.get(i));
										Long peopleCount = spzOfyearService
												.getPeopleCountBySpzIdAndYear(m.getValue().getId(), yearList.get(i));
										if (peopleCount == 0) {
											data[i] = 0.0;
										} else {
											data[i] = income / peopleCount;
										}
									}
									lineChartsDataSets.setData(data);
									datasets[j] = lineChartsDataSets;
									j++;
								}
							}
						}
						lineChartsDataView.setDatasets(datasets);
						res.setData(new ServiceRespondData(lineChartsDataView));
					} else if (!lineChartsFormData.getStartdate().equals("")
							&& !lineChartsFormData.getStartmonth().equals("")
							&& !lineChartsFormData.getEndmonth().equals("")) {
						/**
						 * ***************************************************************************************
						 */
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
						Date startDate = sdf
								.parse(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getStartmonth());
						Date endDate = sdf
								.parse(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getEndmonth());
						LineChartsDataSets lineChartsDataSets = null;
						LineChartsDataView lineChartsDataView = new LineChartsDataView();
						// 获取所有月份
						List<String> monthList = new ArrayList<String>();
						Calendar tempStart = Calendar.getInstance();
						tempStart.setTime(startDate);
						tempStart.add(Calendar.MONTH, 1);
						Calendar tempEnd = Calendar.getInstance();
						tempEnd.setTime(endDate);
						// 添加起始月份
						monthList.add(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getStartmonth());
						// 执行中间月份
						while (tempStart.before(tempEnd)) {
							monthList.add(sdf.format(tempStart.getTime()));
							tempStart.add(Calendar.MONTH, 1);
						}
						// 添加终止月份
						monthList.add(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getEndmonth());
						size = monthList.size();
						// 封装x轴数据
						String[] labels = new String[size];
						String[] yearMonth = new String[2];
						for (int i = 0; i < size; i++) {
							yearMonth = monthList.get(i).split("-");
							labels[i] = yearMonth[0] + "年" + yearMonth[1] + "月";
						}
						lineChartsDataView.setLabels(labels);
						// 根据售票站封装y轴数据
						Object[] data = null;
						LineChartsDataSets[] datasets = new LineChartsDataSets[ticketStationCount];
						int j = 0;
						for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
							if (m.getValue().getStationSectionId() == lineChartsFormData.getStationSectionId()) {
								lineChartsDataSets = new LineChartsDataSets();
								// 封装颜色
								String color = RandomColor.getRandomRGBColor();
								lineChartsDataSets.setLabel(m.getKey());
								lineChartsDataSets.setFillColor(color + "0.5)");
								lineChartsDataSets.setStrokeColor(color + "1)");
								lineChartsDataSets.setPointColor(color + "1)");
								lineChartsDataSets.setPointStrokeColor(color + "1)");
								// 封装数据值
								// 判断前端是查票款还是人数或是上座率
								// 1.总票款 2.总人数 3.上座率
								if (lineChartsFormData.getRadio() == 1) {
									// 查询数据
									data = new Double[size];
									for (int i = 0; i < size; i++) {
										data[i] = spzOfMonthService.getIncomeBySpzIdAndMonth(m.getValue().getId(),
												monthList.get(i));
									}
									lineChartsDataSets.setData(data);
									datasets[j] = lineChartsDataSets;
									j++;
								} else if (lineChartsFormData.getRadio() == 2) {
									// 查询数据
									data = new Long[size];
									for (int i = 0; i < size; i++) {
										data[i] = spzOfMonthService.getPeopleCountBySpzIdAndMonth(m.getValue().getId(),
												monthList.get(i));
									}
									lineChartsDataSets.setData(data);
									datasets[j] = lineChartsDataSets;
									j++;
								} else {
									// 查询数据
									data = new Double[size];
									for (int i = 0; i < size; i++) {
										Double income = spzOfMonthService.getIncomeBySpzIdAndMonth(m.getValue().getId(),
												monthList.get(i));
										Long peopleCount = spzOfMonthService
												.getPeopleCountBySpzIdAndMonth(m.getValue().getId(), monthList.get(i));
										if (peopleCount == 0) {
											data[i] = 0.0;
										} else {
											data[i] = income / peopleCount;
										}
									}
									lineChartsDataSets.setData(data);
									datasets[j] = lineChartsDataSets;
									j++;
								}
							}
						}
						lineChartsDataView.setDatasets(datasets);
						res.setData(new ServiceRespondData(lineChartsDataView));
					} else if (!lineChartsFormData.getStartdate().equals("")
							&& !lineChartsFormData.getEnddate().equals("")) {
						/**
						 * *****************************************************************************************************
						 */
						Long startDate = new Long(lineChartsFormData.getStartdate());
						Long endDate = new Long(lineChartsFormData.getEnddate());
						LineChartsDataSets lineChartsDataSets = null;
						LineChartsDataView lineChartsDataView = new LineChartsDataView();
						// 获取所有年份
						List<Long> yearList = new ArrayList<Long>();
						for (Long i = startDate; i <= endDate; i++) {
							yearList.add(i);
						}
						size = yearList.size();
						// 封装x轴数据
						String[] labels = new String[size];
						for (int i = 0; i < size; i++) {
							labels[i] = yearList.get(i) + "年";
						}
						lineChartsDataView.setLabels(labels);
						// 根据售票站封装y轴数据
						Object[] data = null;
						LineChartsDataSets[] datasets = new LineChartsDataSets[ticketStationCount];
						int j = 0;
						for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
							if (m.getValue().getStationSectionId() == lineChartsFormData.getStationSectionId()) {
								lineChartsDataSets = new LineChartsDataSets();
								// 封装颜色
								String color = RandomColor.getRandomRGBColor();
								lineChartsDataSets.setLabel(m.getKey());
								lineChartsDataSets.setFillColor(color + "0.5)");
								lineChartsDataSets.setStrokeColor(color + "1)");
								lineChartsDataSets.setPointColor(color + "1)");
								lineChartsDataSets.setPointStrokeColor(color + "1)");
								// 封装数据值
								// 判断前端是查票款还是人数或是上座率
								// 1.总票款 2.总人数 3.上座率
								if (lineChartsFormData.getRadio() == 1) {
									// 查询数据
									data = new Double[size];
									for (int i = 0; i < size; i++) {
										data[i] = spzOfyearService.getIncomeBySpzIdAndYear(m.getValue().getId(),
												yearList.get(i));
									}
									lineChartsDataSets.setData(data);
									datasets[j] = lineChartsDataSets;
									j++;
								} else if (lineChartsFormData.getRadio() == 2) {
									// 查询数据
									data = new Long[size];
									for (int i = 0; i < size; i++) {
										data[i] = spzOfyearService.getPeopleCountBySpzIdAndYear(m.getValue().getId(),
												yearList.get(i));
									}
									lineChartsDataSets.setData(data);
									datasets[j] = lineChartsDataSets;
									j++;
								} else {
									// 查询数据
									data = new Double[size];
									for (int i = 0; i < size; i++) {
										Double income = spzOfyearService.getIncomeBySpzIdAndYear(m.getValue().getId(),
												yearList.get(i));
										Long peopleCount = spzOfyearService
												.getPeopleCountBySpzIdAndYear(m.getValue().getId(), yearList.get(i));
										if (peopleCount == 0) {
											data[i] = 0.0;
										} else {
											data[i] = income / peopleCount;
										}
									}
									lineChartsDataSets.setData(data);
									datasets[j] = lineChartsDataSets;
									j++;
								}
							}
						}
						lineChartsDataView.setDatasets(datasets);
						res.setData(new ServiceRespondData(lineChartsDataView));
					} else {
						res = new ServiceRespond();
						res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
						res.setMsg("暂不支持该查询方式");
					}
				}
				return res;
			} else {
				/**
				 * **************************************************************************************************
				 * **************************************************************************************************
				 */
				stationSectionMap = (Map<String, String>) initDataService
						.getEhcacheMapByTableName(StationSectionInfo.TABLE_NAME);
				// 判断起始年份是否为空，为空则查询所有年份数据
				/**
				 * ***************************************************************************************
				 */
				if (lineChartsFormData.getStartdate().equals("")) {
					LineChartsDataSets lineChartsDataSets = null;
					LineChartsDataView lineChartsDataView = new LineChartsDataView();
					// 获取所有年份
					List<Long> yearList = zdOfyearService.getYearList();
					if (yearList != null && yearList.size() > 0) {
						size = yearList.size();
					}else {
						res.setHttpStatus(ApplicationDefined.HTTP_STATUS_FAIL);;
						res.setMsg("没有数据显示");
						return res;
					}
					// 封装x轴数据
					String[] labels = new String[size];
					for (int i = 0; i < size; i++) {
						labels[i] = yearList.get(i) + "年";
					}
					lineChartsDataView.setLabels(labels);
					// 根据站段封装y轴数据
					Object[] data = null;
					LineChartsDataSets[] datasets = new LineChartsDataSets[stationSectionMap.size()];
					int j = 0;
					for (Map.Entry<String, String> m : stationSectionMap.entrySet()) {
						lineChartsDataSets = new LineChartsDataSets();
						// 封装颜色
						String color = RandomColor.getRandomRGBColor();
						lineChartsDataSets.setLabel(m.getKey());
						lineChartsDataSets.setFillColor(color + "0.5)");
						lineChartsDataSets.setStrokeColor(color + "1)");
						lineChartsDataSets.setPointColor(color + "1)");
						lineChartsDataSets.setPointStrokeColor(color + "1)");
						// 封装数据值
						// 判断前端是查票款还是人数或是上座率
						// 1.总票款 2.总人数 3.上座率
						if (lineChartsFormData.getRadio() == 1) {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								data[i] = zdOfyearService.getIncomeByZdIdAndYear(new Long(m.getValue()),
										yearList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else if (lineChartsFormData.getRadio() == 2) {
							// 查询数据
							data = new Long[size];
							for (int i = 0; i < size; i++) {
								data[i] = zdOfyearService.getPeopleCountByZdIdAndYear(new Long(m.getValue()),
										yearList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								Double income = zdOfyearService.getIncomeByZdIdAndYear(new Long(m.getValue()),
										yearList.get(i));
								Long peopleCount = zdOfyearService.getPeopleCountByZdIdAndYear(new Long(m.getValue()),
										yearList.get(i));
								if (peopleCount == 0) {
									data[i] = 0.0;
								} else {
									data[i] = income / peopleCount;
								}
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						}
					}
					lineChartsDataView.setDatasets(datasets);
					res.setData(new ServiceRespondData(lineChartsDataView));
				} else if (!lineChartsFormData.getStartdate().equals("")
						&& !lineChartsFormData.getStartmonth().equals("")
						&& !lineChartsFormData.getEndmonth().equals("")) {
					/**
					 * ***************************************************************************************
					 */
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					Date startDate = sdf
							.parse(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getStartmonth());
					Date endDate = sdf
							.parse(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getEndmonth());
					LineChartsDataSets lineChartsDataSets = null;
					LineChartsDataView lineChartsDataView = new LineChartsDataView();
					// 获取所有月份
					List<String> monthList = new ArrayList<String>();
					Calendar tempStart = Calendar.getInstance();
					tempStart.setTime(startDate);
					tempStart.add(Calendar.MONTH, 1);
					Calendar tempEnd = Calendar.getInstance();
					tempEnd.setTime(endDate);
					// 添加起始月份
					monthList.add(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getStartmonth());
					// 执行中间月份
					while (tempStart.before(tempEnd)) {
						monthList.add(sdf.format(tempStart.getTime()));
						tempStart.add(Calendar.MONTH, 1);
					}
					// 添加终止月份
					monthList.add(lineChartsFormData.getStartdate() + "-" + lineChartsFormData.getEndmonth());
					size = monthList.size();
					// 封装x轴数据
					String[] labels = new String[size];
					String[] yearMonth = new String[2];
					for (int i = 0; i < size; i++) {
						yearMonth = monthList.get(i).split("-");
						labels[i] = yearMonth[0] + "年" + yearMonth[1] + "月";
					}
					lineChartsDataView.setLabels(labels);
					// 根据站段封装y轴数据
					Object[] data = null;
					LineChartsDataSets[] datasets = new LineChartsDataSets[stationSectionMap.size()];
					int j = 0;
					for (Map.Entry<String, String> m : stationSectionMap.entrySet()) {
						lineChartsDataSets = new LineChartsDataSets();
						// 封装颜色
						String color = RandomColor.getRandomRGBColor();
						lineChartsDataSets.setLabel(m.getKey());
						lineChartsDataSets.setFillColor(color + "0.5)");
						lineChartsDataSets.setStrokeColor(color + "1)");
						lineChartsDataSets.setPointColor(color + "1)");
						lineChartsDataSets.setPointStrokeColor(color + "1)");
						// 封装数据值
						// 判断前端是查票款还是人数或是上座率
						// 1.总票款 2.总人数 3.上座率
						if (lineChartsFormData.getRadio() == 1) {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								data[i] = zdOfmonthService.getIncomeByZdIdAndMonth(new Long(m.getValue()),
										monthList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else if (lineChartsFormData.getRadio() == 2) {
							// 查询数据
							data = new Long[size];
							for (int i = 0; i < size; i++) {
								data[i] = zdOfmonthService.getPeopleCountByZdIdAndMonth(new Long(m.getValue()),
										monthList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								Double income = zdOfmonthService.getIncomeByZdIdAndMonth(new Long(m.getValue()),
										monthList.get(i));
								Long peopleCount = zdOfmonthService.getPeopleCountByZdIdAndMonth(new Long(m.getValue()),
										monthList.get(i));
								if (peopleCount == 0) {
									data[i] = 0.0;
								} else {
									data[i] = income / peopleCount;
								}
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						}
					}
					lineChartsDataView.setDatasets(datasets);
					res.setData(new ServiceRespondData(lineChartsDataView));
				} else if (!lineChartsFormData.getStartdate().equals("")
						&& !lineChartsFormData.getEnddate().equals("")) {
					/**
					 * *****************************************************************************************************
					 */
					Long startDate = new Long(lineChartsFormData.getStartdate());
					Long endDate = new Long(lineChartsFormData.getEnddate());
					LineChartsDataSets lineChartsDataSets = null;
					LineChartsDataView lineChartsDataView = new LineChartsDataView();
					// 获取所有年份
					List<Long> yearList = new ArrayList<Long>();
					for (Long i = startDate; i <= endDate; i++) {
						yearList.add(i);
					}
					size = yearList.size();
					// 封装x轴数据
					String[] labels = new String[size];
					for (int i = 0; i < size; i++) {
						labels[i] = yearList.get(i) + "年";
					}
					lineChartsDataView.setLabels(labels);
					// 根据站段封装y轴数据
					Object[] data = null;
					LineChartsDataSets[] datasets = new LineChartsDataSets[stationSectionMap.size()];
					int j = 0;
					for (Map.Entry<String, String> m : stationSectionMap.entrySet()) {
						lineChartsDataSets = new LineChartsDataSets();
						// 封装颜色
						String color = RandomColor.getRandomRGBColor();
						lineChartsDataSets.setLabel(m.getKey());
						lineChartsDataSets.setFillColor(color + "0.5)");
						lineChartsDataSets.setStrokeColor(color + "1)");
						lineChartsDataSets.setPointColor(color + "1)");
						lineChartsDataSets.setPointStrokeColor(color + "1)");
						// 封装数据值
						// 判断前端是查票款还是人数或是上座率
						// 1.总票款 2.总人数 3.上座率
						if (lineChartsFormData.getRadio() == 1) {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								data[i] = zdOfyearService.getIncomeByZdIdAndYear(new Long(m.getValue()),
										yearList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else if (lineChartsFormData.getRadio() == 2) {
							// 查询数据
							data = new Long[size];
							for (int i = 0; i < size; i++) {
								data[i] = zdOfyearService.getPeopleCountByZdIdAndYear(new Long(m.getValue()),
										yearList.get(i));
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						} else {
							// 查询数据
							data = new Double[size];
							for (int i = 0; i < size; i++) {
								Double income = zdOfyearService.getIncomeByZdIdAndYear(new Long(m.getValue()),
										yearList.get(i));
								Long peopleCount = zdOfyearService.getPeopleCountByZdIdAndYear(new Long(m.getValue()),
										yearList.get(i));
								if (peopleCount == 0) {
									data[i] = 0.0;
								} else {
									data[i] = income / peopleCount;
								}
							}
							lineChartsDataSets.setData(data);
							datasets[j] = lineChartsDataSets;
							j++;
						}
					}
					lineChartsDataView.setDatasets(datasets);
					res.setData(new ServiceRespondData(lineChartsDataView));
				} else {
					res = new ServiceRespond();
					res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
					res.setMsg("暂不支持该查询方式");
				}
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("折线统计异常");
			logger.error("折线统计异常", e);
			return res;
		}
	}

	/**
	 * 获取饼状图数据
	 * 
	 * @param chartsDataView
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryPieChart", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond queryPieChart(@RequestBody PieChartFormData pieChartFormData) throws Exception {
		ServiceRespond res = new ServiceRespond();
		List<PieChartDataView> pieChartDataViews=null;
		try {
			// 如果站段Id不为空则查询该站段下的所有售票站信息
			if (pieChartFormData.getStationSectionId() != null && !pieChartFormData.getStationSectionId().equals("")) {
				ticketStationMap = (Map<String, TicketStationInfo>) initDataService
						.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
				// 查询该站段的售票站个数
				int ticketStationCount = 0;
				for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
					if (m.getValue().getStationSectionId() == pieChartFormData.getStationSectionId()) {
						ticketStationCount++;
					}
				}
				// 判断起始年份是否为空，为空则查询所有年份数据
				/**
				 * ***************************************************************************************
				 */
				if (pieChartFormData.getStartdate().equals("")) {
					PieChartDataView pieChartDataView = null;
					// 获取所有年份
					String date = DateUtil.shortTime(new Date());
					pieChartDataViews = new ArrayList<PieChartDataView>(ticketStationCount);
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getStationSectionId() == pieChartFormData.getStationSectionId()) {
							pieChartDataView = new PieChartDataView();
							// 封装颜色
							pieChartDataView.setColor(RandomColor.getRandomRGBColor()+"0.5)");
							// 封装数据值
							pieChartDataView.setLabel(m.getKey());
							// 判断前端是查票款还是人数或是上座率
							// 1.总票款 2.总人数
							if (pieChartFormData.getRadio() == 1) {
								// 查询数据
								pieChartDataView.setValue(
										spzOfyearService.getSumIncomeBySpzId(m.getValue().getStationSectionId(), date));
							} else if (pieChartFormData.getRadio() == 2) {
								// 查询数据
								pieChartDataView.setValue(spzOfyearService
										.getSumPeopleCountBySpzId(m.getValue().getStationSectionId(), date));
							}
							pieChartDataViews.add(pieChartDataView);
						}
					}
					res.setData(new ServiceRespondData(pieChartDataViews));
				} else if (!pieChartFormData.getStartdate().equals("") && !pieChartFormData.getStartmonth().equals("")
						&& !pieChartFormData.getEndmonth().equals("")) {
					/**
					 * ***************************************************************************************
					 */
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					Date startDate = sdf.parse(pieChartFormData.getStartdate() + "-" + pieChartFormData.getStartmonth());
					Date endDate = sdf.parse(pieChartFormData.getStartdate() + "-" + pieChartFormData.getEndmonth());
					PieChartDataView pieChartDataView = null;
					pieChartDataViews = new ArrayList<PieChartDataView>(ticketStationCount);
					// 获取所有月份
					List<String> monthList = new ArrayList<String>();
					Calendar tempStart = Calendar.getInstance();
					tempStart.setTime(startDate);
					tempStart.add(Calendar.MONTH, 1);
					Calendar tempEnd = Calendar.getInstance();
					tempEnd.setTime(endDate);
					// 添加起始月份
					monthList.add(pieChartFormData.getStartdate() + "-" + pieChartFormData.getStartmonth());
					// 执行中间月份
					while (tempStart.before(tempEnd)) {
						monthList.add(sdf.format(tempStart.getTime()));
						tempStart.add(Calendar.MONTH, 1);
					}
					// 添加终止月份
					monthList.add(pieChartFormData.getStartdate() + "-" + pieChartFormData.getEndmonth());
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getStationSectionId() == pieChartFormData.getStationSectionId()) {
							pieChartDataView = new PieChartDataView();
							// 封装颜色
							pieChartDataView.setColor(RandomColor.getRandomRGBColor()+"0.5)");
							pieChartDataView.setLabel(m.getKey());
							// 封装数据值
							// 判断前端是查票款还是人数或是上座率
							// 1.总票款 2.总人数
							if (pieChartFormData.getRadio() == 1) {
								// 查询数据
								Double income = 0D;
								for (int i = 0; i < monthList.size(); i++) {
									income += spzOfMonthService.getIncomeBySpzIdAndMonth(m.getValue().getId(),
											monthList.get(i));
								}
								pieChartDataView.setValue(income);
							} else if (pieChartFormData.getRadio() == 2) {
								// 查询数据
								Long peopleCount = 0L;
								for (int i = 0; i < monthList.size(); i++) {
									peopleCount = spzOfMonthService.getPeopleCountBySpzIdAndMonth(m.getValue().getId(),
											monthList.get(i));
								}
								pieChartDataView.setValue(peopleCount);
							}
							pieChartDataViews.add(pieChartDataView);
						}
					}
					res.setData(new ServiceRespondData(pieChartDataViews));
				} else if (!pieChartFormData.getStartdate().equals("") && !pieChartFormData.getEnddate().equals("")) {
					/**
					 * *****************************************************************************************************
					 */
					Long startDate = new Long(pieChartFormData.getStartdate());
					Long endDate = new Long(pieChartFormData.getEnddate());
					PieChartDataView pieChartDataView = null;
					pieChartDataViews = new ArrayList<PieChartDataView>(ticketStationCount);
					// 获取所有年份
					List<Long> yearList = new ArrayList<Long>();
					for (Long i = startDate; i <= endDate; i++) {
						yearList.add(i);
					}
					for (Map.Entry<String, TicketStationInfo> m : ticketStationMap.entrySet()) {
						if (m.getValue().getStationSectionId() == pieChartFormData.getStationSectionId()) {
							pieChartDataView = new PieChartDataView();
							// 封装颜色
							pieChartDataView.setColor(RandomColor.getRandomRGBColor()+"0.5)");
							pieChartDataView.setLabel(m.getKey());
							// 封装数据值
							// 判断前端是查票款还是人数或是上座率
							// 1.总票款 2.总人数
							if (pieChartFormData.getRadio() == 1) {
								// 查询数据
								Double income = 0D;
								for (int i = 0; i < yearList.size(); i++) {
									income += spzOfyearService.getIncomeBySpzIdAndYear(m.getValue().getId(),
											yearList.get(i));
								}
								pieChartDataView.setValue(income);
							} else if (pieChartFormData.getRadio() == 2) {
								// 查询数据
								// 查询数据
								Long peopleCount = 0L;
								for (int i = 0; i < yearList.size(); i++) {
									peopleCount += spzOfyearService.getPeopleCountBySpzIdAndYear(m.getValue().getId(),
											yearList.get(i));
								}
								pieChartDataView.setValue(peopleCount);
							}
							pieChartDataViews.add(pieChartDataView);
						}
						res.setData(new ServiceRespondData(pieChartDataViews));
					}
				} else {
					res = new ServiceRespond();
					res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
					res.setMsg("暂不支持该查询方式");
				}
				
			} else{
				stationSectionMap = (Map<String,String>) initDataService
						.getEhcacheMapByTableName(StationSectionInfo.TABLE_NAME);
				// 查询该站段的售票站个数
				int stationSectionCount = stationSectionMap.size();
				// 判断起始年份是否为空，为空则查询所有年份数据
				/**
				 * ***************************************************************************************
				 */
				if (pieChartFormData.getStartdate().equals("")) {
					PieChartDataView pieChartDataView = null;
					// 获取所有年份
					String date = DateUtil.shortTime(new Date());
					pieChartDataViews = new ArrayList<PieChartDataView>(stationSectionCount);
					for (Map.Entry<String, String> m : stationSectionMap.entrySet()) {
							pieChartDataView = new PieChartDataView();
							// 封装颜色
							pieChartDataView.setColor(RandomColor.getRandomRGBColor()+"0.5)");
							// 封装数据值
							pieChartDataView.setLabel(m.getKey());
							// 判断前端是查票款还是人数或是上座率
							// 1.总票款 2.总人数
							if (pieChartFormData.getRadio() == 1) {
								// 查询数据
								pieChartDataView.setValue(
										zdOfyearService.getSumIncomeByZdId(Long.parseLong(m.getValue()), date));
							} else if (pieChartFormData.getRadio() == 2) {
								// 查询数据
								pieChartDataView.setValue(zdOfyearService
										.getSumPeopleCountByZdId(Long.parseLong(m.getValue()), date));
							}
							pieChartDataViews.add(pieChartDataView);
					}
					res.setData(new ServiceRespondData(pieChartDataViews));
				} else if (!pieChartFormData.getStartdate().equals("") && !pieChartFormData.getStartmonth().equals("")
						&& !pieChartFormData.getEndmonth().equals("")) {
					/**
					 * ***************************************************************************************
					 */
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					Date startDate = sdf.parse(pieChartFormData.getStartdate() + "-" + pieChartFormData.getStartmonth());
					Date endDate = sdf.parse(pieChartFormData.getStartdate() + "-" + pieChartFormData.getEndmonth());
					PieChartDataView pieChartDataView = null;
					pieChartDataViews = new ArrayList<PieChartDataView>(stationSectionCount);
					// 获取所有月份
					List<String> monthList = new ArrayList<String>();
					Calendar tempStart = Calendar.getInstance();
					tempStart.setTime(startDate);
					tempStart.add(Calendar.MONTH, 1);
					Calendar tempEnd = Calendar.getInstance();
					tempEnd.setTime(endDate);
					// 添加起始月份
					monthList.add(pieChartFormData.getStartdate() + "-" + pieChartFormData.getStartmonth());
					// 执行中间月份
					while (tempStart.before(tempEnd)) {
						monthList.add(sdf.format(tempStart.getTime()));
						tempStart.add(Calendar.MONTH, 1);
					}
					// 添加终止月份
					monthList.add(pieChartFormData.getStartdate() + "-" + pieChartFormData.getEndmonth());
					for (Map.Entry<String, String> m : stationSectionMap.entrySet()) {
							pieChartDataView = new PieChartDataView();
							// 封装颜色
							pieChartDataView.setColor(RandomColor.getRandomRGBColor()+"0.5)");
							pieChartDataView.setLabel(m.getKey());
							// 封装数据值
							// 判断前端是查票款还是人数或是上座率
							// 1.总票款 2.总人数
							if (pieChartFormData.getRadio() == 1) {
								// 查询数据
								Double income = 0D;
								for (int i = 0; i < monthList.size(); i++) {
									income += zdOfyearService.getSumIncomeByZdId(Long.parseLong(m.getValue()),
											monthList.get(i));
								}
								pieChartDataView.setValue(income);
							} else if (pieChartFormData.getRadio() == 2) {
								// 查询数据
								Long peopleCount = 0L;
								for (int i = 0; i < monthList.size(); i++) {
									peopleCount = zdOfyearService.getSumPeopleCountByZdId(Long.parseLong(m.getValue()),
											monthList.get(i));
								}
								pieChartDataView.setValue(peopleCount);
							}
							pieChartDataViews.add(pieChartDataView);
					}
					res.setData(new ServiceRespondData(pieChartDataViews));
				} else if (!pieChartFormData.getStartdate().equals("") && !pieChartFormData.getEnddate().equals("")) {
					/**
					 * *****************************************************************************************************
					 */
					Long startDate = new Long(pieChartFormData.getStartdate());
					Long endDate = new Long(pieChartFormData.getEnddate());
					PieChartDataView pieChartDataView = null;
					pieChartDataViews = new ArrayList<PieChartDataView>(stationSectionCount);
					// 获取所有年份
					List<Long> yearList = new ArrayList<Long>();
					for (Long i = startDate; i <= endDate; i++) {
						yearList.add(i);
					}
					for (Map.Entry<String, String> m : stationSectionMap.entrySet()) {
							pieChartDataView = new PieChartDataView();
							// 封装颜色
							pieChartDataView.setColor(RandomColor.getRandomRGBColor()+"0.5)");
							pieChartDataView.setLabel(m.getKey());
							// 封装数据值
							// 判断前端是查票款还是人数或是上座率
							// 1.总票款 2.总人数
							if (pieChartFormData.getRadio() == 1) {
								// 查询数据
								Double income = 0D;
								for (int i = 0; i < yearList.size(); i++) {
									income += zdOfyearService.getIncomeByZdIdAndYear(Long.parseLong(m.getValue()),
											yearList.get(i));
								}
								pieChartDataView.setValue(income);
							} else if (pieChartFormData.getRadio() == 2) {
								// 查询数据
								// 查询数据
								Long peopleCount = 0L;
								for (int i = 0; i < yearList.size(); i++) {
									peopleCount += zdOfyearService.getPeopleCountByZdIdAndYear(Long.parseLong(m.getValue()),
											yearList.get(i));
								}
								pieChartDataView.setValue(peopleCount);
							}
							pieChartDataViews.add(pieChartDataView);
						res.setData(new ServiceRespondData(pieChartDataViews));
					}
				} else {
					res = new ServiceRespond();
					res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
					res.setMsg("暂不支持该查询方式");
				}
			}
			//判断数据是否为空
		    Double flag=0D;
		    Long flag1=0L;
			for (PieChartDataView pieChartDataView : pieChartDataViews) {
				if (pieChartDataView.getValue() instanceof Double) {
					flag+=Double.parseDouble(pieChartDataView.getValue().toString());
					flag1=1L;
				}
				if (pieChartDataView.getValue() instanceof Long) {
					flag1+=Long.parseLong(pieChartDataView.getValue().toString());
					flag=1D;
				}
			}
			if (flag==0 || flag1==0) {
				res.setHttpStatus(ApplicationDefined.HTTP_STATUS_FAIL);;
				res.setMsg("没有数据显示");
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setHttpStatus(ApplicationDefined.HTTP_STATUS_FAIL);
			res.setMsg("饼图统计异常");
			logger.error("饼图统计异常", e);
			return res;
		}
	}

}
