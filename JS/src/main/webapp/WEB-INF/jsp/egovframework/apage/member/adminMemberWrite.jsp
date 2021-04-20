<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
var checkResult=0;
var pwchk2 = false;
$(document).ready(function () {
	//패스워드 유효성 검사
	$('#mber_pw').keyup(function(){
		var pw_length =  $('#mber_pw').val();
		
	    if(pw_length.length<6  || pw_length.length>10){
			$('#pwValidate1').text('');
			$('#pwValidate1').html("<font color=#FF0000>영 대소문자.숫자,특수문자를 포함하여 6~10자 이내로 다시 입력하세요.</font>");			
			$("#pwCheck").val("N");
		}	
	    else if(!pw_length.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/)){
			$('#pwValidate1').text('');
			$('#pwValidate1').html("<font color=#FF0000>영 대소문자.숫자,특수문자를 포함하여 6~10자 이내로 다시 입력하세요.</font>");
			$("#pwCheck").val("N");
		}
		else{
			$('#pwValidate1').html('');
			$('#pwValidate1').html("<font color=#0054FF>사용 가능합니다.</font>");
			$("#pwCheck").val("Y");
		}
	});

	$('#mber_pw_chk').keyup(function(){
		var pw_length =  $('#mber_pw').val();
		var pw_chk_length =  $('#mber_pw_chk').val();
		
		if(pw_length != pw_chk_length){
			$('#pwValidate2').text('');
			$('#pwValidate2').html("<font color=#FF0000>비밀번호와 일치하지 않습니다.</font>");
			$("#pwCheckPre").val("N");
		}
		else{
			$('#pwValidate2').html('');
			$('#pwValidate2').html("<font color=#0054FF>비밀번호와 일치합니다.</font>");
			$("#pwCheckPre").val("Y");
		}
	});	
	
	
	$("#emailSelect").change(function(){
		var strVal = $(this).val();
		if(strVal == ""){
			$("#mber_email2").val('');
			$("#mber_email2").removeAttr("readonly");
			$("#mber_email2").focus();
		}else{
			$("#mber_email2").val('');
			$("#mber_email2").val(strVal);
			$("#mber_email2").attr("readonly","readonly");
		}
	});
	
	
	$('#mber_id').keyup(function(){
		checkResult = 0;
		$('#idchk').html("<span style=\"color:red\">√ 아이디 중복확인을 해주세요.</span>");
	});

	$('#mber_id').keydown(function(){
		checkResult = 0;
		$('#idchk').html("<span style=\"color:red\">√ 아이디 중복확인을 해주세요.</span>");
	});	
	
	
});


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/member/adminMemberList.do'/>");
	$("#frm").submit();
}

function goAdminIdCheck(){
	if(($("#mber_id").val())==""){
		alert("아이디를 입력하세요.");
		$("#mber_id").focus();
		return;
	}else{
		/* 
		var pattern = /^[a-z]{1}[A-Za-z0-9]{5,10}$/;
		if(!$("#mber_id").val().match(pattern)){
			$('#idchk').html("<span style=\"color:red\">√ 영소문자로 시작하고 영어.숫자 포함하여 6~10자 이내로 입력하세요.</span>");
			return;
		}
		 */
		
		

		var mber_id = $("#mber_id").val();
	
		//아이디에 공백 사용하지 않기
		if (mber_id.indexOf(" ") >= 0) {
			alert("아이디에 공백을 사용할 수 없습니다.");
			$("#mber_id").focus();
			return;
		}
		//아이디 길이 검사
		if (mber_id.length<6 || mber_id.length>20) {
			$("#mber_id").focus();
			$('#idchk').html("<span style=\"color:red\">√ 아이디는 영문자, 숫자조합으로 6~20자리로 입력해주세요.</span>");
			return;
		}
		//아이디 유효성 검사 (영문소문자, 숫자만 허용)
		for (i = 0; i < mber_id.length; i++) {
			ch = mber_id.charAt(i);
			if (!(ch >= '0' && ch <= '9') && !(ch >= 'a' && ch <= 'z')) {
				$('#idchk').html("<span style=\"color:red\">√ 아이디는 소문자, 숫자만 입력가능합니다.</span>");
				$("#mber_id").focus();
				return;
			}
		}
		
		
		
	}
	
	$.ajax({
		url : "<c:url value='/apage/member/adminMemberIdChk.do'/>",
		data		: {mber_id : $("#mber_id").val()},
		async		: true,
		type		: "post",
		dataType : "json",
		beforeSend: function (xhr) {
			xhr.setRequestHeader('AJAX', true);
		}				
		,success	: function(data){
			if (data.root.ResultCode == "Y") {
				$('#idchk').html("<span style=\"color:red\">√ 이미 사용중인 아이디입니다.</span>");
				checkResult=0;			
			} else {
				$('#idchk').html("<span style=\"color:blue\">√ 사용가능 한 아이디입니다.</span>");
				checkResult=1;
			}
		},
		error : function(data, status, err) {
			alert("에러입니다." + data +"<><><>"+ status+"<><><>"+ err);
			return;
		}
	})
	
	return;
}



//비밀번호 유효성 검사
function pwValidate(){
	$("#mber_pw").val($.trim($("#mber_pw").val()));
	$("#pwCheck").val("N");
	$("#pwCheckPre").val("N");
	var pwReg =  /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,12}$/;
	if( !pwReg.test($.trim($("#mber_pw").val()))){
		$("#pwValidate1").css("color","#ff1700");
		$("#pwValidate1").html("영 대소문자.숫자,특수문자를 포함하여 6~16자 이내로 입력하세요.");
	}else{
		$("#pwCheckPre").val("Y");
		$("#pwValidate1").css("color","blue");
		$("#pwValidate1").html("사용가능한 비밀번호입니다.");
		$("#mber_pw_chk").val($.trim($("#mber_pw_chk").val()));
		if($("#mber_pw_chk").val() == $("#mber_pw").val()){
			$("#pwValidate2").css("color","blue");
			$("#pwValidate2").html("위의 비밀번호와 일치합니다.");
			$("#pwCheck").val("Y");
		}else{
			$("#pwValidate2").css("color","#ff1700");
			$("#pwValidate2").html("위의 비밀번호와 동일하게 입력하세요.");
		}
	}
}

//주소 팝업
function addr_pop() {
	// 주소검색을 수행할 팝업 페이지를 호출
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출
	var pop = window.open("${contextPath}/address/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}

//팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
	$("#zipcode").val(zipNo);
	$("#addr").val(roadAddrPart1);
	$("#addr_detail").val(addrDetail);	
}


function goBoardWrite(){
	if(checkResult != 1){
		alert("아이디 중복확인을 해주세요.");
		return;
	}
	
	if($("#pwCheckPre").val() != "Y" || $("#pwCheck").val() != "Y"){
		if($("#pwCheck").val() == "N"){
			alert("비밀번호를 양식에 맞게 입력해주세요.");
			$("#mber_pw").focus();
			return;
		}else if($("#pwCheckPre").val() == "N"){
			alert("입력하신 비밀번호와 동일하게 입력해주세요.");
			$("#mber_pw_chk").focus();
			return;
		}
	}
	
	if($("#mber_name").val() == ""){
		alert("이름을 입력해주세요.");
		$("#mber_name").focus();
		return;
	}
	
	var mber_birth = "";
	var year, month, day;
	if($("#birthYear").val() == ""){
		alert("생년을 선택하세요.");
		$("#birthYear").focus();
		return;
	}else{
		year = $("#birthYear").val();
	}
	
	if($("#birthMonth").val() == ""){
		alert("생년을 선택하세요.");
		$("#birthMonth").focus();
		return;
	}else{
		month = $("#birthMonth").val();
		if(month < 10){
			month = "0"+month;
		}
	}
	
	if($("#birthDay").val() == ""){
		alert("생년을 선택하세요.");
		$("#birthDay").focus();
		return;
	}else{
		day = $("#birthDay").val();
		if(day < 10){
			day = "0"+day;
		}
	}
	
	mber_birth = year+"-"+month+"-"+day;
	$("#mber_birth").val(mber_birth);
	
	
	var mber_tel = "";
	if($("#tel1").val() == ""){
		alert("연락처를 입력하세요.");
		$("#tel1").focus();
		return;
	}
	if($("#tel2").val() == ""){
		alert("연락처를 입력하세요.");
		$("#tel2").focus();
		return;
	}
	if($("#tel3").val() == ""){
		alert("연락처를 입력하세요.");
		$("#tel3").focus();
		return;
	}
	mber_tel = $("#tel1").val() + "-" +$("#tel2").val() + "-" + $("#tel3").val();
	$("#mber_tel").val(mber_tel);
	
	if($("#zipcode").val() == ""){
	 	alert("우편번호를 검색해주세요.");
	 	return;
	}
	
	if($("#sms").is(":checked")){
		$("#sms_yn").val("Y");
	}else{
		$("#sms_yn").val("N");
	}
	
	var mber_email = "";
	if($("#mber_email1").val() == ""){
		alert("이메일를 입력하세요.");
		$("#mber_email1").focus();
		return;
	}
	if($("#mber_email2").val() == ""){
		alert("이메일를 입력하세요.");
		$("#mber_email2").focus();
		return;
	}
	mber_email = $("#mber_email1").val()+"@"+$("#mber_email2").val();

	var email_regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	if (!mber_email.match(email_regExp)) {
		alert("이메일 형식이 올바르지 않습니다.");
		return;
	}
	  
	$("#mber_email").val(mber_email);
	
	if($("#mailing").is(":checked")){
		$("#mailing_yn").val("Y");
	}else{
		$("#mailing_yn").val("N");
	}
	
	
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/member/adminMemberReg.do'/>");
	$("#frm").submit();
}


</script>

<form:form commandName="vo" name="frm" id="frm" method="post" >
	<input type="hidden" name="scType" id="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	
	<input type="hidden" id="pwCheckPre" value="N">
	<input type="hidden" id="pwCheck" value="N">
	<input type="hidden" id="mber_type" name="mber_type" value="M">
	
	<input type="hidden" name="reg_sms" value="N">
	<input type="hidden" name="reg_code" value="">
	<div id="container">
		<h3>회원관리</h3>
		<div class="contents">
			<!-- //ct-tab -->
        
        	<div class="bbs-write">
				<table>
					<colgroup>
						<col width="15%">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th><span style="color:red;">*</span>아이디</th>
							<td class="al_l">
								<form:input path="mber_id" cssClass="w40p" maxlength="20" onKeyPress="if (event.keyCode==13){ goAdminIdCheck(); return false;}" title="아이디 입력" />
								<a href="javascript:goAdminIdCheck()" class="s-blue-btn">중복확인</a>
								<span id="idchk"></span>
							</td>
						</tr>
						<tr>
							<th><span style="color:red;">*</span>비밀번호</th>
							<td class="al_l">
								<input type="password" id="mber_pw" name="mber_pw" class="type-password required" title="비밀번호 입력란"/>
	                            <span id="pwValidate1">영 대소문자.숫자,특수문자를 포함하여 6~10자 이내로 입력하세요.</span>							
							</td>
						</tr>
						<tr>
							<th><span style="color:red;">*</span>비밀번호 확인</th>
							<td class="al_l">
								<input type="password" id="mber_pw_chk" name="mber_pw_chk" class="type-password required" title="비밀번호 확인 입력란"/>
	                            <span id="pwValidate2">비밀번호를 다시 한번 입력해 주세요.</span>							
							</td>
						</tr>
							<tr>
								<th><span style="color:red;">*</span>이름</th>
								<td class="al_l">
									<form:input path="mber_name" cssClass="w40p" maxlength="50" title="이름 입력" />
								</td>
							</tr>
						<tr>
							<th><span style="color:red;">*</span>생년월일</th>
							<td class="al_l">
								<select id="birthYear" title="생년">
	                            	<option value="" >::::선택::::</option>
	                            <%
										for(int i = 2018; i >= 1900; i--){
											
								%>
											<c:set var="year" value="<%=i %>" />
											<option value="<%=i%>" <c:if test="${fn:substring(memberVo.mber_birth,0,4) eq year }">selected</c:if>><%=i%></option>
								<%	
										}
								%>
				                 </select> 년
	                             <select id="birthMonth" title="월">
	                                  <option value="" >::::선택::::</option>
	                                  <%
										for(int i = 12; i >= 1; i--){
								%>
											<c:set var="month" value="<%=i %>" />															
											<option value="<%=i%>" <c:if test="${fn:substring(memberVo.mber_birth,5,7) eq month }">selected</c:if>><%=i%></option>
								<%	
										}
								%>
	                              </select> 월
	                              <select id="birthDay" title="일">
	                                  <option value="">::::선택::::</option>
	                                  <%
										for(int i = 31; i >= 1; i--){
								%>
											<c:set var="day" value="<%=i %>" />
											<option value="<%=i%>" <c:if test="${fn:substring(memberVo.mber_birth,8,10) eq day }">selected</c:if>><%=i%></option>
								<%	
										}
								%>
	                              </select> 일										
	                              <input type="hidden" id="mber_birth" name="mber_birth" value="" /> 						
							</td>
						</tr>
						<tr>
							<th><span style="color:red;">*</span>성별</th>
							<td class="al_l">
								<label><input type="radio" name="mber_gender" id="mber_gender1" value="M" class="type-radio required" title="성별 남 선택" checked="checked"/> 남</label>
	                            <label><input type="radio" name="mber_gender" id="mber_gender2" value="F" class="type-radio required" title="성별 여 선택"/> 여</label>						
							</td>
						</tr>
						<tr>
							<th><span style="color:red;">*</span>연락처</th>
							<td class="al_l">
								<input type="text" id="tel1" name="tel1" class="w10p" title="전화번호 입력란1" value="${fn:substring(memberVo.mber_tel,0,3) }" maxlength="3"/> - 
		                        <input type="text" id="tel2" name="tel2" class="w10p" title="전화번호 입력란2" value="${fn:substring(memberVo.mber_tel,4,8) }" maxlength="4"/> - 
		                        <input type="text" id="tel3" name="tel3" class="w10p" title="전화번호 입력란3" value="${fn:substring(memberVo.mber_tel,9,14) }" maxlength="4"/>
		                        <input type="hidden" id="mber_tel" name="mber_tel" value=""/>
		                        <span class="ml15">
		                        	<input type="checkbox" id="sms" class="type-check required w30" <c:if test="${memberVo.sms_yn eq 'Y' }">checked</c:if>/>SMS수신설정
		                        	<input type="hidden" id="sms_yn" name="sms_yn" value="" />
		                        </span>
							</td>
						</tr>
						<tr>
	                        <th scope="col" rowspan="2"><span style="color:red;">*</span><label for="zip1">주소</label></th>
	                        <td class="al_l">
	                            <input type="text" id="zipcode" name="zipcode" class="w10p" title="우편번호 " value="${memberVo.zipcode }" readonly /> 
	                            <a href="javascript:addr_pop();" id="addrSearchBtn" class="s-blue-btn">우편번호 검색</a>
	                        </td>
	                    </tr>
	                     <tr>
	                        <td class="al_l">
	                        	<input type="text" id="addr" name="addr" class="w25p" title="상세주소" value="${memberVo.addr }" />
	                        	<input type="text" id="addr_detail" name="addr_detail" class="w40p" title="상세주소" value="${memberVo.addr_detail }" />
	                        </td>
	                    </tr>
						<tr>
							<th><span style="color:red;">*</span>이메일</th>
							<td class="al_l">
	                            <input type="text" id="mber_email1" name="mber_email1" class="w10p" title="이메일1" value="${fn:substring(memberVo.mber_email,0,fn:indexOf(memberVo.mber_email,'@')) }" /> @ 
	                            <input type="text" id="mber_email2" name="mber_email2" class="w10p" title="이메일2" value="${fn:substring(memberVo.mber_email,fn:indexOf(memberVo.mber_email,'@')+1,fn:length(memberVo.mber_email)) }"/>
	                            <select id ="emailSelect">
	                            	<option value="">::::선택::::</option>
	                                <option value="naver.com">naver.com</option>
	                                <option value="nate.com">nate.com</option>
	                                <option value="gmail.com">gmail.com</option>
	                                <option value="korea.com">korea.com</option>
	                                <option value="paran.com">paran.com</option>
	                                <option value="hanmail.net">hanmail.net</option>
	                                <option value="hotmail.com">hotmail.com</option>
	                             </select>
	                             <input type="hidden" id="mber_email" name="mber_email" class="type-text required w163" title="이메일 입력란" />
	                         	 <input type="checkbox" id="mailing" class="type-check required" <c:if test="${memberVo.mailing_yn eq 'Y' }">checked</c:if> />뉴스레터 및 안내메일 수신 설정
	                         	 <input type="hidden" id="mailing_yn" name="mailing_yn" value="" />
							</td>
						</tr>					
						<!-- 
						<tr>
							<th><span style="color:red;">*</span>이메일 수신 여부</th>
							<td class="al_l">
								<label><input type="radio" name="mailing_yn" value="Y" class="type-radio required" title="이메일 수신 여부 선택" checked="checked"/>수신동의</label>
	                            <label><input type="radio" name="mailing_yn" value="N" class="type-radio required" title="이메일 수신 여부 선택"/>수신거부</label>						
							</td>
						</tr>
						<tr>
							<th><span style="color:red;">*</span>SMS 수신 여부</th>
							<td class="al_l">
								<label><input type="radio" name="sms_yn"  value="Y" class="type-radio required" title="SMS 수신 여부 선택" checked="checked"/>수신동의</label>
	                            <label><input type="radio" name="sms_yn"  value="N" class="type-radio required" title="SMS 수신 여부 선택"/>수신거부</label>						
							</td>
						</tr>
						 -->
						<tr>
							<th>상태</th>
							<td class="al_l">
								<select id="mber_status" name="mber_status">
									<option value="N" <c:if test="${memberVo.mber_status eq 'N' }">selected</c:if>>활동</option>
									<option value="Y" <c:if test="${memberVo.mber_status eq 'Y' }">selected</c:if>>탈퇴</option>
								</select>						
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<!-- //bbs-write -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardWrite()" class="btn-ty1 black">등록</a>
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
				</p>
			</div>
		</div>
		<!-- //contents -->
	</div>
</form:form>
		
<jsp:include page="/apage/inc/footer.do"></jsp:include>