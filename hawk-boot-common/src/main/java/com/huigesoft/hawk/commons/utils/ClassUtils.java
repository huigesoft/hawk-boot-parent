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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: ClassUtils
 *
 * @Description: Java Class工具类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 11:20:56
 *
 */
public class ClassUtils {

	private static Pattern classNamePattern = Pattern.compile("\\bpublic\\sclass\\s+(\\w+)\\b\\s*\\{");

	private static Pattern packagePattern = Pattern.compile("package\\s+([^\\.\\s]+(\\.[^\\.\\s]+)*);");

	/**
	 * 
	 * @Title: getClassName
	 *
	 * @Description: 根据Java代码获取类名
	 * 
	 * @param: @param  source Java Class 代码
	 * @param: @return 类名
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 11:21:31
	 * 
	 * @throws
	 */
	public static String getClassName(String source) {
		String packageName = "";
		Matcher packageMatcher = packagePattern.matcher(source);
		if (packageMatcher.find()) {
			packageName = packageMatcher.group(1);
		}

		String className = "";
		Matcher classNameMatcher = classNamePattern.matcher(source);
		if (classNameMatcher.find()) {
			className = classNameMatcher.group(1);
		}
		String fullName = packageName + "." + className;
		return fullName;
	}
}
