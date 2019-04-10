package com.base;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 此类设计的目的，是为了对客户端请求进行响应
 * 主要用于控制器层
 * 客户端请求在完成客户端响应后，建议使用该对象进行响应数据封装，从而完成客户端请求响应
 * 该类是在与客户端项目组沟通后，就客户端与服务器端数据传输格式与规范基础上进行的设计
 * @author xianqin-bill
 *
 */
@JsonInclude(Include.NON_NULL)
public class ServiceRespond implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1712283951239384497L;

	private Integer code;
	
	private ServiceRespondData data;
	
	private String msg;
	
	private Integer httpStatus;
	
	/**
	 * 默认构造函数
	 * 使用默认构造函数获取的对象实例，其成功标志位定义为"成功",响应消息定义为"成功"
	 */
	public ServiceRespond(){
		//初始化为成功
		code = ApplicationDefined.PROCESS_CODE_SUCCESS;
		httpStatus=ApplicationDefined.HTTP_STATUS_SUCC;
		msg = "成功";
	}
	
	/**
	 * 构造完整的请求响应对象
	 * @param code 处理结果标志位 注意，请传入ApplicationDefined中PROCESS_CODE_*定义的相关值
	 * @param data 响应数据主题对象实例ServiceRespondData类对象实例
	 * @param msg 响应消息
	 */
	public ServiceRespond(int code,ServiceRespondData data,String msg){
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	
	/**
	 * 获取请求响应状态码
	 * 返回值等于ApplicationDefined中PROCESS_CODE_*定义的相关值
	 * @return
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * 设置请求响应状态码
	 * 请注意:值等于ApplicationDefined中PROCESS_CODE_*定义的相关值
	 * @param code 符合ApplicationDefined中PROCESS_CODE_*定义的相关值
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * 获取请求响应数据主题对象实例
	 * 该对象为ServiceRespondData类的实例
	 * 包含了队列结构、单个对象实例以及翻页相关属性
	 * @return
	 */
	public ServiceRespondData getData() {
		return data;
	}
	
	/**
	 * 设置请求响应数据主题对象实例
	 * 该对象为ServiceRespondData类的实例
	 * 包含了队列结构、单个对象实例以及翻页相关属性
	 * @param  data ServiceRespondData类实例
	 */
	public void setData(ServiceRespondData data) {
		this.data = data;
	}

	/**
	 * 获取请求响应消息
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置请求响应消息
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	

}
