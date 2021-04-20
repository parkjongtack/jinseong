<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<style type="text/css">
/* contents-tab */
.ct-tab { overflow:hidden; width:100%; margin-bottom:20px; border-bottom:1px solid #ddd }
.ct-tab li { float:left;  }
.ct-tab li a { display:block; min-width:175px; height:32px; margin-right:-1px; padding:13px 10px 0 5px; text-align:center; background:#f5f5f5; border:1px solid #ddd; border-bottom:0; font-size:15px; }
.ct-tab li a:hover, .ct-tab li a.on { font-weight:bold; background:#333;  color:#fff; border:1px solid #333; border-bottom:0; }
.ct-tab li.tab1 a { display:block; min-width:132px; height:32px; margin-right:-1px; padding:13px 10px 0 5px; text-align:center; background:#f5f5f5; border:1px solid #ddd; border-bottom:0; font-size:15px; }
.ct-tab li.tab1 a:hover, .ct-tab li.tab1 a.on { font-weight:bold; background:#333; color:#fff; border:1px solid #333; border-bottom:0; }

.bbs-list { }
.bbs-list table { border-top:2px solid #222; border-left:1px solid #bbb; }
.bbs-list table tr:hover { background:#f8f8f8; }
.bbs-list table th { padding:15px 8px; text-align:center; border-right:1px solid #bbb; border-bottom:1px solid #bbb; }
.bbs-list table td { padding:9px 8px; text-align:center; border-right:1px solid #bbb; border-bottom:1px solid #bbb; }
.bbs-list table thead th { background:#f1f1f1; color:#111; font-weight:bold;}
.bbs-list table tbody th { font-weight:normal; }
.bbs-list table td.subj { text-align:left;  }
.bbs-list table td a {  }
.bbs-list table td a:hover { text-decoration:underline; }
.bbs-list table td .bk { display:inline-block; vertical-align:middle; border:1px solid #ddd; }
</style>
<c:import url="/client/header.do" />

<script type="text/javascript">
var currPart = "1";

$(document).ready(function () {
	getAppResultList(1,"0004");
	
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
	

});



function getAppResultList(part,status){
	console.log("part ===> " + part + " /  status ==> " + status);
	$.ajax({
		url		:	"<c:url value='/contest/contestAppResultJson.do'/>",
		data	:	{
			ct_seq	:	"${boardView.ct_seq}",
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
				$("#contestAppList").append("<tr><td colspan='5'>데이터가 존재하지 않습니다.</td></tr>");
			}
		},beforeSend		:function(){
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

function goBoardList() {
	$("#frm").attr("action", "<c:url value='/contest/contestAppList.do'/>");
	$("#frm").submit();
}

</script>


<script id="contestAppListTmpl" type="text/x-jquery-tmpl">
<tr>
	<td scope="row">\${reg_dt }</td>
	<td>\${join_name }(\${reg_id })</td>
	<td>
		{{if gender == 'M'}}남{{/if}}
		{{if gender == 'F'}}여{{/if}}
	</td>
	<td>\${waiting_num }</td>
	<td>
		{{if pay_flag == 'Y'}}입금완료{{/if}}
		{{if pay_flag == 'N'}}입금대기{{/if}}
		{{if pay_flag == 'R'}}환불완료{{/if}}
	</td>
</tr>
</script>


<form:form commandName="vo" name="frm" id="frm">
	<input type="hidden" name="srch_input" id="srch_input" value="<c:out value='${Srch_input }'/>" />
	<input type="hidden" name="scType" value="<c:out value='${scType }'/>" />
	<input type="hidden" name="currRow" value="<c:out value='${currPage }'/>" />
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
			<h2 class="hide">본문</h2>
			<c:import url="/client/snb.do" />
			<div class="sub_content">
				<div class="inner">
					<div class="sub_head">
						<h3 class="c_tit">대회신청 결과</h3>
					</div>

					<div class="board_view_area sub_fade">
						<div class="bbs_view">
							<div class="view_head">
								<dl class="subject">
									<dt>
										<c:out value='${boardView.ct_sbj }' />
									</dt>
									<dd class="info">
										<span><strong><c:out value='${boardView.ntcr_nm }' /></strong></span><span><c:out value='${boardView.reg_dt }' /></span>
									</dd>
								</dl>
							</div>
							<!--VIEW_HEAD// E-->
							<div>
								<ul class="ct-tab">
									<li><a class="btnAppPart on" href="javascript:void(0);" data-part="1">1조</a></li>
									<li><a class="btnAppPart" href="javascript:void(0);" data-part="2">2조</a></li>
									<li><a class="btnAppPart" href="javascript:void(0);" data-part="3">3조</a></li>
									<li><a class="btnAppPart" href="javascript:void(0);" data-part="4">4조</a></li>		
								</ul>
								<ul class="ct-tab">
									<li><a class="btnStatus on" href="javascript:void(0);" data-status="0004">선정</a></li>
									<li><a class="btnStatus" href="javascript:void(0);" data-status="0005">대기</a></li>
									<!-- <li><a class="btnStatus" href="javascript:void(0);" data-status="0003">신청취소</a></li> -->
								</ul>
								<div class="bbs-head" style="margin-bottom: 5px;">
						           	<font color="#003CFF">※인원수 : </font><span id="totCnt"></span>
								</div>
								
								<fieldset class="bbs-list">
									<legend>신청자 목록</legend>
									<table summary="사업신청 결과제출 전체 목록">
									<colgroup>
											<col width="7%">
											<col width="8%">
											<col width="8%">
											<col width="12%">
											<col width="10%">
										</colgroup>
										<thead>
											<tr>
												<th scope="col">신청일자</th>
												<th scope="col">성명<br>(아이디)</th>
						                        <th scope="col">성별</th>
						                        <th scope="col">대기번호</th>
						                        <th scope="col">입금확인</th>
											</tr>
										</thead>
										<tbody id="contestAppList">
						
										</tbody>
									</table>
								</fieldset>
							</div>
							<div class="view_body">
							
							</div>
							<!--VIEW_BODY //E -->

						</div>
						<!--TBL_VIEW //E -->

						<div class="btn_r">
							<a href="javascript:goBoardList();" class="">목록</a>
						</div>

						<!--VIEW_LIST //E -->

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