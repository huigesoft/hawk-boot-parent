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

import io.moquette.broker.security.IAuthorizatorPolicy;
import io.moquette.broker.subscriptions.Topic;

/**
 * @ClassName: HawkAuthorizatorPolicy
 *
 * @Description: MQTT Broker 授权策略
 *
 * @author: 王文辉
 *
 * @date: 2025年12月2日 15:05:57
 * 
 */
public class HawkAuthorizatorPolicy implements IAuthorizatorPolicy {

	@Override
	public boolean canWrite(Topic topic, String user, String client) {
		return true;
	}

	@Override
	public boolean canRead(Topic topic, String user, String client) {
		return false;
	}

}
