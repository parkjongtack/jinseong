package egovframework.apage.apmanage.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.apmanage.service.ApageManageService;
import egovframework.apage.apmanage.service.ApageManageVo;

@Repository("apageManageService")
public class ApageManageServiceImpl implements ApageManageService {
	
	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="apageManageDAO")
	ApageManageDAO apageManageDAO;

	//관리자 로그인
	@Override
	public int adminLoginAction(ApageManageVo vo) throws Exception {
		return apageManageDAO.adminLoginAction(vo);
	}

	//관리자 정보
	@Override
	public ApageManageVo selectAdminMemberInfo(ApageManageVo vo) throws Exception {
		return apageManageDAO.selectAdminMemberInfo(vo);
	}

}
