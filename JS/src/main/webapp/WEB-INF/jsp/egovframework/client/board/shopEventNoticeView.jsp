<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>

<c:import url="/client/header.do" />

<script type="text/javascript">
	function goBoardList() {
		$("#frm").attr("action", "<c:url value='/board/shopEventList.do'/>");
		$("#frm").submit();
	}

	function Down(no) {
		$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
		$("#atch_file_id").val(no);
		$("#frm1").submit();
	}
</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id" />
</form>

<form:form commandName="vo" name="frm" id="frm">
	<input type="hidden" name="ntt_id" id="ntt_id" value="<c:out value='${boardView.ntt_id }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${boardView.atch_file_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input" value="<c:out value='${Srch_input }'/>" />
	<input type="hidden" name="scType" value="<c:out value='${scType }'/>" />
	<input type="hidden" name="currRow" value="<c:out value='${currPage }'/>" />
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
			<h2 class="hide">본문</h2>
			<c:import url="/client/snb.do" />
			<div class="sub_content">
				<div class="inner">
					<div class="sub_head">
						<h3 class="c_tit">세미나 공지사항</h3>
					</div>

					<div class="board_view_area sub_fade">
						<div class="bbs_view">
							<div class="view_head">
								<dl class="subject">
									<dt>
										<c:out value='${boardView.ntt_sj }' />
									</dt>
									<dd class="info">
										<span><strong><c:out value='${boardView.ntcr_nm }' /></strong></span><span><c:out value='${boardView.reg_dt }' /></span><span><c:out value='${boardView.rdcnt }' /></span>
									</dd>
								</dl>
								<dl class="file">
									<dt class="t_tit">첨부파일 : </dt>
									<dd>
									<c:choose>
										<c:when test="${fn:length(noticeFile) > 0}">
											<c:forEach items="${noticeFile}" var="noticeList" varStatus="status">
												<a href="javascript:void(0);" onclick="Down('${noticeList.atch_file_id }');" class="attach"><img src="/resources/client/images/contents/file.gif" alt="첨부파일 아이콘">${noticeList.orignl_file_nm }</a>
											</c:forEach>
										</c:when>
										<c:otherwise>첨부파일이 없습니다.</c:otherwise>
									</c:choose>
									</dd>
								</dl>
							</div>
							<!--VIEW_HEAD// E-->

							<div class="view_body">${boardView.ntt_cn }</div>
							<!--VIEW_BODY //E -->

						</div>
						<!--TBL_VIEW //E -->

						<div class="btn_r">
							<a href="javascript:goBoardList();" class="">목록</a>
						</div>


					</div>
					<!--BOARD_VIEW_AREA //E-->

				</div>
			</div>
		</div>
		<!-- #CONTENTS //E -->
	</div>
	<!-- #CONTAINER //E -->
</form:form>

<jsp:include page="/client/footer.do"></jsp:include>