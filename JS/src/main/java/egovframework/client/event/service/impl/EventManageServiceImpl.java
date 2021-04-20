package egovframework.client.event.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.event.service.EventManageService;
import egovframework.client.event.service.EventManageVo;
import egovframework.client.lb.service.impl.LbEventManageDAO;

@Repository("EventManageService")
public class EventManageServiceImpl implements EventManageService{

	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="EventManageDAO")
	EventManageDAO EventManageDAO;

	@Resource(name="LbEventManageDAO")
	LbEventManageDAO LbEventManageDAO;

	//이벤트 대회 신청 리스트
	@Override
	public List<EventManageVo> lb_selectEventContestList(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_selectEventContestList(vo);
		
	}

	//이벤트 대회 신청 리스트 카운트
	@Override
	public int lb_selectEventContestListCnt(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_selectEventContestListCnt(vo);
	}

	//이벤트 대회 신청 상세
	@Override
	public EventManageVo lb_getEventContestView(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_getEventContestView(vo);
	}

	//이벤트 대회 신청 여부
	@Override
	public int lb_chkMberApp_Event(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_chkMberApp_Event(vo);
	}

	//이벤트 대회 조 리스트
	@Override
	public List<EventManageVo> lb_selectEventContestPartList(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_selectEventContestPartList(vo);
	}

	//이벤트 대회 신청
	@Override
	public int insertEventContestApp(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return EventManageDAO.insertEventContestApp(vo);
	}

	//이벤트 대회 신청 정보 결과 리스트
	@Override
	public List<EventManageVo> lb_selectClientEventContestAppList(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_selectClientEventContestAppList(vo);
	}

	//대회신청 플래그변경
	@Override
	public int updateEventContestManageFlag(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return EventManageDAO.updateEventContestManageFlag(vo);
	}

	//이벤트 대회 신청 내역 상세
	@Override
	public EventManageVo lb_selectMyEventContestView(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_selectMyEventContestView(vo);
	}
	
	//이벤트 대회 신청 정보 수정
	@Override
	public int cpageUpdateMyEventContestAppInfo(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return EventManageDAO.cpageUpdateMyEventContestAppInfo(vo);
	}
	
	//이벤트 대회 신청 취소
	@Override
	public int cancelMyEventContest(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return EventManageDAO.cancelMyEventContest(vo);
	}

	//마이페이지 - 이벤트 대회 대기조에서 1명 추출하기
	@Override
	public EventManageVo lb_selectEventContstBackupMember(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_selectEventContstBackupMember(vo);
	}

	//마이페이지 - 이벤트 대회 선정된 사람이 대회신청 취소할 경우, 추출한 대기자 넣기
	@Override
	public int updateEventContstNewMember(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return EventManageDAO.updateEventContstNewMember(vo);
	}

	//이벤트 대회 대기자 재정렬
	@Override
	public int eventContestAppResultOrderStatusChange(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return EventManageDAO.eventContestAppResultOrderStatusChange(vo);
	}

	//이벤트 대회 참가자 대상 카운트
	@Override
	public int lb_getEventContestAppTargetCnt(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_getEventContestAppTargetCnt(vo);
	}

	//이벤트 대회 선정자 리스트
	@Override
	public List<EventManageVo> lb_getEventContestSelectResultExcel(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_getEventContestSelectResultExcel(vo);
	}

	//파트별 신청자 카운트 
	@Override
	public List<EventManageVo> lb_selectEventContestAppPartApplyCnt(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbEventManageDAO.lb_selectEventContestAppPartApplyCnt(vo);
	}

	//이벤트 대회 신청 프로시저 (왕중왕전)
	@Override
	public int insertEventContestApp_procedure(EventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return EventManageDAO.insertEventContestApp_procedure(vo);
	}
	
	
	
}
