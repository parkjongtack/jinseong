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
	pagingUtil.__url			= "<c:url value='/event/eventContestAppList.do'/>";

});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/event/eventContestAppList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/event/eventContestAppView.do'/>");
	$("#ct_seq").val(val);
	$("#frmDetail").submit();
}

function goLaneView(val,flag){
	if(val != 0){ //(flag =='E' || flag =='F') && 
		if( val == '231'){
			alert("접수 테스트 대회입니다.");
			return;
		}else{
			$("#frmLane input[name=ntt_id]").val(val);
			$("#frmLane").attr("action","<c:url value='/event/eventContestLaneResult.do'/>");
			$("#frmLane").submit();
		}
	}else{
		alert("레인배치 결과가 존재하지 않습니다.");
		return;
	}
}

function goAppResult(val,flag){
	if((flag =='E' || flag =='F') && val != 0){
		if( val == '231'){
			alert("접수 테스트 대회입니다.");
			return;
		}else{
			$("#frmDetail").attr("action", "<c:url value='/event/eventContestAppResult.do'/>");
			$("#ct_seq").val(val);
			$("#frmDetail").submit();
		}
	}else{
		alert("대회가 종료되었습니다.");
		return;
	}
}
</script>
<form id="frmLane">
	<input type="hidden" name="ntt_id">
	<input type="hidden" name="bbs_id" value="laneResult"> 
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="srch_input" value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ct_seq" id="ct_seq" />
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
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
                        <h3 class="c_tit">이벤트 대회신청</h3>
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
                               <caption>대회안내</caption>
                               <colgroup>
                                   <col width="8%" class="pVer">
                                   <col width="*%" class="pVer">
                                   <col width="15%" class="pVer">
                                   <col width="15%" class="pVer">
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
                                    <th scope="row">신청결과</th>
                                    <th scope="row">레인배치</th>
                                </tr>
                               </thead>
                               <tbody>
		                         <c:choose>
				            		<c:when test="${fn:length(contestList) > 0}">
				            			<c:forEach items="${contestList }" var="list" varStatus="status">
				            				<tr>
		                                       	<td class="pVer"><c:out value='${totalNum-(status.index + (10*(currPage-1)))}'/></td>
		                                       	<%-- <td class="pVer"><c:out value='${(totalNum+1)-list.ascnum }'/></td> --%>
		                                       	<td class="subj"><a href="javascript:void(0);" onclick="goBoardView('${list.ct_seq}')"><c:out value='${list.ct_sbj }'/></a>
		                                        	<div class="mVer"><!--모바일 버젼일때만 노출 -->
		                                               <p><c:out value='${list.ct_dt }'/> / <c:out value='${list.ct_place }'/> / 
		                                               	<c:if test="${list.ct_process eq 'R'}" ><span style="color: green;">대회준비</span></c:if>
			                                       		<c:if test="${list.ct_process eq 'S'}" ><span style="color: blue;">대회신청</span></c:if>
			                                       		<c:if test="${list.ct_process eq 'E'}" ><span style="color: red;">신청마감</span></c:if>
			                                       		<c:if test="${list.ct_process eq 'F'}" ><span style="color: black;">대회종료</span></c:if></p>
														<div class="mVer_btn">
															<c:choose>
																<c:when test="${list.ct_process ne 'F'}"> <!-- 대회종료가 아니고 -->
																	<c:if test="${list.expose_yn eq 'Y'  && list.ct_process eq 'E' }"> <!-- 선정결과 노출여부가 Y이면 버튼 보이게 하고 -->
																		<a href="javascript:void(0)" onclick="goAppResult('<c:out value="${list.ct_seq }"/>','<c:out value="${list.ct_process }"/>')" class="">신청결과</a>
																	</c:if>
																	<c:if test="${list.expose_yn eq 'N' }"> <!-- 선정결과 노출여부가 N이면 버튼 안 보이게 -->
																		
																	</c:if>
																</c:when>
																<c:otherwise> <!-- 대회종료이면 -->
																	<c:if test="${list.expose_yn eq 'Y' }"> <!-- 선정결과 노출여부가 Y이면 버튼 보이게 하고 -->
																		<a href="javascript:alert('종료된 대회입니다.')" class="">신청결과</a>
																	</c:if>
																	<c:if test="${list.expose_yn eq 'N' }"> <!-- 선정결과 노출여부가 N이면 버튼 안 보이게 -->
																		
																	</c:if>
																</c:otherwise>
															</c:choose>

															<c:if test="${list.ntt_id != 0  && list.ct_result eq 'Y' }"> <!-- 2. 대회종료가 아니고 레인배치 결과가 있으면 -->
																<a href="javascript:void(0)" onclick="goLaneView('<c:out value="${list.ntt_id }"/>','<c:out value="${list.ct_process }"/>')" class="">배치결과</a>
															</c:if>
														</div>
		                                           	</div>
		                                       	</td>
		                                       	<td><c:out value='${list.ct_dt }'/></td>
		                                       	<td><c:out value='${list.ct_place }'/></td>
		                                       	<td>
		                                       		<c:if test="${list.ct_process eq 'R'}" ><span style="color: green;">대회준비</span></c:if>
		                                       		<c:if test="${list.ct_process eq 'S'}" ><span style="color: blue;">대회신청</span></c:if>
		                                       		<c:if test="${list.ct_process eq 'E'}" ><span style="color: red;">신청마감</span></c:if>
		                                       		<c:if test="${list.ct_process eq 'F'}" ><span style="color: black;">대회종료</span></c:if>
		                                       	</td>
		                                       	<td>
		                                       		<c:choose>
				            							<c:when test="${list.ct_process ne 'F'}"> <!-- 대회종료가 아니고 -->
				                                       		<c:if test="${list.expose_yn eq 'Y' && list.ct_process eq 'E' }"> <!-- 선정결과 노출여부가 Y이면 버튼 보이게 하고 -->
			                                       				<a href="javascript:void(0)" onclick="goAppResult('<c:out value="${list.ct_seq }"/>','<c:out value="${list.ct_process }"/>')" class="appli_result">신청결과</a>
		                                       				</c:if>
		                                       				<c:if test="${list.expose_yn eq 'N' }"> <!-- 선정결과 노출여부가 N이면 버튼 안 보이게 -->
			                                       				
		                                       				</c:if>
				            							</c:when>
				            							<c:otherwise> <!-- 대회종료이면 -->
				            								<c:if test="${list.expose_yn eq 'Y' }"> <!-- 선정결과 노출여부가 Y이면 버튼 보이게 하고 -->
			                                       				<a href="javascript:alert('종료된 대회입니다.')" class="deploy_result">신청결과</a>
		                                       				</c:if>
		                                       				<c:if test="${list.expose_yn eq 'N' }"> <!-- 선정결과 노출여부가 N이면 버튼 안 보이게 -->
			                                       				
		                                       				</c:if>
				            							</c:otherwise>
				            						</c:choose>
		                                       		<!-- 
		                                       		<c:if test="${list.ct_process eq 'E'}">
	                                       				<a href="javascript:void(0)" onclick="goAppResult('<c:out value="${list.ct_seq }"/>','<c:out value="${list.ct_process }"/>')" class="appli_result">신청결과</a>
                                       				</c:if>
		                                       		<c:if test="${list.ct_process eq 'F'}">
	                                       				<a href="javascript:void(0)" onclick="alert('대회가 종료되었습니다.')" class="deploy_result">신청결과</a>
                                       				</c:if>
                                       				 -->
		                                       	</td>
		                                       	<td>
		                                       		<!-- 1.자리배치 전에는 버튼 미노출 -->
		                                       	<c:if test = "${list.ntt_id != 0  && list.ct_result eq 'Y' }">
		                                       		<a href="javascript:void(0)" onclick="goLaneView('<c:out value="${list.ntt_id }"/>','<c:out value="${list.ct_process }"/>')" class="appli_result">배치결과</a>
		                                       	</c:if>
		                                       	
		                                       	<c:if test = "${list.ct_result  eq 'N' }">
		                                       	</c:if>
		                                       		
		                                       		<!-- <c:if test="${list.ct_process ne 'F' && list.ntt_id != 0 }">  2. 대회종료가 아니고 레인배치 결과가 있으면 
	                                       				<a href="javascript:void(0)" onclick="goLaneView('<c:out value="${list.ntt_id }"/>','<c:out value="${list.ct_process }"/>')" class="appli_result">배치결과</a>
                                       				</c:if>
                                       				<c:if test="${list.ct_process eq 'F' && list.ntt_id != 0 }">  3. 대회종료이면 검은색 버튼으로 
	                                       				<a href="javascript:alert('종료된 대회입니다.')" class="deploy_result">배치결과</a>
                                       				</c:if> -->
		                                       	
		                                       	
				            						<!-- 
		                                       		<c:if test="${list.ct_process eq 'F' || list.ct_process eq 'E' }"> 
	                                       				<a href="javascript:void(0)" onclick="goLaneView('<c:out value="${list.ntt_id }"/>','<c:out value="${list.ct_process }"/>')" class="deploy_result">배치결과</a>
                                       				</c:if>
                                       				 -->
		                                       	</td>
			                                </tr>
						                </c:forEach>
						            </c:when>
						            <c:otherwise>
						                <tr>
						                    <td colspan="7">조회된 결과가 없습니다.</td>
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
<c:if test="${!empty msg }" >
	<script type="text/javascript">
	var msg	= '${msg}'; 
	if(msg != ''){
		alert(msg);	
		location.href = '/event/eventContestAppList.do';
	}
	</script>
</c:if>	
<jsp:include page="/client/footer.do"></jsp:include>