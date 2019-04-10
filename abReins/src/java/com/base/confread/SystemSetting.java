package com.base.confread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 该类主要用于读取系统设置配置文件内容
 * 系统设置配置文件名:system_setting.property,放置在项目根目录下
 * 本类采用spring注解形式编写
 * 该配置文件定义了所有公用的配置文件
 * @author xianqin-bill
 *
 */
@Configuration  
@PropertySource(value="classpath:system_setting.properties")
public class SystemSetting {
	/**
	 * 上传文件的临时目录，注意，该目录下的文件每天会进行清空
	 */
	@Value("${UPLOAD_FILE_TEMP_PATH}") 
	public  String UPLOAD_FILE_TEMP_PATH;
	
	/**
	 * 上传文件保存目录，该目录下的文件一般作为存档存在，一般不会进行删除
	 */
	@Value("${UPLOAD_FILE_PATH}") 
	public  String UPLOAD_FILE_PATH;
	
	/**
	 * 上传文件的加密加密盐值
	 */
	@Value("${UPLOAD_FILE_KEY}") 
	public  String UPLOAD_FILE_KEY;
	
	/**
	 * 文件下载路径
	 */
	@Value("${DOWNLOND_FILE_PATH}") 
	public  String DOWNLOND_FILE_PATH;

	/**
	 * 报表模版读取路径
	 */
	@Value("${REPORT_TEMPLET_BASE_PATH}") 
	public  String REPORT_TEMPLET_BASE_PATH;
	
	

}
