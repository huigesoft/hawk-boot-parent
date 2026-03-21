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

import java.util.Arrays;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

/**
 * @ClassName: SM3
 *
 * @Description: SM3国密算法
 *
 * @author: 王文辉
 *
 * @date: 2025年9月23日 10:59:08
 * 
 */
public class SM3 {

	private static final String ENCODING = "UTF-8";

	/**
	 * 
	 * @Title: encrypt
	 *
	 * @Description: SM3无秘钥加密，返回加密后长度为64位的16进制字符串
	 * 
	 * @param: @param  src 源字符串
	 * @param: @return
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月23日 11:26:20
	 *
	 * @throws
	 */
	public synchronized static String encrypt(String src) {
		return Hex.toHexString(encrypt(src.getBytes()));
	}

	/**
	 * 
	 * @Title: encrypt
	 *
	 * @Description: SM3无秘钥加密，返回加密后长度为64位的16进制字符串
	 * 
	 * @param: @param  srcByte 加密数据字节数组
	 * @param: @return
	 *
	 * @return: byte[] 加密后字节数组
	 * 
	 * @date: 2025年9月23日 11:26:50
	 *
	 * @throws
	 */
	public synchronized static byte[] encrypt(byte[] srcByte) {
		SM3Digest sm3 = new SM3Digest();
		sm3.update(srcByte, 0, srcByte.length);
		byte[] encryptByte = new byte[sm3.getDigestSize()];
		sm3.doFinal(encryptByte, 0);
		return encryptByte;
	}

	/**
	 * 
	 * @Title: verify
	 *
	 * @Description: 无秘钥校验
	 * 
	 * @param: @param  src 源数据
	 * @param: @param  sm3HexStr 16进制的加密数据
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: boolean
	 * 
	 * @date: 2025年9月23日 11:27:33
	 *
	 * @throws
	 */
	public synchronized static boolean verify(String src, String sm3HexStr) throws Exception {
		byte[] sm3HashCode = Hex.decode(sm3HexStr);
		byte[] newHashCode = encrypt(src.getBytes(ENCODING));
		return Arrays.equals(newHashCode, sm3HashCode);
	}

	/**
	 * 
	 * @Title: encrypt
	 *
	 * @Description: SM3加密方式之： 根据自定义密钥进行加密，返回加密后长度为32位的16进制字符串
	 * 
	 * @param: @param  src 源字符串
	 * @param: @param  key 密钥
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月23日 11:28:51
	 *
	 * @throws
	 */
	public synchronized static String encrypt(String src, String key) throws Exception {
		byte[] srcByte = src.getBytes(ENCODING);
		byte[] keyByte = key.getBytes(ENCODING);
		return Hex.toHexString(encrypt(srcByte, keyByte));
	}

	/**
	 * 
	 * @Title: encrypt
	 *
	 * @Description: SM3加密方式之： 根据自定义密钥进行加密，返回加密后长度为32位的16进制字符串
	 * 
	 * @param: @param  srcByte 源数据字节数组
	 * @param: @param  keyByte 密钥字节数组
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: byte[]
	 * 
	 * @date: 2025年9月23日 11:29:16
	 *
	 * @throws
	 */
	public synchronized static byte[] encrypt(byte[] srcByte, byte[] keyByte) throws Exception {
		KeyParameter keyParameter = new KeyParameter(keyByte);
		SM3Digest sm3 = new SM3Digest();
		HMac hMac = new HMac(sm3);
		hMac.init(keyParameter);
		hMac.update(srcByte, 0, srcByte.length);
		byte[] result = new byte[hMac.getMacSize()];
		hMac.doFinal(result, 0);
		return result;
	}

	/**
	 * 
	 * @Title: verify
	 *
	 * @Description: 利用源数据+密钥校验与密文是否一致
	 * 
	 * @param: @param  src 源数据
	 * @param: @param  sm3HexStr 16进制的加密数据
	 * @param: @param  key 密钥
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: boolean
	 * 
	 * @date: 2025年9月23日 11:29:53
	 *
	 * @throws
	 */
	public synchronized static boolean verify(String src, String sm3HexStr, String key) throws Exception {
		byte[] srcByte = src.getBytes(ENCODING);
		byte[] keyByte = key.getBytes(ENCODING);
		byte[] sm3HashCode = Hex.decode(sm3HexStr);
		byte[] newHashCode = encrypt(srcByte, keyByte);
		return Arrays.equals(newHashCode, sm3HashCode);
	}
}
