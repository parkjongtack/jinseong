package egovframework.apage.system.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.apage.board.service.apageBoardManageService;
import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.apage.system.service.apageSystemManageService;
import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.common.core.ResultDataManager;
import egovframework.common.core.SendMiPlatform;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.utils.CommonUtil;
import egovframework.common.utils.EgovFileMngUtil;
import egovframework.common.utils.PageVo;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class apageSystemManageController {

private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "apageBoardManageService")
	private apageBoardManageService apageBoardManageService;

	@Resource(name = "apageSystemManageService")
	private apageSystemManageService apageSystemManageService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name = "CommonUtil")
	private CommonUtil CommonUtil;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	@Resource(name = "FileManageService")
	private FileManageService fileManageService;
	
	
	//팝업 리스트
	@RequestMapping(value="/apage/system/adminPopupList.do")
	public String adminPopupList(@ModelAttribute("vo") apageSystemManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		List<apageSystemManageVo> list = apageSystemManageService.selectadminPopupList(vo);		
		int totCnt = apageSystemManageService.selectadminPopupListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		/*//검색 분류1
		model.addAttribute("scType", vo.getScType());
		//검색 분류
		mav.addObject("scType2", vo.getScType2());
		//검색 분류
		mav.addObject("scType3", vo.getScType3());*/
		
		model.addAttribute("popupList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/system/adminPopupList";
	}
	
	//팝업 입력페이지
	@RequestMapping(value = "/apage/system/adminPopupWrite.do")
	public String adminPopupWrite(@ModelAttribute("vo") apageSystemManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		if (null != mv && !"".equals(mv.getMber_id())){			
			isLogin=true;
			/*if (mv.getMember_type().equals("adm")){			
				member_type = "adm";
			}else{
				member_type = "cst";
			}*/
		}
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("memberinfo",mv);

		return "/apage/system/adminPopupWrite";
	}
	
	// 팝업 등록
	@RequestMapping(value="/apage/system/adminPopupReg.do")
	public String adminPopupReg(@ModelAttribute("vo") apageSystemManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response) throws Exception {
		
		int rtnVal = 0;
		try{
			
			rtnVal= apageSystemManageService.insertAdminPopup(vo);
				
			if(rtnVal > 0) {
				model.addAttribute("msg", "등록되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/system/adminPopupWrite";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "잘못된 접근 또는 오류 입니다. 다시 입력해 주세요.");
    		return "/apage/system/adminPopupWrite";
		}
		
		return "forward:/apage/system/adminPopupList.do";
	}
	
	
	// 팝업 상세
	@RequestMapping(value="/apage/system/adminPopupDetail.do")
	public String adminPopupDetail(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		apageSystemManageVo svo= apageSystemManageService.getAdminPopupView(vo);
		
		model.addAttribute("currPage", vo.getCurrRow());
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		/*//검색 분류
		mav.addObject("scType", vo.getScType());
		mav.addObject("scType2", vo.getScType2());
		mav.addObject("scType3", vo.getScType3());*/
		
		model.addAttribute("PopView", svo);
		
		return "/apage/system/adminPopupView";
	}
	
	//팝업 삭제
	@RequestMapping(value="/apage/system/adminPopupDelete.do")
	public String adminPopupDelete(@ModelAttribute("vo") apageSystemManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			result = apageSystemManageService.adminPopupDelete(vo);
			
			model.addAttribute("msg", "삭제되었습니다.");
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/system/adminPopupView";
		}
		return "forward:/apage/system/adminPopupList.do";
	}
	
	// 팝업 수정페이지
	@RequestMapping(value="/apage/system/adminPopupModify.do")
	public String adminPopupModify(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		apageSystemManageVo svo= apageSystemManageService.getAdminPopupView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		if (null != mv && !"".equals(mv.getMber_id())){			
			isLogin=true;
		}
		
		model.addAttribute("currPage", vo.getCurrRow());
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("memberinfo",mv);
		
		model.addAttribute("PopView", svo);
		
		
		return "/apage/system/adminPopupUpdt";
	}
	
	//팝업 수정
	@RequestMapping(value = "/apage/system/adminPopupUpdate.do")
	public String adminPopupUpdate(@ModelAttribute("vo") apageSystemManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		

		int rtnVal = 0;
		try {
			
			/*vo.setPassword(sha.encryption(vo.getPassword()));*/
			rtnVal= apageSystemManageService.updateAdminPopup(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/system/adminPopupUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/system/adminPopupUpdt";
		}
		return "forward:/apage/system/adminPopupList.do";
	}	
	
	
	//배너 리스트
	@RequestMapping(value="/apage/system/adminBannerList.do")
	public String adminBannerList(@ModelAttribute("vo") apageSystemManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		List<apageSystemManageVo> list = apageSystemManageService.selectadminBannerList(vo);		
		int totCnt = apageSystemManageService.selectadminBannerListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		/*//검색 분류1
		model.addAttribute("scType", vo.getScType());
		//검색 분류
		mav.addObject("scType2", vo.getScType2());
		//검색 분류
		mav.addObject("scType3", vo.getScType3());*/
		
		model.addAttribute("bannerList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/system/adminBannerList";
	}
	
	//배너 입력페이지
	@RequestMapping(value = "/apage/system/adminBannerWrite.do")
	public String adminBannerWrite(@ModelAttribute("vo") apageSystemManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		if (null != mv && !"".equals(mv.getMber_id())){			
			isLogin=true;
			/*if (mv.getMember_type().equals("adm")){			
				member_type = "adm";
			}else{
				member_type = "cst";
			}*/
		}
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("memberinfo",mv);

		return "/apage/system/adminBannerWrite";
	}
	
	// 배너 등록
	@RequestMapping(value="/apage/system/adminBannerReg.do")
	public String adminBannerReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageSystemManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		try{
			
			List<FileManageVo> result = null;
			Map<String, MultipartFile> files = multiRequest.getFileMap();		
			if(!files.isEmpty()){
	  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "banner"); 	
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageSystemManageService.insertAdminBanner(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "등록되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/system/adminBannerWrite";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "잘못된 접근 또는 오류 입니다. 다시 입력해 주세요.");
    		return "/apage/system/adminBannerWrite";
		}
		
		return "forward:/apage/system/adminBannerList.do";
	}
	
	
	// 팝업 상세
	@RequestMapping(value="/apage/system/adminBannerDetail.do")
	public String adminBannerDetail(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		apageSystemManageVo svo= apageSystemManageService.getAdminBannerView(vo);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> bannerFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("bannerFile", bannerFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("PopView", svo);
		
		return "/apage/system/adminBannerView";
	}
	
	//팝업 삭제
	@RequestMapping(value="/apage/system/adminBannerDelete.do")
	public String adminBannerDelete(@ModelAttribute("vo") apageSystemManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			result = apageSystemManageService.adminBannerDelete(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "banner";
			FileManageVo fileVO = new FileManageVo();
			if(vo.getAtch_file_id() != null && vo.getAtch_file_id() != ""){
				fileVO.setAtch_file_id(vo.getAtch_file_id().substring(0, 22));
				List<FileManageVo> fvo		= fileManageService.getFileAttachList(fileVO);
				if(fvo.size() > 0){
					for(int i=0; i < fvo.size(); i++){
						File uFile 					= new File(real + "/" + Path, fvo.get(i).getStre_file_nm());
						if(uFile.exists()) uFile.delete();
						
						fileVO.setAtch_file_id(fvo.get(i).getAtch_file_id());
						fileVO.setFile_sn(fvo.get(i).getFile_sn());
						int resultInt	= fileManageService.setFileAttachDelete(fileVO);
						//상위 파일테이블 데이터 삭제
						int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
					}
				}
			}
			
			model.addAttribute("msg", "삭제되었습니다.");
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/system/adminBannerView";
		}
		return "forward:/apage/system/adminBannerList.do";
	}
	
	// 팝업 수정페이지
	@RequestMapping(value="/apage/system/adminBannerModify.do")
	public String adminBannerModify(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		apageSystemManageVo svo= apageSystemManageService.getAdminBannerView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		if (null != mv && !"".equals(mv.getMber_id())){			
			isLogin=true;
		}
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> bannerFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("bannerFile", bannerFile);
		}
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("memberinfo",mv);
		
		model.addAttribute("PopView", svo);
		
		
		return "/apage/system/adminBannerUpdt";
	}
	
	//파일 삭제
	@RequestMapping(value="/apage/system/BannerFileDel.do")
	public String ChangeSms(@ModelAttribute("vo") apageSystemManageVo vo,
							@ModelAttribute("bvo") apageBoardManageVo bvo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "banner";
		FileManageVo fileVO = new FileManageVo();
		
		String file_id = vo.getAtch_file_id2();
		String file_id2 = vo.getAtch_file_id2().substring(0, 22)+" ";
		
		fileVO.setAtch_file_id(file_id); //fileVO.setAtch_file_id(vo.getAtch_file_id2().substring(0, 22));
		List<FileManageVo> fvo		= fileManageService.getFileAttachList(fileVO);
		if(fvo.size() > 0){
			for(int i=0; i < fvo.size(); i++){
				File uFile 					= new File(real + "/" + Path, fvo.get(i).getStre_file_nm());
				if(uFile.exists()) uFile.delete();
				
				fileVO.setAtch_file_id(fvo.get(i).getAtch_file_id());
				fileVO.setFile_sn(fvo.get(i).getFile_sn());
				int resultInt	= fileManageService.setFileAttachDelete(fileVO);
				//상위 파일테이블 데이터 삭제
				int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
			}
		}
		
		fileVO.setAtch_file_id(file_id.substring(0, 22));
		List<FileManageVo> fvo2		= fileManageService.getFileAttachList(fileVO);
		if(fvo2.size() <= 0){
			bvo.setAtch_file_id(file_id2);
			apageBoardManageService.setBoardAttachUpdt(bvo);
		}
		
		//mav.setViewName("redirect:/apage/sys/member");
			
		return this.adminBannerModify(vo, model, request, response, session);
	}
	
	//팝업 수정
	@RequestMapping(value = "/apage/system/adminBannerUpdate.do")
	public String adminBannerUpdate(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageSystemManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		

		int rtnVal = 0;
		try {
			
			String atch_file_id = "";
			int cnt = 0;
			if(!vo.getAtch_file_id().equals(null) && !vo.getAtch_file_id().equals("")){
				atch_file_id =vo.getAtch_file_id().substring(0, 22);
				FileManageVo fileVO = new FileManageVo();
				fileVO.setAtch_file_id(atch_file_id);
				List<FileManageVo> fvo		= fileManageService.getFileAttachList(fileVO);
				cnt = fvo.size();
			}
			
			List<FileManageVo> result = null;
			Map<String, MultipartFile> files = multiRequest.getFileMap();		
			if(!files.isEmpty()){
				if(cnt > 0){
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "banner"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "banner"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageSystemManageService.updateAdminBanner(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/system/adminBannerUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/system/adminBannerUpdt";
		}
		return "forward:/apage/system/adminBannerList.do";
	}
	
	
	//컨텐츠 입력페이지
	@RequestMapping(value = "/apage/system/adminContent.do")
	public String adminContent(@ModelAttribute("aForm") apageSystemManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		return "/apage/system/adminContent";
	}
	
	// 컨텐츠 관리 : 메뉴 선택시 
	@RequestMapping(value="/apage/system/getContents.do")
	public @ResponseBody ModelAndView getContents(@ModelAttribute("aForm") apageSystemManageVo vo
															,HttpServletRequest request
															,HttpServletResponse response) throws Exception {
		
		try {
			ResultDataManager rm 		= new ResultDataManager();
			Map<String,Object> data 	= new HashMap<String,Object>();
			List<apageSystemManageVo> viewList	= apageSystemManageService.getContentsView(vo);

			if(viewList != null && viewList.size() > 0){
				rm.setData(apageSystemManageVo.class, viewList);				
				return SendMiPlatform.SendData(response, rm);
				
			}else{
				data.put("ResultCode","conEmpty");
				data.put("ResultMsg","내용이 없습니다.");
				return SendMiPlatform.SendString(response, data);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
	}
	
	
	// 컨텐츠 생성
	@RequestMapping(value="/apage/system/createContents.do")
	public @ResponseBody ModelAndView createContents(@ModelAttribute("aForm") apageSystemManageVo vo
													 ,HttpServletRequest request
													 ,HttpServletResponse response
													 ,HttpSession session) throws Exception {
		
		try {
			//vo.setReg_id(CommonUtil.getRegId(request)); 로그인이 없어서 주석처리함
			
			ResultDataManager rm 		= new ResultDataManager();
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			vo.setReg_id(mv.getMber_id());
			
			
			int resultInt				= apageSystemManageService.insertContents(vo);

			if(resultInt > 0){
				data.put("ResultCode","1");
				data.put("ResultMsg","성공");
				
				List<apageSystemManageVo> viewList	= apageSystemManageService.getContentsView(vo);
				rm.setData(apageSystemManageVo.class, viewList);
				return SendMiPlatform.SendData(response, rm);
				
			}else{
				data.put("ResultCode","0");
				data.put("ResultMsg","저장 하지 못했습니다.");
				return SendMiPlatform.SendString(response, data);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
		
	}
	// 컨텐츠 수정
	@RequestMapping(value="/apage/system/updateContents.do")
	public @ResponseBody ModelAndView updateContents(@ModelAttribute("aForm") apageSystemManageVo vo
													 ,HttpServletRequest request
													 ,HttpServletResponse response
													 ,HttpSession session) throws Exception {
		
		try {
			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			vo.setUpdt_id(mv.getMber_id());
			
			ResultDataManager rm 		= new ResultDataManager();
			Map<String,Object> data 	= new HashMap<String,Object>();
			int resultInt				= apageSystemManageService.updateContents(vo);

			if(resultInt > 0){
				data.put("ResultCode","1");
				data.put("ResultMsg","성공");
				
				List<apageSystemManageVo> viewList	= apageSystemManageService.getContentsView(vo);
				rm.setData(apageSystemManageVo.class, viewList);
				return SendMiPlatform.SendData(response, rm);
				
			}else{
				data.put("ResultCode","0");
				data.put("ResultMsg","저장 하지 못했습니다.");
				return SendMiPlatform.SendString(response, data);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
		
	}	
	
	//공통코드 리스트
	@RequestMapping(value="/apage/system/adminCodeList.do")
	public String adminCodeList(@ModelAttribute("vo") apageSystemManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		List<apageSystemManageVo> list = apageSystemManageService.selectadminCodeList(vo);		
		int totCnt = apageSystemManageService.selectadminCodeListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		/*//검색 분류
		mav.addObject("scType2", vo.getScType2());
		//검색 분류
		mav.addObject("scType3", vo.getScType3());*/
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);
		
		model.addAttribute("codeList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/system/adminCodeList";
	}
	
	// 분류명 중복확인
	@RequestMapping(value="/apage/system/codeNameChk.do")
	public @ResponseBody ModelAndView getcodeNameChk(@ModelAttribute("vo") apageSystemManageVo vo
												,HttpServletRequest request
												,HttpServletResponse response) throws Exception {
		try {
		
			Map<String,Object> data 	= new HashMap<String,Object>();
			int resultInt				= apageSystemManageService.getcodeNameChk(vo);
			if(resultInt > 0){
				data.put("ResultCode","0");
				data.put("ResultMsg","중복");
			}else{
				data.put("ResultCode","1");
				data.put("ResultMsg","중복아님");
			}
				
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	// 분류명 등록
	@RequestMapping(value="/apage/system/codeNameReg.do")
	public @ResponseBody ModelAndView setcodeNameReg(@ModelAttribute("vo") apageSystemManageVo vo
												,HttpServletRequest request
												,HttpServletResponse response) throws Exception {
		try {
		
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			vo.setCode_gb("U");
			int resultInt	= apageSystemManageService.setcodeNameReg(vo);
			if(resultInt > 0){
				data.put("ResultCode","1");
				data.put("ResultMsg","성공");
			}else{
				data.put("ResultCode","0");
				data.put("ResultMsg","등록이 취소되었습니다.");
			}
				
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
		
	//코드 입력페이지
	@RequestMapping(value = "/apage/system/adminCodeWrite.do")
	public String adminCodeWrite(@ModelAttribute("vo") apageSystemManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		//분류명 리스트
		List<apageSystemManageVo> CLCodeList = apageSystemManageService.getCLCodeList(vo);
		model.addAttribute("CLCodeList", CLCodeList);
		
		model.addAttribute("memberinfo",mv);

		return "/apage/system/adminCodeWrite";
	}
	
	//코드 입력
	@RequestMapping(value = "/apage/system/adminCodeReg.do")
	public String adminCodeReg(@ModelAttribute("vo") apageSystemManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		

		int rtnVal = 0;
		try {
			
			rtnVal= apageSystemManageService.insertAdminCode(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "등록되었습니다.");		
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/system/adminCodeWrite";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/system/adminCodeWrite";
		}
		return "forward:/apage/system/adminCodeList.do";
	}
	
	
	// 공통코드 상세
	@RequestMapping(value="/apage/system/adminCodeDetail.do")
	public String adminCodeDetail(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		apageSystemManageVo svo= apageSystemManageService.getCodeView(vo);
		
		
		model.addAttribute("currPage", vo.getCurrRow());
		//검색 분류
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("codeView", svo);
		
		return "/apage/system/adminCodeView";
	}
	
	//공통코드 삭제
	@RequestMapping(value="/apage/system/adminCodeDelete.do")
	public String adminCodeDelete(@ModelAttribute("vo") apageSystemManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			result = apageSystemManageService.setCodeDelete(vo);
			
			model.addAttribute("msg", "삭제되었습니다.");
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/system/adminCodeView";
		}
		return "forward:/apage/system/adminCodeList.do";
	}
	
	// 공통코드 수정페이지
	@RequestMapping(value="/apage/system/adminCodeModify.do")
	public String adminCodeModify(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		apageSystemManageVo svo= apageSystemManageService.getCodeView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		//분류명 리스트
		List<apageSystemManageVo> CLCodeList = apageSystemManageService.getCLCodeList(svo);
		model.addAttribute("CLCodeList", CLCodeList);
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("memberinfo",mv);
		
		model.addAttribute("codeView", svo);
		
		
		return "/apage/system/adminCodeUpdt";
	}
	
	
	//공통코드 수정
	@RequestMapping(value = "/apage/system/adminCodeUpdate.do")
	public String adminCodeUpdate(@ModelAttribute("vo") apageSystemManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		

		int rtnVal = 0;
		try {
			
			rtnVal= apageSystemManageService.setCodeUpdate(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/system/adminCodeUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/system/adminCodeUpdt";
		}
		return "forward:/apage/system/adminCodeList.do";
	}
	
	// 분류명 등록
	@RequestMapping(value="/apage/system/codeSeqChk.do")
	public @ResponseBody ModelAndView setcodeSeqReg(@ModelAttribute("vo") apageSystemManageVo vo
												,HttpServletRequest request
												,HttpServletResponse response) throws Exception {
		try {
		
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			int resultInt	= apageSystemManageService.codeSeqChk(vo);
			if(resultInt > 0){
				data.put("ResultCode","1");
				data.put("ResultMsg","중복");
			}else{
				data.put("ResultCode","0");
				data.put("ResultMsg","중복안됨");
			}
				
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
		
}
