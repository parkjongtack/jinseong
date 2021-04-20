<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<script type="text/javascript">

</script>
<form id="frm" name="frm" method="post"> 
	<input type="hidden" name="agree_yn" id="agree_yn" />
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
			            <ul class="joinStep"><!--on으로 제어-->
			                <li>1step <strong>약관동의</strong><span class="step1"></span></li>
			                <li>2step <strong>실명인증</strong><span class="step2"></span></li>
			                <li>3step <strong>회원정보입력</strong><span class="step3"></span></li>
			                <li class="on">4step <strong>회원가입완료</strong><span class="step4"></span></li>
			            </ul>
		            	<!--//joinStep //E-->
		                <div class="basicBox">
		                    <div class="step4_wrap">
		                       <div class="join_end">
		                           <p><strong>환영합니다.</strong> <span><c:out value='${mber_name}'/></span>님 회원가입이 완료되었습니다.</p>
		                           <p class="sm_txt">회원정보 수정 및 대회접수 관리는 마이페이지에서 가능합니다. 감사합니다.</p>
		                           <div class="btn_c">
		                               <a href="/main.do" class="btn_or">홈으로</a>
		                               <a href="/membership/login.do" class="btn_gray">로그인</a>
		                               <a href="http://bowlingkoreamall.com/" class="btn_gray">쇼핑몰 가기</a>
		                           </div>
		                       </div>
		                    </div>
		                    <!-- //agree -->
		                </div>
		                <!-- //basicBox -->
		        	</div>
		        	<!-- //member -->

                   </div><!--inner-->
                </div>
            </div>
            <!-- #CONTENTS //E -->
	</div> 
	<!-- #CONTAINER //E -->

<jsp:include page="/client/footer.do"></jsp:include>