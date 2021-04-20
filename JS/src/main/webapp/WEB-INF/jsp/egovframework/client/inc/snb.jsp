<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style type="text/css">
.tepClass{display: none;}
.shopEventBtnWrap{ display: none;}
</style>
<script type="text/javascript">

$(document).ready(function () {
	
	$(".dp1").click(function(){
		
		if($("#dp1_depth2").hasClass("on")){			
			$("#dp1_depth2").removeClass("on");
			$("#dp1_depth2").hide();
		}else{
			$("#dp1_depth2").addClass("on");
			$("#dp1_depth2").show();	
		}
	});
	
	$(".dp2").click(function(){
		
		if($("#dp2_depth2").hasClass("on")){			
			$("#dp2_depth2").removeClass("on");
			$("#dp2_depth2").hide();
		}else{
			$("#dp2_depth2").addClass("on");
			$("#dp2_depth2").show();	
		}
	});
	
	$(".dp3").click(function(){
		
		if($("#dp3_depth3").hasClass("on")){			
			$("#dp3_depth3").removeClass("on");
			$("#dp3_depth3").hide();
		}else{
			$("#dp3_depth3").addClass("on");
			$("#dp3_depth3").show();	
		}
	});
});

function moveShopList(d1, d2, d3){
	$("#menuDepth1").val(d1);
	$("#menuDepth2").val(d2);
	$("#menuDepth3").val(d3);
	$("#shopForm").attr("action","/shop/shopList.do");
	$("#shopForm").submit();
}
</script>

<div class="loc_wrap">
    <div class="loc_cont">
        <span class="home"><a href="/main.do">home</a></span>
         <div class="link dp1">         
             <p>
	             <c:if test="${menuDepth1 eq 1 }">제품소개</c:if>
	             <c:if test="${menuDepth1 eq 2 }">대회안내</c:if>
	             <c:if test="${menuDepth1 eq 7 }">대회신청</c:if>
	             <c:if test="${menuDepth1 eq 3 }">커뮤니티</c:if>
	             <c:if test="${menuDepth1 eq 4 }">회사소개</c:if>
	             <c:if test="${menuDepth1 eq 5 }">KEGEL</c:if>
	             <c:if test="${menuDepth1 eq 6 }">마이페이지</c:if>             	
             </p>
             <div class="ct" id="dp1_depth2">
                 <ul>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="">제품소개</a></li>
                     <li><a href="/contest/contestInfo.do">대회안내</a></li>
                     <li><a href="/board/noticeList.do">커뮤니티</a></li>
                     <li><a href="/contents/greeting.do">회사소개</a></li>
                     <li><a href="/kegel/laneMachines.do">KEGEL</a></li>
                 </ul>
             </div>
         </div>
         <div class="link dp2">
             <p>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 1}">볼링볼</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 2}">볼링백</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3}">볼링의류</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4}">볼링아대</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 5}">볼링슈즈</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 6}">볼링 악세사리</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 7}">프로샵 용품</c:if>
             	           	 
             	<c:if test="${menuDepth1 eq 2 && menuDepth2 eq 1}">고객감사대회</c:if>
             	<c:if test="${menuDepth1 eq 2 && menuDepth2 eq 5}">이벤트대회</c:if>
             	<c:if test="${menuDepth1 eq 2 && menuDepth2 eq 4}">왕중왕전대회</c:if>
             	<c:if test="${menuDepth1 eq 2 && menuDepth2 eq 3}">대회결과</c:if>
             	                  	
             	<c:if test="${menuDepth1 eq 3 && menuDepth2 eq 1}">공지사항</c:if>
             	<%-- <c:if test="${menuDepth1 eq 3 && menuDepth2 eq 2}">이벤트</c:if> --%>
             	<c:if test="${menuDepth1 eq 3 && menuDepth2 eq 3}">A/S게시판</c:if>
             	<c:if test="${menuDepth1 eq 3 && menuDepth2 eq 4}">고객상담</c:if>
             	<c:if test="${menuDepth1 eq 3 && menuDepth2 eq 5}">SNS</c:if>
             	              	           
             	<c:if test="${menuDepth1 eq 4 && menuDepth2 eq 1}">인사말</c:if>
             	<c:if test="${menuDepth1 eq 4 && menuDepth2 eq 2}">연혁</c:if>
             	<c:if test="${menuDepth1 eq 4 && menuDepth2 eq 3}">STAFF</c:if>
             	<c:if test="${menuDepth1 eq 4 && menuDepth2 eq 4}">전국 볼링장</c:if>
             	<c:if test="${menuDepth1 eq 4 && menuDepth2 eq 5}">찾아오시는 길</c:if>
             	             	              	
             	<c:if test="${menuDepth1 eq 5 && menuDepth2 eq 1}">레인정비기계</c:if>
             	<c:if test="${menuDepth1 eq 5 && menuDepth2 eq 2}">레인정비소모품</c:if>
             	<c:if test="${menuDepth1 eq 5 && menuDepth2 eq 3}">훈련 도구</c:if>
				<c:if test="${menuDepth1 eq 5 && menuDepth2 eq 4}">세미나</c:if>
             	   
             	<c:if test="${menuDepth1 eq 6 && menuDepth2 eq 1}">로그인</c:if>
             	<c:if test="${menuDepth1 eq 6 && menuDepth2 eq 2}">회원가입</c:if>
             	<c:if test="${menuDepth1 eq 6 && menuDepth2 eq 3}">회원정보수정</c:if>
             	<c:if test="${menuDepth1 eq 6 && menuDepth2 eq 4}">아이디 찾기</c:if>
             	<c:if test="${menuDepth1 eq 6 && menuDepth2 eq 7}">비밀번호 찾기</c:if>
             	<c:if test="${menuDepth1 eq 6 && menuDepth2 eq 8}">대회신청내역</c:if>
             	<c:if test="${menuDepth1 eq 6 && menuDepth2 eq 10}">회원탈퇴</c:if>
             	<c:if test="${menuDepth1 eq 6 && menuDepth2 eq 5}">개인정보취급방침</c:if>
             	<c:if test="${menuDepth1 eq 6 && menuDepth2 eq 6}">이용약관</c:if>
             	
             	     	           	 
               	<c:if test="${menuDepth1 eq 7 && menuDepth2 eq 1}">고객감사대회</c:if>
             	<c:if test="${menuDepth1 eq 7 && menuDepth2 eq 3}">이벤트대회</c:if>
             	<c:if test="${menuDepth1 eq 7 && menuDepth2 eq 2}">왕중왕전대회</c:if>
             	
             </p>
             <div class="ct" id="dp2_depth2">
                 <ul>
                 	<c:if test="${menuDepth1 eq 1 }">
	                    <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="">볼링볼</a></li>
	                    <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="2" data-d3="1" data-b="1002" data-m="1030" data-s="">볼링백</a></li>
	                    <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="4" data-b="1003" data-m="1096" data-s="">볼링의류</a></li>
	                    <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="4" data-d3="1" data-b="1004" data-m="1044" data-s="">볼링아대</a></li>
	                    <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="5" data-d3="1" data-b="1006" data-m="1079" data-s="">볼링슈즈</a></li>
	                    <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="6" data-d3="1" data-b="1005" data-m="1047" data-s="">볼링 악세사리</a></li>
	                    <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="7" data-d3="1" data-b="1102" data-m="0" data-s="">프로샵 용품</a></li>
	                    <!-- <li><a href="javascript:moveShopList('1','7','');">프로샵 용품</a></li> -->
                    </c:if>
                    <c:if test="${menuDepth1 eq 2 }">
	                    <li><a href="/contest/contestInfo.do">고객감사대회</a></li>
	                    <li><a href="/event/eventContestInfo.do">이벤트대회</a></li>
	                    <li class="tepClass"><a href="/event/kokContestInfo.do">왕중왕전대회</a></li>
	                    <li><a href="/contest/contestRst.do">대회결과</a></li>
                    </c:if>
                    <c:if test="${menuDepth1 eq 3 }">
	                    <li><a href="/board/noticeList.do">공지사항</a></li>
	                    <!-- <li><a href="/board/eventList.do">이벤트</a></li> -->
	                    <li><a href="/board/asList.do">A/S게시판</a></li>
	                    <li><a href="/board/consultList.do">고객상담</a></li>
	                    <li><a href="/board/snsList.do">SNS</a></li>
                    </c:if>
                    <c:if test="${menuDepth1 eq 4 }">
	                    <li><a href="/contents/greeting.do">인사말</a></li>
	                    <li><a href="/contents/history.do">연혁</a></li>
	                    <li><a href="/board/staffList.do">STAFF</a></li>
	                    <li><a href="/board/centerList.do">전국 볼링장</a></li>
	                    <li><a href="/contents/mapinfo.do">찾아오시는길</a></li>	                    
                    </c:if>
                    <c:if test="${menuDepth1 eq 5 }">
	                    <li><a href="/kegel/laneMachines.do">레인정비기계</a></li>
	                    <li><a href="/kegel/laneConsumable.do">레인정비소모품</a></li>
	                    <li><a href="/kegel/trainingList.do">훈련 도구</a></li>	       
						<li class="shopEventBtnWrap"><a href="/board/shopEventList.do">세미나</a></li>            
                    </c:if>
                    <c:if test="${menuDepth1 eq 6 }">
	                    <c:if test="${sessionScope.mberInfo.mber_id eq null}" >
		                    <li><a href="/membership/login.do">로그인</a></li>
		                    <li><a href="/membership/joinStep1.do">회원가입</a></li>
		                    <li><a href="/membership/idFind.do">아이디 찾기</a></li>
		                    <li><a href="/membership/pwFind.do">비밀번호 찾기</a></li>
	                    </c:if>
	                    <c:if test="${sessionScope.mberInfo.mber_id ne null}" >
	                    	<li><a href="/membership/myContestAppList.do">대회신청내역</a></li>
	                    	<!-- <li><a href="javascript:goMberUpdt();">회원정보수정</a></li> -->
	                    	<li><a href="/membership/myPwdCheckPage.do">회원정보수정</a></li>
	                    	<li><a href="/membership/memberLeavePage.do">회원탈퇴</a></li>
	                    </c:if>	                  	
	                  	<li><a href="/contents/privacy.do">개인정보취급방침</a></li>
	                  	<li><a href="/contents/rule.do">이용약관</a></li>	                  	
                    </c:if>
                        <c:if test="${menuDepth1 eq 7 }">
	                    <li><a href="/contest/contestAppList.do">고객감사대회</a></li>
	                    <li class=""><a href="/event/eventContestAppList.do">이벤트대회</a></li>
	                    <!-- <li class="tepClass"><a href="/event/kokContestAppList.do">왕중왕전대회</a></li> -->
	                    <li class=""><a href="/event/kokContestAppList.do">왕중왕전대회</a></li>
                    </c:if>
                 </ul>
             </div>
         </div>
         <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 1}">
         <div class="link dp3">
             <p>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 1 && menuDepth3 eq 1}">Storm</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 1 && menuDepth3 eq 2}">Roto Grip</c:if>
             </p>
             <div class="ct" id="dp3_depth3">
                 <ul>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="">Storm</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="1" data-d3="2" data-b="1001" data-m="1013" data-s="">Roto Grip</a></li>
                 </ul>
             </div>
         </div>
         </c:if>
         
         <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 2}">
         <div class="link dp3">
             <p>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 2 && menuDepth3 eq 1}">Storm</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 2 && menuDepth3 eq 2}">Roto Grip</c:if>
             </p>
             <div class="ct" id="dp3_depth3">
                 <ul>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="2" data-d3="1" data-b="1002" data-m="1030" data-s="">Storm</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="2" data-d3="2" data-b="1002" data-m="1031" data-s="">Roto Grip</a></li>
                 </ul>
             </div>
         </div>
         </c:if>
                      
		<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3}">
         <div class="link dp3">
             <p>
<%--              
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 1}">Storm Nation</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 2}">Roto Grip</c:if>
 --%>             	
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 4}">후드티셔츠</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 1}">전사티셔츠</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 5}">하의</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 3}">Special Edition</c:if>
             </p>
             <div class="ct" id="dp3_depth3">
                 <ul>
                 <!-- 
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="1" data-b="1003" data-m="1093" data-s="1094">Storm Nation</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="2" data-b="1003" data-m="1093" data-s="1095">Roto Grip</a></li>
                  -->    
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="4" data-b="1003" data-m="1096" data-s="">후드티셔츠</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="1" data-b="1003" data-m="1093" data-s="">전사티셔츠</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="5" data-b="1003" data-m="1103" data-s="">하의</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="3" data-d3="3" data-b="1003" data-m="1043" data-s="">Special Edition</a></li>
                 </ul>
             </div>
         </div>
         </c:if>     
         
         <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4}">
         <div class="link dp3">
             <p>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4 && menuDepth3 eq 1}">Storm</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4 && menuDepth3 eq 2}">Gizmo</c:if>             	
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4 && menuDepth3 eq 3}">아대 악세사리</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4 && menuDepth3 eq 4}">테이핑 웨어</c:if>
             </p>
             <div class="ct" id="dp3_depth3">
                 <ul>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="4" data-d3="1" data-b="1004" data-m="1044" data-s="">Storm</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="4" data-d3="2" data-b="1004" data-m="1045" data-s="">Gizmo</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="4" data-d3="3" data-b="1004" data-m="1046" data-s="">아대 악세사리</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="4" data-d3="4" data-b="1004" data-m="1104" data-s="">테이핑 웨어</a></li>
                 </ul>
             </div>
         </div>
         </c:if>       
         
         <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 5}">
         <div class="link dp3">
             <p>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 5 && menuDepth3 eq 1}">Roto Grip</c:if>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 5 && menuDepth3 eq 2}">Storm</c:if>             	
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 5 && menuDepth3 eq 3}">슈즈 악세사리</c:if>
             </p>
             <div class="ct" id="dp3_depth3">
                 <ul>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="5" data-d3="1" data-b="1006" data-m="1079" data-s="">Roto Grip</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="5" data-d3="2" data-b="1006" data-m="1080" data-s="">Storm</a></li>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="5" data-d3="3" data-b="1006" data-m="1082" data-s="">슈즈 악세사리</a></li>
                 </ul>
             </div>
         </div>
         </c:if>      
         
         <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 6}">
         <div class="link dp3">
             <p>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 6 && menuDepth3 eq 1}">악세사리</c:if>             	
             </p>
             <div class="ct" id="dp3_depth3">
                 <ul>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="6" data-d3="1" data-b="1006" data-m="1047" data-s="">악세사리</a></li>                     
                 </ul>
             </div>
         </div>
         </c:if> 
                   
          <%-- 
         <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 7}">
         <div class="link dp3">
             <p>
             	<c:if test="${menuDepth1 eq 1 && menuDepth2 eq 7 && menuDepth3 eq 1}">프로샵 용품</c:if>             	
             </p>
             <div class="ct" id="dp3_depth3">
                 <ul>
                     <li><a href="javascript:void(0);" class="moveShop" data-d1="1" data-d2="7" data-d3="1" data-b="1102" data-m="" data-s="">ALL</a></li>                     
                 </ul>
             </div>
         </div>
         </c:if>   
          --%>        
    </div>
</div>

