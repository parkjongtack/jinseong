<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>::건물명검색::</title>
<link type="text/css" href="./style.css" rel="stylesheet" />
<script src="./juso.js" type="text/javascript"></script>
<script type="text/javascript">

	var xmlHttp;
	var mode;											
	var dataFrom;	
	var dataTo;		
	var objFrom;	
	var objTo;		
	var objClear;					
	var areaIdx;	
	var bldnm,group_nm,oldaddr_nm,newaddr_nm,livein_cnt,corplist;	


   function changeAreaList(idx, obj){	
		if(obj.id == '' || obj.id == 'undefined')
			return;
		
		areaIdx = idx;
   		mode 	= 'area';
		
	
		dataFrom, dataTo, objFrom, objTo, objClear;   		

		// 통합검색 
		if(obj.id == 'city1'){
			dataFrom 	= 'city';
			dataTo 		= 'county';
			objFrom		= 'city1';
			objTo		= 'county1';
			objClear	= 'town1_oldaddr';	

			//①세종시예외처리 
			document.getElementById("county1").disabled = false;


			if(document.getElementById(obj.id).value =='36'){
				document.getElementById("county1").disabled = true;
				dataTo 		= 'town';
				objTo		= 'town1_oldaddr';
			}	

			clearList(objTo);
		}
		// 통합검색 
		else if(obj.id == 'county1'){
			dataFrom 	= 'county';
			objFrom		= 'county1';
			objClear	= '';
			dataTo 		= 'town';
			objTo		= 'town1_oldaddr';

			clearList(objTo);
		}
		else if(obj.id == 'town1_oldaddr'){
			dataFrom 	= 'town';
			objFrom		= 'town1_oldaddr';
			objClear	= '';
			dataTo 		= '';
			objTo		= '';
			
			
		}
		// 통합검색 
		else if(obj.id == 'rd_nm_idx1'){
			dataFrom 	= 'county';
			objFrom		= 'county1';
			objClear	= '';
			
			if(searchType == 'NORMAL1'){		//	새주소검색
				dataTo 		= 'rd_nm';
				objTo		= 'rd_nm1';
			}
		}

		//	update해야 할 select를 초기화
		
		
		//	clear해야 할 select가 설정된 경우 초기화


		
		// from object 선택값이 없으면 return
		if(document.getElementById(objFrom).value == ''){
			return;
		}
		
		var url = "./AjaxRequestXML.jsp?getUrl=http://www.juso.go.kr/getAreaCode.do?" + escape(createParameter());
		 
	  	createXMLHttpRequest();
	 	xmlHttp.onreadystatechange = handleStateChange;
	  	xmlHttp.open("GET", url, true);
	  	xmlHttp.send(null);
	}
	
	
	/*
	 *	행정구역 조회 쿼리에 사용할 파라메터값 설정
	 */
	function createParameter() {
		
		var rdIndex;
		
		// 도로명 clear인 경우, 도로명 인덱스 선택 초기화
		if(dataTo.indexOf("rd_nm") != -1){
			var rdIdxName = 'rd_nm_idx'+areaIdx;
			rdIndex = document.getElementById(rdIdxName).value;
		}
		
		if(objFrom!='')
			valFrom	= document.getElementById(objFrom).value;
		if(objTo!='')
		    valTo = document.getElementById(objTo).value;

		//	시군구>도로명, 시군구>지번주소인 경우
		//	시도코드도 가져가야함
		if(dataFrom == 'county' && (areaIdx == 1 || areaIdx == 3)){
			var cityName = 'city'+areaIdx;
			var cityVal = document.getElementById(cityName).value;
			
			valFrom = cityVal+valFrom;
		}else if(dataFrom == 'town' && (areaIdx == 1 || areaIdx == 3)){
			var cityName	= 'city'+areaIdx;
			var countyName	= 'county'+areaIdx;
			
			valFrom = document.getElementById(cityName).value+document.getElementById(countyName).value+valFrom;
		}
		
		var queryString = "from="+encodeURI(dataFrom)+"&to="+encodeURI(dataTo)+"&valFrom="+encodeURI(valFrom)+"&valTo="+encodeURI(valTo)+"&rdIndex="+encodeURI(rdIndex);


		//②세종시 예외처리
		if(encodeURI(valFrom)=='36'){
			valFrom +='110';
		}
	  	return queryString;
	}
	

	function handleStateChangeSearch() {
	      var j = 1;
	      if(xmlHttp.readyState == 2||xmlHttp.readyState == 3)
		 		document.getElementById("formbox").innerHTML="--처리중입니다.--";
		  if(xmlHttp.readyState == 4) {
		       if(xmlHttp.status == 200)
		    	   var siNm 		= xmlHttp.responseXML.getElementsByTagName('siNm');
			       var sggNm 		= xmlHttp.responseXML.getElementsByTagName('sggNm');
			       var emdNm 		= xmlHttp.responseXML.getElementsByTagName('emdNm');
			       var liNm 		= xmlHttp.responseXML.getElementsByTagName('liNm');
			       var rn 			= xmlHttp.responseXML.getElementsByTagName('rn');
			       var rnCd 		= xmlHttp.responseXML.getElementsByTagName('rnCd');
			       var buldMnnm 	= xmlHttp.responseXML.getElementsByTagName('buldMnnm');
			       var buldSlno 	= xmlHttp.responseXML.getElementsByTagName('buldSlno');
			       var lnbrMnnm 	= xmlHttp.responseXML.getElementsByTagName('lnbrMnnm');
			       var lnbrSlno 	= xmlHttp.responseXML.getElementsByTagName('lnbrSlno');
			       var udrtYn  	 	= xmlHttp.responseXML.getElementsByTagName('udrtYn');
			       var mtYn 	 	= xmlHttp.responseXML.getElementsByTagName('mtYn');
			       var bdNm		 	= xmlHttp.responseXML.getElementsByTagName('bdNm');    
			       var bdKd		 	= xmlHttp.responseXML.getElementsByTagName('bdkd');
			       var zipCl		= xmlHttp.responseXML.getElementsByTagName('zipCl');
			       var intCurrentPage		= xmlHttp.responseXML.getElementsByTagName('intCurrentPage');
			       var intCountPerPage		= xmlHttp.responseXML.getElementsByTagName('intCountPerPage');
			       var totalCount		= xmlHttp.responseXML.getElementsByTagName('totalCount');

			       var setDetailJuso = '';

			       
			        document.getElementById("formbox").innerHTML="";

				 	for(var i = 0; i < sggNm.length; i++) {
					 	var tempLnbr="";
					 	var tempBuld="";
					 	var jibun="";
					 	var newaddr="";
					 	var zip="";
					 	if(lnbrSlno[i].firstChild.nodeValue!="0"){
					 		tempLnbr=lnbrMnnm[i].firstChild.nodeValue+'-'+lnbrSlno[i].firstChild.nodeValue;
					 	}else{
					 		tempLnbr=lnbrMnnm[i].firstChild.nodeValue;
					 	}
					 	if(buldSlno[i].firstChild.nodeValue!="0"){
					 		tempBuld=buldMnnm[i].firstChild.nodeValue+'-'+buldSlno[i].firstChild.nodeValue;
					 	}else{
					 		tempBuld=buldMnnm[i].firstChild.nodeValue;
					 	}
					 	
						if(siNm[i].firstChild.nodeValue=="세종특별자치시"){
							var temp=bdKd[i].firstChild.nodeValue;
						 	if(temp.substring(0,2)=='02'&&bdNm[i].childNodes.length!='0')
						 		setDetailJuso = "("+bdNm[i].firstChild.nodeValue+")";
					 		if(liNm[i].childNodes.length == '0') {
					 			newaddr = siNm[i].firstChild.nodeValue+' '+rn[i].firstChild.nodeValue+' '+tempBuld;
						 		jibun = siNm[i].firstChild.nodeValue+' '+emdNm[i].firstChild.nodeValue+' '+tempLnbr;
					 		}else{
					 			newaddr = siNm[i].firstChild.nodeValue+' '+emdNm[i].firstChild.nodeValue+' '+rn[i].firstChild.nodeValue+' '+tempBuld;
						 		jibun = siNm[i].firstChild.nodeValue+' '+emdNm[i].firstChild.nodeValue+' '+liNm[i].firstChild.nodeValue+' '+tempLnbr;
					 		}
					 		
					 	}else{
					 		if(liNm[i].childNodes.length == '0') {
							 	var temp=bdKd[i].firstChild.nodeValue;
							 	if(temp.substring(0,2)=='02'&&bdNm[i].childNodes.length!='0')
							 		setDetailJuso = "("+emdNm[i].firstChild.nodeValue+"，"+bdNm[i].firstChild.nodeValue+")";
							 	else
							 		setDetailJuso = "("+emdNm[i].firstChild.nodeValue+")";
							 	newaddr = siNm[i].firstChild.nodeValue+' '+sggNm[i].firstChild.nodeValue+' '+rn[i].firstChild.nodeValue+' '+tempBuld;
						 		jibun = siNm[i].firstChild.nodeValue+' '+sggNm[i].firstChild.nodeValue+' '+emdNm[i].firstChild.nodeValue+' '+tempLnbr;
						 	}else{
						 		var temp=bdKd[i].firstChild.nodeValue;
						 		if(temp.substring(0,2)=='02'&&bdNm[i].childNodes.length!='0')
							 		setDetailJuso = "("+bdNm[i].firstChild.nodeValue+")";
						 		newaddr = siNm[i].firstChild.nodeValue+' '+sggNm[i].firstChild.nodeValue+' '+emdNm[i].firstChild.nodeValue+' ' +rn[i].firstChild.nodeValue+' '+tempBuld;
						 		jibun = siNm[i].firstChild.nodeValue+' '+sggNm[i].firstChild.nodeValue+' '+emdNm[i].firstChild.nodeValue+' '+liNm[i].firstChild.nodeValue+' '+tempLnbr;
						 	}
					 	}
					 	

					 	if(bdNm[i].childNodes.length!='0')
					 		jibun += ' '+bdNm[i].firstChild.nodeValue;

				 		if(zipCl[i].childNodes.length=='0'){
					 		zip = "-";
				 		}else{
				 			zip = zipCl[i].firstChild.nodeValue;
				 		}
					 		
				 		
						if(i%2=='0'){
							document.getElementById("formbox").innerHTML += "<ul class='item'>";
			 				document.getElementById("formbox").innerHTML += "<li class='road'><a href='javascript:setParentValue(\""+ newaddr+"*"+setDetailJuso+"*"+zip+"\")'>"+"[도로명]"+newaddr+"</a></li>";
			 				document.getElementById("formbox").innerHTML += "<li class='road'>"+"[지 번]"+jibun+"</li>";
			 				document.getElementById("formbox").innerHTML += "<li class='road'>"+"[우편번호]"+zipCl[i].firstChild.nodeValue+"</li>";
			 				document.getElementById("formbox").innerHTML += "</ul>";
						}
					 	else{
					 		document.getElementById("formbox").innerHTML += "<ul class='item'>";
					 		document.getElementById("formbox").innerHTML += "<li class='road2'><a href='javascript:setParentValue(\""+ newaddr+"*"+setDetailJuso+"*"+zip+"\")'>"+"[도로명]"+newaddr+"</a></li>";
			 				document.getElementById("formbox").innerHTML += "<li class='road2'>"+"[지번]"+jibun+"</li>";
			 				document.getElementById("formbox").innerHTML += "<li class='road2'>"+"[우편번호]"+zipCl[i].firstChild.nodeValue+"</li>";
			 				document.getElementById("formbox").innerHTML += "</ul>";
					 	}
					 		
		 				
			 	   }
				 	if(siNm.length>0)
				 		document.getElementById("formbox").innerHTML+=getPaging(intCountPerPage[0].firstChild.nodeValue,'5',totalCount[0].firstChild.nodeValue,document.check.currentPage.value,"");
				
					if(siNm.length=="0")
				 		document.getElementById("formbox").innerHTML="검색된 결과가 없습니다.";
		           	delete xmlHttp;
		           	xmlHttp = null;
		       }
		  }
	
	

	
	/*
	 *	지정된 행정구역코드 리스트를 초기화
	 */
	 function clearList(obj) {
			if(obj != '' ){
			
				var toObject = document.getElementById(obj);
				  
				toObject.options.length = 0;
				toObject.options[0] = new Option('::선택::', '');
			}
			
		}		

	/* 
	 fListScale : 한페이지 출력할 게시물수 
	 fPageScale : 페이지수를 표시할 갯수 
	 fTotal : 전체 게시물수 
	 fStart : 리스트를 뿌릴 시작점(최근게시물로 order by 해서 뽑아 내는 자료라면 가장 최근 자료가 0번이 됨 
	 fPagingUrl : 클릭시 넘어갈 페이지 
	 */ 
	 function getPaging(fListScale,fPageScale,fTotal,fStart,fPagingUrl) { 
		 if(fStart=='1')
			 fStart = fStart-1;
	     var fReturn = ""; 
	     var fPage; 
	     var fPP; 
	     var fNP; 
	     var fPreStart; 
	     var fLn; 
	     var fVk; 
	     var fNstart; 
	     var fLast; 
	     if(fTotal > fListScale) { 
	         fPage =  Math.floor(fStart/(fListScale*fPageScale)); 
	 
	         fReturn = fReturn + "<table border='0' cellpadding='0' cellspacing='1'><tr><td align='center' class='paging'> "; 
	 
	         fPP=fStart-fListScale; 
	         fNP=parseInt(fStart)+parseInt(fListScale); 
	         // 처음으로 이동 
	         if(fPP>=0) { 
	        	 fReturn = fReturn + " <a href=\"javascript:normalSearch2('sangho',1);\">처음</a> "; 
	         } 
	 
	         // sPageScale 만큼 앞으로 이동 
	         if( fStart+1 >  fListScale*fPageScale ) { 
	             fPreStart = fListScale*(fPage*fPageScale - 1); 
	             if(fPreStart<0)
	            	 fPreStart=-fPreStart;
	             fReturn  = fReturn + "<a href=\"javascript:normalSearch2('sangho'," +fPreStart+ ");\">이전</a> ";
	              
	         } 
	 
	         // sPageScale 만큼 출력 
	         for(i=0; i < fPageScale ; i++) { 
	             fLn = (fPage * fPageScale + i)*fListScale; 
	             fVk= fPage * fPageScale + i+1; 
	             
	             if(fLn<fTotal) { 
	            	 
						if(fLn!=fStart) {
							fReturn  = fReturn + " <a href=\"javascript:normalSearch2('sangho'," + fLn + ");\">" + fVk + "</a> "; }
						else {
							fReturn  = fReturn + " <span style='color:#FCC600;'>" + fVk + "</span> ";}	
		          }  
	         } 
	 
	         // sPageScale 만큼 뒤로 이동 
	         if(fTotal > ((fPage+1)*fListScale*fPageScale)) { 
	             fNstart=(fPage+1)*fListScale*fPageScale; 
	             fReturn  = fReturn + " <a href=\"javascript:normalSearch2('sangho'," + fNstart+ ");\">다음</a> "; 
	         } 
	 
	         // 마지막 페이지 
	       
	         if(fNP<fTotal) { 
	             fLast = (Math.floor(fTotal/fListScale))*fListScale; 
	             fReturn  = fReturn + "<a href=\"javascript:normalSearch2('sangho'," + fLast+ ");\">마지막</a> "; 
	         } 
	 
	         fReturn  = fReturn + "  </td></tr></table>"; 
	     } 
	     return fReturn; 
	 } 

</script>

</head>
<body>
<div id="wrapper">
	<div id="header">
		<h1>
			<img src="img/h1.jpg" alt="도로명주소찾기(우편번호)" />
		</h1>
	</div>
	<div id="container">
		<div id="content">
			<ul class="tab">
				<li><a href="#n" onclick="selectSearch('road');return false;"><img src="img/tab1_off.gif" alt="도로명주소"/></a></li>
				<li><a href="#n" onclick="selectSearch('jibun');return false;"><img src="img/tab2_off.gif" alt="지번"/></a></li>
				<li><a href="#n" onclick="selectSearch('sangho');return false;"><img src="img/tab3.gif" alt="건물명"/></a></li>
			</ul>
			
				<form name="check" method="post">
					<input type="hidden" name="searchType" id="searchType" value ="" />
					<input type="hidden" name="san1" id="san1"  value="0"/>
					<input type="hidden" name="extend"      id="extend"       value="${extend}"/>
					<input type="hidden" name="engineCtpNm" id="engineCtpNm"  value="${engineCtpNm}"/>
					<input type="hidden" name="engineSigNm" id="engineSigNm"  value="${engineSigNm}"/>
					<input type="hidden" name="engineEmdNm" id="engineEmdNm"  value="${engineEmdNm}"/>
					<input type="hidden" name="engineLiNm" id="engineLiNm"  value="${engineLiNm}"/>
					<input type="hidden" name="engineBdNm" id="engineBdNm"  value="${engineBdNm}"/>
					<input type="hidden" name="engineRdNm" id="engineRdNm"  value="${engineRdNm}"/>
					<input type="hidden" name="engineMtYn" id="engineMtYn"  value="${engineMtYn}"/>
					<input type="hidden" name="engineBdMaSn" id="engineBdMaSn"  value="${engineBdMaSn}"/>
					<input type="hidden" name="engineBdSbSn" id="engineBdSbSn"  value="${engineBdSbSn}"/>
					<input type="hidden" name="currentPage" id="currentPage"  value="${currentPage}"/>
				<div class="form" id="formbox">
					<fieldset>
						<legend>정보 입력</legend>
						
						<div class="group">
						<table class="SearchTable">
							<tr>
								<td class="SearchTd1"><label for="siCode"><strong>시&nbsp;&nbsp;&nbsp;&nbsp;도</strong></label></td>
								<td class="SearchTd2"><select title="시/도 선택" class="SearchSelectbox" name="city1" onchange="javascript:changeAreaList(1, this);" id="city1">
								<option value="">::선택::</option>
									<option value="11" title="서울특별시" >서울특별시</option>
									<option value="42" title="강원도" >강원도</option>
									<option value="41" title="경기도">경기도</option>
									<option value="48" title="경상남도" >경상남도</option>
									<option value="47" title="경상북도" >경상북도</option>
									<option value="46" title="전라남도" >전라남도</option>
									<option value="45" title="전라북도" >전라북도</option>
									<option value="44" title="충청남도" >충청남도</option>
									<option value="43" title="충청북도" >충청북도</option>
									<option value="29" title="광주광역시" >광주광역시</option>
									<option value="27" title="대구광역시" >대구광역시</option>
									<option value="30" title="대전광역시" >대전광역시</option>
									<option value="26" title="부산광역시" >부산광역시</option>
									<option value="31" title="울산광역시" >울산광역시</option>
									<option value="28" title="인천광역시" >인천광역시</option>
									<option value="36" title="세종특별자치시" >세종특별자치시</option>
									<option value="50" title="제주특별자치도" >제주특별자치도</option>
							</select>
							    </td>
							</tr>
							<tr>
								<td class="SearchTd1"><label for="siCode2"><strong>시&nbsp;군&nbsp;구</strong></label></td>
								<td class="SearchTd2">
								<select title="시/군/구 선택" class="SearchSelectbox" name="county1" id="county1" onchange="javascript:changeAreaList(1, this);">
									<option value="">::선택::</option>
									<c:forEach items="${county}" var="county">
										<option value="${county.countyId}">${county.countyName}</option>
									</c:forEach>
								</select>
							    </td>
							</tr>
							<tr>
								<td class="SearchTd1"><label for="siCode3"><strong>읍&nbsp;면&nbsp;동</strong></label></td>
								<td class="SearchTd2">
									<select id="town1_oldaddr" class="SearchSelectbox" name="town1_oldaddr" title="지번주소" > 
												<option value="">::선택::</option>
													<c:forEach items="${town}" var="county">
													<option value="${town.townId}" >${town.townName }</option>
												</c:forEach>
								    </select>
							    </td>
							</tr>
							<tr>
								<td class="SearchTd1"><label for="bd_number1"><strong>건&nbsp;물&nbsp;명</strong></label></td>
								<td class="SearchTd2">
									<input type="text" name="bdnm" value="" id="bdnm" class="text" style="width:182px; ime-mode:active" onkeypress="checkKeyASearch();return false;"/>
							    </td>
							</tr>
						</table>
						
						</div>
						<div class="btn">
							<a href="#"><img src="img/btn_search.gif" alt="검색" onclick="normalSearch('sangho','1');return false;" /></a>
						</div>
						<div >
							<img src="img/test.gif" alt="" width="380px" />
							자세한 표기법은   <a href="http://www.juso.go.kr" style="font-size: 10px" target="_blank">도로명주소안내홈페이지</a> 를 참조하세요
						</div>
					</fieldset>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>
