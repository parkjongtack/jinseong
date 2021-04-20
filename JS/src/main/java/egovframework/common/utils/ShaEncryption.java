package egovframework.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class ShaEncryption {
	
	/**
     * 패스워드 암호화
     * @param userPassword
     *            사용자 패스워드
     * @return 암호화 된 사용자 패스워드
     *         암호화 방식 : SHA-512
     */
    public String encryption(String userPassword) {
        MessageDigest md;
        boolean isSuccess;
        String tempPassword = "";

        try {
            md = MessageDigest.getInstance("SHA-512");

            md.update(userPassword.getBytes());
            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) { 
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                tempPassword += s;
            }
            
        } catch (NoSuchAlgorithmException e) {
            isSuccess = false;
            return "";
        }
        return tempPassword;
    }
    public String encryptionSha256(String userPassword) {
        MessageDigest md;
        boolean isSuccess;
        String tempPassword = "";
        StringBuilder sb;

        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] mb = md.digest(userPassword.getBytes("UTF-8"));
            sb = new StringBuilder();
            for (int i = 0; i < mb.length; i++) { 
            	String s = Integer.toHexString(0xff & mb[i]);
            	if(s.length() == 1) sb.append('0');
            	sb.append(s);
                
            }
            tempPassword = sb.toString();
            System.out.println(tempPassword.length());
            
        } catch (UnsupportedEncodingException ue) {
            isSuccess = false;
            return "";
        } catch (NoSuchAlgorithmException e) {
            isSuccess = false;
            return "";
        }
        return tempPassword;
    }
    
}
