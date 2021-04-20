package egovframework.client.board.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.board.service.BoardManageVo;
import egovframework.common.GpAbstractDAO;

@Repository("BoardManageDAO")
public class BoardManageDAO extends GpAbstractDAO {
	
	private Logger logger	= Logger.getLogger(this.getClass());

	//게시판 리스트
	public List<BoardManageVo> selectBoardList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("selectBoardList", vo);
		return list;
	}

	//게시판 리스트 카운트
	public int selectBoardListCnt(BoardManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectBoardListCnt", vo);		
		return list;
	}

	//공지사항 공지 리스트
	public List<BoardManageVo> getTopBoardList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("getTopBoardList", vo);
		return list;
	}
	
	//게시판 삭제
	public int BoardDelete(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("BoardDelete", vo);
	}

	//게시글 조회수 증가
	public int BoardUpdateCnt(BoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("BoardUpdateCnt", vo);
	}

	//게시판 상세
	public BoardManageVo getBoardView(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (BoardManageVo)selectByPk("getBoardView", vo);
	}

	//다음글
	public BoardManageVo getNextBoardView(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (BoardManageVo)selectByPk("getNextBoardView", vo);
	}

	//이전글
	public BoardManageVo getPreBoardView(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (BoardManageVo)selectByPk("getPreBoardView", vo);
	}

	//게시판 등록
	public int insertBoard(BoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertBoard", vo);
	}
	
	//게시판 수정
	public int updateBoard(BoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateBoard", vo);
	}
	
	////////////////////////////////////////////////////////////////////////
	
	//staff 리스트
	public List<BoardManageVo> selectStaffList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("selectStaffList", vo);
		return list;
	}

	//staff 리스트 카운트
	public int selectStaffListCnt(BoardManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectStaffListCnt", vo);		
		return list;
	}
	
	//staff 상세
	public BoardManageVo getStaffView(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (BoardManageVo)selectByPk("getStaffView", vo);
	}

	//staff 조회수 증가
	public int staffCnt(BoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("staffCnt", vo);
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	//대회신청 리스트
	public List<BoardManageVo> selectContestList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("selectContestList", vo);
		return list;
	}

	//대회신청 리스트 카운트
	public int selectContestListCnt(BoardManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectContestListCnt", vo);		
		return list;
	}
	
	//대회신청 상세
	public BoardManageVo getContestView(BoardManageVo vo) {
		return (BoardManageVo)selectByPk("getContestView", vo);
	}
	
	//대회 조회수 증가
	public int UpdateContestCnt(BoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("UpdateContestCnt", vo);
	}
	
	//대회 신청
	public int insertAppContest(BoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertAppContest", vo);
	}
	
	//대회신청 중복체크
	public int chkMberApp(BoardManageVo vo) {
		return (Integer) getSqlMapClientTemplate().queryForObject("chkMberApp", vo);
	}
	
	//대회신청 가능여부
	public BoardManageVo chkAppProcess(BoardManageVo vo) {
		return (BoardManageVo)selectByPk("chkAppProcess", vo);
	}
	
	//조별 인원체크
	public int chkLimitPart(BoardManageVo vo) {
		return (Integer) getSqlMapClientTemplate().queryForObject("chkLimitPart", vo);
	}
	

	public List<BoardManageVo> selectMain(BoardManageVo vo) {
		List<BoardManageVo> list	= list("selectMain", vo);
		return list;
	}
	public List<BoardManageVo> selectMainList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("selectMainList", vo);
		return list;
	}

	//////////////////////////////////////////////////////////////////////////
	
	//마이페이지 - 대회신청내역 리스트
	public List<BoardManageVo> selectMyContestList(BoardManageVo vo) {
		List<BoardManageVo> list	= list("selectMyContestList", vo);
		return list;
	}
	
	//마이페이지 - 대회신청내역 리스트 카운트
	public int selectMyContestListCnt(BoardManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectMyContestListCnt", vo);		
		return list;
	}
	
	//마이페이지 - 대회신청내역 상세
	public BoardManageVo selectMyContestView(BoardManageVo vo) {
		return (BoardManageVo)getSqlMapClientTemplate().queryForObject("selectMyContestView", vo);
	}
	
	//마이페이지 - 대회신청 취소
	public int cancelMyContest(BoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("cancelMyContest", vo);
	}
	
	//마이페이지 - 대기조에서 1명 추출하기
	public BoardManageVo selectBackupMember(BoardManageVo vo) {
		return (BoardManageVo)selectByPk("selectBackupMember", vo);
	}
	
	//마이페이지 - 선정된 사람이 대회신청 취소할 경우, 추출한 대기자 넣기
	public int updateNewMember(BoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateNewMember", vo);
	}

	//대회신청 플래그변경
	public int updateContestManageFlag(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("updateContestManageFlag", vo);
	}

	//대회신청 등록 (프로시저)
	public int pc_insertAppContest(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("pc_insertAppContest", vo);
	}

	//대회 그룹 중복신청 조회	
	public int selectGroupContestAppCnt(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("selectGroupContestAppCnt", vo);
	}

	//대회 신청정보 수정
	public int cpageUpdateMyContestAppInfo(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("cpageUpdateMyContestAppInfo", vo);
	}

	//대회 신청내역 조회
	public List<BoardManageVo> selectClientContestAppList(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectClientContestAppList", vo);
	}

	//대회 접수정보 ROWNUM 체크
	public BoardManageVo selectNowAppDataGetRownum(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (BoardManageVo)getSqlMapClientTemplate().queryForObject("selectNowAppDataGetRownum", vo);
	}

	//대회 신청정보 삭제
	public int deleteNowAppData(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("deleteNowAppData", vo);
	}

	//쇼핑몰 이벤트 상태 플래그변경
	public int updateShopEventManageInfo(BoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("updateShopEventManageInfo", vo);
	}
	
	
}
