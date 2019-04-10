package com.base;

import java.io.Serializable;

import com.xianqin.common.Page;

public class ServiceResponseFromPage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Page page = null;

	public ServiceResponseFromPage(Page page) {
		super();
		this.page = page;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public ServiceResponseFromPage() {
		super();
		// TODO Auto-generated constructor stub
	}

}
