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
package com.huigesoft.hawk.commons.criteria;

import java.io.Serializable;

/**
 * 
 * @ClassName: Sort
 *
 * @Description: 查询排序条件构造器
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 10:43:39
 *
 */
public class Sort implements Serializable {

	private static final long serialVersionUID = -1456775962112413199L;

	/**
	 * 排序属性名
	 */
	private String field;

	/**
	 * 排序方向
	 */
	private String direction = "ASC";

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
