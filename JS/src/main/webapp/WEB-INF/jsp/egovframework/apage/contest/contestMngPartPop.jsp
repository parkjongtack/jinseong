<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>
<style>
#container_pop {
    float: right;
    position: relative;
    width: 100%;
    height: 100%;
    background: #fff url(../images/common/px_1x1_ccc.gif) 100% 0 repeat-y;
    z-index: 1;
</style>
<script type="text/javascript">
$(document).ready(function(){
	var selectData = $("#arrayApp").val();
	
	var arrayData = selectData.split(',');
	$("#dataCnt").text("선택된 데이터 : 총 "+arrayData.length+"건");
});

function DoAdd(){

	if($("#selectPart").val() == ""){
		alert("변경할 조를 선택하세요.");
		return;
	}
	
	if($("#selectPart").val() == $("#currPart").val()){
		alert("현재 조는 "+$("#currPart").val()+"조 입니다. \n다른조를 선택하세요.");
		return;
	}
	
	if(confirm("선택한 신청자를 "+$("#selectPart").val()+"조로 변경합니다.\n실행하시겠습니까?") == true){

		var dataString = $("#dataForm").serialize();
		
		$.ajax({
			url		: "/apage/contest/appPartChange.do", 
			type 	: 'post', 
			data : dataString,
			dataType 	: 'json', 
			cache : false,
			success : function(data) {		
				//console.log(data);
				alert('저장되었습니다.');
				opener.parent.getAppListJson($("#currPart").val());
				window.close();							
			},
			error : function(a, b, c) {
				alert("알수없는 예외가 발생했습니다.");
				return;
			},beforeSend		:function(){
	        	//(이미지 보여주기 처리)
		        $('.wrap-loading').removeClass('display-none');
		    	},complete			:function(){
		           //(이미지 감추기 처리)
		            $('.wrap-loading').addClass('display-none');	     
		        },error		: function(data) {
					alert('에러가 발생했습니다.');
				},timeout:100000		
		});		
	}else{
		return;
	}	
}
</script>

<div id="container_pop">
	<div class="head">
		<h3>
			<span class="sbjt">조 변경</span>
		</h3>
	</div>
	<!-- //head -->
	<div class="contents">
		<div class="contents">
		
		<span id="dataCnt" style="color: red;"></span>
		
			<fieldset class="bbs-list">		
				<form id="dataForm" name="dataForm" method="post">
				<input type="hidden" id="arrayApp" name="arrayApp" value="${arrayApp }"/>	
				<input type="hidden" id="currPart" name="currPart" value="${currPart }"/>
					  <table summary="" style="margin-bottom: 25px;">
					  	<tr style="color:#222;">
				            <td colspan="4" class="al_c" style="background-color:#f8f8f8;">
								<select id="selectPart" name="part">
								<option value="">::조 선택::</option>
								<option value="1">1조</option>
								<option value="2">2조</option>
								<option value="3">3조</option>
								<option value="4">4조</option>								
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:DoAdd();" class="btn-ty1 blue">변경</a>				
				            </td>	            
				          </tr>
				      </table>					
				</form>
			</fieldset>
		</div>
		<!-- //contents -->	
	</div>
		<div class="wrap-loading display-none">
    		<div><img src="/images/common/loading1.gif" /></div>
		</div>