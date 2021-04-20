package egovframework.common.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import egovframework.common.service.CmmnCodeManageService;
import egovframework.common.service.CmmnCodeManageVo;

@Service("CmmnCodeManageService")
public class CmmnCodeManageServiceImpl implements CmmnCodeManageService {

    @Resource(name = "CmmnCodeManageDAO")
    private CmmnCodeManageDAO CmmnCodeManageDAO;

    Logger log = Logger.getLogger(this.getClass());

	// 공통코드그룹 리스트
	
	public List<CmmnCodeManageVo> getCodeGroupList(String group_cd) throws Exception {

		List<CmmnCodeManageVo> list	= CmmnCodeManageDAO.getCodeGroupList(group_cd);

		return list;
	}

   
}
