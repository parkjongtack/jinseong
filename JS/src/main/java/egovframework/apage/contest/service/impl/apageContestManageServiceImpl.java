package egovframework.apage.contest.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.apage.contest.service.apageContestManageService;

@Repository("apageContestManageService")
public class apageContestManageServiceImpl implements apageContestManageService {

	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="apageContestManageDAO")
	apageContestManageDAO apageContestManageDAO;
	
	/** 랜덤 선정자 추첨 **/
	@Override
	public int createRandomSelect(apageBoardManageVo vo) throws Exception {
		// 
		return apageContestManageDAO.createRandomSelect(vo);
	}

	//랜덤 대기자 추첨
	@Override
	public int createRandomWaitingSelect(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub		
		return apageContestManageDAO.createRandomWaitingSelect(vo);
	}

	
	
	
	
	
}
