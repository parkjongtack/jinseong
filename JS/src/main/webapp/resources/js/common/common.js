
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


/*--------------------------------------------------------------------------------------------
 Spec      : 숫자입력시 3자리마다 자동으로 콤마 찍기
 Argument : string
 Return   : string
 Example  : onkeyup="comma_value(this)"
---------------------------------------------------------------------------------------------*/
function comma_value(sval)
{
    if (event.keyCode != 9 && event.keyCode != 37 && event.keyCode != 39) 
    {
        var cur = sval.value;
        var setMinus = 0;

        if (cur.charAt(0) == "-") {
            setMinus = 1;
        }

        cur=cur.replace(/[^.0-9]/g ,"");
        cur=cur.replace(/[.]+/g ,".");

        if (setMinus == 1) 
            sval.value = "-" + formatNumbertoString(cur);
        else
            sval.value = formatNumbertoString(cur);
    }
}


/*--------------------------------------------------------------------------------------------
 Spec      : 숫자입력시 3자리마다 자동으로 콤마 찍기
 Argument : string
 Return   : string
 Example  : onkeyup="comma_value(str)"
---------------------------------------------------------------------------------------------*/
function formatNumbertoString(cur)
{
    leftString = cur;
    rightString = ".";
    dotIndex = 0;
      
    for(i = 0; i < cur.length; i++){
        // 1) '.'이 처음에 입력 되었을때 앞에 0을 더해 "0."을 리턴
        // 2) "0."이외의 입력 일 때 "0"만 리턴
        if(cur.charAt(i) == "." || (cur.length > 1 && cur.charAt(0) == "0" && cur.charAt(1) != "."))
        {
            dotIndex = i;
            if(dotIndex == 0)
            {
                if   (cur.charAt(0) == ".")   leftString="0.";
                else                          leftString="";
                return leftString;
            }
            break;
        }
    }
    
     if(dotIndex != 0)    //dot가 있을 경우..
    {
        leftString = cur.substr(0, dotIndex);
        rightString = cur.substr(dotIndex+1);
        rightString = rightString.replace(/\./g,"");
    }
    else //없으면..
    {
        leftString = cur;
    }

    len=leftString.length-3;
    while(len>0) 
    {
        leftString=leftString.substr(0,len)+","+leftString.substr(len);
        len-=3;
    }           
    
    if(rightString != ".")
        return (leftString + "." + rightString); 
    else
        return leftString;
}

// 숫자만 입력 (소수점 허용, 음수 허용)
// 사용법 : onKeyPress = onlyNum();
function onlyNum()
{
    if (event.keyCode < 45 || event.keyCode > 57 || event.keyCode == 47) 
        event.returnValue = false;
}

// 숫자만 입력 (소수점 허용, 음수 불가)
// 사용법 : onKeyPress = onlyNum2();
function onlyNum2()
{
    if (event.keyCode < 46 || event.keyCode > 57 || event.keyCode == 47) 
        event.returnValue = false;
}

// 숫자만 입력 (소수점 불가, 음수 허용)
// 사용법 : onKeyPress = onlyNum3();
function onlyNum3()
{
    if (event.keyCode < 45 || event.keyCode > 57 || event.keyCode == 46 || event.keyCode == 47)  
        event.returnValue = false;
}

// 숫자만 입력 (소수점 불가, 음수 불가)
// 사용법 : onKeyPress = onlyNum4();
function onlyNum4() 
{
    if (event.keyCode < 48 || event.keyCode > 57)
        event.returnValue=false;
}

/*=============================================================================* 
 * 콤마가 들어간 숫자에서 ","를 뺀다.
 * param : value
 *============================================================================*/
function numOffMask(me){
        var tmp=me.split(",");
         tmp=tmp.join("");
        return tmp;
}

//콤마찍기
function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

//날짜 더하기
function date_add(sDate, nDays) {
    var yy = parseInt(sDate.substr(0, 4), 10);
    var mm = parseInt(sDate.substr(5, 2), 10);
    var dd = parseInt(sDate.substr(8), 10);
 
    d = new Date(yy, mm - 1, dd + nDays);
 
    yy = d.getFullYear();
    mm = d.getMonth() + 1; mm = (mm < 10) ? '0' + mm : mm;
    dd = d.getDate(); dd = (dd < 10) ? '0' + dd : dd;
 
    return '' + yy + '-' +  mm  + '-' + dd;
}
/*=============================================================================* 
 * null check
 * param : value
 *============================================================================*/
function cnull(value){ //null값 확인
	try{
		value = $.trim(value);
		if(value == null){
			return true;
		}
		if(value == "undefine"){
			return true;
		}
		if(value.length <= 0){
			return true;
		}
		return false;
	}finally{
		value = null;
	}
}

/*=============================================================================* 
 * 정수 확인
 * param : value
 *============================================================================*/
function cint(value){ //정수 확인
	try{
		
		if(cnull(value)){
			return false;
		}
		
		var last_index = value.length;
		var check_char = null;
		
		for(var i = 0 ; last_index > i ; i++){
			
			check_char = null;
			check_char = value.substring(i, (i+1));
			
			if(cnull(check_char)){
				return false;
			}
			
			if(!($.isNumeric(check_char))){
				return false;
			}
		}
		
		return true;
		
	}finally{
		last_index = null;
		check_char = null;
		value = null;
	}
}

function NumObj(obj){

	 if (event.keyCode >= 48 && event.keyCode <= 57) { //숫자키만 입력
		 return true;
	 }else{
		 event.returnValue = false;
	 }
}
