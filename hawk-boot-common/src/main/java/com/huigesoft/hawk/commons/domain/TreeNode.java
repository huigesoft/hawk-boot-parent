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
package com.huigesoft.hawk.commons.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: TreeNode
 *
 * @Description: 树节点域对象
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 10:14:15
 * 
 * @param <T> 节点存储的原生对象
 */
public class TreeNode<T> implements Serializable {

	/**
	 * @Fields serialVersionUID : 序列号ID
	 */
	private static final long serialVersionUID = 4200850455186189811L;

	/**
	 * 节点标识，与value可以设置其一
	 */
	private String key;

	/**
	 * 节点标题
	 */
	private String title;

	/**
	 * 节点值，与key可以二选一
	 */
	private String value;

	/**
	 * 图标名称
	 */
	private String iconStr;

	/**
	 * 子节点集合
	 */
	private List<TreeNode<?>> children;

	/**
	 * 原始数据
	 */
	private T data;

	/**
	 * 上级节点标识
	 */
	private String parentKey;

	public TreeNode(String key, String title, String parentKey) {
		this.key = key;
		this.title = title;
		this.value = key;
		this.parentKey = parentKey;
	}

	public TreeNode(String key, String title, String icon, String parentKey) {
		this.key = key;
		this.title = title;
		this.value = key;
		this.iconStr = icon;
		this.parentKey = parentKey;
	}

	public TreeNode(String key, String title, String icon, T data) {
		this.key = key;
		this.title = title;
		this.value = key;
		this.iconStr = icon;
		this.data = data;
	}

	public TreeNode(String key, String title, T data, String parentKey) {
		this.key = key;
		this.title = title;
		this.value = key;
		this.data = data;
		this.parentKey = parentKey;
	}

	public TreeNode(String key, String title, String icon, T data, String parentKey) {
		this.key = key;
		this.title = title;
		this.value = key;
		this.iconStr = icon;
		this.data = data;
		this.parentKey = parentKey;
	}

	public TreeNode(String key, String title, String value, String icon, T data, String parentKey) {
		this.key = key;
		this.title = title;
		this.value = value;
		this.iconStr = icon;
		this.data = data;
		this.parentKey = parentKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIconStr() {
		return iconStr;
	}

	public void setIconStr(String icon) {
		this.iconStr = icon;
	}

	public Boolean getIsLeaf() {
		return null == this.children || this.children.size() == 0;
	}

	public List<TreeNode<?>> getChildren() {
		if (null == children) {
			children = new ArrayList<>();
		}
		return children;
	}

	public void setChildren(List<TreeNode<?>> children) {
		this.children = children;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getLabel() {
		return this.title;
	}

}
