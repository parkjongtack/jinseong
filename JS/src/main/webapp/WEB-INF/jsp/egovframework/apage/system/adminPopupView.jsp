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
	$("#frm").attr("action", "<c:url value='/apage/system/adminPopupList.do'/>");
	$("#frm").submit();
}

function goModify(){
	$("#frm").attr("action", "<c:url value='/apage/system/adminPopupModify.do'/>");
	$("#frm").submit();
}

function goDelete(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/system/adminPopupDelete.do'/>");
	$("#frm").submit();
}
</script>
<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="pop_seq" id="pop_seq" value="<c:out value='${PopView.pop_seq }'/>" />
	<%-- <input type="hidden" name="scType" id="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="scType2" id="scType2"  value="<c:out value='${scType2 }'/>"/>
	<input type="hidden" name="scType3" id="scType3"  value="<c:out value='${scType3 }'/>"/> --%>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>

<div id="container">
	<h3>팝업 관리</h3>
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
						<th>제목</th>
						<td class="al_l"><c:out value='${PopView.pop_subject }'/></td>
					</tr>
					<tr>
						<th>팝업창 url</th>
						<td class="al_l"><c:out value='${PopView.pop_url }'/></td>
					</tr>
					<tr>
						<th>팝업창 사이즈</th>
						<td class="al_l"><c:out value='${PopView.pop_width }'/> px * <c:out value='${PopView.pop_height }'/> px</td>
					</tr>
					<tr>
						<th>팝업창 위치</th>
						<td class="al_l"><c:out value='${PopView.pop_position_x }'/> px * <c:out value='${PopView.pop_position_y }'/> px</td>
					</tr>
					<tr>
						<th>게시기간</th>
						<td class="al_l"><c:out value='${PopView.pop_st_dt }'/> ~ <c:out value='${PopView.pop_ed_dt }'/></td>
					</tr>
					<tr>
						<th>게시상태</th>
						<td class="al_l">
							<c:if test="${PopView.pop_state eq 'Y' }">게시</c:if>
							<c:if test="${PopView.pop_state eq 'N' }">종료</c:if>
						</td>
					</tr>
					<tr>
						<th>쿠키 설정여부</th>
						<td class="al_l">
							<c:if test="${PopView.pop_set eq 'Y' }">Y</c:if>
							<c:if test="${PopView.pop_set eq 'N' }">N</c:if>
						</td>
					</tr>
					<tr>
                       	<td colspan="6" class="cont">${PopView.pop_content }</td>
                    </tr>
				</tbody>
			</table>
		</div>

		<!-- //attach -->
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goDelete()" class="btn-ty1 black">삭제</a>
				<a href="javascript:goModify()" class="btn-ty1 blue">수정</a>
				<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
			</p>
		</div>
		<!-- //bbs-view -->
	</div>
</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>