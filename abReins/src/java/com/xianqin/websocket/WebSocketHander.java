package com.xianqin.websocket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import com.base.utils.LogUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 * WebSocket消息处理中心
 */
public class WebSocketHander implements WebSocketHandler {
	private static WebSocketHander webSocketHander;

	@PostConstruct
	public void init() {
		webSocketHander = this;
	}

	/**
	 * 日志对象初始化
	 */
	protected Logger logger = LogUtils.getConsoleLogIns();
	private static final List<WebSocketSession> users = new ArrayList<WebSocketSession>();

	/**
	 * 初次链接成功执行
	 *
	 * @param session
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("WebSocketHander:链接成功......");
		users.add(session);
		String userName = (String) session.getAttributes().get("WEBSOCKET_USERACCOUNT");
		this.sendMessageByUserId(userName);

	}

	/**
	 * 接受消息处理消息
	 *
	 * @param session
	 * @param message
	 * @throws Exception
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		logger.info("WebSocketHander line 49:handleMessage");
		sendMessageToUsers(new TextMessage(message.getPayload() + ""));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		logger.info("WebSocketHander:链接出错，关闭链接......");
		users.remove(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("WebSocketHander:链接关闭......" + status.toString());
		users.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
    /**
     * 定时任务执行的方法
     */
	public void doWork() {
		logger.info("WebSocketHander :开始执行任务……………………");

		/*List<String> unitIds = inventoryTasksInfoService.saveReleaseCycle();
		if (unitIds.size() > 0 && !unitIds.isEmpty()) {
			List<UserInfo> userInfos = userInfoService.getUserInfoListByUnitIds(unitIds);
			// 群发消息
			for (UserInfo userInfo : userInfos) {
				sendMessageToUser(userInfo.getAccount(), new TextMessage("您有新的盘点任务"));
			}
		}*/

	}

	/**
	 * 当用户登陆时 获取该用户的盘点任务 推送给用户
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public static synchronized void sendMessageByUserId(String userName) throws Exception {
		/*Integer result = webSocketHander.inventoryTaskListService.getInventoryTasksInfoByUserAccount(userName);
		// inventoryTasksInfoService.saveReleaseCycle();
		if (result > 0) {
			// 推送消息
			for (WebSocketSession user : users) {
				if (user.getAttributes().get("WEBSOCKET_USERACCOUNT").equals(userName) && user.isOpen()) {
					user.sendMessage(new TextMessage("您有正在盘点的任务"));
				}
			}
		}*/

	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : users) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				logger.info("WebSocketHander line 83 exception :", e);
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 *
	 * @param userName
	 * @param message
	 */
	public void sendMessageToUser(String userName, TextMessage message) {
		for (WebSocketSession user : users) {
			if (user.getAttributes().get("WEBSOCKET_USERACCOUNT").equals(userName)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
					logger.info("WebSocketHander line 83 exception :", e);
				}
				break;
			}
		}
	}
}
