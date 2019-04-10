package com.base;

import java.util.List;
import java.util.Map;

/**
 * 该类用于封装进行控制器层调用业务服务层的数据交互返回结果
 * 如果业务服务层接口方法可以不用返回简单对象，则推荐使用该类进行处理结果封装
 * 当业务服务层接口处理完业务逻辑后，将处理结果、需要用字符形式传递的消息以及使用各类数据结构返回给调用者(控制器层方法)
 * @author xianqin-bill
 */
public class ReturnMap {
	/**
	 * 处理成功标志
	 */
	public static final int succ = 0;
	
	/**
	 * 处理失败标志
	 */
	public static final int fail = 1;
	
	/**
	 * 业务请求处理状态
	 */
	private int state;
	
	/**
	 * 使用List接口对象实例数据结构进行的接口调用返回容器
	 */
	private List listContext;
	
	/**
	 * 使用对象实例进行的接口调用返回容器
	 */
	private Object objContext;
	
	/**
	 * 使用Map接口对象实例进行的接口调用返回容器
	 */
	private Map mapContext;
	
	/**
	 * 使用String对象实例进行的接口调用返回容器
	 */
	private String strContext;
	
	/**
	 * 业务服务层业务服务处理消息返回容器
	 */
	private String msg;
	
	/**
	 * 构造函数，初始化将处理状态处理为失败
	 */
	public ReturnMap(){
		state = fail;
	}
	
	/**
	 * 设置业务服务处理状态为成功
	 */
	public void setSucc(){
		state = succ;
	}
	
	/**
	 * 设置业务服务处理状态为失败
	 */
	public void setFail(){
		state = fail;
	}

	/**
	 * 获取以List接口实例返回的业务处理结果
	 * @return
	 */
	public List getListContext() {
		return listContext;
	}

	/**
	 * 设置以List接口实例返回的业务处理结果
	 */
	public void setListContext(List listContext) {
		this.listContext = listContext;
	}

	/**
	 * 获取以业务对象实例返回的业务处理结果
	 * @return
	 */
	public Object getObjContext() {
		return objContext;
	}

	/**
	 * 设置以业务对象实例返回的业务处理结果
	 */
	public void setObjContext(Object objContext) {
		this.objContext = objContext;
	}

	/**
	 * 设置以Map接口对象实例返回的业务处理结果
	 * @return
	 */
	public Map getMapContext() {
		return mapContext;
	}

	/**
	 * 获取以Map接口对象实例返回的业务处理结果
	 */
	public void setMapContext(Map mapContext) {
		this.mapContext = mapContext;
	}

	/**
	 * 设置以Stirng对象实例返回的业务处理结果
	 * @return
	 */
	public void setStrContext(String strContext){
		this.strContext = strContext;
	}
	
	/**
	 * 获取以Map接口对象实例返回的业务处理结果
	 */
	public String getStrContext(){
		return this.strContext;
	}
	
	/**
	 * 获取业务服务接口返回的处理消息
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置业务服务接口返回的处理消息
	 * @return
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * 判断业务服务接口的处理结果
	 * true-处理成功
	 * false-处理失败
	 * @return
	 */
	public boolean isSucc(){
		if(state==succ){
			return true;
		}else{
			return false;
		}
	}
}
