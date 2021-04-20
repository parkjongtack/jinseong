<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/member/adminMemberContestList.do'/>");
	$("#frm").submit();
}

//대회 상세정보 팝업창 열기
function goContestDetail(e,app_seq,ct_sbj){ //e,ct_seq,ct_sbj,reg_id
	
	var clientY = e.clientY; //상단 좌표
	//var clientX = e.clientX + 600; //좌측 좌표	

	$("#title").html(ct_sbj);
	//$("#pop-ebk").css('display','block');
	$('#pop-ebk').css({
     	 "top"		: clientY
     	//,"left"		: clientX
 	}).show();

	getContestInfo(app_seq); //ct_seq,reg_id
}

//대회 상세정보 불러오기
function getContestInfo(app_seq) {
	
	$.ajax({
		type		: "POST",
		url			: "/apage/member/adminMemberContestInfo.do",	
		data		: {app_seq : app_seq},
		cache		: false,
		dataType	: "json",
		beforeSend: function (xhr) {
			xhr.setRequestHeader('AJAX', true);
		},
		success		: function(data) {
			
			if(data.root.resultCode == "Y"){
				var bvo = data.root.contestDetail;
				
				$("#mberContestInfo").empty();
				var html = '';
				
				//신청일자 & 그룹
				html += '<tr>';
				html += '<th scope="col">신청일자</th>';
				html += '<td class="al_c">'; //class="al_c"
				html += bvo.reg_dt;
				html += '</td>';
				html += '<th scope="col">그룹(조)</th>';
				html += '<td class="al_c">';
				html += bvo.part+'조';
				html += '</td>';
				html += '</tr>';
				
				//참가자명 & 성별
				html += '<tr>';
				html += '<th scope="col">참가자명</th>';
				html += '<td class="al_c">';
				html += bvo.join_name;
				html += '</td>';
				html += '<th scope="col">성별</th>';
				if(bvo.gender == "M"){
					html += '<td class="al_c">남</td>';
				}else{
					html += '<td class="al_c">여</td>';
				}
				html += '</tr>';
				
				//생년월일 & 연락처
				html += '<tr>';
				html += '<th scope="col">생년월일</th>';
				html += '<td class="al_c">';
				html += bvo.birth;
				html += '</td>';
				html += '<th scope="col">연락처</th>';
				html += '<td class="al_c">';
				if(bvo.telno == null){
					html += bvo.telno_string;
				}else{
					html += bvo.telno;
				}
				html += '</td>';
				html += '</tr>';
				
				//장애여부 & 핸디
				html += '<tr>';
				html += '<th scope="col">장애여부</th>';
				if(bvo.disable_yn == "Y"){
					html += '<td class="al_c">장애3급이상</td>';
				}else{
					html += '<td class="al_c">해당없음</td>';
				}
				html += '<th scope="col">핸디</th>';
				html += '<td class="al_c">';
				html += bvo.handicap+'점';
				html += '</td>';
				html += '</tr>';
				
				//입금자명 & 결제상태
				html += '<tr>';
				html += '<th scope="col">입금자명</th>';
				html += '<td class="al_c">';
				html += bvo.deposit_name;
				html += '</td>';
				html += '<th scope="col">결제상태</th>';
				if(bvo.pay_flag == "Y"){
					html += '<td class="al_c" style="color: blue;">입금완료</td>';
				}else if(bvo.pay_flag == "N"){
					html += '<td class="al_c" style="color: green;">입금대기</td>';
				}else{
					html += '<td class="al_c" style="color: red;">환불완료</td>';
				}
				html += '</tr>';
				
				//상태 & 대기번호 & 레인
				if(bvo.status == "0003"){ //상태가 신청취소일 경우, 취소일자와 함께 보여줌.
					html += '<tr>';
					html += '<th scope="col">상태</th>';
					html += '<td class="al_c" style="color: red;">신청취소</td>';
					html += '<th scope="col">취소일자</th>';
					html += '<td class="al_c">';
					html += bvo.cancel_date;
					html += '</td>';
					html += '</tr>';
				}else{
					html += '<tr>';
					html += '<th scope="col">상태</th>';
					if(bvo.status == "0004"){ //상태가 선정일 경우, 선정된 레인과 함께 보여줌.
						html += '<td class="al_c" style="color: blue;">선정</td>';
						html += '<th scope="col">레인</th>';
						html += '<td class="al_c">';
						if(bvo.lane != null){
							html += bvo.lane;
						}else{
							html += '미확정';
						}
						html += '</td>';
					}else{ //상태가 대기일 경우, 대기번호와 함께 보여줌.
						html += '<td class="al_c" style="color: green;">대기</td>';
						html += '<th scope="col">대기번호</th>';
						html += '<td class="al_c">';
						html += bvo.waiting_num;
						html += '</td>';
					}
					html += '</tr>';
				}
				
				//결제상태가 환불완료일 경우만 보여줌.
				if(bvo.pay_flag == "R"){
					//환불계좌 은행 & 환불계좌 예금주
					html += '<tr>';
					html += '<th scope="col">환불계좌 은행</th>';
					html += '<td class="al_c">';
					html += bvo.refund_bank;
					html += '</td>';
					html += '<th scope="col">환불계좌 예금주</th>';
					html += '<td class="al_c">';
					html += bvo.refund_accholder;
					html += '</td>';
					html += '</tr>';
					
					//환불계좌번호
					html += '<tr>';
					html += '<th scope="col">환불계좌번호</th>';
					html += '<td class="al_c">';
					html += bvo.refund_account;
					html += '</td>';
					html += '</tr>';
				}
				
				
				
				$("#mberContestInfo").append(html);
				
			}else{
			
				$("#mberContestInfo").empty();
				var html = '<tr><td colspan="5" class="al_c">정보가 존재하지 않습니다.</td></tr>';
				$("#mberContestInfo").append(html);
			}
		},
		error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		} 
	});
}

//팝업창 닫기
function goPopupClose(){
	$("#pop-ebk").css('display','none');
}
</script>

<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="scType" id="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>

	<div id="container">
		<h3>${join_name }님 대회신청이력</h3>
		<div class="contents">
			<!-- //ct-tab -->
			<div class="bbs-view">
				<table>
					<colgroup>
                       	<col width="*%" class="pVer">
                       	<col width="15%" class="pVer">
                       	<col width="15%" class="pVer">
                       	<col width="15%" class="pVer">
                   	</colgroup>
                   	<thead>
                     	<tr>
                         	<th scope="row">대회명</th>
                         	<th scope="row">대회일자</th>
                         	<th scope="row">장소</th>
                         	<th scope="row">진행상태</th>                                    
                     	</tr>
                   	</thead>
					<tbody>
						<c:choose>
		            		<c:when test="${fn:length(contestList) > 0}">
		            			<c:forEach items="${contestList }" var="list" varStatus="status">
		            				<tr>
                                       	<td>
                                       		<a href="javascript:void(0)" onclick="goContestDetail(event,'${list.app_seq}','${list.ct_sbj }')"> <!-- onclick="goContestDetail(event,'${list.ct_seq}','${list.ct_sbj }','${list.reg_id }')" -->
                                       			<c:out value='${list.ct_sbj }'/>
                                       		</a>
                                       	</td>
                                       	<td class="al_c"><c:out value='${list.ct_dt }'/></td>
                                       	<td class="al_c"><c:out value='${list.ct_place }'/></td>
                                       	<td class="al_c">
                                       		<c:if test="${list.ct_process eq 'R'}" >대회준비</c:if>
                                       		<c:if test="${list.ct_process eq 'S'}" >대회신청</c:if>
                                       		<c:if test="${list.ct_process eq 'E'}" >신청마감</c:if>
                                       		<c:if test="${list.ct_process eq 'F'}" >대회종료</c:if>
                                       	</td>
	                                </tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
				                <tr>
				                    <td colspan="4" class="al_c">신청한 대회가 존재하지 않습니다.</td>
				                </tr>
				            </c:otherwise>
				        </c:choose>       
					</tbody>
				</table>
			</div>
	
			<!-- //attach -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
				</p>
			</div>
			<!-- //bbs-view -->
		</div>
	</div>
</form:form>

<!-- 수신번호선택 팝업창 -->
<div id="pop-ebk" style="display: none; width: 550px;">
   	<h5 id="title"></h5>
       	<div class="edite">
           	<div class="inner-write mb20">
				<table summary="대회상세정보" style="border-top: 1px solid #bbb">
					<colgroup>
                        <col width="15%">
                        <col width="35%">
                        <col width="15%">
                        <col width="35%">
                    </colgroup>
					<tbody id="mberContestInfo">
						
					</tbody>
				</table>		                
           	</div>
           	<!-- //bbs-write-->
           	<span class="fr" style="position: absolute; bottom: 10px; right: 24px;">
           	 	<a href="javascript:goPopupClose();" target="_blank" class="btn-ty2 blue">닫기</a>
           	</span>
       	</div>
       	<!-- //edit-->
       	<p class="close"><a href="javascript:goPopupClose()">X</a></p>
   	</div>

<jsp:include page="/apage/inc/footer.do"></jsp:include>