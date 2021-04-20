<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
function getQuerystring(paramName){
    var _tempUrl = window.location.search.substring(1); //url에서 처음부터 '?'까지 삭제
    var _tempArray = _tempUrl.split('&'); // '&'을 기준으로 분리하기
    for(var i = 0; i<_tempArray.length; i++) {
        var _keyValuePair = _tempArray[i].split('='); // '=' 을 기준으로 분리하기
        if(_keyValuePair[0] == paramName){ // _keyValuePair[0] : 파라미터 명 // _keyValuePair[1] : 파라미터 값
            return _keyValuePair[1];
        }
    }
}

$(document).ready(function () {
	var $p_msg = getQuerystring("msg");
	if($p_msg != null && $p_msg == 'duplicate.success'){
		alert("중복 신청자가 존재합니다.\n중복 신청자를 제외한 데이터는 등록이 완료되었습니다.");
		return;
	}
	
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/apage/event/kokContestMngList.do'/>";
	
	
/* 
	$(".s-blue-btn").click(function(){
		
		var html = "";
		var ct_seq = $(this).attr("data-seq");
		var prepare_yn = $(this).attr("data-prepareYn");
		var laneCnt = $("#laneCnt").val();
		
		if(confirm("자리배치 하시겠습니까?") == true){
			
			$.ajax({
				type		: "POST",
				url			: "/apage/event/createRandomLane.do",
				data		: {
								ct_seq : ct_seq,
								laneCnt : laneCnt,
								prepare_yn : prepare_yn
							},
				cache		: false,
				dataType	: 'json',
				success		: function(data) {
					$("#ct_seq").val(ct_seq);
					$("#autoLaneResult").val(html);
					
					$("#frmDetail").attr("action","/apage/event/kokContestInsertAutoLane.do");
					$("#frmDetail").submit();
					
					
				},beforeSend		:function(){
		        	//(이미지 보여주기 처리)
			        $('.wrap-loading').removeClass('display-none');
			    	},complete			:function(){
			          //(이미지 감추기 처리)
		            $('.wrap-loading').addClass('display-none');	    
		            alert('자리배치가 완료 되었습니다. 자동배치결과 페이지로 이동합니다.');
			        },error		: function(data) {
								//alert('에러가 발생했습니다.');
								getAppListJson(currPart);
					},timeout:100000
			});
			
			
		}else{
			return;
		}
	});
 */
 
 
});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/apage/event/kokContestMngList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/apage/event/kokContestMngDetail.do'/>");
	$("#ct_seq").val(val);
	$("#frmDetail").submit();
}


 function goRandomSelect(ct_seq){

 	//if(confirm("입금완료된 신청자 대상으로 추첨합니다.\n정원 미달시는 입금완료 대상에서 부족인원만 추첨합니다.\n현재 정원은 " +limitCnt+ "명입니다.\n랜덤추첨 하시겠습니까? ") == true){
 	if(confirm("입금완료된 신청자 대상으로 추첨합니다.\n정원 미달시는 입금완료 대상에서 부족인원만 추첨합니다.\n랜덤추첨 하시겠습니까? ") == true){
 		
 		$.ajax({
 				type		: "POST",
 				url			: "/apage/contest/createRandomSelect.do",
 				data		: {
 											ct_seq : ct_seq
 										},
 				cache		: false,
 				dataType	: 'json',
 				success		: function(data) {
 					//console.log(data);
 	
 				$("#ct_seq").val(ct_seq);
 				//$("#frmDetail").attr("action","/apage/contest/adminContestPlayerMng.do");
 				$("#frmDetail").attr("action","/apage/contest/adminContestSelectResultDetail.do");
 				$("#frmDetail").submit();
 				
 			},beforeSend		:function(){
 	        	//(이미지 보여주기 처리)
 		        $('.wrap-loading').removeClass('display-none');
 		    	},complete			:function(){
 		    		  $('.wrap-loading').addClass('display-none');	    
 			            alert('랜덤 추첨이 완료 되었습니다. 선정결과 페이지로 이동합니다.');
 		        },error		: function(data) {
 					alert('에러가 발생했습니다.');
 					getAppListJson(currPart);
 				},timeout:100000
 		});
 		
 		
 	}else{
 		return;
 	}
 	
 	$("#frmDetail").attr("action", "<c:url value='/apage/contest/adminContestPlayerMng.do'/>");
 	$("#ct_seq").val(val);
 	$("#frmDetail").submit();
 }

function goReg(){
	location.href = "<c:url value='/apage/event/kokContestMngWrite.do'/>";
}

</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ct_seq" id="ct_seq" />
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	<input type="hidden" name="autoLaneResult" id="autoLaneResult" />
</form:form>


	<div id="container">
		<h3>왕중왕전 대회 신청관리</h3>
		<div class="contents">
        	<ul class="ct-tab">
				<li class="tab1"><a href="<c:url value='/apage/event/kokContestList.do'/>" class="on">왕중왕전대회</a></li>
				<li class="tab1"><a href="<c:url value='/apage/event/eventContestList.do'/>">이벤트대회</a></li>
			</ul>
			<!-- //ct-tab -->
			<ul class="ct-tab">
				<li><a href="/apage/event/kokContestList.do" class="" id="part_firBtn">대회안내</a></li>
				<li><a href="/apage/event/kokContestMngList.do" class="on">대회신청관리</a></li>
				<li><a href="/apage/event/kokContestLaneList.do" class="">자리배치결과</a></li>
			</ul>
        
        
        	<div class="bbs-head">
            	<%-- <p class="bbs-total">총 <strong>${totalNum }</strong> 명</p> --%>
            	
            	<form:form commandName="vo" method="post" id="frm" name="frm">
            		<!-- 
	            	<font color="#003CFF">※ 자동배치할 레인 수 </font>
            		<input type="text" style="width: 35px;" id="laneCnt" name="laneCnt" value="3"/> 
            		-->
	                <fieldset class="bbs-srch">
	                	<legend>게시글 검색</legend>
						<label for="srch-word" class="hide">검색어입력</label>						
						<form:input path="srch_input" onKeyPress="if (event.keyCode==13){ searchChk(); return false;}"/>
						<input type="submit" value="검색" />
	                </fieldset>
	             </form:form>
            </div>
        	
			<fieldset class="bbs-list">
				<legend>대회신청관리 목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup>
						<col width="4%" />
						<col width="25%" />
						<%-- <col width="6%" /> 
						<col width="6%" />						
						--%>
						<col width="13%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="6%" />
						<col width="4%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">제목</th>
							<!-- 
							<th scope="col">랜덤추첨</th> 
							<th scope="col">자리배치</th>
							-->
							<th scope="col">대회일자</th>							
							<th scope="col">장소</th>
							<th scope="col">진행상태</th>
							<th scope="col">신청자수</th>
							<th scope="col">선정현황</th>
							<th scope="col">등록일</th>                          
						</tr>
					</thead>
					<tbody>
						<c:choose>
		            		<c:when test="${fn:length(contestList) > 0}">
		            			<c:forEach items="${contestList }" var="list" varStatus="status">
		            				<tr>
										<th scope="row"><c:out value='${totalNum-(status.index + (10*(currPage-1)))}'/></th>
			                            <td class="al_l">
			                            	<a href="javascript:void(0);" onclick="goBoardView('${list.ct_seq}');"><c:out value='${list.ct_sbj }'/></a> 			                            	
			                            </td>
			                            <%-- 
			                            <td class="al_c"><c:if test="${list.ct_process eq 'E'}"> <a href="javascript:void(0);" data-seq="${list.ct_seq}" data-prepareYn="${list.prepare_yn }"class="btnAutoLane s-blue-btn">자리배치</a></c:if></td>
			                             --%>
			                            <td class="al_c"><c:out value='${list.ct_dt }'/></td>
			                            <td class="al_c"><c:out value='${list.ct_place }'/></td>
			                            <td class="al_c">
			                            	<c:if test="${list.ct_process eq 'R' }">대회준비</c:if>
			                            	<c:if test="${list.ct_process eq 'S' }">대회신청</c:if>
			                            	<c:if test="${list.ct_process eq 'E' }">신청마감</c:if>
			                            	<c:if test="${list.ct_process eq 'F' }">대회종료</c:if>
			                            </td>
			                            <td><c:out value='${list.app_cnt }'/>명</td>
			                            <td><c:out value='${list.part1_app_cnt }'/>/<c:out value='${list.limit_part * 2}'/></td>
			                            <td><c:out value='${list.reg_dt }'/></td>
									</tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
				                <tr>
				                    <td colspan="8">조회된 결과가 없습니다.</td>
				                </tr>
				            </c:otherwise>
				        </c:choose>
					</tbody>
				</table>
			</fieldset>
			<!-- //bbs-list -->
			<div class="pg-nav">
            	<p class="btn"><a href="javascript:goReg();" class="btn-ty1 blue">등록</a></p>
            	${pageNav }
				<!-- //paging -->
			</div>
			<!-- //pg-nav -->
		</div>
		<!-- //contents -->
	</div>

		
<jsp:include page="/apage/inc/footer.do"></jsp:include>