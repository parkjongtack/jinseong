package egovframework.apage.member.service;

import java.util.List;

import egovframework.common.service.SmsManageVo;

public interface apageMemberManageService {
	
	// 관리자 로그인
	public int adminLoginAction(apageMemberManageVo vo) throws Exception;

	// 관리자 정보
	public apageMemberManageVo selectAdminMemberInfo(apageMemberManageVo vo) throws Exception;

	//관리자 리스트
	public List<apageMemberManageVo> selectadminMemberList(apageMemberManageVo vo) throws Exception;

	//관리자 리스트 카운트
	public int selectadminMemberListCnt(apageMemberManageVo vo) throws Exception;

	//아이디 중복체크
	public int getadminIdChk(apageMemberManageVo vo) throws Exception;

	//관리자 계정입력
	public int insertAdminMember(apageMemberManageVo vo) throws Exception;

	//관리자 상세
	public apageMemberManageVo getAdminMemberView(apageMemberManageVo vo) throws Exception;

	//관리자 수정
	public int updateAdminMember(apageMemberManageVo vo) throws Exception;
	
	//회원 리스트 엑셀
	public List<apageMemberManageVo> selectMemberExcel(apageMemberManageVo vo) throws Exception;

	//SMS 발송 기록(sms_manage) 리스트
	public List<apageMemberManageVo> selectSmsHistoryList(apageMemberManageVo vo) throws Exception;
	
	//SMS 발송 기록(sms_manage) 리스트 카운트
	public int selectSmsHistoryListCnt(apageMemberManageVo vo) throws Exception;
	
	//SMS 발송 기록(sms_manage)
	public int insertSmsHistoryForList(apageMemberManageVo vo) throws Exception;
}
