<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<style type="text/css">
.view_body {}
.view_body table { border-left:1px solid #ddd; border-top:1px solid #ddd; width: 55% !important; }
.view_body table th, .view_body table td { padding:15px 8px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; }
.view_body table th { background:#f8f8f8; font-weight:bold; color:#111;  text-align:center; }
.view_body table td a { display:block; }
.view_body table td a:hover { text-decoration:underline; }
.view_body table td.cont { min-height:300px; padding:15px; line-height:normal; vertical-align:top; }
.view_body table td textarea { width:98.8%; height:100px; padding:3px 0.5%; }
</style>
<c:import url="/client/header.do" />

<script type="text/javascript">
function goBoardList() {
	$("#frm").attr("action", "<c:url value='/contest/contestAppList.do'/>");
	$("#frm").submit();
}

</script>

<form:form commandName="vo" name="frm" id="frm">
	<input type="hidden" name="srch_input" id="srch_input" value="<c:out value='${Srch_input }'/>" />
	<input type="hidden" name="scType" value="<c:out value='${scType }'/>" />
	<input type="hidden" name="currRow" value="<c:out value='${currPage }'/>" />
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
			<h2 class="hide">본문</h2>
			<c:import url="/client/snb.do" />
			<div class="sub_content">
				<div class="inner">
					<div class="sub_head">
						<h3 class="c_tit">레인배치 결과</h3>
					</div>

					<div class="board_view_area sub_fade">
						<div class="bbs_view">
							<div class="view_head">
								<dl class="subject">
									<dt>
										<c:out value='${boardView.ntt_sj }' />
									</dt>
									<dd class="info">
										<span><strong><c:out value='${boardView.ntcr_nm }' /></strong></span><span><c:out value='${boardView.reg_dt }' /></span><span><c:out value='${boardView.rdcnt }' /></span>
									</dd>
								</dl>
							</div>
							<!--VIEW_HEAD// E-->

							<div class="view_body">
							
								<table style="border:1px solid #c9c9c9;width:45%">
                       			<tbody>
                       				<tr>
                       					<td colspan="3" style="text-align:center;color:red">1조</td>
                       				</tr>
                       				<tr>
	                       				<td style="text-align:center">성명</td>
	                       				<td style="text-align:center">레인</td>
	                       				<td style="text-align:center">핸디</td>
                       				</tr>
									<c:choose>
					            		<c:when test="${fn:length(part1List) > 0}">
					            			<c:forEach items="${part1List }" var="list" varStatus="status">
					            				<tr>
					            					<td style="text-align:center"><c:out value="${list.join_name }"/></td>
					            					<td style="text-align:center"><c:out value="${list.lane }"/></td>
					            					<td style="text-align:center"><c:out value="${list.handicap }"/></td>
					            				</tr>
			        	    				</c:forEach>
			            				</c:when>
	            					</c:choose>
                       			</tbody>
							</table>
							<br/><br/>
                       		
                       		<table style="border:1px solid #c9c9c9;width:45%">
                       			<tbody>
                       				<tr>
                       					<td colspan="3" style="text-align:center;color:red">2조</td>
                       				</tr>
                       				<tr>
	                       				<td style="text-align:center">성명</td>
	                       				<td style="text-align:center">레인</td>
	                       				<td style="text-align:center">핸디</td>
                       				</tr>
									<c:choose>
					            		<c:when test="${fn:length(part2List) > 0}">
					            			<c:forEach items="${part2List }" var="list" varStatus="status">
					            				<tr>
					            					<td style="text-align:center"><c:out value="${list.join_name }"/></td>
					            					<td style="text-align:center"><c:out value="${list.lane }"/></td>
					            					<td style="text-align:center"><c:out value="${list.handicap }"/></td>
					            				</tr>
			        	    				</c:forEach>
			            				</c:when>
	            					</c:choose>
                       			</tbody>
							</table>
							<br/><br/>
                       		
                       		<table style="border:1px solid #c9c9c9;width:45%">
                       			<tbody>
                       				<tr>
                       					<td colspan="3" style="text-align:center;color:red">3조</td>
                       				</tr>
                       				<tr>
	                       				<td style="text-align:center">성명</td>
	                       				<td style="text-align:center">레인</td>
	                       				<td style="text-align:center">핸디</td>
                       				</tr>
									<c:choose>
					            		<c:when test="${fn:length(part3List) > 0}">
					            			<c:forEach items="${part3List }" var="list" varStatus="status">
					            				<tr>
					            					<td style="text-align:center"><c:out value="${list.join_name }"/></td>
					            					<td style="text-align:center"><c:out value="${list.lane }"/></td>
					            					<td style="text-align:center"><c:out value="${list.handicap }"/></td>
					            				</tr>
			        	    				</c:forEach>
			            				</c:when>
	            					</c:choose>
                       			</tbody>
							</table>
							<br/><br/>
                       		
                       		<table style="border:1px solid #c9c9c9;width:45%">
                       			<tbody>
                       				<tr>
                       					<td colspan="3" style="text-align:center;color:red">4조</td>
                       				</tr>
                       				<tr>
	                       				<td style="text-align:center">성명</td>
	                       				<td style="text-align:center">레인</td>
	                       				<td style="text-align:center">핸디</td>
                       				</tr>
									<c:choose>
					            		<c:when test="${fn:length(part4List) > 0}">
					            			<c:forEach items="${part4List }" var="list" varStatus="status">
					            				<tr>
					            					<td style="text-align:center"><c:out value="${list.join_name }"/></td>
					            					<td style="text-align:center"><c:out value="${list.lane }"/></td>
					            					<td style="text-align:center"><c:out value="${list.handicap }"/></td>
					            				</tr>
			        	    				</c:forEach>
			            				</c:when>
	            					</c:choose>
                       			</tbody>
							</table>
							<br/><br/>
								
									
							
							
							
							</div>
							<!--VIEW_BODY //E -->

						</div>
						<!--TBL_VIEW //E -->

						<div class="btn_r">
							<a href="javascript:goBoardList();" class="">목록</a>
						</div>

						<!--VIEW_LIST //E -->

					</div>
					<!--BOARD_VIEW_AREA //E-->

				</div>
			</div>
		</div>
		<!-- #CONTENTS //E -->
	</div>
	<!-- #CONTAINER //E -->
</form:form>

<jsp:include page="/client/footer.do"></jsp:include>