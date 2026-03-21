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
import java.util.List;

/**
 * 
 * @ClassName: Criteria
 *
 * @Description: 查询条件构造器
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 10:47:36
 *
 */
public class Criteria implements Serializable {

	private static final long serialVersionUID = -8726361878748277405L;

	/**
	 * 当前页数
	 */
	private int page = 0;

	/**
	 * 每页条数
	 */
	private int size = 20;

	/**
	 * 通用查询条件表达式集合
	 */
	private List<Expression> expressions;

	/**
	 * Between查询条件集合
	 */
	private List<Between> betweens;

	/**
	 * 排序条件集合
	 */
	private List<Sort> sorts;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Expression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}

	public List<Sort> getSorts() {
		return sorts;
	}

	public void setSorts(List<Sort> sorts) {
		this.sorts = sorts;
	}

	public List<Between> getBetweens() {
		return betweens;
	}

	public void setBetweens(List<Between> betweens) {
		this.betweens = betweens;
	}

}
