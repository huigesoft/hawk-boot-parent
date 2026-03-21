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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.huigesoft.hawk.commons.annotation.NoRepeat;
import com.huigesoft.hawk.commons.cache.SingleCache;
import com.huigesoft.hawk.commons.exception.RepeatException;
import com.huigesoft.hawk.core.utils.ServletUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @ClassName: RepeatAspect
 *
 * @Description: 防重复提交切面
 *
 * @author: 王文辉
 *
 * @date: 2025年9月26日 08:47:05
 * 
 */
@Aspect
public class RepeatAspect {

	private SingleCache cache = SingleCache.newInstance();

	@Resource
	private ServletUtils servletUtils;

	@Pointcut(value = "@annotation(com.huigesoft.hawk.commons.annotation.NoRepeat)")
	private void pointCut() {
	}

	@Around("pointCut()")
	public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {

		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();

		if (!method.isAnnotationPresent(NoRepeat.class)) {
			return pjp.proceed();
		}

		NoRepeat noRepeat = method.getAnnotation(NoRepeat.class);

		HttpServletRequest request = servletUtils.getRequest();
		String token = request.getHeader("Authorization");
		String uri = request.getRequestURI();
		if (null != token) {
			token = request.getRemoteAddr() + request.getRemotePort();
		}

		String key = uri + "_" + token;
		if (null != cache.get(key)) {
			throw new RepeatException(noRepeat.message());
		}
		cache.put(key, key, noRepeat.timeout(), noRepeat.timeUnit());
		return pjp.proceed();
	}
}
