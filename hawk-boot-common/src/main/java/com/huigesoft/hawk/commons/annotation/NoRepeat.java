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
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: NoRepeat
 *
 * @Description: 防重复提交注解
 *
 * @author: 王文辉
 *
 * @date: 2025年9月26日 08:11:46
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NoRepeat {

	/**
	 * 
	 * @Title: timeout
	 *
	 * @Description: 防重有效期（默认2秒，可自定义）
	 * 
	 * @param: @return
	 *
	 * @return: long
	 * 
	 * @date: 2025年9月26日 08:43:49
	 *
	 * @throws
	 */
	long timeout() default 2;

	/**
	 * 
	 * @Title: timeUnit
	 *
	 * @Description: 时间单位（默认秒）
	 * 
	 * @param: @return
	 *
	 * @return: TimeUnit
	 * 
	 * @date: 2025年9月26日 08:44:22
	 *
	 * @throws
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;

	/**
	 * 
	 * @Title: message
	 *
	 * @Description: 防重复提交提示信息
	 * 
	 * @param: @return
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月26日 08:44:38
	 *
	 * @throws
	 */
	String message() default "请勿重复提交，请稍后再试！";

}
