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
package com.huigesoft.hawk.core.datasource;

import static com.huigesoft.hawk.commons.constant.Hawk.TITLE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.huigesoft.hawk.commons.context.HawkContext;

import jakarta.annotation.Resource;

/**
 * 
 * @ClassName: HawkDataSource
 *
 * @Description: Hawk平台数据源
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:20:11
 *
 */
public class HawkDataSource extends AbstractRoutingDataSource {

	private Logger logger = LoggerFactory.getLogger(HawkDataSource.class);

	@Resource
	@Qualifier("manager")
	private DataSource dataSource;

	private Map<Object, Object> targets = new ConcurrentHashMap<Object, Object>();

	public HawkDataSource() {
		this.setTargetDataSources(targets);
		this.setDefaultTargetDataSource(this.dataSource);
		this.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		logger.debug("[{}] determineCurrentLookupKey,{}", TITLE, HawkContext.getTenant());
		return HawkContext.getTenant();
	}

	@Override
	protected DataSource determineTargetDataSource() {
		logger.debug("[{}] determineTargetDataSource", TITLE);
		return dataSource;
	}

	public void addDataSource(String key, DataSource dataSource) {
		if (targets.containsKey(key)) {
			logger.warn("[{}] DataSource {} exists, and it will be replaced.", TITLE, key);
			targets.remove(key);
		}
		targets.put(key, dataSource);
		this.setTargetDataSources(targets);
		logger.info("[{}] DataSource {} added.", TITLE, key);
		this.afterPropertiesSet();
	}
}