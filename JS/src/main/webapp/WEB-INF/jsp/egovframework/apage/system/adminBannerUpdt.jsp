<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
$(document).ready(function () {
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

/* $(document).off( "change",".fileUpload1");
$(document).on( "change",".fileUpload1", function(){
	if(fnFileExtChk($(this).val().split("\\")[2])){
		 var fileId = Math.random().toString(36).substr(2, 15);
		 var newFileNm = "FILE_"+fileId;
		 var bid = $(this).attr("data-bid");
		 var index = $("input[id^='"+bid+"']").length; 
		 var origNm = $(this).val().split("\\")[2];
		 $(this).prev().html($(this).val());
		 $(this).prev().append("<input type='hidden' id='"+bid+index+"' name='"+bid+"' value='"+newFileNm.substr(0,20)+"'>");
		 try{
		  var fileNm = $(this).attr("name").split("#")[0];
		 }catch(e){
			 var fileNm = $(this).attr("name");	 
		 }
		 $(this).attr("name",fileNm+"#"+newFileNm.substr(0,21));
	 }else{
		 var browser = navigator.userAgent.toLowerCase();
			if (-1 != browser.indexOf('msie')) {
			    // ie 일때 input[type=file] init.
				 $(this).replaceWith(  $(this).clone(true) );
			} else {
			    // other browser 일때 input[type=file] init.
				 $(this).val("");
			}
		 $(this).prev().html("");			
		 return false;
	 }
}); */


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/system/adminBannerList.do'/>");
	$("#frm").submit();
}

function goBoardUpdate(){
	if($("#ban_subject").val()==""){
		alert("제목을 입력하세요.");
		$("#ban_subject").focus();
		return ;
	}
	
	var auth_code_chk = $('input:radio[name="ban_use"]:checked').val();
	
	if(auth_code_chk == undefined || auth_code_chk == null || auth_code_chk == ""){
		alert("사용여부 체크해주세요.");
		return;
	}
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/system/adminBannerUpdate.do'/>");
	$("#frm").submit();
}

function fileDelete(no){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/system/BannerFileDel.do'/>");
	$("#atch_file_id2").val(no);
	$("#frm").submit();
}


</script>
<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="atch_file_id2" id="atch_file_id2"/>
	<input type="hidden" name="atch_file_id" id="atch_file_id" value="<c:out value='${PopView.atch_file_id }'/>" />
	<input type="hidden" name="ban_seq" id="ban_seq" value="<c:out value='${PopView.ban_seq }'/>" />
	<input type="hidden" name="ban_up_nm"  value="<c:out value='${memberinfo.mber_name }'/>" />
	<input type="hidden" name="posblAtchFileNumber" value="1" />
	<input type="hidden" name="FileCnt" value="${fn:length(bannerFile) }">
	<div id="container">
		<h3>배너관리</h3>
		<div class="contents">
			<!-- //ct-tab -->
        
        	<div class="bbs-write">
				<table>
					<colgroup>
						<col width="15%">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th>제목</th>
							<td class="al_l">
								<input type="text" id="ban_subject" name="ban_subject" title="제목 입력" class="w40p" maxlength="150" value="<c:out value='${PopView.ban_subject }'/>" >
							</td>
						</tr>
						<tr>
							<th>작성자</th>
							<td class="al_l">
								<c:out value='${PopView.ban_reg_nm }'/>
							</td>
						</tr>
						<tr>
							<th>사용여부</th>
							<td class="al_l">
								<input type="radio" id="close" name="ban_use" value="Y" <c:if test="${PopView.ban_use eq 'Y' }">checked</c:if>  /><label for="close">사용</label>
								<input type="radio" id="open" name="ban_use" value="N" <c:if test="${PopView.ban_use eq 'N' }">checked</c:if> /><label for="open">사용안함</label>
							</td>
						</tr>
						<tr>
							<th>배너용도</th>
							<td class="al_l">
		        				<select name="ban_gubun" title="권한 선택">
									<option value="Y" <c:if test="${PopView.ban_gubun eq 'Y' }">selected</c:if>>기관배너</option>
									<option value="N" <c:if test="${PopView.ban_gubun eq 'N' }">selected</c:if>>기타</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>배너링크</th>
							<td class="al_l">
								<input type="text" id="ban_url" name="ban_url" title="배너링크 입력" class="w40p" maxlength="250" value="<c:out value='${PopView.ban_url }'/>" >
							</td>
						</tr>
						<tr class="content">
							<td class="cont al_l" colspan="2">
								<textarea id="ban_con" name="ban_con" style="width:100%;height:320px;" title="내용 입력"><c:out value='${PopView.ban_con }'/></textarea>
							</td>
						</tr>
						<tr>
							<th>배너이미지</th>
							<td class="al_l">
		                		<input name="file_1" id="egovComFileUploader" type="file" />
		                		<c:choose>
									<c:when test="${fn:length(bannerFile) > 0}">
										<c:forEach items="${bannerFile}" var="noticeList" varStatus="status">
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