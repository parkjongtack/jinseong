package egovframework.apage.board.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.board.service.apageBoardManageService;
import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.apage.lb.service.impl.LbApageBoardManageDAO;

@Repository("apageBoardManageService")
public class apageBoardManageServiceImpl implements apageBoardManageService {

	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="apageBoardManageDAO")
	apageBoardManageDAO apageBoardManageDAO; 
	
	@Resource(name="LbApageBoardManageDAO")
	LbApageBoardManageDAO LbApageBoardManageDAO;

	//게시판 리스트
	@Override
	public List<apageBoardManageVo> selectadminBoardList(apageBoardManageVo vo) throws Exception {
		List<apageBoardManageVo> list = apageBoardManageDAO.selectadminBoardList(vo);
		return list;
	}

	//게시판 리스트 카운트
	@Override
	public int selectadminBoardListCnt(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectadminBoardListCnt(vo);
	}

	//게시판 등록
	@Override
	public int insertAdminBoard(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= apageBoardManageDAO.insertAdminBoard(vo);
		return list;
	}

	//게시판 상세
	@Override
	public apageBoardManageVo getAdminBoardView(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.getAdminBoardView(vo);
	}

	//조회수 증가
	@Override
	public int UpdateCnt(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.UpdateCnt(vo);
	}

	//게시판 삭제
	@Override
	public int adminBoardDelete(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.adminBoardDelete(vo);
	}

	//게시판 수정
	@Override
	public int updateAdminBoard(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.updateAdminBoard(vo);
	}

	//파일 삭제시 banner테이블의 첨부파일 seq값 제거
	@Override
	public int setBoardAttachUpdt(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.setBoardAttachUpdt(vo);
	}
	
	//파일 삭제시 board테이블의 첨부파일 seq값 제거
	@Override
	public int setBoardAttachUpdt2(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.setBoardAttachUpdt2(vo);
	}
	
	//게시판 댓글 리스트
	@Override
	public List<apageBoardManageVo> selectBoardComment(apageBoardManageVo vo) throws Exception {
		List<apageBoardManageVo> list = apageBoardManageDAO.selectBoardComment(vo);
		return list;
	}
	//게시판 댓글 등록
	@Override
	public int insertBoardComment(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= apageBoardManageDAO.insertBoardComment(vo);
		return list;
	}
	//게시판 댓글 삭제
	@Override
	public int deleteBoardComment(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.deleteBoardComment(vo);
	}

	//게시판 댓글 수정
	@Override
	public int updateBoardComment(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.updateBoardComment(vo);
	}
	
	//게시판 댓글 삭제
	@Override
	public int deleteComment(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.deleteComment(vo);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////

	//staff 리스트
	@Override
	public List<apageBoardManageVo> selectadminStaffList(apageBoardManageVo vo) throws Exception {
		List<apageBoardManageVo> list = apageBoardManageDAO.selectadminStaffList(vo);
		return list;
	}

	//staff 리스트 카운트
	@Override
	public int selectadminStaffListCnt(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectadminStaffListCnt(vo);
	}
	//staff 등록
	@Override
	public int insertAdminStaff(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= apageBoardManageDAO.insertAdminStaff(vo);
		return list;
	}	
	
	//staff 수정
	@Override
	public int updateAdminStaff(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.updateAdminStaff(vo);
	}
	
	//staff 삭제
	@Override
	public int deleteAdminStaff(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.deleteAdminStaff(vo);
	}
	
	//staff 상세
	@Override
	public apageBoardManageVo getAdminStaffView(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.getAdminStaffView(vo);
	}

	//staff 조회수 증가
	@Override
	public int UpdateStaffCnt(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.UpdateStaffCnt(vo);
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//대회 리스트
	@Override
	public List<apageBoardManageVo> selectAdminContestList(apageBoardManageVo vo) throws Exception {
		List<apageBoardManageVo> list = apageBoardManageDAO.selectAdminContestList(vo);
		return list;
	}	
	//대회 리스트 no limit
	@Override
	public List<apageBoardManageVo> selectAdminContestListNoLimit() throws Exception {
		List<apageBoardManageVo> list = apageBoardManageDAO.selectAdminContestListNoLimit();
		return list;
	}
	//대회 리스트 카운트
	@Override
	public int selectAdminContestListCnt(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAdminContestListCnt(vo);
	}
	//대회 등록
	@Override
	public int insertAdminContest(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= apageBoardManageDAO.insertAdminContest(vo);
		return list;
	}	
	
	//대회 수정
	@Override
	public int updateAdminContest(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.updateAdminContest(vo);
	}
	
	//대회 삭제
	@Override
	public int deleteAdminContest(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.deleteAdminContest(vo);
	}
	
	//대회 상세
	@Override
	public apageBoardManageVo getAdminContestView(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.getAdminContestView(vo);
	}

	//대회 첨부파일 초기화
	@Override
	public int updateAdminContestFile(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.updateAdminContestFile(vo);
	}
	
	//대회신청자(선정 및 입금완료 상태) 이름 가져오기
	@Override
	public String selectAllContestAppMemberName(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAllContestAppMemberName(vo);
	}
	
	//대회신청자(선정 및 입금완료 상태) 아이디 가져오기
	@Override
	public String selectAllContestAppMemberID(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAllContestAppMemberID(vo);
	}
	
	//대회신청자(선정 및 입금완료 상태) 전화번호 가져오기
	@Override
	public String selectAllContestAppMemberPhone(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAllContestAppMemberPhone(vo);
	}
	
	//대회신청자 리스트
	@Override
	public List<apageBoardManageVo> selectAdminContestAppList(apageBoardManageVo vo) throws Exception {
		List<apageBoardManageVo> list = apageBoardManageDAO.selectAdminContestAppList(vo);
		return list;
	}	
	
	//대회신청자 리스트 카운트
	@Override
	public int selectAdminContestAppListCnt(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAdminContestAppListCnt(vo);
	}
	
	//대회신청자 수정
	@Override
	public int updateAdminContestApp(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.updateAdminContestApp(vo);
	}
	
	//대회신청자 삭제
	@Override
	public int deleteAdminContestApp(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.deleteAdminContestApp(vo);
	}	
	
	//대회신청자 리스트 엑셀출력
	@Override
	public List<apageBoardManageVo> selectAdminContestAppExcel(apageBoardManageVo vo) throws Exception {
		List<apageBoardManageVo> list = apageBoardManageDAO.selectAdminContestAppExcel(vo);
		return list;
	}	
	
	//대회신청자 자리배치
	@Override
	public List<apageBoardManageVo> createRandomLane(apageBoardManageVo vo) throws Exception {
		List<apageBoardManageVo> list = apageBoardManageDAO.createRandomLane(vo);
		return list;
	}

	//대회 신청 마감 리스트
	@Override
	public List<apageBoardManageVo> selectAdminContestAppFinishList(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAdminContestAppFinishList(vo);
	}

	//대회 신청 마감 리스트 카운트
	@Override
	public int selectAdminContestAppFinishListCnt(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAdminContestAppFinishListCnt(vo);
	}

	//선정결과 사용자 페이지 노출여부 update
	@Override
	public int updateContestAppResultExposeYn(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.updateContestAppResultExposeYn(vo);
	}
	
	//대회 선정자 리스트 엑셀
	@Override
	public List<apageBoardManageVo> selectAdminContestSelectResultExcel(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAdminContestSelectResultExcel(vo);
	}
	
	//대회 선정자 레인 랜덤배정 업데이트
	@Override
	public int updateAppRandomLane(List<apageBoardManageVo> vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.updateAppRandomLane(vo);
	}

	//대회 선정자 취소시 대기인원 선정 업데이트
	@Override
	public int updateWaitingToSelect(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.updateWaitingToSelect(vo);
	}

	//대회 예정,진행 리스트
	@Override
	public List<apageBoardManageVo> apageSelectExpectContestList(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageSelectExpectContestList(vo);
	}

	//대회 그룹 리스트
	@Override
	public List<apageBoardManageVo> apageSelectExpectContestGroupList(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageSelectExpectContestGroupList(vo);
	}

	//자동배치결과 삭제
	@Override
	public int deleteAdminBoardLaneResult(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.deleteAdminBoardLaneResult(vo);
	}

	//대회 신청 결과(선정,대기)
	@Override
	public List<apageBoardManageVo> selectAdminContestAppResultList(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAdminContestAppResultList(vo);
	}

	//대회 문자발송일 업데이트
	@Override
	public int apageUpdateContestMsgSendDate(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageUpdateContestMsgSendDate(vo);
	}

	//대회신청 문자발송여부 Y 처리
	@Override
	public int apageUpdateAppSmsFlag(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageUpdateAppSmsFlag(vo);
	}

	//대회신청 문자발송여부 N 처리, 발송시간 UPDATE
	@Override
	public int apageUpdateContestAppSendFlagAndDate(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageUpdateContestAppSendFlagAndDate(vo);
	}

	//대기자 이동시 대기번호 UPDATE
	@Override
	public int apageUpdateContestAppWaitingNum(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageUpdateContestAppWaitingNum(vo);
	}

	//대회신청결과 PART별 조회
	@Override
	public List<apageBoardManageVo> selectAdminContestAppResultGubunPartList(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAdminContestAppResultGubunPartList(vo);
	}

	//신청마감 후 랭키나열 후 상태 UPDATE
	@Override
	public int apageContestAppResultOrderStatusChange(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageContestAppResultOrderStatusChange(vo);
	}

	//업데이트 완료 후 대회 ROWNUM 정렬 플래그 cut_yn ==> Y로 변경
	@Override
	public int apageContestCutYnUpdate(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageContestCutYnUpdate(vo);
	}

	//대회 레인 랜덤배정 문자발송일 업데이트
	@Override
	public int apageUpdateContestRandomLaneMsgSendDate(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageUpdateContestRandomLaneMsgSendDate(vo);
	}

	//대회 레인 랜덤배정 문자발송여부 N 처리, 발송시간 UPDATE
	@Override
	public int apageUpdateContestRandomLaneSendFlagAndDate(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageUpdateContestRandomLaneSendFlagAndDate(vo);
	}
	
	//대회 레인 랜덤배정 문자발송여부 Y,N
	@Override
	public int apageUpdateAppRandomLaneSmsFlag(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageUpdateAppRandomLaneSmsFlag(vo);
	}

	//대회 레인 랜덤배정 문자발송 리스트
	@Override
	public List<apageBoardManageVo> selectAdminContestAppLaneSmsSendResultList(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAdminContestAppLaneSmsSendResultList(vo);
	}

	//대회 레인 랜덤배정 결과(선정,대기)
	@Override
	public List<apageBoardManageVo> selectAdminContestRandomLaneResultList(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.selectAdminContestRandomLaneResultList(vo);
	}	
	
	//레인 배치 결과 노출
	@Override
	public int contestAppResultExposeYn(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.contestAppResultExposeYn(vo);
	}

	//각 대회 환불 리스트 
	@Override
	public List<apageBoardManageVo> apageContestRefundList(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageContestRefundList(vo);
	}

	//최우선 대기자 추출 
	@Override
	public apageBoardManageVo apageGetCtPartWaitingInfo(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageGetCtPartWaitingInfo(vo);
	}

	//접수현황 노출 변경 
	@Override
	public int apageAppSituationShowUpdate(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageAppSituationShowUpdate(vo);
	}
	
	//회원관리 > 대회신청이력
	@Override
	public List<apageBoardManageVo> apageGetContestInfoOfEachMember(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageGetContestInfoOfEachMember(vo);
	}
	
	//회원관리 > 대회신청이력 상세
	@Override
	public apageBoardManageVo apageGetContestInfoDetailOfEachMember(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageGetContestInfoDetailOfEachMember(vo);
	}
	//쇼핑몰 이벤트대회 접수일자 정보 변경 
	@Override
	public int apageUpdateShopEventManageInfo(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageBoardManageDAO.apageUpdateShopEventManageInfo(vo);
	}
	
	
	
	/**
	 * 로드밸런싱 DB
	 * */
	//대회 리스트
	@Override
	public List<apageBoardManageVo> lb_selectAdminContestList(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbApageBoardManageDAO.lb_selectAdminContestList(vo);
	}

	//대회 리스트 카운트
	@Override
	public int lb_selectAdminContestListCnt(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbApageBoardManageDAO.lb_selectAdminContestListCnt(vo);
	}

	//대회 상세
	@Override
	public apageBoardManageVo lb_getAdminContestView(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbApageBoardManageDAO.lb_getAdminContestView(vo);
	}

	
	//쇼핑몰 이벤트 접수일자 정보
	@Override
	public apageBoardManageVo lb_selectAdminShopEventManageInfo(apageBoardManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbApageBoardManageDAO.lb_selectAdminShopEventManageInfo(vo);
	}


}
