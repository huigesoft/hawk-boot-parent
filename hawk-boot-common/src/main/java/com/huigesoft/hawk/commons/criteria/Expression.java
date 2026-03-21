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

import com.huigesoft.hawk.commons.enums.HkQuery;

/**
 * 
 * @ClassName: Expression
 *
 * @Description: 查询表达式构造对象
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 10:42:07
 *
 */
public class Expression implements Serializable {

	private static final long serialVersionUID = 5730326247718134000L;

	/**
	 * 逻辑操作，如： eq,like,lt,gt等
	 */
	private HkQuery query = HkQuery.like;

	/**
	 * 属性名，实体的属性名
	 */
	private String field;

	/**
	 * 属性值
	 */
	private Object value;

	/**
	 * 处理逻辑， and,or
	 */
	private String logic = "and";

	public HkQuery getQuery() {
		return query;
	}

	public void setQuery(HkQuery query) {
		this.query = query;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}
}
