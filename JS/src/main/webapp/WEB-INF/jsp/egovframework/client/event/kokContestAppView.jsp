<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>

<c:import url="/client/header.do" />

<script type="text/javascript">

function goBoardList(){
	$("#frm").attr("action", "<c:url value='/event/kokContestAppList.do'/>");
	$("#frm").submit();
}


function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}

function goApp(){
	$("#frm").attr("action","/event/kokContestAppWrite.do");
	$("#frm").submit();
}

function chkApp(){
	
	if(isLogin == false){
		alert('로그인 후 이용이 가능합니다. 로그인 페이지로 이동하시겠습니까?');
		//location.href = '/membership/login.do';
		$("#returnUrl").val("kokContestAppWrite");
		$("#frm").attr("action","/membership/login.do");
		$("#frm").submit();
		return;
	}
	/*
	if($("#ct_process").val() != "S"){
		alert("신청기간이 아닙니다.");
		return;
	}
	*/
	var ct_seq = $("#ct_seq").val();
	var app_flag =  false;
	$.ajax({
		type : "POST",
		url : "/event/appPermissionCheckJson.do",
		data : {			
					ct_seq : ct_seq
		},
		cache : false,
		dataType : 'json',
		async: false,
		success : function(data) {
			if(data.root.ResultCode == "Y"){
				app_flag = true;
			}else if(data.root.ResultCode == "N"){
				alert("신청 가능 시간이 아닙니다.");
				return;
			}else{
				alert("오류가 발생하였습니다.\n잠시 후 시도해주세요.");
				return;
			}
		},
		error : function(data, status, err) {
			alert("에러가 발생하였습니다.");
			return;
		}
	});		
	
	if(app_flag){
		$.ajax({
			type : "POST",
			url : "/event/chkMberAppJson.do",
			data : {
						ct_seq : ct_seq
			},
			cache : false,
			dataType : 'json',
			async: false,
			success : function(data) {
				if(data.root.resultCode == 'D'){
					alert("이미 신청한 아이디 입니다.");
					return;
				} else if(data.root.resultCode == 'C'){
					alert("로그인한 신청자 정보가 대회 참가자 명단에 존재하지 않습니다.");
					return;
				} else if(data.root.resultCode == 'N'){
					alert("대회 정보가 존재하지 않습니다.");
					return;
				} else{
					goApp();
				}
			},
			error : function(data, status, err) {
				alert("에러가 발생하였습니다.");
				return;
			}
		});
	}
}

</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>

<form:form commandName="vo" name="frm" id="frm" >
		<input type="hidden" name="ct_seq" id="ct_seq" value="<c:out value='${contestView.ct_seq }'/>" />
		<input type="hidden" name="ct_process" id="ct_process" value="<c:out value='${contestView.ct_process }'/>" />
		<input type="hidden" name="limit_part" id="limit_part" value="<c:out value='${contestView.limit_part }'/>" />
		<input type="hidden" name="atch_file_id" value="<c:out value='${contestView.atch_file_id }'/>" />
		<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
		<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
		<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
		<input type="hidden" name="returnUrl" id="returnUrl" value="" />
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
	        <h2 class="hide">본문</h2>
	        <c:import url="/client/snb.do" />                       
	        <div class="sub_content">
	            <div class="inner">
		            <div class="sub_head">
		                <h3 class="c_tit">왕중왕전 대회 신청</h3>
		            </div>    
			        <div class="board_view_area sub_fade">
			            <div class="bbs_view">
			                <div class="view_head">
			                    <dl class="subject">
			                        <dt><c:out value='${contestView.ct_sbj }'/></dt>
			                        <dd class="info"><span><strong><c:out value='${contestView.reg_id }'/></strong></span><span><c:out value='${contestView.reg_dt }'/></span></dd>
			                    </dl>
			                    <dl class="file">
			                       <span class="t_tit">신청기간 : </span> 
			                       <a style="text-decoration: none;"><c:out value='${contestView.app_start_dt } ${contestView.app_start_h}시 ${contestView.app_start_m  }분 ~ ${contestView.app_end_dt } ${contestView.app_end_h}시 ${contestView.app_end_m  }분 '/></a>
			                    </dl>
			                       <c:choose>
										<c:when test="${fn:length(contestFile) > 0}">
						                    <dl class="file">
						                       <span class="t_tit">첨부파일 : </span> 
												<c:forEach items="${contestFile}" var="contestFile" varStatus="status">
													<a href="javascript:void(0);" onclick="Down('${contestFile.atch_file_id }');" class="attach"><img src="/resources/client/images/contents/file.gif" alt="첨부파일 아이콘">${contestFile.orignl_file_nm }</a>												
												</c:forEach>
			               				     </dl>
										</c:when>
									</c:choose>	
								<c:if test="${contestView.situation_show_yn eq 'Y' }">
				                    <dl class="file">
				                       	<table>
					                       	<tbody>
					                       		<tr>
													<td><span style="font-weight: bold; color: black;">접수현황</span></td>
													<td><span style="font-weight: bold; color: red;">1조</span> : <span style="color: blue; font-weight: bold;"><c:out value='${contestView.part1_app_cnt + contestView.part1_wait_cnt }'/>/<c:out value='${contestView.limit_part + contestView.limit_waiting  }'/></span></td>
						                            <td><span style="font-weight: bold; color: red;">2조</span> : <span style="color: blue; font-weight: bold;"><c:out value='${contestView.part2_app_cnt + contestView.part2_wait_cnt }'/>/<c:out value='${contestView.limit_part + contestView.limit_waiting  }'/></span></td>
					                       		</tr>
					                       	</tbody>
				                       	</table> 
				                    </dl>
								</c:if>		                       	
			                </div>
			                <!--VIEW_HEAD// E-->
			                
			                <div class="view_body">
			                    ${contestView.ct_content }
			                </div>
			                <!--VIEW_BODY //E -->
			                
			            </div>
			            <!--TBL_VIEW //E -->
			            
			            <div class="btn_r2">
			            	<c:choose>
				            	<c:when test="${contestView.ct_process eq 'E' || contestView.ct_process eq 'F' }">
				            		<a href="javascript:goBoardList();" class=" ">목록</a>
				            	</c:when>
				            	<c:otherwise>
				            		<a href="javascript:chkApp();" class="gray">신청하기</a>
			                		<a href="javascript:goBoardList();" class="">목록</a>
				            	</c:otherwise>
				            </c:choose>
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