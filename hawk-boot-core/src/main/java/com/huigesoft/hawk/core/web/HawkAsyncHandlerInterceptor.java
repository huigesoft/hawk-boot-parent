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
package com.huigesoft.hawk.core.web;

import static com.huigesoft.hawk.commons.constant.Hawk.TENANT;
import static com.huigesoft.hawk.commons.constant.Hawk.TITLE;
import static com.huigesoft.hawk.commons.constant.Hawk.USER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.huigesoft.hawk.commons.context.HawkContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName: HawkAsyncHandlerInterceptor
 *
 * @Description: Hawk 平台请求拦截处理器
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:54:39
 *
 */
public class HawkAsyncHandlerInterceptor implements AsyncHandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(HawkAsyncHandlerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String tenantId = request.getHeader("tenant_id");
		logger.debug("[{}] Current TenantId : {}", TITLE, tenantId);
		if (null == tenantId || "".equals(tenantId)) {
			tenantId = TENANT;
			logger.debug("[{}] Current TenantId is NULL , Use Default TenantId : {}", TITLE, tenantId);
		}
		HawkContext.setTenant(tenantId);

		String user = request.getHeader("user");
		logger.debug("[{}] Current User : {}", TITLE, user);

		if (null == user) {
			user = USER;
			logger.debug("[{}] Current User is NULL , Use Default User : {}", TITLE, user);
		}
		HawkContext.setUser(user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("[{}] Clear Current TenantId : {}", TITLE, HawkContext.getTenant());
		HawkContext.clear();
	}

}