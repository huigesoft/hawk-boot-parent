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
package com.huigesoft.hawk.core.security.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.huigesoft.hawk.core.security.cache.HawkPermissionCache;

/**
 * @ClassName: HawkAuthorizeService
 *
 * @Description: 授权服务
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 08:53:37
 * 
 */
public class HawkAuthorizeService {

	public boolean has(String permission) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getCredentials() instanceof Jwt jwt) {
			if (jwt.getClaims().get("scope") instanceof List<?> scopes) {
				return HawkPermissionCache.accessDecision(permission, scopes);
			}
		}
		return true;
	}
}
