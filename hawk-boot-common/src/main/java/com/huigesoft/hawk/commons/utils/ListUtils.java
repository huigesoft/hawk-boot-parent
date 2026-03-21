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
package com.huigesoft.hawk.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @ClassName: ListUtils
 *
 * @Description: 集合工具类
 *
 * @author: 王文辉
 *
 * @date: 2025年7月24日 13:05:52
 * 
 */
public class ListUtils {

	/**
	 * 
	 * @Title: intersectionByKey
	 *
	 * @Description: 计算两个集合的交集
	 * 
	 * @param: @param  <T> 泛型
	 * @param: @param  <K> 返回类型
	 * @param: @param  list1 第一个集合
	 * @param: @param  list2 第二个集合
	 * @param: @param  keyExtractor 唯一性字段
	 * @param: @return
	 *
	 * @return: List<T>
	 * 
	 * @date: 2025年7月24日 13:52:18
	 *
	 * @throws
	 */
	public synchronized static <T, K> List<T> intersectionByKey(List<T> list2, List<T> list1,
			Function<T, K> keyExtractor) {

		Map<K, Integer> frequencyMap = new HashMap<>();
		list2.forEach(item -> {
			K key = keyExtractor.apply(item);
			frequencyMap.put(key, frequencyMap.getOrDefault(key, 0) + 1);
		});

		List<T> result = new ArrayList<>();
		list1.forEach(item -> {
			K key = keyExtractor.apply(item);
			if (frequencyMap.containsKey(key) && frequencyMap.get(key) > 0) {
				result.add(item);
				frequencyMap.put(key, frequencyMap.get(key) - 1);
			}
		});

		return result;
	}

	/**
	 * 
	 * @Title: differenceByKey
	 *
	 * @Description: 计算两个集合的差值（List1 - List2）
	 * 
	 * @param: @param  <T> 泛型
	 * @param: @param  <K> 返回类型
	 * @param: @param  list1 第一个集合
	 * @param: @param  list2 第二个集合
	 * @param: @param  keyExtractor 唯一性字段
	 * @param: @return
	 *
	 * @return: List<T>
	 * 
	 * @date: 2025年5月7日 13:53:14
	 *
	 * @throws
	 */
	public static <T, K> List<T> differenceByKey(List<T> list1, List<T> list2, Function<T, K> keyExtractor) {

		Map<K, Integer> frequencyMap = new HashMap<>();
		// 统计 list2 的键频率
		for (T item : list2) {
			K key = keyExtractor.apply(item);
			frequencyMap.put(key, frequencyMap.getOrDefault(key, 0) + 1);
		}

		List<T> result = new ArrayList<>();
		// 遍历 list1，构建差集
		for (T item : list1) {
			K key = keyExtractor.apply(item);
			if (frequencyMap.containsKey(key)) {
				int count = frequencyMap.get(key);
				if (count > 0) {
					frequencyMap.put(key, count - 1); // 减少次数
				} else {
					result.add(item); // 次数已耗尽，加入差集
				}
			} else {
				result.add(item); // 不在 list2 中，加入差集
			}
		}
		return result;
	}

}
