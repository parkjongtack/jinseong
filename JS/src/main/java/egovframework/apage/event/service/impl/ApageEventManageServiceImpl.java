package egovframework.apage.event.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.event.service.ApageEventManageService;
import egovframework.apage.event.service.ApageEventManageVo;

@Repository("ApageEventManageService")
public class ApageEventManageServiceImpl implements ApageEventManageService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "ApageEventManageDAO")
	ApageEventManageDAO ApageEventManageDAO;

	//이벤트 대회 신청 리스트
	@Override
	public List<ApageEventManageVo> selectEventContestManageList(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectEventContestManageList(vo);
	}

	//이벤트 대회 신청 리스트 카운트
	@Override
	public int selectEventContestManageListCnt(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectEventContestManageListCnt(vo);
	}

	//이벤트 대회 신청 정보 등록
	@Override
	public int insertEventContestManageInfo(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.insertEventContestManageInfo(vo);
	}
	
	//이벤트 대회 프리미엄 레슨 파트 정보 등록
	@Override
	public int insertEventContestLeassonPartInfo(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.insertEventContestLeassonPartInfo(vo);
	}

	//이벤트 대회 프리미엄 레슨 파트 정보 리스트
	@Override
	public List<ApageEventManageVo> selectEventContestLeassonPartList(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectEventContestLeassonPartList(vo);
	}

	//이벤트 대회 프리미엄 상세페이지
	@Override
	public ApageEventManageVo selectEventContestManageDetail(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectEventContestManageDetail(vo);
	}

	//이벤트 대회 정보 수정
	@Override
	public int updateEventContestManageInfo(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.updateEventContestManageInfo(vo);
	}

	//이벤트 대회 프리미엄 레슨 파트 정보 수정
	@Override
	public int updateEventContestManagePartInfo(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.updateEventContestManagePartInfo(vo);
	}

	//이벤트 대회 정보 삭제
	@Override
	public int deleteEventContestManageInfo(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.deleteEventContestManageInfo(vo);
	}

	//이벤트 대회 첨부파일 초기화
	@Override
	public int updateEventContestFile(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.updateEventContestFile(vo);
	}

	//이벤트 대회 신청자 리스트JSON
	@Override
	public List<ApageEventManageVo> selectAdminEventContestAppList(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectAdminEventContestAppList(vo);
	}

	//이벤트 대회 신청자 정보 수정
	@Override
	public int updateAdminEventContestApp(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.updateAdminEventContestApp(vo);
	}

	//회원관리 > 이벤트 대회 신청 이력 상세
	@Override
	public ApageEventManageVo apageGetEventContestInfoDetailOfEachMember(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageGetEventContestInfoDetailOfEachMember(vo);
	}

	//이벤트 대회 최우선 대기자 추출
	@Override
	public ApageEventManageVo apageGetEventCtPartWaitingInfo(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageGetEventCtPartWaitingInfo(vo);
	}

	//이벤트 대회 대회 선정자 취소시 대기인원 선정 업데이트
	@Override
	public int updateEventContestWaitingToSelect(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.updateEventContestWaitingToSelect(vo);
	}

	//이벤트 대회 대기자 이동시 대기번호 UPDATE
	@Override
	public int apageUpdateEventContestAppWaitingNum(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageUpdateEventContestAppWaitingNum(vo);
	}

	//이벤트 대회 신청마감 후 랭키나열 후 상태 UPDATE
	@Override
	public int apageEventContestAppResultOrderStatusChange(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageEventContestAppResultOrderStatusChange(vo);
	}
	
	//이벤트 대회 신청자 정보 삭제
	@Override
	public int deleteAdminEventContestApp(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.deleteAdminEventContestApp(vo);
	}

	//이벤트 대회 선정결과 리스트
	@Override
	public List<ApageEventManageVo> selectEventContestAppFinishList(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectEventContestAppFinishList(vo);
	}

	//이벤트 대회 선정결과 리스트 카운트
	@Override
	public int selectEventContestAppFinishListCnt(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectEventContestAppFinishListCnt(vo);
	}

	//업데이트 완료 후 대회 ROWNUM 정렬 플래그 cut_yn ==> Y로 변경
	@Override
	public int apageEventContestCutYnUpdate(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageEventContestCutYnUpdate(vo);
	}

	//이벤트 대회 선정결과 노출여부 업데이트
	@Override
	public int updateEventContestAppResultExposeYn(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.updateEventContestAppResultExposeYn(vo);
	}

	//이벤트 대회 선정자 레인 랜덤배정 업데이트
	@Override
	public int updateEventContestAppRandomLane(List<ApageEventManageVo> list) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.updateEventContestAppRandomLane(list);
	}

	//이벤트 대회 선정자 랜덤 자리배치 리스트
	@Override
	public List<ApageEventManageVo> createEventContestRandomLane(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.createEventContestRandomLane(vo);
	}

	//이벤트 대회 선정자 리스트 엑셀
	@Override
	public List<ApageEventManageVo> selectEventContestSelectResultExcel(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectEventContestSelectResultExcel(vo);
	}

	//이벤트 대회 자리 배치 결과 노출여부 업데이트
	@Override
	public int eventContestAppResultExposeYn(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.eventContestAppResultExposeYn(vo);
	}

	//이벤트 대회 자리 배치 결과 노출여부 업데이트
	@Override
	public List<ApageEventManageVo> selectAdminEventContestRandomLaneResultList(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectAdminEventContestRandomLaneResultList(vo);
	}

	//대회 레인 랜덤배정 문자발송일 업데이트
	@Override
	public int apageUpdateEventContestRandomLaneMsgSendDate(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageUpdateEventContestRandomLaneMsgSendDate(vo);
	}

	//대회 레인 랜덤배정 문자발송여부 N 처리, 발송시간 UPDATE
	@Override
	public int apageUpdateEventContestRandomLaneSendFlagAndDate(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageUpdateEventContestRandomLaneSendFlagAndDate(vo);
	}

	//이벤트 대회 신청 결과(선정,대기)
	@Override
	public List<ApageEventManageVo> selectAdminEventContestAppResultList(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectAdminEventContestAppResultList(vo);
	}

	//이벤트 대회 문자발송일 업데이트
	@Override
	public int apageUpdateEventContestMsgSendDate(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageUpdateEventContestMsgSendDate(vo);
	}

	//대회신청 문자발송여부 N 처리, 발송시간 UPDATE
	@Override
	public int apageUpdateEventContestAppSendFlagAndDate(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageUpdateEventContestAppSendFlagAndDate(vo);
	}

	//대회신청자 대기번호 재정렬
	@Override
	public int apageEventContestAppWaitingNumReOrderring(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageEventContestAppWaitingNumReOrderring(vo);
	}

	//왕중왕전 참가자 등록
	@Override
	public int insertEventContestAppTarget(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.insertEventContestAppTarget(vo);
	}

	//왕중왕전 참가자 리스트
	@Override
	public List<ApageEventManageVo> selectEventContestTopRankerList(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectEventContestTopRankerList(vo);
	}

	//이벤트대회 신청자명단 엑셀출력
	@Override
	public List<ApageEventManageVo> selectAdminEventContestAppExcel(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectAdminEventContestAppExcel(vo);
	}

	//접수현황노출 업데이트
	@Override
	public int apageEventCotnestAppSituationShowUpdate(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageEventCotnestAppSituationShowUpdate(vo);
	}

	//왕중왕전 대회 2차 접수 참가자 등록
	@Override
	public int insertEventContestAppSecondTarget(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.insertEventContestAppSecondTarget(vo);
	}
	
	//준비 및 진행 이벤트 대회 신청 리스트
	@Override
	public List<ApageEventManageVo> apageSelectExpectEventContestList(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageSelectExpectEventContestList(vo);
	}

	//접수제한 리스트
	@Override
	public List<ApageEventManageVo> apageSelectExpectEventContestGroupList(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.apageSelectExpectEventContestGroupList(vo);
	}

	//이벤트 대회 대기자 리스트 엑셀
	@Override
	public List<ApageEventManageVo> selectEventContestSelectResultExcelStatusReady(ApageEventManageVo vo)
			throws Exception {
		// TODO Auto-generated method stub
		return ApageEventManageDAO.selectEventContestSelectResultExcelStatusReady(vo);
	}
	
	
	
	
}
