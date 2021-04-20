<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">

$(document).ready(function () {
	

	
});


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/board/adminCenterList.do'/>");
	$("#frm").submit();
}

function goBoardWrite(){
	
	if($("#ntt_sj").val()==""){
		alert("센터명을 입력하세요.");
		$("#ntt_sj").focus();
		return ;
	}
	
	if($("#memo").val()==""){
		alert("연락처를 입력하세요.");
		$("#memo").focus();
		return ;
	}

	if($("#ntt_cn").val()==""){
		alert("주소를 입력하세요.");
		$("#ntt_cn").focus();
		return ;
	}
		
	
	$("#frm").attr("action", "<c:url value='/apage/board/adminCenterReg.do'/>");
	$("#frm").submit();
}


</script>

<form:form commandName="vo" name="frm" id="frm" method="post">
	<input type="hidden" name="ntcr_nm"  value="<c:out value='${memberinfo.mber_name }'/>" />
	<input type="hidden" name="ntcr_id"  value="<c:out value='${memberinfo.mber_id }'/>" />
	<div id="container">
		<h3>볼링장 등록</h3>
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
							<th>센터명</th>
							<td class="al_l" colspan="3">
								<input type="text" id="ntt_sj" name="ntt_sj" title="센터명 입력" class="w90p" maxlength="150" >
							</td>
						</tr>
						<tr>
							<th>지역</th>
							<td class="al_l" colspan="3">
								<select name="as_type" id="as_type">
								<option value="">::지역::</option>
								<option value="0010">서울</option>
								<option value="0020">경기</option>
								<option value="0030">인천</option>
								<option value="0040">충북</option>
								<option value="0050">충남</option>
								<option value="0060">대전</option>
								<option value="0070">경북</option>
								<option value="0080">경남</option>
								<option value="0090">대구</option>
								<option value="0100">울산</option>
								<option value="0110">부산</option>
								<option value="0120">전북</option>
								<option value="0130">전남</option>
								<option value="0140">광주</option>
								<option value="0150">강원</option>
								<option value="0160">제주</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>연락처</th>
							<td class="al_l" colspan="3">
								<input type="text" id="memo" name="memo" title="연락처 입력" class="w90p" maxlength="150" >
							</td>
						</tr>
						<tr>
							<th>주소</th>
							<td class="al_l" colspan="3">
								<input type="text" id="ntt_cn" name="ntt_cn" title="주소 입력" class="w90p" maxlength="150" >
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<!-- //bbs-write -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardWrite()" class="btn-ty1 black">등록</a>
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
				</p>
			</div>
		</div>
		<!-- //contents -->
	</div>
</form:form>
<c:if test="${!empty msg }" >
	<script type="text/javascript">
	var msg	= '${msg}'; 
	if(msg.indexOf('success') > 0){
		alert('등록되었습니다.');	
		location.href = '/apage/board/adminCenterList.do';
	}else if(msg.indexOf('fail') > 0){
		alert('실패하였습니다.');	
		location.href = '/apage/board/adminCenterList.do';
	}else if(msg.indexOf('delete') > 0){
		alert('삭제하였습니다.');	
		location.href = '/apage/board/adminCenterList.do';
	}
	</script>
</c:if>		
<jsp:include page="/apage/inc/footer.do"></jsp:include>