<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<jsp:include page="/client/header.do"></jsp:include>
<style type="text/css">
.pc_pro_options{
    display: -webkit-box;
    -webkit-line-clamp: 10;
    -webkit-box-orient: vertical;
    word-wrap: break-word;
    height: 160px;
    width: 90%;
    overflow: hidden;
}
</style>
<script type="text/javascript">
(function(a){(jQuery.browser=jQuery.browser||{}).mobile=/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4))})(navigator.userAgent||navigator.vendor||window.opera);
var mobile_yn = "N";
if(($.browser.mobile)){
	mobile_yn = "Y"
}

$(document).ready(function () {
	//볼링볼 및 용품 슬라이드
	$(".item_wrap .item ul li").css("margin-right", "10px"); //각 5번 li은 0px로 되있어서 다음버튼 누르면 살짝 이상해서 설정함.
	
	$(".item_pv, .item_nx").click(function() {
		
		//alert($(this).attr("class"));
		var productList = $("#product_con");
		
		if($(this).hasClass("item_pv")){ //이전
			
			var last = productList.children("li").last();
			last.css("margin-left", "-300px");
			productList.prepend(last);
			productList.find("li:eq(0)").animate({
				"margin-left" : "0"
            }, 800);
            
		}else{ //다음
			
			productList.find("li:eq(0)").animate({
                "margin-left" : "-240px"
            }, 800, function() {
            	$(this).removeAttr("style");
            	productList.append($(this));
            });
		}
    });
	
	//팝업
	var poplist = new Array();
	var poplist2 = new Array();
	var poplist3 = new Array();
	var poplist4 = new Array();
	var poplist5 = new Array();
	var poplist6 = new Array();
	var poplist7 = new Array();
	var poplist8 = new Array();
	
	//팝업 가로크기
	<c:forEach items="${popupList}" var="list">
		poplist.push("${list.pop_width + 10}");
	</c:forEach>
	//팝업 세로크기
	<c:forEach items="${popupList}" var="list">
		poplist2.push("${list.pop_height - 10}");
	</c:forEach>
	//팝업 seq
	<c:forEach items="${popupList}" var="list">
		poplist3.push("${list.pop_seq}");
	</c:forEach>
	//팝업 위치(x)
	<c:forEach items="${popupList}" var="list">
		poplist4.push("${list.pop_position_x}");
	</c:forEach>
	//팝업 위치(y)
	<c:forEach items="${popupList}" var="list">
		poplist5.push("${list.pop_position_y}");
	</c:forEach>
	//팝업 내용
	<c:forEach items="${popupList}" var="list">
		poplist7.push('${list.pop_content}'.replace("&nbsp;",''));
	</c:forEach>
	//팝업 링크
	<c:forEach items="${popupList}" var="list">
		poplist8.push("${list.pop_url}");
	</c:forEach>
	
	var widthto = "0";
	widthto = parseInt(widthto);
	for(var i=0; i< poplist.length; i++){
		if ( getCookie("popup"+ poplist3[i]) == "" ) {
			 winOption =" ,left=" + widthto ;
			 popupMainWin("${contextPath}/main/popup.do?pop_seq="+ poplist3[i], 'popup'+ poplist3[i], poplist[i] , parseInt(poplist2[i])+30 , winOption, poplist4[i], poplist5[i], poplist7[i], poplist8[i], poplist3[i]);
		}
		widthto += parseInt(poplist[i]) + 70;
	}
});

//팝업 기본세팅
function popupMainWin(winURL, winName, winWidth, winHeight, winOption, position_x, position_y, pop_content, pop_url , index) {
	
	var l = 16, t = 16; 	//윈도우 위치(x, y)
	var props = "";
	
	if(winOption == "") {
		props += "toolbar=0,location=0,directories=0,status=0,menubar=0,";	//윈도우 옵션
		props += "scrollbars=no,resizable=0,copyhistory=0,";
		//props += "top="+position_y+",left="+position_x+",";
	}else {
		props = winOption;
	}

	props += ",width=" + winWidth + ",height=" + winHeight + ",top=" + position_y + "left=" + position_x;

	var html=''; 
	if(mobile_yn == "Y"){
		html+='<div id="pop_layer'+index+'" style="position: absolute; width: 96%; height: '+winHeight+'px; top: 10px; left: 10px; z-index: 100;">';
		pop_content = pop_content.replace("<img","<img style='width:100%;' "); 
	}else{
		html+='<div id="pop_layer'+index+'" style="position: absolute; width: '+winWidth+'px; height: '+winHeight+'px; top: '+position_y+'px; left: '+position_x+'px; z-index: 100;">';
	}
	
	html+='<div id="pop_layer_topbar" style="height: 30px; background: #036;">';
	html+='<p class="close" style="position: absolute;top: 5px;right: 10px;width: 16px;height: 16px;border: 1px solid #fff;text-align: center;">';
	html+='<a href="javascript:goPopupClose('+index+')" style="display: block;color: #fff;">X</a>';
	html+='</p>';
	html+='</div>';
	//html+='<div onclick="goPopPageLink(' + '"' +pop_url+ '"' + ')">';
	html+="<div onclick='goPopPageLink("+'"' + pop_url + '"'+")' style='background: white;'>";
	html+= pop_content;
	html+='</div>';
	html+='<div id="pop_layer_bootombar" style="height: 30px; background: #036;">';
	html+='<p class="close" style="bottom: 0px;text-align: left;padding: 5px;">';
	html+="<input type='checkbox' style='display: inline-block;' onchange='popSetCookie("+'"'+index+'"'+");'><span style='color: white;'>오늘하루 열지 않겠습니다.</span>";
	html+='</p>';
	html+='</div>';
	html+='</div>';
	
	$("body").append(html);
	
	
	/*
	if(pop_gubun == "N"){
		var aWinObj = window.open(winURL, winName, props);
	}else{
		var html = '<div id="pop_layer'+index+'" style="position: absolute; width: '+winWidth+'px; height: '+winHeight+'px; top: '+position_y+'px; left: '+position_x+'px; z-index: 100;">';
		html+='<div id="pop_layer_topbar" style="height: 30px; background: #036;">';
		html+='<p class="close" style="position: absolute;top: 5px;right: 10px;width: 16px;height: 16px;border: 1px solid #fff;text-align: center;">';
		html+='<a href="javascript:goPopupClose('+index+')" style="display: block;color: #fff;">X</a>';
		html+='</p>';
		html+='</div>';
		//html+='<div onclick="goPopPageLink(' + '"' +pop_url+ '"' + ')">';
		html+="<div onclick='goPopPageLink("+'"' + pop_url + '"'+")' style='background: white;'>";
		html+= pop_content;
		html+='</div>';
		html+='<div id="pop_layer_bootombar" style="height: 30px; background: #036;">';
		html+='<p class="close" style="bottom: 0px;text-align: left;padding: 5px;">';
		html+="<input type='checkbox' style='display: inline-block;' onchange='popSetCookie("+'"'+index+'"'+");'><span style='color: white;'>오늘하루 열지 않겠습니다.</span>";
		html+='</p>';
		html+='</div>';
		html+='</div>';
		
		$("body").append(html);
		<!-- <div style="background: purple;width: 100%; height: 500px;"></div> -->
	}
	*/
	
	
	//var aWinObj = window.open(winURL, winName, props);
	//aWinObj.focus();
}

//쿠키 가져오기  
function getCookie(name) {  
	var nameOfCookie = name + "=";  
	var x = 0;  
	while (x <= document.cookie.length){  
	   	var y = (x+nameOfCookie.length);  
	   	if (document.cookie.substring( x, y ) == nameOfCookie) {  
	       	if ((endOfCookie=document.cookie.indexOf( ";", y )) == -1)  
	           	endOfCookie = document.cookie.length;  
	       		return unescape(document.cookie.substring( y, endOfCookie ));  
	   	}  
	   	x = document.cookie.indexOf( " ", x ) + 1;  
	   	if ( x == 0 )  
	       	break;  
		}  	
	return "";  
}  

function goPopupClose(index){
	$("#pop_layer"+index).remove();
}

function goPopPageLink(pop_url){
	if(pop_url != ''){
		if(pop_url.indexOf("gie.re.kr") > -1){
			location.href=pop_url;
		}else{
			window.open(pop_url);
		}		
	}
}

function popSetCookie(cookieValue){
   	var today = new Date();
   	today.setDate( today.getDate() + parseInt( "1" ) );
   	var expires = "expires="+today.toGMTString();
   	document.cookie = "popup"+cookieValue+"=" + escape("Y") + "; path=/;" + expires +";"
   	$("#pop_layer"+cookieValue).remove();
}

//볼링볼 및 용품 탭 이동(NEW or HOT)
function goEachTab(gubun) {
	
	$(".tit_wrap > ul > li > a").attr("class","");
	$(".tit_wrap > ul > li > #tab"+gubun).attr("class","on");
	
	//$("#manage_group").val(check);
	$.ajax({
		url 		: "<c:url value='/productList_search.do'/>",
		type		: "post",
		data		: {gubun : gubun},
		dataType 	: "json",		
		success	: function(data){
			if(data.root.resultCode == "Y"){
				var html = '';
				var pList = data.root.productList;
				var oList = data.root.optionlist;
				for(var i = 0; i < pList.length; i ++){
					html += '<li class="p_li">';
					html += '<div class="it_line"></div>';
					html += '<a href=\"javascript:void(0)\" onclick=\"goProductDetail(\''+pList[i].hash+'\',\''+pList[i].big+'\',\''+pList[i].mid+'\')\">';
					html += '<img src=\"https://bowlingkoreamall.com:446/'+pList[i].updir+'/'+pList[i].upfile3+'\" alt=\"KEGEL REVIVE BALL CLEANER\" style=\"width: 129px; height: 130px;\">';
					html += '<p class=\"name\">'+pList[i].name+'</p>';
					html += '</a>';
					html += '<div class=\"a_over btnView\" data-code=\"'+pList[i].hash+'\" onclick=\"goProductDetail(\''+pList[i].hash+'\',\''+pList[i].big+'\',\''+pList[i].mid+'\')\">';
					html += '<p class=\"a_tit\">'+pList[i].name+'</p>';
					html += '<dl>';
					html += '<dt>제조사</dt>';
					html += '<dd>'+pList[i].value+'</dd>';
					html += '</dl>';
					html += '<dl>';
					html += '<dt>옵션 : '+pList[i].options+'</dt>';
					html += '<dd class="pc_pro_options">'+pList[i].items+'</dd>';
					html += '</dl>';
					html += '</div>';
					html += '<div class=\"item_txt mVer btnView\" data-code=\"'+pList[i].hash+'\" onclick=\"goProductDetail(\''+pList[i].hash+'\',\''+pList[i].big+'\',\''+pList[i].mid+'\')\">';
					html += '<dl>';
					html += '<dt>제조사</dt>';
					html += '<dd>'+pList[i].value+'</dd>';
					html += '</dl>';
					html += '<dl>';
					html += '<dt>옵션 : '+pList[i].options+'</dt>';
					html += '<dd>'+pList[i].items+'</dd>';
					html += '</dl>';
					html += '</div>';
					html += '</li>';
				}
				$('#product_con').html(html);
			}
			console.log(data);
			//$('#product_con').html(data);
		},
		error : function(data, status, err) {
			alert("에러입니다." + data +"<><><>"+ status+"<><><>"+ err);
			return;
		}
	})
}

//볼링볼 및 용품 상세페이지 이동
function goProductDetail(hash,big,mid) {
	
	if(big == "1001"){ //볼링볼
		
		$("#menuDepth2").val("1");
	
		if(mid == "1012"){
			$("#menuDepth3").val("1");
		}else if(mid == "1013"){
			$("#menuDepth3").val("2");
		}
		
	}else if(big == "1002"){ //볼링백
		
		$("#menuDepth2").val("2");
	
		if(mid == "1030"){
			$("#menuDepth3").val("1");
		}else if(mid == "1031"){
			$("#menuDepth3").val("2");
		}
		
	}else if(big == "1003"){ //볼링의류
		
		$("#menuDepth2").val("3");
	
		if(mid == "1041"){
			$("#menuDepth3").val("1");
		}else if(mid == "1042"){
			$("#menuDepth3").val("2");
		}else if(mid == "1043"){
			$("#menuDepth3").val("3");
		}else if(mid == "1096"){
			$("#menuDepth3").val("4");
		}
		
	}else if(big == "1004"){ //볼링아대
		
		$("#menuDepth2").val("4");
	
		if(mid == "1044"){
			$("#menuDepth3").val("1");
		}else if(mid == "1045"){
			$("#menuDepth3").val("2");
		}else if(mid == "1046"){
			$("#menuDepth3").val("3");
		}
		
	}else if(big == "1006"){ //볼링슈즈
		
		$("#menuDepth2").val("5");
	
		if(mid == "1079"){
			$("#menuDepth3").val("1");
		}else if(mid == "1080"){
			$("#menuDepth3").val("2");
		}else if(mid == "1082"){
			$("#menuDepth3").val("3");
		}
		
	}else if(big == "1005"){ //볼링 악세사리
		
		$("#menuDepth2").val("6");
		$("#menuDepth3").val("1");
	}
	
	$("#hash").val(hash);	
	$("#big").val(big);	
	$("#mid").val(mid);	
	$("#frmDetail").attr("action","/shop/shopView.do");
	$("#frmDetail").submit();
}

//공지사항 상세페이지 이동
function goBoardView(val,gubun) {
	
	$("#ntt_id").val(val);
	
	if(gubun == "contest"){
		$("#frmBoardDetail").attr("action", "<c:url value='/contest/contestRstView.do'/>");
	}else if(gubun == "notice"){
		$("#frmBoardDetail").attr("action", "<c:url value='/board/noticeDetail.do'/>");
	}else{
		$("#frmBoardDetail").attr("action", "<c:url value='/contest/contestInfoView.do'/>");
	}
	
	$("#frmBoardDetail").submit();
}
</script>

<!-- 볼링볼 및 용품 관련 -->
<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="hash" id="hash" />
	<input type="hidden" id="menuDepth1" name="menuDepth1" value="1"/>
	<input type="hidden" id="menuDepth2" name="menuDepth2" value=""/>
	<input type="hidden" id="menuDepth3" name="menuDepth3" value=""/>
	<input type="hidden" id="big" name="big" value=""/>
	<input type="hidden" id="mid" name="mid" value=""/>
	<input type="hidden" name="currRow"  value="1"/>
</form:form>

<!-- 공지사항 관련 -->
<form:form commandName="vo" id="frmBoardDetail" name="frmBoardDetail">
	<input type="hidden" name="ntt_id" id="ntt_id" />
	<input type="hidden" name="currRow"  value="1"/>
</form:form>

<div id="banner_wrap">
	<div class="slide_inner">
	    <ul class="slide_area">
	    	<c:choose>
	        	<c:when test="${fn:length(bannerList) > 10000}">
	            	<c:forEach items="${bannerList }" var="list" varStatus="status">
	            		<li>
				        	<img src="/commonfile/nunFileDown.do?atch_file_id=${list.atch_file_id }0" alt="banner${status.count }"> <!-- 바탕 -->
				            <div class="ban_txt">
				            	<p class="main_tit">${list.ban_subject }</p>
				                <p class="ban_tit">${list.ban_con }</p>
				                <!-- <p class="ban_item"><img src="/resources/client/images/main/ban_item1.png" alt="진승무역용품 이미지"></p> --> <!-- 상품 -->
				                <c:if test="${list.ban_url != null && list.ban_url != '' }">
				                	<p class="ban_more"><a href="${list.ban_url }" target="_blank">more informaiton</a></p>
				                </c:if>
				            </div>
				        </li> 
	            	</c:forEach>
	            </c:when>
	            <c:otherwise>
	            	<li>
						<img src="/resources/client/images/main/ban_wrap_43.jpg" alt="banner_43" />
						<div class="ban_txt">
							<p class="ban_logo"><img src="/resources/client/images/main/logo_brand_rotogrip.png" alt="logo"></p>
							<p class="main_tit">UFO II</p>
							<p class="ban_tit">
							<!-- factory finish --><span>1500-grit Polished</span>/
							<!-- coverstock --><span> eTrax-H19™ Hybrid Reactive </span>/
							<!-- weightblock --><span>E.T.™ Core</span></p>
							<p class="ban_item"><img src="/resources/client/images/main/ban_item_43.png" alt="진승무역용품 이미지"></p>
							<p class="ban_more"><a href="https://bowlingkorea.com/shop/shopView.do?hash=D6BCB486F72AE7B5DC68B5B7DF7EC887&menuDepth1=1&menuDepth2=1&menuDepth3=1&big=1001&mid=1012&currRow=1">more informaiton</a></p>
						</div>
					</li>
	            
                   <li>
		            <img src="/resources/client/images/main/ban_wrap_42.jpg" alt="banner_42" />
		            <div class="ban_txt">
		              <p class="ban_logo"><img src="https://bowlingkorea.com/resources/client/images/main/logo_brand_storm.png" alt="logo"></p>
		              <p class="main_tit">CODE DYNAMIC™</p>
		              <p class="ban_tit">
		              <!-- factory finish --><span>1500-grit Polished</span>/
		              <!-- coverstock --><span>R3S™ Pearl Reactice</span>/
		              <!-- weightblock --><span>RAD⁴™ Core</span></p>
		              <p class="ban_item"><img src="/resources/client/images/main/ban_item_42.png" alt="진승무역용품 이미지"></p>
		              <p class="ban_more"><a href="https://bowlingkorea.com/shop/shopView.do?hash=487D4C6A324446B3FA45B30CFCEE5337&menuDepth1=1&menuDepth2=1&menuDepth3=1&big=1001&mid=1012&currRow=1">more informaiton</a></p>
		            </div>	
		          </li>  
	            	<li>
						<img src="/resources/client/images/main/ban_wrap_41.jpg" alt="banner_41" />
						<div style="letter-spacing: -0.8px" class="ban_txt">
							<p class="ban_logo"><img src="/resources/client/images/main/logo_brand_rotogrip.png" alt="logo"></p>
							<p class="main_tit" style="font-size:83px;">ATTENTION BLACK PEARL</p>
							<p class="ban_tit">
								<!-- factory finish --><span>1500-grit Polished</span>/
								<!-- coverstock --><span> Hyper-Response™ Pearl Reactive </span>/
								<!-- weightblock --><span>Momentous™ Core </span></p>
							<p class="ban_item"><img src="/resources/client/images/main/ban_item_41.png" alt="진승무역용품 이미지"></p>
							<p class="ban_more"><a href="https://bowlingkorea.com/shop/shopView.do?hash=8CA8DA41FE1EBC8D3CA31DC14F5FC56C&menuDepth1=1&menuDepth2=1&menuDepth3=2&big=1001&mid=1013&currRow=1">more informaiton</a></p>
						</div>
					</li>
					<li>
						<img src="/resources/client/images/main/ban_wrap_40.jpg" alt="banner_40" />
						<div class="ban_txt">
							<p class="ban_logo"><img src="/resources/client/images/main/logo_brand_storm.png" alt="logo"></p>
							<p class="main_tit">PROTON PHYSIX</p>
							<p class="ban_tit">
								<!-- factory finish --><span>2000-grit Abralon®</span>/
								<!-- coverstock --><span> NeX™ Solid Reactive </span>/
								<!-- weightblock --><span> Atomic™ Core </span></p>
							<p class="ban_item"><img src="/resources/client/images/main/ban_item_40.png" alt="진승무역용품 이미지"></p>
							<p class="ban_more"><a href="https://bowlingkorea.com/shop/shopView.do?hash=14EA0D5B0CF49525D1866CB1E95ADA5D&menuDepth1=1&menuDepth2=1&menuDepth3=1&big=1001&mid=1012&small=&currRow=1">more informaiton</a></p>
						</div>
					</li>
            		<li>
						<img src="/resources/client/images/main/ban_wrap_39.jpg" alt="banner_39" />
						<div class="ban_txt">
							<p class="ban_logo"><img src="/resources/client/images/main/logo_brand_rotogrip.png" alt="logo"></p>
							<p class="main_tit">RST X-1</p>
							<p class="ban_tit">
							<!-- factory finish --><span>3000 Abralon®</span>/
							<!-- coverstock --><span>  MicroTrax Hybrid Reactive </span>/
							<!-- weightblock --><span> RST™ Core </span></p>
							<p class="ban_item"><img src="/resources/client/images/main/ban_item_39.png" alt="진승무역용품 이미지"></p>
							<p class="ban_more"><a href="https://bowlingkorea.com/shop/shopView.do?hash=C3535FEBAFF29FCB7C0D20CBE94391C7&menuDepth1=1&menuDepth2=1&menuDepth3=2&big=1001&mid=1013&small=&currRow=1">more informaiton</a></p>
						</div>
					</li>
	            </c:otherwise>
	        </c:choose>        
	    </ul>
	    <!--SLIDE_AREA //E-->
	    <div class="controller">
	       	<div class="dots"></div>
	       	<div class="contBtn">
	       		<a href="#" class="play" style="display:none;"></a><!--클래스 stop/ play로 제어-->
	       		<c:if test="${fn:length(bannerList) > 1}">
	       			<a href="#" class="stop"></a><!--클래스 stop/ play로 제어-->
	       		</c:if>
	       	</div>
	    </div>
	    <!--CONTROLL //E-->
	</div> 
	<!--SLIDE_INNER //E-->
            
    <div class="icon_menu">
        <ul>
            <li onclick="location.href='/contest/contestAppList.do'"><a href="javascript:void(0)"><span></span>볼링대회 신청</a></li>
            <li class="on"  onclick="location.href='/contest/contestRst.do'"><a href="javascript:void(0)"><span></span>대회결과</a></li>
            <li onclick="location.href='/board/asList.do'"><a href="javascript:void(0)"><span></span>AS신청</a></li>
            <li onclick="location.href='/board/snsList.do'"><a href="javascript:void(0)"><span></span>진승 SNS</a></li>
            <li onclick="location.href='/contents/mapinfo.do'"><a href="javascript:void(0)"><span></span>오시는 길</a></li>
            <li onclick="location.href='/board/consultList.do'"><a href="javascript:void(0)"><span></span>고객상담</a></li>
        </ul>
    </div>
    <!--//ICON_MENU //E-->
</div>
<!--#BANNER_WRAP //E-->
	
	<div id="container">
		<div id="contents" class="m_con">
		<div class="inner">
			<h2 class="hide">본문</h2>
			<div class="item_wrap">
			    <div class="tit_wrap">
			        <p class="tit">볼링볼 및 용품</p>
			        <ul>
			            <li><a href="javascript:void(0)" onclick="goEachTab('new')" id="tabnew" class="on">New Product</a></li>
			            <li><a href="javascript:void(0)" onclick="goEachTab('hot')" id="tabhot">Hot Product</a></li>
			        </ul>
			    </div>
			    <!--TIT_WRAP //E-->
			    <div class="item">
			        <ul id="product_con">
			        	<c:choose>
			        		<c:when test="${fn:length(productList) > 0 }">
			        			<c:forEach items="${productList }" var="list">
						        	<li class="p_li">
			                        	<div class="it_line"></div>
						                <a href="javascript:void(0)" onclick="goProductDetail('${list.hash}','${list.big }','${list.mid }')"> <!-- pc 버전 아닐 때 -->
						                    <img src="https://bowlingkoreamall.com:446/${list.updir }/${list.upfile3 }" alt="${list.name }" style="width: 129px; height: 130px;">
			                                <p class="name">${list.name }</p> <!-- ${list.big } ${list.mid } -->
						                </a>
						                <div class="a_over btnView" data-code="${list.hash }" onclick="goProductDetail('${list.hash}','${list.big }','${list.mid }')">
						                   	<p class="a_tit">${list.name }</p>
									       	<dl>
						                       	<dt>제조사</dt>
						                       	<dd>${list.value }</dd>
						                   	</dl>
						                   	<dl>
						                       	<dt>옵션 : ${list.options }</dt>
						                       	<dd class="pc_pro_options">${list.items }</dd>
						                   	</dl>
						                </div>
			                            <div class="item_txt mVer btnView" data-code="${list.hash }" onclick="goProductDetail('${list.hash}','${list.big }','${list.mid }')">
			                               	<dl>
						                       	<dt>제조사</dt>
						                       	<dd>${list.value }</dd>
						                   	</dl>
						                   	<dl>
						                       	<dt>옵션 : ${list.options }</dt>
						                       	<dd>${list.items }</dd>
						                   	</dl>
			                            </div>
						            </li>
			        			</c:forEach>
			        		</c:when>
			        		<c:otherwise>
			        			
			        		</c:otherwise>
			        	</c:choose>
			            
			        </ul>
			    </div>
			    <!--ITEM //E-->
			    <div class="item_ctrl">
			        <a href="javascript:void(0)" class="item_pv"></a>
			        <a href="javascript:void(0)" class="item_nx"></a>
			    </div>
			    <!--ITEM_ARR //E-->
			    
      		</div>
      		<!--ITEM_WRAP //E-->
      		
      		<div class="bbs_wrap">
                 <div class="bbs">
                     <div class="news"> 
                         <div class="tit_wrap">
                             <p class="tit">대회결과</p>
                             <a href="/contest/contestRst.do" class="more">더보기</a>
                         </div>
                         <!--TIT_WRAP //E-->
						<c:choose>
				        	<c:when test="${fn:length(contestList1) > 0}">
				            	<c:forEach items="${contestList1 }" var="list" varStatus="status">
				            		<div class="news_con">
			                             <dl>
			                                 <dt>
			                                 	<a href="javascript:void(0);" onclick="goBoardView('${list.ntt_id}','contest')">
			                                 		<c:if test="${list.atch_file_id ne null && list.atch_file_id ne '' }">
					                            		<img src="/commonfile/thumbFileDown.do?atch_file_id=${list.atch_file_id }" alt="대표이미지"/>
					                            	</c:if>
					                            	<c:if test="${list.atch_file_id eq null || list.atch_file_id eq '' }">
					                            		<img src="/resources/client/images/common/noimg.gif" alt="대표이미지"/>
					                            	</c:if>
					                            	<!-- <img src="/resources/client/images/common/noimg.gif" alt="뉴스이미지"> -->
			                                 	</a>
			                                 </dt>
			                                 <dd class="txt">${list.ntt_sj }</dd>
			                                 <dd class="date">${list.reg_dt }</dd>
			                             </dl>
			                         </div>
				            	</c:forEach>				            	
				           	</c:when>
				         </c:choose>
                          
                         <!--NEWS_CON //E-->
                        <ul class="news_list">
							<c:choose>
					        	<c:when test="${fn:length(contestList2) > 0}">
					            	<c:forEach items="${contestList2 }" var="list" varStatus="status" end="2">
					            	<li><a href="javascript:void(0);" onclick="goBoardView('${list.ntt_id}','contest')">${list.ntt_sj }</a> <span class="date">${list.reg_dt }</span></li>
					            	</c:forEach>
					           	</c:when>
					        </c:choose>                                                                                
                        </ul>  
                        <!--NEWS_LIST //E-->   

                     </div>
                     <!--NEWS //E-->   

                     <div class="notice"> 
                         <div class="tit_wrap">
                             <p class="tit">공지사항</p>
                             <a href="/board/noticeList.do" class="more">더보기</a>
                         </div>
                         <!--TIT_WRAP //E-->

                         <c:choose>
				        	<c:when test="${fn:length(noticeList1) > 0}">
				            	<c:forEach items="${noticeList1 }" var="list" varStatus="status">
				            		<div class="news_con">
			                             <dl>
			                                 <dd class="txt"><a href="javascript:void(0);" onclick="goBoardView('${list.ntt_id}','notice')">${list.ntt_sj }</a></dd>
			                                 <dd class="date">${list.reg_dt }</dd>
			                             </dl>
			                         </div>
				            	</c:forEach>				            	
				           	</c:when>
				         </c:choose>
                         <!--NEWS_CON //E-->

                         <ul class="news_list">
							<c:choose>
					        	<c:when test="${fn:length(noticeList2) > 0}">
					            	<c:forEach items="${noticeList2 }" var="list" varStatus="status">
					            		<li><a href="javascript:void(0);" onclick="goBoardView('${list.ntt_id}','notice')">${list.ntt_sj }</a> <span class="date">${list.reg_dt }</span></li>
					            	</c:forEach>
					           	</c:when>
					        </c:choose>                                                                                
                        </ul>  
                        <!--NEWS_LIST //E-->   

                     </div>
                     <!--NOTICE //E-->
      		     </div>   
      		     
      		     <div class="hotline_wrap">
      		         <div class="tit_wrap">
      		             <p class="ct_tit"><img src="/resources/client/images/main/hotline.png" alt="핫라인"></p>
      		         </div>
      		         <!--TIT_WRAP //E-->
      		         
      		         <div class="hotline_con">
                         <div class="hotline">
                         	<dl class="hot_st">
                         		<dt>Storm<br/>Catalog</dt>
                                <dd class=""><a href="/html/pdf/STORM_2018_Product_Catalog_FULL_LR.pdf" target="_blank">더보기</a></dd>
                            </dl>
      		             </div>
      		             <div class="hotline">
      		             	 <dl class="hot_pg">
                                 <dt>Roto Grip<br/>Catalog</dt>
                                 <!-- <dd class=""><a href="/html/pdf/ROTO_2018Catalog_Full_v2r8_fnl_lr.pdf" target="_blank">더보기</a></dd> -->
                                 <dd class=""><a href="/html/pdf/ROTO_2019Catalog_LR3_19072.pdf" target="_blank">더보기</a></dd>
                             </dl>
                         </div>
                         <div class="hotline">
                            <dl class="hot_js">
                                <dt>KEGEL<br/>Catalog</dt>
                                <dd class=""><a href="/html/pdf/Kegel_Products_Parts_Catalog_1_5_2019.pdf" target="_blank">더보기</a></dd>
                            </dl>
                         </div>
      		         </div>
      		         <!--HOTLINE_CON //E-->
      		     </div>
      		     <!--HOTLINE //E-->
      		     
      		</div>
      		<!--BBS_WRAP //E-->
      		
		</div>
		<!-- #CONTENTS //E -->
		</div>
	</div> 
	<!-- #CONTAINER //E -->		
<jsp:include page="/client/footer.do"></jsp:include>