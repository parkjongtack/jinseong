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
	
	$(".thum").click(function(){
		
		if(!$(this).hasClass("on")){
			$(".thum").removeClass("on");
			$(this).addClass("on");
		}
	});
});
</script>

	<div id="container" class="subpage">
		<div id="contents" >
            <h2 class="hide">본문</h2>
            <c:import url="/client/snb.do" />       
                
            ${content }
            <!-- SUB_CONTENT //E-->               
		</div>
		<!-- #CONTENTS //E -->		
	</div> 
	<!-- #CONTAINER //E -->
<jsp:include page="/client/footer.do"></jsp:include>