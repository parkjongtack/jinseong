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
	pagingUtil.__url			= "<c:url value='/apage/contest/adminContestSelectResultList.do'/>";

});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestSelectResultList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	$("#frmDetail").attr("action", "<c:url value='/apage/contest/adminContestSelectResultDetail.do'/>");
	$("#ct_seq").val(val);
	$("#frmDetail").submit();
}

</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ct_seq" id="ct_seq" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" name="autoLaneResult" id="autoLaneResult" />
</form:form>


	<div id="container">
		<h3>선정결과확인</h3>
		<div class="contents">
        	<ul class="ct-tab">
				<li class="tab1"><a href="<c:url value='/apage/contest/adminContestList.do'/>">대회안내</a></li>
				<li class="tab1"><a href="<c:url value='/apage/contest/adminContestMngList.do'/>">대회신청관리</a></li>
				<li class="tab1"><a href="<c:url value='/apage/contest/adminContestSelectResultList.do'/>"  class="on">선정결과확인</a></li>
				<li class="tab1"><a href="<c:url value='/apage/contest/adminLaneList.do'/>">자리배치결과</a></li>
                <li class="tab1"><a href="<c:url value='/apage/contest/adminContestRstList.do'/>">대회결과</a></li>                     
			</ul>
			<!-- //ct-tab -->
        
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
				<legend>대회신청관리 목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup>
						<col width="4%" />
						<col width="31%" />
						<col width="6%" />						
						<col width="19%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="6%" />
						<col width="4%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">제목</th>
							<th scope="col">대회일자</th>							
							<th scope="col">장소</th>
							<th scope="col">진행상태</th>
							<th scope="col">신청자수</th>
							<th scope="col">1조<br/>선정현황</th>
							<th scope="col">2조<br/>선정현황</th>
							<th scope="col">3조<br/>선정현황</th>
							<th scope="col">4조<br/>선정현황</th>
							<th scope="col">등록일</th>                          
                            <th scope="col">조회수</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
		            		<c:when test="${fn:length(contestList) > 0}">
		            			<c:forEach items="${contestList }" var="list" varStatus="status">
		            				<tr onclick="goBoardView('${list.ct_seq}');" style="cursor: pointer;">
										<th scope="row"><c:out value='${(totalNum+1)-list.ascnum }'/></th>
			                            <td class="al_l">
			                            	<a href="javascript:void(0);"><c:out value='${list.ct_sbj }'/></a> 			                            	
			                            </td>
			                            <td class="al_c"><c:out value='${list.ct_dt }'/></td>
			                            <td class="al_c"><c:out value='${list.ct_place }'/></td>
			                            <td class="al_c">
			                            	<c:if test="${list.ct_process eq 'R' }">대회준비</c:if>
			                            	<c:if test="${list.ct_process eq 'S' }">대회신청</c:if>
			                            	<c:if test="${list.ct_process eq 'E' }">신청마감</c:if>
			                            	<c:if test="${list.ct_process eq 'F' }">대회종료</c:if>
			                            </td>
			                            <td><c:out value='${list.app_cnt }'/>명</td>
			                            <td><c:out value='${list.part1_app_cnt }'/>/<c:out value='${list.limit_part }'/></td>
			                            <td><c:out value='${list.part2_app_cnt }'/>/<c:out value='${list.limit_part }'/></td>
			                            <td><c:out value='${list.part3_app_cnt }'/>/<c:out value='${list.limit_part }'/></td>
			                            <td><c:out value='${list.part4_app_cnt }'/>/<c:out value='${list.limit_part }'/></td>
			                            <td><c:out value='${list.reg_dt }'/></td>
										<td><c:out value='${list.hit }'/></td>
									</tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
				                <tr>
				                    <td colspan="8">조회된 결과가 없습니다.</td>
				                </tr>
				            </c:otherwise>
				        </c:choose>
					</tbody>
				</table>
			</fieldset>
			<!-- //bbs-list -->
			<div class="pg-nav">
            	<!-- <p class="btn"><a href="javascript:goReg();" class="btn-ty1 blue">등록</a></p> -->
            	${pageNav }
				<!-- //paging -->
			</div>
			<!-- //pg-nav -->
		</div>
		<!-- //contents -->
	</div>

		
<jsp:include page="/apage/inc/footer.do"></jsp:include>