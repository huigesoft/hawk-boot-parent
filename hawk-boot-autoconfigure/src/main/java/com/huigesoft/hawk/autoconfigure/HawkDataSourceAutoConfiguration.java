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
package com.huigesoft.hawk.autoconfigure;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.huigesoft.hawk.core.datasource.HawkDataSource;

/**
 * @ClassName: HawkDataSourceAutoConfiguration
 *
 * @Description: 数据源自动配置类
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 11:33:21
 * 
 */
@AutoConfiguration
@Configuration(proxyBeanMethods = false)
public class HawkDataSourceAutoConfiguration {

	@Bean("manager")
	@ConfigurationProperties(prefix = "spring.datasource")
	DataSource dataSource(DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}

	@Bean
	@Primary
	HawkDataSource hawkDataSource() {
		return new HawkDataSource();
	}
}
