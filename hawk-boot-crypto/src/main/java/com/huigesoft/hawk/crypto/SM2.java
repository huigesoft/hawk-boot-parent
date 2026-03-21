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
import java.security.SecureRandom;
import java.security.Security;

import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.util.encoders.Base64;

/**
 * @ClassName: SM2
 *
 * @Description: SM2国密算法
 *
 * @author: 王文辉
 *
 * @date: 2025年9月23日 10:11:01
 * 
 */
public class SM2 {

	private static String CURVE_NAME = "sm2p256v1";

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * 
	 * @Title: genKeyPair
	 *
	 * @Description: 生产SM秘钥对
	 * 
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: KeyPair
	 * 
	 * @date: 2025年9月23日 10:08:57
	 *
	 * @throws
	 */
	public synchronized static KeyPair genKeyPair() throws Exception {
		ECNamedCurveParameterSpec sm2Spec = ECNamedCurveTable.getParameterSpec(CURVE_NAME);
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
		keyPairGenerator.initialize(sm2Spec);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * 
	 * @Title: genKeyPairStr
	 *
	 * @Description: 生产SM秘钥对字符串形式
	 * 
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: KeyPairStr
	 * 
	 * @date: 2025年9月24日 09:58:26
	 *
	 * @throws
	 */
	public synchronized static KeyPairStr genKeyPairStr() throws Exception {
		KeyPair keyPair = genKeyPair();
		return new KeyPairStr(Base64.toBase64String(keyPair.getPublic().getEncoded()),
				Base64.toBase64String(keyPair.getPrivate().getEncoded()));
	}

	/**
	 * 
	 * @Title: genKeyPair
	 *
	 * @Description: 根据 EC曲线名生成密钥对
	 * 
	 * @param: @param  curveName EC曲线名
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: KeyPair
	 * 
	 * @date: 2025年9月23日 10:09:37
	 *
	 * @throws
	 */
	public synchronized static KeyPair genKeyPair(String curveName) throws Exception {
		ECNamedCurveParameterSpec sm2Spec = ECNamedCurveTable.getParameterSpec(curveName);
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
		keyPairGenerator.initialize(sm2Spec);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * 
	 * @Title: genKeyPairStr
	 *
	 * @Description: 根据 EC曲线名生成密钥对
	 * 
	 * @param: @param  curveName EC曲线名
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: KeyPairStr
	 * 
	 * @date: 2025年9月24日 09:58:04
	 *
	 * @throws
	 */
	public synchronized static KeyPairStr genKeyPairStr(String curveName) throws Exception {
		KeyPair keyPair = genKeyPair(curveName);
		return new KeyPairStr(Base64.toBase64String(keyPair.getPublic().getEncoded()),
				Base64.toBase64String(keyPair.getPrivate().getEncoded()));
	}

	/**
	 * 
	 * @Title: encrypt
	 *
	 * @Description: SM2加密
	 * 
	 * @param: @param  data 加密数据
	 * @param: @param  publicKey 公钥
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: byte[]
	 * 
	 * @date: 2025年9月23日 10:22:47
	 *
	 * @throws
	 */
	public synchronized static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
		ECPublicKeyParameters localECPublicKeyParameters = null;
		if (publicKey instanceof BCECPublicKey) {
			BCECPublicKey localECPublicKey = (BCECPublicKey) publicKey;
			ECParameterSpec localECParameterSpec = localECPublicKey.getParameters();
			ECDomainParameters localECDomainParameters = new ECDomainParameters(localECParameterSpec.getCurve(),
					localECParameterSpec.getG(), localECParameterSpec.getN());
			localECPublicKeyParameters = new ECPublicKeyParameters(localECPublicKey.getQ(), localECDomainParameters);
		}
		SM2Engine engine = new SM2Engine();
		engine.init(true, new ParametersWithRandom(localECPublicKeyParameters, new SecureRandom()));
		return engine.processBlock(data, 0, data.length);
	}

	/**
	 * 
	 * @Title: encode
	 *
	 * @Description: SM2加密
	 * 
	 * @param: @param  data 加密数据
	 * @param: @param  publicKey 公钥
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月23日 10:30:27
	 *
	 * @throws
	 */
	public synchronized static String encrypt(String data, PublicKey publicKey) throws Exception {
		byte[] byteData = data.getBytes();
		byte[] encrypts = encrypt(byteData, publicKey);
		return Base64.toBase64String(encrypts);
	}

	/**
	 * 
	 * @Title: encrypt
	 *
	 * @Description: SM2加密
	 * 
	 * @param: @param  data 加密数据
	 * @param: @param  publicKey 公钥
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月24日 09:57:35
	 *
	 * @throws
	 */
	public synchronized static String encrypt(String data, String publicKey) throws Exception {
		KeyFactory instance = KeyFactory.getInstance("EC", "BC");
		PublicKey key = instance.generatePublic(new java.security.spec.X509EncodedKeySpec(Base64.decode(publicKey)));
		return encrypt(data, key);
	}

	/**
	 * 
	 * @Title: decrypt
	 *
	 * @Description: SM2解密
	 * 
	 * @param: @param  encryptdata
	 * @param: @param  privateKey
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: byte[]
	 * 
	 * @date: 2025年9月23日 10:23:52
	 *
	 * @throws
	 */
	public synchronized static byte[] decrypt(byte[] encryptdata, PrivateKey privateKey) throws Exception {
		SM2Engine engine = new SM2Engine();
		BCECPrivateKey sm2PriK = (BCECPrivateKey) privateKey;
		ECParameterSpec localECParameterSpec = sm2PriK.getParameters();
		ECDomainParameters localECDomainParameters = new ECDomainParameters(localECParameterSpec.getCurve(),
				localECParameterSpec.getG(), localECParameterSpec.getN());
		ECPrivateKeyParameters localECPrivateKeyParameters = new ECPrivateKeyParameters(sm2PriK.getD(),
				localECDomainParameters);
		engine.init(false, localECPrivateKeyParameters);
		return engine.processBlock(encryptdata, 0, encryptdata.length);
	}

	/**
	 * 
	 * @Title: decrypt
	 *
	 * @Description: SM2解密
	 * 
	 * @param: @param  encryptdata
	 * @param: @param  privateKey
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月23日 10:30:45
	 *
	 * @throws
	 */
	public synchronized static String decrypt(String encryptdata, PrivateKey privateKey) throws Exception {
		byte[] byteData = Base64.decode(encryptdata);
		byte[] decrypt = decrypt(byteData, privateKey);
		return new String(decrypt);
	}

	/**
	 * 
	 * @Title: decrypt
	 *
	 * @Description: SM2解密
	 * 
	 * @param: @param  encryptdata 加密数据
	 * @param: @param  privateKey 私钥
	 * @param: @return
	 * @param: @throws Exception
	 *
	 * @return: String
	 * 
	 * @date: 2025年9月24日 09:57:01
	 *
	 * @throws
	 */
	public synchronized static String decrypt(String encryptdata, String privateKey) throws Exception {
		KeyFactory instance = KeyFactory.getInstance("EC", "BC");
		PrivateKey key = instance
				.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(Base64.decode(privateKey)));
		return decrypt(encryptdata, key);
	}
}
