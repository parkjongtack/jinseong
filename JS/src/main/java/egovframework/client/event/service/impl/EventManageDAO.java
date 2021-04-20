package egovframework.client.event.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.event.service.EventManageVo;
import egovframework.common.GpAbstractDAO;

@Repository("EventManageDAO")
public class EventManageDAO extends GpAbstractDAO {
	
	private Logger logger	= Logger.getLogger(this.getClass());

	//이벤트 대회 신청
	public int insertEventContestApp(EventManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().insert("insertEventContestApp", vo);
	}

	//대회신청 플래그변경
	public int updateEventContestManageFlag(EventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateEventContestManageFlag", vo);
	}

	//이벤트 대회 신청 정보 수정
	public int cpageUpdateMyEventContestAppInfo(EventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("cpageUpdateMyEventContestAppInfo", vo);
	}

	//이벤트 대회 신청 취소
	public int cancelMyEventContest(EventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("cancelMyEventContest", vo);
	}

	//마이페이지 - 이벤트 대회 선정된 사람이 대회신청 취소할 경우, 추출한 대기자 넣기
	public int updateEventContstNewMember(EventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateEventContstNewMember", vo);
	}

	//이벤트 대회 대기자 재정렬
	public int eventContestAppResultOrderStatusChange(EventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("eventContestAppResultOrderStatusChange", vo);
	}

	//이벤트 대회 신청 프로시저 (왕중왕전)
	public int insertEventContestApp_procedure(EventManageVo vo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().queryForObject("insertEventContestApp_procedure", vo);
		return 1; 
	}
}
