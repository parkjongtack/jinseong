var clsJsHelper = function(){
	var obj;
	var obj2;
	this.returns = true;
	this.checkSpace = function(val){				
		for (var i = 0 ; i < val.length ;i++ ){
			if (val.charAt(i) == ' '){
				alert('공백은 입력할 수 없습니다.');
				return false;
			}
		}		
		return true;
	}	

	this.checkNull = function(val, msg){
		if (val.indexOf(" ") == 0)
		{
			alert('공백으로 시작할수 없습니다.');
			return false;
		}

		var tmp_val = val.replace( /^\s+|\s+$/g, "" )
		if (tmp_val == ""){
			alert(msg);		
			return false;
		}		
		return true;
	}

	this.checkLen = function(objName, nlen, mlen){
		obj = document.getElementById(objName);
		var this_length = 0;

		for (var i = 0;i < obj.value.length; i++){
			var tmp = obj.value.charAt(i);
			if (escape(tmp).length > 4){
				this_length = this_length + 2;
			}else{
				this_length++;
			}

		}
		if (this_length < nlen){
			alert('한글 '+parseInt(nlen/2) + '자, 영문 '+nlen + '자 이상 입력해 주세요.');
			obj.focus();
			return false;
		}

		if (this_length > mlen){
			alert('한글 '+parseInt(mlen/2) + '자, 영문 '+mlen + '자 이하로 입력해 주세요.');
			obj.focus();
			return false;
		}
		return true;
	}

	this.checkVal = function(objName, msg, space){		
		obj = document.getElementById(objName);
		if (!this.checkNull(obj.value, msg)){
			obj.focus();
			return;
		}
		if (space =="Y"){
			if (!this.checkSpace(obj.value)){
				obj.value = "";
				obj.focus();
				return;
			}
		}
		return true;
	}

	this.compare = function(objName, objName2, msg){
		obj = document.getElementById(objName);
		obj2 = document.getElementById(objName2);
		if (obj.value != obj2.value){
			alert(msg);
			obj.value = "";
			obj2.value = "";
			obj.focus();
			return false;
		}
		return true;
	}

	this.compare2 = function(objName, val, msg){
		obj = document.getElementById(objName);
		if (obj.value != val){
			alert(msg);						
			return false;
		}
		return true;
	}

	this.compare3 = function(objName, val, msg){
		obj = document.getElementById(objName);
		if (obj.value == val){
			alert(msg);						
			return false;
		}
		return true;
	}

	this.bigerThan = function(objName, val, msg){
		obj = document.getElementById(objName);
		if (obj.value > val){
			alert(msg);
			obj.value = "";
			obj.focus();
			return false;
		}
		return true;
	}

	this.checkIndexOf = function(objName, sChar, msg){
		obj = document.getElementById(objName);
		if (obj.value.indexOf(sChar) >= 0)
		{
			alert(msg);			
			obj.focus();
			return false;
		}
		return true;
	}

	this.checkNotIndexOf = function(objName, sChar, msg){
		obj = document.getElementById(objName);
		if (obj.value.indexOf(sChar) == -1)
		{
			alert(msg);			
			obj.focus();
			return false;
		}
		return true;
	}
	
	this.checkValNLen = function(objName, nlen, mlen, msg, space){			
		obj = document.getElementById(objName);
		if (!this.checkNull(obj.value, msg)){
			obj.focus();
			return false;
		}	

		if (space == "Y"){
			if (!this.checkSpace(obj.value)){
				obj.value = "";
				obj.focus();
				return false;
			}
		}		

		if (!this.checkLen(objName, nlen, mlen)){
			obj.focus();
			return false;
		}

		return true;
	}	

	this.checkNumber = function(objName, msg){
		obj = document.getElementById(objName);	
		if (obj.value.match(/^[0-9]+$/gi) == null)
		{
			alert(msg);
			obj.value = "";
			obj.focus();
			return false;
		}

		return true;
	}
	
	this.mailExp = /[a-z0-9]{1,}@[a-z0-9-]{1,}\.[a-z0-9]{2,}/i; 
	this.checkEmail = function(objName){
		obj = document.getElementById(objName);	
		if (!this.mailExp.test(obj.value)){
			alert('정확한 메일주소가 아닙니다. 다시 입력해 주세요');
			obj.value= "";
			obj.focus();
			return false;
		}

		var alt_chk = 0;
		var j_chk = 0;

		for (var i = 0 ;i < obj.value.length; i++){
			if (obj.value.charAt(i) == '@')	{
				alt_chk ++;
			}
			if (obj.value.charAt(i) == "."){
				j_chk ++;
			}
		}
		if (alt_chk > 1 || j_chk > 1){
			alert('잘못된 이메일 형식입니다.');
			obj.value = "";
			obj.focus();
			return false;
		}

		if (obj.value.toLowerCase().indexOf("hanmail.net") > 0 || obj.value.toLowerCase().indexOf("daum.net") > 0){
			alert('한메일은 사용하실 수 없습니다.');
			obj.value = "";
			obj.focus();
			return false;
		}
		
		
		
		return true;
	}

	this.checkSpecialWord = function(objName, msg){
		
		obj = document.getElementById(objName)
		for (var i=0; i < obj.value.length; i++) { 
			var chk = obj.value.substring(i,i+1); 
			if(!chk.match(/[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|\s]/)) { 
				alert(msg);
				obj.value = "";
				obj.focus();
				return false;
			} 
		} 

		return true;
	}

	this.checkEss = function(objName, flag){		
		if (document.getElementById(objName))
		{
			obj = document.getElementById(objName);
			if ((obj.value != "" && "Y" != flag) || "Y" == flag)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		return false;
	}

}


//영문(소),숫자만 가능하게
function onlyEngNum(obj)
{
	var e1 = document.getElementById(obj);
	var num ="abcdefghijklmnopqrstuvwxyz0123456789";
	event.returnValue = true;

	for (var i=0;i<e1.value.length;i++)
	{
		//alert(num.indexOf(e1.value.charAt(i)))
		if(-1 == num.indexOf(e1.value.charAt(i)))
			event.returnValue = false;
	}

	if (!event.returnValue)
	{			
		e1.value="";
		e1.focus();
		alert('영문(소문자) 및 숫자만 적어주세요');
	}
}

//숫자만 가능하게
function onlyNumber(obj)
{
	var e1 = document.getElementById(obj);
	var num ="0123456789";
	event.returnValue = true;

	for (var i=0;i<e1.value.length;i++)
	{
		//alert(num.indexOf(e1.value.charAt(i)))
		if(-1 == num.indexOf(e1.value.charAt(i)))
			event.returnValue = false;
	}

	if (!event.returnValue)
	{
		e1.value="";
		e1.focus();
		alert('숫자만 입력해 주세요.');
	}
}

//숫자와 - 만 가능하게 ex)010-1234-124 
function onlyNumber2(obj)
{
	var e1 = document.getElementById(obj);
	var num ="0123456789-";
	event.returnValue = true;

	for (var i=0;i<e1.value.length;i++)
	{
		//alert(num.indexOf(e1.value.charAt(i)))
	if(-1 == num.indexOf(e1.value.charAt(i)))
	event.returnValue = false;
	}

	if (!event.returnValue)
	{
		e1.value="";
		e1.focus();
		alert('숫자와 - 만 입력해 주세요.');
	}
}

function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      oldonload();
      func();
    }
  }
}


function returnThisTextLength(objName, targetName, max){
	var obj = document.getElementById(objName);
	var target = document.getElementById(targetName);
	var this_length;
	var cutChar = '';
	var tmpChar =  '';
	var es = '';
	this_length = 0;
	
	if (obj.value !='')
	{
		for (var i = 0; i < obj.value.length ; i++)
		{
			cutChar = obj.value.charAt(i);
			es = escape(cutChar);
			if (escape(cutChar).length > 4) this_length += 2;
			else this_length+=1;		
			
		}
	}
	else
	{
		target.innerHTML = '<span class="total2">' + 0 + '</span>/'+max+' bytes';
	}
		
	
	if (this_length > max)
	{
		this_length = 0 ;
		for (var i = 0; i < obj.value.length ; i++)
		{
			cutChar = obj.value.charAt(i);
			es = escape(cutChar);
			if (escape(cutChar).length > 4) this_length += 2;
			else this_length+=1;		

			if (this_length > max)
			{
				if (escape(cutChar).length > 4) this_length -= 2;
				else this_length -= 1;
				break;
			}
			else tmpChar += cutChar;
		}
		
		alert('영문 '+ max + '자 / 한글 '+ parseInt(max/2) +' 자까지만 가능합니다');
		obj.value = tmpChar;
		obj.focus();
	}

	target.innerHTML = '<span class="total2">' + this_length + '</span>/'+max+' bytes';	

}

function error(elem, text){
    if (errfound) 
        return;
    window.alert(text);
    elem.select();
    elem.focus();
    errfound = true;
}


function formCheck(theForm){
    errfound = false;
    var str_jumin1 = theForm.jnumber1.value;
    var str_jumin2 = theForm.jnumber2.value;
    var checkImg = '';
    
    
    var i3 = 0
    for (var i = 0; i < str_jumin1.length; i++) {
        var ch1 = str_jumin1.substring(i, i + 1);
        if (ch1 < '0' || ch1 > '9') {
            i3 = i3 + 1
        }
    }
    if ((str_jumin1 == '') || (i3 != 0)) {
        error(theForm.jumin1, '없는 주민등록번호 입니다. 다시 입력해 주세요.');
    }
    
    
    
    var i4 = 0
    for (var i = 0; i < str_jumin2.length; i++) {
        var ch1 = str_jumin2.substring(i, i + 1);
        if (ch1 < '0' || ch1 > '9') {
            i4 = i4 + 1
        }
    }
    if ((str_jumin2 == '') || (i4 != 0)) {
        error(theForm.jumin2, '없는 주민등록번호 입니다. 다시 입력해 주세요.');
    }
    
    if (str_jumin1.substring(0, 1) < 4) {
        error(theForm.jumin2, '없는 주민등록번호 입니다. 다시 입력해 주세요.');
    }
    
    if (str_jumin2.substring(0, 1) > 2) {
        error(theForm.jumin2, '없는 주민등록번호 입니다. 다시 입력해 주세요.');
    }
    
    if ((str_jumin1.length > 7) || (str_jumin2.length > 8)) {
        error(theForm.jumin2, '없는 주민등록번호 입니다. 다시 입력해 주세요.');
    }
    
    if ((str_jumin1 == '72') || (str_jumin2 == '18')) {
        error(theForm.jumin1, '없는 주민등록번호 입니다. 다시 입력해 주세요.');
    }
    
    var f1 = str_jumin1.substring(0, 1)
    var f2 = str_jumin1.substring(1, 2)
    var f3 = str_jumin1.substring(2, 3)
    var f4 = str_jumin1.substring(3, 4)
    var f5 = str_jumin1.substring(4, 5)
    var f6 = str_jumin1.substring(5, 6)
    var hap = f1 * 2 + f2 * 3 + f3 * 4 + f4 * 5 + f5 * 6 + f6 * 7
    var l1 = str_jumin2.substring(0, 1)
    var l2 = str_jumin2.substring(1, 2)
    var l3 = str_jumin2.substring(2, 3)
    var l4 = str_jumin2.substring(3, 4)
    var l5 = str_jumin2.substring(4, 5)
    var l6 = str_jumin2.substring(5, 6)
    var l7 = str_jumin2.substring(6, 7)
    hap = hap + l1 * 8 + l2 * 9 + l3 * 2 + l4 * 3 + l5 * 4 + l6 * 5
    hap = hap % 11
    hap = 11 - hap
    hap = hap % 10
    if (hap != l7) {
        error(theForm.jumin1, '없는 주민등록번호 입니다. 다시 입력해 주세요.');
    }
    var i9 = 0
    if (!errfound) 
        theForm.submit();
}

