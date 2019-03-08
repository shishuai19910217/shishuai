package com.ido85.frame.common.utils;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.ido85.frame.common.security.Digests;

@SuppressWarnings("restriction")
public class KeysUtils {

	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	
	private static final BASE64Decoder base64Decoder = new BASE64Decoder();
	private static final BASE64Encoder base64Encoder = new BASE64Encoder() ;
	
	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 生成AcceptKeyID
	 * @return
	 */
	public static String generateAcceptKeyID(){
		return randomBase62(20).toUpperCase();
	}
	
	/**
	 * 生成AcceptSecurityKeyID
	 * @return
	 */
	public static String generateSecurityKeyID(){
		byte[] salt = Digests.generateSalt(8);
		return (Encodes.encodeHex(salt)+"/"+Encodes.encodeHex(Digests.sha1(uuid().getBytes(), salt))).toUpperCase();
	}

	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
//		byte[] publicKey = key.getEncoded();
		return encryptBASE64(key.getEncoded());
	}

	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
//		byte[] privateKey = key.getEncoded();
		return encryptBASE64(key.getEncoded());
	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return base64Decoder.decodeBuffer(key);
	}

	public static String encryptBASE64(byte[] key) throws Exception {
		return base64Encoder.encodeBuffer(key);
	}

	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(512);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
}
