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
	
	$("#tab1").trigger('click');
	
	$(".thum").click(function(){
		$(".thum").removeClass("on");
		$(this).addClass("on");
	});
});

function goList(){
	
	$("#frmDetail").attr("action","/shop/shopList.do");
	$("#frmDetail").submit();
	
}

//상세페이지에서 탭사진 클릭시 해당 사진 노출
function myFunction(imgs) {
    var expandImg = document.getElementById("expandedImg");
    expandImg.src = imgs.src;
}
    
// 탭메뉴 클릭시 해당 탭컨텐츠 노출
function openPage(pageName,elmnt,color) {
	
	var i, tabcontent, tablinks;
	
	tabcontent = document.getElementsByClassName("tabcontent");
	
	for (i = 0; i < tabcontent.length; i++) {
	    tabcontent[i].style.display = "none";
	}
	
	tablinks = document.getElementsByClassName("tablink");
	
	for (i = 0; i < tablinks.length; i++) {
	    tablinks[i].style.backgroundColor = "";
	}
	document.getElementById(pageName).style.display = "block";
	elmnt.style.backgroundColor = color;

}

function getDetailURL(){
	var param = 'https://bowlingkorea.com/shop/shopView.do';
	$("#frmDetail").find("input").each(function(idx){
		if(idx == 0){
			param += "?"+$(this).attr("name")+"="+$(this).val();
		}else{
			param += "&"+$(this).attr("name")+"="+$(this).val();
		}
	});
	copy_to_clipboard(param);
}

function is_ie() {
	if(navigator.userAgent.toLowerCase().indexOf("chrome") != -1) return false;
	if(navigator.userAgent.toLowerCase().indexOf("msie") != -1) return true;
	if(navigator.userAgent.toLowerCase().indexOf("windows nt") != -1) return true;
	return false;
}
	 
function copy_to_clipboard(str) {
	if( is_ie() ) {
		window.clipboardData.setData("Text", str);
		alert("복사되었습니다.");
		return;
	}
	prompt("Ctrl+C를 눌러 복사하세요.", str);
}
</script>

<form method="post" id="frmDetail" name="frmDetail">
	<input type="hidden" name="hash" value="${shopView.hash }"/>
	<input type="hidden" id="menuDepth1" name="menuDepth1" value="${menuDepth1 }"/>
	<input type="hidden" id="menuDepth2" name="menuDepth2" value="${menuDepth2 }"/>
	<input type="hidden" id="menuDepth3" name="menuDepth3" value="${menuDepth3 }"/>
	<input type="hidden" id="big" name="big" value="${searchVo.big }"/>
	<input type="hidden" id="mid" name="mid" value="${searchVo.mid }"/>
	<input type="hidden" id="small" name="small" value="${searchVo.small }"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form>

<form:form commandName="vo" name="frm" id="frm" >
	<div id="container" class="subpage">
		<div id="contents" class="s_con">
	        <h2 class="hide">본문</h2>
	        <c:import url="/client/snb.do" />                       
	        <div class="sub_content">
                <div class="inner">
                <div class="sub_head">
                    <h3 class="c_tit">${title }
	                    <c:if test="${sessionScope.adminInfo.mber_id != null && sessionScope.adminInfo.mber_id != ''}">
		                    <span onclick="getDetailURL()" style="cursor: pointer; font-size: 10pt; float: right; font-family: none;">링크복사</span>
	                    </c:if>
                    </h3>
                </div>
                <div class="view_detail_area sub_fade">
                    <div class="detail_head">
                        <dl>
                            <dt>${shopView.name }</dt>
                            <dd>
                                <ul class="litype_or">
                                    <li><span>제품명</span>${shopView.name }</li>
                                    <li><span>제조회사</span>${shopView.value }</li>
                                    <li>
                                    	<c:choose>
								        	<c:when test="${fn:length(optionlist) > 0}">
								            	<c:forEach items="${optionlist }" var="list" varStatus="status">
									            	<c:choose>
									            		<c:when test="${list.name eq 'pound' }">
									            			<span>파운드</span>${list.items }
									            		</c:when>
									            		<c:otherwise>
									            			<span>${list.name }</span>${list.items }
									            		</c:otherwise>
									            	</c:choose>
								            	</c:forEach>
								            </c:when>
							            </c:choose>
                                    </li>
                                    <!-- <li><span>출시일</span>?</li> -->
                                </ul>
                            </dd>
                            <dd><!-- 해당 썸네일 클릭시 클래스 on 추가-->
                            <div class="thum on">
                                <img src="https://bowlingkoreamall.com:446/${shopView.updir }/${shopView.upfile3 }" alt="${shopView.name }" onclick="myFunction(this);">
                            </div>
                            <c:choose>
					        	<c:when test="${fn:length(imglist) > 0}">
					            	<c:forEach items="${imglist }" var="list" varStatus="status">
						            	<c:if test="${list.sort ne null && list.sort ne '' }">
						            		<div class="thum">
			                                    <img src="https://bowlingkoreamall.com:446/${list.updir }/${list.filename }" alt="${list.ofilename }" onclick="myFunction(this);">
			                                </div>
						            	</c:if>
					            	</c:forEach>
					            </c:when>
				            </c:choose>
                            </dd>
                            <dd>
                            	<!-- 해당 쇼핑몰로 이동 -->
                            	<c:if test="${searchVo.big ne '1102'}">
	                                <a href="http://bowlingkoreamall.com/shop/detail.php?pno=${shopView.hash }" target="_blank">제품구매</a>
                            	</c:if>
                                <a href="javascript:goList();" class="bdbtn">제품목록</a><!-- 게시판 목록으로 -->
                            </dd>
                        </dl>
                        <div class="bigImg">
                          <img src="https://bowlingkoreamall.com:446/${shopView.updir }/${shopView.upfile3 }" alt="${shopView.name }" id="expandedImg" class="w500">
                        </div>
                    </div>
                    <div class="detail_body">
                    	 <!--	<button class="tablink" onclick="openPage('info',this,'#001d3d')" id="tab1">상세정보</button>
                    		<button class="tablink" onclick="openPage('addv',this,'#001d3d')" id="tab2">광고</button>
                    		 --> 
                    		<!-- -->
                        <a class="tablink" id="tab1" onclick="openPage('info',this,'#001d3d')">상세정보</a>
                        <a class="tablink" id="tab2" onclick="openPage('addv',this,'#001d3d')">광고</a>
                         
                        <div id="info" class="tabcontent">
                          ${shopView.content2 }
                        </div>
                        <div id="addv" class="tabcontent">
                          ${shopView.content }
                          <!-- 
                          ${shopView.content3 }
                          ${shopView.content4 }
                          ${shopView.content5 }
                           -->
                        </div>
                    </div>
                </div>
                <!-- VIEW_DEDAIL_AREA //E -->
                
                </div>
            </div>
            <!-- SUB_CONTENT //E-->
	        
		</div>
		<!-- #CONTENTS //E -->		
	</div> 
	<!-- #CONTAINER //E -->		
</form:form>           
 
<jsp:include page="/client/footer.do"></jsp:include>