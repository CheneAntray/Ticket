package com.xianqin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.base.ReturnMap;
import com.base.ServiceRespond;
import com.base.ServiceRespondData;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.StationSectionInfo;
import com.xianqin.domain.TicketStationInfo;
import com.xianqin.domain.TrainDirection;
import com.xianqin.domain.TrainStationInfo;
import com.xianqin.domain.UndertakeEnterpriseInfo;
import com.xianqin.service.InitDataService;
import com.xianqin.service.ReportDefinedService;
import com.xianqin.service.TrainDirectionService;
@RequestMapping("/querySelectData")
@RestController
public class SelectDataController extends BaseController {
	@Autowired
	private InitDataService initDataService;
	
	@Autowired
	private TrainDirectionService trainDirectionService;
	
	@Autowired
	private ReportDefinedService reportDefinedService;
	
	private Map<String, List<?>> map=null;
	/**
	 * 获取收入信息页面下拉框绑定值
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySelectData", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond querySelectData() throws Exception {
		map=new HashMap<String, List<?>>(3);
		Map<String, ?> dataMap=null;
		ServiceRespond res = new ServiceRespond();
		try {
			//获取站段
			dataMap=initDataService.getEhcacheMapByTableName(StationSectionInfo.TABLE_NAME);
			List<StationSectionInfo> stationSectionInfos=new ArrayList<StationSectionInfo>(dataMap.size());
			StationSectionInfo ss=null;
			for (String item : dataMap.keySet()) {
				ss=new StationSectionInfo();
				ss.setId(Long.valueOf(String.valueOf(dataMap.get(item))));
				ss.setName(item);
				stationSectionInfos.add(ss);
			}
			map.put("stationSection", stationSectionInfos);
			//获取售票站
			dataMap=initDataService.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
			List<TicketStationInfo> ticketStationInfos=new ArrayList<TicketStationInfo>(dataMap.size());
			TicketStationInfo ts=null;
			for (String item :dataMap.keySet()) {
				ts=(TicketStationInfo) dataMap.get(item);
				ticketStationInfos.add(ts);
			}
			map.put("ticketStation",ticketStationInfos);
			//获取担当企业
			dataMap=initDataService.getEhcacheMapByTableName(UndertakeEnterpriseInfo.TABLE_NAME);
			List<UndertakeEnterpriseInfo> ueps=new ArrayList<UndertakeEnterpriseInfo>(dataMap.size());
			UndertakeEnterpriseInfo uep=null;
			for (String item : dataMap.keySet()) {
				uep=new UndertakeEnterpriseInfo();
				uep.setId(Long.valueOf(String.valueOf(dataMap.get(item))));
				uep.setName(item);
				ueps.add(uep);
			}
			map.put("uep", ueps);
			res.setData(new ServiceRespondData(map));			
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询下拉框数据异常"+e);
			logger.debug("查询下拉框数据异常"+e);
		}
		return res;
	}
	
	/**
	 * 获取收入页面站段和售票站联动下拉框绑定值
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryTicketListByStationSectionId", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond queryTicketListByStationSectionId(@RequestBody String stationSectionId) throws Exception {
		ServiceRespond res = new ServiceRespond();
		try {
			map=new HashMap<String, List<?>>(1);
			Map<String, TicketStationInfo> dataMap=null;
			//获取售票站
			dataMap=(Map<String, TicketStationInfo>) initDataService.getEhcacheMapByTableName(TicketStationInfo.TABLE_NAME);
			List<TicketStationInfo> ticketStationInfos=null;
			TicketStationInfo ts=null;
			if (!stationSectionId.equals("null")) {
				ticketStationInfos=new ArrayList<TicketStationInfo>();
				for (Map.Entry<String, TicketStationInfo> m :dataMap.entrySet())  {
		            if (m.getValue().getStationSectionId().toString().equals(stationSectionId)) {
		            	ts=new TicketStationInfo();
		            	ts.setId(m.getValue().getId());
		            	ts.setName(m.getValue().getName());
		            	ticketStationInfos.add(ts);
					}
		        }
			}else {
				ticketStationInfos=new ArrayList<TicketStationInfo>(dataMap.entrySet().size());
				for (Map.Entry<String, TicketStationInfo> m :dataMap.entrySet())  {
		            	ts=new TicketStationInfo();
		            	ts.setId(m.getValue().getId());
		            	ts.setName(m.getValue().getName());
		            	ticketStationInfos.add(ts);
		        }
			}
			map.put("ticketStation",ticketStationInfos);
			res.setData(new ServiceRespondData(map));
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			logger.debug("下拉框联动异常"+e);
			e.printStackTrace();
			res.setMsg("查询下拉框数据异常"+e);
		}
		return res;
	}
	
	
	
	/**
	 * 获取车次管理页面起始站和终到站下拉框绑定值
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTrainStationSelectData", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond queryTrainStationSelectData() throws Exception {
		List<TrainStationInfo> trainStationInfos=null;
		Map<String, ?> dataMap=null;
		ServiceRespond res = new ServiceRespond();
		try {
			//获取车站信息
			dataMap=initDataService.getEhcacheMapByTableName(TrainStationInfo.TABLE_NAME);
			trainStationInfos =new ArrayList<TrainStationInfo>(dataMap.size());
			TrainStationInfo trainStationInfo=null;
			for (String item : dataMap.keySet()) {
				trainStationInfo=new TrainStationInfo();
				trainStationInfo.setId(Long.valueOf(String.valueOf(dataMap.get(item))));
				trainStationInfo.setName(item);
				trainStationInfos.add(trainStationInfo);
			}
			
			res.setData(new ServiceRespondData(trainStationInfos));			
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询车次管理下拉框数据异常"+e);
			logger.debug("查询车次管理下拉框数据异常"+e);
		}
		return res;
	}
	/**
	 * 获取方向信息下拉框
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/queryTrainDirectionSelect", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond queryTrainDirectionSelect() throws Exception{
		ServiceRespond res=null;
		ReturnMap ret=null;
		QueryRule queryRule=null;
		try {
			queryRule=QueryRule.getInstance();
			ret=trainDirectionService.queryDirectionSelect(queryRule);
			res=new ServiceRespond();
			if (ret.isSucc()) {
				List<TrainDirection> list=ret.getListContext();
				res.setData(new ServiceRespondData(list));
				res.setMsg(ret.getMsg());
			}else{
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("方向信息查询失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res=new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询方向异常");
			logger.error("查询方向信息发生异常", e);
		}
		return res;
	}
	/**
	 * 获取报表信息下拉框
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryReportInfoSelect", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond queryReportInfoSelect() throws Exception{
		ServiceRespond res=null;
		ReturnMap ret=null;
		QueryRule queryRule=null;
		try {
			queryRule=QueryRule.getInstance();
			ret=reportDefinedService.queryReportListByReportCode(queryRule);
			res=new ServiceRespond();
			if (ret.isSucc()) {
				@SuppressWarnings("unchecked")
				List<TrainDirection> list=ret.getListContext();
				res.setData(new ServiceRespondData(list));
				res.setMsg(ret.getMsg());
			}else{
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("报表信息查询失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("报表信息异常");
			logger.error("报表信息发生异常", e);
		}
		return res;
	}

}
