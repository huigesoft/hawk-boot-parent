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
package com.huigesoft.hawk.core.log;

import static com.huigesoft.hawk.commons.constant.Hawk.TITLE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: DefaultLogStorage
 *
 * @Description: 默认日志存储实现
 *
 * @author: 王文辉
 *
 * @date: 2025年9月25日 18:34:17
 * 
 */
public class DefaultLogStorage implements ILogStorage {

	private static final Logger logger = LoggerFactory.getLogger(DefaultLogStorage.class);

	@Override
	public void save(OperLog log) {
		logger.info("[{}] Operator Log is : {}", TITLE, log);
	}

}
