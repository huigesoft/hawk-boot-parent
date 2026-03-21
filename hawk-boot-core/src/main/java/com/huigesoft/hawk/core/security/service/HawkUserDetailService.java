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
package com.huigesoft.hawk.core.security.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.huigesoft.hawk.core.domain.User;

/**
 * 
 * @ClassName: HawkUserDetailService
 *
 * @Description: OAuth2 用户信息业务服务类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:45:03
 *
 */
public class HawkUserDetailService implements UserDetailsService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String sql = "select * from hk_user where username = ?";
		List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, username);
		if (null == users || users.size() == 0) {
			return null;
		}
		Map<String, Object> user = users.get(0);
		boolean disabled = ((Integer) user.get("status")) == 1;

		Object expire = user.get("expire_time");
		boolean expired = false;
		if (null != expire) {
			Long expireTime = (Long) expire;
			long current = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			if (current > expireTime) {
				expired = true;
			}
		}
		List<String> roles = queryUserRoles(username);
		return new User(username, user.get("password").toString(), disabled, expired,
				AuthorityUtils.createAuthorityList(roles), user);
	}

	private List<String> queryUserRoles(String username) {
		String sql = "select role_id from hk_role where role_status = 1 and exists (select 1 FROM hk_user_role where hk_role.role_id = hk_user_role.role_id and exists (select 1 from hk_user where hk_user.user_id = hk_user_role.user_id and username = ?) )";
		return jdbcTemplate.query(sql, (rs, _) -> rs.getString(1), username);
	}
}
