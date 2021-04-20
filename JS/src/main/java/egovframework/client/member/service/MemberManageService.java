package egovframework.client.member.service;

import java.util.List;

public interface MemberManageService {
	
	//로그인
	public int loginAction(MemberManageVo vo) throws Exception;

	//로그인 회원 정보
	public MemberManageVo selectMemberInfo(MemberManageVo vo) throws Exception;
	
	//아이디 중볷체크	
	public int mberIdOverlapCheck(MemberManageVo vo) throws Exception;
	
	//회원가입 데이터 저장
	public int insertMember(MemberManageVo vo) throws Exception;

	//로그인 날짜 업데이트
	public int updateLastLoginDt(MemberManageVo vo) throws Exception;
	
	//회원정보 수정
	public int updateMember(MemberManageVo vo) throws Exception;
}
