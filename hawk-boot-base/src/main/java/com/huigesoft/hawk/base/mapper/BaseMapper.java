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
package com.huigesoft.hawk.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * @ClassName: BaseMapper
 *
 * @Description: 通用Mapper父类
 *
 * @author: 王文辉
 *
 * @date: 2025年9月24日 15:04:36
 * 
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

	/**
	 * 
	 * @Title: selectPageBySql
	 *
	 * @Description: 根据SQL语句和查询条件查询分页数据
	 * 
	 * @param: @param  page 分页信息
	 * 
	 * @param: @param  sql 结构化查询语句
	 * 
	 * @param: @param  wrapper 过滤条件
	 * 
	 * @param: @return 分页结果集
	 *
	 * @return: IPage<Map<String,Object>>
	 * 
	 * @date: 2028年09月24日 15:22:21
	 * 
	 * @throws
	 */
	@Select("select * from ( ${sql}) _hk_temp ${ew.customSqlSegment}")
	public IPage<Map<String, Object>> selectPageBySql(IPage<?> page, @Param("sql") String sql,
			@Param(Constants.WRAPPER) Wrapper<?> wrapper);

	/**
	 * 
	 * @Title: selectListBySql
	 *
	 * @Description: 根据SQL语句和查询条件查询数据
	 * 
	 * @param: @param  sql 结构化查询语句
	 * 
	 * @param: @param  wrapper 过滤条件
	 * 
	 * @param: @return
	 *
	 * @return: List<Map<String,Object>>
	 * 
	 * @date: 2028年09月24日 15:15:37
	 * 
	 * @throws
	 */
	@Select("select * from ( ${sql}) _hk_temp ${ew.customSqlSegment}")
	public List<Map<String, Object>> selectListBySql(@Param("sql") String sql,
			@Param(Constants.WRAPPER) Wrapper<?> wrapper);

	/**
	 * 
	 * @Title: selectListBySql
	 *
	 * @Description: 根据SQL语句和查询条件查询数据
	 * 
	 * @param: @param  sql 结构化查询语句
	 * 
	 * @param: @param  countSql 统计sql查询
	 * 
	 * @param: @param  wrapper 过滤条件
	 * 
	 * @param: @return
	 *
	 * @return: List<Map<String,Object>>
	 * 
	 * @date: 2028年09月24日 15:15:37
	 * 
	 * @throws
	 */
	@Select("select ${countSql} from ( ${sql}) _hk_temp ${ew.customSqlSegment}")
	public List<Map<String, Object>> selectCountListBySql(@Param("sql") String sql, @Param("countSql") String countSql,
			@Param(Constants.WRAPPER) Wrapper<?> wrapper);

	/**
	 * 
	 * @Title: selectSql
	 *
	 * @Description: SQL查询接口
	 * 
	 * @param: @param  sql 查询语句
	 * 
	 * @param: @return
	 *
	 * @return: List<Map<String,Object>>
	 * 
	 * @date: 2028年09月24日 15:53:10
	 *
	 * @throws
	 */
	@Select("select * from ( ${sql}) as _hk_temp")
	public List<Map<String, Object>> selectSql(@Param("sql") String sql);
}
