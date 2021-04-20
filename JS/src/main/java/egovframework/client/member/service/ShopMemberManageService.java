package egovframework.client.member.service;

import java.util.List;

/**
 * 쇼핑몰 사용자 인터페이지
 * @author eomhs
 *
 */
public interface ShopMemberManageService {

	/**
	 * 최근접속일 업데이트
	 * @param vo
	 */
	public int updateLastLoginDt(ShopMemberManageVo vo) throws Exception;

	/**
	 * 로그인 처리
	 * @param vo
	 * @return
	 */
	public int loginAction(ShopMemberManageVo vo) throws Exception;

	/**
	 * 회원정보 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ShopMemberManageVo selectMemberInfo(ShopMemberManageVo vo) throws Exception;

	/**
	 * id 종복체크
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mberIdOverlapCheck(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * email 종복체크
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mberEmailOverlapCheck(ShopMemberManageVo vo) throws Exception;
		
	/**
	 * 회원 정보 등록
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int insertMember(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * 회원 정보 등록시 이름 성별 연락처 생일로 중복체크
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int memberCheck(ShopMemberManageVo vo) throws Exception;
		
	/**
	 * 마이페이지 - 비밀번호 재확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int myPwdCheck(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * 회원정보 수정
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateMember(ShopMemberManageVo vo) throws Exception;

	/**
	 * DI 값 비교
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mberDIOverlapCheck(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * 관리자페이지 회원 정보 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<ShopMemberManageVo> selectadminMemberList(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * 관리자페이지 회원 카운트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectadminMemberListCnt(ShopMemberManageVo vo) throws Exception;

	/**
	 * 관리자 페이지 회원상세
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	//public ShopMemberManageVo getAdminMemberView(ShopMemberManageVo vo) throws Exception;
		
	/**
	 * id 찾기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String idFind(ShopMemberManageVo vo) throws Exception;
	
	
	/**
	 * id 찾기 휴대폰 인증
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String midFind(ShopMemberManageVo vo) throws Exception;
	
	
	/**
	 * pw 찾기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int pwFind(ShopMemberManageVo vo) throws Exception;
	
//	/**
//	 * 휴대폰인증 pw 찾기
//	 * @param vo
//	 * @return
//	 * @throws Exception
//	 */
	public int mpwFind(ShopMemberManageVo vo) throws Exception;
	
//	/**
//	 * 레코드값 판별
//	 * @param vo
//	 * @return
//	 * @throws Exception
//	 */
	public String rcCheck(ShopMemberManageVo vo) throws Exception;
	
		
	/**
	 * 회원pw 수정 id 값으로
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updatePw(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * 회원pw 수정 id 값으로
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mupdatePw(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * 마이페이지 - 회원탈퇴(1)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ShopMemberManageVo getMyInfoForLeave(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * 마이페이지 - 회원탈퇴(2)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateMyWithdrawInfo(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * 마이페이지 - 회원탈퇴(3)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int insertMyWithdrawInfo(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * SMS 리스트 - 전체발송 - 문자 수신 동의 회원 이름
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String selectAllSmsAgreeMemberName() throws Exception;
	
	/**
	 * SMS 리스트 - 전체발송 - 문자 수신 동의 회원 아이디
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String selectAllSmsAgreeMemberID() throws Exception;
	
	/**
	 * SMS 리스트 - 전체발송 - 문자 수신 동의 회원 번호
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String selectAllSmsAgreeMemberPhone() throws Exception;
	
	/**
	 * SMS 리스트 - 문자 수신 동의 회원 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<ShopMemberManageVo> selectSmsAgreeMemberList(ShopMemberManageVo vo) throws Exception;
	
	
	/**
	 * 회원 정보 엑셀 다운로드
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<ShopMemberManageVo> selectadminMemberListExcel(ShopMemberManageVo vo) throws Exception;
	
	
	
	/**
	 * 랜덤 회원 선택 후 로그인 처리 (부하 테스트용 임시 로직)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ShopMemberManageVo tempRandomSelectMemberInfo(ShopMemberManageVo vo) throws Exception;
	
	/**
	 * 본인인증 거절 카운팅 증가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateMemberDiRejectCnt(ShopMemberManageVo vo) throws Exception;
	
	
	/**
	 * 본인인증 정보 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateMemberDiInfo(ShopMemberManageVo vo) throws Exception;
	
	
	/**
	 * 본인인증 거절 카운팅 마지막으로 증가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateMemberDiRejectLastCount(ShopMemberManageVo vo) throws Exception;
	
	
	/**
	 * 본인인증 거절 처리
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateNotCertificateMember(ShopMemberManageVo vo) throws Exception;
	
	
	
}
