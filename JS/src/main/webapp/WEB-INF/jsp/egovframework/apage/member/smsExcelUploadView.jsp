<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
//메세지 재전송
$(document).on("click",".resendBtn",function(){
	var msgkey = $(this).attr("data-msgkey");
	var sentdate = $(this).attr("data-senddt");
	var rslt = $(this).attr("data-rslt");
	
	if(confirm("재전송 하시겠습니까?")){
		if(rslt != "0"){
			$.ajax({
				url		:	"<c:url value='/apage/contest/resendMsg.do'/>",
				type	:	"post",
				data	:	{
					msgkey		:	msgkey,
					sentdate	:	sentdate,
					rslt		:	rslt
				},
				async	:	false,
				success	:	function(data){
					if(data.root.resultCode == "Y"){
						alert("재전송 되었습니다.");
						location.reload();
					}else if(data.root.resultCode == "N"){
						alert("실패하였습니다.");
					}else if(data.root.resultCode == "A"){
						alert("잘못된 접근입니다.");
					}else{
						alert("오류가 발생하였습니다.");
					}
				},
				error	:	function(err){
					console.log(err);				
					alert("오류가 발생하였습니다.");
				}
			})
		}
	}
})

function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/member/smsExcelUploadList.do'/>");
	$("#frm").submit();
}
</script>

<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="srch_date" id="srch_date"  value="<c:out value='${srch_date }'/>"/>
	<input type="hidden" name="srch_date2" id="srch_date2"  value="<c:out value='${srch_date2 }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>

	<div id="container">
		<h3>엑셀 업로드 발송</h3>
		<div class="contents">
			<!-- //ct-tab -->
			<div class="bbs-view">
				<table>
					<colgroup>
						<col width="3%">
						<col width="7%">
						<col width="10%">
						<col width="*">
						<col width="7%">
						<col width="10%">
						<col width="7%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">성명</th>
							<th scope="col">핸드폰번호</th>
							<th scope="col">내용</th>
							<th scope="col">전송결과</th>
							<th scope="col">전송일시</th>
							<th scope="col">재전송</th>
						</tr>
					</thead>
					<tbody>
						<% pageContext.setAttribute("newLineChar", "\n"); %>
						<c:choose>
							<c:when test="${fn:length(smsLogList) > 0}">
								<c:forEach items="${smsLogList }" var="list" varStatus="status">
									<tr>
										<td class="al_c">${status.count }</td>
										<td class="al_c">${list.etc7 }</td>
										<td class="al_c">${list.phone }</td>
										<td>${fn:replace(list.msg, newLineChar, "<br/>")}</td>
										<c:if test="${list.rslt eq '0' }">
											<td class="al_c" style="color: blue;">전송성공</td>
										</c:if>
										<c:if test="${list.rslt ne '0' && list.rslt != '99999' }">
											<td class="al_c" style="color: red;">전송실패</td>
										</c:if>
										<td class="al_c">${list.reqdate }</td>
										<td>
											<c:if test="${list.rslt ne '0' && list.rslt != '99999' }">
												<a href="javascript:void(0);" data-msgkey="${list.msgkey }" data-senddt="${list.reqdate}" data-rslt="${list.rslt}" class="s-blue-btn resendBtn" style="text-align: center;">재전송</a>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
				                    <td colspan="7" class="al_c">조회 결과가 없습니다.</td>
				                </tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
	
			<!-- //attach -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardList()" class="btn-ty1 blue">목록</a>
				</p>
			</div>
			<!-- //bbs-view -->
		</div>
	</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>