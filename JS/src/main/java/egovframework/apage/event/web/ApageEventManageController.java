package egovframework.apage.event.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import egovframework.apage.board.service.apageBoardManageService;
import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.apage.event.service.ApageEventManageService;
import egovframework.apage.event.service.ApageEventManageVo;
import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.apage.system.service.apageSystemManageService;
import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.client.shop.service.ShopManageService;
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
public class ApageEventManageController {

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

	@Resource(name = "SmsManageService")
	private SmsManageService SmsManageService;
	
	@Resource(name = "ApageEventManageService")
	private ApageEventManageService ApageEventManageService;
	
	
	
	
	
	//???????????? ???????????? ?????????
	@RequestMapping(value="/apage/event/kokContestList.do")
	public String kokContestList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//????????? ?????????
		vo.setBbs_id("kokContest");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/event/adminKokContestList";
	}
	
	
	//???????????? ???????????? ???????????????
	@RequestMapping(value = "/apage/event/kokContestWrite.do")
	public String kokContestWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);

		return "/apage/event/adminKokContestWrite";
	}
	
	

	//???????????? ???????????? ??????
	@RequestMapping(value="/apage/event/kokContestReg.do")
	public String kokContestReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session
							) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "kokContest"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setBbs_id("kokContest");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "insert.success");	
			return "redirect:/apage/event/kokContestList.do";
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/event/adminKokContestWrite";				
		}
		
	}

	
	
	// ???????????? ???????????? ??????
	@RequestMapping(value="/apage/event/kokContestDetail.do")
	public String kokContestDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//????????? ??????
		//apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/event/adminKokContestView";
	}

	
	//???????????? ????????????  ??????
	@RequestMapping(value="/apage/event/kokContestDelete.do")
	public String kokContestDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			result = apageBoardManageService.adminBoardDelete(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "kokContest";
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
						//?????? ??????????????? ????????? ??????
						int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
					}
				}
			}
			
			model.addAttribute("msg", "?????????????????????.");
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "???????????? ???????????????.");
    		return "/apage/event/adminKokContestView";
		}
		return "redirect:/apage/event/kokContestList.do";
		
	}
	
	
	

	//???????????? ???????????? ???????????????
	@RequestMapping(value="/apage/event/kokContestModify.do")
	public String kokContestModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("contestView", svo);
		
		
		return "/apage/event/adminKokContestUpdt";
	}
	

	//???????????? ???????????? ??????
	@RequestMapping(value = "/apage/event/kokContestUpdate.do")
	public String kokContestUpdate(final MultipartHttpServletRequest multiRequest,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "kokContest"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "kokContest"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "?????????????????????.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/event/adminKokContestUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/event/adminKokContestUpdt";
		}
		return "forward:/apage/event/kokContestList.do";
	}
	
	//?????? ??????
	@RequestMapping(value="/apage/event/kokContestFileDel.do")
	public String kokContestFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "kokContest";
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
				//?????? ??????????????? ????????? ??????
				int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
			}
		}
		
		return this.kokContestModify(vo, model, request, response, session);
	}	
	

	
	
	
	
	
	/**************************************************************************************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 * ************************************????????? ?????? ************************************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 * *************************************************************************************************/
	

	
	//????????? ???????????? ?????????
	@RequestMapping(value="/apage/event/eventContestList.do")
	public String eventContestList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//????????? ?????????
		vo.setBbs_id("eventContest");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/event/adminEventContestList";
	}
	
	
	//????????? ???????????? ???????????????
	@RequestMapping(value = "/apage/event/eventContestWrite.do")
	public String eventContestWrite(@ModelAttribute("vo") apageBoardManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		model.addAttribute("memberinfo",mv);

		return "/apage/event/adminEventContestWrite";
	}
	
	

	//????????? ???????????? ??????
	@RequestMapping(value="/apage/event/eventContestReg.do")
	public String eventContestReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") apageBoardManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session
							) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "eventContest"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setBbs_id("eventContest");
		rtnVal= apageBoardManageService.insertAdminBoard(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "insert.success");	
			return "redirect:/apage/event/eventContestList.do";
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/event/adminEventContestWrite";				
		}
		
	}

	
	
	// ????????? ???????????? ??????
	@RequestMapping(value="/apage/event/eventContestDetail.do")
	public String eventContestDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//????????? ??????
		//apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/event/adminEventContestView";
	}

	
	//????????? ????????????  ??????
	@RequestMapping(value="/apage/event/eventContestDelete.do")
	public String eventContestDelete(@ModelAttribute("vo") apageBoardManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
		
		int result;
	
		try{
			result = apageBoardManageService.adminBoardDelete(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "eventContest";
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
						//?????? ??????????????? ????????? ??????
						int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
					}
				}
			}
			
			model.addAttribute("msg", "?????????????????????.");
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "???????????? ???????????????.");
    		return "/apage/event/adminEventContestView";
		}
		return "redirect:/apage/event/eventContestList.do";
		
	}
	
	
	

	//????????? ???????????? ???????????????
	@RequestMapping(value="/apage/event/eventContestModify.do")
	public String eventContestModify(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("contestView", svo);
		
		
		return "/apage/event/adminEventContestUpdt";
	}
	

	//????????? ???????????? ??????
	@RequestMapping(value = "/apage/event/eventContestUpdate.do")
	public String eventContestUpdate(final MultipartHttpServletRequest multiRequest,
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
					result = fileUtil.parseFileInf2(request,files, "", atch_file_id, cnt, "eventContest"); 	
				}
				else{
					result = fileUtil.parseFileInf2(request,files, "FILE_", atch_file_id, cnt, "eventContest"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= apageBoardManageService.updateAdminBoard(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "?????????????????????.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/event/adminEventContestUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/event/adminEventContestUpdt";
		}
		return "forward:/apage/event/eventContestList.do";
	}
	
	//?????? ??????
	@RequestMapping(value="/apage/event/eventContestFileDel.do")
	public String eventContestFileDel(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		//ModelAndView mav			= new ModelAndView("member");
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "eventContest";
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
				//?????? ??????????????? ????????? ??????
				int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
			}
		}
		
		return this.eventContestModify(vo, model, request, response, session);
	}	
	
	
	
	//????????? ???????????? ????????? ?????????
	@RequestMapping(value="/apage/event/eventContestMngList.do")
	public String eventContestMngList(@ModelAttribute("vo") ApageEventManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow(endPageNum);

		
		String ct_type_arr[] = {"L","A","S"};
		vo.setCt_type_arr(ct_type_arr);
		
		List<ApageEventManageVo> list = ApageEventManageService.selectEventContestManageList(vo);
		int totCnt = ApageEventManageService.selectEventContestManageListCnt(vo);
		
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());		
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/event/eventContestMngList";
	}
	
	
	// ????????? ?????? ?????? ?????????
	@RequestMapping(value="/apage/event/eventContestMngDetail.do")
	public String eventContestMngDetail(@ModelAttribute("vo") ApageEventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
				
		ApageEventManageVo svo = ApageEventManageService.selectEventContestManageDetail(vo);
		
		if(svo != null && svo.getCt_type() != null && svo.getCt_type().equals("L")){
			List<ApageEventManageVo> pList = ApageEventManageService.selectEventContestLeassonPartList(svo);
			model.addAttribute("pList", pList);
		}
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		if(svo != null && svo.getCt_group() != null){
			vo.setCt_seq_arr(svo.getCt_group().split(","));
			List<ApageEventManageVo> ct_group_list = ApageEventManageService.apageSelectExpectEventContestGroupList(vo);
			model.addAttribute("ct_group_list", ct_group_list);
		}
		
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		if("S".equals(svo.getCt_type())){
			return "/apage/event/eventContestMngView_shop"+"_"+svo.getCt_seq();
		}else {
			return "/apage/event/eventContestMngView";
		}
	}
	
	
	//????????? ???????????? ???????????????
	@RequestMapping(value = "/apage/event/eventContestMngWrite.do")
	public String eventContestMngWrite(@ModelAttribute("vo") ApageEventManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		//????????? ?????? ?????????
		String ct_type_arr[] = {"A","L","S"};
		vo.setCt_type_arr(ct_type_arr);
		List<ApageEventManageVo> contest_list = ApageEventManageService.apageSelectExpectEventContestList(vo);
		model.addAttribute("contest_list", contest_list);
		
		
		model.addAttribute("memberinfo",mv);
	
		return "/apage/event/eventContestMngWrite";
	}
	
	//????????? ?????? ????????? ??????
	@RequestMapping(value="/apage/event/eventContestMngReg.do")
	public String eventContestMngReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") ApageEventManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "eventContestMng"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setReg_turn(1);
		rtnVal = ApageEventManageService.insertEventContestManageInfo(vo);
		
		if(rtnVal > 0) {
			if(vo.getCt_type() != null && vo.getCt_type().equals("L")){
				rtnVal = ApageEventManageService.insertEventContestLeassonPartInfo(vo);
			}
			model.addAttribute("msg", "?????????????????????.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/event/eventContestMngWrite";				
		}
		return "redirect:/apage/event/eventContestMngList.do";
	}

	
	
	
	
	//????????? ?????? ???????????????
	@RequestMapping(value="/apage/event/eventContestMngModify.do")
	public String eventContestMngModify(@ModelAttribute("vo") ApageEventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		
		ApageEventManageVo svo= ApageEventManageService.selectEventContestManageDetail(vo);
		
		//????????? ?????? ?????????
		String ct_type_arr[] = {"A","L","S"};
		vo.setCt_type_arr(ct_type_arr);
		List<ApageEventManageVo> contest_list = ApageEventManageService.apageSelectExpectEventContestList(vo);
		model.addAttribute("contest_list", contest_list);
		

		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		
		if(svo != null && svo.getCt_type() != null && svo.getCt_type().equals("L")){
			List<ApageEventManageVo> pList = ApageEventManageService.selectEventContestLeassonPartList(svo);
			model.addAttribute("pList", pList);
		}
		
		
		if(svo != null && svo.getCt_group() != null){
			vo.setCt_seq_arr(svo.getCt_group().split(","));
			List<ApageEventManageVo> ct_group_list = ApageEventManageService.apageSelectExpectEventContestGroupList(vo);
			model.addAttribute("ct_group_list", ct_group_list);
		}
		
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("contestView", svo);
		
		
		return "/apage/event/eventContestMngUpdt";
	}
	
	//????????? ?????? ????????? ??????
	@RequestMapping(value = "/apage/event/eventContestMngUpdt.do")
	public String eventContestMngUpdt(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") ApageEventManageVo vo,
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
					result = fileUtil.parseFileInf3(request,files, "", atch_file_id, cnt, "eventContestMng"); 	
				}
				else{
					result = fileUtil.parseFileInf3(request,files, "FILE_", atch_file_id, cnt, "eventContestMng"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);			  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= ApageEventManageService.updateEventContestManageInfo(vo);
			if(rtnVal > 0) {
				if(vo.getCt_type() != null && vo.getCt_type().equals("L")){
					int partDelResult = ApageEventManageService.updateEventContestManagePartInfo(vo);
				}
				model.addAttribute("msg", "?????????????????????.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/event/eventContestMngUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/event/eventContestMngUpdt";
		}
		return "forward:/apage/event/eventContestMngList.do";
	}
	
	//????????? ?????? ????????? ??????
	@RequestMapping(value="/apage/event/eventContestMngDelete.do")
	public String eventContestMngDelete(@ModelAttribute("vo") ApageEventManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = ApageEventManageService.deleteEventContestManageInfo(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "eventContestMng";
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
						//?????? ??????????????? ????????? ??????
						int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
					}
				}
			}
		}catch(Exception e){			
			e.printStackTrace();
    		model.addAttribute("msg", "???????????? ???????????????.");
    		return "/apage/event/eventContestMngUpdt";
		}
		return "redirect:/apage/event/eventContestMngList.do";
	}
	
	//????????? ??????????????? ?????? ?????? ??????
	@RequestMapping(value="/apage/event/eventContestMngFileDel.do")
	public String eventContestMngFileDel(@ModelAttribute("vo") ApageEventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		int fileCnt = Integer.parseInt(request.getParameter("FileCnt"));
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "eventContestMng";
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
				//?????? ??????????????? ????????? ??????
				int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
			}
		}
		if(fileCnt == 1){
			ApageEventManageService.updateEventContestFile(vo);
		}
		
		return this.eventContestMngModify(vo, model, request, response, session);
	}		
	
	
	
	/**
	 * 
	 * ????????? ?????? ????????? ?????????JSON
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/event/eventContestAppListJson.do")
	@ResponseBody
	public ModelAndView eventContestAppListJson(@ModelAttribute("vo") ApageEventManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
	try{

		ResultDataManager rm = new ResultDataManager();				
		List<ApageEventManageVo> list = ApageEventManageService.selectAdminEventContestAppList(vo); 
				
		rm.setData(apageBoardManageVo.class, list);
		//SendMiPlatform.SendData ?????? LIST2??? ?????? JSON?????? ?????? ??? ??????
		return SendMiPlatform.SendData(response, rm);
		}catch(Exception e){
			
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
	}
	
	
	
	
		
	/**
	 * 
	 * ????????? ?????? ????????? ????????????
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/apage/event/updateEventContestApp.do")
	public void updateEventContestApp(@ModelAttribute("vo") ApageEventManageVo vo,
								HttpServletRequest request, 
								HttpServletResponse response, 
								HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		//apageBoardManageVo contestVo = apageBoardManageService.lb_getAdminContestView(vo);
		ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(vo);
		vo.setUpdt_id(mv.getMber_id());
		if(vo.getStatus().equals("0003") && !contestVo.getCt_process().equals("E")){
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			writer.print(rval);
			writer.flush();
			writer.close();	
		}else{
			
			rtnVal = ApageEventManageService.updateAdminEventContestApp(vo);
			
			//???????????? ??? ????????? ?????? ?????? ?????? MMS??????
			if(rtnVal > 0 && vo.getStatus().equals("0003")){
				ApageEventManageVo cancelVo = ApageEventManageService.apageGetEventContestInfoDetailOfEachMember(vo);
				//apageBoardManageVo cancelVo = apageBoardManageService.apageGetContestInfoDetailOfEachMember(vo);
				if(cancelVo.getStatus().equals("0003")){
					
					SmsManageVo smv = new SmsManageVo();
					smv.setSubject("[????????????]");
					smv.setPhone(vo.getTelno());
					StringBuffer sb = new StringBuffer();
					
					// ETC INFO 
					smv.setEtc1(Integer.toString(contestVo.getCt_seq()));		//?????? ????????????
					if(contestVo.getCt_type().equals("L")){
						smv.setEtc2(Integer.toString(cancelVo.getPart_ord()));						//?????? ???
					}else{
						smv.setEtc2(cancelVo.getPart());							//?????? ???
					}
					smv.setEtc3(Integer.toString(cancelVo.getApp_seq()));		//?????? ????????????
					smv.setEtc5("auto"); 										//?????? ?????? ?????????
					smv.setEtc6(cancelVo.getReg_id());							//????????? ID
					smv.setEtc7(cancelVo.getJoin_name());						//????????? ???
					smv.setEtc8(cancelVo.getBirth());							//????????? ????????????
					smv.setEtc9("0003");										//????????????
					
					if(cancelVo.getPay_flag().equals("Y")){
						sb.append(cancelVo.getJoin_name()+"???\n");
						if(contestVo.getCt_type().equals("L")){
							sb.append(contestVo.getCt_sbj() +" / " + cancelVo.getPart_ord()+"???"+" / ????????????\n");
						} else if(contestVo.getCt_type().equals("A")){
							sb.append(contestVo.getCt_sbj() +" / ????????????\n");
						}else{
							sb.append(contestVo.getCt_sbj() +" / " + cancelVo.getPart()+"???"+" / ????????????\n");
						}
						sb.append("???????????? ?????? ??? ???????????? ???????????????\n");
						sb.append(contestVo.getCt_price()+" ?????? ?????????????????????.");
						smv.setMsg(sb.toString());
						SmsManageService.insertMmsList(smv);
					}else{
						sb.append(cancelVo.getJoin_name()+"???\n");
						if(contestVo.getCt_type().equals("L")){
							sb.append(contestVo.getCt_sbj() +" / " + cancelVo.getPart_ord()+"??? / ????????????");
						} else if(contestVo.getCt_type().equals("A")){
							sb.append(contestVo.getCt_sbj() +" / ????????????");
						}else{
							sb.append(contestVo.getCt_sbj() +" / " + cancelVo.getPart()+"??? / ????????????");
						}
						smv.setMsg(sb.toString());
						SmsManageService.insertMmsList(smv);
					}
				}
				
			}
			if(vo.getOrigin_status().equals("0004") && !vo.getStatus().equals("0004")){		//?????? ==> ????????? ????????? ????????? ?????? ??????
				ApageEventManageVo waitVo = ApageEventManageService.apageGetEventCtPartWaitingInfo(vo);
			//	apageBoardManageVo waitVo = apageBoardManageService.apageGetCtPartWaitingInfo(vo);
				int result = ApageEventManageService.updateEventContestWaitingToSelect(vo);
				//int result = apageBoardManageService.updateWaitingToSelect(vo);
				if(result > 0 && contestVo.getResult_sms_send_yn() != null && contestVo.getResult_sms_send_yn().equals("Y")){	//????????? ???????????? SMS ??????
					//???????????? ???????????? ?????? ?????????  ?????????????????? ??????

					SmsManageVo smvo = new SmsManageVo();
					smvo.setSubject("[????????????]");
					StringBuffer sb2 = new StringBuffer();
					sb2.append(waitVo.getJoin_name()+"???\n");
					
					if(contestVo.getCt_type().equals("L")){
						sb2.append(contestVo.getCt_sbj()+"/"+waitVo.getPart_ord()+"???");
					} else if(contestVo.getCt_type().equals("A")){
						sb2.append(contestVo.getCt_sbj());
					}else{
						sb2.append(contestVo.getCt_sbj()+"/"+waitVo.getPart()+"???");
					}
					
					if(vo.getLane() != null && !vo.getLane().equals("") && contestVo.getLane_sms_send_yn() != null && contestVo.getLane_sms_send_yn().equals("Y")){
						sb2.append(" / "+vo.getLane()+"??????");
					}
					sb2.append("/ ?????? ??????\n");
					
					if(contestVo.getCt_type().equals("K")){
						sb2.append("-????????????-\n");
						sb2.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
						//sb2.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"?????? ?????? ??????\n");
						sb2.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"?????? ?????? ??????\n");
						sb2.append("(????????? ?????????????????? ?????? ????????????.)");
					}
					
					smvo.setMsg(sb2.toString());
					smvo.setPhone(waitVo.getTelno());
					
					
					smvo.setEtc1(Integer.toString(vo.getCt_seq()));				//?????? ????????????
					
					if(contestVo.getCt_type().equals("L")){
						smvo.setEtc2(Integer.toString(waitVo.getPart_ord()));		//?????? ???
					}else{
						smvo.setEtc2(waitVo.getPart());								//?????? ???
					}
					
					smvo.setEtc3(Integer.toString(waitVo.getApp_seq()));		//?????? ????????????
					smvo.setEtc5("auto"); 										//?????? ?????? ?????????
					smvo.setEtc6(waitVo.getReg_id());							//????????? ID
					smvo.setEtc7(waitVo.getJoin_name());						//????????? ???
					smvo.setEtc8(waitVo.getBirth());							//????????? ????????????
					smvo.setEtc9("0004");										//?????? ??????

					
					
					
					SmsManageService.insertMmsList(smvo);
				}
			}
			if(!vo.getOrigin_status().equals("0005") && vo.getStatus().equals("0005")){		//??????X ==> ?????? ???????????? ??????
				int result = ApageEventManageService.apageUpdateEventContestAppWaitingNum(vo);
				//int result = apageBoardManageService.apageUpdateContestAppWaitingNum(vo);
			}
			
		/**
		 * ????????????
		 * */
			if(contestVo.getCt_process() != null && contestVo.getCt_process().equals("E")){
			//	int a = ApageEventManageService.apageEventContestAppResultOrderStatusChange(vo);
				int a = ApageEventManageService.apageEventContestAppWaitingNumReOrderring(vo);
				
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
	 * ????????? ?????? ????????? ????????????
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/apage/event/deleteEventContestApp.do")
	public void deleteEventContestApp(@ModelAttribute("vo") ApageEventManageVo vo,
								HttpServletRequest request, 
								HttpServletResponse response, 
								HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;

		rtnVal = ApageEventManageService.deleteAdminEventContestApp(vo);
	//	rtnVal = apageBoardManageService.deleteAdminContestApp(vo);
		
		if(rtnVal > 0){
			rval = true;
		}		

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print(rval);
		writer.flush();
		writer.close();	
	}	
	
	
	

	//????????? ?????? ?????????????????? ????????? ?????????
	@RequestMapping(value="/apage/event/eventContestSelectResultList.do")
	public String eventContestSelectResultList(@ModelAttribute("vo") ApageEventManageVo vo
			, HttpServletRequest request
			, ModelMap model
			, HttpServletResponse response
			, HttpSession session) throws Exception {	
		
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//????????? ?????? ?????????
		String ct_type_arr[] = {"A","L","S"};
		vo.setCt_type_arr(ct_type_arr);
		List<ApageEventManageVo> list = ApageEventManageService.selectEventContestAppFinishList(vo);
		int totCnt = ApageEventManageService.selectEventContestAppFinishListCnt(vo);
		
		/*
		List<apageBoardManageVo> list  = apageBoardManageService.selectAdminContestAppFinishList(vo);
		int totCnt = apageBoardManageService.selectAdminContestAppFinishListCnt(vo);
		*/
		
		//???????????????
		model.addAttribute("contestList", list);
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		
		return "/apage/event/eventContestSelectResultList";		
	}
	
	//????????? ?????? ???????????? ??????
	@RequestMapping(value="/apage/event/eventContestSelectResultDetail.do")
	public String eventContestSelectResultDetail(@ModelAttribute("vo") ApageEventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
				
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		ApageEventManageVo svo= ApageEventManageService.selectEventContestManageDetail(vo);
		
		int result  = 0;
		if(mv != null && !mv.getMber_id().equals("") && svo != null){
			//???????????? ????????? ????????????
			if(svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
				fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
				List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
				model.addAttribute("contestFile", contestFile);
			}
		}
		
		
		if(svo != null && svo.getCt_type() != null && svo.getCt_type().equals("L")){
			List<ApageEventManageVo> pList = ApageEventManageService.selectEventContestLeassonPartList(svo);
			model.addAttribute("pList", pList);
		}
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		if("S".equals(svo.getCt_type())){
			return "/apage/event/eventContestSelectResultView_shop" + "_" + svo.getCt_seq();
		}else{
			return "/apage/event/eventContestSelectResultView";
		}
	}
	
	/**
	 * ????????? ?????? ?????? ???????????????
	 * */
	@RequestMapping(value="/apage/event/eventContestAppResultListJson.do")
	@ResponseBody
	public ModelAndView eventContestAppResultListJson(@ModelAttribute("vo") ApageEventManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		try{
	
			Map<String,Object> data 	= new HashMap<String,Object>();
	
			List<ApageEventManageVo> list = ApageEventManageService.selectAdminEventContestAppList(vo);
			
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
	 * 
	 * ????????? ?????? ???????????? ??????, ????????? ??????
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/event/eventContestAppClassification.do")
	public @ResponseBody ModelAndView contestAppClassification(@ModelAttribute("vo") ApageEventManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) throws Exception {
		
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{

			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			
			//apageBoardManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
			ApageEventManageVo svo = ApageEventManageService.selectEventContestManageDetail(vo);
			
			int result  = 0;
			int compareCnt = 0;
			if(mv != null && !mv.getMber_id().equals("") && svo != null){
				if(svo.getCt_process().equals("E")){	//?????? : ????????????, ???????????? : N
					ApageEventManageVo updtVo = new ApageEventManageVo();
					updtVo.setCt_seq(svo.getCt_seq());
					updtVo.setUpdt_id(mv.getMber_id());
					if(svo != null && svo.getCt_type() != null && svo.getCt_type().equals("L")){
						List<ApageEventManageVo> pList = ApageEventManageService.selectEventContestLeassonPartList(svo);
						compareCnt = pList.size();
						for(int i = 0; i < pList.size(); i++){
							updtVo.setPart(Integer.toString(pList.get(i).getEcp_seq()));
							//???????????? ??? ???????????? ??? ?????? UPDATE 
							int updt_result = ApageEventManageService.apageEventContestAppResultOrderStatusChange(updtVo);
							if(updt_result > 0){result++;};
						}
					}else{
						updtVo.setPart("1");
						compareCnt = 1;
						//???????????? ??? ???????????? ??? ?????? UPDATE 
						int updt_result = ApageEventManageService.apageEventContestAppResultOrderStatusChange(updtVo);
						if(updt_result > 0){result++;};
					}
						
					
					if(result == compareCnt){
						updtVo.setCut_yn("Y");
						//???????????? ?????? ??? ?????? ROWNUM ?????? ????????? cut_yn ==> Y??? ??????
						int updt_result = ApageEventManageService.apageEventContestCutYnUpdate(updtVo);
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
	 * ????????? ?????? ???????????? ???????????? ?????? AJAX
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/event/eventContestAppResultExposeYn.do")
	@ResponseBody
	public ModelAndView eventContestAppResultExposeYn(@ModelAttribute("vo") ApageEventManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = ApageEventManageService.updateEventContestAppResultExposeYn(vo);
	
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
	 * ????????? ?????? ????????????
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/event/createRandomLane.do")
	@ResponseBody
	public ModelAndView createRandomLane(@ModelAttribute("vo") ApageEventManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		List<ApageEventManageVo> list = null;
				
		try{
	
			ResultDataManager rm = new ResultDataManager();		
			vo.setCt_seq(Integer.parseInt(request.getParameter("ct_seq")));
			//apageBoardManageVo contestVo = apageBoardManageService.lb_getAdminContestView(vo);
			
			ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(vo);
			
			
			
			int laneCnt = Integer.parseInt(request.getParameter("laneCnt"));
			if(contestVo != null && contestVo.getCt_type() != null && contestVo.getCt_type().equals("L")){
				List<ApageEventManageVo> pList = ApageEventManageService.selectEventContestLeassonPartList(contestVo);
				for(int i = 0; i < pList.size(); i++){
					list = new ArrayList<ApageEventManageVo>();
					
					vo.setPart(Integer.toString(pList.get(i).getEcp_seq()));			
					list = ApageEventManageService.createEventContestRandomLane(vo);
					List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
					
					int a = 1;
					int b = 1;
					int laneCnt_final = laneCnt;
					if(vo.getPrepare_yn() != null && vo.getPrepare_yn().equals("Y")){	//??????????????? ?????? 
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
					//int result = apageBoardManageService.updateAppRandomLane(list);	
					int result = ApageEventManageService.updateEventContestAppRandomLane(list);	
					
					rm.setData(ApageEventManageVo.class, list);
				}
				return SendMiPlatform.SendData(response, rm);
				
				
			}else if(contestVo != null && contestVo.getCt_type() != null && contestVo.getCt_type().equals("K")){ //????????????
				for(int i = 1; i <= 2; i++){
					list = new ArrayList<ApageEventManageVo>();
					
					vo.setPart(Integer.toString(i));			
					list = ApageEventManageService.createEventContestRandomLane(vo);
					
					int a = 1;
					int b = 1;
					int laneCnt_final = laneCnt;
					if(vo.getPrepare_yn() != null && vo.getPrepare_yn().equals("Y")){	//??????????????? ?????? 
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
					//int result = apageBoardManageService.updateAppRandomLane(list);	
					int result = ApageEventManageService.updateEventContestAppRandomLane(list);	
					
					rm.setData(ApageEventManageVo.class, list);
				}
				return SendMiPlatform.SendData(response, rm);
				
				
				
				
			}else{
				
				list = new ArrayList<ApageEventManageVo>();
				
				vo.setPart("1");			
				list = ApageEventManageService.createEventContestRandomLane(vo);
				List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
				
				int a = 1;
				int b = 1;
				int laneCnt_final = laneCnt;
				if(vo.getPrepare_yn() != null && vo.getPrepare_yn().equals("Y")){	//??????????????? ?????? 
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
				//int result = apageBoardManageService.updateAppRandomLane(list);	
				int result = ApageEventManageService.updateEventContestAppRandomLane(list);	
				
				rm.setData(ApageEventManageVo.class, list);
		
				return SendMiPlatform.SendData(response, rm);
				
			}
				
			
		}catch(Exception e){
			
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
	}
	
	
	

	//????????? ?????? ????????? ?????? ??????
	@RequestMapping(value="/apage/event/eventContestInsertAutoLane.do")
	public String eventContestInsertAutoLane(@ModelAttribute("vo") ApageEventManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		ApageEventManageVo svo= ApageEventManageService.selectEventContestManageDetail(vo);
		
		apageBoardManageVo bvo = new apageBoardManageVo();
		bvo.setBbs_id("event_laneResult");
		bvo.setNtt_sj(svo.getCt_sbj());
		bvo.setNtt_cn(vo.getAutoLaneResult());
		bvo.setNtcr_id(mv.getMber_id());
		bvo.setNtcr_nm(mv.getMber_name());
		bvo.setCt_seq(svo.getCt_seq());
		
		if(vo.getCt_seq() > 0){
			rtnVal= apageBoardManageService.deleteAdminBoardLaneResult(bvo);
		}
		rtnVal= apageBoardManageService.insertAdminBoard(bvo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "insert.success");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "redirect:/apage/event/eventContestLaneList.do";				
		}
		
		return "redirect:/apage/event/eventContestLaneList.do";
	}
	
	
	
	
	//?????????????????? ?????????
	@RequestMapping(value="/apage/event/eventContestLaneList.do")
	public String eventContestLaneList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//????????? ?????????
		vo.setBbs_id("event_laneResult");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/event/eventContestLaneList";
	}
	
	//?????????????????? ??????
	@RequestMapping(value="/apage/event/eventContestLaneDetail.do")
	public String eventContestLaneDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//????????? ??????
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		List<List<ApageEventManageVo>> resultList = new ArrayList<List<ApageEventManageVo>>();
		
		if(svo != null && svo.getCt_seq()>0){
			ApageEventManageVo evo = new ApageEventManageVo();
			evo.setCt_seq(svo.getCt_seq());
			
			ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(evo);
			if(contestVo != null && contestVo.getCt_type() != null && contestVo.getCt_type().equals("L")){
				List<ApageEventManageVo> pList = ApageEventManageService.selectEventContestLeassonPartList(evo);
				model.addAttribute("pList", pList);
				for(int j = 0; j < pList.size(); j++){
					evo.setPart(Integer.toString(pList.get(j).getEcp_seq()));
					List<ApageEventManageVo> list = ApageEventManageService.selectEventContestSelectResultExcel(evo);		
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
					resultList.add(list);
					
				}
			}else{
				evo.setPart("1");
				List<ApageEventManageVo> list = ApageEventManageService.selectEventContestSelectResultExcel(evo);		
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
				resultList.add(list);
				
				
				
				model.addAttribute("part1List",list);
				model.addAttribute("part_cnt","1");
			}
			model.addAttribute("contestVo", contestVo);
			
		}
		model.addAttribute("boardView", svo);
		model.addAttribute("resultList",resultList);
		
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/event/eventContestLaneView";
	}	
	

	
	
	
	/**
	 * ?????????????????? SMS ???????????? ?????????
	 * */
	@RequestMapping(value="/apage/event/eventContestMsgRandomLaneResultJson.do")
	@ResponseBody
	public ModelAndView eventContestMsgRandomLaneResultJson(@ModelAttribute("vo") ApageEventManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) throws Exception {
		
		try{
			
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(vo);
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
					//smsLogVo.setTemp1("auto");
					smsLogVo.setScType(contestVo.getCt_type());
					if(contestVo.getCt_type().equals("K")){
						smsLogVo.setEtc5("kok_lane");
					}else{
						smsLogVo.setEtc5("event_lane");
					}
					List<SmsManageVo> smsHistory = SmsManageService.getMmsLogTableUnionListEventContest(smsLogVo);
					data.put("smsHistory",smsHistory);
					data.put("resultCode" , "Y");
				}else{
					data.put("resultCode" , "N");
				}
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
	 * 
	 * ????????? ?????? ?????? ?????? ?????? ???????????? ?????? AJAX
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/event/eventContestLaneResultExposeYn.do")
	@ResponseBody
	public ModelAndView eventContestLaneResultExposeYn(@ModelAttribute("vo") ApageEventManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = ApageEventManageService.eventContestAppResultExposeYn(vo);
	
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
	
	//????????? ???????????? ??????
	@RequestMapping(value="/apage/event/eventContestLaneBoardDelete.do")
	public String eventContestLaneBoardDelete(@ModelAttribute("vo") apageBoardManageVo vo
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
    		return "redirect:/apage/event/eventContestLaneList.do";
		}		
		return "redirect:/apage/event/eventContestLaneList.do";
	}	
	
	
	/**
	 * 
	 * ??????????????? ???????????? ??????????????? ?????? AJAX
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/event/eventContestLaneResultSendMsg.do")
	@ResponseBody
	public ModelAndView contestLaneResultSendMsg(@ModelAttribute("vo") ApageEventManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = 0;
			
			
			int play_cnt = 0;
			if(vo != null && vo.getCt_seq() > 0){
				ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(vo);
				if(contestVo != null){
					List<ApageEventManageVo> list = ApageEventManageService.selectAdminEventContestRandomLaneResultList(vo);
					List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
					for(int i = 0; i <list.size(); i++){
						SmsManageVo smv = new SmsManageVo();
						smv.setSubject("[????????????]");
						if(list.get(i).getStatus() != null && list.get(i).getStatus().equals("0004") && list.get(i).getLane() != null && !list.get(i).getLane().equals("")){			//??????
							SmsManageVo smvo = new SmsManageVo();
							smvo.setSubject("[????????????]");
							StringBuffer sb2 = new StringBuffer();
							sb2.append(list.get(i).getJoin_name()+"???\n");
							if(contestVo.getCt_type().equals("A")){
								sb2.append(contestVo.getCt_sbj()+" / "+list.get(i).getLane()+"?????? ?????? ??????");
							}else{
								sb2.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"??? / "+list.get(i).getLane()+"?????? ?????? ??????");
							}
							
							/*
							sb2.append("-????????????-\n");
							sb2.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
							//sb2.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"?????? ?????? ??????\n");
							sb2.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"?????? ?????? ??????\n");
							sb2.append("(????????? ?????????????????? ?????? ????????????.)");
							*/
							
							smvo.setMsg(sb2.toString());
							smvo.setPhone(list.get(i).getTelno());
							
							smvo.setEtc1(Integer.toString(list.get(i).getCt_seq()));
							smvo.setEtc2(list.get(i).getPart());
							smvo.setEtc3(Integer.toString(list.get(i).getApp_seq()));
							if(contestVo.getCt_type().equals("K")){
								smvo.setEtc5("kok_lane");
							}else{
								smvo.setEtc5("event_lane");
							}
							
							smvo.setEtc6(list.get(i).getReg_id());						//????????? ID
							smvo.setEtc7(list.get(i).getJoin_name());					//????????? ???
							smvo.setEtc8(list.get(i).getBirth());						//????????? ????????????
							smvo.setEtc9(list.get(i).getStatus());						//?????? ??????
						
							
							smsList.add(smvo);
						}
						/*
						else if(list.get(i).getStatus().equals("0005")){	//??????
							StringBuffer sb = new StringBuffer();
							sb.append(list.get(i).getJoin_name()+"???\n");
							sb.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"???/ ????????? ??????\n");
							sb.append("??????????????? ??????????????????.");
							smv.setMsg(sb.toString());
						}
						*/
						
					}
					
					if(smsList.size() > 0){
						result = SmsManageService.insertMmsBatch(smsList);
						
						ApageEventManageService.apageUpdateEventContestRandomLaneMsgSendDate(vo);
						ApageEventManageService.apageUpdateEventContestRandomLaneSendFlagAndDate(vo);
						/*
						apageBoardManageService.apageUpdateContestRandomLaneMsgSendDate(vo);
						apageBoardManageService.apageUpdateContestRandomLaneSendFlagAndDate(vo);
						*/
						
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
	 * ????????? ?????? ????????? ???????????? JSON
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/event/eventContestAppResultSendMsg.do")
	@ResponseBody
	public ModelAndView eventContestAppResultSendMsg(@ModelAttribute("vo") ApageEventManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = 0;
			
			
			int play_cnt = 0;
			if(vo != null && vo.getCt_seq() > 0){
				ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(vo);
				if(contestVo != null && !contestVo.getResult_sms_send_yn().equals("Y")){
					List<ApageEventManageVo> list = ApageEventManageService.selectAdminEventContestAppResultList(vo);
					List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
					for(int i = 0; i <list.size(); i++){
						SmsManageVo smv = new SmsManageVo();
						smv.setSubject("[????????????]");
						if(list.get(i).getStatus().equals("0004")){			//??????
							StringBuffer sb = new StringBuffer();
							sb.append(list.get(i).getJoin_name()+"???\n");
							if(contestVo.getCt_type().equals("L")){
								sb.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart_ord()+"??? ?????? ??????\n");
							} else if(contestVo.getCt_type().equals("A")){
								sb.append(contestVo.getCt_sbj()+"/ ?????? ??????\n");
							}else{
								sb.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"??? ?????? ??????\n");
							}
							/*
							sb.append("-????????????-\n");
							sb.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
							//sb2.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"?????? ?????? ??????\n");
							sb.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"?????? ?????? ??????\n");
							sb.append("(????????? ?????????????????? ?????? ????????????.)");
							*/
							smv.setMsg(sb.toString());
							
							
							
							smv.setPhone(list.get(i).getTelno());
							smv.setEtc1(Integer.toString(vo.getCt_seq()));				//?????? ????????????
							smv.setEtc2(list.get(i).getPart());							//?????? ???
							smv.setEtc3(Integer.toString(list.get(i).getApp_seq()));	//?????? ????????????
							smv.setEtc5("event_all"); 									//?????? ?????? ?????????
							smv.setEtc6(list.get(i).getReg_id());						//????????? ID
							smv.setEtc7(list.get(i).getJoin_name());					//????????? ???
							smv.setEtc8(list.get(i).getBirth());						//????????? ????????????
							smv.setEtc9(list.get(i).getStatus());						//?????? ??????
							
							
							
							smsList.add(smv);
						}
						/*
						else if(list.get(i).getStatus().equals("0005")){	//??????
							StringBuffer sb = new StringBuffer();
							sb.append(list.get(i).getJoin_name()+"???\n");
							sb.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"???/ ????????? ??????\n");
							sb.append("??????????????? ??????????????????.");
							smv.setMsg(sb.toString());
						}
						*/
						
					}
					
					if(smsList.size() > 0){
						result = SmsManageService.insertMmsBatch(smsList);
						
						ApageEventManageService.apageUpdateEventContestMsgSendDate(vo);
						ApageEventManageService.apageUpdateEventContestAppSendFlagAndDate(vo);
						
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
	 * ????????? ?????? ???????????? SMS ???????????? ?????????
	 * */
	@RequestMapping(value="/apage/event/evnetContestMsgResulttListJson.do")
	@ResponseBody
	public ModelAndView evnetContestMsgResulttListJson(@ModelAttribute("vo") ApageEventManageVo vo
															, HttpServletRequest request
															, HttpServletResponse response
															, HttpSession session) throws Exception {
		
		try{
			
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(vo);
			if(contestVo != null){
				if(contestVo.getCt_type().equals("K")){
					SmsManageVo smsLogVo = new SmsManageVo();
					String date = contestVo.getApp_start_dt();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					cal.setTime(sdf.parse(date));
					
					cal.add(Calendar.MONTH, -1);
					smsLogVo.setBeforeMonth(sdf.format(cal.getTime()).toString());
					
					cal.add(Calendar.MONTH, 2);
					smsLogVo.setAfterMonth(sdf.format(cal.getTime()).toString());
					
					List<SmsManageVo> logTableList = SmsManageService.getMmsLogTableList(smsLogVo);
					String log_tables[] = new String[logTableList.size()];
					if(logTableList.size() == log_tables.length){
						for(int i = 0; i < log_tables.length; i++){
							log_tables[i] = logTableList.get(i).getTable_name();
						}
						smsLogVo.setLog_tables(log_tables);
						smsLogVo.setEtc1(Integer.toString(contestVo.getCt_seq()));
						smsLogVo.setEtc2(vo.getPart());
						smsLogVo.setEtc5("kok_auto");
						smsLogVo.setTemp1("kok_auto");
						List<SmsManageVo> smsHistory = SmsManageService.getMmsLogTableUnionListEventContest(smsLogVo);
						data.put("smsHistory",smsHistory);
						data.put("resultCode" , "Y");
					}else{
						data.put("resultCode" , "N");
					}
				}else{
					if(contestVo.getCt_sms_send_dt2() != null && !contestVo.getCt_sms_send_dt2().equals("")){
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
							smsLogVo.setEtc5("event_all");
							smsLogVo.setTemp1("event_auto");
							List<SmsManageVo> smsHistory = SmsManageService.getMmsLogTableUnionListEventContest(smsLogVo);
							data.put("smsHistory",smsHistory);
							data.put("resultCode" , "Y");
						}else{
							data.put("resultCode" , "N");
						}
					}
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
	

	//??????????????? ??????????????? ????????????
	@RequestMapping(value = "/apage/event/eventContestSelectResulExcel.do")
	public String contestSelectResulExcel(@ModelAttribute("vo") ApageEventManageVo evo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
	

		List<List<ApageEventManageVo>> resultList = new ArrayList<List<ApageEventManageVo>>();
		
		List<List<ApageEventManageVo>> readyResultList = new ArrayList<List<ApageEventManageVo>>();
		
		ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(evo);
		if(contestVo != null && contestVo.getCt_type() != null && contestVo.getCt_type().equals("L")){
			List<ApageEventManageVo> pList = ApageEventManageService.selectEventContestLeassonPartList(evo);
			model.addAttribute("pList", pList);
			for(int j = 0; j < pList.size(); j++){
				evo.setPart(Integer.toString(pList.get(j).getEcp_seq()));
				List<ApageEventManageVo> list = ApageEventManageService.selectEventContestSelectResultExcel(evo);
				resultList.add(list);
				
				List<ApageEventManageVo> readylist = ApageEventManageService.selectEventContestSelectResultExcelStatusReady(evo);		
				readyResultList.add(readylist);
				
				/*
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
				resultList.add(list);
				 */
				
			}
		}else{
			evo.setPart("1");
			List<ApageEventManageVo> list = ApageEventManageService.selectEventContestSelectResultExcel(evo);		
			resultList.add(list);
			
			List<ApageEventManageVo> readylist = ApageEventManageService.selectEventContestSelectResultExcelStatusReady(evo);		
			readyResultList.add(readylist);
			
			/*
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
				resultList.add(list);
			}
			 */
			
			
			
			model.addAttribute("part1List",list);
			model.addAttribute("part_cnt","1");
		}
		model.addAttribute("contestVo", contestVo);
		
		model.addAttribute("boardView", evo);
		model.addAttribute("resultList",resultList);
		model.addAttribute("readyResultList",readyResultList);
		
	
	
		if("S".equals(contestVo.getCt_type())){
			return "/apage/event/eventContestSelectResultExcel_shop";
		}else {
			return "/apage/event/eventContestSelectResultExcel";
		}
	}
	
	
	//??????????????? ??????????????? ????????????
	@RequestMapping(value = "/apage/event/eventContestAppListExcel.do")
	public String contestAppListExcel(@ModelAttribute("vo") ApageEventManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {
		
		ApageEventManageVo svo = ApageEventManageService.selectEventContestManageDetail(vo);
		//ApageEventManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
		
		List<ApageEventManageVo> list = ApageEventManageService.selectAdminEventContestAppExcel(vo);
		//List<ApageEventManageVo> list = apageBoardManageService.selectAdminContestAppExcel(vo);		
		
		model.addAttribute("contestView", svo);
		model.addAttribute("contestAppList",list);
		
		if("S".equals(svo.getCt_type())){
			return "/apage/event/eventContestMngAppExcel_shop";
		}else{
			return "/apage/event/eventContestMngAppExcel";
		}
	}
	
	
	
	/**
	 * ****************************************************************
	 * ****************************************************************
	 * ****************************************************************
	 * 
	 * 							????????????
	 * 
	 * ****************************************************************
	 * ****************************************************************
	 * ****************************************************************
	 * **/
	
	
	

	//???????????? ???????????? ????????? ?????????
	@RequestMapping(value="/apage/event/kokContestMngList.do")
	public String kokContestMngList(@ModelAttribute("vo") ApageEventManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow(endPageNum);

		
		String ct_type_arr[] = {"K"};
		vo.setCt_type_arr(ct_type_arr);
		
		List<ApageEventManageVo> list = ApageEventManageService.selectEventContestManageList(vo);
		int totCnt = ApageEventManageService.selectEventContestManageListCnt(vo);
		
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());		
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/event/kokContestMngList";
	}
	
	
	// ???????????? ?????? ?????? ?????????
	@RequestMapping(value="/apage/event/kokContestMngDetail.do")
	public String kokContestMngDetail(@ModelAttribute("vo") ApageEventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
				
		ApageEventManageVo svo = ApageEventManageService.selectEventContestManageDetail(vo);
		
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/event/kokContestMngView";
	}
	
	
	//???????????? ???????????? ???????????????
	@RequestMapping(value = "/apage/event/kokContestMngWrite.do")
	public String kokContestMngWrite(@ModelAttribute("vo") ApageEventManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		model.addAttribute("memberinfo",mv);
	
		return "/apage/event/kokContestMngWrite";
	}
	
	//???????????? ?????? ????????? ??????
	@RequestMapping(value="/apage/event/kokContestMngReg.do")
	public String kokContestMngReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") ApageEventManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		List<FileManageVo> result = null;
		Map<String, MultipartFile> files = multiRequest.getFileMap();		
		if(!files.isEmpty()){
  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "kokContestMng"); 	
  			if(result.size() != 0){
	  			fileManageService.insertFileInfs2(result);	
	  			
	  			String file_id = result.get(0).atch_file_id;			
	  			vo.setAtch_file_id(file_id.substring(0, 22));
  			}
		}	
		
		vo.setReg_turn(1);
		rtnVal = ApageEventManageService.insertEventContestManageInfo(vo);
		int ct_seq = rtnVal;
		if(rtnVal > 0) {
			if(vo.getCt_type() != null && vo.getCt_type().equals("L")){
				rtnVal = ApageEventManageService.insertEventContestLeassonPartInfo(vo);
			}else if(vo.getCt_type() != null && vo.getCt_type().equals("K")){

				//???????????? ?????? ??????
				apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
				apageBoardManageVo bvo = new apageBoardManageVo();
				bvo.setBbs_id("kok_laneResult");
				bvo.setNtt_sj(vo.getCt_sbj());
				bvo.setNtt_cn(vo.getAutoLaneResult());
				bvo.setNtcr_id(mv.getMber_id());
				bvo.setNtcr_nm(mv.getMber_name());
				bvo.setCt_seq(ct_seq);
				rtnVal= apageBoardManageService.insertAdminBoard(bvo);
				
			}
			model.addAttribute("msg", "?????????????????????.");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "/apage/event/kokContestMngWrite";				
		}
		
		vo.setCt_seq(ct_seq);
		return "redirect:/apage/event/kokContestTopRankcerWrite.do?ct_seq="+ct_seq;
		//return this.kokContestTopRankcerWrite(vo, request, model, response, session);
	}

	
	
	
	
	//???????????? ?????? ???????????????
	@RequestMapping(value="/apage/event/kokContestMngModify.do")
	public String kokContestMngModify(@ModelAttribute("vo") ApageEventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		
		ApageEventManageVo svo= ApageEventManageService.selectEventContestManageDetail(vo);
		

		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		FileManageVo fvo			= new FileManageVo();
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		
		if(svo != null && svo.getCt_type() != null && svo.getCt_type().equals("L")){
			List<ApageEventManageVo> pList = ApageEventManageService.selectEventContestLeassonPartList(svo);
			model.addAttribute("pList", pList);
		}
		
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("memberinfo",mv);
		model.addAttribute("contestView", svo);
		
		
		return "/apage/event/kokContestMngUpdt";
	}
	
	//???????????? ?????? ????????? ??????
	@RequestMapping(value = "/apage/event/kokContestMngUpdt.do")
	public String kokContestMngUpdt(final MultipartHttpServletRequest multiRequest,
								@ModelAttribute("vo") ApageEventManageVo vo,
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
					result = fileUtil.parseFileInf3(request,files, "", atch_file_id, cnt, "kokContestMng"); 	
				}
				else{
					result = fileUtil.parseFileInf3(request,files, "FILE_", atch_file_id, cnt, "kokContestMng"); 	
				}
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);			  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			rtnVal= ApageEventManageService.updateEventContestManageInfo(vo);
			if(rtnVal > 0) {
				if(vo.getCt_type() != null && vo.getCt_type().equals("L")){
					int partDelResult = ApageEventManageService.updateEventContestManagePartInfo(vo);
				}
				model.addAttribute("msg", "?????????????????????.");	
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/apage/event/kokContestMngUpdt";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/apage/event/kokContestMngUpdt";
		}
		return "forward:/apage/event/kokContestMngList.do";
	}
	
	//???????????? ?????? ????????? ??????
	@RequestMapping(value="/apage/event/kokContestMngDelete.do")
	public String kokContestMngDelete(@ModelAttribute("vo") ApageEventManageVo vo
								, ModelMap model
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {
				
	
		try{	
			int result = ApageEventManageService.deleteEventContestManageInfo(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "kokContestMng";
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
						//?????? ??????????????? ????????? ??????
						int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
					}
				}
			}
		}catch(Exception e){			
			e.printStackTrace();
    		model.addAttribute("msg", "???????????? ???????????????.");
    		return "/apage/event/kokContestMngUpdt";
		}
		return "redirect:/apage/event/kokContestMngList.do";
	}
	
	//???????????? ??????????????? ?????? ?????? ??????
	@RequestMapping(value="/apage/event/kokContestMngFileDel.do")
	public String kokContestMngFileDel(@ModelAttribute("vo") ApageEventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		int fileCnt = Integer.parseInt(request.getParameter("FileCnt"));
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "kokContestMng";
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
				//?????? ??????????????? ????????? ??????
				int resultInt2	= fileManageService.setFileAttachDelete2(fileVO);
			}
		}
		if(fileCnt == 1){
			ApageEventManageService.updateEventContestFile(vo);
		}
		
		return this.kokContestMngModify(vo, model, request, response, session);
	}		
	
	

	//???????????? ?????? ????????? ?????? ??????
	@RequestMapping(value="/apage/event/kokContestInsertAutoLane.do")
	public String kokContestInsertAutoLane(@ModelAttribute("vo") ApageEventManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		ApageEventManageVo svo= ApageEventManageService.selectEventContestManageDetail(vo);
		
		apageBoardManageVo bvo = new apageBoardManageVo();
		bvo.setBbs_id("kok_laneResult");
		bvo.setNtt_sj(svo.getCt_sbj());
		bvo.setNtt_cn(vo.getAutoLaneResult());
		bvo.setNtcr_id(mv.getMber_id());
		bvo.setNtcr_nm(mv.getMber_name());
		bvo.setCt_seq(svo.getCt_seq());
		
		if(vo.getCt_seq() > 0){
			rtnVal= apageBoardManageService.deleteAdminBoardLaneResult(bvo);
		}
		rtnVal= apageBoardManageService.insertAdminBoard(bvo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "insert.success");	
		} else {
			model.addAttribute("msg", "insert.fail");
			return "redirect:/apage/event/kokContestLaneList.do";				
		}
		
		return "redirect:/apage/event/kokContestLaneList.do";
	}
	

	//?????????????????? ?????????
	@RequestMapping(value="/apage/event/kokContestLaneList.do")
	public String kokContestLaneList(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//????????? ?????????
		vo.setBbs_id("kok_laneResult");
		List<apageBoardManageVo> list = apageBoardManageService.selectadminBoardList(vo);		
		int totCnt = apageBoardManageService.selectadminBoardListCnt(vo);
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/apage/event/kokContestLaneList";
	}
	


	//???????????? ?????????????????? ??????
	@RequestMapping(value="/apage/event/kokContestLaneDetail.do")
	public String kokContestLaneDetail(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//????????? ??????
		apageBoardManageService.UpdateCnt(vo);
		
		apageBoardManageVo svo= apageBoardManageService.getAdminBoardView(vo);
		
		List<List<ApageEventManageVo>> resultList = new ArrayList<List<ApageEventManageVo>>();
		
		if(svo != null && svo.getCt_seq()>0){
			ApageEventManageVo evo = new ApageEventManageVo();
			evo.setCt_seq(svo.getCt_seq());
			
			ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(evo);
			if(contestVo != null){
				for(int j = 1; j <= 2; j++){
					evo.setPart(Integer.toString(j));
					List<ApageEventManageVo> list = ApageEventManageService.selectEventContestSelectResultExcel(evo);		
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
					resultList.add(list);
					
				}
				//model.addAttribute("part1List",list);
				model.addAttribute("part_cnt","2");
				
			}
			model.addAttribute("contestVo", contestVo);
			
		}
		model.addAttribute("boardView", svo);
		model.addAttribute("resultList",resultList);
		
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/event/kokContestLaneView";
	}	
	

	
	
	
	//???????????? ???????????? ?????? ??????????????????
	@RequestMapping(value = "/apage/event/kokContestTopRankcerWrite.do")
	public String kokContestTopRankcerWrite(@ModelAttribute("vo") ApageEventManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		ApageEventManageVo svo = ApageEventManageService.selectEventContestManageDetail(vo);
		if(svo != null && vo.getCt_seq() > 0){
			model.addAttribute("ct_seq", vo.getCt_seq());
			model.addAttribute("contestVo", svo);
			model.addAttribute("memberinfo",mv);
			
			return "/apage/event/kokContestTopRankerWrite";
		}else{
			return "redirect:/apage/event/kokContestMngList.do";
		}
	}
	
	

	//???????????? ????????? ???????????? ??????
	@RequestMapping(value="/apage/event/kokContestTopRankcerExcelRead.do")
	@ResponseBody
	public ModelAndView kokContestTopRankcerExcelRead(final MultipartHttpServletRequest multiRequest
											, @ModelAttribute("vo") ApageEventManageVo vo
											, ModelMap model
											, HttpServletRequest request
											, HttpServletResponse response
											, HttpSession session) throws Exception {
		
		try {

			
			
			int rtnVal = 0;
			String msg = "";
			List<FileManageVo> result = null;
			List<ApageEventManageVo> resultList = null;
			Map<String, MultipartFile> files = multiRequest.getFileMap();		
			if(!files.isEmpty()){
	  			//result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "kokCtTarget"); 	
	  			result = fileUtil.fileEventContestAppTargetExcel(request,files, "FILE_", "", 0, "kokCtTarget"); 	
	  			
	  			if(result.size() != 0){
	  				resultList = eventContestAppTargetExcelFileRead(result,request, vo.getCt_seq());
	  			}
			}	
	  		
			
			Map<String,Object> data	= new HashMap<String,Object>();
			if(resultList != null && resultList.size() > 0 ){
				data.put("resultList", resultList);
				data.put("resultCode","Y");
			}else{
				data.put("resultCode","N");
			}
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	
	/**
	 * ???????????? ????????? ???????????? ??????
	 * */
	public List<ApageEventManageVo> eventContestAppTargetExcelFileRead(List<FileManageVo> fileList, HttpServletRequest request, int ct_seq) throws Exception{
		

		
		Map<String,Object> resultMap 	= new HashMap<String,Object>();
		List<ApageEventManageVo> resultList = new ArrayList();
		int returnCnt = 0;
		try {

			apageSystemManageVo codeVo = new apageSystemManageVo();
			
			 // ?????? ?????? ??????(.xlsx)
	  			
	  		String path = propertyService.getString("Globals.fileStorePath");
	  		for(FileManageVo fvo: fileList){
	  	  		File excelFile = new File(path + "/" + "kokCtTarget" + "/" + fvo.getOrignl_file_nm());
	  	  		//origin_file_name ?????? ?????????
	  	  		String ofn = fvo.getOrignl_file_nm();
	  	  		ofn = ofn.substring(0,ofn.lastIndexOf("."));
	  	  		if(excelFile.exists()){

		  	  		StringBuffer sb = new StringBuffer();
		  	  		StringBuffer jsonSb = new StringBuffer();

		  	  		List<String> columnName = new ArrayList();
		  	  		
					// Create an object of filereader
					// class with CSV file as a parameter.
					
		  	  		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
		  	  		Cell cell = null;
		  	  		
		  	  		// ????????? sheet ?????? ??????
		  	  		sb.append("{\"data\" : [");
		  	  		int cnt = 0;
		  	  		
		  	  		int colCnt = 8;
		  	  		for (Row row : wb.getSheetAt(0)) {
			  	  		if(row.getRowNum() < 2) { //3??? ?????? ??????
		                    continue;
		                }
		  	  			jsonSb = new StringBuffer();
		  	  			if(row.getRowNum() != 2){
		  	  				jsonSb.append("{");
		  	  				jsonSb.append("\"ct_seq\":"+"\""+ct_seq+"\",");
		  	  			}
		  	  			for(int i = 0; i < colCnt; i++){
		  	  				if(row.getRowNum() == 2){
		  	  					String cellName = row.getCell(i).toString();
		  	  					columnName.add(cellName);
		  	  				}else{
		  	  					String json ="";
		  	  					String data = "";
		  	  					if(row.getCell(i) != null){
		  	  						row.getCell(i).setCellType(row.getCell(i).CELL_TYPE_STRING); //????????? ????????? ?????? ?????? String ??????
		  	  						data = row.getCell(i).getStringCellValue().toString();
		  	  						if(data != null && !data.equals("") && data.indexOf("\"") > 0){
		  	  							data = data.replaceAll("\"", "'");
		  	  						}
		  	  					}
		  	  					if(i == (colCnt-1)){
		  	  						json= "\""+columnName.get(i)+"\":\""+data+"\"";
		  	  					}else{
		  	  						json= "\""+columnName.get(i)+"\":\""+data+"\",";
		  	  					}
		  	  					jsonSb.append(json);
		  	  				}
		  	  			}
		  	  			if(row.getRowNum() != 2){
		  	  				if(wb.getSheetAt(0).getLastRowNum() -2 == cnt){
		  	  					jsonSb.append("}");
		  	  				}else{
		  	  					jsonSb.append("},");
		  	  				}
		  	  				
		  	  			}
		  	  			sb.append(jsonSb.toString());
		  	  			cnt++;
		  	  		}
	  	  			
	  	  		
		  	  		
		  	  		
		  	  		sb.append("]}");
		  	  		
		  	  		System.out.println(sb.toString());
		  	  		JsonParser parser = new JsonParser();
		  	  		Object obj = parser.parse(sb.toString());
		  	  		JsonObject jsonObj = (JsonObject) obj;
		  	  		JsonArray ja = (JsonArray)jsonObj.get("data");
		  	  		System.out.println("ja.size() ==> " + ja.size());
		  	  		for(int i = 0; i < ja.size(); i++){
		  	  			Gson gson = new Gson(); 
		  	  			resultList.add(gson.fromJson(ja.get(i), ApageEventManageVo.class));
		  	  		}
	  	  		}else{
	  	  			System.out.println("file Not Found");
	  	  		}
	  	  		
	  	  		
	  		}
			
		}catch(Exception e){
			System.out.println("read Game Excel Read Exception");
			e.printStackTrace();
		}
		
		
		return resultList;
		
	}
	
	

	//???????????? ?????? ????????? ??????
	@RequestMapping(value="/apage/event/kokContestTopRankcerReg.do")
	public String kokContestTopRankcerReg(@ModelAttribute("vo") ApageEventManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session
							) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		
		try {
			rtnVal= ApageEventManageService.insertEventContestAppTarget(vo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		};
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "insert.success");	
			return "redirect:/apage/event/kokContestMngList.do";
		} else {
			model.addAttribute("msg", "insert.fail");
			return "forward:/apage/event/kokContestTopRankcerWrite.do";
		}
	}

	
	// ????????? ?????? ?????? ?????????
	@RequestMapping(value="/apage/event/kokContestTopRankcerDetail.do")
	public String kokContestTopRankcerDetail(@ModelAttribute("vo") ApageEventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
				
		ApageEventManageVo svo = ApageEventManageService.selectEventContestManageDetail(vo);
		List<ApageEventManageVo> t_list = ApageEventManageService.selectEventContestTopRankerList(vo);
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		model.addAttribute("t_list", t_list);
		
		return "/apage/event/kokContestTopRankerList";
	}
	
	
	// ????????? ?????? ?????? ?????????
	@RequestMapping(value="/apage/event/kokContestTopRankcerModify.do")
	public String kokContestTopRankcerModify(@ModelAttribute("vo") ApageEventManageVo vo
			, ModelMap model
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		ApageEventManageVo svo = ApageEventManageService.selectEventContestManageDetail(vo);
		List<ApageEventManageVo> t_list = ApageEventManageService.selectEventContestTopRankerList(vo);
		
		//???????????? ????????? ????????????
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		model.addAttribute("t_list", t_list);
		model.addAttribute("ct_seq", svo.getCt_seq());
		
		return "/apage/event/kokContestTopRankerUpdt";
	}
	
	
	
	
	
	
	

	/**
	 * 
	 * ???????????? ?????? ????????? ???????????? JSON
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/event/kokContestAppResultSendMsg.do")
	@ResponseBody
	public ModelAndView kokContestAppResultSendMsg(@ModelAttribute("vo") ApageEventManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{
			int result = 0;
			
			
			int play_cnt = 0;
			if(vo != null && vo.getCt_seq() > 0){
				ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(vo);
				if(contestVo != null && !contestVo.getResult_sms_send_yn().equals("Y")){
					List<ApageEventManageVo> list = ApageEventManageService.selectAdminEventContestAppResultList(vo);
					List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
					for(int i = 0; i <list.size(); i++){
						SmsManageVo smv = new SmsManageVo();
						smv.setSubject("[????????????]");
						if(list.get(i).getStatus().equals("0004")){			//??????
							StringBuffer sb = new StringBuffer();
							sb.append(list.get(i).getJoin_name()+"???\n");
							sb.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"??? ?????? ??????\n");
							sb.append("-????????????-\n");
							sb.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
							//sb2.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"?????? ?????? ??????\n");
							sb.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"?????? ?????? ??????\n");
							sb.append("(????????? ?????????????????? ?????? ????????????.)");
							smv.setMsg(sb.toString());
							
							
							
							smv.setPhone(list.get(i).getTelno());
							smv.setEtc1(Integer.toString(vo.getCt_seq()));				//?????? ????????????
							smv.setEtc2(list.get(i).getPart());							//?????? ???
							smv.setEtc3(Integer.toString(list.get(i).getApp_seq()));	//?????? ????????????
							smv.setEtc5("kok_all"); 									//?????? ?????? ?????????
							smv.setEtc6(list.get(i).getReg_id());						//????????? ID
							smv.setEtc7(list.get(i).getJoin_name());					//????????? ???
							smv.setEtc8(list.get(i).getBirth());						//????????? ????????????
							smv.setEtc9(list.get(i).getStatus());						//?????? ??????
							
							
							
							smsList.add(smv);
						}
						/*
						else if(list.get(i).getStatus().equals("0005")){	//??????
							StringBuffer sb = new StringBuffer();
							sb.append(list.get(i).getJoin_name()+"???\n");
							sb.append(contestVo.getCt_sbj()+"/"+list.get(i).getPart()+"???/ ????????? ??????\n");
							sb.append("??????????????? ??????????????????.");
							smv.setMsg(sb.toString());
						}
						*/
						
					}
					
					if(smsList.size() > 0){
						result = SmsManageService.insertMmsBatch(smsList);
						
						ApageEventManageService.apageUpdateEventContestMsgSendDate(vo);
						ApageEventManageService.apageUpdateEventContestAppSendFlagAndDate(vo);
						
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
	
	//????????? ???????????? ??????
	@RequestMapping(value="/apage/event/kokContestLaneBoardDelete.do")
	public String kokContestLaneBoardDelete(@ModelAttribute("vo") apageBoardManageVo vo
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
    		return "redirect:/apage/event/kokContestLaneList.do";
		}		
		return "redirect:/apage/event/kokContestLaneList.do";
	}	
	
	
	
	
	

	/**
	 * 
	 * ?????? ???????????? ??????, ????????? ??????
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/apage/event/situationShowUpdate.do")
	public @ResponseBody ModelAndView situationShowUpdate(@ModelAttribute("vo") ApageEventManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) throws Exception {
		
		Map<String,Object> data 	= new HashMap<String,Object>();
		try{

			apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
			
			int result  = 0;
			if(mv != null && !mv.getMber_id().equals("") && vo != null && vo.getCt_seq() > 0){
				vo.setUpdt_id(mv.getMber_id());
				int updt_result = ApageEventManageService.apageEventCotnestAppSituationShowUpdate(vo);
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
	
	
	
	

	
	//???????????? ???????????? 2????????? ?????? ??????????????????
	@RequestMapping(value = "/apage/event/kokContestTopRankcerSecondWrite.do")
	public String kokContestTopRankcerSecondWrite(@ModelAttribute("vo") ApageEventManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		ApageEventManageVo svo = ApageEventManageService.selectEventContestManageDetail(vo);
		if(svo != null && vo.getCt_seq() > 0){
			model.addAttribute("ct_seq", vo.getCt_seq());
			model.addAttribute("contestVo", svo);
			model.addAttribute("memberinfo",mv);
			
			return "/apage/event/kokContestTopRankerSecondWrite";
		}else{
			return "redirect:/apage/event/kokContestMngList.do";
		}
	}
	
	


	//???????????? 2??? ?????? ????????? ???????????? ??????
	@RequestMapping(value="/apage/event/kokContestTopRankcerSecondExcelRead.do")
	@ResponseBody
	public ModelAndView kokContestTopRankcerSecondExcelRead(final MultipartHttpServletRequest multiRequest
											, @ModelAttribute("vo") ApageEventManageVo vo
											, ModelMap model
											, HttpServletRequest request
											, HttpServletResponse response
											, HttpSession session) throws Exception {
		
		try {

			
			
			int rtnVal = 0;
			String msg = "";
			List<FileManageVo> result = null;
			List<ApageEventManageVo> resultList = null;
			Map<String, MultipartFile> files = multiRequest.getFileMap();		
			if(!files.isEmpty()){
	  			//result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "kokCtTarget"); 	
	  			result = fileUtil.fileEventContestAppTargetExcel(request,files, "FILE_", "", 0, "kokCtSecondTarget"); 	
	  			
	  			if(result.size() != 0){
	  				resultList = kokContestTopRankcerSecondTargetExcelFileRead(result,request, vo.getCt_seq());
	  			}
			}	
	  		
			
			Map<String,Object> data	= new HashMap<String,Object>();
			if(resultList != null && resultList.size() > 0 ){
				data.put("resultList", resultList);
				data.put("resultCode","Y");
			}else{
				data.put("resultCode","N");
			}
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	
	/**
	 * ???????????? ????????? ???????????? ??????
	 * */
	public List<ApageEventManageVo> kokContestTopRankcerSecondTargetExcelFileRead(List<FileManageVo> fileList, HttpServletRequest request, int ct_seq) throws Exception{
		

		
		Map<String,Object> resultMap 	= new HashMap<String,Object>();
		List<ApageEventManageVo> resultList = new ArrayList();
		int returnCnt = 0;
		try {

			apageSystemManageVo codeVo = new apageSystemManageVo();
			
			 // ?????? ?????? ??????(.xlsx)
	  			
	  		String path = propertyService.getString("Globals.fileStorePath");
	  		for(FileManageVo fvo: fileList){
	  	  		File excelFile = new File(path + "/" + "kokCtSecondTarget" + "/" + fvo.getOrignl_file_nm());
	  	  		//origin_file_name ?????? ?????????
	  	  		String ofn = fvo.getOrignl_file_nm();
	  	  		ofn = ofn.substring(0,ofn.lastIndexOf("."));
	  	  		if(excelFile.exists()){

		  	  		StringBuffer sb = new StringBuffer();
		  	  		StringBuffer jsonSb = new StringBuffer();

		  	  		List<String> columnName = new ArrayList();
		  	  		
					// Create an object of filereader
					// class with CSV file as a parameter.
					
		  	  		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
		  	  		Cell cell = null;
		  	  		
		  	  		// ????????? sheet ?????? ??????
		  	  		sb.append("{\"data\" : [");
		  	  		int cnt = 0;
		  	  		
		  	  		int colCnt = 9;
		  	  		for (Row row : wb.getSheetAt(0)) {
			  	  		if(row.getRowNum() < 2) { //3??? ?????? ??????
		                    continue;
		                }
		  	  			jsonSb = new StringBuffer();
		  	  			if(row.getRowNum() != 2){
		  	  				jsonSb.append("{");
		  	  				jsonSb.append("\"ct_seq\":"+"\""+ct_seq+"\",");
		  	  			}
		  	  			for(int i = 0; i < colCnt; i++){
		  	  				if(row.getRowNum() == 2){
		  	  					String cellName = row.getCell(i).toString();
		  	  					columnName.add(cellName);
		  	  				}else{
		  	  					String json ="";
		  	  					String data = "";
		  	  					if(row.getCell(i) != null){
		  	  						row.getCell(i).setCellType(row.getCell(i).CELL_TYPE_STRING); //????????? ????????? ?????? ?????? String ??????
		  	  						data = row.getCell(i).getStringCellValue().toString();
		  	  						if(data != null && !data.equals("") && data.indexOf("\"") > 0){
		  	  							data = data.replaceAll("\"", "'");
		  	  						}
		  	  					}
		  	  					if(i == (colCnt-1)){
		  	  						json= "\""+columnName.get(i)+"\":\""+data+"\"";
		  	  					}else{
		  	  						json= "\""+columnName.get(i)+"\":\""+data+"\",";
		  	  					}
		  	  					jsonSb.append(json);
		  	  				}
		  	  			}
		  	  			if(row.getRowNum() != 2){
		  	  				if(wb.getSheetAt(0).getLastRowNum() -2 == cnt){
		  	  					jsonSb.append("}");
		  	  				}else{
		  	  					jsonSb.append("},");
		  	  				}
		  	  				
		  	  			}
		  	  			sb.append(jsonSb.toString());
		  	  			cnt++;
		  	  		}
	  	  			
	  	  		
		  	  		
		  	  		
		  	  		sb.append("]}");
		  	  		
		  	  		System.out.println(sb.toString());
		  	  		JsonParser parser = new JsonParser();
		  	  		Object obj = parser.parse(sb.toString());
		  	  		JsonObject jsonObj = (JsonObject) obj;
		  	  		JsonArray ja = (JsonArray)jsonObj.get("data");
		  	  		System.out.println("ja.size() ==> " + ja.size());
		  	  		for(int i = 0; i < ja.size(); i++){
		  	  			Gson gson = new Gson(); 
		  	  			resultList.add(gson.fromJson(ja.get(i), ApageEventManageVo.class));
		  	  		}
	  	  		}else{
	  	  			System.out.println("file Not Found");
	  	  		}
	  	  		
	  	  		
	  		}
			
		}catch(Exception e){
			System.out.println("read Game Excel Read Exception");
			e.printStackTrace();
		}
		
		
		return resultList;
		
	}
	
	

	//???????????? ?????? 2??? ?????? ????????? ??????
	@RequestMapping(value="/apage/event/kokContestTopRankcerSecondReg.do")
	public String kokContestTopRankcerSecondReg(@ModelAttribute("vo") ApageEventManageVo vo
							, ModelMap model
							, HttpServletRequest request
							, HttpServletResponse response
							, HttpSession session
							) throws Exception {
		
		int rtnVal = 0;
		String msg = "";
		
		try {
			rtnVal= ApageEventManageService.insertEventContestAppSecondTarget(vo);
		} catch (DuplicateKeyException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
		// TODO: handle exception
			e.printStackTrace();
		}
		if(rtnVal > 0) {
			if(rtnVal == 9999999){
				model.addAttribute("msg", "duplicate.success");	
			}else{
				model.addAttribute("msg", "insert.success");	
			}
			return "redirect:/apage/event/kokContestMngList.do";
		} else {
			model.addAttribute("msg", "insert.fail");
			return "forward:/apage/event/kokContestTopRankcerSecondWrite.do";
		}
	}

	
	
	
	
	
	//???????????? ?????? ??????????????? ????????????
	@RequestMapping(value = "/apage/event/kokContestAppListExcel.do")
	public String kokContestAppListExcel(@ModelAttribute("vo") ApageEventManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {
		
		ApageEventManageVo svo = ApageEventManageService.selectEventContestManageDetail(vo);
		//ApageEventManageVo svo= apageBoardManageService.lb_getAdminContestView(vo);
		
		List<ApageEventManageVo> list = ApageEventManageService.selectAdminEventContestAppExcel(vo);
		//List<ApageEventManageVo> list = apageBoardManageService.selectAdminContestAppExcel(vo);		
		
		model.addAttribute("contestView", svo);
		model.addAttribute("contestAppList",list);
		
		return "/apage/event/kokContestMngAppExcel";
	}	
	
	

	//??????????????? ??????????????? ????????????
	@RequestMapping(value = "/apage/event/kokContestSelectResulExcel.do")
	public String kokContestSelectResulExcel(@ModelAttribute("vo") ApageEventManageVo evo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
	

		List<List<ApageEventManageVo>> resultList = new ArrayList<List<ApageEventManageVo>>();
		List<List<ApageEventManageVo>> resultList2 = new ArrayList<List<ApageEventManageVo>>();
		
		List<List<ApageEventManageVo>> readyResultList = new ArrayList<List<ApageEventManageVo>>();
		
		ApageEventManageVo contestVo = ApageEventManageService.selectEventContestManageDetail(evo);
		if(contestVo != null && contestVo.getCt_type() != null && contestVo.getCt_type().equals("K")){
			contestVo.setPart("1");
			List<ApageEventManageVo> list = ApageEventManageService.selectEventContestSelectResultExcel(contestVo);		
			resultList.add(list);
			model.addAttribute("part1List",list);
			
			contestVo.setPart("2");
			List<ApageEventManageVo> list2 = ApageEventManageService.selectEventContestSelectResultExcel(contestVo);		
			resultList2.add(list2);
			model.addAttribute("part2List",list2);
			
			
		}
		model.addAttribute("contestVo", contestVo);
		
		model.addAttribute("boardView", evo);
		model.addAttribute("resultList",resultList);
		model.addAttribute("readyResultList",readyResultList);
		
	
	
		return "/apage/event/kokContestSelectResultExcel";
	}
	
	
	
}