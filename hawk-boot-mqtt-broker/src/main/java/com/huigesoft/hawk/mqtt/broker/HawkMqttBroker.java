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
package com.huigesoft.hawk.mqtt.broker;

import static com.huigesoft.hawk.commons.constant.Hawk.TITLE;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.huigesoft.hawk.mqtt.broker.interception.HawkMessageInterceptHandler;
import com.huigesoft.hawk.mqtt.broker.message.IHawkServerMessageHandler;
import com.huigesoft.hawk.mqtt.broker.properties.HawkMqttBrokerProperties;
import com.huigesoft.hawk.mqtt.broker.security.HawkAuthenticator;
import com.huigesoft.hawk.mqtt.broker.security.HawkAuthorizatorPolicy;

import io.moquette.BrokerConstants;
import io.moquette.broker.ClientDescriptor;
import io.moquette.broker.Server;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.MemoryConfig;
import io.moquette.interception.InterceptHandler;

/**
 * @ClassName: HawkMqttBroker
 *
 * @Description: Hawk MQTT Broker 服务类
 *
 * @author: 王文辉
 *
 * @date: 2025年12月2日 14:58:46
 * 
 */
public class HawkMqttBroker implements InitializingBean, DisposableBean, Closeable {

	private Logger logger = LoggerFactory.getLogger(HawkMqttBroker.class);

	private static final String path = "mqtt";

	private HawkMqttBrokerProperties props;

	private Server server = null;

	private HawkMessageInterceptHandler defaultInterceptHandler = new HawkMessageInterceptHandler();

	public HawkMqttBroker(HawkMqttBrokerProperties properties) {
		this.props = properties;
	}

	public synchronized void start() throws IOException {
		if (null == server) {
			String mqttPath = props.getStoragePath() + File.separator + path;
			File file = new File(mqttPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			logger.info("[{}] MQTT Broker Host: {} ", TITLE, props.getHost());
			logger.info("[{}] MQTT Broker Port: {} ", TITLE, props.getPort());
			logger.info("[{}] MQTT Broker Message Size: {} ", TITLE, props.getMessageSize());
			logger.info("[{}] MQTT Broker Thread Pool Size: {} ", TITLE, props.getThreadPoolSize());
			server = new Server();
			Properties properties = new Properties();
			properties.put(IConfig.AUTHENTICATOR_CLASS_NAME, HawkAuthenticator.class.getName());
			properties.put(IConfig.AUTHORIZATOR_CLASS_NAME, HawkAuthorizatorPolicy.class.getName());
			properties.put(IConfig.PORT_PROPERTY_NAME, Integer.toString(props.getPort()));
			properties.put(IConfig.HOST_PROPERTY_NAME, props.getHost());
			properties.put(IConfig.NETTY_MAX_BYTES_PROPERTY_NAME, String.valueOf(props.getMessageSize()));
			properties.put(IConfig.DATA_PATH_PROPERTY_NAME, mqttPath);
			properties.setProperty(BrokerConstants.BROKER_INTERCEPTOR_THREAD_POOL_SIZE,
					props.getThreadPoolSize().toString());
			properties.setProperty(BrokerConstants.ALLOW_ZERO_BYTE_CLIENT_ID_PROPERTY_NAME, "false");
			IConfig config = new MemoryConfig(properties);
			List<InterceptHandler> handlers = List.of(defaultInterceptHandler);
			server.startServer(config, handlers);
		}
	}

	public void addServerMessageHandler(IHawkServerMessageHandler handler) {
		defaultInterceptHandler.addHandler(handler);
	}

	public void registerInterceptHandlers(List<InterceptHandler> handlers) {
		assertServer();
		if (null != server) {
			for (int i = 0; i < handlers.size(); i++) {
				server.addInterceptHandler(handlers.get(i));
			}
		}
	}

	public void registerInterceptHandler(InterceptHandler handler) {
		assertServer();
		server.addInterceptHandler(handler);
	}

	public synchronized void close() throws IOException {
		if (null != server) {
			server.stopServer();
		}
	}

	/**
	 * 
	 * @Title: getOnlines
	 *
	 * @Description: 获取所有在线客户端ID
	 * 
	 * @param: @return
	 *
	 * @return: Set<String>
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-09-10 15:47:16
	 * 
	 * @throws
	 */
	public Set<String> getOnlines() {
		Set<String> ids = new HashSet<>();
		if (null != server) {
			Collection<ClientDescriptor> onlines = server.listConnectedClients();
			for (Iterator<ClientDescriptor> iter = onlines.iterator(); iter.hasNext();) {
				ClientDescriptor client = iter.next();
				ids.add(client.getClientID());
			}
		}
		return ids;
	}

	@Override
	public void destroy() throws Exception {
		close();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("[{}] MQTT Broker Enabled: {} ", TITLE, props.getEnable());
		if (props.getEnable()) {
			start();
		}
	}

	private void assertServer() {
		Assert.notNull(server, "MQTT Broker 未启动！");
	}

}
