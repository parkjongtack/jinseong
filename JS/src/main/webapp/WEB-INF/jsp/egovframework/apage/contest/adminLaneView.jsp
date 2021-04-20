<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
$(document).ready(function(){
	getAppResultList(1,"SMS");
	
	$(".btnAppPart").click(function(){
		$(".btnAppPart").each(function(){
			$(this).removeClass('on');
		});
		$(this).addClass('on');
		currPart = $(this).attr("data-part");
		$("#currPart").val(currPart);
		
		$(".chkAppAll").removeAttr("checked");
		
		getAppResultList($(this).attr("data-part"),$(".btnStatus.on").attr("data-status"));
	});
	
	$(".btnStatus").click(function(){
		$(".btnStatus").removeClass("on");
		$(this).addClass("on");
		var stat = $(this).attr("data-status");
		getAppResultList($(".btnAppPart.on").attr("data-part"),$(this).attr("data-status"));
	})
	
})
function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/contest/adminLaneList.do'/>");
	$("#frm").submit();
}


function goDelete(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/contest/adminLaneDelete.do'/>");
	$("#frm").submit();
}


function sendMsg(){
	if(!confirm('문자발송 하시겠습니까?')){
		return;
	}
		
	var smsFlag = $("#lane_sms_send_yn").val();
	if(smsFlag == "Y"){
		alert("이미 전송하였습니다.\n전송 실패건이 있다면 재전송을 이용해주세요.");
		return;
	}
	
	$.ajax({
		url		:	"<c:url value='/apage/contest/contestLaneResultSendMsg.do'/>",
		data	:	{
			ct_seq	:	"${contestView.ct_seq}"
		},
		async	:	false,
		type	:	"post",
		success	:	function(data){
			if(data.root.resultCode == "Y"){
				alert("문자발송이 성공하였습니다.");
				$("#lane_sms_send_yn").val('Y');
			}else if(data.root.resultCode == 'E'){
				alert("실패하였습니다.");
				$("#lane_sms_send_yn").val('N');
			}else{
				alert("더 이상 발송할 건이 없습니다.");
			}
		},beforeSend		:function(){
			$('.wrap-loading').removeClass('display-none'); //(이미지 보여주기 처리)
		},complete			:function(){
			$('.wrap-loading').addClass('display-none'); //(이미지 감추기 처리)
		},error		: function(data) {
			alert('에러가 발생했습니다.');
		},timeout:100000
	})
}



function getAppResultList(part,status){
	console.log("part ===> " + part + " /  status ==> " + status);
	if(status == "SMS"){
		$.ajax({
			url		:	"<c:url value='/apage/contest/contestMsgRandomLaneResultJson.do'/>",
			data	:	{
				ct_seq	:	"${contestView.ct_seq}",
				part	:	part
			},
			type	:	"post",
			success	:	function(data){
				$("#contestAppList").empty();
				$("#totCnt").empty();
				if(data.root.resultCode == "Y"){
					$("#totCnt").html(data.root.smsHistory.length+"건");
					$("#smsLogTmpl").tmpl(data.root.smsHistory).appendTo("#contestAppList");			
				}else{
					$("#totCnt").html("0명");
					$("#contestAppList").append("<tr><td colspan='6'>데이터가 존재하지 않습니다.</td></tr>");
				}
			},beforeSend		:function(){
				$(".msgLogListTr").css("display","table-row");
				$(".contestAppListTr").css("display","none");
				$('.wrap-loading').removeClass('display-none'); //(이미지 보여주기 처리)
			},complete			:function(){
				$('.wrap-loading').addClass('display-none'); //(이미지 감추기 처리)
			},error		: function(data) {
				alert('에러가 발생했습니다.');
				$("#contestAppList").empty();
				$("#totCnt").empty();
			},timeout:100000
		})	
	}
}


function resuslt(val){
	var v_ct_result = $("#ct_result_btn").attr("data-result");
	var v_msg = '';
	if(v_ct_result == "Y"){
		v_msg = "레인배치 결과를 비노출 하시겠습니까?";
		v_ct_result = "N";
	}else{
		v_msg = "레인배치 결과를 노출 하시겠습니까?";
		v_ct_result = "Y";
	}
	
	if(confirm(v_msg)){
		$.ajax({
			url	: "<c:url value='/apage/contest/contestLaneResultExposeYn.do'/>",
			data : {
				ct_seq	:	"${contestView.ct_seq}",
				ct_result : v_ct_result
			},
			type : "post",
			success	:	function(data){
				if(v_ct_result == "Y"){
					$("#ct_result_btn").attr("data-result","Y");
				}else{
					$("#ct_result_btn").attr("data-result","N");
				}
				
				if(data.root.resultCode == "Y"){
					alert("성공하였습니다.");
				}else if(data.root.resultCode == "Y"){
					alert('에러가 발생했습니다.');
				}else{
					alert("실패하였습니다.");
				}
			},beforeSend		:function(){
				$('.wrap-loading').removeClass('display-none'); //(이미지 보여주기 처리)
			},complete			:function(){
				$('.wrap-loading').addClass('display-none'); //(이미지 감추기 처리)
			},error		: function(data) {
				alert('에러가 발생했습니다.');
			},timeout:100000
				
		})
	}
}

function getExcel(){
	$("#excelFrm").attr("action","/apage/contest/contestSelectResulExcel.do");
	$("#excelFrm").submit();
}

$(document).on("click",".resendBtn",function(){
	var msgkey = $(this).attr("data-msgkey");
	var sentdate = $(this).attr("data-senddt");
	var rslt = $(this).attr("data-rslt");
	
	if(rslt != "0"){
		$.ajax({
			url		:	"<c:url value='/apage/contest/resendMsg.do'/>",
			type	:	"post",
			data	:	{
				msgkey		:	msgkey,
				sentdate	:	sentdate,
				rslt		:	rslt
			},
			async	:	false,
			success	:	function(data){
				if(data.root.resultCode == "Y"){
					alert("재전송 되었습니다.");
				}else if(data.root.resultCode == "N"){
					alert("실패하였습니다.");
				}else if(data.root.resultCode == "A"){
					alert("잘못된 접근입니다.");
				}else{
					alert("오류가 발생하였습니다.");
				}
			},
			error	:	function(err){
				console.log(err);				
				alert("오류가 발생하였습니다.");
			}
		
		})
	}
})
</script>


<script id="smsLogTmpl" type="text/x-jquery-tmpl">
<tr>
	<td scope="row" style="width:5%;">\${etc7 }</td>
	<td style="width:10%;">\${etc6 }</td>
	<td style="width:10%;">\${etc8 }</td>
	<td style="width:10%;">\${phone }</td>
	<td style="width:20%;"><pre style="text-align : left;">{{html msg}}</pre></td>
	<td style="width:5%;">
		{{if etc9 == '0004'}}<span style="color:blue;">선정</span>{{/if}}
		{{if etc9 == '0005'}}<span style="color:green;">대기</span>{{/if}}
		{{if etc9 == '0003'}}<span style="color:red;">신청취소</span>{{/if}}
	</td>
	<td style="width:8%;">
		{{if rslt == '0'}}<span style="color:blue;">전송성공</span>{{/if}}
		{{if rslt != '0' && rslt != '99999' }}<span style="color:red;">전송실패</span>{{/if}}
		{{if rslt == '99999'}}<span style="color:green;">재전송</span>{{/if}}
	</td>
	<td style="width:10%;">\${reqdate }</td>
	<td style="width:8%;">
		{{if rslt != '0' && rslt != '99999'}}<a href="javascript:void(0);" data-msgkey="\${msgkey }" data-senddt="\${reqdate}" data-rslt="\${rslt}" class="s-blue-btn resendBtn">재전송</a>{{/if}}
	</td>
</tr>
</script>

<!-- 
<script id="smsLogTmpl" type="text/x-jquery-tmpl">
<tr>
	<td scope="row">\${app_sms_date }</td>
	<td>\${join_name }</td>
	<td>\${birth }</td>
	<td>\${telno }</td>
	<td>\${lane}</td>
	<td>
		{{if app_lane_sms_flag == 'Y'}}<span style="color:blue;">전송성공</span>{{/if}}
		{{if app_lane_sms_flag != 'Y'}}<span style="color:red;">전송실패</span>{{/if}}
	</td>
</tr>
</script>
 -->

<form id="excelFrm" name="excelFrm" method="post">
	<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
</form>

	
<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="ntt_id" id="ntt_id" value="<c:out value='${contestView.ntt_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" id="lane_sms_send_yn"  value="${contestView.lane_sms_send_yn }" />
<div id="container">
	<h3>자동배치결과</h3>
	<div class="contents">
		<!-- //ct-tab -->
		<div class="bbs-view">
			<table>
				<colgroup>
					<col width="15%">
					<col width="*">
					<col width="15%">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td class="al_l"><c:out value='${contestView.ntt_sj }'/></td>
						<th>작성자</th>
						<td class="al_l"><c:out value='${contestView.ntcr_id }'/></td>
					</tr>
					<tr>
						<th>작성일</th>
						<td class="al_l"><c:out value='${contestView.reg_dt }'/></td>
						<th>조회수</th>
						<td class="al_l"><c:out value='${contestView.rdcnt }'/></td>
					</tr>
					<tr>
                       	<td colspan="6" class="cont">
                       		<table style="border:1px solid #c9c9c9;width:45%">
                       			<tbody>
                       				<tr>
                       					<td colspan="3" style="text-align:center;color:red">1조</td>
                       				</tr>
                       				<tr>
	                       				<td style="text-align:center">성명</td>
	                       				<td style="text-align:center">레인</td>
	                       				<td style="text-align:center">핸디</td>
                       				</tr>
									<c:choose>
					            		<c:when test="${fn:length(part1List) > 0}">
					            			<c:forEach items="${part1List }" var="list" varStatus="status">
					            				<tr>
					            					<td style="text-align:center"><c:out value="${list.join_name }"/></td>
					            					<td style="text-align:center"><c:out value="${list.lane }"/></td>
					            					<td style="text-align:center"><c:out value="${list.handicap }"/></td>
					            				</tr>
			        	    				</c:forEach>
			            				</c:when>
	            					</c:choose>
                       			</tbody>
							</table>
							<br/><br/>
                       		
                       		<table style="border:1px solid #c9c9c9;width:45%">
                       			<tbody>
                       				<tr>
                       					<td colspan="3" style="text-align:center;color:red">2조</td>
                       				</tr>
                       				<tr>
	                       				<td style="text-align:center">성명</td>
	                       				<td style="text-align:center">레인</td>
	                       				<td style="text-align:center">핸디</td>
                       				</tr>
									<c:choose>
					            		<c:when test="${fn:length(part2List) > 0}">
					            			<c:forEach items="${part2List }" var="list" varStatus="status">
					            				<tr>
					            					<td style="text-align:center"><c:out value="${list.join_name }"/></td>
					            					<td style="text-align:center"><c:out value="${list.lane }"/></td>
					            					<td style="text-align:center"><c:out value="${list.handicap }"/></td>
					            				</tr>
			        	    				</c:forEach>
			            				</c:when>
	            					</c:choose>
                       			</tbody>
							</table>
							<br/><br/>
                       		
                       		<table style="border:1px solid #c9c9c9;width:45%">
                       			<tbody>
                       				<tr>
                       					<td colspan="3" style="text-align:center;color:red">3조</td>
                       				</tr>
                       				<tr>
	                       				<td style="text-align:center">성명</td>
	                       				<td style="text-align:center">레인</td>
	                       				<td style="text-align:center">핸디</td>
                       				</tr>
									<c:choose>
					            		<c:when test="${fn:length(part3List) > 0}">
					            			<c:forEach items="${part3List }" var="list" varStatus="status">
					            				<tr>
					            					<td style="text-align:center"><c:out value="${list.join_name }"/></td>
					            					<td style="text-align:center"><c:out value="${list.lane }"/></td>
					            					<td style="text-align:center"><c:out value="${list.handicap }"/></td>
					            				</tr>
			        	    				</c:forEach>
			            				</c:when>
	            					</c:choose>
                       			</tbody>
							</table>
							<br/><br/>
                       		
                       		<table style="border:1px solid #c9c9c9;width:45%">
                       			<tbody>
                       				<tr>
                       					<td colspan="3" style="text-align:center;color:red">4조</td>
                       				</tr>
                       				<tr>
	                       				<td style="text-align:center">성명</td>
	                       				<td style="text-align:center">레인</td>
	                       				<td style="text-align:center">핸디</td>
                       				</tr>
									<c:choose>
					            		<c:when test="${fn:length(part4List) > 0}">
					            			<c:forEach items="${part4List }" var="list" varStatus="status">
					            				<tr>
					            					<td style="text-align:center"><c:out value="${list.join_name }"/></td>
					            					<td style="text-align:center"><c:out value="${list.lane }"/></td>
					            					<td style="text-align:center"><c:out value="${list.handicap }"/></td>
					            				</tr>
			        	    				</c:forEach>
			            				</c:when>
	            					</c:choose>
                       			</tbody>
							</table>
							<br/><br/>

	
                       	</td>
                    </tr>
				</tbody>
			</table>
		</div>

		
		<!-- //attach -->
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:sendMsg()" class="btn-ty1 blue">문자발송</a>
				<a href="javascript:resuslt()" id="ct_result_btn" class="btn-ty1 red" data-result ="${contestView.ct_result }">결과노출</a>
				<a href="javascript:goDelete()" class="btn-ty1 black">삭제</a>
				<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
			</p>
		</div>
		<!-- //bbs-view -->
		
		
		<ul class="ct-tab">
			<li><a href="javascript:void(0);" class="btnAppPart on" data-part="1">1조</a></li>
			<li><a href="javascript:void(0);" class="btnAppPart" data-part="2">2조</a></li>
			<li><a href="javascript:void(0);" class="btnAppPart" data-part="3">3조</a></li>
			<li><a href="javascript:void(0);" class="btnAppPart" data-part="4">4조</a></li>		
			<p class="bbs-total mb20"><a href="javascript:getExcel();" class="btn-ty2 excel fr">신청자 엑셀다운</a></p>							
		</ul>		
		<ul class="ct-tab">
			<li><a href="javascript:void(0);" class="btnStatus on" data-status="SMS">문자전송내역</a></li>
		</ul>		
		<div class="bbs-head">
           	<font color="#003CFF">※인원수 : </font><span id="totCnt"></span>
		</div>
		
		<fieldset class="bbs-list">
			<legend>신청자 목록</legend>
			<table summary="사업신청 결과제출 전체 목록">
			<colgroup>
					<col width="3%">
					<col width="3%">
					<col width="7%">
					<col width="8%">
					<col width="3%">
					<col width="8%">
					<col width="5%">
					<col width="12%">
					<col width="10%">
					<col width="5%">
					<col width="5%">
					<col width="5%">
					<col width="8%">
					<col width="8%">
					<col width="15%">
				</colgroup>
				<thead>
					<tr class="msgLogListTr" style="display: none;">
						<th scope="col">성명</th>
                        <th scope="col">아이디</th>
                        <th scope="col">생년월일</th>
                        <th scope="col">수신번호</th>
                        <th scope="col">내용</th>
                        <th scope="col">신청상태</th>
                        <th scope="col">전송결과</th>
						<th scope="col">전송일자</th>
                        <th scope="col">재전송</th>
					</tr>
				</thead>
				<tbody id="contestAppList">

				</tbody>
			</table>
		</fieldset>
	</div>
</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>