package egovframework.client.lb.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.board.service.BoardManageVo;
import egovframework.client.event.service.EventManageVo;
import egovframework.common.GpAbstractDAO;
import egovframework.common.GpAbstractDAO_lb;

@Repository("LbEventManageDAO")
public class LbEventManageDAO extends GpAbstractDAO_lb {
	
	private Logger logger	= Logger.getLogger(this.getClass());

	//이벤트 대회 신청 리스트
	public List<EventManageVo> lb_selectEventContestList(EventManageVo vo) {
		// TODO Auto-generated method stub
		return list("lb_selectEventContestList", vo);
	}

	//이벤트 대회 신청 리스트 카운트
	public int lb_selectEventContestListCnt(EventManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("lb_selectEventContestListCnt",vo);
	}

	//이벤트 대회 신청 상세
	public EventManageVo lb_getEventContestView(EventManageVo vo) {
		// TODO Auto-generated method stub
		return (EventManageVo)getSqlMapClientTemplate().queryForObject("lb_getEventContestView",vo);
	}

	//이벤트 대회 신청 여부
	public int lb_chkMberApp_Event(EventManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("lb_chkMberApp_Event",vo);
	}

	//이벤트 대회 조 리스트
	public List<EventManageVo> lb_selectEventContestPartList(EventManageVo vo) {
		// TODO Auto-generated method stub
		return list("lb_selectEventContestPartList", vo);
	}

	//이벤트 대회 신청 정보 결과 리스트
	public List<EventManageVo> lb_selectClientEventContestAppList(EventManageVo vo) {
		// TODO Auto-generated method stub
		return list("lb_selectClientEventContestAppList", vo);
	}

	//이벤트 대회 신청 내역 상세
	public EventManageVo lb_selectMyEventContestView(EventManageVo vo) {
		// TODO Auto-generated method stub
		return (EventManageVo)getSqlMapClientTemplate().queryForObject("lb_selectMyEventContestView",vo);
	}

	//마이페이지 - 이벤트 대회 대기조에서 1명 추출하기
	public EventManageVo lb_selectEventContstBackupMember(EventManageVo vo) {
		// TODO Auto-generated method stub
		return (EventManageVo)getSqlMapClientTemplate().queryForObject("lb_selectEventContstBackupMember",vo);
	}
	
	//마이페이지 - 이벤트 대회 대기조에서 1명 추출하기
	public int lb_getEventContestAppTargetCnt(EventManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("lb_getEventContestAppTargetCnt",vo);
	}
	
	//이벤트 대회 선정자 리스트 엑셀
	public List<EventManageVo> lb_getEventContestSelectResultExcel(EventManageVo vo) {
		// TODO Auto-generated method stub
		return list("lb_getEventContestSelectResultExcel",vo);
	}
	
	//파트별 신청자 카운트 
	public List<EventManageVo> lb_selectEventContestAppPartApplyCnt(EventManageVo vo) {
		// TODO Auto-generated method stub
		return list("lb_selectEventContestAppPartApplyCnt",vo);
	}


	
	
	
}
