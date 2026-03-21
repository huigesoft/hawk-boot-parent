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
package com.huigesoft.hawk.commons.enums;

/**
 * @ClassName: HKOperType
 *
 * @Description: 操作类型
 *
 * @author: 王文辉
 *
 * @date: 2025年9月29日 08:32:48
 * 
 */
public enum HKOperType {

	OTHER(0, "其他"), ADD(1, "新增"), DELETE(2, "删除"), UPDATE(3, "修改"), QUERY(4, "查询"), IMPORT(5, "导入"), EXPORT(6, "导入"),
	GENERATE(7, "生成");

	private int value;

	private String name;

	private HKOperType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
