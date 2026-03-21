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

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huigesoft.hawk.commons.criteria.Criteria;
import com.huigesoft.hawk.commons.domain.TreeNode;

/**
 * 
 * @ClassName: IBaseService
 *
 * @Description: 平台通用业务服务类
 *
 * @author: 王文辉
 *
 * @date: 2025年9月24日 15:07:31
 * 
 * @param <T> 实体类型
 */
public non-sealed interface IBaseService<T> extends IService<T>, IPrivateService<T> {
	/**
	 * 
	 * @Title: page
	 *
	 * @Description: 根据组合条件查询分页数据
	 * 
	 * @param: @param  criteria 组合条件
	 * @param: @return
	 *
	 * @return: IPage<T>
	 * 
	 * @throws
	 */
	public IPage<T> page(Criteria criteria);

	/**
	 * 
	 * @Title: list
	 *
	 * @Description: 根据组合条件查询数据列表
	 * 
	 * @param: @param  criteria
	 * @param: @return
	 *
	 * @return: List<T>
	 * 
	 * @throws
	 */
	public List<T> list(Criteria criteria);

	/**
	 * 
	 * @Title: pageBySql
	 *
	 * @Description: 根据SQL语句查询分页数据，结果集转换成驼峰命名方式
	 * 
	 * @param: @param  sql 查询语句
	 * @param: @param  criteria 条件构造器
	 * @param: @return
	 *
	 * @return: IPage<Map<String,Object>>
	 * 
	 * @throws
	 */
	public IPage<Map<String, Object>> pageBySql(String sql, Criteria criteria);

	/**
	 * 
	 * @Title: listBySql
	 *
	 * @Description: 根据SQL语句查询列表数据，结果集转换成驼峰命名方式
	 * 
	 * @param: @param  sql 查询语句
	 * @param: @param  criteria 条件构造器
	 * @param: @return
	 *
	 * @return: List<Map<String,Object>>
	 * 
	 * @throws
	 */
	public List<Map<String, Object>> listBySql(String sql, Criteria criteria);

	/**
	 * 
	 * @Title: listBySql
	 *
	 * @Description: 根据SQL语句查询列表数据，结果集转换成驼峰命名方式
	 * 
	 * @param: @param  sql 查询语句
	 * @param: @return
	 *
	 * @return: List<Map<String,Object>>
	 * 
	 * @throws
	 */
	public List<Map<String, Object>> listBySql(String sql);

	/**
	 * 
	 * @Title: list2Tree
	 *
	 * @Description: 集合转树形结构
	 * 
	 * @param: @param  nodes 结点列表集合
	 * @param: @param  parentKey 父类Key
	 * @param: @return
	 *
	 * @return: List<TreeNode<?>>
	 * 
	 * @throws
	 */
	public List<TreeNode<?>> list2Tree(List<TreeNode<?>> nodes, String parentKey);

	/**
	 * 
	 * @Title: searchTree
	 *
	 * @Description: 搜索树形结点
	 * 
	 * @param: @param  tree
	 * @param: @param  searchValue
	 * @param: @return
	 *
	 * @return: List<TreeNode<?>>
	 * 
	 * @throws
	 */
	public List<TreeNode<?>> searchTree(List<TreeNode<?>> tree, String searchValue);

	/**
	 * 
	 * @Title: checkExists
	 *
	 * @Description: 查询谋值是否存在
	 * 
	 * @param: @param  param {id:主键属性名，idValue:主键属性值}
	 * 
	 * @param: @return true:存在，false:不存在
	 *
	 * @return: Boolean
	 * 
	 * @date: 2023年10月23日 下午8:38:45
	 *
	 * @throws
	 */
	public Boolean checkExists(Map<String, Object> param);
}
