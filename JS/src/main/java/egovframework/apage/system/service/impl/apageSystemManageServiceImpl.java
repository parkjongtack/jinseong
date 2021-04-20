package egovframework.apage.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import egovframework.apage.system.service.apageSystemManageService;
import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.client.lb.service.impl.LbSystemManageDAO;

@Repository("apageSystemManageService")
public class apageSystemManageServiceImpl implements apageSystemManageService {
	
	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="apageSystemManageDAO")
	apageSystemManageDAO apageSystemManageDAO;

	@Resource(name="LbSystemManageDAO")
	LbSystemManageDAO LbSystemManageDAO;

	//팝업 리스트
	@Override
	public List<apageSystemManageVo> selectadminPopupList(apageSystemManageVo vo) throws Exception {
		List<apageSystemManageVo> list = apageSystemManageDAO.selectadminPopupList(vo);
		return list;
	}

	//팝업 리스트 카운트
	@Override
	public int selectadminPopupListCnt(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.selectadminPopupListCnt(vo);
	}

	//팝업 입력
	@Override
	public int insertAdminPopup(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= apageSystemManageDAO.insertAdminPopup(vo);
		return list;
	}

	//팝업 상세
	@Override
	public apageSystemManageVo getAdminPopupView(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.getAdminPopupView(vo);
	}

	//팝업 삭제
	@Override
	public int adminPopupDelete(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.adminPopupDelete(vo);
	}

	//팝업 수정
	@Override
	public int updateAdminPopup(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.updateAdminPopup(vo);
	}

	//배너 리스트
	@Override
	public List<apageSystemManageVo> selectadminBannerList(apageSystemManageVo vo) throws Exception {
		List<apageSystemManageVo> list = apageSystemManageDAO.selectadminBannerList(vo);
		return list;
	}

	//배너 리스트 카운트
	@Override
	public int selectadminBannerListCnt(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.selectadminBannerListCnt(vo);
	}

	//배너 입력
	@Override
	public int insertAdminBanner(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= apageSystemManageDAO.insertAdminBanner(vo);
		return list;
	}

	//배너 상세
	@Override
	public apageSystemManageVo getAdminBannerView(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.getAdminBannerView(vo);
	}

	//배너 삭제
	@Override
	public int adminBannerDelete(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.adminBannerDelete(vo);
	}

	//배너 수정
	@Override
	public int updateAdminBanner(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.updateAdminBanner(vo);
	}

	//컨텐츠 불러오기
	@Override
	public List<apageSystemManageVo> getContentsView(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		List<apageSystemManageVo> list = apageSystemManageDAO.getContentsView(vo);
		return list;
	}

	//컨텐츠 불러오기
	@Override
	public apageSystemManageVo getContents(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.getContents(vo);
	}
	
	//컨텐츠 등록
	@Override
	public int insertContents(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.insertContents(vo);
	}

	//컨텐츠 수정
	@Override
	public int updateContents(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.updateContents(vo);
	}

	//공통코드 리스트
	@Override
	public List<apageSystemManageVo> selectadminCodeList(apageSystemManageVo vo) throws Exception {
		List<apageSystemManageVo> list = apageSystemManageDAO.selectadminCodeList(vo);
		return list;
	}

	//공통코드 리스트 카운트
	@Override
	public int selectadminCodeListCnt(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.selectadminCodeListCnt(vo);
	}

	//분류명 중복체크
	@Override
	public int getcodeNameChk(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.getcodeNameChk(vo);
	}

	//분류명 등록
	@Override
	public int setcodeNameReg(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.setcodeNameReg(vo);
	}

	//분류명 리스트
	@Override
	public List<apageSystemManageVo> getCLCodeList(apageSystemManageVo vo) throws Exception {
		List<apageSystemManageVo> list = apageSystemManageDAO.getCLCodeList(vo);
		return list;
	}

	//공통코드 등록
	@Override
	public int insertAdminCode(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.insertAdminCode(vo);
	}

	//공통코드 상세
	@Override
	public apageSystemManageVo getCodeView(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.getCodeView(vo);
	}

	//공통코드 삭제
	@Override
	public int setCodeDelete(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.setCodeDelete(vo);
	}

	//공통코드 수정
	@Override
	public int setCodeUpdate(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.setCodeUpdate(vo);
	}

	//등록된 코드 가져오기
	@Override
	public List<apageSystemManageVo> getCodeNameList(apageSystemManageVo cvo) throws Exception {
		List<apageSystemManageVo> list = apageSystemManageDAO.getCodeNameList(cvo);
		return list;
	}

	//코드 중복체크
	@Override
	public int codeSeqChk(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.codeSeqChk(vo);
	}

	//메인에 컨텐츠 출력
	@Override
	public apageSystemManageVo getContentsView_main(apageSystemManageVo vo) throws Exception {
		
		// TODO Auto-generated method stub
		apageSystemManageVo list = apageSystemManageDAO.getContentsView_main(vo);
		return list;
	}

	//팝업 메인페이지 리스트 조건 추가
	@Override
	public List<apageSystemManageVo> selectClientPopupList(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		List<apageSystemManageVo> list = apageSystemManageDAO.selectClientPopupList(vo);
		return list;
	}
	
	//사용자 메인페이지 배너 리스트
	@Override
	public List<apageSystemManageVo> selectClientBannerList(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		List<apageSystemManageVo> list = apageSystemManageDAO.selectClientBannerList(vo);
		return list;
	}

	//파일 ID 업데이트
	@Override
	public int updateBannerFile(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.updateBannerFile(vo);
	}

	//파일 ID 업데이트
	@Override
	public int updateContentFile(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.updateContentFile(vo);
	}
	
	//팝업 체크박스 선택삭제
	@Override
	public int adminPopupChkSelectDelete(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.adminPopupChkSelectDelete(vo);
	}

	//배너 체크박스 선택삭제
	@Override
	public int adminBannerChkSelectDelete(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.adminBannerChkSelectDelete(vo);
	}
	
	//접근제어 리스트
	@Override
	public List<apageSystemManageVo> selectadminAccList(apageSystemManageVo vo) throws Exception {
		List<apageSystemManageVo> list = apageSystemManageDAO.selectadminAccList(vo);
		return list;
	}

	//접근제어 리스트 카운트
	@Override
	public int selectadminAccListCnt(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.selectadminAccListCnt(vo);
	}
	
	//접근제어 입력
	@Override
	public int insertAdminAcc(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		int list= apageSystemManageDAO.insertAdminAcc(vo);
		return list;
	}
	
	//접근제어 수정
	@Override
	public int updateAdminAcc(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.updateAdminAcc(vo);
	}
	
	//접근제어 상세
	@Override
	public apageSystemManageVo getAdminAccView(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.getAdminAccView(vo);
	}
	
	//ip체크 카운트
	@Override
	public int getAdminAccCnt(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.getAdminAccCnt(vo);
	}	
	
	//접근제어 삭제
	@Override
	public int deleteAdminAcc(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.deleteAdminAcc(vo);
	}

	//로그입력
	@Override
	public int insertAgentLog(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return apageSystemManageDAO.insertAgentLog(vo);
	}

	
	/**
	 * 로드밸런싱용 
	 * 
	 * */
	@Override
	public List<apageSystemManageVo> lb_selectClientPopupList(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbSystemManageDAO.lb_selectClientPopupList(vo);
	}

	@Override
	public List<apageSystemManageVo> lb_selectadminBannerList(apageSystemManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return LbSystemManageDAO.lb_selectadminBannerList(vo);
	}
	
	
}
