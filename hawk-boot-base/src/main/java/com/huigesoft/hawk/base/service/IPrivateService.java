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
package com.huigesoft.hawk.base.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.huigesoft.hawk.commons.criteria.Between;
import com.huigesoft.hawk.commons.criteria.Criteria;
import com.huigesoft.hawk.commons.criteria.Expression;
import com.huigesoft.hawk.commons.criteria.Sort;
import com.huigesoft.hawk.commons.enums.HkQuery;
import com.huigesoft.hawk.commons.function.HkFunctionTwo;

/**
 * @ClassName: IPrivateService
 *
 * @Description: 平台私有化默认实现服务
 *
 * @author: 王文辉
 *
 * @date: 2025年9月24日 15:07:07
 * 
 */
public sealed interface IPrivateService<T> permits IBaseService {
	static ISqlRunner sqlRunner = new SqlRunner();

	default QueryWrapper<T> criteria2Wrapper(Criteria criteria) {
		QueryWrapper<T> wrapper = new QueryWrapper<T>();
		wrapper.checkSqlInjection();
		if (null != criteria) {
			createExpressions(criteria.getExpressions(), wrapper);
			createBetweens(criteria.getBetweens(), wrapper);
			createSorts(criteria.getSorts(), wrapper);
		}
		return wrapper;
	}

	private void createExpressions(List<Expression> expressions, QueryWrapper<T> wrapper) {
		if (null != expressions) {
			for (Iterator<Expression> iter = expressions.iterator(); iter.hasNext();) {
				Expression expression = iter.next();
				Object value = expression.getValue();
				if (!ObjectUtils.isEmpty(value)) {
					String logic = expression.getLogic();
					if ("or".equalsIgnoreCase(logic)) {
						wrapper.or();
					}
					HkFunctionTwo<String, Object, QueryWrapper<T>> condition = createCondition(expression.getQuery(),
							wrapper);
					if (HkQuery.in == expression.getQuery()) {
						if (value instanceof Collection values) {
							wrapper.in(StringUtils.camelToUnderline(expression.getField()), values.toArray());
						}
					} else if (HkQuery.notIn == expression.getQuery()) {
						if (value instanceof Collection values) {
							wrapper.notIn(StringUtils.camelToUnderline(expression.getField()), values.toArray());
						}
					} else {
						condition.apply(StringUtils.camelToUnderline(expression.getField()), expression.getValue());
					}
				}
			}
		}
	}

	private void createBetweens(List<Between> betweens, QueryWrapper<T> wrapper) {
		if (null != betweens) {
			for (Iterator<Between> iter = betweens.iterator(); iter.hasNext();) {
				Between between = iter.next();
				wrapper.between(StringUtils.camelToUnderline(between.getField()), between.getBeginValue(),
						between.getEndValue());
				if ("or".equalsIgnoreCase(between.getLogic())) {
					wrapper.or();
				}
			}
		}
	}

	private void createSorts(List<Sort> sorts, QueryWrapper<T> wrapper) {
		if (null != sorts) {
			for (Iterator<Sort> iter = sorts.iterator(); iter.hasNext();) {
				Sort sort = iter.next();
				if ("asc".equalsIgnoreCase(sort.getDirection())) {
					wrapper.orderByAsc(StringUtils.camelToUnderline(sort.getField()));
				} else {
					wrapper.orderByDesc(StringUtils.camelToUnderline(sort.getField()));
				}
			}
		}
	}

	private HkFunctionTwo<String, Object, QueryWrapper<T>> createCondition(HkQuery query, QueryWrapper<T> wrapper) {
		return switch (query) {
		case like -> wrapper::like;
		case eq -> wrapper::eq;
		case ne -> wrapper::ne;
		case gt -> wrapper::gt;
		case ge -> wrapper::ge;
		case lt -> wrapper::lt;
		case le -> wrapper::le;
		case notLike -> wrapper::notLike;
		case likeLeft -> wrapper::likeLeft;
		case likeRight -> wrapper::likeRight;
		case notLikeLeft -> wrapper::notLikeLeft;
		case notLikeRight -> wrapper::notLikeRight;
		case in -> wrapper::in;
		case notIn -> wrapper::notIn;
		default -> wrapper::like;
		};
	}

}
