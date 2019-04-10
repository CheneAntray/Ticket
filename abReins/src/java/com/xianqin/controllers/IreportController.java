package com.xianqin.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.base.BaseController;
import com.base.ReturnMap;
import com.base.confread.SystemSetting;
import com.xianqin.domain.ReportDefined;
import com.xianqin.service.ReoprtDataSourceService;
import com.xianqin.service.ReportDefinedService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RequestMapping("/report")
@Controller
public class IreportController extends BaseController {

	@Autowired
	private ReportDefinedService reportDefinedService;

	@Autowired
	private ReoprtDataSourceService reportDataSourceService;

	@Autowired
	private SystemSetting systemSeting;

	@RequestMapping(value = "/model/{reportCode}/{reportName}/{startDate}/{endDate}/{directionCode}/{trainNumber}/{stationId}/{tickStationId}/{outForm}", method = RequestMethod.GET)
	public String report(@PathVariable("reportCode") String reportCode, @PathVariable("reportName") String reportName,
			@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate,
			@PathVariable("directionCode") String directionCode, @PathVariable("trainNumber") String trainNumber,
			@PathVariable("stationId") String stationId, @PathVariable("tickStationId") String tickStationId,
			@PathVariable("outForm") String outForm, Model model, HttpServletRequest request) throws Exception{
		ReturnMap ret = null;
		ReportDefined reportDefined = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("directionCode", directionCode);
		map.put("reportName", reportName);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("trainNumber", trainNumber);
		map.put("stationId", stationId);
		map.put("tickStationId", tickStationId);
		try {
			ret = reportDefinedService.queryReportDefinedByReportCode(reportCode);
			if (ret.isSucc()) {
				reportDefined = (ReportDefined) ret.getObjContext();
				JRDataSource jrDataSource = new JRBeanCollectionDataSource(reportDataSourceService.getListByReportCode(reportCode, map));
				model.addAttribute("url", systemSeting.REPORT_TEMPLET_BASE_PATH + reportDefined.getReportFileName());
				model.addAttribute("format", outForm); // 报表格式
				model.addAttribute("jrMainDataSource", jrDataSource);
				model.addAttribute("session", request.getSession());
			}
		} catch (IllegalStateException e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("没有查询到数据", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询报表异常", e);
		}
		return "iReportView";
	}

}
