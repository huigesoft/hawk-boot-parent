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
package com.huigesoft.hawk.mqtt.broker.message;

import io.moquette.interception.messages.InterceptPublishMessage;

/**
 * @ClassName: IHawkServerMessageHandler
 *
 * @Description: 服务端消息到达时间
 *
 * @author: 王文辉
 *
 * @date: 2025年12月3日 13:57:27
 * 
 */
public interface IHawkServerMessageHandler {

	/**
	 * 
	 * @Title: onArrived
	 *
	 * @Description: 消息已到达事件
	 * 
	 * @param: @param message
	 *
	 * @return: void
	 * 
	 * @date: 2025年12月3日 14:00:29
	 *
	 * @throws
	 */
	public void onArrived(InterceptPublishMessage message);

}
