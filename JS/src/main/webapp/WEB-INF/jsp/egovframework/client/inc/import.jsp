<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	 request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<meta name="format-detection" content="telephone=no" />
	<title>(주)진승</title>
	<link rel="canonical" href="https://bowlingkorea.com/main.do">
	
	<meta name="description" content ="(주)진승 대표홈페이지"/>
		
	<meta property="og:type" content="website" />
	<meta property="og:title" content="(주)진승" />
	<meta property="og:url" content="" />
	<meta property="og:description" content="" />
	<meta property="og:image" content="" />
	<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="/resources/client/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="/resources/client/js/common.js"></script>
	<script type="text/javascript" src="/resources/client/js/slick.min.js"></script>
	<script type="text/javascript" src="/resources/client/js/viewportchecker.js"></script>	
	<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.tmpl.js"></script>	
	<script type="text/javascript" src="/resources/js/EgovMultiFile.js" ></script>
	<script type="text/javascript" src="/resources/js/jquery.bxslider.js" ></script>
	<script type="text/javascript" src="/resources/js/jquery.throttledresize.js" ></script>
	<script type="text/javascript" src="/resources/js/common.js" ></script>
	<script type="text/javascript" src="/resources/js/jquery.cycle.all.js" ></script>
	<script type="text/javascript" src="/resources/js/jquery.cookie.js" ></script>
	<script type="text/javascript" src="/resources/client/js/storage.js"></script>
	<script type="text/javascript" src="/resources/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
	<link rel="stylesheet" type="text/css" href="/resources/client/font/nanumsquare/nanumsquare.css" />
	<link rel="stylesheet" type="text/css" href="/resources/client/css/jquery.ui.lastest.css" />
	<link rel="stylesheet" type="text/css" href="/resources/client/css/style.css" />
	<link rel="stylesheet" type="text/css" href="/resources/client/css/slick.min.css" />
	<link rel="stylesheet" type="text/css" href="/resources/client/css/slick-theme.css" />
	<link href="/resources/client/images/common/jin_ico.ico" rel="icon" type="image/x-icon">

	

    <script>
       $(document).ready(function(){
			/* if(location.host === 'www.bowlingkorea.com'){
    		    location.origin = location.protocol + '//' + location.host;
    		    location.href = "https://bowlingkorea.com/main.do";
			}
			if (document.location.protocol == 'http:') {
    	        document.location.href = document.location.href.replace('http:', 'https:');
    	    }*/
           /* 메인슬라이드 */
           $('.slide_area').slick({
               autoplay: true,
               autoplaySpeed: 3000,
               speed: 700,
               arrows: true,
               appendDots:$(".slide_inner .controller .dots"),
               dots: true,
               customPaging: function(slider, i) {
                   return '<button type="button" class="dot"></button>';
               },
               variableWidth: false
           });
       });
       </script>
       
    <meta name="NaverBot" content="All"/>
    <meta name="NaverBot" content="index,follow"/>
    <meta name="Yeti" content="All"/>
    <meta name="Yeti" content="index,follow"/>
       
</head>
<body>
	<div id="skip-navi">
	    <a href="#contents">본문영역 바로가기</a>
	    <a href="#header">주메뉴 바로가기</a>
	    <a href="#footer">하단 영역 바로가기</a>
	</div>
	<!--#SKIP-NAVI //E-->