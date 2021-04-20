package egovframework.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Enumeration;
public class StringUtil {
	/**
	 * 초성
	 */
	private static final char[] firstSounds = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ',
			'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

	/**
	 * 문자 하나가 한글인지 검사
	 *
	 * @param c
	 *            검사 하고자 하는 문자
	 * @return 한글 여부에 따라 'true' or 'false'
	 */
	public static boolean isHangul(char c) {
		if (c < 0xAC00 || c > 0xD7A3)
			return false;
		return true;
	}

	/**
	 * 문자열이 한글인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @return 한글 여부에 따라 'true' or 'false'
	 */
	public static boolean isHangul(String str) {
		if (str == null)
			return false;

		str = str.trim();
		int len = str.length();
		if (len == 0)
			return false;

		for (int i = 0; i < len; i++) {
			if (!isHangul(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 문자 하나가 알파벳인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자
	 * @return 알파벳인지의 여부에 따라 'true' or 'false'
	 */
	public static boolean isAlpha(char c) {
		if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && c != '_'
				&& c != ' ')
			return false;
		return true;
	}

	/**
	 * 문자열이 알파벳인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @return 알파벳인지의 여부에 따라 'true' or 'false'
	 */
	public static boolean isAlpha(String str) {
		if (str == null)
			return false;

		str = str.trim();
		int len = str.length();
		if (len == 0)
			return false;

		for (int i = 0; i < len; i++) {
			if (!isAlpha(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 문자 하나가 숫자인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자
	 * @return 숫자인지의 여부에 따라 'true' or 'false'
	 */
	public static boolean isNumber(char c) {
		if (c < '0' || c > '9')
			return false;
		return true;
	}

	/**
	 * 문자열이 숫자인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @return 숫자인지의 여부에 따라 'true' or 'false'
	 */
	public static boolean isNumber(String str) {
		if (str == null)
			return false;

		str = str.trim();
		int len = str.length();
		if (len == 0)
			return false;

		for (int i = 0; i < len; i++) {
			if (!isNumber(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 문자 하나가 전화번호 형식인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자
	 * @return 숫자인지의 여부에 따라 'true' or 'false'
	 */
	public static boolean isNumberDesh(char c) {
		if (c < '0' || c > '9')
			if ((c < '0' || c > '9') && c != '-')
				return false;
		return true;
	}

	/**
	 * 문자열이 전화번호(숫자 -) 로만 이루어 져있는지 검사
	 *
	 * @param str
	 * @return
	 */
	public static boolean isTel(String str) {
		if (str == null)
			return false;

		str = str.trim();
		int len = str.length();
		if (len == 0 || len > 13)
			return false;

		for (int i = 0; i < len; i++) {
			if (!isNumberDesh(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 문자열이 우편번호(숫자 -) 로만 이루어 져있는지 검사
	 *
	 * @param str
	 * @return
	 */
	public static boolean isZip_Desh(String str) {
		if (str == null)
			return false;

		str = str.trim();
		int len = str.length();
		if (len == 0 || len != 7)
			return false;

		for (int i = 0; i < len; i++) {
			if (!isNumberDesh(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 문자 하나가 한글 또는 알파벳인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @return 한글또는 알파벳 여부에 따라 'true' or 'false'
	 */
	public static boolean isHanAlp(char c) {
		if (!isAlpha(c) && !isHangul(c))
			return false;
		return true;
	}

	/**
	 * 문자열이 한글 또는 알파벳인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @return 한글 여부에 따라 'true' or 'false'
	 */
	public static boolean isHanAlp(String str) {
		if (str == null)
			return false;

		str = str.trim();
		int len = str.length();
		if (len == 0)
			return false;

		for (int i = 0; i < len; i++) {
			if (!isHanAlp(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 문자 하나가 알파벳 또는 숫자인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @return 알파벳 또는 숫자인지의 여부에 따라 'true' or 'false'
	 */
	public static boolean isAlpNum(char c) {
		if (!isAlpha(c) && !isNumber(c))
			return false;
		return true;
	}

	/**
	 * 문자열이 알바벳 또는 숫자인지 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @return 알파벳 또는 숫자인지의 여부에 따라 'true' or 'false'
	 */
	public static boolean isAlpNum(String str) {
		if (str == null)
			return false;

		str = str.trim();
		int len = str.length();
		if (len == 0)
			return false;

		for (int i = 0; i < len; i++) {
			if (!isAlpNum(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 이메일의 형식을 검사한다.
	 *
	 * @param str
	 *            검사 하고자 하는 이메일 문자열.
	 * @return 문자열이 이메일이 맞으면 'true', 아니면 'false'
	 */
	public static boolean isEmail(String email) {
		if (email == null)
			return false;

		email = email.trim();
		int i = email.indexOf('@');
		if (i != email.lastIndexOf('@'))
			return false;

		if (email.indexOf("..") > -1)
			return false;

		if (email.indexOf("--") > -1)
			return false;

		return true;
	}

	/**
	 * 주민등록번호의 형식을 검사한다.
	 *
	 * @param regcode1
	 *            주민등록 번호 앞자리.
	 * @param regcode2
	 *            주민등록 번호 뒷자리.
	 * @param str
	 *            검사 하고자 하는 주민등록번호의 문자열.
	 * @return 문자열이 주민등록번호이면 'true', 아니면 'false'
	 */
	public static boolean isRegcode(String regcode1, String regcode2) {
		return isRegcode(regcode1 + regcode2);
	}

	/**
	 * 주민등록번호의 형식을 검사한다.
	 *
	 * @param regcode
	 *            주민등록 번호.
	 * @return 문자열이 주민등록번호이면 'true', 아니면 'false'
	 */
	public static boolean isRegcode(String regcode) {
		if (regcode == null)
			return false;

		regcode = replace(regcode, "-", "");

		if (!compareLength(regcode, 13) || !isNumber(regcode))
			return false;

		if (Integer.parseInt(regcode.substring(2, 4)) > 12)
			return false;

		if (Integer.parseInt(regcode.substring(4, 6)) > 31)
			return false;

		if ("1234".indexOf(regcode.charAt(6)) < 0)
			return false;

		int sum = 0;
		byte code = 2;

		for (int i = 0; i < 12; i++) {
			sum += Integer.parseInt(regcode.substring(i, i + 1)) * code++;

			if (code > 9)
				code = 2;
		}
		sum = 11 - (sum % 11);
		if (sum > 9)
			sum -= 10;

		if (Integer.parseInt(regcode.substring(12)) != sum)
			return false;
		return true;
	}

	/**
	 * 한글 문자의 마지막 문자의 종성 코드값을 추출
	 *
	 * @param c
	 *            추출 하고자 하는 문자
	 * @return 존재하지 않으면 '0', 존재하면 코드값 (한글이 아닐때 '-1')
	 */
	public static int getLastElementCode(char c) {
		if (!isHangul(c))
			return -1;
		return (c - 0xAC00) % 28;
	}

	/**
	 * 한글 문자열의 마지막 문자의 종성 코드값을 추출
	 *
	 * @param str
	 *            추출 하고자 하는 문자열
	 * @return 존재하지 않으면 '0', 존재하면 코드값 (한글이 아닐때 '-1')
	 */
	public static int getLastElementCode(String str) {
		if (str == null)
			return -1;

		str = str.trim();
		int len = str.length();
		if (len == 0)
			return -1;

		return getLastElementCode(str.charAt(len - 1));
	}

	/**
	 * 마지막 한글 문자의 종성이 존제하는 검사
	 *
	 * @param c
	 *            검사 하고자 하는 문자
	 * @return 존재하지 않으면 'false', 존재하면 'true'
	 */
	public static boolean hasLastElement(char c) {
		if (getLastElementCode(c) > 0)
			return true;
		return false;
	}

	/**
	 * 한글 만자열의 마지막 문자의 종성이 존제하는 검사
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @return 존재하지 않으면 'false', 존재하면 'true'
	 */
	public static boolean hasLastElement(String str) {
		if (str == null)
			return false;

		str = str.trim();
		int len = str.length();
		if (len == 0)
			return false;

		return hasLastElement(str.charAt(len - 1));
	}

	/**
	 * 한글 문자의 초성을 추출
	 *
	 * @param c
	 *            첫번째 문자의 요소를 추출할 문자열
	 * @return 한글 문자의 초성
	 */
	public static char getFirstElement(char c) {
		if (!isHangul(c))
			return c;
		return firstSounds[(c - 0xAC00) / (21 * 28)];
	}

	/**
	 * 문자열의 첫번째 요소를 추출 (한글일 경우 초성을 추출)
	 *
	 * @param str
	 *            첫번째 문자의 요소를 추출할 문자열
	 * @return 첫번째 요소 (한글일 경우 첫번째 문자의 자음)
	 */
	public static char getFirstElement(String str) {
		if (str == null)
			return '\u0000';

		str = str.trim();
		int len = str.length();
		if (len == 0)
			return '\u0000';

		return getFirstElement(str.charAt(0));
	}

	/**
	 * 문자열의 바이트 길이를 주어진 길이와 비교한다.
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @param len
	 *            검사 하고자 하는 길이
	 * @return 문자열의 바이트 길이가 주어진 길이와 같으면 'true' or 'false'
	 */
	public static boolean compareByteLength(String str, int len) {
		return compareByteLength(str, len, "8859_1");
	}

	/**
	 * 문자열의 바이트 길이를 주어진 길이와 비교한다.
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @param len
	 *            검사 하고자 하는 길이
	 * @param enc
	 *            문자 인코딩
	 * @return 문자열의 바이트 길이가 주어진 길이와 같으면 'true' or 'false'
	 */
	public static boolean compareByteLength(String str, int len, String enc) {
		str = str.trim();
		try {
			int l = str.getBytes(enc).length;
			if (l == len)
				return true;
		} catch (UnsupportedEncodingException _ex) {
		}
		return false;
	}

	/**
	 * 문자열의 바이트 길이를 주어진 최소, 최대 길이와 비교한다.
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @param min
	 *            검사 하고자 하는 최소 길이
	 * @param max
	 *            검사 하고자 하는 최대 길이
	 * @return 문자열의 바이트 길이가 유효하면 'true' or 'false'
	 */
	public static boolean compareByteLength(String str, int min, int max) {
		return compareByteLength(str, min, max, "8859_1");
	}

	/**
	 * 문자열의 바이트 길이를 주어진 최소, 최대 길이와 비교한다.
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @param min
	 *            검사 하고자 하는 최소 길이
	 * @param max
	 *            검사 하고자 하는 최대 길이
	 * @param enc
	 *            문자 인코딩
	 * @return 문자열의 바이트 길이가 유효하면 'true' or 'false'
	 */
	public static boolean compareByteLength(String str, int min, int max,
			String enc) {
		str = str.trim();
		try {
			int l = str.getBytes(enc).length;
			if (l >= min && l <= max)
				return true;
		} catch (UnsupportedEncodingException _ex) {
		}
		return false;
	}

	/**
	 * 문자열의 길이를 주어진 길이와 비교한다.
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @param len
	 *            검사 하고자 하는 길이
	 * @return 문자열의 길이가 주어진 길이와 같으면 'true' or 'false'
	 */
	public static boolean compareLength(String str, int len) {
		str = str.trim();
		int l = str.length();
		if (l == len)
			return true;
		return false;
	}

	/**
	 * 문자열의 길이를 주어진 최소, 최대 길이와 비교한다.
	 *
	 * @param str
	 *            검사 하고자 하는 문자열
	 * @param min
	 *            검사 하고자 하는 최소 길이
	 * @param max
	 *            검사 하고자 하는 최대 길이
	 * @return 문자열의 길이가 유효하면 'true' or 'false'
	 */
	public static boolean compareLength(String str, int min, int max) {
		str = str.trim();
		int l = str.length();
		if (l < min || l > max)
			return false;
		return true;
	}

	/**
	 * 문자열을 바꾼다.
	 *
	 * @param s
	 *            바꿀 문자열.
	 * @param oldStr
	 *            과거의 문자열.
	 * @param newStr
	 *            새로운 문자열.
	 * @return 바뀐 문자열.
	 */
	public static String replace(String s, String oldStr, String newStr) {
		int len = s.length() + (newStr.length() - oldStr.length())
				* (s.length() / oldStr.length());
		StringBuffer r = new StringBuffer(len);

		int pos = 0;
		int index = 0;

		while ((pos = s.indexOf(oldStr, index)) != -1) {
			r.append(s.substring(index, pos));
			r.append(newStr);
			index = pos + oldStr.length();
		}
		r.append(s.substring(index));

		return r.toString();
	}

	/**
	 * 문자열을 주어진 길이만큼 자른다.
	 *
	 * @param s
	 *            자를 문자열.
	 * @param len
	 *            자를 문자열의 길이.
	 * @return 바뀐 문자열.
	 */
	public static String cut(String s, int len) {
		return cut(s, len, "...");
	}

	/**
	 * 문자열을 주어진 길이만큼 자른다.
	 *
	 * @param s
	 *            자를 문자열.
	 * @param len
	 *            자를 문자열의 길이.
	 * @param prefix
	 *            자른후에 뒤에 붙일 문자열.
	 * @return 바뀐 문자열.
	 */
	public static String cut(String s, int len, String prefix) {
		if (s == null)
			return null;

		s = s.trim();
		if (s.equals(""))
			return s;

		int l = s.length();
		if (0 >= len)
			return "";
		if (l < len)
			return s;

		return s.substring(0, len) + prefix;
	}

	/**
	 * 문자열을 HTML형식으로 보여주기
	 *
	 * @param sText
	 *            원본 문자열
	 * @return HTML형식으로 변환된 문자열을 반환한다.
	 */
	public static String toHtmlType(String sText) {

		sText = replace(sText, " ", "&nbsp;");
		sText = replace(sText, "\"", "&quot;");
		sText = replace(sText, "\r\n", "<br>");

		return sText;

	}

	public static String toTextType(String sText) {

		sText = replace(sText, "&nbsp;", " ");
		sText = replace(sText, "&quot;", "\"");
		sText = replace(sText, "<br>", "\r\n");

		return sText;
	}

	/**
	 * iMaxbyte를 초과하는 문자열을 제거한다.
	 *
	 *
	 */
	public static String Bytesubstring(String sText, int iMaxbyte, String sGubun) {

		int ibyte = 0;
		int iLength = 0;
		String sResult = "";

		if (sText == null || sText.length() == 0) {
			return sResult;
		}

		if (iMaxbyte == 0) {
			return sText;
		}

		try {

			for (int i = 0; i < sText.length(); i++) {
				char c = sText.charAt(i); // 문자하나하나를 char로 변환한다.

				if (c < 0xAC00 || c > 0xD7A3) {
					ibyte = ibyte + 1;
				} else {
					ibyte = ibyte + 2;
				}

				if (ibyte <= iMaxbyte) {
					iLength = i + 1;
				}
			}

			if (ibyte > iMaxbyte) {
				if (sText.length() > iLength) {
					sResult = sText.substring(0, iLength) + sGubun;
				} else {
					sResult = sText;
				}
			} else {
				sResult = sText;
			}

		} catch (Exception e) {
			System.out.println("Bytesubstring " + e);
		}
		return sResult;
	}

	/**
	 * CLOB 데이타를 String으로 변환하는 함수
	 *
	 * @param t_clob
	 *            CLOB 객체
	 * @return 변환된 문자열
	 */
	public static String ClobToString(Clob t_clob) {

		String ret = "";

		if (t_clob == null)
			return ret;

		try {

			Reader sr = t_clob.getCharacterStream();

			char[] clobBuff = new char[(int) t_clob.length()];

			sr.read(clobBuff);

			ret = new String(clobBuff);

		} catch (Exception e) {
			System.out.println(e.toString() + "StringUtil - ClobToString");
			ret = "";
		}

		return ret;

	}

	public static String BlobToString(Blob b_lob) throws IOException,
			SQLException {

		if (b_lob == null)
			return "";
		byte[] raw_buffer = new byte[(int) b_lob.length()];

		// First via streaming as a single chunk.
		InputStream raw_instream = b_lob.getBinaryStream();
		raw_instream.read(raw_buffer);

		String out_buffer = new String(raw_buffer);
		raw_instream.close();

		// Now directly as a byte array.
		raw_buffer = b_lob.getBytes(1, (int) b_lob.length());
		out_buffer = new String(raw_buffer);

		return out_buffer;

	}

	/**
	 * 값 체크
	 *
	 * @param val
	 * @return
	 */
	public static boolean chk(String val) {

		boolean bFlag = false;

		try {

			if (val != null && (val.trim()).length() > 0) {
				bFlag = true;
			} else {
				bFlag = false;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return bFlag;
	}

	/**
	 * html에서 < xxx > 형태의 모든 태그를 제거하고 텍스트만 추출하여 리턴
	 *
	 * @param strHtml
	 * @return
	 */
	public static String toText(String strHtml) {
		StringBuffer sbHtml = new StringBuffer(strHtml);
		StringBuffer sbResult = new StringBuffer();

		int iOpenStatus = 0; // html태그 안에 있는지 판단한다. 안에 있으면 1, 아니면 0

		for (int i = 0; i < sbHtml.length(); i++) {
			if (openTag(sbHtml.charAt(i)) == 1)
				iOpenStatus = 1;
			if (openTag(sbHtml.charAt(i)) == 2)
				iOpenStatus = 0;

			if (iOpenStatus == 0 && sbHtml.charAt(i) != '>')
				sbResult.append(sbHtml.charAt(i));
		}

		return sbResult.toString();
	}

/**
	*	스트링에 '<' 가 포함되어 있으면 1을 리턴, '>'이 포함되어 있으면 2를 리턴
	*/
	private static int openTag(char c) {
		int cb = 0;
		if (c == '<')
			cb = 1;
		if (c == '>')
			cb = 2;
		return cb;
	}

	/**
	 * clob을 디비에서 읽어 올때 (예)rs.getCharacterStream("content");의 형태로 읽을때 String으로
	 * 변환하여 리턴
	 */
	public static String convertClobToString(Reader input) {

		StringBuffer output = new StringBuffer();
		char[] buffer = new char[1024];
		int byteRead;

		try {
			while ((byteRead = input.read(buffer, 0, 1024)) != -1) {
				output.append(buffer, 0, byteRead);
			}

			input.close();

		} catch (java.io.IOException e) {
			System.out
					.println("Ocurr Error!. ParseHtmlToText.convertClobToString. Detail="
							+ e);
		}
		return output.toString();
	}

	public static String readBlobData(Reader reader) throws IOException {
		String r = "";
		int cnt = 0;

		if (reader != null) {
			while ((cnt = reader.read()) != -1) {
				r = r + (char) cnt;
			}
		}
		return r;
	}

	public static String getNumberString(String str) {
		String result = str;
		if (str != null && !str.equals("")) {
			int pos = str.indexOf(".");
			if (pos == 0)
				result = "0" + str;
		}
		return result;
	}

	public static boolean isNull(String str) {
		return str == null || str.equals(null) || str.equals("");
	}

	public static boolean isNotNull(String str) {
		return str != null && !str.equals(null) && !str.equals("");
	}

	public static boolean parseBoolean(String str) {
		if (isNull(str))
			return false;
		return str.equalsIgnoreCase("true");
	}

	public static boolean equals(String str1, String str2) {
		if (isNotNull(str1) && isNotNull(str2))
			return str1.equals(str2);
		else
			return false;
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (isNotNull(str1) && isNotNull(str2))
			return str1.equalsIgnoreCase(str2);
		else
			return false;
	}

	public static String longToString(Long in) {
		if (in != null)
			return nvl(in.toString(), "0");
		else
			return "0";
	}

	public static String strReplace(String inputStr, String findStr,
			String targetStr) {
		String tmpStr = "";
		for (int i = 0; i < inputStr.length(); i++) {
			if (i + findStr.length() > inputStr.length()) {
				tmpStr = tmpStr + inputStr.substring(i, inputStr.length());
				return tmpStr;
			}
			if (inputStr.substring(i, i + findStr.length()).equals(findStr)) {
				tmpStr = tmpStr + targetStr;
				i = (i + findStr.length()) - 1;
			} else {
				tmpStr = tmpStr + inputStr.substring(i, i + 1);
			}
		}

		return tmpStr;
	}

	public static String substring(String source, int spos, int epos) {
		String result = "";
		if (source != null && source.length() >= epos)
			result = source.substring(spos, epos);
		else
			result = source;
		return result;
	}

	public static String substring(String source, int spos) {
		String result = "";
		if (source != null && source.length() >= spos)
			result = source.substring(spos);
		else
			result = source;
		return result;
	}

	public static String nvl(String str) {
		if (isNull(str))
			str = "";
		else if (str.equalsIgnoreCase("null"))
			str = "";
		return str;
	}

	public static String nvl(String str, String chstr) {
		if (isNull(str))
			str = chstr;
		else if (str.equalsIgnoreCase("null"))
			str = chstr;
		return str;
	}

	public static String nvl(String str, int chstr) {
		if (isNull(str))
			str = chstr + "";
		else if (str.equalsIgnoreCase("null"))
			str = chstr + "";
		return str;
	}

	public static String nvl(String str, long chstr) {
		if (isNull(str))
			str = chstr + "";
		else if (str.equalsIgnoreCase("null"))
			str = chstr + "";
		return str;
	}

	public static long nvlLong(String str) {
		long result = 0L;
		try {
			result = Long.parseLong(nvl(str, "0"));
		} catch (Exception e) {
			result = 0L;
		}
		return result;
	}

	public static long nvlLong(String str, long value) {
		long result = 0L;
		try {
			result = Long.parseLong(nvl(str, value));
		} catch (Exception e) {
			result = 0L;
		}
		return result;
	}

	public static int nvlInt(String str) {
		int result = 0;
		try {
			result = Integer.parseInt(nvl(str, "0"));
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public static int nvlInt(String str, int value) {
		int result = 0;
		try {
			result = Integer.parseInt(nvl(str, value));
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public static double nvlDouble(String str) {
		double result = 0;
		try {
			result = Double.parseDouble(str);
		} catch (Exception e) {
			result = 0.0;
		}
		return result;
	}

	public static double nvlDouble(String str, double dInitVal) {
		double result = 0;
		try {
			result = Double.parseDouble(str);
		} catch (Exception e) {
			result = dInitVal;
		}
		return result;
	}

	public static String textToHtml(String str) {
		String result = "";
		if (str != null && !str.equals("")) {
			result = str.replaceAll("&", "&amp;");
			result = str.replaceAll("\"", "&quot;");
			result = str.replaceAll("<", "&lt;");
			result = str.replaceAll(">", "&gt;");
			result = nlToBr(str);
		}
		return result;
	}

	public static String htmlToText(String str) {
		String result = "";
		if (str != null && !str.equals("")) {
			result = str.replaceAll("&gt;", ">");
			result = str.replaceAll("&lt;", "<");
			result = str.replaceAll("&quot;", "\"");
			result = str.replaceAll("&amp;", "&");
			result = brToNl(str);
		}
		return result;
	}

	public static String nlToBr(String str) {
		String result = "";
		if (str != null && !str.equals(""))
			result = str.replaceAll("\n", "<br>");
		return result;
	}

	public static String brToNl(String str) {
		String result = "";
		if (str != null && !str.equals(""))
			result = str.replaceAll("<br>", "\n");
		return result;
	}

	public static String toJavaScript(String str) {
		String result = "";
		if (str != null && !str.equals("")) {
			result = str.replaceAll("\\\\", "\\\\\\\\");
			result = str.replaceAll("\r", "");
			result = str.replaceAll("\n", "\\\\n");
			result = str.replaceAll("\"", "\\\\\"");
			result = str.replaceAll("'", "\\\\'");
		}
		return result;
	}

	public static String cutKor(String p_sString, int p_iTo) {
		if (p_sString != null && !p_sString.equals("")) {
			byte b[] = p_sString.getBytes();
			if (b.length < p_iTo)
				return p_sString;
			byte b1[] = new byte[p_iTo];
			for (int i = 0; i < p_iTo; i++)
				b1[i] = b[i];

			String s = new String(b1);
			if (s.equals("")) {
				b1 = new byte[p_iTo + 1];
				for (int i = 0; i < p_iTo + 1; i++)
					b1[i] = b[i];

			}
			s = new String(b1) + "...";
			return s;
		} else {
			return "";
		}
	}

	public static String stripFrontZero(String str) {
		String ret = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).equals("0"))
				continue;
			ret = str.substring(i, str.length());
			break;
		}

		return ret;
	}

	public static String thread_idToStr(String str) {
		String index = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String tmpStr = "";
		String ret = "";
		tmpStr = stripFrontZero(str);
		for (int i = 0; i < tmpStr.length(); i++)
			ret = ret + index.indexOf(tmpStr.substring(i, i + 1).toUpperCase());

		return ret;
	}

	public static long thread_idToLong(String str) {
		return Long.parseLong(thread_idToStr(str));
	}

	public static String paddingLeft(String str, String pad, int cnt) {
		for (int i = 0; i < cnt; i++)
			str = pad + str;

		return str;
	}

	public static String paddingRight(String str, String pad, int cnt) {
		for (int i = 0; i < cnt; i++)
			str = str + pad;

		return str;
	}

	public static String formatNumber(long in, String strPattern) {
		DecimalFormat df = new DecimalFormat(strPattern);
		return df.format(in);
	}

	public static String formatNumber(double in, String strPattern) {
		DecimalFormat df = new DecimalFormat(strPattern);
		return df.format(in);
	}


	/**
	 * html을 변환하여 화면에 태그를 그대로 보여준다.(목록, 상세보기에 사용)
	 *
	 * @param str
	 * @return
	 */
	public static String textTohtml(String str) {
		String result = "";
		if (str != null) {
			result = str;

			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
			result = result.replaceAll("'", "&#39;");

			result = result.replaceAll("\n", "<br>");
			result = result.replaceAll("\n\n", "<p>");
			result = result.replaceAll(" ", "&nbsp;");
			result = result.replaceAll("\t",
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");

			// result = UtilStringMng.replaceString(result, "<br>", "\n" );
		}

		return result;
	}

	/**
	 * html을 textarea에 뿌려준다.
	 *
	 * @param str
	 * @return
	 */
	public static String htmlTotext(String str) {
		String result = "";
		if (str != null) {
			result = str;

			result = result.replaceAll("&amp;", "&");
			result = result.replaceAll("&lt;", "<");
			result = result.replaceAll("&gt;", ">");
			result = result.replaceAll("&quot;", "\"");
			result = result.replaceAll("&#39;", "'");
			result = result.replaceAll("        ", "\t");

			result = result.replaceAll("<br>", "\n");
			result = result.replaceAll("<p>", "\n");
			result = result.replaceAll(" ", "&nbsp;");
			result = result.replaceAll("\t",
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");

			// result= UtilStringMng.replaceString(result,"'","\'");
			// result = UtilStringMng.replaceString(result, "<br>", "\n" );
		}

		return result;
	}

	/**
	 * json string으로 변환한다.
	 *
	 * @param str
	 * @return
	 */
	public static String textTojson(String str) {
		String result = "";
		if (str != null) {
			result = str;

			result = result.replace("\\", "\\\\");
			result = result.replace("\"", "\\\"");
			result = result.replace("/", "\\/");
			result = result.replace("\b", "\\b");
			result = result.replace("\f", "\\f");
			result = result.replace("\n", "\\n");
			result = result.replace("\r", "\\r");
			result = result.replace("\t", "\\t");
		}

		return result;
	}

	  /**
     * lpad 함수
     *
     * @param str   대상문자열, len 길이, addStr 대체문자
     * @return      문자열
     */

    public static String lpad(String str, int len, String addStr) {
        String result = str;
        int templen   = len - result.length();

        for (int i = 0; i < templen; i++){
              result = addStr + result;
        }

        return result;
     }


	/**
	 * JavaScript 의 Join()과 같은 기능으로
	 * 배열을 받았을 경우 배열을 하나의 String 으로 구분자를 받아서 이어붙여 준다.
	 * 구분자가 없을 경우 [,]로 구분한다.
	 *
	 * @param strArr[]
	 *            : 변경할 배열
	 * @param div
	 *            : 이용할 구분자 Default 는 [,]
	 * @return String
	 */
	public static String join(String strArr[], String div) {
		String returnVal = "";
		for(int i = 0; i < strArr.length; i++){
			returnVal += div +  strArr[i];
		}

		return returnVal.substring(1);
	}

	/**
	 * JavaScript 의 Join()과 같은 기능으로
	 * 배열을 받았을 경우 배열을 하나의 String 으로 구분자를 받아서 이어붙여 준다.
	 * 구분자가 없을 경우 [,]로 구분한다.
	 *
	 * @param strArr[]
	 *            : 변경할 배열
	 * @param div
	 *            : 이용할 구분자 Default 는 [,]
	 * @param wrap
	 *            : 배열의 값을 감쌀 String
	 * @return String
	 */
	public static String join(String strArr[], String div, String wrap) {
		String returnVal = "";
		for(int i = 0; i < strArr.length; i++){
			returnVal += div +  wrap +strArr[i]+ wrap;
		}

		return returnVal.substring(1);
	}

	/**
	 * 현재 서버의 IP 주소를 가져옵니다.
	 *
	 * @return IP 주소
	 */
	public static String getLocalServerIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& !inetAddress.isLinkLocalAddress()
							&& inetAddress.isSiteLocalAddress()) {
						return inetAddress.getHostAddress().toString().replaceAll(".", "");
					}
				}
			}
		} catch (SocketException ex) {
		}
		return null;
	}

	public static String null2Space(String paramString){
		String str = "";
		try{
			if((paramString == null) || (paramString.trim().length() == 0) || (paramString.equals("null"))) {
				str = "";
			}else{
				str = paramString;
			}
		}catch (NullPointerException localNullPointerException) {}catch (Exception localException){
			localException.printStackTrace();
		}
		return str;
	}
	public static String null2String(String paramString1,String paramString2){
		String str = "";
		try{
			if((paramString1 == null) || (paramString1.trim().length() == 0) || (paramString1.equals("null"))) {
				str = paramString2;
			}else{
				str = paramString1;
			}
		}catch (NullPointerException localNullPointerException) {}catch (Exception localException){
			localException.printStackTrace();
		}
		return str;
	}
	public static int stringToInt(String paramString){
		
		String a	= null2String(paramString,"0");
		String b	= a.replaceAll("[^0-9]", "0");
		int c		= Integer.parseInt(b);
		
		return c;
	}
	
	  /**
     * XSS 방지 처리.
     * 
     * @param data
     * @return
     */
	public static String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }
        
        String ret = data;
        
        
        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
        
        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
        
        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
        
        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        
        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }
	public static String displayTag(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }		
		String str = data;
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&apos;", "'");
		return str;
	}
	
}
