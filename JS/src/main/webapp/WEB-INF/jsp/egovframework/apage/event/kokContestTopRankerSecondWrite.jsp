<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
$(document).ready(function () {
	
   
   
});



function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}

//엑셀 파일 선택
function selectExcelFile(){
	
	$("#excelFile").trigger("click");
}

//엑셀 파일 읽기
function inputFileEle(val){
	
	var fileValue = val.split("\\");
	var fileName = fileValue[fileValue.length-1]; // 파일명
	var ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
	
	//alert(ext);
	$("#excelFileName").html(fileName);
	
	var columnCnt = 8;
		
	var formData = new FormData();
	formData.append("scType", columnCnt);				//컬럼 수
	formData.append("ct_seq", $("#frm input[name=ct_seq]").val());				//컬럼 수
	formData.append("file_1", $("input[name=file_1]")[0].files[0]);
	
	$.ajax({
		url		:	"<c:url value='/apage/event/kokContestTopRankcerSecondExcelRead.do'/>", 
		data: formData, 
		processData: false, 
		contentType: false, 
		type: 'POST',
		success	:	function(data){
			if(data.root.resultCode == "Y"){
				var aList = data.root.resultList;
				drawTagType(aList);
			}else{
				alert("양식이 올바르지 않은 파일입니다.");
			}
			$("#div_ajax_load_image").hide();
		},
		beforeSend: function () {
				
			var width = 0;
	        var height = 0;
	        var left = 0;
	        var top = 0;

	        width = 50;
	        height = 50;
	        left = ($(window).width() - width) / 2 + $(window).scrollLeft();
	        top = ($(window).height() - height) / 2 + $(window).scrollTop();
	            
	        //화면의 높이와 너비를 구한다.
	        var maskHeight = $(document).height();
	        var maskWidth = window.document.body.clientWidth;

	        if($("#div_ajax_load_image").length != 0) {
	        	$("#div_ajax_load_image").css({
	            	"top": top+"px",
	                "left": left+"px"
	            });
	            $("#div_ajax_load_image").show();
	            $('#mask').show();
	        }else {
	            	   
	        	var mask = "<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>";
	            	
	            $('body').append(mask).append('<div id="div_ajax_load_image" style="position:absolute; top:' + top + 'px; left:' + left + 'px; width:' + width + 'px; height:' + height + 'px; z-index:9999; filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="/resources/apage/images/common/loading1.gif" style="width:100px; height:100px;"></div>');
	            	
	            //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
	            $('#mask').css({
	            	'width' : maskWidth, 
	                'height': maskHeight,
	                'opacity' : '0.3'
	            }); 
	             
	            //마스크 표시
	            $('#mask').show();
	        }
		},
		complete: function () {
		  	$("#mask").hide();
		   	$("#div_ajax_load_image").hide();
		},
		error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			$("#div_ajax_load_image").hide();
		} 
	})	
}



function drawTagType(aList){
	var html = '';
	
	for(var i = 0; i < aList.length; i ++){
		html += '<tr>';
		html += '<td>'+(i+1)+'</td>';
		html += '<td><input type="text" name="list['+i+'].join_name" value="'+aList[i].join_name+'"></td>';
		html += '<td><input type="text" name="list['+i+'].reg_id" value="'+aList[i].reg_id+'"></td>';
		
		/*
		html += '<td>';
		html += '<select name="list['+i+'].part">';
		if(aList[i].part == '1'){
			html += '<option value="1" selected="selected">1조</option>';
			html += '<option value="2">2조</option>';
		}else{
			html += '<option value="1">1조</option>';
			html += '<option value="2" selected="selected">2조</option>';
		}
		html += '</select>';
		html += '</td>';
		*/
		
		html += '<td><input type="text" name="list['+i+'].deposit_name" value="'+aList[i].deposit_name+'"></td>';
		html += '<td><input type="text" name="list['+i+'].telno" value="'+aList[i].telno+'" data-type="tel"></td>';
		html += '<td>';
		html += '<select name="list['+i+'].gender">';
		if(aList[i].gender == 'M'){
			html += '<option value="M" selected="selected">남</option>';
			html += '<option value="F">여</option>';
		}else{
			html += '<option value="M">남</option>';
			html += '<option value="F" selected="selected">여</option>';
		}
		html += '</select>';
		html += '</td>';
		
		
		html += '<td><input type="text" name="list['+i+'].birth" value="'+aList[i].birth+'" data-type="birth"></td>';
		html += '<td>';
		html += '<select name="list['+i+'].disable_yn">';
		if(aList[i].disable_yn == 'Y'){
			html += '<option value="Y" selected="selected">장애3급이상</option>';
			html += '<option value="N">해당없음</option>';
		}else{
			html += '<option value="Y">장애3급이상</option>';
			html += '<option value="N" selected="selected">해당없음</option>';
		}
		html += '</select>';
		html += '</td>';
		html += '<td><input type="number" name="list['+i+'].handicap" value="'+aList[i].handicap+'"></td>';
		html += '<td>';
		html += '<select name="list['+i+'].option1">';

			html += '<option value="85" '; if(aList[i].option1 == '85'){ html += 'selected="selected"';}; html += '>85</option>';
			html += '<option value="90" '; if(aList[i].option1 == '90'){ html += 'selected="selected"';}; html += '>90</option>';
			html += '<option value="95" '; if(aList[i].option1 == '95'){ html += 'selected="selected"';}; html += '>95</option>';
			html += '<option value="100" '; if(aList[i].option1 == '100'){ html += 'selected="selected"';}; html += '>100</option>';
			html += '<option value="105" '; if(aList[i].option1 == '105'){ html += 'selected="selected"';}; html += '>105</option>';
			html += '<option value="110" '; if(aList[i].option1 == '110'){ html += 'selected="selected"';}; html += '>110</option>';
			html += '<option value="120" '; if(aList[i].option1 == '120'){ html += 'selected="selected"';}; html += '>120</option>';
			html += '<option value="130" '; if(aList[i].option1 == '130'){ html += 'selected="selected"';}; html += '>130</option>';
			
		html += '</select>';
		html += '</td>';
		html += '</tr>';
	}
	
	$("#excelDataForm").empty();
	$("#excelDataForm").html(html);
}




function goBoardList(){
	$("#frm").attr("action", "<c:url value='/apage/event/kokContestMngDetail.do'/>");
	$("#frm").submit();
}

function goBoardWrite(){
	var regFlag = true;
	$("#excelDataForm tr").each(function(idx){
		$input = $(this).find("input");
		$input.each(function(){
			if($.trim($(this).val()) == ''){
				alert("데이터를 입력해주세요.");
				$(this).focus();
				regFlag = false;
				return false;
			}else{
				$dataType = $(this).attr("data-type");
				if($dataType == "tel"){
					var telnoRegExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
					if(!telnoRegExp.test($.trim($(this).val()))){
						alert("잘못된 휴대폰 번호입니다.\n010-1234-1234처럼 ' - ' (하이픈)과 같이 입력해주세요.");
						$(this).focus();
						regFlag = false;
						return false;
					}
				}else if($dataType == "birth"){
					 var birthRegExp = /^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
					 if(!birthRegExp.test($.trim($(this).val()))){
						alert("잘못된 생년월일입니다.\n1983-03-24처럼 ' - ' (하이픈)과 같이 입력해주세요.");
						$(this).focus();
						regFlag = false;
						return false;
					 }
				}
			}
		})
	});
	
	if(regFlag){
		var dataString =  $("#frm").serialize();
		$("#frm").attr("action", "<c:url value='/apage/event/kokContestTopRankcerSecondReg.do'/>");
		$("#frm").submit();
	}
}


function goCtReg(){
	var ct_val = '';
	var ct_html = '';
	$("input:checkbox[name=ct_checked_val]").each(function(){
		if($(this).is(":checked")){
 			if(ct_val == null || ct_val == ""){
				ct_val = $(this).val();
 			}else{
				ct_val += ","+$(this).val();
 			}
 			
 			ct_html += '<p style="margin-top:5px;">'+$(this).parent().parent().find(".ct_sbj").html()+'<a href="javascript:propChecked('+$(this).val()+')" class="ct_cancel btn-ty2 cancel" data-ctSeq="'+$(this).val()+'" style="margin-left:5px;">삭제</a></p>';
 			
		}
	})
	
	$("#ct_select_list").html(ct_html);
	$("input[name=ct_group]").val(ct_val);
	$("#pop-ebk").css("display","none");
}



</script>
<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>
	<div id="container">
		<h3>왕중왕전 대회 2차 참가자 등록</h3>
		<div class="contents">
			<ul class="ct-tab">
				<li><a href="javascript:void(0)" class="on">2차 참가자 등록</a></li>
			</ul>
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
								${contestVo.ct_sbj }
							</td>
						</tr>
						<tr>
							<th>대회일자</th>
							<td class="al_l">
								${contestVo.ct_dt }								
							</td>
							<th>대회장소</th>
							<td class="al_l">
								${contestVo.ct_place }								
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<br/><br/>
			
			
			<div class="bbs-write" style="margin-bottom: 20px; border: 1px solid #bbb;">
				· 대회신청이 가능한 상위랭커 업로드 화면입니다. 대회등록정원과 상위랭커수를 동일하게 작성해 주십시오.<br/>
				· 파일첨부 후 하단의 상위랭커내역을 재검토 해주십시오.<br/>
				· 상위랭커 업로드 후 하단 등록버튼 클릭 시 상위랭커 업로드 내역 등록이 완료됩니다.<br/>
				· 반드시 정해진 양식의 엑셀 파일로 업로드하시기 바랍니다. <br/>   
				· ID는 필수입력사항입니다. <br/>
			</div>
			<form id="frm" name="frm"  method="post">
				<input type="hidden" name="reg_id"  value="<c:out value='${memberinfo.mber_id }'/>" />
				<input type="hidden" name="ct_seq" value="${ct_seq }">
				<input type="hidden" name="ct_type" value="${contestVo.ct_type }">
				<input type="hidden" name="ct_group" value="${contestVo.ct_group }">
				<input type="hidden" name="currRow" value="1">
				
				<div class="bbs-select">
					<table summary="" id="smsArea">
	                	<colgroup>
	                		<col width="10%">
	                		<col width="55%">
	                		<col width="10%">
	                		<col width="25%">
	                	</colgroup>
	                	<tbody>
	                		<tr>
								<th>양식</th>
								<td class="al_l" colspan="3">
				                	<a href="javascript:void(0);" onclick="Down('FILE_201911250355253210');" class="s-blue-btn" style="background-color: #1e7145; border: 1px solid #1e7145;">엑셀 양식 다운로드</a>	         
								</td>
							</tr>	
	                		<tr>
								<th><span style="color:red;">*</span>엑셀 파일</th>
								<td class="al_l" colspan="3">
									<input type="file" id="excelFile" name="file_1" onchange="inputFileEle(this.value);" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" style="display: none;">
									<label id="excelFileName"></label>
				                	<a href="javascript:selectExcelFile();" class="s-blue-btn">파일첨부</a>	         
								</td>
							</tr>	
	                    </tbody>
	                </table>
	            </div>
            
           		<!-- 엑셀 파일 내용 -->
            	<div class="bbs-write" style="overflow: scroll; height: 700px;">
					<table>
						<colgroup>
							<col width="*">
						</colgroup> 
						<tbody>
							<tr class="sheetType" id="sheetType1">
								<th scope="col">번호</th>
								<th scope="col"><span style="color:red;">*</span>성명</th>
								<th scope="col"><span style="color:red;">*</span>아이디</th>
								<!-- 
								<th scope="col"><span style="color:red;">*</span>조</th> 
								-->
								<th scope="col">입금자명</th>
								<th scope="col">연락처</th>
								<th scope="col">성별</th>
								<th scope="col">생년월일</th>
								<th scope="col">장애여부</th>
								<th scope="col">핸디</th>
								<th scope="col">티셔츠 사이즈</th>
							</tr>
						</tbody>
						<tbody id="excelDataForm">
						
						</tbody>
					</table>
				</div>
			</form>
			
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


<jsp:include page="/apage/inc/footer.do"></jsp:include>