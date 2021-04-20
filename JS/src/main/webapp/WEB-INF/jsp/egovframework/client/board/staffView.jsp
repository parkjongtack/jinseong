<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<script type="text/javascript">
	$(document).ready(function() {

	});

	function goBoardList() {
		$("#frmDetail").attr("action", "<c:url value='/board/staffList.do'/>");
		$("#frmDetail").submit();
	}
</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="seq" id="seq" value="${staffView.seq }" />
	<input type="hidden" name="asso_type" id="asso_type" value="${asso_type }" />
	<input type="hidden" name="scType" value="<c:out value='${scType }'/>" />
	<input type="hidden" name="srch_input" id="srch_input" value="<c:out value='${Srch_input }'/>" />
	<input type="hidden" name="currRow" value="<c:out value='${currPage }'/>" />
</form:form>

<div id="container" class="subpage">
	<div id="contents">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />
		<div class="sub_content">
			<div class="inner">
				<div class="sub_head">
					<h3 class="c_tit">STAFF</h3>
				</div>
				<div class="prof_detail_wrap sub_fade">
					<div class="inner">
						<div class="pf_head">
							<div class="head_in">
								<div class="imgBx">
									<span class="mask"></span>
									<c:choose>
										<c:when test="${fn:length(imgFile) > 0}">
		           							<c:forEach items="${imgFile }" var="ifile" varStatus="status">
												<img src="/commonfile/thumbFileDown.do?atch_file_id=${ifile.atch_file_id }" alt="선수프로필 사진"/>
											</c:forEach>
										</c:when>		
				           				<c:otherwise>
				           					  <img src="/resources/client/images/common/noimg.gif" alt="이미지 없음" />
				           				</c:otherwise>
           							</c:choose>
								</div>
								<div class="profile_info">
									<div class="pf_name">
										<p class="big"><c:out value='${staffView.staff_name }'/></p>
									</div>
									<div class="profile">
										<dl><dt>소속</dt><dd><c:out value='${staffView.team }'/></dd></dl>									
										<dl><dt>프로기수</dt><dd><c:out value='${staffView.pro_no }'/></dd></dl>
										<dl><dt>구력</dt><dd><c:out value='${staffView.career }'/></dd></dl>
										<dl><dt>사용손</dt><dd><c:out value='${staffView.use_hand }'/></dd></dl>
										<dl><dt>포인트 랭킹</dt><dd><c:out value='${staffView.point_rank }'/></dd></dl>
									</div>
								</div>
							</div>
							<!--HEAD_IN //E -->
						</div>
						<div class="pf_body">
							<c:out value='${staffView.staff_info }' escapeXml="false"/>
						</div>
						<div class="pf_img">
					    	<p class="pf_tit">프로필 이미지</p>
					    	<div class="info_pic">
					    		<ul>
									<c:choose>
										<c:when test="${fn:length(imgFile) > 0}">
		           							<c:forEach items="${addFile }" var="afile" varStatus="status">
												<li><img src="/commonfile/nunFileDown.do?atch_file_id=${afile.atch_file_id }" alt="선수프로필 사진"/></li>
											</c:forEach>
										</c:when>		
				           				<c:otherwise>
				           					<li>데이터가 없습니다.</li>
				           				</c:otherwise>
			           				</c:choose>					    
					    		</ul>
					    	</div>
						</div>
						<div class="btn_r">
							<a href="javascript:goBoardList();">목록</a>
						</div>						
					</div>
				</div>
				<!--PROF_DETAIL_WRAP-->
			</div>
			<!-- SUB_CONTENT //E-->
		</div>
	</div>
	<!-- #CONTENTS //E -->

</div>
<!-- #CONTAINER //E -->
<jsp:include page="/client/footer.do"></jsp:include>