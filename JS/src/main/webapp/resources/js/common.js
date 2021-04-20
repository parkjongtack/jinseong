
$.datepicker.regional['ko'] = {		
    closeText: '닫기',
    prevText: '이전달',
    nextText: '다음달',
    currentText: '오늘',
    monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
    '7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
    monthNamesShort: ['1월','2월','3월','4월','5월','6월',
    '7월','8월','9월','10월','11월','12월'],
    dayNames: ['일','월','화','수','목','금','토'],
    dayNamesShort: ['일','월','화','수','목','금','토'],
    dayNamesMin: ['일','월','화','수','목','금','토'],
    weekHeader: 'Wk',
    dateFormat: 'yy-mm-dd',
    firstDay: 0,
    isRTL: false,
    showMonthAfterYear: true,
    yearSuffix: '',
    changeMonth: true,
    changeYear: true,
    yearRange: 'c-99:c+5',
};
$.datepicker.setDefaults($.datepicker.regional['ko']);

/*****************************************************************
 * 페이징 처리 스크립트
 *****************************************************************/
var pagingUtil	=  {
	
	__url			: '',
	__form			: '',
	__currRow		: '',
	__func			: '',
	pageSubmit		: function(currRow) {
		this.__currRow		= currRow;
		$("#currRow").remove();
		$('<input>').attr({
		    type	: 'hidden',
		    id		: 'currRow',
		    name	: 'currRow',
		    value	: this.__currRow
		}).appendTo(this.__form);
		if(this.__func == ""){
			this.__form.attr({action:this.__url, method:'post'}).submit();
		}
		else{
			eval(this.__func+'()');
		}

	}
};
var pagingUtil2	=  {

		__url			: '',
		__form			: '',
		__currRow		: '',
		__func			: '',
		pageSubmit		: function(currRow) {			
			if(this.__func != ''){
				this.__currRow		= currRow;

				$("#currRow2").remove();
				$('<input>').attr({
				    type	: 'hidden',
				    id		: 'currRow2',
				    name	: 'currRow2',
				    value	: this.__currRow
				}).appendTo(this.__form);

				//this.__form.attr({action:this.__url, method:'post'}).submit();
				//eval(this.__url);
				eval(this.__func+'()');
			}
		}
};


/*****************************************************************
 * 특수문자 replace function
 *****************************************************************/
function htmlspecialchars_decode (string, quote_style,dom) {
	  var optTemp = 0,
	    i = 0,
	    noquotes = false;
	  if (typeof quote_style === 'undefined') {
	    quote_style = 2;
	  }
	  string = string.toString().replace(/&lt;/g, '<').replace(/&gt;/g, '>');
	  //alert(string);
	  var OPTS = {
	    'ENT_NOQUOTES': 0,
	    'ENT_HTML_QUOTE_SINGLE': 1,
	    'ENT_HTML_QUOTE_DOUBLE': 2,
	    'ENT_COMPAT': 2,
	    'ENT_QUOTES': 3,
	    'ENT_IGNORE': 4
	  };
	  if (quote_style === 0) {
	    noquotes = true;
	  }
	  if (typeof quote_style !== 'number') { 
	    quote_style = [].concat(quote_style);
	    for (i = 0; i < quote_style.length; i++) {
	      if (OPTS[quote_style[i]] === 0) {
	        noquotes = true;
	      } else if (OPTS[quote_style[i]]) {
	        optTemp = optTemp | OPTS[quote_style[i]];
	      }
	    }
	    quote_style = optTemp;
	  }
	  if (quote_style & OPTS.ENT_HTML_QUOTE_SINGLE) {
	    string = string.replace(/&#0*39;/g, "'"); 
	   
	  }
	  if (!noquotes) {
	    string = string.replace(/&quot;/g, '"');
	  }
	  
	  string = string.replace(/&amp;/g, '');
	  string = string.replace(/lt;/g, '<');
	  //console.log(dom);
	  //$(".aaaaaa").html(string);
	  //alert(string);
	  return string;
	}

function fnFileExtChk(fileinfo){
	
	
	var fileExt = fileinfo.split(".")[1].toLowerCase();

	if( fileExt != "" ){
		
		if($.inArray(fileExt, ['php','asp','jsp','html','htm','exe']) != -1) {
			alert('업로드 가능한 파일이 아닙니다.');
			return false;
		
		}else{
			return true;
		}
	}
	
}

//브라우저 타입 체크
function getBrowserType(){
    
    var _ua = navigator.userAgent;
    var rv = -1;
     
    //IE 11,10,9,8
    var trident = _ua.match(/Trident\/(\d.\d)/i);
    if( trident != null )
    {
        if( trident[1] == "7.0" ) return rv = "IE" + 11;
        if( trident[1] == "6.0" ) return rv = "IE" + 10;
        if( trident[1] == "5.0" ) return rv = "IE" + 9;
        if( trident[1] == "4.0" ) return rv = "IE" + 8;
    }
     
    //IE 7...
    if( navigator.appName == 'Microsoft Internet Explorer' ) return rv = "IE" + 7;
     
    /*
    var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
    if(re.exec(_ua) != null) rv = parseFloat(RegExp.$1);
    if( rv == 7 ) return rv = "IE" + 7; 
    */
     
    //other
    var agt = _ua.toLowerCase();
    if (agt.indexOf("chrome") != -1) return 'Chrome';
    if (agt.indexOf("opera") != -1) return 'Opera'; 
    if (agt.indexOf("staroffice") != -1) return 'Star Office'; 
    if (agt.indexOf("webtv") != -1) return 'WebTV'; 
    if (agt.indexOf("beonex") != -1) return 'Beonex'; 
    if (agt.indexOf("chimera") != -1) return 'Chimera'; 
    if (agt.indexOf("netpositive") != -1) return 'NetPositive'; 
    if (agt.indexOf("phoenix") != -1) return 'Phoenix'; 
    if (agt.indexOf("firefox") != -1) return 'Firefox'; 
    if (agt.indexOf("safari") != -1) return 'Safari'; 
    if (agt.indexOf("skipstone") != -1) return 'SkipStone'; 
    if (agt.indexOf("netscape") != -1) return 'Netscape'; 
    if (agt.indexOf("mozilla/5.0") != -1) return 'Mozilla';
}

function numberOnlyFn(param){
	var num	= param.replace(/[^0-9]/gi,'');
	num		= $.trim(num) == '' ? 0 : parseInt(num);
	return num;
}
function comMaFn(param){
	return param.toString().replace(/\B(?=(\d{3})+(?!\d))/g,',');
}
function trimAllFn(param){
	var returnStr	= '';
	if(param != null && param != undefined && param != ''){
		returnStr	= $.trim(param.replace(/ /gi, ''));
	}
	return returnStr;
}
function guroartPageMoveFn(url,param){
	if($.trim(url) == '' || $.trim(param) == ''){
		alert('준비중입니다.');
		return false;
	}
	var $form	= $('<form>').attr('method','get').attr('action',url).appendTo('body');
	$('<input type="hidden">').attr('name','school1004_menu_cd').val(param).appendTo($form);
	$form.submit();
	
}
function onlyNumKey() {
	if ((event.keyCode < 48) || (event.keyCode > 57)){
		if (event.keyCode < 96 || event.keyCode > 105){
			if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 46){}
			else
				event.returnValue = false;
		}
	}
}
//javascript repalceAll
function replaceAll(str, searchStr, replaceStr) {
	return str.split(searchStr).join(replaceStr);
}