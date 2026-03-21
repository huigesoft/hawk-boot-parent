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

import static com.huigesoft.hawk.commons.constant.Hawk.TITLE;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.huigesoft.hawk.commons.domain.HawkToken;
import com.huigesoft.hawk.core.cache.HawkTokenCache;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName: HawkFilter
 *
 * @Description: Hawk 平台WEB过滤器，主要处理token信息
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:53:32
 *
 */
public class HawkFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(HawkFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HeaderRequestWrapper wrapper = new HeaderRequestWrapper(request);
		String auth = request.getHeader("Authorization");
		logger.debug("[{}] Client Authorization : {}", TITLE, auth);
		if (StringUtils.hasText(auth)) {
			HawkToken hawkToken = HawkTokenCache.get(auth);
			if (null != hawkToken) {
				Object accessToken = hawkToken.getAccessToken();
				Object tokenType = hawkToken.getTokenType();
				String user = hawkToken.getUsername();
				String tenantId = null;

				logger.debug("[{}] Client User : {}", TITLE, user);
				logger.debug("[{}] Client Tenant : {}", TITLE, tenantId);

				if (StringUtils.hasText(String.valueOf(accessToken))
						&& StringUtils.hasText(String.valueOf(tokenType))) {
					wrapper.addHeader("Authorization", tokenType + " " + accessToken);
				}

				if (StringUtils.hasText(user)) {
					wrapper.addHeader("user", String.valueOf(user));
				}

				if (StringUtils.hasText(tenantId)) {
					wrapper.addHeader("tenant_id", String.valueOf(tenantId));
				}
			}
		}
		filterChain.doFilter(wrapper, response);
	}

}
