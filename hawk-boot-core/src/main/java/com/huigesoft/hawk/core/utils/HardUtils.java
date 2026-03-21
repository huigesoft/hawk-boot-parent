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

import oshi.SystemInfo;

/**
 * 
 * @ClassName: HardUtils
 *
 * @Description: 硬件信息工具类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:23:17
 *
 */
public class HardUtils {

	private static SystemInfo si = new SystemInfo();

	/**
	 * 
	 * @Title: getHardwareId
	 *
	 * @Description: 获取机器唯一序列号
	 * 
	 * @param: @return
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 13:23:41
	 * 
	 * @throws
	 */
	public static String getHardwareId() {
		return si.getHardware().getComputerSystem().getHardwareUUID();
	}

	/**
	 * 
	 * @Title: getLogicalProcessorCount
	 *
	 * @Description: 获取CPU逻辑核心数
	 * 
	 * @param: @return
	 *
	 * @return: int
	 * 
	 * @date: 2025年7月28日 16:02:30
	 *
	 * @throws
	 */
	public static int getLogicalProcessorCount() {
		return si.getHardware().getProcessor().getLogicalProcessorCount();
	}

	/**
	 * 
	 * @Title: getPhysicalProcessorCount
	 *
	 * @Description: 获取CPU物理核心数
	 * 
	 * @param: @return
	 *
	 * @return: int
	 * 
	 * @date: 2025年7月28日 16:02:57
	 *
	 * @throws
	 */
	public static int getPhysicalProcessorCount() {
		return si.getHardware().getProcessor().getPhysicalProcessorCount();
	}
}
