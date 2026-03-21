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
package com.huigesoft.hawk.core.runner;

import static com.huigesoft.hawk.commons.constant.Hawk.TITLE;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import com.huigesoft.hawk.core.cache.HawkTokenCache;
import com.huigesoft.hawk.core.security.cache.HawkPermissionCache;

/**
 * @ClassName: HawkTokenRunner
 *
 * @Description: Token令牌运行类
 *
 * @author: 王文辉
 *
 * @date: 2025年11月11日 09:31:19
 * 
 */
public class HawkTokenRunner implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(HawkTokenRunner.class);

	@Override
	public void run(String... args) throws Exception {
		logger.info("[{}] Initing Platform Tokens ", TITLE);
		HawkTokenCache.initToken();
		logger.info("[{}] Inited Platform Tokens Size :  ", TITLE, HawkTokenCache.size());

		Timer timer = new Timer("TokenTimer", true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				logger.info("[{}] Clear Platform Tokens ", TITLE);
				HawkTokenCache.clearExpireToken();
			}
		}, new Date(), 1000 * 60 * 60);

		HawkPermissionCache.refresh();
	}

}
