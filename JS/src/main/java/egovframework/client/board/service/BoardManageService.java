package egovframework.client.board.service;

import java.util.List;

public interface BoardManageService {

	//게시판 리스트
	public List<BoardManageVo> selectBoardList(BoardManageVo vo) throws Exception;

	//게시판 리스트 카운트
	public int selectBoardListCnt(BoardManageVo vo) throws Exception;

	//공지사항 공지 리스트
	public List<BoardManageVo> getTopBoardList(BoardManageVo vo) throws Exception;

	//게시판 삭제
	public int BoardDelete(BoardManageVo vo) throws Exception;
	
	//게시판 조회수 증가
	public int BoardUpdateCnt(BoardManageVo vo) throws Exception;

	//게시판 상세
	public BoardManageVo getBoardView(BoardManageVo vo) throws Exception;

	//다음글 가져오기
	public BoardManageVo getNextBoardView(BoardManageVo vo) throws Exception;

	//이전글 가져오기
	public BoardManageVo getPreBoardView(BoardManageVo vo) throws Exception;
	
	//게시판 등록
	public int insertBoard(BoardManageVo vo) throws Exception;
	
	//게시판 수정
	public int updateBoard(BoardManageVo vo) throws Exception;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////

	//staff 리스트
	public List<BoardManageVo> selectStaffList(BoardManageVo vo) throws Exception;

	//staff 리스트 카운트
	public int selectStaffListCnt(BoardManageVo vo) throws Exception;
	
	//staff 상세
	public BoardManageVo getStaffView(BoardManageVo vo) throws Exception;

	//staff 조회수 증가
	public int staffCnt(BoardManageVo vo) throws Exception;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//대회신청 리스트
	public List<BoardManageVo> selectContestList(BoardManageVo vo) throws Exception;
	
	//대회신청 리스트 카운트
	public int selectContestListCnt(BoardManageVo vo) throws Exception;
	
	//대회 상세
	public BoardManageVo getContestView(BoardManageVo vo) throws Exception;
	
	//대회 조회수 증가
	public int UpdateContestCnt(BoardManageVo vo) throws Exception;
	
	//대회 신청
	public int insertAppContest(BoardManageVo vo) throws Exception;
	
	//대회신청 중복체크
	public int chkMberApp(BoardManageVo vo) throws Exception;
	
	//대회신청 가능여부
	public BoardManageVo chkAppProcess(BoardManageVo vo) throws Exception;
	
	//조별 인원체크
	public int chkLimitPart(BoardManageVo vo) throws Exception;	
	
	public List<BoardManageVo> selectMain(BoardManageVo vo) throws Exception;
	public List<BoardManageVo> selectMainList(BoardManageVo vo) throws Exception;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//마이페이지 - 대회신청내역 리스트
	public List<BoardManageVo> selectMyContestList(BoardManageVo vo) throws Exception;
	
	//마이페이지 - 대회신청내역 리스트 카운트
	public int selectMyContestListCnt(BoardManageVo vo) throws Exception;

	//마이페이지 - 대회신청내역 상세
	public BoardManageVo selectMyContestView(BoardManageVo vo) throws Exception;
	
	//마이페이지 - 대회신청 취소
	public int cancelMyContest(BoardManageVo vo) throws Exception;
	
	//마이페이지 - 대기조에서 1명 추출하기
	public BoardManageVo selectBackupMember(BoardManageVo vo) throws Exception;
	
	//마이페이지 - 선정된 사람이 대회신청 취소할 경우, 추출한 대기자 넣기
	public int updateNewMember(BoardManageVo vo) throws Exception;
	
	//대회신청 플래그변경
	public int updateContestManageFlag(BoardManageVo vo) throws Exception;
	
	//대회신청 등록 (프로시저)
	public int pc_insertAppContest(BoardManageVo vo) throws Exception;
	
	//대회 그룹 중복신청 조회
	public int selectGroupContestAppCnt(BoardManageVo vo) throws Exception;
	
	//대회 신청정보 수정
	public int cpageUpdateMyContestAppInfo(BoardManageVo vo) throws Exception;
	
	//대회 신청내역 조회
	public List<BoardManageVo> selectClientContestAppList(BoardManageVo vo) throws Exception;
	
	//대회 접수정보 ROWNUM 체크
	public BoardManageVo selectNowAppDataGetRownum(BoardManageVo vo) throws Exception;
	
	//대회 신청정보 삭제
	public int deleteNowAppData(BoardManageVo vo) throws Exception;
	
	//쇼핑몰 이벤트 상태 플래그변경 
	public int updateShopEventManageInfo(BoardManageVo vo) throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 로드밸런싱용 
	 * **/
	public List<BoardManageVo> lb_selectMain(BoardManageVo vo) throws Exception;
	public List<BoardManageVo> lb_selectMainList(BoardManageVo vo) throws Exception;

	//공지사항 공지 리스트
	public List<BoardManageVo> lb_getTopBoardList(BoardManageVo vo) throws Exception;
	//게시판 리스트
	public List<BoardManageVo> lb_selectBoardList(BoardManageVo vo) throws Exception;

	//게시판 리스트 카운트
	public int lb_selectBoardListCnt(BoardManageVo vo) throws Exception;
	//staff 리스트
	public List<BoardManageVo> lb_selectStaffList(BoardManageVo vo) throws Exception;

	//staff 리스트 카운트
	public int lb_selectStaffListCnt(BoardManageVo vo) throws Exception;
	
	//대회신청 리스트
	public List<BoardManageVo> lb_selectContestList(BoardManageVo vo) throws Exception;
	
	//대회신청 리스트 카운트
	public int lb_selectContestListCnt(BoardManageVo vo) throws Exception;

	//대회 상세
	public BoardManageVo lb_getContestView(BoardManageVo vo) throws Exception;


	//대회 접수정보 ROWNUM 체크
	public BoardManageVo lb_selectNowAppDataGetRownum(BoardManageVo vo) throws Exception;

	//게시판 상세
	public BoardManageVo lb_getBoardView(BoardManageVo vo) throws Exception;
	
	//대회신청 중복체크
	public int lb_chkMberApp(BoardManageVo vo) throws Exception;
	
	//마이페이지 - 대회신청내역 리스트 (이벤트 대회 union 고객감사)
	public List<BoardManageVo> lb_selectMyContestList_union(BoardManageVo vo) throws Exception;
	
	//마이페이지 - 대회신청내역 리스트 카운트 (이벤트 대회 union 고객감사)
	public int lb_selectMyContestListCnt_union(BoardManageVo vo) throws Exception;
	
	//쇼핑몰 이벤트대회 정보조회 
	public BoardManageVo lb_getShopEventManageInfo(BoardManageVo vo) throws Exception;

}
