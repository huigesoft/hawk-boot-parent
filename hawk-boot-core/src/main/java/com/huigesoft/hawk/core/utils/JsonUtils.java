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
package com.huigesoft.hawk.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * 
 * @ClassName: JsonUtils
 *
 * @Description: JSON工具类
 *
 * @author: 王文辉
 *
 * @date: 2028-08-22 13:24:35
 *
 */
public class JsonUtils {

	private static Map<String, DateFormat> formats = new HashMap<>();

	public static ObjectMapper mapper = JsonMapper.builder().enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.enable(DateTimeFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS).build();

//	static {
//		mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
//	}

	/**
	 * 
	 * @Title: object2Json
	 *
	 * @Description: 对象转换为JSON字符串
	 * 
	 * @param: @param  object 实例对象
	 * @param: @return
	 * @param: @throws JsonProcessingException
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 13:24:48
	 * 
	 * @throws
	 */
	public synchronized static String object2Json(Object object) {
		if (null != object) {
			mapper.serializationConfig().with(format(null));
			return mapper.writeValueAsString(object);
		}
		return "";
	}

	/**
	 * 
	 * @Title: object2Json
	 *
	 * @Description: 实例对象转换为JSON字符串
	 * 
	 * @param: @param  object 实例对象
	 * @param: @param  datePattern 数据格式化形式，如：yyyy-MM-dd
	 * @param: @return
	 * @param: @throws JsonProcessingException
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 13:25:15
	 * 
	 * @throws
	 */
	public synchronized static String object2Json(Object object, String datePattern) {
		if (null != object) {
			mapper.serializationConfig().with(format(datePattern));
			return mapper.writeValueAsString(object);
		}
		return "";
	}

	/**
	 * 
	 * @Title: json2Object
	 *
	 * @Description: JSON字符串转换为实例对象
	 * 
	 * @param: @param  <T> 对象类型
	 * @param: @param  jsonStr JSON字符串
	 * @param: @param  valueType 对象类型
	 * @param: @return
	 * @param: @throws JsonMappingException
	 * @param: @throws JsonProcessingException
	 *
	 * @return: T
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 13:26:07
	 * 
	 * @throws
	 */
	public synchronized static <T> T json2Object(String jsonStr, Class<T> valueType) {
		if (null != jsonStr) {
			mapper.serializationConfig().with(format(null));
			return mapper.readValue(jsonStr, valueType);
		}
		return null;
	}

	/**
	 * 
	 * @Title: json2Object
	 *
	 * @Description: JSON字符串转换为实例对象
	 * 
	 * @param: @param  <T> 泛型类型
	 * @param: @param  jsonStr JSON字符串
	 * @param: @param  valueType 对象类型
	 * @param: @param  datePattern 日期格式，如：yyyy-MM-dd
	 * @param: @return
	 * @param: @throws JsonMappingException
	 * @param: @throws JsonProcessingException
	 *
	 * @return: T
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 13:27:08
	 * 
	 * @throws
	 */
	public synchronized static <T> T json2Object(String jsonStr, Class<T> valueType, String datePattern) {
		if (null != jsonStr) {
			mapper.serializationConfig().with(format(datePattern));
			return mapper.readValue(jsonStr, valueType);
		}
		return null;
	}

	/**
	 * 
	 * @Title: format
	 *
	 * @Description: 日期格式化
	 * 
	 * @param: @param  pattern 日期格式 ： yyyy-MM-dd
	 * @param: @return
	 *
	 * @return: DateFormat
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 13:27:48
	 * 
	 * @throws
	 */
	private static DateFormat format(String pattern) {
		if (null == pattern) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat dateFormat = formats.get(pattern);
		if (null != dateFormat) {
			return dateFormat;
		}
		DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		formats.put(pattern, simpleDateFormat);
		return simpleDateFormat;
	}
}
