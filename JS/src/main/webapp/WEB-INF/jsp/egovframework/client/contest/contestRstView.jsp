<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<c:import url="/client/header.do" />

<script type="text/javascript">
$(document).ready(function() {

	$('.bxslider').bxSlider({
		minSlides : 3,
		maxSlides : 3,
		infiniteLoop : true,
		slideWidth : 400,
		slideMargin : 10,
		pager : false
		//mode:'fade'
	});
});

function goBoardList() {
	$("#frm").attr("action", "<c:url value='/contest/contestRst.do'/>");
	$("#frm").submit();
}

function goPreBoardView(no) {
	$("#frm").attr("action", "<c:url value='/contest/contestRstView.do'/>");
	$("#ntt_id").val(no);
	$("#frm").submit();
}

function goNextBoardView(no) {
	$("#frm").attr("action", "<c:url value='/contest/contestRstView.do'/>");
	$("#ntt_id").val(no);
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
	<input type="hidden" name="ntt_id" id="ntt_id" value="<c:out value='${contestRstView.ntt_id }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${contestRstView.atch_file_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input" value="<c:out value='${Srch_input }'/>" />
	<input type="hidden" name="scType" value="<c:out value='${scType }'/>" />
	<input type="hidden" name="currRow" value="<c:out value='${currPage }'/>" />
</form:form>
<div id="container" class="subpage">
	<div id="contents" class="s_con">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />

		<div class="sub_content">
			<div class="inner">
				<div class="sub_head">
					<h3 class="c_tit">대회결과</h3>
				</div>

				<div class="board_view_area sub_fade">
					<div class="bbs_view">
						<div class="view_head">
							<dl class="subject">
								<dt>
									<c:out value='${contestRstView.ntt_sj }' /> <%-- ${fn:length(addFile)} --%>
								</dt>
							</dl>
						</div>

						<!--VIEW_HEAD// E-->
						<div class="view_slide">
							<div class="slide">
								<ul class="regular slider bxslider"> <!-- 대표이미지 안보이게 하려면 컨트롤러에서 file_gu 주석 풀기 -->
									<c:choose>
										<c:when test="${fn:length(addFile) > 0 }">
											<c:forEach items="${addFile }" var="list" varStatus="status">
												<li class="mySlides">
													<a href="javascript:void(0);"><img src="/commonfile/nunFileDown.do?atch_file_id=${list.atch_file_id }" alt="대표이미지" /></a>
												</li>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<li class="mySlides">
												<a href="javascript:void(0);"><img src="/resources/client/images/common/noimg.gif" alt="대표이미지" /></a>
											</li>
										</c:otherwise>
									</c:choose>
								</ul>
							</div>
							<!--SLIDE // E -->
						</div>
						<!--VIEW_SLIDE //E -->
						<div class="view_body">
							<p class="txt">
								<c:out value='${contestRstView.memo }' />
							</p>
							<p>${contestRstView.ntt_cn }</p>
						</div>
						<!--VIEW_BODY //E -->

					</div>
					<!--TBL_VIEW //E -->

					<div class="btn_r">
						<a href="javascript:goBoardList();">목록</a>
					</div>

					<ul class="view_list">
						<li class="prev"><span class="tit">이전글</span> <c:choose>
								<c:when test="${pre.ntt_id == null}">
									<span class="txt">이전글이 없습니다.</span>
								</c:when>
								<c:otherwise>
									<span class="txt"><a href="javascript:goPreBoardView('${pre.ntt_id }')">${pre.ntt_sj }</a></span>
								</c:otherwise>
							</c:choose></li>
						<li class="next"><span class="tit">다음글</span> <c:choose>
								<c:when test="${next.ntt_id == null}">
									<span class="txt">다음글이 없습니다.</span>
								</c:when>
								<c:otherwise>
									<span class="txt"><a href="javascript:goNextBoardView('${next.ntt_id }')">${next.ntt_sj }</a></span>
								</c:otherwise>
							</c:choose></li>
					</ul>
					<!--VIEW_LIST //E -->

				</div>
				<!--BOARD_VIEW_AREA //E-->

			</div>
		</div>
	</div>
	<!-- #CONTENTS //E -->

</div>
<!-- #CONTAINER //E -->
<jsp:include page="/client/footer.do"></jsp:include>