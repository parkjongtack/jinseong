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
		alert("???????????? ???????????????");
		$tr.find('#telno').fucus();
		return;
	}else{
		telno = $tr.find('#telno').val();
	}
	
	
	if($tr.find('#handicap').val() == ""){
		alert("?????????????????? ???????????????");
		$tr.find('#handicap').fucus();
		return;
	}else{
		handicap = $tr.find('#handicap').val();
	}
	
	if(!birthFocusout($tr.find('input[name=birth]').val())){
		alert("??????????????? ???????????????.\nYYYY-MM-DD ???????????? ??????????????????.");
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
	
	if(confirm("??????????????? ?????????????????????????") == true){
		
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
	
	if(confirm("??????????????? ?????????????????????????") == true){
		
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
	if(!confirm('?????????????????????????')) return;
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
					strhtml += "<tr><td colspan='16'>????????? ?????? ????????? ????????????.</td></tr>";
					$("#contestAppList").html("");
					$("#contestAppList").html(strhtml); 

				}
			},beforeSend		:function(){
	        	//(????????? ???????????? ??????)
		        $('.wrap-loading').removeClass('display-none');
		    	},complete			:function(){
		           //(????????? ????????? ??????)
		            $('.wrap-loading').addClass('display-none');	     
		        },error		: function(data) {
					//alert('????????? ??????????????????.');
					//getAppListJson(part);
		        	getAppResultList($(".btnAppPart.on").attr("data-part"),$(".btnStatus.on").attr("data-status"));
				},timeout:100000
		});
}

//??????????????? ??????
function goAppDelete(){
	var app_seq = new Array();
	var checkVal = "";
	
	$(".chkApp").each(function(){
		if($(this).is(":checked")){
			checkVal += $(this).val()+",";
		}
	})
	if(checkVal == ""){
		alert("????????? ???????????? ????????????.");
		return;
	}
	
	checkVal = checkVal.substring(0,checkVal.length - 1);
	app_seq = checkVal.split(',');
	
	if(confirm("????????? ?????????????????? ?????????????????????????")==true){
		
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
		alert("????????? ???????????? ????????????.");
		return;
	}
	
	if(confirm("????????? ???????????? ?????????????????????????") == true){
		
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
					alert("???????????? ???????????????");
					return false;
				}else{
					telno = $(this).find('#telno').val();
				}
							
				if($(this).find('#email').val() == ""){
					alert("???????????? ???????????????");
					return false;
				}else{
					email = $(this).find('#email').val();
				}
				
				if($(this).find('#handicap').val() == ""){
					alert("?????????????????? ???????????????");
					return false;
				}else{
					handicap = $(this).find('#handicap').val();
				}
				
				if(!birthFocusout($(this).find('input[name=birth]').val())){
					alert("??????????????? ???????????????.\nYYYY-MM-DD ???????????? ??????????????????.");
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

//????????? ????????????
function getExcel(){
	
	$("#frm").attr("action","/apage/event/kokContestAppListExcel.do");
	$("#frm").submit();
}
	
	
//???????????? ??????
function goSituationShow(){
	var flag = $("#frm #situation_show_yn").val();
	var situation_show_yn = "";
	var up_flag = false;
	if(flag != "" && flag == "Y"){
		if(confirm("????????????????????? '????????????' ????????? ?????????????????????????")){
			situation_show_yn = "N";
			up_flag = true;
		}
	}else{
		if(confirm("????????????????????? '??????' ????????? ?????????????????????????")){
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
					alert("?????????????????????.");
					//location.reload();
					if(situation_show_yn == 'Y'){
						$("#situation_wrap").html("<span style='color:blue;'>??????</span>");
					}else{
						$("#situation_wrap").html("<span style='color:red;'>????????????</span>");
					}
				}else if(data.root.resultCode == "N"){
					alert("?????????????????????.");
				}else if(data.root.resultCode == "A"){
					alert("????????? ???????????????.");
				}else if(data.root.resultCode == "E"){
					alert("????????? ?????????????????????.");
				}
			},
			error	:	function(err){
				alert("????????? ?????????????????????.");
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


//????????? ???????????? ???????????? ???????????? or ?????????
function goUserPageSee(ct_seq) {
	
	var html = $("#userPageSee").html();
	
	if(html == "????????????"){
		if(confirm("????????? ?????????????????????????") == true){
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
						alert("????????? ?????????????????????.");
						$("#userPageSee").html("????????????");		
					}else{
						alert("???????????????.");
					}
				}
			});		
		}
	}else{
		if(confirm("????????? ??????????????????????") == true){
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
						alert("????????? ???????????? ????????????.");
						$("#userPageSee").html("????????????");		
					}else{
						alert("???????????????.");
					}			
				}
			});		
		}
	}
}


function sendMsg(){
	if(!confirm("???????????? ???????????????????")){
		return;
	}
	
	var smsFlag = $("#result_sms_send_yn").val();
	if(smsFlag == "Y"){
		alert("?????? ?????????????????????.\n?????? ???????????? ????????? ???????????? ??????????????????.");
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
				alert("??????????????? ?????????????????????.");
				$("#result_sms_send_yn").val("Y");
			}else if(data.root.resultCode == 'E'){
				alert("?????????????????????.");
				$("#result_sms_send_yn").val("N");
			}else{
				alert("??? ?????? ????????? ?????? ????????????.");
			}
		},beforeSend		:function(){
			$('.wrap-loading').removeClass('display-none'); //(????????? ???????????? ??????)
		},complete			:function(){
			$('.wrap-loading').addClass('display-none'); //(????????? ????????? ??????)
		},error		: function(data) {
			alert('????????? ??????????????????.');
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
					//$("#totCnt").html(data.root.smsResultList.length+"???");
					//$("#smsLogTmpl").tmpl(data.root.smsResultList).appendTo("#contestAppList");
					if(data.root.smsHistory.length > 0){
						$("#totCnt").html(data.root.smsHistory.length+"???");
						$("#smsLogTmpl").tmpl(data.root.smsHistory).appendTo("#contestAppList");			
					}else{
						$("#totCnt").html("0???");
						$("#contestAppList").append("<tr><td colspan='9'>???????????? ???????????? ????????????.</td></tr>");
					}
				}else{
					$("#totCnt").html("0???");
					$("#contestAppList").append("<tr><td colspan='9'>???????????? ???????????? ????????????.</td></tr>");
				}
			},beforeSend		:function(){
				$(".msgLogListTr").css("display","table-row");
				$(".contestAppListTr").css("display","none");
				$(".refundListTr").css("display","none");
				$('.wrap-loading').removeClass('display-none'); //(????????? ???????????? ??????)
			},complete			:function(){
				$('.wrap-loading').addClass('display-none'); //(????????? ????????? ??????)
			},error		: function(data) {
				alert('????????? ??????????????????.');
				$('.wrap-loading').addClass('display-none'); //(????????? ????????? ??????)
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
					$("#totCnt").html(data.root.resultList.length+"???");
					$("#contestAppListTmpl").tmpl(data.root.resultList).appendTo("#contestAppList");			
				}else{
					$("#totCnt").html("0???");
					$("#contestAppList").append("<tr><td colspan='16'>???????????? ???????????? ????????????.</td></tr>");
				}
			},beforeSend		:function(){
				$(".msgLogListTr").css("display","none");
				$(".refundListTr").css("display","none");
				$(".contestAppListTr").css("display","table-row");
				$('.wrap-loading').removeClass('display-none'); //(????????? ???????????? ??????)
			},complete			:function(){
				$('.wrap-loading').addClass('display-none'); //(????????? ????????? ??????)
			},error		: function(data) {
				alert('????????? ??????????????????.');
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
	
	if(confirm("????????? ???????????????????")){
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
						alert("????????? ???????????????.");
						$(".btnStatus").each(function(data){
							if($(this).hasClass("on")){
								$(this).trigger("click");
							}
						})
					}else if(data.root.resultCode == "N"){
						alert("?????????????????????.");
					}else if(data.root.resultCode == "A"){
						alert("????????? ???????????????.");
					}else{
						alert("????????? ?????????????????????.");
					}
				},
				error	:	function(err){
					console.log(err);				
					alert("????????? ?????????????????????.");
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
	<td scope="row">\${reg_turn }???</td>	
	<td scope="row">\${rownum }</td>	
	<td scope="row">\${reg_dt }</td>
	<td>\${join_name }<br/>(\${reg_id })</td>
	<td>\${deposit_name }</td>
	<td><input type="text" name="telno" id="telno" class="w90p" value="\${telno }" /></td>
	<td>
		<select id="gender" name="gender">
			<option value="M"{{if gender == 'M'}}selected{{/if}}>???</option>
			<option value="F"{{if gender == 'F'}}selected{{/if}}>???</option>										
		</select>					
	</td>
	<td><input type="text" name="birth" id="birth" class="w90p" value="\${birth }" class="app_birth" /></td>
	<td>
		<select id="disable_yn" name="disable_yn">
			<option value="N"{{if disable_yn == 'N'}}selected{{/if}}>????????????</option>
			<option value="Y"{{if disable_yn == 'Y'}}selected{{/if}}>??????3?????????</option>										
		</select>		
	</td>
	<td><input type="text" name="handicap" id="handicap" class="w60p" value="\${handicap }" />???</td>
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
			<option value="0003" {{if status == '0003'}}selected{{/if}}>????????????</option>
			<option value="0004" {{if status == '0004'}}selected{{/if}}>??????</option>
		</select>
	</td>
	<td>
		<select id="pay_flag" name="pay_flag" {{if pay_flag == 'Y'}}style="color: blue;"{{/if}} {{if pay_flag == 'N'}}style="color: red;"{{/if}} {{if pay_flag == 'R'}}style="color: green;"{{/if}}>
			<option value="Y" {{if pay_flag == 'Y'}}selected{{/if}}>????????????</option>
			<option value="N" {{if pay_flag == 'N'}}selected{{/if}}>????????????</option>
			<option value="R" {{if pay_flag == 'R'}}selected{{/if}}>????????????</option>
		</select>
	</td>
	<td>
		<a href="javascript:void(0);" data-seq="\${app_seq }" data-part="\${part }" data-status="\${status}" data-ctseq="\${ct_seq}"class="btnUpdt s-blue-btn">??????</a>
		<a href="javascript:void(0);" data-seq="\${app_seq }" data-part="\${part }" class="btnDel s-gry-btn">??????</a>		
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
		{{if etc9 == '0004'}}<span style="color:blue;">??????</span>{{/if}}
		{{if etc9 == '0005'}}<span style="color:green;">??????</span>{{/if}}
		{{if etc9 == '0003'}}<span style="color:red;">????????????</span>{{/if}}
	</td>
	<td style="width:8%;">
		{{if rslt == '0'}}<span style="color:blue;">????????????</span>{{/if}}
		{{if rslt != '0' && rslt != '99999' }}<span style="color:red;">????????????</span>{{/if}}
		{{if rslt == '99999'}}<span style="color:green;">?????????</span>{{/if}}

	</td>
	<td style="width:10%;">\${reqdate }</td>
	<td style="width:8%;">
		{{if rslt != '0' && rslt != '99999'}}<a href="javascript:void(0);" data-msgkey="\${msgkey }" data-senddt="\${reqdate}" data-rslt="\${rslt}" class="s-blue-btn resendBtn">?????????</a>{{/if}}
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
	<h3>???????????? ?????? ????????????</h3>
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
						<th>??????</th>
						<td class="al_l" colspan="3">
							${contestView.ct_sbj }
						</td>
					</tr>
					<tr>
						<th>???????????????(??????[???-???])</th>
						<td class="al_l">
							${contestView.app_start_dt } [${contestView.app_start_h }??? ${contestView.app_start_m }???]
						</td>
						<th>???????????????(??????[???-???])</th>
						<td class="al_l">
							${contestView.app_end_dt } [${contestView.app_end_h }??? ${contestView.app_end_m }???]
						</td>
					</tr>
					<tr>
						<th>????????????</th>
						<td class="al_l" colspan="3">
							${contestView.limit_part } ???
						</td>
					</tr>
					<tr>
						<th>????????????</th>
						<td class="al_l">
							<c:if test="${contestView.use_at eq 'Y' }">??????</c:if>
							<c:if test="${contestView.use_at eq 'N' }">????????????</c:if>								
						</td>
						<th>????????????</th>
						<td class="al_l">
							<c:if test="${contestView.ct_process eq 'R' }">????????????</c:if>
							<c:if test="${contestView.ct_process eq 'S' }">????????????</c:if>
							<c:if test="${contestView.ct_process eq 'E' }">????????????</c:if>
							<c:if test="${contestView.ct_process eq 'F' }">????????????</c:if>
						</td>
					</tr>
					<tr>
						<th>????????????</th>
						<td class="al_l">
							${contestView.ct_dt }								
						</td>
						<th>????????????</th>
						<td class="al_l">
							${contestView.ct_place }								
						</td>
					</tr>
					<tr>
						<th>???????????? ????????????</th>
						<td class="al_l">
							<c:if test="${contestView.prepare_yn eq 'Y'}">??????</c:if>
							<c:if test="${contestView.prepare_yn ne 'Y'}">????????????</c:if>
						</td>
						<th>???????????? ???????????? ??????</th>
						<td class="al_l">
							<c:if test="${contestView.updt_yn eq 'Y' }">??????</c:if>
							<c:if test="${contestView.updt_yn ne 'Y' }">?????????</c:if>
						</td>
					</tr>
					<tr>
						<th>???????????? ????????????</th>
						<td class="al_l" id="situation_wrap">
							<c:if test="${contestView.situation_show_yn eq 'Y'}"><span style="color: blue;">??????</span></c:if>
							<c:if test="${contestView.situation_show_yn ne 'Y'}"><span style="color: red;">????????????</span></c:if>
						</td>
						<th>??????????????? ?????? ???</th>
						<td class="al_l">
							<input type="text" name="lane_num" value="${contestView.lane_num}" readonly="readonly" disabled="disabled" style="width: 35px; text-align: center;">							
							<!-- 
							<span style="color: blue; font-weight: bold;">${contestView.lane_num }</span>??????
							 -->
							<p style="color: red;">????????? ????????? ???????????? ??????????????? ??????????????? ????????? ??????????????????.</p>
						</td>
					</tr>
					<!-- ???????????? -->
					<tr>
						<th>?????????</th>
						<td class="al_l">${contestView.ct_bank }</td>
						<th>?????????</th>
						<td class="al_l">${contestView.ct_acchholder }</td>
					</tr>
					<tr>
						<th>????????????</th>
						<td class="al_l">${contestView.ct_account }</td>
						<th>?????????</th>
						<td class="al_l">${contestView.ct_price }</td>
					</tr>
					<tr>
						<th>???????????????(??????-???-???)</th>
						<td class="al_l">
							${contestView.ct_deposit_stdt } [${contestView.ct_deposit_sth }??? ${contestView.ct_deposit_stm }???]
						</td>
						<th>???????????????(??????-???-???)</th>
						<td class="al_l">
							${contestView.ct_deposit_eddt } [${contestView.ct_deposit_edh }??? ${contestView.ct_deposit_edm }???]
						</td>
					</tr>
					<tr>
						<th>?????????????????????(??????-???-???)</th>
						<td class="al_l" colspan="3">
							${contestView.refund_finish_date } [${contestView.refund_finish_h }??? ${contestView.refund_finish_m }???]
						</td>
					</tr>
					
					<tr>
                       	<td colspan="6" class="cont">${contestView.ct_content }</td>
                    </tr>
                    <tr>
                       	<th>????????????</th>
                        <td colspan="5">
                           	<c:choose>
								<c:when test="${fn:length(contestFile) > 0}">
									<c:forEach items="${contestFile}" var="contestFile" varStatus="status">
										<a href="javascript:void(0);" onclick="Down('${contestFile.atch_file_id }');">${contestFile.orignl_file_nm }</a>
									</c:forEach>
								</c:when>
								<c:otherwise>??????????????? ????????????.</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
				</tbody>
			</table>
		</div>
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goContestAppTargetList();" class="btn-ty1 black" style="background-color: #1284c7; color: #fff; border-color: #1284c7;">????????? ?????????</a>
				<a href="javascript:goSituationShow();" class="btn-ty1 black" style="background-color: #1e7145; color: #fff; border-color: #1e7145;">????????????????????????</a>
				<a href="javascript:goUserPageSee('${contestView.ct_seq }')" id="userPageSee" class="btn-ty1" style="background-color: olive; color: #fff;">????????????</a>
				<a href="javascript:goDelete()" class="btn-ty1 black">??????</a>
				<a href="javascript:goModify()" class="btn-ty1 blue">??????</a>	
				<a href="javascript:goBoardList()" class="btn-ty1 light">??????</a>
			</p>
		</div>
		<!-- //bbs-view -->
		<ul class="ct-tab">
			<li><a href="javascript:void(0);" class="btnAppPart on" data-part="1">1???</a></li>
			<li><a href="javascript:void(0);" class="btnAppPart" data-part="2">2???</a></li>
			
			<p class="bbs-total mb20">
				<a href="javascript:getExcel();" class="btn-ty1 excel fr">????????? ????????????</a>
				<a href="javascript:goSecondExcelUpload()" class="btn-ty1 blue fr">2????????? ?????????</a>
			</p>							
		</ul>		
		<ul class="ct-tab">
			<li><a href="javascript:void(0);" class="btnStatus on" data-status="0004" id="stat_firBtn">??????</a></li>
			<li><a href="javascript:void(0);" class="btnStatus" data-status="0003">????????????</a></li>
			<li><a href="javascript:void(0);" class="btnStatus" data-status="SMS">??????????????????</a></li>
		</ul>		
		<fieldset class="bbs-list">
			<legend>????????? ??????</legend>
			<table summary="???????????? ???????????? ?????? ??????">
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
						<th scope="col">??????</th>
						<th scope="col">??????</th>
						<th scope="col">????????????</th>
						<th scope="col">??????<br>(?????????)</th>
                        <th scope="col">????????????</th>
                        <th scope="col">?????????</th>
                        <th scope="col">??????</th>
                        <th scope="col">????????????</th>
                        <th scope="col">????????????</th>
                        <th scope="col">??????</th>
                        <th scope="col">?????????<br/>?????????</th>
                        <th scope="col">??????</th>
                        <th scope="col">??????</th>
                        <th scope="col">????????????</th>
                        <th scope="col">??????</th>
					</tr>
					<tr class="msgLogListTr" style="display: none;">
                        <th scope="col">??????</th>
                        <th scope="col">?????????</th>
                        <th scope="col">????????????</th>
                        <th scope="col">????????????</th>
                        <th scope="col">??????</th>
                        <th scope="col">????????????</th>
                        <th scope="col">????????????</th>
						<th scope="col">????????????</th>
                        <th scope="col">?????????</th>
					</tr>
				</thead>
				<tbody id="contestAppList">

				</tbody>
			</table>
		</fieldset>
		<div class="pg-nav">
			<p class="btn">
				<a href="javascript:goAppDelete()" class="btn-ty1 black">????????????</a>
				<a href="javascript:goAppModify()" class="btn-ty1 blue">????????????</a>
			</p>
		</div>
	</div>
</div>
</form:form>

<jsp:include page="/apage/inc/footer.do"></jsp:include>