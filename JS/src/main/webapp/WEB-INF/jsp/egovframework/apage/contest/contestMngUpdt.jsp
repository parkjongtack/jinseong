<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
var oEditors = [];
$(document).ready(function () {
	
	nhn.husky.EZCreator.createInIFrame({
	    oAppRef: oEditors,
	    elPlaceHolder: "ct_content",
	    sSkinURI: "/resources/se2/SmartEditor2Skin.html",
	    htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			fOnBeforeUnload : function(){}
		},	
	    fCreator: "createSEditor2"
	});
	
	  var existFileNum = document.frm.FileCnt.value;
	  var maxFileNum = document.frm.posblAtchFileNumber.value;
	  var uploadableFileNum = maxFileNum - existFileNum; // 최대등록가능한 파일숫자에서 기존에 등록된 숫자를 뺀다.
	  if(uploadableFileNum<0) {
	    uploadableFileNum = 0;
	  }
	  if(uploadableFileNum != 0){
	 $("#egovComFileUploader").css('display','');
	    var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList' ), uploadableFileNum );
	    multi_selector.addElement( document.getElementById( 'egovComFileUploader' ) );
	  }else{
	  $("#egovComFileUploader").css('display','none');
	  }  
	  
	  $('.datepicker').removeClass('hasDatepicker').datepicker({
		showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
		  buttonImage: "/resources/apage/images/board/ic_date.gif", // 버튼 이미지
		  buttonImageOnly: true  // 버튼에 있는 이미지만 표시한다.		
	});
	  
	  $(".ct_sbj").on("click",function(){
		   if($(this).parent().find("input:checkbox[name=ct_checked_val]").is(":checked")){
			   $(this).parent().find("input[name=ct_checked_val]").prop("checked",false);
		   }else{
			   $(this).parent().find("input[name=ct_checked_val]").prop("checked",true);
		   }
	   })
	   
	   $("#ct_checked_all").on("click",function(){
		   if($(this).is(":checked")){
			   $("input[name=ct_checked_val]").prop("checked",true);
		   }else{
			   $("input[name=ct_checked_val]").prop("checked",false);
		   }
	   })
	   
	   
	   var ct_gp_arr = "${contestView.ct_group }".split(",");
	   if(ct_gp_arr != ''){
		   $("input[name=ct_checked_val]").each(function(){
			   for(var i = 0; i < ct_gp_arr.length; i++){
				   if($(this).val() == ct_gp_arr[i]){
					   $(this).prop("checked",true);
				   }
			   }
		   })
	   }
	  
});

function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestMngList.do'/>");
	$("#frm").submit();
}

function goBoardUpdate(){
	if($("#ct_sbj").val()==""){
		alert("제목을 입력하세요.");
		$("#ct_sbj").focus();
		return ;
	}
	if($("#ct_process").val()==""){
		alert("진행상태를 선택하세요.");
		$("#ct_process").focus();
		return ;
	}
	if($("#ct_dt").val()==""){
		alert("대회일자를 입력하세요.");
		$("#ct_dt").focus();
		return ;
	}
	if($("#ct_place").val()==""){
		alert("대회장소를 입력하세요.");
		$("#ct_place").focus();
		return ;
	}
	var use_at = $('input:radio[name="use_at"]:checked').val();
	
	if(use_at == undefined || use_at == null || use_at == ""){
		alert("노출여부를 선택해주세요.");
		return;
	}
	
	oEditors.getById["ct_content"].exec("UPDATE_CONTENTS_FIELD", []);
	
	
	if($("input[name=ct_group]").val() == ''){
		$("input[name=ct_group]").val(0);
	}
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestMngUpdt.do'/>");
	$("#frm").submit();
}

function fileDelete(no){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/contest/ContestMngFileDel.do'/>");
	$("#atch_file_id2").val(no);
	$("#frm").submit();
}




function goCtReg(){
	var ct_val = '';
	var ct_html = '';
	$("input:checkbox[name=ct_checked_val]").each(function(){
		if($(this).is(":checked")){
 			if(ct_val == null || ct_val == ""){
				ct_val = $(this).val();
 			}else{
				ct_val += ","+$(this).val();
 			}
 			
 			ct_html += '<p style="margin-top:5px;">'+$(this).parent().parent().find(".ct_sbj").html()+'<a href="javascript:propChecked('+$(this).val()+')" class="ct_cancel btn-ty2 cancel" data-ctSeq="'+$(this).val()+'" style="margin-left:5px;">취소</a></p>';
 			
		}
	})
	
	$("#ct_select_list").html(ct_html);
	$("input[name=ct_group]").val(ct_val);
	$("#pop-ebk").css("display","none");
}

function propChecked(val){
	$("input:checkbox[name=ct_checked_val]").each(function(){
		if($(this).val() == val){
			$(this).prop("checked",false);
		}
	})
	goCtReg();
}


function findContest(){
	$("#pop-ebk").css("display","block");
//	$("#pop-ebk").find("input[type=checkbox]").prop("checked",false);
}


</script>
<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="atch_file_id2" id="atch_file_id2"/>
	<input type="hidden" name="atch_file_id" id="atch_file_id" value="<c:out value='${contestView.atch_file_id }'/>" />
	<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
	<input type="hidden" name="updt_id"  value="<c:out value='${memberinfo.mber_id }'/>" />
	<input type="hidden" name="posblAtchFileNumber" value="3" />
	<input type="hidden" name="FileCnt" value="${fn:length(contestFile)}">
	<input type="hidden" name="ct_group" value="${contestView.ct_group }"/>
	<div id="container">
		<h3>대회신청관리</h3>
		<div class="contents">
			<!-- //ct-tab -->
        
        	<div class="bbs-write">
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
								<input type="text" id="ct_sbj" name="ct_sbj" title="제목 입력" class="w90p" maxlength="150" value="${contestView.ct_sbj }" >
							</td>
						</tr>
						<tr>
							<th>접수시작일(일자-시-분)</th>
							<td class="al_l">
								<input type="text" class="datepicker w20p" id="app_start_dt" name="app_start_dt" value="${contestView.app_start_dt }" />
								<select title="접수시작일 시간 선택" name="app_start_h" id="app_start_h"  class="w20p">
                                	<option value="">시</option>
                                	<c:forEach var="si" begin="1" end="23">
	                                	<c:choose>
	                                		<c:when test="${si < 10}">
	                                			<option value="<c:out value='0${si}' />" <c:set var="now_si" value="0${si}" /> <c:if test="${now_si eq contestView.app_start_h }"> selected </c:if>><c:out value="0${si}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${si}' />" <c:if test="${si eq contestView.app_start_h }"> selected </c:if>><c:out value="${si}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>
                                <select title="접수시작일 분 선택" name="app_start_m" id="app_start_m"  class="w20p">
                                	<option value="">분</option>
                                	<c:forEach var="min" begin="0" end="59">
                                		<c:choose>
	                                		<c:when test="${min < 10}">
	                                			<option value="<c:out value='0${min}' />" <c:set var="now_min" value="0${min}" /> <c:if test="${now_min eq contestView.app_start_m }"> selected </c:if>><c:out value="0${min}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${min}' />" <c:if test="${min eq contestView.app_start_m }"> selected </c:if>><c:out value="${min}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>	
							</td>
							<th>접수마감일(일자-시-분)</th>
							<td class="al_l">
								<input type="text" class="datepicker w20p" id="app_end_dt" name="app_end_dt" value="${contestView.app_end_dt }" />
								<select title="접수마감 시간 선택" name="app_end_h" id="app_end_h"  class="w20p">
                                	<option value="">시</option>
                                	<c:forEach var="si" begin="1" end="23">
	                                	<c:choose>
	                                		<c:when test="${si < 10}">
	                                			<option value="<c:out value='0${si}' />" <c:set var="now_si" value="0${si}" /> <c:if test="${now_si eq contestView.app_end_h }"> selected </c:if>><c:out value="0${si}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${si}' />" <c:if test="${si eq contestView.app_end_h }"> selected </c:if>><c:out value="${si}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>
                                <select title="접수시작일 분 선택" name="app_end_m" id="app_end_m"  class="w20p">
                                	<option value="">분</option>
                                	<c:forEach var="min" begin="0" end="59">
                                		<c:choose>
	                                		<c:when test="${min < 10}">
	                                			<option value="<c:out value='0${min}' />" <c:set var="now_min" value="0${min}" /> <c:if test="${now_min eq contestView.app_end_m }"> selected </c:if>><c:out value="0${min}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${min}' />" <c:if test="${min eq contestView.app_end_m }"> selected </c:if>><c:out value="${min}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>	
							</td>
						</tr>
						<tr>
							<th>조별정원</th>
							<td class="al_l">
								<input type="number" id="limit_part" name="limit_part" title="조별정원 입력" class="w60p" value="${contestView.limit_part }" > 명
							</td>
							<th>대기자정원</th>
							<td class="al_l">
								<input type="number" id="limit_waiting" name="limit_waiting" title="대기자정원 입력" class="w60p" value="${contestView.limit_waiting }" > 명
							</td>
						</tr>
						<tr>
							<th>노출여부</th>
							<td class="al_l">
								<input type="radio" id="close" name="use_at" value="Y" <c:if test="${contestView.use_at eq 'Y' }">checked</c:if> /><label for="close">노출</label>
								<input type="radio" id="open" name="use_at" value="N" <c:if test="${contestView.use_at eq 'N' }">checked</c:if> /><label for="open">노출안함</label>
							</td>
							<th>진행상태</th>
							<td class="al_l">
								<select id="ct_process" name="ct_process">									
									<option value="R" <c:if test="${contestView.ct_process eq 'R' }">selected</c:if>>대회준비</option>
									<option value="S" <c:if test="${contestView.ct_process eq 'S' }">selected</c:if>>대회신청</option>
									<option value="E" <c:if test="${contestView.ct_process eq 'E' }">selected</c:if>>신청마감</option>
									<option value="F" <c:if test="${contestView.ct_process eq 'F' }">selected</c:if>>대회종료</option>									
								</select>
							</td>
						</tr>
						<tr>
							<th>대회일자</th>
							<td class="al_l">
								<input type="text" class="datepicker w50p" id="ct_dt" name="ct_dt" value="${contestView.ct_dt }" />
							</td>
							<th>대회장소</th>
							<td class="al_l">
								<input type="text" class="w90p" id="ct_place" name="ct_place" value="${contestView.ct_place }" />
							</td>
						</tr>
						<tr>
							<th>예비레인 사용유무</th>
							<td class="al_l">
								<label><input type="radio" name="prepare_yn" value="Y" <c:if test="${contestView.prepare_yn eq 'Y' }">checked="checked"</c:if>>사용</label>
								<label><input type="radio" name="prepare_yn" value="N" <c:if test="${contestView.prepare_yn ne 'Y' }">checked="checked"</c:if>>사용안함</label>
							</td>
							<th>신청정보 수정가능 여부</th>
							<td class="al_l">
								<label><input type="radio" name="updt_yn" value="Y" <c:if test="${contestView.updt_yn eq 'Y' }">checked="checked"</c:if>>가능</label>
								<label><input type="radio" name="updt_yn" value="N" <c:if test="${contestView.updt_yn ne 'Y' }">checked="checked"</c:if>>불가능</label>
							</td>
						</tr>
						<tr>
							<th>접수현황 노출여부</th>
							<td class="al_l" colspan="3">
								<label><input type="radio" name="situation_show_yn" value="Y" <c:if test="${contestView.situation_show_yn eq 'Y' }">checked="checked"</c:if>>노출</label>
								<label><input type="radio" name="situation_show_yn" value="N" <c:if test="${contestView.situation_show_yn ne 'Y' }">checked="checked"</c:if>>노출안함</label>
							</td>
						</tr>
						<tr>
							<th>접수제한 리스트</th>
							<td class="al_l" colspan="3">
								<a href="javascript:findContest();" class="btn-ty2 select">대회 리스트</a>
								<span style="color: red; margin: 5px 0px; font-weight: bold; ">※자신의 대회는 필수로 추가하셔야 합니다.</span>
								<div id="ct_select_list">
									<c:choose>
										<c:when test="${fn:length(ct_group_list) > 0}">
											<c:forEach items="${ct_group_list }" var="list">
												<p style="margin-top:5px;">${list.ct_sbj }<a href="javascript:propChecked('${list.ct_seq }')" class="ct_cancel btn-ty2 cancel" data-ctseq="${list.ct_seq }" style="margin-left:5px; display: inline-block;">삭제</a></p>
											</c:forEach>
										</c:when>
									</c:choose>
								</div>
							</td>
						</tr>
						
						
							<!-- 입금정보 -->
						<tr>
							<th>은행명</th>
							<td class="al_l">
								<input type="text" class="w50p" id="ct_bank" name="ct_bank" value="${contestView.ct_bank }" />
							</td>
							<th>예금주</th>
							<td class="al_l">
								<input type="text" class="w50p" id="ct_acchholder" name="ct_acchholder"  value="${contestView.ct_acchholder }"/>
							</td>
						</tr>
						<tr>
							<th>계좌번호</th>
							<td class="al_l">
								<input type="text" class="w50p" id="ct_account" name="ct_account"  value="${contestView.ct_account }" />
							</td>
							<th>참가비</th>
							<td class="al_l">
								<input type="text" class="w50p" id="ct_price" name="ct_price"  value="${contestView.ct_price }"/>
							</td>
						</tr>
						<tr>
							<th>입금시작일(일자-시-분)</th>
							<td class="al_l">
								<input type="text" class="datepicker w20p" id="ct_deposit_stdt" name="ct_deposit_stdt" value="${contestView.ct_deposit_stdt }" />
								<select title="입금시작일 시간 선택" name="ct_deposit_sth" id="ct_deposit_sth"  class="w20p">
                                	<option value="">시</option>
                                	<c:forEach var="si" begin="1" end="23">
	                                	<c:choose>
	                                		<c:when test="${si < 10}">
	                                			<option value="<c:out value='0${si}' />" <c:set var="now_si" value="0${si}" /> <c:if test="${now_si eq contestView.ct_deposit_sth }"> selected </c:if>><c:out value="0${si}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${si}' />" <c:if test="${si eq contestView.ct_deposit_sth }"> selected </c:if>><c:out value="${si}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>
                                <select title="입금시작일 분 선택" name="ct_deposit_stm" id="ct_deposit_stm"  class="w20p">
                                	<option value="">분</option>
                                	<c:forEach var="min" begin="0" end="59">
                                		<c:choose>
	                                		<c:when test="${min < 10}">
	                                			<option value="<c:out value='0${min}' />" <c:set var="now_min" value="0${min}" /> <c:if test="${now_min eq contestView.ct_deposit_stm }"> selected </c:if>><c:out value="0${min}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${min}' />" <c:if test="${min eq contestView.ct_deposit_stm }"> selected </c:if>><c:out value="${min}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>	
							</td>
							<th>입금마감일(일자-시-분)</th>
							<td class="al_l">
								<input type="text" class="datepicker w20p" id="ct_deposit_eddt" name="ct_deposit_eddt" value="${contestView.ct_deposit_eddt }" />
								<select title="입금마감일 시간 선택" name="ct_deposit_edh" id="ct_deposit_edh"  class="w20p">
                                	<option value="">시</option>
                                	<c:forEach var="si" begin="1" end="23">
	                                	<c:choose>
	                                		<c:when test="${si < 10}">
	                                			<option value="<c:out value='0${si}' />" <c:set var="now_si" value="0${si}" /> <c:if test="${now_si eq contestView.ct_deposit_edh }"> selected </c:if>><c:out value="0${si}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${si}' />" <c:if test="${si eq contestView.ct_deposit_edh }"> selected </c:if>><c:out value="${si}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>
                                <select title="입금마감일 분 선택" name="ct_deposit_edm" id="ct_deposit_edm"  class="w20p">
                                	<option value="">분</option>
                                	<c:forEach var="min" begin="0" end="59">
                                		<c:choose>
	                                		<c:when test="${min < 10}">
	                                			<option value="<c:out value='0${min}' />" <c:set var="now_min" value="0${min}" /> <c:if test="${now_min eq contestView.ct_deposit_edm }"> selected </c:if>><c:out value="0${min}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${min}' />" <c:if test="${min eq contestView.ct_deposit_edm }"> selected </c:if>><c:out value="${min}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>	
							</td>
						</tr>
						
						<tr>
							<th>신청취소마감일(일자-시-분)</th>
							<td class="al_l">
								<input type="text" class="datepicker w20p" id="refund_finish_date" name="refund_finish_date" value="${contestView.refund_finish_date }" />
								<select title="신청취소마감일 시간 선택" name="refund_finish_h" id="refund_finish_h"  class="w20p">
                                	<option value="">시</option>
                                	<c:forEach var="si" begin="1" end="23">
	                                	<c:choose>
	                                		<c:when test="${si < 10}">
	                                			<option value="<c:out value='0${si}' />" <c:set var="now_si" value="0${si}" /> <c:if test="${now_si eq contestView.refund_finish_h }"> selected </c:if>><c:out value="0${si}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${si}' />" <c:if test="${si eq contestView.refund_finish_h }"> selected </c:if>><c:out value="${si}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>
                                <select title="신청취소마감일 분 선택" name="refund_finish_m" id="refund_finish_m"  class="w20p">
                                	<option value="">분</option>
                                	<c:forEach var="min" begin="0" end="59">
                                		<c:choose>
	                                		<c:when test="${min < 10}">
	                                			<option value="<c:out value='0${min}' />" <c:set var="now_min" value="0${min}" /> <c:if test="${now_min eq contestView.refund_finish_m }"> selected </c:if>><c:out value="0${min}" /></option>
	                                		</c:when>
	                                		<c:otherwise>
	                                			<option value="<c:out value='${min}' />" <c:if test="${min eq contestView.refund_finish_m }"> selected </c:if>><c:out value="${min}" /></option>
	                                		</c:otherwise>
	                                	</c:choose>
									</c:forEach>
                                </select>	
							</td>
						</tr>
						
						
						<tr class="content">
							<td class="cont al_l" colspan="4">
								<textarea id="ct_content" name="ct_content" style="width:100%;height:320px;" title="내용 입력"><c:out value='${contestView.ct_content }'/></textarea>
							</td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td class="al_l" colspan="4">
		                		<input name="file_1" id="egovComFileUploader" type="file" />
		                		<c:choose>
									<c:when test="${fn:length(contestFile) > 0}">
										<c:forEach items="${contestFile}" var="contestFile" varStatus="status">
										<div>
											<a href="javascript:void(0);" onclick="Down('${contestFile.atch_file_id }');">${contestFile.orignl_file_nm }</a>
											<input type="button" title="삭제" value="Delete" class="file_cla" style='cursor:pointer;' onclick="fileDelete('${contestFile.atch_file_id }');" /><br>
										</div>
										</c:forEach>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
		                		<div id="egovComFileList"></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<!-- //bbs-write -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardUpdate()" class="btn-ty1 black">수정</a>
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
				</p>
			</div>
		</div>
		<!-- //contents -->
	</div>
</form:form>
		
		
		
<div id="pop-ebk" style="display: none;">
	<h5>중복제한 대회선택</h5>
	<div class="edite">
		<div class="inner-write mb20">
			<h6>
				<span>대회 리스트</span> 
				<span style="color: red;">※대회 상태가 신청마감, 대회종료가 아닌 대회 리스트입니다.</span>
			</h6>
			<table summary="">
				<colgroup>
					<col width="8%">
					<col width="*">
					<col width="*">
					<col width="*">
				</colgroup>
				<thead>
					<tr>
						<th>
							<label><input type="checkbox" id="ct_checked_all">전체</label>
						</th>
						<th>대회명</th>
						<th>대회상태</th>
						<th>대회일자</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(contest_list) > 0}">
							<c:forEach items="${contest_list }" var="list">
								<tr class="ct_check_tr" style="cursor: pointer;">
									<td style="text-align: center;">
										<input type="checkbox" name="ct_checked_val" value="${list.ct_seq }">
									</td>
									<td class="ct_sbj">${list.ct_sbj }</td>
									<td>
										<c:if test="${list.ct_process eq 'R' }">대회준비</c:if>
										<c:if test="${list.ct_process eq 'S' }">대회신청</c:if>
										<c:if test="${list.ct_process eq 'E' }">신청마감</c:if>
										<c:if test="${list.ct_process eq 'F' }">대회종료</c:if>
									</td>
									<td>${list.ct_dt}</td>
								</tr>
							</c:forEach>
						</c:when>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- //bbs-write-->
		<span class="fr"> <a href="javascript:goCtReg()"
			class="btn-ty2 blue">선택완료</a>
		</span>
	</div>
	<!-- //edit-->
	<p class="close">
		<a href="javascript:goCtReg()">X</a>
	</p>
</div>
		
<jsp:include page="/apage/inc/footer.do"></jsp:include>