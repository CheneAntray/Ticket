package com.base;

import org.apache.log4j.Logger;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * 该类为所有junit测试类的基础类
 * 在该类中，完成了对项目基础资源的加载
 * 使用junit编写测试类时，注意继承该类
 * @author xianqin-bill
 *
 */




/** 声明用的是Spring的测试类 **/
@RunWith(SpringJUnit4ClassRunner.class)
/** 声明spring主配置文件位置，注意：以当前测试类的位置为基准,有多个配置文件以字符数组声明 **/
@ContextConfiguration({ "classpath*:/applicationContext.xml", "classpath*:/applicationContext-shiro.xml", "classpath*:/applicationContext-jdbc.xml", "classpath*:/applicationContext-mvc-rest.xml" })
/** 声明使用事务，不声明spring会使用默认事务管理 **/
@Transactional
@WebAppConfiguration
public class BasicWebTest{
	/**
	 * 控制台输出的日志
	 */
	protected static Logger logger = Logger.getLogger("STDOUT_LOG"); 
	
	@Autowired
    protected WebApplicationContext wac;
    
    protected MockMvc mvc;
    
    /** request 参数模拟  **/
    protected MockHttpServletRequest request;  
    
    /** request response参数模拟  **/
    protected MockHttpServletResponse response;  
    
    
    /**
     * 初始化方法，进行各类公用工具类的初始化
     * 如request模拟对象、reponse模拟对象、框架内容对象、测试代理对象的初始化工作
     * 在子类中，可以直接使用mvc(测试代理对象的初始化工作)、request（request模拟对象）、reponse（reponse模拟对象）
     * 实例进行相关测试案例编写
     */
    @Before
    public void setUp() {
    	//初始化requesrt对象
    	request = new MockHttpServletRequest();
    	//初始化response对象
        response = new MockHttpServletResponse();
        //设置传输的字符集
        request.setCharacterEncoding("UTF-8");  
        //初始化测试代理实例
        mvc=MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
