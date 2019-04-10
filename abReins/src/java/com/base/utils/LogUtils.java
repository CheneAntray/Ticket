package com.base.utils;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 * 该类用于在项目开发时获取各种日志
 * @author xianqin-bill
 *
 */
public class LogUtils {
	/**
	 * 开发阶段输出到控制台的日志器名称
	 */
	private static String DEV_CONSOLE = "STDOUT_LOG";
	
	/**
	 * 开发阶段输出到控制台的文件的日志器名称
	 */
	private static String DEV_FILE = "DEBUG_LOG";
	
	/**
	 * 控制台日志对象实例
	 */
	private static Logger console_log = null;
	
	
	/**
	 * 文件日志对象实例
	 */
	private static Logger file_log = null;
	
	
	/**
	 * 获取控制台日志对象实例
	 * 该对象输出对象为控制台
	 * 一般在开发调试阶使用IDE进行调试的时候适用
	 * @return
	 */
	public static Logger getConsoleLogIns(){
		if(console_log == null){
			console_log =  Logger.getLogger(DEV_CONSOLE); 
		}
		return console_log;
	}
	
	
	/**
	 * 获取文件日志对象实例
	 * 该对象输入到文件中
	 * 一般在不方便用控制台查看日志的时候，可以使用文件形式进行日志查看
	 * @return
	 */
	public static Logger getFileLogIns(){
		if(file_log == null){
			file_log =  Logger.getLogger(DEV_FILE); 
		}
		return file_log;
	}

}
