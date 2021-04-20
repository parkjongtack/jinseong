function mask(){
	$(".darkBg").width($(window).width());
	$(".darkBg").height($(document).height());
};

$(window).load(function(){
	var h = $("#container").outerHeight();
	$("#lnb").height(h+50);
})	;
$(document).ajaxStop(function(){
	var h = $("#container").outerHeight();
	$("#lnb").height(h+50);
	mask();
});

jQuery(function($){	
	
	/* 공통 모달 팝업 */
	$(".open_modal").live("click", function(){
		var popHtml = "<div class='pop_modal'>" +
						"<p><strong>알림</strong></p>" +
						"<div class='cont'>" +
							"<div class='pop_btn_area'><a href='#' class='btns_normal modal_ok'>확인</a></div>"+
							"<a href='#' class='modal_close'>닫기</a>"+
						"</div>"+
					"</div>";
		
		$(popHtml).appendTo("#container").fadeIn();
		
		pop($(".pop_modal"),$("#container"));
	})
	
	$(".modal_close, .modal_ok").live("click", function(){
		$(".pop_modal").fadeOut();
	})
	
	
	/* 평생학습강좌 팝업 */
	$('.btn_close').bind('click',function(){
		$('.darkBg,.showLayer').hide();
			
		return false;
	});
		
	
	$(window).resize(function(){
		mask();
		pop($(".showLayer"),$(window));
	})
	
	function pop(modal,default_p){
		modal.css({
			left : ( default_p.scrollLeft() + (default_p.width() - modal.width()) / 2 ),
			top : ( default_p.scrollTop() + (default_p.height() - modal.height()) / 2 )
		})
	}
	
	
	/* 오픈멘토링 댓글 달기 */
	$(".border_list_li3 .btns_normal").live("click",function(){
		$commBox = $(this).parents(".p_div").next(".sub_border_list");
		if($commBox.is(":visible")){ return; }else{
			$commBox.stop().slideDown();
		}
		
		return false;
	})
	
	$(".sub_question_div .btns_normal2").live("click",function(){
		$(this).parents(".sub_border_list").stop().slideUp();	
		
		return false;
	})
	
	
	/* start -layer_pop */
	/*
	function openLayer(){
		$cont = $("#container");
		$cont.append($("" +
				"<div class='layer_pop'>" +
					"<h3 class='tit'>레이어 팝업</h3>"+
					"<div class='layer_body'>내용</div>"+
					"<a href='#' class='btns_normal2 btn_close_layer'>닫기</a>"+
				"</div>"
				).fadeIn());
		$div = $(".layer_pop"); 			
		
		$div.css("position","absolute");
		$div.css("top", Math.max(0, (($(window).height() - $div.outerHeight()) / 2) + $(window).scrollTop()) + "px");
		$div.css("left", Math.max(0, (($cont.width() - $div.outerWidth()) / 2) + $cont.scrollLeft()) + "px");
	
	}
	
	function closeLayer(){
		$(".layer_pop").fadeOut();	
	}
	
	$(".layer_open").live("click",function(){
		openLayer();
	})
	
	$(".btn_close_layer").live("click",function(){
		closeLayer();
	})
	*/
	/* end -layer_pop */
	
	/*$("#wrap").append("<div class='bg_lng'></div>");*/
	$("#navi>ul>li").on("mouseenter", function(){
			$(this).find("img").attr("src", $(this).find("img").attr("src").replace(".png","_on.png"));
			$(this).addClass("on");
		}).on("focusin", function(){
			//$(this).find("img").attr("src", $(this).find("img").attr("src").replace(".png","_on.png"));
			$(this).addClass("on");
		}).on("mouseleave focusout", function(){
			$(this).find("img").attr("src", $(this).find("img").attr("src").replace("_on.png",".png"));
			$(this).removeClass("on");
	}); 
	$("#navi>ul>li>ul").on("mouseleave", function(){
			$(this).hide();
	});

	$("#navi").on("mouseenter", function(){
			//$("#lnb").addClass("on");
			$(".bg_lng").show();
			$(".bg_lng").animate({left:'+=190px'},500,'swing');
			var lnbHeight = $("#lnb").height();
			$(".bg_lng").css({"height":lnbHeight});
			$("#lnb").stop().animate({
				width: "320px"
			},500,'swing');
			$("#wrap").animate({ "width" : "+=130px" });
		}).on("mouseleave", function(){
			//$("#lnb").removeClass("on");
			$(".bg_lng").animate({left:'-=190px'},250,'swing');
			$(".bg_lng").hide();
			$("#navi>ul>li>ul").hide();
			$("#lnb").stop().animate({
				width: "198px"
			},250,'swing');
			$("#wrap").animate({ "width" : "-=130px" });
	}); 

	$(".btn_set_view").click(function(){
		if($(this).hasClass("on")==true){
			$(this).removeClass("on");
			$(this).parent().next().hide();
		}else if($(this).hasClass("on")==false){
			$(this).addClass("on");
			$(this).parent().next().show();
		}
		return false;
	});

	$(".btn_detail").click(function(){
		if($(this).hasClass("on")==true){
			$(this).removeClass("on");
			$(this).next().hide();
		}else if($(this).hasClass("on")==false){
			$(this).addClass("on");
			$(this).next().show();
		}
		return false;
	});
	//프로그램 상세설명
	$(".program_detail").click(function(){
		if($(this).hasClass("on")==true){
			$(this).removeClass("on");
			$(this).next().hide();
		}else if($(this).hasClass("on")==false){
			$(this).addClass("on");
			$(this).next().show();
		}
		return false;
	});

	//시군선택
	$(".js_select_si, .select_si").click(function(){
		
		var pop = $(this).next(".select_si_box");
		
		if(pop.is(":visible")){
			pop.stop().slideUp();
		}else{
			pop.stop().slideDown();
		}
	
		/* if($(this).hasClass("on")==true){
			$(this).removeClass("on");
			$(this).next().hide();
		}else if($(this).hasClass("on")==false){
			$(this).addClass("on");
			$(this).next().show();
		} */
		return false;
	});
	
	// 지역변경
	$(".near_organ_change .btn_organ").click(function(){
		if($(this).hasClass("on")==true){
			$(this).removeClass("on");
			$(this).next().hide();
		}else if($(this).hasClass("on")==false){
			$(this).addClass("on");
			$(this).next().show();
		}
		return false;
	});

	// 탭메뉴
	$(".tabjs li").click(function(){
		$(".tabjs li").removeClass("on");
		$(this).addClass("on");
		return false;
	});
	

	// 탭메뉴
	$(".mentoring_tab li").click(function(){
		$(".mentoring_tab li").removeClass("on");
		$(this).addClass("on");
		return false;
	});
	
	
	// 셀렉트박스		
	$('.select').click(function(){
		if($(this).hasClass("on")){
			return;
		}else{
			$(this).addClass('on');
		}
	});
		
	$('.select.on').live("mouseleave",function(){
		$(this).removeClass('on');
	})
	
	$('.select').find('li:last-child a').blur(function(){
		$(this).parent('li').parent('ul').parent('div').removeClass('on');
	});
	
	$('.select ul li a').live('click',function(){
		$(this).parents(".select").find("a:first").text($(this).text());
		$(this).parents(".select").removeClass('on');
		$(this).parents(".select").find("a:first").focus();
		
		var selected = $(this).parent().index()+1;
		
		$(this).parents(".select").find("select option").each(function(){
			$(this).removeAttr("selected");
		});
		$(this).parents(".select").find("select option").eq(selected).attr("selected", "selected");
		
		return false;
	})
	
	// 슬라이드배너
		$("#rolling_banner").carouFredSel({
		items : 3,
		direction : "left",
		prev: "#prev_rolling_banner",
		next: "#next_rolling_banner",
		scroll: 1,
		circular: false,
		infinite: false,
		auto: false,
		width:135,
		height:72
		});

		// 내재능뽐내기 슬라이드
		$("#talentImg_list").carouFredSel({
		items : 1,
		direction : "left",
		prev: "#pre_talent",
		next: "#next_talent",
		scroll: 1,
		circular: true,
		infinite: true,
		auto: true,
		width:375,
		height:245
		});

	// 메인탭
	$('#main_tab01 > ul > li > a').click(function(){
		$('#main_tab01 > ul > li > a').removeClass('on');
		$(this).addClass('on');
		$('#main_tab01 > ul > li > ul').css('display','none');
		$(this).next('ul').css('display','block');
		return false;
	});

	// 경기 일자리 플러스탭
	$('.jop_plus > ul > li > a').click(function(){
		$('.jop_plus > ul > li > a').removeClass('on');
		$(this).addClass('on');
		$('.jop_plus > ul > li > ul').css('display','none');
		$(this).next('ul').css('display','block');
		return false;
	});

	// 소통과 공감탭
	$('.under_standing > ul > li > a').click(function(){
		$('.under_standing > ul > li > a').removeClass('on');
		$(this).addClass('on');
		$('.under_standing > ul > li > dl').css('display','none');
		$(this).next('dl').css('display','block');
		return false;
	});

	// 경기도 전용학습
		$("#txt_rolling").carouFredSel({
		items : 1,
		direction : "left",
		prev: "#pre_txt_rolling",
		next: "#next_txt_rolling",
		scroll: 1,
		circular: true,
		infinite: true,
		auto: false,
		width:278,
		height:200
		});
	
		
		//메인비주얼 배너
		$('.top_cont').hover(function(){
			$('.prev_button').css('opacity','100');
			$('.next_button').css('opacity','100');
		},function(){
			$('.prev_button').css('opacity','0');
			$('.next_button').css('opacity','0');
		});
		
	// 메인비주얼
	jQuery.fn.viewStack = function(options) {
		options = jQuery.extend({'useRandom':true,'duration':3000}, options);

		var btnPlay = $(options.play);
		var btnStop = $(options.stop);
		var banners = this;
		var buttons = $(options.buttons);
		var interval = -1;
		var current = 0;

		banners.hide();
		
		$(banners[0]).show();
		$(buttons[0]).addClass('on');

		buttons.each(function(idx, item) {
			$(item).attr('index', idx);
		});

		btnPlay.click(function() {
			if(interval > -1) return false;

			current++;
			interval = setInterval(play, options.duration);

			return false;
		});

		btnStop.click(function() {
			if(interval > -1) {
				clearInterval(interval);
				interval = -1;
			}

			return false;
		});

		buttons.click(function(event) {
			if(interval > -1) clearInterval(interval);

			current = parseInt($(event.currentTarget).attr('index'));
			play();
			interval = setInterval(play, options.duration);

			return false;
		});

		function next() {
			if(buttons.length > current+1)
				current++;
			else
				current=0;
		}

		function play() {
			buttons.removeClass('on');
			$(buttons[current]).addClass('on');
			banners.hide();
			$(banners[current]).show();
			
			next();
		}

		btnPlay.click();
	};

	
	
	 
});


// navi
	menustart = function(DivName, menuseq) {
		$("#" + DivName + ">ul>li").each(function(idx, item) {
			item = $(item);
			item.find(">ul").hide();

		});

		$("#" + DivName + ">ul>li>a").hover(
			function(event) {
				onHover(event.currentTarget);
			},
			function(event) {
				onOut(event.currentTarget);
			}
		);

		$("#" + DivName + ">ul>li>a").focus(
			function(event) {
				onHover(event.currentTarget);
			}
		);

	};

	function onHover(item) {
		if(item.tagName == 'A')
			item = item.parentNode;

		item = $(item);
		
		item.parent().find(">li>ul").hide();
		item.parent().find(">li>a").each(
			function(idx, item) {
				item = $(item);
			}
		);

		item.find(">ul").parent().addClass("on");
		item.find(">ul").show();
	}

	function onOut(item) {
		item = $(item);

		item.parent().find(">li>a").each(
			function(idx, item) {
				item = $(item);
			}
		);
		item.find(">ul").hide();

	}

// 팝업배너
	jQuery(document).ready(function(){
	var photo_list = jQuery('.pop_img li');
	var photo_list_left = jQuery('.prev_pop');
	var photo_list_right = jQuery('.next_pop');
	var photo_list_play = jQuery(".play_pop");
	var photo_list_stop = jQuery(".stop_pop");
	var photo_list_current_p = 0;
	var photo_paging = 3;
	var d_time = 2200;

	var clearenter;
	photo_list.hide()
	.find('.lage').hide();
	jQuery(photo_list[0]).find('.lage').show();
	for ( var i = 0 ; i < photo_paging ; i ++ )
	{
		jQuery(photo_list[i]).show();
	}
	photo_list.find('button').bind('keyup click' , function(){ //팝업리스트 아이콘
		clearInterval(clearenter);
		photo_list.find('img').hide();
		photo_list.removeClass("on");
		jQuery(this).parent().addClass("on");
		jQuery(this).parent().find("img").show();
		photo_list_current_p = jQuery(photo_list).find('button').index(this);
		return false;
	});
	photo_list_right.bind('click' , function(){
		clearInterval(clearenter);
		photo_list_current_p += 1;
		if ( photo_list_current_p > photo_paging-1)
		{
			//alert('마지막 목록입니다')
			photo_list_current_p = 0;
			photo_list.find('img').hide();
			photo_list.removeClass("on");
			jQuery(photo_list[photo_list_current_p]).find('img').show();
			jQuery(photo_list[photo_list_current_p]).addClass("on");
		}else{
			photo_list.find('img').hide();
			photo_list.removeClass("on");
			jQuery(photo_list[photo_list_current_p]).find('img').show();
			jQuery(photo_list[photo_list_current_p]).addClass("on");
		}
	})
	photo_list_left.bind('click' , function(){
		clearInterval(clearenter);
		if (photo_list_current_p == 0)
		{
			//alert('처음 목록입니다')
			photo_list_current_p = $(photo_list).length-1;
			photo_list.find('img').hide();
			photo_list.removeClass("on");
			jQuery(photo_list[photo_list_current_p]).find('img').show();
			jQuery(photo_list[photo_list_current_p]).addClass("on");
		}else{
			photo_list_current_p -= 1
			photo_list.find('img').hide();
			photo_list.removeClass("on");
			jQuery(photo_list[photo_list_current_p]).find('img').show();
			jQuery(photo_list[photo_list_current_p]).addClass("on");
		}
	})
	photo_list_play.bind('click',function(){
		clearInterval(clearenter);
		clearenter = setInterval(photolist_play,d_time);
	})
	photo_list_stop.bind('click',function(){
		clearInterval(clearenter);
	});
	function photolist_play(){
		if (photo_list_current_p < photo_paging-1)
		{
			photo_list_current_p +=1;
		}else{
			photo_list_current_p = 0;
		}
		photo_list.find('img').hide();
		photo_list.removeClass("on");
		jQuery(photo_list[photo_list_current_p]).find('img').show();
		jQuery(photo_list[photo_list_current_p]).addClass("on");
	}
	clearenter = setInterval(photolist_play,d_time);
});


jQuery(function(){
	
	var article = $('.faq .article');
	article.addClass('hide');
	article.find('.a').slideUp(100);
	
	$('.faq .article .trigger').click(function(){
		var myArticle = $(this).parents('.article:first');
		if(myArticle.hasClass('hide')){
			article.addClass('hide').removeClass('show'); // 아코디언 효과를 원치 않으면 이 라인을 지우세요
			article.find('.a').slideUp(100); // 아코디언 효과를 원치 않으면 이 라인을 지우세요
			myArticle.removeClass('hide').addClass('show');
			myArticle.find('.a').slideDown(100);
		} else {
			myArticle.removeClass('show').addClass('hide');
			myArticle.find('.a').slideUp(100);
		}
	});
	
	$('.faq .hgroup .trigger').click(function(){
		var hidden = $('.faq .article.hide').length;
		if(hidden > 0){
			article.removeClass('hide').addClass('show');
			article.find('.a').slideDown(100);
		} else {
			article.removeClass('show').addClass('hide');
			article.find('.a').slideUp(100);
		}
	});
	
});

/*********************** k_add **************************/	
jQuery(function(){
	/** /portal/jsp/c-4-2-1.jsp 시군선택 팝업 **/
	/*
	$(".sel_table td a").not('.sel_all a').click(function(){	
		if($(this).parent().hasClass("on")){
			$('.sel_all a').removeClass("on");
			$(this).parent().removeClass("on");
		}else{ 
			if($(this).parents(".sel_table").hasClass("one")){
				$(this).parents(".sel_table").find("td").each(function(){
					$(this).removeClass("on");
				});
			}
			$(this).parent().addClass("on"); 
		}
		return false;
	});

	$(".sel_all a").click(function(){
		if($(this).hasClass("on")){
			$(this).removeClass("on");
			$(".sel_table td").not('.sel_all').each(function(){
				$(this).removeClass('on');
			});
		}else{
			$(this).addClass("on");
			$(".sel_table td").not('.sel_all').each(function(){
				$(this).addClass('on');
			});
		}
		
		return false;
	});*/
	$(".pop_close").click(function(){
		$(".select_si").removeClass("on");
		$(this).parent().fadeOut();
		
		$(".sel_table td, .sel_all a").each(function(){
			$(this).removeClass('on');
		})
		return false;
	})
	
	/***** 학습동아리신청 *****/
	/** 필드 추가 **/
	$(".design_k .btn_add").live('click',function(){
		//$add_html = $('.d_tbody tr:last').clone();
		$add_html2 = $('.d_tbody tr:last').clone();
		
		
		$add_html = $('.d_tbody').find('tr').eq(1).clone();
		$add_html.find("input[type='text']").val("");
		$add_html.find("input[type='text']").attr("readonly",false);
		var num = $('.d_tbody').find("tr").length;
		var name = $('.d_tbody').find(".date:last").attr("name");

		$add_html.find(".date").attr("id", name + num);
		$add_html.find(".date").removeClass('hasDatepicker').datepicker({showOn: 'focus', dateFormat: 'yymmdd'});
		$add_html.find("select").val($('.d_tbody tr:first').find("select").val());
		$add_html.find("select").attr("disabled",true);
		$add_html.find("select").addClass("selectDis");
		$add_html.find("input[type='checkbox']").attr("checked", false);
        $('.d_tbody').append($add_html);
        //$add_html2.css("display", "none");
        $add_html2.find("#addr").attr("disabled", true);
        $add_html2.find("#addr").val("");
        $add_html2.find("#non_mem").val("N");
        $('.d_tbody').append($add_html2);
		//$add_html.find("td:first").html(parseInt($add_html.find("td:first").html())+1);
        //alert($("input[name='user_name']").length);
        $add_html.find("td:first").html($("input[name='user_name']").length);
		if($add_html.find("td:last").find("a").length==1){
		  var html ="<a data-href='#dialog21' class='fn_confirm'><span class='btn_ok'>확인</span></a>";
		   $add_html.find("td:last").find("a").after(html);
		}
		$add_html.find("td:last").find("a:eq(1)").attr("num",parseInt($add_html.find("td:first").html()));
		
		
		
		return false;
	})

	/** 필드 삭제 **/
	/*
	$(".design_k .btn_del").live("click" ,function(){
		
		//var idx = parseInt($(this).parent().parent().find("td:first").text()) - 2;
		//alert($(this).parent().parent().find("td:first").html());
		//alert(idx);
		
		$(this).parents().parents().find(".d_tbody tr:last").remove();
		$(this).parents("tr").remove();
		
		$(".d_tbody tr").not(".d_tbody tr:first").each(function(i){
			$(this).find(".d_tbody tr:first").html(i+2);
			$(this).find(".d_tbody tr:last").find("a:eq(1)").attr("num",i+1);
			
		})
		return false;
	})
	*/
	/***** 학습강사신청 *****/
	/** 필드 추가 **/
	$(".design_k .techtree3 .btn_add2").live('click',function(){
		var thisTable = $(this).parents('.techtree3');
		$add_html = thisTable.find("tr:last").clone().removeClass();
		$add_html.find("input[type='text']").val("");
		$add_html.find(".fileUpload").prev("span").html("");
		var id_uploadTxt = thisTable.find(".fileUpload:first").prev("span").attr("id");
		
		var num = thisTable.find("tr").length -1;
		var name = thisTable.find(".date").attr("name");
		
		var nm   = $add_html.find(".fileUpload").attr("name");
	
		$add_html.find(".date").attr("id", name + num);
		$add_html.find(".date").removeClass('hasDatepicker').datepicker({showOn: 'focus', dateFormat: 'yymmdd'});
		$add_html.find(".fileUpload:last").attr("data-bid", id_uploadTxt);
		$add_html.find(".fileUpload:last").attr("name", nm);
		$add_html.find(".fileUpload:last").prev("span").attr("id", id_uploadTxt + num);
		
        thisTable.append($add_html);
		thisTable.find("th:first").attr("rowspan", parseInt(thisTable.find("th:first").attr("rowspan")) + 1);
		
		return false;
	});
	
	$(".design_k .techtree3 .btn_add3").live('click',function(){
		var thisTable = $(this).parents('.techtree3');
		$add_html = thisTable.find("tr:last").clone().removeClass();
		$add_html.find("input[type='text']").val("");
		$add_html.find(".FileULoadAction").prev("span").html("");
		var id_uploadTxt = thisTable.find(".FileULoadAction:first").prev("span").attr("id");
		var num = thisTable.find("tr").length -1;
		var name1 = thisTable.find(".start:first").attr("name");
		var name2 = thisTable.find(".end:first").attr("name");
		
		$add_html.find(".start").attr("id", name1 + num);
		$add_html.find(".end").attr("id", name2 + num);
		$add_html.find(".start").removeClass('hasDatepicker').datepicker({showOn: 'focus', dateFormat: 'yymmdd',
			onSelect: function(selectedDate) {
				$add_html.find(".end").datepicker("option","minDate", selectedDate);
	        }
		});
		$add_html.find(".end").removeClass('hasDatepicker').datepicker({showOn: 'focus', dateFormat: 'yymmdd',
			onSelect: function(selectedDate) {
				$add_html.find(".start").datepicker("option","maxDate", selectedDate);
	        }
		});
		$add_html.find(".FileULoadAction:last").attr("data-bid", id_uploadTxt + num);
		$add_html.find(".FileULoadAction:last").prev("span").attr("id", id_uploadTxt + num);
		
        thisTable.append($add_html);
		thisTable.find("th:first").attr("rowspan", parseInt(thisTable.find("th:first").attr("rowspan")) + 1);
		
		return false;
	})

	/** 필드 삭제 **/
	$(".design_k .techtree3 .btn_del").live('click',function(){
		$(this).parents("tr").remove();
		var thisTable = $(this).parents('.techtree3');
		thisTable.find("th:first").attr("rowspan", parseInt(thisTable.find("th:first").attr("rowspan")) - 1);
		
		return false;
	})
	
	$(".tab_js dt").click(function(){
		$(this).parent().find('> dd.visible').removeClass('visible');
		$(this).parent().find('> dt a.on').removeClass('on');
		$(this).next('dd').addClass('visible');
		$(this).find('a').addClass('on');
		
		return false;
	})
	
	/******** tab  *******/
	
	$(".tab").each(function(){
		var liLength = $(this).find("li").length;
		$(this).addClass("tab_list0"+parseInt(liLength));		
	})
	
	
	
	/******** normal_table  *******/
	$(".normal_table tr:odd").addClass("bg_odd_td");
	
	/******* main_slide **********/
	$(".btn_slide").click(function(){
		var slide = $(this).parents("dd").find(".slide");
		var listWidth = slide.find(">li").outerWidth();
		var listWidth_another;
		var end = slide.css("width", listWidth* slide.find(">li").length + "px");
		
		var mp;
		if($(this).hasClass("left")){ // 좌
			mp = "+=";
		}else if($(this).hasClass("right")){
			mp = "-=";
		}
		
		if($(this).hasClass("left")){
			if(slide.css("left") == "0px"){// 
				return;
			}
		}
		
		// alert("-"+(listWidth * slide.find(">li").length - listWidth )+ "px") 
		
		/* if(slide.hasClass("slide_large")){ //7개씩
			listWidth_another = listWidth * 7;
			var num =  slide.find(".row_last").length-1
		}else{
			listWidth_another = listWidth
		} */
		
		if($(this).hasClass("right")){
		
			if(slide.hasClass("slide_large")){ //7개씩
				listWidth_another = listWidth * 7;
				var num =  slide.find(".row_last").length-1
				/*
				if(slide.css("left") == "-"+(listWidth_another * num )+ "px"){
					return;
				}
				*/
				if(slide.css("left")=="-1428px"){
					listWidth_another=0;
				}
				
			}else{
				listWidth_another = listWidth
			}
			
			if(slide.css("left") == "-"+(listWidth * slide.find(">li").length - listWidth_another )+ "px"){
				return;
			}
		}else if(slide.hasClass("slide_large")){
			listWidth_another = listWidth * 7;
		}else{
			listWidth_another = listWidth;
		}		
		
		slide.filter(':not(:animated)').animate({
			left : mp + listWidth_another +"px"
		},500,"swing");
	})
	
})





