<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>

<c:import url="/client/header.do" />


<c:if test="${contestView.ct_seq != '10000026' && contestView.ct_seq != '10000027'}">
	<style type="text/css">
		.colorOpt{ display: none; }
	</style>
</c:if>

<style type="text/css">
#addrSearchBtn{
	display: inline-block;
    background: #545454;
    color: #fff;
    padding: 0px 10px;
    font-size: 13px;
    height: 36px;
    line-height: 36px;
}

#zipcode{
	width: 15%;
}

#addr, #addr_detail{
	margin-bottom: 5px;
}

.opt_title {
	padding: 5px;
	font-weight: bold;
}
.opt_wrap {
	padding: 5px;
}
.opt_wrap > label {
	margin-right: 10px;
} 
</style>
<script type="text/javascript">

var gender;
var disable;
var birth_y;
var birth_m;
var birth_d;
var age;

//신청 취소
function goCancel() {
	var refundFlag = false;
	$.ajax({
		url		:	"<c:url value='/membership/myEventContestAppCancelCheckYn.do'/>",
		data	:	{
			ct_seq : "${contestView.ct_seq }"
		},
		type	:	"post",
		async: false,
		success	:	function(data){
			if(data.root.ResultCode == "Y"){
				refundFlag = true;
			}else{
				alert("신청취소가 불가능한 기간입니다.");
				refundFlag = false;
				return;
			}
		}
	});
	
	if(!refundFlag) return;
	
	if(!confirm("정말 취소하시겠습니까?")){
		return;
	}
	$("#frmCancel").attr("action", "<c:url value='/membership/myEventContestAppCancel_shop.do'/>");
	$("#frmCancel").submit();
	
}

function goBoardList(){
	$("#frm").attr("action", "<c:url value='/membership/myContestAppList.do'/>");
	$("#frm").submit();
}



function goAppInfoUpdt(){
	
	var telno = "";
	
	if($("#telno1").val() == ""){
		alert("연락처를 입력하세요.");
		$("#telno1").focus();
		return;
	}
	if($("#telno2").val() == ""){
		alert("연락처를 입력하세요.");
		$("#telno2").focus();
		return;
	}
	if($("#telno3").val() == ""){
		alert("연락처를 입력하세요.");
		$("#telno3").focus();
		return;
	}
	telno = $("#telno1").val()+"-"+$("#telno2").val()+"-"+$("#telno3").val();
	var telnoRegExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
	if(!telnoRegExp.test(telno)){
		alert("잘못된 휴대폰 번호입니다. 각 항목에 숫자또는 자리수에 맞게 입력해주세요.");
		return;
	}

	
	$("#telno").val(telno);
	
	if($("#gender").val() == ""){
		alert("성별을 선택하세요.");
		$("#gender").focus();
		return;
	}
	
	
	if($("#option1").val() == ""){
		alert("파운드를 선택하세요.");
		$("#option1").focus();
		return;
	}
	
	
	$("#frm").attr("action","<c:url value='/membership/myEventContestAppUpdate.do'/>");
	$("#frm").submit();
}

function chgDomain(val){
	$("#email2").val(val);
}



//주소 팝업
function addr_pop() {
	// 주소검색을 수행할 팝업 페이지를 호출
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출
	var pop = window.open("${contextPath}/address/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}

//팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
	$("#zipcode").val(zipNo);
	$("#addr").val(roadAddrPart1);
	$("#addr_detail").val(addrDetail);	
}

</script>

<form:form id="frmCancel" name="frmCancel" commandName="vo">
	<input type="hidden" name="ct_seq" id="ct_seq" value="${contestView.ct_seq }" />
	<input type="hidden" name="app_seq" id="app_seq" value="${contestView.app_seq }" />
	<input type="hidden" name="part" id="part" value="${contestView.part }"/>
	<input type="hidden" name="lane" id="lane" value="${contestView.lane }"/>
	<input type="hidden" name="status" id="status" value="${contestView.status }"/>
	<input type="hidden" name="updt_id" id="updt_id" value="${mber_id }" />
	<input type="hidden" name="pay_flag" value="${contestView.pay_flag }" />
	<input type="hidden" name="telno" value="${contestView.telno }" />
	<input type="hidden" name="join_name" value="${contestView.join_name }" />
	<input type="hidden" name="part_ord" value="${contestView.part_ord }">
	<input type="hidden" name="ct_type" value="${contestView.ct_type }">
</form:form> 

<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
	
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
			<h2 class="hide">본문</h2>
		    <c:import url="/client/snb.do" />                       
		        
	        <div class="sub_content">
	        	<div class="inner">
		        	<div class="sub_head">
		            	<h3 class="c_tit">이벤트 신청내역</h3>
		            </div>
		            
	        
			        <div class="board_view_area sub_fade">
                   		<div class="bbs_view">
			                <div class="view_head">
			                    <dl class="subject">
			                        <dt><c:out value='${svo.ct_sbj }'/></dt>
			                        <dd class="info"><span><strong><c:out value='${svo.reg_id }'/></strong></span><span><c:out value='${svo.reg_dt }'/></span><span><c:out value='${svo.hit }'/></span></dd>
			                    </dl> 
			                </div>
			                <!--VIEW_HEAD// E-->
			            </div>    
			            
						<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
						<input type="hidden" name="app_seq" value="<c:out value='${contestView.app_seq }'/>" />
						<p style="text-align: right;">
	    					<span class="ft_or">* </span>
	   						<span >는 필수입력 항목입니다.</span>
						</p>
                        <div class="bbs_write">
                            <div class="tbl_w">
                                <table summary="게시판 글쓰기 표로 제목, 구분, 내용,첨부파일 등이 있습니다.">
                                    <caption>게시판 작성</caption>
                                    <colgroup>
                                        <col width="15%">
                                        <col width="35%">
                                        <col width="15%">
                                        <col width="35%">
                                    </colgroup>
                                    <tbody>
                                        <tr <c:if test="${contestView.ct_type ne 'L' }">style="display: none;"</c:if>>
                                            <th scope="col"><span class="ft_or">* </span><label for="part">그룹(조)</label></th>
                                            <td colspan="3">
                                            	<input type="hidden" name="part" id="part" value="${contestView.part }">${contestView.part_ord }조 ${contestView.part_name }
                                            	<input type="hidden" name="part_ord" value="${contestView.part_ord }">
                                            	<input type="hidden" name="ct_type" value="${contestView.ct_type }">
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="col"><label for="reg_id">아이디</label></th>
                                            <td>
                                            	${contestView.reg_id }
                                            </td>
                                       	    <th scope="col"><label for="join_name">성명</label></th>
                                            <td>
                                            	${contestView.join_name }
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="col"><span class="ft_or">* </span><label for="telno">연락처</label></th>
                                            <td>
                                            	<input type="text" name="telno1" id="telno1" class="w90" maxlength="3" pattern="\d*" placeholder="" value="${tel_arr0 }"/><span class="insert">-</span>
                                            	<input type="text" name="telno2" id="telno2" class="w90" maxlength="4" pattern="\d*" placeholder="" value="${tel_arr1 }"/><span class="insert">-</span>
                                            	<input type="text" name="telno3" id="telno3" class="w90" maxlength="4" pattern="\d*" placeholder="" value="${tel_arr2 }"/> 
                                            	<input type="hidden" name="telno" id="telno"/>  
                                            </td>
                                            <th scope="col"><span class="ft_or">* </span><label for="gender">성별</label></th>
                                            <td>
                                           		<c:if test="${contestView.gender eq 'M' }">남</c:if>
												<c:if test="${contestView.gender eq 'F' }">여</c:if>
                                            </td>
                                        </tr>
                                          <tr>
	                                        <th scope="col" rowspan="2"><span class="ft_or">* </span><label for="zip1">주소</label></th>
	                                        <td colspan="3">
	                                            <input type="text" id="zipcode" name="zipcode" class="type-text required" title="우편번호 " value="${contestView.zipcode }" readonly="readonly"> 
	                                            <a href="javascript:addr_pop();" id="addrSearchBtn">우편번호 검색</a>
	                                        </td>
	                                    </tr>
	                                    <tr>
	                                        <td colspan="3">
	                                        	<input type="text" id="addr" name="addr" class="type-text required w98p" title="상세주소" value="${contestView.addr }">
	                                        	<input type="text" id="addr_detail" name="addr_detail" class="type-text required w98p" title="상세주소" value="${contestView.addr_detail }">
	                                        </td>
	                                    </tr>
                                    	<tr>
	                                        <th scope="col"><span class="ft_or">* </span><label for="option1">파운드</label></th>
	                                        <td colspan="3">	
                                               	<select id="option1" name="option1" class="wd50p">
                                                    <option value="">::파운드 선택::</option>
                                                    <option value="13" <c:if test="${contestView.option1 eq '13'}">selected="selected"</c:if>>13</option>
                                                    <option value="14" <c:if test="${contestView.option1 eq '14'}">selected="selected"</c:if>>14</option>
                                                    <option value="15" <c:if test="${contestView.option1 eq '15'}">selected="selected"</c:if>>15</option>
												</select>
	                                        </td>
	                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
			            <!--TBL_VIEW //E -->
			            <div class="btn_r2">
				            <c:if test="${contestView.status ne '0003'}">
				            	<a href="javascript:goCancel()" class="gray">신청취소</a>
				            <%-- 
				            	<c:if test="${contestView.ct_process == 'E' }">
					            	<a href="javascript:goCancel()" class="gray">신청취소</a>
				            	</c:if>
				             --%>	
					            <c:if test="${contestView.updt_yn == 'Y'}">
				            		<a href="javascript:goAppInfoUpdt();" class="gray">수정</a>
				            	</c:if>
				            </c:if>
			                <a href="javascript:goBoardList();" class="">목록</a>
			            </div>     
			        </div>
			        <!--BOARD_VIEW_AREA //E-->
	    		</div>
	   		</div>
		</div>
		<!-- #CONTENTS //E -->		
	</div> 
	<!-- #CONTAINER //E -->		
</form:form>

<jsp:include page="/client/footer.do"></jsp:include>