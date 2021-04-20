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
	$("#frm").attr("action", "<c:url value='/apage/member/adminList.do'/>");
	$("#frm").submit();
}

function goModify(){
	$("#frm").attr("action", "<c:url value='/apage/member/adminModify.do'/>");
	$("#frm").submit();
}
</script>

<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="mber_id" id="mber_id" value="<c:out value='${memberVo.mber_id }'/>" />
	<input type="hidden" name="scType" id="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>

<div id="container">
	<h3>관리자 계정 관리</h3>
	<div class="contents">
		<!-- //ct-tab -->
		<div class="bbs-view">
			<table>
				<colgroup>
					<col width="15%">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>아이디</th>
						<td class="al_l"><c:out value='${memberVo.mber_id }'/></td>
					</tr>
					<tr>
						<th>관리자명</th>
						<td class="al_l"><c:out value='${memberVo.mber_name }'/></td>
					</tr>
					<tr>
						<th>연락처</th>
						<td class="al_l"><c:out value='${memberVo.mber_tel }'/></td>
					</tr>
					<tr>
						<th>이메일</th>
						<td class="al_l"><c:out value='${memberVo.mber_email }'/></td>
					</tr>					
					<tr>
						<th>권한</th>
						<td class="al_l">
							<c:if test="${memberVo.auth_code eq 'super' }">총괄관리자</c:if>
							<c:if test="${memberVo.auth_code eq 'admin' }">대회관리자</c:if>
							<c:if test="${memberVo.auth_code eq 'board' }">게시판관리자</c:if>
						</td>
					</tr>					
					<tr>
						<th>상태</th>
						<td class="al_l">
							<c:if test="${memberVo.mber_status eq 'Y' }"><span style="color: blue;">사용</span></c:if>
							<c:if test="${memberVo.mber_status eq 'N' }"><span style="color: red;">사용중지</span></c:if>							
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- //attach -->
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goModify()" class="btn-ty1 blue">수정</a>
				<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
			</p>
		</div>
		<!-- //bbs-view -->
	</div>
</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>