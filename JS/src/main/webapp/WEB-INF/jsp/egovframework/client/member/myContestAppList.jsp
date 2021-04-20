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
	pagingUtil.__url			= "<c:url value='/membership/myContestAppList.do'/>";
	
	//msg
	var msg = $(location).attr("search").slice($(location).attr("search").indexOf("=")+1);
	if(msg != null && msg != ""){
		if(msg == "success"){
			alert("신청이 취소되었습니다.");
		}else if(msg == "updtsuccess"){
			alert("수정되었습니다.");
		}else if(msg == "timeover"){
			alert("신청취소가 불가능한 기간입니다.");
		}else if(msg == "alreadyCancel"){
			alert("이미 취소하였습니다.");
		}
	}
});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/membership/myContestAppList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val,val2,val3){
	
	if(val2 == "N"){
		alert("접수 결과 마감 중입니다.\n잠시 기다려 주세요.");
		return;
	}
	
	if(val3 == 'A' || val3 == 'L' || val3 == 'S'){
		$("#frmDetail").attr("action", "<c:url value='/membership/myEventContestAppView.do'/>");
	}else if(val3 == 'K'){
		$("#frmDetail").attr("action", "<c:url value='/membership/myKokContestAppView.do'/>");
	}else{
		$("#frmDetail").attr("action", "<c:url value='/membership/myContestAppView.do'/>");
	}
	$("#ct_seq").val(val);
	$("#frmDetail").submit();
	
}	
</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ct_seq" id="ct_seq" />
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form:form>

<div id="container" class="subpage">
	<div id="contents">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />

		<div class="sub_content">
			<div class="inner">
				<div class="sub_head">			
					<h3 class="c_tit">대회신청내역</h3>
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
                               	<label for="srch_input"><input type="text" id="srch_input" name="srch_input" value="${Srch_input }" class="w200" placeholder="제목을 입력해주세요."></label>
                               	<button type="submit" class="sb_btn">검색</button>
                           	</div>
                       	</div>
                       	</form>
                   	</div>
                   	<!-- BOARD_INFO //E -->
				
					<div class="bbs_list">
						<div class="tbl_cm">
                           	<table summary="구분, 제목, 작성자, 등록일자, 조회수 표 입니다.">
                               	<caption>대회안내</caption>
                               	<colgroup>
                                   	<col width="8%" class="pVer">
                                   	<col width="*%" class="pVer">
                                   	<col width="15%" class="pVer">
                                   	<col width="15%" class="pVer">
                                   	<col width="15%" class="pVer">
                               	</colgroup>
                               	<thead>
	                                <tr>
	                                    <th scope="row" class="pVer">NO.</th>
	                                    <th scope="row">제목</th>
	                                    <th scope="row">대회일자</th>
	                                    <th scope="row">장소</th>
	                                    <th scope="row">진행상태</th>                                    
	                                </tr>
                               	</thead>
                               	<tbody>
			                        <c:choose>
					            		<c:when test="${fn:length(contestList) > 0}">
					            			<c:forEach items="${contestList }" var="list" varStatus="status">
					            				<tr>
			                                       	<td class="pVer"><c:out value='${(totalNum+1)-list.ascnum }'/></td>
			                                       	<td class="subj"><a href="javascript:void(0);" onclick="goBoardView('${list.ct_seq}','${list.expose_yn }','${list.ct_type }')"><c:out value='${list.ct_sbj }'/></a>
			                                        	<div class="mVer"><!--모바일 버젼일때만 노출 -->
			                                               	<p>
			                                               		<c:out value='${list.ct_dt }'/> / <c:out value='${list.ct_place }'/> / 
				                                               	<c:if test="${list.ct_process eq 'R'}" >대회준비</c:if>
					                                       		<c:if test="${list.ct_process eq 'S'}" >대회신청</c:if>
					                                       		<c:if test="${list.ct_process eq 'E'}" >신청마감</c:if>
					                                       		<c:if test="${list.ct_process eq 'F'}" >대회종료</c:if>
				                                       		</p>
			                                           	</div>
			                                       	</td>
			                                       	<td><c:out value='${list.ct_dt }'/></td>
			                                       	<td><c:out value='${list.ct_place }'/></td>
			                                       	<td>
			                                       		<c:if test="${list.ct_process eq 'R'}" >대회준비</c:if>
			                                       		<c:if test="${list.ct_process eq 'S'}" >대회신청</c:if>
			                                       		<c:if test="${list.ct_process eq 'E'}" >신청마감</c:if>
			                                       		<c:if test="${list.ct_process eq 'F'}" >대회종료</c:if>
			                                       	</td>
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
			<!-- inner //E -->
		</div>
		<!-- sub_content //E -->
	</div>
	<!-- #CONTENTS //E -->
</div>
<!-- #CONTAINER //E -->

<jsp:include page="/client/footer.do"></jsp:include>