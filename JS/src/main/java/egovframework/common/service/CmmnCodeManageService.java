package egovframework.common.service;

import java.util.List;
import java.util.Map;
import egovframework.common.service.CmmnCodeManageVo;

public interface CmmnCodeManageService {

	// 공통코드그룹 리스트
	public List<CmmnCodeManageVo> getCodeGroupList(String group_cd) throws Exception;
}
