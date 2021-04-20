package egovframework.apage.member.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.common.GpAbstractDAO;

@Repository("apageMemberManageDAO")
public class apageMemberManageDAO extends GpAbstractDAO {
	
	private Logger logger	= Logger.getLogger(this.getClass());
	
	//관리자 로그인
	public int adminLoginAction(apageMemberManageVo vo) {
		return (Integer) getSqlMapClientTemplate().queryForObject("adminLoginAction", vo);
	}

	//관리자 정보
	public apageMemberManageVo selectAdminMemberInfo(apageMemberManageVo vo) {
		return (apageMemberManageVo) selectByPk("selectAdminMemberInfo", vo);
	}
	

	//관리자계정 리스트
	public List<apageMemberManageVo> selectadminMemberList(apageMemberManageVo vo) {
		// TO-DO, 
		List<apageMemberManageVo> list	= list("selectadminMemberList", vo);
		
		return list;
	}

	//관리자계정 리스트 카운트
	public int selectadminMemberListCnt(apageMemberManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectadminMemberListCnt", vo);		
		return list;
	}

	//아이디중복체크
	public int getadminIdChk(apageMemberManageVo vo) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getadminIdChk", vo);
	}

	//관리자계정 입력
	public int insertAdminMember(apageMemberManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertAdminMember",vo);
	}

	//관리자 상세
	public apageMemberManageVo getAdminMemberView(apageMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (apageMemberManageVo)selectByPk("getAdminMemberView", vo);
	}

	//관리자 수정
	public int updateAdminMember(apageMemberManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateAdminMember", vo);
	}

	//회원 리스트 엑셀
	public List<apageMemberManageVo> selectMemberExcel(apageMemberManageVo vo) { 
		List<apageMemberManageVo> list	= list("selectMemberExcel", vo);			
		return list;
	}
	
	//SMS 발송 기록(sms_manage) 리스트
	public List<apageMemberManageVo> selectSmsHistoryList(apageMemberManageVo vo) {
		List<apageMemberManageVo> list	= list("selectSmsHistoryList", vo);
		return list;
	}
	
	//SMS 발송 기록(sms_manage) 리스트 카운트
	public int selectSmsHistoryListCnt(apageMemberManageVo vo) {
		
		return (Integer)getSqlMapClientTemplate().queryForObject("selectSmsHistoryListCnt", vo);
	}
	
	//SMS 발송 기록(sms_manage)
	public int insertSmsHistoryForList(apageMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().insert("insertSmsHistoryForList",vo);
	}
}
