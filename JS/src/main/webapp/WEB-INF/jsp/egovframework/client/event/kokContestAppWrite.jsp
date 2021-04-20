<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />

<style type="text/css">
#addrSearchBtn{
	display: inline-block;
    background: #545454;
    color: #fff;
    padding: 0px 10px;
    font-size: 13px;
    height: 36px;
    line-height: 36px;
}

#zipcode{
	width: 15%;
}

#addr, #addr_detail{
	margin-bottom: 5px;
}
</style>

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
		
		//여성10점 ==> 12점 ==> 10점 왕중왕전
		if(gender == 'F'){
			handicap += 10;
		}
		//장애 8점
		if(disable == 'Y'){
			handicap += 8;
		}
		
		//56세부터 1점추가 최고 10점 ==> 55세부터 1점 추가 최고 10점
		console.log(age);
/* 
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
*/	
		
		if(gender == 'F'){
			var ageScore = age - 54;
			if(age >= 55){
				if(ageScore > 10){
					handicap += 10;	
				}else{
					handicap += ageScore;
				}	
			}
		}else {
			var ageScore = age - 56;
			if(age >= 57){
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


function goList(){	
	$("#cancelFrm").attr("action", "<c:url value='/event/kokContestAppView.do'/>"); //contestAppList
	$("#cancelFrm").submit();
}

function goReg(){
	var $ct_type = $("#frm input[name=ct_type]").val();

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
	 
	
	if($("#disable_yn").val() == ""){
		alert("장애여부를 선택하세요.");
		$("#disable_yn").focus();
		return;
	}
	
	
	if($("#option1").val() == ""){
		alert("티셔츠 사이즈를 선택하세요.");
		$("#option1").focus();
		return;
	}
	
	
	
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
		alert("오류가 발생하였습니다.\n핸디캡 계산이 불가능합니다.\n다시 시도해주세요.");
		return;
		 */
	}
	
	

	try {
	 
		$.ajax({
			url		:	"<c:url value='/event/eventContestAppRegAjax.do'/>",
			type	:	"post",
			async	:	false,
			data	:	$("#frm").serialize(),
			beforeSend : function(){
				$(".loading_layer_wrap").removeAttr("style"); 
			},
			success	:	function(data){
				if(data.root.ResultCode == "Y"){
					alert("접수가 완료되었습니다.\n결과 확인은 접수 마감 후 신청결과에서 확인 가능합니다.");
				}else if(data.root.ResultCode == "E"){
					alert("오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
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
				}else if(data.root.ResultCode == "C"){
					alert("로그인한 신청자 정보가 대회 참가자 명단에 존재하지 않습니다.");
				}else{
					alert("알 수 없는 오류가 발생하였습니다.");
				}
				$(".loading_layer_wrap").css("display","none");
				location.href="<c:url value='/event/kokContestAppList.do'/>";
			},
			error	:	 function(err){
				alert("오류가 발생하였습니다.\n잠시후 시도해주세요.");
				$(".loading_layer_wrap").css("display","none"); 
				location.href="<c:url value='/event/kokContestAppList.do'/>";
			}
		})
	
	}catch (e) {
	   // statements to handle any exceptions
		console.log(e); // pass exception object to error handler
		alert("알 수 없는 오류가 발생하였습니다.");
		$(".loading_layer_wrap").css("display","none");
		location.href="<c:url value='/event/kokContestAppList.do'/>";
	}
	
}



//주소 팝업
function addr_pop() {
	// 주소검색을 수행할 팝업 페이지를 호출
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출
	var pop = window.open("${contextPath}/address/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}

//팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
	$("#zipcode").val(zipNo);
	$("#addr").val(roadAddrPart1);
	$("#addr_detail").val(addrDetail);	
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
	                    <h3 class="c_tit">왕중왕전 대회 신청</h3>
                    </div>
					<p class="view_area_tit"><c:out value='${contestView.ct_sbj }'/></p>
					<p style="text-align: right;">
    					<span class="ft_or">* </span>
   						<span>는 필수입력 항목입니다.</span>
					</p>
                    <div class="board_area sub_fade">
                       	<form:form commandName="vo" id="frm" name="frm" method="post" enctype="multipart/form-data">                      	
						<input type="hidden" name="ct_type" value="<c:out value='${contestView.ct_type }'/>" />
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
                                        <tr <c:if test="${contestView.ct_type ne 'L' }"> style="display: none;"</c:if>>
                                            <th scope="col"><span class="ft_or">* </span><label for="part">그룹(조)</label></th>
                                            <td colspan="3">
                                            	<c:choose>
                                            		<c:when test="${contestView.ct_type eq 'L' }">
		                                            	<select id="part" name="part" class="wd50p" >
		                                                    <option value="">::선택::</option>
		                                                    <c:choose>
		                                                    	<c:when test="${fn:length(pList) > 0 }">
		                                                    		<c:forEach items="${pList }" var="pList">
					                                                    <option value="${pList.ecp_seq }">${pList.part_ord }조 ${pList.part_name }</option>
		                                                    			
		                                                    		</c:forEach>
		                                                    	</c:when>
		                                                    </c:choose>
		                                                </select>
                                            		</c:when>
                                            		<c:when test="${contestView.ct_type eq 'A' }">
		                                            	<select id="part" name="part" class="wd50p" >
		                                                    <option value="1" selected="selected">1조</option>
		                                                </select>
                                            		</c:when>
                                            	</c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                       	    <th scope="col"><label for="join_name">아이디</label></th>
                                            <td>
                                            	<span>${sessionScope.mberInfo.mber_id }</span>
                                            </td>
                                             <th scope="col"><label for="deposit_name">성명</label></th>
                                            <td>
                                            	<input type="hidden" name="join_name" id="join_name"  value="${sessionScope.mberInfo.mber_name }" readonly="readonly" class="wd60p"/>                                          	
                                            	<input type="text" name="deposit_name" id="deposit_name"  value="${sessionScope.mberInfo.mber_name }" readonly="readonly" class="wd60p"/>                                            	
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="col"><label for="telno">연락처</label></th>
                                            <td>
                                            	<input type="text" name="telno1" id="telno1" maxlength="3" pattern="\d*" placeholder="" value="${tel_arr0 }"  class="wd30p mb03" />
                                            	<input type="text" name="telno2" id="telno2" maxlength="4" pattern="\d*" placeholder="" value="${tel_arr1 }"  class="wd30p mb03"/>
                                            	<input type="text" name="telno3" id="telno3" maxlength="4" pattern="\d*" placeholder="" value="${tel_arr2 }"  class="wd30p"/>
                                            	<input type="hidden" name="telno" id="telno"/>  
                                            </td>
                                            <th scope="col"><span class="ft_or">* </span><label for="gender">성별</label></th>
                                            <td>
												<!-- <select id="gender" name="gender" onchange="getHandi('gender',this.value);" class="wd40p"> -->
												<select id="gender" name="gender" onFocus='this.initialSelect = this.selectedIndex;' onChange='this.selectedIndex = this.initialSelect;' class="wd40p">
													<option value="">::선택::</option>
													<option value="M" <c:if test="${sessionScope.mberInfo.mber_gender eq 'M' }">selected="selected"</c:if>>남</option>
													<option value="F" <c:if test="${sessionScope.mberInfo.mber_gender eq 'F' }">selected="selected"</c:if>>여</option>
												</select>
												<p style="color: red;">※대회 신청 후 유선상 성별 변경이 가능합니다.</p>
                                            </td>
                                        </tr>
                                        <tr>
                                        	<th scope="col"><label for="birth">생년월일</label></th>
                                            <td>
                                            	<input type="text" id="bir" title="생년월일" value="${birth_arr0 }년 ${birth_arr1 }월 ${birth_arr2 }일" readonly="readonly" class="wd60p">
                                            	<input type="hidden" id="birthYear" title="생년" value="${birth_arr0 }">
                                            	<input type="hidden" id="birthMonth" title="월" value="${birth_arr1 }">
                                            	<input type="hidden" id="birthDay" title="일" value="${birth_arr2 }">
                                            	<input type="hidden" id="birth" name="birth" />      
                                            </td>
                                        	<th scope="col"><label for="disable_yn">장애여부</label></th>
                                            <td>
                                            	<select id="disable_yn" name="disable_yn" onchange="getHandi('disable',this.value);" class="wd40p">
                                            		<option value="">::선택::</option>
													<option value="N">해당없음</option>
													<option value="Y">장애3급이상</option>
												</select>
                                            </td>
                                        </tr>
                                        <tr>
	                                        <th scope="col"><label for="option1">티셔츠 사이즈</label></th>
	                                        <td>	
	                                        	<select id="option1" name="option1" class="wd50p">
                                                    <option value="">::사이즈 선택::</option>
                                                    <option value="85">85</option>
                                                    <option value="90">90</option>
                                                    <option value="95">95</option>
                                                    <option value="100">100</option>
                                                    <option value="105">105</option>
                                                    <option value="110">110</option>
                                                    <option value="120">120</option>
                                                    <option value="130">130</option>
                                                </select>
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
                        

                </div>
            </div>   
 			
		</div>
		<!-- #CONTENTS //E -->
		
	</div> 
	<!-- #CONTAINER //E -->

<jsp:include page="/client/footer.do"></jsp:include>