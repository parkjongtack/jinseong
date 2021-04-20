package egovframework.client.event.service;

import java.util.List;

public interface EventManageService {

	//이벤트 대회 신청 리스트
	public List<EventManageVo> lb_selectEventContestList(EventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 리스트 카운트
	public int lb_selectEventContestListCnt(EventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 상세
	public EventManageVo lb_getEventContestView(EventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 여부
	public int lb_chkMberApp_Event(EventManageVo vo) throws Exception;
	
	//이벤트 대회 조 리스트
	public List<EventManageVo> lb_selectEventContestPartList(EventManageVo vo) throws Exception;
	
	//이벤트 대회 신청
	public int insertEventContestApp(EventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 정보 결과 리스트
	public List<EventManageVo> lb_selectClientEventContestAppList(EventManageVo vo) throws Exception;
	
	//대회신청 플래그변경
	public int updateEventContestManageFlag(EventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 내역 상세
	public EventManageVo lb_selectMyEventContestView(EventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 정보 수정
	public int cpageUpdateMyEventContestAppInfo(EventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 취소
	public int cancelMyEventContest(EventManageVo vo) throws Exception;
	
	//마이페이지 - 이벤트 대회 대기조에서 1명 추출하기
	public EventManageVo lb_selectEventContstBackupMember(EventManageVo vo) throws Exception;
	
	//마이페이지 - 이벤트 대회 선정된 사람이 대회신청 취소할 경우, 추출한 대기자 넣기
	public int updateEventContstNewMember(EventManageVo vo) throws Exception;
	
	//이벤트 대회 대기자 재정렬
	public int eventContestAppResultOrderStatusChange(EventManageVo vo) throws Exception;
	
	//이벤트 대회 참가자 대상 체크
	public int lb_getEventContestAppTargetCnt(EventManageVo vo) throws Exception;
	
	//이벤트 대회 선정자 리스트
	public List<EventManageVo> lb_getEventContestSelectResultExcel(EventManageVo vo) throws Exception;
	
	//파트별 신청자 카운트 
	public List<EventManageVo> lb_selectEventContestAppPartApplyCnt(EventManageVo vo) throws Exception;
	
	//이벤트 대회 신청 프로시저 (왕중왕전)
	public int insertEventContestApp_procedure(EventManageVo vo) throws Exception;
}
