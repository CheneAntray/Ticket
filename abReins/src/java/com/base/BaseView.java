package com.base;

import java.io.Serializable;

/**
 * 所有前台数据对象基础类，前后台交互过程中，在控制器层使用的前台参数封装类，都可以继承该类
 * 该类定义了公用参数
 * @author xianqin-bill
 *
 */
public class BaseView implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1929232920447493344L;

	/**
	 * 前台分页中的当前页码
	 */
	private Integer pageIndex;
	
	/**
	 * 前台分页参数中的每页显示数量
	 */
	private Integer pageSize;

	/**
	 * 获取前台分页参数的当前页码
	 * @return
	 */
	public Integer getPageIndex() {
		return pageIndex;
	}

	/**
	 * 设置前台分页参数的当前页码
	 * @param pageIndex 当前页码
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 获取前台分页参数中的每页数据显示条数
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 设置前台分页参数中的每页数据显示条数
	 * @param pageSize  每页数据显示条数
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
