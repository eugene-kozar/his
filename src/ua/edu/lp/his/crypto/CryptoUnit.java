package ua.edu.lp.his.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUnit {
	
	private static final String password = "his_2015password";
	
	public static byte[] encodeData(byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedData = cipher.doFinal(data);
		return encryptedData;
	}
	
	public static byte[] decodeData(byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte [] decryptedData = cipher.doFinal(data);
		return decryptedData;
	}

}
