package egovframework.apage.board.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.common.GpAbstractDAO;

@Repository("apageBoardManageDAO")
public class apageBoardManageDAO extends GpAbstractDAO {
	
	private Logger logger	= Logger.getLogger(this.getClass());

	//게시판 리스트
	public List<apageBoardManageVo> selectadminBoardList(apageBoardManageVo vo) {
		List<apageBoardManageVo> list	= list("selectadminBoardList", vo);
		return list;
	}

	//게시판 리스트 카운트
	public int selectadminBoardListCnt(apageBoardManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectadminBoardListCnt", vo);		
		return list;
	}

	//게시판 등록
	public int insertAdminBoard(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertAdminBoard", vo);
	}

	//게시판 상세
	public apageBoardManageVo getAdminBoardView(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (apageBoardManageVo)selectByPk("getAdminBoardView", vo);
	}

	//조회수 증가
	public int UpdateCnt(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("UpdateCnt", vo);
	}

	//게시판 삭제
	public int adminBoardDelete(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("adminBoardDelete", vo);
	}

	//게시판 수정
	public int updateAdminBoard(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateAdminBoard", vo);
	}

	//파일 삭제시 banner테이블의 첨부파일 seq값 제거
	public int setBoardAttachUpdt(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("setBoardAttachUpdt", vo);
	}

	//파일 삭제시 board테이블의 첨부파일 seq값 제거
	public int setBoardAttachUpdt2(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("setBoardAttachUpdt2", vo);
	}
		
	//게시판 답글 리스트
	public List<apageBoardManageVo> selectBoardComment(apageBoardManageVo vo) {
		List<apageBoardManageVo> list	= list("selectBoardComment", vo);
		return list;
	}
	//게시판 답글 등록
	public int insertBoardComment(apageBoardManageVo vo) {		
		if (getSqlMapClientTemplate().insert("insertBoardComment", vo) == null) {
			return 1;
		}else{
			return 0;
		}
	}
	//게시판 답글 삭제
	public int deleteBoardComment(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("deleteBoardComment", vo);
	}

	//게시판 답글 수정
	public int updateBoardComment(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateBoardComment", vo);
	}	
	
	//게시판 답글 전체삭제
	public int deleteComment(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("deleteComment", vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//staff 리스트
	public List<apageBoardManageVo> selectadminStaffList(apageBoardManageVo vo) {
		List<apageBoardManageVo> list	= list("selectadminStaffList", vo);
		return list;
	}

	//staff 리스트 카운트
	public int selectadminStaffListCnt(apageBoardManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectadminStaffListCnt", vo);		
		return list;
	}
	
	//staff 등록
	public int insertAdminStaff(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertAdminStaff", vo);
	}
	
	//staff 수정
	public int updateAdminStaff(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateAdminStaff", vo);
	}
	
	//staff 삭제
	public int deleteAdminStaff(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().delete("deleteAdminStaff", vo);
	}
	
	//staff 상세
	public apageBoardManageVo getAdminStaffView(apageBoardManageVo vo) {
		return (apageBoardManageVo)selectByPk("getAdminStaffView", vo);
	}

	//staff 조회수 증가
	public int UpdateStaffCnt(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("UpdateStaffCnt", vo);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//대회 리스트
	public List<apageBoardManageVo> selectAdminContestList(apageBoardManageVo vo) {
		List<apageBoardManageVo> list	= list("selectAdminContestList", vo);
		return list;
	}
	
	//대회 리스트 no limit
	public List<apageBoardManageVo> selectAdminContestListNoLimit() {
		List<apageBoardManageVo> list	= list("selectAdminContestListNoLimit");
		return list;
	}

	//대회 리스트 카운트
	public int selectAdminContestListCnt(apageBoardManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectAdminContestListCnt", vo);		
		return list;
	}
	
	//대회 등록
	public int insertAdminContest(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertAdminContest", vo);
	}
	
	//대회 수정
	public int updateAdminContest(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateAdminContest", vo);
	}
	
	//대회 삭제
	public int deleteAdminContest(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().delete("deleteAdminContest", vo);
	}
	
	//대회 상세
	public apageBoardManageVo getAdminContestView(apageBoardManageVo vo) {
		return (apageBoardManageVo)selectByPk("getAdminContestView", vo);
	}

	//대회 첨부파일 초기화
	public int updateAdminContestFile(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateAdminContestFile", vo);
	}
	
	//대회신청자(선정 및 입금완료 상태) 이름 가져오기
	public String selectAllContestAppMemberName(apageBoardManageVo vo) {
		String list = (String)getSqlMapClientTemplate().queryForObject("selectAllContestAppMemberName", vo);		
		return list;
	}
	
	//대회신청자(선정 및 입금완료 상태) 아이디 가져오기
	public String selectAllContestAppMemberID(apageBoardManageVo vo) {
		String list = (String)getSqlMapClientTemplate().queryForObject("selectAllContestAppMemberID", vo);		
		return list;
	}
	
	//대회신청자(선정 및 입금완료 상태) 전화번호 가져오기
	public String selectAllContestAppMemberPhone(apageBoardManageVo vo) {
		String list = (String)getSqlMapClientTemplate().queryForObject("selectAllContestAppMemberPhone", vo);		
		return list;
	}
	
	//대회신청자 리스트
	public List<apageBoardManageVo> selectAdminContestAppList(apageBoardManageVo vo) {
		List<apageBoardManageVo> list	= list("selectAdminContestAppList", vo);
		return list;
	}

	//대회신청자 리스트 카운트
	public int selectAdminContestAppListCnt(apageBoardManageVo vo) {
		int list = (Integer)getSqlMapClientTemplate().queryForObject("selectAdminContestAppListCnt", vo);		
		return list;
	}
	
	//대회신청자 수정
	public int updateAdminContestApp(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateAdminContestApp", vo);
	}
	
	//대회신청자 삭제
	public int deleteAdminContestApp(apageBoardManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("deleteAdminContestApp", vo);
	}
	
	//대회신청자 리스트 엑셀출력
	public List<apageBoardManageVo> selectAdminContestAppExcel(apageBoardManageVo vo) {
		List<apageBoardManageVo> list	= list("selectAdminContestAppExcel", vo);
		return list;
	}
	
	//대회신청자 자리배치
	public List<apageBoardManageVo> createRandomLane(apageBoardManageVo vo) {
		List<apageBoardManageVo> list	= list("createRandomLane", vo);
		return list;
	}

	//대회 신청 마감 리스트
	public List<apageBoardManageVo> selectAdminContestAppFinishList(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("selectAdminContestAppFinishList", vo);
	}

	//대회 신청 마감 리스트 카운트
	public int selectAdminContestAppFinishListCnt(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("selectAdminContestAppFinishListCnt", vo);
	}
	
	//선정결과 사용자 페이지 노출여부 update
	public int updateContestAppResultExposeYn(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("updateContestAppResultExposeYn", vo);
	}

	//대회 선정자 리스트 엑셀
	public List<apageBoardManageVo> selectAdminContestSelectResultExcel(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("selectAdminContestSelectResultExcel", vo);
	}

	//대회 선정자 레인 랜덤배정 업데이트
	public int updateAppRandomLane(List<apageBoardManageVo> list) throws Exception{
		// TODO Auto-generated method stub
		int resultInt = 0;
		if(list.size() > 0){
			getSqlMapClientTemplate().getSqlMapClient().startBatch();
			for(int i = 0; i < list.size(); i++){
				resultInt += (Integer)getSqlMapClientTemplate().update("updateAppRandomLane", list.get(i));
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();  
		}
		return resultInt;
	}

	//대회 선정자 취소시 대기인원 선정 업데이트
	public int updateWaitingToSelect(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateWaitingToSelect", vo);
	}

	//대회 예정,진행 리스트
	public List<apageBoardManageVo> apageSelectExpectContestList(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("apageSelectExpectContestList", vo);
	}

	//대회 그룹 리스트
	public List<apageBoardManageVo> apageSelectExpectContestGroupList(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("apageSelectExpectContestGroupList", vo);
	}

	//자동배치결과 삭제
	public int deleteAdminBoardLaneResult(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("deleteAdminBoardLaneResult", vo);
	}

	//대회 신청 결과(선정,대기)
	public List<apageBoardManageVo> selectAdminContestAppResultList(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("selectAdminContestAppResultList", vo);
	}

	//대회 문자발송일 업데이트
	public int apageUpdateContestMsgSendDate(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateContestMsgSendDate", vo);
	}

	//대회신청 문자발송여부 Y 처리
	public int apageUpdateAppSmsFlag(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateAppSmsFlag", vo);
	}

	//대회신청 문자발송여부 N 처리, 발송시간 UPDATE
	public int apageUpdateContestAppSendFlagAndDate(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateContestAppSendFlagAndDate", vo);
	}

	//대기자 이동시 대기번호 UPDATE
	public int apageUpdateContestAppWaitingNum(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateContestAppWaitingNum", vo);
	}

	//대회신청결과 PART별 조회
	public List<apageBoardManageVo> selectAdminContestAppResultGubunPartList(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("selectAdminContestAppResultGubunPartList", vo);
	}

	//신청마감 후 랭키나열 후 상태 UPDATE
	public int apageContestAppResultOrderStatusChange(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageContestAppResultOrderStatusChange", vo);
	}

	//업데이트 완료 후 대회 ROWNUM 정렬 플래그 cut_yn ==> Y로 변경
	public int apageContestCutYnUpdate(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageContestCutYnUpdate", vo);
	}

	//대회 레인 랜덤배정 문자발송일 업데이트
	public int apageUpdateContestRandomLaneMsgSendDate(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateContestRandomLaneMsgSendDate", vo);
	}

	//대회 레인 랜덤배정 문자발송여부 N 처리, 발송시간 UPDATE
	public int apageUpdateContestRandomLaneSendFlagAndDate(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateContestRandomLaneSendFlagAndDate", vo);
	}

	//대회 레인 랜덤배정 문자발송여부 Y,N
	public int apageUpdateAppRandomLaneSmsFlag(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateAppRandomLaneSmsFlag", vo);
	}

	//대회 레인 랜덤배정 문자발송 리스트
	public List<apageBoardManageVo> selectAdminContestAppLaneSmsSendResultList(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("selectAdminContestAppLaneSmsSendResultList", vo);
	}

	//대회 레인 랜덤배정 결과(선정,대기)
	public List<apageBoardManageVo> selectAdminContestRandomLaneResultList(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("selectAdminContestRandomLaneResultList", vo);
	}
	

	//레인 배치 결과 노출
	public int contestAppResultExposeYn(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("contestAppResultExposeYn", vo);
	}

	//각 대회 환불 리스트 
	public List<apageBoardManageVo> apageContestRefundList(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("apageContestRefundList", vo);
	}

	//최우선 대기자 추출 
	public apageBoardManageVo apageGetCtPartWaitingInfo(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (apageBoardManageVo)getSqlMapClientTemplate().queryForObject("apageGetCtPartWaitingInfo", vo);
	}

	//접수현황 노출 변경 
	public int apageAppSituationShowUpdate(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageAppSituationShowUpdate", vo);
	}
	
	//회원관리 > 대회신청이력
	public List<apageBoardManageVo> apageGetContestInfoOfEachMember(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return  list("apageGetContestInfoOfEachMember", vo);
	}
	
	//회원관리 > 대회신청이력 상세
	public apageBoardManageVo apageGetContestInfoDetailOfEachMember(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return (apageBoardManageVo)getSqlMapClientTemplate().queryForObject("apageGetContestInfoDetailOfEachMember", vo);
	}

	//쇼핑몰 이벤트대회 접수일자 정보 변경 
	public int apageUpdateShopEventManageInfo(apageBoardManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateShopEventManageInfo", vo);
	}
	
	
	
}
