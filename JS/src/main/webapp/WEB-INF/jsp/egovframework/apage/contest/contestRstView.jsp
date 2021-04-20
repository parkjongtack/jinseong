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
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestRstList.do'/>");
	$("#frm").submit();
}

function goModify(){
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestRstModify.do'/>");
	$("#frm").submit();
}

function goDelete(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestRstDelete.do'/>");
	$("#frm").submit();
}

function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}
</script>
<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>
<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="ntt_id" id="ntt_id" value="<c:out value='${contestRstView.ntt_id }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${contestView.atch_file_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>

<div id="container">
	<h3>대회결과</h3>
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
						<th>대회명</th>
						<td class="al_l" colspan="3">
							${contestRstView.ntt_sj }
						</td>
					</tr>
					<tr>
						<th>요약</th>
						<td class="al_l" colspan="3">
							${contestRstView.memo }							
						</td>
					</tr>
					<tr>
						<th>대표이미지</th>
						<td class="al_l" colspan="3">
	                		<c:choose>
								<c:when test="${fn:length(imgFile) > 0}">
									<c:forEach items="${imgFile}" var="contestFile" varStatus="status">
										<a href="javascript:void(0);" onclick="Down('${contestFile.atch_file_id }');">${contestFile.orignl_file_nm }</a>	
										<img src="/commonfile/nunFileDown.do?atch_file_id=${contestFile.atch_file_id }" alt="대표이미지" width="200px"/>									
									</c:forEach>
								</c:when>
								<c:otherwise>첨부파일이 없습니다.</c:otherwise>
							</c:choose>
						</td>
					</tr>						
					<tr>
						<th>이미지</th>
						<td class="al_l" colspan="3">
	                		<c:choose>
								<c:when test="${fn:length(addFile) > 0}">
									<c:forEach items="${addFile}" var="contestFile" varStatus="status">
										<a href="javascript:void(0);" onclick="Down('${contestFile.atch_file_id }');">${contestFile.orignl_file_nm }</a>
									</c:forEach>
								</c:when>
								<c:otherwise>첨부파일이 없습니다.</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr class="content">							
						<td class="cont al_l"  colspan="4">
							${contestRstView.ntt_cn }
						</td>
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
<c:if test="${!empty msg }" >
	<script type="text/javascript">
	var msg	= '${msg}'; 
	if(msg != ''){
		alert(msg);	
		location.href = '/apage/contest/adminContestRstList.do';
	}
	</script>
</c:if>
<jsp:include page="/apage/inc/footer.do"></jsp:include>