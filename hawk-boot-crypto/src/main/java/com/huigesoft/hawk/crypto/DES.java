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
package com.huigesoft.hawk.crypto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @ClassName: DES
 *
 * @Description: DES加解密工具类
 *
 * @author: 王文辉
 *
 * @date: 2025年9月24日 08:57:45
 * 
 */
public class DES {

	private static final String ALGORITHM = "DES";

	/**
	 * 
	 * @Title: encrypt
	 *
	 * @Description: DES 加密方法
	 * 
	 * @param: @param  plainText 原始文本
	 * @param: @param  key 加密KEY
	 * @param: @return 加密后的Base64字符串
	 * @param: @throws Exception
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2025-08-23 10:14:32
	 * 
	 * @throws
	 */
	public static String encrypt(String plainText, String key) throws Exception {
		if (null == plainText) {
			return null;
		}
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public static String encrypt(byte[] plainText, String key) throws Exception {
		if (null == plainText) {
			return null;
		}
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] encryptedBytes = cipher.doFinal(plainText);
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	/**
	 * 
	 * @Title: decrypt
	 *
	 * @Description: DES 解密方法
	 * 
	 * @param: @param  encryptedText 待解密的Base64字符串
	 * @param: @param  key 解密KEY
	 * @param: @return 解密后的字符串
	 * @param: @throws Exception
	 *
	 * @return: String
	 *
	 * @author: 王文辉
	 *
	 * @datetime: 2025-08-23 10:15:11
	 * 
	 * @throws
	 */
	public static String decrypt(String encryptedText, String key) throws Exception {
		if (null == encryptedText) {
			return null;
		}
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	public static String decrypt(byte[] encryptedText, String key) throws Exception {
		if (null == encryptedText) {
			return null;
		}
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}
}
