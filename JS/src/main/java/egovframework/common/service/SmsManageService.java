package egovframework.common.service;

import java.util.List;

public interface SmsManageService {

	//발송 SMS 리스트
	public List<SmsManageVo> selectSmsList(SmsManageVo vo) throws Exception;
	
	//발송 SMS 리스트 카운트
	public int selectSmsListCnt(SmsManageVo vo) throws Exception;
	
	//SMS 발송
	public int insertSmsList(SmsManageVo vo) throws Exception;
	
	//MMS 발송
	public int insertMmsList(SmsManageVo vo) throws Exception;
	
	//MMS INSERT BATCH
	public int insertMmsBatch(List<SmsManageVo> list) throws Exception;
	
	//MMS LOG LIST
	public List<SmsManageVo> selectSmsLogList(SmsManageVo vo) throws Exception;
	
	//MMS LOG LIST - SMS관리에서 발송
	public List<SmsManageVo> selectSmsLogListAboutGeneralAndExcel(SmsManageVo vo) throws Exception;
	
	//MMS LOG Table 리스트
	public List<SmsManageVo> getMmsLogTableList(SmsManageVo vo) throws Exception;
	
	//MMS LOG Table 발송내역 UNION 리스트
	public List<SmsManageVo> getMmsLogTableUnionList(SmsManageVo vo) throws Exception;
	
	//MMS 재전송 정보 조회 
	public SmsManageVo getSelectResendMmsInfo(SmsManageVo vo) throws Exception;
	
	//MMS 재전송 처리 후 FLAG 변경 
	public int setUpdateFailMsgResendInfo(SmsManageVo vo) throws Exception;

	//SMS INSERT BATCH
	public int insertSmsBatch(List<SmsManageVo> list) throws Exception;
	
	//이벤트 대회용 MMS LOG Table 발송내역 UNION 리스트
	public List<SmsManageVo> getMmsLogTableUnionListEventContest(SmsManageVo vo) throws Exception;
	
	//MMS LOG Table 발송내역 UNION 페이징 리스트
	public List<SmsManageVo> getMmsLogTableUnionPagingList(SmsManageVo vo) throws Exception;
	
	//MMS LOG Table 발송내역 UNION 페이징 리스트 카운트
	public int getMmsLogTableUnionPagingListCnt(SmsManageVo vo) throws Exception;
	
}
