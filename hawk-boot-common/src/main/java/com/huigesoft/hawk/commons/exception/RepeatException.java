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
package com.huigesoft.hawk.commons.exception;

/**
 * @ClassName: RepeatException
 *
 * @Description: 重复提交异常处理类
 *
 * @author: 王文辉
 *
 * @date: 2025年11月5日 09:41:06
 * 
 */
public class RepeatException extends Exception {

	private static final long serialVersionUID = 689364137584184090L;

	public RepeatException() {
		super();
	}

	public RepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RepeatException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatException(String message) {
		super(message);
	}

	public RepeatException(Throwable cause) {
		super(cause);
	}

}
