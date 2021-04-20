package egovframework.apage.system.service;

import java.util.List;

public interface apageSystemManageService {

	//팝업 리스트
	public List<apageSystemManageVo> selectadminPopupList(apageSystemManageVo vo) throws Exception;

	//팝업 리스트 카운트
	public int selectadminPopupListCnt(apageSystemManageVo vo) throws Exception;

	//팝업 입력
	public int insertAdminPopup(apageSystemManageVo vo) throws Exception;

	//팝업 상세
	public apageSystemManageVo getAdminPopupView(apageSystemManageVo vo) throws Exception;

	//팝업 삭제
	public int adminPopupDelete(apageSystemManageVo vo) throws Exception;

	//팝업 수정
	public int updateAdminPopup(apageSystemManageVo vo) throws Exception;

	//배너 리스트
	public List<apageSystemManageVo> selectadminBannerList(apageSystemManageVo vo) throws Exception;

	//배너 리스트 카운트
	public int selectadminBannerListCnt(apageSystemManageVo vo) throws Exception;

	//배너 입력
	public int insertAdminBanner(apageSystemManageVo vo) throws Exception;

	//배너 상세
	public apageSystemManageVo getAdminBannerView(apageSystemManageVo vo) throws Exception;

	//배너 삭제
	public int adminBannerDelete(apageSystemManageVo vo) throws Exception;

	//배너 수정
	public int updateAdminBanner(apageSystemManageVo vo) throws Exception;

	//컨텐츠 리스트
	public List<apageSystemManageVo> getContentsView(apageSystemManageVo vo) throws Exception;
	
	//컨텐츠 상세
	public apageSystemManageVo getContents(apageSystemManageVo vo) throws Exception;

	//컨텐츠 등록
	public int insertContents(apageSystemManageVo vo) throws Exception;

	//컨텐츠 수정
	public int updateContents(apageSystemManageVo vo) throws Exception;

	//공통코드 리스트
	public List<apageSystemManageVo> selectadminCodeList(apageSystemManageVo vo) throws Exception;

	//공통코드 리스트 카운트
	public int selectadminCodeListCnt(apageSystemManageVo vo) throws Exception;

	//분류명 중복 체크
	public int getcodeNameChk(apageSystemManageVo vo) throws Exception;

	//분류명 등록
	public int setcodeNameReg(apageSystemManageVo vo) throws Exception;

	//분류명 리스트
	public List<apageSystemManageVo> getCLCodeList(apageSystemManageVo vo) throws Exception;

	//공통코드 등록
	public int insertAdminCode(apageSystemManageVo vo) throws Exception;

	//공통코드 상세
	public apageSystemManageVo getCodeView(apageSystemManageVo vo) throws Exception;

	//공통코드 삭제
	public int setCodeDelete(apageSystemManageVo vo) throws Exception;

	//공통코드 수정
	public int setCodeUpdate(apageSystemManageVo vo) throws Exception;

	//등록된 코드 가져오기
	public List<apageSystemManageVo> getCodeNameList(apageSystemManageVo cvo) throws Exception;

	//코드 중복 체크
	public int codeSeqChk(apageSystemManageVo vo) throws Exception;

	//메인 컨텐츠 출력
	public apageSystemManageVo getContentsView_main(apageSystemManageVo vo) throws Exception;

	//팝업 메인페이지 리스트 조건 추가
	public List<apageSystemManageVo> selectClientPopupList(apageSystemManageVo vo) throws Exception;

	//사용자 메인페이지 배너 리스트
	public List<apageSystemManageVo> selectClientBannerList(apageSystemManageVo vo) throws Exception;

	//파일 ID 업데이트
	public int updateBannerFile(apageSystemManageVo vo) throws Exception;
	
	//파일 ID 업데이트
	public int updateContentFile(apageSystemManageVo vo) throws Exception;

	//팝업 체크박스 선택삭제
	public int adminPopupChkSelectDelete(apageSystemManageVo vo) throws Exception;

	//배너 체크박스 선택삭제
	public int adminBannerChkSelectDelete(apageSystemManageVo vo) throws Exception;
	
	//접근제어 리스트
	public List<apageSystemManageVo> selectadminAccList(apageSystemManageVo vo) throws Exception;

	//접근제어 리스트 카운트
	public int selectadminAccListCnt(apageSystemManageVo vo) throws Exception;
	
	//접근제어 입력
	public int insertAdminAcc(apageSystemManageVo vo) throws Exception;
	
	//접근제어 수정
	public int updateAdminAcc(apageSystemManageVo vo) throws Exception;
	
	//접근제어 상세
	public apageSystemManageVo getAdminAccView(apageSystemManageVo vo) throws Exception;
	
	//ip체크 카운트
	public int getAdminAccCnt(apageSystemManageVo vo) throws Exception;
	
	//접근제어 삭제
	public int deleteAdminAcc(apageSystemManageVo vo) throws Exception;

	//로그 입력
	public int insertAgentLog(apageSystemManageVo vo) throws Exception;
	
	/**
	 * 로드밸런싱 용
	 * */
	//팝업 메인페이지 리스트 조건 추가
	public List<apageSystemManageVo> lb_selectClientPopupList(apageSystemManageVo vo) throws Exception;

	//배너 리스트
	public List<apageSystemManageVo> lb_selectadminBannerList(apageSystemManageVo vo) throws Exception;
}
