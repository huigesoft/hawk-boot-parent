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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 
 * @ClassName: DateUtils
 *
 * @Description: 日期工具类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 11:27:02
 *
 */
public class DateUtils {

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * 
	 * @Title: now
	 *
	 * @Description: 获取当前时间毫秒数
	 * 
	 * @param: @return
	 *
	 * @return: long
	 * 
	 * @date: 2024年7月14日 下午4:41:56
	 *
	 * @throws
	 */
	public static long now() {
		return new Date().getTime();
	}

	/**
	 * 
	 * @Title: between
	 *
	 * @Description: 计算两个时间点之间的毫秒数
	 * 
	 * @param: @param  start 开始时间
	 * @param: @param  end 结束时间
	 * @param: @return
	 *
	 * @return: long
	 * 
	 * @date: 2025年9月25日 10:46:57
	 *
	 * @throws
	 */
	public static long between(Instant start, Instant end) {
		return Duration.between(start, end).toMillis();
	}

	/**
	 * 
	 * @Title: currentDateTime
	 *
	 * @Description: 获取当前格式化后的日期时间
	 * 
	 * @param: @return
	 *
	 * @return: String 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @throws
	 */
	public static String currentDateTime() {
		return LocalDateTime.now(ZoneOffset.systemDefault()).format(dateTimeFormatter);
	}

	/**
	 * 
	 * @Title: currentDate
	 *
	 * @Description: 获取当前格式化后的日期
	 * 
	 * @param: @return
	 *
	 * @return: String 格式：yyyy-MM-dd
	 * 
	 * @throws
	 */
	public static String currentDate() {
		return LocalDate.now(ZoneOffset.systemDefault()).format(dateFormatter);
	}

	/**
	 * 
	 * @Title: formateDate
	 *
	 * @Description: 格式化日期
	 * 
	 * @param: @param  date
	 * @param: @return
	 *
	 * @return: String 格式：yyyy-MM-dd
	 * 
	 * @throws
	 */
	public synchronized static String formateDate(Date date) {
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.format(dateFormatter);
	}

	/**
	 * 
	 * @Title: formateDate
	 *
	 * @Description: 格式化日期
	 * 
	 * @param: @param  date 日期
	 * @param: @param  pattern 格式,例：yyyy-MM-dd
	 * @param: @return
	 *
	 * @return: String 格式化后的日期字符串
	 * 
	 * @throws
	 */
	public synchronized static String formateDate(Date date, String pattern) {
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 
	 * @Title: formateDateTime
	 *
	 * @Description: 格式化日期时间
	 * 
	 * @param: @param  date 日期时间
	 * @param: @return
	 *
	 * @return: String 格式化后的日期时间字符串
	 * 
	 * @throws
	 */
	public synchronized static String formateDateTime(Date date) {
		Instant instant = date.toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localDateTime.format(dateTimeFormatter);
	}

	public synchronized static String formateDateTime(Date date, String pattern) {
		Instant instant = date.toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}

	public synchronized static String formateDate(LocalDate date) {
		return date.format(dateFormatter);
	}

	public synchronized static String formateDate(LocalDate date, String pattern) {
		return date.format(DateTimeFormatter.ofPattern(pattern));
	}

	public synchronized static String formateDateTime(LocalDateTime dateTime) {
		return dateTime.format(dateTimeFormatter);
	}

	public synchronized static String formateDateTime(LocalDateTime dateTime, String pattern) {
		return dateTime.format(DateTimeFormatter.ofPattern(pattern));
	}

	public synchronized static LocalDate parseLocalDate(String date) {
		return LocalDate.parse(date, dateFormatter);
	}

	public synchronized static LocalDateTime parseLocalDateTime(String date) {
		return LocalDateTime.parse(date, dateTimeFormatter);
	}

	public synchronized static LocalDate parseLocalDate(String date, String pattern) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
	}

	public synchronized static LocalDateTime parseLocalDateTime(String date, String pattern) {
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
	}

	public synchronized static Date parseDate(String date) {
		return Date.from(LocalDate.parse(date, dateFormatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public synchronized static Date parseDateTime(String date) {
		return Date.from(LocalDate.parse(date, dateTimeFormatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public synchronized static Date parseDate(String date, String pattern) {
		return Date.from(LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern))
				.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public synchronized static Date parseDateTime(String date, String pattern) {
		return Date.from(LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern))
				.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public synchronized static Date localDate2Date(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public synchronized static LocalDate date2LocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public synchronized static LocalDate firstDayOfMonth(LocalDate localDate) {
		return localDate.with(TemporalAdjusters.firstDayOfMonth());
	}

	public synchronized static LocalDate lastDayOfMonth(LocalDate localDate) {
		return localDate.with(TemporalAdjusters.lastDayOfMonth());
	}

	public synchronized static LocalDate firstDayOfYear(LocalDate localDate) {
		return localDate.with(TemporalAdjusters.firstDayOfYear());
	}

	public synchronized static LocalDate lastDayOfYear(LocalDate localDate) {
		return localDate.with(TemporalAdjusters.lastDayOfYear());
	}

	public synchronized static Date firstDayOfMonth(Date date) {
		return localDate2Date(date2LocalDate(date).with(TemporalAdjusters.firstDayOfMonth()));
	}

	public synchronized static Date lastDayOfMonth(Date date) {
		return localDate2Date(date2LocalDate(date).with(TemporalAdjusters.lastDayOfMonth()));
	}

	public synchronized static Date firstDayOfYear(Date date) {
		return localDate2Date(date2LocalDate(date).with(TemporalAdjusters.firstDayOfYear()));
	}

	public synchronized static Date lastDayOfYear(Date date) {
		return localDate2Date(date2LocalDate(date).with(TemporalAdjusters.lastDayOfYear()));
	}
}
