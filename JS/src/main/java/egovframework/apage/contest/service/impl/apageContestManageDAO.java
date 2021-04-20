package egovframework.apage.contest.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.common.GpAbstractDAO;

@Repository("apageContestManageDAO")	
public class apageContestManageDAO extends GpAbstractDAO {

	private Logger logger	= Logger.getLogger(this.getClass());
	
	//랜덤 선정자 추첨
	public  int createRandomSelect(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("createRandomSelect", vo);
	}

	//랜덤 대기자 추첨
	public int createRandomWaitingSelect(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("createRandomWaitingSelect", vo);
	}
	
}
