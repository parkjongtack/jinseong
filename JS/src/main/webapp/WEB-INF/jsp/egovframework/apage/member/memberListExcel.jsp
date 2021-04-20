<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>


<%
DecimalFormat df = new DecimalFormat("00");
Calendar currentCalendar = Calendar.getInstance();
//현재 날짜 구하기
String strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
String strMonth = df.format(currentCalendar.get(Calendar.MONTH) + 1);
String strDay = df.format(currentCalendar.get(Calendar.DATE));
String strDate = strYear +"-"+ strMonth +"-"+ strDay;
%>
<%
	request.setCharacterEncoding("utf-8"); 
    response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode("회원리스트_")+ strDate + ".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	response.setContentType("application/vnd.ms-excel"); 
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

	</head>
	<body>
		<table border="1">
			<tr>
				<td colspan="12" style="text-align:center; font-weight:bold;font-size: 22px;">진승무역 홈페이지 회원리스트</td>				
			</tr>
			<tr>
				<td colspan="12" style="text-align:right;">출력일자 : <%=strDate %></td>				
			</tr>			
			<tr>
				<td style="text-align:center; background-color:#c9c9c9">No</td>
				<td style="text-align:center; background-color:#c9c9c9">아이디</td>
				<td style="text-align:center; background-color:#c9c9c9">이름</td>
				<td style="text-align:center; background-color:#c9c9c9">생년월일</td>
				<td style="text-align:center; background-color:#c9c9c9">성별</td>
				<td style="text-align:center; background-color:#c9c9c9">연락처</td>				
				<td style="text-align:center; background-color:#c9c9c9">이메일</td>
				<td style="text-align:center; background-color:#c9c9c9">주소</td>				
				<td style="text-align:center; background-color:#c9c9c9">가입일자</td>
				<td style="text-align:center; background-color:#c9c9c9">최근접속일자</td>
				<td style="text-align:center; background-color:#c9c9c9">상태</td>							
				<td style="text-align:center; background-color:#c9c9c9">SMS 수신여부</td>							
			</tr>			
				<c:choose>
            		<c:when test="${fn:length(memberList) > 0}">
            			<c:forEach items="${memberList }" var="list" varStatus="status">
            				<tr>
								<th scope="row"><c:out value='${status.index + 1 }'/></th>
	                            <td><c:out value='${list.mber_id }'/></td>
	                            <td><c:out value='${list.mber_name }'/></td>
								<td><c:out value='${list.mber_birth }'/></td>
								<td><c:out value='${list.mber_gender }'/></td>
								<td><c:out value='${list.mber_tel }'/></td>								
								<td><c:out value='${list.mber_email }'/></td>
								<td>(<c:out value='${list.zipcode }'/>) <c:out value='${list.addr }'/>  <c:out value='${list.addr_detail }'/></td>
								<td><c:out value='${list.reg_dt }'/></td>
								<td><c:out value='${list.last_login_dt }'/></td>
								<td>
									<c:if test="${list.mber_status eq 'Y'}">탈퇴</c:if>
									<c:if test="${list.mber_status eq 'N'}">활동</c:if>
								</td>
								<td>
									<c:if test="${list.sms_yn eq 'Y'}">수신</c:if>
									<c:if test="${list.sms_yn ne 'Y'}">수신거부</c:if>
								</td>
							</tr>
		                </c:forEach>
		           	</c:when>	           	
		           	<c:otherwise>
			           	<tr>
			           		<td style="text-align:center;" colspan="12">데이터가 없습니다.</td>
			           	</tr>
		           	</c:otherwise>
	           	</c:choose>		 
		</table>
	</body>
</html>