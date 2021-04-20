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

$(document).ready(function () {
//	getAppListJson('1');
	$('.chkAppAll').change(function () {
	    var checked = (this.checked) ? true : false;
	    $('.chkApp').prop('checked',checked);
	});
	/* 
	$(".btnAppPart").click(function(){
		
		$(".btnAppPart").each(function(){
			$(this).removeClass('on');
		});
		$(this).addClass('on');
		currPart = $(this).attr("data-part");
		$("#currPart").val(currPart);
		
		$(".chkAppAll").removeAttr("checked");
		
		getAppListJson($(this).attr("data-part"));		
	});
	if($(".btnAppPart").length > 0){
		$(".btnAppPart").each(function(idx){ 
			if(idx == 0){
				var d_part = $(this).attr("data-part"); 
				getAppListJson(d_part);
				return false;
			}
		})
	}
	*/
	
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
	var email;
	var handicap;
	var gender;
	var disable_yn;
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
	
	
	if($tr.find('#handicap').val() == ""){
		alert("핸디캡점수를 입력하세요");
		$tr.find('#handicap').fucus();
		return;
	}else{
		handicap = $tr.find('#handicap').val();
	}
	
	if(!birthFocusout($tr.find('input[name=birth]').val())){
		alert("생년월일을 확인하세요.\nYYYY-MM-DD 형식으로 입력해주세요.");
		$tr.find('input[name=birth]').focus();
		return false;
	}else{
		birth = $tr.find('input[name=birth]').val();
	}
	
	gender = $tr.find('#gender').val();
	disable_yn = $tr.find('#disable_yn').val();
	status = $tr.find('#status').val();
	var origin_status = $(this).attr("data-status");
	var app_seq = $(this).attr("data-seq");
	var part = $(this).attr("data-part");
	var ct_seq = $(this).attr("data-ctseq");
	var pay_flag =  $tr.find('#pay_flag').val();

	var option1	 =	$tr.find('select[name=option1]').val();
	
	if(confirm("신청정보를 수정하시겠습니까?") == true){
		
		$.ajax({
			type : "POST",
			url : "/apage/event/updateEventContestApp.do",
			data : {
					
				telno     :telno,
				//email     :email,
				handicap   :handicap,
				gender    :gender,
				disable_yn:disable_yn,
				status    :status,
				app_seq   :app_seq,
				origin_status : origin_status,
				lane : lane,
				part : part,
				ct_seq : ct_seq,
				pay_flag : pay_flag,
				birth	:	birth,
				option1	:	option1
				
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
		//getAppListJson(part);
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
				//getAppListJson(part);	
				getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
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
	$("#frm").attr("action", "<c:url value='/apage/event/kokContestMngList.do'/>");
	$("#frm").submit();
}

function goModify(){
	$("#frm").attr("action", "<c:url value='/apage/event/kokContestMngModify.do'/>");
	$("#frm").submit();
}

function goDelete(){
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/apage/event/kokContestMngDelete.do'/>");
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
			url			: "/apage/event/eventContestAppListJson.do",
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
					strhtml += "<tr><td colspan='16'>해당조 신청 인원이 없습니다.</td></tr>";
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
					//getAppListJson(part);
		        	getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
				},timeout:100000
		});
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

					//getAppListJson(currPart);
					getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));

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
		var email;
		var handicap;
		var gender;
		var disable_yn;
		var status;
		var lane;
		var pay_flag;
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
							
				if($(this).find('#email').val() == ""){
					alert("이메일를 입력하세요");
					return false;
				}else{
					email = $(this).find('#email').val();
				}
				
				if($(this).find('#handicap').val() == ""){
					alert("핸디캡점수를 입력하세요");
					return false;
				}else{
					handicap = $(this).find('#handicap').val();
				}
				
				if(!birthFocusout($(this).find('input[name=birth]').val())){
					alert("생년월일을 확인하세요.\nYYYY-MM-DD 형식으로 입력해주세요.");
					$(this).find('input[name=birth]').focus();
					return false;
				}else{
					birth = $(this).find('input[name=birth]').val();
				}

				
				gender = $(this).find('#gender').val();
				disable_yn = $(this).find('#disable_yn').val();
				status = $(this).find('#status').val();
				var origin_status = $(this).find(".btnUpdt").attr("data-status");
				var app_seq = $(this).find(".btnUpdt").attr("data-seq");
				var part = $(this).find(".btnUpdt").attr("data-part");
				var ct_seq = $(this).find(".btnUpdt").attr("data-ctseq");
				pay_flag = $(this).find("#pay_flag").val();

				var option1	 =	$(this).find('select[name=option1]').val();
				
				$.ajax({
					type : "POST",
					//url : "/apage/contest/updateContestApp.do",
					url : "/apage/event/updateEventContestApp.do",
					data : {
							
						telno     :	telno,
						//email     :	email,
						handicap  :	handicap,
						gender    :	gender,
						disable_yn:	disable_yn,
						status    :	status,
						app_seq   :	app_seq,
						origin_status : origin_status,
						lane : lane,
						part : part,
						ct_seq : ct_seq,
						pay_flag : pay_flag,
						birth : birth,
						option1	:	option1
					},
					cache : false,
					dataType : 'json',
					success : function(msg){
						//getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
						//getAppListJson(currPart);
						getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
					},
					error : function(data, status, err) {
						//alert(status);
						console.log(data + " / " + status + " / " + err);
						return;
					}
				});	
			}			
		})		
		//getAppListJson(currPart);			
	}else{
		return;
	}
}

//신청자 엑셀출력
function getExcel(){
	
	$("#frm").attr("action","/apage/event/kokContestAppListExcel.do");
	$("#frm").submit();
}
	
	
//신청현황 노출
function goSituationShow(){
	var flag = $("#frm #situation_show_yn").val();
	var situation_show_yn = "";
	var up_flag = false;
	if(flag != "" && flag == "Y"){
		if(confirm("접수현황노출을 '노출안함' 상태로 변경하시겠습니까?")){
			situation_show_yn = "N";
			up_flag = true;
		}
	}else{
		if(confirm("접수현황노출을 '노출' 상태로 변경하시겠습니까?")){
			situation_show_yn = "Y";
			up_flag = true;
		}
	}
	
	if(up_flag){
		$.ajax({
			url		:	"<c:url value='/apage/event/situationShowUpdate.do'/>",
			data	:	{
				situation_show_yn	:	situation_show_yn,
				ct_seq				:	'${contestView.ct_seq }'
			},
			type	:	"post",
			async	:	false,
			success	:	function(data){
				if(data.root.resultCode == "Y"){
					$("#frm #situation_show_yn").val(situation_show_yn);
					alert("성공하였습니다.");
					//location.reload();
					if(situation_show_yn == 'Y'){
						$("#situation_wrap").html("<span style='color:blue;'>노출</span>");
					}else{
						$("#situation_wrap").html("<span style='color:red;'>노출안함</span>");
					}
				}else if(data.root.resultCode == "N"){
					alert("실패하였습니다.");
				}else if(data.root.resultCode == "A"){
					alert("잘못된 접근입니다.");
				}else if(data.root.resultCode == "E"){
					alert("오류가 발생하였습니다.");
				}
			},
			error	:	function(err){
				alert("오류가 발생하였습니다.");
				console.log(err);
			}
		})
	}

}

function birthFocusout(val){
	var format = /^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
	if(!format.test(val)){
		return false;
	}else{
		return true;
	}
}


function goContestAppTargetList(){
	 var pop_title = "popupOpener" ;
     
     window.open("", pop_title,"width=1125,height=700,top=100,left=100"); 

      
     var frmData = document.appTargetFrm ;
     frmData.target = pop_title ;
     frmData.action = "/apage/event/kokContestTopRankcerDetail.do" ;
     frmData.submit() ;
}

function goContestAppTargetUpdate(){
	$("#frm").attr("action","/apage/event/kokContestTopRankcerModify.do");
	$("#frm").submit();
    
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
		url		:	"<c:url value='/apage/event/kokContestAppResultSendMsg.do'/>",
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
					if(data.root.smsHistory.length > 0){
						$("#totCnt").html(data.root.smsHistory.length+"건");
						$("#smsLogTmpl").tmpl(data.root.smsHistory).appendTo("#contestAppList");			
					}else{
						$("#totCnt").html("0명");
						$("#contestAppList").append("<tr><td colspan='9'>데이터가 존재하지 않습니다.</td></tr>");
					}
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
				$('.wrap-loading').addClass('display-none'); //(이미지 감추기 처리)
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
					$("#contestAppList").append("<tr><td colspan='16'>데이터가 존재하지 않습니다.</td></tr>");
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


function goSecondExcelUpload(){
	$("#frm").attr("action","/apage/event/kokContestTopRankcerSecondWrite.do");
	$("#frm").submit();
}
</script>

<script id="contestAppListTmpl" type="text/x-jquery-tmpl">
<tr>
	<th scope="row"><input type="checkbox" class="chkApp" value="\${app_seq }"/></th>
	<td scope="row">\${reg_turn }차</td>	
	<td scope="row">\${rownum }</td>	
	<td scope="row">\${reg_dt }</td>
	<td>\${join_name }<br/>(\${reg_id })</td>
	<td>\${deposit_name }</td>
	<td><input type="text" name="telno" id="telno" class="w90p" value="\${telno }" /></td>
	<td>
		<select id="gender" name="gender">
			<option value="M"{{if gender == 'M'}}selected{{/if}}>남</option>
			<option value="F"{{if gender == 'F'}}selected{{/if}}>여</option>										
		</select>					
	</td>
	<td><input type="text" name="birth" id="birth" class="w90p" value="\${birth }" class="app_birth" /></td>
	<td>
		<select id="disable_yn" name="disable_yn">
			<option value="N"{{if disable_yn == 'N'}}selected{{/if}}>해당없음</option>
			<option value="Y"{{if disable_yn == 'Y'}}selected{{/if}}>장애3급이상</option>										
		</select>		
	</td>
	<td><input type="text" name="handicap" id="handicap" class="w60p" value="\${handicap }" />점</td>
	<td>
		<select id="option1" name="option1">
			<option value="85" {{if option1 == '85'}}selected{{/if}}>85</option>
			<option value="90" {{if option1 == '90'}}selected{{/if}}>90</option>
			<option value="95" {{if option1 == '95'}}selected{{/if}}>95</option>
			<option value="100" {{if option1 == '100'}}selected{{/if}}>100</option>
			<option value="105" {{if option1 == '105'}}selected{{/if}}>105</option>
			<option value="110" {{if option1 == '110'}}selected{{/if}}>110</option>
			<option value="120" {{if option1 == '120'}}selected{{/if}}>120</option>
			<option value="130" {{if option1 == '130'}}selected{{/if}}>130</option>
		</select>
	</td>
	<td><span class="lane_num">\${lane }</span></td>
	<td>
		<select id="status" name="status" {{if status == '0003'}}style="color: red;"{{/if}} {{if status == '0004'}}style="color: blue;"{{/if}}>
			<option value="0003" {{if status == '0003'}}selected{{/if}}>신청취소</option>
			<option value="0004" {{if status == '0004'}}selected{{/if}}>선정</option>
		</select>
	</td>
	<td>
		<select id="pay_flag" name="pay_flag" {{if pay_flag == 'Y'}}style="color: blue;"{{/if}} {{if pay_flag == 'N'}}style="color: red;"{{/if}} {{if pay_flag == 'R'}}style="color: green;"{{/if}}>
			<option value="Y" {{if pay_flag == 'Y'}}selected{{/if}}>입금완료</option>
			<option value="N" {{if pay_flag == 'N'}}selected{{/if}}>입금대기</option>
			<option value="R" {{if pay_flag == 'R'}}selected{{/if}}>환불완료</option>
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
<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>
<form id="appTargetFrm" name="appTargetFrm">
	<input type="hidden" name="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
</form>
<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${contestView.atch_file_id }'/>" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" name="arrayApp" id="arrayApp" value="" />
	<input type="hidden" name="currPart" id="currPart" value="1" />
	<input type="hidden" id="situation_show_yn" value="${contestView.situation_show_yn }" />
	

<div id="container">
	<h3>왕중왕전 대회 신청관리</h3>
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
						<th>접수시작일(일자[시-분])</th>
						<td class="al_l">
							${contestView.app_start_dt } [${contestView.app_start_h }시 ${contestView.app_start_m }분]
						</td>
						<th>접수마감일(일자[시-분])</th>
						<td class="al_l">
							${contestView.app_end_dt } [${contestView.app_end_h }시 ${contestView.app_end_m }분]
						</td>
					</tr>
					<tr>
						<th>조별정원</th>
						<td class="al_l" colspan="3">
							${contestView.limit_part } 명
						</td>
					</tr>
					<tr>
						<th>노출여부</th>
						<td class="al_l">
							<c:if test="${contestView.use_at eq 'Y' }">노출</c:if>
							<c:if test="${contestView.use_at eq 'N' }">노출안함</c:if>								
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
						<td class="al_l">
							${contestView.ct_dt }								
						</td>
						<th>대회장소</th>
						<td class="al_l">
							${contestView.ct_place }								
						</td>
					</tr>
					<tr>
						<th>예비레인 사용유무</th>
						<td class="al_l">
							<c:if test="${contestView.prepare_yn eq 'Y'}">사용</c:if>
							<c:if test="${contestView.prepare_yn ne 'Y'}">사용안함</c:if>
						</td>
						<th>신청정보 수정가능 여부</th>
						<td class="al_l">
							<c:if test="${contestView.updt_yn eq 'Y' }">가능</c:if>
							<c:if test="${contestView.updt_yn ne 'Y' }">불가능</c:if>
						</td>
					</tr>
					<tr>
						<th>접수현황 노출여부</th>
						<td class="al_l" id="situation_wrap">
							<c:if test="${contestView.situation_show_yn eq 'Y'}"><span style="color: blue;">노출</span></c:if>
							<c:if test="${contestView.situation_show_yn ne 'Y'}"><span style="color: red;">노출안함</span></c:if>
						</td>
						<th>자동배치할 레인 수</th>
						<td class="al_l">
							<input type="text" name="lane_num" value="${contestView.lane_num}" readonly="readonly" disabled="disabled" style="width: 35px; text-align: center;">							
							<!-- 
							<span style="color: blue; font-weight: bold;">${contestView.lane_num }</span>레인
							 -->
							<p style="color: red;">※접수 시작시 자동으로 레인배치가 이루어지니 반드시 확인바랍니다.</p>
						</td>
					</tr>
					<!-- 입금정보 -->
					<tr>
						<th>은행명</th>
						<td class="al_l">${contestView.ct_bank }</td>
						<th>예금주</th>
						<td class="al_l">${contestView.ct_acchholder }</td>
					</tr>
					<tr>
						<th>계좌번호</th>
						<td class="al_l">${contestView.ct_account }</td>
						<th>참가비</th>
						<td class="al_l">${contestView.ct_price }</td>
					</tr>
					<tr>
						<th>입금시작일(일자-시-분)</th>
						<td class="al_l">
							${contestView.ct_deposit_stdt } [${contestView.ct_deposit_sth }시 ${contestView.ct_deposit_stm }분]
						</td>
						<th>입금마감일(일자-시-분)</th>
						<td class="al_l">
							${contestView.ct_deposit_eddt } [${contestView.ct_deposit_edh }시 ${contestView.ct_deposit_edm }분]
						</td>
					</tr>
					<tr>
						<th>신청취소마감일(일자-시-분)</th>
						<td class="al_l" colspan="3">
							${contestView.refund_finish_date } [${contestView.refund_finish_h }시 ${contestView.refund_finish_m }분]
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
				<a href="javascript:goContestAppTargetList();" class="btn-ty1 black" style="background-color: #1284c7; color: #fff; border-color: #1284c7;">참가자 리스트</a>
				<a href="javascript:goSituationShow();" class="btn-ty1 black" style="background-color: #1e7145; color: #fff; border-color: #1e7145;">접수현황노출변경</a>
				<a href="javascript:goUserPageSee('${contestView.ct_seq }')" id="userPageSee" class="btn-ty1" style="background-color: olive; color: #fff;">결과노출</a>
				<a href="javascript:goDelete()" class="btn-ty1 black">삭제</a>
				<a href="javascript:goModify()" class="btn-ty1 blue">수정</a>	
				<a href="javascript:goBoardList()" class="btn-ty1 light">목록</a>
			</p>
		</div>
		<!-- //bbs-view -->
		<ul class="ct-tab">
			<li><a href="javascript:void(0);" class="btnAppPart on" data-part="1">1조</a></li>
			<li><a href="javascript:void(0);" class="btnAppPart" data-part="2">2조</a></li>
			
			<p class="bbs-total mb20">
				<a href="javascript:getExcel();" class="btn-ty1 excel fr">신청자 엑셀다운</a>
				<a href="javascript:goSecondExcelUpload()" class="btn-ty1 blue fr">2차접수 업로드</a>
			</p>							
		</ul>		
		<ul class="ct-tab">
			<li><a href="javascript:void(0);" class="btnStatus on" data-status="0004" id="stat_firBtn">선정</a></li>
			<li><a href="javascript:void(0);" class="btnStatus" data-status="0003">신청취소</a></li>
			<li><a href="javascript:void(0);" class="btnStatus" data-status="SMS">문자전송내역</a></li>
		</ul>		
		<fieldset class="bbs-list">
			<legend>신청자 목록</legend>
			<table summary="사업신청 결과제출 전체 목록">
				<colgroup>
					<col width="3%">
					<col width="3%">
					<col width="3%">
					<col width="7%">
					<col width="7%">
					<col width="5%">
					<col width="8%">
					<col width="4%">
					<col width="8%">
					<col width="10%">
					<col width="6%">
					<col width="5%">
					<col width="5%">
					<col width="6%">
					<col width="6%">
					<col width="10%">
				</colgroup>
				<thead>
					<tr class="contestAppListTr">
						<th scope="col"><input type="checkbox" class="chkAppAll"/></th>
						<th scope="col">구분</th>
						<th scope="col">번호</th>
						<th scope="col">신청일자</th>
						<th scope="col">성명<br>(아이디)</th>
                        <th scope="col">임금자명</th>
                        <th scope="col">연락처</th>
                        <th scope="col">성별</th>
                        <th scope="col">생년월일</th>
                        <th scope="col">장애여부</th>
                        <th scope="col">핸디</th>
                        <th scope="col">티셔츠<br/>사이즈</th>
                        <th scope="col">레인</th>
                        <th scope="col">상태</th>
                        <th scope="col">결제유무</th>
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
				</thead>
				<tbody id="contestAppList">

				</tbody>
			</table>
		</fieldset>
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goAppDelete()" class="btn-ty1 black">선택삭제</a>
				<a href="javascript:goAppModify()" class="btn-ty1 blue">선택수정</a>
			</p>
		</div>
	</div>
</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>