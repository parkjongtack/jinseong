<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style type="text/css">
.contwrap{margin:0px; padding:0px; }
.pop_cont{}
.pop_bottom{width:100%;height:30px; vertical-align:right; padding:0px 0px 0px 0px; background:#666; color:#fff; margin-top: -30px; }
input.pop_check{vertical-align:middle; margin-right:5px; }
.contwrap{min-height:100%;padding:0; }
.pop_cont{}
.pop_bottom{}
</style>

<script type="text/javascript">
function pageGo(URL) {
	
	if(URL.trim() != ""){
		window.opener.location.href=URL;
	  	self.close();
	  	//opener.focus();
	}
}

function fSetToday() {
   	
	var popSeq = "${mainPopup.pop_seq}";
   
   	setCookie("popup"+popSeq, "Y", 1);
   	self.close();
}
 
  
function setCookie(cookieName, cookieValue, expireDate) {
	
	var today = new Date();
    today.setDate(today.getDate() + parseInt(expireDate));
     
    var expires = "expires="+today.toGMTString();
     
    document.cookie = cookieName + "=" + escape(cookieValue) + "; path=/;" + expires +";"
}
</script>


<div class="contwrap">
	<div class="pop_cont" onclick="pageGo('${mainPopup.pop_url}')">
		 <div style="width:${mainPopup.pop_width}px; height:${mainPopup.pop_height+30}px;" >${mainPopup.pop_content }</div>
	</div>
</div>
<c:if test="${mainPopup.pop_set eq 'Y' }">
	<div class="pop_bottom">
		<form name="pop"  action="get">
			<input type="checkbox" id="today_close"  class="pop_check" onclick="fSetToday()"/>
			<label for="today_close">오늘하루 열지 않겠습니다.</label>
		</form>
	</div>
</c:if>
