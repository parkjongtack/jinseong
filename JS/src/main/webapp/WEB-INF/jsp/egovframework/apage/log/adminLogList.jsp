<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<script type="text/javascript">
//<![CDATA[
$(document).ready(function () {
   $('.datepicker').removeClass('hasDatepicker').datepicker({
		showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
		  buttonImage: "/resources/apage/images/board/ic_date.gif", // 버튼 이미지
		  buttonImageOnly: true  // 버튼에 있는 이미지만 표시한다.	 	
	});
   
   $("input[name=srch_input]").on("change",function(){
	   getLogFileAjax($(this).val());
   })
   /* 
   var $d_json = {browser:{},url:[]};
   var aa = "ie";
   $d_json.browser[aa]=1;
   console.log($d_json)
   
    */
   var d = new Date("July 21, 1983 01:15:00");
   var n = d.getHours();
   console.log(n);
});
 

Date.prototype.hhmmss = function() {
	var hh = this.getHours();
	var mm = this.getMinutes();
	//var ss = this.getSeconds();

	return [(hh>9 ? '' : '0') + hh,
			(mm>9 ? '' : '0') + mm,
		//	(ss>9 ? '' : '0') + ss,
			].join('');
};

	
function getLogFileAjax(){
	$.ajax({
		url		:	"/apage/log/logReadAjax.do",
		type	:	"post",
		data	:	$("#frm").serialize(),
		success	:	function(data){
			var r_line = data.root.resultLine;
			var $d_json = {browser:{},url:[]};
			for(var i = 0; i < r_line.length; i++){ 
				r_line[i] = r_line[i].replace(/\[/gi,"");
				var r_arr = r_line[i].split("]"); 
		//		console.log(r_arr);

				var row_status		=	r_arr[0];
				var row_url			=	r_arr[2];
				var row_browser		=	r_arr[4];
				var row_datetime	=	r_arr[5];
				
				

	
					
				var $date = new Date(row_datetime);
				console.log($date.getHours());
				
				
			//	console.log($date.hhmmss());
				
				
				
				var $d_url = $d_json.url;
				
				var $url_exist_flag = false;
				if($d_url.length == 0){
					var $push_url_data = {
							request_url	:	row_url,
							count		:	1,
							status		:	{
								[row_status]	:	1
							}
					};
					
					$d_url.push($push_url_data);
				}else{
					for(var j = 0; j < $d_url.length; j++){
						if($d_url[j].request_url == row_url){	//URL정보가 JSON 타입으로 저장되어 있을경우
							$d_url[j].count = eval($d_url[j].count) + 1;
							//STATUS 정보가 JSON 타입으로 저장된 경우
							if(typeof $d_url[j].status[row_status] == undefined || $d_url[j].status[row_status] == "undefined" ||  $d_url[j].status[row_status] == undefined){
								$d_url[j].status[row_status] = 1;
							}else{
								$d_url[j].status[row_status] = eval($d_url[j].status[row_status]) + 1;
							}
							
							$url_exist_flag = true;
						}
					}
					
					if(!$url_exist_flag){	//URL정보가 JSON 타입으로 저장되어있지 않을경우
						var $push_url_data = {
								request_url	:	row_url,
								count		:	1,
								status		:	{
									[row_status]	:	1
								}
						};
						$d_url.push($push_url_data);
						
					}
				}
				
				
				if(typeof $d_json.browser[row_browser] == undefined || $d_json.browser[row_browser] == "undefined" ||  $d_json.browser[row_browser] == undefined){
					$d_json.browser[row_browser] = 1;	
				}else{
					$d_json.browser[row_browser] = eval($d_json.browser[row_browser]) + 1;
				}
				
				for(var j = 0; j < r_arr.length; j++){
				//	console.log($d_json.browser.r_arr[j]);
				}
				/* 
				console.log(r_arr);
				r_arr[5]
				alert($d_json.browser.r_arr[4]); */
			}
			
			/*
			var replace_all = r_line.replace(/\[/gi,"");
			var r_line_arr = replace_all.split("]");
			console.log(r_line_arr);
			*/
			
			
			console.log($d_json);
		}
		
	})
}

//]]>
</script>
	<div id="container">
		<h3>로그분석</h3>
		<div class="contents">
			<div class="bbs-select">
               	<form id="frm" name="frm" method="post">
				<table summary="">
                	<colgroup>
                		<col width="10%">
                		<col width="*">
                	</colgroup>
                	<tbody>
                        <tr>
                        	<th>저장일자</th>
                            <td><input type="text" id="log_file_date" name="log_file_date" class="w10p datepicker"></td>  
                        </tr>
                        <tr>
                        	<td colspan="4" class="btn">
	                            <a href="javascript:getLogFileAjax();" class="btn-ty1 blue">분석</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                </form>
            </div>
		
        	
			<fieldset class="bbs-list">
				<legend>공지사항 목록</legend>
				<table summary="게시판관리 전체 목록">
					<colgroup><col width="7%" /><col width="*" /><col width="15%" /><col width="10%" /><col width="7%" /></colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">제목</th>
							<th scope="col">등록일</th>
                            <th scope="col">작성자</th>
                            <th scope="col">조회수</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</fieldset>
		</div>
		<!-- //contents -->
	</div>

		
<jsp:include page="/apage/inc/footer.do"></jsp:include>