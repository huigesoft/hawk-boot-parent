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

package com.huigesoft.hawk.core.mp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.huigesoft.hawk.commons.context.HawkContext;
import com.huigesoft.hawk.core.utils.ServletUtils;

import jakarta.annotation.Resource;

/**
 * @ClassName: HawkMetaObjectHandler
 *
 * @Description: 基础字段自动填充处理器
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 08:47:29
 * 
 */
public class HawkMetaObjectHandler implements MetaObjectHandler {

	@Resource
	private ServletUtils servletUtils;

	@Override
	public void insertFill(MetaObject metaObject) {

		Long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

		String createAt = metaObject.findProperty("createAt", true);
		if (null != createAt) {
			this.strictInsertFill(metaObject, "createAt", () -> now, Long.class);
		}

		String createBy = metaObject.findProperty("createBy", true);
		if (null != createBy) {
			this.strictInsertFill(metaObject, "createBy", () -> HawkContext.getUser(), String.class);
		}

		String createIp = metaObject.findProperty("createIp", true);
		if (null != createIp) {
			this.strictInsertFill(metaObject, "createIp", () -> servletUtils.getIpAddr(), String.class);
		}

		String updateAt = metaObject.findProperty("updateAt", true);
		if (null != updateAt) {
			this.strictInsertFill(metaObject, "updateAt", () -> now, Long.class);
		}

		String updateBy = metaObject.findProperty("updateBy", true);
		if (null != updateBy) {
			this.strictInsertFill(metaObject, "updateBy", () -> HawkContext.getUser(), String.class);
		}

		String updateIp = metaObject.findProperty("updateIp", true);
		if (null != updateIp) {
			this.strictInsertFill(metaObject, "updateIp", () -> servletUtils.getIpAddr(), String.class);
		}

	}

	@Override
	public void updateFill(MetaObject metaObject) {
		Long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

		String updateAt = metaObject.findProperty("updateAt", true);
		if (null != updateAt) {
			this.setFieldValByName("updateAt", now, metaObject);
		}

		String updateBy = metaObject.findProperty("updateBy", true);
		if (null != updateBy) {
			this.setFieldValByName("updateBy", HawkContext.getUser(), metaObject);
		}

		String updateIp = metaObject.findProperty("updateIp", true);
		if (null != updateIp) {
			this.setFieldValByName("updateIp", servletUtils.getIpAddr(), metaObject);
		}
	}
}
