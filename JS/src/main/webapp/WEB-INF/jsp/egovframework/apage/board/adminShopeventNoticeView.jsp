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
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventList.do'/>");
	$("#frm").submit();
}

function goModify(){
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventNoticeModify.do'/>");
	$("#frm").submit();
}

function goDelete(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventNoticeDelete.do'/>");
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
	<input type="hidden" name="ntt_id" id="ntt_id" value="<c:out value='${boardView.ntt_id }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${boardView.atch_file_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>

<div id="container">
	<h3>세미나 공지사항</h3>
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
						<td class="al_l"><c:out value='${boardView.ntt_sj }'/></td>
						<th>작성자</th>
						<td class="al_l"><c:out value='${boardView.ntcr_id }'/></td>
					</tr>
					<tr>
						<th>사용여부</th>
						<td class="al_l" colspan="3">
							<c:if test="${boardView.use_at eq 'Y' }">사용</c:if>
							<c:if test="${boardView.use_at eq 'N' }">사용안함</c:if>
						</td>
					</tr>
					<tr>
						<th>작성일</th>
						<td class="al_l"><c:out value='${boardView.reg_dt }'/></td>
						<th>조회수</th>
						<td class="al_l"><c:out value='${boardView.rdcnt }'/></td>
					</tr>
					<tr>
						<th>사용자페이지 URL</th>
						<td class="al_l" colspan="3">
							<span>https://bowlingkorea.com/board/noticeDetail.do?ntt_id=<c:out value='${boardView.ntt_id }'/>&currRow=1</span>
						</td>
					</tr>
					<tr>
                       	<td colspan="6" class="cont">${boardView.ntt_cn }</td>
                    </tr>
                    <tr>
                       	<th>첨부파일</th>
                        <td colspan="5">
                           	<c:choose>
								<c:when test="${fn:length(ShopeventNotice) > 0}">
									<c:forEach items="${ShopeventNotice}" var="noticeList" varStatus="status">
										<a href="javascript:void(0);" onclick="Down('${noticeList.atch_file_id }');">${noticeList.orignl_file_nm }</a>
									</c:forEach>
								</c:when>
								<c:otherwise>첨부파일이 없습니다.</c:otherwise>
							</c:choose>
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

<jsp:include page="/apage/inc/footer.do"></jsp:include>