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
   multi_selector.addElement2( document.getElementById( 'egovComFileUploader' ) );
   
   var maxFileNum2 = document.frm.posblAtchFileNumber2.value;
   if(maxFileNum2==null || maxFileNum2==""){
     maxFileNum2 = 5;
    }     
   var multi_selector2 = new MultiSelector( document.getElementById( 'egovComFileList2' ), maxFileNum2 );
   multi_selector2.addElement( document.getElementById( 'egovComFileUploader2' ) );
	
});


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/board/adminTrainingList.do'/>");
	$("#frm").submit();
}

function goBoardWrite(){
	if($("#ntt_sj").val()==""){
		alert("제목을 입력하세요.");
		$("#ntt_sj").focus();
		return ;
	}

	oEditors.getById["ntt_cn"].exec("UPDATE_CONTENTS_FIELD", []);
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/board/adminTrainingReg.do'/>");
	$("#frm").submit();
}


</script>

<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="ntcr_nm"  value="<c:out value='${memberinfo.mber_name }'/>" />
	<input type="hidden" name="ntcr_id"  value="<c:out value='${memberinfo.mber_id }'/>" />
	<input type="hidden" name="posblAtchFileNumber" value="1" />
	<input type="hidden" name="posblAtchFileNumber2" value="5" />
	<input type="hidden" name="use_at" value="Y" />
	<div id="container">
		<h3>훈련 도구</h3>
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
							<th>명칭/사양</th>
							<td class="al_l" colspan="3">
								<input type="text" name="ntt_sj" id="ntt_sj" value=""/>
							</td>
						</tr>
						<tr>
							<th>설명(특징)기재</th>
							<td class="al_l" colspan="3">
								<input type="text" name="memo" id="memo" value=""/>
							</td>
						</tr>
						<tr>
							<th>대표이미지</th>
							<td class="al_l" colspan="3">
		                		<input name="img_1" id="egovComFileUploader" type="file" />
		                		<div id="egovComFileList"></div>
							</td>
						</tr>						
						<tr>
							<th>이미지</th>
							<td class="al_l" colspan="3">
		                		<input name="file_1" id="egovComFileUploader2" type="file" />
		                		<div id="egovComFileList2"></div>
							</td>
						</tr>
						<tr class="content">
							<th>제품설명</th>	
							<td class="cont al_l"  colspan="3">
								<textarea id="ntt_cn" name="ntt_cn" style="width:100%;height:320px;" title="내용 입력"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<!-- //bbs-write -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
					<a href="javascript:goBoardWrite()" class="btn-ty1 black">저장</a>
				</p>
			</div>
			
		</div>
		<!-- //contents -->
	</div>
</form:form>
		
<jsp:include page="/apage/inc/footer.do"></jsp:include>