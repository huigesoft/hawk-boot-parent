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

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * @ClassName: RSA
 *
 * @Description: RSA非对称加密算法
 *
 * @author: 王文辉
 *
 * @date: 2025年9月24日 10:36:53
 * 
 */
public class RSA {

	private static final String ALGORITHM = "RSA";

	/**
	 * 
	 * @Title: genKeyPair
	 *
	 * @Description: 生成RSA密钥对
	 * 
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: KeyPair
	 * 
	 * @date: 2025年9月24日 10:37:14
	 *
	 * @throws
	 */

	public synchronized static KeyPair genKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
		keyPairGenerator.initialize(2048);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * 
	 * @Title: genKeyPairStr
	 *
	 * @Description: 生成Base64字符串形式的RSA密钥对
	 * 
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: KeyPairStr
	 * 
	 * @date: 2025年9月24日 10:38:00
	 *
	 * @throws
	 */
	public synchronized static KeyPairStr genKeyPairStr() throws Exception {
		KeyPair keyPair = genKeyPair();
		return new KeyPairStr(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()),
				Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
	}

	public synchronized static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	public synchronized static byte[] encrypt(byte[] data, byte[] publicKey) throws Exception {
		PublicKey key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey));
		return encrypt(data, key);
	}

	public synchronized static byte[] encrypt(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(publicKey);
		return encrypt(data, keyBytes);
	}

	public synchronized static String encrypt(String data, String publicKey) throws Exception {
		return Base64.getEncoder().encodeToString(encrypt(data.getBytes(), publicKey));
	}

	public synchronized static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	public synchronized static byte[] decrypt(byte[] data, byte[] privateKey) throws Exception {
		PrivateKey key = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		return decrypt(data, key);
	}

	public synchronized static byte[] decrypt(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(privateKey);
		return decrypt(data, keyBytes);
	}

	public synchronized static String decrypt(String data, String privateKey) throws Exception {
		return new String(decrypt(Base64.getDecoder().decode(data), privateKey));
	}

}
