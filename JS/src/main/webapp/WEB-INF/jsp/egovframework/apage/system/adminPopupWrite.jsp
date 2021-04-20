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
	    elPlaceHolder: "pop_content",
	    sSkinURI: "/resources/se2/SmartEditor2Skin.html",
	    htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			fOnBeforeUnload : function(){}
		},	
	    fCreator: "createSEditor2"
	});
	
	$('.datepicker').removeClass('hasDatepicker').datepicker({
		showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
		  buttonImage: "/resources/apage/images/board/ic_date.gif", // 버튼 이미지
		  buttonImageOnly: true  // 버튼에 있는 이미지만 표시한다.		
	});
});


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/system/adminPopupList.do'/>");
	$("#frm").submit();
}

function goBoardWrite(){
	if(($("#pop_subject").val())==""){
		alert("제목을 입력하세요.");
		$("#pop_subject").focus();
		return 0;
	}
	
	if($("#pop_width").val()==""){
		alert("사이즈를 입력해주세요.");
		$("#pop_width").focus();
		return;
	}
	
	if($("#pop_height").val()==""){
		alert("사이즈를 입력해주세요.");
		$("#pop_height").focus();
		return;
	}

	if($("#pop_st_dt").val()==""){
		alert("기간을 입력해주세요.");
		$("#pop_st_dt").focus();
		return;
	}
	
	if($("#pop_ed_dt").val()==""){
		alert("기간을 입력해주세요.");
		$("#pop_ed_dt").focus();
		return;
	}
	
	var auth_code_chk = $('input:radio[name="pop_state"]:checked').val();
	
	if(auth_code_chk == undefined || auth_code_chk == null || auth_code_chk == ""){
		alert("게시상태 체크해주세요.");
		return;
	}
	
	var state_code_chk = $('input:radio[name="pop_set"]:checked').val();

	if(state_code_chk == undefined || state_code_chk == null || state_code_chk == "" ){
		alert("쿠키설정 체크해주세요.");
		return;
	}
	
	oEditors.getById["pop_content"].exec("UPDATE_CONTENTS_FIELD", []);
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/apage/system/adminPopupReg.do'/>");
	$("#frm").submit();
}


</script>

<form:form commandName="vo" name="frm" id="frm" method="post" >
	<input type="hidden" name="pop_reg_nm"  value="<c:out value='${memberinfo.mber_name }'/>" />
	<div id="container">
		<h3>팝업 관리</h3>
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
								<input type="text" id="pop_subject" name="pop_subject" title="제목 입력" class="w40p" maxlength="150" >
							</td>
						</tr>
						<tr>
							<th>팝업창 url</th>
							<td class="al_l">
								<input type="text" id="pop_url" name="pop_url" title="url입력" class="w40p" maxlength="250" >
							</td>
						</tr>
						<tr>
							<th>팝업창 사이즈</th>
							<td class="al_l">
								<input type="text" id="pop_width" name="pop_width" title="가로 입력" class="w20p" maxlength="50" > px * <input type="text" id="pop_height" name="pop_height" title="세로 입력" class="w20p" maxlength="50" > px
							</td>
						</tr>
						<tr>
							<th>팝업창 위치</th>
							<td class="al_l">
								<input type="text" id="pop_position_x" name="pop_position_x" title="가로 입력" class="w20p" maxlength="50" > px * <input type="text" id="pop_position_y" name="pop_position_y" title="세로 입력" class="w20p" maxlength="50" > px
							</td>
						</tr>
						<tr>
							<th>게시기간</th>
							<td class="al_l">
								<input type="text" name="pop_st_dt" id="pop_st_dt" title="게시일 입력" class="w20p datepicker" maxlength="10" /> ~ <input type="text" name="pop_ed_dt" id="pop_ed_dt" title="게시종료일 입력" class="w20p datepicker" maxlength="10" />
							</td>
						</tr>
						<tr>
							<th>게시상태</th>
							<td class="al_l">
								<input type="radio" id="close" name="pop_state" value="Y" /><label for="close">게시</label>
								<input type="radio" id="open" name="pop_state" value="N"  /><label for="open">종료</label>
							</td>
						</tr>
						<tr>
							<th>쿠키설정여부</th>
							<td class="al_l">
								<input type="radio" id="close" name="pop_set" value="Y" /><label for="close">Y</label>
								<input type="radio" id="open" name="pop_set" value="N" /><label for="open">N</label>
							</td>
						</tr>
						<tr class="content">
							<td class="cont al_l" colspan="2">
								<textarea id="pop_content" name="pop_content" style="width:100%;height:320px;" title="내용 입력"></textarea>
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