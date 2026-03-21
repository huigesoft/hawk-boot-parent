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
package com.huigesoft.hawk.core.security.token;

import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;

import com.huigesoft.hawk.core.domain.User;

/**
 * @ClassName: HawkTokenGenerator
 *
 * @Description: Token生成器
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 09:00:26
 * 
 */
public class HawkTokenGenerator {

	private JwtEncoder jwtEncoder;

	public HawkTokenGenerator(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

	public synchronized Jwt generateToken(User user) {
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		Set<String> authorizedScopes = new HashSet<>();
		authorities.forEach(authority -> {
			authorizedScopes.add(authority.getAuthority());
		});
		Map<String, Object> data = user.getDatas();
		RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId(user.getUsername()).clientSecret(user.getPassword())
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE).scope("*")
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(Duration.ofHours((Integer) data.get("session_time"))).build())
				.build();
		OAuth2TokenContext tokenContext = DefaultOAuth2TokenContext.builder().registeredClient(registeredClient)
				.principal(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())) // 模拟用户认证
				.tokenType(OAuth2TokenType.ACCESS_TOKEN).authorizedScopes(authorizedScopes) // 解析授权范围
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) // 使用客户端凭证模式
				.build();
		JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
		return jwtGenerator.generate(tokenContext);
	}
}
