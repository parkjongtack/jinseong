<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<script type="text/javascript">
	$(document).ready(function() {

		pagingUtil.__form = $("#frm");
		pagingUtil.__url = "<c:url value='/board/centerList.do'/>";

	});

	function searchChk() {
		$("#frm").attr("action", "<c:url value='/board/centerList.do'/>");
		$("#frm").find('#currRow').val('1');
		$("#frm").submit();
	}

	function areaSelect(val) {
		$("#as_type").val(val);
		$("#frm").attr("action", "<c:url value='/board/centerList.do'/>");
		$("#frm").find('#currRow').val('1');
		$("#frm").submit();
	}
</script>


<div id="container" class="subpage">
	<div id="contents">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />

		<div class="sub_content">
			<div class="inner">
				<div class="sub_head">
					<h3 class="c_tit">전국 볼링장</h3>
				</div>
				<div class="board_list_area sub_fade">
					<form id="frm" name="frm" method="post">
						<div class="board_info_center">
							<div>
								<div class="schBx">
									<label for="" class="pVer">지역선택</label> 
									<select id="as_type" name="as_type">
										<option value="">::지역::</option>
										<option value="0010" <c:if test="${as_type eq '0010' }">selected</c:if>>서울</option>
										<option value="0020" <c:if test="${as_type eq '0020' }">selected</c:if>>경기</option>
										<option value="0030" <c:if test="${as_type eq '0030' }">selected</c:if>>인천</option>
										<option value="0040" <c:if test="${as_type eq '0040' }">selected</c:if>>충북</option>
										<option value="0050" <c:if test="${as_type eq '0050' }">selected</c:if>>충남</option>
										<option value="0060" <c:if test="${as_type eq '0060' }">selected</c:if>>대전</option>
										<option value="0070" <c:if test="${as_type eq '0070' }">selected</c:if>>경북</option>
										<option value="0080" <c:if test="${as_type eq '0080' }">selected</c:if>>경남</option>
										<option value="0090" <c:if test="${as_type eq '0090' }">selected</c:if>>대구</option>
										<option value="0100" <c:if test="${as_type eq '0100' }">selected</c:if>>울산</option>
										<option value="0110" <c:if test="${as_type eq '0110' }">selected</c:if>>부산</option>
										<option value="0120" <c:if test="${as_type eq '0120' }">selected</c:if>>전북</option>
										<option value="0130" <c:if test="${as_type eq '0130' }">selected</c:if>>전남</option>
										<option value="0140" <c:if test="${as_type eq '0140' }">selected</c:if>>광주</option>
										<option value="0150" <c:if test="${as_type eq '0150' }">selected</c:if>>강원</option>
										<option value="0160" <c:if test="${as_type eq '0160' }">selected</c:if>>제주</option>
									</select> 
									<label for="schCondition" class="hide">검색</label> 
									<label for="schKeword"><input type="text" id="srch_input" name="srch_input" value="${Srch_input }" class="w200"></label>
									<button type="submit" class="sb_btn">검색</button>
								</div>
							</div>
						</div>
					</form>
					<!-- BOARD_INFO //E -->
					<div class="boling_map">
						<ul>
							<!-- 해당 지역 클릭하면 클래스 "on" 추가-->
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0010');" class="map1 <c:if test="${as_type eq '0010' }">on</c:if>">서울</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0020');" class="map2 <c:if test="${as_type eq '0020' }">on</c:if>">경기</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0150');" class="map3 <c:if test="${as_type eq '0150' }">on</c:if>">강원</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0080');" class="map4 <c:if test="${as_type eq '0080' }">on</c:if>">경상남도</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0070');" class="map5 <c:if test="${as_type eq '0070' }">on</c:if>">경상북도</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0140');" class="map6 <c:if test="${as_type eq '0140' }">on</c:if>">광주</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0090');" class="map7 <c:if test="${as_type eq '0090' }">on</c:if>">대구</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0060');" class="map8 <c:if test="${as_type eq '0060' }">on</c:if>">대전</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0110');" class="map9 <c:if test="${as_type eq '0110' }">on</c:if>">부산</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0100');" class="map10 <c:if test="${as_type eq '0100' }">on</c:if>">울산</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0030');" class="map11 <c:if test="${as_type eq '0030' }">on</c:if>">인천</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0130');" class="map12 <c:if test="${as_type eq '0130' }">on</c:if>">전라남도</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0120');" class="map13 <c:if test="${as_type eq '0120' }">on</c:if>">전라북도</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0050');" class="map14 <c:if test="${as_type eq '0050' }">on</c:if>">충청남도</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0040');" class="map15 <c:if test="${as_type eq '0040' }">on</c:if>">충청북도</a></li>
							<li><a href="javascript:void();" onclick="javascript:areaSelect('0160');" class="map16 <c:if test="${as_type eq '0160' }">on</c:if>">제주</a></li>
						</ul>
					</div>

					<div class="bbs_list tbl_rg">
						<div class="tbl_cm">
							<table summary="구분, 제목, 작성자, 등록일자, 조회수 표 입니다.">
								<caption>전국볼링장 찾기</caption>
								<colgroup>
									<col width="15%" class="pVer">
									<col width="15%" class="pVer">
									<col width="50%" class="pVer">
									<col width="20%" class="pVer">
								</colgroup>
								<thead>
									<tr>
										<th scope="col">지역</th>
										<th scope="col">볼링장명</th>
										<th scope="col">주소</th>
										<th scope="col">전화번호</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(centerList) > 0}">
											<c:forEach items="${centerList }" var="list" varStatus="status">
												<tr>
													<td data-cell-header="지역 : "><c:if test="${as_type eq '0010' }">서울</c:if> <c:if test="${as_type eq '0020' }">경기</c:if> <c:if test="${as_type eq '0030' }">인천</c:if> <c:if
															test="${as_type eq '0040' }">충북</c:if> <c:if test="${as_type eq '0050' }">충남</c:if> <c:if test="${as_type eq '0060' }">대전</c:if> <c:if test="${as_type eq '0070' }">경북</c:if> <c:if
															test="${as_type eq '0080' }">경남</c:if> <c:if test="${as_type eq '0090' }">대구</c:if> <c:if test="${as_type eq '0100' }">울산</c:if> <c:if test="${as_type eq '0110' }">부산</c:if> <c:if
															test="${as_type eq '0120' }">전북</c:if> <c:if test="${as_type eq '0130' }">전남</c:if> <c:if test="${as_type eq '0140' }">광주</c:if> <c:if test="${as_type eq '0150' }">강원</c:if> <c:if
															test="${as_type eq '0160' }">제주</c:if></td>
													<td data-cell-header="볼링장명 : ">${list.ntt_sj }</td>
													<td class="tl" data-cell-header="주소 : ">${list.ntt_cn }</td>
													<td data-cell-header="전화번호 : ">${list.memo }</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="4">조회된 결과가 없습니다.</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
					<!--BBS_LIST //E-->

					<div class="paging pagAlign">${pageNav }</div>
					<!--PAGING //E-->
				</div>
				<!-- BOARD_LIST_AREA //E -->
			</div>
		</div>
		<!-- SUB_CONTENT //E-->
	</div>
	<!-- #CONTENTS //E -->
</div>
<!-- #CONTAINER //E -->
<jsp:include page="/client/footer.do"></jsp:include>