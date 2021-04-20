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
	     multi_selector.addElement2( document.getElementById( 'egovComFileUploader' ) );
	   }else{
		  $("#egovComFileUploader").css('display','none');
	   }  
	   
	   var existFileNum2 = document.frm.FileCnt2.value;
	   var maxFileNum2 = document.frm.posblAtchFileNumber2.value;
	   var uploadableFileNum2 = maxFileNum2 - existFileNum2; // 최대등록가능한 파일숫자에서 기존에 등록된 숫자를 뺀다.
	   if(uploadableFileNum2<0) {
	     uploadableFileNum2 = 0;
	   }
	   if(uploadableFileNum2 != 0){
		 $("#egovComFileUploader2").css('display','');
	     var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList2' ), uploadableFileNum );
	     multi_selector.addElement( document.getElementById( 'egovComFileUploader2' ) );
	   }else{
		  $("#egovComFileUploader2").css('display','none');
	   }  
});


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestRstList.do'/>");
	$("#frm").submit();
}

function goBoardWrite(){
	
	if($("#ntt_sj").val()==""){
		alert("대회명을 입력하세요.");
		$("#ntt_sj").focus();
		return ;
	}

	oEditors.getById["ntt_cn"].exec("UPDATE_CONTENTS_FIELD", []);
	
	//var dataString =  $("#frm").serialize();
	
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestRstUpdt.do'/>");
	$("#frm").submit();
}

function goBoardDel(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/contest/adminContestRstDelete.do'/>");
	$("#frm").submit();
}

function fileDelete(no){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/contest/contestRstFileDel.do'/>");
	$("#atch_file_id2").val(no);
	$("#frm").submit();
}


</script>

<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="use_at"  value="Y" />
	<input type="hidden" name="atch_file_id2" id="atch_file_id2"/>
	<input type="hidden" name="atch_file_id" id="atch_file_id" value="<c:out value='${contestRstView.atch_file_id }'/>" />
	<input type="hidden" name="ntt_id" id="ntt_id" value="<c:out value='${contestRstView.ntt_id }'/>" />
	<input type="hidden" name="updt_id"  value="<c:out value='${memberinfo.mber_id }'/>" />
	<input type="hidden" name="posblAtchFileNumber" value="1" />
	<input type="hidden" name="posblAtchFileNumber2" value="5" />
	<input type="hidden" name="FileCnt" value="${fn:length(imgFile) }">
	<input type="hidden" name="FileCnt2" value="${fn:length(addFile) }">
	<div id="container">
		<h3>대회결과 수정</h3>
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
							<th>대회명</th>
							<td class="al_l" colspan="3">
								<input type="text" id="ntt_sj" name="ntt_sj" value="<c:out value='${contestRstView.ntt_sj }'/>"/>
							</td>
						</tr>
						<tr>
							<th>요약</th>
							<td class="al_l" colspan="3">
								<input type="text" id="memo" name="memo" value="<c:out value='${contestRstView.memo }'/>"/>
							</td>
						</tr>
						<tr>
							<th>대표이미지</th>
							<td class="al_l" colspan="3">
								<input name="img_1" id="egovComFileUploader" type="file" />
		                		<c:choose>
									<c:when test="${fn:length(imgFile) > 0}">
										<c:forEach items="${imgFile}" var="list" varStatus="status">
										<div>
											<a href="javascript:void(0);" onclick="Down('${list.atch_file_id }');">${list.orignl_file_nm }</a>
											<input type="button" title="삭제" value="Delete" class="file_cla" style='cursor:pointer;' onclick="fileDelete('${list.atch_file_id }');" /><br>
										</div>
										</c:forEach>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
		                		<div id="egovComFileList"></div>
							</td>
						</tr>						
						<tr>
							<th>이미지</th>
							<td class="al_l" colspan="3">
								<input name="file_1" id="egovComFileUploader2" type="file" />
		                		<c:choose>
									<c:when test="${fn:length(addFile) > 0}">
										<c:forEach items="${addFile}" var="list" varStatus="status">
										<div>
											<a href="javascript:void(0);" onclick="Down('${list.atch_file_id }');">${list.orignl_file_nm }</a>
											<input type="button" title="삭제" value="Delete" class="file_cla" style='cursor:pointer;' onclick="fileDelete('${list.atch_file_id }');" /><br>
										</div>
										</c:forEach>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
		                		<div id="egovComFileList2"></div>
							</td>
						</tr>
						<tr class="content">							
							<td class="cont al_l"  colspan="4">
								<textarea id="ntt_cn" name="ntt_cn" style="width:100%;height:320px;" title="내용 입력">${contestRstView.ntt_cn }</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<!-- //bbs-write -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardWrite()" class="btn-ty1 black">수정</a>
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
				</p>
			</div>
			
		</div>
		<!-- //contents -->
	</div>
</form:form>
<c:if test="${!empty msg }" >
	<script type="text/javascript">
	var msg	= '${msg}'; 
	if(msg != ''){
		alert(msg);	
		location.href = '/apage/contest/adminContestRstList.do';
	}
	</script>
</c:if>
<jsp:include page="/apage/inc/footer.do"></jsp:include>