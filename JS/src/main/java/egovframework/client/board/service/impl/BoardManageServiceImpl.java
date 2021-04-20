package egovframework.client.board.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.board.service.impl.BoardManageDAO;
import egovframework.client.lb.service.impl.LbBoardManageDAO;
import egovframework.client.board.service.BoardManageService;
import egovframework.client.board.service.BoardManageVo;

@Repository("BoardManageService")
public class BoardManageServiceImpl implements BoardManageService {
	
private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="BoardManageDAO")
	BoardManageDAO BoardManageDAO;
	
	@Resource(name="LbBoardManageDAO")
	LbBoardManageDAO LbBoardManageDAO;
	
	
	
	//게시판 리스트
	@Override
	public List<BoardManageVo> selectBoardList(BoardManageVo vo) throws Exception {
		List<BoardManageVo> list = BoardManageDAO.selectBoardList(vo);
		return list;
	}

	//게시판 리스트 카운트
	@Override
	public int selectBoardListCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.selectBoardListCnt(vo);
	}

	//공지사항 공지 리스트
	@Override
	public List<BoardManageVo> getTopBoardList(BoardManageVo vo) throws Exception {
		List<BoardManageVo> list = BoardManageDAO.getTopBoardList(vo);
		return list;
	}
	
	//게시판 삭제
	@Override
	public int BoardDelete(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.BoardDelete(vo);
	}

	//게시글 조회수 증가
	@Override
	public int BoardUpdateCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.BoardUpdateCnt(vo);
	}

	//게시글 상세
	@Override
	public BoardManageVo getBoardView(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.getBoardView(vo);
	}
	
	//다음글 
	@Override
	public BoardManageVo getNextBoardView(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.getNextBoardView(vo);
	}

	//이전글
	@Override
	public BoardManageVo getPreBoardView(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.getPreBoardView(vo);
	}

	//게시판 등록
	@Override
	public int insertBoard(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= BoardManageDAO.insertBoard(vo);
		return list;
	}
	
	//게시판 수정
	@Override
	public int updateBoard(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.updateBoard(vo);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////

	//staff 리스트
	@Override
	public List<BoardManageVo> selectStaffList(BoardManageVo vo) throws Exception {
		List<BoardManageVo> list = BoardManageDAO.selectStaffList(vo);
		return list;
	}
	
	//staff 리스트 카운트
	@Override
	public int selectStaffListCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.selectStaffListCnt(vo);
	}
	
	//staff 상세
	@Override
	public BoardManageVo getStaffView(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.getStaffView(vo);
	}
	
	//staff 조회수 증가
	@Override
	public int staffCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.staffCnt(vo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//대회신청 리스트
	@Override
	public List<BoardManageVo> selectContestList(BoardManageVo vo) throws Exception {
		List<BoardManageVo> list = BoardManageDAO.selectContestList(vo);
		return list;
	}	
	//대회신청 리스트 카운트
	@Override
	public int selectContestListCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.selectContestListCnt(vo);
	}
	//대회신청 상세
	@Override
	public BoardManageVo getContestView(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.getContestView(vo);
	}
	//대회 조회수 증가
	@Override
	public int UpdateContestCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.UpdateContestCnt(vo);
	}
	//대회 신청
	@Override
	public int insertAppContest(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= BoardManageDAO.insertAppContest(vo);
		return list;
	}
	
	//대회신청 중복체크
	@Override
	public int chkMberApp(BoardManageVo vo) throws Exception {
		return BoardManageDAO.chkMberApp(vo);
	}
	
	//대회신청 가능여부
	@Override
	public BoardManageVo chkAppProcess(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.chkAppProcess(vo);
	}
	
	//조별 인원체크
	@Override
	public int chkLimitPart(BoardManageVo vo) throws Exception {
		return BoardManageDAO.chkLimitPart(vo);
	}
	

	@Override
	public List<BoardManageVo> selectMain(BoardManageVo vo) throws Exception {
		List<BoardManageVo> list = BoardManageDAO.selectMain(vo);
		return list;
	}
	@Override
	public List<BoardManageVo> selectMainList(BoardManageVo vo) throws Exception {
		List<BoardManageVo> list = BoardManageDAO.selectMainList(vo);
		return list;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	//마이페이지 - 대회신청내역 리스트
	@Override
	public List<BoardManageVo> selectMyContestList(BoardManageVo vo) throws Exception {
		List<BoardManageVo> list = BoardManageDAO.selectMyContestList(vo);
		return list;
	}
	
	//마이페이지 - 대회신청내역 리스트 카운트
	@Override
	public int selectMyContestListCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.selectMyContestListCnt(vo);
	}
	
	//마이페이지 - 대회신청내역 상세
	@Override
	public BoardManageVo selectMyContestView(BoardManageVo vo) throws Exception {
		BoardManageVo list = BoardManageDAO.selectMyContestView(vo);
		return list;
	}

	//마이페이지 - 대회신청 취소
	@Override
	public int cancelMyContest(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.cancelMyContest(vo);
	}
	
	//마이페이지 - 대기조에서 1명 추출하기
	@Override
	public BoardManageVo selectBackupMember(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.selectBackupMember(vo);
	}
	
	//마이페이지 - 선정된 사람이 대회신청 취소할 경우, 추출한 대기자 넣기
	@Override
	public int updateNewMember(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.updateNewMember(vo);
	}

	//대회신청 플래그변경
	@Override
	public int updateContestManageFlag(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.updateContestManageFlag(vo);
	}

	//대회신청 등록 (프로시저)
	@Override
	public int pc_insertAppContest(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.pc_insertAppContest(vo);
	}

	//대회 그룹 중복신청 조회
	@Override
	public int selectGroupContestAppCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.selectGroupContestAppCnt(vo);
	}

	//대회 신청정보 수정
	@Override
	public int cpageUpdateMyContestAppInfo(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.cpageUpdateMyContestAppInfo(vo);
	}

	//대회 신청내역 조회
	@Override
	public List<BoardManageVo> selectClientContestAppList(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.selectClientContestAppList(vo);
	}
	
	//대회 접수정보 ROWNUM 체크
	@Override
	public BoardManageVo selectNowAppDataGetRownum(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.selectNowAppDataGetRownum(vo);
	}

	//대회 신청정보 삭제
	@Override
	public int deleteNowAppData(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.deleteNowAppData(vo);
	}
	//쇼핑몰 이벤트 상태 플래그변경
	@Override
	public int updateShopEventManageInfo(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return BoardManageDAO.updateShopEventManageInfo(vo);
	}
	
	
	
	
	
	
	
	
	
	

	/**
	 * 로드밸런싱용
	 * */
	@Override
	public List<BoardManageVo> lb_selectMain(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectMain(vo);
	}

	@Override
	public List<BoardManageVo> lb_selectMainList(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectMainList(vo);
	}

	@Override
	public List<BoardManageVo> lb_getTopBoardList(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_getTopBoardList(vo);
	}

	@Override
	public List<BoardManageVo> lb_selectBoardList(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectBoardList(vo);
	}

	@Override
	public int lb_selectBoardListCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectBoardListCnt(vo);
	}

	@Override
	public List<BoardManageVo> lb_selectStaffList(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectStaffList(vo);
	}

	@Override
	public int lb_selectStaffListCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectStaffListCnt(vo);
	}

	//대회신청 리스트
	@Override
	public List<BoardManageVo> lb_selectContestList(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectContestList(vo);
	}

	//대회신청 리스트 카운트
	@Override
	public int lb_selectContestListCnt(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectContestListCnt(vo);
	}

	//대회 상세
	@Override
	public BoardManageVo lb_getContestView(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_getContestView(vo);
	}

	//대회 접수정보 ROWNUM 체크
	@Override
	public BoardManageVo lb_selectNowAppDataGetRownum(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectNowAppDataGetRownum(vo);
	}

	//게시판 상세
	@Override
	public BoardManageVo lb_getBoardView(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_getBoardView(vo);
	}

	//대회신청 중복체크
	@Override
	public int lb_chkMberApp(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_chkMberApp(vo);
	}

	//마이페이지 - 대회신청내역 리스트 (이벤트 대회 union 고객감사)
	@Override
	public List<BoardManageVo> lb_selectMyContestList_union(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectMyContestList_union(vo);
	}

	//마이페이지 - 대회신청내역 리스트 카운트 (이벤트 대회 union 고객감사)
	@Override
	public int lb_selectMyContestListCnt_union(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_selectMyContestListCnt_union(vo);
	}

	//쇼핑몰 이벤트대회 정보조회
	@Override
	public BoardManageVo lb_getShopEventManageInfo(BoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbBoardManageDAO.lb_getShopEventManageInfo(vo);
	}
	

}
