package com.app.wallet.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * This example program shows how AES encryption and decryption can be done in
 * Java. Please note that secret key and encrypted text is unreadable binary and
 * hence in the following program we display it in hexadecimal format of the
 * underlying bytes.
 * 
 * @author Jayson
 */
public class AESEncryption {

	public static class Password {
		public String secretKey;
		public String encPasswd;

		public Password(String secretKey, String encPasswd) {
			this.secretKey = secretKey;
			this.encPasswd = encPasswd;
		}
	}

	public static AESEncryption inst = new AESEncryption();

	private AESEncryption() {
	}

	public Password encypt(String plainText) throws Exception {
		SecretKey secKey = getSecretEncryptionKey();
		String encPasswd = encode(encryptText(plainText, secKey));
		return new Password(encode(secKey.getEncoded()), encPasswd);
	}

	public String decrypt(Password passwd) throws Exception {
		byte[] encodedKey = decode(passwd.secretKey);
		SecretKey secKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return decryptText(decode(passwd.encPasswd), secKey);
	}

	private String encode(byte[] data) throws Exception {
		return Base64.getEncoder().encodeToString(data);
	}

	private byte[] decode(String data) throws Exception {
		return Base64.getDecoder().decode(data);
	}

	/**
	 * 1. Generate a plain text for encryption 2. Get a secret key (printed in
	 * hexadecimal form). In actual use this must by encrypted and kept safe. The
	 * same key is required for decryption. 3.
	 */
	public static void main(String[] args) throws Exception {
		String plainText = "Hello World";
		SecretKey secKey = getSecretEncryptionKey();

		byte[] cipherText = encryptText(plainText, secKey);
		String decryptedText = decryptText(cipherText, secKey);

		System.out.println("Original Text:" + plainText);
		System.out.println("AES Key (Hex Form):" + bytesToHex(secKey.getEncoded()));
		System.out.println("Encrypted Text (Hex Form):" + bytesToHex(cipherText));
		System.out.println("Descrypted Text:" + decryptedText);

	}

	/**
	 * gets the AES encryption key. In your actual programs, this should be safely
	 * stored.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static SecretKey getSecretEncryptionKey() throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(128); // The AES key size in number of bits
		SecretKey secKey = generator.generateKey();
		return secKey;
	}

	/**
	 * Encrypts plainText in AES using the secret key
	 * 
	 * @param plainText
	 * @param secKey
	 * @return
	 * @throws Exception
	 */
	private static byte[] encryptText(String plainText, SecretKey secKey) throws Exception {
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
		byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
		return byteCipherText;
	}

	/**
	 * Decrypts encrypted byte array using the key used for encryption.
	 * 
	 * @param byteCipherText
	 * @param secKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.DECRYPT_MODE, secKey);
		byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
		return new String(bytePlainText);
	}

	/**
	 * Convert a binary byte array into readable hex form
	 * 
	 * @param hash
	 * @return
	 */
	private static String bytesToHex(byte[] hash) {
		return DatatypeConverter.printHexBinary(hash);
	}
}