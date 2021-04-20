<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />

<script type="text/javascript">

var limitPart = "${limitPart }";


var gender;
var disable;
var birth_y;
var birth_m;
var birth_d;
var age;

$(document).ready(function () {
	getHandi('birth_y','${birth_arr0 }');
	getHandi('birth_m','${birth_arr1 }');
	getHandi('birth_d','${birth_arr2 }');
	getHandi('gender','${sessionScope.mberInfo.mber_gender}');
	
	$("#emailSelect").change(function(){
		var strVal = $(this).val();
		if(strVal == ""){
			$("#email2").val('');
			$("#email2").removeAttr("readonly");
			$("#email2").focus();
		}else{
			$("#email2").val('');
			$("#email2").val(strVal);
			$("#email2").attr("readonly","readonly");
		}
	}); 
	
});

function viewLayer(){
	$("#infoLayer").show();	
}
function closeLayer(){
	$("#infoLayer").hide();	
}

function goList(){	
	$("#cancelFrm").attr("action", "<c:url value='/contest/contestAppView.do'/>"); //contestAppList
	$("#cancelFrm").submit();
}

function goReg(){
	
	if($('input:checkbox[id="agree"]').is(":checked") != true){
		alert("개인정보 수집 및 동의를 체크해주세요.");
		$("#agree").focus();
		return;
	}
	
	if($("#part").val() == ""){
		alert("그룹(조)을 선택하세요.");
		$("#part").focus();
		return;
	}
	if($("#join_name").val() == ""){
		alert("참가자명을 입력하세요.");
		$("#join_name").focus();
		return;
	}
	if($("#deposit_name").val() == ""){
		alert("입금자명을 입력하세요.");
		$("#deposit_name").focus();
		return;
	}
	
	var telno = "";
	
	if($("#telno1").val() == ""){
		alert("연락처를 입력하세요.");
		$("#telno1").focus();
		return;
	}
	if($("#telno2").val() == ""){
		alert("연락처를 입력하세요.");
		$("#telno2").focus();
		return;
	}
	if($("#telno3").val() == ""){
		alert("연락처를 입력하세요.");
		$("#telno3").focus();
		return;
	}
	
	telno = $("#telno1").val()+"-"+$("#telno2").val()+"-"+$("#telno3").val();
	var telnoRegExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
	if(!telnoRegExp.test(telno)){
		alert("잘못된 휴대폰 번호입니다. 각 항목에 숫자또는 자리수에 맞게 입력해주세요.");
		return;
	}

	$("#telno").val(telno);
	
	if($("#gender").val() == ""){
		alert("성별을 선택하세요.");
		$("#gender").focus();
		return;
	}
	
	var email = "";
	if($("#email1").val() == ""){
		alert("이메일를 입력하세요.");
		$("#email1").focus();
		return;
	}
	if($("#email2").val() == ""){
		alert("이메일를 입력하세요.");
		$("#email2").focus();
		return;
	}
	email = $("#email1").val()+"@"+$("#email2").val();
	$("#email").val(email);
	
	if($("#disable_yn").val() == ""){
		alert("장애여부를 선택하세요.");
		$("#disable_yn").focus();
		return;
	}
	
	var birth = "";
	var year, month, day;
	if($("#birthYear").val() == ""){
		alert("생년월일을 선택하세요.");
		$("#birthYear").focus();
		return;
	}else{
		year = $("#birthYear").val();
	}
	
	if($("#birthMonth").val() == ""){
		alert("생년월일을 선택하세요.");
		$("#birthMonth").focus();
		return;
	}else{
		month = $("#birthMonth").val();
		if(month < 10){
			month = "0"+month;
		}
	}
	
	if($("#birthDay").val() == ""){
		alert("생년월일을 선택하세요.");
		$("#birthDay").focus();
		return;
	}else{
		day = $("#birthDay").val();
		if(day < 10){
			day = "0"+day;
		}
	}
	
	birth = year+"-"+month+"-"+day;
	$("#birth").val(birth);
	 
	
	var hadi_flag = false;
	if($("#handicap").val() == ""){
		getHandi('','');
		if($("#handicap").val() == ""){
			hadi_flag = true;
		}
	}
	
	if(hadi_flag){
		$("#handicap").val("99");
		/*
		alert("핸디캡 계산이 불가능합니다.\n관리자에게 문의해주세요.");
		return;
		*/
	}
	
	 
	$.ajax({
		url		:	"<c:url value='/contest/contestAppRegAjax.do'/>",
		type	:	"post",
		async	:	false,
		data	:	$("#frm").serialize(),
		beforeSend : function(){
			$(".loading_layer_wrap").removeAttr("style"); 
		},
		success	:	function(data){
			console.log(data);
			if(data.root.ResultCode == "Y"){
				alert("접수(대기자포함)가 완료되었습니다.\n결과 확인은 접수 마감 후 신청결과에서 확인 가능합니다.");
			}else if(data.root.ResultCode == "E"){
				alert("현재 접속자가 너무 많습니다\n다시 시도해주세요.");
			}else if(data.root.ResultCode == "O"){
				alert("신청하신 조의 정원이 초과되었습니다.");
			}else if(data.root.ResultCode == "D"){
				alert("이미 본 대회 또는 다른대회에 접수하였습니다.");
			}else if(data.root.ResultCode == "T"){
				alert("접수 신청 기간이 아닙니다.");
			}else if(data.root.ResultCode == "F"){
				alert("접수가 마감되었습니다.");
			}else if(data.root.ResultCode == "S"){
				alert("올바른 접속이 아닙니다.");
			}else{
				alert("현재 접속자가 너무 많습니다.\n다시 시도해주세요");
			}
			$(".loading_layer_wrap").css("display","none");
			location.href="<c:url value='/contest/contestAppList.do'/>";
		},
		error	:	 function(err){
			alert("현재 접속자가 너무 많습니다.\n다시 시도해주세요.");
			$(".loading_layer_wrap").css("display","none"); 
			console.log(err);
			location.href="<c:url value='/contest/contestAppList.do'/>";
		}
	})
	
}

//핸디점수 자동계산
function getHandi(type, value){
	var handicap = 0;
	
	if(type == 'gender'){
		gender = value;	
	}
	if(type == 'disable'){
		disable = value;	
	}
	if(type == 'birth_y'){
		birth_y = value;	
	}
	if(type == 'birth_m'){
		if(value<10){
			value = "0"+value;
		}
		birth_m = value;	
	}
	if(type == 'birth_d'){
		if(value<10){
			value = "0"+value;
		}
		birth_d = value;	
	}

	if(gender != null && disable != null && birth_y != null && birth_m != null && birth_d != null){
		
		//age = (year_change(birth_y+"-"+birth_m+"-"+birth_d)+1);
		age = birth_handiCal(birth_y);
		
		//여성10점 ==> 12점
		if(gender == 'F'){
			handicap += 12;
		}
		//장애 8점
		if(disable == 'Y'){
			handicap += 8;
		}
		
		//56세부터 1점추가 최고 10점 ==> 55세부터 1점 추가 최고 10점
		console.log(age);
		if(age >= 55){
			var ageScore = age - 54;
			if(gender == 'F'){
				if(ageScore > 10){
					handicap += 10;	
				}else{
					handicap += ageScore;
				}			
			}else{
				if(ageScore > 20){
					handicap += 20;	
				}else{
					handicap += ageScore;
				}			
			}
		}		
	}else{
		return;
	}
	
	//합산 20점이상일경우 20점
	if(handicap > 20){
		handicap = 20;
	}
	
	$("#handiScore").text(handicap+"점");
	$("#handicap").val(handicap);
}

function birth_handiCal(year){
	 var now_year = new Date().getFullYear();
	 var age_cal = (eval(now_year) - eval(year));
	 return age_cal;
}


function year_change(birth){
	
  var birthday = new Date(birth);
  var age_year = birthday.getFullYear();
  var age_month = (birthday.getMonth());
  var age_day = birthday.getDate();  
  
  var date = new Date();
  var today_year = date.getFullYear();
  var today_month = (date.getMonth() + 1);
  var today_day = date.getDate();     
  
 
  var birth = new Date(age_year, age_month, age_day);
  var today_orgin = new Date(today_year, today_month, today_day);
  
  var time = today_orgin.getTime() - birth.getTime();
  var sec =  parseFloat( time / 1000).toFixed(2);
  var min = parseFloat(sec /60).toFixed(2);
  var hour = parseFloat(min /60).toFixed(2);
  var day = parseFloat(hour / 24).toFixed(2);
  var year = Math.floor(day / 365);//소수점 버림.
  
  var temp_birth = new Date(today_year, age_month, age_day);
  var temp_today_orgin = new Date(today_year, today_month, today_day);
  
  var temp_remain = temp_birth - temp_today_orgin;
  var remain_day = Math.floor(temp_remain/1000/60/60/24);

  return year;

}

function chkLimitPart(val){
	/* 
	var ct_seq = $("#ct_seq").val();
	
	$.ajax({
		type : "POST",
		url : "/contest/chkLimitPart.do",
		data : {
				ct_seq : 	ct_seq,
				part   :	val
		},
		cache : false,
		dataType : 'json',
		success : function(cnt){
			if(cnt < limitPart){
				$("#status").val('0001');
			}else{
				alert("해당조는 정원이 초과되어 대기자로 접수됩니다.");
				$("#status").val('0004');
			}						
		},
		error : function(data, status, err) {
			alert(status);
			return;
		}
	});
	
	 */
}
</script>


<div class="loading_layer_wrap" style="display: none;">
	<div class="loading_layer">
		<img src="/resources/client/images/common/loading_icon.gif" alt="loading" />
		<p>로딩 중입니다. 잠시만 기다려주세요.</p>
	</div>
</div>

<form id="cancelFrm">
	<input type="hidden" name="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form>

	<div id="container" class="subpage">
		<div id="contents" >
            <h2 class="hide">본문</h2>
            <c:import url="/client/snb.do" />       
             <div class="sub_content">
             	<div class="inner">
                	<div class="sub_head">
	                    <h3 class="c_tit">대회신청</h3>
                    </div>
					<p class="view_area_tit"><c:out value='${contestView.ct_sbj }'/></p>
					<p style="text-align: right;">
    					<span class="ft_or">* </span>
   						<span >는 필수입력 항목입니다.</span>
					</p>
                    <div class="board_area sub_fade">
                       	<form:form commandName="vo" id="frm" name="frm" method="post" enctype="multipart/form-data">                      	
						<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
						<input type="hidden" name="limit_part" id="limit_part" value="<c:out value='${contestView.limit_part }'/>" />
						<input type="hidden" name="status" id="status" value="" />
                        <div class="bbs_write">
                            <div class="tbl_w">
                                <table summary="게시판 글쓰기 표로 제목, 구분, 내용,첨부파일 등이 있습니다.">
                                    <caption>게시판 작성</caption>
                                    <colgroup>
                                        <col width="15%">
                                        <col width="35%">
                                        <col width="15%">
                                        <col width="35%">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th scope="col"><span class="ft_or">* </span><label for="part">그룹(조)</label></th>
                                            <td colspan="3">
                                            	<select id="part" name="part" onchange="chkLimitPart(this.value);"  class="wd50p" >
                                                    <option value="">::선택::</option>
                                                    <option value="1">1조</option>
                                                    <option value="2">2조</option>
                                                    <option value="3">3조</option>
                                                    <option value="4">4조</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                       	    <th scope="col"><label for="join_name">참가자명</label></th>
                                            <td>
                                            	<input type="text" name="join_name" id="join_name"  value="${sessionScope.mberInfo.mber_name }" readonly="readonly" class="wd60p"/>                                          	
                                            </td>
                                             <th scope="col"><label for="deposit_name">입금자명</label></th>
                                            <td>
                                            	<input type="text" name="deposit_name" id="deposit_name"  value="${sessionScope.mberInfo.mber_name }" readonly="readonly" class="wd60p"/>                                            	
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="col"><label for="telno">참가자 연락처</label></th>
                                            <td>
                                            	<input type="text" name="telno1" id="telno1" maxlength="3" pattern="\d*" placeholder="" value="${tel_arr0 }"  class="wd30p mb03" />
                                            	<input type="text" name="telno2" id="telno2" maxlength="4" pattern="\d*" placeholder="" value="${tel_arr1 }"  class="wd30p mb03"/>
                                            	<input type="text" name="telno3" id="telno3" maxlength="4" pattern="\d*" placeholder="" value="${tel_arr2 }"  class="wd30p"/>
                                            	<input type="hidden" name="telno" id="telno"/>  
                                            </td>
                                            <th scope="col"><span class="ft_or">* </span><label for="gender">성별</label></th>
                                            <td>
												<select id="gender" name="gender" onchange="getHandi('gender',this.value);" class="wd40p">
													<option value="">::선택::</option>
													<option value="M" <c:if test="${sessionScope.mberInfo.mber_gender eq 'M' }">selected="selected"</c:if>>남</option>
													<option value="F" <c:if test="${sessionScope.mberInfo.mber_gender eq 'F' }">selected="selected"</c:if>>여</option>
												</select>
                                            </td>
                                        </tr>
                                         <tr>
                                            <th scope="col"><label for="email">이메일</label></th>
                                            <td class="w_email">
                                            <input type="text" name="email1" id="email1"  maxlength="40" value="${email_arr0 }"  class="wd30p"/><span class="insert">@</span>
                                            <input type="text" name="email2" id="email2"  value="${email_arr1 }"  class="wd30p mb03"/>                                                                                       	
                                               	<select id="emailSelect" >
                                                    <option value="">::직접입력::</option>
                                                    <option value="naver.com" <c:if test="${email_arr1 eq 'naver.com'}">selected="selected"</c:if>>naver.com</option>
                                                    <option value="nate.com" <c:if test="${email_arr1 eq 'nate.com'}">selected="selected"</c:if>>nate.com</option>
                                                    <option value="hanmail.net" <c:if test="${email_arr1 eq 'hanmail.net'}">selected="selected"</c:if>>hanmail.net</option>
                                                    <option value="gmail.com" <c:if test="${email_arr1 eq 'gmail.com'}">selected="selected"</c:if>>gmail.com</option>
                                                    <option value="hotmail.com" <c:if test="${email_arr1 eq 'hotmail.com'}">selected="selected"</c:if>>hotmail.com</option>
                                                    <option value="daum.net" <c:if test="${email_arr1 eq 'daum.net'}">selected="selected"</c:if>>daum.net</option>
                                                    <option value="korea.com" <c:if test="${email_arr1 eq 'korea.com'}">selected="selected"</c:if>>korea.com</option>
                                                </select>
                                                <input type="hidden" name="email" id="email"/> 
                                            </td>
                                            <th scope="col"><span class="ft_or">* </span><label for="disable_yn">장애여부</label></th>
                                            <td>
                                            	<select id="disable_yn" name="disable_yn" onchange="getHandi('disable',this.value);" class="wd40p">
                                            		<option value="">::선택::</option>
													<option value="N">해당없음</option>
													<option value="Y">장애3급이상</option>
												</select>
                                            </td>
                                        </tr>
                                        <tr>
                                        	<th scope="col"><label for="birth">생년월일</label></th>
                                            <td>
                                            	
                                            	<input type="text" id="bir" title="생년월일" value="${birth_arr0 }년 ${birth_arr1 }월 ${birth_arr2 }일" readonly="readonly" class="wd60p">
                                            	<input type="hidden" id="birthYear" title="생년" value="${birth_arr0 }">
                                            	<input type="hidden" id="birthMonth" title="월" value="${birth_arr1 }">
                                            	<input type="hidden" id="birthDay" title="일" value="${birth_arr2 }">
                                            	<%-- 
                                            	<select id="birthYear" title="생년" onchange="getHandi('birth_y',this.value);">
	                                                <option value="" >::::선택::::</option>
				                                    <%
															for(int i = 2018; i >= 1900; i--){
													%>
																<option value="<%=i%>" <c:if test="${birth_arr0 == i }">selected="selected"</c:if>><%=i%></option>
													<%	
															}
													%>
				                                </select> 년
				                                <select id="birthMonth" title="월" onchange="getHandi('birth_m',this.value);">
				                                    <option value="" >::::선택::::</option>
				                                    <%
															for(int i = 12; i >= 1; i--){															
													%>
																<option value="<%=i%>" <c:if test="${birth_arr1 == i }">selected="selected"</c:if>><%=i%></option>
													<%	
															}
													%>
				                                </select> 월
				                                <select id="birthDay" title="일" onchange="getHandi('birth_d',this.value);">
				                                    <option value="">::::선택::::</option>
				                                    <%
															for(int i = 31; i >= 1; i--){
													%>
																<option value="<%=i%>" <c:if test="${birth_arr2 == i }">selected="selected"</c:if>><%=i%></option>
													<%	
															}
													%>
				                                </select> 일										 
				                                --%>
                                            	<input type="hidden" id="birth" name="birth" />      
                                            </td>
                                            <th scope="col"><label for="handicap">핸디</label></th>
                                            <td>
                                            	<span id="handiScore"></span>
                                            	<input type="hidden" name="handicap" id="handicap" />                                                                                        	
                                            </td>
                                        </tr>                                                                              
                                    </tbody>
                                </table>
                            </div>
                            <div class="agree" style="display: none;">
                                <p><input type="checkbox" id="agree" checked="checked"><label for="agree">개인정보 수집 및 정보제공동의 </label> <a href="javscript:viewLayer();" id="pop_txt">전문보기</a></p>
                            </div>
                        </div>
                        </form:form>
                        <!--BOARD_WRITE //E -->

                        <div class="btn_r2">
                            <a href="javascript:goReg();" class="">등록</a>
                            <a href="javascript:goList();" class="gray">취소</a>
                        </div>
                    </div>
                    <!--BOARD_AREA //E-->
                        
                    <div id="infoLayer" class="layer_pop4" style="display:none;"><!-- 전문보기 클릭시 레이어 팝업 생성 -->
                    	<div class="layer_wrap">
                        	<div class="layer_head">개인정보 수집 및 정보제공동의</div>
                            	<div class="layer_con">
                                   <div class="txt_pop">
                                       <div class="agreeTxt">
                                	       <p>(주)진승무역(이하 “진승”이라고 함)은 회원의 개인정보를 중요시하며, "정보통신망 이용촉진 및 정보보호"에 관한 법률을 준수하고 있습니다. 진승은 개인정보취급방침을 통하여 회원님께서 제공하시는 개인정보가 어떠한 용도와 방식으로 이용되고 있으며, 개인정보보호를 위해 어떠한 조치가 취해지고 있는지 알려드립니다. </p>
                                           <dl>
                                               <dt>1. 개인정보의 수집 항목 및 수집 방법 </dt>
                                               <dd>
                                                <ul>
                                                    <li>1) 진승은 회원가입, 각종 서비스의 제공을 위해 최초 회원가입 당시 아래와 같은 개인정보를 수집하고 있습니다. <br/>
 														- 수집항목 : 생년월일, 성명, 아이디, 비밀번호, 주소, 전화번호, 휴대전화 번호, 이메일</li>
                                                    <li>2) 서비스 이용과정에서 아래와 같은 정보들이 자동으로 생성되어 수집될 수 있습니다.<br/> 
													    - 최근접속일, 접속 IP 정보, 쿠키, 구매로그, 이벤트로그 </li>
                                                    <li>3) 개인정보의 수집 방법 <br/>
 														- 홈페이지, 서면양식, 팩스, 전화, 이메일, 배송요청 </li>
                                                    <li>4) 개인정보 수집에 대한 동의 <br/>
  														- 진승은 회원이 개인정보취급방침 및 이용약관의 내용에 대해 「동의한다」버튼 또는 「동의하지 않는다」버튼을 클릭할 수 있는 절차를 마련하여, 「동의한다」버튼을 클릭하면 개인정보 수집에 대해 동의한 것으로 봅니다. </li>
                                                    <li>5) 비회원의 개인정보보호 <br/>
  														- 진승은 비회원 주문의 경우에도 배송, 결제, 주문내역 조회 및 구매확인, 실명여부 확인을 위하여 필요한 개인정보만을 요청하고 있으며, 이 경우 그 정보는 결제 및 상품의 배송에 관련된 용도 이외에는 다른 어떠한 용도로도 사용되지 않습니다. <br/>
  														- 진승은 비회원의 개인정보도 회원과 동등한 수준으로 보호합니다.</li>
                                                </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                               <dt>2. 개인정보의 수집목적 및 이용목적 </dt>
                                               <dd>
                                                <ul>
                                                    <li>진승은 다음과 같은 목적을 위하여 개인정보를 수집하고 있습니다. </li>
                                                    <li>1) 회원제 서비스 이용에 따른 본인 확인 절차에 이용 </li>
													<li>2) 고지사항 전달, 본인 의사 확인, 불만 처리 등 원활한 의사소통 경로의 확보, 새로운 서비스, 신상품이나 이벤트 정보 등 최신 정보의 안 </li>
													<li>3) 물품 주문 및 접수, 대금결제, 배송에 대한 정확한 배송지의 확보 </li>
                                                </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                               <dt>3. 개인정보의 공유 및 제공 </dt>
                                               <dd>
                                                <ul>
                                                    <li>진승은 회원의 개인정보를 2. 개인정보의 수집목적 및 이용목적> 에서 고지한 범위 내에서 사용하며, 동 범위를 초과하여 이용하거나 타인 또는 타기업/기관에 제공하지 않습니다. </li>
                                                    <li>2) 단, 다음은 예외로 합니다. <br/>
 														- 관계법령에 의하여 수사상의 목적으로 관계기관으로부터의 요구가 있을 경우 <br/>
														 - 이용자들이 사전에 동의한 경우 <br/>
														 - 그러나 예외사항에서도 관계법령에 의하거나 수사기관의 요청에 의해 정보를 제공한 경우에는 이를 당사자에게 고지하는 것을 원칙으로 운영하고 있습니다. 법률상의 근거에의해 부득이하게 고지를 하지 못할 수도 있습니다. 본래의 수집목적 및 이용목적에 반하 여 무분별하게 정보가 제공되지 않도록 최대한 노력하겠습니다. </li>
                                                </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                               <dt>4. 개인정보의 취급위탁 </dt>
                                               <dd>
                                                <ul>
                                                    <li>진승은 서비스 향상을 위해서 아래와 같이 개인정보를 위탁하고 있으며, 관계 법령에 따라 위탁계약시 개인정보가 안전하게 관리될 수 있도록 필요한 사항을 규정하고 있습니다. <br/>
														진승의 개인정보 위탁처리 기관 및 위탁업무 내용은 아래와 같습니다. </li>
                                                    <li>
                                                        <div class="tbl_con">
                                                        <table>
                                                            <caption>개인정보의 취급위탁</caption>
                                                            <thead>
                                                                <tr>
                                                                    <th scope="row">수탁자</th>
                                                                    <th scope="row">위탁범위</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td>한국모바일인증(주)</td>
                                                                    <td>본인확인</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>로젠택배</td>
                                                                    <td>상품배송업무</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>올더게이트</td>
                                                                    <td>전자결제 대행</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>SMS16</td>
                                                                    <td>SMS 발송</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                        </div>
                                                    </li>
                                                </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                                <dt>5. 개인정보의 보유 및 이용기간 </dt>
                                                   <dd>
                                                    <ul>
                                                        <li>1) 회원의 개인정보는 회사가 신청인에게 서비스를 제공하는 기간 동안에 한하여 보유하고 이를 활용합니다. 다만 다른 법률에 특별한 규정이 있는 경우에는 관계법령에 따라 보관합니다.<br/> 
														 - 회원가입정보 : 회원가입을 탈퇴하거나 회원에 제명된 때 <br/> 
														 - 대금지급정보 : 대금의 완제일 또는 채권소명시효기간이 만료된 때 <br/> 
														 - 배송정보 : 물품 또는 서비스가 인도되거나 제공된 때 <br/> 
														 - 설문조사, 이벤트 등 일시적 목적을 위하여 수집한 경우 : 당해 설문조사, 이벤트 등이 종료한 때 </li>
														 <li>2) 위 개인정보 수집목적 달성시 즉시파기 원칙에도 불구하고 다음과 같이 거래 관련 권리의무 관계의 확인 등을 이유로 일정기간 보유하여야 할 필요가 있을 경우에는 전자상거래 등에서의 소비자보호에 관한 법률 등에 근거하여 일정기간 보유합니다. <br/> 
														 - 계약 또는 청약철회 등에 관한 기록 : 5년 <br/> 
														 - 대금결제 및 재화 등의 공급에 관한 기록 : 5년 <br/> 
														 - 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년 <br/> 
														 - 설문조사, 이벤트 등 일시적 목적을 위하여 수집한 경우 : 당해 설문조사, 이벤트 등의 종료 시점 </li>
														 <li>3) 회원의 동의를 받아 보유하고 있는 거래정보 등을 회원이 열람을 요구하는 경우 진승은 지체 없이 열람, 확인 할 수 있도록 조치합니다. </li>
                                                       
                                                   </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                                <dt>6. 개인정보의 파기절차 및 방법 </dt>
                                                   <dd>
                                                    <ul>
                                                        <li>진승은 원칙적으로 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다. 파기절차 및 방법은 다음과 같습니다. </li>
                                                        <li>1) 파기절차 <br/>
 															- 회원이 회원가입 등을 위해 입력하신 정보는 목적이 달성된 후 내부 방침 및 기타 관련법령에 의한 정보보호 사유에 따라 &lt;3. 개인정보의 보유, 이용기간 참조&gt; 일정 기간 저장된 후 파기되어집니다. 동 개인정보는 법률에 의한 경우가 아니고서는 보유되어지는 이외의 다른 목적으로 이용되지 않습니다. </li>
                                                        <li>2) 파기방법 <br/>
															 - 종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다. 전자적 파일형태로 저장된 개인정보는 현재의 기술적, 경제적 수준에서 가능한 범위 내에서 재생할수 없는 방법을 이용하여 삭제합니다. 
														</li>
                                                   </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                                <dt>7. 개인정보 보호를 위한 기술 및 관리적 대책 </dt>
                                                   <dd>
                                                    <ul>
                                                        <li>* 기술적 대책 <br/>
															진승은 회원의 개인정보를 취급함에 있어 개인정보가 분실, 도난, 누출, 변도 또는 훼손되지 않도록 안정성 확보를 위하여 다음과 같은 기술적 대책을 강구하고 있습니다. 
														</li>
                                                        <li>1) 개인정보의 암호화 </li>
														<li>2) 백신 프로그램을 이용한 컴퓨터 바이러스 피해 방지장치 </li>
														<li>3) 채널보안방식인 SSL보안 서버 시스템 </li>
														<li>4) 개인정보 취급자를 최소 인원으로 제한 지정 </li>
                                                        <li>* 관리적 대책 <br/>1) 진승은 회원의 개인정보에 대한 접근권한을 최소한의 인원으로 제한하고 있습니다. 그 최소한의 인원에 해당하는 자는 다음과 같습니다. <br/>
															 - 이용자를 직접 상대로 하여 마케팅 업무를 수행하는 자<br/> 
															 - 개인정보보호책임자 및 담당자 등 개인정보관리업무를 수행하는 자 <br/>
															 - 기타 업무상 개인정보의 취급이 불가피한 자 </li>
                                                   </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                                <dt>8. 링크 사이트 </dt>
                                                   <dd>진승은 회원님께 다른 회사의 웹사이트 또는 자료에 대한 링크를 제공할 수 있습니다. 이 경우 진승은 외부사이트 및 자료에 대한 아무런 통제권이 없으므로 그로부터 제공받는 서비스나 자료의 유용성에 대해 책임질 수 없으며 보증할 수 없습니다. 진승이 포함하고 있는 링크를 클릭(Click)하여 타 사이트(Site)의 페이지로 옮겨갈 경우 해당 사이트의 개인정보처리방침은 진승과 무관하므로 새로 방문한 사이트의 정책을 검토해 보시기 바랍니다. 
 												</dd>
                                           </dl>
                                           <dl>
                                                <dt>9. 이용자의 권리와 의무 </dt>
                                                   <dd>
                                                    <ul>
                                                        <li>1) 회원은 개인정보를 최신의 상태로 정확하게 입력하여 불의의 사고를 예방해 주시기 바랍니다. 회원이 입력한 부정확한 정보로 인해 발생하는 사고의 책임은 이용자 자신에게 있으며 타인 정보의 도용 등 허위정보를 입력할 경우 회원 자격이 상실될 수 있습니 다. </li>
                                                        <li>2) 회원은 개인정보를 보호받을 권리와 함께 스스로를 보호하고 타인의 정보를 침해하지 않을 의무도 가지고 있습니다. 비밀번호를 포함한 회원의 개인정보가 유출되지 않도록 조심 하시고 게시물을 포함한 타인의 개인정보를 훼손하지 않도록 유의해 주십시오. 만 약 이 같은 책임을 다하지 못하고 타인의 정보 및 존엄성을 훼손할 시에는 ‘정보통신망 이용 촉진및정보보호등에관한법률’ 등에 의해 처벌받을 수 있습니다. </li>
                                                        <li>3) 온라인상에서(게시판, E-mail등) 자발적으로 제공하는 개인정보는 다른 사람들이 수집하여 사용할 수 있음을 항상 유념하시기 바랍니다. 즉, 공개적으로 접속할 수 있는 온라인상에서 개인정보를 게재하는 경우, 다른 사람들로부터 원치 않는 메시지를 답장으로 받 게 될 수도 있음을 의미합니다. </li>
                                                        <li>4) 공공장소에서 이용할 때에는 자신의 비밀번호가 노출되지 않도록 하고 서비스 이용을 마친 후에는 반드시 로그아웃을 해주시기 바랍니다. </li>
                                                   </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                                <dt>10. 이용자 및 법정 대리인의 권리와 그 행사방법</dt>
                                                   <dd>
                                                    <ul>
                                                        <li>1) 회원은 언제든지 등록되어 있는 자신의 개인정보를 조회하거나 수정할 수 있으며 가입해지를 요청할 수도 있습니다. </li>
                                                        <li>2) 회원의 개인정보 조회, 수정 또는 가입해지를 위해서는 「마이페이지」버튼을 클릭하여 자신의 개인정보를 직접 열람, 정정 또는 탈퇴가 가능합니다. 혹은 개인정보관리책임자에게 전화 또는 이메일로 연락하시면 지체 없이 조치하겠습니다. </li>
                                                   </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                                <dt>11. 개인정보 자동 수집 장치의 설치, 운영 및 그 거부에 대한 사항</dt>
                                                   <dd>
                                                    <ul>
                                                        <li>1) 쿠키(cookie)란? <br/>
															진승은 회원에 대한 정보를 저장하고 수시로 찾아내는 쿠키(cookie)를 사용합니다. 쿠키는	사이트가 회원의 컴퓨터 브라우저로 전송하는 소량의 정보입니다. 회원님께서 웹사이트에 접속을 하면 진승의 서버는 회원의 브라우저에 추가정보를 임시로 저장하여 접속에 따른 성명 등의 추가 입력 없이 진승의 서비스를 제공할 수 있습니다. 쿠키는 회원의 컴퓨터는 식별하지만 회원을 개인적으로 식별하지는 않습니다. 또한 회원은 쿠키에 대한 선택권이 있습니다. </li>
                                                        <li>2) 진승의 쿠키 운용 <br/>
															진승은 이용자의 편의를 위하여 쿠키를 운영합니다. 진승이 쿠키를 통해 수집하는 정보는 회원ID에 한하며, 그 외의 다른 정보는 수집하지 않습니다. 진승이 쿠키를 통해 수집한 회원 ID는 다음의 목적을 위해 사용됩니다. <br/>
															 - 개인의 관심 분야에 따란 차별화된 정보를 제공 <br/>
															 - 쇼핑한 품목들에 대한 정보와 장바구니 서비스를 제공 <br/>
															 - 회원과 비회원의 접속빈도 또는 머문 시간 등을 분석하여 서비스 및 마케팅에 활용 </li>
                                                        <li>3) 쿠키는 브라우저의 종료시나 로그아웃 시 만료됩니다. </li>
                                                        <li>4) 쿠키의 설치 및 거부<br/>
															 - 회원은 쿠키 설치에 대한 선택권을 가지고 있습니다. 따라서 회원은 웹브라우저에서 옵션을 설정함으로써 모든 쿠키를 허용하거나, 쿠키가 저장될 때마다 확인을 거치거나, 아니면 모든 쿠키의 저장을 거부할 수도 있습니다.<br/> 
															 - 다만, 쿠키의 저장을 거부할 경우에는 로그인이 필요한 일부 서비스는 이용에 어려움이 있을 수 있습니다. <br/>
															 - 쿠키 설치 허용 여부를 지정하는 방법 <br/>
															* Internet Explorer의 경우 : [도구] 메뉴에서 [인터넷 옵션]을 선택 → [개인정보]를 클릭 → [고급]을 클릭 → 쿠키 허용여부를 선택 <br/>
															* Safari의 경우 : MacOS 상단 좌측 메뉴바에서 [Safari]에서 [환경설정]을 선택 → [환경설정] 창에서 [보안]으로 이동하여 쿠키 허용여부 선택 </li>
                                                   </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                                <dt>12. 개인정보 보호문의처 및 담당자</dt>
                                                   <dd>
                                                    <ul>
                                                        <li>1) 진승은 회원의 개인정보를 보호하고 개인정보와 관련된 불만 등을 처리하기 위하여 고객센터 및 개인정보 관리책임자를 두고 있습니다. 회원의 개인정보와 관련한 문의사항은 아래의 고객센터 또는 개인정보관리 담당자에게 연락하여 주시기 바랍니다. <br/>
															<strong>고객센터</strong><br/>
															 - 전화번호 : 031-769-8433 <br/>
															 - E-mail : admin@bowlingkorea.com<br/>
															<strong>개인 정보 관리 담당자</strong><br/>
															 - 성명 : 황 석 / 소속 : 영업팀 / 직책 : 과장 <br/>
															 - E-mail : hsuk76@bowlingkorea.com</li>
                                                        <li>2) 기타 개인정보침해에 대한 신고나 상담이 필요하신 경우에는 아래 기관에 문의하시기 바랍니다.<br/> 
															 - 개인분쟁조정위원회 (www.1336.or.kr/1336) <br/>
															 - 정보보호마크인증위원회 (www.eprivacy.or.kr/02-580-0533~4)<br/> 
															 - 대검찰청 인터넷범죄수사센터 (http://icic.sppo.go.kr/02-3480-3600) <br/>
															 - 경찰청 사이버테러대응센터 (www.ctrc.go.kr / 02-392-0330) </li>
                                                   </ul>
                                               </dd>
                                           </dl>
                                           <dl>
                                                <dt>13. 고지의 의무 </dt>
                                                   <dd>
                                                    <ul>
                                                        <li>법령, 정책 또는 보안기술의 변경에 따라 내용의 추가, 삭제 및 수정이 있을 시에는 변경사항 시행일의7일 전부터 홈페이지의 '공지'란을 통해 고지 할 것입니다.<br/> - 개인정보처리방침 버전번호 : v2.0 <br/> 
														 - 시행 일자 : 2013년 10월 11일 <br/> 
														 - 공고 일자 : 2013년 10월 04일  </li>
                                                   </ul>
                                               </dd>
                                           </dl>
                                       </div>
                                   </div>
                               </div>
                               <a href="javascript:closeLayer();" class="popClose">닫기</a>
                           </div>
                       </div>
                       <!--LAYER_POP : E-->

                </div>
            </div>   
 			
		</div>
		<!-- #CONTENTS //E -->
		
	</div> 
	<!-- #CONTAINER //E -->

<jsp:include page="/client/footer.do"></jsp:include>
