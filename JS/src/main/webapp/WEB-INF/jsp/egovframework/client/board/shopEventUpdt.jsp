<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>

<c:import url="/client/header.do" />
<style type="text/css">
label{
	margin-right: 10px;
}
</style>
<script type="text/javascript">
$(document).ready(function () {
	

	//이메일 선택
	$("#emailSelect").change(function() {
		var strVal = $(this).val();
		if (strVal == "") {
			$("#as_email2").val('');
			$("#as_email2").removeAttr("readonly");
			$("#as_email2").focus();
		} else {
			$("#as_email2").val('');
			$("#as_email2").val(strVal);
			$("#as_email2").attr("readonly", "readonly");
		}
	});
});

function goList() {
	$("#frm").attr("action", "<c:url value='/board/shopEventList.do'/>");
	$("#frm").submit();
}

function fileDelete(no){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/board/fileDel.do'/>");
	$("#atch_file_id2").val(no);
	$("#frm").submit();
}

function goUpdt() {

	if($("#ntt_sj").val()==""){
		alert("제목을 입력하세요.");
		$("#ntt_sj").focus();
		return ;
	}
	
	if($("#as_tel1").val()==""){
		alert("연락처를 입력하세요.");
		$("#as_tel1").focus();
		return ;
	}
	if($("#as_tel2").val()==""){
		alert("연락처를 입력하세요.");
		$("#as_tel2").focus();
		return ;
	}
	if($("#as_tel3").val()==""){
		alert("연락처를 입력하세요.");
		$("#as_tel3").focus();
		return ;
	}
	
	if($("#as_email1").val()==""){
		alert("이메일을 입력하세요.");
		$("#email1").focus();
		return ;
	}
	
	if($("#email2").val()==""){
		alert("이메일을 입력하세요.");
		$("#email2").focus();
		return ;
	}
	
	
	var $as_tel = $("#as_tel1").val() + '-' + $("#as_tel2").val() + '-' + $("#as_tel3").val(); 
	var regExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
	
	if(!regExp.test($as_tel)){
        //경고
        alert("전화번호를 확인해주세요.");
		return;
	}
	
	$("#as_tel").val($as_tel);
	$("#as_email").val($("#as_email1").val() + '@' + $("#as_email2").val());

	var dataString = $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/board/shopEventUpdt.do'/>");
	$("#frm").submit();
}

</script>

<div id="container" class="subpage">
	<div id="contents">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />
		<div class="sub_content">
			<div class="inner">
				<div class="sub_head">
					<h3 class="c_tit">세미나</h3>
				</div>
				<div class="board_area sub_fade">
					<div class="icoBx ic1">
						<p>(<span class="ft_or">*</span>), 정확히 입력하셔야 등록이 가능합니다.</p>
					</div>
					<form:form commandName="vo" id="frm" name="frm" method="post" enctype="multipart/form-data">
						<input type="hidden" name="ntt_id"  id="ntt_id" value="${boardView.ntt_id }" />	
						
						<input type="hidden" name="posblAtchFileNumber" value="3" />
						
						<input type="hidden" name="as_tel" id="as_tel" value="" /> <!-- 데이터 조합 결과용 -->
						<input type="hidden" name="as_email" id="as_email" value="" /> <!-- 데이터 조합 결과용 -->
						<input type="hidden" name="as_status" id="as_status" value="R" />
						<input type="hidden" name="ntup_id" value="${sessionScope.mberInfo.mber_id}" />
						<input type="hidden" name="ntup_nm" value="${sessionScope.mberInfo.mber_name}" />
						<input type="hidden" name="atch_file_id2" id="atch_file_id2"/>
						<input type="hidden" name="atch_file_id" id="atch_file_id" value="<c:out value='${boardView.atch_file_id }'/>" />
						
						<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
						<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
						<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
						
						<input type="hidden" name="open_yn" value="N"/>
						
						<div class="bbs_write">
							<div class="tbl_w">
								<table summary="게시판 글쓰기 표로 제목, 구분, 내용,첨부파일 등이 있습니다.">
									<caption>게시판 작성</caption>
									<colgroup>
										<col width="25%" class="">
										<col width="75%" class="">
									</colgroup>
									<tbody>
										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="subj">제목</label></th>
											<td><input id="ntt_sj" name="ntt_sj" type="text" value="${boardView.ntt_sj }"></td>
										</tr>
										<tr>
											<th scope="col"><label for="writer">작성자</label></th>
											<td><c:out value="${sessionScope.mberInfo.mber_name}" /></td>
										</tr>
										<%-- 
										<tr>
											<th scope="col">글 공개 여부</th>
											<td>
												<label style="margin-right:8px;" >
													<input type="radio" name="open_yn" id="open" value="Y" <c:if test="${boardView.open_yn eq 'Y' }">checked="checked"</c:if> />글 공개
												</label>
	                                            <label>
	                                            	<input type="radio" name="open_yn" id="close" value="N" <c:if test="${boardView.open_yn eq 'N' }">checked="checked"</c:if> />글 비공개
	                                            </label>
	                                        </td>	                                        
										</tr> 
										--%>
										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="phone">연락처</label></th>
											<td>
												<c:set var= "mber_tel" value="${fn:split(boardView.as_tel, '-')}"></c:set>
												<form:input path="as_tel1" class="w50" maxlength="3" value="${mber_tel[0]}" pattern="\d*" placeholder=""/><span class="insert"> - </span> 
												<form:input path="as_tel2" class="w50" maxlength="4" value="${mber_tel[1]}" pattern="\d*" placeholder=""/><span class="insert"> - </span> 
												<form:input path="as_tel3" class="w50" maxlength="4" value="${mber_tel[2]}" pattern="\d*" placeholder=""/>													
											</td>
										</tr>
										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="email">이메일</label></th>
											<td class="w_email">
												<form:input path="as_email1" class="w200" maxlength="30" value="${fn:substring(boardView.as_email,0,fn:indexOf(boardView.as_email,'@')) }" />@ 
												<form:input path="as_email2" class="w200" value="${fn:substring(boardView.as_email,fn:indexOf(boardView.as_email,'@')+1,fn:length(boardView.as_email)) }" /> 
												<select id="emailSelect">
													<option value="">::직접입력::</option>
													<option value="naver.com">naver.com</option>
													<option value="nate.com">nate.com</option>
													<option value="gmail.com">gmail.com</option>
													<option value="korea.com">korea.com</option>
													<option value="paran.com">paran.com</option>
													<option value="hanmail.net">hanmail.net</option>
													<option value="hotmail.com">hotmail.com</option>
												</select>
											</td>
										</tr>
										
										<tr>
											<th scope="col"><label for="temp1">소속 볼링장<br/>(없으면 공란)</label></th>
											<td>
												<input id="temp1" name="temp1" type="text" value="${boardView.temp1 }"  maxlength="100">
											</td>
										</tr>
										<tr>
											<th scope="col"><label for="temp2">보유 정비기계<br/>(선택사항)</label></th>
											<td>
												<input id="temp2" name="temp2" type="text" value="${boardView.temp2 }"  maxlength="100">
											</td>
										</tr>
										<tr>
											<th scope="col"><span class="ft_or">*</span><label for="temp3">참석여부</label></th>
											<td>
												<input type="radio" id="temp3_1" name="temp3" value="1"  <c:if test="${boardView.temp3 eq '1'}">checked="checked"</c:if>><label  for="temp3_1">1부 참석</label>
												<input type="radio" id="temp3_2" name="temp3" value="2" <c:if test="${boardView.temp3 eq '2'}">checked="checked"</c:if>><label  for="temp3_2">2부 참석</label>
												<input type="radio" id="temp3_3" name="temp3" value="3" <c:if test="${boardView.temp3 eq '3'}">checked="checked"</c:if>><label  for="temp3_3">1부,2부 참석</label>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- 
                            <div class="agree">
                                <p><input type="checkbox" id="agree"><label for="agree">개인정보 수집 및 정보제공동의 </label> <a href="javscript:viewLayer();" id="pop_txt">전문보기</a></p>
                            </div>
                             -->
						</div>
					</form:form>
					<!--BOARD_WRITE //E -->

					<div class="btn_r2">
						<a href="javascript:goUpdt();" class="">수정</a> 
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