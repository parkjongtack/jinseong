<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
var clCodeChk	= false;
$(document).ready(function () {
	
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/apage/system/adminCodeList.do'/>";
	
	var msg = "${msg}";

	if(msg != ""){
		alert(msg);	
	}	
	
	
	$('#cl_code_nm').keyup(function(){
		var cl_code_nm =  $('#cl_code_nm').val();
		var sp = document.getElementById("idch");
		
		if(cl_code_nm == ""){
			sp.innerHTML = "";
		}
		
		// jQuery의 ajax이용
		$.ajax({
			url : "/apage/system/codeNameChk.do",
			data		: $('#aForm').serialize(),
			async		: true,
			type		: "post",
			dataType : "json",
			beforeSend: function (xhr) {
				xhr.setRequestHeader('AJAX', true);
			}				
			,success	: function(data){
				if (data.root.ResultCode == "1") {
					if(cl_code_nm == ""){
						sp.innerHTML = "";
					}else{
						sp.innerHTML = "<span style=\"color:blue;\">* 사용 가능한 분류명 입니다.</span>";
					}
					clCodeChk = true;
				} else {
					if(cl_code_nm == ""){
						sp.innerHTML = "";
					}
					else{
						sp.innerHTML = "<span style=\"color:red;\">* 중복된 분류명 입니다.</span>";
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

function searchChk(){ 
	$("#frm").attr("action", "/apage/system/adminCodeList.do");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(no){
	$("#frm1").attr("action", "/apage/system/adminCodeDetail.do");
	$("#code_id_seq").val(no);
	$("#frm1").submit();
}

function goBoardWrite(){
	location.href = "<c:url value='/apage/system/adminCodeWrite.do'/>";
}

function goCodePopup(){
	$("#pop-ebk").css('display','block');
}

function goCodePopupClose(){
	var sp = document.getElementById("idch");
	$("#pop-ebk").css('display','none');
	$("#cl_code_nm").val("");
	sp.innerHTML = "";
}

function goCodePopupReg(){
	var sp = document.getElementById("idch");
	var code_nm = $("#cl_code_nm").val();
	var pattern = /\s/g;
	
	
	if(code_nm == ""){
		alert("분류명을 입력해 주세요.");
	    $("#cl_code_nm").focus();
		return;
	}
	
	if( code_nm.match(pattern) )
	{ 
	    alert("분류명에 공백이 있습니다.");
	    $("#cl_code_nm").focus();
		return;
	} 
	
	
	if(clCodeChk){
		$.ajax({
			url			: '/apage/system/codeNameReg.do'
			,data		: $('#aForm').serialize()
			,async		: true
			,type		: "post"
			,dataType	: 'json'
			,beforeSend: function (xhr) {
				xhr.setRequestHeader('AJAX', true);
			}				
			,success	: function(jsonObj){
				
				if(jsonObj.root.ResultCode == '0'){
					alert('분류명 등록이 취소되었습니다.');
				}else if(jsonObj.root.ResultCode == '1'){
					alert('분류명이 등록되었습니다.');
					$("#pop-ebk").css('display','none');
					$("#cl_code_nm").val("");
					sp.innerHTML = "";
				}
				
			}
			,error		: function(xhr,status,error){
				alert("status : "+xhr.status+"\n"+"error : "+error);
			}
		});
	}
}

</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="code_id_seq" id="code_id_seq" />
	<input type="hidden" name="scType" id="scType"  value="${scType }"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="${Srch_input }"/>
	<input type="hidden" name="currRow"  value="${currPage }"/>
</form>
	<div id="container">
		<h3>공통코드관리</h3>
		<div class="contents">
        	<ul class="ct-tab">
				<li class="tab1"><a href="<c:url value='/apage/system/adminContent.do'/>">콘텐츠관리</a></li>
                <li class="tab1"><a href="<c:url value='/apage/system/adminPopupList.do'/>">팝업관리</a></li>
                <li class="tab1"><a href="<c:url value='/apage/system/adminBannerList.do'/>">배너관리</a></li>
                <li class="tab1"><a href="<c:url value='/apage/system/adminCodeList.do'/>"  class="on">공통코드관리</a></li>
			</ul>
			<!-- //ct-tab -->
			
			<div class="bbs-head">
				<form method="post" id="frm" name="frm">
					<input type="hidden" name="currRow" id="currRow"  value="${currPage }"/>
					<fieldset class="bbs-srch">
							<legend>게시글 검색</legend>
							<label for="scType" class="hide">검색카테고리 선택</label>
							<select name="scType" id="scType" title="검색 범위 선택">
								<option value="all">전체</option>
								<option value="subject" <c:if test="${scType eq 'subject' }"> selected </c:if>>분류명</option>
								<option value="contents" <c:if test="${scType eq 'contents' }"> selected </c:if>>코드ID명</option>
							</select>
						<label for="srch_input"><input type="text" id="srch_input" name="srch_input" title="검색어 입력" value="${Srch_input}" onKeyPress="if (event.keyCode==13){ searchChk(); return false;}" /></label>
						<input type="submit" onclick="searchChk();" value="Search" />
					</fieldset>
				</form>
			</div>
			<!-- //bbs-head -->
			<fieldset class="bbs-list">
				<legend>게시글 목록</legend>
				<table summary="공지사항 전체 목록">
					<colgroup><col width="10%" /><col width="15%" /><col width="*" /><col width="10%" /></colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">분류명</th>
							<th scope="col">코드 ID명</th>
							<th scope="col">사용여부</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
				            <c:when test="${fn:length(codeList) > 0}">
				                <c:forEach items="${codeList }" var="list" varStatus="status">
				                    <tr onclick="goBoardView('${list.code_id_seq}');" style='cursor:pointer;'>
				                        <th scope="row"><c:out value='${(totalNum+1)-list.ascnum }'/></th>
				                        <td><c:out value='${list.cl_code_nm }'/></td>
										<td class="subj"><c:out value='${list.code_id_nm }'/></td>
										<td><c:out value='${list.use_yn }'/></td>
				                    </tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
				                <tr>
				                    <td colspan="4">조회된 결과가 없습니다.</td>
				                </tr>
				            </c:otherwise>
				        </c:choose>
					</tbody>
				</table>
			</fieldset>
			<!-- //bbs-list -->
			<div class="pg-nav">
				<p class="btn"><a href="javascript:goBoardWrite()" class="btn-ty1 blue">등록</a></p>
				<p class="btnL"><a href="javascript:goCodePopup()" class="btn-ty1 blue">분류명 추가 +</a></p> 
				${pageNav }
			</div>
			<!-- //pg-nav -->
			<form method="post" id="aForm" name="aForm">
			<input type="hidden" name="reg_nm" id="reg_nm"  value="<c:out value='${memberinfo.mber_name }'/>"/>
			<div id="pop-ebk" style="display:none;">
		        <h5>분류명 추가</h5>
		        <div class="edite">
		            <div class="inner-write mb20">
		            	<h6>분류명추가</h6>
		                <table summary="">
		                	<colgroup><col width="15%" /><col width="*" /></colgroup>
		                    <tbody>
		                        <tr>
		                            <th>분류명</th>
		                            <td colspan="3">
		                            <input type="text" id="cl_code_nm" name="cl_code_nm"  title="분류명 입력" maxlength="70" class="w40p"
		                            onkeydown="this.value=this.value.replace(' ','')" 
                 					onkeyup="this.value=this.value.replace(' ','')" 
                 					onblur="this.value=this.value.replace(' ','')"/>
                 					<span id="idch"></span>
		                            </td>
		                        </tr>
		                    </tbody>
		                </table>
		            </div>
		            <!-- //bbs-write-->
		            <span class="fr">
		                 <a href="javascript:goCodePopupReg()" class="btn-ty2 blue">등록</a>
		            </span>
		        </div>
		        <!-- //edit-->
		        <p class="close"><a href="javascript:goCodePopupClose()">X</a></p>
		    </div>
		    </form>
		</div>
	</div>
	
<jsp:include page="/apage/inc/footer.do"></jsp:include>