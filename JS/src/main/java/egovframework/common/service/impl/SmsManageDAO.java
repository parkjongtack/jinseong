package egovframework.common.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.common.GpAbstractDAO_SMS;
import egovframework.common.service.SmsManageVo;


@Repository("SmsManageDAO")	
public class SmsManageDAO extends GpAbstractDAO_SMS{

	private Logger logger	= Logger.getLogger(this.getClass());
	
	//발송 SMS 리스트
	public List<SmsManageVo> selectSmsList(SmsManageVo vo) {
		List<SmsManageVo> list	= list("selectSmsList", vo);
		return list;
	}
	
	//발송 SMS 리스트 카운트
	public int selectSmsListCnt(SmsManageVo vo) {
		
		return (Integer)getSqlMapClientTemplate().queryForObject("selectSmsListCnt", vo);
	}
	
	//SMS 발송
	public int insertSmsList(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().insert("insertSmsList",vo);
	}

	//MMS 발송
	public int insertMmsList(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().insert("insertMmsList",vo);
	}

	//MMS INSERT BATCH
	public int insertMmsBatch(List<SmsManageVo> list) throws Exception {
		// TODO Auto-generated method stub
		int resultInt = 0;
		if(list.size() > 0){
			getSqlMapClientTemplate().getSqlMapClient().startBatch();
			for(int i = 0; i < list.size(); i++){
				resultInt += (Integer)getSqlMapClientTemplate().getSqlMapClient().insert("insertMmsList", list.get(i));
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();  
		}
		return resultInt;
	}

	//MMS LOG LIST
	public List<SmsManageVo> selectSmsLogList(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectSmsLogList",vo);
	}
	
	//MMS LOG LIST - SMS관리에서 발송
	public List<SmsManageVo> selectSmsLogListAboutGeneralAndExcel(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectSmsLogListAboutGeneralAndExcel",vo);
	}

	//MMS LOG Table 리스트
	public List<SmsManageVo> getMmsLogTableList(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return list("getMmsLogTableList",vo);
	}

	//MMS LOG Table 발송내역 UNION 리스트
	public List<SmsManageVo> getMmsLogTableUnionList(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return list("getMmsLogTableUnionList",vo);
	}
	
	//MMS 재전송 정보 조회 
	public SmsManageVo getSelectResendMmsInfo(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return (SmsManageVo)getSqlMapClientTemplate().queryForObject("getSelectResendMmsInfo", vo);
	}
	
	//MMS 재전송 처리 후 FLAG 변경
	public int setUpdateFailMsgResendInfo(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return (int)getSqlMapClientTemplate().update("setUpdateFailMsgResendInfo", vo);
	}


	//SMS INSERT BATCH
	public int insertSmsBatch(List<SmsManageVo> list) throws Exception {
		// TODO Auto-generated method stub
		int resultInt = 0;
		if(list.size() > 0){
			getSqlMapClientTemplate().getSqlMapClient().startBatch();
			for(int i = 0; i < list.size(); i++){
				resultInt += (Integer)getSqlMapClientTemplate().getSqlMapClient().insert("insertSmsList", list.get(i));
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();  
		}
		return resultInt;
	}


	//이벤트 대회용 MMS LOG Table 발송내역 UNION 리스트
	public List<SmsManageVo> getMmsLogTableUnionListEventContest(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return list("getMmsLogTableUnionListEventContest",vo);
	}


	//MMS LOG Table 발송내역 UNION 페이징 리스트
	public List<SmsManageVo> getMmsLogTableUnionPagingList(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return list("getMmsLogTableUnionPagingList", vo);
	}

	//MMS LOG Table 발송내역 UNION 페이징 리스트 카운트
	public int getMmsLogTableUnionPagingListCnt(SmsManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("getMmsLogTableUnionPagingListCnt", vo);
	}
	
	
}
