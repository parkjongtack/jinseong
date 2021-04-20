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
	pagingUtil.__url			= "<c:url value='/board/asList.do'/>";

	//msg
	$.urlParam = function(name){
		var results = new RegExp("[\?&]" + name + "=([^&#]*)").exec(window.location.href);
	    if(results == null){
	       return null;
	    }
	    else{
	       return results[1] || 0;
	    }
	}  
	var msg = $.urlParam("msg");
	if(msg != null){
		if(msg == "insert.success"){
			alert("등록되었습니다.");	
		}else if(msg == "login.fail"){
			if(confirm('재 로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?')) {
				//location.href = '/membership/login.do';
				$("#returnUrl").val("asList");
				$("#frmDetail").attr("action","/membership/login.do");
				$("#frmDetail").submit();
				return;
			}
		}else if(msg == "delete.success"){
			alert("삭제되었습니다.");	
		}else{
			alert("실패했습니다.");
		}
	}
	

	/*
	var msg = "${msg}";
	if(msg != ""){
		alert(msg);	
	}
	*/
});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/board/asList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/board/asDetail.do'/>");
	$("#ntt_id").val(val);
	$("#frmDetail").submit();
}
function chkApp(){
	
	if(isLogin == false){
		if(confirm('로그인 후 이용이 가능합니다. 로그인 페이지로 이동하시겠습니까?')) {
			//location.href = '/membership/login.do';
			$("#returnUrl").val("asList");
			$("#frmDetail").attr("action","/membership/login.do");
			$("#frmDetail").submit();
			return;
		} 
		
	} else {
		location.href = '/board/asWrite.do';
	}
}

</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ntt_id" id="ntt_id" />
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" name="returnUrl" id="returnUrl" value="" />
</form:form>

<c:set var="now" value="<%=new java.util.Date() %>"/>

	<div id="container" class="subpage">
		<div id="contents" >
            <h2 class="hide">본문</h2>
            <c:import url="/client/snb.do" />       
                
 			<div class="sub_content">
                <div class="inner">                
                <div class="sub_head">
                    <h3 class="c_tit">A/S 게시판</h3>
                </div>
                  
                <div class="board_list_area sub_fade">
                   	<div class="board_info">                   
                       	<div class="l_bx">
                           	<p>총 <span class="ft_or"><c:out value='${totalNum }'/></span>건 (페이지 <span  class="ft_or">${currPageInfo }</span>/${pageCntInfo })</p>
                       	</div>
                       	<%-- <form id="frm" name="frm" method="post"> --%>
                       	<form:form commandName="vo" method="post" id="frm" name="frm">
	                       	<div class="r_bx">
	                           	<div class="schBx">
	                           		<form:select path="as_type" class="w20p">
			                           	<form:option value="">::전체::</form:option>
			                            <form:option value="볼링볼">볼링볼</form:option>
			                            <form:option value="볼링가방">볼링가방</form:option>
			                            <form:option value="볼링의류">볼링의류</form:option>
			                            <form:option value="볼링아대">볼링아대</form:option>
			                            <form:option value="볼링슈즈">볼링슈즈</form:option>
			                            <form:option value="볼링악세사리">볼링악세사리</form:option>
			                            <form:option value="프로샵 용품">프로샵 용품</form:option>
			                            <form:option value="기타">기타</form:option>
				                	</form:select>
	                               	<label for="schKeword"><input type="text" id="schKeword" name="srch_input" value="${Srch_input }" class="w200"></label>
	                               	<button type="submit" class="sb_btn">검색</button>
	                           	</div>
	                       	</div>
	                    </form:form>
                       	<%-- </form> --%>
                   	</div>
                   <!-- BOARD_INFO //E -->
                   
                   <div class="bbs_list">
                       <div class="tbl_cm">
                           <table summary="구분, 제목, 작성자, 등록일자, 조회수 표 입니다.">
                               <caption>공지사항</caption>
                               <colgroup>
                                   <col width="5%" class="pVer">
                                   <col width="5%" class="pVer">
                                   <col width="50%" class="pVer">
                                   <col width="10%" class="pVer">
                                   <col width="10%" class="pVer">
                                   <col width="10%" class="pVer">
                                   <col width="10%" class="pVer">
                               </colgroup>
                               <thead>
                                <tr>
                                    <th scope="col">NO.</th>
                                    <th scope="col">분류</th>
                                    <th scope="col">제목</th>
                                    <th scope="col">작성자</th>
                                    <th scope="col">등록일자</th>
                                    <th scope="col">조회수</th>
                                    <th scope="col">처리상태</th>
                                </tr>
                               	</thead>
                               	<tbody>
                               	<c:choose>
				            		<c:when test="${fn:length(asList) > 0}">
				            			<c:forEach items="${asList }" var="list" varStatus="status">
			                                <tr>
		                                       	<td class="pVer"><c:out value='${totalNum-(status.index + (10*(currPage-1)))}'/></td>
		                                       	<td><c:out value='${list.as_type }'/></td>
		                                       	<td class="subj">								   
		                                       		<c:choose>
			                                       	 	<c:when test="${list.ntcr_id eq sessionScope.mberInfo.mber_id }">
			                                       	   		<a href="javascript:void(0);" onclick="goBoardView('${list.ntt_id}')"><c:out value='${list.ntt_sj }'/>(${list.comment_cnt })</a>
			                                       	   		<!-- new 아이콘 48시간(2일)유지 -->
					                                        <fmt:parseNumber value="${now.time / (1000*60*60*24)}" integerOnly="true" var="today"></fmt:parseNumber>
															<fmt:parseDate value="${list.reg_dt}" var="reg_dt" pattern="yyyy-MM-dd"/>
															<fmt:parseNumber value="${reg_dt.time / (1000*60*60*24)}" integerOnly="true" var="chgReg_dt"></fmt:parseNumber>
			
															<c:if test="${today - chgReg_dt le 2}">
																<img src="/resources/client/images/contents/new.gif" alt="new">
															</c:if>
															<!-- //new 아이콘 48시간(2일)유지 -->
			                                       		</c:when>
			                                       		<c:otherwise>
			                                       			<a href="javascript:void(0);" onclick="alert('작성자만 확인이 가능합니다.');"><c:out value='${list.ntt_sj }'/>(${list.comment_cnt })</a>
			                                       			<!-- new 아이콘 48시간(2일)유지 -->
					                                        <fmt:parseNumber value="${now.time / (1000*60*60*24)}" integerOnly="true" var="today"></fmt:parseNumber>
															<fmt:parseDate value="${list.reg_dt}" var="reg_dt" pattern="yyyy-MM-dd"/>
															<fmt:parseNumber value="${reg_dt.time / (1000*60*60*24)}" integerOnly="true" var="chgReg_dt"></fmt:parseNumber>
			
															<c:if test="${today - chgReg_dt le 2}">
																<img src="/resources/client/images/contents/new.gif" alt="new">
															</c:if>
															<!-- //new 아이콘 48시간(2일)유지 -->
			                                       		</c:otherwise>
		                                       		</c:choose>		                                       
			                                       	<div class="mVer"><!--모바일 버젼일때만 노출 -->
			                                       		<p>
			                                       			<c:if test="${list.as_status eq 'R'}">접수</c:if>
			                                       			<c:if test="${list.as_status eq 'C'}">처리완료</c:if>
			                                       		 	/ <c:out value='${list.as_type }'/> / <c:out value='${list.ntcr_nm }'/>  / <c:out value='${list.reg_dt }'/> / <c:out value='${list.rdcnt }'/></p>
			                                        </div>
		                                        </td>
		                                       	<td><c:out value='${list.ntcr_id }'/></td>
		                                       	<td><c:out value='${list.reg_dt }'/></td>
		                                       	<td><c:out value='${list.rdcnt }'/></td>
		                                       	<td>
			                                       	<c:if test="${list.as_status eq 'R'}"><span class="btn_gray">접수</span></c:if>
			                                       	<c:if test="${list.as_status eq 'C'}"><span class="btn_or">처리완료</span></c:if>
		                                       		<!--접수 대기중 일때 btn_gray 클래스 추가 ..  처리완료 일때 btn_or 추가-->		
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
                       <!-- 이벤트 게시판 글쓰기 버튼-->
                       <div class="btn_r">
                       			<a href="javascript:void();" onclick="chkApp()" >글쓰기</a>
                       </div> 
                       
                       
                   </div>
                   <!--BBS_LIST //E-->
                    
                    <div class="paging">
                        ${pageNav }
                    </div>
                    <!--PAGING //E-->
                </div>
               </div>
            </div>
		</div>
		<!-- #CONTENTS //E -->
		
	</div> 
	<!-- #CONTAINER //E -->

<jsp:include page="/client/footer.do"></jsp:include>