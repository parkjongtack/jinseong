package egovframework.apage.event.service;

import java.util.List;

public interface ApageEventManageService {

	//이벤트 대회 신청 리스트
	public List<ApageEventManageVo> selectEventContestManageList(ApageEventManageVo vo) throws Exception;

	//이벤트 대회 신청 리스트 카운트
	public int selectEventContestManageListCnt(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 정보 등록
	public int insertEventContestManageInfo(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 프리미엄 레슨 파트 정보 등록
	public int insertEventContestLeassonPartInfo(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 프리미엄 레슨 파트 정보 리스트
	public List<ApageEventManageVo> selectEventContestLeassonPartList(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 프리미엄 상세페이지
	public ApageEventManageVo selectEventContestManageDetail(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 정보 수정
	public int updateEventContestManageInfo(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 프리미엄 레슨 파트 정보 수정
	public int updateEventContestManagePartInfo(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 정보 삭제
	public int deleteEventContestManageInfo(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 첨부파일 초기화
	public int updateEventContestFile(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 신청자 리스트JSON
	public List<ApageEventManageVo> selectAdminEventContestAppList(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 신청자 정보 수정
	public int updateAdminEventContestApp(ApageEventManageVo vo) throws Exception;
	
	//회원관리 > 이벤트 대회 신청 이력 상세
	public ApageEventManageVo apageGetEventContestInfoDetailOfEachMember(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 최우선 대기자 추출
	public ApageEventManageVo apageGetEventCtPartWaitingInfo(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 대회 선정자 취소시 대기인원 선정 업데이트
	public int updateEventContestWaitingToSelect(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 대기자 이동시 대기번호 UPDATE
	public int apageUpdateEventContestAppWaitingNum(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 신청마감 후 랭키나열 후 상태 UPDATE
	public int apageEventContestAppResultOrderStatusChange(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 신청자 정보 삭제
	public int deleteAdminEventContestApp(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 선정결과 리스트
	public List<ApageEventManageVo> selectEventContestAppFinishList(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 선정결과 리스트 카운트
	public int selectEventContestAppFinishListCnt(ApageEventManageVo vo) throws Exception;
	
	//업데이트 완료 후 대회 ROWNUM 정렬 플래그 cut_yn ==> Y로 변경
	public int apageEventContestCutYnUpdate(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 선정결과 노출여부 업데이트
	public int updateEventContestAppResultExposeYn(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 선정자 레인 랜덤배정 업데이트
	public int updateEventContestAppRandomLane(List<ApageEventManageVo> list) throws Exception;
	
	//이벤트 대회 선정자 랜덤 자리배치 리스트
	public List<ApageEventManageVo> createEventContestRandomLane(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 선정자 리스트 엑셀
	public List<ApageEventManageVo> selectEventContestSelectResultExcel(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 자리 배치 결과 노출여부 업데이트
	public int eventContestAppResultExposeYn(ApageEventManageVo vo) throws Exception;
	
	//대회 레인 랜덤배정 결과(선정,대기)
	public List<ApageEventManageVo> selectAdminEventContestRandomLaneResultList(ApageEventManageVo vo) throws Exception;
	
	//대회 레인 랜덤배정 문자발송일 업데이트
	public int apageUpdateEventContestRandomLaneMsgSendDate(ApageEventManageVo vo) throws Exception;

	//대회 레인 랜덤배정 문자발송여부 N 처리, 발송시간 UPDATE
	public int apageUpdateEventContestRandomLaneSendFlagAndDate(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 결과(선정,대기)
	public List<ApageEventManageVo> selectAdminEventContestAppResultList(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 문자발송일 업데이트
	public int apageUpdateEventContestMsgSendDate(ApageEventManageVo vo) throws Exception;
	
	//대회신청 문자발송여부 N 처리, 발송시간 UPDATE
	public int apageUpdateEventContestAppSendFlagAndDate(ApageEventManageVo vo) throws Exception;
	
	//대회신청자 대기번호 재정렬
	public int apageEventContestAppWaitingNumReOrderring(ApageEventManageVo vo) throws Exception;
	
	//왕중왕전 참가자 등록
	public int insertEventContestAppTarget(ApageEventManageVo vo) throws Exception;
	
	//왕중왕전 참가자 리스트
	public List<ApageEventManageVo> selectEventContestTopRankerList(ApageEventManageVo vo) throws Exception;

	//이벤트대회 신청자명단 엑셀출력
	public List<ApageEventManageVo> selectAdminEventContestAppExcel(ApageEventManageVo vo) throws Exception;
	
	//접수현황노출 업데이트
	public int apageEventCotnestAppSituationShowUpdate(ApageEventManageVo vo) throws Exception;
	
	//왕중왕전 대회 2차 접수 참가자 등록
	public int insertEventContestAppSecondTarget(ApageEventManageVo vo) throws Exception;
	
	//준비 및 진행 이벤트 대회 신청 리스트
	public List<ApageEventManageVo> apageSelectExpectEventContestList(ApageEventManageVo vo) throws Exception;
	
	//접수제한 리스트
	public List<ApageEventManageVo> apageSelectExpectEventContestGroupList(ApageEventManageVo vo) throws Exception;
	
	//이벤트 대회 대기자 리스트 엑셀
	public List<ApageEventManageVo> selectEventContestSelectResultExcelStatusReady(ApageEventManageVo vo) throws Exception;
	
	
}

