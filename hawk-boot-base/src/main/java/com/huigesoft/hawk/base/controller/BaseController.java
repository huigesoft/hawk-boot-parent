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
package com.huigesoft.hawk.base.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huigesoft.hawk.base.service.IBaseService;
import com.huigesoft.hawk.commons.annotation.HkBiz;
import com.huigesoft.hawk.commons.annotation.HkLog;
import com.huigesoft.hawk.commons.criteria.Criteria;
import com.huigesoft.hawk.commons.enums.HKOperType;
import com.huigesoft.hawk.commons.http.Response;
import com.huigesoft.hawk.commons.utils.RandomUtils;

/**
 * @ClassName: BaseController
 *
 * @Description: 通用控制器
 *
 * @author: 王文辉
 *
 * @date: 2025年9月24日 15:13:39
 * 
 * @param <S>
 * @param <T>
 * 
 */
public abstract class BaseController<S extends IBaseService<T>, T> {

	private String permName = null;

	@Autowired
	public S baseService;

	@GetMapping("getById")
	@PreAuthorize("@hk.has(this.getName() +':query')")
	public Response<T> getById(@RequestParam Serializable id) {
		try {
			T t = baseService.getById(id);
			return Response.of(t);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@PostMapping("listByIds")
	@PreAuthorize("@hk.has(this.getName() +':query')")
	public Response<List<T>> listByIds(@RequestBody List<Serializable> idList) {
		try {
			List<T> list = baseService.listByIds(idList);
			return Response.of(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@PostMapping("list")
	@PreAuthorize("@hk.has(this.getName() +':query')")
	public Response<List<T>> list(@RequestBody Criteria criteria) {
		try {
			List<T> list = this.baseService.list(criteria);
			return Response.of(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@PostMapping("page")
	@PreAuthorize("@hk.has(this.getName() +':query')")
	public Response<IPage<T>> page(@RequestBody Criteria criteria) {
		try {
			IPage<T> list = this.baseService.page(criteria);
			return Response.of(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@PostMapping("save")
	@PreAuthorize("@hk.has(this.getName() +':save')")
	@HkLog(type = HKOperType.ADD, desc = "保存信息")
	public Response<T> save(@RequestBody T t) {
		try {
			boolean success = baseService.save(t);
			return Response.of(t, success);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@PostMapping("saveBatch")
	@PreAuthorize("@hk.has(this.getName() +':save')")
	@HkLog(type = HKOperType.ADD, desc = "批量保存信息")
	public Response<List<T>> saveBatch(@RequestBody List<T> ts) {
		try {
			boolean success = baseService.saveBatch(ts);
			return Response.of(ts, success);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@RequestMapping(value = "update", method = { RequestMethod.POST, RequestMethod.PUT })
	@PreAuthorize("@hk.has(this.getName() +':save')")
	@HkLog(type = HKOperType.UPDATE, desc = "更新信息")
	public Response<T> update(@RequestBody T t) {
		try {
			boolean success = baseService.updateById(t);
			return Response.of(t, success);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@RequestMapping(value = "updateBatch", method = { RequestMethod.POST, RequestMethod.PUT })
	@PreAuthorize("@hk.has(this.getName() +':save')")
	@HkLog(type = HKOperType.UPDATE, desc = "批量更新信息")
	public Response<List<T>> updateBatch(@RequestBody List<T> ts) {
		try {
			boolean success = baseService.updateBatchById(ts);
			return Response.of(ts, success);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@RequestMapping(value = "saveOrUpdate", method = { RequestMethod.POST, RequestMethod.PUT })
	@PreAuthorize("@hk.has(this.getName() +':save')")
	@HkLog(type = HKOperType.UPDATE, desc = "保存或更新信息")
	public Response<T> saveOrUpdate(@RequestBody T t) {
		try {
			boolean success = baseService.saveOrUpdate(t);
			return Response.of(t, success);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@RequestMapping(value = "saveOrUpdateBatch", method = { RequestMethod.POST, RequestMethod.PUT })
	@PreAuthorize("@hk.has(this.getName() +':save')")
	@HkLog(type = HKOperType.UPDATE, desc = "批量保存或更新信息")
	public Response<List<T>> saveOrUpdateBatch(@RequestBody List<T> ts) {
		try {
			boolean success = baseService.saveOrUpdateBatch(ts);
			return Response.of(ts, success);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@RequestMapping(value = "deleteById", method = { RequestMethod.POST, RequestMethod.DELETE })
	@PreAuthorize("@hk.has(this.getName() +':delete')")
	@HkLog(type = HKOperType.DELETE, desc = "根据ID删除记录")
	public Response<Boolean> deleteById(@RequestParam Serializable id) {
		try {
			boolean success = baseService.removeById(id);
			return Response.of(success);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@RequestMapping(value = "deleteByIds", method = { RequestMethod.POST, RequestMethod.DELETE })
	@PreAuthorize("@hk.has(this.getName() +':delete')")
	@HkLog(type = HKOperType.DELETE, desc = "根据ID集合删除记录")
	public Response<Boolean> deleteByIds(@RequestBody List<Serializable> ids) {
		try {
			boolean success = baseService.removeByIds(ids);
			return Response.of(success);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@PostMapping("pageBySql")
	@PreAuthorize("@hk.has(this.getName() +':query')")
	public Response<IPage<Map<String, Object>>> queryPageBySql(@RequestBody Criteria criteria) {
		try {
			return Response.of(this.baseService.pageBySql(querySql(), criteria));
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@PostMapping("listBySql")
	@PreAuthorize("@hk.has(this.getName() +':query')")
	public Response<List<Map<String, Object>>> queryListBySql(@RequestBody Criteria criteria) {
		try {
			return Response.of(this.baseService.listBySql(querySql(), criteria));
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@PostMapping("checkExists")
	public Response<Boolean> checkExists(@RequestBody Map<String, Object> param) {
		try {
			if (null != param) {
				return Response.of(baseService.checkExists(param));
			}
			return Response.of(false);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	@RequestMapping(value = "randomStr", method = { RequestMethod.GET, RequestMethod.POST })
	@HkLog(desc = "生成随机数", type = HKOperType.GENERATE)
	public Response<List<String>> randomStr(@RequestParam(required = false, defaultValue = "8") Integer len,
			@RequestParam(required = false, defaultValue = "1") Integer size) {
		try {
			List<String> datas = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				datas.add(RandomUtils.randomString(len));
			}
			return Response.of(datas);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.err(e);
		}
	}

	protected String querySql() {
		return "";
	}

	public String getName() {
		if (null == permName) {
			permName = initName();
		}
		return permName;
	}

	private String initName() {
		HkBiz biz = this.getClass().getAnnotation(HkBiz.class);
		if (null != biz) {
			return biz.name();
		}
		RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
		if (null != requestMapping) {
			String[] values = requestMapping.value();
			if (values.length > 0) {
				String path = values[0];
				if (path.startsWith("/")) {
					path = path.substring(1);
				}
				if (path.endsWith("/")) {
					path = path.substring(0, path.length() - 1);
				}
				path = path.replace("/", ":");
				return path;
			}
		}
		return "";
	}

}
