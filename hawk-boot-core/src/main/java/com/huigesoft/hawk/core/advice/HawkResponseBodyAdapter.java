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
package com.huigesoft.hawk.core.advice;

import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.huigesoft.hawk.commons.annotation.HttpSecure;
import com.huigesoft.hawk.core.utils.JsonUtils;

/**
 * @ClassName: HawkResponseAdvice
 *
 * @Description: Spring Controller 返回结果处理
 *
 * @author: 王文辉
 *
 * @date: 2025年9月10日 14:46:23
 * 
 */
@RestControllerAdvice
public class HawkResponseBodyAdapter implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		HttpSecure httpSecure = null;
		if (returnType.hasMethodAnnotation(HttpSecure.class)) {
			httpSecure = returnType.getMethodAnnotation(HttpSecure.class);
			return httpSecure.encrypt();
		}
		httpSecure = returnType.getDeclaringClass().getAnnotation(HttpSecure.class);
		if (null != httpSecure) {
			return httpSecure.encrypt();
		}
		return false;
	}

	@Override
	public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		Class<?> clazz = body.getClass();
		String data = null;
		if (clazz.isPrimitive() || body instanceof String) {
			data = String.valueOf(body);
		}
		return JsonUtils.object2Json(data);
	}
}
