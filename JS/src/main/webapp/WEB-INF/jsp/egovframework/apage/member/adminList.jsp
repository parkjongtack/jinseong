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
	pagingUtil.__url			= "<c:url value='/apage/member/adminList.do'/>";
	
	$('.datepicker').removeClass('hasDatepicker').datepicker({
		showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
		buttonImage: "/resources/apage/images/board/ic_date.gif", // 버튼 이미지
		buttonImageOnly: true  // 버튼에 있는 이미지만 표시한다.		
	});

});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/apage/member/adminList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/apage/member/adminDetail.do'/>");
	$("#mber_id").val(val);
	$("#frmDetail").submit();
}

function goReg(){
	
	$("#frmDetail").attr("action", "<c:url value='/apage/member/adminWrite.do'/>");
	$("#frmDetail").submit();
	
}

</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="mber_id" id="mber_id" />
	<input type="hidden" name="scType" id="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form:form>


	<div id="container">
		<h3>관리자 계정 관리</h3>
		<div class="contents">
        	<ul class="ct-tab">
				<li class="tab1"><a href="<c:url value='/apage/member/adminMemberList.do'/>">회원정보관리</a></li>
                <li class="tab1"><a href="<c:url value='/apage/member/adminList.do'/>" class="on">관리자 계정관리</a></li>
                <li class="tab1"><a href="<c:url value='/apage/member/adminMemberContestList.do'/>" >대회신청이력</a></li>
			</ul>
			<!-- //ct-tab -->

        	<div class="bbs-select">
				<table summary="">
                	<colgroup>
                		<col width="10%">
                		<col width="*">
                		<col width="10%">
                		<col width="*">
                	</colgroup>
                	<tbody>
                	<form:form commandName="vo" method="post" id="frm" name="frm">
                	<input type="hidden" name="mber_type" value="A" />
                        <tr>
                        	<th>검색분류</th>
                            <td>
	                            <form:select path="scType" cssClass="w20p">
	                            	<form:option value="" label="::전체::" />
									<form:option value="id" label="아이디" />
									<form:option value="name" label="성명" />
									<form:option value="email" label="이메일" />
								</form:select>
	                            <form:input path="srch_input" cssClass="w40p" title="검색어를 입력하세요." onKeyPress="if (event.keyCode==13){ searchChk(); return false;}"/>	                            
                            </td>    
                            <th>상태</th>
                            <td>
                            	<select name="mber_status" id="mber_status" class="w40p">
                            		<option value="">::전체::</option>
                            		<option value="Y">사용</option>
                            		<option value="N">사용중지</option>
                            	</select>	                                           
                            </td>                                                   
                        </tr>
                        <tr>
                        	<th>등록일자</th>
                            <td colspan="3">
					            <input type="text" id="reg_dt_s" name="srch_date" class="w7p datepicker" value="${srch_date }" /> ~ 
					            <input type="text" id="reg_dt_e" name="srch_date2" class="w7p datepicker" value="${srch_date2 }" />					                                      
                            </td>                                                       
                        </tr>                                                 
                        <tr>
                        	<td colspan="4" class="btn">
	                            <a href="javascript:searchChk();" class="btn-ty1 topSearch">검색</a>
	                            <!-- <a href="javascript:excelDown()" class="btn-ty1 excel">엑셀 다운로드</a> -->
                            </td>
                        </tr>
                       </form:form>
                    </tbody>
                </table>
            </div>
        	
			<fieldset class="bbs-list">
				<legend>회원정보 목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup>
						<col width="5%" />
						<col width="10%" />
						<col width="10%" />
						<col width="*" />
						<col width="*" />
						<col width="10%" />
						<col width="12%" />
						<col width="12%" />
						<col width="10%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">아이디</th>
							<th scope="col">성명</th>
							<th scope="col">연락처</th>
                            <th scope="col">이메일</th>
                            <th scope="col">권한구분</th>
                            <th scope="col">등록일</th>
                            <th scope="col">최근접속일</th>
                            <th scope="col">상태</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
		            		<c:when test="${fn:length(memberList) > 0}">
		            			<c:forEach items="${memberList }" var="list" varStatus="status">
		            				<tr onclick="goBoardView('${list.mber_id}');" style='cursor:pointer;'>
										<th scope="row"><c:out value='${(totalNum+1)-list.ascnum }'/></th>
			                            <td><c:out value='${list.mber_id }'/></td>
			                            <td><c:out value='${list.mber_name }'/></td>
			                            <td><c:out value='${list.mber_tel }'/></td>
										<td><c:out value='${list.mber_email }'/></td>
										<td>
											<c:if test="${list.auth_code eq 'super' }">총괄관리자</c:if>
											<c:if test="${list.auth_code eq 'admin' }">대회관리자</c:if>
											<c:if test="${list.auth_code eq 'board' }">게시판관리자</c:if>
										</td>
										<td><c:out value='${list.reg_dt }'/></td>
										<td><c:out value='${list.last_login_dt }'/></td>
										<td>
											<c:if test="${list.mber_status eq 'Y' }"><span style="color:blue;">사용</span></c:if>
											<c:if test="${list.mber_status eq 'N' }"><span style="color:red;">사용중지</span></c:if>
										</td>
									</tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
				                <tr>
				                    <td colspan="9">조회된 결과가 없습니다.</td>
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