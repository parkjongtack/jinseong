package egovframework.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.common.service.SmsManageService;
import egovframework.common.service.SmsManageVo;

@Repository("SmsManageService")
public class SmsManageServiceImpl implements SmsManageService{

	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="SmsManageDAO")
	SmsManageDAO SmsManageDAO;
	
	//발송 SMS 리스트
	@Override
	public List<SmsManageVo> selectSmsList(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.selectSmsList(vo);
	}
	
	//발송 SMS 리스트 카운트
	@Override
	public int selectSmsListCnt(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.selectSmsListCnt(vo);
	}
	
	//SMS 발송
	@Override
	public int insertSmsList(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.insertSmsList(vo);
	}

	//MMS 발송
	@Override
	public int insertMmsList(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.insertMmsList(vo);
	}
	//MMS INSERT BATCH
	@Override
	public int insertMmsBatch(List<SmsManageVo> list) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.insertMmsBatch(list);
	}

	//MMS LOG LIST
	@Override
	public List<SmsManageVo> selectSmsLogList(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.selectSmsLogList(vo);
	}
	
	//MMS LOG LIST - SMS관리에서 발송
	@Override
	public List<SmsManageVo> selectSmsLogListAboutGeneralAndExcel(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.selectSmsLogListAboutGeneralAndExcel(vo);
	}

	//MMS LOG Table 리스트
	@Override
	public List<SmsManageVo> getMmsLogTableList(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.getMmsLogTableList(vo);
	}

	//MMS LOG Table 발송내역 UNION 리스트
	@Override
	public List<SmsManageVo> getMmsLogTableUnionList(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.getMmsLogTableUnionList(vo);
	}

	//MMS 재전송 정보 조회 
	@Override
	public SmsManageVo getSelectResendMmsInfo(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.getSelectResendMmsInfo(vo);
	}

	//MMS 재전송 처리 후 FLAG 변경
	@Override
	public int setUpdateFailMsgResendInfo(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.setUpdateFailMsgResendInfo(vo);
	}

	//SMS INSERT BATCH
	@Override
	public int insertSmsBatch(List<SmsManageVo> list) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.insertSmsBatch(list);
	}

	//이벤트 대회용 MMS LOG Table 발송내역 UNION 리스트
	@Override
	public List<SmsManageVo> getMmsLogTableUnionListEventContest(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.getMmsLogTableUnionListEventContest(vo);
	}

	//MMS LOG Table 발송내역 UNION 페이징 리스트
	@Override
	public List<SmsManageVo> getMmsLogTableUnionPagingList(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.getMmsLogTableUnionPagingList(vo);
	}

	//MMS LOG Table 발송내역 UNION 페이징 리스트 카운트
	@Override
	public int getMmsLogTableUnionPagingListCnt(SmsManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return SmsManageDAO.getMmsLogTableUnionPagingListCnt(vo);
	}
}

