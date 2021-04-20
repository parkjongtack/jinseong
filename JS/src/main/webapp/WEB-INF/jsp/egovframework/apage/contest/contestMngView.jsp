<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
var currPart = "1";

$(document).ready(function () {
	getAppListJson('1');
	
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
	
});


$(document).on("click",".btnUpdt",function() {
	var telno;
	var email;
	var handicap;
	var gender;
	var disable_yn;
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
	
	
	if($tr.find('#email').val() == ""){
		alert("이메일를 입력하세요");
		$tr.find('#email').fucus();
		return;
	}else{
		email = $tr.find('#email').val();
	}
	
	if($tr.find('#handicap').val() == ""){
		alert("핸디캡점수를 입력하세요");
		$tr.find('#handicap').fucus();
		return;
	}else{
		handicap = $tr.find('#handicap').val();
	}
	
	if(!birthFocusout($tr.find('input[name=birth]').val())){
		alert("생년월일을 확인하세요.\nYYYY-MM-DD 형식으로 입력해주세요.");
		$tr.find('input[name=birth]').focus();
		return false;
	}else{
		birth = $tr.find('input[name=birth]').val();
	}
	
	gender = $tr.find('#gender').val();
	disable_yn = $tr.find('#disable_yn').val();
	status = $tr.find('#status').val();
	var origin_status = $(this).attr("data-status");
	var app_seq = $(this).attr("data-seq");
	var part = $(this).attr("data-part");
	var ct_seq = $(this).attr("data-ctseq");
	var pay_flag =  $tr.find('#pay_flag').val();
	console.log(origin_status+"/"+lane);
	
	if(confirm("신청정보를 수정하시겠습니까?") == true){
		
		$.ajax({
			type : "POST",
			url : "/apage/contest/updateContestApp.do",
			data : {
					
				telno     :telno,
				email     :email,
				handicap   :handicap,
				gender    :gender,
				disable_yn:disable_yn,
				status    :status,
				app_seq   :app_seq,
				origin_status : origin_status,
				lane : lane,
				part : part,
				ct_seq : ct_seq,
				pay_flag : pay_flag,
				birth	:	birth
				
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
			url : "/apage/contest/deleteContestApp.do",
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
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestMngList.do'/>");
	$("#frm").submit();
}

function goModify(){
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestMngModify.do'/>");
	$("#frm").submit();
}

function goDelete(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestMngDelete.do'/>");
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
			url			: "/apage/contest/contestAppListJson.do",
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
					strhtml += "<tr><td colspan='16'>해당조 신청 인원이 없습니다.</td></tr>";
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
				url : "/apage/contest/deleteContestApp.do",
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
		var email;
		var handicap;
		var gender;
		var disable_yn;
		var status;
		var lane;
		var pay_flag;
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
							
				if($(this).find('#email').val() == ""){
					alert("이메일를 입력하세요");
					return false;
				}else{
					email = $(this).find('#email').val();
				}
				
				if($(this).find('#handicap').val() == ""){
					alert("핸디캡점수를 입력하세요");
					return false;
				}else{
					handicap = $(this).find('#handicap').val();
				}
				
				if(!birthFocusout($(this).find('input[name=birth]').val())){
					alert("생년월일을 확인하세요.\nYYYY-MM-DD 형식으로 입력해주세요.");
					$(this).find('input[name=birth]').focus();
					return false;
				}else{
					birth = $(this).find('input[name=birth]').val();
				}

				
				gender = $(this).find('#gender').val();
				disable_yn = $(this).find('#disable_yn').val();
				status = $(this).find('#status').val();
				var origin_status = $(this).find(".btnUpdt").attr("data-status");
				var app_seq = $(this).find(".btnUpdt").attr("data-seq");
				var part = $(this).find(".btnUpdt").attr("data-part");
				var ct_seq = $(this).find(".btnUpdt").attr("data-ctseq");
				pay_flag = $(this).find("#pay_flag").val();
				console.log(origin_status+"/"+lane);
				
				$.ajax({
					type : "POST",
					url : "/apage/contest/updateContestApp.do",
					data : {
							
						telno     :	telno,
						email     :	email,
						handicap  :	handicap,
						gender    :	gender,
						disable_yn:	disable_yn,
						status    :	status,
						app_seq   :	app_seq,
						origin_status : origin_status,
						lane : lane,
						part : part,
						ct_seq : ct_seq,
						pay_flag : pay_flag,
						birth : birth
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

//조 이동
function goAppPartMove(){
	var cnt = 0;
	var app_seq = "";
	$(".chkApp").each(function(){
		if($(this).is(":checked")){
			cnt++;
			app_seq += $(this).val() + ",";
		}
	})
	
	if(cnt > 0){
		var pop_title = "movePart";
        window.open("/apage/contest/contestMngPartPop.do", pop_title,'width=500,height=150') ;
        
        var frm = document.frm ;
        frm.arrayApp.value = app_seq.substring(0,app_seq.length - 1);
        frm.currPart = currPart;
        frm.target = pop_title ;
        frm.action = "/apage/contest/contestMngPartPop.do" ;
        frm.submit();
	}else{
		alert("체크된 데이터가 없습니다.");
		return;
	}
}

//신청자 엑셀출력
function getExcel(){
	
	$("#frm").attr("action","/apage/contest/contestAppListExcel.do");
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
			url		:	"<c:url value='/apage/contest/situationShowUpdate.do'/>",
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
</script>

<script id="contestAppListTmpl" type="text/x-jquery-tmpl">
<tr>
	<th scope="row"><input type="checkbox" class="chkApp" value="\${app_seq }"/></th>
	<td scope="row">\${rownum }</td>	
	<td scope="row">\${reg_dt }</td>
	<td>\${join_name }<br/>(\${reg_id })</td>
	<td>\${deposit_name }</td>
	<td><input type="text" name="telno" id="telno" class="w90p" value="\${telno }" /></td>
	<td>
		<select id="gender" name="gender">
			<option value="M"{{if gender == 'M'}}selected{{/if}}>남</option>
			<option value="F"{{if gender == 'F'}}selected{{/if}}>여</option>										
		</select>					
	</td>
	<td><input type="text" name="birth" id="birth" class="w90p" value="\${birth }" class="app_birth" /></td>
	<td><input type="text" name="email" id="email" class="w90p" value="\${email }"/></td>
	<td>
		<select id="disable_yn" name="disable_yn">
			<option value="N"{{if disable_yn == 'N'}}selected{{/if}}>해당없음</option>
			<option value="Y"{{if disable_yn == 'Y'}}selected{{/if}}>장애3급이상</option>										
		</select>		
	</td>
	<td><input type="text" name="handicap" id="handicap" class="w60p" value="\${handicap }" />점</td>
	<td><span class="lane_num">\${lane }</span></td>
	<td>\${waiting_num }</td>
	<td>
		<select id="status" name="status" {{if status == '0003'}}style="color: red;"{{/if}} {{if status == '0004'}}style="color: blue;"{{/if}}>
			<option value="0003" {{if status == '0003'}}selected{{/if}}>신청취소</option>
			<option value="0004" {{if status == '0004'}}selected{{/if}}>선정</option>
			<option value="0005" {{if status == '0005'}}selected{{/if}}>대기</option>												
		</select>
	</td>
	<td>
		<select id="pay_flag" name="pay_flag" {{if pay_flag == 'Y'}}style="color: blue;"{{/if}} {{if pay_flag == 'N'}}style="color: red;"{{/if}} {{if pay_flag == 'R'}}style="color: green;"{{/if}}>
			<option value="Y" {{if pay_flag == 'Y'}}selected{{/if}}>입금완료</option>
			<option value="N" {{if pay_flag == 'N'}}selected{{/if}}>입금대기</option>
			<option value="R" {{if pay_flag == 'R'}}selected{{/if}}>환불완료</option>
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
	<h3>대회신청관리</h3>
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
					
					
					<!-- 입금정보 -->
					<tr>
						<th>은행명</th>
						<td class="al_l">${contestView.ct_bank }</td>
						<th>예금주</th>
						<td class="al_l">${contestView.ct_acchholder }</td>
					</tr>
					<tr>
						<th>계좌번호</th>
						<td class="al_l">${contestView.ct_account }</td>
						<th>참가비</th>
						<td class="al_l">${contestView.ct_price }</td>
					</tr>
					<tr>
						<th>입금시작일(일자-시-분)</th>
						<td class="al_l">
							${contestView.ct_deposit_stdt } [${contestView.ct_deposit_sth }시 ${contestView.ct_deposit_stm }분]
						</td>
						<th>입금마감일(일자-시-분)</th>
						<td class="al_l">
							${contestView.ct_deposit_eddt } [${contestView.ct_deposit_edh }시 ${contestView.ct_deposit_edm }분]
						</td>
					</tr>
					<tr>
						<th>신청취소마감일(일자-시-분)</th>
						<td class="al_l" colspan="3">
							${contestView.refund_finish_date } [${contestView.refund_finish_h }시 ${contestView.refund_finish_m }분]
						</td>
					</tr>
					
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
				<a href="javascript:goDelete()" class="btn-ty1 black">삭제</a>
				<a href="javascript:goModify()" class="btn-ty1 blue">수정</a>	
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
		<fieldset class="bbs-list">
			<legend>신청자 목록</legend>
			<table summary="사업신청 결과제출 전체 목록">
				<colgroup>
					<col width="3%">
					<col width="3%">
					<col width="7%">
					<col width="7%">
					<col width="5%">
					<col width="8%">
					<col width="4%">
					<col width="8%">
					<col width="10%">
					<col width="7%">
					<col width="5%">
					<col width="5%">
					<col width="4%">
					<col width="8%">
					<col width="8%">
					<col width="8%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><input type="checkbox" class="chkAppAll"/></th>
						<th scope="col">번호</th>
						<th scope="col">신청일자</th>
						<th scope="col">성명<br>(아이디)</th>
                        <th scope="col">임금자명</th>
                        <th scope="col">연락처</th>
                        <th scope="col">성별</th>
                        <th scope="col">생년월일</th>
                        <th scope="col">이메일</th>
                        <th scope="col">장애여부</th>
                        <th scope="col">핸디</th>
                        <th scope="col">레인</th>
                        <th scope="col">대기번호</th>
                        <th scope="col">상태</th>
                        <th scope="col">결제유무</th>
                        <th scope="col">관리</th>
					</tr>
				</thead>
				<tbody id="contestAppList">

				</tbody>
			</table>
		</fieldset>
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goAppPartMove()" class="btn-ty1 light">조변경</a>
				<a href="javascript:goAppDelete()" class="btn-ty1 black">선택삭제</a>
				<a href="javascript:goAppModify()" class="btn-ty1 blue">선택수정</a>
			</p>
		</div>
	</div>
</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>