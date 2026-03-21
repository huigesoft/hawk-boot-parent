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
package com.huigesoft.hawk.commons.http;

import static com.huigesoft.hawk.commons.constant.Hawk.ERROR;
import static com.huigesoft.hawk.commons.constant.Hawk.SUCCESS;

import java.io.Serializable;

/**
 * 
 * @ClassName: Response
 *
 * @Description: Http Servlet 通用返回对象
 *
 * @author: 王文辉
 *
 * @date: 2025年09月01日 10:23:18
 * 
 * @param <T>
 */
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 6241632117616563109L;

	/**
	 * 响应是否成功
	 */
	private boolean success = true;

	/**
	 * 响应提示消息
	 */
	private String msg = SUCCESS;

	/**
	 * 响应原始数据
	 */
	private T data;

	/**
	 * 响应异常信息
	 */
	private Object exception;

	/**
	 * 响应状态
	 */
	private int status = 200;

	public static <T> Response<T> of() {
		return new Response<T>();
	}

	public static <T> Response<T> of(T data) {
		return init(data, true, null, null, 200);
	}

	public static <T> Response<T> of(T data, int status) {
		return init(data, true, null, null, status);
	}

	public static <T> Response<T> of(T data, boolean success) {
		return init(data, success, null, null, 200);
	}

	public static <T> Response<T> of(T data, boolean success, int status) {
		return init(data, success, null, null, status);
	}

	public static <T> Response<T> of(T data, String msg) {
		return init(data, true, msg, null, 200);
	}

	public static <T> Response<T> of(T data, String msg, int status) {
		return init(data, true, msg, null, status);
	}

	public static <T> Response<T> of(T data, boolean success, String msg) {
		return init(data, success, msg, null, 200);
	}

	public static <T> Response<T> of(T data, boolean success, String msg, int status) {
		return init(data, success, msg, null, status);
	}

	public static <T> Response<T> of(T data, boolean success, String msg, Exception exception) {
		return init(data, success, msg, exception, 200);
	}

	public static <T> Response<T> of(T data, boolean success, String msg, Exception exception, int status) {
		return init(data, success, msg, exception, status);
	}

	public static <T> Response<T> err(String msg, Exception exception) {
		return init(null, false, msg, exception, 500);
	}

	public static <T> Response<T> err(String msg, Exception exception, int status) {
		return init(null, false, msg, exception, status);
	}

	public static <T> Response<T> err(Exception exception) {
		return init(null, false, ERROR, exception, 500);
	}

	public static <T> Response<T> err(int status, Exception exception) {
		return init(null, false, ERROR, exception, 500);
	}

	public static <T> Response<T> err(Exception exception, int status) {
		return init(null, false, ERROR, exception, status);
	}

	private static <T> Response<T> init(T data, boolean success, String msg, Object exception, int status) {
		Response<T> response = new Response<>();
		if (null != data) {
			response.setData(data);
		}
		response.setSuccess(success);
		response.setStatus(status);
		if (null != msg) {
			response.setMsg(msg);
		}
		if (null != exception) {
			if (exception instanceof Exception) {
				response.setException(((Exception) exception).getMessage());
			} else {
				response.setException(exception);
			}
		}
		return response;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Object getException() {
		return exception;
	}

	public void setException(Object exception) {
		this.exception = exception;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
