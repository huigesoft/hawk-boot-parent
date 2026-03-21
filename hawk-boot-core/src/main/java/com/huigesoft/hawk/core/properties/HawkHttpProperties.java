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

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.huigesoft.hawk.core.enums.HttpAlgorithm;

/**
 * @ClassName: HawkHttpProperties
 *
 * @Description: Http请求配置类
 *
 * @author: 王文辉
 *
 * @date: 2025年9月22日 10:12:11
 * 
 */
@ConfigurationProperties(prefix = "hawk.http")
public class HawkHttpProperties {

	/**
	 * 请求参数加解密密钥
	 */
	private String secureKey = "admin888";

	/**
	 * 请求参数加解密算法，默认DES
	 */
	private HttpAlgorithm algorithm = HttpAlgorithm.DES;

	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}

	public HttpAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(HttpAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

}
