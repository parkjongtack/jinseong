package egovframework.client.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.member.service.ShopMemberManageService;
import egovframework.client.member.service.ShopMemberManageVo;
/**
 * 
 * @author eomhs
 * 쇼핑몰 사용자 정보 관련 서비스
 *
 */
@Repository("shopMemberManageService")
public class ShopMemberManageServiceImpl implements ShopMemberManageService {
	
	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="shopMemberManageDAO")
	ShopMemberManageDAO shopMemberManageDAO;	

	@Override
	public int updateLastLoginDt(ShopMemberManageVo vo) throws Exception {
		return shopMemberManageDAO.updateLastLoginDt(vo);
	}

	@Override
	public int loginAction(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.loginAction(vo);
	}

	@Override
	public ShopMemberManageVo selectMemberInfo(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.selectMemberInfo(vo);
	}

	@Override
	public int mberIdOverlapCheck(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.mberIdOverlapCheck(vo);
	}
	
	@Override
	public int mberEmailOverlapCheck(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.mberEmailOverlapCheck(vo);
	}

	@Override
	public int insertMember(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.insertMember(vo);
	}
	
	// 회원 가입시 이중가입 확인
	@Override
	public int memberCheck(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.memberCheck(vo);
	}
	
	//마이페이지 - 비밀번호 재확인
	@Override
	public int myPwdCheck(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.myPwdCheck(vo);
	}

	@Override
	public int updateMember(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.updateMember(vo);
	}

	@Override
	public int mberDIOverlapCheck(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.mberDIOverlapCheck(vo);
	}

	@Override
	public List<ShopMemberManageVo> selectadminMemberList(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.selectadminMemberList(vo);
	}

	@Override
	public int selectadminMemberListCnt(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.selectadminMemberListCnt(vo);
	}
	
	@Override
	public String idFind(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.idFind(vo);
	}
	
	@Override
	public String midFind(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.midFind(vo);
	}
	
	@Override
	public int pwFind(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.pwFind(vo);
	}
	
	@Override
	public int mpwFind(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.mpwFind(vo);
	}
	
	@Override
	public String rcCheck(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.rcCheck(vo);
	}
	
	@Override
	public int updatePw(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.updatePw(vo);
	}
	
	@Override
	public int mupdatePw(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.mupdatePw(vo);
	}
	
	//마이페이지 - 회원탈퇴(1)
	@Override
	public ShopMemberManageVo getMyInfoForLeave(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.getMyInfoForLeave(vo);
	}

	//마이페이지 - 회원탈퇴(2)
	@Override
	public int updateMyWithdrawInfo(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.updateMyWithdrawInfo(vo);
	}
	
	//마이페이지 - 회원탈퇴(3)
	@Override
	public int insertMyWithdrawInfo(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.insertMyWithdrawInfo(vo);
	}
	
	//SMS 리스트 - 전체발송 - 문자 수신 동의 회원 이름
	@Override
	public String selectAllSmsAgreeMemberName() throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.selectAllSmsAgreeMemberName();
	}
	
	//SMS 리스트 - 전체발송 - 문자 수신 동의 회원 아이디
	@Override
	public String selectAllSmsAgreeMemberID() throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.selectAllSmsAgreeMemberID();
	}
	
	//SMS 리스트 - 전체발송 - 문자 수신 동의 회원 번호
	@Override
	public String selectAllSmsAgreeMemberPhone() throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.selectAllSmsAgreeMemberPhone();
	}
	
	//SMS 리스트 - 문자 수신 동의 회원 리스트
	@Override
	public List<ShopMemberManageVo> selectSmsAgreeMemberList(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.selectSmsAgreeMemberList(vo);
	}

	
	/**
	 * 회원 정보 엑셀 다운로드
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ShopMemberManageVo> selectadminMemberListExcel(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.selectadminMemberListExcel(vo);
	}

	
	/**
	 * 랜덤 회원 선택 후 로그인 처리 (부하 테스트용 임시 로직)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public ShopMemberManageVo tempRandomSelectMemberInfo(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.tempRandomSelectMemberInfo(vo);
	}

	/**
	 * 본인인증 거절 카운팅 증가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateMemberDiRejectCnt(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.updateMemberDiRejectCnt(vo);
	}

	/**
	 * 본인인증 정보 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateMemberDiInfo(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.updateMemberDiInfo(vo);
	}

	
	/**
	 * 본인인증 거절 카운팅 마지막으로 증가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateMemberDiRejectLastCount(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.updateMemberDiRejectLastCount(vo);
	}

	/**
	 * 본인인증 거절 처리
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateNotCertificateMember(ShopMemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return shopMemberManageDAO.updateNotCertificateMember(vo);
	}
	
	
	
}
