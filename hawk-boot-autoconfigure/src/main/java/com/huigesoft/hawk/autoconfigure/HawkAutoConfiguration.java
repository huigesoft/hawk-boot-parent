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

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

import com.huigesoft.hawk.core.advice.HawkRequestBodyAdapter;
import com.huigesoft.hawk.core.aspect.LogAspect;
import com.huigesoft.hawk.core.aspect.RepeatAspect;
import com.huigesoft.hawk.core.datasource.HawkDataSorceRegisterImpl;
import com.huigesoft.hawk.core.datasource.IHawkDataSourceRegister;
import com.huigesoft.hawk.core.log.DefaultLogStorage;
import com.huigesoft.hawk.core.log.ILogStorage;
import com.huigesoft.hawk.core.properties.HawkHttpProperties;
import com.huigesoft.hawk.core.properties.HawkKeyStoreProperties;
import com.huigesoft.hawk.core.properties.HawkProperties;
import com.huigesoft.hawk.core.properties.HawkSecurityProperties;
import com.huigesoft.hawk.core.runner.HawkTokenRunner;
import com.huigesoft.hawk.core.utils.ServletUtils;
import com.huigesoft.hawk.core.utils.SpringContextHolder;
import com.huigesoft.hawk.core.web.HawkAsyncHandlerInterceptor;
import com.huigesoft.hawk.core.web.HawkPermitAllAware;

/**
 * @ClassName: HawkAutoConfiguration
 *
 * @Description: 平台自动配置类
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 11:28:13
 * 
 */
@AutoConfiguration
@Configuration(proxyBeanMethods = false)
public class HawkAutoConfiguration {

	@Bean
	IHawkDataSourceRegister hawkDataSourceRegister() {
		return new HawkDataSorceRegisterImpl();
	}

	@Bean
	@DependsOn("hawkDataSourceRegister")
	HawkProperties hawkProperties() {
		return new HawkProperties();
	}

	@Bean
	HawkKeyStoreProperties hawkKeyStoreProperties() {
		return new HawkKeyStoreProperties();
	}

	@Bean
	HawkSecurityProperties hawkSecurityProperties() {
		return new HawkSecurityProperties();
	}

	@Bean
	HawkHttpProperties hawkHttpProperties() {
		return new HawkHttpProperties();
	}

	@Bean
	HawkPermitAllAware permitAllAware() {
		return new HawkPermitAllAware();
	}

	@Bean
	HawkAsyncHandlerInterceptor asyncHandlerInterceptor() {
		return new HawkAsyncHandlerInterceptor();
	}

	@Bean
	ServletUtils servletUtils() {
		return new ServletUtils();
	}

	@Bean
	LogAspect logAspect(ILogStorage logStorage, ServletUtils servletUtils) {
		return new LogAspect(logStorage, servletUtils);
	}

	@Bean
	RepeatAspect repeatAspect() {
		return new RepeatAspect();
	}

	@Bean
	@ConditionalOnMissingBean({ RestTemplate.class })
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@ConditionalOnMissingBean({ HawkRequestBodyAdapter.class })
	HawkRequestBodyAdapter hawkRequestBodyDecryptAdapter() {
		return new HawkRequestBodyAdapter();
	}

	@Bean
	@ConditionalOnMissingBean({ ILogStorage.class })
	ILogStorage logStorage() {
		return new DefaultLogStorage();
	}

	@Bean
	SpringContextHolder springContextHolder() {
		return new SpringContextHolder();
	}

	@Bean
	HawkTokenRunner hawkTokenRunner() {
		return new HawkTokenRunner();
	}

}
