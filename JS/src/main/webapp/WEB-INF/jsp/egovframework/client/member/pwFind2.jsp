<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<script type="text/javascript">
$(document).ready(function () {
	var rc = '${rc}';
	if(rc != ''){
		/* console.log('rc', rc); */
		$('#hpChc').val('Y');
	}
 	var memberId = '${memberId}';
// 	console.log('memberId', memberId);
	
 	var msg	= '${msg}';
// 	console.log('msg', msg);
	
	if(msg != ''){
 	var Form = document.frm;
	if(msg.indexOf('success') >= 0){
		alert('회원정보가 수정 되었습니다.');
		location.href='/main.do';
	/* 	Form.submit(); */ 
			
	}else if(msg.indexOf('fail') >= 0){
		alert('실패하였습니다.');
		location.href='/main.do';
	}else{
		alert(msg);
		location.href='/main.do';
	}
		}
 
}); 
 
</script>

<script type="text/javascript">

$(document).ready(function () {
	//패스워드 유효성 검사
	$('#mber_pw').keyup(function(){
		var pw_length =  $('#mber_pw').val();
		
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




function pwUpdt(){
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
	
	
	$("#frm").attr("action","/membership/updatePw.do");
	$("#frm").submit();
	
}


</script>

<!--//LOC_WRAP //E-->
<div id="container" class="subpage">
	<div id="contents">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />
                <div class="sub_content">
                    <div class="inner">
                        <div class="sub_head">
                            <h3 class="c_tit">비밀번호 변경</h3>
                        </div>


                        <div id="member">
                            
                            <div class="find_id_wrap">

                                <div class="findBx">
                                    <div class="f_password">
                                        <p class="tit">비밀번호 변경</p>
                                        <div class="find_txt">
                                        <form:form commandName="vo" id="frm" name="frm" method="post">
                                        <input type="hidden" name="mber_id" value="${memberId}" >
                                        <input type="hidden" name="reg_code" id="reg_code" value="${rc}"/>
                                        <input type="hidden" name="hp_chck" id="hpChc" value="N"/>
                                        <input type="hidden" id="pwCheckPre" value="N">
										<input type="hidden" id="pwCheck" value="N">
										
										
										                    
    									<!-- 20181220 수정 시작-->
                                        	<div>
                                        		<p class="fp_title"><span class="ft_or">*</span><label for="pw">새 비밀번호</label></p>
                                        			<p class="mb20">
                                        			  <input type="password" id="mber_pw" name="mber_pw" class="type-password required" title="비밀번호 입력란" value=""/>
                                        			  <span id="pwValidate1" class="fp_tip">영 소문자 또는 영 대문자, 숫자, 특수문자를 포함하여 6~20자 이내로 입력하세요.</span>
                                        			</p>
                                        			
                                      			  <p class="fp_title"><span class="ft_or"><br>*</span><label for="pwCheck">새 비밀번호 확인</label></p>
                                        			<p class="mb20">
                                        				<input type="password" id="mber_pw_chk" name="mber_pw_chk" class="type-password required" title="비밀번호 확인 입력란" />
                                        			  <span id="pwValidate2" class="fp_tip">비밀번호를 다시 한번 입력해 주세요.</span>
                                    			    </p>
                                    			    
                                                <div class="fd_btn">
                                                    <!-- <a href="#" class="btn_gr w200">이전으로</a> --> 
                                                     
                                                    <a href="javascript:pwUpdt();" class="btn_or w200">확인</a>
                                                </div>
                                            </div>
                                             <!-- // 20181220 수정 끝-->
                                            
                                        </form:form>
                                        </div>
                                    </div>
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

        <!--//#FOOTER //E-->



<jsp:include page="/client/footer.do"></jsp:include>