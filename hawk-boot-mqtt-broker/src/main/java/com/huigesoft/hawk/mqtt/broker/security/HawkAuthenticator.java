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
package com.huigesoft.hawk.mqtt.broker.security;

import io.moquette.broker.security.IAuthenticator;

/**
 * @ClassName: HawkAuthenticator
 *
 * @Description: Hawk MQTT Broker 鉴权器
 *
 * @author: 王文辉
 *
 * @date: 2025年12月2日 15:04:49
 * 
 */
public class HawkAuthenticator implements IAuthenticator {

	@Override
	public boolean checkValid(String clientId, String username, byte[] password) {
		if (null != clientId && null != username && null != password) {
			return true;
		}
		return false;
	}

}
