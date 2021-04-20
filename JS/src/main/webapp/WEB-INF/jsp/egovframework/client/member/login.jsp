<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<style type="text/css">
#checkFrame { position: absolute; z-index: 15; display: none; left:50%; top:10%; margin-left:-199px; overflow:hidden;  width: 398px; height: 600px; padding:0 !important; box-sizing:border-box; border:1px solid #666; overflow: scroll;}
#checkFrame p { text-align: right; background:#000 !important;   }
#checkFrame p a { font-size: 13px; color:#fff; line-height:30px; height:30px; width:100%; padding-right:10px; }
#checkFrame iframe { border:none; width:100%;   }
	
@media only screen and (max-width:640px){
#checkFrame{ width: 96%; left:2%; margin-left:0; height: 180%;  }
}
</style>
<% 
    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
    
    String sSiteCode = "BN547";			// NICE로부터 부여받은 사이트 코드
    String sSitePassword = "aVrmqOfKvVvP";		// NICE로부터 부여받은 사이트 패스워드
    
    String sRequestNumber = "REQ0000000001";        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 
                                                    	// 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
    sRequestNumber = niceCheck.getRequestNO(sSiteCode);
  	session.setAttribute("REQ_SEQ" , sRequestNumber);	// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.
  	
   	String sAuthType = "";      	// 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
   	
   	String popgubun 	= "N";		//Y : 취소버튼 있음 / N : 취소버튼 없음
	String customize 	= "";		//없으면 기본 웹페이지 / Mobile : 모바일페이지
	
	String sGender = ""; 			//없으면 기본 선택 값, 0 : 여자, 1 : 남자 
	
    // CheckPlus(인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
	//리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
		
	String sReturnUrl = "https://bowlingkorea.com/checkplus/di_success.jsp";     		 // 성공시 이동될 URL
    String sErrorUrl = "https://bowlingkorea.com/checkplus/di_fail.jsp";          // 실패시 이동될 URL
	//String sReturnUrl = "http://125.7.222.194:8880/checkplus/di_success.jsp";     		 // 성공시 이동될 URL
    //String sErrorUrl = "http://125.7.222.194:8880/checkplus/di_fail.jsp";          // 실패시 이동될 URL
	//String sReturnUrl = "http://localhost:8080/checkplus/di_success.jsp";     		 // 성공시 이동될 URL
    //String sErrorUrl = "http://localhost:8080/checkplus/di_fail.jsp";          // 실패시 이동될 URL

    // 입력될 plain 데이타를 만든다.
    String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                        "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                        "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize + 
						"6:GENDER" + sGender.getBytes().length + ":" + sGender;
    
    String sMessage = "";
    String sEncData = "";
    
    int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
    if( iReturn == 0 ) {
        sEncData = niceCheck.getCipherData();
    } else if( iReturn == -1) {
        sMessage = "암호화 시스템 에러입니다.";
    } else if( iReturn == -2) {
        sMessage = "암호화 처리오류입니다.";
    } else if( iReturn == -3) {
        sMessage = "암호화 데이터 오류입니다.";
    } else if( iReturn == -9) {
        sMessage = "입력 데이터 오류입니다.";
    } else {
        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
    }
    
%>


<c:if test="${!empty msg }" >
	<script type="text/javascript">
	var msg	= '${msg}'; 
	if(msg != ''){
		//var Form = document.frm;
		if(msg.indexOf('fail') >= 0){
			alert('아이디 또는 패스워드를 확인하세요.');
			$("#frm").attr("action", "<c:url value='/membership/login.do'/>");
			$("#frm").submit();
		}else{
			alert(msg);
			location.href='/main.do';
		}
	}
	</script>
</c:if>

<script type="text/javascript">
var isLogin = false;
var loginId = "${sessionScope.mberInfo.mber_id }";
if(loginId != ''){
		location.href = '/main.do';
}
var reject_cnt = 0;
$(document).ready(function () {
		
	$('#mber_id').focus();
	
	$("#mber_id, #mber_pw").keydown(function(key) {
		if (key.keyCode == 13) {
				goLogin();
		}
	});

	$("input[name=di_yn]").on("change",function(){
		if($(this).val() == "Y"){
			di_certification();
		}else{
			if(eval(reject_cnt) == 2){
				di_reject('last');
			}else{
				di_reject('');
			}
		}
	})
	
	
	loginBxResizing();

	
});

function di_certification(){
	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
	document.form_chk.target = "popupChk";
	document.form_chk.submit();
}

/* 
function di_certification(){
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
	document.form_chk.target = "checkIfm";
	document.form_chk.submit();
	$("#checkFrame").css("display","block");
}

 */
function go_diNext(){
	var returnUrl = $("#returnUrl").val();
	
	var f = document.cform;
	checkClose();
	
	var overlapFlag = false;
	if($("#auth").val() == "true") {
		$.ajax({
			url			:	"/membership/diOverLapCheck.do",
			type		:	"post",
			data		:	$("#cform").serialize(),
			async		:	false,
			success		:	function(data){
				if(data.root.resultCode == "N"){		//중복아님
					overlapFlag = true;
				}else if(data.root.resultCode == "Y"){	//중복
					alert("동일한 명의로 가입한 회원이 있습니다.\n해당 명의로는 인증 등록이 불가능합니다.\n(아이디/비밀번호 찾기 서비스는 회원 정보찾기로 이용 가능)");
				}else{
					alert("실패하였습니다.\n다음 로그인시 재시도 해주세요.");
				}
			},
			error		:	function(err){
				alert("실패하였습니다.\n다음 로그인시 재시도 해주세요.");
			}
		});
		
	}else {
		alert("본인 인증에 실패하였습니다.");
	}
	
	if(overlapFlag){
		$.ajax({
			url			:	"/membership/diUpdate.do",
			type		:	"post",
			data		:	$("#cform").serialize(),
			async		:	false,
			success		:	function(data){
				if(data.root.resultCode == "Y"){
					alert("휴대폰 인증 등록이 완료 되었습니다.");
				}else{
					alert("실패하였습니다.\n다음 로그인시 재시도 해주세요.");
				}
			},
			error		:	function(err){
				alert("실패하였습니다.\n다음 로그인시 재시도 해주세요.");
			}
		});
	}else{
		$.ajax({
			url			:	"/membership/rejectLastCnt.do",
			type		:	"post",
			data		:	$("#cform").serialize(),
			async		:	false,
			success		:	function(data){
				if(data.root.resultCode == "Y"){
					alert("다음 로그인시 인증을 하실 수 있습니다.");
				}
			},
		});
	}

	if(returnUrl != null && returnUrl != ""){
		//ct_seq != null && ct_seq != "" &&
		if(returnUrl == "contestAppWrite"){
			location.href = "/contest/contestAppList.do";
		}else if(returnUrl == "asList"){
			location.href = "/board/asList.do";
		}else if(returnUrl == "consultList"){
			location.href = "/board/consultList.do";
		}else if(returnUrl == "shopEventList"){
			location.href = "/board/shopEventList.do";
		}else{
			location.href = "/main.do";
		}
	}else{
		location.href = "/main.do";
	}
	
	
}

function checkClose(){
	//$("iframe[name=checkIfm]").attr("src","");
	$("#checkFrame").css("display","none");
}


function di_reject(type){
	var returnUrl = $("#returnUrl").val();
	
	var di_flag = false;

	if(type == 'last'){
		if(confirm("지금 인증을 안 하실 경우 다시 인증을 하실 수 없습니다.\n정말 인증을 안 하시겠습니까?")){
			di_flag = true;
		}
	}else{
		if(confirm("정말 인증을 안 하시겠습니까?")){
			di_flag = true;
		}
	}
	
	if(di_flag){
		$.ajax({
			url		:	"/membership/diReject.do",
			type	:	"post",
		    async	:	false,
			success	:	function(data){
				if(returnUrl != null && returnUrl != ""){
					//ct_seq != null && ct_seq != "" &&
					if(returnUrl == "contestAppWrite"){
						location.href = "/contest/contestAppList.do";
					}else if(returnUrl == "asList"){
						location.href = "/board/asList.do";
					}else if(returnUrl == "consultList"){
						location.href = "/board/consultList.do";
					}else if(returnUrl == "shopEventList"){
						location.href = "/board/shopEventList.do";
					}else{
						location.href = "/main.do";
					}
				}else{
					location.href = "/main.do";
				}
			},
			error	:	function(err){
				console.log(err);
				location.href = "/main.do";
			}
		})
	}

}

$(window).resize(function(){
	loginBxResizing();
}).resize();


function loginBxResizing(){

	var logBx_top		= $(".loginBx").offset().top;
	var logBx_padding_x = eval($(".loginBx").css("padding-left").replace("px","")) + eval($(".loginBx").css("padding-right").replace("px",""));
	var logBx_padding_y = eval($(".loginBx").css("padding-top").replace("px","")) + eval($(".loginBx").css("padding-bottom").replace("px",""));
	
	var logBx_bottom	= $(window).height() - logBx_top - $(".loginBx").height(); logBx_bottom = logBx_bottom - logBx_top;
	var logBx_left		= $(".loginBx").offset().left;
	var logBx_right		= $(".loginBx").offset().right;
	
	var logBx_width		= (eval($(".loginBx").width())	+ eval(logBx_padding_x)) + "px";
	var logBx_height	= (eval($(".loginBx").height()) + eval(logBx_padding_y)) + "px";

	//var logBx_height	= $(".loginBx").height();
	//alert("logBx_height ==> " + logBx_height);
	
	$(".agree_text").css("height", $(".loginBx").height()+"px");
	console.log($(".loginBx").height()+"px");
	
//	console.log("logBx_top ==> " + logBx_top + "/ logBx_bottom ==> " + logBx_bottom +" / logBx_left ==> " + logBx_left + " / logBx_right ==> " + logBx_right + " / logBx_width ==> " + logBx_width + " / logBx_height ==> " + logBx_height);
	
	$("#pop_layer").css({
		 top	:	logBx_top
		,left	:	logBx_left
		,width	:	logBx_width
		,height	:	logBx_height
	});

}

$(document).on("click",".chkApp",function() {
	if($(this).is(":checked")==true){
		$(this).parent().parent().css("background-color","#f8f8f8");
	}else{
		$(this).parent().parent().css("background-color","");
	}
});


function goPopupClose(){
	if(eval(reject_cnt) == 2){
		di_reject('last');
	}else{
		di_reject('');
	}
}

function goLogin(){
	
	if($("#mber_id").val()==""){
		alert("아이디를 입력하세요.");
		$("#mber_id").focus();
		return;
	}
	
	if($("#mber_pw").val()==""){
		alert("비밀번호를 입력하세요.");
		$("#mber_pw").focus();
		return;
	}
	
	
	//var ct_seq = $("#ct_seq").val();
	var id = $("#mber_id").val();
	var pwd = $("#mber_pw").val();
	var returnUrl = $("#returnUrl").val();
	
	if($("input:checkbox[id=autoLogin]").is(":checked")){
		$("input[name=autoLogin]").val("Y");
	}else{
		$("input[name=autoLogin]").val("N");
	}
	
	$.ajax({
		type : "POST",
		url : "/membership/loginJson.do",
		data : {
			mber_id		:	$("#mber_id").val(),
			mber_pw		:	$("#mber_pw").val(),
			autoLogin	:	$("input[name=autoLogin]").val()
		},
		cache : false,
		dataType : 'json',
		beforeSend : function(){
			$(".loading_layer_wrap").removeAttr("style"); 
		},
		success : function(data) {
			if (data.root.resultCode == "Y") {
				if($("input:checkbox[id='save']").is(":checked")){
					$.cookie("js_record_id",$("#mber_id").val(),{expires: 365, path:'/'});
				}else{
					var record_id= $.cookie("js_record_id");
					if(record_id!=null && record_id!=""){
						if($("#mber_id").val()==record_id){
								//alert("cookie_id eq input_id");
							//$.removeCookie("gfta_record_id");
							$.cookie("js_record_id" , null);
						}
					}
				};
				if($("input:checkbox[id=autoLogin]").is(":checked")){
					if(data.root.result_token != ""){
						storage.updateStorage("Y");
						storage.updateToken(data.root.result_token);
					}
				}else{
					storage.setAutoLoginStorage();
				}
				
				$(".loading_layer_wrap").css("display","none");
				//인증 필요상태
				reject_cnt = data.root.rejection_cnt;
				if(data.root.di_yn == 'N' && data.root.rejection_cnt < 3){
					$("#pop_layer").css("display","block");
					$(".c_tit").html("인증");
				}else{
					if(returnUrl != null && returnUrl != ""){
						//ct_seq != null && ct_seq != "" &&
						if(returnUrl == "contestAppWrite"){
							location.href = "/contest/contestAppList.do";
						}else if(returnUrl == "asList"){
							location.href = "/board/asList.do";
						}else if(returnUrl == "consultList"){
							location.href = "/board/consultList.do";
						}else if(returnUrl == "eventContestAppWrite"){
							location.href = "/event/eventContestAppList.do";
						}else if(returnUrl == "kokContestAppWrite"){
							location.href = "/event/kokContestAppList.do";
						}else if(returnUrl == "shopEventList"){
							location.href = "/board/shopEventList.do";
						}else{
							location.href = "/main.do";
						}
					}else{
						location.href = "/main.do";
					}
				}
				
			}else {
				alert("아이디 또는 패스워드를 확인하세요.");
				location.href="<c:url value='/membership/login.do'/>";
				return;
			}
		},
		error	:	 function(err){
			alert("오류가 발생하였습니다.\n잠시후 시도해주세요.");
			$(".loading_layer_wrap").css("display","none");
			location.href="<c:url value='/membership/login.do'/>";
		}
	});
}


//아이디저장 쿠키
window.onload=function(){
	var record_id=$.cookie("js_record_id");
	if(record_id!="null" && record_id!="" && record_id!=undefined && record_id!=null){
		$("input:checkbox[id='save']").prop("checked", true);
		$("#mber_id").val(record_id);
	}
}

function notCertificate(){
	if(confirm("'다시 열지 않겠습니다.'를 선택하여 팝업을 닫을 경우 다시 인증을 진행하실 수 없습니다.\n그래도 닫으시겠습니까?")){
		$.ajax({
			url		:	"/membership/notCertificateUpdate.do",
			cache 	:	false,
			async	:	false,
			success	:	function(data){
				if(data.root.resultCode == "Y"){
					alert("다음 로그인부터 인증 팝업은 노출되지 않습니다.\n");
				}else{
					alert("오류가 발생하였습니다.\n잠시 후 다시 시도해주세요.");
				}
			},
			error	:	function(err){
				alert("오류가 발생하였습니다.\n잠시 후 다시 시도해주세요.");
			}
		})
		
		var returnUrl = $("#returnUrl").val();
		if(returnUrl != null && returnUrl != ""){
			//ct_seq != null && ct_seq != "" &&
			if(returnUrl == "contestAppWrite"){
				location.href = "/contest/contestAppList.do";
			}else if(returnUrl == "asList"){
				location.href = "/board/asList.do";
			}else if(returnUrl == "consultList"){
				location.href = "/board/consultList.do";
			}else{
				location.href = "/main.do";
			}
		}else{
			location.href = "/main.do";
		}
	}else{
		 $("input:checkbox[id='notCertificate']").attr("checked", false);
	}
}

</script>

<form name="form_chk" method="post" >
	<input type="hidden" name="m" value="checkplusSerivce" />			<!-- 필수 데이타로, 누락하시면 안됩니다. -->
	<input type="hidden" name="EncodeData" value="<%= sEncData %>">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
</form>

<form id="cform" name="cform" method="post">
	<input type="hidden" name="mber_name" id="mber_name" value="" />
	<input type="hidden" name="reg_code" id="reg_code" value="" />
	<input type="hidden" name="birth_year" id="birth_year" value="" />
	<input type="hidden" name="birth_month" id="birth_month" value="" />
	<input type="hidden" name="birth_day" id="birth_day" value="" />
	<input type="hidden" name="mber_gender" id="mber_gender" value="" />
	<input type="hidden" name="auth" id="auth" value="" />
</form>

<div id="pop_layer" style="position: absolute; z-index: 100; display: none;">
	<div id="pop_layer_topbar" style="height: 30px; background: #036;">
		<p class="close" style="position: absolute;top: 5px;right: 10px;width: 16px;height: 16px;border: 1px solid #fff;text-align: center;">
			<a href="javascript:goPopupClose()" style="display: block;color: #fff;">X</a>
		</p>
	</div>
	<div style="background: white;">
		<div id="member" style="margin-bottom: 0px;">
			<div class="basicBox" style="margin-bottom: 0px;">
				<!-- 인증 -->
				<div id="checkFrame">
					<p><a href="javascript:void(0)" onclick="checkClose()">X</a></p>
					<!-- <iframe name="checkIfm" src="https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb" style="width: 100%; height: 100%;"></iframe> -->
				</div>
		
				<div class="agree_wrap">
					<div class="agree_text" style="max-height: 300px !important;">
                        <strong>휴대폰 인증 등록안내</strong> <br><br>
                        휴대폰 인증으로 아이디/비밀번호 찾기 서비스를 사용하려면 최초 1번 인증절차가 필요합니다. <br><br>
						휴대폰 인증 등록을 하지 않으면 아이디/비밀번호 찾기 이용 시 휴대폰 인증으로 찾기가 불가하며 회원정보로 찾기만 가능합니다. <br><br>
                         * 휴대폰 인증 후 해당 명의가 등록되어 있으면 중복으로 등록이 불가 합니다.<br><br>
                         - 휴대폰 인증 등록이 안되더라도 아이디/비밀번호 찾기 서비스 회원정보로 찾기가 가능합니다.<br><br>
                         - 기타 다른 서비스 이용에는 지장이 없습니다.<br><br>
                         - 인증 3번 이상 거부 시 다음 로그인부터 인증 창은 뜨지 않습니다.<br>
                    </div>
                    <div class="" style="text-align: center;">
                        <p>휴대폰 인증 등록을 하시겠습니까?</p><br/>
                        <p> 
                        	<input type="radio" id="di_yn_y" name="di_yn" class="type-radio required" value="Y">
                       		<label for="di_yn_y">예</label>
                        	<input type="radio" id="di_yn_n" name="di_yn" class="type-radio required" value="N">
                        	<label for="di_yn_n">아니요</label>
						</p><br/>
                    </div>
                </div>
                <div id="pop_layer_bootombar" style="height: 30px; background: #036;">
                	<p class="close" style="bottom: 0px;text-align: left;padding: 5px;">
                		<input type='checkbox' style='display: inline-block;' onchange='notCertificate();' id="notCertificate"><span style='color: white;'><label for="notCertificate">다시 열지 않겠습니다.</label></span>
                	</p>
               	</div>
                
           </div>
		</div>
	</div>
</div>

<div class="loading_layer_wrap" style="display: none;">
	<div class="loading_layer">
		<img src="/resources/client/images/common/loading_icon.gif" alt="loading" />
		<p>로그인중 입니다. 잠시만 기다려주세요.</p>
	</div>
</div>


<form id="diFrm" method="post">
	<input type="hidden" name="di_yn">
	<input type="hidden" name="returnUrl" value="<c:out value='${returnUrl }'/>" />
</form>
<form:form commandName="vo" id="frm" name="frm">
	<input type="hidden" name="mber_id" id="mber_id_sub" value="" />
	<input type="hidden" name="mber_pw" id="mber_pw_sub" value="" />
	
	<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${ct_seq }'/>" />
	<input type="hidden" name="returnUrl" id="returnUrl" value="<c:out value='${returnUrl }'/>" />
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" name="autoLogin"  value="N"/>
</form:form>

	<div id="container" class="subpage">
		<div id="contents" class="s_con">
                <h2 class="hide">본문</h2>
                <c:import url="/client/snb.do" />  
                
                <div class="sub_content">
                    <div class="inner">
                        <div class="sub_head">
                            <h3 class="c_tit">로그인</h3>
                        </div>
                        <div class="board_area sub_fade">
                            <div class="login_wrap">
                                <div class="loginBx">
                                    <div class="loginImg">Welcome to<br/><strong> JINSEUNG TRADING CO.LTD</strong></div>
                                    <div class="loginWrite">
                                        <div class="login">
                                            <p>
	                                            <label for="id">아이디</label>
	                                            <input type="text" id="mber_id" name="mber_id" tabindex="1" maxlength="20"/>
                                            </p>
                                            <p>
                                            	<label for="password">비밀번호</label>
                                            	<input type="password" id="mber_pw" name="mber_pw" tabindex="2" />
                                            </p>
                                        </div>
                                        <div class="btn">
                                            <a href="javascript:goLogin();" id="loginBtn">로그인</a>
                                        </div>
                                        <div class="idsave">
                                            <p>
                                            	<input type="checkbox" id="save" tabindex="3" ><label for="save">아이디 저장</label>
                                            	<c:if test="${mobileFlag == 'Y' }">
                                            		<input type="checkbox" id="autoLogin"><label for="autoLogin">자동로그인</label>
                                            	</c:if>
                                            </p>
                                            <div class="memeberlink">
                                                <span><a href="/membership/joinStep1.do">회원가입</a></span>
                                                <span><a href="/membership/idFind.do">아이디 찾기</a></span>
                                                <span><a href="/membership/pwFind.do">비밀번호 찾기</a></span>
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                            <!--LOGIN //E -->
                             <div class="litype1">
                                <p>하나의 아이디로 진승의<span class="ft_or"> 모든 회원제 사이트를 이용</span>하실 수 있습니다.</p>
                                <p>회원정보가 바뀐 경우에는 한 번만 수정하시면 진승 패밀리 사이트의 정보가 자동으로 변경됩니다. </p>
                            </div> 
                        </div>
                        <!--BOARD_AREA //E-->
                   </div><!--inner-->
                </div>
            </div>
            <!-- #CONTENTS //E -->
	</div> 
	<!-- #CONTAINER //E -->
<jsp:include page="/client/footer.do"></jsp:include>