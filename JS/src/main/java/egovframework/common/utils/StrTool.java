package egovframework.common.utils;

import java.util.HashMap;


public class StrTool {

	/**
     * String Null을 공백문자로 변환한다
     * @param arg String 입력 문자열
     * @return String 결과 문자열
	 * @Author  Paul Ha
     */
	public static String sNN(String arg) {
		if( arg == null || arg.length() < 1 )
			return "";
		return arg;
	}
	@SuppressWarnings("rawtypes")
	public static String sNN(HashMap obj) {
		if( obj == null || obj.toString().length() < 1 )
			return "";
		return obj.toString();
	}

	public static String sNN(Object obj) {
		if( obj == null || obj.toString().length() < 1 )
			return "";
		return obj.toString();
	}
	/**
     * String Null을 Default 문자로 변환한다
     * @param arg String 입력 문자열
     * @param default Null일 경우 return할 문자열
     * @return String 결과 문자열
	 * @Author  Paul Ha
     */
	public static String sNN(String arg, String defaultStr) {
		if( arg == null || arg.length() < 1 )
			return defaultStr;
		return arg;
	}
	/**
     * String을 int 변환한다. Null일때 0으로
     * @param String 문자열
     * @return int 문자열
	 * @Author  Paul Ha
     */
	public static int strToInt(String s){
        if (s == null || s.equals(""))  return 0;
		else return Integer.parseInt(s);
	}
	/**
     * 제목String을 길이수로 짜른다. Null일때 ''으로
     * @param String 문자열
     * @return String 문자열
	 * @Author  Paul Ha
     */
	public static String shortTitle(String s) {
		return shortTitle(s, 10);
	}
	public static String shortTitle(String s, int len)
    {
		String str = "";
		if(s.equals("") || s == null) return "";
		try{
			if(s.length() > len) str = s.substring(0,len)+"...";
			else str = s;
		} catch(Exception exception) { }
        return str;
    }
	/**
     * yyyyMMdd 날짜를 .을 붙혀 yyyy.MM.dd형태로 만든다.
     * @param String 문자열
     * @return String 문자열
	 * @Author  Paul Ha
     */
	public static String fillDot(String s)
    {
		String str = "";
		if( s == null ||s.equals("") ) return "";
        try { str = s.substring(0,4)+"."+s.substring(4,6)+"."+s.substring(6,8); } catch(Exception exception) { }
        return str;
    }


    public static String uni2Ksc(String s) {
	   String s1 = null;
       if(s == null)
	       return null;
	   try { s1 = new String(s.getBytes("8859_1"), "KSC5601"); } catch(Exception exception) { }
	   return s1;
    }


    public static String strNoNull(String p) {
        return p == null ? "": p;
    }

	public static String strNoComm(String p) {
        if (p == null)   return "";
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            if (c >= '0' && c <= '9' || c == '+' || c == '-' || c == '.')
                result.append(c);
        }
        return result.toString();
    }
    public static int intNoNull(String p) {
        int result = 0;
        if (p != null) {
        	try {  result = Integer.parseInt(p);  } catch (NumberFormatException e) {  }
   		}
	    return result;
    }

    public static int intNoNull(Object p) {
        int result = 0;
        if (p != null) {
        	try {  result = Integer.parseInt(sNN(p));  } catch (NumberFormatException e) {  }
   		}
	    return result;
    }
	/*
		Acts like split function. Use this if you are using JDK lower version than 1.4
	*/
	public static String[] splits(String s, String delimiter) {
		int arrayIndex = 0;
		for(int i = 0 ; i < s.length() ; i++)
			if((s.substring(i,i+1)).equals(delimiter))
				arrayIndex++;
		String[] rr = new String[arrayIndex+1];
		String temp = "";	int x = 0;
		for(int i = 0 ; i < s.length() ; i++) {
			if(!(s.substring(i,i+1)).equals(delimiter))
				temp += s.substring(i,i+1);
			else { rr[x] = temp; x++; temp = ""; }
		}
		rr[x] = temp;	return rr;
	}

	public static String checkNull(String in){
		if(in == null || in.equals("null")){
			in = "";
		}
		return in;
	}

	public static String checkNull(Object in){
		String out = null;
		if(in == null || in.equals("null")){
			out = "";
		} else {
			out = in.toString();
			in = null;
		}
		return out;
	}

	public static String join(String token, String[] strings) {
		StringBuffer sb = new StringBuffer();

		for(int n = 0; n < (strings.length - 1); n++) {
			sb.append(strings[n]);
			sb.append(token);
		}
		sb.append(strings[strings.length-1]);
		token = null;
		strings = null;

		return sb.toString();
	}

	public static String replace(String src, String oldStr, String newStr){
		if(src == null){
			return null;
		}
		StringBuffer dest = new StringBuffer("");
		int len = oldStr.length();
		int srcLen = src.length();
		int pos=0;
		int oldPos = 0;

		while((pos = src.indexOf(oldStr, oldPos)) >=0 ){
			dest.append(src.substring(oldPos,pos));
			dest.append(newStr);
			oldPos = pos + len;
		}
		if(oldPos<srcLen){
			dest.append(src.substring(oldPos, srcLen));
		}
		src = null;
		oldStr = null;
		newStr = null;

		return dest.toString();
	}

    /**
     * 입력창에 tag처리를 위해 사용하는 변환용 Util.  This method is to be used standalone like.
     */
    public static String convertFromTag(String in){

		String new_str = in;
		if(new_str == null) return new String();
		String[] search = {";", "&", "'", "\"", "<", ">"};
		String[] replace = {"", "&amp;", "&#039;", "&quot;", "&lt;", "&gt;"};
		for( int i=0 ; i<search.length ; i++ ) {
			new_str = new_str.replaceAll(search[i], replace[i]);
		}
		return new_str;
	}


    /**
     * To convert untagged to tag. This method is to be used standalone like.
     */
    public static String convert2Tag(String in){

		String new_str = in;
		if(new_str == null) return new String();
		String[] search = {"&amp;", "&#039;", "&quot;", "&lt;", "&gt;"};
		String[] replace = {"&", "'", "\"", "<", ">"};
		for( int i=0 ; i<search.length ; i++ ) {
			new_str = new_str.replaceAll(search[i], replace[i]);
		}
		return new_str;
	}


    /**
     * Convert html tags in input/textarea fields for CrossSite Check.
     * This method is to be invoked by framework. Do not modify this. by Paul Ha.
     * 37 %, 40 (, 41 ), 43 +
     */
    public static String frmConvertFromTag(String in){

		String new_str = in;
		if(new_str == null) return new String();
		String[] search = {"&", "'", "\"", "<", ">"};
		String[] replace = {"&amp;", "&#039;", "&quot;", "&lt;", "&gt;"};
		for( int i=0 ; i<search.length ; i++ ) {
			new_str = new_str.replaceAll(search[i], replace[i]);
		}
		//System.out.println("frmConvert2Tag ["+in+"]");
		//System.out.println(new_str);
		return new_str;
	}

    /**
     * Convert html tags in ARRAY input/textarea fields for CrossSite Check.
     * This method is to be invoked by framework. Do not modify this. by Paul Ha.
     * 37 %, 40 (, 41 ), 43 +
     */
    public static String[] frmConvertFromArrayTags(String[] ins){

    	if( ins == null ) return null;
    	String new_str = "";
		//String[] search = {"&", "'", "\"", "<", ">", String.valueOf((char)37), String.valueOf((char)40), String.valueOf((char)41), String.valueOf((char)43)};
		String[] search = {"&", "'", "\"", "<", ">"};
		String[] replace = {"&amp;", "&#039;", "&quot;", "&lt;", "&gt;"};
    	for( int i = 0 ; i < ins.length ; i++ ) {
			if(ins[i] == null || ins[i].equals("")) continue;
			for( int j = 0 ; j < search.length ; j++ ) {
				new_str = ins[i].replaceAll(search[j], replace[j]);
			}
			ins[i] = new_str;
    	}
		return ins;
	}


    /**
     * To convert untagged to tag for request parameter.
     * This method is to be invoked by framework. Do not modify this. by Paul Ha.
     * 37 %, 40 (, 41 ), 43 +
     */
    public static String frmConvert2Tag(String in){

		String new_str = in;
		if(new_str == null) return new String();
		String[] search = {"&amp;", "&#039;", "&quot;", "&lt;", "&gt;"};
		String[] replace = {"&", "'", "\"", "<", ">"};
		for( int i=0 ; i<search.length ; i++ ) {
			new_str = new_str.replaceAll(search[i], replace[i]);
		}
		//System.out.println("frmConvert2Tag ["+in+"]");
		//System.out.println(new_str);
		return new_str;
	}



    public static String fixLength(String in, int maxlength, String suf){
		if(in == null){
			return "";
		}
		int len = in.length();
		if(len>maxlength){
			StringBuffer buff = new StringBuffer();
			buff.append(in.substring(0,maxlength)+suf);
			in = null;
			in = buff.toString();
			buff = null;
		}
		suf = null;

		return in;
	}


	public static String getToolTip(String title, String text, String position,
		String titlebg, String textbg, String titlefont, String textfont){

		StringBuffer buff = new StringBuffer();
		if(position.equals("LEFT")) {
			buff.append(" onMouseOver=\"tooltipOnEx2('");
		} else {
			buff.append(" onMouseOver=\"tooltipOnEx('");
		}
		buff.append(title.replaceAll("\"","").replaceAll("\'","").replaceAll("\n","").replaceAll("\r",""))
			.append("','")
			.append(text)
			.append("','"+titlebg+"','"+textbg+"','"+titlefont+"','"+textfont+"');\" onMouseOut=\"tooltipOff(false);\" ");

		title = null;
		text = null;
		position = null;
		titlebg = null;
		textbg = null;
		titlefont = null;
		textfont = null;

		return buff.toString();
	}


   /**
     * 리스트 하단에 페이지 처리를 위한 부분..
     * @param totalCount 총리스트수
     * @param currentPage 현재 페이지
     * @param numPerPage 한페이지에서 보여줄 리스트 수
     * @param pageLength 페이지안에서 보여줄 navigation 수
     * @param BackIMG
     * @param ForwardIMG
     * @param firstIMG
     * @param lastIMG
     * @return String Tag형태의 페이지 처리 부분
     */
	public static String getPageAnchor(int totalCount, int currentPage, int numPerPage, int pageLength,
		String firstIMG, String BackIMG, String ForwardIMG,  String lastIMG, String form){

		StringBuffer buff = new StringBuffer();

		int PageCount   = totalCount / numPerPage;
		if((totalCount % numPerPage) > 0 || totalCount == 0){
			PageCount ++;
		}

		int FromPage = 0;
		int ToPage = 0;
		FromPage = (int)((currentPage-1)/pageLength)*pageLength+1;
		if(PageCount < FromPage + pageLength){
			ToPage = PageCount;
		} else {
			ToPage = FromPage+pageLength-1;
		}

		//이전레벨 페이지 버튼
		if((int)((currentPage-1)/pageLength)>0){
			buff.append("<table border=0><tr><td>");
			buff.append("<A href=\"javascript:selectPage("+form+",'1');\"><img src='"+firstIMG+"' border=0></a></td>");
			buff.append("<td><A href=\"javascript:selectPage("+form+",'"+(FromPage-1)+"');\"><img src='"+BackIMG+"' border=0></a></td>");
		} else {
			buff.append("<table border=0><tr><td><img src='"+firstIMG+"' border=0>");
			buff.append("</td><td><img src='"+BackIMG+"' border=0></a></td>");
		}

		// ArrayList 디자인관련 수정
		//buff.append("<td><font size=2>");
		buff.append("<td class=page>");
		for(int i=FromPage;i<=ToPage;i++){
			if(i==currentPage){
				//buff.append("<b>["+i+"]</b>");
				buff.append("&nbsp;&nbsp;"+i);
			} else {
				//buff.append("<A href=\"javascript:selectPage("+form+",'"+i+"');\">["+i+"]</a>");
				buff.append("&nbsp;&nbsp;<A href=\"javascript:selectPage("+form+",'"+i+"');\">"+i+"</a>");
			}
		}
		//buff.append("</font></td>");
		buff.append("&nbsp;&nbsp;</td>");

		//다음레벨 페이지 버튼
		if(ToPage<PageCount){
			buff.append("<td><A href=\"javascript:selectPage("+form+",'"+(ToPage+1)+"');\"><img src='"+ForwardIMG+"' border=0></a></td>");
			buff.append("<td><A href=\"javascript:selectPage("+form+",'"+PageCount+"');\"><img src='"+lastIMG+"' border=0></a></td></tr></table>");
		} else {
			buff.append("<td><img src='"+ForwardIMG+"' border=0></td>");
			buff.append("<td><img src='"+lastIMG+"' border=0></td></tr></table>");
		}

		firstIMG = null;
		BackIMG = null;
		ForwardIMG = null;
		lastIMG = null;
		form = null;

		return buff.toString();
	}



	/*
	*	Below methods are for converting Language set
	*/

    private static final String EUC_CHARSET = "EUC-KR";
    private static final String KSC_CHARSET = "ksc5601";
    private static final String ASC_CHARSET = "8859_1";

    /**
     * 8859_1 문자셋(영문)을 EUC-KR 문자셋으로 변환한다.
     * @param asc 8859_1 문자셋의 문자열
     * @return    EUC-KR 문자셋의 문자열
     */
    public static String ascToEuc(String asc){
        String euc = null;
        if (asc == null) return null;
        try{
            euc = new String(asc.getBytes(ASC_CHARSET), EUC_CHARSET);
        } catch (Exception e){
            euc = null;
        }
        return euc;
    }

    /**
     * EUC-KR 문자셋(영문)을 8859_1 문자셋으로 변환한다.
     * @param euc 8859_1 문자셋의 문자열
     * @return    EUC-KR 문자셋의 문자열
     */
    public static String eucToAsc(String euc){
        String asc = null;
        if (euc == null) return null;

        try{
            asc = new String(euc.getBytes(EUC_CHARSET), ASC_CHARSET);
        }catch (Exception e){
            asc = null;
        }
        return asc;
    }

    /**
     * EUC-KR 문자셋(영문)을 8859_1 문자셋으로 변환한다.
     * @param euc 8859_1 문자셋의 문자열
     * @return    EUC-KR 문자셋의 문자열
     */
    public static String eucToKsc(String euc){
        String ksc = "";
        if (euc == null) return null;

        try{
            ksc = new String(euc.getBytes(EUC_CHARSET), KSC_CHARSET);
        } catch (Exception e){
            ksc = null;
        }
        return ksc;
    }

    /**
     * KSC5601 문자셋을 8859 문자셋으로 변환한다.
     * @param ksc KSC5601 문자셋의 문자열
     * @return    8859_1 문자셋의 문자열
     */
    public static String kscToAsc(String ksc){
        String asc = null;
        if (ksc == null) return null;

        try{
            asc = new String(ksc.getBytes(KSC_CHARSET), ASC_CHARSET);
        } catch (Exception e){
            asc = null;
        }
        return asc;
    }

    /**
     * 8859_1 문자셋을 KSC5601 문자셋으로 변환한다.
     * @param  asc 8859_1 문자셋의 문자열
     * @return KSC5601 문자셋의 문자열
     */
    public static String ascToKsc(String asc){
        String ksc = null;
        if (asc == null) return null;

        try{
            ksc = new String(asc.getBytes(ASC_CHARSET), KSC_CHARSET);
        } catch (Exception e){
            ksc = null;
        }
        return ksc;
    }

    /**
     * KSC5601 문자셋을 8859_1 문자셋으로 변환한다.
     * @param kscs KSC5601 문자셋의 문자열의 배열
     * @return     8859_1 문자셋의 문자열의 배열
     */
    public static String[] kscToAsc(String[] kscs){
        if (kscs == null) return null;

        String[] ascs = new String[kscs.length];
        for (int i = 0; i < kscs.length; i++){
            ascs[i] = kscToAsc(kscs[i]);
        }
        return ascs;
    }

    /**
     * 8859_1 문자셋을 KSC5601 문자셋으로 변환한다.
     * @param ascs Western 문자셋의 문자열의 배열
     * @return     KSC 문자셋의 문자열의 배열
     */
    public static String[] ascToKsc(String[] ascs){
        if (ascs == null) return null;

        String[] kscs = new String[ascs.length];
        for (int i = 0; i < ascs.length; i++){
            kscs[i] = ascToKsc(ascs[i]);
        }
        return kscs;
    }
    
    public String[] NullCheck(String[] str) {
		int len = 0;
		try {
			len = str.length;
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					if (str[i] == null)
						str[i] = "";
				}

			} else {
				str = new String[1];
				str[0] = "";
			}
		} catch (Exception e) {
			str = new String[1];
			str[0] = "";
			return str;
		}
		return str;
	}
    
    public static String arrayToString(String args[]) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length;) {
            builder.append(args[i]);
            if (++i < args.length) {
                builder.append(",");
            }
        }
        return builder.toString();
    }



} // End of this class.
