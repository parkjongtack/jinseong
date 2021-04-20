package egovframework.client.member.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.member.service.MemberManageVo;
import egovframework.common.GpAbstractDAO;

@Repository("MemberManageDAO")
public class MemberManageDAO extends GpAbstractDAO {
	
	private Logger logger	= Logger.getLogger(this.getClass());
	
	//로그인
	public int loginAction(MemberManageVo vo) {
		return (Integer) getSqlMapClientTemplate().queryForObject("loginAction", vo);
	}

	//로그인 회원 정보
	public MemberManageVo selectMemberInfo(MemberManageVo vo) {
		return (MemberManageVo) selectByPk("selectMemberInfo", vo);
	}
	
	//아이디 중복체크
	public int mberIdOverlapCheck(MemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("mberIdOverlapCheck", vo);
	}
	
	//회원가입 데이터 저장
	public int insertMember(MemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().insert("insertMember",vo);
	}

	//로그인 날짜 업데이트
	public int updateLastLoginDt(MemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("updateLastLoginDt",vo);
	}	

	//회원정보수정
	public int updateMember(MemberManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("updateMember",vo);
	}
}
