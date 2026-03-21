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
package com.huigesoft.hawk.core.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName: ServletUtils
 *
 * @Description: Servlet 工具类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:30:26
 *
 */
public class ServletUtils {

	/**
	 * 
	 * @Title: getRequest
	 *
	 * @Description: 获取当前请求的HttpServletRequest实例
	 * 
	 * @param: @return
	 *
	 * @return: HttpServletRequest
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 13:30:38
	 * 
	 * @throws
	 */
	public HttpServletRequest getRequest() {
		if (null == RequestContextHolder.getRequestAttributes()) {
			return null;
		}
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	/**
	 * 
	 * @Title: getIpAddr
	 *
	 * @Description: 获取客户端IP
	 * 
	 * @param: @return
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 13:30:59
	 * 
	 * @throws
	 */
	public String getIpAddr() {
		HttpServletRequest request = getRequest();
		if (null != request) {
			String ip = request.getHeader("X-Real-IP");
			if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
			ip = request.getHeader("X-Forwarded-For");
			if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
				// 多次反向代理后会有多个IP值，第一个为真实IP。
				int index = ip.indexOf(',');
				if (index != -1) {
					return ip.substring(0, index);
				} else {
					return ip;
				}
			} else {
				ip = request.getRemoteAddr();
				return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
			}
		}
		return "Default";
	}

	/**
	 * 
	 * @Title: getBrowser
	 *
	 * @Description: 获取客户端浏览器信息
	 * 
	 * @param: @return
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 13:31:09
	 * 
	 * @throws
	 */
	public String getBrowser() {
		HttpServletRequest request = getRequest();
		if (null == request) {
			return "Default";
		}
		String ua = request.getHeader("User-Agent");
		return ua;
//		if (ua.contains("Chrome")) {
//			return "Chrome";
//		} else if (ua.contains("Edge")) {
//			return "Microsoft Edge";
//		} else if (ua.contains("Firefox")) {
//			return "Firefox";
//		} else if (ua.contains("Safari") && !ua.contains("Chrome")) {
//			return "Safari";
//		} else if (ua.contains("MSIE") || ua.contains("Trident/")) {
//			return "Internet Explorer";
//		} else if (ua.contains("Opera") || ua.contains("OPR")) {
//			return "Opera";
//		} else {
//			return "Unknown";
//		}
	}
}