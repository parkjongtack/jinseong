package egovframework.apage.event.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import egovframework.apage.event.service.ApageEventManageVo;
import egovframework.common.GpAbstractDAO;


@Repository("ApageEventManageDAO")
public class ApageEventManageDAO extends GpAbstractDAO {
	
	private Logger logger	= Logger.getLogger(this.getClass());

	//이벤트 대회 신청 리스트
	public List<ApageEventManageVo> selectEventContestManageList(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectEventContestManageList",vo);
	}

	//이벤트 대회 신청 리스트 카운트
	public int selectEventContestManageListCnt(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("selectEventContestManageListCnt",vo);
	}

	//이벤트 대회 신청 정보 등록
	public int insertEventContestManageInfo(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().insert("insertEventContestManageInfo",vo);
	}

	//이벤트 대회 프리미엄 레슨 파트 정보 등록
	public int insertEventContestLeassonPartInfo(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		int result = 0;
		List<ApageEventManageVo> list = vo.getList();
		for(int i = 0; i < vo.getList().size(); i++){
			vo.getList().get(i).setCt_seq(vo.getCt_seq());
			vo.getList().get(i).setPart_ord(i+1);
			result += (Integer)getSqlMapClientTemplate().insert("insertEventContestLeassonPartInfo",vo.getList().get(i));
		}
		return result;
	}

	//이벤트 대회 프리미엄 레슨 파트 정보 리스트
	public List<ApageEventManageVo> selectEventContestLeassonPartList(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectEventContestLeassonPartList",vo);
	}

	//이벤트 대회 프리미엄 상세페이지
	public ApageEventManageVo selectEventContestManageDetail(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return (ApageEventManageVo)getSqlMapClientTemplate().queryForObject("selectEventContestManageDetail",vo);
	}

	//이벤트 대회 정보 수정
	public int updateEventContestManageInfo(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateEventContestManageInfo",vo);
	}
	
	//이벤트 대회 프리미엄 레슨 파트 정보 수정
	public int updateEventContestManagePartInfo(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		//Update Or Insert 전 Delete 처리
		if(vo != null && vo.getEcp_seq_arr() != null && vo.getEcp_seq_arr().length > 0){
			int delResult = getSqlMapClientTemplate().delete("deleteEventContestManagePartInfoArr",vo);
		}else{
			int delResult = getSqlMapClientTemplate().delete("deleteEventContestManagePartInfoAll",vo);
		}
		int result = 0;
		List<ApageEventManageVo> list = vo.getList();
		for(int i = 0; i < vo.getList().size(); i++){
			vo.getList().get(i).setCt_seq(vo.getCt_seq());
			if(vo.getList().get(i).getEcp_seq() > 0){
				if(getSqlMapClientTemplate().update("updateEventContestManagePartInfo",vo.getList().get(i)) > 0){
					result++;
				}
			}else{
				if((Integer)getSqlMapClientTemplate().insert("insertEventContestLeassonPartInfo",vo.getList().get(i)) > 0){
					result ++;
				}
				
			}
		}
		return result;
	}

	//이벤트 대회 정보 삭제
	public int deleteEventContestManageInfo(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("deleteEventContestManageInfo",vo);
	}

	//이벤트 대회 첨부파일 초기화
	public int updateEventContestFile(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateEventContestFile",vo);
	}

	//이벤트 대회 신청자 리스트JSON
	public List<ApageEventManageVo> selectAdminEventContestAppList(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectAdminEventContestAppList", vo);
	}

	//이벤트 대회 신청자 정보 수정
	public int updateAdminEventContestApp(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateAdminEventContestApp",vo);
	}

	//회원관리 > 이벤트 대회 신청 이력 상세
	public ApageEventManageVo apageGetEventContestInfoDetailOfEachMember(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return (ApageEventManageVo)getSqlMapClientTemplate().queryForObject("apageGetEventContestInfoDetailOfEachMember",vo);
	}

	//이벤트 대회 최우선 대기자 추출
	public ApageEventManageVo apageGetEventCtPartWaitingInfo(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return (ApageEventManageVo)getSqlMapClientTemplate().queryForObject("apageGetEventCtPartWaitingInfo",vo);
	}

	//이벤트 대회 대회 선정자 취소시 대기인원 선정 업데이트
	public int updateEventContestWaitingToSelect(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateEventContestWaitingToSelect",vo);
	}

	//이벤트 대회 대기자 이동시 대기번호 UPDATE
	public int apageUpdateEventContestAppWaitingNum(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateEventContestAppWaitingNum",vo);
	}

	//이벤트 대회 신청마감 후 랭키나열 후 상태 UPDATE
	public int apageEventContestAppResultOrderStatusChange(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageEventContestAppResultOrderStatusChange",vo);
	}

	//이벤트 대회 신청자 정보 삭제
	public int deleteAdminEventContestApp(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("deleteAdminEventContestApp",vo);
	}

	//이벤트 대회 선정결과 리스트
	public List<ApageEventManageVo> selectEventContestAppFinishList(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectEventContestAppFinishList", vo);
	}

	//이벤트 대회 선정결과 리스트 카운트
	public int selectEventContestAppFinishListCnt(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("selectEventContestAppFinishListCnt", vo);
	}

	//업데이트 완료 후 대회 ROWNUM 정렬 플래그 cut_yn ==> Y로 변경
	public int apageEventContestCutYnUpdate(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageEventContestCutYnUpdate",vo);
	}

	//이벤트 대회 선정결과 노출여부 업데이트
	public int updateEventContestAppResultExposeYn(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateEventContestAppResultExposeYn",vo);
	}

	//이벤트 대회 선정자 레인 랜덤배정 업데이트
	public int updateEventContestAppRandomLane(List<ApageEventManageVo> list) throws Exception{
		// TODO Auto-generated method stub
		int resultInt = 0;
		if(list.size() > 0){
			getSqlMapClientTemplate().getSqlMapClient().startBatch();
			for(int i = 0; i < list.size(); i++){
				resultInt += (Integer)getSqlMapClientTemplate().update("updateEventContestAppRandomLane", list.get(i));
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();  
		}
		return resultInt;
	}

	//이벤트 대회 선정자 랜덤 자리배치 리스트
	public List<ApageEventManageVo> createEventContestRandomLane(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("createEventContestRandomLane", vo);
	}

	//이벤트 대회 선정자 리스트 엑셀
	public List<ApageEventManageVo> selectEventContestSelectResultExcel(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectEventContestSelectResultExcel", vo);
	}

	//이벤트 대회 자리 배치 결과 노출여부 업데이트
	public int eventContestAppResultExposeYn(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("eventContestAppResultExposeYn",vo);
	}

	//이벤트 대회 자리 배치 결과 노출여부 업데이트
	public List<ApageEventManageVo> selectAdminEventContestRandomLaneResultList(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectAdminEventContestRandomLaneResultList", vo);
	}

	//대회 레인 랜덤배정 문자발송일 업데이트
	public int apageUpdateEventContestRandomLaneMsgSendDate(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateEventContestRandomLaneMsgSendDate",vo);
	}

	//대회 레인 랜덤배정 문자발송여부 N 처리, 발송시간 UPDATE
	public int apageUpdateEventContestRandomLaneSendFlagAndDate(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateEventContestRandomLaneSendFlagAndDate",vo);
	}

	//이벤트 대회 신청 결과(선정,대기)
	public List<ApageEventManageVo> selectAdminEventContestAppResultList(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectAdminEventContestAppResultList", vo);
	}

	//이벤트 대회 문자발송일 업데이트
	public int apageUpdateEventContestMsgSendDate(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateEventContestMsgSendDate",vo);
	}

	//대회신청 문자발송여부 N 처리, 발송시간 UPDATE
	public int apageUpdateEventContestAppSendFlagAndDate(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageUpdateEventContestAppSendFlagAndDate",vo);
	}

	//대회신청자 대기번호 재정렬
	public int apageEventContestAppWaitingNumReOrderring(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageEventContestAppWaitingNumReOrderring",vo);
	}

	//왕중왕전 참가자 등록
	public int insertEventContestAppTarget(ApageEventManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		List<ApageEventManageVo> list = vo.getList();
		int ct_seq = vo.getCt_seq();
		int resultInt = 0;
		getSqlMapClientTemplate().delete("deleteEventContestAppTarget", vo);
		if(ct_seq > 0 && list.size() > 0){
			getSqlMapClientTemplate().getSqlMapClient().startBatch();
			for(int i = 0; i < list.size(); i++){
				list.get(i).setCt_seq(ct_seq);
				resultInt += (Integer)getSqlMapClientTemplate().update("insertEventContestAppTarget", list.get(i));
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();  
		}
		return resultInt;
	}

	//왕중왕전 참가자 리스트
	public List<ApageEventManageVo> selectEventContestTopRankerList(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectEventContestTopRankerList", vo);
	}

	//이벤트대회 신청자명단 엑셀출력
	public List<ApageEventManageVo> selectAdminEventContestAppExcel(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectAdminEventContestAppExcel", vo);
	}

	//접수현황노출 업데이트
	public int apageEventCotnestAppSituationShowUpdate(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("apageEventCotnestAppSituationShowUpdate",vo);
	}

	//왕중왕전 대회 2차 접수 참가자 등록
	public int insertEventContestAppSecondTarget(ApageEventManageVo vo) throws Exception{
		// TODO Auto-generated method stub
		List<ApageEventManageVo> list = vo.getList();
		int ct_seq = vo.getCt_seq();
		String ct_group = vo.getCt_group();
		int resultInt = 0;
		
		int flag = 0;
		int duplicateCnt = 0;
		if(ct_seq > 0 && list.size() > 0){
	//		getSqlMapClientTemplate().getSqlMapClient().startBatch();
			for(int i = 0; i < list.size(); i++){
				list.get(i).setCt_seq(ct_seq);
				list.get(i).setReg_turn(2);
				list.get(i).setStatus("0004");;
				list.get(i).setCt_type(vo.getCt_type());
				list.get(i).setCt_group(ct_group);
				
				int aa = 0;
				try {
					getSqlMapClientTemplate().queryForObject("insertEventContestAppSecondTarget", list.get(i));
					aa ++;
				} catch (DuplicateKeyException e) {
					// TODO: handle exception
					flag = 1;
					duplicateCnt ++;
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					flag = 2;
					e.printStackTrace();
				}
				if(aa > 0){
					resultInt ++;
				}
			}
		//	getSqlMapClientTemplate().getSqlMapClient().executeBatch();  
		}
		
		
		if((duplicateCnt + resultInt) == list.size()){
			if(duplicateCnt > 0){
				resultInt = 9999999;
			}else{
				resultInt = 1;
			}
		}else{
			resultInt = 0;
		}
		return resultInt;
	}

	//준비 및 진행 이벤트 대회 신청 리스트
	public List<ApageEventManageVo> apageSelectExpectEventContestList(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return  list("apageSelectExpectEventContestList",vo);
	}

	//접수제한 리스트
	public List<ApageEventManageVo> apageSelectExpectEventContestGroupList(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return  list("apageSelectExpectEventContestGroupList",vo);
	}

	//이벤트 대회 대기자 리스트 엑셀
	public List<ApageEventManageVo> selectEventContestSelectResultExcelStatusReady(ApageEventManageVo vo) {
		// TODO Auto-generated method stub
		return  list("selectEventContestSelectResultExcelStatusReady",vo);
	}
	
	

}
