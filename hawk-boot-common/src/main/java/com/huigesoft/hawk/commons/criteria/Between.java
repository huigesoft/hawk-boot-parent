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
 * @ClassName: Between
 *
 * @Description: Between条件构造器
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 10:38:05
 *
 */
public class Between implements Serializable {

	/**
	 * @Fields serialVersionUID : 序列号ID
	 */
	private static final long serialVersionUID = 6716243681627015626L;

	/**
	 * 逻辑操作，如： eq,like,lt,gt等
	 */
	private String opers;

	/**
	 * 属性名，实体的属性名
	 */
	private String field;

	/**
	 * 开始值
	 */
	private Object beginValue;

	/**
	 * 结束值
	 */
	private Object endValue;

	/**
	 * 处理逻辑， and,or
	 */
	private String logic = "and";

	/**
	 * 是否不在条件内
	 */
	private Boolean not = false;

	public String getOpers() {
		return opers;
	}

	public void setOpers(String opers) {
		this.opers = opers;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getBeginValue() {
		return beginValue;
	}

	public void setBeginValue(Object beginValue) {
		this.beginValue = beginValue;
	}

	public Object getEndValue() {
		return endValue;
	}

	public void setEndValue(Object endValue) {
		this.endValue = endValue;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public Boolean getNot() {
		return not;
	}

	public void setNot(Boolean not) {
		this.not = not;
	}

}
