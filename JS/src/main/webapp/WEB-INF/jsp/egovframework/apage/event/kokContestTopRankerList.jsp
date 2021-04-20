<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>


<script type="text/javascript">


function goClose(){
	self.close();
}

function goBoardWrite(){
	opener.parent.goContestAppTargetUpdate();
	self.close();
}
</script>


<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${contestView.atch_file_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" name="arrayApp" id="arrayApp" value="" />
	<input type="hidden" name="currPart" id="currPart" value="1" />
	<input type="hidden" id="situation_show_yn" value="${contestView.situation_show_yn }" />
	

<div id="container">
	<h3>왕중왕전 대회 참가자 리스트</h3>
	<div class="contents">
		<!-- //ct-tab -->
		<div class="bbs-view">
           		<!-- 엑셀 파일 내용 -->
            	<div class="bbs-write" style="overflow: scroll; height: 700px;">
					<table>
						<colgroup>
							<col width="*">
							<col width="5%">
							<col width="10%">
							<col width="10%">
							<col width="5%">
							<col width="15%">
							<col width="10%">
							<col width="*">
						</colgroup> 
						<tbody>
							<tr class="sheetType" id="sheetType1">
								<th scope="col">회차/지역</th>
								<th scope="col">순위</th>
								<th scope="col">이름</th>
								<th scope="col">생년월일</th>
								<th scope="col">성별</th>
								<th scope="col">전화번호</th>
								<th scope="col"><span style="color:red;">*</span>아이디</th>
								<th scope="col">비고</th>
							</tr>
						</tbody>
						<tbody id="excelDataForm">
							<c:choose>
								<c:when test="${fn:length(t_list) > 0}">
									<c:forEach items="${t_list }" var="list">
										<tr>
											<td><c:out value="${list.ct_title }"/></td>
											<td><c:out value="${list.t_rank }"/></td>
											<td><c:out value="${list.t_name }"/></td>
											<td><c:out value="${list.t_birth }"/></td>
											<td><c:out value="${list.t_gender }"/></td>
											<td><c:out value="${list.t_tel }"/></td>
											<td><c:out value="${list.t_reg_id }"/></td>
											<td><c:out value="${list.t_memo }"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="8">참가자 정보가 존재하지 않습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				
				<!-- //bbs-write -->
				<div class="pg-nav">
					<p class="btn">
						<a href="javascript:goBoardWrite()" class="btn-ty1 black">등록</a>
						<a href="javascript:goClose()" class="btn-ty1 light">닫기</a>
					</p>
				</div>
			
		</div>
	</div>
</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>