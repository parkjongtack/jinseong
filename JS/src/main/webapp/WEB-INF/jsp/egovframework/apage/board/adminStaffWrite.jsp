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
	$("#frm").attr("action", "<c:url value='/apage/board/adminStaffList.do'/>");
	$("#frm").submit();
}

function goBoardWrite(){
	
	if($("#staff_name").val() == ""){
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
	$("#frm").attr("action", "<c:url value='/apage/board/adminStaffReg.do'/>");
	$("#frm").submit();
}


</script>

<form:form commandName="vo" name="frm" id="frm" method="post" enctype="multipart/form-data">	
	<input type="hidden" name="reg_id"  value="<c:out value='${memberinfo.mber_id }'/>" />
	<input type="hidden" name="posblAtchFileNumber" value="1" />
	<input type="hidden" name="posblAtchFileNumber2" value="5" />
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
								<input type="text" id="staff_name" name="staff_name" value=""/>
							</td>
						</tr>
						<tr>
							<th>협회구분</th>
							<td class="al_l">
								<select id="asso_type" name="asso_type">
									<option value="">::선택::</option>
									<option value="PBA">PBA</option>
									<option value="KPBA">KPBA</option>
									<option value="Amateur">Amateur</option>
								</select>
							</td>
							<th>STAFF구분</th>
							<td class="al_l">
								<select id="staff_type" name="staff_type">
									<option value="">::선택::</option>
									<option value="Storm">Storm</option>
									<option value="Roto Grip">Roto Grip</option>									
								</select>
							</td>
						</tr>
						<tr>
							<th>소속</th>
							<td class="al_l">
								<input type="text" id="team" name="team" value=""/>
							</td>
							<th>정렬순서</th>
							<td class="al_l">
								10
							</td>
						</tr>
						<tr>
							<th>프로기수</th>
							<td class="al_l">
								<input type="text" id="pro_no" name="pro_no" value=""/>
							</td>
							<th>구력</th>
							<td class="al_l">
								<input type="text" id="career" name="career" value=""/>
							</td>
						</tr>
						<tr>
							<th>사용손</th>
							<td class="al_l">
								<input type="text" id="use_hand" name="use_hand" value=""/>
							</td>
							<th>포인트랭킹</th>
							<td class="al_l">
								<input type="text" id="point_rank" name="point_rank" value=""/>
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
							<th>경력</th>	
							<td class="cont al_l"  colspan="3">
								<textarea id="staff_info" name="staff_info" style="width:100%;height:320px;" title="내용 입력"></textarea>
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