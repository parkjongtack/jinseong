<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>진승무역 홈페이지 관리자로그인</title>
<link rel="stylesheet" type="text/css" href="/resources/apage/css/reset.css" />
<link rel="stylesheet" type="text/css" href="/resources/apage/css/common.css" />
<link href="/resources/client/images/common/jin_ico.ico" rel="icon" type="image/x-icon">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/apage/css/jquery-ui.min.css"/>
<script type="text/javascript" src="/resources/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/jquery.tmpl.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	if (document.location.protocol == 'http:') {
        document.location.href = document.location.href.replace('http:', 'https:');
    }

 	var isLogin = "${isLogin}";
	
	if(isLogin == "true"){
		$(location).attr("href", "<c:url value='/apage/index.do'/>");
	}
	prev_url = document.referrer; 
});

function doLogin() {
	
	if ($("#mber_id").val() == "" || $("#mber_id").val() == null) {
		alert("아이디는 필수 입력 항목입니다.");
		return;
	}

	if ($("#mber_pw").val() == "" || $("#mber_pw").val() == null) {
		alert("패스워드는 필수 입력 항목입니다.");
		return;
	}

	$.ajax({
		type : "POST",
		url : "<c:url value='/apage/adminLoginJson.do'/>",
		data : {
			mber_id : $("#mber_id").val(),
			mber_pw : $("#mber_pw").val()
		},
		cache : false,
		dataType : 'json',
		success : function(data) {
			//alert(data);
			if (data) {
				location.href = "<c:url value='/apage/index.do'/>";
			} else {
				alert("아이디 또는 패스워드를 확인하세요.");
				return;
			}

		},
		error : function(data, status, err) {
			alert("로그인에 실패하였습니다.");
			return;
		}
	});
}
</script>
</head>
<body class="pg-log">
    <div class="log-panel">
        <h1><img src="<c:url value='/resources/apage/images/common/logo_b.jpg'/>" alt="로고" />
        <span>진승무역 홈페이지<br/>관리자 시스템입니다. </span>
        </h1>
        <p class="desc">관리자로부터 부여 된 아이디 / 비밀번호를 입력하세요. </p>
        <form:form commandName="vo" name="frm" id="frm" method="post" onSubmit="return doLogin();">
        <fieldset class="log-frm">
            <legend>관리자 로그인</legend>
            <p><label for="id"><form:input path="mber_id" title="아이디 입력"/></label></p>
            <p><label for="pwd"><form:password path="mber_pw" title="비밀번호 입력" onkeypress="if(event.keyCode==13) {doLogin(); return false;}"/></label></p>
            <input type="button" value="SIGN IN" onclick="doLogin();" />
        </fieldset>
        </form:form>
        <p class="log-foot">COPYRIGHT © 진승무역 2018. ALL RIGHTS RESERVED.</p>
    </div>
    <!-- //log-panel -->
</body>
</html>