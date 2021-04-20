<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<%	//���� �� ������� null�� ������ �κ��� ��������ڿ��� ���� �ٶ��ϴ�.
    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

    String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");

    String sSiteCode = "BN547";				// NICE�κ��� �ο����� ����Ʈ �ڵ�
    String sSitePassword = "aVrmqOfKvVvP";			// NICE�κ��� �ο����� ����Ʈ �н�����

    String sCipherTime = "";			// ��ȣȭ�� �ð�
    String sRequestNumber = "";			// ��û ��ȣ
    String sResponseNumber = "";		// ���� ������ȣ
    String sAuthType = "";				// ���� ����
    String sName = "";					// ����
    String sDupInfo = "";				// �ߺ����� Ȯ�ΰ� (DI_64 byte)
    String sConnInfo = "";				// �������� Ȯ�ΰ� (CI_88 byte)
    String sBirthDate = "";				// �������(YYYYMMDD)
    String sGender = "";				// ����
    String sNationalInfo = "";			// ��/�ܱ������� (���߰��̵� ����)
	String sMobileNo = "";				// �޴�����ȣ
	String sMobileCo = "";				// ��Ż�
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
        
        // ����Ÿ�� �����մϴ�.
        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
        
        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
        sResponseNumber = (String)mapresult.get("RES_SEQ");
        sAuthType		= (String)mapresult.get("AUTH_TYPE");
        sName			= (String)mapresult.get("NAME");
		//sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 ���� �ּ� ���� �� ���
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
            sMessage = "���ǰ��� �ٸ��ϴ�. �ùٸ� ��η� �����Ͻñ� �ٶ��ϴ�.";
            sResponseNumber = "";
            sAuthType = "";
        }
    } else if( iReturn == -1) {
        sMessage = "��ȣȭ �ý��� �����Դϴ�.";
    } else if( iReturn == -4) {
        sMessage = "��ȣȭ ó�������Դϴ�.";
    } else if( iReturn == -5) {
        sMessage = "��ȣȭ �ؽ� �����Դϴ�.";
    } else if( iReturn == -6) {
        sMessage = "��ȣȭ ������ �����Դϴ�.";
    } else if( iReturn == -9) {
        sMessage = "�Է� ������ �����Դϴ�.";
    }    
    else if( iReturn == -12) {
        sMessage = "����Ʈ �н����� �����Դϴ�.";
    } else {
        sMessage = "�˼� ���� ���� �Դϴ�. iReturn : " + iReturn;
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
    <title>NICE������ - CheckPlus �Ƚɺ������� ���</title>
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