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
	$("#frm").attr("action", "/apage/system/adminCodeList.do");
	$("#frm").submit();
}

function goBoardWrite(){
	
	if($("#cl_code").val()==""){
		alert("분류명을 선택하세요.");
		$("#cl_code").focus();
		return;
	}
	
	if($("#code_id_nm").val()==""){
		alert("코드명을 입력하세요.");
		$("#code_id_nm").focus();
		return;
	}
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "/apage/system/adminCodeReg.do");
	$("#frm").submit();
}
</script>
<form name="frm" id="frm" method="post" >
	<input type="hidden" name="reg_nm" value="<c:out value='${memberinfo.mber_name }'/>" />
<div id="container">
	<h3>공통코드관리</h3>
	<div class="contents">
		<div class="bbs-write">
			<table>
				<colgroup>
					<col width="10%" />
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>분류명</th>
						<td class="al_l">
							<select name="cl_code" id="cl_code" title="분류명 선택">
								<option value="">:: 분류명을 선택하세요. ::</option>
								<c:forEach items="${CLCodeList }" var="list" varStatus="status">
									<option value="${list.cl_code }">${list.cl_code_nm }</option>
				                </c:forEach>
							</select>
							<span style="color:red;">* 선택할 분류명이 없을경우 목록 좌측하단 분류명 추가를 눌러 분류명을 추가해주세요.</span>
						</td>
					</tr>
					<tr>
						<th>코드명</th>
						<td class="al_l"><input type="text" id="code_id_nm" name="code_id_nm" title="코드명 입력" maxlength="70"/></td>
					</tr>
					<tr>
						<th>작성자</th>
						<td class="al_l"><c:out value='${memberinfo.mber_name }'/></td>
					</tr>
					<tr>
						<th>사용여부</th>
						<td class="al_l">
							<input type="radio" id="open" name="use_yn" value="Y"  checked="checked"  /><label for="open">사용함</label>
							<input type="radio" id="close" name="use_yn" value="N" /><label for="close">사용안함</label>
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
</div>
</form>
<jsp:include page="/apage/inc/footer.do"></jsp:include>
		<c:if test="${!empty msg }" >
			<script type="text/javascript">
			var msg	= '${msg}'; 
			var board_gb = '${board_gb}';
			if(msg != ''){
				var aForm		= document.frm;
				if(msg.indexOf('success') >= 0){
					alert('저장 하였습니다.');
					aForm.action	= '/apage/sys/'+board_gb;
					aForm.submit();
				}else if(msg.indexOf('fail') >= 0){
					alert('등록이 취소 되었습니다.');
				}else{
					alert(msg);
				}
			}
			</script>
		</c:if>
	<!-- //pg-nav -->