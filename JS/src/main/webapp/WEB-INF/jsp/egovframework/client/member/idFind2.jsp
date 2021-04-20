<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/client/inc/import.jsp"%>
<c:import url="/client/header.do" />
<script type="text/javascript">


</script>

<!--//LOC_WRAP //E-->
<div id="container" class="subpage">
	<div id="contents">
		<h2 class="hide">본문</h2>
		<c:import url="/client/snb.do" />
                <div class="sub_content">
                    <div class="inner">
                        <div class="sub_head">
                            <h3 class="c_tit">아이디 찾기</h3>
                        </div>


                        <div id="member">
                            
                            <div class="find_id_wrap">

                                <div class="findBx">
                                    <div class="f_password">
                                        <p class="tit">아이디 확인창</p>
                                        <div class="find_txt">
                                            <div>
                                                <dl>
                                                	<dd>
                                                    <label for="id3">고객님의 아이디는 다음과 같습니다. </label>
                                                    
                                                    <c:out value='${memberId}'/>
                                                                                                        
                                                    </dd>
                                                    
                                                </dl>
                                                <div class="fd_btn">
                                                    <a href="/membership/login.do" class="btn_gr w200">로그인</a>
                                                    <a href="/membership/pwFind.do" class="btn_or w200">비밀번호 찾기</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <!-- //FIND_ID_WRAP [E] -->

                        </div>
                        <!-- //member -->

                    </div>
                    <!--inner-->
                </div>
            </div>
            <!-- #CONTENTS //E -->
        </div>
        <!-- #CONTAINER //E -->

        <!--//#FOOTER //E-->



<jsp:include page="/client/footer.do"></jsp:include>