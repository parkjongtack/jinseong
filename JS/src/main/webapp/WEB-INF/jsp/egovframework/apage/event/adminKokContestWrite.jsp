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
	
   var maxFileNum = document.frm.posblAtchFileNumber.value;
   if(maxFileNum==null || maxFileNum==""){
     maxFileNum = 3;
    }     
   var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList' ), maxFileNum );
   multi_selector.addElement( document.getElementById( 'egovComFileUploader' ) );
	
});


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/event/kokContestList.do'/>");
	$("#frm").submit();
}

function goBoardWrite(){
	if($("#ntt_sj").val()==""){
		alert("제목을 입력하세요.");
		$("#ntt_sj").focus();
		return ;
	}
	
	var auth_code_chk = $('input:radio[name="use_at"]:checked').val();
	
	if(auth_code_chk == undefined || auth_code_chk == null || auth_code_chk == ""){
		alert("사용여부 체크해주세요.");
		return;
	}
	
	oEditors.getById["ntt_cn"].exec("UPDATE_CONTENTS_FIELD", []);
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/event/kokContestReg.do'/>");
	$("#frm").submit();
}


</script>

<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="ntcr_nm"  value="<c:out value='${memberinfo.mber_name }'/>" />
	<input type="hidden" name="ntcr_id"  value="<c:out value='${memberinfo.mber_id }'/>" />
	<input type="hidden" name="posblAtchFileNumber" value="3" />
	<div id="container">
		<h3>왕중왕전 대회안내 등록</h3>
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
							<th>제목</th>
							<td class="al_l">
								<input type="text" id="ntt_sj" name="ntt_sj" title="제목 입력" class="w90p" maxlength="150" >
							</td>
							<th>작성자</th>
							<td class="al_l">
								<c:out value='${memberinfo.mber_name }'/>
							</td>
						</tr>
						<tr>
							<th>상단공지여부</th>
							<td class="al_l">
								<input type="radio" id="close" name="check_yn" value="Y" /><label for="close">Y(사용)</label>
								<input type="radio" id="open" name="check_yn" value="N"  checked /><label for="open">N(사용 안함)</label>
							</td>
							<th>사용여부</th>
							<td class="al_l">
								<input type="radio" id="close" name="use_at" value="Y" checked /><label for="close">Y(사용)</label>
								<input type="radio" id="open" name="use_at" value="N"  /><label for="open">N(사용 안함)</label>
							</td>
						</tr>
						<tr class="content">
							<td class="cont al_l" colspan="4">
								<textarea id="ntt_cn" name="ntt_cn" style="width:100%;height:320px;" title="내용 입력"></textarea>
							</td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td class="al_l" colspan="3">
		                		<input name="file_1" id="egovComFileUploader" type="file" />
		                		<div id="egovComFileList"></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<!-- //bbs-write -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardWrite()" class="btn-ty1 black">등록</a>
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
				</p>
			</div>
		</div>
		<!-- //contents -->
	</div>
</form:form>
		
<jsp:include page="/apage/inc/footer.do"></jsp:include>