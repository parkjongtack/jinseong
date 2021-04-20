package egovframework.apage.board.service;

import java.util.List;

import egovframework.common.service.FileManageVo;

public interface apageBoardManageService {

	//게시판 리스트
	public List<apageBoardManageVo> selectadminBoardList(apageBoardManageVo vo) throws Exception;

	//게시판 리스트 카운트
	public int selectadminBoardListCnt(apageBoardManageVo vo) throws Exception;

	//게시판 등록
	public int insertAdminBoard(apageBoardManageVo vo) throws Exception;
	
	//게시판 상세
	public apageBoardManageVo getAdminBoardView(apageBoardManageVo vo) throws Exception;

	//조회수 증가
	public int UpdateCnt(apageBoardManageVo vo) throws Exception;

	//게시판 삭제
	public int adminBoardDelete(apageBoardManageVo vo) throws Exception;

	//게시판 수정
	public int updateAdminBoard(apageBoardManageVo vo) throws Exception;

	//파일 삭제시 banner테이블의 첨부파일 seq값 제거
	public int setBoardAttachUpdt(apageBoardManageVo vo) throws Exception;
	
	//파일 삭제시 board테이블의 첨부파일 seq값 제거
	public int setBoardAttachUpdt2(apageBoardManageVo vo) throws Exception;
	
	//게시판 댓글 리스트
	public List<apageBoardManageVo> selectBoardComment(apageBoardManageVo vo) throws Exception;
	
	//게시판 댓글 등록
	public int insertBoardComment(apageBoardManageVo vo) throws Exception;
	
	//게시판 댓글 삭제
	public int deleteBoardComment(apageBoardManageVo vo) throws Exception;

	//게시판 댓글 수정
	public int updateBoardComment(apageBoardManageVo vo) throws Exception;
	
	//게시판 댓글 삭제
	public int deleteComment(apageBoardManageVo vo) throws Exception;	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////

	//staff 리스트
	public List<apageBoardManageVo> selectadminStaffList(apageBoardManageVo vo) throws Exception;

	//staff 리스트 카운트
	public int selectadminStaffListCnt(apageBoardManageVo vo) throws Exception;
	
	//staff 등록
	public int insertAdminStaff(apageBoardManageVo vo) throws Exception;
	
	//staff 수정
	public int updateAdminStaff(apageBoardManageVo vo) throws Exception;
	
	//staff 삭제
	public int deleteAdminStaff(apageBoardManageVo vo) throws Exception;	
	
	//staff 상세
	public apageBoardManageVo getAdminStaffView(apageBoardManageVo vo) throws Exception;

	//staff 조회수 증가
	public int UpdateStaffCnt(apageBoardManageVo vo) throws Exception;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	

	//대회 리스트
	public List<apageBoardManageVo> selectAdminContestList(apageBoardManageVo vo) throws Exception;
	
	//대회 리스트 no limit
	public List<apageBoardManageVo> selectAdminContestListNoLimit() throws Exception;
	
	//대회 리스트 카운트
	public int selectAdminContestListCnt(apageBoardManageVo vo) throws Exception;
	
	//대회 등록
	public int insertAdminContest(apageBoardManageVo vo) throws Exception;
	
	//대회 수정
	public int updateAdminContest(apageBoardManageVo vo) throws Exception;
	
	//대회 삭제
	public int deleteAdminContest(apageBoardManageVo vo) throws Exception;	
	
	//대회 상세
	public apageBoardManageVo getAdminContestView(apageBoardManageVo vo) throws Exception;
	
	//대회 첨부파일 초기화
	public int updateAdminContestFile(apageBoardManageVo vo) throws Exception;
	
	//대회신청자(선정 및 입금완료 상태) 이름 가져오기
	public String selectAllContestAppMemberName(apageBoardManageVo vo) throws Exception;
	
	//대회신청자(선정 및 입금완료 상태) 아이디 가져오기
	public String selectAllContestAppMemberID(apageBoardManageVo vo) throws Exception;
	
	//대회신청자(선정 및 입금완료 상태) 전화번호 가져오기
	public String selectAllContestAppMemberPhone(apageBoardManageVo vo) throws Exception;
	
	//대회신청자 리스트
	public List<apageBoardManageVo> selectAdminContestAppList(apageBoardManageVo vo) throws Exception;
	
	//대회신청자 리스트 카운트
	public int selectAdminContestAppListCnt(apageBoardManageVo vo) throws Exception;
	
	//대회신청자 수정
	public int updateAdminContestApp(apageBoardManageVo vo) throws Exception;
	
	//대회신청자 삭제
	public int deleteAdminContestApp(apageBoardManageVo vo) throws Exception;
	
	//대회신청자 리스트 엑셀출력
	public List<apageBoardManageVo> selectAdminContestAppExcel(apageBoardManageVo vo) throws Exception;
	
	//대회신청자 자리배치
	public List<apageBoardManageVo> createRandomLane(apageBoardManageVo vo) throws Exception;
	
	//대회 신청 마감 리스트
	public List<apageBoardManageVo> selectAdminContestAppFinishList(apageBoardManageVo vo) throws Exception;

	//대회 신청 마감 리스트 카운트
	public int selectAdminContestAppFinishListCnt(apageBoardManageVo vo) throws Exception;

	//선정결과 사용자 페이지 노출여부 update
	public int updateContestAppResultExposeYn(apageBoardManageVo vo) throws Exception;
	
	//대회 선정자 리스트 엑셀
	public List<apageBoardManageVo> selectAdminContestSelectResultExcel(apageBoardManageVo vo) throws Exception;
	
	//대회 선정자 레인 랜덤배정 업데이트
	public int updateAppRandomLane(List<apageBoardManageVo> vo) throws Exception;
	
	//대회 선정자 취소시 대기인원 선정 업데이트
	public int updateWaitingToSelect(apageBoardManageVo vo) throws Exception;
	
	//대회 예정,진행 리스트
	public List<apageBoardManageVo> apageSelectExpectContestList(apageBoardManageVo vo) throws Exception;
	
	//대회 그룹 리스트
	public List<apageBoardManageVo> apageSelectExpectContestGroupList(apageBoardManageVo vo) throws Exception;
	
	//자동배치결과 삭제
	public int deleteAdminBoardLaneResult(apageBoardManageVo vo) throws Exception;
	
	//대회 신청 결과(선정,대기)
	public List<apageBoardManageVo> selectAdminContestAppResultList(apageBoardManageVo vo) throws Exception;
	
	//대회 문자발송일 업데이트
	public int apageUpdateContestMsgSendDate(apageBoardManageVo vo) throws Exception;
	
	//대회신청 문자발송여부 Y 처리
	public int apageUpdateAppSmsFlag(apageBoardManageVo vo) throws Exception;
	
	//대회신청 문자발송여부 N 처리, 발송시간 UPDATE
	public int apageUpdateContestAppSendFlagAndDate(apageBoardManageVo vo) throws Exception;
	
	//대기자 이동시 대기번호 UPDATE
	public int apageUpdateContestAppWaitingNum(apageBoardManageVo vo) throws Exception;
	
	//대회신청결과 PART별 조회
	public List<apageBoardManageVo> selectAdminContestAppResultGubunPartList(apageBoardManageVo vo) throws Exception;
	
	//신청마감 후 랭키나열 후 상태 UPDATE
	public int apageContestAppResultOrderStatusChange(apageBoardManageVo vo) throws Exception;
	
	//업데이트 완료 후 대회 ROWNUM 정렬 플래그 cut_yn ==> Y로 변경
	public int apageContestCutYnUpdate(apageBoardManageVo vo) throws Exception;
	
	//대회 레인 랜덤배정 문자발송일 업데이트
	public int apageUpdateContestRandomLaneMsgSendDate(apageBoardManageVo vo) throws Exception;
	
	//대회 레인 랜덤배정 문자발송여부 N 처리, 발송시간 UPDATE
	public int apageUpdateContestRandomLaneSendFlagAndDate(apageBoardManageVo vo) throws Exception;
	
	//대회 레인 랜덤배정 문자발송여부 Y,N
	public int apageUpdateAppRandomLaneSmsFlag(apageBoardManageVo vo) throws Exception;
	
	//대회 레인 랜덤배정 문자발송 리스트
	public List<apageBoardManageVo> selectAdminContestAppLaneSmsSendResultList(apageBoardManageVo vo) throws Exception;
	
	//대회 레인 랜덤배정 결과(선정,대기)
	public List<apageBoardManageVo> selectAdminContestRandomLaneResultList(apageBoardManageVo vo) throws Exception;
	
	//레인 배치 결과 노출
	public int contestAppResultExposeYn(apageBoardManageVo vo) throws Exception;
	
	//각 대회 환불 리스트 
	public List<apageBoardManageVo> apageContestRefundList(apageBoardManageVo vo) throws Exception;
	
	//최우선 대기자 추출 
	public apageBoardManageVo apageGetCtPartWaitingInfo(apageBoardManageVo vo) throws Exception;
	
	//접수현황 노출 변경 
	public int apageAppSituationShowUpdate(apageBoardManageVo vo) throws Exception;
	
	//회원관리 > 대회신청이력
	public List<apageBoardManageVo> apageGetContestInfoOfEachMember(apageBoardManageVo vo) throws Exception;
	
	//회원관리 > 대회신청이력 상세
	public apageBoardManageVo apageGetContestInfoDetailOfEachMember(apageBoardManageVo vo) throws Exception;
	
	//쇼핑몰 이벤트대회 접수일자 정보 변경 
	public int apageUpdateShopEventManageInfo(apageBoardManageVo vo) throws Exception;
	
	
	
	/**
	 * 로드밸런싱 DB
	 * */
	//대회 리스트
	public List<apageBoardManageVo> lb_selectAdminContestList(apageBoardManageVo vo) throws Exception;
	
	//대회 리스트 카운트
	public int lb_selectAdminContestListCnt(apageBoardManageVo vo) throws Exception;
	
	//대회 상세
	public apageBoardManageVo lb_getAdminContestView(apageBoardManageVo vo) throws Exception;
	
	//쇼핑몰 이벤트 접수일자 정보
	public apageBoardManageVo lb_selectAdminShopEventManageInfo(apageBoardManageVo vo) throws Exception;
}
