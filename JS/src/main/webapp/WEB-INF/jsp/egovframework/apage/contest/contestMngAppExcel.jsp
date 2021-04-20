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
    response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode("대회신청자_리스트_")+ strDate + ".xls");
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
				<td colspan="13" style="text-align:center; font-weight:bold;font-size: 22px;">${contestView.ct_sbj }</td>				
			</tr>
			<tr>
				<td colspan="13" style="text-align:right;">출력일자 : <%=strDate %></td>				
			</tr>			
			<tr>
				<td style="text-align:center; background-color:#c9c9c9">No</td>
				<td style="text-align:center; background-color:#c9c9c9">그룹(조)</td>
				<td style="text-align:center; background-color:#c9c9c9">참가자명</td>
				<td style="text-align:center; background-color:#c9c9c9">입금자명</td>
				<td style="text-align:center; background-color:#c9c9c9">성별</td>
				<td style="text-align:center; background-color:#c9c9c9">연락처</td>				
				<td style="text-align:center; background-color:#c9c9c9">이메일</td>
				<td style="text-align:center; background-color:#c9c9c9">장애여부</td>
				<td style="text-align:center; background-color:#c9c9c9">핸디</td>
				<td style="text-align:center; background-color:#c9c9c9">레인</td>
				<td style="text-align:center; background-color:#c9c9c9">상태</td>
				<td style="text-align:center; background-color:#c9c9c9">결제유무</td>
				<td style="text-align:center; background-color:#c9c9c9">신청자아이디</td>				
			</tr>			
				<c:choose>
            		<c:when test="${fn:length(contestAppList) > 0}">
            			<c:forEach items="${contestAppList }" var="list" varStatus="status">
            				<tr>
								<th scope="row"><c:out value='${status.index + 1 }'/></th>
	                            <td><c:out value='${list.part }'/>조</td>
	                            <td><c:out value='${list.join_name }'/></td>
								<td><c:out value='${list.deposit_name }'/></td>
								<td>
									<c:if test="${list.gender eq 'M'}">남</c:if>
									<c:if test="${list.gender eq 'F'}">여</c:if>
								</td>
								<td><c:out value='${list.telno }'/></td>								
								<td><c:out value='${list.email }'/></td>
								<td>
									<c:if test="${list.disable_yn eq 'Y'}">장애3급이상</c:if>
									<c:if test="${list.disable_yn eq 'N'}">해당없음</c:if>									
								</td>
								<td><c:out value='${list.handicap }'/>점</td>
								<td style='mso-number-format:"\@";'><c:out value='${list.lane }' escapeXml="false"/></td>
								<td>
									<c:if test="${list.status eq '0003'}">신청취소</c:if>
									<c:if test="${list.status eq '0004'}">선정</c:if>
									<c:if test="${list.status eq '0005'}">대기</c:if>
								</td>
								<td>
									<c:if test="${list.pay_flag eq 'Y'}">입금완료</c:if>
									<c:if test="${list.pay_flag eq 'N'}">입금대기</c:if>
									<c:if test="${list.pay_flag eq 'R'}">환불완료</c:if>
								</td>
								<td><c:out value='${list.reg_id }'/></td>
							</tr>
		                </c:forEach>
		           	</c:when>	           	
		           	<c:otherwise>
			           	<tr>
			           		<td style="text-align:center;" colspan="13">데이터가 없습니다.</td>
			           	</tr>
		           	</c:otherwise>
	           	</c:choose>		 
		</table>
	</body>
</html>