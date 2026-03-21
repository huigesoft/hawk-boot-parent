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

import com.huigesoft.hawk.commons.domain.TreeNode;

/**
 * 
 * @ClassName: TreeUtils
 *
 * @Description: 树形结构工具类
 *
 * @author: 王文辉
 *
 * @date: 2025年09月01日 10:32:27
 *
 */
public class TreeUtils {

	/**
	 * 
	 * @Title: list2Tree
	 *
	 * @Description: 列表转树形结构
	 * 
	 * @param: @param  nodes 节点集合
	 * @param: @param  parentKey 父类Key
	 * @param: @return
	 *
	 * @return: List<TreeNode<?>> 树形结构集合
	 * 
	 * @date: 2024年12月27日 10:33:00
	 *
	 * @throws
	 */
	public static synchronized List<TreeNode<?>> list2Tree(List<TreeNode<?>> nodes, String parentKey) {
		List<TreeNode<?>> root = new ArrayList<>();
		Map<String, TreeNode<?>> map = new HashMap<>();
		for (int i = 0; i < nodes.size(); i++) {
			TreeNode<?> node = nodes.get(i);
			map.put(node.getKey(), node);
		}
		for (int i = 0; i < nodes.size(); i++) {
			TreeNode<?> node = nodes.get(i);

			if (node.getParentKey().equals(parentKey)) {
				root.add(node);
			} else {
				TreeNode<?> parent = map.get(node.getParentKey());
				if (null != parent) {
					parent.getChildren().add(node);
				}
			}

		}
		return root;
	}

	/**
	 * 
	 * @Title: list2Tree
	 *
	 * @Description: 列表转树形结构
	 * 
	 * @param: @param  nodes 节点集合
	 * @param: @return
	 *
	 * @return: List<TreeNode<?>> 树形结构集合
	 * 
	 * @date: 2024年12月27日 10:34:59
	 *
	 * @throws
	 */
	public static List<TreeNode<?>> list2Tree(List<TreeNode<?>> nodes) {
		List<TreeNode<?>> root = new ArrayList<>();
		Map<String, TreeNode<?>> map = new HashMap<>();
		for (int i = 0; i < nodes.size(); i++) {
			TreeNode<?> node = nodes.get(i);
			map.put(node.getKey(), node);
		}
		for (int i = 0; i < nodes.size(); i++) {
			TreeNode<?> node = nodes.get(i);
			TreeNode<?> parent = map.get(node.getParentKey());
			if (null != parent) {
				parent.getChildren().add(node);
			} else {
				root.add(parent);
			}
		}
		return root;
	}
}
