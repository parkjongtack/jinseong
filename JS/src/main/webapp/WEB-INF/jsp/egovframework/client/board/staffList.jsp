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
	pagingUtil.__url			= "<c:url value='/board/staffList.do'/>";
	
	
	$(".btnCate").click(function(){
		
		var cate = $(this).attr("data-cate");
		
		$("#asso_type").val(cate);
		$("#frm").attr("action", "<c:url value='/board/staffList.do'/>");
		$("#frm").submit();
		
	});

});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/board/staffList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/board/staffDetail.do'/>");
	$("#seq").val(val);
	$("#frmDetail").submit();
}


</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="seq" id="seq" />
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="asso_type"  value="<c:out value='${asso_type }'/>"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form:form>

	<div id="container" class="subpage">
		<div id="contents" >
            <h2 class="hide">본문</h2>
            <c:import url="/client/snb.do" />       
                
            <div class="sub_content">
                <div class="inner">
               <div class="sub_head">
                    <h3 class="c_tit">STAFF</h3>
                </div>
                
                <div class="board_col_area sub_fade">                  

                   <div class="board_info">
                       <div>
                           <ul class="tabMenu">
                           <!-- 메뉴 복구시 History 확인 -->
                            <li><a href="javascript:void(0);" class="btnCate <c:if test="${asso_type eq 'KPBA' }">on</c:if>" data-cate="KPBA"><span></span>KPBA</a></li>
                           </ul>
                       </div>
                       <div class="l_bx">
                            <p>총 <span class="ft_or"><c:out value='${totalNum }'/></span>건 (페이지 <span  class="ft_or">${currPageInfo }</span>/${pageCntInfo })</p>
                       </div>
                       <form id="frm" name="frm" method="post">
                       <input type="hidden" id="asso_type" name="asso_type" value="${asso_type }"/>
                       <div class="r_bx">
                           <div class="schBx">
                               <label for="schCondition" class="hide">검색</label>
                               <label for="schKeword"><input type="text" id="schKeword" name="srch_input" value="${Srch_input }" class="w200"></label>
                               <button type="submit" class="sb_btn">검색</button>
                           </div>
                       </div>
                       </form>
                   </div>
                   <!-- BOARD_INFO //E -->
                   <div class="staff_intro_wrap">
                   	<c:choose>
           		   		<c:when test="${fn:length(staffList) > 0}">
           					<c:forEach items="${staffList }" var="list" varStatus="status">
	           					<div class="staff_bx">
		                           	<div class="pf_img">
		                               	<a href="javascript:goBoardView('${list.seq }');">
			                            	<c:if test="${list.atch_file_id ne null && list.atch_file_id ne '' }">
			                            		<img src="/commonfile/thumbFileDown.do?atch_file_id=${list.atch_file_id }" alt="선수프로필 사진"/>
			                            	</c:if>
			                            	<c:if test="${list.atch_file_id eq null || list.atch_file_id eq '' }">
			                            		<img src="/resources/client/images/common/noimg.gif" alt="선수프로필 사진" style="height: 148px;"/>
			                            	</c:if>		                               	
		                               	</a>
		                           	</div>
		                           	<div class="pf_txt">
		                               	<dl>
		                                   	<dt>${list.staff_name }</dt>
		                                   	<dd>
		                                       	<ul>
		                                           	<li><span class="pf_th">소속</span><span>${list.team }</span></li>
		                                           	<li><span class="pf_th">프로기수</span><span>${list.pro_no }</span></li>
		                                           	<li><span class="pf_th">구력</span><span>${list.career }</span></li>		                                           
		                                       	</ul>
		                                   	</dd>
		                               	</dl>
		                           	</div>
		                           	<div class="pf_link"><a href="javascript:goBoardView('${list.seq }');" class="pf_btn"><span></span>스텝 프로필 보기</a></div>
		                      	</div>
           					</c:forEach>
           				</c:when>
           				<c:otherwise>
           					<p style="text-align: center;">해당 스태프가 존재하지 않습니다.</p> <!-- 조회결과 없을시 디자인필요 -->
           				</c:otherwise>
           			</c:choose>                                              
                   <!--STAFF_INTRO //E-->
                    <div class="paging">
                        ${pageNav }
                    </div>
                    <!--PAGING //E-->                  
                </div>
                <!-- BOARD_LIST_AREA //E -->                
                </div>
            </div>
            <!-- SUB_CONTENT //E-->                
			</div>		
		</div> 
		<!-- #CONTENTS //E -->
	</div> 
<!-- #CONTAINER //E -->
<jsp:include page="/client/footer.do"></jsp:include>