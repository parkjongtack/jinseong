$(document).ready(function(){
    
    //주메뉴
    var myWin = $(window).width();
	var header = $('#header');
	var depth1 = $('#gnb > ul > li');
	var depth2 = $('#gnb > ul > li > .depth2');
	var menubg = $('#header .depth2_bg');
	var itemW = $('.item');
	var item = $('.item > ul > li');
	var a_over = $('.item > ul > li > .a_over');

	header.each(function(){
		depth1.bind('mouseenter focusin', function(){
			depth2.css('display','block');
			menubg.css('display','block');
		});

		header.bind('mouseleave focusout', function(){
			depth2.css('display','none');
			menubg.css('display','none');
		});
	});
    
	$('.slide_inner .stop').on('click', function() {
		$('.slide_area') .slick('slickPause');
		$('.stop').hide();
		$('.play').show();
	});

	$('.slide_inner .play').on('click', function() {
		$('.slide_area').slick('slickPlay');
		$('.play').hide();
		$('.stop').show();
	}); //주메뉴 /E/
    
    
    // 볼링볼 및 용품 
	$(document).on("mouseenter", ".p_li", function(){
		$(this).find(".a_over").css('opacity','0.8');
	})
	
	$(document).on("mouseleave", ".p_li", function(){
		$(this).find(".a_over").css('opacity','0');
	})
    

	$(window).resize(function(){
		var myWin = $(window).width();
		var gnb = $('#header #m_gnb');
		if( myWin>1080 ){
			gnb.each(function(){
		
				$(this).css('display','none');
			});
			$('.m_bg').hide();
		}
	});
    
    $("#header .m_tm").on('click', function(){
		if( !$(this).hasClass('on') ){
			$(this).addClass('on');
			$('.m_bg').show();
			$('#m_gnb').show();
			$('.m_login').show();
			$('#m_gnb').animate({
				'left':'0'
			});
		}else{
			$(this).removeClass('on');
			$('.m_bg').hide();
			$('#header #m_gnb').animate({
				'left':'-100%'
			});
		}
		
	});
    $(".btn_all_close").on('click', function(){
		$('.m_bg').hide();
			$('#header #m_gnb').animate({
				'left':'-100%'
			});
	});

	
    //사이트맵
	$('.util .tm').on('click', function(){
		$('.sitemap').show();
		$('.sitemap').animate({
			'top':'150px',
			'opacity':'1'
		});
		$('.m_bg').show();
	});
    
	$('.btn_all_close').on('click', function(){
		$('.sitemap').hide();
		$('.sitemap').animate({
			'top':'0',
			'opacity':'0'
		});
		$('.m_bg').hide();
	});
    
    //모바일 서브메뉴 
    $(".snb_wrap ul li a").on('click', function(){
		if( !$(this).hasClass('on') ){
			$(this).addClass('on');
			$('.snb_depth2').show();
			$('.snb_depth2').animate({
				'top':'0'
			});
		}else{
			$(this).removeClass('on');
			$('.snb_depth2').hide();
			$('.snb_depth2').animate({
				'top':'-100%'
			});
		}
	});
    
    
 //레이어 팝업 생성
    $(".pop_btn").on('click', function(){
		if( !$(this).hasClass('on') ){
			$('.layer_pop').show();
			$('.pop_over_bg').show();
		}else{
			$(this).removeClass('on');
			$('.layer_pop').hide();
			$('.pop_over_bg').hide();
		}
	});
    
    $(".subj a").on('click', function(){
		if( !$(this).hasClass('on') ){
			$('.layer_pop').show();
			$('.pop_over_bg').show();
		}else{
			$(this).removeClass('on');
			$('.layer_pop').hide();
			$('.pop_over_bg').hide();
		}
	});
    
    $("#pop_txt").on('click', function(){
		if( !$(this).hasClass('on') ){
			$('.layer_pop4').show();
			$('.layer_pop').hide();
			$('.pop_over_bg').show();
		}else{
			$(this).removeClass('on');
			$('.layer_pop4').hide();
			$('.pop_over_bg').hide();
		}
	});
    
    $(".pop_btn2").on('click', function(){
		if( !$(this).hasClass('on') ){
			$('.layer_pop2').show();
			$('.pop_over_bg').show();
		}else{
			$(this).removeClass('on');
			$('.layer_pop2').hide();
			$('.pop_over_bg').hide();
		}
	});
    $(".pop_btn3").on('click', function(){
		if( !$(this).hasClass('on') ){
			$('.layer_pop3').show();
			$('.pop_over_bg').show();
		}else{
			$(this).removeClass('on');
			$('.layer_pop3').hide();
			$('.pop_over_bg').hide();
		}
	});
      $(".popClose").on('click', function(){
		$('.layer_pop').hide();
	});
           

});
