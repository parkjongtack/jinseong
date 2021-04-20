package egovframework.apage.system.service.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.common.GpAbstractDAO;

@Repository("apageSystemManageDAO")
public class apageSystemManageDAO extends GpAbstractDAO {
	
	private Logger logger	= Logger.getLogger(this.getClass());

	//팝업 리스트
	public List<apageSystemManageVo> selectadminPopupList(apageSystemManageVo vo) {
		// TO-DO, 
		List<apageSystemManageVo> list	= list("selectadminPopupList", vo);
		
		return list;
	}

	//팝업 리스트 카운트
	public int selectadminPopupListCnt(apageSystemManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectadminPopupListCnt", vo);		
		return list;
	}

	//팝업입력
	public int insertAdminPopup(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertAdminPopup", vo);
	}

	//팝업 상세
	public apageSystemManageVo getAdminPopupView(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (apageSystemManageVo)selectByPk("getAdminPopupView", vo);
	}

	//팝업 삭제
	public int adminPopupDelete(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("adminPopupDelete", vo);
	}

	//팝업 수정
	public int updateAdminPopup(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateAdminPopup", vo);
	}

	//배너 리스트
	public List<apageSystemManageVo> selectadminBannerList(apageSystemManageVo vo) {
		// TO-DO, 
		List<apageSystemManageVo> list	= list("selectadminBannerList", vo);
		
		return list;
	}

	//배너 리스트 카운트
	public int selectadminBannerListCnt(apageSystemManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectadminBannerListCnt", vo);		
		return list;
	}

	//배너 입력
	public int insertAdminBanner(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertAdminBanner", vo);
	}

	//배너 상세
	public apageSystemManageVo getAdminBannerView(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (apageSystemManageVo)selectByPk("getAdminBannerView", vo);
	}

	//배너 삭제
	public int adminBannerDelete(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("adminBannerDelete", vo);
	}

	//배너 수정
	public int updateAdminBanner(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateAdminBanner", vo);
	}

	//컨텐츠 불러오기
	public List<apageSystemManageVo> getContentsView(apageSystemManageVo vo) {
		// TO-DO,
		List<apageSystemManageVo> list = list("getContentsView", vo);

		return list;
	}
	
	//컨텐츠 불러오기
	public apageSystemManageVo getContents(apageSystemManageVo vo) {
		return (apageSystemManageVo)selectByPk("getContentsView", vo);
	}

	//컨텐츠 등록
	public int insertContents(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertContents", vo);
	}

	//컨텐츠 수정
	public int updateContents(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateContents", vo);
	}

	//공통코드 리스트
	public List<apageSystemManageVo> selectadminCodeList(apageSystemManageVo vo) {
		// TO-DO, 
		List<apageSystemManageVo> list	= list("selectadminCodeList", vo);
		
		return list;
	}

	//공통코드 리스트 카운트
	public int selectadminCodeListCnt(apageSystemManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectadminCodeListCnt", vo);		
		return list;
	}

	//분류명 중복체크
	public int getcodeNameChk(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getcodeNameChk", vo);
	}

	//분류명 등록
	public int setcodeNameReg(apageSystemManageVo vo) {
		if (getSqlMapClientTemplate().insert("setcodeNameReg", vo) == null) {
			return 1;
		}else{
			return 0;
		}
	}

	//분류명 리스트
	public List<apageSystemManageVo> getCLCodeList(apageSystemManageVo vo) {
		// TO-DO, 
		List<apageSystemManageVo> list	= list("getCLCodeList", vo);
		return list;
	}
	
	//공통코드 등록
	public int insertAdminCode(apageSystemManageVo vo) {
		if (getSqlMapClientTemplate().insert("insertAdminCode", vo) == null) {
			return 1;
		}else{
			return 0;
		}
	}

	//공통코드 상세
	public apageSystemManageVo getCodeView(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (apageSystemManageVo)selectByPk("getCodeView", vo);
	}

	//공통코드 삭제
	public int setCodeDelete(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("setCodeDelete", vo);
	}

	//공통코드 수정
	public int setCodeUpdate(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("setCodeUpdate", vo);
	}

	//등록된 코드 가져오기
	public List<apageSystemManageVo> getCodeNameList(apageSystemManageVo cvo) {
		// TO-DO, 
		List<apageSystemManageVo> list	= list("getCodeNameList", cvo);
		
		return list;
	}

	//코드 중복체크
	public int codeSeqChk(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().queryForObject("codeSeqChk", vo);
	}

	//메인에 컨텐츠 출력
	public apageSystemManageVo getContentsView_main(apageSystemManageVo vo) {
		// TO-DO,
		return (apageSystemManageVo)selectByPk("getContentsView", vo);
	}

	//팝업 메인페이지 리스트 조건 추가
	public List<apageSystemManageVo> selectClientPopupList(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		List<apageSystemManageVo> list	= list("selectClientPopupList", vo);
		return list;
	}

	//사용자 메인페이지 배너 리스트
	public List<apageSystemManageVo> selectClientBannerList(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		List<apageSystemManageVo> list	= list("selectClientBannerList", vo);
		return list;
	}

	//파일 ID 업데이트
	public int updateBannerFile(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateBannerFile", vo);
	}
	
	//파일 ID 업데이트
	public int updateContentFile(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateContentFile", vo);
	}

	//팝업 체크박스 선택삭제
	public int adminPopupChkSelectDelete(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("adminPopupChkSelectDelete", vo);
	}

	//배너 체크박스 선택삭제
	public int adminBannerChkSelectDelete(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("adminBannerChkSelectDelete", vo);
	}

	//접근제어 리스트
	public List<apageSystemManageVo> selectadminAccList(apageSystemManageVo vo) {
		// TO-DO, 
		List<apageSystemManageVo> list	= list("selectadminAccList", vo);
		
		return list;
	}

	//접근제어 리스트 카운트
	public int selectadminAccListCnt(apageSystemManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectadminAccListCnt", vo);		
		return list;
	}
	
	//접근제어 입력
	public int insertAdminAcc(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertAdminAcc", vo);
	}
	
	//접근제어 수정
	public int updateAdminAcc(apageSystemManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateAdminAcc", vo);
	}
	
	//접근제어 상세
	public apageSystemManageVo getAdminAccView(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (apageSystemManageVo)selectByPk("getAdminAccView", vo);
	}
	
	//ip체크 카운트
	public int getAdminAccCnt(apageSystemManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("getAdminAccCnt", vo);		
		return list;
	}
	
	//접근제어 삭제
	public int deleteAdminAcc(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().delete("deleteAdminAcc", vo);
	}

	//로그입력
	public int insertAgentLog(apageSystemManageVo vo) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().insert("insertAgentLog", vo);
	}
}
