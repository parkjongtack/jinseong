package egovframework.apage.board.web;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.apage.board.service.apageBoardManageService;
import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.apage.contest.service.apageContestManageService;
import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.apage.system.service.apageSystemManageService;
import egovframework.client.shop.service.ShopManageService;
import egovframework.client.shop.service.ShopManageVo;
import egovframework.common.core.ResultDataManager;
import egovframework.common.core.SendMiPlatform;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.service.SmsManageService;
import egovframework.common.service.SmsManageVo;
import egovframework.common.utils.CommonUtil;
import egovframework.common.utils.EgovFileMngUtil;
import egovframework.common.utils.PageVo;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class apageBoardManageController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "apageBoardManageService")
	private apageBoardManageService apageBoardManageService;

	@Resource(name = "ShopManageService")
	private ShopManageService ShopManageService;
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name = "CommonUtil")
	private CommonUtil CommonUtil;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	@Resource(name = "FileManageService")
	private FileManageService fileManageService;
	
	@Resource(name = "apageSystemManageService")
	private apageSystemManageService apageSystemManageService;

	@Resource(name = "apageContestManageService")
	private apageContestManageService apageContestManageService;
	
	@Resource(name = "SmsManageService")
	private SmsManageService SmsManageService;
	
	
	//공지사항 리스트
	@RequestMapping(value="/apage/board/adminNoticeList.do")
	public String adminList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("notice");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("noticeList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/board/adminNoticeList";
	}
	
	
	//공지사항 입력페이지
	@RequestMapping(value = "/apage/board/adminNoticeWrite.do")
	public String adminNoticeWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);

		return "/apage/board/adminNoticeWrite";
	}
	
	//공지사항 등록
	@RequestMapping(value="/apage/board/adminNoticeReg.do")
	public String adminNoticeReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "notice"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setBbs_id("notice");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminNoticeWrite";				
		}
		
		return "forward:/apage/board/adminNoticeList.do";
	}
	
	
	// 공지사항 상세
	@RequestMapping(value="/apage/board/adminNoticeDetail.do")
	public String adminNoticeDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> noticeFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("noticeFile", noticeFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("boardView", svo);
		
		return "/apage/board/adminNoticeView";
	}
	
	//공지사항 삭제
	@RequestMapping(value="/apage/board/adminNoticeDelete.do")
	public String adminNoticeDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			result = apageBoardManageService.adminBoardDelete(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "notice";
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
    		return "/apage/board/adminNoticeView";
		}
		return "forward:/apage/board/adminNoticeList.do";
	}
	
	// 공지사항 수정페이지
	@RequestMapping(value="/apage/board/adminNoticeModify.do")
	public String adminNoticeModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> noticeFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("noticeFile", noticeFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("boardView", svo);
		
		
		return "/apage/board/adminNoticeUpdt";
	}
	
	//공지사항 수정
	@RequestMapping(value = "/apage/board/adminNoticeUpdate.do")
	public String adminNoticeUpdate(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "notice"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "notice"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminNoticeUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminNoticeUpdt";
		}
		return "forward:/apage/board/adminNoticeList.do";
	}
	
	//파일 삭제
	@RequestMapping(value="/apage/board/NoticeFileDel.do")
	public String NoticeFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "notice";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		
		//mav.setViewName("redirect:/apage/sys/member");
			
		return this.adminNoticeModify(vo, model, request, response, session);
	}
	
	//제품소개 리스트
	@RequestMapping(value="/apage/board/adminProductList.do")
	public String adminProductList(@ModelAttribute("vo") apageBoardManageVo vo
								 , @ModelAttribute("vo2") ShopManageVo vo2
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo2.setCurrRow(startPageNum);
		vo2.setEndRow(endPageNum);
		
		if(vo2.getBig() == null || vo2.getBig().equals("")) {
			vo2.setBig("1001");
		}
		List<ShopManageVo> list = ShopManageService.selectShopList(vo2);		
		int totCnt = ShopManageService.selectShopListCnt(vo2);
		
		model.addAttribute("productlist", list);
		model.addAttribute("totalNum", totCnt);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
				
		return "/apage/board/adminProductList";
	}
	
	//제품소개 수정페이지
	@RequestMapping(value="/apage/board/adminProductModify.do")
	public String adminProductModify(@ModelAttribute("vo") apageBoardManageVo vo
								 , @ModelAttribute("vo2") ShopManageVo vo2
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		ShopManageVo svo = ShopManageService.selectShopView(vo2);
		if(svo.getContent() != null && !svo.getContent().equals("")) {
			model.addAttribute("ad_image_check", "ad_image_exist");
		}
		
		model.addAttribute("shopView", svo);
		
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("currPage", vo.getCurrRow());
		
		return "/apage/board/adminProductUpdt";
	}
	
	//제품소개 수정
	@RequestMapping(value="/apage/board/adminProductUpdate.do")
	public String adminProductUpdate(@ModelAttribute("vo") apageBoardManageVo vo
								 , @ModelAttribute("vo2") ShopManageVo vo2
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
	
		int rtnVal = 0;
		try {
			String ad_image_exist_check = request.getParameter("content_check");
			
			if(ad_image_exist_check != null && !ad_image_exist_check.equals("") && ad_image_exist_check.equals("ad_image_exist")) {
				rtnVal = ShopManageService.updateShopADImage(vo2);
			}else {
				rtnVal = ShopManageService.insertShopADImage(vo2);
			}
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "등록되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminProductUpdt";				
			}
		}catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminProductUpdt";	
		}
		
		return "forward:/apage/board/adminProductList.do";
		//return "redirect:/apage/board/adminProductList.do";
	}
	
	//이벤트 리스트
	@RequestMapping(value="/apage/board/adminEventList.do")
	public String adminEventList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("event");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("eventList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/board/adminEventList";
	}
	
	
	//이벤트 입력페이지
	@RequestMapping(value = "/apage/board/adminEventWrite.do")
	public String adminEventWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);

		return "/apage/board/adminEventWrite";
	}
	
	//이벤트 등록
	@RequestMapping(value="/apage/board/adminEventReg.do")
	public String adminEventReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "event"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setBbs_id("event");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminEventWrite";				
		}
		
		return "forward:/apage/board/adminEventList.do";
	}
	
	
	//이벤트 상세
	@RequestMapping(value="/apage/board/adminEventDetail.do")
	public String adminEventDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> noticeFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("eventFile", noticeFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("boardView", svo);
		
		return "/apage/board/adminEventView";
	}
	
	//이벤트 삭제
	@RequestMapping(value="/apage/board/adminEventDelete.do")
	public String adminEventDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			result = apageBoardManageService.adminBoardDelete(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "event";
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
    		return "/apage/board/adminEventView";
		}
		return "forward:/apage/board/adminEventList.do";
	}
	
	//이벤트 수정페이지
	@RequestMapping(value="/apage/board/adminEventModify.do")
	public String adminEventModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> noticeFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("eventFile", noticeFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("boardView", svo);
		
		
		return "/apage/board/adminEventUpdt";
	}
	
	//이벤트 수정
	@RequestMapping(value = "/apage/board/adminEventUpdate.do")
	public String adminEventUpdate(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "event"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "event"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminEventUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminEventUpdt";
		}
		return "forward:/apage/board/adminEventList.do";
	}
	
	//이벤트 파일 삭제
	@RequestMapping(value="/apage/board/EventFileDel.do")
	public String EventFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "event";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		
		//mav.setViewName("redirect:/apage/sys/member");
			
		return this.adminEventModify(vo, model, request, response, session);
	}
	
	
	//as게시판 리스트
	@RequestMapping(value="/apage/board/adminAsList.do")
	public String adminAsList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("as");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("asList", list);
		model.addAttribute("totalNum", totCnt);
		model.addAttribute("scType", vo.getScType());
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/board/adminAsList";
	}
	
	//as게시판 입력페이지
	@RequestMapping(value = "/apage/board/adminAsWrite.do")
	public String adminAsWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);

		return "/apage/board/adminAsWrite";
	}
	
	//as게시판 등록
	@RequestMapping(value="/apage/board/adminAsReg.do")
	public String adminAsReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "as"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setBbs_id("as");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminEventWrite";				
		}
		
		return "forward:/apage/board/adminAsList.do";
	}	

	//as 상세
	@RequestMapping(value="/apage/board/adminAsDetail.do")
	public String adminAsDetail(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> asFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("asFile", asFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/apage/board/adminAsUpdt";
	}	

	//as 수정
	@RequestMapping(value = "/apage/board/adminAsUpdate.do")
	public String adminAsUpdate(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "as"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "as"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);			  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminAstUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminAsUpdt";
		}
		return "forward:/apage/board/adminAsList.do";
	}
	
	//as 삭제
	@RequestMapping(value="/apage/board/adminAsDelete.do")
	public String adminAsDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = apageBoardManageService.adminBoardDelete(vo);
			apageBoardManageService.deleteComment(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "as";
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
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/board/adminEventView";
		}
		return "redirect:/apage/board/adminAsList.do";
	}
	
	//as 파일 삭제
	@RequestMapping(value="/apage/board/AsFileDel.do")
	public String AsFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "as";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		return this.adminAsDetail(vo, model, request, response, session);
	}	

	
	////////////
	
	
	
	//고객상담 게시판 리스트
	@RequestMapping(value="/apage/board/adminConsultList.do")
	public String adminConsultList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("consult");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("consultList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/board/adminConsultList";
	}
	
	//고객상담 게시판 입력페이지
	@RequestMapping(value = "/apage/board/adminConsultWrite.do")
	public String adminConsultWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);

		return "/apage/board/adminConsultWrite";
	}
	
	//고객상담 게시판 등록
	@RequestMapping(value="/apage/board/adminConsultReg.do")
	public String adminConsultReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "consult"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setBbs_id("consult");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminConsultWrite";				
		}
		
		return "forward:/apage/board/adminConsultList.do";
	}	

	//고객상담 상세
	@RequestMapping(value="/apage/board/adminConsultDetail.do")
	public String adminConsultDetail(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> consultFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("consultFile", consultFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("boardView", svo);
		
		return "/apage/board/adminConsultUpdt";
	}	

	//as 수정
	@RequestMapping(value = "/apage/board/adminConsultUpdate.do")
	public String adminConsultUpdate(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "consult"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "consult"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);			  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminConsultUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminConsultUpdt";
		}
		return "forward:/apage/board/adminConsultList.do";
	}
	
	//고객상담 삭제
	@RequestMapping(value="/apage/board/adminConsultDelete.do")
	public String adminConsultDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = apageBoardManageService.adminBoardDelete(vo);
			apageBoardManageService.deleteComment(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "consult";
			FileManageVo fileVO = new FileManageVo();
			if(vo.getAtch_file_id() != null && !vo.getAtch_file_id().equals("")){
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
		}catch(Exception e){			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/board/adminConsultUpdt";
		}
		return "redirect:/apage/board/adminConsultList.do";
	}
	
	//고객상담 파일 삭제
	@RequestMapping(value="/apage/board/ConsultFileDel.do")
	public String ConsultFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "consult";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		return this.adminConsultDetail(vo, model, request, response, session);
	}	
	
	
	
	
	
	
	
	/**
	 * 
	 * 코멘트 리스트JSON
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/board/commentListJson.do")
	@ResponseBody
	public ModelAndView commentListJson(@ModelAttribute("aForm") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
			try{
				//ResultDataManager 여러 LIST를 별도의 LIST2에 추가
				ResultDataManager rm = new ResultDataManager();				
				List<apageBoardManageVo> list = apageBoardManageService.selectBoardComment(vo);		
		
				if(list.size() > 0){
					for(int i = 0 ;i < list.size();i++) {
						list.get(i).setContent(list.get(i).getContent().replaceAll("\n", "</br>"));
					}
				}
				rm.setData(apageBoardManageVo.class, list);
				//SendMiPlatform.SendData 에서 LIST2의 값을 JSON으로 변경 및 출력
				return SendMiPlatform.SendData(response, rm);
				}catch(Exception e){
				return SendMiPlatform.ErrorData(response, e.getMessage());
				}	
	}
	
	
	//코멘트 작성
	@RequestMapping(value = "/apage/board/insertBoardComment.do")
	public void insertBoardComment(@ModelAttribute("aForm") apageBoardManageVo vo,
					   			HttpServletRequest request, 
					   			HttpServletResponse response, 
					   			HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;
		
		if(vo.getReg_id() == "" || vo.getReg_id() == null){
			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			vo.setReg_id(mv.getMber_id());	
		}
		
		rtnVal = apageBoardManageService.insertBoardComment(vo);		

		if(rtnVal > 0){
			vo.setNtt_id(vo.getParent_seq());
			vo.setAs_status("C");
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			rval = true;
		}		

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print(rval);
		writer.flush();
		writer.close();	
	}
	
	//코멘트 수정
	@RequestMapping(value = "/apage/board/updateBoardComment.do")
	public void updateBoardComment(@ModelAttribute("aForm") apageBoardManageVo vo,
					   			HttpServletRequest request, 
					   			HttpServletResponse response, 
					   			HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;

		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		vo.setUpdt_id(mv.getMber_id());
		
		rtnVal = apageBoardManageService.updateBoardComment(vo);

		if(rtnVal > 0){
			rval = true;
		}		

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print(rval);
		writer.flush();
		writer.close();	
	}
	
	//코멘트 삭제
	@RequestMapping(value = "/apage/board/deleteBoardComment.do")
	public void deleteBoardComment(@ModelAttribute("aForm") apageBoardManageVo vo,
					   			HttpServletRequest request, 
					   			HttpServletResponse response, 
					   			HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;
		
		rtnVal = apageBoardManageService.deleteBoardComment(vo);
		
		if(rtnVal > 0){
			rval = true;
		}		

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print(rval);
		writer.flush();
		writer.close();	
	}
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	//staff 게시판 리스트
	@RequestMapping(value="/apage/board/adminStaffList.do")
	public String adminStaffList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		List<apageBoardManageVo> list = apageBoardManageService.selectadminStaffList(vo);		
		int totCnt = apageBoardManageService.selectadminStaffListCnt(vo);
		
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		for(int i = 0;i < list.size();i++) {
			String atch_file_id = list.get(i).getAtch_file_id();
			if(atch_file_id != null && atch_file_id != ""){
				fvo.setAtch_file_id(atch_file_id.substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
				
				if(imgFile.size() != 0) {
					list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
				}else {
					list.get(i).setAtch_file_id("");
				}
			}
		}
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());		
		model.addAttribute("staffList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/board/adminStaffList";
	}
	
	//staff 입력페이지
	@RequestMapping(value = "/apage/board/adminStaffWrite.do")
	public String adminStaffWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);
	
		return "/apage/board/adminStaffWrite";
	}
	
	//staff 등록
	@RequestMapping(value="/apage/board/adminStaffReg.do")
	public String adminStaffReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf3(request,files, "FILE_", "", 0, "staff"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		rtnVal= apageBoardManageService.insertAdminStaff(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminStaffWrite";				
		}
		
		return "forward:/apage/board/adminStaffList.do";
	}
	
	//staff 수정페이지
	@RequestMapping(value="/apage/board/adminStaffModify.do")
	public String adminStaffModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		//조회수 증가
		apageBoardManageService.UpdateStaffCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminStaffView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("I");
			List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("imgFile", imgFile);
		}
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("F");
			List<FileManageVo> addFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("addFile", addFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("staffView", svo);
		
		
		return "/apage/board/adminStaffUpdt";
	}
	
	//staff 수정
	@RequestMapping(value = "/apage/board/adminStaffUpdt.do")
	public String adminStaffUpdt(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					cnt += 1;
					result = fileUtil.parseFileInf3(request,files, "", atch_file_id, cnt, "staff"); 	
				}
				else{
					result = fileUtil.parseFileInf3(request,files, "FILE_", atch_file_id, cnt, "staff"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);			  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminStaff(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminStaffUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminStaffUpdt";
		}
		return "forward:/apage/board/adminStaffList.do";
	}
	
	//staff 삭제
	@RequestMapping(value="/apage/board/adminStaffDelete.do")
	public String adminStaffDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = apageBoardManageService.deleteAdminStaff(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "staff";
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
		}catch(Exception e){			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/board/adminStaffUpdt";
		}
		return "redirect:/apage/board/adminStaffList.do";
	}
	
	//staff 파일 삭제
	@RequestMapping(value="/apage/board/StaffFileDel.do")
	public String StaffFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "staff";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		return this.adminStaffModify(vo, model, request, response, session);
	}	
	
	
	
	
	

	//레인정비기계관리 리스트
	@RequestMapping(value="/apage/board/adminLaneMachines.do")
	public String adminLaneMachine(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("lane");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		for(int i = 0;i < list.size();i++) {
			String atch_file_id = list.get(i).getAtch_file_id();
			if(atch_file_id != null && atch_file_id != ""){
				fvo.setAtch_file_id(atch_file_id.substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
				
				if(imgFile.size() != 0) {
					list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
				}else {
					list.get(i).setAtch_file_id("");
				}
			}
		}
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());		
		model.addAttribute("laneList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/board/adminLaneMachine";
	}
	
	//레인정비기계관리 입력페이지
	@RequestMapping(value = "/apage/board/adminLaneMachinesWrite.do")
	public String adminLaneMachineWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);
	
		return "/apage/board/adminLaneMachineWrite";
	}
	
	//레인정비기계관리 등록
	@RequestMapping(value="/apage/board/adminLaneMachinesReg.do")
	public String adminLaneMachinesReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf3(request,files, "FILE_", "", 0, "lane"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		vo.setBbs_id("lane");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminLaneMachineWrite";				
		}
		
		return "forward:/apage/board/adminLaneMachines.do";
	}
	
	//레인정비기계관리 수정페이지
	@RequestMapping(value="/apage/board/adminLaneMachinesModify.do")
	public String adminLaneMachinesModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("I");
			List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("imgFile", imgFile);
		}
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("F");
			List<FileManageVo> addFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("addFile", addFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("laneView", svo);
		
		
		return "/apage/board/adminLaneMachineUpdt";
	}
	
	//레인정비기계관리 수정
	@RequestMapping(value = "/apage/board/adminLaneMachinesUpdt.do")
	public String adminLaneMachinesUpdt(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "lane"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "lane"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminLaneMachineUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminLaneMachineUpdt";
		}
		return "forward:/apage/board/adminLaneMachines.do";
	}
	
	//레인정비기계관리 삭제
	@RequestMapping(value="/apage/board/adminLaneMachinesDelete.do")
	public String adminLaneMachinesDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = apageBoardManageService.adminBoardDelete(vo);
		
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "lane";
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
		}catch(Exception e){			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/board/adminLaneMachineUpdt";
		}
		return "redirect:/apage/board/adminLaneMachines.do";
	}
	
	//레인정비기계관리 파일 삭제
	@RequestMapping(value="/apage/board/LaneMachinesFileDel.do")
	public String LaneMachinesFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "lane";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
		List<FileManageVo> fvo2		= fileManageService.getFileAttachList(fileVO);
		if(fvo2.size() <= 0){
			vo.setAtch_file_id(vo.getAtch_file_id2().substring(0, 22));
			apageBoardManageService.setBoardAttachUpdt2(vo);
		}
		
		return this.adminLaneMachinesModify(vo, model, request, response, session);
	}
	
	
	
	//레인정비 소모품 리스트
	@RequestMapping(value="/apage/board/adminLaneSupplies.do")
	public String adminLaneSupplies(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("lanesupp");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		for(int i = 0;i < list.size();i++) {
			String atch_file_id = list.get(i).getAtch_file_id();
			if(atch_file_id != null && atch_file_id != ""){
				fvo.setAtch_file_id(atch_file_id.substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
				
				if(imgFile.size() != 0) {
					list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
				}else {
					list.get(i).setAtch_file_id("");
				}
			}
		}
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());		
		model.addAttribute("laneList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/board/adminLaneSupplies";
	}
	
	//레인정비 소모품 입력페이지
	@RequestMapping(value = "/apage/board/adminLaneSuppliesWrite.do")
	public String adminLaneSuppliesWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);
	
		return "/apage/board/adminLaneSuppliesWrite";
	}
	
	//레인정비 소모품 등록
	@RequestMapping(value="/apage/board/adminLaneSuppliesReg.do")
	public String adminLaneSuppliesReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf3(request,files, "FILE_", "", 0, "lanesupp"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		vo.setBbs_id("lanesupp");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminLaneSuppliesWrite";				
		}
		
		return "forward:/apage/board/adminLaneSupplies.do";
	}
	
	//레인정비 소모품 수정페이지
	@RequestMapping(value="/apage/board/adminLaneSuppliesModify.do")
	public String adminLaneSuppliesModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("I");
			List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("imgFile", imgFile);
		}
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("F");
			List<FileManageVo> addFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("addFile", addFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("laneView", svo);
		
		
		return "/apage/board/adminLaneSuppliesUpdt";
	}
	
	//레인정비 소모품 수정
	@RequestMapping(value = "/apage/board/adminLaneSuppliesUpdt.do")
	public String adminLaneSuppliesUpdt(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					cnt += 1;
					result = fileUtil.parseFileInf3(request,files, "", atch_file_id, cnt, "lanesupp"); 	
				}
				else{
					result = fileUtil.parseFileInf3(request,files, "FILE_", atch_file_id, cnt, "lanesupp"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);			  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminLaneSuppliesUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminLaneMachineUpdt";
		}
		return "forward:/apage/board/adminLaneSupplies.do";
	}
	
	//레인정비 소모품 삭제
	@RequestMapping(value="/apage/board/adminLaneSuppliesDelete.do")
	public String adminLaneSuppliesDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = apageBoardManageService.adminBoardDelete(vo);
		
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "lanesupp";
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
		}catch(Exception e){			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/board/adminLaneMachineUpdt";
		}
		return "redirect:/apage/board/adminLaneSupplies.do";
	}
	
	//레인정비 소모품 파일 삭제
	@RequestMapping(value="/apage/board/LaneSuppliesFileDel.do")
	public String LaneSuppliesFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "lanesupp";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
		List<FileManageVo> fvo2		= fileManageService.getFileAttachList(fileVO);
		if(fvo2.size() <= 0){
			vo.setAtch_file_id(vo.getAtch_file_id2().substring(0, 22));
			apageBoardManageService.setBoardAttachUpdt2(vo);
		}
		
		
		return this.adminLaneSuppliesModify(vo, model, request, response, session);
	}
	
	
	
	
	//훈련 도구 리스트
		@RequestMapping(value="/apage/board/adminTrainingList.do")
		public String adminTrainingList(@ModelAttribute("vo") apageBoardManageVo vo
										 , HttpServletRequest request
										 , ModelMap model
										 , HttpServletResponse response
										 , HttpSession session) throws Exception {
			
			PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
			
			int startPageNum			= CommonUtil.getStartNum(request, pVo);
			int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
			vo.setCurrRow(startPageNum);
			vo.setEndRow(endPageNum);
			
			//게시판 구분값
			vo.setBbs_id("training");
			List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
			int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
			
			//첨부파일(대표이미지) 가져오기
			FileManageVo fvo			= new FileManageVo();
			
			for(int i = 0;i < list.size();i++) {
				String atch_file_id = list.get(i).getAtch_file_id();
				if(atch_file_id != null && atch_file_id != ""){
					fvo.setAtch_file_id(atch_file_id.substring(0, 22));
					fvo.setFile_gu("I");
					List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
					
					if(imgFile.size() != 0) {
						list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
					}else {
						list.get(i).setAtch_file_id("");
					}
				}
			}
			
			//검색입력값
			model.addAttribute("Srch_input", vo.getSrch_input());		
			model.addAttribute("laneList", list);
			model.addAttribute("totalNum", totCnt);
			
			pVo.setTotalRowCnt(totCnt);
			model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
			model.addAttribute("currPage", pVo.getCurrPage());
			return "/apage/board/adminTrainingList";
		}
		
		
		//훈련 도구 입력페이지
		@RequestMapping(value = "/apage/board/adminTrainingWrite.do")
		public String adminTrainingWrite(@ModelAttribute("vo") apageBoardManageVo vo,
									HttpServletRequest request, ModelMap model,
									HttpServletResponse response, HttpSession session) throws Exception {		
			
			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			model.addAttribute("memberinfo",mv);
		
			return "/apage/board/adminTrainingWrite";
		}
		
		
		//훈련 도구 등록
		@RequestMapping(value="/apage/board/adminTrainingReg.do")
		public String adminTrainingReg(final MultipartHttpServletRequest multiRequest
								, @ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session) throws Exception {
			
			int rtnVal = 0;
			String msg = "";
			List<FileManageVo> result = null;
			Map<String, MultipartFile> files = multiRequest.getFileMap();		
			if(!files.isEmpty()){
	  			result = fileUtil.parseFileInf3(request,files, "FILE_", "", 0, "lanesupp"); 	
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			vo.setBbs_id("training");
			rtnVal= apageBoardManageService.insertAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "등록되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminTrainingWrite";				
			}
			
			return "forward:/apage/board/adminTrainingList.do";
		}
		
		
		//훈련 도구 수정페이지
		@RequestMapping(value="/apage/board/adminTrainingModify.do")
		public String adminTrainingModify(@ModelAttribute("vo") apageBoardManageVo vo
										, ModelMap model
										, HttpServletRequest request
										, HttpServletResponse response
										, HttpSession session) throws Exception {
		
			//조회수 증가
			apageBoardManageService.UpdateCnt(vo);
			
			apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
			
			boolean isLogin = false;
			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			
			FileManageVo fvo			= new FileManageVo();
			
			//첨부파일 리스트 가져오기
			if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
				fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
				model.addAttribute("imgFile", imgFile);
			}
			
			//첨부파일 리스트 가져오기
			if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
				fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
				fvo.setFile_gu("F");
				List<FileManageVo> addFile	= fileManageService.getFileAttachList(fvo);
				model.addAttribute("addFile", addFile);
			}
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("memberinfo",mv);
			model.addAttribute("laneView", svo);
			
			
			return "/apage/board/adminTrainingUpdt";
		}
		
		
		//훈련 도구 수정
		@RequestMapping(value = "/apage/board/adminTrainingUpdt.do")
		public String adminTrainingUpdt(final MultipartHttpServletRequest multiRequest,
									@ModelAttribute("vo") apageBoardManageVo vo,
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
						cnt += 1;
						result = fileUtil.parseFileInf3(request,files, "", atch_file_id, cnt, "lanesupp"); 	
					}
					else{
						result = fileUtil.parseFileInf3(request,files, "FILE_", atch_file_id, cnt, "lanesupp"); 	
					}
		  			if(result.size() != 0){
			  			fileManageService.insertFileInfs2(result);			  			
			  			String file_id = result.get(0).atch_file_id;			
			  			vo.setAtch_file_id(file_id.substring(0, 22));
		  			}
				}	
				rtnVal= apageBoardManageService.updateAdminBoard(vo);
				
				if(rtnVal > 0) {
					model.addAttribute("msg", "수정되었습니다.");	
				} else {
					model.addAttribute("msg", "insert.fail");
					return "/apage/board/adminTrainingUpdt";				
				}
				
			} catch (Exception e) {
				e.printStackTrace();
	    		model.addAttribute("msg", "insert.fail");
	    		return "/apage/board/adminTrainingUpdt";
			}
			return "forward:/apage/board/adminTrainingList.do";
		}
		
		
		//훈련 도구 삭제
		@RequestMapping(value="/apage/board/adminTrainingListDelete.do")
		public String adminTrainingListDelete(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
					
		
			try{	
				int result = apageBoardManageService.adminBoardDelete(vo);
			
				String real =  propertyService.getString("Globals.fileStorePath");
				String Path = "training";
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
			}catch(Exception e){			
				e.printStackTrace();
	    		model.addAttribute("msg", "삭제되지 않았습니다.");
	    		return "/apage/board/adminTrainingUpdt";
			}
			return "redirect:/apage/board/adminTrainingList.do";
		}
	
		
		//훈련 장비 파일 삭제
		@RequestMapping(value="/apage/board/adminTrainingListFileDel.do")
		public String adminTrainingListFileDel(@ModelAttribute("vo") apageBoardManageVo vo
										, ModelMap model
										, HttpServletRequest request
										, HttpServletResponse response
										, HttpSession session) throws Exception {
			
			//ModelAndView mav			= new ModelAndView("member");
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "training";
			FileManageVo fileVO = new FileManageVo();
			fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
			
			fileVO.setAtch_file_id(vo.getAtch_file_id2());
			List<FileManageVo> fvo2		= fileManageService.getFileAttachList(fileVO);
			if(fvo2.size() <= 0){
				vo.setAtch_file_id(vo.getAtch_file_id2().substring(0, 22));
				apageBoardManageService.setBoardAttachUpdt2(vo);
			}
			
			
			return this.adminTrainingModify(vo, model, request, response, session);
		}
	

	
	//전국볼링장 리스트
	@RequestMapping(value="/apage/board/adminCenterList.do")
	public String adminCenterList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("center");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("centerList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/board/adminCenterList";
	}
	
	
	//전국볼링장 입력페이지
	@RequestMapping(value = "/apage/board/adminCenterWrite.do")
	public String adminCenterWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);

		return "/apage/board/adminCenterWrite";
	}
	
	//전국볼링장 등록
	@RequestMapping(value="/apage/board/adminCenterReg.do")
	public String adminCenterReg(@ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;

		vo.setBbs_id("center");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "insert.success");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminCenterWrite";				
		}
		
		return "/apage/board/adminCenterWrite";
	}
	
	
	//전국볼링장 수정페이지
	@RequestMapping(value="/apage/board/adminCenterModify.do")
	public String adminCenterModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		

		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
				
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("centerView", svo);		
		
		return "/apage/board/adminCenterUpdt";
	}
	
	
	//전국볼링장 수정
	@RequestMapping(value="/apage/board/adminCenterUpdt.do")
	public String adminCenterUpdt(@ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;

		rtnVal= apageBoardManageService.updateAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "insert.success");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminCenterUpdt";				
		}
		
		return "/apage/board/adminCenterUpdt";
	}
	
	
	//전국볼링장 삭제
	@RequestMapping(value="/apage/board/adminCenterDel.do")
	public String adminCenterDel(@ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;

		rtnVal= apageBoardManageService.adminBoardDelete(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "delete");	
		} else {
			model.addAttribute("msg", "fail");
			return "/apage/board/adminCenterUpdt";				
		}
		
		return "/apage/board/adminCenterUpdt";
	}
	
	
	/*대회관리*/
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	//대회안내 리스트
	@RequestMapping(value="/apage/contest/adminContestList.do")
	public String adminContestList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("contest");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/contest/adminContestList";
	}
	
	
	//대회안내 입력페이지
	@RequestMapping(value = "/apage/contest/adminContestWrite.do")
	public String adminContestWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);

		return "/apage/contest/adminContestWrite";
	}
	
	//대회안내 등록
	@RequestMapping(value="/apage/contest/adminContestReg.do")
	public String adminContestReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "contest"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setBbs_id("contest");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminContestWrite";				
		}
		
		return "forward:/apage/contest/adminContestList.do";
	}
	
	
	// 대회안내 상세
	@RequestMapping(value="/apage/contest/adminContestDetail.do")
	public String adminContestDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/contest/adminContestView";
	}
	
	//대회안내 삭제
	@RequestMapping(value="/apage/contest/adminContestDelete.do")
	public String adminContestDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			result = apageBoardManageService.adminBoardDelete(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "contest";
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
    		return "/apage/contest/adminContestView";
		}
		return "forward:/apage/contest/adminContestList.do";
	}
	
	// 대회안내 수정페이지
	@RequestMapping(value="/apage/contest/adminContestModify.do")
	public String adminContestModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("contestView", svo);
		
		
		return "/apage/contest/adminContestUpdt";
	}
	
	//대회안내 수정
	@RequestMapping(value = "/apage/contest/adminContestUpdate.do")
	public String adminContestUpdate(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "contest"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "contest"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/contest/adminContestUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/contest/adminContestUpdt";
		}
		return "forward:/apage/contest/adminContestList.do";
	}
	
	//파일 삭제
	@RequestMapping(value="/apage/contest/ContestFileDel.do")
	public String ContestFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "contest";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		
		return this.adminContestModify(vo, model, request, response, session);
	}	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////대회신청데이터 관련////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	//대회신청 게시판 리스트
	@RequestMapping(value="/apage/contest/adminContestMngList.do")
	public String adminContestMngList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow(endPageNum);
		
/*		
		List<apageBoardManageVo> list = apageBoardManageService.selectAdminContestList(vo);		
		int totCnt = apageBoardManageService.selectAdminContestListCnt(vo);
*/		
		List<apageBoardManageVo> list = apageBoardManageService.lb_selectAdminContestList(vo);		
		int totCnt = apageBoardManageService.lb_selectAdminContestListCnt(vo);
		
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());		
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/contest/contestMngList";
	}
	
	// 대회 상세
	@RequestMapping(value="/apage/contest/adminContestMngDetail.do")
	public String adminContestMngDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
				
		apageBoardManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		if(svo != null && svo.getCt_group() != null){
			vo.setCt_seq_arr(svo.getCt_group().split(","));
			List<apageBoardManageVo> ct_group_list = apageBoardManageService.apageSelectExpectContestGroupList(vo);
			model.addAttribute("ct_group_list", ct_group_list);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/contest/contestMngView";
	}
	
	//대회신청 입력페이지
	@RequestMapping(value = "/apage/contest/adminContestMngWrite.do")
	public String adminContestMngWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		List<apageBoardManageVo> contest_list = apageBoardManageService.apageSelectExpectContestList(vo);
		model.addAttribute("contest_list", contest_list);
		
		model.addAttribute("memberinfo",mv);
	
		return "/apage/contest/contestMngWrite";
	}
	
	//대회 데이터 등록
	@RequestMapping(value="/apage/contest/adminContestMngReg.do")
	public String adminContestMngReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "contestMng"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		rtnVal= apageBoardManageService.insertAdminContest(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminStaffWrite";				
		}
		
		return "forward:/apage/contest/adminContestMngList.do";
	}
	
	//대회 데이터 수정페이지
	@RequestMapping(value="/apage/contest/adminContestMngModify.do")
	public String adminContestMngModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		
		apageBoardManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
		

		List<apageBoardManageVo> contest_list = apageBoardManageService.apageSelectExpectContestList(vo);
		model.addAttribute("contest_list", contest_list);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		if(svo != null && svo.getCt_group() != null){
			vo.setCt_seq_arr(svo.getCt_group().split(","));
			List<apageBoardManageVo> ct_group_list = apageBoardManageService.apageSelectExpectContestGroupList(vo);
			model.addAttribute("ct_group_list", ct_group_list);
		}
		
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("contestView", svo);
		
		
		return "/apage/contest/contestMngUpdt";
	}
	
	//대회 데이터 수정
	@RequestMapping(value = "/apage/contest/adminContestMngUpdt.do")
	public String adminContestMngUpdt(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					cnt += 1;
					result = fileUtil.parseFileInf3(request,files, "", atch_file_id, cnt, "contestMng"); 	
				}
				else{
					result = fileUtil.parseFileInf3(request,files, "FILE_", atch_file_id, cnt, "contestMng"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);			  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminContest(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/contest/contestMngUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/contest/contestMngUpdt";
		}
		return "forward:/apage/contest/adminContestMngList.do";
	}
	
	//대회 데이터 삭제
	@RequestMapping(value="/apage/contest/adminContestMngDelete.do")
	public String adminContestMngDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = apageBoardManageService.deleteAdminContest(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "contestMng";
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
		}catch(Exception e){			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/contest/contestMngUpdt";
		}
		return "redirect:/apage/contest/adminContestMngList.do";
	}
	
	//대회데이터 첨부 파일 삭제
	@RequestMapping(value="/apage/contest/ContestMngFileDel.do")
	public String ContestMngFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		int fileCnt = Integer.parseInt(request.getParameter("FileCnt"));
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "contestMng";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		if(fileCnt == 1){
			apageBoardManageService.updateAdminContestFile(vo);
		}
		
		return this.adminContestMngModify(vo, model, request, response, session);
	}		
	
	
	/**
	 * 
	 * 대회신청자 리스트JSON
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/createRandomLane.do")
	@ResponseBody
	public ModelAndView createRandomLane(@ModelAttribute("vo") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		List<apageBoardManageVo> list = null;
				
		try{
	
			ResultDataManager rm = new ResultDataManager();		
			vo.setCt_seq(Integer.parseInt(request.getParameter("ct_seq")));
			apageBoardManageVo contestVo = apageBoardManageService.lb_getAdminContestView(vo);
			int laneCnt = Integer.parseInt(request.getParameter("laneCnt"));
				for(int i=0;i<4;i++){
					
					list = new ArrayList<apageBoardManageVo>();
					
					vo.setPart(String.valueOf(i+1));			
					list = apageBoardManageService.createRandomLane(vo);	
					List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
					
					//레인번호 따기
					/* 2019-01-03 주석처리 
					int a = 1;
					int b = 1;
					int laneCnt_final = laneCnt;
					if(vo.getPrepare_yn() != null && vo.getPrepare_yn().equals("Y")){	//예비레인일 경우 
						b = 3;
						laneCnt_final += 2;
					}

					for(int j=0; j<list.size();j++){
						if(b<=laneCnt_final){
							list.get(j).setLane(a+"-"+b);
							b++;
						}else{
							a++;
							if(vo.getPrepare_yn() != null && vo.getPrepare_yn().equals("Y")){	//예비레인일 경우 
								b = 3;
							}else{
								b = 1;
							}
							
							list.get(j).setLane(a+"-"+b);
							b++;
						}
						if(list.get(j) != null && list.get(j).getTelno() != null && list.get(j).getTelno().length() >= 8){
							String tel_no_arr[] = list.get(j).getTelno().split("-");
							StringBuffer sb = new StringBuffer();
							for(int z = 0; z < tel_no_arr.length; z++){
								if(z == 1){
									sb.append("-****-");
								}else{
									sb.append(tel_no_arr[z]);
								}
							}
							list.get(j).setTelno(sb.toString());
						}
						int result = apageBoardManageService.updateAppRandomLane(list.get(j));	
					}
					
					*/
					int a = 1;
					int b = 1;
					int laneCnt_final = laneCnt;
					if(vo.getPrepare_yn() != null && vo.getPrepare_yn().equals("Y")){	//예비레인일 경우 
						a = 3;
					}
					
					for(int j=0; j<list.size();j++){
						if(b<=laneCnt_final){
							list.get(j).setLane(a+"-"+b);
							b++;
						}else{
							a++;
							b=1;
							list.get(j).setLane(a+"-"+b);
							b++;
						}
						/*
						SmsManageVo smvo = new SmsManageVo();
						smvo.setSubject("[진승무역]");
						StringBuffer sb2 = new StringBuffer();
						sb2.append(list.get(j).getJoin_name()+"님\n");
						sb2.append(contestVo.getCt_sbj()+"/"+list.get(j).getPart()+"조/"+list.get(j).getLane()+"레인 참가 확정\n");
						sb2.append("-계좌정보-\n");
						sb2.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
						//sb2.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
						sb2.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
						sb2.append("(반드시 참가자명으로 입금 바랍니다.)");
						smvo.setMsg(sb2.toString());
						smvo.setPhone(list.get(j).getTelno());
						smsList.add(smvo);
						*/
						if(list.get(j) != null && list.get(j).getTelno() != null && list.get(j).getTelno().length() >= 8){
							String tel_no_arr[] = list.get(j).getTelno().split("-");
							StringBuffer sb = new StringBuffer();
							for(int z = 0; z < tel_no_arr.length; z++){
								if(z == 1){
									sb.append("-****-");
								}else{
									sb.append(tel_no_arr[z]);
								}
							}
							list.get(j).setTelno(sb.toString());
						}
					}
					int result = apageBoardManageService.updateAppRandomLane(list);	
					//SmsManageService.insertMmsBatch(smsList);
					
					
					rm.setData(apageBoardManageVo.class, list);
			}
	
			return SendMiPlatform.SendData(response, rm);
			
		}catch(Exception e){
			
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
	}
	
	//대회결과 리스트
	@RequestMapping(value="/apage/contest/adminContestRstList.do")
	public String adminContestRstList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("contestRst");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		for(int i = 0;i < list.size();i++) {
			String atch_file_id = list.get(i).getAtch_file_id();
			if(atch_file_id != null && atch_file_id != ""){
				fvo.setAtch_file_id(atch_file_id.substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
				
				if(imgFile.size() != 0) {
					list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
				}else {
					list.get(i).setAtch_file_id("");
				}
			}
		}
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());		
		model.addAttribute("contestRstList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/contest/contestRstList";
	}
	
	//대회결과 상세
	@RequestMapping(value="/apage/contest/adminContestRstDetail.do")
	public String adminContestRstDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("I");
			List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("imgFile", imgFile);
		}
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("F");
			List<FileManageVo> addFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("addFile", addFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("contestRstView", svo);
		
		return "/apage/contest/contestRstView";
	}
		
	//대회결과 입력페이지
	@RequestMapping(value = "/apage/contest/adminContestRstWrite.do")
	public String adminContestRstWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);
	
		return "/apage/contest/contestRstWrite";
	}
	
	//대회결과 등록
	@RequestMapping(value="/apage/contest/adminContestRstReg.do")
	public String adminContestRstReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf3(request,files, "FILE_", "", 0, "contestRst"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		vo.setBbs_id("contestRst");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "등록에 실패하였습니다.");
			return "/apage/contest/contestRstWrite";				
		}
		
		return "/apage/contest/contestRstWrite";
	}
	
	//대회결과 수정페이지
	@RequestMapping(value="/apage/contest/adminContestRstModify.do")
	public String adminContestRstModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("I");
			List<FileManageVo> imgFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("imgFile", imgFile);
		}
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			fvo.setFile_gu("F");
			List<FileManageVo> addFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("addFile", addFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("contestRstView", svo);
		
		
		return "/apage/contest/contestRstUpdt";
	}
	
	//대회결과 수정
	@RequestMapping(value = "/apage/contest/adminContestRstUpdt.do")
	public String adminContestRstUpdt(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					cnt += 1;
					result = fileUtil.parseFileInf3(request,files, "", atch_file_id, cnt, "contestRst"); 	
				}
				else{
					result = fileUtil.parseFileInf3(request,files, "FILE_", atch_file_id, cnt, "contestRst"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);			  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/contest/contestRstUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminLaneMachineUpdt";
		}
		return "/apage/contest/contestRstUpdt";
	}
	
	//대회결과 삭제
	@RequestMapping(value="/apage/contest/adminContestRstDelete.do")
	public String adminContestRstDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = apageBoardManageService.adminBoardDelete(vo);
			
			if(result > 0){
				model.addAttribute("msg", "삭제되었습니다.");
			}
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "contestRst";
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
		}catch(Exception e){			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/contest/contestRstView";
		}
		return "/apage/contest/contestRstView";
	}
	
	//대회결과 파일 삭제
	@RequestMapping(value="/apage/contest/contestRstFileDel.do")
	public String contestRstFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {

		int fileCnt1 = Integer.parseInt(request.getParameter("FileCnt"));
		int fileCnt2 = Integer.parseInt(request.getParameter("FileCnt2"));
		int fileCnt = fileCnt1+ fileCnt2;
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "contestRst";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		
		if(fileCnt == 1){
			vo.setAtch_file_id(vo.getAtch_file_id());
			apageBoardManageService.setBoardAttachUpdt2(vo);
		}
		return this.adminContestRstModify(vo, model, request, response, session);
	}	
	
	/**
	 * 
	 * 대회신청자 리스트JSON
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/contestAppListJson.do")
	@ResponseBody
	public ModelAndView contestAppListJson(@ModelAttribute("vo") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
	try{

		ResultDataManager rm = new ResultDataManager();				
		List<apageBoardManageVo> list = apageBoardManageService.selectAdminContestAppList(vo);								
		
		rm.setData(apageBoardManageVo.class, list);
		//SendMiPlatform.SendData 에서 LIST2의 값을 JSON으로 변경 및 출력
		return SendMiPlatform.SendData(response, rm);
		}catch(Exception e){
			
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
	}
	
	/**
	 * 
	 * 대회신청자 정보수정
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/apage/contest/updateContestApp.do")
	public void updateContestApp(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, 
								HttpServletResponse response, 
								HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		apageBoardManageVo contestVo = apageBoardManageService.lb_getAdminContestView(vo);
		vo.setUpdt_id(mv.getMber_id());
		if(vo.getStatus().equals("0003") && !contestVo.getCt_process().equals("E")){
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			writer.print(rval);
			writer.flush();
			writer.close();	
		}else{
			rtnVal = apageBoardManageService.updateAdminContestApp(vo);
			
			//수정처리 후 상태가 취소 라면 수정 MMS발송
			if(rtnVal > 0 && vo.getStatus().equals("0003")){
				apageBoardManageVo cancelVo = apageBoardManageService.apageGetContestInfoDetailOfEachMember(vo);
				if(cancelVo.getStatus().equals("0003")){
					
					SmsManageVo smv = new SmsManageVo();
					smv.setSubject("[진승무역]");
					smv.setPhone(vo.getTelno());
					StringBuffer sb = new StringBuffer();
					
					// ETC INFO 
					smv.setEtc1(Integer.toString(contestVo.getCt_seq()));		//대회 고유번호
					smv.setEtc2(cancelVo.getPart());							//신청 조
					smv.setEtc3(Integer.toString(cancelVo.getApp_seq()));		//신청 고유번호
					smv.setEtc5("auto"); 										//전체 발송 플래그
					smv.setEtc6(cancelVo.getReg_id());							//신청자 ID
					smv.setEtc7(cancelVo.getJoin_name());						//신청자 명
					smv.setEtc8(cancelVo.getBirth());							//신청자 생년월일
					smv.setEtc9("0003");										//신청취소
					
					if(cancelVo.getPay_flag().equals("Y")){
						sb.append(cancelVo.getJoin_name()+"님\n");
						sb.append(contestVo.getCt_sbj() +" / " + cancelVo.getPart()+"조"+" / 취소완료\n");
						sb.append("입금사항 확인 후 기입하신 환불계좌로\n");
						sb.append(contestVo.getCt_price()+" 환불 해드리겠습니다.");
						smv.setMsg(sb.toString());
						SmsManageService.insertMmsList(smv);
					}else{
						sb.append(cancelVo.getJoin_name()+"님\n");
						sb.append(contestVo.getCt_sbj() +" / " + cancelVo.getPart()+"조 / 취소완료");
						smv.setMsg(sb.toString());
						SmsManageService.insertMmsList(smv);
					}
				}
				
			}
			if(vo.getOrigin_status().equals("0004") && !vo.getStatus().equals("0004")){		//선정 ==> 선정이 아닐때 대기자 선정 처리
				apageBoardManageVo waitVo = apageBoardManageService.apageGetCtPartWaitingInfo(vo);
				int result = apageBoardManageService.updateWaitingToSelect(vo);
				if(result > 0 && contestVo.getResult_sms_send_yn() != null && contestVo.getResult_sms_send_yn().equals("Y")){	//대기자 선정처리 SMS 발송
					//취소인원 발생으로 대기 인원이  참가확정으로 변경

					SmsManageVo smvo = new SmsManageVo();
					smvo.setSubject("[진승무역]");
					StringBuffer sb2 = new StringBuffer();
					sb2.append(waitVo.getJoin_name()+"님\n");
					sb2.append(contestVo.getCt_sbj()+"/"+waitVo.getPart()+"조");
					if(vo.getLane() != null && !vo.getLane().equals("") && contestVo.getLane_sms_send_yn() != null && contestVo.getLane_sms_send_yn().equals("Y")){
						sb2.append(" / "+vo.getLane()+"레인");
					}
					sb2.append("/ 배정 확정\n");
					sb2.append("-계좌정보-\n");
					sb2.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
					//sb2.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
					sb2.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
					sb2.append("(반드시 참가자명으로 입금 바랍니다.)");
					 
					
					smvo.setMsg(sb2.toString());
					smvo.setPhone(waitVo.getTelno());
					
					
					smvo.setEtc1(Integer.toString(vo.getCt_seq()));				//대회 고유번호
					smvo.setEtc2(waitVo.getPart());								//신청 조
					smvo.setEtc3(Integer.toString(waitVo.getApp_seq()));		//신청 고유번호
					smvo.setEtc5("auto"); 										//전체 발송 플래그
					smvo.setEtc6(waitVo.getReg_id());							//신청자 ID
					smvo.setEtc7(waitVo.getJoin_name());						//신청자 명
					smvo.setEtc8(waitVo.getBirth());							//신청자 생년월일
					smvo.setEtc9("0004");										//신청 상태

					
					
					
					SmsManageService.insertMmsList(smvo);
				}
			}
			if(!vo.getOrigin_status().equals("0005") && vo.getStatus().equals("0005")){		//대기X ==> 대기 대기번호 발급
				int result = apageBoardManageService.apageUpdateContestAppWaitingNum(vo);
			}
			
			
			if(contestVo.getCt_process() != null && contestVo.getCt_process().equals("E")){
				int a = apageBoardManageService.apageContestAppResultOrderStatusChange(vo);
			}
			
			
			if(rtnVal > 0){
				rval = true;
			}		
			
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			writer.print(rval);
			writer.flush();
			writer.close();	
			
		}
		
	}
	
	/**
	 * 
	 * 대회신청자 정보삭제
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/apage/contest/deleteContestApp.do")
	public void deleteContestApp(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, 
								HttpServletResponse response, 
								HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;

		rtnVal = apageBoardManageService.deleteAdminContestApp(vo);
		
		if(rtnVal > 0){
			rval = true;
		}		

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print(rval);
		writer.flush();
		writer.close();	
	}	
	
	//조선택 팝업
	@RequestMapping(value = "/apage/contest/contestMngPartPop.do")
	public String contestMngPartPop(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {	
		
		String arrayApp = request.getParameter("arrayApp");
		String currPart = request.getParameter("currPart");
		model.addAttribute("arrayApp",arrayApp);
		model.addAttribute("currPart",currPart);
		
		return "/apage/contest/contestMngPartPop";
	}
	
	
	/**
	 * 
	 * 대회신청자 정보수정
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/apage/contest/appPartChange.do")
	public void appPartChange(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, 
								HttpServletResponse response, 
								HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		String arrayApp = request.getParameter("arrayApp");
		String[] appseq = arrayApp.split(",");
		
		vo.setUpdt_id(mv.getMber_id());
		vo.setPart(request.getParameter("part"));
		
		for(int i=0;i<appseq.length;i++){
			vo.setApp_seq(Integer.parseInt(appseq[i]));
			rtnVal += apageBoardManageService.updateAdminContestApp(vo);	
		}

		if(rtnVal > 0){
			rval = true;
		}		

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print(rval);
		writer.flush();
		writer.close();	
	}
	
	//신청자명단 엑셀출력
	@RequestMapping(value = "/apage/contest/contestAppListExcel.do")
	public String contestAppListExcel(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageBoardManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
		
		List<apageBoardManageVo> list = apageBoardManageService.selectAdminContestAppExcel(vo);		
		
		model.addAttribute("contestView", svo);
		model.addAttribute("contestAppList",list);
		
		return "/apage/contest/contestMngAppExcel";
	}
	
	//신청자 레인 지정
	@RequestMapping(value="/apage/contest/insertAutoLane.do")
	public String insertAutoLane(@ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		apageBoardManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
		
		vo.setBbs_id("laneResult");
		vo.setNtt_sj(svo.getCt_sbj());
		vo.setNtt_cn(vo.getAutoLaneResult());
		vo.setNtcr_id(mv.getMber_id());
		vo.setNtcr_nm(mv.getMber_name());
		
		if(vo.getCt_seq() > 0){
			rtnVal= apageBoardManageService.deleteAdminBoardLaneResult(vo);
		}
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "insert.success");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "redirect:/apage/contest/adminLaneList.do";				
		}
		
		return "redirect:/apage/contest/adminLaneList.do";
	}
	
	

	//자동배치결과 리스트
	@RequestMapping(value="/apage/contest/adminLaneList.do")
	public String adminLaneList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("laneResult");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/contest/adminLaneList";
	}
	
	//자동배치결과 상세
	@RequestMapping(value="/apage/contest/adminLaneDetail.do")
	public String adminLaneDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		
		if(svo != null && svo.getCt_seq()>0){
			
			vo.setCt_seq(svo.getCt_seq());
			vo.setPart("1");
			List<apageBoardManageVo> list = apageBoardManageService.selectAdminContestSelectResultExcel(vo);		
			for(int i = 0; i < list.size(); i++){
				if(list.get(i) != null && list.get(i).getTelno() != null && list.get(i).getTelno().length() >= 8){
					String tel_no_arr[] = list.get(i).getTelno().split("-");
					StringBuffer sb = new StringBuffer();
					for(int z = 0; z < tel_no_arr.length; z++){
						if(z == 1){
							sb.append("-****-");
						}else{
							sb.append(tel_no_arr[z]);
						}
					}
					list.get(i).setTelno(sb.toString());
				}
			}
			
			vo.setPart("2");
			List<apageBoardManageVo> list2 = apageBoardManageService.selectAdminContestSelectResultExcel(vo);		
			for(int i = 0; i < list2.size(); i++){
				if(list2.get(i) != null && list2.get(i).getTelno() != null && list2.get(i).getTelno().length() >= 8){
					String tel_no_arr[] = list2.get(i).getTelno().split("-");
					StringBuffer sb = new StringBuffer();
					for(int z = 0; z < tel_no_arr.length; z++){
						if(z == 1){
							sb.append("-****-");
						}else{
							sb.append(tel_no_arr[z]);
						}
					}
					list2.get(i).setTelno(sb.toString());
				}
			}
			vo.setPart("3");
			List<apageBoardManageVo> list3 = apageBoardManageService.selectAdminContestSelectResultExcel(vo);		
			for(int i = 0; i < list3.size(); i++){
				if(list3.get(i) != null && list3.get(i).getTelno() != null && list3.get(i).getTelno().length() >= 8){
					String tel_no_arr[] = list3.get(i).getTelno().split("-");
					StringBuffer sb = new StringBuffer();
					for(int z = 0; z < tel_no_arr.length; z++){
						if(z == 1){
							sb.append("-****-");
						}else{
							sb.append(tel_no_arr[z]);
						}
					}
					list3.get(i).setTelno(sb.toString());
				}
			}
			
			vo.setPart("4");
			List<apageBoardManageVo> list4 = apageBoardManageService.selectAdminContestSelectResultExcel(vo);		
			for(int i = 0; i < list4.size(); i++){
				if(list4.get(i) != null && list4.get(i).getTelno() != null && list4.get(i).getTelno().length() >= 8){
					String tel_no_arr[] = list4.get(i).getTelno().split("-");
					StringBuffer sb = new StringBuffer();
					for(int z = 0; z < tel_no_arr.length; z++){
						if(z == 1){
							sb.append("-****-");
						}else{
							sb.append(tel_no_arr[z]);
						}
					}
					list4.get(i).setTelno(sb.toString());
				}
			}
			
			
			model.addAttribute("contestView", svo);
			
			model.addAttribute("part1List",list);
			model.addAttribute("part2List",list2);
			model.addAttribute("part3List",list3);
			model.addAttribute("part4List",list4);
			
		}
		
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/contest/adminLaneView";
	}	
	
	//대회안내 삭제
	@RequestMapping(value="/apage/contest/adminLaneDelete.do")
	public String adminLaneDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			
			result = apageBoardManageService.adminBoardDelete(vo);
			
			if(result > 0){
				model.addAttribute("msg", "delete.success");				
			}
						
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "delete.fail");
    		return "/apage/contest/adminLaneList";
		}		
		return "/apage/contest/adminLaneList";
	}	
	
	
	

	//대회 선정자 목록페이지
	@RequestMapping(value="/apage/contest/adminContestSelectResultList.do")
	public String adminContestSelectResultList(@ModelAttribute("vo") apageBoardManageVo vo
			, HttpServletRequest request
			, ModelMap model
			, HttpServletResponse response
			, HttpSession session) throws Exception {	
		
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//대회 신청 게시글 조회
		List<apageBoardManageVo> list  = apageBoardManageService.selectAdminContestAppFinishList(vo);
		int totCnt = apageBoardManageService.selectAdminContestAppFinishListCnt(vo);
		
		
		//검색입력값
		model.addAttribute("contestList", list);
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		
		return "/apage/contest/contestSelectResultList";		
	}
	
	// 대회 상세
	@RequestMapping(value="/apage/contest/adminContestSelectResultDetail.do")
	public String adminContestSelectResultDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
				
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		apageBoardManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
		
		int result  = 0;
		if(mv != null && !mv.getMber_id().equals("") && svo != null){
			//첨부파일 리스트 가져오기
			if(svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
				fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
				List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
				model.addAttribute("contestFile", contestFile);
			}
		}
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/contest/contestSelectResultView";
	}
	
	/**
	 * 
	 * 선정결과 노출여부 관련 AJAX
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/contestAppResultExposeYn.do")
	@ResponseBody
	public ModelAndView contestAppResultExposeYn(@ModelAttribute("vo") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = apageBoardManageService.updateContestAppResultExposeYn(vo);
	
			if(result > 0){
				data.put("resultCode" , "Y");
			}else{
				data.put("resultCode" , "N");
			}
			
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	/**
	 * 
	 * 대회신청자 리스트JSON
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/contestAppResultSendMsg.do")
	@ResponseBody
	public ModelAndView contestAppResultSendMsg(@ModelAttribute("vo") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = 0;
			
			
			int play_cnt = 0;
			if(vo != null && vo.getCt_seq() > 0){
				apageBoardManageVo contestVo = apageBoardManageService.lb_getAdminContestView(vo);
				if(contestVo != null && !contestVo.getResult_sms_send_yn().equals("Y")){
					List<apageBoardManageVo> list = apageBoardManageService.selectAdminContestAppResultList(vo);
					List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
					for(int i = 0; i <list.size(); i++){
						SmsManageVo smv = new SmsManageVo();
						smv.setSubject("[진승무역]");
						if(list.get(i).getStatus().equals("0004")){			//선정
							StringBuffer sb = new StringBuffer();
							sb.append(list.get(i).getJoin_name()+"님\n");
							sb.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"조 참가 확정\n");
							sb.append("-계좌정보-\n");
							sb.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
							//sb2.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
							sb.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
							sb.append("(반드시 참가자명으로 입금 바랍니다.)");
							smv.setMsg(sb.toString());
							
							
							
							smv.setPhone(list.get(i).getTelno());
							smv.setEtc1(Integer.toString(vo.getCt_seq()));				//대회 고유번호
							smv.setEtc2(list.get(i).getPart());							//신청 조
							smv.setEtc3(Integer.toString(list.get(i).getApp_seq()));	//신청 고유번호
							smv.setEtc5("all"); 										//전체 발송 플래그
							smv.setEtc6(list.get(i).getReg_id());						//신청자 ID
							smv.setEtc7(list.get(i).getJoin_name());					//신청자 명
							smv.setEtc8(list.get(i).getBirth());						//신청자 생년월일
							smv.setEtc9(list.get(i).getStatus());						//신청 상태
							
							
							
							smsList.add(smv);
						}
						/*
						else if(list.get(i).getStatus().equals("0005")){	//대기
							StringBuffer sb = new StringBuffer();
							sb.append(list.get(i).getJoin_name()+"님\n");
							sb.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"조/ 대기자 확정\n");
							sb.append("공석발생시 연락드립니다.");
							smv.setMsg(sb.toString());
						}
						*/
						
					}
					
					if(smsList.size() > 0){
						result = SmsManageService.insertMmsBatch(smsList);
						apageBoardManageService.apageUpdateContestMsgSendDate(vo);
						apageBoardManageService.apageUpdateContestAppSendFlagAndDate(vo);
					}
				}
			}
	
			if(result > 0){
				data.put("resultCode" , "Y");
			}else{
				data.put("resultCode" , "N");
			}
			
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	
	//신청자명단 엑셀출력
	@RequestMapping(value = "/apage/contest/contestSelectResulExcel.do")
	public String contestSelectResulExcel(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageBoardManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
		
		vo.setPart("1");
		List<apageBoardManageVo> list = apageBoardManageService.selectAdminContestSelectResultExcel(vo);		
		
		vo.setPart("2");
		List<apageBoardManageVo> list2 = apageBoardManageService.selectAdminContestSelectResultExcel(vo);		
		
		vo.setPart("3");
		List<apageBoardManageVo> list3 = apageBoardManageService.selectAdminContestSelectResultExcel(vo);		
		
		vo.setPart("4");
		List<apageBoardManageVo> list4 = apageBoardManageService.selectAdminContestSelectResultExcel(vo);		
		
		model.addAttribute("contestView", svo);
		
		model.addAttribute("part1List",list);
		model.addAttribute("part2List",list2);
		model.addAttribute("part3List",list3);
		model.addAttribute("part4List",list4);
		
		
		return "/apage/contest/contestSelectResultExcel";
	}
	
	
	
	/**
	 * 대회 신청결과리스트
	 * */
	@RequestMapping(value="/apage/contest/contestAppResultListJson.do")
	@ResponseBody
	public ModelAndView contestAppResultListJson(@ModelAttribute("vo") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		try{
	
			Map<String,Object> data 	= new HashMap<String,Object>();
	
			List<apageBoardManageVo> list = apageBoardManageService.selectAdminContestAppList(vo);
			if(list.size() > 0){
				data.put("resultList",list);
				data.put("resultCode" , "Y");
			}else{
				data.put("resultCode" , "N");
			}
			
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
		
	}
	
	
	/**
	 * SMS 발송내역 리스트
	 * */
	@RequestMapping(value="/apage/contest/contestMsgResulttListJson.do")
	@ResponseBody
	public ModelAndView contestMsgResulttListJson(@ModelAttribute("vo") apageBoardManageVo vo
															, HttpServletRequest request
															, HttpServletResponse response
															, HttpSession session) throws Exception {
		
		try{
			
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			apageBoardManageVo contestVo = apageBoardManageService.lb_getAdminContestView(vo);
			/*
			SmsManageVo smv = new SmsManageVo();
			smv.setEtc1(Integer.toString(vo.getCt_seq()));				//대회 고유번호
			smv.setEtc2(vo.getPart());							//신청 조
			smv.setLog_table_name("mms_log_"+contestVo.getCt_sms_send_dt());
			smv.setEtc5("all");
			List<SmsManageVo> smsLogList = SmsManageService.selectSmsLogList(smv);
			StringBuffer sb = new StringBuffer();
			int cnt = 0;
			for(int i = 0; i < smsLogList.size(); i++){
				if(smsLogList.get(i).getRslt().equals("0")){
					if(cnt == 0){
						sb.append(smsLogList.get(i).getEtc3());
					}else{
						sb.append(","+smsLogList.get(i).getEtc3());
					}
					cnt++;
				}
			}
			if(sb != null){
				String app_seq_arr[] = sb.toString().split(",");
				vo.setApp_seq_arr(app_seq_arr);
				vo.setApp_sms_flag("Y");
				int updt_result = apageBoardManageService.apageUpdateAppSmsFlag(vo);
			}
			
			List<apageBoardManageVo> smsResultList = apageBoardManageService.selectAdminContestAppResultGubunPartList(vo);
			*/

			
			
			if(contestVo != null && contestVo.getCt_sms_send_dt2() != null && !contestVo.getCt_sms_send_dt2().equals("")){
				SmsManageVo smsLogVo = new SmsManageVo();
				String date = contestVo.getCt_sms_send_dt2();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(date));
				
				cal.add(Calendar.MONTH, -1);
				smsLogVo.setBeforeMonth(sdf.format(cal.getTime()).toString());
				System.out.println("sdf.format(cal.getTime()).toString() ==> " + sdf.format(cal.getTime()).toString());
				
				cal.add(Calendar.MONTH, 2);
				smsLogVo.setAfterMonth(sdf.format(cal.getTime()).toString());
				System.out.println("sdf.format(cal.getTime()).toString() ==> " + sdf.format(cal.getTime()).toString());
				
				List<SmsManageVo> logTableList = SmsManageService.getMmsLogTableList(smsLogVo);
				String log_tables[] = new String[logTableList.size()];
				if(logTableList.size() == log_tables.length){
					for(int i = 0; i < log_tables.length; i++){
						log_tables[i] = logTableList.get(i).getTable_name();
					}
					smsLogVo.setLog_tables(log_tables);
					smsLogVo.setEtc1(Integer.toString(contestVo.getCt_seq()));
					smsLogVo.setEtc2(vo.getPart());
					smsLogVo.setEtc5("all");
					smsLogVo.setTemp1("auto");
					List<SmsManageVo> smsHistory = SmsManageService.getMmsLogTableUnionList(smsLogVo);
					data.put("smsHistory",smsHistory);
					data.put("resultCode" , "Y");
				}else{
					data.put("resultCode" , "N");
				}
			}else{
				data.put("resultCode" , "N");
			}
			/*
			if(smsResultList.size() > 0){
				data.put("smsResultList",smsResultList);
				data.put("resultCode" , "Y");
			}else{
				data.put("resultCode" , "N");
			}
			*/
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
		
	}
	
	
	/**
	 * 랜덤레인배정 SMS 발송내역 리스트
	 * */
	@RequestMapping(value="/apage/contest/contestMsgRandomLaneResultJson.do")
	@ResponseBody
	public ModelAndView contestMsgRandomLaneResultJson(@ModelAttribute("vo") apageBoardManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) throws Exception {
		
		try{
			
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			apageBoardManageVo contestVo = apageBoardManageService.lb_getAdminContestView(vo);
			if(contestVo != null && contestVo.getCt_lane_sms_send_dt2() != null && !contestVo.getCt_lane_sms_send_dt2().equals("")){
				SmsManageVo smsLogVo = new SmsManageVo();
				String date = contestVo.getCt_lane_sms_send_dt2();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(date));
				
				cal.add(Calendar.MONTH, -1);
				smsLogVo.setBeforeMonth(sdf.format(cal.getTime()).toString());
				System.out.println("sdf.format(cal.getTime()).toString() ==> " + sdf.format(cal.getTime()).toString());
				
				cal.add(Calendar.MONTH, 2);
				smsLogVo.setAfterMonth(sdf.format(cal.getTime()).toString());
				System.out.println("sdf.format(cal.getTime()).toString() ==> " + sdf.format(cal.getTime()).toString());
				
				List<SmsManageVo> logTableList = SmsManageService.getMmsLogTableList(smsLogVo);
				String log_tables[] = new String[logTableList.size()];
				if(logTableList.size() == log_tables.length){
					for(int i = 0; i < log_tables.length; i++){
						log_tables[i] = logTableList.get(i).getTable_name();
					}
					smsLogVo.setLog_tables(log_tables);
					smsLogVo.setEtc1(Integer.toString(contestVo.getCt_seq()));
					smsLogVo.setEtc2(vo.getPart());
					smsLogVo.setEtc5("lane");
					//smsLogVo.setTemp1("auto");
					List<SmsManageVo> smsHistory = SmsManageService.getMmsLogTableUnionList(smsLogVo);
					data.put("smsHistory",smsHistory);
					data.put("resultCode" , "Y");
				}else{
					data.put("resultCode" , "N");
				}
			}else{
				data.put("resultCode" , "N");
			}
			
			
			
			/*
			
			SmsManageVo smv = new SmsManageVo();
			smv.setEtc1(Integer.toString(vo.getCt_seq()));				//대회 고유번호
			smv.setEtc2(vo.getPart());							//신청 조
			smv.setLog_table_name("mms_log_"+contestVo.getCt_sms_send_dt());
			smv.setEtc5("lane");
			List<SmsManageVo> smsLogList = SmsManageService.selectSmsLogList(smv);
			StringBuffer sb = new StringBuffer();
			int cnt = 0;
			for(int i = 0; i < smsLogList.size(); i++){
				if(smsLogList.get(i).getRslt().equals("0")){
					if(cnt == 0){
						sb.append(smsLogList.get(i).getEtc3());
					}else{
						sb.append(","+smsLogList.get(i).getEtc3());
					}
					cnt++;
				}
			}
			if(sb != null){
				String app_seq_arr[] = sb.toString().split(",");
				vo.setApp_seq_arr(app_seq_arr);
				vo.setApp_lane_sms_flag("Y");
				int updt_result = apageBoardManageService.apageUpdateAppRandomLaneSmsFlag(vo);
			}
			
			
			List<apageBoardManageVo> smsResultList = apageBoardManageService.selectAdminContestAppLaneSmsSendResultList(vo);
			
			if(smsResultList.size() > 0){
				//data.put("smsLogList",smsLogList);
				data.put("smsResultList",smsResultList);
				data.put("resultCode" , "Y");
			}else{
				data.put("resultCode" , "N");
			}
			*/
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
		
	}
	
	
	
	/**
	 * 
	 * 대회신청자 레인배치 문자메시지 발송 AJAX
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/contestLaneResultSendMsg.do")
	@ResponseBody
	public ModelAndView contestLaneResultSendMsg(@ModelAttribute("vo") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = 0;
			
			
			int play_cnt = 0;
			if(vo != null && vo.getCt_seq() > 0){
				apageBoardManageVo contestVo = apageBoardManageService.lb_getAdminContestView(vo);
				if(contestVo != null){
					List<apageBoardManageVo> list = apageBoardManageService.selectAdminContestRandomLaneResultList(vo);
					List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
					for(int i = 0; i <list.size(); i++){
						SmsManageVo smv = new SmsManageVo();
						smv.setSubject("[진승무역]");
						if(list.get(i).getStatus() != null && list.get(i).getStatus().equals("0004") && list.get(i).getLane() != null && !list.get(i).getLane().equals("")){			//선정
							SmsManageVo smvo = new SmsManageVo();
							smvo.setSubject("[진승무역]");
							StringBuffer sb2 = new StringBuffer();
							sb2.append(list.get(i).getJoin_name()+"님\n");
							sb2.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"조 / "+list.get(i).getLane()+"레인 배정 확정");
							
							/*
							sb2.append("-계좌정보-\n");
							sb2.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
							//sb2.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
							sb2.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
							sb2.append("(반드시 참가자명으로 입금 바랍니다.)");
							*/
							
							smvo.setMsg(sb2.toString());
							smvo.setPhone(list.get(i).getTelno());
							
							smvo.setEtc1(Integer.toString(list.get(i).getCt_seq()));
							smvo.setEtc2(list.get(i).getPart());
							smvo.setEtc3(Integer.toString(list.get(i).getApp_seq()));
							smvo.setEtc5("lane");
							
							smvo.setEtc6(list.get(i).getReg_id());						//신청자 ID
							smvo.setEtc7(list.get(i).getJoin_name());					//신청자 명
							smvo.setEtc8(list.get(i).getBirth());						//신청자 생년월일
							smvo.setEtc9(list.get(i).getStatus());						//신청 상태
						
							
							smsList.add(smvo);
						}
						/*
						else if(list.get(i).getStatus().equals("0005")){	//대기
							StringBuffer sb = new StringBuffer();
							sb.append(list.get(i).getJoin_name()+"님\n");
							sb.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"조/ 대기자 확정\n");
							sb.append("공석발생시 연락드립니다.");
							smv.setMsg(sb.toString());
						}
						*/
						
					}
					
					if(smsList.size() > 0){
						result = SmsManageService.insertMmsBatch(smsList);
						apageBoardManageService.apageUpdateContestRandomLaneMsgSendDate(vo);
						apageBoardManageService.apageUpdateContestRandomLaneSendFlagAndDate(vo);
					}
				}
			}
	
			if(result > 0){
				data.put("resultCode" , "Y");
			}else{
				data.put("resultCode" , "N");
			}
			
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	
	
	/**
	 * 
	 * 배치결과 노출여부 관련 AJAX
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/contestLaneResultExposeYn.do")
	@ResponseBody
	public ModelAndView contestlaneResultExposeYn(@ModelAttribute("vo") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = apageBoardManageService.contestAppResultExposeYn(vo);
	
			if(result > 0){
				data.put("resultCode" , "Y");
			}else{
				data.put("resultCode" , "N");
			}
			
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	
	/**
	 * 
	 * 각 대회 환불 리스트 
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/contestRefundListJson.do")
	@ResponseBody
	public ModelAndView contestRefundListJson(@ModelAttribute("vo") apageBoardManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) throws Exception {
		
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			List<apageBoardManageVo> refundList = apageBoardManageService.apageContestRefundList(vo);
			
			if(refundList.size() > 0){
				data.put("resultCode" , "Y");
				data.put("refundList" , refundList);
			}else{
				data.put("resultCode" , "N");
			}
			
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	
	/**
	 * 
	 * 대회 신청결과 선정, 대기자 분류
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/contestAppClassification.do")
	public @ResponseBody ModelAndView contestAppClassification(@ModelAttribute("vo") apageBoardManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) throws Exception {
		
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{

			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			
			apageBoardManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
			
			int result  = 0;
			if(mv != null && !mv.getMber_id().equals("") && svo != null){
				if(svo.getCt_process().equals("E")){	//상태 : 신청마감, 정렬여부 : N
					apageBoardManageVo updtVo = new apageBoardManageVo();
					updtVo.setCt_seq(svo.getCt_seq());
					updtVo.setUpdt_id(mv.getMber_id());
					for(int i = 1; i <= 4; i++){
						updtVo.setPart(Integer.toString(i));
						//신청마감 후 랭키나열 후 상태 UPDATE 
						int updt_result = apageBoardManageService.apageContestAppResultOrderStatusChange(updtVo);
						if(updt_result > 0){result++;};
					}
					
					if(result == 4){
						updtVo.setCut_yn("Y");
						//업데이트 완료 후 대회 ROWNUM 정렬 플래그 cut_yn ==> Y로 변경
						int updt_result = apageBoardManageService.apageContestCutYnUpdate(updtVo);
						data.put("resultCode", "Y");
					}else{
						data.put("resultCode", "N");
					}
				}else{
					data.put("resultCode", "P");
				}
			}else{
				data.put("resultCode", "A");
			}
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	
	/**
	 * 
	 * 대회 신청결과 선정, 대기자 분류
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/situationShowUpdate.do")
	public @ResponseBody ModelAndView situationShowUpdate(@ModelAttribute("vo") apageBoardManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) throws Exception {
		
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{

			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			
			int result  = 0;
			if(mv != null && !mv.getMber_id().equals("") && vo != null && vo.getCt_seq() > 0){
				vo.setUpdt_id(mv.getMber_id());
				int updt_result = apageBoardManageService.apageAppSituationShowUpdate(vo);
				if(updt_result > 0){
					data.put("resultCode", "Y");
				}else{
					data.put("resultCode", "N");
				}
			}else{
				data.put("resultCode", "A");
			}
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	/**
	 * 
	 * 대회 신청결과 선정, 대기자 분류
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/contest/resendMsg.do")
	public @ResponseBody ModelAndView resendMsg(@ModelAttribute("vo") SmsManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) throws Exception {
		
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			
			data.put("resultCode", "Y");
			if(mv != null && !mv.getMber_id().equals("") && vo != null && vo.getMsgkey() > 0){
				
				System.out.println("=================================");
				System.out.println("msgkey ==> " + vo.getMsgkey());
				System.out.println("vo.getSentdate() ==> " + vo.getSentdate());
				System.out.println("vo.getRslt() ==> " + vo.getRslt());
				System.out.println("=================================");
				
				String logTable =  vo.getSentdate().substring(0,7).replaceAll("-", "");

				System.out.println("logTable ===> " + logTable);
				System.out.println("logTable ===> " + logTable);
				System.out.println("logTable ===> " + logTable);
				System.out.println("logTable ===> " + logTable);
				
				vo.setLog_table_name("mms_log_"+logTable);
					
				SmsManageVo msgVo = SmsManageService.getSelectResendMmsInfo(vo);
				
				
				int resend_result = SmsManageService.insertMmsList(msgVo);

				if(resend_result > 0){
					//MMS 재전송 처리 후 FLAG 변경
					//msgVo.setLog_table_name("mms_log_"+logTable);
					int updtMsgVo = SmsManageService.setUpdateFailMsgResendInfo(vo);
					data.put("resultCode", "Y");
				}else{
					data.put("resultCode", "N");
				}
			}else{
				data.put("resultCode", "A");
			}
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//쇼핑몰 이벤트 게시판 리스트
	@RequestMapping(value="/apage/board/adminShopeventList.do")
	public String adminShopeventList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("shopevent");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//게시판 구분값
		vo.setBbs_id("ShopeventNotice");
		List<apageBoardManageVo> topList = apageBoardManageService.selectadminBoardList(vo);		
		model.addAttribute("topList", topList);
		
		
		apageBoardManageVo shopEventManageVO = apageBoardManageService.lb_selectAdminShopEventManageInfo(vo);		
		model.addAttribute("shopEventManageVO", shopEventManageVO);
		
		
		
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("consultList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/board/adminShopeventList";
	}
	

	//쇼핑몰 이벤트 게시판 상세페이지
	@RequestMapping(value="/apage/board/adminShopeventDetail.do")
	public String adminShopeventDetail(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> consultFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("consultFile", consultFile);
		}
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("boardView", svo);
		
		return "/apage/board/adminShopeventUpdt";
	}	

	//쇼핑몰 이벤트 게시판 수정
	@RequestMapping(value = "/apage/board/adminShopeventUpdate.do")
	public String adminShopeventUpdate(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "shopEvent"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "shopEvent"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminConsultUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminShopeventUpdt";
		}
		return "forward:/apage/board/adminShopeventList.do";
	}
	
	//쇼핑몰 이벤트 게시판 삭제
	@RequestMapping(value="/apage/board/adminShopeventDelete.do")
	public String adminShopeventDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = apageBoardManageService.adminBoardDelete(vo);
			apageBoardManageService.deleteComment(vo);
			

		}catch(Exception e){			
			e.printStackTrace();
    		model.addAttribute("msg", "삭제되지 않았습니다.");
    		return "/apage/board/adminShopeventUpdt";
		}
		return "redirect:/apage/board/adminShopeventList.do";
	}
	
	//쇼핑몰 이벤트 신청 첨부파일 삭제
	@RequestMapping(value="/apage/board/adminShopeventFileDel.do")
	public String adminShopeventFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "shopEvent";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		return this.adminShopeventDetail(vo, model, request, response, session);
	}	
	
	
	
	
	
	
	/**
	 * 쇼핑몰 이벤트 상단공지 CRUD
	 * */
	//쇼핑몰 이벤트 상단공 입력페이지
	@RequestMapping(value = "/apage/board/adminShopeventNoticeWrite.do")
	public String adminShopeventNoticeWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);

		return "/apage/board/adminShopeventNoticeWrite";
	}
		
	//쇼핑몰 이벤트 상단공지 등록
	@RequestMapping(value="/apage/board/adminShopeventNoticeReg.do")
	public String adminShopeventNoticeReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "ShopeventNotice"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setBbs_id("ShopeventNotice");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "등록되었습니다.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/board/adminShopeventNoticeWrite";				
		}
		
		return "forward:/apage/board/adminShopeventList.do";
	}	

	

	//쇼핑몰 이벤트 게시판 상단공지 상세페이지
	@RequestMapping(value="/apage/board/adminShopeventNoticeDetail.do")
	public String adminShopeventNoticeDetail(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session) throws Exception {
		
	FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> noticeFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("ShopeventNotice", noticeFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("boardView", svo);
		
		
		return "/apage/board/adminShopeventNoticeView";
	}	
	
	
	

	//쇼핑몰 이벤트 상단 공지사항 삭제
	@RequestMapping(value="/apage/board/adminShopeventNoticeDelete.do")
	public String adminShopeventNoticeDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			result = apageBoardManageService.adminBoardDelete(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "ShopeventNotice";
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
    		return "/apage/board/adminShopeventNoticeView";
		}
		return "forward:/apage/board/adminShopeventList.do";
	}
	
	// 쇼핑몰이벤트 상단 공지 수정페이지
	@RequestMapping(value="/apage/board/adminShopeventNoticeModify.do")
	public String adminShopeventNoticeModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> noticeFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("noticeFile", noticeFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("boardView", svo);
		
		
		return "/apage/board/adminShopeventNoticeUpdt";
	}
	
	//쇼핑몰이벤트 상단 공지  수정
	@RequestMapping(value = "/apage/board/adminShopeventNoticeUpdate.do")
	public String adminShopeventNoticeUpdate(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") apageBoardManageVo vo,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "ShopeventNotice"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "ShopeventNotice"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "수정되었습니다.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/board/adminShopeventNoticeUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/board/adminShopeventNoticeUpdt";
		}
		return "forward:/apage/board/adminShopeventList.do";
	}
	
	//쇼핑몰이벤트 상단 공지 파일 삭제
	@RequestMapping(value="/apage/board/ShopeventNoticeFileDel.do")
	public String ShopeventNoticeFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "ShopeventNotice";
		FileManageVo fileVO = new FileManageVo();
		fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
		
		//mav.setViewName("redirect:/apage/sys/member");
			
		return this.adminShopeventNoticeModify(vo, model, request, response, session);
	}
	


	

	/**
	 * 
	 * 이벤트대회 접수일자 변경 AJAX
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/board/adminShopeventManageUpdt.do")
	@ResponseBody
	public ModelAndView adminShopeventManageUpdt(@ModelAttribute("vo") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = apageBoardManageService.apageUpdateShopEventManageInfo(vo);
	
			if(result > 0){
				data.put("resultCode" , "Y");
			}else{
				data.put("resultCode" , "N");
			}
			
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
}
