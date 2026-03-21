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
package com.huigesoft.hawk.core.cache;

import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.huigesoft.hawk.commons.domain.HawkToken;
import com.huigesoft.hawk.commons.utils.SingleCacheUtils;
import com.huigesoft.hawk.core.security.service.HawkTokenStoreService;
import com.huigesoft.hawk.core.utils.SpringContextHolder;

/**
 * 
 * @ClassName: HawkTokenCache
 *
 * @Description: Token 缓存管理
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 08:40:54
 *
 */
public class HawkTokenCache {

	private static HawkTokenStoreService storeService = null;

	private final static Map<String, HawkToken> datas = new ConcurrentHashMap<>();

	private final static Map<String, Future<?>> futures = new ConcurrentHashMap<>();

	private final static ScheduledExecutorService executor = Executors
			.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());

	public synchronized static void put(HawkToken token) {
		String key = token.getToken();
		long expireTime = token.getExpireTime();
		long now = Instant.now().toEpochMilli();
		long expire = expireTime - now;
		if (expire > 0) {
			Future<?> future = executor.schedule(() -> {
				// 过期后清除该键值对
				synchronized (SingleCacheUtils.class) {
					remove(key);
				}
			}, expire, TimeUnit.MILLISECONDS);
			datas.put(key, token);
			futures.put(key, future);
			getStoreService().saveToken(key, token);
		}
	}

	public synchronized static void put(HawkToken token, int size) {
		put(token);
		String username = token.getUsername();
		List<HawkToken> tokens = datas.values().stream().filter(value -> username.equals(value.getUsername()))
				.sorted(Comparator.comparingLong(HawkToken::getExpireTime)).collect(Collectors.toList());
		int len = tokens.size() - size;
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				HawkToken tk = tokens.get(i);
				remove(tk.getToken());
			}
		}
	}

	public synchronized static void putAll(List<HawkToken> tokens) {
		tokens.forEach(token -> {
			put(token);
		});
	}

	public synchronized static HawkToken get(String key) {
		HawkToken token = datas.get(key);
		if (null == token) {
			return getStoreService().getToken(key);
		}
		return token;
	}

	public synchronized static Collection<HawkToken> getAll() {
		return datas.values();
	}

	public static Set<String> keys() {
		return datas.keySet();
	}

	public static int size() {
		return datas.size();
	}

	public synchronized static HawkToken remove(String key) {
		getStoreService().removeToken(key);
		// 清除指定缓存数据
		HawkToken value = datas.remove(key);
		Future<?> future = futures.remove(key);
		if (null != future) {
			future.cancel(true);
			future = null;
		}
		return value;
	}

	public synchronized static void initToken() {
		Map<String, HawkToken> tokens = getStoreService().getAllToken();
		tokens.entrySet().forEach(entry -> {
			String key = entry.getKey();
			HawkToken token = entry.getValue();
			long expireTime = token.getExpireTime();
			long now = Instant.now().toEpochMilli();
			long expire = expireTime - now;
			if (expire > 0) {
				Future<?> future = executor.schedule(() -> {
					// 过期后清除该键值对
					synchronized (SingleCacheUtils.class) {
						remove(key);
					}
				}, expire, TimeUnit.MILLISECONDS);
				datas.put(key, token);
				futures.put(key, future);
			} else {
				getStoreService().removeToken(key);
			}
		});

	}

	public synchronized static void clearExpireToken() {
		long now = Instant.now().toEpochMilli();
		datas.entrySet().forEach(entry -> {
			if (entry.getValue().getExpireTime() <= now) {
				remove(entry.getKey());
			}
		});
	}

	private static HawkTokenStoreService getStoreService() {
		if (null == storeService) {
			storeService = SpringContextHolder.getBean(HawkTokenStoreService.class);
		}
		return storeService;
	}
}
