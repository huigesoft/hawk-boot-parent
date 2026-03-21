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
package com.huigesoft.hawk.core.log;

import java.io.Serializable;

/**
 * @ClassName: OperLog
 *
 * @Description: 操作日志实体
 *
 * @author: 王文辉
 *
 * @date: 2025年9月25日 09:22:55
 * 
 */
public class OperLog implements Serializable {

	private static final long serialVersionUID = 6801964241636547981L;

	/**
	 * 日志ID
	 */
	private String logId;

	/**
	 * 模块
	 */
	private String module;

	/**
	 * 业务类型（0其它 1新增 2修改 3删除）
	 */
	private Integer type;

	/**
	 * 方法名称
	 */
	private String method;

	/**
	 * 请求方式
	 */
	private String httpMethod;

	/**
	 * 操作人员
	 */
	private String operName;

	/**
	 * 请求URL
	 */
	private String operUrl;

	/**
	 * 操作IP
	 */
	private String operIp;

	/**
	 * 操作时间，当前时间戳
	 */
	private Long operTime;

	/**
	 * 执行时间，单位毫秒
	 */
	private Integer execTimes;

	/**
	 * 请求参数
	 */
	private String requestData;

	/**
	 * 返回参数
	 */
	private String responseData;

	/**
	 * 操作状态（0正常 1异常）
	 */
	private Integer status = 0;

	/**
	 * 错误消息
	 */
	private String errorMsg;

	/**
	 * 描述
	 */
	private String description;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperUrl() {
		return operUrl;
	}

	public void setOperUrl(String operUrl) {
		this.operUrl = operUrl;
	}

	public String getOperIp() {
		return operIp;
	}

	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getOperTime() {
		return operTime;
	}

	public void setOperTime(Long operTime) {
		this.operTime = operTime;
	}

	public Integer getExecTimes() {
		return execTimes;
	}

	public void setExecTimes(Integer execTimes) {
		this.execTimes = execTimes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * <p>
	 * Title: toString
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OperLog [logId=" + logId + ", module=" + module + ", type=" + type + ", method=" + method
				+ ", requestMethod=" + httpMethod + ", operName=" + operName + ", operUrl=" + operUrl + ", operIp="
				+ operIp + ", operTime=" + operTime + ", execTimes=" + execTimes + ", requestData=" + requestData
				+ ", responseData=" + responseData + ", status=" + status + ", errorMsg=" + errorMsg + ", description="
				+ description + "]";
	}

}
