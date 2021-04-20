<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
$(document).ready(function(){
	if (document.location.protocol == 'http:') {
        //document.location.href = document.location.href.replace('http:', 'https:');
    }
	$('.log-info').hover(function(){
        	$(this).find('.adInfoBoxDd').stop(true,true).slideDown(200);
        }, function(){
        	$(this).find('.adInfoBoxDd').stop(true,true).slideUp(200);
    });	
	if ("${isLogin}" != "true") {
		alert("요청하신 페이지는 관리자만 접근하실 수 있는 페이지 입니다.\r\n 로그인을 한지 오래되었거나 권한이 부족합니다.");
		location.href="<c:url value='/apage.do'/>";
		return;
	}
});


function getQuerystring(paramName){
	var _tempUrl = window.location.search.substring(1); //url에서 처음부터 '?'까지 삭제 
	var _tempArray = _tempUrl.split('&'); // '&'을 기준으로 분리하기
	for(var i = 0; i<_tempArray.length; i++) {
		var _keyValuePair = _tempArray[i].split('='); // '=' 을 기준으로 분리하기 
		if(_keyValuePair[0] == paramName){ // _keyValuePair[0] : 파라미터 명 // _keyValuePair[1] : 파라미터 값 
			return _keyValuePair[1]; 
		}
	}
}

</script>
</head>
<body>
<div id="wrap">
	<div id="header">
		<h1><img src="<c:url value='/resources/apage/images/common/logo.jpg'/>" alt="로고" width="150" /> <span>진승무역 홈페이지 관리자 시스템</span></h1>
        <ul class="log-info">
            <li><span><c:out value='${memberinfo.mber_name }'/>(<c:out value='${memberinfo.mber_id }'/>)님 환영합니다.</span></li>
            <li><a href="<c:url value='/apage/adminLogout.do'/>">로그아웃</a></li>
        </ul>
        <div id="nav">
            <ul>
                <c:if test="${memberinfo.auth_code eq 'super' || memberinfo.auth_code eq 'admin'}">
	                <li><a href="<c:url value='/apage/contest/adminContestList.do'/>">대회관리</a></li>
	                <li><a href="<c:url value='/apage/event/kokContestList.do'/>">이벤트관리</a></li>
                </c:if>
                <c:if test="${memberinfo.auth_code eq 'super' || memberinfo.auth_code eq 'board'}">
	                <li><a href="<c:url value='/apage/board/adminNoticeList.do'/>">게시판관리</a></li>
                </c:if>
                <li><a href="<c:url value='/apage/member/adminMemberList.do'/>">회원관리</a></li>
                <c:if test="${memberinfo.auth_code eq 'super' }">
	                <li><a href="<c:url value='/apage/system/adminContent.do'/>">시스템관리</a></li>
	                <li><a href="<c:url value='/apage/member/smsList.do'/>">문자발송관리</a></li>
	                <%-- <li><a href="<c:url value='/apage/log/logList.do'/>">로그분석</a></li> --%>
                </c:if>
            </ul>
        </div>
     </div>