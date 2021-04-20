<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style type="text/css">
.tepClass{display: none;}
.shopEventBtnWrap{ display: none;}
</style>
<script type="text/javascript">
$(document).ready(function() {
	
	//마우스 우클릭 방지
	$(document).on("contextmenu",function(e){
    	//console.log("c"+e);
        return false;
    });

	$(".moveShop").click(function() {

		var d1 = $(this).attr("data-d1");
		var d2 = $(this).attr("data-d2");
		var d3 = $(this).attr("data-d3");

		var b = $(this).attr("data-b");
		var m = $(this).attr("data-m");
		var s = $(this).attr("data-s");

		$("#d1").val(d1);
		$("#d2").val(d2);
		$("#d3").val(d3);
		$("#b").val(b);
		$("#m").val(m);
		$("#s").val(s);

		$("#shopForm").attr("action", "/shop/shopList.do");
		$("#shopForm").submit();

	});

});

var isLogin = false;
var loginId = "${sessionScope.mberInfo.mber_id }";

if (loginId != '') {
	isLogin = true
}

function goMberUpdt() {
	$("#mberForm").attr("action", "/membership/memberUpdt.do");
	$("#mberForm").submit();
}

function goLogout(){
	storage.setAutoLoginStorage();
	location.href="/membership/logout.do";
}

function goLoginPage(){
	autoLoginExec();
	location.href="/membership/login.do";
}
</script>

<form id="shopForm" name="shopForm" method="post">
	<input type="hidden" id="d1" name="menuDepth1" /> 
	<input type="hidden" id="d2" name="menuDepth2" /> 
	<input type="hidden" id="d3" name="menuDepth3" /> 
	<input type="hidden" id="b" name="big" /> 
	<input type="hidden" id="m" name="mid" /> 
	<input type="hidden" id="s" name="small" />
</form>

<form id="mberForm" name="mberForm" method="post">
	<input type="hidden" name="mber_id" value="${sessionScope.mberInfo.mber_id }" />
</form>
<div id="wrap">
	<div id="header" class="is-fixed">
		<div class="logo">
			<h1>
				<a href="/main.do"> 
					<img src="/resources/client/images/common/h_logo.png" alt="진승무역 로고" class="pVer"> 
					<img src="/resources/client/images/main/m_logo.png" alt="진승무역 로고" class="mVer" style="width: 138px">
				</a>
			</h1>
		</div>
		<div id="gnb">
			<ul>
				<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="">제품소개</a>
					<div class="depth2">
						<!-- display:none; 으로 제어-->
						<ul>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="">볼링볼</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="2" data-d3="1" data-b="1002" data-m="1030" data-s="">볼링백</a></li>
							<!-- <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="1" data-b="1003" data-m="1093" data-s="">볼링의류</a></li> -->
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="4" data-b="1003" data-m="1096" data-s="">볼링의류</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="4" data-d3="1" data-b="1004" data-m="1044" data-s="">볼링아대</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="5" data-d3="1" data-b="1006" data-m="1079" data-s="">볼링슈즈</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="6" data-d3="1" data-b="1005" data-m="1047" data-s="">볼링 악세사리</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="7" data-d3="1" data-b="1102" data-m="0" data-s="">프로샵 용품</a></li>
							<!-- <li><a href="javascript:moveShopList('1','7');">프로샵 용품</a></li> -->
						</ul>
					</div>
				</li>
				<li><a href="/contest/contestInfo.do">대회안내</a>
					<div class="depth2">
						<ul>
							<li><a href="/contest/contestInfo.do">고객감사대회</a></li>
							<li><a href="/event/eventContestInfo.do">이벤트대회</a></li>
							<li class="tepClass"><a href="/event/kokContestInfo.do">왕중왕전대회</a></li>
							<li><a href="/contest/contestRst.do">대회결과</a></li>
						</ul>
					</div>
				</li>
				<li><a href="/contest/contestAppList.do">대회신청</a>
					<div class="depth2">
						<ul>
							<li><a href="/contest/contestAppList.do">고객감사대회</a></li>
							<li><a href="/event/eventContestAppList.do">이벤트대회</a></li>
							<li class=""><a href="/event/kokContestAppList.do">왕중왕전대회</a></li>
						</ul>
					</div>
				</li>
				<li><a href="/board/noticeList.do">커뮤니티</a>
					<div class="depth2">
						<ul>
							<li><a href="/board/noticeList.do">공지사항</a></li>
							<!-- <li><a href="/board/eventList.do">이벤트</a></li> -->
							<li><a href="/board/asList.do">A/S게시판</a></li>
							<li><a href="/board/consultList.do">고객상담</a></li>
							<li><a href="/board/snsList.do">SNS</a></li>
						</ul>
					</div>
				</li>
				<li><a href="/contents/greeting.do">회사소개</a>
					<div class="depth2">
						<ul>
							<li><a href="/contents/greeting.do">인사말</a></li>
							<li><a href="/contents/history.do">연혁</a></li>
							<li><a href="/board/staffList.do">STAFF</a></li>
							<li><a href="/board/centerList.do">전국 볼링장</a></li>
							<li><a href="/contents/mapinfo.do">찾아오시는 길</a></li>
						</ul>
					</div>
				</li>
				<li><a href="/kegel/laneMachines.do">KEGEL</a>
					<div class="depth2">
						<ul class="bdr">
							<li><a href="/kegel/laneMachines.do">레인정비기계</a></li>
							<li><a href="/kegel/laneConsumable.do">레인정비소모품</a></li>
							<li><a href="/kegel/trainingList.do">훈련 도구</a></li>
							<li class="shopEventBtnWrap"><a href="/board/shopEventList.do">세미나</a></li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
		<!--GNB //E-->
		<div id="m_gnb">
			<div class="m_login">
				<c:if test="${sessionScope.mberInfo.mber_id eq null}">
					<a href="javascript:void(0);" onclick="goLoginPage()"><strong>로그인</strong>을 해주세요</a>
				</c:if>
				<c:if test="${sessionScope.mberInfo.mber_id ne null}">
					<a href="javascript:goLogout();">로그아웃</a>
					<!-- <a href="/membership/memberUpdt.do">회원정보수정</a> -->
					<!-- <a href="/membership/myContestAppList.do">마이페이지</a> -->
				</c:if>
				<button class="btn_all_close" type="button">닫기</button>
			</div>
			
			<ul>
				<c:if test="${sessionScope.mberInfo.mber_id ne null}">
					<li><a href="/membership/myContestAppList.do">마이페이지</a>
						<div class="depth2">
							<ul>
								<li><a href="/membership/myContestAppList.do">대회신청내역</a></li>
								<li><a href="/membership/myPwdCheckPage.do">회원정보수정</a></li>
								<li><a href="/membership/memberLeavePage.do">회원탈퇴</a></li>
								<li><a href="/contents/privacy.do">개인정보취급방침</a></li>
								<li><a href="/contents/rule.do">이용약관</a></li>
							</ul>
						</div>
					</li>
				</c:if>
					
				<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="">제품소개</a>
					<div class="depth2">
						<!-- display:none; 으로 제어-->
						<ul>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="">볼링볼</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="2" data-d3="1" data-b="1002" data-m="1030" data-s="">볼링백</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="1" data-b="1003" data-m="1093" data-s="">볼링의류</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="4" data-d3="1" data-b="1004" data-m="1044" data-s="">볼링아대</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="5" data-d3="1" data-b="1006" data-m="1079" data-s="">볼링슈즈</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="6" data-d3="1" data-b="1005" data-m="1047" data-s="">볼링 악세사리</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="7" data-d3="1" data-b="1102" data-m="0" data-s="">프로샵 용품</a></li>
							<!-- <li><a href="javascript:moveShopList('1','7');">프로샵 용품</a></li> -->
						</ul>
						<!-- 
						<ul>
							<li><a href="javascript:alert('준비중');">볼링볼</a></li>
							<li><a href="javascript:alert('준비중');">볼링백</a></li>
							<li><a href="javascript:alert('준비중');">볼링의류</a></li>
							<li><a href="javascript:alert('준비중');">볼링아대</a></li>
							<li><a href="javascript:alert('준비중');">볼링슈즈</a></li>
							<li><a href="javascript:alert('준비중');">볼링 악세사리</a></li>
							<li><a href="javascript:alert('준비중');">프로샵 용품</a></li>
						</ul>
						 -->
					</div>
				</li>
				
				
				<li><a href="/contest/contestInfo.do">대회안내</a>
					<div class="depth2">
						<ul>
							<li><a href="/contest/contestInfo.do">고객감사대회</a></li>
							<li><a href="/event/eventContestInfo.do">이벤트대회</a></li>
							<li class="tepClass"><a href="/event/kokContestInfo.do">왕중왕전대회</a></li>
							<li><a href="/contest/contestRst.do">대회결과</a></li>
						</ul>
					</div>
				</li>
				
				
				<li><a href="/contest/contestAppList.do">대회신청</a>
					<div class="depth2">
						<ul>
							<li><a href="/contest/contestAppList.do">고객감사대회</a></li>
							<!-- <li class="tepClass"><a href="/event/kokContestAppList.do">왕중왕전대회</a></li> -->
							<li><a href="/event/eventContestAppList.do">이벤트대회</a></li>
							<li class=""><a href="/event/kokContestAppList.do">왕중왕전대회</a></li>
						</ul>
					</div>
				</li>
				
				
				<li><a href="/board/noticeList.do">커뮤니티</a>
					<div class="depth2">
						<ul>
							<li><a href="/board/noticeList.do">공지사항</a></li>
							<!-- <li><a href="/board/eventList.do">이벤트</a></li> -->
							<li><a href="/board/asList.do">A/S게시판</a></li>
							<li><a href="/board/consultList.do">고객상담</a></li>
							<li><a href="/board/snsList.do">SNS</a></li>
						</ul>
					</div>
				</li>
				
				
				<li><a href="/contents/greeting.do">회사소개</a>
					<div class="depth2">
						<ul>
							<li><a href="/contents/greeting.do">인사말</a></li>
							<li><a href="/contents/history.do">연혁</a></li>
							<li><a href="/board/staffList.do">STAFF</a></li>
							<li><a href="/board/centerList.do">전국 볼링장</a></li>
							<li><a href="/contents/mapinfo.do">찾아오시는 길</a></li>
						</ul>
					</div>
				</li>
				
				
				<li><a href="javascript:void(0);">KEGEL</a>
					<div class="depth2">
						<ul class="bdr">
							<li><a href="/kegel/laneMachines.do">레인정비기계</a></li>
							<li><a href="/kegel/laneConsumable.do">레인정비소모품</a></li>
							<li><a href="/kegel/trainingList.do">훈련 도구</a></li>
							<li class="shopEventBtnWrap"><a href="/board/shopEventList.do">세미나</a></li>
						</ul>
					</div>
				</li>
				
				
			</ul>
		</div>
		<!--GNB //E-->	

		<div class="sitemap">
			<!--display:none 으로 제어 -->
			<strong>SITEMAP</strong>
			<ul>
				<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="">제품소개</a>
					<div class="depth2">
						<!-- display:none; 으로 제어-->
						<ul>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="">볼링볼</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="2" data-d3="1" data-b="1002" data-m="1030" data-s="">볼링백</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="1" data-b="1003" data-m="1041" data-s="">볼링의류</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="4" data-d3="1" data-b="1004" data-m="1044" data-s="">볼링아대</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="5" data-d3="1" data-b="1006" data-m="1079" data-s="">볼링슈즈</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="6" data-d3="1" data-b="1005" data-m="1047" data-s="">볼링 악세사리</a></li>
							<li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="7" data-d3="1" data-b="1102" data-m="0" data-s="">프로샵 용품</a></li>
							<!-- <li><a href="javascript:moveShopList('1','7');">프로샵 용품</a></li> -->
						</ul>
						<!-- 
						<ul>
							<li><a href="javascript:alert('준비중');">볼링볼</a></li>
							<li><a href="javascript:alert('준비중');">볼링백</a></li>
							<li><a href="javascript:alert('준비중');">볼링의류</a></li>
							<li><a href="javascript:alert('준비중');">볼링아대</a></li>
							<li><a href="javascript:alert('준비중');">볼링슈즈</a></li>
							<li><a href="javascript:alert('준비중');">볼링 악세사리</a></li>
							<li><a href="javascript:alert('준비중');">프로샵 용품</a></li>
						</ul>
						 -->
					</div>
				</li>
				<li><a href="/contest/contestInfo.do">대회안내</a>
					<div class="depth2">
						<ul>
							<li><a href="/contest/contestInfo.do">고객감사대회</a></li>
		                    <li><a href="/event/eventContestInfo.do">이벤트대회</a></li>
		                    <li class="tepClass"><a href="/event/kokContestInfo.do">왕중왕전대회</a></li>
		                    <li><a href="/contest/contestRst.do">대회결과</a></li>
						</ul>
					</div>
				</li>
				<li><a href="/contest/contestAppList.do">대회신청</a>
					<div class="depth2">
						<ul>
							<li><a href="/contest/contestAppList.do">고객감사대회</a></li>
		                    <!-- <li class="tepClass"><a href="/event/kokContestAppList.do">왕중왕전대회</a></li> -->
		                    <li><a href="/event/eventContestAppList.do">이벤트대회</a></li>
		                    <li class=""><a href="/event/kokContestAppList.do">왕중왕전대회</a></li>
						</ul>
					</div>
				</li>
				<li><a href="/board/noticeList.do">커뮤니티</a>
					<div class="depth2">
						<ul>
							<li><a href="/board/noticeList.do">공지사항</a></li>
							<!-- <li><a href="/board/eventList.do">이벤트</a></li> -->
							<li><a href="/board/asList.do">A/S게시판</a></li>
							<li><a href="/board/consultList.do">고객상담</a></li>
							<li><a href="/board/snsList.do">SNS</a></li>
						</ul>
					</div>
				</li>
				<li><a href="/contents/greeting.do">회사소개</a>
					<div class="depth2">
						<ul>
							<li><a href="/contents/greeting.do">인사말</a></li>
							<li><a href="/contents/history.do">연혁</a></li>
							<li><a href="/board/staffList.do">STAFF</a></li>
							<li><a href="/board/centerList.do">전국 볼링장</a></li>
							<li><a href="/contents/mapinfo.do">찾아오시는 길</a></li>
						</ul>
					</div>
				</li>
				<li><a href="javascript:void(0);">KEGEL</a>
					<div class="depth2">
						<ul>
							<li><a href="/kegel/laneMachines.do">레인정비기계</a></li>
							<li><a href="/kegel/laneConsumable.do">레인정비소모품</a></li>
							<li><a href="/kegel/trainingList.do">훈련 도구</a></li>
							<li class="shopEventBtnWrap"><a href="/board/shopEventList.do">세미나</a></li>
						</ul>
					</div>
				</li>
			</ul>
			<button class="btn_all_close" type="button">닫기</button>
		</div>
		<!--SITEMAP //E-->


		<div class="util clear">
			<div class="all_menu">
				<a href="javascript:void(0);" class="tm" title="전체메뉴"><img src="/resources/client/images/common/h_total.png" alt="전체메뉴"></a> 
				<a href="javascript:void(0);" class="m_tm" title="전체메뉴"><img src="/resources/client/images/common/h_total.png" alt="전체메뉴"></a>
			</div>
			<ul class="link_util clear">
				<c:if test="${sessionScope.mberInfo.mber_id eq null}">
					<li class="ic_login"><a href="javascript:void(0);"  onclick="goLoginPage()" title="로그인">로그인</a></li>
					<li class="join"><a href="/membership/joinStep1.do" title="회원가입">회원가입</a></li>
				</c:if>
				<c:if test="${sessionScope.mberInfo.mber_id ne null}">
					<li class="ic_logout"><a href="javascript:goLogout();">로그아웃</a></li>
					<!-- <li><a href="javascript:goMberUpdt();">회원정보수정</a></li> -->
					<li class="mpge"><a href="/membership/myContestAppList.do">마이페이지</a></li>
				</c:if>
			</ul>
			<a href="http://bowlingkoreamall.com/" target="_blank" class="link_mall">JINSEUNG MALL</a>
		</div>
		<!--UTIL //E-->

		<div class="depth2_bg"></div>
		<!-- display:none; 으로 제어-->

	</div>
	<!--#HEADER //E-->
	

	<div class="m_bg"></div>