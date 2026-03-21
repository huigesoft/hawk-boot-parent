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
package com.huigesoft.hawk.core.aspect;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.huigesoft.hawk.commons.annotation.HkBiz;
import com.huigesoft.hawk.commons.annotation.HkLog;
import com.huigesoft.hawk.commons.context.HawkContext;
import com.huigesoft.hawk.commons.utils.DateUtils;
import com.huigesoft.hawk.core.log.ILogStorage;
import com.huigesoft.hawk.core.log.OperLog;
import com.huigesoft.hawk.core.utils.JsonUtils;
import com.huigesoft.hawk.core.utils.ServletUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @ClassName: LogAspect
 *
 * @Description: 操作日志切面
 *
 * @author: 王文辉
 *
 * @date: 2025年9月25日 09:29:46
 * 
 */
@Aspect
public class LogAspect {

	private ServletUtils servletUtils;

	private IdentifierGenerator identifierGenerator = null;

	private ILogStorage logStorage;

	public LogAspect(ILogStorage logStorage, ServletUtils servletUtils) {
		this.logStorage = logStorage;
		this.servletUtils = servletUtils;
		try {
			identifierGenerator = new DefaultIdentifierGenerator(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			identifierGenerator = new DefaultIdentifierGenerator(new Sequence(1, 1));
		}
	}

	@Pointcut(value = "@annotation(com.huigesoft.hawk.commons.annotation.HkLog)")
	private void pointCut() {
	}

	@Around(value = "pointCut()")
	public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {

		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();

		if (!method.isAnnotationPresent(HkLog.class)) {
			return pjp.proceed();
		}

		HkLog log = method.getAnnotation(HkLog.class);

		HttpServletRequest request = servletUtils.getRequest();
		OperLog operLog = new OperLog();
		if (StringUtils.hasText(log.module())) {
			operLog.setModule(log.module());
		} else {
			Class<?> clazz = pjp.getTarget().getClass();
			if (clazz.isAnnotationPresent(HkBiz.class)) {
				HkBiz biz = clazz.getAnnotation(HkBiz.class);
				operLog.setModule(biz.module());
			}
		}
		operLog.setLogId(identifierGenerator.nextId(operLog).toString());
		operLog.setType(log.type().getValue());
		operLog.setMethod(method.getName());
		operLog.setHttpMethod(request.getMethod());
		operLog.setOperName(HawkContext.getUser());
		operLog.setOperUrl(request.getRequestURI());
		operLog.setOperIp(servletUtils.getIpAddr());
		operLog.setDescription(log.desc());

		if (log.saveRequestData()) {
			String params = JsonUtils.object2Json(pjp.getArgs());
			operLog.setRequestData(params);
		}
		Instant start = Instant.now();
		try {
			Object data = pjp.proceed();
			if (log.saveResponseData()) {
				operLog.setResponseData(JsonUtils.object2Json(data));
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			operLog.setStatus(1);
			operLog.setErrorMsg(e.getMessage());
			throw e;
		} finally {
			operLog.setOperTime(start.toEpochMilli());
			Instant end = Instant.now();
			Long times = DateUtils.between(start, end);
			operLog.setExecTimes(times.intValue());
			logStorage.save(operLog);
		}

	}

}
