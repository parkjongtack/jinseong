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
		
	<c:if test="${contestView.ct_seq != '10000026' && contestView.ct_seq != '10000027'}">
		<style type="text/css">
			.colorOpt{ display: none; }
		</style>
	</c:if>
		
</head>
	<body>
		<table border="1">
			<tr>
				<td colspan="10" style="text-align:center; font-weight:bold;font-size: 22px;">${contestVo.ct_sbj }</td>				
			</tr>
			<tr>
				<td colspan="10" style="text-align:right;">출력일자 : <%=strDate %></td>				
			</tr>
		</table>
		
		
		<c:choose>
  			<c:when test="${fn:length(resultList) > 0 }">
  				<c:forEach items="${resultList }" var="fList" varStatus="status">
  					<table style="border:1px solid #c9c9c9;width:45%">
  						<tbody>
		     				<tr>
		     					<td colspan="10" style="text-align:center;color:red">${status.count }조 선정자 명단</td>
		     				</tr>
     						<tr>
 								<td style="text-align:center; background-color:#c9c9c9">No</td>
								<td style="text-align:center; background-color:#c9c9c9">성명</td>
								<td style="text-align:center; background-color:#c9c9c9">성별</td>
								<td style="text-align:center; background-color:#c9c9c9">연락처</td>				
								<td style="text-align:center; background-color:#c9c9c9">생년월일</td>				
								<td style="text-align:center; background-color:#c9c9c9">영문이름</td>
								<td style="text-align:center; background-color:#c9c9c9">주소</td>
								<td style="text-align:center; background-color:#c9c9c9">티셔츠 사이즈</td>
								<td style="text-align:center; background-color:#c9c9c9" class="colorOpt">선호하는 색상</td>
								<td style="text-align:center; background-color:#c9c9c9">레인</td>
								<td style="text-align:center; background-color:#c9c9c9">신청자아이디</td>	
		     				</tr>
							<c:forEach items="${fList }" var="sList" varStatus="status2">
								<tr>
									<th scope="row"><c:out value='${status2.index + 1 }'/></th>
									<td><c:out value='${sList.join_name }'/></td>
									<td>
										<c:if test="${sList.gender eq 'M'}">남</c:if>
										<c:if test="${sList.gender eq 'F'}">여</c:if>
									</td>
									<td><c:out value='${sList.telno }'/></td>		
									<td><c:out value='${sList.birth}'/></td>						
									<td><c:out value='${sList.eng_last_name }'/>&nbsp;<c:out value='${sList.eng_first_name }'/></td>
									<td>(<c:out value='${sList.zipcode }'/>)&nbsp;<c:out value='${sList.addr }'/>&nbsp;<c:out value='${sList.addr_detail }'/></td>
									<td><c:out value='${sList.option1 }'/></td>
									<td class="colorOpt"><c:out value='${sList.option2 }'/></td>
									<td style='mso-number-format:"\@";'><c:out value='${sList.lane }' escapeXml="false"/></td>
									<td><c:out value='${sList.reg_id }'/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<br/><br/>
				</c:forEach>
			</c:when>
		</c:choose>
		
		
		<c:choose>
  			<c:when test="${fn:length(readyResultList) > 0 }">
  				<c:forEach items="${readyResultList }" var="fList" varStatus="status">
  					<table style="border:1px solid #c9c9c9;width:45%">
  						<tbody>
		     				<tr>
		     					<td colspan="11" style="text-align:center;color:red">${status.count }조 대기자 명단</td>
		     				</tr>
     						<tr>
 								<td style="text-align:center; background-color:#c9c9c9">No</td>
								<td style="text-align:center; background-color:#c9c9c9">성명</td>
								<td style="text-align:center; background-color:#c9c9c9">성별</td>
								<td style="text-align:center; background-color:#c9c9c9">연락처</td>				
								<td style="text-align:center; background-color:#c9c9c9">생년월일</td>				
								<td style="text-align:center; background-color:#c9c9c9">영문이름</td>
								<td style="text-align:center; background-color:#c9c9c9">주소</td>
								<td style="text-align:center; background-color:#c9c9c9">티셔츠 사이즈</td>
								<td style="text-align:center; background-color:#c9c9c9"  class="colorOpt">선호하는 색상</td>
								<td style="text-align:center; background-color:#c9c9c9">레인</td>
								<td style="text-align:center; background-color:#c9c9c9">신청자아이디</td>	
								<td style="text-align:center; background-color:#c9c9c9">대기번호</td>	
		     				</tr>
							<c:forEach items="${fList }" var="sList" varStatus="status2">
								<tr>
									<th scope="row"><c:out value='${status2.index + 1 }'/></th>
									<td><c:out value='${sList.join_name }'/></td>
									<td>
										<c:if test="${sList.gender eq 'M'}">남</c:if>
										<c:if test="${sList.gender eq 'F'}">여</c:if>
									</td>
									<td><c:out value='${sList.telno }'/></td>		
									<td><c:out value='${sList.birth}'/></td>						
									<td><c:out value='${sList.eng_last_name }'/>&nbsp;<c:out value='${sList.eng_first_name }'/></td>
									<td>(<c:out value='${sList.zipcode }'/>)&nbsp;<c:out value='${sList.addr }'/>&nbsp;<c:out value='${sList.addr_detail }'/></td>
									<td><c:out value='${sList.option1 }'/></td>
									<td class="colorOpt"><c:out value='${sList.option2 }'/></td>
									<td style='mso-number-format:"\@";'><c:out value='${sList.lane }' escapeXml="false"/></td>
									<td><c:out value='${sList.reg_id }'/></td>
									<td><c:out value='${sList.waiting_num }'/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<br/><br/>
				</c:forEach>
			</c:when>
		</c:choose>

	</body>
</html>