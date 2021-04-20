<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
var clCodeChk	= true;
$(document).ready(function () {
	
	$('#code_id_seq2').keyup(function(){
		var cl_code_nm =  $('#code_id_seq2').val();
		var sp = document.getElementById("idch");
		
		if(cl_code_nm == ""){
			sp.innerHTML = "";
		}
		
		// jQuery의 ajax이용
		$.ajax({
			url : "/apage/system/codeSeqChk.do",
			data		: {code_id_seq : cl_code_nm},
			async		: true,
			type		: "post",
			dataType : "json",
			beforeSend: function (xhr) {
				xhr.setRequestHeader('AJAX', true);
			}				
			,success	: function(data){
				if (data.root.ResultCode == "0") {
					if(cl_code_nm == ""){
						sp.innerHTML = "";
					}else{
						sp.innerHTML = "<span style=\"color:blue;\">* 사용 가능한 코드 입니다.</span>";
					}
					clCodeChk = true;
				} else {
					if(cl_code_nm == ""){
						sp.innerHTML = "";
					}
					else{
						sp.innerHTML = "<span style=\"color:red;\">* 중복된 코드 입니다.</span>";
					}
					clCodeChk = false;
				}
			},
			error : function(data, status, err) {
				alert("에러입니다." + data +"<><><>"+ status+"<><><>"+ err);
				return;
			}
		})
	});
});

function goCodeChange(){
	$("#code_id_seq2").css('display','');
	$("#codeBtn").css('display','none');
}


function goCodeList(){
	$("#frm").attr("action", "/apage/system/adminCodeList.do");
	$("#frm").submit();
}

function goCodeUpdate(){
	var code_id_seq = $("#code_id_seq2").val();
	var pattern = /\s/g;
	
	if($("#cl_code").val()==""){
		alert("분류명을 선택하세요.");
		$("#cl_code").focus();
		return;
	}
	
	if($("#code_id_nm").val()==""){
		alert("코드명을 입력하세요.");
		$("#code_id_nm").focus();
		return;
	}
	
	if($("#code_id_seq2").val()==""){
		alert("코드를 입력하세요.");
		$("#code_id_seq2").focus();
		return;
	}
	
	if( code_id_seq.match(pattern) )
	{ 
	    alert("코드에 공백이 있습니다.");
	    $("#code_id_seq2").focus();
		return;
	} 
	
	if(!clCodeChk){
		alert("코드가 중복됩니다.");
	    $("#code_id_seq2").focus();
		return;
	}
	
	var dataString =  $("#frm").serialize();
	$("#frm").attr("action", "/apage/system/adminCodeUpdate.do");
	$("#frm").submit();
}
</script>
<form name="frm" id="frm" method="post" >
	<input type="hidden" name="code_id_seq" id="code_id_seq" value="<c:out value='${codeView.code_id_seq }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="${Srch_input }"/>
	<input type="hidden" name="currRow" value="${currPage }"/>
	<input type="hidden" name="reg_nm"  value="<c:out value='${memberinfo.mber_name }'/>" />
<div id="container">
	<h3>공통코드관리</h3>
	<div class="contents">	
		<div class="bbs-write">
			<table>
				<colgroup>
					<col width="10%" />
					<col width="40%">
					<col width="10%" />
					<col width="40%">
				</colgroup>
				<tbody>
					<tr>
						<th>분류명</th>
						<td class="al_l" colspan="3">
							<select name="cl_code" id="cl_code" title="분류명 선택">
								<option value="">:: 분류명을 선택하세요. ::</option>
								<c:forEach items="${CLCodeList }" var="list" varStatus="status">
									<option value="${list.cl_code }" <c:if test="${list.cl_code eq codeView.cl_code }"> selected </c:if>>${list.cl_code_nm }</option>
				                </c:forEach>
							</select>
							<span style="color:red;">* 선택할 분류명이 없을경우 목록 좌측하단 분류명 추가를 눌러 분류명을 추가해주세요.</span>
						</td>
					</tr>
					<tr>
						<th>코드명</th>
						<td class="al_l"><input type="text" id="code_id_nm" name="code_id_nm" class="w40p" title="코드명 입력" maxlength="70" value="<c:out value='${codeView.code_id_nm }'/>"/></td>
						<th>코드</th>
						<td class="al_l">
							<a href="javascript:goCodeChange()" class="s-blue-btn" id="codeBtn">코드변경</a>
							<input type="text" id="code_id_seq2" name="code_id_seq2" class="w20p" title="코드 입력" maxlength="10" value="<c:out value='${codeView.code_id_seq }'/>"
							onkeydown="this.value=this.value.replace(' ','')" 
                 			onkeyup="this.value=this.value.replace(' ','')" 
                 			onblur="this.value=this.value.replace(' ','')" style="display:none;"/>
                 			<span id="idch"></span>
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td class="al_l" colspan="3"><c:out value='${memberinfo.mber_name }'/></td>
					</tr>
					<tr>
						<th>사용여부</th>
						<td class="al_l" colspan="3">
							<input type="radio" id="open" name="use_yn" value="Y" <c:if test="${codeView.use_yn eq 'Y' }">  checked="checked" </c:if>  /><label for="open">사용함</label>
							<input type="radio" id="close" name="use_yn" value="N" <c:if test="${codeView.use_yn eq 'N' }">  checked="checked" </c:if> /><label for="close">사용안함</label>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<!-- //bbs-write -->
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goCodeUpdate()" class="btn-ty1 black">수정</a>
				<a href="javascript:goCodeList()" class="btn-ty1 light">목록</a>
			</p>
		</div>
	</div>
</div>
</form>