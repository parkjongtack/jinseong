package egovframework.client.member.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.client.member.service.MemberManageVo;
import egovframework.client.member.service.ShopMemberManageVo;
import egovframework.common.ShopAbstractDAO;

@Repository("shopMemberManageDAO")
public class ShopMemberManageDAO extends ShopAbstractDAO {

	//로그인
	public int loginAction(ShopMemberManageVo vo) {
		return (Integer) getSqlMapClientTemplate().queryForObject("loginShopAction", vo);
	}

	//로그인 회원 정보
	public ShopMemberManageVo selectMemberInfo(ShopMemberManageVo vo) {
		return (ShopMemberManageVo) selectByPk("selectShopMemberInfo", vo);
	}
	
	//아이디 중복체크
	public int mberIdOverlapCheck(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("shopMberIdOverlapCheck", vo);
	}
	
	//이메일 중복체크
	public int mberEmailOverlapCheck(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("shopMberEmailOverlapCheck", vo);
	}
	
	//회원가입 데이터 저장
	public int insertMember(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().insert("insertShopMember",vo);
	}
	
	//회원가입 데이터 저장시 이름 성별 연락처 생일로 중복 체크
	public int memberCheck(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("checkShopMember",vo);
	}
	
	//로그인 날짜 업데이트
	public int updateLastLoginDt(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("updateShopLastLoginDt",vo);
	}	
	
	//마이페이지 - 비밀번호 재확인
	public int myPwdCheck(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("myPwdCheck", vo);
	}

	//회원정보수정
	public int updateMember(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("updateShopMember",vo);
	}
	
	//회원정보수정 id 값으로
	public int updatePw(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("updatePw",vo);
	}
	
	//회원정보수정 reg 값으로
	public int mupdatePw(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("mupdatePw",vo);
	}
		
	//DI 값 확인
	public int mberDIOverlapCheck(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("shopMberDIOverlapCheck", vo);
	}
	
	//id 찾기
	public String idFind(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("idFind", vo);
	}
	
	//id 찾기 휴대폰인증
	public String midFind(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("midFind", vo);
	}
	
	//pw 찾기
	public int pwFind(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("pwFind", vo);
	}
	
	//pw 찾기 휴대폰인증
	public int mpwFind(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("mpwFind", vo);
	}
	
	//pw 찾기 휴대폰인증
	public String rcCheck(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("rcCheck", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<ShopMemberManageVo> selectadminMemberList(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return list("selectadminMemberList", vo);
	}

	public int selectadminMemberListCnt(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("selectadminMemberListCnt", vo);
	}
	
	//마이페이지 - 회원탈퇴(1)
	public ShopMemberManageVo getMyInfoForLeave(ShopMemberManageVo vo) {
		return (ShopMemberManageVo)selectByPk("getMyInfoForLeave", vo);
	}
	
	//마이페이지 - 회원탈퇴(2)
	public int updateMyWithdrawInfo(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("updateMyWithdrawInfo",vo);
	}
	
	//마이페이지 - 회원탈퇴(3)
	public int insertMyWithdrawInfo(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().insert("insertMyWithdrawInfo",vo);
	}
	
	//SMS 리스트 - 전체발송 - 문자 수신 동의 회원 이름
	public String selectAllSmsAgreeMemberName() {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("selectAllSmsAgreeMemberName");
	}
	
	//SMS 리스트 - 전체발송 - 문자 수신 동의 회원 아이디
	public String selectAllSmsAgreeMemberID() {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("selectAllSmsAgreeMemberID");
	}
	
	//SMS 리스트 - 전체발송 - 문자 수신 동의 회원 번호
	public String selectAllSmsAgreeMemberPhone() {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("selectAllSmsAgreeMemberPhone");
	}
	
	//SMS 리스트 - 문자 수신 동의 회원 리스트
	public List<ShopMemberManageVo> selectSmsAgreeMemberList(ShopMemberManageVo vo) {
		List<ShopMemberManageVo> list	= list("selectSmsAgreeMemberList",vo);
		return list;
	}

	
	/**
	 * 회원 정보 엑셀 다운로드
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<ShopMemberManageVo> selectadminMemberListExcel(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return  list("selectadminMemberListExcel",vo);
	}

	/**
	 * 랜덤 회원 선택 후 로그인 처리 (부하 테스트용 임시 로직)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ShopMemberManageVo tempRandomSelectMemberInfo(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return (ShopMemberManageVo)getSqlMapClientTemplate().queryForObject("tempRandomSelectMemberInfo");
	}
	
	

	/**
	 * 본인인증 거절 카운팅 증가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateMemberDiRejectCnt(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateMemberDiRejectCnt", vo);
	}

	/**
	 * 본인인증 정보 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateMemberDiInfo(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateMemberDiInfo", vo);
	}

	
	/**
	 * 본인인증 거절 카운팅 마지막으로 증가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateMemberDiRejectLastCount(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateMemberDiRejectLastCount", vo);
	}


	/**
	 * 본인인증 거절 처리
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateNotCertificateMember(ShopMemberManageVo vo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateNotCertificateMember", vo);
	}

	
	
}
