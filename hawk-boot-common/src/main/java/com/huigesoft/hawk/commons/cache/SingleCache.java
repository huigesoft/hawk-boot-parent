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
package com.huigesoft.hawk.commons.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.huigesoft.hawk.commons.utils.SingleCacheUtils;

/**
 * @ClassName: SingleCache
 *
 * @Description: 简单本地缓存
 *
 * @author: 王文辉
 *
 * @date: 2025年9月26日 09:06:04
 * 
 */
public class SingleCache {

	private final Map<String, Object> datas = new ConcurrentHashMap<>();

	private final Map<String, Future<?>> futures = new ConcurrentHashMap<>();

	private final ScheduledExecutorService executor = Executors
			.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());

	private SingleCache() {
	}

	public static SingleCache newInstance() {
		return new SingleCache();
	}

	public synchronized void put(String key, Object data) {
		put(key, data, 0);
	}

	/**
	 * 添加缓存 过期时间: 单位毫秒, 0表示无限长
	 */
	public synchronized void put(String key, Object value, long expire) {
		// 清除原键值对
		remove(key);
		// 设置过期时间
		if (expire > 0) {
			Future<?> future = executor.schedule(() -> {
				// 过期后清除该键值对
				synchronized (SingleCacheUtils.class) {
					remove(key);
				}
			}, expire, TimeUnit.MILLISECONDS);
			datas.put(key, value);
			futures.put(key, future);
		} else {
			// 不设置过期时间
			datas.put(key, value);
		}
	}

	public synchronized void put(String key, Object value, long expire, TimeUnit timeUnit) {
		// 清除原键值对
		remove(key);
		// 设置过期时间
		if (expire > 0) {
			Future<?> future = executor.schedule(() -> {
				// 过期后清除该键值对
				synchronized (SingleCacheUtils.class) {
					remove(key);
				}
			}, expire, timeUnit);
			datas.put(key, value);
			futures.put(key, future);
		} else {
			// 不设置过期时间
			datas.put(key, value);
		}
	}

	public synchronized Object get(String key) {
		return datas.get(key);
	}

	public Set<String> keys() {
		return datas.keySet();
	}

	public synchronized Object remove(String key) {
		// 清除指定缓存数据
		Object value = datas.remove(key);
		Future<?> future = futures.remove(key);
		if (null != future) {
			future.cancel(true);
			future = null;
		}
		return value;
	}

	/**
	 * 清除所有缓存
	 */
	public synchronized void clear() {
		datas.clear();
		Iterator<Future<?>> iter = futures.values().iterator();
		if (iter.hasNext()) {
			iter.next().cancel(true);
		}
		futures.clear();
	}

	/**
	 * 查询当前缓存的键值对数量
	 */
	public synchronized int size() {
		return datas.size();
	}
}
