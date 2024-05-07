package boardServer.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordCrypto {
	
	// 암호화
	public static String encrypt(String password) {
		// 두 번째 값으로 솔트값을 받는데 솔트값 : 암호화를 위한 키값
		// 해싱된 패스워드 반환
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	// 복호화
	public static boolean decrypt(String password, String encryptedPassword) {
		return BCrypt.checkpw(password, encryptedPassword);
	}
	
}
