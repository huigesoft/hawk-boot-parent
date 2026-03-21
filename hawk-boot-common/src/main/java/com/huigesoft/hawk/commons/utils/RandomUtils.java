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
package com.huigesoft.hawk.commons.utils;

import java.util.Random;

/**
 * 
 * @ClassName: RandomUtils
 *
 * @Description: 随机数工具类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 11:29:06
 *
 */
public class RandomUtils {

	private static String str = "1234567890abcdefghijklmnopqrstuvwxyZABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*";

	/**
	 * 
	 * @Title: randomString
	 *
	 * @Description: 获取随机字符串
	 * 
	 * @param: @param  len 随机字符串长度
	 * @param: @return
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 11:29:22
	 * 
	 * @throws
	 */
	public static String randomString(int len) {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int num = random.nextInt(70);
			buffer.append(str.charAt(num));
		}
		return buffer.toString();
	}
}
