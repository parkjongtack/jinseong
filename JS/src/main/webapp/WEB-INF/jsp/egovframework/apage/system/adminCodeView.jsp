<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">

function goBoardList(){
	$("#frm").attr("action", "/apage/system/adminCodeList.do");
	$("#frm").submit();
}

function goDelete(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "/apage/system/adminCodeDelete.do");
	$("#frm").submit();
}

function goModify(){
	$("#frm").attr("action", "/apage/system/adminCodeModify.do");
	$("#frm").submit();
}
</script>
<form name="frm" id="frm" method="post" >
	<input type="hidden" name="code_id_seq" id="code_id_seq" value="<c:out value='${codeView.code_id_seq }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow" value="<c:out value='${currPage }'/>"/>
<div id="container">
	<h3>공통코드관리</h3>
	<div class="contents">			
		<div class="bbs-view">
			<h5><c:out value='${codeView.code_id_nm }'/></h5>
               <table>
               	<colgroup><col width="10%" /><col width="" /><col width="10%" /><col width="" /><col width="10%" /><col width="" /></colgroup>
               	<tbody>
                   	<tr>
                       	<th>분류명</th>
                           <td><c:out value='${codeView.cl_code_nm }'/></td>
                       	<th>사용여부</th>
                           <td>
                           	<c:if test="${codeView.use_yn eq 'Y' }">사용</c:if>
							<c:if test="${codeView.use_yn eq 'N' }">사용안함</c:if>
						</td>
                           <th>등록자</th>
                           <td><c:out value='${codeView.reg_nm }'/></td>
                       </tr>
                   </tbody>
			</table>
		</div>
		
		<!-- //attach -->
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goDelete()" class="btn-ty1 black">삭제</a>
				<a href="javascript:goModify()" class="btn-ty1 blue">수정</a>
				<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
			</p>
		</div>
		<!-- //bbs-view -->
	</div>
</div>
</form>
<jsp:include page="/apage/inc/footer.do"></jsp:include>