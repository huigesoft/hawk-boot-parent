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
package com.huigesoft.hawk.mqtt.broker.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName: MqttBrokerProperties
 *
 * @Description: MQTT Broker 配置类
 *
 * @author: 王文辉
 *
 * @date: 2025年12月2日 14:51:11
 * 
 */
@ConfigurationProperties(prefix = "hawk.mqtt.broker")
public class HawkMqttBrokerProperties {

	/**
	 * mqtt broker 是否启用
	 */
	private Boolean enable = true;

	/**
	 * mqtt broker 端口号
	 */
	private Integer port = 1883;

	/**
	 * mqtt broker 绑定主机
	 */
	private String host = "0.0.0.0";

	/**
	 * mqtt 消息大小
	 */
	private Integer messageSize = 1048576;

	/**
	 * mqtt handler处理线程数
	 */
	private Integer threadPoolSize = 1;

	/**
	 * mqtt 消息存储路径
	 */
	private String storagePath = ".";

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getMessageSize() {
		return messageSize;
	}

	public void setMessageSize(Integer messageSize) {
		this.messageSize = messageSize;
	}

	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
