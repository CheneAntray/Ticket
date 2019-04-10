package com.xianqin.view.urlinfo;

import java.io.Serializable;

import com.xianqin.domain.UrlInfo;

public class UrlView extends UrlInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
	 * @param urlView 数据源对象实例
	 * @param urlInfo 数据目标对象实例
	 * @return 具有源对象属性值的目标对象实例
	 */
	 public static UrlInfo processUrlViewToUrlInfo(UrlView urlView,UrlInfo urlInfo){
		urlInfo.setCreateOper(urlView.getCreateOper());
		urlInfo.setUrlName(urlView.getUrlName());
		urlInfo.setCreateTime(urlView.getCreateTime());
		urlInfo.setUpdateTime(urlView.getUpdateTime());
		urlInfo.setId(urlView.getId());
		urlInfo.setUrlPath(urlView.getUrlPath());
		urlInfo.setUpdateOper(urlView.getUpdateOper());
		return urlInfo;
	}

}
