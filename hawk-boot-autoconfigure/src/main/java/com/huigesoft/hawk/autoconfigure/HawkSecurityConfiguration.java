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

import static com.huigesoft.hawk.commons.constant.Hawk.OAUTH_PREFIX;
import static com.huigesoft.hawk.commons.constant.Hawk.TITLE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import com.huigesoft.hawk.core.properties.HawkKeyStoreProperties;
import com.huigesoft.hawk.core.properties.HawkSecurityProperties;
import com.huigesoft.hawk.core.security.service.HawkAuthorizeService;
import com.huigesoft.hawk.core.security.service.HawkRegisteredClientRepository;
import com.huigesoft.hawk.core.security.service.HawkTokenStoreService;
import com.huigesoft.hawk.core.security.service.HawkUserDetailService;
import com.huigesoft.hawk.core.security.token.HawkTokenGenerator;
import com.huigesoft.hawk.core.utils.JwksUtils;
import com.huigesoft.hawk.core.web.HawkPermitAllAware;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.annotation.Resource;

/**
 * @ClassName: HawkSecurityConfiguration
 *
 * @Description: TODO(描述这个类的作用)
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 11:39:44
 * 
 */
@AutoConfiguration
@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity(prePostEnabled = true)
public class HawkSecurityConfiguration {

	private Logger logger = LoggerFactory.getLogger(HawkSecurityConfiguration.class);

	@Resource
	private HawkSecurityProperties securityProperties;

	@Resource
	private HawkPermitAllAware hawkPermitAllAware;

	@Bean
	@Order(1)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
//		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
//				OAuth2AuthorizationServerConfigurer
//				.authorizationServer();
		http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher()).with(authorizationServerConfigurer,
				Customizer.withDefaults());
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

		http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable());
		http.exceptionHandling((exceptions) -> exceptions.defaultAuthenticationEntryPointFor(
				new LoginUrlAuthenticationEntryPoint("/login"), new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
				.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));
		return http.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		logger.info("[{}] Enable Resource Server Config", TITLE);
		List<String> permitMatchers = securityProperties.getPermitMatchers();
		permitMatchers.add("/sys/image-code");
		permitMatchers.add("/sys/check-code");
		permitMatchers.add("/sys/login");
		permitMatchers.add("/sys/all");
		permitMatchers.addAll(hawkPermitAllAware.getPermitUrls());
		logger.info("[{}] Permits matchers: {} ", TITLE, permitMatchers);

		String[] permitArr = permitMatchers.toArray(new String[permitMatchers.size()]);
//		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.OPTIONS).permitAll()
//				.requestMatchers(permitArr).permitAll()).formLogin(Customizer.withDefaults())
//				.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
//				.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));

		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.OPTIONS).permitAll()
				.requestMatchers(permitArr).permitAll()).csrf(csrf -> csrf.ignoringRequestMatchers(permitArr))
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//				.authorizeHttpRequests(authorize -> authorize.anyRequest().access(new HawkAccessDecisionManager()))
				.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));
		return http.build();
	}

	@Bean
	RegisteredClientRepository registeredClientRepository() {
		return new HawkRegisteredClientRepository();
	}

//	@Bean
//	JWKSource<SecurityContext> jwkSource() {
//		KeyPair keyPair = generateRsaKey();
//		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//		RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString())
//				.build();
//		JWKSet jwkSet = new JWKSet(rsaKey);
//		jwkSet.toString();
//		return new ImmutableJWKSet<>(jwkSet);
//	}

//	private static KeyPair generateRsaKey() {
//		KeyPair keyPair;
//		try {
//			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//			keyPairGenerator.initialize(2048);
//			keyPair = keyPairGenerator.generateKeyPair();
//		} catch (Exception ex) {
//			throw new IllegalStateException(ex);
//		}
//		return keyPair;
//	}

	@Bean
	JWKSource<SecurityContext> jwkSource(HawkKeyStoreProperties properties) {
		RSAKey rsaKey = JwksUtils.generateRsa(properties);
		JWKSet jwkSet = new JWKSet(rsaKey);
		jwkSet.toString();
		return new ImmutableJWKSet<>(jwkSet);
	}

	@Bean
	JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

	@Bean
	AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().authorizationEndpoint("/" + OAUTH_PREFIX + "/authorize")
				.deviceAuthorizationEndpoint("/" + OAUTH_PREFIX + "/device_authorization")
				.deviceVerificationEndpoint("/" + OAUTH_PREFIX + "/device_verification")
				.tokenEndpoint("/" + OAUTH_PREFIX + "/token").jwkSetEndpoint("/" + OAUTH_PREFIX + "/jwks")
				.tokenRevocationEndpoint("/" + OAUTH_PREFIX + "/revoke")
				.tokenIntrospectionEndpoint("/" + OAUTH_PREFIX + "/introspect").build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new HawkUserDetailService();
	}

	@Bean
	HawkTokenStoreService tokenStoreService() {
		return new HawkTokenStoreService();
	}

	@Bean(name = "hk")
	HawkAuthorizeService authorizeService() {
		return new HawkAuthorizeService();
	}

	@Bean
	HawkTokenGenerator tokenGenerator(JwtEncoder jwtEncoder) {
		return new HawkTokenGenerator(jwtEncoder);
	}

}
