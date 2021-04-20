<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
	var currPart = "1";

	$(document).ready(function() {
		getAppListJson('1');

		$(".btnAppPart").click(function() {

			$(".btnAppPart").each(function() {
				$(this).removeClass('on');
			});
			$(this).addClass('on');
			currPart = $(this).attr("data-part");
			$("#currPart").val(currPart);

			$(".chkAppAll").removeAttr("checked");

			getAppListJson($(this).attr("data-part"));
		});

		$('.chkAppAll').change(function() {
			var checked = (this.checked) ? true : false;
			$('.chkApp').prop('checked', checked);
		});

	});

	$(document).on("click", ".btnUpdt", function() {

		var telno;
		var email;
		var handicap;
		var gender;
		var disable_yn;
		var status;

		var $tr = $(this).parent().parent();
/*
		if ($tr.find('#telno').val() == "") {
			alert("연락처를 입력하세요");
			$tr.find('#telno').fucus();
			return;
		} else {
			telno = $tr.find('#telno').val();
		}

		if ($tr.find('#email').val() == "") {
			alert("이메일를 입력하세요");
			$tr.find('#email').fucus();
			return;
		} else {
			email = $tr.find('#email').val();
		}
*/
		if ($tr.find('#handicap').val() == "") {
			alert("핸디캡점수를 입력하세요");
			$tr.find('#handicap').fucus();
			return;
		} else {
			handicap = $tr.find('#handicap').val();
		}

		gender = $tr.find('#gender').val();
		disable_yn = $tr.find('#disable_yn').val();
		status = $tr.find('#status').val();
		var origin_status = $(this).attr("data-status");

		var app_seq = $(this).attr("data-seq");
		var part = $(this).attr("data-part");

		if (confirm("신청정보를 수정하시겠습니까?") == true) {

			$.ajax({
				type : "POST",
				url : "/apage/contest/updateContestApp.do",
				data : {

					telno : telno,
					email : email,
					handicap : handicap,
					gender : gender,
					disable_yn : disable_yn,
					status : status,
					origin_status : origin_status,
					app_seq : app_seq
				},
				cache : false,
				dataType : 'json',
				success : function(msg) {
					getAppListJson(part);
				},
				error : function(data, status, err) {
					alert(status);
					return;
				}
			});
		} else {
			return;
		}
	});

	$(document).on("click", ".btnDel", function() {

		if (confirm("신청정보를 삭제하시겠습니까?") == true) {

			var app_seq = $(this).attr("data-seq");
			var part = $(this).attr("data-part");

			$.ajax({
				type : "POST",
				url : "/apage/contest/deleteContestApp.do",
				data : {
					app_seq : app_seq
				},
				cache : false,
				dataType : 'json',
				success : function(msg) {
					getAppListJson(part);
				},
				error : function(data, status, err) {
					alert(status);
					return;
				}
			});
		} else {
			return;
		}
	});

	$(document).on("click", ".chkApp", function() {
		if ($(this).is(":checked") == true) {
			$(this).parent().parent().css("background-color", "#f8f8f8");
		} else {
			$(this).parent().parent().css("background-color", "");
		}
	});

	function goBoardList() {
		$("#frm").attr("action",
				"<c:url value='/apage/contest/adminContestMngList.do'/>");
		$("#frm").submit();
	}

	function goModify() {
		$("#frm").attr("action",
				"<c:url value='/apage/contest/adminContestMngModify.do'/>");
		$("#frm").submit();
	}

	function goDelete() {
		if (!confirm('삭제하시겠습니까?'))
			return;
		$("#frm").attr("action",
				"<c:url value='/apage/contest/adminContestMngDelete.do'/>");
		$("#frm").submit();
	}

	function Down(no) {
		$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
		$("#atch_file_id").val(no);
		$("#frm1").submit();
	}

	function getAppListJson(part) {

		var ct_seq = $("#ct_seq").val();

		$
				.ajax({
					type : "POST",
					url : "/apage/contest/contestAppListJson.do",
					data : {
						ct_seq : ct_seq,
						part : part
					},
					cache : false,
					dataType : 'json',
					success : function(data) {
						//console.log(data.root.dataset0);
						arrayData24s = data.root.dataset0;
						if (arrayData24s.length > 0) {
							$("#contestAppList").html("");
							$("#contestAppListTmpl").tmpl(data.root.dataset0)
									.appendTo("#contestAppList");
						} else {
							var strhtml = "";
							strhtml += "<tr><td colspan='12'>해당조 신청 인원이 없습니다.</td></tr>";
							$("#contestAppList").html("");
							$("#contestAppList").html(strhtml);

						}
					},
					beforeSend : function() {
						//(이미지 보여주기 처리)
						$('.wrap-loading').removeClass('display-none');
					},
					complete : function() {
						//(이미지 감추기 처리)
						$('.wrap-loading').addClass('display-none');
					},
					error : function(data) {
						//alert('에러가 발생했습니다.');
						getAppListJson(currPart);
					},
					timeout : 100000
				});
	}

	//체크데이터 삭제
	function goAppDelete() {
		var app_seq = new Array();
		var checkVal = "";

		$(".chkApp").each(function() {
			if ($(this).is(":checked")) {
				checkVal += $(this).val() + ",";
			}
		})
		if (checkVal == "") {
			alert("체크된 데이터가 없습니다.");
			return;
		}

		checkVal = checkVal.substring(0, checkVal.length - 1);
		app_seq = checkVal.split(',');

		if (confirm("선택한 신청데이터를 삭제하시겠습니까?") == true) {

			for (var i = 0; i < app_seq.length; i++) {

				$.ajax({
					type : "POST",
					url : "/apage/contest/deleteContestApp.do",
					data : {
						app_seq : app_seq[i]
					},
					cache : false,
					dataType : 'json',
					success : function(msg) {

						getAppListJson(currPart);

					},
					error : function(data, status, err) {
						alert(status);
						return;
					}
				});
			}
		}

	}

	function goAppModify() {

		var app_seq;
		var checkVal = 0;

		$(".chkApp").each(function() {
			if ($(this).is(":checked")) {
				checkVal++;
			}
		})
		if (checkVal == 0) {
			alert("체크된 데이터가 없습니다.");
			return;
		}

		if (confirm("체크된 데이터를 수정하시겠습니까?") == true) {

			var telno;
			var email;
			var handicap;
			var gender;
			var disable_yn;
			var status;

			$("#contestAppList").find('tr').each(function() {

				if ($(this).find('.chkApp').is(":checked")) {

					app_seq = $(this).find('.chkApp').val();

					if ($(this).find('#telno').val() == "") {
						alert("연락처를 입력하세요");
						return false;
					} else {
						telno = $(this).find('#telno').val();
					}

					if ($(this).find('#email').val() == "") {
						alert("이메일를 입력하세요");
						return false;
					} else {
						email = $(this).find('#email').val();
					}

					if ($(this).find('#handicap').val() == "") {
						alert("핸디캡점수를 입력하세요");
						return false;
					} else {
						handicap = $(this).find('#handicap').val();
					}

					gender = $(this).find('#gender').val();
					disable_yn = $(this).find('#disable_yn').val();
					status = $(this).find('#status').val();

					var origin_status = $(this).find(".btnUpdt").attr("data-status");
					 
					$.ajax({
						type : "POST",
						url : "/apage/contest/updateContestApp.do",
						data : {

							telno : telno,
							email : email,
							handicap : handicap,
							gender : gender,
							disable_yn : disable_yn,
							status : status,
							origin_status : origin_status,
							app_seq : app_seq
						},
						cache : false,
						dataType : 'json',
						success : function(msg) {
							getAppListJson(currPart);
						},
						error : function(data, status, err) {
							alert(status);
							return;
						}
					});
				}
			})
		} else {
			return;
		}
	}

	//조 이동
	function goAppPartMove() {
		var cnt = 0;
		var app_seq = "";
		$(".chkApp").each(function() {
			if ($(this).is(":checked")) {
				cnt++;
				app_seq += $(this).val() + ",";
			}
		})

		if (cnt > 0) {
			var pop_title = "movePart";
			window.open("/apage/contest/contestMngPartPop.do", pop_title,
					'width=500,height=150');

			var frm = document.frm;
			frm.arrayApp.value = app_seq.substring(0, app_seq.length - 1);
			frm.currPart = currPart;
			frm.target = pop_title;
			frm.action = "/apage/contest/contestMngPartPop.do";
			frm.submit();
		} else {
			alert("체크된 데이터가 없습니다.");
			return;
		}
	}

	//신청자 엑셀출력
	function getExcel() {

		$("#frm").attr("action", "/apage/contest/contestAppListExcel.do");
		$("#frm").submit();
	}
</script>

<script id="contestAppListTmpl" type="text/x-jquery-tmpl">
<tr>
	<th scope="row"><input type="checkbox" class="chkApp" value="\${app_seq }"/></th>
	<td scope="row">\${reg_dt }</td>
	<td>\${join_name }(\${reg_id })</td>
	<td>\${deposit_name }</td>
	<td><input type="text" name="telno" id="telno" class="w90p" value="\${telno }" /></td>
	<td>
		<select id="gender" name="gender">
			<option value="M"{{if gender == 'M'}}selected{{/if}}>남</option>
			<option value="F"{{if gender == 'F'}}selected{{/if}}>여</option>										
		</select>					
	</td>
	<td><input type="text" name="email" id="email" class="w90p" value="\${email }" /></td>
	<td>
		<select id="disable_yn" name="disable_yn">
			<option value="N"{{if disable_yn == 'N'}}selected{{/if}}>해당없음</option>
			<option value="Y"{{if disable_yn == 'Y'}}selected{{/if}}>장애3급이상</option>										
		</select>		
	</td>
	<td><input type="text" name="handicap" id="handicap" class="w60p" value="\${handicap }" />점</td>
	<td>
		<select id="status" name="status" {{if status == '0002'}}style="color: blue;"{{/if}} {{if status == '0003'}}style="color: red;"{{/if}} >
			<option value="0001" {{if status == '0001'}}selected{{/if}}>입금대기</option>
			<option value="0002" {{if status == '0002'}}selected{{/if}}>입금완료</option>
			<option value="0003" {{if status == '0003'}}selected{{/if}}>신청취소</option>
			<option value="0004" {{if status == '0004'}}selected{{/if}}>선정</option>
			<option value="0005" {{if status == '0005'}}selected{{/if}}>대기</option>												
		</select>
	</td>
	<td>
		<a href="javascript:void(0);" data-seq="\${app_seq }" data-part="\${part }" data-status="\${status}" class="btnUpdt s-blue-btn">수정</a>
		<a href="javascript:void(0);" data-seq="\${app_seq }" data-part="\${part }" class="btnDel s-gry-btn">삭제</a>		
	</td>															
</tr>
</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id" />
</form>
<form:form commandName="vo" name="frm" id="frm">
	<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${contestView.atch_file_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input" value="<c:out value='${Srch_input }'/>" />
	<input type="hidden" name="currRow" value="<c:out value='${currPage }'/>" />
	<input type="hidden" name="arrayApp" id="arrayApp" value="" />
	<input type="hidden" name="currPart" id="currPart" value="1" />

	<div id="container">
		<h3>대회신청자관리</h3>
		<div class="contents">
			<!-- //ct-tab -->
			<div class="bbs-view">
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
							<td class="al_l">${contestView.ct_sbj }</td>
							<th>조별정원</th>
							<td class="al_l">${contestView.limit_part } 명</td>
						</tr>
						<tr>
							<th>상단공지여부</th>
							<td class="al_l">
								<c:if test="${contestView.check_yn eq 'Y' }">사용</c:if> 
								<c:if test="${contestView.check_yn eq 'N' }">사용안함</c:if>
							</td>
							<th>진행상태</th>
							<td class="al_l">
								<c:if test="${contestView.ct_process eq 'R' }">대회준비</c:if> 
								<c:if test="${contestView.ct_process eq 'S' }">대회신청</c:if> 
								<c:if test="${contestView.ct_process eq 'E' }">신청마감</c:if>
								<c:if test="${contestView.ct_process eq 'F' }">대회종료</c:if>
							</td>
						</tr>
						<tr>
							<th>대회일자</th>
							<td class="al_l">${contestView.ct_dt }</td>
							<th>대회장소</th>
							<td class="al_l">${contestView.ct_place }</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- //bbs-view -->
			<div class="pg-nav"> </div>
			<ul class="ct-tab">
				<li><a href="javascript:void(0);" class="btnAppPart on" data-part="1">1조</a></li>
				<li><a href="javascript:void(0);" class="btnAppPart" data-part="2">2조</a></li>
				<li><a href="javascript:void(0);" class="btnAppPart" data-part="3">3조</a></li>
				<li><a href="javascript:void(0);" class="btnAppPart" data-part="4">4조</a></li>
				<p class="bbs-total mb20"><a href="javascript:getExcel();" class="btn-ty1 excel fr">신청자 엑셀다운</a></p>
			</ul>
			<div class="pg-nav">
			  <p class="btnL">
			  	<a href="javascript:goAppModify()" class="btn-ty1 blue">선택수정</a>
			  	<a href="javascript:goAppDelete()" class="btn-ty1 black">선택삭제</a> 
			  </p>
				<p class="btn">
					<c:if test="${contestView.ct_process eq 'E' }"><a href="javascript:goAppPartMove()" class="btn-ty1 red">추첨</a></c:if>
					<a href="javascript:goAppPartMove()" class="btn-ty1 select">조변경</a>
					<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
				</p>
			</div>			
			<fieldset class="bbs-list">
				<legend>게시글 목록</legend>
				<table summary="사업신청 결과제출 전체 목록">
					<colgroup>
						<col width="3%">
						<col width="8%">
						<col width="8%">
						<col width="6%">
						<col width="15%">
						<col width="5%">
						<col width="15%">
						<col width="10%">
						<col width="8%">
						<col width="8%">
						<col width="*%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" class="chkAppAll" /></th>
							<th scope="col">신청일자</th>
							<th scope="col">성명 (아이디)
							</th>
							<th scope="col">임금자명</th>
							<th scope="col">연락처</th>
							<th scope="col">성별</th>
							<th scope="col">이메일</th>
							<th scope="col">장애여부</th>
							<th scope="col">핸디</th>
							<th scope="col">상태</th>
							<th scope="col">관리</th>
						</tr>
					</thead>
					<tbody id="contestAppList">

					</tbody>
				</table>
			</fieldset>
			
			<div class="pg-nav">
			  <p class="btnL">
			  	<a href="javascript:goAppModify()" class="btn-ty1 blue">선택수정</a>
			  	<a href="javascript:goAppDelete()" class="btn-ty1 black">선택삭제</a> 
			  </p>
				<p class="btn">
					<c:if test="${contestView.ct_process eq 'E' }"><a href="javascript:void();" class="btn-ty1 red">추첨</a></c:if>				
					<a href="javascript:goAppPartMove();" class="btn-ty1 select">조변경</a>
					<a href="javascript:goBoardList();" class="btn-ty1 light">목록</a>
				</p>
			</div>
		</div>
	</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>