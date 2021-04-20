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

});

function goBoardList(){
	$("#frmDetail").attr("action", "<c:url value='/kegel/laneConsumable.do'/>");
	$("#frmDetail").submit();
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

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ntt_id" id="ntt_id" value="${boardView.ntt_id }" />
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
                            <h3 class="c_tit">레인정비 소모품</h3>
                        </div>

                    <div class="board_area sub_fade">
                        <div class="bbs_view">
                            <div class="tbl_w">
                                <table summary="게시판 글쓰기 표로 제목, 구분, 내용,첨부파일 등이 있습니다.">
                                    <caption>게시판 작성</caption>
                                    <colgroup>
                                        <col width="25%" class="">
                                        <col width="75%" class="">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th scope="col">명칭/사양</th>
                                            <td>${boardView.ntt_sj }</td>
                                        </tr>
                                        <tr>
                                            <th scope="col">설명(특징)기재</th>
                                            <td><p class="txt">${boardView.memo }</p>
										</td>
                                        </tr>
                                         <tr>
                                            <th scope="col">대표이미지</th>
                                            <td>
                                               <c:choose>
													<c:when test="${fn:length(imgFile) > 0}">
														<c:forEach items="${imgFile}" var="list" varStatus="status">
														<a href="javascript:void(0);" onclick="Down('${list.atch_file_id }');" class="attach"><img src="/resources/client/images/contents/file.gif" alt="첨부파일 아이콘">${list.orignl_file_nm }</a>
														</c:forEach>
													</c:when>
													<c:otherwise>첨부파일이 없습니다.</c:otherwise>
												</c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="col"><label for="w_file2">이미지</label></th>
                                            <td>
                                                <c:choose>
													<c:when test="${fn:length(addFile) > 0}">
														<c:forEach items="${addFile}" var="list" varStatus="status">
														<a href="javascript:void(0);" onclick="Down('${list.atch_file_id }');" class="attach"><img src="/resources/client/images/contents/file.gif" alt="첨부파일 아이콘">${list.orignl_file_nm }</a>
														</c:forEach>
													</c:when>
													<c:otherwise>첨부파일이 없습니다.</c:otherwise>
												</c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="view_con">
                                               <p class="txt">${boardView.ntt_cn }</p>
                                            </td>
                                        </tr>
                                       
                                    </tbody>
                                </table>
                            </div>
                            
                        </div>
                        <!--BOARD_WRITE //E -->

                        <div class="btn_r2">
                            <a href="javascript:goBoardList();" class="">목록</a>
                        </div>
                    </div>
                    <!--BOARD_AREA //E-->
                </div>
            </div>
		</div>
		<!-- #CONTENTS //E -->
	</div> 
	<!-- #CONTAINER //E -->
<jsp:include page="/client/footer.do"></jsp:include>