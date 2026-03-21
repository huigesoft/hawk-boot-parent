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
package com.huigesoft.hawk.core.datasource;

import java.util.Map;

import javax.sql.DataSource;

/**
 * @ClassName: IHawkDataSourceRegister
 *
 * @Description: 数据源注册接口
 *
 * @author: 王文辉
 *
 * @date: 2025年10月5日 10:33:57
 * 
 */
public interface IHawkDataSourceRegister {

	/**
	 * 
	 * @Title: registers
	 *
	 * @Description: 批量注册数据源
	 * 
	 * @param: @param dataSources 数据源集合，key为数据源标识，value为数据源对象
	 *
	 * @return: void
	 * 
	 * @date: 2025年10月5日 10:36:26
	 *
	 * @throws
	 */
	public void registers(Map<String, DataSource> dataSources);
}
