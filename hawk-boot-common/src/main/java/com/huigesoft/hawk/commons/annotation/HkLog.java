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
package com.huigesoft.hawk.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.huigesoft.hawk.commons.enums.HKOperType;

/**
 * @ClassName: HkLog
 *
 * @Description: 操作日志注解
 *
 * @author: 王文辉
 *
 * @date: 2025年9月25日 09:12:38
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HkLog {

	/**
	 * 
	 * @Title: module
	 *
	 * @Description: 模块
	 * 
	 * @param: @return
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月25日 09:19:42
	 *
	 * @throws
	 */
	String module() default ""; // 模块

	/**
	 * 
	 * @Title: HKOperType
	 *
	 * @Description: 业务类型
	 * 
	 * @param: @return
	 *
	 * @return: int
	 * 
	 * @date: 2025年9月25日 09:20:08
	 *
	 * @throws
	 */
	HKOperType type() default HKOperType.OTHER;

	/**
	 * 
	 * @Title: saveRequestData
	 *
	 * @Description: 是否保存请求的参数
	 * 
	 * @param: @return
	 *
	 * @return: boolean
	 * 
	 * @date: 2025年9月25日 09:20:26
	 *
	 * @throws
	 */
	boolean saveRequestData() default true;

	/**
	 * 
	 * @Title: saveResponseData
	 *
	 * @Description: 是否保存响应的参数
	 * 
	 * @param: @return
	 *
	 * @return: boolean
	 * 
	 * @date: 2025年9月25日 09:20:38
	 *
	 * @throws
	 */
	boolean saveResponseData() default true;

	/**
	 * 
	 * @Title: description
	 *
	 * @Description: 描述
	 * 
	 * @param: @return
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月25日 09:20:51
	 *
	 * @throws
	 */
	String desc() default "";

}
