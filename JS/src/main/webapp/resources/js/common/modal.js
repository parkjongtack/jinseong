
$(document).ready(function() {	
	$('a[name=modal]').click(function(e) {
		e.preventDefault();
		
		var id = $(this).attr('href');
	
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
	
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		
		$('#mask').fadeIn(300);      	 
		$('#mask').fadeTo("fast",0.8);	
	
		var winH = $(window).height();
		var winW = $(window).width();
         var winWid;
         var winHei;
		 if(winH/2-$(id).height()/2<0){
			winWid =50;
		 }else{
			 winWid =winH/2-$(id).height()/2;
		 }

		 if(id=='#dialog50' || id=='#dialog51'){
				winHei = $(id).width()/2;
		}else{
			winHei =winW/2-$(id).width()/2;
		 }
		$(id).css('top',  winWid);
		
		$(id).css('left', winHei);
		
		$(id).fadeIn(1000); //페이드인 속도..숫자가 작으면 작을수록 빨라집니다.
		$('.btn_cancel01').focus();
	
	});
	
	$('.window .close').click(function (e) {
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
	});		
	
	$('#mask').click(function () {
		$(this).hide();
		$('.window').hide();
	});			
	
});
