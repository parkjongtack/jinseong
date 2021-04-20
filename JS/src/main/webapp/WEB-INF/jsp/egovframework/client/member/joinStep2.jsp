<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<%@ page import="java.util.*,java.text.SimpleDateFormat"%>
<% 
    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
    
    String sSiteCode = "BN547";			// NICE로부터 부여받은 사이트 코드
    String sSitePassword = "aVrmqOfKvVvP";		// NICE로부터 부여받은 사이트 패스워드
    
    String sRequestNumber = "REQ0000000001";        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 
                                                    	// 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
    sRequestNumber = niceCheck.getRequestNO(sSiteCode);
  	session.setAttribute("REQ_SEQ" , sRequestNumber);	// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.
  	
   	String sAuthType = "";      	// 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
   	
   	String popgubun 	= "N";		//Y : 취소버튼 있음 / N : 취소버튼 없음
		String customize 	= "";		//없으면 기본 웹페이지 / Mobile : 모바일페이지
	
		String sGender = ""; 			//없으면 기본 선택 값, 0 : 여자, 1 : 남자 
	
    // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
		//리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
		
		String sReturnUrl = "https://bowlingkorea.com/checkplus/checkplus_success.jsp";      // 성공시 이동될 URL
	    String sErrorUrl = "https://bowlingkorea.com/checkplus/checkplus_fail.jsp";          // 실패시 이동될 URL

    // 입력될 plain 데이타를 만든다.
    String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                        "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                        "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize + 
						"6:GENDER" + sGender.getBytes().length + ":" + sGender;
    
    String sMessage = "";
    String sEncData = "";
    
    int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
    if( iReturn == 0 ) {
        sEncData = niceCheck.getCipherData();
    } else if( iReturn == -1) {
        sMessage = "암호화 시스템 에러입니다.";
    } else if( iReturn == -2) {
        sMessage = "암호화 처리오류입니다.";
    } else if( iReturn == -3) {
        sMessage = "암호화 데이터 오류입니다.";
    } else if( iReturn == -9) {
        sMessage = "입력 데이터 오류입니다.";
    } else {
        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
    }
    
%>

<!-- <style type="text/css">
@media only screen and (max-width:460px){
#checkFrame{ width: 95%; height: 75%; position: absolute; z-index: 10; display: none; }
#checkFrame p{ text-align: right; font-size: 18px; background: white; }
}
@media only screen and (min-width:460px){
#checkFrame{ width: 450px; height: 580px; position: absolute; z-index: 10; display: none; }
#checkFrame p{ text-align: right; font-size: 18px; background: white; }
}

</style> -->



<style type="text/css">
#checkFrame { position: absolute; z-index: 15; display: none; left:50%; top:10%; margin-left:-199px; overflow:hidden;  width: 398px; height: 600px; padding:0 !important; box-sizing:border-box; border:1px solid #666;}
#checkFrame p { text-align: right; background:#000 !important;   }
#checkFrame p a { font-size: 13px; color:#fff; line-height:30px; height:30px; width:100%; padding-right:10px; }
#checkFrame iframe { border:none; width:100%;   }
	
@media only screen and (max-width:640px){
#checkFrame{ width: 96%; left:2%; margin-left:0; height: 70%;  }
}
</style>



<script type="text/javascript">
window.name ="Parent_window";

function fnPopup(){
	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
	document.form_chk.target = "popupChk";
	document.form_chk.submit();
}

/* 
function fnPopup(){
	//window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
	//document.form_chk.target = "popupChk";
	document.form_chk.target = "checkIfm";
	document.form_chk.submit();
	$("#checkFrame").css("display","block");
}
 */
function goNext() {
	
	var f = document.cform;

	checkClose();
	if($("#auth").val() == "true") { 
		f.action = "/membership/joinStep3.do";
		f.submit();
	} else {
		alert("본인 인증에 실패하였습니다.");
	}

}	

 function checkClose(){
	//$("iframe[name=checkIfm]").attr("src","");
	$("#checkFrame").css("display","none");
}

</script>
<form name="form_chk" method="post" >
	<input type="hidden" name="m" value="checkplusSerivce" />						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
	<input type="hidden" name="EncodeData" value="<%= sEncData %>">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
</form>
<form name="cform" method="post">
	<input type="hidden" name="mber_name" id="mber_name" value="" />
	<input type="hidden" name="reg_sms" id="reg_sms" value="Y" />	
	<input type="hidden" name="reg_code" id="reg_code" value="" />
	<input type="hidden" name="birth_year" id="birth_year" value="" />
	<input type="hidden" name="birth_month" id="birth_month" value="" />
	<input type="hidden" name="birth_day" id="birth_day" value="" />
	<input type="hidden" name="mber_gender" id="mber_gender" value="" />
	<input type="hidden" name="auth" id="auth" value="" />
	<input type="hidden" name="agree_yn" value="<c:out value='${agree_yn}' />" />
</form>
	
<div id="container" class="subpage">
	<div id="contents" class="s_con">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />

		<div class="sub_content">
			<div class="inner">
				<div class="sub_head">
					<h3 class="c_tit">회원가입</h3>
				</div>

				<div id="member">
					<ul class="joinStep">
						<!--on으로 제어-->
						<li class="">1step <strong>약관동의</strong><span class="step1"></span></li>
						<li class="on">2step <strong>실명인증</strong><span class="step2"></span></li>
						<li class="">3step <strong>회원정보입력</strong><span class="step3"></span></li>
						<li class="">4step <strong>회원가입완료</strong><span class="step4"></span></li>
					</ul>
					<!--//joinStep //E-->

					<h4 class="h4_tit">실명인증</h4>
					<div class="basicBox">
						<div id="checkFrame">
							<p><a href="javascript:void(0)" onclick="checkClose()">X</a></p>
							<!-- <iframe name="checkIfm" src="https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb" style="width: 100%; height: 100%;"></iframe> -->
						</div>
								
						<div class="step2_wrap">
							<div class="member2">
								<p><strong>안전한 회원가입</strong>을 위한 본인인증</p>
								<p>본인 명의의 휴대전화로 인증번호를 전송 받아 가입할 수 있습니다.</p>
								<div class="membtn"><a href="javascript:void(0);" onclick="fnPopup();">휴대폰 본인 인증</a></div>
							</div>
							<div class="txtBx">
								<dl>
									<dt>진승은 회원님의 소중한 개인정보를 안전하게 관리하고 있습니다.</dt>
									<dd>2012년 8월 18일 부터 시행되는 (정보통신망 이용 촉진 및 정보보호 등에 관한 법률) 제 23조의 2"주민등록번호의 사용 제한"에 따라 진승 내 모든 서비스에서 주민등록번호를 입력 받지 않습니다. 이에 따라 진승 회원으로 가입하실 때는 주민등록번호를 입력하는 대신 휴대폰을 이용하여 본인인증을 하셔야 합니다.</dd>
								</dl>
								<dl>
									<dt>본인 인증 전 반드시 읽어 보세요.</dt>
									<dd>
										<ul>
											<li><!-- 본 실명인증 서비스는 한국모바일인증(주)에서 제공하고 있으며, 실증인증관련 문의사항은 1644-1552로 문의 주시기 바랍니다.--></li>
											<li>- 본인인증시 제공되는 정보는 해당 인증기관에서 직접 수집하며, 인증 이외 저장 또는 이용하지 않습니다.</li>
											<li>- 본인 소유의 휴대폰번호가 아닐 경우 실명인증 및 회원가입에 제한을 받을 수 있으니, 유의 하시기 바랍니다.</li>
											<li>- 실명인증 시 발생하는 비용은 진승에서 부담 합니다.</li>
										</ul>
									</dd>
								</dl>
							</div>
						</div>
						<!-- //agree -->
					</div>
					<!-- //basicBox -->
				</div>
				<!-- //member -->
				
			
			</div>
			<!--inner-->
		</div>
	</div>
	<!-- #CONTENTS //E -->
</div>
<!-- #CONTAINER //E -->
<c:if test="${!empty msg }">
	<script type="text/javascript">
		var msg = '${msg}';
		if (msg != '') {
			alert(msg);
			location.href = '/membership/joinStep1.do';
		}
	</script>
</c:if>
<jsp:include page="/client/footer.do"></jsp:include>