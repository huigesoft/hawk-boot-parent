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
package com.huigesoft.hawk.commons.context;

/**
 * 
 * @ClassName: HawkContext
 *
 * @Description: 平台上下文类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 10:08:39
 *
 */
public class HawkContext {

	/**
	 * 用户名上下文对象
	 */
	private static ThreadLocal<String> userLocal = new ThreadLocal<>();

	/**
	 * 租户上下文对象
	 */
	private static ThreadLocal<String> tenantLocal = new ThreadLocal<>();

	/**
	 * 
	 * @Title: setUser
	 *
	 * @Description: 设置当前线程的用户信息
	 * 
	 * @param: @param user 用户名
	 *
	 * @return: void
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2025-08-22 10:09:51
	 * 
	 * @throws
	 */
	public static void setUser(String user) {
		userLocal.set(user);
	}

	/**
	 * 
	 * @Title: getUser
	 *
	 * @Description: 获取当前线程的用户信息
	 * 
	 * @param: @return 用户名
	 *
	 * @return: String 用户名
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2025-08-22 10:10:18
	 * 
	 * @throws
	 */
	public static String getUser() {
		return userLocal.get();
	}

	/**
	 * 
	 * @Title: setTenant
	 *
	 * @Description: 设置当前线程的租户信息
	 * 
	 * @param: @param tenant 租户
	 *
	 * @return: void
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2025-08-22 10:10:50
	 * 
	 * @throws
	 */
	public static void setTenant(String tenant) {
		tenantLocal.set(tenant);
	}

	/**
	 * 
	 * @Title: getTenant
	 *
	 * @Description: 获取当前线程的租户信息
	 * 
	 * @param: @return 租户信息
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2025-08-22 10:11:16
	 * 
	 * @throws
	 */
	public static String getTenant() {
		return tenantLocal.get();
	}

	/**
	 * 
	 * @Title: clear
	 *
	 * @Description: 清除当前线程的上下文信息
	 * 
	 * @param: 无
	 *
	 * @return: void
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2025-08-22 10:11:42
	 * 
	 * @throws
	 */
	public static void clear() {
		userLocal.set(null);
		tenantLocal.set(null);
	}
}
