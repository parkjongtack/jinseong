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
	$(document).ready(function() {
				
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

				var maxFileNum = document.frm.posblAtchFileNumber.value;
				if (maxFileNum == null || maxFileNum == "") {
					maxFileNum = 3;
				}
				var multi_selector = new MultiSelector(document
						.getElementById('egovComFileList'), maxFileNum);
				multi_selector.addElement(document
						.getElementById('egovComFileUploader'));

				if ("${captchaResult}" != '') {
					if ("${captchaResult}" == 'fail') {
						alert("자동입력방지 코드를 정확히 입력해주세요.");
					} else if ("${captchaResult}" == 'error') {
						alert("자동입력방지 코드 에러가 발생하였습니다.\n잠시후 시도해주세요.");
					}
				}
				
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

	function viewLayer() {
		$("#infoLayer").show();
	}
	function closeLayer() {
		$("#infoLayer").hide();
	}

	function goList() {
		$("#frm").attr("action", "<c:url value='/board/asList.do'/>");
		$("#frm").submit();
	}

	function goReg() {

		//if($('input:checkbox[id="agree"]').is(":checked") != true){
		//	alert("개인정보 수집 및 동의를 체크해주세요.");
		//	$("#agree").focus();
		//	return;
		//}

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

		//if($("#ntcr_nm").val()==""){
		//	alert("작성자명을 입력하세요.");
		//	$("#ntcr_nm").focus();
		//	return ;
		//}

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
		
		$("#as_tel").val(
				$("#as_tel1").val() + '-' + $("#as_tel2").val() + '-'
						+ $("#as_tel3").val());
		$("#as_email").val($("#as_email1").val() + '@' + $("#as_email2").val());
		
		oEditors.getById["ntt_cn"].exec("UPDATE_CONTENTS_FIELD", []);

		var dataString = $("#frm").serialize();
		$("#frm").attr("action", "<c:url value='/board/asReg.do'/>");
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
		$("#audioPlayBtn")
				.bind(
						"click",
						function() {
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
						<input type="hidden" name="captcha_type" id="captcha_type" value="img">
						<input type="hidden" name="captcha_key" id="captcha_key" value="${captchaKEY }">
						<input type="hidden" name="captcha_key_audio" id="captcha_key_audio" value="${captchaAudioKEY }">
						<input type="hidden" name="posblAtchFileNumber" value="3" />
						<input type="hidden" name="use_at" id="use_at" value="Y" />
						<input type="hidden" name="as_tel" id="as_tel" value="" />
						<input type="hidden" name="as_email" id="as_email" value="" />
						<input type="hidden" name="as_status" id="as_status" value="R" />
						<input type="hidden" name="ntcr_id" value="${sessionScope.mberInfo.mber_id}" />
						<input type="hidden" name="ntcr_nm" value="${sessionScope.mberInfo.mber_name}" />
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
											<td><form:input path="ntt_sj" /></td>
										</tr>

										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="type">분류</label></th>
											<td><form:select path="as_type" class="w200">
													<option value="">::분류선택::</option>
													<form:option value="볼링볼" />
													<form:option value="볼링가방" />
													<form:option value="볼링의류" />
													<form:option value="볼링아대" />
													<form:option value="볼링슈즈" />
													<form:option value="볼링악세사리" />
													<form:option value="프로샵 용품" />
												</form:select></td>
										</tr>
										<tr>
											<th scope="col"><label for="writer">작성자</label></th>
											<td><c:out value="${sessionScope.mberInfo.mber_name}" /></td>
										</tr>
										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="phone">연락처</label></th>
											<td>
												<c:set var="mber_tel" value="${fn:split(mberInfo.mber_tel, '-')}"></c:set>
												<form:input path="as_tel1" class="w50" maxlength="3" value="${mber_tel[0] }" pattern="\d*" placeholder=""/><span class="insert"> - </span> 
												<form:input path="as_tel2" class="w50" maxlength="4" value="${mber_tel[1] }" pattern="\d*" placeholder=""/><span class="insert"> - </span> 
												<form:input path="as_tel3" class="w50" maxlength="4" value="${mber_tel[2] }" pattern="\d*" placeholder=""/>
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
											</select></td>
										</tr>
										<tr>
											<th scope="col"><span class="ft_or">* </span><label for="coText">내용</label></th>
											<td>
												<textarea id="ntt_cn" name="ntt_cn" style="width:100%; height:auto;">
													<c:choose>
														<c:when test="${not empty ntt_cn}">
															${ntt_cn}
														</c:when>
														<c:otherwise>
															<table style="border-collapse:collapse;border-spacing:0; border: 2px solid black;">
																<colgroup>
																	<col style="width: 10%">
																	<col style="width: 10%">
																	<col style="width: 20%">
																	<col style="width: 10%">
																	<col style="width: 10%">
																	<col style="width: 20%">
																</colgroup>
																<tbody>
															    	<tr style="height:35px;">
																		<td style="border: 1px solid black; font-weight:bold; text-align:center; background-color:#F3F3F3;">주소</td>
																		<td colspan="5" style="border: 1px solid black; padding-left: 1%;"></td>
																	</tr>
																	<tr style="height:35px;">
																		<td style="border: 1px solid black; font-weight:bold; text-align:center; background-color:#F3F3F3;">구매처</td>
																		<td colspan="2" style="border: 1px solid black; padding-left: 1%;"></td>
																		<td style="border: 1px solid black; font-weight:bold; text-align:center; background-color:#F3F3F3;">구매일자</td>
																		<td colspan="2" style="border: 1px solid black; padding-left: 1%;"></td>
																	</tr>
																	<tr style="height:35px;">
																		<td rowspan="3" style="border: 1px solid black; font-weight:bold; text-align:center; background-color:#F3F3F3;">신발</td>
																		<td style="border: 1px solid black; padding-left: 1%;">상품명</td>
																		<td style="border: 1px solid black; padding-left: 1%;"></td>
																		<td rowspan="3" style="border: 1px solid black; font-weight:bold; text-align:center; background-color:#F3F3F3;">가방</td>
																		<td style="border: 1px solid black; padding-left: 1%;">상품명</td>
																		<td style="border: 1px solid black; padding-left: 1%;"></td>
																	</tr>
																	<tr style="height:35px;">
																		<td style="border: 1px solid black; padding-left: 1%;">색상</td>
																		<td style="border: 1px solid black; padding-left: 1%;"></td>
																		<td style="border: 1px solid black; padding-left: 1%;">색상</td>
																		<td style="border: 1px solid black; padding-left: 1%;"></td>
																	</tr>
																	<tr style="height:35px;">
																		<td style="border: 1px solid black; padding-left: 1%;">사이즈</td>
																		<td style="border: 1px solid black; padding-left: 1%;"></td>
																		<td colspan="2" style="border: 1px solid black; padding-left: 1%;"></td>
																	</tr>
																	<tr style="height:35px;">
																		<td rowspan="3" style="border: 1px solid black; font-weight:bold; text-align:center; background-color:#F3F3F3;">볼링공</td>
																		<td style="border: 1px solid black; padding-left: 1%;">상품명</td>
																		<td colspan="4" style="border: 1px solid black; padding-left: 1%;"></td>
																	</tr>
																	<tr style="height:35px;">
																		<td style="border: 1px solid black; padding-left: 1%;">파운드</td>
																		<td colspan="4" style="border: 1px solid black; padding-left: 1%;"></td>
																	</tr>
																	<tr style="height:35px;">
																		<td style="border: 1px solid black; padding-left: 1%;">시리얼넘버</td>
																		<td colspan="4" style="border: 1px solid black; padding-left: 1%;"></td>
																	</tr>
																	<tr style="height:20px;">
																		<td colspan="6" style="border: 1px solid black; font-weight:bold; text-align:center;">원활한 A/S처리를 위해 상품의 규격과 A/S 내용 등을 상세하게 입력하여 주시기 바랍니다.</td>
																	</tr>
																	<tr style="height:60px;">
																		<td style="border: 1px solid black; font-weight:bold; text-align:center; background-color:#F3F3F3;">A/S 내용</td>
																		<td colspan="5" style="border: 1px solid black; vertical-align: top; padding-left: 1%;"></td>
																	</tr>
																</tbody>
															</table>
														</c:otherwise>
													</c:choose>
												</textarea>	
											</td>
										</tr>
										<tr>
											<th scope="col"><label for="w_file">첨부파일</label></th>
											<td class="fileBtn"><input name="file_1" id="egovComFileUploader" type="file" />
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
														<a href="${captchaAudioURL }" class="btn-voice" id="audioPlayBtn">음성으로 듣기</a> <a href="javascript:showImg()" class="btn-voice" id="showImgBtn" style="display: none;">이미지로 보기</a>
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
						<a href="javascript:goReg();" class="">등록</a> <a href="javascript:goList();" class="gray">취소</a>
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