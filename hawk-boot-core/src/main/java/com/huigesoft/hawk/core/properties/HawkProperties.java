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
package com.huigesoft.hawk.core.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @ClassName: HawkProperties
 *
 * @Description: 平台配置类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:37:27
 *
 */
@ConfigurationProperties(prefix = "hawk")
public class HawkProperties {

	private String secretKey = "hawk_platform";

	private Map<String, Object> config = new HashMap<>();

	private Map<String, Object> variable = new HashMap<>();

	private String clusterProtocol = "udp";

	private String clusterName = "hawk_cluster";

	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}

	public Map<String, Object> getVariable() {
		return variable;
	}

	public void setVariable(Map<String, Object> variable) {
		this.variable = variable;
	}

	public String getClusterProtocol() {
		return clusterProtocol;
	}

	public void setClusterProtocol(String clusterProtocol) {
		this.clusterProtocol = clusterProtocol;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
