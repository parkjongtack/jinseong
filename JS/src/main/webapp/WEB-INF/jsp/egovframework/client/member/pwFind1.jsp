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
	NiceID.Check.CPClient niceCheck = new NiceID.Check.CPClient();

	String sSiteCode = "BN547"; // NICE로부터 부여받은 사이트 코드
	String sSitePassword = "aVrmqOfKvVvP"; // NICE로부터 부여받은 사이트 패스워드

	String sRequestNumber = "REQ0000000001"; // 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 
												// 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
	sRequestNumber = niceCheck.getRequestNO(sSiteCode);
	session.setAttribute("REQ_SEQ", sRequestNumber); // 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.

	String sAuthType = ""; // 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서

	String popgubun = "N"; //Y : 취소버튼 있음 / N : 취소버튼 없음
	String customize = ""; //없으면 기본 웹페이지 / Mobile : 모바일페이지

	String sGender = ""; //없으면 기본 선택 값, 0 : 여자, 1 : 남자 

	// CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
	//리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~

	String sReturnUrl = "https://bowlingkorea.com/checkplus/pwFind1_success.jsp"; // 성공시 이동될 URL
	String sErrorUrl = "https://bowlingkorea.com/checkplus/pwFind1_fail.jsp"; // 실패시 이동될 URL

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
	if (iReturn == 0) {
		sEncData = niceCheck.getCipherData();
	} else if (iReturn == -1) {
		sMessage = "암호화 시스템 에러입니다.";
	} else if (iReturn == -2) {
		sMessage = "암호화 처리오류입니다.";
	} else if (iReturn == -3) {
		sMessage = "암호화 데이터 오류입니다.";
	} else if (iReturn == -9) {
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
window.name = "Parent_window";
function fnPopup(){
	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
	document.form_chk.target = "popupChk";
	document.form_chk.submit();
}

/* 
function fnPopup() {
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
	if ($("#auth").val() == "true") {		
		f.action = "/membership/pwFind2.do";
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

<form name="form_chk" method="post">
	<input type="hidden" name="m" value="checkplusSerivce" />
	<!-- 필수 데이타로, 누락하시면 안됩니다. -->
	<input type="hidden" name="EncodeData" value="<%=sEncData%>">
	<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
</form>

<form name="cform" method="post">
	<input type="hidden" name="mber_name" id="mber_name" value="" />
	<input type="hidden" name="mber_id" id="mber_id" value="${mi}"> 
	<input type="hidden" name="reg_sms" id="reg_sms" value="Y" /> 
	<input type="hidden" name="reg_code" id="reg_code" value="" /> 
	<input type="hidden" name="birth_year" id="birth_year" value="" /> 
	<input type="hidden" name="birth_month" id="birth_month" value="" /> 
	<input type="hidden" name="birth_day" id="birth_day" value="" /> 
	<input type="hidden" name="mber_gender" id="mber_gender" value="" /> 
	<input type="hidden" name="auth" id="auth" value="" /> 
	<input type="hidden" name="agree_yn" value="<c:out value='${agree_yn}' />" 
	/>
</form>

<!--//LOC_WRAP //E-->

<div id="container" class="subpage">
	<div id="contents">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />
		<div class="sub_content">
			<div class="inner">
				<div id="checkFrame">
					<p><a href="javascript:void(0)" onclick="checkClose()">X</a></p>
						<!-- <iframe name="checkIfm" src="https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb" style="width: 100%; height: 100%;"></iframe> -->
				</div>
				
				<div class="sub_head">
					<h3 class="c_tit">기존 가입자 인증</h3>
				</div>

				<div id="member">
					<div class="icoBx ic2">
						<p>본인 명의 휴대전화번호를 입력 하십시오.</p>
						<p>정보를 찾을 수 없을때는 사이트 관리자에게 연락 하십시오.</p>
					</div>
					
					<div class="find_id_wrap">
						<div class="findBx">
							<div class="fl f_phone" style="width: 100%;">
								<p class="tit">기존가입자는 휴대폰 인증이 필요합니다.</p>
									
									<div class="find_txt">
										<div>
											<p>가입자 휴대폰 인증이 필요합니다.</p>
											<a href="javascript:void(0);" onclick="fnPopup();">휴대폰 인증</a>
										</div>
									</div>
							</div>
							
							<!-- 휴대폰 본인 인증-->
							
						</div>

					</div>
					<!-- //FIND_ID_WRAP [E] -->

				</div>
				<!-- //member -->

			</div>
			<!--inner-->
		</div>
	</div>
	<!-- #CONTENTS //E -->
</div>
<!-- #CONTAINER //E -->

<jsp:include page="/client/footer.do"></jsp:include>