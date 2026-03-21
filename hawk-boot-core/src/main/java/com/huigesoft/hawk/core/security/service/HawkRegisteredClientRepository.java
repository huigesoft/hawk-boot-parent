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

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import com.huigesoft.hawk.core.domain.User;

/**
 * 
 * @ClassName: HawkRegisteredClientRepository
 *
 * @Description: OAuth2 客户端持久化Repository
 *
 * @author: 王文辉
 *
 * @date: 2024-08-22 13:43:15
 *
 */
public class HawkRegisteredClientRepository implements RegisteredClientRepository {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void save(RegisteredClient registeredClient) {

	}

	@Override
	public RegisteredClient findById(String id) {
		return null;
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		User user = (User) userDetailsService.loadUserByUsername(clientId);
		if (null == user) {
			return null;
		}
		Map<String, Object> datas = user.getDatas();
		long expireTime = datas.get("expire_time") == null ? 4102415999000L : (long) datas.get("expire_time");
		if (!user.isEnabled()) {
			throw new RuntimeException("用户已被禁用,请联系管理员！");
		}
		return RegisteredClient.withId(UUID.randomUUID().toString()).clientId(user.getUsername())
				.clientSecret(user.getPassword()).clientSecretExpiresAt(Instant.ofEpochMilli(expireTime))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
				.redirectUri("http://127.0.0.1:8080/authorized").scope(OidcScopes.OPENID).scope(OidcScopes.PROFILE)
				.scope("*").clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(Duration.ofHours((int) datas.get("session_time"))).build())
				.build();
	}
}
