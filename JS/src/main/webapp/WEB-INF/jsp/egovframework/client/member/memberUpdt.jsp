<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<c:if test="${!empty msg }" >
	<script type="text/javascript">
	var msg	= '${msg}'; 
	if(msg != ''){
		var Form = document.frm;
		if(msg.indexOf('success') >= 0){
			alert('회원정보가 수정되었습니다.');
			location.href='/main.do';
			
			Form.submit();
		}else if(msg.indexOf('fail') >= 0){
			alert('실패하였습니다.');
			location.href='/main.do';
		}else{
			alert(msg);
			location.href='/main.do';
		}
	}
	</script>
</c:if>	
<script type="text/javascript">
$(document).ready(function () {
	
	//패스워드 유효성 검사
	$('#mber_pw').keyup(function(){
		var pw_length =  $('#mber_pw').val();
		if(pw_length.length > 1) {
		    if(pw_length.length<6  || pw_length.length>20){
				$('#pwValidate1').text('');
				$('#pwValidate1').html("<font color=#FF0000>영 대소문자.숫자,특수문자를 포함하여 6~20자 이내로 다시 입력하세요.</font>");			
				$("#pwCheck").val("N");
			}	
		    else if(!pw_length.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~,`,=,+,|,{,},;,:,<,>,.,?,/])|([!,@,#,$,%,^,&,*,?,_,~,`,=,+,|,{,},;,:,<,>,.,?,/].*[a-zA-Z0-9])/)){
				$('#pwValidate1').text('');
				$('#pwValidate1').html("<font color=#FF0000>영 대소문자.숫자,특수문자를 포함하여 6~20자 이내로 다시 입력하세요.</font>");
				$("#pwCheck").val("N");
			} 
		    else{
				$('#pwValidate1').html('');
				$('#pwValidate1').html("<font color=#0054FF>사용 가능합니다.</font>");
				$("#pwCheck").val("Y");
			}
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
		
		if($("#pwCheck").val() != "Y"){
			$('#pwValidate2').html("<font color=#FF0000>비밀번호를 영 대소문자.숫자,특수문자를 포함하여 6~20자 이내로 다시 입력하세요.</font>");
			$("#pwCheckPre").val("N");
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

function mberUpdt(){
	
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
	
		/* 
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
	 */
	
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
	
  	//연락처 유효성 검사
	var regExp = /^01(?:0|1|[6-9]+)-([0-9]{3}|[0-9]{4})-[0-9]{4}$/;
	if(regExp.test(mber_tel) == false) {
		alert("연락처가 유효하지 않습니다");
		$("#mber_tel").focus();
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
	
	if($("#mber_email2").val() == "직접입력"){
		$("#mber_email2").val("");
	}
	
	mber_email = $("#mber_email1").val()+"@"+$("#mber_email2").val();
	$("#mber_email").val(mber_email);
	
  	//이메일 유효성 검사
	var regExp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	/* var regExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i; */
	if(regExp.test(mber_email) == false) {
		alert("이메일 주소가 유효하지 않습니다");
		$("#mber_email").focus();
		return;
	}
	
	//뉴스
	if($("#mailing").is(":checked")){
		$("#mailing_yn").val("Y");
	}else{
		$("#mailing_yn").val("N");
	}
	
 	//var dataString  = $("#frm").serialize();
	//alert(dataString);
	//return;

	$("#frm").attr("action","/membership/updateMember.do");
	$("#frm").submit();

	
}
</script>

<div id="container" class="subpage">
	<div id="contents" class="s_con">
    	<h2 class="hide">본문</h2>
        <c:import url="/client/snb.do" />
                
        <div class="sub_content">
        	<div class="inner">
            	<div class="sub_head">
                   	<h3 class="c_tit">회원정보수정</h3>
                </div>                   
                    
           <div id="member">
            <!--//joinStep //E-->
            <h4 class="h4_tit">회원정보수정</h4>
                    <div class="step3_wrap">
                        <div class="join_tbl">
                        <form:form commandName="vo" id="frm" name="frm" method="post">
						<input type="hidden" id="pwCheckPre" value="Y">
						<input type="hidden" id="pwCheck" value="Y">
						<input type="hidden" id="idCheck" value="Y">
						<input type="hidden" id="emailCheck" value="N">
						<form:hidden path="mber_dupinfo"/>
                            <table summary="회원정보수정">
                                <caption>회원정보수정</caption>
                                <colgroup>
                                    <col width="25%">
                                    <col width="75%">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col"><span class="ft_or">*</span>이름</th>
                                        <td>                                        	
                                        	${memberVo.mber_name }
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="col"><label for="birth">생년월일</label></th>
                                        <td class="birInput">
                                        	${memberVo.mber_birth }			                            			                                										
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="col">성별</th>
                                    
                                    	<td>
                                    		<c:if test="${memberVo.mber_gender eq 'M'}">남성</c:if>
                                    		<c:if test="${memberVo.mber_gender eq 'F'}">여성</c:if>
			                            </td>
			                            
                                    </tr>
                                    <tr>
                                        <th scope="col"><span class="ft_or">*</span><label for="id">아이디</label></th>
                                        <td>
                                            ${memberVo.mber_id }
                                            <input type="hidden" name="mber_id" id="mber_id" value="${memberVo.mber_id }" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="col"><label for="pw">비밀번호</label></th>
                                        <td>
                                            <input type="password" id="mber_pw" name="mber_pw" class="type-password required" title="비밀번호 입력란" value=""/>
                                            <span id="pwValidate1">영 대소문자.숫자,특수문자를 포함하여 6~20자 이내로 입력하세요.(패스워드 변경을 원치 않으시면 빈칸으로 남겨 주세요.)</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="col"><label for="pwCheck">비밀번호 확인</label></th>
                                        <td>
                                        	<input type="password" id="mber_pw_chk" name="mber_pw_chk" class="type-password required" title="비밀번호 확인 입력란" />
                                        	<span id="pwValidate2">비밀번호를 다시 한번 입력해 주세요.</span> </td>
                                    </tr>
                                    <tr>
                                        <th scope="col" rowspan="2"><label for="zip1">주소</label></th>
                                        <td>
                                            <input type="text" id="zipcode" name="zipcode" class="type-text required" title="우편번호 " value="${memberVo.zipcode }" readonly /> 
                                            <a href="javascript:addr_pop();" id="addrSearchBtn">우편번호 검색</a>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td>
                                        	<input type="text" id="addr" name="addr" class="type-text required w98p" title="상세주소" value="${memberVo.addr }" />
                                        	<input type="text" id="addr_detail" name="addr_detail" class="type-text required w98p" title="상세주소" value="${memberVo.addr_detail }" />
                                        </td>
                                    </tr>
                                     <tr>
                                        <th scope="col"><label for="tel1">연락처</label></th>
                                        <td class="phone">
                                            <input type="text" id="tel1" name="tel1" class="type-text required " title="전화번호 입력란1" value="${tel_arr0 }" maxlength="3" pattern="\d*" placeholder="" 
                                            onkeydown="this.value=this.value.replace(/[^0-9]/g,'')" 
                 							onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" 
                 							onblur="this.value=this.value.replace(/[^0-9]/g,'')"
                                            /> -
                                            <input type="text" id="tel2" name="tel2" class="type-text required " title="전화번호 입력란1" value="${tel_arr1 }" maxlength="4" pattern="\d*" placeholder=""
                                            onkeydown="this.value=this.value.replace(/[^0-9]/g,'')" 
                 					onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" 
                 					onblur="this.value=this.value.replace(/[^0-9]/g,'')"
                                            /> - 
                                            <input type="text" id="tel3" name="tel3" class="type-text required " title="전화번호 입력란1" value="${tel_arr2 }" maxlength="4" pattern="\d*" placeholder=""
                                            onkeydown="this.value=this.value.replace(/[^0-9]/g,'')" 
                 					onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" 
                 					onblur="this.value=this.value.replace(/[^0-9]/g,'')"
                                            />
                                            <input type="hidden" id="mber_tel" name="mber_tel" value=""/>
                                            <span class="ml15">
                                            	<input type="checkbox" id="sms" class="type-check required w30" <c:if test="${memberVo.sms_yn eq 'Y' }">checked</c:if>/>SMS수신설정
                                            	<input type="hidden" id="sms_yn" name="sms_yn" value="" />
                                            </span>
                                        </td>
                                    </tr>
                                     <tr>
                                        <th scope="col"><span class="ft_or">*</span><label for="mail1">이메일</label></th>
                                        <td>
											<dd>
												<input type="text" id="mber_email1" name="mber_email1" maxlength="40" value="${email_arr0 }" class="type-text required" title="이메일1" > @
												<input type="text" id="mber_email2" name="mber_email2" value="${email_arr1 }" class="type-text required" title="이메일2" >
												<select id= "emailSelect">
													<option value="">::::선택::::</option>
													<option value="naver.com" <c:if test="${email_arr1 eq 'naver.com'}">selected="selected"</c:if>>naver.com</option>
													<option value="nate.com" <c:if test="${email_arr1 eq 'nate.com'}">selected="selected"</c:if>>nate.com</option>
													<option value="gmail.com" <c:if test="${email_arr1 eq 'gmail.com'}">selected="selected"</c:if>>gmail.com</option>
													<option value="korea.com" <c:if test="${email_arr1 eq 'korea.com'}">selected="selected"</c:if>>korea.com</option>
													<option value="daum.net" <c:if test="${email_arr1 eq 'daum.net'}">selected="selected"</c:if>>daum.net</option>
													<option value="hanmail.net" <c:if test="${email_arr1 eq 'hotmail.com'}">selected="selected"</c:if>>hanmail.net</option>
													<option value="hotmail.com" <c:if test="${email_arr1 eq 'hotmail.com'}">selected="selected"</c:if>>hotmail.com</option>
													<option value="">직접 입력</option>
												</select>
												<input type="hidden" id="mber_email" name="mber_email" class="type-text required w163" title="이메일 입력란" />
											</dd>
                                        
                                            <p>
                                            	<input type="checkbox" id="mailing" class="type-check required" <c:if test="${memberVo.mailing_yn eq 'Y' }">checked</c:if> />뉴스레터 및 안내메일 수신 설정
                                            	<input type="hidden" id="mailing_yn" name="mailing_yn" value="" />
                                            </p>
                                        </td>
                                    </tr>                                    
                                </tbody>
                            </table>
                            </form:form>
                        </div>
                    </div>
                </div>
                <div class="btn_r2">
                   <a href="javascript:mberUpdt();">정보수정</a>
                   <a href="/main.do" class="gray">취소</a>
                </div>
                 <!-- //basicBox -->
              </div><!--inner-->
           </div>
       </div>
       <!-- #CONTENTS //E -->
	</div> 
	<!-- #CONTAINER //E -->	
<jsp:include page="/client/footer.do"></jsp:include>
