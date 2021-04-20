<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<script type="text/javascript">

$(document).ready(function () {
	
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/kegel/trainingList.do'/>";

});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/kegel/trainingList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/kegel/trainingView.do'/>");
	$("#ntt_id").val(val);
	$("#frmDetail").submit();
}


</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ntt_id" id="ntt_id" />
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form:form>

<div id="container" class="subpage">
		<div id="contents" class="s_con">
                <h2 class="hide">본문</h2>
                <c:import url="/client/snb.do" /> 
                
                <div class="sub_content">
                <div class="inner">
                <div class="sub_head">
                    <h3 class="c_tit">훈련 도구</h3>
                </div>
                
                <div class="board_list_area sub_fade">
                   <div class="board_info">
                       <div class="l_bx">
                           <p>총 <span class="ft_or"><c:out value='${totalNum }'/></span>건 (페이지 <span  class="ft_or">${currPageInfo }</span>/${pageCntInfo })</p>
                       </div>
                       <form id="frm" name="frm" method="post">
                       <div class="r_bx">
                           <div class="schBx">
                               <label for="srch_input"><input type="text" id="srch_input" name="srch_input" value="${Srch_input }" class="w200"></label>
                               <button type="submit" class="sb_btn">검색</button>
                           </div>
                       </div>
                       </form>
                   </div>
                   <!-- BOARD_INFO //E -->
                   
                   <div class="bbs_gallery">
                       <ul>
                       		<c:choose>
           		   				<c:when test="${fn:length(laneList) > 0}">
           							<c:forEach items="${laneList }" var="list" varStatus="status">
	           							<li>
			                               	<a href="javascript:goBoardView('${list.ntt_id }');">		
			                               		<c:if test="${list.atch_file_id ne null && list.atch_file_id ne '' }">
				                            		<img src="/commonfile/thumbFileDown.do?atch_file_id=${list.atch_file_id }" alt="대표이미지"/>
				                            	</c:if>
				                            	<c:if test="${list.atch_file_id eq null || list.atch_file_id eq '' }">
				                            		<img src="/resources/client/images/common/noimg.gif" alt="대표이미지"/>
				                            	</c:if>
			                               	</a>  
			                               	<div class="gall_con">
			                                    <dl>
			                                        <dt>${list.ntt_sj }</dt>   
			                                        <dd>${list.memo }</dd>
			                                    </dl>
			                               	</div>
			                               	<div class="gall_btn"><a href="javascript:goBoardView('${list.ntt_id }');">상세보기</a></div>
			                           	</li>
           							</c:forEach>
           						</c:when>
           						<c:otherwise>
           							<p style="text-align: center; margin-bottom: 20px;">조회된 결과가 없습니다.</p>
           						</c:otherwise>
           					</c:choose>
                                                     
                       </ul>
                   </div>
                   <!--BBS_GALLERY //E-->
                    
                    <div class="paging">
                       ${pageNav }
                    </div>
                    <!--PAGING //E-->
                    
                </div>
                <!-- BOARD_LIST_AREA //E -->
                
            </div>
		</div>
		<!-- #CONTENTS //E -->
        </div>
		
	</div> 
	<!-- #CONTAINER //E -->
<jsp:include page="/client/footer.do"></jsp:include>