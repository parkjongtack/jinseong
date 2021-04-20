<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>


<c:if test="${contestView.ct_seq != '10000026' && contestView.ct_seq != '10000027'}">
	<style type="text/css">
		.colorOpt{ display: none; }
	</style>
</c:if>


<script type="text/javascript">
var currPart = "1";

$(document).ready(function () {
	//getAppResultList(1,"0004");
	
	$(".btnAppPart").click(function(){
		
		$(".btnAppPart").each(function(){
			$(this).removeClass('on');
		});
		$(this).addClass('on');
		currPart = $(this).attr("data-part");
		$("#currPart").val(currPart);
		
		$(".chkAppAll").removeAttr("checked");
		
		getAppResultList($(this).attr("data-part"),$(".btnStatus.on").attr("data-status"));
	});
	
	$('.chkAppAll').change(function () {
	    var checked = (this.checked) ? true : false;
	    $('.chkApp').prop('checked',checked);
	});
	
	
	$(".btnStatus").click(function(){
		$(".btnStatus").removeClass("on");
		$(this).addClass("on");
		var stat = $(this).attr("data-status");
		getAppResultList($(".btnAppPart.on").attr("data-part"),$(this).attr("data-status"));
	})
	
	
	
	if($(".btnAppPart").length > 0){
		$(".btnAppPart").each(function(idx){ 
			if(idx == 0){
				var d_part = $(this).attr("data-part"); 
				getAppResultList(d_part,"0004");
				return false;
			}
		})
	}
	
	
});


$(document).on("click",".btnUpdt",function() {
	
	var telno;
	var gender;
	var status;
	var lane;
	var birth;

	var $tr = $(this).parent().parent();
	
	lane = $tr.find(".lane_num").html();
	
	if($tr.find('#telno').val() == ""){
		alert("연락처를 입력하세요");
		$tr.find('#telno').fucus();
		return;
	}else{
		telno = $tr.find('#telno').val();
	}
	
	if(!birthFocusout($tr.find('input[name=birth]').val())){
		alert("생년월일을 확인하세요.\nYYYY-MM-DD 형식으로 입력해주세요.");
		$tr.find('input[name=birth]').focus();
		return false;
	}else{
		birth = $tr.find('input[name=birth]').val();
	}
	
	gender = $tr.find('#gender').val();
	status = $tr.find('#status').val();
	var origin_status = $(this).attr("data-status");
	var app_seq = $(this).attr("data-seq");
	var part = $(this).attr("data-part");
	var ct_seq = $(this).attr("data-ctseq");

	
	
	
	var eng_last_name	=	$tr.find('input[name=eng_last_name]').val();
	var eng_first_name	=	$tr.find('input[name=eng_first_name]').val();
	
	var zipcode			=	$tr.find('input[name=zipcode]').val();
	var addr			=	$tr.find('input[name=addr]').val();
	var addr_detail		=	$tr.find('input[name=addr_detail]').val();
	
	var option1			=	$tr.find('select[name=option1]').val();
	var option2			=	$tr.find('select[name=option2]').val();
	
	
	
	if(confirm("신청정보를 수정하시겠습니까?") == true){
		
		$.ajax({
			type : "POST",
			url : "/apage/event/updateEventContestApp.do",
			data : {
				
				telno     		:	telno,
				gender    		:	gender,
				status    		:	status,
				app_seq   		:	app_seq,
				origin_status	:	origin_status,
				lane 			:	lane,
				ct_seq 			:	ct_seq,
				part			:	part,
				birth			:	birth,
				
				eng_last_name	:	eng_last_name,
				eng_first_name	:	eng_first_name,
				zipcode			:	zipcode,
				addr			:	addr,
				addr_detail		:	addr_detail,
				option1			:	option1,
				option2			:	option2
				
				
			},
			cache : false,
			dataType : 'json',
			success : function(msg){
				//getAppListJson(part);
				getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
			},
			error : function(data, status, err) {
				console.log(data + " / " + status + " / " + err);
				return;
			}
		});
		//getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
	}else{
		return;
	}
});

$(document).on("click",".btnDel",function() {
	if(confirm("신청정보를 삭제하시겠습니까?") == true){
		
		var app_seq = $(this).attr("data-seq");
		var part = $(this).attr("data-part");
		
		$.ajax({
			type : "POST",
			url : "/apage/event/deleteEventContestApp.do",
			data : {
					app_seq   :app_seq
			},
			cache : false,
			dataType : 'json',
			success : function(msg){
				getAppListJson(part);			
			},
			error : function(data, status, err) {
				alert(status);
				return;
			}
		});
	}else{
		return;
	}
});


$(document).on("click",".chkApp",function() {
	if($(this).is(":checked")==true){
		$(this).parent().parent().css("background-color","#f8f8f8");
	}else{
		$(this).parent().parent().css("background-color","");
	}
});


function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/event/eventContestSelectResultList.do'/>");
	$("#frm").submit();
}

function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}


function getAppListJson(part){
	
	var ct_seq = $("#ct_seq").val();
	 	
	 $.ajax({
			type		: "POST",
			url			: "/apage/contest/contestAppListJson.do",
			data		: {
							ct_seq : ct_seq, 
							part : part
						},
			cache		: false,
			dataType	: 'json',
			success		: function(data) {
				//console.log(data.root.dataset0);
				arrayData24s = data.root.dataset0;
				if(arrayData24s.length > 0){	
					$("#contestAppList").html("");
					$("#contestAppListTmpl").tmpl(data.root.dataset0).appendTo("#contestAppList");			
				}else{		
	 				var strhtml ="";
					strhtml += "<tr><td colspan='12'>해당조 신청 인원이 없습니다.</td></tr>";
					$("#contestAppList").html("");
					$("#contestAppList").html(strhtml); 

				}
			},beforeSend		:function(){
	        	//(이미지 보여주기 처리)
		        $('.wrap-loading').removeClass('display-none');
		    	},complete			:function(){
		           //(이미지 감추기 처리)
		            $('.wrap-loading').addClass('display-none');	     
		        },error		: function(data) {
					//alert('에러가 발생했습니다.');
					getAppListJson(currPart);
				},timeout:100000
		});
}

//사용자 페이지에 선정결과 노출하기 or 숨기기
function goUserPageSee(ct_seq) {
	
	var html = $("#userPageSee").html();
	
	if(html == "결과노출"){
		if(confirm("결과를 노출하시겠습니까?") == true){
			$.ajax({
				type		: "POST",
				url			: "/apage/event/eventContestAppResultExposeYn.do",
				data		: {
								ct_seq : ct_seq, 
								expose_yn : "Y"
							},
				cache		: false,
				dataType	: 'json',
				success		: function(data) {
					if(data.root.resultCode == "Y"){
						alert("결과가 노출되었습니다.");
						$("#userPageSee").html("결과숨김");		
					}else{
						alert("에러입니다.");
					}
				}
			});		
		}
	}else{
		if(confirm("결과를 숨기시겠습니까?") == true){
			$.ajax({
				type		: "POST",
				url			: "/apage/event/eventContestAppResultExposeYn.do",
				data		: {
								ct_seq : ct_seq, 
								expose_yn : "N"
							},
				cache		: false,
				dataType	: 'json',
				success		: function(data) {
					if(data.root.resultCode == "Y"){
						alert("결과가 노출되지 않습니다.");
						$("#userPageSee").html("결과노출");		
					}else{
						alert("에러입니다.");
					}			
				}
			});		
		}
	}
}

//체크데이터 삭제
function goAppDelete(){
	var app_seq = new Array();
	var checkVal = "";
	
	$(".chkApp").each(function(){
		if($(this).is(":checked")){
			checkVal += $(this).val()+",";
		}
	})
	if(checkVal == ""){
		alert("체크된 데이터가 없습니다.");
		return;
	}
	
	checkVal = checkVal.substring(0,checkVal.length - 1);
	app_seq = checkVal.split(',');
	
	if(confirm("선택한 신청데이터를 삭제하시겠습니까?")==true){
		
		for(var i=0;i<app_seq.length;i++){
			
			$.ajax({
				type : "POST",
				url : "/apage/event/deleteEventContestApp.do",
				data : {
						app_seq   :app_seq[i]
				},
				cache : false,
				dataType : 'json',
				success : function(msg){

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

function goAppModify(){
	
	var app_seq;
	var checkVal = 0;
	
	$(".chkApp").each(function(){
		if($(this).is(":checked")){
			checkVal++;
		}
	})
	if(checkVal == 0){
		alert("체크된 데이터가 없습니다.");
		return;
	}
	
	if(confirm("체크된 데이터를 수정하시겠습니까?") == true){
		var telno;
		var gender;
		var status;
		var lane;
		var birth;
		
		
		$("#contestAppList").find('tr').each(function(){
			if($(this).find('.chkApp').is(":checked")){
				app_seq = $(this).find('.chkApp').val();
				
				if($(this).find('#telno').val() == ""){
					alert("연락처를 입력하세요");
					return false;
				}else{
					telno = $(this).find('#telno').val();
				}
				
				if(!birthFocusout($(this).find('input[name=birth]').val())){
					alert("생년월일을 확인하세요.\nYYYY-MM-DD 형식으로 입력해주세요.");
					$(this).find('input[name=birth]').focus();
					return false;
				}else{
					birth = $(this).find('input[name=birth]').val();
				}

				gender = $(this).find('#gender').val();
				status = $(this).find('#status').val();
				var origin_status = $(this).find(".btnUpdt").attr("data-status");
				var app_seq = $(this).find(".btnUpdt").attr("data-seq");
				var part = $(this).find(".btnUpdt").attr("data-part");
				var ct_seq = $(this).find(".btnUpdt").attr("data-ctseq");
				
				var eng_last_name	=	$(this).find('input[name=eng_last_name]').val();
				var eng_first_name	=	$(this).find('input[name=eng_first_name]').val();
				
				var zipcode			=	$(this).find('input[name=zipcode]').val();
				var addr			=	$(this).find('input[name=addr]').val();
				var addr_detail		=	$(this).find('input[name=addr_detail]').val();
				
				var option1			=	$(this).find('select[name=option1]').val();
				var option2			=	$(this).find('select[name=option2]').val();
				
				$.ajax({
					type : "POST",
					url : "/apage/event/updateEventContestApp.do",
					data : {
						telno     		:	telno,
						gender    		:	gender,
						status    		:	status,
						app_seq   		:	app_seq,
						origin_status	:	origin_status,
						lane 			:	lane,
						ct_seq 			:	ct_seq,
						part			:	part,
						birth			:	birth,
						
						eng_last_name	:	eng_last_name,
						eng_first_name	:	eng_first_name,
						zipcode			:	zipcode,
						addr			:	addr,
						addr_detail		:	addr_detail,
						option1			:	option1,
						option2			:	option2
					},
					cache : false,
					dataType : 'json',
					success : function(msg){
						//getAppListJson(currPart);
						getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
					},
					error : function(data, status, err) {
						console.log(data + " / " + status + " / " + err);
						return;
					}
				});	
			}			
		})		
		//getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
	}else{
		return;
	}
}


function getAppResultList(part,status){
	console.log("part ===> " + part + " /  status ==> " + status);
	if(status == "SMS"){
		$.ajax({
			url		:	"<c:url value='/apage/event/evnetContestMsgResulttListJson.do'/>",
			data	:	{
				ct_seq	:	"${contestView.ct_seq}",
				part	:	part
			},
			type	:	"post",
			success	:	function(data){
				$("#contestAppList").empty();
				$("#totCnt").empty();
				if(data.root.resultCode == "Y"){
					//$("#totCnt").html(data.root.smsResultList.length+"명");
					//$("#smsLogTmpl").tmpl(data.root.smsResultList).appendTo("#contestAppList");			
					$("#totCnt").html(data.root.smsHistory.length+"건");
					$("#smsLogTmpl").tmpl(data.root.smsHistory).appendTo("#contestAppList");			
				}else{
					$("#totCnt").html("0명");
					$("#contestAppList").append("<tr><td colspan='9'>데이터가 존재하지 않습니다.</td></tr>");
				}
			},beforeSend		:function(){
				$(".msgLogListTr").css("display","table-row");
				$(".contestAppListTr").css("display","none");
				$(".refundListTr").css("display","none");
				$('.wrap-loading').removeClass('display-none'); //(이미지 보여주기 처리)
			},complete			:function(){
				$('.wrap-loading').addClass('display-none'); //(이미지 감추기 처리)
			},error		: function(data) {
				alert('에러가 발생했습니다.');
				$("#contestAppList").empty();
				$("#totCnt").empty();
			},timeout:100000
		})	
	}else{
		$.ajax({
			url		:	"<c:url value='/apage/event/eventContestAppResultListJson.do'/>",
			data	:	{
				ct_seq	:	"${contestView.ct_seq}",
				part	:	part,
				status	:	status
			},
			type	:	"post",
			success	:	function(data){
				$("#contestAppList").empty();
				$("#totCnt").empty();
				if(data.root.resultCode == "Y"){
					$("#totCnt").html(data.root.resultList.length+"명");
					$("#contestAppListTmpl").tmpl(data.root.resultList).appendTo("#contestAppList");			
				}else{
					$("#totCnt").html("0명");
					$("#contestAppList").append("<tr><td colspan='15'>데이터가 존재하지 않습니다.</td></tr>");
				}
			},beforeSend		:function(){
				$(".msgLogListTr").css("display","none");
				$(".refundListTr").css("display","none");
				$(".contestAppListTr").css("display","table-row");
				$('.wrap-loading').removeClass('display-none'); //(이미지 보여주기 처리)
			},complete			:function(){
				$('.wrap-loading').addClass('display-none'); //(이미지 감추기 처리)
			},error		: function(data) {
				alert('에러가 발생했습니다.');
				$("#contestAppList").empty();
				$("#totCnt").empty();
			},timeout:100000
		})	
	}
}
//신청자 엑셀출력
function getExcel(){
	$("#frm").attr("action","/apage/event/eventContestSelectResulExcel.do");
	$("#frm").submit();
}
	
	
function sendMsg(){
	if(!confirm("문자발송 하시겠습니까?")){
		return;
	}
	
	var smsFlag = $("#result_sms_send_yn").val();
	if(smsFlag == "Y"){
		alert("이미 전송하였습니다.\n전송 실패건이 있다면 재전송을 이용해주세요.");
		return;
	}
	
	$.ajax({
		url		:	"<c:url value='/apage/event/eventContestAppResultSendMsg.do'/>",
		data	:	{
			ct_seq	:	"${contestView.ct_seq}"
		},
		async	:	false,
		type	:	"post",
		success	:	function(data){
			if(data.root.resultCode == "Y"){
				alert("문자발송이 성공하였습니다.");
				$("#result_sms_send_yn").val("Y");
			}else if(data.root.resultCode == 'E'){
				alert("실패하였습니다.");
				$("#result_sms_send_yn").val("N");
			}else{
				alert("더 이상 발송할 건이 없습니다.");
			}
		},beforeSend		:function(){
			$('.wrap-loading').removeClass('display-none'); //(이미지 보여주기 처리)
		},complete			:function(){
			$('.wrap-loading').addClass('display-none'); //(이미지 감추기 처리)
		},error		: function(data) {
			alert('에러가 발생했습니다.');
		},timeout:100000
	})	
}


function goRequestResult(val){
	if(confirm("신청자 선정/대기 분류를 진행하시겠습니까?")){
		$.ajax({
			url		:	"<c:url value='/apage/event/eventContestAppClassification.do'/>",
			type	:	"post",
			data	:	{
				ct_seq		:	val
			},
			success	:	function(data){
				console.log(data);
				if(data.root.resultCode == "Y"){
					alert("성공하였습니다.");
					//getAppResultList(1,"0004");
					//location.reload();
					
					/*
					$("#part_firBtn").trigger("click");
					$("#stat_firBtn").trigger("click");
					*/
					
					
					if($(".btnAppPart").length > 0){
						var d_part = $('btnAppPart').first().attr("data-part");
						getAppResultList(d_part,"0004");
					}
					
				}else if(data.root.resultCode == "N"){
					alert("실패하였습니다.");
				}else if(data.root.resultCode == "A"){
					alert("잘못된 접근입니다.");
				}else if(data.root.resultCode == "E"){
					alert("오류가 발생하였습니다.\n잠시후 시도해주세요.");
				}else if(data.root.resultCode == "P"){
					alert("대회상태가 신청마감이 아닙니다.");
				}
			},
			error	:	function(err){
				alert("오류가 발생하였습니다.\n잠시후 시도해주세요.");
				console.log(err);
			}
		})
	}
	
}


$(document).on("click",".resendBtn",function(){
	var msgkey = $(this).attr("data-msgkey");
	var sentdate = $(this).attr("data-senddt");
	var rslt = $(this).attr("data-rslt");
	
	if(confirm("재전송 하시겠습니까?")){
		if(rslt != "0"){
			$.ajax({
				url		:	"<c:url value='/apage/contest/resendMsg.do'/>",
				type	:	"post",
				data	:	{
					msgkey		:	msgkey,
					sentdate	:	sentdate,
					rslt		:	rslt
				},
				async	:	false,
				success	:	function(data){
					if(data.root.resultCode == "Y"){
						alert("재전송 되었습니다.");
						$(".btnStatus").each(function(data){
							if($(this).hasClass("on")){
								$(this).trigger("click");
							}
						})
					}else if(data.root.resultCode == "N"){
						alert("실패하였습니다.");
					}else if(data.root.resultCode == "A"){
						alert("잘못된 접근입니다.");
					}else{
						alert("오류가 발생하였습니다.");
					}
				},
				error	:	function(err){
					console.log(err);				
					alert("오류가 발생하였습니다.");
				}
			
			})
		}
	}
})

function birthFocusout(val){
	var format = /^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
	if(!format.test(val)){
		return false;
	}else{
		return true;
	}
}
</script>


<!-- 
	<td>
		<select id="option2" name="option2">
			<option value="BLACK"{{if option2 == 'BLACK'}}selected{{/if}}>BLACK</option>
			<option value="WHITE"{{if option2 == 'WHITE'}}selected{{/if}}>WHITE</option>
			<option value="BLUE"{{if option2 == 'BLUE'}}selected{{/if}}>BLUE</option>
			<option value="PINK"{{if option2 == 'PINK'}}selected{{/if}}>PINK</option>
		</select>		
	</td>
 -->
<script id="contestAppListTmpl" type="text/x-jquery-tmpl">
<tr>
	<th scope="row"><input type="checkbox" name="app_seq" class="chkApp" value="\${app_seq }"/></th>
	<td scope="row">\${rownum }</td>	
	<td scope="row">\${reg_dt }</td>
	<td>\${join_name }<br/>(\${reg_id })</td>
	<td><input type="text" name="telno" id="telno" class="w90p" value="\${telno }" /></td>
	<td>
		<select id="gender" name="gender">
			<option value="M"{{if gender == 'M'}}selected{{/if}}>남</option>
			<option value="F"{{if gender == 'F'}}selected{{/if}}>여</option>										
		</select>					
	</td>
	<td><input type="text" name="birth" id="birth" class="w90p" value="\${birth }" class="app_birth" /></td>
	<td>
		<input type="text" name="eng_last_name" id="eng_last_name" class="w20p" value="\${eng_last_name }" class="app_eng_last_name" />
		<input type="text" name="eng_first_name" id="eng_first_name" class="w60p" value="\${eng_first_name }" class="app_eng_first_name" />
	</td>
	<td>
		<input type="text" name="zipcode" id="zipcode" class="w10p" value="\${zipcode }" class="app_zipcode" />
		<input type="text" name="addr" id="addr" class="w60p" value="\${addr }" class="app_addr" />
		<input type="text" name="addr_detail" id="addr_detail" class="w20p" value="\${addr_detail }" class="app_addr_detail" />
	</td>
	<td>
		<select id="option1" name="option1">
			<option value="S"{{if option1 == 'S'}}selected{{/if}}>S</option>
			<option value="M"{{if option1 == 'M'}}selected{{/if}}>M</option>
			<option value="L"{{if option1 == 'L'}}selected{{/if}}>L</option>		
			<option value="85"{{if option1 == '85'}}selected{{/if}}>85</option>
			<option value="90"{{if option1 == '90'}}selected{{/if}}>90</option>
			<option value="95"{{if option1 == '95'}}selected{{/if}}>95</option>
			<option value="100"{{if option1 == '100'}}selected{{/if}}>100</option>
			<option value="105"{{if option1 == '105'}}selected{{/if}}>105</option>
			<option value="110"{{if option1 == '110'}}selected{{/if}}>110</option>
			<option value="120"{{if option1 == '120'}}selected{{/if}}>120</option>
			<option value="130"{{if option1 == '130'}}selected{{/if}}>130</option>										
		</select>					
	</td>
	<td class="colorOpt">
		<select id="option2" name="option2">
			<option value="" {{if option2 == ''}}selected{{/if}}>해당없음</option>
			<option value="black"{{if option2 == 'black'}}selected{{/if}}>블랙</option>
			<option value="navy"{{if option2 == 'navy'}}selected{{/if}}>네이비</option>
			<option value="red"{{if option2 == 'red'}}selected{{/if}}>레드</option>
			<option value="purple"{{if option2 == 'purple'}}selected{{/if}}>퍼플</option>
			<option value="pink"{{if option2 == 'pink'}}selected{{/if}}>핑크</option>
			<option value="blue"{{if option2 == 'blue'}}selected{{/if}}>블루</option>
			<option value="gold"{{if option2 == 'gold'}}selected{{/if}}>골드</option>
		</select>					
	</td>
	<td>\${waiting_num }</td>
	<td>
		<select id="status" name="status" {{if status == '0003'}}style="color: red;"{{/if}} {{if status == '0004'}}style="color: blue;"{{/if}}>
			<option value="0003" {{if status == '0003'}}selected{{/if}}>신청취소</option>
			<option value="0004" {{if status == '0004'}}selected{{/if}}>선정</option>
			<option value="0005" {{if status == '0005'}}selected{{/if}}>대기</option>												
		</select>
	</td>
	<td>
		<a href="javascript:void(0);" data-seq="\${app_seq }" data-part="\${part }" data-status="\${status}" data-ctseq="\${ct_seq}"class="btnUpdt s-blue-btn">수정</a>
		<a href="javascript:void(0);" data-seq="\${app_seq }" data-part="\${part }" class="btnDel s-gry-btn">삭제</a>		
	</td>															
</tr>
</script>

<script id="smsLogTmpl" type="text/x-jquery-tmpl">
<tr data-pk="\${msgkey }">
	<td scope="row" style="width:5%;">\${etc7 }</td>
	<td style="width:10%;">\${etc6 }</td>
	<td style="width:10%;">\${etc8 }</td>
	<td style="width:10%;">\${phone }</td>
	<td style="width:20%;"><pre style="text-align : left;">{{html msg}}</pre></td>
	<td style="width:5%;">
		{{if etc9 == '0004'}}<span style="color:blue;">선정</span>{{/if}}
		{{if etc9 == '0005'}}<span style="color:green;">대기</span>{{/if}}
		{{if etc9 == '0003'}}<span style="color:red;">신청취소</span>{{/if}}
	</td>
	<td style="width:8%;">
		{{if rslt == '0'}}<span style="color:blue;">전송성공</span>{{/if}}
		{{if rslt != '0' && rslt != '99999' }}<span style="color:red;">전송실패</span>{{/if}}
		{{if rslt == '99999'}}<span style="color:green;">재전송</span>{{/if}}

	</td>
	<td style="width:10%;">\${reqdate }</td>
	<td style="width:8%;">
		{{if rslt != '0' && rslt != '99999'}}<a href="javascript:void(0);" data-msgkey="\${msgkey }" data-senddt="\${reqdate}" data-rslt="\${rslt}" class="s-blue-btn resendBtn">재전송</a>{{/if}}
	</td>
</tr>
</script>
<!-- 
<script id="smsLogTmpl" type="text/x-jquery-tmpl">
<tr>
	<td scope="row">\${app_sms_date }</td>
	<td>\${join_name }</td>
	<td>\${birth }</td>
	<td>\${telno }</td>
	<td>
		{{if app_sms_flag == 'Y'}}<span style="color:blue;">전송성공</span>{{/if}}
		{{if app_sms_flag != 'Y'}}<span style="color:red;">전송실패</span>{{/if}}
	</td>
	<td>
		{{if status == '0004'}}<span style="color:blue;">선정</span>{{/if}}
		{{if status == '0005'}}<span style="color:red;">대기</span>{{/if}}
	</td>
</tr>
</script>
 -->



<script id="refundTmpl" type="text/x-jquery-tmpl">
<tr>
	<td scope="row">\${app_seq }</td>
	<td>\${join_name }<br/>(\${reg_id })</td>
	<td>\${birth }</td>
	<td>\${telno }</td>
	<td>\${email }</td>
	<td>
		{{if status == '0003'}}<span style="color:red;">신청취소</span>{{/if}}
		{{if status == '0004'}}<span style="color:blue;">선정</span>{{/if}}
		{{if status == '0005'}}<span style="color:black;">대기</span>{{/if}}
	</td>
	<td>\${cancel_date }</td>
	<td>
		{{if pay_flag == 'Y'}}<span style="color:red;">입금완료</span>{{/if}}
		{{if pay_flag == 'N'}}<span style="color:black;">입금대기</span>{{/if}}
		{{if pay_flag == 'R'}}<span style="color:blue;;">환불완료</span>{{/if}}
	</td>
	<td>\${refund_bank }</td>
	<td>\${refund_accholder }</td>
	<td style="width:10%;">\${refund_account }</td>
	<td>\${updt_id }</td>
</tr>
</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>
<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${contestView.atch_file_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" name="arrayApp" id="arrayApp" value="" />
	<input type="hidden" name="currPart" id="currPart" value="1" />
	<input type="hidden" id="result_sms_send_yn"  value="${contestView.result_sms_send_yn }" />
	
	

<div id="container">
	<h3>이벤트 대회 선정결과</h3>
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
							<td class="al_l" colspan="3">
								${contestView.ct_sbj }
							</td>
						</tr>
						<tr>
							<th>진행상태</th>
							<td class="al_l">
								<c:if test="${contestView.ct_process eq 'R' }">대회준비</c:if>
								<c:if test="${contestView.ct_process eq 'S' }">대회신청</c:if>
								<c:if test="${contestView.ct_process eq 'E' }">신청마감</c:if>
								<c:if test="${contestView.ct_process eq 'F' }">대회종료</c:if>
							</td>
							<th>조별정원</th>
							<td class="al_l">
								${contestView.limit_part } 명
							</td>
						</tr>
						<tr>
							<th>대회일자</th>
							<td class="al_l">
								${contestView.ct_dt }								
							</td>
							<th>대회장소</th>
							<td class="al_l">
								${contestView.ct_place }								
							</td>
						</tr>
					<tr>
                       	<td colspan="6" class="cont">${contestView.ct_content }</td>
                    </tr>
                    <tr>
                       	<th>첨부파일</th>
                        <td colspan="5">
                           	<c:choose>
								<c:when test="${fn:length(contestFile) > 0}">
									<c:forEach items="${contestFile}" var="contestFile" varStatus="status">
										<a href="javascript:void(0);" onclick="Down('${contestFile.atch_file_id }');">${contestFile.orignl_file_nm }</a>
									</c:forEach>
								</c:when>
								<c:otherwise>첨부파일이 없습니다.</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
				</tbody>
			</table>
		</div>
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goRequestResult('${contestView.ct_seq }')" class="btn-ty1" style="background-color: #1e7145; color: #fff;">신청결과선정분류</a>
				<a href="javascript:goUserPageSee('${contestView.ct_seq }')" id="userPageSee" class="btn-ty1" style="background-color: olive; color: #fff;">결과노출</a>
				<a href="javascript:sendMsg()" class="btn-ty1 blue">문자발송</a>
				<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
			</p>
		</div>
		<!-- //bbs-view -->
		<ul class="ct-tab">
			<c:if test="${contestView.ct_type eq 'L' && fn:length(pList) > 0}">
				<c:forEach items="${pList }" var="pList" varStatus="status">
					<li><a href="javascript:void(0);" class="btnAppPart <c:if test="${status.index == 0}"> on</c:if>" data-part="${pList.ecp_seq }" <c:if test="${status.index == 0}"> id="part_firBtn"</c:if>>${pList.part_ord }조&nbsp;${pList.part_name }</a></li>
				</c:forEach>
			</c:if>
			<c:if test="${contestView.ct_type eq 'A'}">
				<li><a href="javascript:void(0);" class="btnAppPart on" data-part="1">전체</a></li>
			</c:if>
			<p class="bbs-total mb20"><a href="javascript:getExcel();" class="btn-ty2 excel fr">신청자 엑셀다운</a></p>
		</ul>	
		<!-- 
		<ul class="ct-tab">
			<li><a href="javascript:void(0);" class="btnAppPart on" data-part="1" id="part_firBtn">1조</a></li>
			<li><a href="javascript:void(0);" class="btnAppPart" data-part="2">2조</a></li>
			<li><a href="javascript:void(0);" class="btnAppPart" data-part="3">3조</a></li>
			<li><a href="javascript:void(0);" class="btnAppPart" data-part="4">4조</a></li>		
			<p class="bbs-total mb20"><a href="javascript:getExcel();" class="btn-ty2 excel fr">신청자 엑셀다운</a></p>							
		</ul>		
		 -->
		<ul class="ct-tab">
			<li><a href="javascript:void(0);" class="btnStatus on" data-status="0004" id="stat_firBtn">선정</a></li>
			<li><a href="javascript:void(0);" class="btnStatus" data-status="0005">대기</a></li>
			<li><a href="javascript:void(0);" class="btnStatus" data-status="0003">신청취소</a></li>
			<li><a href="javascript:void(0);" class="btnStatus" data-status="SMS">문자전송내역</a></li>
		</ul>		
		<div class="bbs-head">
           	<font color="#003CFF">※인원수 : </font><span id="totCnt"></span>
		</div>
		
		<fieldset class="bbs-list">
			<legend>신청자 목록</legend>
			<table summary="사업신청 결과제출 전체 목록">
				<colgroup>
					<col width="3%">
					<col width="3%">
					<col width="8%">
					<col width="5%">
					<col width="8%">
					<col width="4%">
					<col width="7%">
					<col width="10%">
					<col width="*">
					<col width="5%">
					<col width="5%" class="colorOpt">
					<col width="5%">
					<col width="4%">
					<col width="8%">
				</colgroup>
				<thead>
					<tr class="contestAppListTr">
						<th scope="col"><input type="checkbox" class="chkAppAll"/></th>
						<th scope="col">번호</th>
						<th scope="col">신청일자</th>
						<th scope="col">성명<br>(아이디)</th>
                        <th scope="col">연락처</th>
                        <th scope="col">성별</th>
                        <th scope="col">생년월일</th>
                        <th scope="col">영문이름</th>
                        <th scope="col">주소</th>
                        <th scope="col">티셔츠<br/>사이즈</th>
                        <th scope="col" class="colorOpt">선호하는<br/>색상</th>
                        <th scope="col">대기번호</th>
                        <th scope="col">상태</th>
                        <th scope="col">관리</th>
					</tr>
					<tr class="msgLogListTr" style="display: none;">
                        <th scope="col">성명</th>
                        <th scope="col">아이디</th>
                        <th scope="col">생년월일</th>
                        <th scope="col">수신번호</th>
                        <th scope="col">내용</th>
                        <th scope="col">신청상태</th>
                        <th scope="col">전송결과</th>
						<th scope="col">전송일자</th>
                        <th scope="col">재전송</th>
					</tr>
					<tr class="refundListTr" style="display: none;">
                        <th scope="col">신청번호</th>
                        <th scope="col">성명<br/>(아이디)</th>
                        <th scope="col">생년월일</th>
                        <th scope="col">전화번호</th>
                        <th scope="col">이메일</th>
                        <th scope="col">상태</th>
                        <th scope="col">취소일자</th>
                        <th scope="col">결제유무</th>
                        <th scope="col">환불은행</th>
                        <th scope="col">환불예금주</th>
                        <th scope="col">환불계좌</th>
                        <th scope="col">수정자ID</th>
					</tr>
				</thead>
				<tbody id="contestAppList">

				</tbody>
			</table>
		</fieldset>
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goAppPartMove()" class="btn-ty1 light">조변경</a>
				<a href="javascript:goAppDelete()" class="btn-ty1 black">선택삭제</a>
				<a href="javascript:goAppModify()" class="btn-ty1 blue">선택수정</a>
			</p>
		</div>
	</div>
</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>