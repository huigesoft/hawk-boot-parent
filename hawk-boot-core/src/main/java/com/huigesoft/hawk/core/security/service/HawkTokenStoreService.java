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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.huigesoft.hawk.commons.domain.HawkToken;

import jakarta.annotation.Resource;

/**
 * @ClassName: HawkTokenStoreService
 *
 * @Description: token存储服务接口
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 08:34:57
 * 
 */
public class HawkTokenStoreService {

	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * @Title: saveToken
	 *
	 * @Description: 保存Token信息
	 * 
	 * @param: @param  token token
	 * @param: @param  data token数据
	 * @param: @return
	 *
	 * @return: boolean
	 * 
	 * @date: 2025年9月5日 08:36:17
	 *
	 * @throws
	 */
	public boolean saveToken(String token, HawkToken data) {
		String sql = "insert into hk_token (token,access_token,username,login_time,expire_time,token_type,login_ip,login_browser) values (?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, token);
				pstmt.setObject(2, data.getAccessToken());
				pstmt.setObject(3, data.getUsername());
				pstmt.setObject(4, data.getLoginTime());
				pstmt.setObject(5, data.getExpireTime());
				pstmt.setObject(6, data.getTokenType());
				pstmt.setObject(7, data.getLoginIp());
				pstmt.setObject(8, data.getLoginBrowser());
				return pstmt;
			}
		}) > 0;
	}

	/**
	 * 
	 * @Title: getToken
	 *
	 * @Description: 获取Token信息
	 * 
	 * @param: @param  token
	 * @param: @return
	 *
	 * @return: HawkToken
	 * 
	 * @date: 2025年9月5日 08:36:52
	 *
	 * @throws
	 */
	public HawkToken getToken(String token) {
		String sql = "select token,access_token,username,login_time,expire_time,token_type,login_ip,login_browser from hk_token where token = '"
				+ token + "' ";
		try {
			List<HawkToken> tokens = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(HawkToken.class));
			return tokens.size() > 0 ? tokens.get(0) : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @Title: getAllToken
	 *
	 * @Description: 获取所有Token信息
	 * 
	 * @param: @return
	 *
	 * @return: Map<String,HawkToken>
	 * 
	 * @date: 2025年9月5日 08:37:11
	 *
	 * @throws
	 */
	public Map<String, HawkToken> getAllToken() {
		Map<String, HawkToken> tokens = new HashMap<>();
		String sql = "select * from hk_token ";
		List<HawkToken> ts = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(HawkToken.class));
		if (null != tokens && ts.size() > 0) {
			for (int i = 0; i < ts.size(); i++) {
				HawkToken t = ts.get(i);
				tokens.put(t.getToken(), t);
			}
		}
		return tokens;
	}

	/**
	 * 
	 * @Title: removeToken
	 *
	 * @Description: 移除Token信息
	 * 
	 * @param: @param token
	 *
	 * @return: void
	 * 
	 * @date: 2025年9月5日 08:37:22
	 *
	 * @throws
	 */
	public void removeToken(String token) {
		String sql = "delete from hk_token where token = '" + token + "' ";
		jdbcTemplate.execute(sql);
	}

	/**
	 * 
	 * @Title: clearToken
	 *
	 * @Description: 清除过期Token信息
	 * 
	 * @param: @param time
	 *
	 * @return: void
	 * 
	 * @date: 2025年9月5日 08:37:36
	 *
	 * @throws
	 */
	public void clearToken(long time) {
		String sql = "delete from hk_token where expire_time < " + time;
		jdbcTemplate.execute(sql);
	}

}
