<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>

<c:import url="/client/header.do" />

<script type="text/javascript">
$(document).ready(function () {
	
	getCommentJson();
});

function goBoardUpdt() {
	$("#frm").attr("action", "<c:url value='/board/asModify.do'/>");
	$("#frm").submit();
}

function goBoardDel() {
	if(!confirm('삭제하시겠습니까?')) return;
	$("#frm").attr("action", "<c:url value='/board/asDelete.do'/>");
	$("#frm").submit();
}

function goBoardList(){
	$("#frm").attr("action", "<c:url value='/board/asList.do'/>");
	$("#frm").submit();
}

function goPreBoardView(no){
	$("#frm").attr("action", "<c:url value='/board/asDetail.do'/>");
	$("#ntt_id").val(no);
	$("#frm").submit();
}

function Down(no){
	$("#frm1").attr("action", "<c:url value='/commonfile/nunFileDown.do'/>");
	$("#atch_file_id").val(no);
	$("#frm1").submit();
}

//코멘트 불러오기
function getCommentJson(){
	 	
	 $.ajax({
			type		: "POST",
			url			: "/board/commentListJson.do",
			data		: {
								bbs_id	: 'as',
								ntt_id	:  $("#ntt_id").val()
						 },
			cache		: false,
			dataType	: 'json',
			success		: function(data) {
				arrayData24s = data.root.dataset0;
				
				if(arrayData24s.length > 0){	
					$("#commentList").html("");
					for(var i=0; i < arrayData24s.length; i++) {
						$("#commentListTmpl").tmpl([
              	{
              		 bbs_id  : arrayData24s[i].bbs_id
              		,seq     : arrayData24s[i].seq
              		,content : arrayData24s[i].content
              		,reg_dt  : arrayData24s[i].reg_dt
              		,reg_id  : arrayData24s[i].reg_id
              	}
            	]).appendTo("#commentList");	
					}
				}else{	
					$("#commentList").html("");
				}
			}
		});
}
//댓글 삭제
function goCommentDel(seq){
	if(confirm("삭제하시겠습니까?")== true){
		$.ajax({
			type : "POST",
			url : "/board/deleteBoardComment.do",
			data : { seq	:	seq},
			cache : false,
			dataType : 'json',
			success : function(msg){	
				alert("정상적으로 삭제 되었습니다.");
				getCommentJson();				
			},
			error : function(data, status, err) {
				alert(status);
				return;
			}
		});
	}else{
		return;
	}
	
} 
//사용자 댓글등록
function goReplyReg(){
	
	if($("#content").val() == ""){
		alert("코멘트를 작성하세요.");
		$("#content").focus();
		return;
	}
	
	if(confirm("등록 하시겠습니까?") == true){

		$.ajax({
			type : "POST",
			url : '/board/insertBoardComment.do',
			data : {
				bbs_id		: 'as',
				parent_seq	: $("#ntt_id").val(),
				content		: $("#content").val()
			},
			cache : false,
			dataType : 'json',
			success : function(msg){
				$("#content").val('');
				getCommentJson();				
			},
			error : function(data, status, err) {
				alert(status);
				return;
			}
		});
	}else{
		return;
	}
}	
</script>
<script id="commentListTmpl" type="text/x-jquery-tmpl">
<dl>
{{if reg_id == "${sessionScope.mberInfo.mber_id }" }}
	<dt>{{= reg_id}}<a href="javascript:void();" class="txt_md" onclick="goCommentDel('{{= seq}}')">삭제</a></dt>
{{else}} 
	<dt>{{= reg_id}}</dt>
{{/if}}
	<dd><pre>{{= content}}</pre></dd>

	<dd class="date">{{= reg_dt}}</dd>
</dl> 
</script>

<form method="post" id="frm1" name="frm1">
	<input type="hidden" name="atch_file_id" id="atch_file_id"/>
</form>

<form:form commandName="vo" name="frm" id="frm" >
	<input type="hidden" name="ntt_id" id="ntt_id" value="<c:out value='${boardView.ntt_id }'/>" />
	<input type="hidden" name="atch_file_id" value="<c:out value='${boardView.atch_file_id }'/>" />
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
		                <h3 class="c_tit">A/S 게시판</h3>
		            </div>    
			        <div class="board_view_area sub_fade">
			            <div class="bbs_view">
			                <div class="view_head">
			                    <dl class="subject">
			                        <dt><c:out value='${boardView.ntt_sj }'/></dt>
			                        <dd class="info">
			                        	<span><strong><c:out value='${boardView.ntcr_nm }'/></strong></span>
			                        	<span><c:out value='${boardView.reg_dt }'/></span>
			                        	<span><c:out value='${boardView.rdcnt }'/></span>
		                        	</dd>
			                    </dl>
			                    <dl class="file">
			                       <dt class="t_tit">첨부파일 : </dt>
			                       <dd> 
			                       <c:choose>
										<c:when test="${fn:length(asfile) > 0}">
											<c:forEach items="${asfile}" var="list" varStatus="status">
												<a href="javascript:void(0);" onclick="Down('${list.atch_file_id }');" class="attach"><img src="/resources/client/images/contents/file.gif" alt="첨부파일 아이콘">${list.orignl_file_nm }</a>												
											</c:forEach>
										</c:when>
										<c:otherwise>첨부파일이 없습니다.</c:otherwise>
									</c:choose>			
														</dd>                       	
			                    </dl>
			                </div>
			                <!--VIEW_HEAD// E-->
							<% pageContext.setAttribute("LF", "\n"); %>
							<%-- <div class="view_body"><c:out value="${fn:replace(boardView.ntt_cn, LF,'<br />') }"  escapeXml="false"/></div> --%>
							 <div class="view_body"><c:out value='${boardView.ntt_cn }' escapeXml="false" /></div>
			                <!--VIEW_BODY //E -->
			            </div>
			            <!--TBL_VIEW //E -->
			            <div class="btn_r2">
			                <a href="javascript:goBoardUpdt();" class="">수정</a>
			                <a href="javascript:goBoardDel();" class="gray">삭제</a>
			                <a href="javascript:goBoardList();" class="gray">목록</a>
			            </div>
			        </div>
			        <!--BOARD_VIEW_AREA //E-->	        
	    		</div>
	    		
				<div class="commWrite">
					<div class="inner">
						<p class="tit">
							나도 한마디! <span>위의 내용과 관련하여 고객님의 궁금증이 있으시면 자세히 적어주세요.</span>
						</p>
						<div class="commInput">
							<p class="textArea">
								<label for="content" class="hide">나도 한마디 내용</label>
								<textarea id="content" rows="3" name="content"></textarea>
							</p>
							<p class="btnComm">
								<a href="javascript:void();" onclick="goReplyReg();">등록</a>
							</p>
						</div>
						<div class="commTxt" id="commentList"></div>
					</div>
				</div>
				<!--COMMWRITE //E-->
	    		
       		</div>
		</div>
		<!-- #CONTENTS //E -->		
	</div> 
	<!-- #CONTAINER //E -->		
</form:form>           
 
<jsp:include page="/client/footer.do"></jsp:include>