package com.base;

import java.io.Serializable;
import java.util.List;

/**
 * 该类用于封装客户端请求响应数据结构中关于数据的节点
 * 主要由于数据除了数据本身外，还包含了翻页信息等除数据主题以外的附属其他信息
 * 该类属于初稿，需要在实际使用过程中持续完善
 * @author xianqin-bill
 *
 */
public class ServiceRespondData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7665059794389138237L;

	/**
	 * List接口对象实例
	 * 用于保存使用List接口对象实例作为容器保存的客户端请求
	 */
	private List<?> list;
	
	/**
	 * 对象实例
	 * 用于保存使用对象实例作为容器保存的客户端请求
	 */
	private Object obj;
	
	/**
	 * 数据请求总长度
	 * 适用于用户批量数据查询
	 * 当用户批量数据查询时，不能一次性返回所有用户数据以保证客户端的稳定性
	 * 该属性用于保存批量数据查询时所有符合批量数据查询条件的数据条数总计
	 * 当使用obj作为返回值得时候，该属性为空，不参与传输
	 */
	private Integer dataTotal;
	
	/**
	 * 当前批量数据查询响应的数据页数
	 * 当使用obj作为返回值得时候，该属性为空，不参与传输
	 */
	private Integer pageNo;
	
	/**
	 * 当前批量数据查询响应的数据页中数据条数
	 * 当使用obj作为返回值得时候，该属性为空，不参与传输
	 */
	private Integer pageSize;

	/**
	 * 默认构造函数
	 * 构造一个空对象
	 */
	public ServiceRespondData(){
		
	}
	
	/**
	 * 构造使用单个对象为主题的响应对象
	 * 如果传入的实例为List，则会将参数赋值给List，此时就需要设置如dataTotal，pageNo、pageSize等相关参数
	 * @param dataObj 对象主题实例
	 */
	public ServiceRespondData(Object dataObj){
		if(dataObj instanceof List){
			this.list = (List<?>)dataObj;
		}else{
			this.obj = dataObj;
		}
	}
	
	/**
	 * 构造一个使用List实例对象为主题的响应对象
	 * @param list List实例，包含了需要传输的数据实例
	 * @param dataTotal 数据查询中符合查询条件的数据总条数
	 * @param pageNo 当前数据页数
	 * @param pageSize 每页包含的数据条数
	 */
	public ServiceRespondData(List<?> list,Integer dataTotal,Integer pageNo,Integer pageSize){
		this.list = list;
		this.dataTotal = dataTotal;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	/**
	 * 获取数据队列实例
	 * @return
	 */
	public List<?> getList() {
		return list;
	}

	
	/**
	 * 设置数据队列实例
	 * @param list List接口对象实例
	 */
	public void setList(List<?> list) {
		this.list = list;
	}

	/**
	 * 获取单个响应对象实例数据实例
	 * @return
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * 设置单个响应对象实例数据实例
	 * @param obj 单个响应对象实例数据实例
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * 当响应结果为多个数据对象实例时，获取所有符合条件数据的总条数
	 * 当响应结果为单个数据对象实例时，该属性为空，不参与传输
	 * @return
	 */
	public Integer getDataTotal() {
		return dataTotal;
	}

	/**
	 * 当响应结果为多个数据对象实例时，设置所有符合条件数据的总条数
	 * 当响应结果为单个数据对象实例时，该属性不需要设置
	 * @param dataTotal 所有符合条件数据的总条数
	 */
	public void setDataTotal(Integer dataTotal) {
		this.dataTotal = dataTotal;
	}

	/**
	 * 当响应结果为多个数据对象实例时，设置所有当前所在的数据页数
	 * 当响应结果为单个数据对象实例时，该属性为空
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * 当响应结果为多个数据对象实例时，设置当前所在的数据页数
	 * 当响应结果为多个数据对象实例时，该属性不需要设置
	 * @param pageNo 数据页数
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 当响应结果为多个数据对象实例时，获取当前页的数据条数
	 * 当响应结果为多个数据对象实例时，该属性不需要设置
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 当响应结果为多个数据对象实例时，设置当前页的数据条数
	 * 当响应结果为多个数据对象实例时，该属性不需要设置
	 * @param  pageSize 当前页的数据条数
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
}
