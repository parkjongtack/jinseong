<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="/WEB-INF/jsp/egovframework/apage/inc/import.jsp"%>

<jsp:include page="/apage/inc/header.do"></jsp:include>

<style type="text/css">
.deps-grp1 ul{padding-left:13px;}
.viewTable th{font-weight:bold !important; background-color:#f4f4f4;}
.viewTable td{text-align:left !important;}
.bbs-list{width:100%;}
.resizeBtn{display:inherit; background-color:#434b5d; padding: 5px 10px;}
.resizeBtn A:link,.resizeBtn a:HOVER,.resizeBtn a:ACTIVE,.resizeBtn A:visited{color:#fff;}
.resizeAtag{display:inline-block; color:#fff; vertical-align:middle;}
.resizeIcon{display:inline-block; width:15px; height:15px; background:url("/resources/apage/images/common/resize.png") no-repeat -15px 0; vertical-align:middle; margin-left:3px;}
.apage_contentCmsDiv_one{width:100%}
.apage_contentCmsDiv_one .layoutTable{}
.apage_contentCmsDiv_one .layoutTable .leftField{width:25%; vertical-align: top; padding-right:1%;}
.apage_contentCmsDiv_one .layoutTable .rightField{width:*; vertical-align: top;}

.bx-deps { border-left:1px solid #ddd; border-top:1px solid #ddd;}
.bx-deps dt { height:25px; padding:13px 0 0 10px; font-weight:bold; background:#f8f8f8; border-right:1px solid #ddd; border-bottom:1px solid #ddd; }
.bx-deps dd { border-right:1px solid #ddd; border-bottom:1px solid #ddd; height: 406px; }
.bx-deps #treeNode {height:500px; overflow-x:hidden; overflow-y:auto; }
.bx-deps .deps-grp1 {}
.bx-deps .deps-grp1 li {}
.bx-deps .deps-grp1 li a:hover, .bx-deps .deps-grp1 li a.on { color:#fff; background:#434b5d url(../images/common/ico_deps_arr_white.png) 10px 12px no-repeat; }
.bx-deps .deps-grp2 {}
.bx-deps .deps-grp2 li {}
.bx-deps .deps-grp2 li a { padding:9px 30px 0; background-position:20px 11px; } 
.bx-deps .deps-grp2 li a:hover, .bx-deps .deps-grp2 li a.on { color:#fff; background:#7c7c7c url(../images/common/ico_deps_arr_white.png) 20px 12px no-repeat; }
.bx-deps .deps-grp3 { background:#f8f8f8; }
.bx-deps .deps-grp3 li {}
.bx-deps .deps-grp3 li a { padding:9px 40px 0; background-position:30px 11px; }
.bx-deps .deps-grp3 li a:hover, .bx-deps .deps-grp3 li a.on { color:#666; text-decoration:underline; background:url(../images/common/ico_deps_arr_gray.png) 30px 12px no-repeat; }
</style>

<!-- <link href="/resources/admin/css/jquery-ui.min.css" rel="stylesheet"> -->

<script type="text/javascript">

var oEditors = [];
$(document).ready(function () {
	
	nhn.husky.EZCreator.createInIFrame({
	    oAppRef: oEditors,
	    elPlaceHolder: "contents",
	    sSkinURI: "/resources/se2/SmartEditor2Skin.html",
	    htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			fOnBeforeUnload : function(){}
		},	
	    fCreator: "createSEditor2"
	});
	
	$('.actionSubmit').click(function(){
		var aflag		= $("#aFlag").val();		
		document.getElementById('contents').value	= '';
	 	oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
	 	
	 	var object = $("textarea[id='contents']");
	 	var value = object.val();
	 	
	 	if(value == null || value.length <= 0){
	 		alert("내용을 입력해주세요.");
	 		value = null;
	 		obejct.focus();
	 		return;
	 	}
	 	
	 	var paramUrl = "";
	 	if(aflag == 'create'){
	 		paramUrl	= '/apage/system/createContents.do';	
	 	}else{
	 		paramUrl	= '/apage/system/updateContents.do';
	 	}
		
		var paramData	= $("#aForm").serialize();
		
		ajaxCallFn(paramUrl,paramData,aflag);
	});
	
	$('#treeNode').find('li').each(function(){
		var $this	= $(this);
		var pmc		= $.trim($this.attr('parentMenuCd'));
		if(pmc != '0'){
			var $pmc	= $('#'+pmc); 
			if($pmc.children('ul').length > 0){
				$pmc.children('ul').append($this);
			}else{
				var $pmcUl	= $pmc.closest('ul');
				var $ul	= $('<ul>').appendTo($pmc);
				$ul.append($this);
			}
		}
	});
	
	$('#treeNode').find('a').click(function(){		
		var $this		= $(this);
		var $li			= $this.closest('li');
		$('#menu_cd').val($li.attr('id'));
		$('#menu_nm').val($this.text());
		$('.setMenuNm').html('- '+$this.text());
		var paramUrl	= '/apage/system/getContents.do';
		var paramData	= $("#aForm").serialize();
		ajaxCallFn(paramUrl,paramData,'menuConListApplyView');	
	});
	
});

function ajaxCallFn(paramUrl,paramData,gb){
	$.ajax({
		url			: paramUrl
		,data		: paramData
		,async		: true
		,type		: "post"
		,dataType	: 'json'
		,beforeSend: function (xhr) {
			xhr.setRequestHeader('AJAX', true);
		}
		,success	: function(jsonObj){
			if(jsonObj.root.ResultCode == 'conEmpty'){
				alert(jsonObj.root.ResultMsg);
				conCreateFieldResetFn();
				$("#aFlag").val("create");
			}else{
				if(gb == 'menuConListApplyView'){
					menuConApplyViewFn(jsonObj);					
				}else if(gb == 'create'){
					alert('저장 되었습니다.');
					menuConApplyViewFn(jsonObj);
				}else if(gb == 'update'){
					alert('수정 되었습니다.');
					menuConApplyViewFn(jsonObj);
				}
			}
		}
		,error		: function(xhr,status,error){
			alert("status : "+xhr.status+"\n"+"error : "+error);
		}
	});
}

function menuConApplyViewFn(jsonObj){
	$("#aFlag").val("update");
	document.getElementById('menu_cd').value 		= jsonObj.root.dataset0[0].menu_cd;
	document.getElementById('menu_nm').value 		= jsonObj.root.dataset0[0].menu_nm;
	document.getElementById('contents').value 		= jsonObj.root.dataset0[0].contents;
	oEditors.getById["contents"].exec("SET_CONTENTS", [document.getElementById('contents').value]);
}

function conCreateFieldResetFn(){
    var aForm 	= document.getElementById('aForm');
    var em 		= aForm.elements;
    for (var i = 0; i < em.length; i++) {
        if (em[i].name == 'user_con_seq') em[i].value 	= '';
        if (em[i].type == 'text') em[i].value 			= '';
        if (em[i].type == 'textarea') em[i].value 		= '';
    }
    oEditors.getById["contents"].exec("SET_CONTENTS", ['']);
}

</script>

<div id="container">
	<h3>콘텐츠관리</h3>
	<div class="contents">
       	<ul class="ct-tab">
			<li class="tab1"><a href="<c:url value='/apage/system/adminContent.do'/>" class="on">콘텐츠관리</a></li>
               <li class="tab1"><a href="<c:url value='/apage/system/adminPopupList.do'/>">팝업관리</a></li>
               <li class="tab1"><a href="<c:url value='/apage/system/adminBannerList.do'/>">배너관리</a></li>
               <li class="tab1"><a href="<c:url value='/apage/system/adminCodeList.do'/>">공통코드관리</a></li>
		</ul>
		
		<div class="apage_contentCmsDiv_one">
			<table class="layoutTable">
				<tr>
					<td class="leftField big">
						<dl class="bx-deps">
							<dt>메뉴구성</dt>
							<dd>
								<ul class="deps-grp1" id="treeNode">
									<li id="C0001" 	  parentMenuCd="0" 	  ><a href="javascript:void(0);">진승무역</a></li>							
										<li id="C0001-01" parentMenuCd="C0001"><a href="javascript:void(0);">인사말</a></li>							
										<li id="C0001-02" parentMenuCd="C0001"><a href="javascript:void(0);">연혁</a></li>
										<li id="C0001-03" parentMenuCd="C0001"><a href="javascript:void(0);">전국볼링장</a></li>
										<li id="C0001-04" parentMenuCd="C0001"><a href="javascript:void(0);">찾아오시는길</a></li>
										<li id="C0001-05" parentMenuCd="C0001"><a href="javascript:void(0);">개인정보취급방침</a></li>
										<li id="C0001-06" parentMenuCd="C0001"><a href="javascript:void(0);">이용약관</a></li>
								</ul>
							</dd>
						</dl>
					</td>
					<td class="rightField">
						<fieldset class="bbs-list">
							<form id="aForm" name="aForm" action="/apage/contents/contentsCMS" method="post">
								<input id="menu_cd" name="menu_cd" valueCheck="true" type="hidden" value=""/>
								<input id="menu_nm" name="menu_nm" type="hidden" value=""/>
								<input type="hidden" id="aFlag" value="" />
								<table class="viewTable">
									<tr>
										<th colspan="4" style="text-align:left;">컨텐츠 관리
											<span class="setMenuNm">- 선택된 메뉴가 없습니다.</span>
										</th>
									</tr>
									<tr>
										<td colspan="4">
											<textarea id="contents" name="contents" style="width:100%;height:350px;"></textarea>
										</td>
									</tr>
								</table>
								<div style="padding:15px 0 10px 0; text-align:center;">
									<a href="javascript:void(0);" class="btn-ty1 blue actionSubmit">저장</a>
									<a href="javascript:void(0);" class="btn-ty1 blue" onclick="conCreateFieldResetFn();">취소</a>
								</div>
							</form>
						</fieldset>			
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
		
<jsp:include page="/apage/inc/footer.do"></jsp:include>