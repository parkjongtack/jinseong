<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">

$(document).ready(function () {
	
	//패스워드 유효성 검사
	$('#mber_pw').keyup(function(){
		var pw_length =  $('#mber_pw').val();
		
	    if(pw_length.length<6  || pw_length.length>20){
			$('#pwValidate1').text('');
			$('#pwValidate1').html("<font color=#FF0000>사용 불가능합니다.</font>");			
			$("#pwCheck").val("N");
		}	
	    else if(!pw_length.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/)){
			$('#pwValidate1').text('');
			$('#pwValidate1').html("<font color=#FF0000>사용 불가능합니다.</font>");
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
	
	$('#mber_pw').change(function() {
		var sp = document.getElementById("pwValidate2");
		sp.innerHTML = "<font color=#FF0000>비밀번호 중복확인을 해주세요.</font>";
		$("#pwCheckPre").val('N');
	});
	
});



//비밀번호 유효성 검사
function pwValidate(){
	$("#mber_pw").val($.trim($("#mber_pw").val()));
	$("#pwCheck").val("N");
	$("#pwCheckPre").val("N");
	var pwReg =  /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,12}$/;
	if( !pwReg.test($.trim($("#mber_pw").val()))){
		$("#pwValidate1").css("color","#ff1700");
		$("#pwValidate1").html("영 대소문자.숫자,특수문자를 포함하여 6~20자 이내로 입력하세요.");
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


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/member/adminList.do'/>");
	$("#frm").submit();
}

function goBoardWrite(){
	
		var mberPw =  $('#mber_pw').val();
		var mberPwChk =  $('#mber_pw_chk').val();
		if(mberPw != '' && mberPw != '') {
			if($("#pwCheckPre").val() != "Y" || $("#pwCheck").val() != "Y"){
				if($("#pwCheckPre").val() == "N"){
					alert("비밀번호를 양식에 맞게 입력해주세요.");
					$("#mber_pw").focus();
					return;
				}else if($("#pwCheck").val() == "N"){
					alert("입력하신 비밀번호와 동일하게 입력해주세요.");
					$("#mber_pw_chk").focus();
					return;
				}
			}
		}	

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
	mber_tel = $("#tel1").val() + "-" + $("#tel2").val() + "-"+ $("#tel3").val();
	$("#mber_tel").val(mber_tel);
	
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
	/*if($("#mber_pw").val() == ""){
		alert("비밀번호를 입력하세요.");
		$("#mber_pw").focus();
		return;
	}
	if($("#mber_pw_chk").val() == ""){
		alert("비밀번호확인을 입력하세요.");
		$("#mber_pw_chk").focus();
		return;
	}*/
	mber_email = $("#mber_email1").val()+"@"+$("#mber_email2").val();
	$("#mber_email").val(mber_email);
	
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/member/adminUpdate.do'/>");
	$("#frm").submit();
}


</script>

<form:form commandName="vo" name="frm" id="frm" method="post" >
	<input type="hidden" name="mber_id" id="mber_id" value="<c:out value='${memberVo.mber_id }'/>" />
	<input type="hidden" id="pwCheckPre" value="N">
	<input type="hidden" id="pwCheck" value="N">
	<input type="hidden" id="mber_type" name="mber_type" value="A">
	<div id="container">
		<h3>관리자 계정관리</h3>
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
						<th>아이디</th>
						<td class="al_l"><c:out value='${memberVo.mber_id }'/></td>
					</tr>
					<tr>
						<th><span style="color:red;">*</span>비밀번호</th>
						<td class="al_l">
							<input type="password" id="mber_pw" name="mber_pw" class="type-password required" title="비밀번호 입력란"/>
                            <span id="pwValidate1">6~20자 영문, 숫자로만 공백없이 입력하세요.</span>							
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
						<th>관리자명</th>
						<td class="al_l">
							<input type="text" id="mber_name" name="mber_name" class="w10p" title="관리자명"  value="<c:out value='${memberVo.mber_name }'/>" /></td>
					</tr>
					
					<tr>
						<th><span style="color:red;">*</span>연락처</th>
						<td class="al_l">
							<c:set var="mber_tel" value="${fn:split(memberVo.mber_tel, '-')}"></c:set>
							<input type="text" id="tel1" name="tel1" class="w10p" title="전화번호 입력란1" value="${mber_tel[0] }" maxlength="3"/> - 
	                        <input type="text" id="tel2" name="tel2" class="w10p" title="전화번호 입력란2" value="${mber_tel[1] }" maxlength="4"/> - 
	                        <input type="text" id="tel3" name="tel3" class="w10p" title="전화번호 입력란3" value="${mber_tel[2] }" maxlength="4"/>	                        
	                        <input type="hidden" id="mber_tel" name="mber_tel" value=""/>
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
                                <option value="">직접입력</option>
                             </select>
                             <input type="hidden" id="mber_email" name="mber_email" class="type-text required w163" title="이메일 입력란" />
						</td>
					</tr>					
					<tr>
						<th>상태</th>
						<td class="al_l">
							<select id="mber_status" name="mber_status">
								<option value="Y" <c:if test="${memberVo.mber_status eq 'Y' }">selected</c:if>>사용</option>
								<option value="N" <c:if test="${memberVo.mber_status eq 'N' }">selected</c:if>>사용중지</option>
							</select>						
						</td>
					</tr>
					<tr>
						<th>권한</th>
						<td class="al_l">
							<select id="auth_code" name="auth_code">
								<option value="super" <c:if test="${memberVo.auth_code eq 'super' }">selected</c:if>>총괄관리자</option>
								<option value="admin" <c:if test="${memberVo.auth_code eq 'admin' }">selected</c:if>>대회관리자</option>
								<option value="board" <c:if test="${memberVo.auth_code eq 'board' }">selected</c:if>>게시판관리자</option>
							</select>						
						</td>
					</tr>
				</tbody>
				</table>
			</div>
			
			<!-- //bbs-write -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardWrite()" class="btn-ty1 black">수정</a>
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
				</p>
			</div>
		</div>
		<!-- //contents -->
	</div>
</form:form>
<c:if test="${!empty msg }" >
	<script type="text/javascript">
	var msg	= '${msg}'; 
	if(msg != ''){
		if(msg.indexOf('success') > 0){
			alert('수정되었습니다.');	
			location.href = '/apage/member/adminList.do';
		}
		if(msg.indexOf('fail') > 0){
			alert('실패하였습니다.');	
			location.href = '/apage/member/adminList.do';
		}		
	}
	</script>
</c:if>			
<jsp:include page="/apage/inc/footer.do"></jsp:include>