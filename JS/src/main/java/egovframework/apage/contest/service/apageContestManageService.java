package egovframework.apage.contest.service;

import egovframework.apage.board.service.apageBoardManageVo;

public interface apageContestManageService {

	//랜덤 선정자 추첨
	public int createRandomSelect(apageBoardManageVo vo) throws Exception;
	
	//랜덤 대기자 추첨
	public int createRandomWaitingSelect(apageBoardManageVo vo) throws Exception;

}
