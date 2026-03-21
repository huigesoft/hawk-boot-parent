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
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

/**
 * @ClassName: SM4
 *
 * @Description: SM4国密算法
 *
 * @author: 王文辉
 *
 * @date: 2025年9月23日 14:27:25
 * 
 */
public class SM4 {

	private static String algorithm = "SM4";

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * 
	 * @Title: encrypt
	 *
	 * @Description: SM4加密，返回加密后Base64字符串
	 * 
	 * @param: @param  key 16字节秘钥
	 * @param: @param  data 待加密数据
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月23日 14:34:56
	 *
	 * @throws
	 */
	public synchronized static String encrypt(String data, String key) throws Exception {
		return Hex.toHexString(encrypt(data.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8)));
	}

	/**
	 * 
	 * @Title: encrypt
	 *
	 * @Description: SM4加密，返回加密后byte数组
	 * 
	 * @param: @param  key 16字节秘钥
	 * @param: @param  data 待加密数据
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: byte[]
	 * 
	 * @date: 2025年9月23日 14:34:56
	 *
	 * @throws
	 */
	public synchronized static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);
		byte[] iv = new byte[16];
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
		byte[] cipherBytes = cipher.doFinal(data);
		return cipherBytes;
	}

	public static String decrypt(String data, String key) throws Exception {
		return new String(decrypt(Hex.decode(data), key.getBytes(StandardCharsets.UTF_8)));
	}

	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
		byte[] iv = new byte[16];
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
		return cipher.doFinal(data);
	}

	public static void main(String[] args) throws Exception {
		String key = "1234567812345678";
		String data = "Hello, World!, 你好，世界！你好，世界";
		String encrypt = encrypt(data, key);
		System.out.println("加密后Base64字符串：" + encrypt);
		String decrypt = decrypt(encrypt, key);
		System.out.println("解密后字符串：" + decrypt);
	}
}
