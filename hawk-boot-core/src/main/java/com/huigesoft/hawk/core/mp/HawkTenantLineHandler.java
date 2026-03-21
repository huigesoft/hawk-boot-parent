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

import static com.huigesoft.hawk.commons.constant.Hawk.TENANT_ID;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.huigesoft.hawk.commons.context.HawkContext;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

/**
 * @ClassName: HawkTenantLineHandler
 *
 * @Description: 多租户处理器
 *
 * @author: 王文辉
 *
 * @date: 2025年10月5日 10:05:16
 * 
 */
public class HawkTenantLineHandler implements TenantLineHandler {

	@Override
	public Expression getTenantId() {
		return new StringValue(HawkContext.getTenant());
	}

	@Override
	public String getTenantIdColumn() {
		return TENANT_ID;
	}

	@Override
	public boolean ignoreTable(String tableName) {
		return true;
	}

}
