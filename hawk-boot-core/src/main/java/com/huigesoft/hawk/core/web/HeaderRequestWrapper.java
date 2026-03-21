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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * @ClassName: HeaderRequestWrapper
 *
 * @Description: Web头部请求包装器
 *
 * @author: 王文辉
 *
 * @date: 2024-08-22 13:52:10
 *
 */
public class HeaderRequestWrapper extends HttpServletRequestWrapper {

	private Map<String, String> headerMap = new HashMap<>();

	public HeaderRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public void addHeader(String name, String value) {
		headerMap.put(name, value);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (headerMap.containsKey(name)) {
			value = headerMap.get(name);
		}
		return value;
	}

//	@Override
//	public Enumeration<String> getHeaders(String name) {
//		List<String> values = Collections.list(super.getHeaderNames());
//		if (headerMap.containsKey(name)) {
//			values = Arrays.asList(headerMap.get(name));
//		}
//		return Collections.enumeration(values);
//	}

	@Override
	public Enumeration<String> getHeaderNames() {
		List<String> names = Collections.list(super.getHeaderNames());
		for (String name : headerMap.keySet()) {
			names.add(name);
		}
		return Collections.enumeration(names);
	}
}
