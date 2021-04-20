<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>

<c:import url="/client/header.do" />

<script type="text/javascript">

var gender;
var disable;
var birth_y;
var birth_m;
var birth_d;
var age;

$(document).ready(function(){
	getHandi('birth_y','${birth_arr0 }');
	getHandi('birth_m','${birth_arr1 }');
	getHandi('birth_d','${birth_arr2 }');
	getHandi('gender','${sessionScope.mberInfo.mber_gender}');
})
//신청 취소
function goCancel() {
	var refundFlag = false;
	$.ajax({
		url		:	"<c:url value='/membership/myEventContestAppCancelCheckYn.do'/>",
		data	:	{
			ct_seq : "${contestView.ct_seq }"
		},
		type	:	"post",
		async: false,
		success	:	function(data){
			if(data.root.ResultCode == "Y"){
				refundFlag = true;
			}else{
				alert("신청취소가 불가능한 기간입니다.");
				refundFlag = false;
				return;
			}
		}
	});
	
	if(refundFlag){
		if("${contestView.pay_flag}" == "Y"){
			if($("#refund_bank").val() == ""){
				alert("환불계좌 은행을 입력해주세요.");
				$("#refund_bank").focus();
				return;
			}
			
			if($("#refund_accholder").val() == ""){
				alert("환불계좌 예금주를 입력해주세요.");
				$("#refund_accholder").focus();
				return;
			}	
			
			if($("#refund_account").val() == ""){
				alert("환불계좌 번호를 입력해주세요.");
				$("#refund_account").focus();
				return;
			}
			
			$('<input type="hidden">').attr({'name':'refund_bank','value':$("#refund_bank").val()}).appendTo($("#frmCancel"));
			$('<input type="hidden">').attr({'name':'refund_accholder','value':$("#refund_accholder").val()}).appendTo($("#frmCancel"));
			$('<input type="hidden">').attr({'name':'refund_account','value':$("#refund_account").val()}).appendTo($("#frmCancel"));
		}
		
		if(!confirm("정말 취소하시겠습니까?")){
			return;
		}
		
		$("#frmCancel").attr("action", "<c:url value='/membership/myKokContestAppCancel.do'/>");
		$("#frmCancel").submit();
	}
	
}

function goBoardList(){
	$("#frm").attr("action", "<c:url value='/membership/myContestAppList.do'/>");
	$("#frm").submit();
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
		
		//여성10점 ==> 12점 ==> 10점 왕중왕전
		if(gender == 'F'){
			handicap += 10;
		}
		//장애 8점
		if(disable == 'Y'){
			handicap += 8;
		}
		
		//56세부터 1점추가 최고 10점 ==> 55세부터 1점 추가 최고 10점
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

function goAppInfoUpdt(){
	
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
	
	
	$("#frm").attr("action","<c:url value='/membership/myKokContestAppUpdate.do'/>");
	$("#frm").submit();
}

function chgDomain(val){
	$("#email2").val(val);
}
</script>

<form:form id="frmCancel" name="frmCancel" commandName="vo">
	<input type="hidden" name="ct_seq" id="ct_seq" value="${contestView.ct_seq }" />
	<input type="hidden" name="app_seq" id="app_seq" value="${contestView.app_seq }" />
	<input type="hidden" name="part" id="part" value="${contestView.part }"/>
	<input type="hidden" name="lane" id="lane" value="${contestView.lane }"/>
	<input type="hidden" name="status" id="status" value="${contestView.status }"/>
	<input type="hidden" name="updt_id" id="updt_id" value="${mber_id }" />
	<input type="hidden" name="pay_flag" value="${contestView.pay_flag }" />
	<input type="hidden" name="telno" value="${contestView.telno }" />
	<input type="hidden" name="join_name" value="${contestView.join_name }" />
</form:form> 

<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
			<h2 class="hide">본문</h2>
		    <c:import url="/client/snb.do" />                       
		        
	        <div class="sub_content">
	        	<div class="inner">
		        	<div class="sub_head">
		            	<h3 class="c_tit">대회신청내역</h3>
		            </div>
		            
	        
			        <div class="board_view_area sub_fade">
                   		<div class="bbs_view">
			                <div class="view_head">
			                    <dl class="subject">
			                        <dt><c:out value='${svo.ct_sbj }'/></dt>
			                        <dd class="info"><span><strong><c:out value='${svo.reg_id }'/></strong></span><span><c:out value='${svo.reg_dt }'/></span><span><c:out value='${svo.hit }'/></span></dd>
			                    </dl> 
			                </div>
			                <!--VIEW_HEAD// E-->
			            </div>    
			            
						<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
						<input type="hidden" name="app_seq" value="<c:out value='${contestView.app_seq }'/>" />
						<p style="text-align: right;">
	    					<span class="ft_or">* </span>
	   						<span >는 필수입력 항목입니다.</span>
						</p>
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
                                    	<c:if test="${contestView.ct_result eq 'Y'}">
	                                        <tr>
	                                            <th scope="col"><span class="ft_or">* </span><label for="part">그룹(조)</label></th>
	                                            <td colspan="3">
	                                            	<input type="hidden" name="part" id="part" value="${contestView.part }">${contestView.part }조
	                                            </td>
	                                        </tr>
                                        </c:if>
                                         <tr>
                                            <th scope="col"><label for="reg_id">아이디</label></th>
                                            <td>
                                            	${contestView.reg_id }
                                            </td>
                                       	    <th scope="col"><label for="join_name">성명</label></th>
                                            <td>
                                            	${contestView.join_name }
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="col"><label for="telno">참가자 연락처</label></th>
                                            <td>
                                            	<input type="text" name="telno1" id="telno1" class="w90" maxlength="3" pattern="\d*" placeholder="" value="${tel_arr0 }"/><span class="insert">-</span>
                                            	<input type="text" name="telno2" id="telno2" class="w90" maxlength="4" pattern="\d*" placeholder="" value="${tel_arr1 }"/><span class="insert">-</span>
                                            	<input type="text" name="telno3" id="telno3" class="w90" maxlength="4" pattern="\d*" placeholder="" value="${tel_arr2 }"/> 
                                            	<input type="hidden" name="telno" id="telno"/>  
                                            </td>
                                            <th scope="col"><span class="ft_or">* </span><label for="gender">성별</label></th>
                                            <td>
                                           		<c:if test="${contestView.gender eq 'M' }">남</c:if>
												<c:if test="${contestView.gender eq 'F' }">여</c:if>
                                            </td>
                                        </tr>
                                         <tr>
                                         	<th scope="col"><label for="birth">생년월일</label></th>
                                            <td>
                                            	${birth_arr0 }년 ${birth_arr1 }월 ${birth_arr2 }일
                                            	<input type="hidden" id="bir" title="생년월일" value="${birth_arr0 }년 ${birth_arr1 }월 ${birth_arr2 }일" class="w120" readonly="readonly">
                                            	<input type="hidden" id="birthYear" title="생년" value="${birth_arr0 }">
                                            	<input type="hidden" id="birthMonth" title="월" value="${birth_arr1 }">
                                            	<input type="hidden" id="birthDay" title="일" value="${birth_arr2 }">
                                            	<input type="hidden" id="birth" name="birth" />      
                                            </td>
                                            <th scope="col"><span class="ft_or">* </span><label for="disable_yn">장애여부</label></th>
                                            <td>
                                            	<select id="disable_yn" name="disable_yn" class="w90" onchange="getHandi('disable',this.value);">
                                            		<option value="">::선택::</option>
													<option value="N" <c:if test="${contestView.disable_yn eq 'N' }">selected="selected"</c:if>>해당없음</option>
													<option value="Y" <c:if test="${contestView.disable_yn eq 'Y' }">selected="selected"</c:if>>장애3급이상</option>
												</select>
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                        	<th scope="col"><label for="option1">티셔츠 사이즈</label></th>
	                                        <td>	
	                                        	<select id="option1" name="option1" class="wd50p">
                                                    <option value="">::사이즈 선택::</option>
                                                    <option value="85" <c:if test="${contestView.option1 eq '85'}">selected="selected"</c:if>>85</option>
                                                    <option value="90" <c:if test="${contestView.option1 eq '90'}">selected="selected"</c:if>>90</option>
                                                    <option value="95" <c:if test="${contestView.option1 eq '95'}">selected="selected"</c:if>>95</option>
                                                    <option value="100" <c:if test="${contestView.option1 eq '100'}">selected="selected"</c:if>>100</option>
                                                    <option value="105" <c:if test="${contestView.option1 eq '105'}">selected="selected"</c:if>>105</option>
                                                    <option value="110" <c:if test="${contestView.option1 eq '110'}">selected="selected"</c:if>>110</option>
                                                    <option value="120" <c:if test="${contestView.option1 eq '120'}">selected="selected"</c:if>>120</option>
                                                    <option value="130" <c:if test="${contestView.option1 eq '130'}">selected="selected"</c:if>>130</option>
												</select>
	                                        </td>
                                            <th scope="col"><label for="handicap">핸디</label></th>
                                            <td>
                                            	<span id="handiScore">${contestView.handicap }</span>
                                            	<input type="hidden" name="handicap" id="handicap" value="${contestView.handicap }"/>                                                                                        	
                                            </td>
                                        </tr> 
                                        <tr>
                                        	<th scope="col"><label for="birth">상태</label></th>
                                            <td>
                                            	<c:if test="${contestView.expose_yn eq 'Y'}">
                                            		<c:if test="${contestView.status eq '0003'}">신청취소</c:if>
	                                            	<c:if test="${contestView.status eq '0004'}">선정</c:if>
	                                            	<c:if test="${contestView.status eq '0005'}">대기</c:if>        
                                            	</c:if>
                                            	<c:if test="${contestView.expose_yn eq 'N'}">
                                            		<c:if test="${contestView.status eq '0003'}">취소접수</c:if>
                                            		<c:if test="${contestView.status ne '0003'}">대기접수</c:if>
                                            	</c:if>
                                            	<!-- 
                                            	<c:if test="${contestView.status eq '0003'}">신청취소</c:if>
                                            	<c:if test="${contestView.status eq '0004'}">선정</c:if>
                                            	<c:if test="${contestView.status eq '0005'}">대기</c:if>                                            	
                                            	 -->
                                            </td>
                                            <th scope="col"><label for="handicap">결제상태</label></th>
                                            <td>
                                            	<c:if test="${contestView.pay_flag eq 'Y'}">입금완료</c:if>
                                            	<c:if test="${contestView.pay_flag eq 'N'}">입금대기</c:if>
                                            	<c:if test="${contestView.pay_flag eq 'R'}">환불완료</c:if>
                                            </td>
                                        </tr>                                                                              
                                        <tr>
                                        	<th scope="col"><label for="birth">레인</label></th>
                                            <td colspan="3">
                                           		<c:if test="${contestView.ct_result eq 'Y'}">
                                           			${contestView.lane}
                                            	</c:if>
                                            </td>
                                        </tr>                                                                              
                                        <tr>
                                        	<th scope="col"><label for="refund_bank">환불계좌 은행</label></th>
                                            <td>
                                            	<input type="text" name="refund_bank" id="refund_bank" class="w160" placeholder="" value="${contestView.refund_bank }"/>
                                            </td>
                                            <th scope="col"><label for="refund_accholder">환불계좌 예금주</label></th>
                                            <td>
                                            	<input type="text" name="refund_accholder" id="refund_accholder" class="w160" placeholder="" value="${contestView.refund_accholder }"/>
                                            </td>
                                        </tr>                                                                              
                                        <tr>
                                        	<th scope="col"><label for="refund_account">환불계좌번호</label></th>
                                            <td>
                                            	<input type="text" name="refund_account" id="refund_account" class="" placeholder="" value="${contestView.refund_account }"/>
                                            </td>
                                        </tr>                                                                              
                                    </tbody>
                                </table>
                            </div>
                        </div>
			            <!--TBL_VIEW //E -->
			            <div class="btn_r2">
				            <c:if test="${contestView.status ne '0003'}">
				            	<c:if test="${(contestView.ct_process == 'E' || contestView.ct_process == 'S') && contestView.pay_flag != 'Y'}">
					            	<a href="javascript:goCancel()" class="gray">신청취소</a>
				            	</c:if>
					            <c:if test="${contestView.updt_yn eq 'Y'}">
				            		<a href="javascript:goAppInfoUpdt();" class="gray">수정</a>
				            	</c:if>
				            </c:if>
			                <a href="javascript:goBoardList();" class="">목록</a>
			            </div>     
			        </div>
			        <!--BOARD_VIEW_AREA //E-->
	    		</div>
	   		</div>
		</div>
		<!-- #CONTENTS //E -->		
	</div> 
	<!-- #CONTAINER //E -->		
</form:form>

<jsp:include page="/client/footer.do"></jsp:include>