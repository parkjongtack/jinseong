package egovframework.apage.apmanage.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.apmanage.service.ApageManageVo;
import egovframework.common.GpAbstractDAO;

@Repository("apageManageDAO")
public class ApageManageDAO extends GpAbstractDAO {
	private Logger logger	= Logger.getLogger(this.getClass());

	//관리자 로그인
	public int adminLoginAction(ApageManageVo vo) {
		return (Integer) getSqlMapClientTemplate().queryForObject("adminLoginAction", vo);
	}

	//관리자 정보
	public ApageManageVo selectAdminMemberInfo(ApageManageVo vo) {
		return (ApageManageVo) selectByPk("selectAdminMemberInfo", vo);
	}
	
	
	
}
