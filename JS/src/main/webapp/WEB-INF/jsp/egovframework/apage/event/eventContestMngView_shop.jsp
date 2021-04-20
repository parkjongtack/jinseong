<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<c:if test="${contestView.ct_seq != '10000026' && contestView.ct_seq != '10000027' }">
	<style type="text/css">
		/*.colorOpt{ display: none; }*/
	</style>
</c:if>

<script type="text/javascript">
var currPart = "1";

$(document).ready(function () {
//	getAppListJson('1');
	
	$(".btnAppPart").click(function(){
		
		$(".btnAppPart").each(function(){
			$(this).removeClass('on');
		});
		$(this).addClass('on');
		currPart = $(this).attr("data-part");
		$("#currPart").val(currPart);
		
		$(".chkAppAll").removeAttr("checked");
		
		getAppListJson($(this).attr("data-part"));		
	});
	
	$('.chkAppAll').change(function () {
	    var checked = (this.checked) ? true : false;
	    $('.chkApp').prop('checked',checked);
	});
	
	if($(".btnAppPart").length > 0){
		$(".btnAppPart").each(function(idx){ 
			if(idx == 0){
				var d_part = $(this).attr("data-part"); 
				getAppListJson(d_part);
				return false;
			}
		})
	}
	
	
});


$(document).on("click",".btnUpdt",function() {
	var telno;
	var gender;
	var status;
	var lane;
	var birth;

	var $tr = $(this).parent().parent();
	
	lane = $tr.find(".lane_num").html();
	
	if($tr.find('#telno').val() == ""){
		alert("연락처를 입력하세요");
		$tr.find('#telno').fucus();
		return;
	}else{
		telno = $tr.find('#telno').val();
	}
	
	
	gender = $tr.find('#gender').val();
	status = $tr.find('#status').val();
	var origin_status = $(this).attr("data-status");
	var app_seq = $(this).attr("data-seq");
	var part = $(this).attr("data-part");
	var ct_seq = $(this).attr("data-ctseq");

	
	
	
	var zipcode			=	$tr.find('input[name=zipcode]').val();
	var addr			=	$tr.find('input[name=addr]').val();
	var addr_detail		=	$tr.find('input[name=addr_detail]').val();
	
	var option1			=	$tr.find('select[name=option1]').val();
	var option2			=	$tr.find('select[name=option2]').val();
	
	
	
	if(confirm("신청정보를 수정하시겠습니까?") == true){
		
		$.ajax({
			type : "POST",
			url : "/apage/event/updateEventContestApp.do",
			data : {
					
				telno     		:	telno,
				gender    		:	gender,
				status    		:	status,
				app_seq   		:	app_seq,
				origin_status	:	origin_status,
				lane 			:	lane,
				ct_seq 			:	ct_seq,
				part			:	part,
				zipcode			:	zipcode,
				addr			:	addr,
				addr_detail		:	addr_detail,
				option1			:	option1,
				option2			:	option2
				
			},
			cache : false,
			dataType : 'json',
			success : function(msg){
				getAppListJson(part);
				//getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
			},
			error : function(data, status, err) {
				console.log(data + " / " + status + " / " + err);
				return;
			}
		});
		//getAppListJson(part);
	}else{
		return;
	}
});

$(document).on("click",".btnDel",function() {
	
	if(confirm("신청정보를 삭제하시겠습니까?") == true){
		
		var app_seq = $(this).attr("data-seq");
		var part = $(this).attr("data-part");
		
		$.ajax({
			type : "POST",
			url : "/apage/event/deleteEventContestApp.do",
			data : {
					app_seq   :app_seq
			},
			cache : false,
			dataType : 'json',
			success : function(msg){
				getAppListJson(part);			
			},
			error : function(data, status, err) {
				alert(status);
				return;
			}
		});
	}else{
		return;
	}
});


$(document).on("click",".chkApp",function() {
	if($(this).is(":checked")==true){
		$(this).parent().parent().css("background-color","#f8f8f8");
	}else{
		$(this).parent().parent().css("background-color","");
	}
});


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/event/eventContestMngList.do'/>");
	$("#frm").submit();
}

function goModify(){
	$("#frm").attr("action", "<c:url value='/apage/event/eventContestMngModify.do'/>");
	$("#frm").submit();
}

function goDelete(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/event/eventContestMngDelete.do'/>");
	$("#frm").submit();
}

function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}

function getAppListJson(part){
	
	var ct_seq = $("#ct_seq").val();
	 	
	 $.ajax({
			type		: "POST",
			url			: "/apage/event/eventContestAppListJson.do",
			data		: {
							ct_seq : ct_seq, 
							part : part
						},
			cache		: false,
			dataType	: 'json',
			success		: function(data) {
				//console.log(data.root.dataset0);
				arrayData24s = data.root.dataset0;
				if(arrayData24s.length > 0){	
					$("#contestAppList").html("");
					$("#contestAppListTmpl").tmpl(data.root.dataset0).appendTo("#contestAppList");			
				}else{		
	 				var strhtml ="";
					strhtml += "<tr><td colspan='14'>해당조 신청 인원이 없습니다.</td></tr>";
					$("#contestAppList").html("");
					$("#contestAppList").html(strhtml); 

				}
			},beforeSend		:function(){
	        	//(이미지 보여주기 처리)
		        $('.wrap-loading').removeClass('display-none');
		    	},complete			:function(){
		           //(이미지 감추기 처리)
		            $('.wrap-loading').addClass('display-none');	     
		        },error		: function(data) {
					//alert('에러가 발생했습니다.');
					getAppListJson(currPart);
				},timeout:100000
		});
}

//체크데이터 삭제
function goAppDelete(){
	var app_seq = new Array();
	var checkVal = "";
	
	$(".chkApp").each(function(){
		if($(this).is(":checked")){
			checkVal += $(this).val()+",";
		}
	})
	if(checkVal == ""){
		alert("체크된 데이터가 없습니다.");
		return;
	}
	
	checkVal = checkVal.substring(0,checkVal.length - 1);
	app_seq = checkVal.split(',');
	
	if(confirm("선택한 신청데이터를 삭제하시겠습니까?")==true){
		
		for(var i=0;i<app_seq.length;i++){
			
			$.ajax({
				type : "POST",
				url : "/apage/event/deleteEventContestApp.do",
				data : {
						app_seq   :app_seq[i]
				},
				cache : false,
				dataType : 'json',
				success : function(msg){

					getAppListJson(currPart);

				},
				error : function(data, status, err) {
					alert(status);
					return;
				}
			});
		}
	}
	
}

function goAppModify(){
	
	var app_seq;
	var checkVal = 0;
	
	$(".chkApp").each(function(){
		if($(this).is(":checked")){
			checkVal++;
		}
	})
	if(checkVal == 0){
		alert("체크된 데이터가 없습니다.");
		return;
	}
	
	if(confirm("체크된 데이터를 수정하시겠습니까?") == true){
		var telno;
		var gender;
		var status;
		var lane;
		var birth;
		
		$("#contestAppList").find('tr').each(function(){
			if($(this).find('.chkApp').is(":checked")){
				app_seq = $(this).find('.chkApp').val();
				
				if($(this).find('#telno').val() == ""){
					alert("연락처를 입력하세요");
					return false;
				}else{
					telno = $(this).find('#telno').val();
				}
							
				
				gender = $(this).find('#gender').val();
				status = $(this).find('#status').val();
				var origin_status = $(this).find(".btnUpdt").attr("data-status");
				var app_seq = $(this).find(".btnUpdt").attr("data-seq");
				var part = $(this).find(".btnUpdt").attr("data-part");
				var ct_seq = $(this).find(".btnUpdt").attr("data-ctseq");
				
				var zipcode			=	$(this).find('input[name=zipcode]').val();
				var addr			=	$(this).find('input[name=addr]').val();
				var addr_detail		=	$(this).find('input[name=addr_detail]').val();
				
				var option1			=	$(this).find('select[name=option1]').val();
				var option2			=	$(this).find('select[name=option2]').val();
				
				$.ajax({
					type : "POST",
					url : "/apage/event/updateEventContestApp.do",
					data :  {
						telno     		:	telno,
						gender    		:	gender,
						status    		:	status,
						app_seq   		:	app_seq,
						origin_status	:	origin_status,
						lane 			:	lane,
						ct_seq 			:	ct_seq,
						part			:	part,
						zipcode			:	zipcode,
						addr			:	addr,
						addr_detail		:	addr_detail,
						option1			:	option1,
						option2			:	option2
					},
					cache : false,
					dataType : 'json',
					success : function(msg){
						//getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
						getAppListJson(currPart);			
					},
					error : function(data, status, err) {
						//alert(status);
						console.log(data + " / " + status + " / " + err);
						return;
					}
				});	
			}			
		})		
		//getAppListJson(currPart);			
	}else{
		return;
	}
}

//신청자 엑셀출력
function getExcel(){
	
	$("#frm").attr("action","/apage/event/eventContestAppListExcel.do");
	$("#frm").submit();
}
	
	
//신청현황 노출
function goSituationShow(){
	var flag = $("#frm #situation_show_yn").val();
	var situation_show_yn = "";
	var up_flag = false;
	if(flag != "" && flag == "Y"){
		if(confirm("접수현황노출을 '노출안함' 상태로 변경하시겠습니까?")){
			situation_show_yn = "N";
			up_flag = true;
		}
	}else{
		if(confirm("접수현황노출을 '노출' 상태로 변경하시겠습니까?")){
			situation_show_yn = "Y";
			up_flag = true;
		}
	}
	
	if(up_flag){
		$.ajax({
			url		:	"<c:url value='/apage/event/situationShowUpdate.do'/>",
			data	:	{
				situation_show_yn	:	situation_show_yn,
				ct_seq				:	'${contestView.ct_seq }'
			},
			type	:	"post",
			async	:	false,
			success	:	function(data){
				if(data.root.resultCode == "Y"){
					$("#frm #situation_show_yn").val(situation_show_yn);
					alert("성공하였습니다.");
					//location.reload();
					if(situation_show_yn == 'Y'){
						$("#situation_wrap").html("<span style='color:blue;'>노출</span>");
					}else{
						$("#situation_wrap").html("<span style='color:red;'>노출안함</span>");
					}
				}else if(data.root.resultCode == "N"){
					alert("실패하였습니다.");
				}else if(data.root.resultCode == "A"){
					alert("잘못된 접근입니다.");
				}else if(data.root.resultCode == "E"){
					alert("오류가 발생하였습니다.");
				}
			},
			error	:	function(err){
				alert("오류가 발생하였습니다.");
				console.log(err);
			}
		})
	}

}

function birthFocusout(val){
	var format = /^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
	if(!format.test(val)){
		return false;
	}else{
		return true;
	}
}



//사용자 페이지에 선정결과 노출하기 or 숨기기
function goUserPageSee(ct_seq) {
	
	var html = $("#userPageSee").html();
	
	if(html == "결과노출"){
		if(confirm("결과를 노출하시겠습니까?") == true){
			$.ajax({
				type		: "POST",
				url			: "/apage/event/eventContestAppResultExposeYn.do",
				data		: {
								ct_seq : ct_seq, 
								expose_yn : "Y"
							},
				cache		: false,
				dataType	: 'json',
				success		: function(data) {
					if(data.root.resultCode == "Y"){
						alert("결과가 노출되었습니다.");
						$("#userPageSee").html("결과숨김");		
					}else{
						alert("에러입니다.");
					}
				}
			});		
		}
	}else{
		if(confirm("결과를 숨기시겠습니까?") == true){
			$.ajax({
				type		: "POST",
				url			: "/apage/event/eventContestAppResultExposeYn.do",
				data		: {
								ct_seq : ct_seq, 
								expose_yn : "N"
							},
				cache		: false,
				dataType	: 'json',
				success		: function(data) {
					if(data.root.resultCode == "Y"){
						alert("결과가 노출되지 않습니다.");
						$("#userPageSee").html("결과노출");		
					}else{
						alert("에러입니다.");
					}			
				}
			});		
		}
	}
}

</script>


<!-- 
	<option value="S"{{if option1 == 'S'}}selected{{/if}}>S</option>
	<option value="M"{{if option1 == 'M'}}selected{{/if}}>M</option>
	<option value="L"{{if option1 == 'L'}}selected{{/if}}>L</option>		
 -->
 
 <!--
	<td>
		<select id="option2" name="option2">
			<option value="BLACK"{{if option2 == 'BLACK'}}selected{{/if}}>BLACK</option>
			<option value="WHITE"{{if option2 == 'WHITE'}}selected{{/if}}>WHITE</option>
			<option value="BLUE"{{if option2 == 'BLUE'}}selected{{/if}}>BLUE</option>
			<option value="PINK"{{if option2 == 'PINK'}}selected{{/if}}>PINK</option>
		</select>
	</td>
-->



<!--
	<td><input type="text" name="birth" id="birth" class="w90p" value="\${birth }" class="app_birth" /></td> 
	<td>
		<input type="text" name="eng_last_name" id="eng_last_name" class="w20p" value="\${eng_last_name }" class="app_eng_last_name" />
		<input type="text" name="eng_first_name" id="eng_first_name" class="w60p" value="\${eng_first_name }" class="app_eng_first_name" />
	</td>

 -->
<script id="contestAppListTmpl" type="text/x-jquery-tmpl">
<tr>
	<th scope="row"><input type="checkbox" name="app_seq" class="chkApp" value="\${app_seq }"/></th>
	<td scope="row">\${rownum }</td>	
	<td scope="row">\${reg_dt }</td>
	<td>\${join_name }<br/>(\${reg_id })</td>
	<td><input type="text" name="telno" id="telno" class="w90p" value="\${telno }" /></td>
	<td>
		<select id="gender" name="gender">
			<option value="M"{{if gender == 'M'}}selected{{/if}}>남</option>
			<option value="F"{{if gender == 'F'}}selected{{/if}}>여</option>										
		</select>					
	</td>
	<td>
		<input type="text" name="zipcode" id="zipcode" class="w10p" value="\${zipcode }" class="app_zipcode" />
		<input type="text" name="addr" id="addr" class="w60p" value="\${addr }" class="app_addr" />
		<input type="text" name="addr_detail" id="addr_detail" class="w20p" value="\${addr_detail }" class="app_addr_detail" />
	</td>
	<td>
		<select id="option1" name="option1">
			<option value="1"{{if option1 == '1'}}selected{{/if}}>DOMINATION</option>
			<option value="2"{{if option1 == '2'}}selected{{/if}}>LOCK</option>
			<option value="3"{{if option1 == '3'}}selected{{/if}}>CODE</option>
			<option value="4"{{if option1 == '4'}}selected{{/if}}>GATE</option>
			<option value="5"{{if option1 == '5'}}selected{{/if}}>PHYSIX</option>
		</select>					
	</td>
	<td class="colorOpt">
		<select id="option2" name="option2">
			<option value="1"{{if option2 == '1'}}selected{{/if}}>IQ</option>
			<option value="2"{{if option2 == '2'}}selected{{/if}}>MARVEL</option>
			<option value="3"{{if option2 == '3'}}selected{{/if}}>PHAZE</option>
			<option value="4"{{if option2 == '4'}}selected{{/if}}>AXIOM</option>
		</select>					
	</td>
	<td>\${waiting_num }</td>
	<td>
		<select id="status" name="status" {{if status == '0003'}}style="color: red;"{{/if}} {{if status == '0004'}}style="color: blue;"{{/if}}>
			<option value="0003" {{if status == '0003'}}selected{{/if}}>신청취소</option>
			<option value="0004" {{if status == '0004'}}selected{{/if}}>선정</option>
			<option value="0005" {{if status == '0005'}}selected{{/if}}>대기</option>												
		</select>
	</td>
	<td>
		<a href="javascript:void(0);" data-seq="\${app_seq }" data-part="\${part }" data-status="\${status}" data-ctseq="\${ct_seq}"class="btnUpdt s-blue-btn">수정</a>
		<a href="javascript:void(0);" data-seq="\${app_seq }" data-part="\${part }" class="btnDel s-gry-btn">삭제</a>		
	</td>															
</tr>
</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>

<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${contestView.atch_file_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" name="arrayApp" id="arrayApp" value="" />
	<input type="hidden" name="currPart" id="currPart" value="1" />
	<input type="hidden" id="situation_show_yn" value="${contestView.situation_show_yn }" />
	

<div id="container">
	<h3>이벤트 대회 신청관리</h3>
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
						<td class="al_l" colspan="3">
							${contestView.ct_sbj }
						</td>
					</tr>
					<tr>
						<th>대회타입</th>
						<td class="al_l" colspan="3">
							<c:if test="${contestView.ct_type eq 'L' }">프리미엄 레슨</c:if>
							<c:if test="${contestView.ct_type eq 'A' }">프로암 이벤트</c:if>
							<c:if test="${contestView.ct_type eq 'S' }">이벤트</c:if>
						</td>
					</tr>
					<tr>
						<th>접수시작일(일자[시-분])</th>
						<td class="al_l">
							${contestView.app_start_dt } [${contestView.app_start_h }시 ${contestView.app_start_m }분]
						</td>
						<th>접수마감일(일자[시-분])</th>
						<td class="al_l">
							${contestView.app_end_dt } [${contestView.app_end_h }시 ${contestView.app_end_m }분]
						</td>
					</tr>
					<tr>
						<th>조별정원</th>
						<td class="al_l">
							${contestView.limit_part } 명
						</td>
						<th>대기자정원</th>
						<td class="al_l">
							${contestView.limit_waiting } 명
						</td>
					</tr>
					<tr>
						<th>노출여부</th>
						<td class="al_l">
							<c:if test="${contestView.use_at eq 'Y' }">노출</c:if>
							<c:if test="${contestView.use_at eq 'N' }">노출안함</c:if>								
						</td>
						<th>진행상태</th>
						<td class="al_l">
							<c:if test="${contestView.ct_process eq 'R' }">대회준비</c:if>
							<c:if test="${contestView.ct_process eq 'S' }">대회신청</c:if>
							<c:if test="${contestView.ct_process eq 'E' }">신청마감</c:if>
							<c:if test="${contestView.ct_process eq 'F' }">대회종료</c:if>
						</td>
					</tr>
					<tr>
						<th>대회일자</th>
						<td class="al_l">
							${contestView.ct_dt }								
						</td>
						<th>대회장소</th>
						<td class="al_l">
							${contestView.ct_place }								
						</td>
					</tr>
					<tr>
						<th>예비레인 사용유무</th>
						<td class="al_l">
							<c:if test="${contestView.prepare_yn eq 'Y'}">사용</c:if>
							<c:if test="${contestView.prepare_yn ne 'Y'}">사용안함</c:if>
						</td>
						<th>신청정보 수정가능 여부</th>
						<td class="al_l">
							<c:if test="${contestView.updt_yn eq 'Y' }">가능</c:if>
							<c:if test="${contestView.updt_yn ne 'Y' }">불가능</c:if>
						</td>
					</tr>
					<tr>
						<th>접수현황 노출여부</th>
						<td class="al_l" colspan="3" id="situation_wrap">
							<c:if test="${contestView.situation_show_yn eq 'Y'}"><span style="color: blue;">노출</span></c:if>
							<c:if test="${contestView.situation_show_yn ne 'Y'}"><span style="color: red;">노출안함</span></c:if>
						</td>
					</tr>
					<tr>
						<th>접수제한 리스트</th>
						<td class="al_l" colspan="3">
							<span style="color: red; margin: 5px 0px; font-weight: bold; ">※자신의 대회는 필수로 추가하셔야 합니다.</span>
							<div id="ct_select_list">
								<c:choose>
									<c:when test="${fn:length(ct_group_list) > 0}">
										<c:forEach items="${ct_group_list }" var="list">
											<p style="margin-top:5px;">${list.ct_sbj }</p>
										</c:forEach>
									</c:when>
								</c:choose>
							</div>
						</td>
					</tr>
					<tr>
						<th>신청취소마감일(일자-시-분)</th>
						<td class="al_l" colspan="3">
							${contestView.refund_finish_date } [${contestView.refund_finish_h }시 ${contestView.refund_finish_m }분]
						</td>
					</tr>
					
					<c:if test="${fn:length(pList) > 0 }">
						<tr>
							<th>프리미엄 레슨 이벤트</th>
							<td class="al_l" colspan="3">
								<table>
									<c:forEach items="${pList }" var="pList">
										<tr>
											<td><p>${pList.part_ord }조&nbsp;${pList.part_name }</p></td>
										</tr>
								</c:forEach>
								</table>
							</td>
						</tr>
					</c:if>
					<tr>
                       	<td colspan="6" class="cont">${contestView.ct_content }</td>
                    </tr>
                    <tr>
                       	<th>첨부파일</th>
                        <td colspan="5">
                           	<c:choose>
								<c:when test="${fn:length(contestFile) > 0}">
									<c:forEach items="${contestFile}" var="contestFile" varStatus="status">
										<a href="javascript:void(0);" onclick="Down('${contestFile.atch_file_id }');">${contestFile.orignl_file_nm }</a>
									</c:forEach>
								</c:when>
								<c:otherwise>첨부파일이 없습니다.</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
				</tbody>
			</table>
		</div>
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goSituationShow();" class="btn-ty1 black" style="background-color: olive; color: #fff; border-color: olive;">접수현황노출변경</a>
				<a href="javascript:goUserPageSee('${contestView.ct_seq }')" id="userPageSee" class="btn-ty1" style="background-color: #1284c7; color: #fff; background-color: #1284c7;">결과노출</a>
				<a href="javascript:goDelete()" class="btn-ty1 black">삭제</a>
				<a href="javascript:goModify()" class="btn-ty1 blue">수정</a>	
				<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
			</p>
		</div>
		<!-- //bbs-view -->
		<ul class="ct-tab">
			<c:if test="${contestView.ct_type eq 'L' && fn:length(pList) > 0}">
				<c:forEach items="${pList }" var="pList" varStatus="status">
					<li><a href="javascript:void(0);" class="btnAppPart <c:if test="${status.index == 0}"> on</c:if>" data-part="${pList.ecp_seq }">${pList.part_ord }조&nbsp;${pList.part_name }</a></li>
				</c:forEach>
			</c:if>
			<c:if test="${contestView.ct_type eq 'S'}">
				<li><a href="javascript:void(0);" class="btnAppPart on" data-part="1">전체</a></li>
			</c:if>
			<p class="bbs-total mb20"><a href="javascript:getExcel();" class="btn-ty2 excel fr">신청자 엑셀다운</a></p>
		</ul>		
		<fieldset class="bbs-list">
			<legend>신청자 목록</legend>
			<table summary="신청자 목록">
				<colgroup>
					<col width="3%">
					<col width="3%">
					<col width="8%">
					<col width="5%">
					<col width="8%">
					<col width="4%">
					<%-- 
					<col width="7%">
					<col width="10%">
					 --%>
					<col width="*">
					<col width="5%">
					<col width="5%" class="colorOpt">
					<col width="5%">
					<col width="4%">
					<col width="8%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><input type="checkbox" class="chkAppAll"/></th>
						<th scope="col">번호</th>
						<th scope="col">신청일자</th>
						<th scope="col">성명<br>(아이디)</th>
                        <th scope="col">연락처</th>
                        <th scope="col">성별</th>
                        <!-- 
                        <th scope="col">생년월일</th>
                        <th scope="col">영문이름</th>
                         -->
                        <th scope="col">주소</th>
                        <th scope="col">프리미어<br/>라인볼</th>
                        <th scope="col" class="colorOpt">마스터<br/>라인볼</th>
                        <th scope="col">대기번호</th>
                        <th scope="col">상태</th>
                        <th scope="col">관리</th>
					</tr>
				</thead>
				<tbody id="contestAppList">

				</tbody>
			</table>
		</fieldset>
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goAppDelete()" class="btn-ty1 black">선택삭제</a>
				<a href="javascript:goAppModify()" class="btn-ty1 blue">선택수정</a>
			</p>
		</div>
	</div>
</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>