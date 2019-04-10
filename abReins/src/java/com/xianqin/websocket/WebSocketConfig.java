package com.xianqin.websocket;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.base.utils.LogUtils;
/**
 * 开启websocket服务
 *
 */
//@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	/**
	 * 日志对象初始化
	 */
	protected Logger logger = LogUtils.getConsoleLogIns();
	/**
	 * 配置WebSocket建立连接路径
	 */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 支持websocket 的访问链接
        registry.addHandler(new WebSocketHander(), "/ws")
        .addInterceptors(new HandInterceptor());
        // 不支持websocket的访问链接
        registry.addHandler(new WebSocketHander(), "/ws")
                .addInterceptors(new HandInterceptor())
                .withSockJS();
        logger.info("init webstocket registerWebSocketHandlers");
    }
    
    @Bean
    public WebSocketHandler webSocketHandler(){
        return new WebSocketHander();
    }
}