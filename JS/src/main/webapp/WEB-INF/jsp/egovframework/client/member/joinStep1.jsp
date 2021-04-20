<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<script type="text/javascript">

function goNext(){
	
	var chk1 = $("#chk1").val();
	var chk3 = $("#chk3").val();
	
	if($("input[name=clauseAgree]:checked").val() != chk1){
		alert("회원약관에 동의하셔야 합니다.");
		$("#chk1").focus();
		return;
	}
	if($("input[name=clauseAgree2]:checked").val() != chk3){
		alert("개인정보 수집 약관에 동의하셔야 합니다.");
		$("#chk3").focus();
		return;
	}
	
	$("#agree_yn").val("Y");
	$("#frm").attr("action","<c:url value='/membership/joinStep2.do'/>");
	$("#frm").submit();
	
}

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
	               <h3 class="c_tit">통합 회원가입</h3>
	           </div>
	                    
	                    
	           <div id="member">
	            <ul class="joinStep"><!--on으로 제어-->
	                <li class="on">1step <strong>약관동의</strong><span class="step1"></span></li>
	                <li class="">2step <strong>실명인증</strong><span class="step2"></span></li>
	                <li class="">3step <strong>회원정보입력</strong><span class="step3"></span></li>
	                <li class="">4step <strong>회원가입완료</strong><span class="step4"></span></li>
	            </ul>
	            <!--//joinStep //E-->
	            <h4 class="h4_tit">회원약관동의</h4>
	                
	                <div class="basicBox">
	                    <div class="agree_wrap">
	                        
	                        <div class="agree_text">
	                            <strong>제 1 장 총칙</strong> <br>
	                            <br>
	                            <strong>제1조 - 목 적</strong><br>
	                            본 약관은 ㈜진승이 제공하는 모든 서비스의 이용조건 및 절차, 기타 필요한 사항을 규정함을 목적으로 한다.<br>
	                            <br>
	                            <strong>제2조 - 약관의 효력</strong><br>
	                            본 약관은 전기통신사업법 제31조, 동법 시행규칙 제21조의 2에 따라 공시절차를 거친 후 서비스를 통하여 <br>
	                            이를 공지하거나 전자우편 기타의 방법으로 회원에게 통지함으로써 효력을 발생합니다. <br>
	                            <br>
	                            <strong>제3조 - 약관의 변경</strong><br>
	                            회사는 회사 사정상 혹은 서비스상 중요 사유가 있을 때 약관을 변경할 수있으며, 변경된 약관은 제2조와 <br>
	                            은 방법으로 효력을 발생합니다<br>
	                            <br>
	                            <strong>제4조 - 약관 외 준칙</strong> <br>
	                            본 약관에 명시되지 않은 사항은 전기통신기본법, 전기통신사업법 등 기타 대한민국의 관련 법령과 상관습에 따릅니다.<br>
	                            <br>
	                            <strong>제 2 장 회원 가입 및 탈퇴</strong> <br>
	                            <br>
	                            <strong>제1조 - 회원 가입</strong><br>
	                            (1) 가입신청자 온라인으로 등록절차를 거쳐서 '동의함' 버튼을 누르면 이 약관에 합법적으로 동의하는 것으로 간주합니다.<br>
	                            (2) 회사는 다음 각 호에 해당하지 않는 한 신청자를 회원으로 등록합니다.<br>
	                            - 등록내용에 허위, 기재누락, 오기가 있는 경우<br>
	                            - 회원으로 등록하는 것이 회사의 기술상 또는 업무 수행상 현저히 지장이 있는 경우<br>
	                            - 사회의 안녕과 질서 그리고 미풍양속을 저해할 목적으로 신청한 경우.<br>
	                            <br>
	                            <strong>제2조 - 회원 탈퇴</strong><br>
	                            회원은 회사에 언제든지 자신의 회원 등록을 말소해 줄 것(이용자 탈퇴)을 요청 할 수 있으며 회사는 위 <br>
	                            요청을 받은 즉시 해당 이용자의 회원 등록 말소를 위한 절차를 밟습니다 <br>
	                            (1) 회원이 사망한 때는 회원자격을 상실합니다. <br>
	                            (2) 회원이 다음 각 호의 사유에 해당하는 경우, 회사는 사전 통지없이 회원 자격을 상실시키거나 또는 <br>
	                            간을 정하여 서비스 이용을 중지할 수 있습니다. <br>
	                            - 등록 신청 시에 허위 내용을 등록한 경우 <br>
	                            - 본 약관을 위반한 경우 <br>
	                            - 공공질서 및 미풍양속에 반하는 경우 <br>
	                            - 범죄적 행위에 관련되는 경우 <br>
	                            - 서비스에 위해를 가하는 등 서비스의 건전한 이용을 저해한 경우 <br>
	                            - 기타 회원으로서의 자격을 지속시키는 것이 부적절하다고 판단되는 경우 <br>
	                            <br>
	                            <strong>제 3 장 서비스 제공 및 이용</strong> <br>
	                            <br>
	                            <strong>제1조 - 서비스의 내용</strong><br>
	                            회사는 필요한 경우 서비스의 내용을 추가 또는 변경할 수 있으며, 회사는 추가 또는 변경내용을 <br>
	                            회원에게 통지하여야 합니다.<br>
	                            <br>
	                            <strong> 제2조 - 서비스 이용시간</strong><br>
	                            (1) 서비스는 회사의 업무상 또는 기술상 장야, 기타 특별한 사유가 없는 한 연중 무휴, 1일 24시간 이용할 수 있습니다.<br>
	                            (2) 회사는 제공하는 서비스 중 일부에 대한 이용시간을 별도로 정할 수 있으며, 이 경우 관련 사항을 사전에 <br>
	                            회원에게 공지 또는 통지합니다.<br>
	                            <br>
	                            <strong>제3조 - 정보의 제공 및 광고의 게재</strong><br>
	                            (1) 회사는 서비스를 운용함에 있어서 각종 정보를 서비스에 게재하는 방법등으로 회원에게 제공할 수 있습니다.<br>
	                            (2) 회사는 광고 수익을 기반으로 본 서비스를 제공하고 있습니다. 서비스를 이용하고자 하는 이용자는 <br>
	                            이 광고 게재에 대해 동의하는 것으로 간주됩니다.<br>
	                            <br>
	                            <strong>제4조 - 서비스 제공의 중지</strong><br>
	                            회사는 언제든지 본 서비스 (전체 혹은 그 일부)를 일시 또는 영구적으로 수정하거나 중단할 수 있습니다. <br>
	                            귀하는 서비스의 수정, 중단 또는 중지에 대해 회원 또는 제3자에 대하여 어떠한 책임도 지지 않습니다.<br>
	                            <br>
	                            <strong>제 4 장 의무사항</strong><br>
	                            <br>
	                            <strong>제1조 - 회사의 의무</strong><br>
	                            (1) 회사는 본 약관이 정하는 바에 따라 지속적이고, 안정적인 서비스를 제공하 기 위해서 노력합니다. <br>
	                            (2) 회사는 서비스 제공과 관련해서 알고 있는 회원의 신상정보를 본인의 승낙 없이 제 3자에게 누설, 배포하지 않습니다. <br>
	                            (3) 회사는 회원 전체 또는 일부의 개인정보에 관한 통계 자료를 작성하여 회사의 내부 마케팅 자료 또는 <br>
	                            고 영업을 위한 통계 자료로 활용할 수 있습니다.<br>
	                            (4) 회사는 회원으로부터 제출되는 의견 및 불만사항이 정당하다고 판단하는 경우 우선적으로 그 사항을 처리하여야 합니다.<br>
	                            (5) 종이카드 서비스는 우편으로 제공되기 때문에 배달과정에서의 우편배달 사고에 대해서는 회사가 책임을 지지 않습니다.<br>
	                            <br>
	                            <strong>제2조 - 회원의 의무</strong><br>
	                            (1) 회원은 본 약관을 준수 하여야 합니다.<br>
	                            (2) 사용상의 과실 혹은 사용자명 및 비밀번호의 부주의한 관리로 3자의 부정사용 등 피해가 발생한 경우 모든 책임은 이를 <br>
	                            야기한 회원에게 있습니다 <br>
	                            (3) 전기통신법 제53조와 전기통신사업법 시행령 16조(불온통신), 통신사업  제53조3항에 의거, 본 서비스를 이용하여 <br>
	                            란물이나 불온한 내용을 전송함으로써 발생하는 모든 법적인 책임은 '아이디 이용자'에게 있습니다<br>
	                            (4) 자신의 ID가 부정하게 사용된 경우 회원은 반드시 회사에 그 사실을 신고하여야 합니다.<br>
	                            <br>
	                            <strong>[부칙]</strong><br>
	                            본 약관은 2000년 7월 1일부로 시행합니다.
	                        </div>
	                        <div class="agree_q">
	                            <span>위 이용약관 내용에 동의하십니까?</span>
	                            <p><input type="radio" id="chk1" name="clauseAgree" class="type-radio required" value="Y"/>
	                            <label for="chk1">약관에 동의합니다.</label></p>	                        
	                            
	                            <p><input type="radio" id="chk2" name="clauseAgree" class="type-radio required" value="N"/>
	                            <label for="chk2">약관에 동의하지않습니다.</label></p>
	                        </div>
	                    </div>
	               </div>
	                 <!-- //basicBox -->
	                        
	                    <h4 class="h4_tit">개인정보 수집에 대한 동의</h4>
	                    <div class="basicBox">
	                        <div class="agree_wrap pt30">
	                        
	                        <div class="agree_text"><strong>㈜진승(이하 “진승”이라고 함)은 별도의 회원가입 절차 없이 대부분의 컨텐츠에 자유롭게 접근할 수 있습니다. 
	                      <br>진승은의 회원제 서비스를 이용하시고자 할 경우다음의 정보를 입력해주셔야 하며 선택항목을 입력하시지 않았다 하여<br>서비스 이용에 제한은 없습니다.</strong> <br>
	                        <br />
	                  				<strong>개인정보의 수집 항목 및 수집 방법 </strong><br />
	                  				1. 진승은 회원 가입 전에 진승의 개인정보 보호 방침 또는 이용 약관의 내용에 대해 회원 가입전에 동의할 수 있는 절차를 마련하여 진승의 개인정보 수집에 대해 회원 가입을 하는 것은 개인 정보 수집에 대하여 동의하는 것으로 간주합니다. <br />
	                  				- 필수항목 : 이름, 생년월일, 아이디, 비밀번호, 주소, 전화번호, 휴대전화번호, 이메일 <br />
	                  				- 성명, 아이디, 비밀번호, 생년월일 : 회원제 서비스 이용에 따른 본인 식별 절차에 이용 <br />
	                  				- 이메일주소, 전화번호, 휴대전화번호 : 고지사항 전달, 본인 의사 확인, 불만 처리 등 원활한 의사소통 경로의 확보, <br>
	                  				&nbsp;&nbsp;새로운 서비스/신상품이나 이벤트 정보의 안내 <br />
	                  				- 은행계좌정보, 신용카드정보 : 유료정보 이용에 대한 요금 결제 <br />
	                  				- 주소, 휴대전화번호 : 청구서, 경품과 쇼핑 물품 배송에 대한 정확한 배송지의 확보 <br />
	                  				- 생년월일 : 인구통계학적 분석 자료(이용자의 연령별, 성별, 지역별 통계분석) <br />
	                  				- 그 외 선택항목 : 개인맞춤 서비스를 제공하기 위한 자료 <br /><br />
	                  				2. 개인정보의 수집 방법<br>
	                  				- 홈페이지, 서면양식, 팩스, 전화, 이메일, 배송요청<br><br />
	                  				3. 개인정보 수집에 대한 동의<br>
	                  				- 진승은 회원이 개인정보취급방침 및 이용약관의 내용에 대해 「동의한다」버튼 또는 「동의하지 않는다」버튼을 클릭할 수 있는 절차를 마련하여, 「동의한다」버튼을 클릭하면 개인정보 수집에 대해 동의한 것으로 봅니다.
	                  				<br />
	                  				<br>
	                  				<strong>개인정보의 보유기간 및 이용시간 </strong><br />
	                  				1. 회원의 개인정보는 회사가 신청인에게 서비스를 제공하는 기간 동안에 한하여 보유하고 이를 활용합니다. 다만 다른<br>법률에 특별한 규정이 있는 경우에는 관계법령에 따라 보관합니다.<br />
	                  				- 회원가입정보 : 회원가입을 탈퇴하거나 회원에 제명된 때 <br />
	                  				- 대금지급정보 : 대금의 완제일 또는 채권소명시효기간이 만료된 때 <br />
	                  				- 배송정보 : 물품 또는 서비스가 인도되거나 제공된 때 <br />
	                  				- 설문조사, 이벤트 등 일시적 목적을 위하여 수집한 경우 : 당해 설문조사, 이벤트 등이 종료한 때 <br /><br />
	                  				2. 위 개인정보 수집목적 달성시 즉시파기 원칙에도 불구하고 다음과 같이 거래 관련 권리 의무 관계의 확인 등을 이유로 일정기간 보유하여야 할 필요가 있을 경우에는 전자상거래 등에서의 소비자보호에 관한 법률 등에 근거하여 일정기간 보유합니다.<br>
	                  				- 계약 또는 청약철회 등에 관한 기록 : 5년<br />
	                          - 대금결제 및 재화 등의 공급에 관한 기록 : 5년<br />
	                          - 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년<br />
	                          - 설문조사, 이벤트 등 일시적 목적을 위하여 수집한 경우 : 당해 설문조사, 이벤트 등의 종료 시점<br /><br />
	                          3. 회원의 동의를 받아 보유하고 있는 거래정보 등을 회원이 열람을 요구하는 경우 진승은 지체 없이 열람, 확인 할 수 있도록 조치합니다.<br>
	                  				<br />
	                  			</div>
	                        <div class="agree_q">
	                            <span>위 이용약관 내용에 동의하십니까?</span>
	                            <p><input type="radio" id="chk3" name="clauseAgree2" class="type-radio required" value="Y"/>
	                            <label for="chk3">약관에 동의합니다.</label></p>	                        
	                            
	                            <p><input type="radio" id="chk4" name="clauseAgree2" class="type-radio required" value="N"/>
	                            <label for="chk4">약관에 동의하지않습니다.</label></p>
	                        </div><br>
	                        
	                    </div>
	                   </div>
	                    <!-- //basicBox -->
	               
	                
	                <ul class="boardBtnex">
	                    <li>진승 회원으로 가입하시면 동일한 아이디와 비밀번호로 진승의 모든 회원제 사이트의 서비스를 안전하게 이용하실 수 있습니다.</li>
	                </ul>
	                <div class="btn_r2">
	                    <a href="javascript:goNext();">다음으로</a>
	                    <a href="/main.do" class="gray">취소하기</a>
	                </div>
	                <!--//boardBtn -->
	                 </div>
	                <!-- //member -->
	                   </div><!--inner-->
	                </div>
            	</div>
            <!-- #CONTENTS //E -->
		</div> 
	<!-- #CONTAINER //E -->

<jsp:include page="/client/footer.do"></jsp:include>