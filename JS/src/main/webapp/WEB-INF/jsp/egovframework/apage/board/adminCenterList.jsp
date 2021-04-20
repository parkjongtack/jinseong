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
	pagingUtil.__url			= "<c:url value='/apage/board/adminCenterList.do'/>";
	
});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/apage/board/adminCenterList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/apage/board/adminCenterModify.do'/>");
	$("#ntt_id").val(val);
	$("#frmDetail").submit();
}


function goReg(){
	location.href = "<c:url value='/apage/board/adminCenterWrite.do'/>";
}

</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ntt_id" id="ntt_id" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form:form>


	<div id="container">
		<h3>전국볼링장</h3>
		<div class="contents">
        	<ul class="ct-tab">
				<li class="tab1"><a href="<c:url value='/apage/board/adminNoticeList.do'/>">공지사항</a></li>
				<li class="tab1"><a href="<c:url value='/apage/board/adminProductList.do'/>">제품소개_광고</a></li>
				<li class="tab1"><a href="<c:url value='/apage/board/adminEventList.do'/>">이벤트</a></li>
                <li class="tab1"><a href="<c:url value='/apage/board/adminAsList.do'/>">A/S게시판</a></li>          
                <li class="tab1"><a href="<c:url value='/apage/board/adminConsultList.do'/>">고객상담</a></li>      
                <li class="tab1"><a href="<c:url value='/apage/board/adminStaffList.do'/>">STAFF관리</a></li>
                <li class="tab1"><a href="<c:url value='/apage/board/adminLaneMachines.do'/>">레인정비기계</a></li>
                <li class="tab1"><a href="<c:url value='/apage/board/adminLaneSupplies.do'/>">레인정비소모품</a></li>
                <li class="tab1"><a href="<c:url value='/apage/board/adminTrainingList.do'/>">훈련 도구</a></li>           
                <li class="tab1"><a href="<c:url value='/apage/board/adminCenterList.do'/>" class="on">전국볼링장</a></li>
                <li class="tab1"><a href="<c:url value='/apage/board/adminShopeventList.do'/>">세미나</a></li>           
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
				<legend>공지사항 목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup>
						<col width="7%" />
						<col width="*%" />
						<col width="20%" />
						<col width="25%" />
						<col width="12%" />
						<col width="7%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">센터명</th>
							<th scope="col">연락처</th>
                            <th scope="col">주소</th>
                            <th scope="col">등록일</th>
                            <th scope="col">등록자</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
		            		<c:when test="${fn:length(centerList) > 0}">
		            			<c:forEach items="${centerList }" var="list" varStatus="status">
		            				<tr onclick="goBoardView('${list.ntt_id}');" style='cursor:pointer;'>
										<th scope="row"><c:out value='${(totalNum+1)-list.ascnum }'/></th>
			                            <td class="al_l"><c:out value='${list.ntt_sj }'/></td>
			                            <td><c:out value='${list.memo }'/></td>
			                            <td><c:out value='${list.ntt_cn }'/></td>
										<td><c:out value='${list.reg_dt }'/></td>
										<td><c:out value='${list.ntcr_nm }'/></td>
									</tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
				                <tr>
				                    <td colspan="6">조회된 결과가 없습니다.</td>
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
<c:if test="${!empty msg }" >
	<script type="text/javascript">
	var msg	= '${msg}'; 
	if(msg.indexOf('success') > 0){
		alert('등록되었습니다.');	
		location.href = '/apage/board/adminCenterList.do';
	}else if(msg.indexOf('fail') > 0){
		alert('실패하였습니다.');	
		location.href = '/apage/board/adminCenterList.do';
	}else if(msg.indexOf('delete') > 0){
		alert('삭제하였습니다.');	
		location.href = '/apage/board/adminCenterList.do';
	}
	</script>
</c:if>	
<jsp:include page="/apage/inc/footer.do"></jsp:include>