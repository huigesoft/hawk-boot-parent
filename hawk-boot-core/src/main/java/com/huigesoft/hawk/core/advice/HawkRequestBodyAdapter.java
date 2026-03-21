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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.huigesoft.hawk.commons.annotation.HttpSecure;
import com.huigesoft.hawk.core.properties.HawkHttpProperties;
import com.huigesoft.hawk.crypto.DES;

import jakarta.annotation.Resource;

/**
 * @ClassName: DecryptAdapter
 *
 * @Description: https://blog.csdn.net/k849875005/article/details/128075413
 *
 * @author: 王文辉
 *
 * @date: 2025年9月9日 09:40:26
 * 
 */
@RestControllerAdvice
public class HawkRequestBodyAdapter extends RequestBodyAdviceAdapter {

	@Resource
	private HawkHttpProperties hawkHttpProperties;

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		HttpSecure httpSecure = null;
		if (methodParameter.hasMethodAnnotation(HttpSecure.class)) {
			httpSecure = methodParameter.getMethodAnnotation(HttpSecure.class);
			return httpSecure.decrypt();
		}
		httpSecure = methodParameter.getDeclaringClass().getAnnotation(HttpSecure.class);
		if (null != httpSecure) {
			return httpSecure.decrypt();
		}
		return false;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		int available = inputMessage.getBody().available();
		if (available <= 0) {
			return inputMessage;
		}
		byte[] dataByte = new byte[available];
		inputMessage.getBody().read(dataByte);
		try {
			String decrypt = DES.decrypt(dataByte, "123456789");
			InputStream is = new ByteArrayInputStream(decrypt.getBytes());
			return new HttpInputMessage() {

				@Override
				public InputStream getBody() throws IOException {
					return is;
				}

				@Override
				public HttpHeaders getHeaders() {
					return inputMessage.getHeaders();
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
	}

}
