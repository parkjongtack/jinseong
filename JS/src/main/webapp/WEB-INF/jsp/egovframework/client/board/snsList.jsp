<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
<script type="text/javascript">

$(document).ready(function () {
	
	pagingUtil.__form			= $("#frm");
	pagingUtil.__url			= "<c:url value='/board/snsList.do'/>";

});

function searchChk() {
	$("#frm").attr("action", "<c:url value='/board/snsList.do'/>");
	$("#frm").find('#currRow').val('1');
	$("#frm").submit();
}

function goBoardView(val){
	
	$("#frmDetail").attr("action", "<c:url value='/board/snsDetail.do'/>");
	$("#ntt_id").val(val);
	$("#frmDetail").submit();
}
function chkApp(){
	
	if(isLogin == false){
		if(confirm('로그인이 필요한 페이지 입니다. 이동 하시겠습니까?')) {
			location.href = '/membership/login.do';	
			return;	
		} 
		
	} else {
		location.href = '/board/asWrite.do';
	}
}




/** 페이스북 연동 */
var feedArr = new Array(); 
var nextLink ='';
var aLink = 'https://www.facebook.com/stormrotokorea/posts/';
$(document).ready(function () {
	var token = "EAAezPygOQZC4BAOeidBC39SEP1E9kxbllesTrnOucPug01jj4G6kCTqVNHOhQ0H95o5b8qNu4LvTZC1CSHxLRa5zdp1siMwhTVQnFvItEgrYtTpM9Ti94CvYyTztWvgPahkH24TTKSr4R7D1KCSaRR9SB3Dx76UEvgZBu8VqQZDZD";
	var fUrl = "https://graph.facebook.com/v3.2/901928803198957/feed?pretty=0&fields=link%2Cfull_picture%2Cmessage%2Cupdated_time&access_token="+token;
	if(token != ""){
		requestFeed(fUrl);
	}
});

function moreFeed(){
	if(typeof nextLink == undefined || nextLink == '' || nextLink == 'undefined'){
		alert("더 이상 게시물이 존재하지 않습니다.");
	}else{
		requestFeed(nextLink);
	}
}

function requestFeed(url){
	$.ajax({
		url			:	url+"&limit=9",
		type	:	'get',
		dataType : 'json',
		success	:	function(data){
			if(data.error != null){
				alert(data.error.message);
				return;
			}
			if(data.paging.next == 'undefined' || data.paging.next == null || typeof data.paging.next == undefined){
				nextLink = '';
			}else{
				nextLink = data.paging.next;
			}
			feedArr = data.data;
			var html ='';
			if(feedArr != null && feedArr.length > 0){
				for(var i = 0; i < feedArr.length; i++){
					if(data.data[i].link == null || typeof data.data[i].link == undefined || data.data[i].link == 'undefined'){
						continue;
					}
					var data_id = feedArr[i].id.split("_");
					html += '<li>';
					html += '<a href="'+data.data[i].link+'" target="_blank" class="sns_img" style="background-image:url(' + data.data[i].full_picture + ')">image</a>';
					html += '<div class="sns_box">';
					html += '<div class="sns_text">';
					html += '<a href="'+data.data[i].link+'" target="_blank" class="sns_title">[주)진승storm&rotogrip]</a>';
					html += '<span class="sns_text_detail">'+ data.data[i].message +'</span>';
					html += '</div>';
					html += '<a href="'+data.data[i].link+'" target="_blank" class="sns_info">';
					html += '<p><img src="/resources/client/images/contents/icon_sns_date.png" alt="등록일" />' + data.data[i].updated_time.substring(0,10) + '</p>';
					html += '</a>';
					html += '</div>';
					html += '</li>';


				}
				/* $("#sns_faceb").empty(); */
				$("#sns_faceb").append(html);
			}
		}
	})

}

	




/** 인스타그램 데이터 연동 */
var ifeedArr = new Array();
var inextLink ='';
$(document).ready(function (){
	var token = "3291378607.fa8e6ba.e25a8c08e3cd418db60ee7bf161e24d1"; /* Access Tocken 입력 */  
 	var iUrl = "https://api.instagram.com/v1/users/self/media/recent/?access_token=" + token;
/* 	var token = "${token}";  
 	var iUrl = "https://api.instagram.com/v1/users/self/media/recent/?access_token=" + token; */
	if(token !=""){
		irequestFeed(iUrl);
	}
});

function imoreFeed(){
	if(typeof inextLink == undefined || inextLink == '' || inextLink == 'undefined'){
		alert("더 이상 게시물이 존재하지 않습니다.");
	}else{
		irequestFeed(inextLink);
	}
}

function irequestFeed(url) {
	$.ajax({
		/* url	:	url + "&count=8",  	 */	
		url	: 	url,
		type	:	"get",  
		dataType : "jsonp",
		cache : false,
		success: function(data) {
			if(data.error != null){
				alert(data.error.message);
				return;
			}
			
			console.log(data);
			/* console.log(data.pagination.next_url); */
			if(data.pagination.next_url == 'undefined' || data.pagination.next_url == null || typeof data.pagination.next_url == undefined) {
				inextLink = '';
			}else{
				inextLink = data.pagination.next_url;
			}
			
			ifeedArr = data.data;
			var html = '';
			if(ifeedArr != null && ifeedArr.length > 0){
				for(var i = 0; i < ifeedArr.length; i++) {
					var timestamp = data.data[i].created_time * 1000;
					var date = new Date(timestamp);
					var data_id = ifeedArr[i].id.split("_");
					html += '<li>';
					html += '<a href="'+data.data[i].link+'" target="_blank" class="sns_img" style="background-image:url(' + data.data[i].images.low_resolution.url + ')">image</a>';
					html += '<div class="sns_box">';
					html += '<div class="sns_text">';
					html += '<a href="'+data.data[i].link+'" target="_blank" class="sns_title">[주)진승storm&rotogrip]</a>';
					html += '<span class="sns_corp_name">' + data.data[i].caption.from.full_name + '</span>';
					html += '<span class="sns_text_detail">'+ data.data[i].caption.text +'</span>';
					html += '</div>';
					html += '<a href="'+data.data[i].link+'" target="_blank" class="sns_info">';
					html += '<p><img src="/resources/client/images/contents/icon_sns_heart.png" alt="좋아요" />' + data.data[i].likes.count +  '</p>';
					html += '<p><img src="/resources/client/images/contents/icon_sns_date.png" alt="등록일" />' + date.getFullYear()+"-"+(date.getMonth() + 1)+"-"+date.getDate()+ '</p>';
					html += '</a>';
					html += '</div>';
					html += '</li>';

									
														
				}
				$("#sns_insta").append(html);
			}	
		}  
	});  
}
 





function jsSns(type){
	if(type == "face"){
		$(".fc").addClass("on");
		$(".is").removeClass("on");
		$("#sns_insta").hide()
		
		$("#sns_faceb").show();
		$("#sns_faceb_more").show();
		
	}else{
		$(".fc").removeClass("on");
		$(".is").addClass("on");
		$("#sns_insta").show()
		
		$("#sns_faceb").hide();
		$("#sns_faceb_more").hide();

		}
	};


	

</script>

<form:form commandName="vo" id="frmDetail" name="frmDetail">
	<input type="hidden" name="ntt_id" id="ntt_id" />
	<input type="hidden" name="scType"  value="<c:out value='${scType }'/>"/>
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
                		<h3 class="c_tit">SNS</h3>   
                    </div>
                    
                <div class="board_list_area sub_fade hidden fadeInUp">
                	<div class="board_info">
                    	<div>
                        	<ul class="tabMenu2">
                            	<li class="fc on"><a href="javascript:jsSns('face')">Facebook</a></li>                    
                            	<li class="is"><a href="javascript:jsSns('inst')">Instagram</a></li>
                           	</ul>
                       	</div>
                   	</div>
                   	
                	<!-- BOARD_INFO //E -->
                	<!-- <ul class="sns_list sns_list_facebook" id ="sns_faceb">
						<li>
							<a href="#" target="_blank" class="sns_img" style="background-image:url('')">image</a>
							<div class="sns_box">
								<div class="sns_text">
									<a href="#" target="_blank" class="sns_title">[주)진승storm&rotogrip]</a>
									<span class="sns_corp_name">진승</span>
									<span class="sns_text_detail">▶️제13회 ⚡스톰&🍕도미노피자컵🏆 국제볼링대회 본선✌2일차 경기가 용인 레드힐볼링라운지에서 진행중입니다. ⚡스톰&🍕도미노피자컵🏆에 출전하는 국내외 프로볼러들과 지난 12월 15일(토) 2018 프리미엄 레슨이벤트를 진행하였습니다. 💖많은 관심과 성원 보내주신 볼러여러분들 감사합니다.💖 -투핸드 클리닉
									Jesper Svensson
									Osku Palermaa
									Chris Via
									Anthony Simonsen -원핸드 클리닉
									Danielle McEwan
									Liz Johnson
									Daria Pajak
																	
									#진승 #썬더레인즈 #볼링 #볼링공 #볼링백 #프로볼링 #팀스톰 #무료이벤트 #스톰도미노피자컵 #도미노피자 #투핸드 #레슨이벤트</span>
								</div>
								<a href="#" target="_blank" class="sns_info">
									<p><img src="/resources/client/images/contents/icon_sns_heart.png" alt="좋아요" />550</p>
									<p><img src="/resources/client/images/contents/icon_sns_date.png" alt="등록일" />2018-12-18</p>
								</a>
							</div>
						</li>
					</ul> -->
                	<ul class="sns_list sns_list_facebook" id ="sns_faceb"></ul>
                   	<div class="btn_r tc" id ="sns_faceb_more">
                   		<a href="javascript:moreFeed();">more</a>
                   	</div>
                   	
                	<ul class="sns_list sns_list_instagram" id ="sns_insta" style="display: none;"></ul>
                   	<div class="btn_r tc" id ="sns_insta_more" style="display: none;">
                   		
                   	</div>
                   	

                </div>
                <!-- BOARD_LIST_AREA //E -->                
                </div>
            </div>
            <!-- SUB_CONTENT //E-->
        	        
		</div>
		<!-- #CONTENTS //E -->
		
	</div> 
	
<jsp:include page="/client/footer.do"></jsp:include>