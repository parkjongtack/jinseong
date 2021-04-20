<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<%	//인증 후 결과값이 null로 나오는 부분은 관리담당자에게 문의 바랍니다.
    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

    String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");

    String sSiteCode = "BN547";				// NICE로부터 부여받은 사이트 코드
    String sSitePassword = "aVrmqOfKvVvP";			// NICE로부터 부여받은 사이트 패스워드

    String sCipherTime = "";			// 복호화한 시간
    String sRequestNumber = "";			// 요청 번호
    String sResponseNumber = "";		// 인증 고유번호
    String sAuthType = "";				// 인증 수단
    String sName = "";					// 성명
    String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
    String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
    String sBirthDate = "";				// 생년월일(YYYYMMDD)
    String sGender = "";				// 성별
    String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
	String sMobileNo = "";				// 휴대폰번호
	String sMobileCo = "";				// 통신사
    String sMessage = "";
    String sPlainData = "";
    String birth_year = "";
    String birth_month = "";
    String birth_day = "";    
    
    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

    if( iReturn == 0 )
    {
        sPlainData = niceCheck.getPlainData();
        sCipherTime = niceCheck.getCipherDateTime();
        
        // 데이타를 추출합니다.
        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
        
        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
        sResponseNumber = (String)mapresult.get("RES_SEQ");
        sAuthType		= (String)mapresult.get("AUTH_TYPE");
        sName			= (String)mapresult.get("NAME");
		//sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
        sBirthDate		= (String)mapresult.get("BIRTHDATE");
        birth_year 	= sBirthDate.substring(0, 4);
        birth_month = sBirthDate.substring(4, 6);		
        birth_day = sBirthDate.substring(6, 8);		
        sGender			= (String)mapresult.get("GENDER");
        if("1".equals(sGender)) {
        	sGender = "M";
        } else {
        	sGender = "w";
        }
        sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
        sDupInfo		= (String)mapresult.get("DI");
        sConnInfo		= (String)mapresult.get("CI");
        sMobileNo		= (String)mapresult.get("MOBILE_NO");
        sMobileCo		= (String)mapresult.get("MOBILE_CO");
        
        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
        if(!sRequestNumber.equals(session_sRequestNumber))
        {
            sMessage = "세션값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
            sResponseNumber = "";
            sAuthType = "";
        }
    } else if( iReturn == -1) {
        sMessage = "복호화 시스템 에러입니다.";
    } else if( iReturn == -4) {
        sMessage = "복호화 처리오류입니다.";
    } else if( iReturn == -5) {
        sMessage = "복호화 해쉬 오류입니다.";
    } else if( iReturn == -6) {
        sMessage = "복호화 데이터 오류입니다.";
    } else if( iReturn == -9) {
        sMessage = "입력 데이터 오류입니다.";
    }    
    else if( iReturn == -12) {
        sMessage = "사이트 패스워드 오류입니다.";
    } else {
        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
    }

%>
<%!

	public String requestReplace (String paramValue, String gubun) {

        String result = "";
        
        if (paramValue != null) {
        	
        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");
        	
        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
            paramValue = paramValue.replaceAll("=", "");
        	}
        	
        	result = paramValue;
            
        }
        return result;
  }
%>

<html>
<head>
    <title>NICE평가정보 - CheckPlus 안심본인인증 결과</title>
</head>
<body>
   <%
	if(iReturn == 0){
%>
	<script type="text/javascript">
			
		parent.document.cform.mber_name.value = "<%= sName %>";
		parent.document.cform.reg_code.value = "<%= sDupInfo %>";
		parent.document.cform.birth_year.value = "<%= birth_year %>";
		parent.document.cform.birth_month.value = "<%= birth_month %>";
		parent.document.cform.birth_day.value = "<%= birth_day %>";
		parent.document.cform.mber_gender.value = "<%= sGender %>";
		parent.document.cform.auth.value = "true";
		parent.go_diNext();
		
		self.close();
		
	</script>
<%}else{ %>
	<script type="text/javascript">
		parent.document.cform.auth.value = "false";
		parent.document.cform.iReturn.value = "<%= iReturn %>";
		parent.go_diNext();
		self.close();
	</script>
<%} %>
</body>
</html>