package com.xianqin.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.base.BaseController;
import com.base.ReturnMap;
import com.base.ServiceRespond;
import com.base.ServiceRespondData;
import com.xianqin.domain.StationSectionInfo;
import com.xianqin.service.StationSectionInfoService;
@RestController
public class StationSectionInfoController extends BaseController {
	
	@Autowired
	private StationSectionInfoService stationSectionInfoService;
	
	
	/**
	 * 导入数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStationSection", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond addStationSection(@RequestBody StationSectionInfo stationSectionInfo) throws Exception {
		ServiceRespond res = new ServiceRespond();
		try {
			ReturnMap ret=stationSectionInfoService.saveStationSectionInfo(stationSectionInfo);
			res.setData(new ServiceRespondData(ret.getMapContext()));			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("异常"+e.getMessage());
		}
		return res;
	}

}
