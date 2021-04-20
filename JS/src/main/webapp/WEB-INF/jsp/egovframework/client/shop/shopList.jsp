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
	
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/shop/shopList.do'/>";

	$(".btnCate").click(function(){
		var mid = $(this).attr("data-mid");
		var small = $(this).attr("data-sm");
		$("#mid").val(mid);
		$("#small").val(small);
		$("#frm").attr("action", "<c:url value='/shop/shopList.do'/>");
		$("#frm").submit();	
	});
	
	$(".btnView").click(function(){
		
		$("#hash").val($(this).attr("data-code"));		
		$("#frmDetail").attr("action","/shop/shopView.do");
		$("#frmDetail").submit();
		
	});
});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/shop/shopList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="hash" id="hash" />
	<input type="hidden" id="menuDepth1" name="menuDepth1" value="${menuDepth1 }"/>
	<input type="hidden" id="menuDepth2" name="menuDepth2" value="${menuDepth2 }"/>
	<input type="hidden" id="menuDepth3" name="menuDepth3" value="${menuDepth3 }"/>
	<input type="hidden" id="big" name="big" value="${searchVo.big }"/>
	<input type="hidden" id="mid" name="mid" value="${searchVo.mid }"/>
	<input type="hidden" id="small" name="small" value="${searchVo.small }"/>
	<input type="hidden" name="srch_input" id="srch_input"  value="<c:out value='${Srch_input }'/>"/>
	<input type="hidden" name="currRow"  value="<c:out value='${currPage }'/>"/>
</form:form>

	<div id="container" class="subpage">
		<div id="contents" >
            <h2 class="hide">본문</h2>
            <c:import url="/client/snb.do" />       
			<div class="sub_content">
                <div class="inner">
                <div class="sub_head">
                    <h3 class="c_tit">${title }</h3>
                </div>
                <div class="board_list_area">
                   <div class="board_info">
                       <div class="">
                           <ul class="tabMenu">
                           <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 1 && menuDepth3 eq 1}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s=""><span></span>ALL</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1020'}">on</c:if>" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="1020"><span></span>PRIMIER LINE</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1081'}">on</c:if>" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="1081"><span></span>SIGNATURE LINE</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1021'}">on</c:if>" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="1021"><span></span>MASTER LINE</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1022'}">on</c:if>" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="1022"><span></span>THUNDER LINE</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1023'}">on</c:if>" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="1023"><span></span>HOT LINE</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1024'}">on</c:if>" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="1024"><span></span>TROPICAL LINE</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1025'}">on</c:if>" data-d1="1" data-d2="1" data-d3="1" data-b="1001" data-m="1012" data-s="1025"><span></span>ICE LINE</a></li>
                            </c:if>
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 1 && menuDepth3 eq 2}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="1" data-d3="2" data-b="1001" data-m="1013" data-s=""><span></span>ALL</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1026'}">on</c:if>" data-d1="1" data-d2="1" data-d3="2" data-b="1001"  data-m="1013" data-s="1026"><span></span>#HP-4 LINE</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1027'}">on</c:if>" data-d1="1" data-d2="1" data-d3="2" data-b="1001"  data-m="1013" data-s="1027"><span></span>#HP-3 LINE</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1028'}">on</c:if>" data-d1="1" data-d2="1" data-d3="2" data-b="1001"  data-m="1013" data-s="1028"><span></span>#HP-2 LINE</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1029'}">on</c:if>" data-d1="1" data-d2="1" data-d3="2" data-b="1001"  data-m="1013" data-s="1029"><span></span>#HP-1 LINE</a></li>
                            </c:if>
                            
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 2 && menuDepth3 eq 1}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="2" data-d3="1" data-b="1002" data-m="1030" data-s=""><span></span>ALL</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1032'}">on</c:if>" data-d1="1" data-d2="2" data-d3="1" data-b="1002"  data-m="1030" data-s="1032"><span></span>#1-BALL-BAG</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1033'}">on</c:if>" data-d1="1" data-d2="2" data-d3="1" data-b="1002"  data-m="1030" data-s="1033"><span></span>#2-BALL-BAG</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1034'}">on</c:if>" data-d1="1" data-d2="2" data-d3="1" data-b="1002"  data-m="1030" data-s="1034"><span></span>#3-BALL-BAG</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1035'}">on</c:if>" data-d1="1" data-d2="2" data-d3="1" data-b="1002"  data-m="1030" data-s="1035"><span></span>#4-BALL-BAG</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1036'}">on</c:if>" data-d1="1" data-d2="2" data-d3="1" data-b="1002"  data-m="1030" data-s="1036"><span></span>#6-BALL-BAG</a></li>
                            </c:if>
                            
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 2 && menuDepth3 eq 2}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="2" data-d3="2" data-b="1002" data-m="1031" data-s=""><span></span>ALL</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1037'}">on</c:if>" data-d1="1" data-d2="2" data-d3="2" data-b="1002"  data-m="1031" data-s="1037"><span></span>#1-BALL-BAG</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1038'}">on</c:if>" data-d1="1" data-d2="2" data-d3="2" data-b="1002"  data-m="1031" data-s="1038"><span></span>#2-BALL-BAG</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1039'}">on</c:if>" data-d1="1" data-d2="2" data-d3="2" data-b="1002"  data-m="1031" data-s="1039"><span></span>#3-BALL-BAG</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1040'}">on</c:if>" data-d1="1" data-d2="2" data-d3="2" data-b="1002"  data-m="1031" data-s="1040"><span></span>#4-BALL-BAG</a></li>
                            </c:if>
                            
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 1}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="3" data-d3="1" data-b="1003" data-m="1093" data-s=""><span></span>ALL</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1094'}">on</c:if>" data-d1="1" data-d2="3" data-d3="1" data-b="1003"  data-m="1093" data-s="1094"><span></span>#STORM NATION</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1095'}">on</c:if>" data-d1="1" data-d2="3" data-d3="1" data-b="1003"  data-m="1093" data-s="1095"><span></span>#ROTO GRIP</a></li>
                            </c:if>
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 2}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="3" data-d3="2" data-b="1003" data-m="1042" data-s=""><span></span>ALL</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1051'}">on</c:if>" data-d1="1" data-d2="3" data-d3="2" data-b="1003"  data-m="1042" data-s="1051"><span></span>#전사티셔츠</a></li>
                            </c:if>
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 3}">
                            <li><a href="javascript:void(0);" class="moveShop on" data-d1="1" data-d2="3" data-d3="3" data-b="1003" data-m="1043" data-s=""><span></span>ALL</a></li>
                            </c:if> 
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 3 && menuDepth3 eq 5}">
                            <li><a href="javascript:void(0);" class="moveShop on" data-d1="1" data-d2="3" data-d3="5" data-b="1003" data-m="1103" data-s=""><span></span>ALL</a></li>
                            </c:if> 
                            
                            
                            
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4 && menuDepth3 eq 1}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="4" data-d3="1" data-b="1004" data-m="1044" data-s=""><span></span>ALL</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1053'}">on</c:if>" data-d1="1" data-d2="4" data-d3="1" data-b="1004"  data-m="1044" data-s="1053"><span></span>#코브라형</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1054'}">on</c:if>" data-d1="1" data-d2="4" data-d3="1" data-b="1004"  data-m="1044" data-s="1054"><span></span>#몽구스형</a></li>
                            </c:if>
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4 && menuDepth3 eq 2}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="4" data-d3="2" data-b="1003" data-m="1045" data-s=""><span></span>ALL</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1055'}">on</c:if>" data-d1="1" data-d2="4" data-d3="2" data-b="1004"  data-m="1045" data-s="1055"><span></span>#코브라형</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1056'}">on</c:if>" data-d1="1" data-d2="4" data-d3="2" data-b="1004"  data-m="1045" data-s="1056"><span></span>#몽구스형</a></li>
                            </c:if>
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4 && menuDepth3 eq 3}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="4" data-d3="3" data-b="1004" data-m="1046" data-s=""><span></span>ALL</a></li>
                            </c:if>
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 4 && menuDepth3 eq 4}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="4" data-d3="4" data-b="1004" data-m="1104" data-s=""><span></span>ALL</a></li>
                            </c:if>
                            
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 5 && menuDepth3 eq 1}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="5" data-d3="1" data-b="1006" data-m="1079" data-s=""><span></span>ALL</a></li>
                            </c:if>
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 5 && menuDepth3 eq 2}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="5" data-d3="2" data-b="1006" data-m="1080" data-s=""><span></span>ALL</a></li>
                            </c:if>
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 5 && menuDepth3 eq 3}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="5" data-d3="3" data-b="1006" data-m="1082" data-s=""><span></span>ALL</a></li>
                            </c:if>
                            
                            
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 6 && menuDepth3 eq 1}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005" data-m="1047" data-s=""><span></span>ALL</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1057'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1057"><span></span>#엄지 테이프</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1058'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1058"><span></span>#중약지 테이프</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1059'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1059"><span></span>#인서트 테이프</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1060'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1060"><span></span>#볼클리너</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1061'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1061"><span></span>#폴리싱액</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1062'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1062"><span></span>#볼타월/시소백</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1063'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1063"><span></span>#퍼프볼</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1064'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1064"><span></span>#그립색</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1065'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1065"><span></span>#로진</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1066'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1066"><span></span>#이지슬라이더</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1067'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1067"><span></span>#슈즈브러쉬</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1068'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1068"><span></span>#가위</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1083'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1083"><span></span>#덤</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1084'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1084"><span></span>#인서트</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1085'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1085"><span></span>#툴</a></li>
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '1086'}">on</c:if>" data-d1="1" data-d2="6" data-d3="1" data-b="1005"  data-m="1047" data-s="1086"><span></span>#아브랄론 패드</a></li>
                            </c:if>
                            
                            
                            
                            <c:if test="${menuDepth1 eq 1 && menuDepth2 eq 7 && menuDepth3 eq 1}">
                            <li><a href="javascript:void(0);" class="moveShop <c:if test="${searchVo.small eq '' ||  searchVo.small eq null}">on</c:if>" data-d1="1" data-d2="7" data-d3="1" data-b="1102" data-m="0" data-s=""><span></span>ALL</a></li>
                            </c:if>
                           </ul>
                       </div>
                       <div class="l_bx">
                           <p>총 <span class="ft_or"><c:out value='${totalNum }'/></span>건 (페이지 <span  class="ft_or">${currPageInfo }</span>/${pageCntInfo })</p>
                       </div>
                       <div class="r_bx">
                       	<form id="frm" name="frm" method="post">
                       	   	<input type="hidden" name="big" id="big" value="${searchVo.big }" />
                       	   	<input type="hidden" name="mid" id="mid" value="${searchVo.mid }" />
                       	   	<input type="hidden" name="small" id="small" value="${searchVo.small }" />
                       	   	<input type="hidden" id="menuDepth1" name="menuDepth1" value="${menuDepth1 }"/>
							<input type="hidden" id="menuDepth2" name="menuDepth2" value="${menuDepth2 }"/>
							<input type="hidden" id="menuDepth3" name="menuDepth3" value="${menuDepth3 }"/>
                           	<div class="schBx">
                               	<label for="schCondition" class="hide">검색</label>
                               	<label for="srch_input"><input type="text" id="srch_input" name="srch_input" value="${Srch_input }" class="w200"></label>
                               	<button type="submit" class="sb_btn">검색</button>
                           	</div>
                         </form>
                       </div>
                   </div>
                   <!-- BOARD_INFO //E -->

                   <div class="bbs_ball sub_fade">
                       <ul>
						<c:choose>
				        	<c:when test="${fn:length(shopList) > 0}">
				            	<c:forEach items="${shopList }" var="list" varStatus="status">
				            		<li>
			                           <dl>
			                                <dt>
			                               		<a href="javascript:void(0);" class="btnView" data-code="${list.hash }">
			                               		<c:if test="${list.upfile3 eq null }">
			                               			<img src="https://bowlingkoreamall.com:446/_image/_default/prd/noimg3.gif" alt="대체 이미지">
			                               		</c:if>
			                               		<c:if test="${list.upfile3 ne null }">
			                               			<img src="https://bowlingkoreamall.com:446/${list.updir }/${list.upfile3 }" alt="${list.name }">
			                               		</c:if>			                               		
			                               		</a> 
			                               	</dt>
			                                <dd class="sm_txt">
				                               <c:if test="${list.small eq '1020'}">Primier Line</c:if>
				                               <c:if test="${list.small eq '1081'}">Signature Line</c:if>
				                               <c:if test="${list.small eq '1021'}">Master Line</c:if>
				                               <c:if test="${list.small eq '1022'}">Thunder Line</c:if>
				                               <c:if test="${list.small eq '1023'}">Hot Line</c:if>
				                               <c:if test="${list.small eq '1024'}">Tropical Line</c:if>
				                               <c:if test="${list.small eq '1025'}">Ice Line</c:if>		
			                               	</dd>
			                                <dd class="tit_txt">${list.name }</dd>
			                           </dl> 
		                           </li>
				            	</c:forEach>
				           	</c:when>
				           	<c:otherwise>
				           		<p style="text-align: center;">상품이 존재하지 않습니다.</p>
				           	</c:otherwise>
				        </c:choose>
                       </ul>
                   </div>
                   <!--BBS_LIST //E-->

                    <div class="paging">
                        ${pageNav }
                    </div>
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
<c:if test="${!empty msg }" >
<script type="text/javascript">
var msg	= '${msg}'; 
if(msg != ''){
	var Form = document.frm;
	if(msg == 'wrong'){
		alert('잘못된 접급입니다');
		Form.action	= '/main.do';
		Form.submit();
	}
}
</script>
</c:if>
<jsp:include page="/client/footer.do"></jsp:include>