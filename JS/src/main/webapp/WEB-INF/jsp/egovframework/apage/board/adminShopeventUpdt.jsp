<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<%
	//치환 변수 선언합니다.
    pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
    pageContext.setAttribute("br", "<br/>");  //br 태그
%> 

<script type="text/javascript">
var oEditors = [];
$(document).ready(function () {
	
	getCommentJson();
	
	/*
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
	*/
	
   var maxFileNum = document.frm.posblAtchFileNumber.value;
   if(maxFileNum==null || maxFileNum==""){
     maxFileNum = 3;
    }     
   var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList' ), maxFileNum );
   multi_selector.addElement( document.getElementById( 'egovComFileUploader' ) );
	
});


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventList.do'/>");
	$("#frm").submit();
}

function goBoardUpdt(){
	if($("#ntt_sj").val()==""){
		alert("제목을 입력하세요.");
		$("#ntt_sj").focus();
		return ;
	}
	
	
	//oEditors.getById["ntt_cn"].exec("UPDATE_CONTENTS_FIELD", []);
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventUpdate.do'/>");
	$("#frm").submit();
}

function goBoardDel(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventDelete.do'/>");
	$("#frm").submit();
}

function fileDelete(no){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/board/adminShopeventFileDel.do'/>");
	$("#atch_file_id2").val(no);
	$("#frm").submit();
}


//코멘트 불러오기
function getCommentJson(){
	 	
	 $.ajax({
			type		: "POST",
			url			: "/apage/board/commentListJson.do",
			data		: {
								bbs_id	: 'shopEvent',
								ntt_id	:  $("#ntt_id").val()
						 },
			cache		: false,
			dataType	: 'json',
			success		: function(data) {
				console.log(data.root.dataset0);
				console.log("aaaa-"+data.root.dataset0.content);
				arrayData24s = data.root.dataset0;
				if(arrayData24s.length > 0){	
					$("#commentList").html("");
					$("#commentListTmpl").tmpl(data.root.dataset0).appendTo("#commentList");				
	
				}else{		
	 				var strhtml ="";
					strhtml += "<tr><td colspan='2'>댓글이 입력되지 않았습니다.</td></tr>";
					$("#commentList").html("");
					$("#commentList").html(strhtml); 

				}
			}
		});
}


//관리자 댓글 등록
function goReplyReg(){
	
	if($("#content").val() == ""){
		alert("코멘트를 작성하세요.");
		$("#content").focus();
		return;
	}
	
	if(confirm("등록 하시겠습니까?") == true){

		$.ajax({
			type : "POST",
			url : '/apage/board/insertBoardComment.do',
			data : {
				bbs_id		: 'shopEvent',
				parent_seq	: $("#ntt_id").val(),
				content		: $("#content").val()
			},
			cache : false,
			dataType : 'json',
			success : function(msg){
				$("#content").val('');
				getCommentJson();				
			},
			error : function(data, status, err) {
				alert(status);
				return;
			}
		});
	}else{
		return;
	}
}

//관리자 댓글 수정
function goCommentModify(reg_id,content,seq) {
	
	if(confirm("수정하시겠습니까?") == true){
		
		var html = '';
		
		html += '<tr>';
		html += '<th>';
		html += reg_id;
		html += '</th>';
		html += '<td class="al_l">';
		html += '<textarea id="comment_content" name="content" style="width: 80%; height: 150px;">';
		html += content;
		html += '</textarea>';
		html += '<a href="javascript:goCommentUpdt(\''+seq+'\');" class="btn-ty3 black" style="margin-left: 4px;">수정</a>';
		html += '<a href="javascript:getCommentJson()" class="btn-ty3 light" style="margin-left: 4px;">취소</a>';
		html += '</td>';
		html += '</tr>';
		
		$("#commentList").html(html);
		
	}else{
		return;
	}
}

//관리자 댓글 수정 처리
function goCommentUpdt(seq) {
	
	if($("#comment_content").val() == ""){
		alert("코멘트를 작성하세요.");
		$("#comment_content").focus();
		return;
	}
	
	$.ajax({
		type : "POST",
		url : "/apage/board/updateBoardComment.do",
		data : {
			seq			: seq,
			content		: $("#comment_content").val()	
		},
		cache : false,
		dataType : 'json',
		success : function(msg){			
			getCommentJson();				
		},
		error : function(data, status, err) {
			alert(status);
			return;
		}
	});
}

//관리자 댓글 삭제
function goCommentDel(seq){
	
	if(confirm("삭제하시겠습니까?")== true){
		$.ajax({
			type : "POST",
			url : "/apage/board/deleteBoardComment.do",
			data : { seq	:	seq},
			cache : false,
			dataType : 'json',
			success : function(msg){			
				getCommentJson();				
			},
			error : function(data, status, err) {
				alert(status);
				return;
			}
		});
	}else{
		return;
	}
	
} 

//첨부파일 다운로드
function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}
</script>
<script id="commentListTmpl" type="text/x-jquery-tmpl">
<tr>
	<th>\${reg_id }</th>
	<td class="al_l">{{html content }}  [\${reg_dt }] 
		<a href="javascript:goCommentModify('\${reg_id }','\${content }','\${seq }');" class="btn-ty3 black">수정</a>
		<a href="javascript:goCommentDel('\${seq }');" class="btn-ty3 light">삭제</a>
	</td>
</tr>
</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>

<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="atch_file_id2" id="atch_file_id2"/>
	<input type="hidden" name="atch_file_id" id="atch_file_id" value="<c:out value='${boardView.atch_file_id }'/>" />
	<input type="hidden" name="ntt_id"  id="ntt_id" value="${boardView.ntt_id }" />	
	<input type="hidden" name="ntcr_id" value="<c:out value='${memberinfo.mber_id }'/>" />
	<input type="hidden" name="posblAtchFileNumber" value="3" />
	<div id="container">
		<h3>세미나 관리</h3>
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
							<td class="al_l" colspan="3">								
								<input type="text" name="ntt_sj" id="ntt_sj" value="${boardView.ntt_sj }" />
							</td>							
						</tr>
						<tr>
							<th>작성자</th>
							<td class="al_l" colspan="3">
								<input type="text" name="ntcr_nm" id="ntcr_nm" value="${boardView.ntcr_nm }" />							
							</td>
						</tr>
						<tr>
							<th>연락처</th>
							<td class="al_l">
								<input type="text" name="as_tel" id="as_tel" value="${boardView.as_tel }" />
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td class="al_l">
								<input type="text" name="as_email" id="as_email" value="${boardView.as_email }" />								
							</td>
						</tr>
						<tr>
							<th>소속 볼링장</th>
							<td class="al_l">
								<input type="text" name="temp1" id="temp1" value="${boardView.temp1 }" />								
							</td>
						</tr>
						<tr>
							<th>보유 정비기계</th>
							<td class="al_l">
								<input type="text" name="temp2" id="temp2" value="${boardView.temp2 }" />								
							</td>
						</tr>
						<tr>
							<th>참석여부</th>
							<td class="al_l">
								<input type="radio" id="temp3_1" name="temp3" value="1"  <c:if test="${boardView.temp3 eq '1'}">checked="checked"</c:if>><label for="temp3_1">1부 참석</label>
								<input type="radio" id="temp3_2" name="temp3" value="2" <c:if test="${boardView.temp3 eq '2'}">checked="checked"</c:if>><label for="temp3_2">2부 참석</label>
								<input type="radio" id="temp3_3" name="temp3" value="3" <c:if test="${boardView.temp3 eq '3'}">checked="checked"</c:if>><label for="temp3_3">1부,2부 참석</label>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<!-- //bbs-write -->
			<div class="pg-nav">
				<p class="btn">
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
					<a href="javascript:goBoardUpdt()" class="btn-ty1 black">저장</a>
					<a href="javascript:goBoardDel()" class="btn-ty1 black">삭제</a>
				</p>
			</div>
			
			<div class="bbs-write">
				<table>
					<colgroup>
						<col width="15%">
						<col width="*">
						<col width="10%">						
					</colgroup>
					<tbody>
						<tr>
							<th>관리자 답변등록</th>
							<td class="al_l">
								<textarea id="content" name="content" width="80%" style="height:150px;"></textarea>
							</td>
							<th class="al_c">
								<a href="javascript:goReplyReg();" class="btn-ty1 black">등록</a>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
			
			<fieldset class="bbs-write">
				<legend>공지사항 목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup>
						<col width="10%"/>
						<col width="*%"/>
					</colgroup>
					<tbody id="commentList">
						
					</tbody>
				</table>
			</fieldset>
						
		</div>
		<!-- //contents -->
	</div>
</form:form>
		
<jsp:include page="/apage/inc/footer.do"></jsp:include>