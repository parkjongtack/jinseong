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
	
	   var existFileNum = document.frm.FileCnt.value;
	   var maxFileNum = document.frm.posblAtchFileNumber.value;
	   var uploadableFileNum = maxFileNum - existFileNum; // 최대등록가능한 파일숫자에서 기존에 등록된 숫자를 뺀다.
	   if(uploadableFileNum<0) {
	     uploadableFileNum = 0;
	   }
	   if(uploadableFileNum != 0){
		 $("#egovComFileUploader").css('display','');
	     var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList' ), uploadableFileNum );
	     multi_selector.addElement( document.getElementById( 'egovComFileUploader' ) );
	   }else{
		  $("#egovComFileUploader").css('display','none');
	   }  
});

function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventList.do'/>");
	$("#frm").submit();
}

function goBoardUpdate(){
	
	if($("#ntt_sj").val()==""){
		alert("제목을 입력하세요.");
		$("#ntt_sj").focus();
		return ;
	}
	
	var auth_code_chk = $('input:radio[name="use_at"]:checked').val();
	if(auth_code_chk == undefined || auth_code_chk == null || auth_code_chk == ""){
		alert("사용여부를 체크해주세요.");
		return;
	}
	
	oEditors.getById["ntt_cn"].exec("UPDATE_CONTENTS_FIELD", []);
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventNoticeUpdate.do'/>");
	$("#frm").submit();
}

function fileDelete(no){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/board/ShopeventNoticeFileDel.do'/>");
	$("#atch_file_id2").val(no);
	$("#frm").submit();
}


</script>
<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="atch_file_id2" id="atch_file_id2"/>
	<input type="hidden" name="atch_file_id" id="atch_file_id" value="<c:out value='${boardView.atch_file_id }'/>" />
	<input type="hidden" name="ntt_id" id="ntt_id" value="<c:out value='${boardView.ntt_id }'/>" />
	<input type="hidden" name="last_updusr_id"  value="<c:out value='${memberinfo.mber_id }'/>" />
	<input type="hidden" name="posblAtchFileNumber" value="3" />
	<input type="hidden" name="FileCnt" value="${fn:length(noticeFile) }">
	<div id="container">
		<h3>세미나 공지사항</h3>
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
								<input type="text" id="ntt_sj" name="ntt_sj" title="제목 입력" class="w90p" maxlength="150" value="<c:out value='${boardView.ntt_sj }'/>" >
							</td>
							<th>작성자</th>
							<td class="al_l">
								<c:out value='${boardView.ntcr_id }'/>
							</td>
						</tr>
						<tr>
							<th>사용여부</th>
							<td class="al_l" colspan="3">
								<input type="hidden" name="check_yn" value="Y">
								<input type="radio" id="close" name="use_at" value="Y" <c:if test="${boardView.use_at eq 'Y' }">checked</c:if>  /><label for="close">사용</label>
								<input type="radio" id="open" name="use_at" value="N" <c:if test="${boardView.use_at eq 'N' }">checked</c:if> /><label for="open">사용안함</label>
							</td>
						</tr>
						<tr class="content">
							<td class="cont al_l" colspan="4">
								<textarea id="ntt_cn" name="ntt_cn" style="width:100%;height:320px;" title="내용 입력"><c:out value='${boardView.ntt_cn }'/></textarea>
							</td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td class="al_l" colspan="4">
		                		<input name="file_1" id="egovComFileUploader" type="file" />
		                		<c:choose>
									<c:when test="${fn:length(noticeFile) > 0}">
										<c:forEach items="${noticeFile}" var="noticeList" varStatus="status">
										<div>
											<a href="javascript:void(0);" onclick="Down('${noticeList.atch_file_id }');">${noticeList.orignl_file_nm }</a>
											<input type="button" title="삭제" value="Delete" class="file_cla" style='cursor:pointer;' onclick="fileDelete('${noticeList.atch_file_id }');" /><br>
										</div>
										</c:forEach>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
		                		<div id="egovComFileList"></div>
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