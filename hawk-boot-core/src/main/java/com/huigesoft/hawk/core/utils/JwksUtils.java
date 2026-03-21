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

import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.huigesoft.hawk.core.properties.HawkKeyStoreProperties;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;

/**
 * 
 * @ClassName: JwksUtils
 *
 * @Description: JWK 证书工具类
 *
 * @author: 王文辉
 *
 * @date: 2025-08-22 13:29:19
 *
 */
public class JwksUtils {
	private static final String ecKeyId = "hawk_ec_kid";;
	private static final String rsaKeyId = "hawk_rsa_kid";
	private static final String octetSequenceKeyId = "hawk_octet_sequence_kid";

	private JwksUtils() {
	}

	/**
	 * 生成RSA加密key (即JWK)
	 */
	public static RSAKey generateRsa(HawkKeyStoreProperties properties) {
		// 生成RSA加密的key
		KeyPair keyPair = KeyGeneratorUtils.generateRsaKey(properties);
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 构建RSA加密key
		return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(rsaKeyId).build();
	}

	/**
	 * 生成EC加密key (即JWK)
	 */
	public static ECKey generateEc() {
		// 生成EC加密的key
		KeyPair keyPair = KeyGeneratorUtils.generateEcKey();
		// 公钥
		ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
		// 私钥
		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
		// 根据公钥参数生成曲线
		Curve curve = Curve.forECParameterSpec(publicKey.getParams());
		// 构建EC加密key
		return new ECKey.Builder(curve, publicKey).privateKey(privateKey).keyID(ecKeyId).build();
	}

	/**
	 * 生成HmacSha256密钥
	 */
	public static OctetSequenceKey generateSecret() {
		SecretKey secretKey = KeyGeneratorUtils.generateSecretKey();
		return new OctetSequenceKey.Builder(secretKey).keyID(octetSequenceKeyId).build();
	}
}

class KeyGeneratorUtils {

	private KeyGeneratorUtils() {
	}

	public static KeyPair loadKeyPairFromKeystore(String storePassword, String keyPassword, String keyAlias,
			String type, String path) {
		try {
			KeyStore keyStore = KeyStore.getInstance(type);
			InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			keyStore.load(stream, storePassword.toCharArray());
			// PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
			// keyPassword.toCharArray());
			RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) keyStore.getKey(keyAlias, keyPassword.toCharArray());
			if (privateKey == null) {
				throw new RuntimeException("Couldn't load key with alias '" + keyAlias + "' from keystore");
			}
			PublicKey publicKey;
			Certificate certificate = keyStore.getCertificate(keyAlias);
			if (certificate != null) {
				publicKey = certificate.getPublicKey();
			} else {
				RSAPublicKeySpec spec = new RSAPublicKeySpec(privateKey.getModulus(), privateKey.getPublicExponent());
				publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
			}
			return new KeyPair(publicKey, privateKey);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load private key: " + e.getMessage(), e);
		}
	}

	/**
	 * 生成RSA密钥
	 */
	static KeyPair generateRsaKey(HawkKeyStoreProperties properties) {
//		KeyPair keyPair;
//		try {
//			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//			keyPairGenerator.initialize(2048);
//			keyPair = keyPairGenerator.generateKeyPair();
//		} catch (Exception ex) {
//			throw new IllegalStateException(ex);
//		}
//		return keyPair;
//		HawkKeyStoreProperties properties = SpringContextHolder.getBean(HawkKeyStoreProperties.class);
//		HawkKeyStoreProperties properties = new HawkKeyStoreProperties();
		KeyPair keyPair = loadKeyPairFromKeystore(properties.getKeyStorePassword(), properties.getKeyPassword(),
				properties.getAlias(), properties.getKeystoreType(), properties.getPath());
		return keyPair;
	}

	/**
	 * 生成EC密钥
	 */
	static KeyPair generateEcKey() {
		EllipticCurve ellipticCurve = new EllipticCurve(
				new ECFieldFp(new BigInteger(
						"115792089210356248762697446949407573530086143415290314195533631308867097853951")),
				new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853948"),
				new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291"));
		ECPoint ecPoint = new ECPoint(
				new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286"),
				new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109"));
		ECParameterSpec ecParameterSpec = new ECParameterSpec(ellipticCurve, ecPoint,
				new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369"), 1);

		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
			keyPairGenerator.initialize(ecParameterSpec);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	/**
	 * 生成HmacSha256密钥
	 */
	static SecretKey generateSecretKey() {
		SecretKey hmacKey;
		try {
			hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return hmacKey;
	}
}