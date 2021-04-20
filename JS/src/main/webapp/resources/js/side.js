$(document).ready(function(){
	var $ticketInfo		= $(".ticket-info");
	var ticketHeight	= parseInt($ticketInfo.height());
	var $sidebar		= $('#sidebar');
	var standardTop		= parseInt($sidebar.offset().top);
	var sidebarTop		= standardTop;
	var sidebarLeft		= 0;
	var sidebarWidth	= parseInt($sidebar.width());
	var sidebarHeight	= parseInt($sidebar.height());
	
	ticketInfoPositionFn();
	
	$(window).resize(function(){
		ticketInfoPositionFn();
	});
	
	$(window).scroll(function() {
		var sidebarScroll	= parseInt($(window).scrollTop());
		var fieldHeight		= standardTop + sidebarHeight;
		var maxHeight		= sidebarScroll + ticketHeight;
		var bottomInt		= fieldHeight - maxHeight;
		var resultTop		= 0; 
		
		if(bottomInt > 0 && standardTop > sidebarScroll){
			resultTop	= standardTop - sidebarScroll;
			
		}else if(bottomInt > 0 && standardTop <= sidebarScroll){
			resultTop	= resultTop + parseInt(10);
			
		}else if(bottomInt <= 0){
			resultTop	= bottomInt;
		}
		
		sidebarTop	= resultTop;
		$ticketInfo.css({"top":resultTop });  
	});

	function ticketInfoPositionFn(){
		sidebarLeft		= parseInt($sidebar.offset().left);
		$ticketInfo.css({'position':'fixed','top':sidebarTop,'right':sidebarLeft});
	}
});	
