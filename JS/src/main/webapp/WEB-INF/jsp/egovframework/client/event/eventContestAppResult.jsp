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



function getAppResultList(part,status){
	$.ajax({
		url		:	"<c:url value='/event/eventContestAppResultJson.do'/>",
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
				$("#totCnt").html(data.root.resultList.length+"???");
				$("#headWrap").empty();
				if(status == '0005'){
					$("#contestAppListTmplWaitHeader").tmpl().appendTo("#headWrap");			
					$("#contestAppListTmplWait").tmpl(data.root.resultList).appendTo("#contestAppList");			
				}else{
					$("#contestAppListTmplHeader").tmpl().appendTo("#headWrap");			
					$("#contestAppListTmpl").tmpl(data.root.resultList).appendTo("#contestAppList");			
				}
				
			}else{
				$("#totCnt").html("0???");
				$("#contestAppList").append("<tr><td colspan='5'>???????????? ???????????? ????????????.</td></tr>");
			}
		},beforeSend		:function(){
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

function goBoardList() {
	$("#frm").attr("action", "<c:url value='/event/eventContestAppList.do'/>");
	$("#frm").submit();
}

</script>


<script id="contestAppListTmplHeader" type="text/x-jquery-tmpl">
<tr>
	<th scope="col">????????????</th>
	<th scope="col">??????<br>(?????????)</th>
	<th scope="col">??????</th>
</tr>
</script>
<script id="contestAppListTmpl" type="text/x-jquery-tmpl">
<tr>
	<td scope="row">\${rownum }</td>
	<td>\${join_name }(\${reg_id })</td>
	<td>
		{{if gender == 'M'}}???{{/if}}
		{{if gender == 'F'}}???{{/if}}
	</td>
</tr>
</script>


<script id="contestAppListTmplWaitHeader" type="text/x-jquery-tmpl">
<tr>
	<th scope="col">????????????</th>
	<th scope="col">??????<br>(?????????)</th>
	<th scope="col">??????</th>
</tr>
</script>

<script id="contestAppListTmplWait" type="text/x-jquery-tmpl">
<tr>
	<td>\${waiting_num }</td>
	<td>\${join_name }(\${reg_id })</td>
	<td>
		{{if gender == 'M'}}???{{/if}}
		{{if gender == 'F'}}???{{/if}}
	</td>
	
</tr>
</script>


<form:form commandName="vo" name="frm" id="frm">
	<input type="hidden" name="srch_input" id="srch_input" value="<c:out value='${Srch_input }'/>" />
	<input type="hidden" name="scType" value="<c:out value='${scType }'/>" />
	<input type="hidden" name="currRow" value="<c:out value='${currPage }'/>" />
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
			<h2 class="hide">??????</h2>
			<c:import url="/client/snb.do" />
			<div class="sub_content">
				<div class="inner">
					<div class="sub_head">
						<c:choose>
							<c:when test="${boardView.ct_type eq 'S' }">
								<h3 class="c_tit">????????? ????????????</h3>
							</c:when>
							<c:otherwise>
								<h3 class="c_tit">????????? ?????? ????????????</h3>
							</c:otherwise>
						</c:choose>
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
									<c:if test="${boardView.ct_type eq 'L' && fn:length(pList) > 0}">
										<c:forEach items="${pList }" var="pList" varStatus="status">
											<li><a href="javascript:void(0);" class="btnAppPart <c:if test="${status.index == 0}"> on</c:if>" data-part="${pList.ecp_seq }">${pList.part_ord }???&nbsp;${pList.part_name }</a></li>
										</c:forEach>
									</c:if>
									<c:if test="${boardView.ct_type eq 'A' || boardView.ct_type eq 'S'}">
										<li><a href="javascript:void(0);" class="btnAppPart on" data-part="1">??????</a></li>
									</c:if>
								</ul>		
								<ul class="ct-tab">
									<li><a class="btnStatus on" href="javascript:void(0);" data-status="0004">??????</a></li>
									<li><a class="btnStatus" href="javascript:void(0);" data-status="0005">??????</a></li>
									<!-- <li><a class="btnStatus" href="javascript:void(0);" data-status="0003">????????????</a></li> -->
								</ul>
								<div class="bbs-head" style="margin-bottom: 5px;">
						           	<font color="#003CFF">???????????? : </font><span id="totCnt"></span>
								</div>
								
								<fieldset class="bbs-list">
									<legend>????????? ??????</legend>
									<table summary="???????????? ???????????? ?????? ??????">
									<colgroup>
											<col width="7%">
											<col width="8%">
											<col width="8%">
											<col width="12%">
										</colgroup>
										<thead id="headWrap">
										
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
							<a href="javascript:goBoardList();" class="">??????</a>
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