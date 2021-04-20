<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>
<%
	response.sendRedirect("/apage/contest/adminContestMngList.do");
%>
<script type="text/javascript">
</script>
<div id="container">
	<h3>메인</h3>
	<div class="contents">
		
	</div>
	<!-- //contents -->
</div>
		
<jsp:include page="/apage/inc/footer.do"></jsp:include>