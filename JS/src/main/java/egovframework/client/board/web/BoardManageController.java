package egovframework.client.board.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import egovframework.apage.board.service.apageBoardManageService;
import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.client.board.service.BoardManageService;
import egovframework.client.board.service.BoardManageVo;
import egovframework.client.member.service.ShopMemberManageVo;
import egovframework.common.core.ResultDataManager;
import egovframework.common.core.SendMiPlatform;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.utils.CommonUtil;
import egovframework.common.utils.EgovFileMngUtil;
import egovframework.common.utils.PageVo;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class BoardManageController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "BoardManageService")
	private BoardManageService BoardManageService;
	
	@Resource(name = "apageBoardManageService")
	private apageBoardManageService apageBoardManageService;	
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name = "CommonUtil")
	private CommonUtil CommonUtil;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	@Resource(name = "FileManageService")
	private FileManageService fileManageService;
	
	//네이버 캡챠
	static String clientId = "aa4R8Pa8LxYaeTZSBgHV";//애플리케이션 클라이언트 아이디값";
    static String clientSecret = "abwmr3sW94";
	
	//공지사항 리스트
	@RequestMapping(value="/board/noticeList.do")
	public String noticeList(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		
		//게시판 구분값
		vo.setBbs_id("notice");
		List<BoardManageVo> TopBoardList = BoardManageService.lb_getTopBoardList(vo);
		List<BoardManageVo> list = BoardManageService.lb_selectBoardList(vo);		
		int totCnt = BoardManageService.lb_selectBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("TopBoardList", TopBoardList);
		model.addAttribute("noticeList", list);
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "1");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/board/noticeList";
	}
	
	
	// 공지사항 상세
	@RequestMapping(value="/board/noticeDetail.do")
	public String noticeDetail(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		vo.setBbs_id("notice");
		BoardManageVo next = BoardManageService.getNextBoardView(vo);
		model.addAttribute("next", next);
		
		vo.setBbs_id("notice");
		BoardManageVo pre = BoardManageService.getPreBoardView(vo);
		model.addAttribute("pre", pre);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> noticeFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("noticeFile", noticeFile);
		}
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "1");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/board/noticeView";
	}
	
	//이벤트 리스트
	@RequestMapping(value="/board/eventList.do")
	public String eventList(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		
		//게시판 구분값
		vo.setBbs_id("event");
		List<BoardManageVo> TopBoardList = BoardManageService.getTopBoardList(vo);
		List<BoardManageVo> list = BoardManageService.selectBoardList(vo);		
		int totCnt = BoardManageService.selectBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("TopBoardList", TopBoardList);
		model.addAttribute("eventList", list);
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "2");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/board/eventList";
	}
	
	//이벤트 상세
	@RequestMapping(value="/board/eventDetail.do")
	public String eventDetail(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		vo.setBbs_id("event");
		BoardManageVo next = BoardManageService.getNextBoardView(vo);		
		BoardManageVo pre = BoardManageService.getPreBoardView(vo);
		model.addAttribute("next", next);
		model.addAttribute("pre", pre);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> noticeFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("noticeFile", noticeFile);
		}
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "2");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/board/eventView";
	}	
	
	//as 리스트
	@RequestMapping(value="/board/asList.do")
	public String asList(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		
		//게시판 구분값
		vo.setBbs_id("as");
		List<BoardManageVo> list = BoardManageService.lb_selectBoardList(vo);		
		int totCnt = BoardManageService.lb_selectBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("asList", list);
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "3");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/board/asList";
	}
	
	//as 작성페이지
	@RequestMapping(value="/board/asWrite.do")
	public String asWrite(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {

		//session check - 190111
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		if (null == mv || mv.getMber_id().equals("")){
			model.addAttribute("msg", "login.fail");
			return "redirect:/board/asList.do";		
		}
		
		if (request.getParameter("ntt_cn") != null || request.getParameter("ntt_cn") != "") {
			String ntt_cn = request.getParameter("ntt_cn");
			model.addAttribute("ntt_cn", ntt_cn);
		}
		
		//네이버 캡챠
		String captchaKey = captchaKeyReturn();
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(captchaKey);
		JsonObject jsonObj = (JsonObject) obj;
		
		String cKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
		String captchaURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + cKey;
		
		String captchaAudioKey = captchaKeyReturnAudio();
		obj = parser.parse(captchaAudioKey);
		jsonObj = (JsonObject) obj;
		String aKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
		String captchaAudioURL = "https://openapi.naver.com/v1/captcha/scaptcha?key=" + aKey;
		
		model.addAttribute("captchaURL", captchaURL);
		model.addAttribute("captchaKEY", cKey);
		model.addAttribute("captchaAudioURL", captchaAudioURL);
		model.addAttribute("captchaAudioKEY", aKey);
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "3");

		return "/client/board/asWrite";
	}	
	
	

	/**
	 * as 등록
	 *
	 * @param multiRequest,vo,request,response,session,model,RedirectAttributes
	 * @return String
	 */
	@RequestMapping("/board/asReg.do")
	public String asReg(final MultipartHttpServletRequest multiRequest
								,@ModelAttribute("vo") BoardManageVo vo
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session
								, ModelMap model) throws Exception{
		
		int rtnVal = 0;
		String msg = "";
		try{
			
			//session check - 190111
			ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
			if (null == mv || mv.getMber_id().equals("")){
				model.addAttribute("msg", "insert.fail");
				return "redirect:/board/asList.do";		
			}

			if(vo.getCaptcha_type().equals("img")){
				String captchaCompareResult = captchaCompareResult(vo.getCaptcha_key(),vo.getCaptcha_answer());
				if(captchaCompareResult == null || captchaCompareResult.equals("") || captchaCompareResult.equals("error")){
					model.addAttribute("captchaResult", "error");
					return "forward:/board/asWrite.do";
				}else{
					JsonParser parser = new JsonParser();
					Object obj = parser.parse(captchaCompareResult);
					JsonObject jsonObj = (JsonObject) obj;
					
					String captchaResult = jsonObj.get("result").toString();
					if(!captchaResult.equals("true")){
						model.addAttribute("captchaResult", "fail");
						return "forward:/board/asWrite.do";
					}
				}
			}else{
				String captchaCompareResultAudio = captchaCompareResultAudio(vo.getCaptcha_key_audio(),vo.getCaptcha_answer());
				if(captchaCompareResultAudio == null || captchaCompareResultAudio.equals("") || captchaCompareResultAudio.equals("error")){
					model.addAttribute("captchaResult", "error");
					return "forward:/board/asWrite.do";
				}else{
					JsonParser parser = new JsonParser();
					Object obj = parser.parse(captchaCompareResultAudio);
					JsonObject jsonObj = (JsonObject) obj;
					
					String captchaResult = jsonObj.get("result").toString();
					if(!captchaResult.equals("true")){
						model.addAttribute("captchaResult", "fail");
						return "forward:/board/asWrite.do";
					}
				}
			}
			
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
			rtnVal = BoardManageService.insertBoard(vo);
			if(rtnVal > 0) {
				model.addAttribute("msg", "insert.success");
			} else {
				model.addAttribute("msg", "insert.fail");
				return "forward:/board/asWrite.do";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "forward:/board/asWrite.do";				
		}
		
		return "redirect:/board/asList.do";
	}
	
	//as 상세
	@RequestMapping(value="/board/asDetail.do")
	public String asDetail(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);		
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		vo.setBbs_id("as");		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> asfile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("asfile", asfile);
		}
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "3");		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/board/asView";
	}	
	
	//as 수정페이지
	@RequestMapping(value="/board/asModify.do")
	public String asModify(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		
		if (request.getParameter("ntt_cn") != null || request.getParameter("ntt_cn") != "") {
			String ntt_cn_new = request.getParameter("ntt_cn");
			model.addAttribute("ntt_cn_new", ntt_cn_new);
		}
		
		//네이버 캡챠
		String captchaKey = captchaKeyReturn();
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(captchaKey);
		JsonObject jsonObj = (JsonObject) obj;
		
		String cKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
		String captchaURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + cKey;
		
		String captchaAudioKey = captchaKeyReturnAudio();
		obj = parser.parse(captchaAudioKey);
		jsonObj = (JsonObject) obj;
		String aKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
		String captchaAudioURL = "https://openapi.naver.com/v1/captcha/scaptcha?key=" + aKey;
		
		model.addAttribute("captchaURL", captchaURL);
		model.addAttribute("captchaKEY", cKey);
		model.addAttribute("captchaAudioURL", captchaAudioURL);
		model.addAttribute("captchaAudioKEY", aKey);
		
		//상세
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		FileManageVo fvo = new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> asFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("asFile", asFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("boardView", svo);
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "3");
		
		return "/client/board/asUpdt";
	}
	
	//첨부파일 삭제
	@RequestMapping(value="/board/fileDel.do")
	public String fileDel(@ModelAttribute("vo") BoardManageVo vo
						, @ModelAttribute("bvo") apageBoardManageVo bvo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		String real =  propertyService.getString("Globals.fileStorePath");
		String Path = "as";
		FileManageVo fileVO = new FileManageVo();
		
		String file_id = vo.getAtch_file_id2();
		String file_id2 = vo.getAtch_file_id2().substring(0, 22)+" ";		
		
		fileVO.setAtch_file_id(file_id); //fileVO.setAtch_file_id(vo.getAtch_file_id2());
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
			apageBoardManageService.setBoardAttachUpdt2(bvo);
		}
		
		return this.asModify(vo, model, request, response);
	}
	
	/**
	 * as 수정
	 *
	 * @param multiRequest,vo,request,response,session,model,RedirectAttributes
	 * @return String
	 */
	@RequestMapping("/board/asUpdt.do")
	public String asUpdt(final MultipartHttpServletRequest multiRequest
								,@ModelAttribute("vo") BoardManageVo vo
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session
								, ModelMap model) throws Exception{
		
		int rtnVal = 0;
		String msg = "";
		try{

			//네이버 캡챠
			if(vo.getCaptcha_type().equals("img")){
				String captchaCompareResult = captchaCompareResult(vo.getCaptcha_key(),vo.getCaptcha_answer());
				if(captchaCompareResult == null || captchaCompareResult.equals("") || captchaCompareResult.equals("error")){
					model.addAttribute("captchaResult", "error");
					return "forward:/board/asModify.do";
				}else{
					JsonParser parser = new JsonParser();
					Object obj = parser.parse(captchaCompareResult);
					JsonObject jsonObj = (JsonObject) obj;
					
					String captchaResult = jsonObj.get("result").toString();
					if(!captchaResult.equals("true")){
						model.addAttribute("captchaResult", "fail");
						return "forward:/board/asModify.do";
					}
				}
			}else{
				String captchaCompareResultAudio = captchaCompareResultAudio(vo.getCaptcha_key_audio(),vo.getCaptcha_answer());
				if(captchaCompareResultAudio == null || captchaCompareResultAudio.equals("") || captchaCompareResultAudio.equals("error")){
					model.addAttribute("captchaResult", "error");
					return "forward:/board/asModify.do";
				}else{
					JsonParser parser = new JsonParser();
					Object obj = parser.parse(captchaCompareResultAudio);
					JsonObject jsonObj = (JsonObject) obj;
					
					String captchaResult = jsonObj.get("result").toString();
					if(!captchaResult.equals("true")){
						model.addAttribute("captchaResult", "fail");
						return "forward:/board/asModify.do";
					}
				}
			}
			
			//첨부파일
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
			
			//수정
			rtnVal = BoardManageService.updateBoard(vo);
			if(rtnVal > 0) {
				model.addAttribute("msg", "insert.success");
			} else {
				model.addAttribute("msg", "insert.fail");
				return "forward:/board/asModify.do";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "forward:/board/asModify.do";				
		}
		return "redirect:/board/asList.do";
	}
	
	//as 삭제
	@RequestMapping(value="/board/asDelete.do")
	public String asDelete(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		int result;
		
		try{
			result = BoardManageService.BoardDelete(vo);
			
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
			
			model.addAttribute("msg", "delete.success");
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "delete.fail");
    		return "/client/board/asView";
		}
		return "redirect:/board/asList.do";
	}
	
	//사용자 댓글 작성 (as, consult)
	@RequestMapping(value = "/board/insertBoardComment.do")
	public void insertBoardComment(@ModelAttribute("frm") apageBoardManageVo vo,
					   			HttpServletRequest request, 
					   			HttpServletResponse response, 
					   			HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;
		System.out.println("####### " + session.getAttribute("mber_id"));
		
		if(session.getAttribute("mber_id") != null ) {
			vo.setReg_id((String)session.getAttribute("mber_id"));
			rtnVal = apageBoardManageService.insertBoardComment(vo);		
			if(rtnVal > 0) {
				rval = true;
			}
		
		} else {
			throw new Exception("session is null");
		}
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print(rval);
		writer.flush();
		writer.close();	
	}
	
	//댓글 조회
	@RequestMapping(value="/board/commentListJson.do")
	@ResponseBody
	public ModelAndView commentListJson(@ModelAttribute("aForm") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		try{
			//ResultDataManager 여러 LIST를 별도의 LIST2에 추가
			ResultDataManager rm = new ResultDataManager();				
			List<apageBoardManageVo> list = apageBoardManageService.selectBoardComment(vo);		
	
			rm.setData(apageBoardManageVo.class, list);
			//SendMiPlatform.SendData 에서 LIST2의 값을 JSON으로 변경 및 출력
			return SendMiPlatform.SendData(response, rm);
			}catch(Exception e){
			return SendMiPlatform.ErrorData(response, e.getMessage());
			}	
	}	
	
	//댓글 삭제
	@RequestMapping(value = "/board/deleteBoardComment.do")
	public void deleteBoardComment(@ModelAttribute("aForm") apageBoardManageVo vo,
					   			HttpServletRequest request, 
					   			HttpServletResponse response, 
					   			HttpSession session) throws Exception {
		
		int rtnVal = 0;
		boolean rval = false;
		if(session.getAttribute("mber_id") != null ) { //세션  유무 체크
			vo.setReg_id((String)session.getAttribute("mber_id"));
			rtnVal = apageBoardManageService.deleteBoardComment(vo);
			
			if(rtnVal > 0) {
				rval = true;
			}
		} else {
			throw new Exception("session is null");
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
	
	
	
	
	// sns 리스트
	@RequestMapping(value="/board/snsList.do")
		public String snsList(ModelMap model
				 , HttpServletRequest request
				 , HttpServletResponse response) { 
			
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "5");
		
		
		return "/client/board/snsList";
	}

	
	
	
	
	
	//고객상담 리스트
	@RequestMapping(value="/board/consultList.do")
	public String consultList(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		
		//게시판 구분값
		vo.setBbs_id("consult");
		List<BoardManageVo> list = BoardManageService.lb_selectBoardList(vo);		
		int totCnt = BoardManageService.lb_selectBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("consultList", list);
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "4");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/board/consultList";
	}
	
	
	//고객상담 작성페이지
	@RequestMapping(value="/board/consultWrite.do")
	public String consultWrite(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {

		//session check - 190111
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		if (null == mv || mv.getMber_id().equals("")){
			model.addAttribute("msg", "login.fail");
			return "redirect:/board/consultList.do";		
		}
				
		//네이버 캡챠
		String captchaKey = captchaKeyReturn();
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(captchaKey);
		JsonObject jsonObj = (JsonObject) obj;
		
		String cKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
		String captchaURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + cKey;
		
		String captchaAudioKey = captchaKeyReturnAudio();
		obj = parser.parse(captchaAudioKey);
		jsonObj = (JsonObject) obj;
		String aKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
		String captchaAudioURL = "https://openapi.naver.com/v1/captcha/scaptcha?key=" + aKey;
		
		model.addAttribute("captchaURL", captchaURL);
		model.addAttribute("captchaKEY", cKey);
		model.addAttribute("captchaAudioURL", captchaAudioURL);
		model.addAttribute("captchaAudioKEY", aKey);
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "4");

		return "/client/board/consultWrite";
	}	
	
	

	/**
	 * 고객상담 등록
	 *
	 * @param multiRequest,vo,request,response,session,model,RedirectAttributes
	 * @return String
	 */
	@RequestMapping("/board/consultReg.do")
	public String consultReg(final MultipartHttpServletRequest multiRequest
								,@ModelAttribute("vo") BoardManageVo vo
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session
								, ModelMap model) throws Exception{
		
		int rtnVal = 0;
		String msg = "";
		try{
			
			//session check - 190111
			ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
			if (null == mv || mv.getMber_id().equals("")){
				model.addAttribute("msg", "insert.fail");
				return "redirect:/board/consultList.do";		
			}

			if(vo.getCaptcha_type().equals("img")){
				String captchaCompareResult = captchaCompareResult(vo.getCaptcha_key(),vo.getCaptcha_answer());
				if(captchaCompareResult == null || captchaCompareResult.equals("") || captchaCompareResult.equals("error")){
					model.addAttribute("captchaResult", "error");
					return "forward:/board/consultWrite.do";
				}else{
					JsonParser parser = new JsonParser();
					Object obj = parser.parse(captchaCompareResult);
					JsonObject jsonObj = (JsonObject) obj;
					
					String captchaResult = jsonObj.get("result").toString();
					if(!captchaResult.equals("true")){
						model.addAttribute("captchaResult", "fail");
						return "forward:/board/consultWrite.do";
					}
				}
			}else{
				String captchaCompareResultAudio = captchaCompareResultAudio(vo.getCaptcha_key_audio(),vo.getCaptcha_answer());
				if(captchaCompareResultAudio == null || captchaCompareResultAudio.equals("") || captchaCompareResultAudio.equals("error")){
					model.addAttribute("captchaResult", "error");
					return "forward:/board/consultWrite.do";
				}else{
					JsonParser parser = new JsonParser();
					Object obj = parser.parse(captchaCompareResultAudio);
					JsonObject jsonObj = (JsonObject) obj;
					
					String captchaResult = jsonObj.get("result").toString();
					if(!captchaResult.equals("true")){
						model.addAttribute("captchaResult", "fail");
						return "forward:/board/consultWrite.do";
					}
				}
			}
			
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
			rtnVal = BoardManageService.insertBoard(vo);
			if(rtnVal > 0) {
				model.addAttribute("msg", "insert.success");
			} else {
				model.addAttribute("msg", "insert.fail");
				return "forward:/board/consultWrite.do";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "forward:/board/consultWrite.do";				
		}
		
		return "redirect:/board/consultList.do";
	}
	
	//고객상담 상세
	@RequestMapping(value="/board/consultDetail.do")
	public String consultDetail(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);		
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		vo.setBbs_id("as");		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> consultfile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("consultfile", consultfile);
		}
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "4");		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/board/consultView";
	}	
	
	//고객상담 수정페이지
	@RequestMapping(value="/board/consultModify.do")
	public String consultModify(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		
		//네이버 캡챠
		String captchaKey = captchaKeyReturn();
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(captchaKey);
		JsonObject jsonObj = (JsonObject) obj;
		
		String cKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
		String captchaURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + cKey;
		
		String captchaAudioKey = captchaKeyReturnAudio();
		obj = parser.parse(captchaAudioKey);
		jsonObj = (JsonObject) obj;
		String aKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
		String captchaAudioURL = "https://openapi.naver.com/v1/captcha/scaptcha?key=" + aKey;
		
		model.addAttribute("captchaURL", captchaURL);
		model.addAttribute("captchaKEY", cKey);
		model.addAttribute("captchaAudioURL", captchaAudioURL);
		model.addAttribute("captchaAudioKEY", aKey);
		
		//상세
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		FileManageVo fvo = new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> consultFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("consultFile", consultFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("boardView", svo);
		
		model.addAttribute("menuDepth1", "3");
		model.addAttribute("menuDepth2", "3");
		
		return "/client/board/consultUpdt";
	}
	
	//고객상담 수정
	@RequestMapping("/board/consultUpdt.do")
	public String consultUpdt(final MultipartHttpServletRequest multiRequest
								,@ModelAttribute("vo") BoardManageVo vo
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session
								, ModelMap model) throws Exception{
		
		int rtnVal = 0;
		String msg = "";
		try{

			//네이버 캡챠
			if(vo.getCaptcha_type().equals("img")){
				String captchaCompareResult = captchaCompareResult(vo.getCaptcha_key(),vo.getCaptcha_answer());
				if(captchaCompareResult == null || captchaCompareResult.equals("") || captchaCompareResult.equals("error")){
					model.addAttribute("captchaResult", "error");
					return "forward:/board/consultModify.do";
				}else{
					JsonParser parser = new JsonParser();
					Object obj = parser.parse(captchaCompareResult);
					JsonObject jsonObj = (JsonObject) obj;
					
					String captchaResult = jsonObj.get("result").toString();
					if(!captchaResult.equals("true")){
						model.addAttribute("captchaResult", "fail");
						return "forward:/board/consultModify.do";
					}
				}
			}else{
				String captchaCompareResultAudio = captchaCompareResultAudio(vo.getCaptcha_key_audio(),vo.getCaptcha_answer());
				if(captchaCompareResultAudio == null || captchaCompareResultAudio.equals("") || captchaCompareResultAudio.equals("error")){
					model.addAttribute("captchaResult", "error");
					return "forward:/board/consultModify.do";
				}else{
					JsonParser parser = new JsonParser();
					Object obj = parser.parse(captchaCompareResultAudio);
					JsonObject jsonObj = (JsonObject) obj;
					
					String captchaResult = jsonObj.get("result").toString();
					if(!captchaResult.equals("true")){
						model.addAttribute("captchaResult", "fail");
						return "forward:/board/consultModify.do";
					}
				}
			}
			
			//첨부파일
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
			
			//수정
			rtnVal = BoardManageService.updateBoard(vo);
			if(rtnVal > 0) {
				model.addAttribute("msg", "insert.success");
			} else {
				model.addAttribute("msg", "insert.fail");
				return "forward:/board/consultModify.do";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "forward:/board/consultModify.do";				
		}
		return "redirect:/board/consultList.do";
	}
	
	//고객상담 삭제
	@RequestMapping(value="/board/consultDelete.do")
	public String consultDelete(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		int result;
		
		try{
			result = BoardManageService.BoardDelete(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "consult";
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
			
			model.addAttribute("msg", "delete.success");
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "delete.fail");
    		return "/client/board/consultView";
		}
		return "redirect:/board/consultList.do";
	}
	
	
	//staff 리스트
	@RequestMapping(value="/board/staffList.do")
	public String staffList(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		/*
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		*/
		//게시판 구분값
		vo.setAsso_type("KPBA");
		List<BoardManageVo> list = BoardManageService.lb_selectStaffList(vo);		
		int totCnt = BoardManageService.lb_selectStaffListCnt(vo);
		
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		for(int i = 0;i < list.size();i++) {
			String atch_file_id = list.get(i).getAtch_file_id();
			if(atch_file_id != null && atch_file_id != ""){
				fvo.setAtch_file_id(atch_file_id.substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.lb_getFileAttachList(fvo);
				
				if(imgFile.size() != 0) {
					list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
				}else {
					list.get(i).setAtch_file_id("");
				}
			}
		}
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("asso_type", vo.getAsso_type());
		
		model.addAttribute("staffList", list);
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "4");
		model.addAttribute("menuDepth2", "3");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/board/staffList";
	}	
	
	//staff 상세
	@RequestMapping(value="/board/staffDetail.do")
	public String staffDetail(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.staffCnt(vo);
		BoardManageVo svo= BoardManageService.getStaffView(vo);
			
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
		
		model.addAttribute("menuDepth1", "4");
		model.addAttribute("menuDepth2", "3");		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("asso_type", vo.getAsso_type());
		model.addAttribute("staffView", svo);
		
		return "/client/board/staffView";
	}	
	
	//레인정비기계 리스트
	@RequestMapping(value="/kegel/laneMachines.do")
	public String laneMachines(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		pVo.setCntPerPage(8);
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		
		//게시판 구분값
		vo.setBbs_id("lane");
		List<BoardManageVo> list = BoardManageService.lb_selectBoardList(vo);		
		int totCnt = BoardManageService.lb_selectBoardListCnt(vo);
		
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		for(int i = 0;i < list.size();i++) {
			String atch_file_id = list.get(i).getAtch_file_id();
			if(atch_file_id != null && atch_file_id != ""){
				fvo.setAtch_file_id(atch_file_id.substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.lb_getFileAttachList(fvo);
				
				if(imgFile.size() != 0) {
					list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
				}else {
					list.get(i).setAtch_file_id("");
				}
			}
		}
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("laneList", list);
		model.addAttribute("totalNum", totCnt);		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "1");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/board/laneMachines";
	}
	
	
	//레인정비기계 상세
	@RequestMapping(value="/kegel/laneMachinesView.do")
	public String laneMachinesView(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
			
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
		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "1");		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/board/laneMachinesView";
	}
	
	
	//레인정비소모품 리스트
	@RequestMapping(value="/kegel/laneConsumable.do")
	public String laneConsumable(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		pVo.setCntPerPage(8);
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);
		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		
		/*
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		*/
		//게시판 구분값
		vo.setBbs_id("lanesupp");
		List<BoardManageVo> list = BoardManageService.lb_selectBoardList(vo);		
		int totCnt = BoardManageService.lb_selectBoardListCnt(vo);
		
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		for(int i = 0;i < list.size();i++) {
			String atch_file_id = list.get(i).getAtch_file_id();
			if(atch_file_id != null && atch_file_id != ""){
				fvo.setAtch_file_id(atch_file_id.substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.lb_getFileAttachList(fvo);
				
				if(imgFile.size() != 0) {
					list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
				}else {
					list.get(i).setAtch_file_id("");
				}
			}
		}
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("laneList", list);
		model.addAttribute("totalNum", totCnt);		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "2");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		
		return "/client/board/laneConsumable";
	}
	
	
	//레인정비소모품 상세
	@RequestMapping(value="/kegel/laneConsumableView.do")
	public String laneConsumableView(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
			
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
		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "2");		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/board/laneConsumableView";
	}
	
	
	
	//훈련 도구 리스트
	@RequestMapping(value="/kegel/trainingList.do")
	public String training(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		pVo.setCntPerPage(8);
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);
		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		/*
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		*/
		//게시판 구분값
		vo.setBbs_id("training");
		List<BoardManageVo> list = BoardManageService.lb_selectBoardList(vo);		
		int totCnt = BoardManageService.lb_selectBoardListCnt(vo);
		
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		for(int i = 0;i < list.size();i++) {
			String atch_file_id = list.get(i).getAtch_file_id();
			if(atch_file_id != null && atch_file_id != ""){
				fvo.setAtch_file_id(atch_file_id.substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.lb_getFileAttachList(fvo);
				
				if(imgFile.size() != 0) {
					list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
				}else {
					list.get(i).setAtch_file_id("");
				}
			}
		}
		
//		검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
//		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("laneList", list);
		model.addAttribute("totalNum", totCnt);		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "3");
	
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		
		return "/client/board/trainingList";
		     
	}
	
	
	
	// 훈련 도구 리스트 상세
	@RequestMapping(value="/kegel/trainingView.do")
	public String trainingView(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
			
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
		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "3");		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/board/trainingView";
	}
	
	
	//대회안내 리스트
	@RequestMapping(value="/contest/contestInfo.do")
	public String contestInfo(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		/*
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		*/
		//공지사항 topList 불러오기 - 190108 추가
		vo.setBbs_id("notice");
		List<BoardManageVo> TopBoardList = BoardManageService.lb_getTopBoardList(vo);
		model.addAttribute("TopBoardList", TopBoardList);
		
		//게시판 구분값
		vo.setBbs_id("contest");
		List<BoardManageVo> TopContestList = BoardManageService.lb_getTopBoardList(vo);
		List<BoardManageVo> list = BoardManageService.lb_selectBoardList(vo);		
		int totCnt = BoardManageService.lb_selectBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("TopContestList", TopContestList);
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "2");
		model.addAttribute("menuDepth2", "1");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/contest/contestInfo";
	}
	
	
	//대회안내 상세
	@RequestMapping(value="/contest/contestInfoView.do")
	public String contestInfoView(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		vo.setBbs_id("contest");
		BoardManageVo next = BoardManageService.getNextBoardView(vo);
		model.addAttribute("next", next);
		
		vo.setBbs_id("contest");
		BoardManageVo pre = BoardManageService.getPreBoardView(vo);
		model.addAttribute("pre", pre);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		model.addAttribute("menuDepth1", "2");
		model.addAttribute("menuDepth2", "1");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("contestView", svo);
		
		return "/client/contest/contestInfoView";
	}
	
	
	//대회결과 리스트
	@RequestMapping(value="/contest/contestRst.do")
	public String contestRst(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		pVo.setCntPerPage(8);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
	/*	
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
	*/	
		//게시판 구분값
		vo.setBbs_id("contestRst");
		List<BoardManageVo> list = BoardManageService.lb_selectBoardList(vo);		
		int totCnt = BoardManageService.lb_selectBoardListCnt(vo);
		
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		for(int i = 0;i < list.size();i++) {
			String atch_file_id = list.get(i).getAtch_file_id();
			if(atch_file_id != null && atch_file_id != ""){
				fvo.setAtch_file_id(atch_file_id.substring(0, 22));
				fvo.setFile_gu("I");
				List<FileManageVo> imgFile	= fileManageService.lb_getFileAttachList(fvo);
				
				if(imgFile.size() != 0) {
					list.get(i).setAtch_file_id(imgFile.get(0).getAtch_file_id());
				}else {
					list.get(i).setAtch_file_id("");
				}
			}
		}
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("contestRstList", list);
		model.addAttribute("totalNum", totCnt);		
		model.addAttribute("menuDepth1", "2");
		model.addAttribute("menuDepth2", "3");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/contest/contestRstList";
	}
	
	//대회결과 상세
	@RequestMapping(value="/contest/contestRstView.do")
	public String contestRstView(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);		
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		vo.setBbs_id("contestRst");
		BoardManageVo next = BoardManageService.getNextBoardView(vo);
		model.addAttribute("next", next);
		
		vo.setBbs_id("contestRst");
		BoardManageVo pre = BoardManageService.getPreBoardView(vo);
		model.addAttribute("pre", pre);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			//fvo.setFile_gu("F");
			List<FileManageVo> addFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("addFile", addFile);
		}
		
		model.addAttribute("menuDepth1", "2");
		model.addAttribute("menuDepth2", "3");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("contestRstView", svo);
		
		return "/client/contest/contestRstView";
	}
	
	//대회신청 리스트
	@RequestMapping(value="/contest/contestAppList.do")
	public String contestAppList(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow(endPageNum);
		
		BoardManageVo rvo = new BoardManageVo();
		rvo.setSrch_input(vo.getSrch_input());
		rvo.setScType(vo.getScType());

		rvo.setCurrRow(startPageNum-1);
		rvo.setEndRow(endPageNum);
		// rvo.setScType("R");
		List<BoardManageVo> rsList = BoardManageService.lb_selectContestList(rvo);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		int r_flag = 0;
		for(int i = 0; i < rsList.size(); i++){
			
			if(rsList.get(i) != null 
					&& rsList.get(i).getApp_start_dt() != null && rsList.get(i).getApp_start_h() != null && rsList.get(i).getApp_start_m() != null
					&& rsList.get(i).getApp_end_dt() != null && rsList.get(i).getApp_end_h() != null && rsList.get(i).getApp_end_m() != null
			){
				String start_dt = rsList.get(i).getApp_start_dt().replaceAll("-", "") + rsList.get(i).getApp_start_h() + rsList.get(i).getApp_start_m();
				String end_dt = rsList.get(i).getApp_end_dt().replaceAll("-", "") + rsList.get(i).getApp_end_h() + rsList.get(i).getApp_end_m();
				
				Date start_time = dateFormat.parse(start_dt);
				Date end_time = dateFormat.parse(end_dt);
				Date now = new Date();
				
				BoardManageVo flagVo = new BoardManageVo();
				if(now.getTime() >= start_time.getTime() && now.getTime() <= end_time.getTime()){	//신청가능
					if(rsList.get(i).getCt_process().equals("R")){
						flagVo.setCt_process("S");
						flagVo.setCt_seq(rsList.get(i).getCt_seq());
						int result1 = BoardManageService.updateContestManageFlag(flagVo);
						
						r_flag = 1;
					};
				}
				/*
				else if(now.getTime() >= end_time.getTime()){	//신청기간 초과
					flagVo.setCt_process("E");
					flagVo.setCt_seq(rsList.get(i).getCt_seq());
					int result1 = BoardManageService.updateContestManageFlag(flagVo);
				}
				*/
				
			}
		}
		if(r_flag > 0 ){
			List<BoardManageVo> list = BoardManageService.lb_selectContestList(vo);
			model.addAttribute("contestList", list);
		}else{
			model.addAttribute("contestList", rsList);
			
		}
		int totCnt = BoardManageService.lb_selectContestListCnt(vo);
		
		
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());

		
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "7");
		model.addAttribute("menuDepth2", "1");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/contest/contestAppList";
	}
	
	
	//대회신청 상세
	@RequestMapping(value="/contest/contestAppView.do")
	public String contestAppView(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {

		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		//BoardManageService.UpdateContestCnt(vo);
		
		BoardManageVo svo= BoardManageService.lb_getContestView(vo);
		
		if(svo != null && (svo.getCt_process().equals("R") || svo.getCt_process().equals("S"))){
			//첨부파일 리스트 가져오기
			if(svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
				fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
				List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
				model.addAttribute("contestFile", contestFile);
			}
			
			model.addAttribute("menuDepth1", "7");
			model.addAttribute("menuDepth2", "1");
			
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("scType", vo.getScType());
			model.addAttribute("contestView", svo);
			
			return "/client/contest/contestAppView";
		}else{
			model.addAttribute("menuDepth1", "7");
			model.addAttribute("menuDepth2", "1");
			
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("scType", vo.getScType());
			
			model.addAttribute("msg", "신청가능 상태가 아닙니다.");
			return "forward:/contest/contestAppList.do";
		}
	
	}	
	
	//대회 레인배치 결과페이지
	@RequestMapping(value="/contest/contestLaneResult.do")
	public String contestLaneResult(@ModelAttribute("vo") BoardManageVo vo
												, ModelMap model
												, HttpServletRequest request
												, HttpServletResponse response) throws Exception {
		
		
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		model.addAttribute("menuDepth1", "7");
		model.addAttribute("menuDepth2", "1");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		
		apageBoardManageVo avo = new apageBoardManageVo();
		if(svo != null && svo.getCt_seq()>0){
			avo.setCt_seq(svo.getCt_seq());
			avo.setPart("1");
			List<apageBoardManageVo> list = apageBoardManageService.selectAdminContestSelectResultExcel(avo);		
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
			
			avo.setPart("2");
			List<apageBoardManageVo> list2 = apageBoardManageService.selectAdminContestSelectResultExcel(avo);		
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
			avo.setPart("3");
			List<apageBoardManageVo> list3 = apageBoardManageService.selectAdminContestSelectResultExcel(avo);		
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
			
			avo.setPart("4");
			List<apageBoardManageVo> list4 = apageBoardManageService.selectAdminContestSelectResultExcel(avo);		
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
			
			model.addAttribute("part1List",list);
			model.addAttribute("part2List",list2);
			model.addAttribute("part3List",list3);
			model.addAttribute("part4List",list4);
			
		}
			
		
		return "/client/contest/contestLaneResult";
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
	@RequestMapping(value="/contest/contestAppResultJson.do")
	@ResponseBody
	public ModelAndView contestAppResultJson(@ModelAttribute("vo") BoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		try{
	
			Map<String,Object> data 	= new HashMap<String,Object>();
	
			List<BoardManageVo> list = BoardManageService.selectClientContestAppList(vo);
			for(int j = 0; j < list.size(); j++){
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
	 * 대회신청자 리스트페이지
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/contest/contestAppResult.do")
	public String contestAppResult(@ModelAttribute("vo") BoardManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session
			, ModelMap model) throws Exception {
		
		if(!(vo.getCt_seq() > 0)){
			model.addAttribute("msg", "ctNull");
			return "redirect:/contest/contestAppList.do";
		}
		
		BoardManageVo svo= BoardManageService.lb_getContestView(vo);
		
		model.addAttribute("menuDepth1", "7");
		model.addAttribute("menuDepth2", "1");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/contest/contestAppResult";
	}
	
	//대회신청 상세
	@RequestMapping(value="/contest/contestAppWrite.do")
	public String contestAppWrite(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		
		model.addAttribute("jm_param", request.getParameter("jm_param"));
		
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			request.setAttribute("result_code", "502");
			return "/client/contest/contestAppList";
		}else{
			model.addAttribute("memberinfo",mv);
			if(mv.getMber_birth() != null){
				String birth_arr[] = mv.getMber_birth().split("-");
				for(int i = 0; i < birth_arr.length; i ++){
					if(i == 1 && birth_arr[i] != null){
						model.addAttribute("birth_arr"+i, Integer.parseInt(birth_arr[i]));
					}else if(i == 2 && birth_arr[i] != null){
						model.addAttribute("birth_arr"+i, Integer.parseInt(birth_arr[i]));
					}else{
						model.addAttribute("birth_arr"+i, birth_arr[i]);
					}
				}
			}
			if(mv.getMber_tel() != null){
				String tel_arr[] = mv.getMber_tel().split("-");
				for(int i = 0; i < tel_arr.length; i ++){
					model.addAttribute("tel_arr"+i, tel_arr[i]);
				}
			}
			if(mv.getMber_email() != null){
				String email_arr[] = mv.getMber_email().split("@");
				for(int i = 0; i < email_arr.length; i ++){
					model.addAttribute("email_arr"+i, email_arr[i]);
				}
			}
		}
		
		BoardManageVo svo= BoardManageService.lb_getContestView(vo);
		
		if(svo != null){
			if(svo.getCt_process().equals("E") || svo.getCt_process().equals("F")){
				model.addAttribute("msg", "대회 신청 기간이 아닙니다.");
				request.setAttribute("result_code", "503");
				return "forward:/contest/contestAppList.do";
			}
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			String start_dt = svo.getApp_start_dt().replaceAll("-", "") + svo.getApp_start_h() + svo.getApp_start_m();
			String end_dt = svo.getApp_end_dt().replaceAll("-", "") + svo.getApp_end_h() + svo.getApp_end_m();
			
			Date start_time = dateFormat.parse(start_dt);
			Date now = new Date();
			
			vo.setLimit_part(svo.getLimit_part());
			vo.setLimit_waiting(svo.getLimit_waiting());
			vo.setCt_group(svo.getCt_group());
			
			if(svo.getCt_process().equals("R") || now.getTime() < start_time.getTime()){
				model.addAttribute("msg", "대회 신청 기간이 아닙니다.");
				request.setAttribute("result_code", "503");
				return "forward:/contest/contestAppList.do";
			}
			
			
			/*
			if(svo.getCt_group() != null){
				
				svo.setCt_seq_arr(svo.getCt_group().split(","));
				svo.setReg_id(mv.getMber_id());
				int group_contest_appCnt = BoardManageService.selectGroupContestAppCnt(svo);
				if(group_contest_appCnt > 0){
					model.addAttribute("msg", "대회 신청이력이 존재하여 해당 대회에 신청하실 수 없습니다.");
					request.setAttribute("result_code", "503");
					return "forward:/contest/contestAppList.do";
				}
			}
			
			int total_app_cnt = (svo.getLimit_part()+svo.getLimit_waiting())*4;
			System.out.println("===================================");
			System.out.println("svo.getApp_cnt() ===> " + svo.getApp_cnt());
			System.out.println("total_app_cnt ===> " + total_app_cnt);
			System.out.println("===================================");
			if(svo.getApp_cnt() >= total_app_cnt){
				BoardManageVo flagVo = new BoardManageVo();
				flagVo.setCt_process("E");
				flagVo.setCt_seq(svo.getCt_seq());
				int result1 = BoardManageService.updateContestManageFlag(flagVo);
				
				model.addAttribute("msg", "대회참가신청이 마감 되었습니다.");
				request.setAttribute("result_code", "500");
				return "/client/contest/contestAppList";
			}else{
				request.setAttribute("result_code", "200");
			}
			*/
			
			request.setAttribute("result_code", "200");
			model.addAttribute("limitPart", svo.getLimit_part());
			
			model.addAttribute("menuDepth1", "7");
			model.addAttribute("menuDepth2", "1");
			model.addAttribute("contestView", svo);
			
			
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("scType", vo.getScType());
			
			
			return "/client/contest/contestAppWrite";
		}else{
			request.setAttribute("result_code", "501");
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "/client/contest/contestAppList";
		}
		
	}	
	
	/**
	 * 대회 신청 
	 *
	 * @param multiRequest,vo,request,response,session,model,RedirectAttributes
	 * @return String
	 */
	@RequestMapping("/contest/contestAppReg.do")
	public String contestAppWrite(@ModelAttribute("vo") BoardManageVo vo
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session
								, ModelMap model) throws Exception{
	/*	
		int rtnVal = 0;
		
		try{
			
			ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
			
			if (null == mv){			
				model.addAttribute("msg", "잘못된 접근입니다.");
				request.setAttribute("result_code", "603");
				return "/client/contest/contestAppList";
			}else{
				vo.setReg_id(mv.getMber_id());
			}
			
			
			BoardManageVo svo= BoardManageService.getContestView(vo);

			boolean insertFlag = false;
			if(svo != null){
				
				if(svo.getCt_group() != null){
					svo.setCt_seq_arr(svo.getCt_group().split(","));
					svo.setReg_id(mv.getMber_id());
					int group_contest_appCnt = BoardManageService.selectGroupContestAppCnt(svo);
					if(group_contest_appCnt > 0){
						model.addAttribute("msg", "대회 신청이력이 존재하여 해당 대회에 신청하실 수 없습니다.");
						request.setAttribute("result_code", "604");
						return "forward:/contest/contestAppList.do";
					}
				}
				
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
				String start_dt = svo.getApp_start_dt().replaceAll("-", "") + svo.getApp_start_h() + svo.getApp_start_m();
				String end_dt = svo.getApp_end_dt().replaceAll("-", "") + svo.getApp_end_h() + svo.getApp_end_m();
				
				Date start_time = dateFormat.parse(start_dt);
				Date end_time = dateFormat.parse(end_dt);
				Date now = new Date();
				
				vo.setLimit_part(svo.getLimit_part());
				vo.setLimit_waiting(svo.getLimit_waiting());
				BoardManageVo flagVo = new BoardManageVo();
				if(now.getTime() >= end_time.getTime()){	//신청기간 초과
					flagVo.setCt_process("E");
					flagVo.setCt_seq(svo.getCt_seq());
					int result1 = BoardManageService.updateContestManageFlag(flagVo);
					model.addAttribute("msg", "접수기간이 아닙니다.\n접수시간을 확인해 주세요.");
				}else{
					if(vo.getPart().equals("1")){
						if(svo.getPart1_app_cnt() < svo.getLimit_part()){	//선정 가능
							vo.setStatus("0004");
							insertFlag = true;
						}else{
							if(svo.getPart1_wait_cnt() < svo.getLimit_waiting()){	//대기 가능
								vo.setStatus("0005");
								insertFlag = true;
							}else{
								model.addAttribute("msg", "선택하신 그룹(조)는 접수가 마감되었습니다. ");
								insertFlag = false;
							}
						}
					}else if(vo.getPart().equals("2")){
						if(svo.getPart2_app_cnt() < svo.getLimit_part()){	//선정 가능
							vo.setStatus("0004");
							insertFlag = true;
						}else{
							if(svo.getPart2_wait_cnt() < svo.getLimit_waiting()){	//대기 가능
								vo.setStatus("0005");
								insertFlag = true;
							}else{
								model.addAttribute("msg", "선택하신 그룹(조)는 접수가 마감되었습니다. ");
								insertFlag = false;
							}
						}
					}else if(vo.getPart().equals("3")){
						if(svo.getPart3_app_cnt() < svo.getLimit_part()){	//선정 가능
							vo.setStatus("0004");
							insertFlag = true;
						}else{
							if(svo.getPart3_wait_cnt() < svo.getLimit_waiting()){	//대기 가능
								vo.setStatus("0005");
								insertFlag = true;
							}else{
								model.addAttribute("msg", "선택하신 그룹(조)는 접수가 마감되었습니다. ");
								insertFlag = false;
							}
						}
						
					}else if(vo.getPart().equals("4")){
						if(svo.getPart4_app_cnt() < svo.getLimit_part()){	//선정 가능
							vo.setStatus("0004");
							insertFlag = true;
						}else{
							if(svo.getPart4_wait_cnt() < svo.getLimit_waiting()){	//대기 가능
								vo.setStatus("0005");
								insertFlag = true;
							}else{
								model.addAttribute("msg", "선택하신 그룹(조)는 접수가 마감되었습니다. ");
								insertFlag = false;
							}
						}
					}
				}
				
				if(insertFlag){
				//	rtnVal = BoardManageService.insertAppContest(vo);
					rtnVal = BoardManageService.pc_insertAppContest(vo);
					if(rtnVal == 0){
						model.addAttribute("msg", "정원이 초과되어 접수가 마감되었습니다.");
						request.setAttribute("result_code", "600");
					}else if(rtnVal == 1){
						model.addAttribute("msg", "정원이 초과되어 대기로 접수되었습니다.");
						request.setAttribute("result_code", "601");
					}else if(rtnVal == 2){
						model.addAttribute("msg", "신청되었습니다.");
						request.setAttribute("result_code", "200");
					}else{ // rtnVal == 3 Status 0004(선정), 0005(대기) 파라미터 없음
						model.addAttribute("msg", "잘못된 접근입니다.");
						request.setAttribute("result_code", "602");
					}
				}
			}else{
				model.addAttribute("msg", "실패하였습니다.");		
			}
			
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("msg", "실패하였습니다.");
			return "/client/contest/contestAppList";				
		}*/
		
		return "forward:/contest/contestAppList.do";
	}
	
	/**
	 * 대회신청 AJAX 처리
	 * 
	 * @return
	 * ResultCode
	 * Y : 접수완료
	 * E : 오류발생
	 * O : 정원초과
	 * D : 이미신청
	 * T : 시간초과
	 * F : 접수마감(플래그)
	 * S : 세션없음
	 * */
	@RequestMapping("/contest/contestAppRegAjax.do")
	public @ResponseBody ModelAndView contestAppRegAjax(@ModelAttribute("vo") BoardManageVo vo
															,HttpServletRequest request
															,HttpServletResponse response
															,HttpSession session) throws Exception {

		Map<String,Object> data 	= new HashMap<String,Object>();
		
		int rtnVal = 0;
		
		try{
			/*
			String refererURL = (request.getHeader("referer") != null ? request.getHeader("referer") : "" );
			System.out.println("refererURL ==> " + refererURL);
			if(!refererURL.contains("/contest/contestAppWrite")){
				data.put("ResultCode", "S");
				request.setAttribute("result_code", "607");
				return SendMiPlatform.SendString(response, data);
			}
			*/
			
			ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
			
			if (null == mv || "".equals(mv.getMber_id())){
				data.put("ResultCode", "S");
				request.setAttribute("result_code", "605");
				return SendMiPlatform.SendString(response, data);
			}else{
				vo.setReg_id(mv.getMber_id());
			}
			
			BoardManageVo svo= BoardManageService.lb_getContestView(vo);

			boolean insertFlag = false;
			if(svo != null && svo.getCt_group() != null && !"".equals(svo.getCt_group())){
				if(svo.getCt_process().equals("E") || svo.getCt_process().equals("F")){	// 대회플래그 : 신청마감, 대회종료
					data.put("ResultCode", "F");
					request.setAttribute("result_code", "604");
					return SendMiPlatform.SendString(response, data);
				}
				/*
				if(svo.getCt_group() != null){
					svo.setCt_seq_arr(svo.getCt_group().split(","));
					svo.setReg_id(mv.getMber_id());
					int group_contest_appCnt = BoardManageService.selectGroupContestAppCnt(svo);
					if(group_contest_appCnt > 0){
						//model.addAttribute("msg", "대회 신청이력이 존재하여 해당 대회에 신청하실 수 없습니다.");
						data.put("ResultCode", "5");
						request.setAttribute("result_code", "604");
						return SendMiPlatform.SendString(response, data);
					}
				}
				*/
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
				String start_dt = svo.getApp_start_dt().replaceAll("-", "") + svo.getApp_start_h() + svo.getApp_start_m();
				String end_dt = svo.getApp_end_dt().replaceAll("-", "") + svo.getApp_end_h() + svo.getApp_end_m();
				
				Date start_time = dateFormat.parse(start_dt);
				Date end_time = dateFormat.parse(end_dt);
				Date now = new Date();
				
				vo.setLimit_part(svo.getLimit_part());
				vo.setLimit_waiting(svo.getLimit_waiting());
				vo.setCt_group(svo.getCt_group());
				BoardManageVo flagVo = new BoardManageVo();
				
				if(svo.getCt_process().equals("R") || now.getTime() < start_time.getTime()){
					data.put("ResultCode", "T");
					request.setAttribute("result_code", "603");
					return SendMiPlatform.SendString(response, data);
				}
				
				if(now.getTime() >= end_time.getTime()){	//신청기간 초과
					flagVo.setCt_process("E");
					flagVo.setCt_seq(svo.getCt_seq());
					int result1 = BoardManageService.updateContestManageFlag(flagVo);
					//model.addAttribute("msg", "접수기간이 아닙니다.\n접수시간을 확인해 주세요.");

					data.put("ResultCode", "T");
					request.setAttribute("result_code", "603");
					return SendMiPlatform.SendString(response, data);
				}else{
					if(vo.getPart().equals("1")){
						if(svo.getPart1_app_cnt() + svo.getPart1_wait_cnt() < svo.getLimit_part() + svo.getLimit_waiting()){	//선정 가능
							vo.setStatus("0005");
							insertFlag = true;
						}else{
							//model.addAttribute("msg", "선택하신 그룹(조)는 접수가 마감되었습니다. ");
							insertFlag = false;
							data.put("ResultCode", "O");
							request.setAttribute("result_code", "601");
							return SendMiPlatform.SendString(response, data);
						}
					}else if(vo.getPart().equals("2")){
						if(svo.getPart2_app_cnt() + svo.getPart2_wait_cnt() < svo.getLimit_part() + svo.getLimit_waiting()){	//선정 가능
							vo.setStatus("0005");
							insertFlag = true;
						}else{
							//model.addAttribute("msg", "선택하신 그룹(조)는 접수가 마감되었습니다. ");
							insertFlag = false;
							data.put("ResultCode", "O");
							request.setAttribute("result_code", "601");
							return SendMiPlatform.SendString(response, data);
						}
					}else if(vo.getPart().equals("3")){
						if(svo.getPart3_app_cnt() +svo.getPart3_wait_cnt()  < svo.getLimit_part() + svo.getLimit_waiting()){	//선정 가능
							vo.setStatus("0005");
							insertFlag = true;
						}else{
							//model.addAttribute("msg", "선택하신 그룹(조)는 접수가 마감되었습니다. ");
							insertFlag = false;
							data.put("ResultCode", "O");
							request.setAttribute("result_code", "601");
							return SendMiPlatform.SendString(response, data);
						}
						
					}else if(vo.getPart().equals("4")){
						if(svo.getPart4_app_cnt()+svo.getPart4_wait_cnt() < svo.getLimit_part() + svo.getLimit_waiting()){	//선정 가능
							vo.setStatus("0005");
							insertFlag = true;
						}else{
							insertFlag = false;
							data.put("ResultCode", "O");
							request.setAttribute("result_code", "601");
							return SendMiPlatform.SendString(response, data);
						}
					}
				}
				
				if(insertFlag){		//접수 가능 플래그(선정 또는 대기)
					rtnVal = BoardManageService.insertAppContest(vo);
					//rtnVal = BoardManageService.pc_insertAppContest(vo);
					if(rtnVal > 0){
						BoardManageVo insertAfterVo = BoardManageService.lb_getContestView(vo);
						//대회 정원 초과
						if(!(insertAfterVo.getCt_process().equals("E") || insertAfterVo.getCt_process().equals("F"))){		// 대회플래그 : 신청마감, 대회종료
							SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmm");
							String start_dt2 = insertAfterVo.getApp_start_dt().replaceAll("-", "") + insertAfterVo.getApp_start_h() + insertAfterVo.getApp_start_m();
							String end_dt2 = insertAfterVo.getApp_end_dt().replaceAll("-", "") + insertAfterVo.getApp_end_h() + insertAfterVo.getApp_end_m();
							
							Date start_time2 = dateFormat2.parse(start_dt2);
							Date end_time2 = dateFormat2.parse(end_dt2);
							Date now2 = new Date();
							
							BoardManageVo flagVo2 = new BoardManageVo();
							if(now2.getTime() >= end_time2.getTime()){	
								flagVo2.setCt_process("E");
								flagVo2.setCt_seq(insertAfterVo.getCt_seq());
								int result1 = BoardManageService.updateContestManageFlag(flagVo2);
							}else if(insertAfterVo.getPart1_app_cnt() + insertAfterVo.getPart1_wait_cnt() >= insertAfterVo.getLimit_part() + insertAfterVo.getLimit_waiting()
								&&
								insertAfterVo.getPart2_app_cnt() + insertAfterVo.getPart2_wait_cnt() >= insertAfterVo.getLimit_part() + insertAfterVo.getLimit_waiting()
								&&
								insertAfterVo.getPart3_app_cnt() + insertAfterVo.getPart3_wait_cnt() >= insertAfterVo.getLimit_part() + insertAfterVo.getLimit_waiting()
								&&
								insertAfterVo.getPart4_app_cnt() + insertAfterVo.getPart4_wait_cnt() >= insertAfterVo.getLimit_part() + insertAfterVo.getLimit_waiting()
							){
								flagVo2.setCt_process("E");
								flagVo2.setCt_seq(insertAfterVo.getCt_seq());
								int result1 = BoardManageService.updateContestManageFlag(flagVo2);
							}
						}
						
						
						
						boolean deleteFlag = false;
						if(vo.getPart().equals("1")){
							if(insertAfterVo.getPart1_app_cnt() + insertAfterVo.getPart1_wait_cnt() > insertAfterVo.getLimit_part() + insertAfterVo.getLimit_waiting()){	//선정 가능
								deleteFlag = true;
							}
						}else if(vo.getPart().equals("2")){
							if(insertAfterVo.getPart2_app_cnt() + insertAfterVo.getPart2_wait_cnt() > insertAfterVo.getLimit_part() + insertAfterVo.getLimit_waiting()){	//선정 가능
								deleteFlag = true;
							}
						}else if(vo.getPart().equals("3")){
							if(insertAfterVo.getPart3_app_cnt() +insertAfterVo.getPart3_wait_cnt() > insertAfterVo.getLimit_part() + insertAfterVo.getLimit_waiting()){	//선정 가능
								deleteFlag = true;
							}
						}else if(vo.getPart().equals("4")){
							if(insertAfterVo.getPart4_app_cnt()+insertAfterVo.getPart4_wait_cnt() > insertAfterVo.getLimit_part() + insertAfterVo.getLimit_waiting()){	//선정 가능
								deleteFlag = true;
							}
						}
						if(deleteFlag){
							vo.setApp_seq(rtnVal);
							BoardManageVo chkVo = BoardManageService.lb_selectNowAppDataGetRownum(vo);
							if(chkVo != null && chkVo.getRownum() > 0){
								if(chkVo.getRownum() > (insertAfterVo.getLimit_part() + insertAfterVo.getLimit_waiting())){
									int delResult = BoardManageService.deleteNowAppData(vo);
									if(delResult > 0){
										data.put("ResultCode", "O");	//정원초과 삭제완료
										request.setAttribute("result_code", "601");
										return SendMiPlatform.SendString(response, data);
									}else{
										data.put("ResultCode", "E");	//오류발생
										request.setAttribute("result_code", "600");
										return SendMiPlatform.SendString(response, data);
									}
								}else{
									data.put("ResultCode", "Y");	//접수완료
									request.setAttribute("result_code", "200");
									return SendMiPlatform.SendString(response, data);
								}
							}else{
								data.put("ResultCode", "E");	//오류 발생
								request.setAttribute("result_code", "600");
								return SendMiPlatform.SendString(response, data);
							}
						}else{
							data.put("ResultCode", "Y");	//접수완료
							request.setAttribute("result_code", "200");
							return SendMiPlatform.SendString(response, data);
						}
					}else{	//실패 또는 이미 신청
						data.put("ResultCode", "E");	//오류 발생
						request.setAttribute("result_code", "600");
						return SendMiPlatform.SendString(response, data);
					}
				}else{
					data.put("ResultCode", "O");		//정원 초과
					request.setAttribute("result_code", "601");
					return SendMiPlatform.SendString(response, data);
				}
			}else{
				//model.addAttribute("msg", "실패하였습니다.");		
				data.put("ResultCode", "E");	//오류 발생
				request.setAttribute("result_code", "600");
				return SendMiPlatform.SendString(response, data);
			}
			
		}catch(DuplicateKeyException e){
//			e.printStackTrace();
			logger.error(e);
			data.put("ResultCode", "D");	//PK 오류 DUPLICATE 이미신청건이 존재함.
			request.setAttribute("result_code", "602");
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
//			e.printStackTrace();
			logger.error(e);
			//model.addAttribute("msg", "실패하였습니다.");
			data.put("ResultCode", "E");
			request.setAttribute("result_code", "606");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	
	
	
	/**
	 * 대회 신청 시작 체크
	 *
	 * @param multiRequest,vo,request,response,session,model,RedirectAttributes
	 * @return String
	 */
	@RequestMapping(value="/contest/appPermissionCheckJson.do")
	public @ResponseBody ModelAndView appPermissionCheckJson(@ModelAttribute("vo") BoardManageVo vo
			,HttpServletRequest request
			,HttpServletResponse response) throws Exception {

		Map<String,Object> data 	= new HashMap<String,Object>();
		try {
			
			BoardManageVo rvo = BoardManageService.lb_getContestView(vo);
			if(rvo == null){
				data.put("ResultCode", "E");
			}else{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
				
				String start_dt = rvo.getApp_start_dt().replaceAll("-", "") + rvo.getApp_start_h() + rvo.getApp_start_m();
				String end_dt = rvo.getApp_end_dt().replaceAll("-", "") + rvo.getApp_end_h() + rvo.getApp_end_m();
				
				Date start_time = dateFormat.parse(start_dt);
				Date end_time = dateFormat.parse(end_dt);
				Date now = new Date();
				
				if(now.getTime() >= start_time.getTime() && now.getTime() <= end_time.getTime()){
					data.put("ResultCode", "Y");
				}else{
					data.put("ResultCode", "N");
				}
//				System.out.println("=================현재시간================");
//				System.out.println("start_dt ==> " + start_time.getTime());
//				System.out.println("end_dt ==> " + end_time.getTime());
//				System.out.println("now ==> " + now.getTime());
//				System.out.println("=========================================");
				
				Calendar calendar = Calendar.getInstance();
				
//				System.out.println("현재 시간 : " + dateFormat.format(calendar.getTime()));

			}
			
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			data.put("ResultCode", "E");
			return SendMiPlatform.SendString(response, data);
		}
	}
		
		
	
	/**
	 *  대회신청 중복 체크
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/contest/chkMberAppJson.do")
	public void chkMberAppJson(@ModelAttribute("vo") BoardManageVo vo, 
						  HttpServletRequest request, 
						  HttpServletResponse response, 
						  HttpSession session) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean chkMber = false;
		//MemberManageVo mv = (MemberManageVo) session.getAttribute("mberInfo");
		//vo.setReg_id(mv.getMber_id());
		String mber_id = (String)session.getAttribute("mber_id");
		vo.setReg_id(mber_id);
		int result = BoardManageService.lb_chkMberApp(vo);
		
		/*
		System.out.println("==============================");
		System.out.println("result : " + result);
		System.out.println("==============================");
		*/
		
		if (result > 0) {			
			chkMber = true;			
		}
		response.setContentType("text/html");		
		PrintWriter writer = response.getWriter();
		writer.print(chkMber);
		writer.flush();
		writer.close();	
	}
	
	/**
	 *  대회신청 가능여부 체크
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/contest/chkAppProcess.do")
	public void chkAppProcess(@ModelAttribute("vo") BoardManageVo vo, 
						  HttpServletRequest request, 
						  HttpServletResponse response, 
						  HttpSession session) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String process;

		BoardManageVo bvo = BoardManageService.chkAppProcess(vo);
		
		process = bvo.getCt_process();
		response.setContentType("text/html");		
		PrintWriter writer = response.getWriter();
		writer.print(process);
		writer.flush();
		writer.close();	
	}
	
	/**
	 * 조별 정원체크
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/contest/chkLimitPart.do")
	public void chkLimitPart(@ModelAttribute("vo") BoardManageVo vo, 
						  HttpServletRequest request, 
						  HttpServletResponse response, 
						  HttpSession session) throws Exception {

		int result = BoardManageService.chkLimitPart(vo);
		
		
		/*
		System.out.println("==============================");
		System.out.println("result : " + result);
		System.out.println("==============================");
		*/
		
		response.setContentType("text/html");		
		PrintWriter writer = response.getWriter();
		writer.print(result);
		writer.flush();
		writer.close();	
	}
	
	//대회안내 리스트
	@RequestMapping(value="/board/centerList.do")
	public String centerList(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		//게시판 구분값
		vo.setBbs_id("center");
		
		if(vo.getAs_type() == null || "".equals(vo.getAs_type())){
			vo.setAs_type("0010");
		}
				
		List<BoardManageVo> list = BoardManageService.selectBoardList(vo);		
		int totCnt = BoardManageService.selectBoardListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("as_type", vo.getAs_type());
		
		model.addAttribute("centerList", list);
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "4");
		model.addAttribute("menuDepth2", "4");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/board/centerList";
	}
	
	//네이버 이미지 캡챠
	//캡챠 KEY 발급
	public static String captchaKeyReturn() {
		
	try {
			String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
        	//System.out.println(e);
        	return e.toString();
        }
	}
		   
	//캡챠 코드 비교
	public static String captchaCompareResult(String captcha_key,String value) {
		try {
	            String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
	            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code +"&key="+ captcha_key + "&value="+ value;

	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            System.out.println(response.toString());
	            return response.toString();
	        } catch (Exception e) {
	            System.out.println(e);
	            return "error";
	        }
	}
		
	//음성 캡챠 KEY 발급
	public static String captchaKeyReturnAudio() {
		try {
				String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
	            String apiURL = "https://openapi.naver.com/v1/captcha/skey?code=" + code;
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            return response.toString();
	        } catch (Exception e) {
	        	//System.out.println(e);
	        	return e.toString();
	        }
	}
		
		
	//음성 캡챠 코드 비교
	public static String captchaCompareResultAudio(String captcha_key,String value) {
		try {
	            String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
	            String apiURL = "https://openapi.naver.com/v1/captcha/skey?code=" + code +"&key="+ captcha_key + "&value="+ value;

	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            System.out.println(response.toString());
	            return response.toString();
	        } catch (Exception e) {
	            System.out.println(e);
	            return "error";
	        }
	}		
		    
    //캡챠 새로고침 AJAX
	@RequestMapping(value="/resetCaptcha.do")
	public @ResponseBody ModelAndView resetCaptcha(@ModelAttribute("vo") BoardManageVo vo
			,HttpServletRequest request
			,HttpServletResponse response) throws Exception {
		try {
			
			
			String captchaKey = captchaKeyReturn();
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(captchaKey);
			JsonObject jsonObj = (JsonObject) obj;

			
			String cKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
			String captchaURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + cKey;
			
			
			String captchaAudioKey = captchaKeyReturnAudio();
			obj = parser.parse(captchaAudioKey);
			jsonObj = (JsonObject) obj;
			
			String aKey = jsonObj.get("key").toString().substring(1, jsonObj.get("key").toString().length()-1);
			String captchaAudioURL = "https://openapi.naver.com/v1/captcha/scaptcha?key=" + aKey;
			
			Map<String,Object> data 	= new HashMap<String,Object>();
			if(cKey != null && aKey != null){
				data.put("ResultCode","Y");
				data.put("cKey",cKey);
				data.put("captchaURL",captchaURL);

				data.put("aKey",aKey);
				data.put("captchaAudioURL",captchaAudioURL);
				
			}else{
				data.put("ResultCode","N");
			}
			
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***
	 * 2019-10-30 추가
	 * 쇼핑몰 이벤트 게시판
	 * 
	 * */
		

	//쇼핑몰 이벤트 리스트
	@RequestMapping(value="/board/shopEventList.do")
	public String shopEventList(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);
		
		//게시판 구분값
		vo.setBbs_id("shopEvent");
		List<BoardManageVo> list = BoardManageService.lb_selectBoardList(vo);		
		int totCnt = BoardManageService.lb_selectBoardListCnt(vo);
		
		//게시판 구분값
		vo.setBbs_id("ShopeventNotice");
		List<BoardManageVo> topList = BoardManageService.lb_getTopBoardList(vo);
		model.addAttribute("topList", topList);
		
		
		
		
		BoardManageVo eventManage = BoardManageService.lb_getShopEventManageInfo(vo);
		if(shopEventStatus(eventManage,request)){	//신청가능 상태라면
			model.addAttribute("eventManage", eventManage);
			model.addAttribute("eventFlag", "true");
		}
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("consultList", list);
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "4");	
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		return "/client/board/shopEventList";
	}
	
	
	//쇼핑몰 이벤트 작성페이지
	@RequestMapping(value="/board/shopEventWrite.do")
	public String shopEventWrite(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {

		//session check - 190111
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		if (null == mv || mv.getMber_id().equals("")){
			model.addAttribute("msg", "login.fail");
			return "redirect:/board/shopEventList.do";		
		}
		
		BoardManageVo eventManage = BoardManageService.lb_getShopEventManageInfo(vo);
		if(!shopEventStatus(eventManage,request)){	//신청가능 상태라면
			model.addAttribute("msg", "date.fail");
			return "redirect:/board/shopEventList.do";		
		}
		model.addAttribute("eventVo",eventManage);
		
				
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "4");	

		return "/client/board/shopEventWrite";
	}	
	
	

	/**
	 * 쇼핑몰 이벤트 등록
	 *
	 * @param multiRequest,vo,request,response,session,model,RedirectAttributes
	 * @return String
	 */
	@RequestMapping("/board/shopEventReg.do")
	public String shopEventReg(final MultipartHttpServletRequest multiRequest
								,@ModelAttribute("vo") BoardManageVo vo
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session
								, ModelMap model) throws Exception{
		
		int rtnVal = 0;
		String msg = "";
		try{
			
			//session check - 190111
			ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
			if (null == mv || mv.getMber_id().equals("")){
				model.addAttribute("msg", "insert.fail");
				return "redirect:/board/shopEventList.do";		
			}
			
			BoardManageVo eventManage = BoardManageService.lb_getShopEventManageInfo(vo);
			if(!shopEventStatus(eventManage,request)){	//신청가능 상태라면
				model.addAttribute("msg", "date.fail");
				return "redirect:/board/shopEventList.do";		
			}

			
			List<FileManageVo> result = null;
			Map<String, MultipartFile> files = multiRequest.getFileMap();		
			if(!files.isEmpty()){
	  			result = fileUtil.parseFileInf2(request,files, "FILE_", "", 0, "shopEvent"); 	
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			
			vo.setBbs_id("shopEvent");
			rtnVal = BoardManageService.insertBoard(vo);
			if(rtnVal > 0) {
				model.addAttribute("msg", "insert.success");
			} else {
				model.addAttribute("msg", "insert.fail");
				return "forward:/board/shopEventWrite.do";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "forward:/board/shopEventWrite.do";				
		}
		
		return "redirect:/board/shopEventList.do";
	}
	
	//쇼핑몰 이벤트 상세
	@RequestMapping(value="/board/shopEventDetail.do")
	public String shopEventDetail(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
				
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		if(svo == null) {
			model.addAttribute("msg", "access.error");
			return "redirect:/board/shopEventList.do";		
		}
		
		
		ShopMemberManageVo sessionVo = (ShopMemberManageVo)request.getSession().getAttribute("mberInfo");
		if(sessionVo == null || "".equals(sessionVo.getMber_id()) || !sessionVo.getMber_id().equals(svo.getNtcr_id())){
			model.addAttribute("msg", "access.fail");
			return "redirect:/board/shopEventList.do";		
		}
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		
		
		vo.setBbs_id("shopEvent");		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> consultfile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("consultfile", consultfile);
		}
		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "4");		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/board/shopEventView";
	}	
	
	//쇼핑몰 이벤트 수정페이지
	@RequestMapping(value="/board/shopEventModify.do")
	public String shopEventModify(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		
		//상세
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		if(svo == null) {
			model.addAttribute("msg", "access.error");
			return "redirect:/board/shopEventList.do";		
		} 
		
		
		
		ShopMemberManageVo sessionVo = (ShopMemberManageVo)request.getSession().getAttribute("mberInfo");
		if(sessionVo == null || "".equals(sessionVo.getMber_id()) || !sessionVo.getMber_id().equals(svo.getNtcr_id())){
			model.addAttribute("msg", "access.fail");
			return "redirect:/board/shopEventList.do";		
		}
		
		FileManageVo fvo = new FileManageVo();
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> consultFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("consultFile", consultFile);
		}
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("boardView", svo);
		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "4");
		
		return "/client/board/shopEventUpdt";
	}
	
	//쇼핑몰이벤트 수정
	@RequestMapping("/board/shopEventUpdt.do")
	public String shopEventUpdt(final MultipartHttpServletRequest multiRequest
								,@ModelAttribute("vo") BoardManageVo vo
								, HttpServletRequest request
								, HttpServletResponse response
								, HttpSession session
								, ModelMap model) throws Exception{
		
		int rtnVal = 0;
		String msg = "";
		try{
			
			
			BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
			if(svo == null) {
				model.addAttribute("msg", "access.error");
				return "redirect:/board/shopEventList.do";		
			}
			
			String mber_id = (String)request.getSession().getAttribute("mber_id");
			if(mber_id == null || "".equals(mber_id) || !mber_id.equals(svo.getNtcr_id())){
				model.addAttribute("msg", "access.fail");
				return "redirect:/board/shopEventList.do";		
			}

			//첨부파일
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
			
			//수정
			rtnVal = BoardManageService.updateBoard(vo);
			if(rtnVal > 0) {
				model.addAttribute("msg", "insert.success");
			} else {
				model.addAttribute("msg", "insert.fail");
				return "forward:/board/shopEventModify.do";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "forward:/board/shopEventModify.do";				
		}
		return "redirect:/board/shopEventList.do";
	}
	
	//쇼핑몰 이벤트 삭제
	@RequestMapping(value="/board/shopEventDelete.do")
	public String shopEventDelete(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		int result;
		
		try{
			BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
			if(svo == null) {
				model.addAttribute("msg", "access.error");
				return "redirect:/board/shopEventList.do";		
			}
			
			String mber_id = (String)request.getSession().getAttribute("mber_id");
			if(mber_id == null || "".equals(mber_id) || !mber_id.equals(svo.getNtcr_id())){
				model.addAttribute("msg", "access.fail");
				return "redirect:/board/shopEventList.do";		
			}
			
			result = BoardManageService.BoardDelete(vo);
			
			String real =  propertyService.getString("Globals.fileStorePath");
			String Path = "shopEvent";
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
			
			model.addAttribute("msg", "delete.success");
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "delete.fail");
    		return "/client/board/shopEventView";
		}
		return "redirect:/board/shopEventList.do";
	}
		
	
	

	//쇼핑몰 이벤트 공지사항 상세
	@RequestMapping(value="/board/shopEventNoticeDetail.do")
	public String shopEventNoticeDetail(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		if(svo == null) {
			model.addAttribute("msg", "access.error");
			return "redirect:/board/shopEventList.do";		
		}
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> noticeFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("noticeFile", noticeFile);
		}
		
		model.addAttribute("menuDepth1", "5");
		model.addAttribute("menuDepth2", "4");	
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/board/shopEventNoticeView";
	}
	
	
	/**
	 * 쇼핑몰 이벤트 상태
	 * @throws Exception 
	 * **/
	public boolean shopEventStatus(BoardManageVo vo,HttpServletRequest request) throws Exception{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String start_dt = vo.getShop_event_start_dt().replaceAll("-", "") + vo.getShop_event_start_h() + vo.getShop_event_start_m();
		String end_dt = vo.getShop_event_end_dt().replaceAll("-", "") + vo.getShop_event_end_h() + vo.getShop_event_end_m();
		
		Date start_time = dateFormat.parse(start_dt);
		Date end_time = dateFormat.parse(end_dt);
		Date now = new Date();
		
		if("S".equals(vo.getShop_event_process())){
			if(now.getTime() >= end_time.getTime()){	//신청기간 초과
				BoardManageVo flagVo = new BoardManageVo();
				flagVo.setShop_event_process("E");
				int result1 = BoardManageService.updateShopEventManageInfo(flagVo);
				//model.addAttribute("msg", "접수기간이 아닙니다.\n접수시간을 확인해 주세요.");

				request.setAttribute("result_code", "603");
				
				return false;
			}else{	//신청가능 상태
				return true;
			}
		}else{
			if(now.getTime() >= start_time.getTime() && now.getTime() < end_time.getTime()){	//신청 가능 기간 
				BoardManageVo flagVo = new BoardManageVo();
				flagVo.setShop_event_process("S");				//신청 가능상태로 변경
				int result1 = BoardManageService.updateShopEventManageInfo(flagVo);
				//model.addAttribute("msg", "접수기간이 아닙니다.\n접수시간을 확인해 주세요.");

				request.setAttribute("result_code", "603");
				
				return true;
			}else{						//신청 불가능 상태
				return false;
			}
		}
	}
	
	
}
