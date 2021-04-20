<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>

<c:import url="/client/header.do" />

<script type="text/javascript">
var oEditors = [];
$(document).ready(function () {
	
	nhn.husky.EZCreator.createInIFrame({
	    oAppRef: oEditors,
	    elPlaceHolder: "ntt_cn",
	    sSkinURI: "/resources/se2/SmartEditor2SkinForClient.html",
	    htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : false,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			fOnBeforeUnload : function(){}
		},	
	    fCreator: "createSEditor2"
	});
	
	//첨부파일
	var maxFileNum = document.frm.posblAtchFileNumber.value;
	if (maxFileNum == null || maxFileNum == "") {
		maxFileNum = 3;
	}
	var multi_selector = new MultiSelector(document.getElementById('egovComFileList'), maxFileNum);
	multi_selector.addElement(document.getElementById('egovComFileUploader'));
	/*
	if($(".alreadyFileList").length == 0 && $("#egovComFileList").html() == ""){
		$("#atch_file_id").val("");
	}
	*/
	//캡차
	if ("${captchaResult}" != '') {
		if ("${captchaResult}" == 'fail') {
			alert("자동입력방지 코드를 정확히 입력해주세요.");
		} else if ("${captchaResult}" == 'error') {
			alert("자동입력방지 코드 에러가 발생하였습니다.\n잠시후 시도해주세요.");
		}
	}

	//이메일 선택
	$("#emailSelect").change(function() {
		var strVal = $(this).val();
		if (strVal == "") {
			$("#as_email2").val('');
			$("#as_email2").removeAttr("readonly");
			$("#as_email2").focus();
		} else {
			$("#as_email2").val('');
			$("#as_email2").val(strVal);
			$("#as_email2").attr("readonly", "readonly");
		}
	});
});

function goList() {
	$("#frm").attr("action", "<c:url value='/board/asDetail.do'/>");
	$("#frm").submit();
}

function fileDelete(no){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/board/fileDel.do'/>");
	$("#atch_file_id2").val(no);
	$("#frm").submit();
}

function goUpdt() {

	if ($("#ntt_sj").val() == "") {
		alert("제목을 입력하세요.");
		$("#ntt_sj").focus();
		return;
	}

	if ($("#as_type").val() == "") {
		alert("분류를 선택하세요.");
		$("#as_type").focus();
		return;
	}

	if ($("#as_tel1").val() == "") {
		alert("연락처를 입력하세요.");
		$("#as_tel1").focus();
		return;
	}
	if ($("#as_tel2").val() == "") {
		alert("연락처를 입력하세요.");
		$("#as_tel2").focus();
		return;
	}
	if ($("#as_tel3").val() == "") {
		alert("연락처를 입력하세요.");
		$("#as_tel3").focus();
		return;
	}

	if ($("#as_email1").val() == "") {
		alert("이메일을 입력하세요.");
		$("#email1").focus();
		return;
	}

	if ($("#email2").val() == "") {
		alert("이메일을 입력하세요.");
		$("#email2").focus();
		return;
	}

	if ($("#ntt_cn").val() == "") {
		alert("내용을 입력하세요.");
		$("#ntt_cn").focus();
		return;
	}
	/*
	if($(".alreadyFileList").length == 0 && $("#egovComFileList").html() == ""){
		alert("aaaaaaaaaaaaaaaaaa");
		$("#atch_file_id").val("");
	}
	*/
	$("#as_tel").val($("#as_tel1").val() + '-' + $("#as_tel2").val() + '-' + $("#as_tel3").val());
	$("#as_email").val($("#as_email1").val() + '@' + $("#as_email2").val());

	oEditors.getById["ntt_cn"].exec("UPDATE_CONTENTS_FIELD", []);
	
	var dataString = $("#frm").serialize();
	$("#frm").attr("action", "<c:url value='/board/asUpdt.do'/>");
	$("#frm").submit();
}

function captchaReset() {

	if ($("#mp3_audio").length) {
		document.getElementById("mp3_audio").pause();
	} else if ($("#mp3_embed").length) {
		document.embeds[0].stop();
	}

	$.ajax({
		url : "<c:url value='/resetCaptcha.do'/>",
		type : "post",
		success : function(data) {
			if (data.root.ResultCode == "Y") {
				$("#frm").find("#captcha_key").val(data.root.cKey);
				$("#frm").find("#captchaURL").attr("src",
						data.root.captchaURL);

				$("#audioPlayBtn").attr("href", "");
				if ($("#mp3_audio").length)
					$("#mp3_audio").remove();
				if ($("#mp3_embed").length)
					$("#mp3_embed").remove();
				$("#frm").find("#captcha_key_audio").val(data.root.aKey);
				$("#frm").find("#audioPlayBtn").attr("href",
						data.root.captchaAudioURL);
				if ($("#frm").find("#captcha_type").val() == "audio") {
					$("#audioPlayBtn").trigger("click");
				}
			} else {
				alert("실패하였습니다.");
			}
		}
	})

}

function audioPlay() {
	$("#showImgBtn").css("display", "block");
	$("#audioPlayBtn").css("display", "none");
	$("#captchaAudio").css("display", "block");
	$("#captchaImg").css("display", "none");
	$("#audioGuideText").css("display", "block");
	$("#imgGuideText").css("display", "none");
	$("#frm").find("#captcha_type").val("audio");
}

function showImg() {
	$("#audioPlayBtn").css("display", "block");
	$("#showImgBtn").css("display", "none");
	$("#captchaImg").css("display", "block");
	$("#captchaAudio").css("display", "none");
	$("#imgGuideText").css("display", "block");
	$("#audioGuideText").css("display", "none");
	
	if ($("#mp3_audio").length) {
		document.getElementById("mp3_audio").pause();
	} else if ($("#mp3_embed").length) {
		document.embeds[0].stop();
	}

	$("#frm").find("#captcha_type").val("img");
}

$(function() {
	$("#audioPlayBtn").bind("click",function() {
		$("#showImgBtn").css("display", "block");
		$("#audioPlayBtn").css("display", "none");
		$("#captchaAudio").css("display", "block");
		$("#captchaImg").css("display", "none");
		$("#audioGuideText").css("display", "block");
		$("#imgGuideText").css("display", "none");
		//document.getElementById("captchaAudioPlayer").play(); 
		$("#frm").find("#captcha_type").val("audio");

		var mp3_url = this.href;
		var html5use = false;
		var html5audio = document.createElement("audio");
		//if (html5audio.canPlayType && html5audio.canPlayType("audio/mpeg")) {
		if (html5audio.canPlayType
				&& html5audio.canPlayType("audio/x-wav")) {
			var wav = new Audio(mp3_url);
			wav.id = "mp3_audio";
			wav.autoplay = true;
			wav.controls = false;
			wav.autobuffer = false;
			wav.loop = false;
			if ($("#mp3_audio").length)
				$("#mp3_audio").remove();
			$("#audioPlayBtn").after(wav);
			html5use = true;
		}
		if (!html5use) {
			var object = '<embed id="mp3_embed" src="' + mp3_url + '" autoplay="true" hidden="true" volume="100" type="audio/x-wav" style="display:inline;" />';
			if ($("#mp3_embed").length)
				$("#mp3_embed").remove();
			$("#audioPlayBtn").after(object);
		}
		return false;
	});
});
</script>

<div id="container" class="subpage">
	<div id="contents">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />
		<div class="sub_content">
			<div class="inner">
				<div class="sub_head">
					<h3 class="c_tit">A/S 게시판</h3>
				</div>
				<div class="board_area sub_fade">
					<div class="icoBx ic1">
						<p>(<span class="ft_or">*</span>), 정확히 입력하셔야 등록이 가능합니다.</p>
					</div>
					<form:form commandName="vo" id="frm" name="frm" method="post" enctype="multipart/form-data">
						<input type="hidden" name="ntt_id"  id="ntt_id" value="${boardView.ntt_id }" />	
						
						<input type="hidden" name="captcha_type" id="captcha_type" value="img">
						<input type="hidden" name="captcha_key" id="captcha_key" value="${captchaKEY }">
						<input type="hidden" name="captcha_key_audio" id="captcha_key_audio" value="${captchaAudioKEY }">
						
						<input type="hidden" name="posblAtchFileNumber" value="3" />
						
						<input type="hidden" name="as_tel" id="as_tel" value="" /> <!-- 데이터 조합 결과용 -->
						<input type="hidden" name="as_email" id="as_email" value="" /> <!-- 데이터 조합 결과용 -->
						<input type="hidden" name="as_status" id="as_status" value="R" />
						<input type="hidden" name="ntup_id" value="${sessionScope.mberInfo.mber_id}" />
						<input type="hidden" name="ntup_nm" value="${sessionScope.mberInfo.mber_name}" />
						<input type="hidden" name="atch_file_id2" id="atch_file_id2"/>
						<input type="hidden" name="atch_file_id" id="atch_file_id" value="<c:out value='${boardView.atch_file_id }'/>" />
						
						<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
						<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
						<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
						
						<div class="bbs_write">
							<div class="tbl_w">
								<table summary="게시판 글쓰기 표로 제목, 구분, 내용,첨부파일 등이 있습니다.">
									<caption>게시판 작성</caption>
									<colgroup>
										<col width="25%" class="">
										<col width="75%" class="">
									</colgroup>
									<tbody>
										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="subj">제목</label></th>
											<td><input id="ntt_sj" name="ntt_sj" type="text" value="${boardView.ntt_sj }"></td>
										</tr>

										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="type">분류</label></th>
											<td>
												<select name="as_type" id="as_type" class="w200">
					                               	<option value="">::분류선택::</option>
					                               	<option value="볼링볼" <c:if test="${boardView.as_type eq '볼링볼' }">selected='selected'</c:if>>볼링볼</option>
					                               	<option value="볼링가방" <c:if test="${boardView.as_type eq '볼링가방' }">selected='selected'</c:if>>볼링가방</option>
					                               	<option value="볼링의류" <c:if test="${boardView.as_type eq '볼링의류' }">selected='selected'</c:if>>볼링의류</option>
					                               	<option value="볼링아대" <c:if test="${boardView.as_type eq '볼링아대' }">selected='selected'</c:if>>볼링아대</option>
					                               	<option value="볼링슈즈" <c:if test="${boardView.as_type eq '볼링슈즈' }">selected='selected'</c:if>>볼링슈즈</option>
					                               	<option value="볼링악세사리" <c:if test="${boardView.as_type eq '볼링악세사리' }">selected='selected'</c:if>>볼링악세사리</option>
					                               	<option value="프로샵 용품" <c:if test="${boardView.as_type eq '프로샵 용품' }">selected='selected'</c:if>>프로샵 용품</option>
					                            </select>
											</td>
										</tr>
										<tr>
											<th scope="col"><label for="writer">작성자</label></th>
											<td><c:out value="${sessionScope.mberInfo.mber_name}" /></td>
										</tr>
										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="phone">연락처</label></th>
											<td>
												<c:set var= "mber_tel" value="${fn:split(mberInfo.mber_tel, '-')}"></c:set>
												<form:input path="as_tel1" class="w50" maxlength="3" value="${mber_tel[0]}" pattern="\d*" placeholder=""/><span class="insert"> - </span> 
												<form:input path="as_tel2" class="w50" maxlength="4" value="${mber_tel[1]}" pattern="\d*" placeholder=""/><span class="insert"> - </span> 
												<form:input path="as_tel3" class="w50" maxlength="4" value="${mber_tel[2]}" pattern="\d*" placeholder=""/>													
											</td>
										</tr>
										<!-- 
                                        <tr>
                                            <th scope="col"><span class="ft_or">*</span><label for="pw">임시 비밀번호</label></th>
                                            <td>
                                            	<form:password path="password" class="w200"/>
                                            </td>
                                        </tr>
                                         -->
										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="email">이메일</label></th>
											<td class="w_email">
												<form:input path="as_email1" class="w200" maxlength="30" value="${fn:substring(sessionScope.mberInfo.mber_email,0,fn:indexOf(sessionScope.mberInfo.mber_email,'@')) }" />@ 
												<form:input path="as_email2" class="w200" value="${fn:substring(sessionScope.mberInfo.mber_email,fn:indexOf(sessionScope.mberInfo.mber_email,'@')+1,fn:length(sessionScope.mberInfo.mber_email)) }" /> 
												<select id="emailSelect">
													<option value="">::직접입력::</option>
													<option value="naver.com">naver.com</option>
													<option value="nate.com">nate.com</option>
													<option value="gmail.com">gmail.com</option>
													<option value="korea.com">korea.com</option>
													<option value="paran.com">paran.com</option>
													<option value="hanmail.net">hanmail.net</option>
													<option value="hotmail.com">hotmail.com</option>
												</select>
											</td>
										</tr>
										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="coText">내용</label></th>
											<td>
												<textarea id="ntt_cn" name="ntt_cn" style="width:100%; height:auto;">
													<c:choose>
														<c:when test="${not empty ntt_cn_new}">
															${ntt_cn_new}
														</c:when>
														<c:otherwise>
															${boardView.ntt_cn }
														</c:otherwise>
													</c:choose>
												</textarea>
											</td>
										</tr>
										<tr>
											<th scope="col"><label for="w_file">첨부파일</label></th>
											<td> <!-- class="fileBtn" -->
												<input name="file_1" id="egovComFileUploader" type="file" />
						                		<c:choose>
													<c:when test="${fn:length(asFile) > 0}">
														<c:forEach items="${asFile}" var="asFile" varStatus="status">
														<div class="alreadyFileList">
															<a href="javascript:void(0);" onclick="Down('${asFile.atch_file_id }');">${asFile.orignl_file_nm }</a>
															<input type="button" title="삭제" value="Delete" class="file_cla" style='cursor:pointer;' onclick="fileDelete('${asFile.atch_file_id }');" /><br>
														</div>
														</c:forEach>
													</c:when>
													<c:otherwise></c:otherwise>
												</c:choose>
						                		<div id="egovComFileList"></div>
											</td>
										</tr>
										<tr>
											<th scope="row"><span class="ft_or">*</span> <label for="captcha_answer">자동방지여부</label></th>
											<td>
												<div class="auto_num">
													<!-- <img src="/resources/client/images/board/auto_num_sam.gif" alt="sample"> -->
													<p id="captchaImg" class="captchaImg">
														<img alt="자동입력방지 이미지" src="${captchaURL }" id="captchaURL">
													</p>
													<p id="captchaAudio" style="text-align: center; display: none;">
														<span class="audio_txt" id="audio_txt" style="display: block;">음성으로 안내되고 있습니다.<br />(시작음 3회 반복 후 재생)</span>
													</p>
												</div>
												<div class="auto_input">
													<p id="imgGuideText">왼쪽의 문자를 순서대로 입력하세요.</p>
													<p id="audioGuideText" style="display: none;">스피커로 들리는 내용을 숫자로 입력해 주세요.</p>
													<input type="text" id="captcha_answer" name="captcha_answer" class="size1" />
													<div class="auto_btn">
														<a href="javascript:captchaReset()" class="btn-reset">새로고침</a> 
														<a href="${captchaAudioURL }" class="btn-voice" id="audioPlayBtn">음성으로 듣기</a> 
														<a href="javascript:showImg()" class="btn-voice" id="showImgBtn" style="display: none;">이미지로 보기</a>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- 
                            <div class="agree">
                                <p><input type="checkbox" id="agree"><label for="agree">개인정보 수집 및 정보제공동의 </label> <a href="javscript:viewLayer();" id="pop_txt">전문보기</a></p>
                            </div>
                             -->
						</div>
					</form:form>
					<!--BOARD_WRITE //E -->

					<div class="btn_r2">
						<a href="javascript:goUpdt();" class="">수정</a> 
						<a href="javascript:goList();" class="gray">취소</a>
					</div>
				</div>
				<!--BOARD_AREA //E-->
			</div>
		</div>
	</div>
	<!-- #CONTENTS //E -->
</div>
<!-- #CONTAINER //E -->

<jsp:include page="/client/footer.do"></jsp:include>