package com.xianqin.websocket;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 拦截器（握手）
 */
public class HandInterceptor implements HandshakeInterceptor {
	
	/**
	 * 
	 */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
            ServerHttpResponse response, WebSocketHandler handler,
            Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
            String userAccount =req.getParameter("account");
            map.put("WEBSOCKET_USERACCOUNT", userAccount);
            //req.getSession().setAttribute("WEBSOCKET_USERNAME", userAccount);
        }
        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse,
            WebSocketHandler webSocketHandler, Exception e) {
    }
}

