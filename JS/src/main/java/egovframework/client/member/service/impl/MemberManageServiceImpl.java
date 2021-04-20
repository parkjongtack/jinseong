package egovframework.client.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.member.service.impl.MemberManageDAO;
import egovframework.client.member.service.MemberManageService;
import egovframework.client.member.service.MemberManageVo;

@Repository("MemberManageService")
public class MemberManageServiceImpl implements MemberManageService {
	
	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="MemberManageDAO")
	MemberManageDAO MemberManageDAO;
	
	//로그인
	@Override
	public int loginAction(MemberManageVo vo) throws Exception {
		return MemberManageDAO.loginAction(vo);
	}

	//로그인 회원 정보
	@Override
	public MemberManageVo selectMemberInfo(MemberManageVo vo) throws Exception {
		return MemberManageDAO.selectMemberInfo(vo);
	}
	
	//아이디 중복체크
	@Override
	public int mberIdOverlapCheck(MemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return MemberManageDAO.mberIdOverlapCheck(vo);
	}
	
	//회원가입 데이터 저장
	@Override
	public int insertMember(MemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return MemberManageDAO.insertMember(vo);
	}
	
	//로그인 날짜 업데이트
	@Override
	public int updateLastLoginDt(MemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return MemberManageDAO.updateLastLoginDt(vo);
	}
	
	//회원정보 수정
	@Override
	public int updateMember(MemberManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return MemberManageDAO.updateMember(vo);
	}
}
