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
package com.huigesoft.hawk.commons.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * 
 * @ClassName: HkFunctionTwo
 *
 * @Description: 两个参数函数引用对象
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 10:54:21
 * 
 * @param <T> 参数一类型
 * @param <U> 参数二类型
 * @param <R> 返回值类型
 */
@FunctionalInterface
public interface HkFunctionTwo<T, U, R> {

	R apply(T t, U u);

	default <V> HkFunctionTwo<T, U, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> after.apply(apply(t, u));
	}
}