<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>

<c:import url="/client/header.do" />

<script type="text/javascript">

function goBoardList(){
	$("#frm").attr("action", "<c:url value='/event/eventContestInfo.do'/>");
	$("#frm").submit();
}

function goPreBoardView(no){
	$("#frm").attr("action", "<c:url value='/event/eventContestInfoView.do'/>");
	$("#ntt_id").val(no);
	$("#frm").submit();
}

function goNextBoardView(no){
	$("#frm").attr("action", "<c:url value='/event/eventContestInfoView.do'/>");
	$("#ntt_id").val(no);
	$("#frm").submit();
}

function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}
</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>

<form:form commandName="vo" name="frm" id="frm" >
		<input type="hidden" name="ntt_id" id="ntt_id" value="<c:out value='${contestView.ntt_id }'/>" />
		<input type="hidden" name="atch_file_id" value="<c:out value='${contestView.atch_file_id }'/>" />
		<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
		<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
		<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
	        <h2 class="hide">본문</h2>
	        <c:import url="/client/snb.do" />                       
	        <div class="sub_content">
	            <div class="inner">
		            <div class="sub_head">
		                <h3 class="c_tit">이벤트대회 안내</h3>
		            </div>    
	        
			        <div class="board_view_area sub_fade">
			            <div class="bbs_view">
			                <div class="view_head">
			                    <dl class="subject">
			                        <dt><c:out value='${contestView.ntt_sj }'/></dt>
			                        <dd class="info"><span><strong><c:out value='${contestView.ntcr_nm }'/></strong></span><span><c:out value='${contestView.reg_dt }'/></span><span><c:out value='${contestView.rdcnt }'/></span></dd>
			                    </dl>
			                    <dl class="file">
			                       <span class="t_tit">첨부파일 : </span> 
			                       <c:choose>
										<c:when test="${fn:length(contestFile) > 0}">
											<c:forEach items="${contestFile}" var="contestFile" varStatus="status">
												<a href="javascript:void(0);" onclick="Down('${contestFile.atch_file_id }');" class="attach"><img src="/resources/client/images/contents/file.gif" alt="첨부파일 아이콘">${contestFile.orignl_file_nm }</a>												
											</c:forEach>
										</c:when>
										<c:otherwise>첨부파일이 없습니다.</c:otherwise>
									</c:choose>			                       	
			                    </dl>
			                </div>
			                <!--VIEW_HEAD// E-->
			                
			                <div class="view_body">
			                    ${contestView.ntt_cn }
			                </div>
			                <!--VIEW_BODY //E -->
			                
			            </div>
			            <!--TBL_VIEW //E -->
			            
			            <div class="btn_r">
			                <a href="javascript:goBoardList();" class="">목록</a>
			            </div>
			            
			            <ul class="view_list">
			                <li class="prev">
			                	<span class="tit">이전글</span>
			                	<c:choose>
				           			<c:when test="${pre.ntt_id == null}">
										 <span class="txt">이전글이 없습니다.</span>
								 	</c:when>
								 	<c:otherwise>
								 		<span class="txt"><a href="javascript:goPreBoardView('${pre.ntt_id }')">${pre.ntt_sj }</a></span>
								 	</c:otherwise>
								</c:choose>
			                	
			                </li>
			                <li class="next">
			                	<span class="tit">다음글</span> 
			                	<c:choose>
				           			<c:when test="${next.ntt_id == null}">
										 <span class="txt">다음글이 없습니다.</span>
								 	</c:when>
								 	<c:otherwise>
								 		<span class="txt"><a href="javascript:goNextBoardView('${next.ntt_id }')">${next.ntt_sj }</a></span>
								 	</c:otherwise>
								</c:choose>
			                </li>
			            </ul>
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