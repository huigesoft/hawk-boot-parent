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

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @ClassName: HawkSecurityProperties
 *
 * @Description: 平台安全配置类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:38:05
 *
 */
@ConfigurationProperties(prefix = "hawk.security")
public class HawkSecurityProperties {

	/**
	 * 忽略拦截的路径集合
	 */
	private List<String> permitMatchers = new ArrayList<>();

	public List<String> getPermitMatchers() {
		return permitMatchers;
	}

	public void setPermitMatchers(List<String> permitMatchers) {
		this.permitMatchers = permitMatchers;
	}
}