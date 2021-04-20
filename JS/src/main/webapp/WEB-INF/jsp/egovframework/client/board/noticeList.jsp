<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<script type="text/javascript">

$(document).ready(function () {
	
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/board/noticeList.do'/>";

});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/board/noticeList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/board/noticeDetail.do'/>");
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

<c:set var="now" value="<%=new java.util.Date() %>"/>

	<div id="container" class="subpage">
		<div id="contents" >
            <h2 class="hide">본문</h2>
            <c:import url="/client/snb.do" />       
                
            <div class="sub_content">
                <div class="inner">
                <div class="sub_head">
                        <h3 class="c_tit">공지사항</h3>
                    </div>   
                <div class="board_list_area sub_fade">
                   <div class="board_info">
                       <div class="l_bx">                      	  
                           <p>총 <span class="ft_or"><c:out value='${totalNum }'/></span>건 (페이지 <span  class="ft_or">${currPageInfo }</span>/${pageCntInfo })</p>
                       </div>
                       <form id="frm" name="frm" method="post">
                       <div class="r_bx">
                           <div class="schBx">
                               <label for="schCondition" class="hide">검색</label>
                               <label for="srch_input"><input type="text" id="srch_input" name="srch_input" value="${Srch_input }" class="w200"></label>
                               <button type="submit" class="sb_btn">검색</button>
                           </div>
                       </div>
                       </form>
                   </div>
                   <!-- BOARD_INFO //E -->

                   <div class="bbs_list">
                       <div class="tbl_cm">
                           <table summary="구분, 제목, 작성자, 등록일자, 조회수 표 입니다.">
                               <caption>공지사항</caption>
                               <colgroup>
                                   <col width="8%" class="pVer">
                                   <col width="47%" class="pVer">
                                   <col width="15%" class="pVer">
                                   <col width="15%" class="pVer">
                                   <col width="15%" class="pVer">
                               </colgroup>
                               <thead>
                                <tr>
                                    <th scope="row" class="pVer">NO.</th>
                                    <th scope="row">제목</th>
                                    <th scope="row">작성자</th>
                                    <th scope="row">등록일자</th>
                                    <th scope="row">조회수</th>
                                </tr>
                               </thead>
                               <tbody>
                               <c:choose>
				            		<c:when test="${fn:length(TopBoardList) > 0}">
				            			<c:forEach items="${TopBoardList }" var="list" varStatus="status">
				            				<tr class="pt_notice">
			                                	<td class="pVer">공지</td>
			                                    <td class="subj"><a href="javascript:void(0);" onclick="goBoardView('${list.ntt_id}')"><span>공지</span><c:out value='${list.ntt_sj }'/></a>
			                                    <img src="/resources/client/images/contents/new.gif" alt="new">
			                                    <img src="/resources/client/images/contents/hot.gif" alt="hot">
			                                    	<div class="mVer">모바일 버젼일때만 노출
			                                        	<p><c:out value='${list.ntcr_id }'/> / <c:out value='${list.reg_dt }'/> / <c:out value='${list.rdcnt }'/></p>
			                                        </div>
			                                    </td>
			                                    <td><c:out value='${list.ntcr_nm }'/></td>
		                                       	<td><c:out value='${list.reg_dt }'/></td>
		                                       	<td><c:out value='${list.rdcnt }'/></td>
			                                </tr>
						                </c:forEach>
						            </c:when>
						        </c:choose>
		                        <c:choose>
				            		<c:when test="${fn:length(noticeList) > 0}">
				            			<c:forEach items="${noticeList }" var="list" varStatus="status">
				            				<tr>
		                                       	<td class="pVer"><c:out value='${totalNum-(status.index + (10*(currPage-1)))}'/></td>
		                                       	<td class="subj"><a href="javascript:void(0);" onclick="goBoardView('${list.ntt_id}')"><c:out value='${list.ntt_sj }'/></a>
		                                        <!-- new 아이콘 48시간(2일)유지 -->
		                                        <fmt:parseNumber value="${now.time / (1000*60*60*24)}" integerOnly="true" var="today"></fmt:parseNumber>
												<fmt:parseDate value="${list.reg_dt}" var="reg_dt" pattern="yyyy-MM-dd"/>
												<fmt:parseNumber value="${reg_dt.time / (1000*60*60*24)}" integerOnly="true" var="chgReg_dt"></fmt:parseNumber>

												<c:if test="${today - chgReg_dt le 2}">
													<img src="/resources/client/images/contents/new.gif" alt="new">
												</c:if>
												<!-- //new 아이콘 48시간(2일)유지 -->
		                                        	<div class="mVer"><!--모바일 버젼일때만 노출 -->
		                                               <p><c:out value='${list.ntcr_id }'/> / <c:out value='${list.reg_dt }'/> / <c:out value='${list.rdcnt }'/></p>
		                                           	</div>
		                                       	</td>
		                                       	<td><c:out value='${list.ntcr_nm }'/></td>
		                                       	<td><c:out value='${list.reg_dt }'/></td>
		                                       	<td><c:out value='${list.rdcnt }'/></td>
			                                </tr>
						                </c:forEach>
						            </c:when>
						            <c:otherwise>
						                <tr>
						                    <td colspan="5">조회된 결과가 없습니다.</td>
						                </tr>
						            </c:otherwise>
						        </c:choose>                               
                               </tbody>
                           </table>
                       </div>
                   </div>
                   <!--BBS_LIST //E-->
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
		<!-- #CONTENTS //E -->		
	</div> 
	<!-- #CONTAINER //E -->
<jsp:include page="/client/footer.do"></jsp:include>