/**
 *
 */
package egovframework.common.utils;

import java.util.Random;

/**
 * 프로젝트명 : CREDU_GGW <BR>
 * 패키지명 : com.credu.library <BR>
 * 파일명 : EnDecode.java <BR>
 * 작성날짜 : 2009. 10. 08 <BR>
 * 작성자 : 신동래 (drsin@credu.com) <BR>
 * 처리업무 :seed 암/복호화 <BR>
 * 수정내용 : <BR>
 * JDK버젼 : 1.4 Copyright (c) 1999-2020 Credu. Inc All Rights Reserved. <BR>
 */

public class EnDecode {
	public static String[] EncodeByFileInType(String strType, String filePath, String strData) {
		String[] arrRet = new String[4];
		String strD0 = strType;
		String strD1 = EncodeBySType(strData);
		String strD2 = null;
		String strD3 = null;

		arrRet[0] = strD1;
		arrRet[1] = strD2;
		arrRet[2] = strD3;
		arrRet[3] = strD0;

		return arrRet;
	}

	public static String[] EncodeByFile(String filePath, String strData) {
		String strType = "SDS";
		return EncodeByFileInType(strType, filePath, strData);
	}

	public static String getRandomPassword() {
		Random random = new Random(100);
		return EncodeBySType("" + random.nextInt(10)).substring(0, 8).toUpperCase();
	}

	public static String[] EncodeByCertIDInType(String strType, String certID, String strData) {

		String[] arrRet = new String[4];
		String strD0 = strType;
		String strD1 = EncodeBySType(strData);
		String strD2 = null;
		String strD3 = null;

		arrRet[0] = strD1;
		arrRet[1] = strD2;
		arrRet[2] = strD3;
		arrRet[3] = strD0;

		return arrRet;
	}

	public static String[] EncodeByCertID(String certID, String strData) {
		String strType = "SDS";
		return EncodeByCertIDInType(strType, certID, strData);
	}

	public static String DecodeByFileInType(String strType, String filePath, String strKey, String strHash, String strData) {
		return DecodeBySType(strData);
	}

	public static String DecodeByFile(String filePath, String strKey, String strHash, String strData) {
		String strType = "SDS";
		return DecodeByFileInType(strType, filePath, strKey, strHash, strData);
	}

	/**
	 * 암호화
	 *
	 * @param strData
	 * @return
	 */
	public static String EncodeBySType(String strData) {
		return Encrypt.com_Encode(":" + strData + ":sisenc");
	}

	/**
	 * 복호화 작업
	 *
	 * @param strData
	 * @return
	 */
	public static String DecodeBySType(String strData) {

		String strRet = Encrypt.com_Decode(strData);

		int e = strRet.indexOf(":");
		int d = strRet.indexOf(":sisenc");
		if (e > -1 && d > -1) {
			strRet = strRet.substring(e + 1, d);
		}

		return strRet;
	}
}