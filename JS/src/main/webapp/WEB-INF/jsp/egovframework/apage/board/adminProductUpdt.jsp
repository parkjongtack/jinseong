<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
var oEditors = [];
$(document).ready(function () {
	
	nhn.husky.EZCreator.createInIFrame({
	    oAppRef: oEditors,
	    elPlaceHolder: "ntt_cn",
	    sSkinURI: "/resources/se2/SmartEditor2Skin.html",
	    htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			fOnBeforeUnload : function(){}
		},	
	    fCreator: "createSEditor2"
	});
});

function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/board/adminProductList.do'/>");
	$("#frm").submit();
}

function goBoardUpdate(){
	
	oEditors.getById["ntt_cn"].exec("UPDATE_CONTENTS_FIELD", []);
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/board/adminProductUpdate.do'/>");
	$("#frm").submit();
}
</script>
<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="hash" id="hash" value="<c:out value='${shopView.hash }'/>" />
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" name="content_check" id="content_check" value="<c:out value='${ad_image_check }'/>" />
	<div id="container">
		<h3>제품소개_광고</h3>
		<div class="contents">
			<!-- //ct-tab -->
        
        	<div class="bbs-write">
				<table>
					<colgroup>
						<col width="15%">
						<col width="*">
						<col width="15%">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th>제품명</th>
							<td class="al_l" colspan="4"><c:out value='${shopView.name }'/></td>
						</tr>
						<tr class="content">
							<td class="cont al_l" colspan="4">
								<textarea id="ntt_cn" name="content" style="width:100%;height:320px;" title="내용 입력"><c:out value='${shopView.content }'/></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<!-- //bbs-write -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardUpdate()" class="btn-ty1 black">수정</a>
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
				</p>
			</div>
		</div>
		<!-- //contents -->
	</div>
</form:form>
		
<jsp:include page="/apage/inc/footer.do"></jsp:include>