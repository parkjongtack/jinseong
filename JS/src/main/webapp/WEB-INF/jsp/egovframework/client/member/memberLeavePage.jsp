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
			alert('탈퇴처리 되었습니다.\n그동안 이용해주셔서 감사합니다.');
			location.href='/membership/logout.do';
			
			Form.submit();
		}else if(msg.indexOf('fail') >= 0){
			alert('실패하였습니다.');
			location.href='/main.do';
		}else if(msg.indexOf('pwfail') >= 0){
			alert('비밀번호를 확인해주세요.');
			location.href='/membership/memberLeavePage.do';
		}else {
			alert(msg);
			location.href='/main.do';
		}
	}
	</script>
</c:if>	

<script type="text/javascript">
$(document).ready(function () {
	
	//비밀번호 일치여부 체크
	$('#pwdChk').keyup(function(){
		$("#pwCheckAjax").val("N");		
	});
	
	//비밀번호 일치여부 체크
	$('#pwdChk').keydown(function(){
		$("#pwCheckAjax").val("N");		
	});
});

function goLeave() {
	
	if($("#pwdChk").val() == ""){
		alert("비밀번호를 입력해주세요.");
		$("#pwdChk").focus();
		return;
	}
	
	if($("#withdraw_content").val() == ""){
		alert("탈퇴 사유를 입력해주세요.");
		$("#withdraw_content").focus();
		return;
	}
	/* 
	if($("#pwCheckAjax").val() == "N"){
		alert("비밀번호가 일치하지 않습니다.");
		return;
	}
	
	 */
	 
 
	var mber_id = $("#mber_id").val();
	var mber_pw = $("#pwdChk").val();

	$.ajax({
		url 		: "<c:url value='/membership/memberLeave_pwdCheck.do'/>", 
		type		: "post",
		data		: {mber_id : mber_id, mber_pw : mber_pw},
		async		: true,
		dataType 	: "json",
		beforeSend: function (xhr) {
			xhr.setRequestHeader('AJAX', true);
		},
		success	: function(data){
			if(data.root.ResultCode == "Y") { //일치
				$("#frm").attr("action", "<c:url value='/membership/memberLeave.do'/>");
				$("#frm").submit();		
			}else { //일치
				$("#pwdChkResult").html("비밀번호가 일치하지 않습니다.");
				$("#pwdChkResult").css("color","#FF0000");
				$("#pwCheckAjax").val("N");
				alert("비밀번호를 확인해주세요.");
			}
		},
		error : function(data, status, err) {
			alert("에러입니다." + data +"<><><>"+ status+"<><><>"+ err);
			return;
		}
	})
	

}	
</script>

<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" id="pwCheckAjax" value="N">
	<div id="container" class="subpage">
		<div id="contents">
			<h2 class="hide">본문</h2>
			<c:import url="/client/snb.do" />
	
			<div class="sub_content">
				<div class="inner">
					<div class="sub_head">
						<h3 class="c_tit">회원탈퇴</h3>
					</div>
	
					<div id="member">
						<div class="step3_wrap">
	                        <div class="join_tbl">
	                            <table summary="회원정보입력">
	                                <caption>회원정보입력</caption>
	                                <colgroup>
	                                    <col width="25%">
	                                    <col width="75%">
	                                </colgroup>
	                                <tbody>
	                                    <tr>
	                                        <th scope="col">이름</th>
	                                        <td>
	                                        	${mber_name }
	                                        	<input type="hidden" name="mber_name" id="mber_name" value="${mber_name }">
	                                        </td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="col">아이디</th>
	                                        <td>
	                                        	${mber_id }
	                                        	<input type="hidden" name="mber_id" id="mber_id" value="${mber_id }">
	                                        </td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="col"><span class="ft_or">*</span><label for="pwCheck">비밀번호 확인</label></th>
	                                        <td>
	                                        	<input type="password" name="mber_pw" id="pwdChk" class="type-password required" title="비밀번호 확인 입력란" />
	                                        	<span id="pwdChkResult">비밀번호를 입력해 주세요.</span> 
	                                        </td>
	                                    </tr>
	                                     <tr>
	                                       <th>탈퇴 사유</th>
	                                        <td>
	                                            <textarea name="withdraw_content" id="withdraw_content" placeholder="탈퇴 사유를 적어주시기 바랍니다."></textarea>
	                                        </td>
	                                    </tr>                              
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
					</div>
					<!-- //member -->
					<div class="btn_r2">
						<a href="javascript:void(0)" onclick="goLeave()">확인</a>
	                   	<a href="/" class="gray">취소</a>
	                </div>
	                <!-- btn_r2 //E -->
				</div>
				<!-- inner //E -->
			</div>
		</div>
		<!-- #CONTENTS //E -->
	</div>
	<!-- #CONTAINER //E -->
</form:form>

<jsp:include page="/client/footer.do"></jsp:include>