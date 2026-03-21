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
package com.huigesoft.hawk.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huigesoft.hawk.base.mapper.BaseMapper;
import com.huigesoft.hawk.base.service.IBaseService;
import com.huigesoft.hawk.commons.criteria.Criteria;
import com.huigesoft.hawk.commons.domain.TreeNode;
import com.huigesoft.hawk.commons.utils.TreeUtils;

/**
 * 
 * @ClassName: BaseServiceImpl
 *
 * @Description: 通用业务服务实现类
 *
 * @author: 王文辉
 *
 * @date: 2025年9月24日 15:10:50
 * 
 * @param <M> Mapper类型
 * @param <T> Entity类型
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

	@Override
	public IPage<T> page(Criteria criteria) {
		IPage<T> page = new Page<>(criteria.getPage(), criteria.getSize());
		return baseMapper.selectPage(page, criteria2Wrapper(criteria));
	}

	@Override
	public List<T> list(Criteria criteria) {
		return baseMapper.selectList(criteria2Wrapper(criteria));
	}

	@Override
	public IPage<Map<String, Object>> pageBySql(String sql, Criteria criteria) {
		IPage<T> page = new Page<>(criteria.getPage(), criteria.getSize());
		return baseMapper.selectPageBySql(page, sql, criteria2Wrapper(criteria));
	}

	@Override
	public List<Map<String, Object>> listBySql(String sql, Criteria criteria) {
		return baseMapper.selectListBySql(sql, criteria2Wrapper(criteria));
	}

	@Override
	public List<TreeNode<?>> list2Tree(List<TreeNode<?>> nodes, String parentKey) {
		return TreeUtils.list2Tree(nodes, parentKey);
	}

	@Override
	public List<TreeNode<?>> searchTree(List<TreeNode<?>> tree, String searchValue) {
		List<TreeNode<?>> nodes = new ArrayList<>();
		tree.forEach(node -> {
			if (node.getChildren() != null && node.getChildren().size() > 0) {
				List<TreeNode<?>> children = node.getChildren();
				List<TreeNode<?>> searchChildren = searchTree(children, searchValue);
				node.setChildren(searchChildren);
				if (node.getTitle().contains(searchValue)
						|| (null != node.getChildren() && node.getChildren().size() > 0)) {
					nodes.add(node);
				}
			} else {
				if (node.getTitle().contains(searchValue)) {
					nodes.add(node);
				}
			}
		});
		return nodes;
	}

	@Override
	public Boolean checkExists(Map<String, Object> param) {
		Map<String, Object> params = new HashMap<String, Object>();
		param.entrySet().forEach(entry -> {
			String key = entry.getKey();
			if (!"id".equals(key) && !"idValue".equals(key)) {
				params.put(StringUtils.camelToUnderline(entry.getKey()), entry.getValue());
			}
		});
		QueryWrapper<T> wrapper = new QueryWrapper<T>().allEq(params);
		if (StringUtils.isNotBlank(param.get("id").toString()) && null != param.get("idValue")) {
			wrapper = wrapper.ne(StringUtils.camelToUnderline(param.get("id").toString()), param.get("idValue"));
		}
		Long selectCount = this.baseMapper.selectCount(wrapper);
		return selectCount > 0;
	}

	@Override
	public List<Map<String, Object>> listBySql(String sql) {
		return this.baseMapper.selectSql(sql);
	}

}
