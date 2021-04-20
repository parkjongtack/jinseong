<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<style type="text/css">
#pop-ebk{
	/*top:500px !important;*/
	top: 200px !important; 
	left: 1100px !important;
	width: 682px !important;
} 
#pop-ebk input[type="text"] {
    width: 270px !important;
}
#pop-ebk .edite{
	height: 500px !important;
	overflow: scroll;
}
</style>

<script type="text/javascript">
$(document).ready(function () {
	/* 
	pagingUtil2.__form			= $("#frm");
	pagingUtil2.__func			= 'getSmsListJson';
	*/
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/apage/member/smsList.do'/>";
	
	//발송유형 별 작성 양식 노출
	$(".msg_type").click(function(){
		
		if($(this).val() == "3"){
			$(".contestSms").hide();
			$(".selectSmsOnly").show();
		}else if($(this).val() == "2"){
			$(".contestSms").hide();
			$(".selectSmsOnly").hide();
		}else{
			$(".contestSms").show();
			$(".selectSmsOnly").hide();
		}
	});
	
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

//검색
function goSearch() {
	
	$("#messagefrm").attr("action", "<c:url value='/apage/member/smsList.do'/>");
	$("#messagefrm").submit();
}

//회원선택 팝업창 열기
function findMber(){
	$("#pop-ebk").css('display','block');
	//getMberList();
}

//회원선택 팝업창 - 검색
function getMberList(){
 	
	var dataString = $("#searchMberForm").serialize();
	
	$.ajax({
		type		: "POST",
		url			: "/apage/member/smsMberList.do",	
		data		: dataString,
		cache		: false,
		dataType	: 'json',
		beforeSend: function (xhr) {
			xhr.setRequestHeader('AJAX', true);
		},
		success		: function(data) {
			
			if(data.root.resultCode == "Y"){
				var aList = data.root.resultList;
				
				$("#mberList").empty();
				var html = '';
				
				for(var i = 0; i < aList.length; i++){
					
					html += '<tr>';
					//체크박스
					html += '<td class="al_c">';
					html += '<input type="checkbox" id="chkMber" class="chkMber" value="'+aList[i].mber_tel+':'+aList[i].mber_name+':'+aList[i].mber_id+'">';
					html += '</td>';
					
					//NO
					html += '<td class="al_c">';
					html += (i+1);
					html += '</td>';
											
					//아이디
					html += '<td class="al_c">';
					html += aList[i].mber_id;
					html += '<input type="hidden" name="mber_id" value="'+aList[i].mber_id+'">';
					html += '</td>';
				
					//이름
					html += '<td class="al_c">';
					html += aList[i].mber_name;
					html += '<input type="hidden" name="mber_name" value="'+aList[i].mber_name+'">';
					html += '</td>';
					
					//휴대폰 번호
					html += '<td class="al_c">';
					html += aList[i].mber_tel;
					html += '<input type="hidden" name="mber_tel" value="'+aList[i].mber_tel+'">';
					html += '</td>';
					
					html += '</tr>';
				}
				
				$("#mberList").append(html);
				
			}else{
			
				$("#mberList").empty();
				var html = '<tr><td colspan="5" class="al_c">회원이 존재하지 않습니다.</td></tr>';
				$("#mberList").append(html);
			}
		},
		error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		} 
	});
}

//회원선택 팝업창 - 수신번호 선택
$(document).on("click", "#chkAll", function(){
	if($(this).is(":checked")){
		$(".chkMber").each(function(){
			$(this).attr("checked", true);
		});
	}else{
		$(".chkMber").each(function(){
			$(this).attr("checked", false);
		});
	}
});

//회원선택 팝업창 - 선택한 수신번호 넣기
function selectMber(){
	
	var tel_no = null;
	var name = null;
	var id = null;
	var cnt = 0;
	$(".chkMber").each(function(index){
		if($(this).is(":checked")){
			if(cnt == 0){
				var mArray = $(this).val().split(":");
				tel_no = mArray[0];
				name = mArray[1];
				id = mArray[2];
			}else{
				var mArray = $(this).val().split(":");
				tel_no += "," + mArray[0];
				name += "," + mArray[1];
				id += "," + mArray[2];
			}
			cnt++;
		}
	})
	
	$("#tr_phone").val(tel_no);
	$("#tr_name").val(name);
	$("#tr_id").val(id);
	
	goPopupClose();
}

//회원선택 팝업창 닫기
function goPopupClose(){
	$("#pop-ebk").css('display','none');
}

//초기화
function goInit(){
	$('#frm').find('input,select,textarea').each(function(){
		$(this).val('');
	});
}

//SMS 발송
function sendSms(){
	
	var msg_type = $("input[name='msg_type']:checked").val();
	if(msg_type == "1" && $("#contestlengthCheck").val() == 0){
		alert("종료된 대회가 존재하지 않습니다.");
		return;
	}
	if(msg_type == "3"){ //선택발송
		if($("#tr_phone").val() == ""){
			alert("수신번호를 선택하세요.");
			return;
		}
	}
	if($("#tr_msg").val() == ""){
		alert("메시지를 입력하세요.");
		$("#tr_msg").focus();
		return;
	}
	
	
	if(confirm("발송하시겠습니까?")==true){
		
		$("#frm").attr("action", "<c:url value='/apage/member/insertSmsList.do'/>");
		$("#frm").submit();
		/*
		var dataString = $("#frm").serialize();	
		//alert(dataString);
		//return;		
		$.ajax({
			url			: '/apage/member/insertSmsList.do', 
			type 		: 'post', 
			data 		: dataString,
			dataType 	: 'json', 
			cache : false,
			success : function(data) {			
				location.reload();				
			},
			error : function(a, b, c) {
				alert("알수없는 예외가 발생했습니다.");
				return;
			}
		});	
		*/
	}else{
		return;
	}		
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

function goSmsView(val,val2) {
	
	$("#frmDetail").attr("action", "<c:url value='/apage/member/smsDetail.do'/>");
	$("#seq").val(val);
	$("#sms_send_date_ym").val(val2);
	$("#frmDetail").submit();
}
</script>

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
				<li class="tab1"><a href="<c:url value='/apage/member/smsList.do'/>" class="on">일반발송</a></li>
                <li class="tab1"><a href="<c:url value='/apage/member/smsExcelUploadList.do'/>">엑셀 업로드 발송</a></li>
			</ul>
			<!-- //ct-tab -->
			
			<div class="bbs-select">
               	<form:form commandName="vo" id="frm" name="frm" method="post">
					<!-- <input type="hidden" name="tr_sendstat" value="1"> --> <!-- 0:발송대기, 1:전송완료, 2:결과수신완료 -->
					<!-- <input type="hidden" name="tr_msgtype" value="0"> --> <!-- 0:일반메세지, 1:콜백URL메세지 -->
					<!-- <input type="hidden" name="tr_callback" value=""> -->
					<input type="hidden" id="contestlengthCheck" value="${fn:length(contestList)}">
										
					<table summary="" id="smsArea">
	                	<colgroup>
	                		<col width="10%">
	                		<col width="55%">
	                		<col width="10%">
	                		<col width="25%">
	                	</colgroup>
	                	<tbody>
	                		<tr>
	                        	<th>발송유형</th>
	                            <td colspan="3">
									<input type="radio" name="msg_type" value="1" class="msg_type" checked="checked" />대회발송&nbsp;&nbsp;&nbsp;
									<input type="radio" name="msg_type" value="2" class="msg_type" />전체발송&nbsp;&nbsp;&nbsp;
									<input type="radio" name="msg_type" value="3" class="msg_type" />선택발송
	                            </td>                                                       
	                        </tr>
	                        <tr class="contestSms">
	                        	<th>대회목록</th>
	                        	<td>
                        			<c:choose>
							        	<c:when test="${fn:length(contestList) > 0}">
							            	<select name="ct_seq">
								            	<c:forEach items="${contestList }" var="list" varStatus="status">
								            		<option value="${list.ct_seq }">${list.ct_sbj }</option>
								            	</c:forEach>
							            	</select>
							            </c:when>
							            <c:otherwise>
							            	종료된 대회가 존재하지 않습니다.
							            </c:otherwise>
							        </c:choose>
	                        	</td>
	                        </tr>
	                        <tr class="selectSmsOnly" style="display: none;">
	                        	<th>회원정보</th>
	                            <td>
	                            	<p style="margin-bottom: 5px;">
		                            	<a href="javascript:findMber();" class="s-blue-btn">회원선택</a>	        
		                            </p>
	                            </td>  
	                        </tr>
	                        <tr class="selectSmsOnly" style="display: none;">
	                        	<th>수신자명</th>
	                            <td><input type="text" id="tr_name" name="tr_name" style="width: 99%;"/></td> <!-- class="w90p" -->  
	                        </tr>
	                        <tr class="selectSmsOnly" style="display: none;">
	                        	<th>아이디</th>
	                            <td><input type="text" id="tr_id" name="tr_id" style="width: 99%;" readonly="readonly"/></td> <!-- class="w90p" -->  
	                        </tr>
	                        <tr class="selectSmsOnly" style="display: none;">
	                        	<th>수신번호</th>
	                            <td><input type="text" id="tr_phone" name="tr_phone" style="width: 99%;"/></td> <!-- class="w90p" -->  
	                        </tr>
	                        <tr>
	                        	<th>메시지</th>
	                            <td>
									<textarea id="tr_msg" name="msg" onkeyup="fnChkByte(this);" style="width: 99%; height:150px;"></textarea>
									<span id="byteInfo">0</span><span style="margin-left: 2px;">Byte / 2000 Byte</span>
									<p style="color: red;">※90 Byte 이하는 SMS로 발송되며, 90 Byte 이상은 MMS로 발송됩니다.</p>
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
                </form:form>
            </div>

			<div class="bbs-head">
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
						<col width="20%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">No</th>
							<th scope="col">메시지</th>
							<th scope="col">발송유형</th>
							<th scope="col">발송일</th>
						</tr>
					</thead>
					<tbody id="smsList">
						<% pageContext.setAttribute("newLineChar", "\n"); %>
						<c:choose>
							<c:when test="${fn:length(smsHistory) > 0}">
								<c:forEach items="${smsHistory }" var="list" varStatus="status">
									<tr onclick="goSmsView('${list.etc3}','${list.reqdate_ym }')" style="cursor: pointer;">
										<td>${status.count }</td>
										<td class="al_l">${fn:replace(list.msg, newLineChar, "<br/>")}</td>
										<td>
											<c:if test="${list.etc10 eq 'endContestMember' }">대회발송</c:if>
											<c:if test="${list.etc10 eq 'allMember' }">전체발송</c:if>
											<c:if test="${list.etc10 eq 'selectMember' }">선택발송</c:if>
										</td>
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
									<tr onclick="goSmsView('${list.seq}','${sms_send_date_ym }')" style="cursor: pointer;">
										<td>${(totalNum+1)-list.ascnum }</td>
										<td class="al_l">${list.msg_content }</td>
										<td>
											<c:if test="${list.msg_type eq '1' }">대회발송</c:if>
											<c:if test="${list.msg_type eq '2' }">전체발송</c:if>
											<c:if test="${list.msg_type eq '3' }">선택발송</c:if>
										</td>
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





	<!-- 수신번호선택 팝업창 -->
	<div id="pop-ebk" style="display: none;">
        <h5>수신번호선택</h5>
        <div class="edite">
            <div class="inner-srch mb20">
            	<h6>회원검색</h6>
            	<form id="searchMberForm" name="searchMberForm" method="post">
	                <table summary="">
	                    <tbody>
	                        <tr>		                            
	                            <td class="al_c">
	                            	<select id="scType" name="scType" title="시도조건선택">
				            			<option value="">::전체::</option>
				            			<option value="mber_id">아이디</option>
				            			<option value="mber_name">성명</option>
	                            	</select>
	                            	<input type="text" id="srch_input" name="srch_input" class="w25p" onkeypress="if (event.keyCode==13){ getMberList(); return false;}">
	                            	<a href="javascript:getMberList();" class="btn-ty2 blue">검색</a>
	                            </td>
	                        </tr>
	                    </tbody>
	                </table>
            	</form>
			</div>
            <div class="inner-write mb20">
				<table summary="회원검색결과" style="border-top: 1px solid #bbb">
					<colgroup>
						<col width="5%" />
						<col width="5%" />
						<col width="25%" />
						<col width="20%" />
						<col width="*%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" id="chkAll" /></th>
							<th scope="col">No</th>
							<th scope="col">아이디</th>
							<th scope="col">성명</th>
							<th scope="col">연락처</th>
						</tr>
					</thead>
					<tbody id="mberList">
						<c:choose>
							<c:when test="${fn:length(smsAgreeList) > 0}">
								<c:forEach items="${smsAgreeList }" var="list" varStatus="status">
									<tr>
										<td class="al_c"><input type="checkbox" id="chkMber" class="chkMber" value="${list.mber_tel }:${list.mber_name }:${list.mber_id }"></td>
										<td class="al_c">${status.count }</td>
										<td class="al_c">${list.mber_id }</td>
										<td class="al_c">${list.mber_name }</td>
										<td class="al_c">${list.mber_tel }</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
				                    <td colspan="5" class="al_c">조회 결과가 없습니다.</td>
				                </tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>		                
            </div>
            <!-- //bbs-write-->
            <span class="fr" style="position: absolute; bottom: 20px; right: 20px;">
            	 <a href="javascript:goPopupClose();" target="_blank" class="btn-ty2 blue">취소</a>
                 <a href="javascript:selectMber();" class="btn-ty2 blue">확인</a>
            </span>
        </div>
        <!-- //edit-->
        <p class="close"><a href="javascript:goPopupClose()">X</a></p>
    </div>
    
    
    
<jsp:include page="/apage/inc/footer.do"></jsp:include>