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
package com.huigesoft.hawk.core.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @ClassName: User
 *
 * @Description: 登录用户实体类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:34:32
 *
 */
public class User extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -3035363449205178118L;

	private Map<String, Object> datas;

	public User(String username, String password) {
		this(username, password, List.of(), Map.of());
	}

	public User(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Map<String, Object> datas) {
		this(username, password, true, true, true, true, authorities, datas);
	}

	public User(String username, String password, boolean enabled, boolean accountExpired,
			Collection<? extends GrantedAuthority> authorities, Map<String, Object> datas) {
		this(username, password, enabled, !accountExpired, true, true, authorities, datas);
	}

	public User(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			Map<String, Object> datas) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.datas = datas;
	}

	public Map<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}

}