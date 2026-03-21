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
package com.huigesoft.hawk.core.security.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huigesoft.hawk.core.utils.SpringContextHolder;

/**
 * @ClassName: HawkPermissionCache
 *
 * @Description: 权限缓存
 *
 * @author: 王文辉
 *
 * @date: 2025年11月13日 09:49:58
 * 
 */
public class HawkPermissionCache {

	private static Map<String, List<String>> PERMISSIONS = new ConcurrentHashMap<>();

	public static void setPermissions(Map<String, List<String>> permissions) {
		PERMISSIONS = permissions;
	}

	public static boolean accessDecision(String permission, List<?> userRoles) {
		List<String> roles = PERMISSIONS.get(permission);
		if (roles == null) {
			return true;
		}
		for (int i = 0; i < userRoles.size(); i++) {
			String role = String.valueOf(userRoles.get(i));
			if (roles.contains(role)) {
				return true;
			}
		}
		return false;
	}

	public static void refresh() {
		JdbcTemplate jdbcTemplate = SpringContextHolder.getBean(JdbcTemplate.class);
		String sql = "select role_id ,menu_id from hk_role_menu where exists (select 1 from hk_role where hk_role.role_status = 1 and hk_role_menu.role_id = hk_role.role_id) ";
		jdbcTemplate.query(sql, rs -> {
			Map<String, List<String>> permissions = new ConcurrentHashMap<>();
			do {
				String roleId = rs.getString("role_id");
				String menuId = rs.getString("menu_id");
				permissions.computeIfAbsent(menuId, _ -> new ArrayList<>()).add(roleId);
			} while (rs.next());
			setPermissions(permissions);
		});
	}
}
