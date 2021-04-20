<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>
<style type="text/css">
#shopManageUpdtBtn{
	float: left;
    width: 60px;
    height: 25px;
    color: #fff;
    background: #333;
    font-weight: bold;
    font-family: 'Nanum Gothic', 'NanumGothic', 'Malgun Gothic', 'dotum', 'helvetica', 'sans-serif';
    text-align: center;
    padding-top: 6px;
}


#shopManageFrm > fieldset > table > tbody > tr > th{ padding-left : 5px;}
</style>
<script type="text/javascript">

$(document).ready(function () {
	
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/apage/board/adminShopeventList.do'/>";

	var msg = "${msg}";

	if(msg != ""){
		alert(msg);
		searchChk();
	}	
	
	
  $('.datepicker').removeClass('hasDatepicker').datepicker({
		showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
		  buttonImage: "/resources/apage/images/board/ic_date.gif", // 버튼 이미지
		  buttonImageOnly: true  // 버튼에 있는 이미지만 표시한다.		
	});
	
});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/apage/board/adminShopeventDetail.do'/>");
	$("#ntt_id").val(val);
	$("#frmDetail").submit();
}


function goReg(){
	location.href = "<c:url value='/apage/board/adminShopeventNoticeWrite.do'/>";
}

function goTopBoardView(val){
	$("#frmDetail").attr("action", "<c:url value='/apage/board/adminShopeventNoticeDetail.do'/>");
	$("#ntt_id").val(val);
	$("#frmDetail").submit();
}

function goShopEventManageUpdt(){
	if(confirm("쇼핑몰 이벤트 일자를 변경하시겠습니까?")){
		$.ajax({
			url		:	"<c:url value='/apage/board/adminShopeventManageUpdt.do'/>",
			type	:	"post",
			data	:	$("#shopManageFrm").serialize(),
			success	:	function(data){
				if(data.root.resultCode == "Y"){
					alert("변경되었습니다.");
				}else{
					alert("실패하였습니다.\n잠시후 다시 시도해주세요.");
				}
			},
			error	:	function(err){
				alert("오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
				console.log(err);
			}
		})
	}
}

</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ntt_id" id="ntt_id" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form:form>


	<div id="container">
		<h3>세미나 관리</h3>
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
                <li class="tab1"><a href="<c:url value='/apage/board/adminCenterList.do'/>">전국볼링장</a></li>              
                <li class="tab1"><a href="<c:url value='/apage/board/adminShopeventList.do'/>" class="on">세미나</a></li>              
			</ul>
			<!-- //ct-tab -->
        
        	<div class="bbs-head">
            	<%-- <p class="bbs-total">총 <strong>${totalNum }</strong> 명</p> --%>
            	<form id="shopManageFrm">
	               	<fieldset class="" style="float: left;">
						<table>
							<colgroup>
								<col width="*">
							</colgroup>
	                		<tbody>
	                			<tr>
	                				<th>이벤트상태</th>
									<td class="al_l">
										<select title="상태 시간 선택" name="shop_event_process" id="shop_event_process">
											<option value="R" <c:if test="${shopEventManageVO.shop_event_process eq 'R' }">selected="selected"</c:if>>준비</option>
											<option value="S" <c:if test="${shopEventManageVO.shop_event_process eq 'S' }">selected="selected"</c:if>>시작</option>
											<option value="E" <c:if test="${shopEventManageVO.shop_event_process eq 'E' }">selected="selected"</c:if>>종료</option>
										</select>
									</td>
									<th>접수시작일(일자-시-분)</th>
									<td class="al_l">
										<input type="text" class="datepicker w40p" id="shop_event_start_dt" name="shop_event_start_dt" value="${shopEventManageVO.shop_event_start_dt }" />
										<select title="접수시작일 시간 선택" name="shop_event_start_h" id="shop_event_start_h"  class="w20p">
		                                	<option value="">시</option>
		                                	<c:forEach var="si" begin="1" end="23">
			                                	<c:choose>
			                                		<c:when test="${si < 10}">
			                                			<option value="<c:out value='0${si}' />" <c:set var="now_si" value="0${si}" /> <c:if test="${now_si eq shopEventManageVO.shop_event_start_h }"> selected </c:if>><c:out value="0${si}" /></option>
			                                		</c:when>
			                                		<c:otherwise>
			                                			<option value="<c:out value='${si}' />" <c:if test="${si eq shopEventManageVO.shop_event_start_h }"> selected </c:if>><c:out value="${si}" /></option>
			                                		</c:otherwise>
			                                	</c:choose>
											</c:forEach>
		                                </select>
		                                <select title="접수시작일 분 선택" name="shop_event_start_m" id="shop_event_start_m"  class="w20p">
		                                	<option value="">분</option>
		                                	<c:forEach var="min" begin="0" end="59">
		                                		<c:choose>
			                                		<c:when test="${min < 10}">
			                                			<option value="<c:out value='0${min}' />" <c:set var="now_min" value="0${min}" /> <c:if test="${now_min eq shopEventManageVO.shop_event_start_m }"> selected </c:if>><c:out value="0${min}" /></option>
			                                		</c:when>
			                                		<c:otherwise>
			                                			<option value="<c:out value='${min}' />" <c:if test="${min eq shopEventManageVO.shop_event_start_m }"> selected </c:if>><c:out value="${min}" /></option>
			                                		</c:otherwise>
			                                	</c:choose>
											</c:forEach>
		                                </select>	
									</td>
									<th>접수마감일(일자-시-분)</th>
									<td class="al_l">
										<input type="text" class="datepicker w40p" id="shop_event_end_dt" name="shop_event_end_dt" value="${shopEventManageVO.shop_event_end_dt }" />
										<select title="접수마감 시간 선택" name="shop_event_end_h" id="shop_event_end_h"  class="w20p">
		                                	<option value="">시</option>
		                                	<c:forEach var="si" begin="1" end="23">
			                                	<c:choose>
			                                		<c:when test="${si < 10}">
			                                			<option value="<c:out value='0${si}' />" <c:set var="now_si" value="0${si}" /> <c:if test="${now_si eq shopEventManageVO.shop_event_end_h }"> selected </c:if>><c:out value="0${si}" /></option>
			                                		</c:when>
			                                		<c:otherwise>
			                                			<option value="<c:out value='${si}' />" <c:if test="${si eq shopEventManageVO.shop_event_end_h }"> selected </c:if>><c:out value="${si}" /></option>
			                                		</c:otherwise>
			                                	</c:choose>
											</c:forEach>
		                                </select>
		                                <select title="접수시작일 분 선택" name="shop_event_end_m" id="shop_event_end_m"  class="w20p">
		                                	<option value="">분</option>
		                                	<c:forEach var="min" begin="0" end="59">
		                                		<c:choose>
			                                		<c:when test="${min < 10}">
			                                			<option value="<c:out value='0${min}' />" <c:set var="now_min" value="0${min}" /> <c:if test="${now_min eq shopEventManageVO.shop_event_end_m }"> selected </c:if>><c:out value="0${min}" /></option>
			                                		</c:when>
			                                		<c:otherwise>
			                                			<option value="<c:out value='${min}' />" <c:if test="${min eq shopEventManageVO.shop_event_end_m }"> selected </c:if>><c:out value="${min}" /></option>
			                                		</c:otherwise>
			                                	</c:choose>
											</c:forEach>
		                                </select>	
									</td>
									<td>
										<a href="javascript:void(0);" onclick="goShopEventManageUpdt();" id="shopManageUpdtBtn">수정</a>
									</td>
								</tr>
	                		</tbody>
	                	</table>
                	</fieldset>
            	</form>
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
				<legend>고객상담 목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup>
						<col width="7%"/>
						<col width="*"/>
						<col width="15%"/>
						<col width="10%"/>
						<col width="7%"/>
						<col width="7%"/>
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>							
							<th scope="col">제목</th>							
                            <th scope="col">작성자</th>
                            <th scope="col">등록일</th>
                            <th scope="col">상태</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
		            		<c:when test="${fn:length(topList) > 0}">
		            			<c:forEach items="${topList }" var="list" varStatus="status">
		            				<tr onclick="goTopBoardView('${list.ntt_id}');" style='cursor:pointer;'>
										<th scope="row">상단공지</th>
			                            <td class="al_l"><c:out value='${list.ntt_sj }'/></td>
			                            <td><c:out value='${list.reg_dt }'/></td>
			                            <td><c:out value='${list.ntcr_id }'/></td>
										<td><c:out value='${list.rdcnt }'/></td>
									</tr>
		            			</c:forEach>
		            		</c:when>
		            	</c:choose>
					</tbody>
					<tbody>
						<c:choose>
		            		<c:when test="${fn:length(consultList) > 0}">
		            			<c:forEach items="${consultList }" var="list" varStatus="status">
		            				<tr onclick="goBoardView('${list.ntt_id}');" style='cursor:pointer;'>
										<th scope="row"><c:out value='${(totalNum+1)-list.ascnum }'/></th>
			                            <td class="al_l"><c:out value='${list.ntt_sj }'/></td>
			                            <td><c:out value='${list.ntcr_nm }'/></td>
			                            <td><c:out value='${list.reg_dt }'/></td>
										<td><c:out value='${list.rdcnt }'/></td>
									</tr>
				                </c:forEach>
				            </c:when>
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