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
	
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/apage/member/smsExcelUploadList.do'/>";
	
	//발송일 기간검색
	$('.datepicker').removeClass('hasDatepicker').datepicker({
		showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
		buttonImage: "/resources/apage/images/board/ic_date.gif", // 버튼 이미지
		buttonImageOnly: true  // 버튼에 있는 이미지만 표시한다.		
	});
	
	//msg alert
	$.urlParam = function(name){
		var results = new RegExp("[\?&]" + name + "=([^&#]*)").exec(window.location.href);
	    if(results == null){
	       return null;
	    }
	    else{
	       return results[1] || 0;
	    }
	}  
	var msg = $.urlParam("msg");
	if(msg != null){
		if(msg == "insert.success"){
			alert("발송되었습니다.");	
		}else{
			alert("실패했습니다.");
		}
	}
	
	//getSmsListJson();
});

//엑셀 양식 다운로드
function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}

//검색
function goSearch() {
	
	$("#messagefrm").attr("action", "<c:url value='/apage/member/smsExcelUploadList.do'/>");
	$("#messagefrm").submit();
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
	
	var columnCnt = 2;
		
	var formData = new FormData();
	formData.append("scType", columnCnt);				//컬럼 수
	formData.append("file_1", $("input[name=file_1]")[0].files[0]);
	
	$.ajax({
		url		:	"<c:url value='/apage/member/smsExcelRead.do'/>", 
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

//엑셀 내용 그리기
function drawTagType(list) {
	
	var aList = list;
	$("#excelDataForm").empty();
	$(".sheetType").css("display","none");
	$("#sheetType1").css("display","table-row");
	var html = '';
	
	for(var i = 0; i < aList.length; i++){
		
		html += '<tr class="smsRow smsMemberInfo'+(i+1)+'">';
		//추가,삭제 버튼
		html += '<td>';
		html += '<a href="javascript:void(0)" onclick="addsmsMemberInfo()" style="background-color: #fafafa; border: 1px solid #aaa; color: #444; padding: 7px; border-radius: .25em">추가</a> / ';
		html += '<a href="javascript:void(0)" onclick="delsmsMemberInfo('+(i+1)+')" style="background-color: #fafafa; border: 1px solid #aaa; color: #444; padding: 7px; border-radius: .25em">삭제</a>';
		html += '</td>';
		
		//이름
		html += '<td><input type="text" name="name_arr" title="이름 입력" value="'+aList[i].mber_name+'"></td>'; //value="'+aList[i].mber_name+'"
		
		//전화번호
		html += '<td><input type="text" name="cell_arr" title="전화번호 입력" value="'+aList[i].mber_tel+'"></td>'; //value="'+aList[i].mber_tel+'"
		
		html += '</tr>';	
	}
	$("#excelDataForm").append(html);
}

//행 추가
function addsmsMemberInfo() {

	var trLength = $(".smsRow").length;
	
	var html = '';
	
	html += '<tr class="smsRow smsMemberInfo'+(trLength+1)+'">';
	//추가,삭제 버튼
	html += '<td>';
	html += '<a href="javascript:void(0)" onclick="addsmsMemberInfo()" style="background-color: #fafafa; border: 1px solid #aaa; color: #444; padding: 7px; border-radius: .25em">추가</a> / ';
	html += '<a href="javascript:void(0)" onclick="delsmsMemberInfo('+(trLength+1)+')" style="background-color: #fafafa; border: 1px solid #aaa; color: #444; padding: 7px; border-radius: .25em">삭제</a>';
	html += '</td>';
	
	//이름
	html += '<td><input type="text" name="name_arr" title="이름 입력"></td>';
	
	//전화번호
	html += '<td><input type="text" name="cell_arr" title="전화번호 입력"></td>';
	
	html += '</tr>';
	
	$(".smsMemberInfo"+trLength).after(html);
}

//행 삭제
function delsmsMemberInfo(index) {
	
	var trLength = $(".smsRow").length;
	if(trLength == 1){
		alert("더 이상 삭제하실 수 없습니다.");
		return;
	}

	$(".smsMemberInfo"+index).remove();
}

//초기화
function goInit(){
	$('#frm').find('input,select,textarea').each(function(){
		$(this).val('');
	});
}

//SMS 발송
function sendSms(){
	/*
	var mber_name_arr = null;
	var mber_tel_arr = null;
	
	var cnt = 0;
	*/
	if($("#excelFileName").html() == ""){
		alert("엑셀 파일을 선택해주세요.");
		return;
	}
	
	if($("#tr_msg").val() == ""){
		alert("메시지를 입력하세요.");
		$("#tr_msg").focus();
		return;
	}
	
	//************************************************
	/*
	$("input[name=name_arr]").each(function(){
		
		if(cnt == 0){
			mber_name_arr = $(this).val();	
		}else{
			mber_name_arr += "," + $(this).val();								
		}
		cnt++;
		
		if($("input[name=name_arr]").length == cnt){
			cnt = 0;
		}
	})
	
	$("input[name=cell_arr]").each(function(){
		
		if(cnt == 0){
			mber_tel_arr = $(this).val();	
		}else{
			mber_tel_arr += "," + $(this).val();								
		}
		cnt++;
		
		if($("input[name=cell_arr]").length == cnt){
			cnt = 0;
		}
	})
	
	$("#mber_name").val(mber_name_arr);
	$("#mber_tel").val(mber_tel_arr);
	*/
	//************************************************
	
	if(confirm("발송하시겠습니까?")==true){
		
		$("#frm").attr("action", "<c:url value='/apage/member/insertSmsExcelList.do'/>");
		$("#frm").submit();
		
	}else{
		return;
	}		
}

function goSmsView(val,val2) {
	
	$("#frmDetail").attr("action", "<c:url value='/apage/member/smsExcelUploadDetail.do'/>");
	$("#seq").val(val);
	$("#sms_send_date_ym").val(val2);
	$("#frmDetail").submit();
}

//입력 가능한 바이트 체크
function fnChkByte(obj) {
	    
	var maxByte = 2000; //최대 입력 바이트
    var str = obj.value;
    var str_len = str.length;
 
    var rbyte = 0;
    var rlen = 0;
    var one_char = "";
    var str2 = "";
 
    for(var i = 0; i < str_len; i++) {
    	one_char = str.charAt(i);
 
        if(escape(one_char).length > 4) {
            rbyte += 2; //한글2Byte
        }else {
            rbyte++; //영문 등 나머지 1Byte
        }
 
        if(rbyte <= maxByte) {
            rlen = i + 1; //return할 문자열 갯수
        }
    }
 
    if(rbyte > maxByte) {
        alert("한글 " + (maxByte / 2) + "자 / 영문 " + maxByte + "자를 초과 입력할 수 없습니다.");
        str2 = str.substr(0, rlen); //문자열 자르기
        obj.value = str2;
        fnChkByte(obj, maxByte);
    } else {
        document.getElementById("byteInfo").innerText = rbyte;
    }
}
</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="seq" id="seq" />
	<input type="hidden" name="sms_send_date_ym" id="sms_send_date_ym" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="srch_date" value="<c:out value='${srch_date }'/>"/> <!-- id="srch_date" -->
	<input type="hidden" name="srch_date2" value="<c:out value='${srch_date2 }'/>"/>
	<input type="hidden" name="currRow" value="<c:out value='${currPage }'/>"/> <!-- id="srch_date2" -->
</form:form>

<div id="container">
	<h3>문자발송관리</h3>
		<div class="contents">
			<ul class="ct-tab">
				<li class="tab1"><a href="<c:url value='/apage/member/smsList.do'/>">일반발송</a></li>
                <li class="tab1"><a href="<c:url value='/apage/member/smsExcelUploadList.do'/>" class="on">엑셀 업로드 발송</a></li>
			</ul>
			<!-- //ct-tab -->
			
			<div class="bbs-write" style="margin-bottom: 20px; border: 1px solid #bbb;">
				- 정해진 양식의 엑셀 파일로 반드시 업로드하시기 바랍니다.<br>
				- 전화번호는 필수 입력사항입니다.<br>
				- 추가로 전화번호 입력이 필요할 때는 추가 / 삭제 버튼을 사용하세요.<br>
			</div>
			
			<form:form commandName="vo" id="frm" name="frm" method="post">
				<input type="hidden" name="msg_type" value="4"/>
				<input type="hidden" name="mber_name" id="mber_name"/>
				<input type="hidden" name="mber_tel" id="mber_tel"/>
				
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
				                	<a href="javascript:void(0);" onclick="Down('FILE_201901170239569820');" class="s-blue-btn" style="background-color: #1e7145; border: 1px solid #1e7145;">엑셀 양식 다운로드</a>	         
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
							<tr>
	                        	<th><span style="color:red;">*</span>메시지</th>
	                            <td>
									<textarea id="tr_msg" name="msg" onkeyup="fnChkByte(this);" style="width: 99%; height:150px;"></textarea>
									<span id="byteInfo">0</span><span style="margin-left: 2px;">Byte / 2000 Byte</span>
	                            </td>  
	                        </tr>                        
	                        <tr>
	                        	<td colspan="4" class="btn">
		                            <a href="javascript:sendSms();" class="btn-ty1 blue">발송</a>
		                            <a href="javascript:goInit();" class="btn-ty1 light">초기화</a>
	                            </td>
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
            
           		<!-- 엑셀 파일 내용 -->
            	<div class="bbs-write" style="overflow: scroll; height: 700px;">
					<table>
						<colgroup>
							<col width="20%">
							<col width="30%">
							<col width="50%">
						</colgroup> 
						<tbody>
							<tr class="sheetType" id="sheetType1">
								<th scope="col">추가 / 삭제</th>
								<th scope="col">이름</th>
								<th scope="col"><span style="color:red;">*</span>전화번호</th>
							</tr>
						</tbody>
						<tbody  id="excelDataForm">
						
						</tbody>
					</table>
				</div>
			</form:form>
            
			<!-- 리스트 -->
			<div class="bbs-head" style="margin-top: 30px;">
            	<form:form commandName="vo" method="post" id="messagefrm" name="messagefrm">
	            	<span>${msgCnt }건</span>
	            	<fieldset class="bbs-srch">
	                <!-- 
	                <span style="padding-top: 0px; font-weight: normal; width: 340px;">
	                	<label>발송일</label>
	               		<form:input path="srch_date" title="발송기간 시작일" class="w7p datepicker" maxlength="10" readonly="true" style="float: none; min-width: 100px;"/> ~ 
	               		<form:input path="srch_date2" title="발송기간 종료일" class="w7p datepicker" maxlength="10" readonly="true"  style="float: none; min-width: 100px;"/>
	                </span>
                	<legend>검색</legend>
					<label for="srch-word" class="hide">검색어입력</label>
						<form:input path="srch_input"/> <!-- onKeyPress="if (event.keyCode==13){ getSmsListJson(); return false;}" --
						<a href="javascript:goSearch();" class="s-blue-btn">검색</a>
					 -->
	                </fieldset>
	            </form:form>
            </div>
			<fieldset class="bbs-list">
				<legend>SMS목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup>
						<col width="5%" />
						<col width="*%" />
						<col width="20%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">No</th>
							<th scope="col">메시지</th>
							<th scope="col">발송일</th>
						</tr>
					</thead>
					<tbody id="smsList">
						<% pageContext.setAttribute("newLineChar", "\n"); %>
						<c:choose>
							<c:when test="${fn:length(smsHistory) > 0}">
								<c:forEach items="${smsHistory }" var="list" varStatus="status">
									<tr onclick="goSmsView('${list.etc3}','${list.reqdate_ym }')" style="cursor: pointer;">
										<%-- <td>${status.count }</td> --%>
										<td><c:out value='${totalNum-(status.index + (10*(currPage-1)))}'/></td>
										<td class="al_l">${fn:replace(list.msg, newLineChar, "<br/>")}</td>
										<td>${list.reqdate }</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
				                    <td colspan="4" class="al_c">조회 결과가 없습니다.</td>
				                </tr>
							</c:otherwise>
						</c:choose>
						<!-- 
						<c:choose>
							<c:when test="${fn:length(smsList) > 0}">
								<c:forEach items="${smsList }" var="list" varStatus="status">
									<tr onclick="goSmsView('${list.seq}','${list.sms_send_date_ym }')" style="cursor: pointer;">
										<td>${(totalNum+1)-list.ascnum }</td>
										<td class="al_l">${fn:replace(list.msg_content, newLineChar, "<br/>")}</td>
										<td>${list.send_date }</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
				                    <td colspan="4" class="al_c">조회 결과가 없습니다.</td>
				                </tr>
							</c:otherwise>
						</c:choose>
						 -->
					</tbody>
				</table>
			</fieldset>
			<!-- //bbs-list -->
			<div class="pg-nav">
			    ${pageNav }
				<!-- //paging -->
			</div>
			<!-- //pg-nav -->
		</div>
		<!-- //contents -->
	</div>

    
<jsp:include page="/apage/inc/footer.do"></jsp:include>