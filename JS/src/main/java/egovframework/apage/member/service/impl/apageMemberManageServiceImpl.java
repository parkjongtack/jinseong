package egovframework.apage.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.member.service.impl.apageMemberManageDAO;
import egovframework.common.service.SmsManageVo;
import egovframework.apage.member.service.apageMemberManageService;
import egovframework.apage.member.service.apageMemberManageVo;

@Repository("apageMemberManageService")
public class apageMemberManageServiceImpl implements apageMemberManageService {
	
	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="apageMemberManageDAO")
	apageMemberManageDAO apageMemberManageDAO;
	
	//관리자 로그인
	@Override
	public int adminLoginAction(apageMemberManageVo vo) throws Exception {
		return apageMemberManageDAO.adminLoginAction(vo);
	}

	//관리자 정보
	@Override
	public apageMemberManageVo selectAdminMemberInfo(apageMemberManageVo vo) throws Exception {
		return apageMemberManageDAO.selectAdminMemberInfo(vo);
	}
	
	//관리자계정 리스트
	@Override
	public List<apageMemberManageVo> selectadminMemberList(apageMemberManageVo vo) throws Exception {
		List<apageMemberManageVo> list = apageMemberManageDAO.selectadminMemberList(vo);
		return list;
	}

	//관리자계정 리스트 카운트
	@Override
	public int selectadminMemberListCnt(apageMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageMemberManageDAO.selectadminMemberListCnt(vo);
	}

	//아이디 중복체크
	@Override
	public int getadminIdChk(apageMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageMemberManageDAO.getadminIdChk(vo);
	}

	//관리자계정 입력
	@Override
	public int insertAdminMember(apageMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= apageMemberManageDAO.insertAdminMember(vo);
		return list;
	}

	//관리자 상세
	@Override
	public apageMemberManageVo getAdminMemberView(apageMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageMemberManageDAO.getAdminMemberView(vo);
	}

	//관리자 수정
	@Override
	public int updateAdminMember(apageMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageMemberManageDAO.updateAdminMember(vo);
	}
	
	//회원 리스트 엑셀출력
	@Override
	public List<apageMemberManageVo> selectMemberExcel(apageMemberManageVo vo) throws Exception {
		List<apageMemberManageVo> list = apageMemberManageDAO.selectMemberExcel(vo);
		return list;
	}
	
	//SMS 발송 기록(sms_manage) 리스트
	@Override
	public List<apageMemberManageVo> selectSmsHistoryList(apageMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageMemberManageDAO.selectSmsHistoryList(vo);
	}
	
	//SMS 발송 기록(sms_manage) 리스트 카운트
	@Override
	public int selectSmsHistoryListCnt(apageMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageMemberManageDAO.selectSmsHistoryListCnt(vo);
	}
	
	//SMS 발송 기록(sms_manage)
	@Override
	public int insertSmsHistoryForList(apageMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageMemberManageDAO.insertSmsHistoryForList(vo);
	}
}
