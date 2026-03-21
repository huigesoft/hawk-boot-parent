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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 
 * @ClassName: CodeImageUtils
 *
 * @Description: 验证码工具类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 11:22:48
 *
 */
public class CodeImageUtils {

	private static String[] opers = { "＋", "－", "×" };

	/**
	 * 
	 * @Title: generate
	 *
	 * @Description: 生成验证码
	 * 
	 * @param: @param  code 验证码数组
	 * @param: @return 验证码图片对象
	 *
	 * @return: BufferedImage
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 11:23:51
	 * 
	 * @throws
	 */
	public static BufferedImage generate(String... code) {
		Random random = new Random();
		int width = 150;
		int height = 35;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 25));
		for (int i = 0; i < code.length; i++) {
			g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			if (i % 2 == 0) {
				g.setFont(new Font(null, Font.BOLD, random.nextInt(22, 25)));
				g.drawString(code[i], 30 * i + 10, 25);
			} else {
				g.drawString(code[i], 30 * i + 10, 25);
			}
		}
		return image;
	}

	/**
	 * 
	 * @Title: getCalcExpression
	 *
	 * @Description: 随机生成验证码表达式数组
	 * 
	 * @param: @return 表达式
	 *
	 * @return: String[]
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 11:24:27
	 * 
	 * @throws
	 */
	public static String[] getCalcExpression() {
		Random random = new Random();
		int a = random.nextInt(0, 10);
		int b = random.nextInt(1, 10);
		String oper = opers[random.nextInt(0, 3)];
		return new String[] { String.valueOf(a), oper, String.valueOf(b), "=", "?" };
	}

	/**
	 * 
	 * @Title: getCalcValue
	 *
	 * @Description: 计算表达式结果
	 * 
	 * @param: @param  expressions 表达式
	 * @param: @return 计算结果
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2024-08-22 11:24:57
	 * 
	 * @throws
	 */
	public static String getCalcValue(String... expressions) {
		int result = -1;
		if (null != expressions && expressions.length == 5) {
			int a = Integer.parseInt(expressions[0]);
			int b = Integer.parseInt(expressions[2]);
			String oper = expressions[1];
			if ("＋".equals(oper)) {
				result = a + b;
			} else if ("－".equals(oper)) {
				result = a - b;
			} else if ("×".equals(oper)) {
				result = a * b;
			}
		}
		return String.valueOf(result);
	}
}
