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
		if(msg.indexOf('fail') >= 0){
			alert('비밀번호를 정확하게 입력해주세요.');
			location.href='/membership/myPwdCheckPage.do';
		}else{
			alert(msg);
			location.href='/main.do';
		}
	}
	</script>
</c:if>	

<script type="text/javascript">
$(document).ready(function () {	
	
	$("#mber_id, #mber_pw").keydown(function(key) {
		if (key.keyCode == 13) {
				goPwdCheck();
		}
	});
	
});

function goPwdCheck() {
	
	if($("#mber_pw").val() == ""){
		alert("비밀번호를 입력해주세요.");
		$("#mber_pw").focus();
		return;
	}
	
	
	$("#frm").attr("action", "<c:url value='/membership/myPwdCheck.do'/>");
	$("#frm").submit();
}
</script>

<form:form commandName="vo" name="frm" id="frm" >
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
			<h2 class="hide">본문</h2>
		    <c:import url="/client/snb.do" />                       
		        
	        <div class="sub_content">
	        	<div class="inner">
		        	<div class="sub_head">
		            	<h3 class="c_tit">비밀번호 재확인</h3>
		            </div>    
	        
			        <div id="member">
                    	<div class="icoBx ic2">
                        	<p>회원가입시 입력하신 비밀번호를 입력하세요</p>
                            <p>본인확인을 위해 한번 더 비밀번호를 입력해주세요. </p>
                            <p>비밀번호는 타인에게 노출되지 않도록 주의해 주세요.</p>
                        </div>   
                        <div class="find_id_wrap">
                        	<div class="findBx">
                            	<div class="f_password">
                                	<p class="tit">비밀번호 재확인</p>
                                   	<div class="find_txt">
                                    		<div>
                                           	<dl>
                                               	<dt><label for="id3">아이디</label></dt>
                                               	<dd>
                                               		<span>${mber_id }</span>
                                               		<input type="hidden" name="mber_id" value="${mber_id }">
                                               	</dd>
                                            </dl>
                                            <dl class="pw_input">
                                               	<dt><label for="mber_pw">비밀번호</label></dt>
                                               	<dd><input type="password" name="mber_pw" class="type-text required" value="" id="mber_pw" ></dd>
                                            </dl>
                                            <div class="fd_btn">
                                               	<a href="javascript:void(0)" onkeybord onclick="goPwdCheck()" class="btn_or w200">확인</a>
                                               	<a href="#" class="btn_gr w200">이전으로</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                           	</div>
                        </div>
                        <!-- //FIND_ID_WRAP [E] -->
                    </div>
                    <!-- //member -->
	    		</div>
	    		<!-- inner //E -->
	   		</div>
	   		<!-- sub_content //E -->
		</div>
		<!-- #CONTENTS //E -->		
	</div> 
	<!-- #CONTAINER //E -->		
</form:form>

<jsp:include page="/client/footer.do"></jsp:include>