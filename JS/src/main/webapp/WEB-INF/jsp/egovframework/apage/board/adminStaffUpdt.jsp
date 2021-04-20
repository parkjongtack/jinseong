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
	    elPlaceHolder: "staff_info",
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
	$("#frm").attr("action", "<c:url value='/apage/board/adminStaffList.do'/>");
	$("#frm").submit();
}

function goBoardWrite(){
	
	if($("#staff_name").val()==""){
		alert("제목을 입력하세요.");
		$("#staff_name").focus();
		return ;
	}
	/*
	if($("#asso_type").val() == ""){
		alert("협회를 선택하세요.");
		$("#asso_type").focus();
		return ;
	}
	if($("#staff_type").val() == ""){
		alert("staff구분을 선택하세요.");
		$("#staff_type").focus();
		return ;
	}
	if($("#team").val() == ""){
		alert("소속을 입력하세요.");
		$("#team").focus();
		return ;
	}
	if($("#pro_no").val() == ""){
		alert("프로기수를 입력하세요.");
		$("#pro_no").focus();
		return ;
	}
	if($("#career").val() == ""){
		alert("구력을 입력하세요.");
		$("#career").focus();
		return ;
	}
	if($("#use_hand").val() == ""){
		alert("사용손을 입력하세요.");
		$("#use_hand").focus();
		return ;
	}
	if($("#point_rank").val() == ""){
		alert("포인트랭킹을 입력하세요.");
		$("#point_rank").focus();
		return ;
	}
	*/
	oEditors.getById["staff_info"].exec("UPDATE_CONTENTS_FIELD", []);
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/board/adminStaffUpdt.do'/>");
	$("#frm").submit();
}

function goBoardDel(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/board/adminStaffDelete.do'/>");
	$("#frm").submit();
}

function fileDelete(no){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/board/StaffFileDel.do'/>");
	$("#atch_file_id2").val(no);
	$("#frm").submit();
}

//첨부파일 다운로드
function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}
</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>

<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="seq"  value="<c:out value='${staffView.seq }'/>" />	
	<input type="hidden" name="reg_id"  value="<c:out value='${memberinfo.mber_id }'/>" />
	<input type="hidden" name="atch_file_id" id="atch_file_id" value="<c:out value='${staffView.atch_file_id }'/>" />
	<input type="hidden" name="atch_file_id2" id="atch_file_id2" value="" />
	<input type="hidden" name="posblAtchFileNumber" value="1" />
	<input type="hidden" name="posblAtchFileNumber2" value="5" />
	<input type="hidden" name="FileCnt" value="${fn:length(imgFile) }">
	<input type="hidden" name="FileCnt2" value="${fn:length(addFile) }">
	<div id="container">
		<h3>STAFF관리</h3>
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
							<th>스탭명</th>
							<td class="al_l" colspan="3">
								<input type="text" id="staff_name" name="staff_name" value="<c:out value='${staffView.staff_name }'/>"/>
							</td>
						</tr>
						<tr>
							<th>협회구분</th>
							<td class="al_l">
								<select id="asso_type" name="asso_type">
									<option value="">::선택::</option>
									<option value="PBA" <c:if test="${staffView.asso_type eq 'PBA' }">selected</c:if>>PBA</option>
									<option value="KPBA" <c:if test="${staffView.asso_type eq 'KPBA' }">selected</c:if>>KPBA</option>
									<option value="Amateur" <c:if test="${staffView.asso_type eq 'Amateur' }">selected</c:if>>Amateur</option>
								</select>
							</td>
							<th>STAFF구분</th>
							<td class="al_l">
								<select id="staff_type" name="staff_type">
									<option value="">::선택::</option>
									<option value="Storm" <c:if test="${staffView.staff_type eq 'Storm' }">selected</c:if>>Storm</option>
									<option value="Roto Grip" <c:if test="${staffView.staff_type eq 'Roto Grip' }">selected</c:if>>Roto Grip</option>									
								</select>
							</td>
						</tr>
						<tr>
							<th>소속</th>
							<td class="al_l">
								<input type="text" id="team" name="team" value="<c:out value='${staffView.team }'/>"/>
							</td>
							<th>정렬순서</th>
							<td class="al_l">
								10
							</td>
						</tr>
						<tr>
							<th>프로기수</th>
							<td class="al_l">
								<input type="text" id="pro_no" name="pro_no" value="<c:out value='${staffView.pro_no }'/>"/>
							</td>
							<th>구력</th>
							<td class="al_l">
								<input type="text" id="career" name="career" value="<c:out value='${staffView.career }'/>"/>
							</td>
						</tr>
						<tr>
							<th>사용손</th>
							<td class="al_l">
								<input type="text" id="use_hand" name="use_hand" value="<c:out value='${staffView.use_hand }'/>"/>
							</td>
							<th>포인트랭킹</th>
							<td class="al_l">
								<input type="text" id="point_rank" name="point_rank" value="<c:out value='${staffView.point_rank }'/>"/>
							</td>
						</tr>
						<tr>
							<th>사용자페이지 URL</th>
							<td class="al_l" colspan="3">
								<span>https://bowlingkorea.com/board/staffDetail.do?seq=<c:out value='${staffView.seq }'/>&currRow=1</span>
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
							<th>경력</th>	
							<td class="cont al_l"  colspan="3">
								<textarea id="staff_info" name="staff_info" style="width:100%;height:320px;" title="내용 입력"><c:out value='${staffView.staff_info }'/></textarea>
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
					<a href="javascript:goBoardDel()" class="btn-ty1 black">삭제</a>
				</p>
			</div>
			
		</div>
		<!-- //contents -->
	</div>
</form:form>
		
<jsp:include page="/apage/inc/footer.do"></jsp:include>