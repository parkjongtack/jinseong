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
	
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/apage/event/eventContestList.do'/>";

	//msg alert
	var msg = "${msg}";

	if(msg != ""){
		alert(msg);	
	}	
});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/apage/event/eventContestList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/apage/event/eventContestDetail.do'/>");
	$("#ntt_id").val(val);
	$("#frmDetail").submit();
}


function goReg(){
	location.href = "<c:url value='/apage/event/eventContestWrite.do'/>";
}

</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ntt_id" id="ntt_id" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form:form>


	<div id="container">
		<h3>이벤트 대회 안내</h3>
		<div class="contents">
        	<ul class="ct-tab">
				<li class="tab1"><a href="<c:url value='/apage/event/kokContestList.do'/>">왕중왕전대회</a></li>
				<li class="tab1"><a href="<c:url value='/apage/event/eventContestList.do'/>" class="on">이벤트대회</a></li>
			</ul>
			<!-- //ct-tab -->
			<ul class="ct-tab">
				<li><a href="/apage/event/eventContestList.do" class="on" id="part_firBtn">대회안내</a></li>
				<li><a href="/apage/event/eventContestMngList.do" class="">대회신청관리</a></li>
				<li><a href="/apage/event/eventContestSelectResultList.do" class="">선정결과관리</a></li>
				<li><a href="/apage/event/eventContestLaneList.do" class="">자리배치결과</a></li>
			</ul>
        
        	<div class="bbs-head">
            	<%-- <p class="bbs-total">총 <strong>${totalNum }</strong> 명</p> --%>
            	<form:form commandName="vo" method="post" id="frm" name="frm">
	                <fieldset class="bbs-srch">
	                	<legend>게시글 검색</legend>
						<label for="srch-word" class="hide">검색어입력</label>
						<form:input path="srch_input" onKeyPress="if (event.keyCode==13){ searchChk(); return false;}"/>
						<input type="submit" value="검색" />
	                </fieldset>
	             </form:form>
            </div>
        	
			<fieldset class="bbs-list">
				<legend>대회안내 목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup><col width="7%" /><col width="*" /><col width="15%" /><col width="10%" /><col width="7%" /></colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">제목</th>
							<th scope="col">등록일</th>
                            <th scope="col">작성자</th>
                            <th scope="col">조회수</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
		            		<c:when test="${fn:length(contestList) > 0}">
		            			<c:forEach items="${contestList }" var="list" varStatus="status">
		            				<tr onclick="goBoardView('${list.ntt_id}');" style='cursor:pointer;'>
										<th scope="row"><c:out value='${(totalNum+1)-list.ascnum }'/></th>
			                            <td class="al_l"><c:out value='${list.ntt_sj }'/></td>
			                            <td><c:out value='${list.reg_dt }'/></td>
			                            <td><c:out value='${list.ntcr_id }'/></td>
										<td><c:out value='${list.rdcnt }'/></td>
									</tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
				                <tr>
				                    <td colspan="5">조회된 결과가 없습니다.</td>
				                </tr>
				            </c:otherwise>
				        </c:choose>
					</tbody>
				</table>
			</fieldset>
			<!-- //bbs-list -->
			<div class="pg-nav">
            	<p class="btn"><a href="javascript:goReg();" class="btn-ty1 blue">등록</a></p>
            	${pageNav }
				<!-- //paging -->
			</div>
			<!-- //pg-nav -->
		</div>
		<!-- //contents -->
	</div>

		
<jsp:include page="/apage/inc/footer.do"></jsp:include>