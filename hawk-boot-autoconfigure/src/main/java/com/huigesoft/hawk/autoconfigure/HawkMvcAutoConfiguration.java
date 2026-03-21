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
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.huigesoft.hawk.core.web.HawkAsyncHandlerInterceptor;
import com.huigesoft.hawk.core.web.HawkFilter;

import jakarta.annotation.Resource;

/**
 * @ClassName: HawkMvcAutoConfiguration
 *
 * @Description: TODO(描述这个类的作用)
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 11:38:21
 * 
 */
@AutoConfiguration
@Configuration(proxyBeanMethods = false)
public class HawkMvcAutoConfiguration implements WebMvcConfigurer {

	@Resource
	private HawkAsyncHandlerInterceptor asyncHandlerInterceptor;

	@Bean
	CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOriginPattern("*");
		config.setAllowCredentials(true);
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.addExposedHeader("*");
		UrlBasedCorsConfigurationSource crosConfigurationSource = new UrlBasedCorsConfigurationSource();
		crosConfigurationSource.registerCorsConfiguration("/**", config);
		return new CorsFilter(crosConfigurationSource);
	}

	@Bean
	FilterRegistrationBean<HawkFilter> i18nFilterRegistrationBean() {
		FilterRegistrationBean<HawkFilter> registrationBean = new FilterRegistrationBean<>();
		HawkFilter hawkFilter = new HawkFilter();
		registrationBean.setFilter(hawkFilter);
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Integer.MIN_VALUE); // 这里
		return registrationBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(asyncHandlerInterceptor);
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
