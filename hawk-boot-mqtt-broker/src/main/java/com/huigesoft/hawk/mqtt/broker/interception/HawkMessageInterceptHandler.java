/*
 * Copyright 2025-present the HuiGeSoft or Wangwenhui.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Email: wangwha@126.com
 * 
 */
package com.huigesoft.hawk.mqtt.broker.interception;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.huigesoft.hawk.mqtt.broker.message.IHawkServerMessageHandler;

import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptPublishMessage;

/**
 * @ClassName: HawkMessageInterceptHandler
 *
 * @Description: MQTT Broker 消息拦截处理器
 *
 * @author: 王文辉
 *
 * @date: 2025年12月2日 15:07:37
 * 
 */
public class HawkMessageInterceptHandler extends AbstractInterceptHandler {

	private List<IHawkServerMessageHandler> handlers = new CopyOnWriteArrayList<>();

	@Override
	public String getID() {
		return "HAWK-MQTT-BROKER-LISTENER";
	}

	@Override
	public void onPublish(InterceptPublishMessage msg) {
		for (int i = 0; i < handlers.size(); i++) {
			IHawkServerMessageHandler handler = handlers.get(i);
			try {
				handler.onArrived(msg);
			} catch (Exception e) {
				e.addSuppressed(e);
			}
		}
		super.onPublish(msg);
	}

	@Override
	public void onSessionLoopError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onConnect(InterceptConnectMessage msg) {
	}

	public void addHandler(IHawkServerMessageHandler handler) {
		this.handlers.add(handler);
	}

	public void removeHandler(IHawkServerMessageHandler handler) {
		this.handlers.remove(handler);
	}
}
