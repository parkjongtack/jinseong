package egovframework.client.lb.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.board.service.BoardManageVo;
import egovframework.common.GpAbstractDAO;
import egovframework.common.GpAbstractDAO_lb;

@Repository("LbBoardManageDAO")
public class LbBoardManageDAO extends GpAbstractDAO_lb {
	
	private Logger logger	= Logger.getLogger(this.getClass());


	public List<BoardManageVo> lb_selectMain(BoardManageVo vo) {
		List<BoardManageVo> list	= list("lb_selectMain", vo);
		return list;
	}
	public List<BoardManageVo> lb_selectMainList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("lb_selectMainList", vo);
		return list;
	}
	public List<BoardManageVo> lb_getTopBoardList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("lb_getTopBoardList", vo);
		return list;
	}
	public List<BoardManageVo> lb_selectBoardList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("lb_selectBoardList", vo);
		return list;
	}
	public int lb_selectBoardListCnt(BoardManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("lb_selectBoardListCnt", vo);		
		return list;
	}
	public List<BoardManageVo> lb_selectStaffList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("lb_selectStaffList", vo);
		return list;
	}
	public int lb_selectStaffListCnt(BoardManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("lb_selectStaffListCnt", vo);		
		return list;
	}

	//대회신청 리스트
	public List<BoardManageVo> lb_selectContestList(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return list("lb_selectContestList", vo);
	}
	

	//대회신청 리스트 카운트
	public int lb_selectContestListCnt(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("lb_selectContestListCnt", vo);
	}
	
	//대회 상세
	public BoardManageVo lb_getContestView(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (BoardManageVo)getSqlMapClientTemplate().queryForObject("lb_getContestView", vo);
	}
	
	//대회 접수정보 ROWNUM 체크
	public BoardManageVo lb_selectNowAppDataGetRownum(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (BoardManageVo)getSqlMapClientTemplate().queryForObject("lb_selectNowAppDataGetRownum", vo);
	}
	//게시판 상세
	public BoardManageVo lb_getBoardView(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (BoardManageVo)getSqlMapClientTemplate().queryForObject("lb_getBoardView", vo);
	}

	//대회신청 중복체크
	public int lb_chkMberApp(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("lb_chkMberApp", vo);
	}
	
	//마이페이지 - 대회신청내역 리스트 (이벤트 대회 union 고객감사)
	public List<BoardManageVo> lb_selectMyContestList_union(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return list("lb_selectMyContestList_union", vo);
	}

	//마이페이지 - 대회신청내역 리스트 카운트 (이벤트 대회 union 고객감사)
	public int lb_selectMyContestListCnt_union(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("lb_selectMyContestListCnt_union", vo);
	}
	
	//쇼핑몰 이벤트대회 정보조회
	public BoardManageVo lb_getShopEventManageInfo(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (BoardManageVo)getSqlMapClientTemplate().queryForObject("lb_getShopEventManageInfo", vo);
	}
	
}
