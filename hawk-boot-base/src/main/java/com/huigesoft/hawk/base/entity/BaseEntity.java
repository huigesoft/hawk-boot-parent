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
package com.huigesoft.hawk.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.huigesoft.hawk.base.annotation.HkField;

/**
 * @ClassName: BaseEntity
 *
 * @Description: 平台通用实体类基础实现
 *
 * @author: 王文辉
 *
 * @date: 2025年11月6日 10:27:26
 * 
 */
@SuppressWarnings("serial")
public class BaseEntity implements Entity {

	@HkField("创建人")
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	@HkField("创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Long createAt;

	@HkField("创建人IP")
	@TableField(fill = FieldFill.INSERT)
	private String createIp;

	@HkField("更新人")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;

	@HkField("更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateAt;

	@HkField("更新IP")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateIp;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Long getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Long updateAt) {
		this.updateAt = updateAt;
	}

	public String getUpdateIp() {
		return updateIp;
	}

	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
}
