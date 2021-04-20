package egovframework.apage.lb.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.common.GpAbstractDAO_lb;

@Repository("LbApageBoardManageDAO")
public class LbApageBoardManageDAO extends GpAbstractDAO_lb {
	
	private Logger logger	= Logger.getLogger(this.getClass());

	//대회 리스트
	public List<apageBoardManageVo> lb_selectAdminContestList(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("lb_selectAdminContestList",vo);
	}

	//대회 리스트 카운트
	public int lb_selectAdminContestListCnt(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("lb_selectAdminContestListCnt",vo);	
	}

	//대회 상세
	public apageBoardManageVo lb_getAdminContestView(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (apageBoardManageVo)getSqlMapClientTemplate().queryForObject("lb_getAdminContestView",vo);	
	}

	//쇼핑몰 이벤트 접수일자 정보
	public apageBoardManageVo lb_selectAdminShopEventManageInfo(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (apageBoardManageVo)getSqlMapClientTemplate().queryForObject("lb_selectAdminShopEventManageInfo",vo);	
	}


	
}
