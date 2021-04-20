package egovframework.apage.apmanage.service;

public interface ApageManageService {

	// 관리자 로그인
	public int adminLoginAction(ApageManageVo vo) throws Exception;

	// 관리자 정보
	public ApageManageVo selectAdminMemberInfo(ApageManageVo vo) throws Exception;

}
