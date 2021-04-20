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
	pagingUtil.__url			= "<c:url value='/apage/board/adminProductList.do'/>";

	var msg = "${msg}";

	if(msg != ""){
		alert(msg);	
	}	
	
});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/apage/board/adminProductList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardModify(val){
	
	$("#frmDetail").attr("action", "<c:url value='/apage/board/adminProductModify.do'/>");
	$("#hash").val(val);
	$("#frmDetail").submit();
}

/*
function goReg(){
	location.href = "<c:url value='/apage/board/adminNoticeWrite.do'/>";
}
*/
</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="hash" id="hash" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form:form>


	<div id="container">
		<h3>제품소개_광고</h3>
		<div class="contents">
        	<ul class="ct-tab">
				<li class="tab1"><a href="<c:url value='/apage/board/adminNoticeList.do'/>">공지사항</a></li>
				<li class="tab1"><a href="<c:url value='/apage/board/adminProductList.do'/>" class="on">제품소개_광고</a></li>
				<li class="tab1"><a href="<c:url value='/apage/board/adminEventList.do'/>">이벤트</a></li>
                <li class="tab1"><a href="<c:url value='/apage/board/adminAsList.do'/>">A/S게시판</a></li>          
                <li class="tab1"><a href="<c:url value='/apage/board/adminConsultList.do'/>">고객상담</a></li>      
                <li class="tab1"><a href="<c:url value='/apage/board/adminStaffList.do'/>">STAFF관리</a></li>
                <li class="tab1"><a href="<c:url value='/apage/board/adminLaneMachines.do'/>">레인정비기계</a></li>
                <li class="tab1"><a href="<c:url value='/apage/board/adminLaneSupplies.do'/>">레인정비소모품</a></li>
                <li class="tab1"><a href="<c:url value='/apage/board/adminTrainingList.do'/>">훈련 도구</a></li>        
                <li class="tab1"><a href="<c:url value='/apage/board/adminCenterList.do'/>">전국볼링장</a></li>           
                <li class="tab1"><a href="<c:url value='/apage/board/adminShopeventList.do'/>">세미나</a></li>
			</ul>
			<!-- //ct-tab --> 
        
        	<div class="bbs-head">
            	<%-- <p class="bbs-total">총 <strong>${totalNum }</strong> 명</p> --%>
            	<form:form commandName="vo" method="post" id="frm" name="frm">
	                <fieldset class="bbs-srch">
	                	<legend>게시글 검색</legend>
						<label for="srch-word" class="hide">검색어입력</label>
						<form:select path="big">
		                	<form:option value="1001">볼링볼</form:option>
		                	<form:option value="1002">볼링백</form:option>
		                	<form:option value="1003">볼링의류</form:option>
		                	<form:option value="1004">볼링아대</form:option>
		                	<form:option value="1006">볼링슈즈</form:option>
		                	<form:option value="1005">볼링 악세사리</form:option>
		                </form:select>
						<form:input path="srch_input" onKeyPress="if (event.keyCode==13){ searchChk(); return false;}"/>
						<input type="submit" value="검색" />
	                </fieldset>
	             </form:form>
            </div>
        	
			<fieldset class="bbs-list">
				<legend>제품 목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup>
						<col width="10%" />
						<col width="*" />
						<!-- 
						<col width="7%" />
						<col width="*" />
						<col width="15%" />
						<col width="10%" />
						<col width="7%" />
						 -->
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">제품명</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
		            		<c:when test="${fn:length(productlist) > 0}">
		            			<c:forEach items="${productlist }" var="list" varStatus="status">
		            				<tr onclick="goBoardModify('${list.hash}');" style='cursor:pointer;'>
										<th scope="row"><c:out value='${(totalNum+1)-list.ascnum }'/></th>
			                            <td class="al_l"><c:out value='${list.name }'/></td>
									</tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
				                <tr>
				                    <td colspan="2">조회된 결과가 없습니다.</td>
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