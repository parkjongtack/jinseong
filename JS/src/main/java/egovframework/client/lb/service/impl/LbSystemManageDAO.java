package egovframework.client.lb.service.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.common.GpAbstractDAO_lb;

@Repository("LbSystemManageDAO")
public class LbSystemManageDAO extends GpAbstractDAO_lb {
	
	private Logger logger	= Logger.getLogger(this.getClass());


	//배너 리스트
	public List<apageSystemManageVo> lb_selectClientPopupList(apageSystemManageVo vo) {
		// TO-DO, 
		List<apageSystemManageVo> list	= list("lb_selectClientPopupList", vo);
		
		return list;
	}
	
	//팝업 메인페이지 리스트 조건 추가
	public List<apageSystemManageVo> lb_selectadminBannerList(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		List<apageSystemManageVo> list	= list("lb_selectadminBannerList", vo);
		return list;
	}
	
}
