package egovframework.common.service.impl;

import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import egovframework.common.GpAbstractDAO;
import egovframework.common.service.CmmnCodeManageService;
import egovframework.common.service.CmmnCodeManageVo;


@Repository("CmmnCodeManageDAO")
public class CmmnCodeManageDAO extends GpAbstractDAO {

    Logger log = Logger.getLogger(this.getClass());
    
	// 공통코드그룹 리스트
	@SuppressWarnings("unchecked")
	public List<CmmnCodeManageVo> getCodeGroupList(String group_cd){
		List<CmmnCodeManageVo> list	= list("getCodeGroupList", group_cd);
		return list;
	}
}
