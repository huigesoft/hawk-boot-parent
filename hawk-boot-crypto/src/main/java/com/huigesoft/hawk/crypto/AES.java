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

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ClassName: AES
 *
 * @Description: AES加解密工具类
 *
 * @author: 王文辉
 *
 * @date: 2025年9月24日 10:12:02
 * 
 */
public class AES {

	private static final String MODEL = "AES/CBC/PKCS5Padding";

	private static final String ALGORITHM = "AES";

	public synchronized static String genKey() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
		keyGen.init(128);
		SecretKey secretKey = keyGen.generateKey();
		return Base64.getEncoder().encodeToString(secretKey.getEncoded());
	}

	public synchronized static byte[] encrypt(byte[] data, SecretKey secretKey) throws Exception {
		byte[] iv = new byte[16];
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance(MODEL);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
		return cipher.doFinal(data);
	}

	public synchronized static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		return encrypt(data, secretKey);
	}

	public synchronized static byte[] encrypt(byte[] data, String key) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(key);
		return encrypt(data, keyBytes);
	}

	public synchronized static String encrypt(String data, String key) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(key);
		return Base64.getEncoder().encodeToString(encrypt(data.getBytes(), keyBytes));
	}

	public synchronized static byte[] decrypt(byte[] data, SecretKey secretKey) throws Exception {
		byte[] iv = new byte[16];
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance(MODEL);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
		return cipher.doFinal(data);
	}

	public synchronized static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		return decrypt(data, secretKey);
	}

	public synchronized static byte[] decrypt(byte[] data, String key) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(key);
		return decrypt(data, keyBytes);
	}

	public synchronized static String decrypt(String data, String key) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(key);
		return new String(decrypt(Base64.getDecoder().decode(data), keyBytes));
	}

}
