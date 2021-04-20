package egovframework.client.event.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import org.springframework.web.servlet.ModelAndView;

import egovframework.client.board.service.BoardManageService;
import egovframework.client.board.service.BoardManageVo;
import egovframework.client.event.service.EventManageService;
import egovframework.client.event.service.EventManageVo;
import egovframework.client.member.service.ShopMemberManageService;
import egovframework.client.member.service.ShopMemberManageVo;
import egovframework.common.core.SendMiPlatform;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.service.SmsManageService;
import egovframework.common.service.SmsManageVo;
import egovframework.common.utils.CommonUtil;
import egovframework.common.utils.EgovFileMngUtil;
import egovframework.common.utils.EgovHttpSessionBindingListener;
import egovframework.common.utils.PageVo;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class EventManageController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "BoardManageService")
	private BoardManageService BoardManageService;
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name = "CommonUtil")
	private CommonUtil CommonUtil;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	@Resource(name = "FileManageService")
	private FileManageService fileManageService;
	
	@Resource(name = "EventManageService")
	private EventManageService EventManageService;
	
	@Resource(name = "SmsManageService")
	private SmsManageService SmsManageService;

	@Resource(name = "shopMemberManageService")
	private ShopMemberManageService shopMemberManageService;
	
		
	//왕중왕전 대회안내 리스트
	@RequestMapping(value="/event/kokContestInfo.do")
	public String kokContestInfo(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);

		//공지사항 topList 불러오기 - 190108 추가
		/*
		vo.setBbs_id("notice");
		List<BoardManageVo> TopBoardList = BoardManageService.lb_getTopBoardList(vo);
		model.addAttribute("TopBoardList", TopBoardList);
		*/
		
		//게시판 구분값
		vo.setBbs_id("kokContest");
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
		model.addAttribute("menuDepth2", "2");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		
		return "/client/event/kokContestInfo";
	}
	
	
	//왕중왕전 대회안내 상세
	@RequestMapping(value="/event/kokContestInfoView.do")
	public String kokContestInfoView(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		vo.setBbs_id("kokContest");
		BoardManageVo next = BoardManageService.getNextBoardView(vo);
		model.addAttribute("next", next);
		
		vo.setBbs_id("kokContest");
		BoardManageVo pre = BoardManageService.getPreBoardView(vo);
		model.addAttribute("pre", pre);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		model.addAttribute("menuDepth1", "2");
		model.addAttribute("menuDepth2", "2");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("contestView", svo);
		
		return "/client/event/kokContestInfoView";
	} 
	
	
	
	
	//이벤트 대회안내 리스트
	@RequestMapping(value="/event/eventContestInfo.do")
	public String eventContestInfo(@ModelAttribute("vo") BoardManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow((endPageNum-startPageNum)+1);

		//공지사항 topList 불러오기 - 190108 추가
		/*
		vo.setBbs_id("notice");
		List<BoardManageVo> TopBoardList = BoardManageService.lb_getTopBoardList(vo);
		model.addAttribute("TopBoardList", TopBoardList);
		*/
		//게시판 구분값
		vo.setBbs_id("eventContest");
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
		model.addAttribute("menuDepth2", "3");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		
		return "/client/event/eventContestInfo";
	}
	
	
	//이벤트 대회안내 상세
	@RequestMapping(value="/event/eventContestInfoView.do")
	public String eventContestInfoView(@ModelAttribute("vo") BoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		//조회수 증가
		BoardManageService.BoardUpdateCnt(vo);
		
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		vo.setBbs_id("eventContest");
		BoardManageVo next = BoardManageService.getNextBoardView(vo);
		model.addAttribute("next", next);
		
		vo.setBbs_id("eventContest");
		BoardManageVo pre = BoardManageService.getPreBoardView(vo);
		model.addAttribute("pre", pre);
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
		
		model.addAttribute("menuDepth1", "2");
		model.addAttribute("menuDepth2", "3");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("contestView", svo);
		
		return "/client/event/eventContestInfoView";
	} 
	
	
	
	
	
	/**
	 * **********************************************************
	 * 
	 *	이하 왕중왕전, 이벤트 대회 신청 관련 메소드 
	 * 
	 * **********************************************************
	 * **/
	

	//왕중왕전 대회 신청 리스트
	@RequestMapping(value="/event/kokContestAppList.do")
	public String kokContestAppList(@ModelAttribute("vo") EventManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		

		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow(endPageNum);
		
		EventManageVo rvo = new EventManageVo();
		rvo.setCurrRow(startPageNum-1);
		rvo.setEndRow(endPageNum);
		// rvo.setScType("R");
		rvo.setSrch_input(vo.getSrch_input());
		String ct_type_arr[] = {"K"};
		rvo.setCt_type_arr(ct_type_arr);
		List<EventManageVo> rsList = EventManageService.lb_selectEventContestList(rvo);
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
				
				EventManageVo flagVo = new EventManageVo();
				if(now.getTime() >= start_time.getTime() && now.getTime() <= end_time.getTime()){	//신청가능
					if(rsList.get(i).getCt_process().equals("R")){
						flagVo.setCt_process("S");
						flagVo.setCt_seq(rsList.get(i).getCt_seq());
						flagVo.setCt_type(rsList.get(i).getCt_type());
						int result1 = EventManageService.updateEventContestManageFlag(flagVo);
						
						r_flag = 1;
					};
				}
				else if(now.getTime() >= end_time.getTime()){	//신청기간 초과
					flagVo.setCt_process("E");
					flagVo.setCt_seq(rsList.get(i).getCt_seq());
					flagVo.setCt_type(rsList.get(i).getCt_type());
					int result1 = EventManageService.updateEventContestManageFlag(flagVo);
				}
				
			}
		}
		if(r_flag > 0 ){
			List<EventManageVo> list = EventManageService.lb_selectEventContestList(rvo);
			model.addAttribute("contestList", list);
		}else{
			model.addAttribute("contestList", rsList);
			
		}
		int totCnt = EventManageService.lb_selectEventContestListCnt(rvo);
		
		
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		
		
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "7");
		model.addAttribute("menuDepth2", "2");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		
		
		return "/client/event/kokContestAppList";
	}
	
	
	//왕중왕전 대회 신청 상세
	@RequestMapping(value="/event/kokContestAppView.do")
	public String kokContestAppView(@ModelAttribute("vo") EventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {

		
		FileManageVo fvo			= new FileManageVo();
		
		
		EventManageVo svo= EventManageService.lb_getEventContestView(vo);
		
		if(svo != null && (svo.getCt_process().equals("R") || svo.getCt_process().equals("S"))){
			//첨부파일 리스트 가져오기
			if(svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
				fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
				List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
				model.addAttribute("contestFile", contestFile);
			}
			
			model.addAttribute("menuDepth1", "7");
			model.addAttribute("menuDepth2", "2");
			
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("scType", vo.getScType());
			model.addAttribute("contestView", svo);
			
			return "/client/event/kokContestAppView";
		}else{
			model.addAttribute("menuDepth1", "7");
			model.addAttribute("menuDepth2", "2");
			
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("scType", vo.getScType());
			
			model.addAttribute("msg", "신청가능 상태가 아닙니다.");
			return "forward:/event/kokContestAppList.do";
		}
		
	
	}	
	
	
	
	
	
	
	//이벤트대회 신청 리스트
	@RequestMapping(value="/event/eventContestAppList.do")
	public String eventContestAppList(@ModelAttribute("vo") EventManageVo vo
			, ModelMap model
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum-1);
		vo.setEndRow(endPageNum);
		
		EventManageVo rvo = new EventManageVo();
		rvo.setCurrRow(startPageNum-1);
		rvo.setEndRow(endPageNum);
		// rvo.setScType("R");
		rvo.setSrch_input(vo.getSrch_input());
		
		
		String ct_type_arr[] = {"L","A","S"};
		rvo.setCt_type_arr(ct_type_arr);
		List<EventManageVo> rsList = EventManageService.lb_selectEventContestList(rvo);
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
				
				EventManageVo flagVo = new EventManageVo();
				if(now.getTime() >= start_time.getTime() && now.getTime() <= end_time.getTime()){	//신청가능
					if(rsList.get(i).getCt_process().equals("R")){
						flagVo.setCt_process("S");
						flagVo.setCt_seq(rsList.get(i).getCt_seq());
						flagVo.setCt_type(rsList.get(i).getCt_type());
						int result1 = EventManageService.updateEventContestManageFlag(flagVo);
						
						r_flag = 1;
					};
				}
				else if(now.getTime() >= end_time.getTime()){	//신청기간 초과
					flagVo.setCt_process("E");
					flagVo.setCt_seq(rsList.get(i).getCt_seq());
					flagVo.setCt_type(rsList.get(i).getCt_type());
					int result1 = EventManageService.updateEventContestManageFlag(flagVo);
				}
				
			}
		}
		if(r_flag > 0 ){
			List<EventManageVo> list = EventManageService.lb_selectEventContestList(rvo);
			model.addAttribute("contestList", list);
		}else{
			model.addAttribute("contestList", rsList);
			
		}
		int totCnt = EventManageService.lb_selectEventContestListCnt(rvo);
		
		
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		
		
		model.addAttribute("totalNum", totCnt);
		
		model.addAttribute("menuDepth1", "7");
		model.addAttribute("menuDepth2", "3");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		
		
		return "/client/event/eventContestAppList";
	}
	
	
	//이벤트대회 신청 정보 상세
	@RequestMapping(value="/event/eventContestAppView.do")
	public String eventContestAppView(@ModelAttribute("vo") EventManageVo vo
			, ModelMap model
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		
		FileManageVo fvo			= new FileManageVo();
		
		EventManageVo svo= EventManageService.lb_getEventContestView(vo);
		
		if(svo != null && (svo.getCt_process().equals("R") || svo.getCt_process().equals("S"))){
			//첨부파일 리스트 가져오기
			if(svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
				fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
				List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
				model.addAttribute("contestFile", contestFile);
			}
			
			model.addAttribute("menuDepth1", "7");
			model.addAttribute("menuDepth2", "3");
			
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("scType", vo.getScType());
			model.addAttribute("contestView", svo);
			
			if(svo.getCt_type() != null && svo.getCt_type().equals("L")){
				List<EventManageVo> partAppList	= EventManageService.lb_selectEventContestAppPartApplyCnt(svo);
				model.addAttribute("partAppList", partAppList);
			}
			
			return "/client/event/eventContestAppView";
		}else{
			model.addAttribute("menuDepth1", "7");
			model.addAttribute("menuDepth2", "3");
			
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("scType", vo.getScType());
			
			model.addAttribute("msg", "신청가능 상태가 아닙니다.");
			return "forward:/event/eventContestAppList.do";
		}
		
	}
	
	
	
	
	
	
	//이벤트 대회 신청 페이지
	@RequestMapping(value="/event/eventContestAppWrite.do")
	public String eventContestAppWrite(@ModelAttribute("vo") EventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		
		model.addAttribute("jm_param", request.getParameter("jm_param"));
		
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			request.setAttribute("result_code", "502");
			return "forward:/event/eventContestAppList.do";
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
		
		EventManageVo svo= EventManageService.lb_getEventContestView(vo);
		
		if(svo != null){
			if(svo.getCt_process().equals("E") || svo.getCt_process().equals("F")){
				model.addAttribute("msg", "대회 신청 기간이 아닙니다.");
				request.setAttribute("result_code", "503");
				return "forward:/event/eventContestAppList.do";
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
				return "forward:/event/eventContestAppList.do";
			}
			
			
			request.setAttribute("result_code", "200");
			model.addAttribute("limitPart", svo.getLimit_part());
			
			model.addAttribute("menuDepth1", "7");
			model.addAttribute("menuDepth2", "3");
			model.addAttribute("contestView", svo);
			
			
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("scType", vo.getScType());
			
			
			//조 정보
			List<EventManageVo> pList= EventManageService.lb_selectEventContestPartList(svo);
			model.addAttribute("pList", pList);
			
			if("S".equals(svo.getCt_type())){
				return "/client/event/eventContestAppWrite_shop"+"_"+svo.getCt_seq();
			}else{
				return "/client/event/eventContestAppWrite";
			}
			
		}else{
			request.setAttribute("result_code", "501");
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "forward:/event/eventContestAppList.do";
		}
		
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
	 * C : 왕중왕전 참가자 명단 없음
	 * */
	@RequestMapping("/event/eventContestAppRegAjax.do")
	public @ResponseBody ModelAndView eventContestAppRegAjax(@ModelAttribute("vo") EventManageVo vo
															,HttpServletRequest request
															,HttpServletResponse response
															,HttpSession session) throws Exception {

		Map<String,Object> data 	= new HashMap<String,Object>();
		
		int rtnVal = 0;
		
		try{
			
			
			ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
			
			if (null == mv || mv.getMber_id().equals("")){
				data.put("ResultCode", "S");
				request.setAttribute("result_code", "605");
				return SendMiPlatform.SendString(response, data);
			}else{
				vo.setReg_id(mv.getMber_id());
			}
			
			EventManageVo svo= EventManageService.lb_getEventContestView(vo);
			boolean insertFlag = false;
			if(svo != null){
				if(svo.getCt_type().equals("K")){
					int appTarget = EventManageService.lb_getEventContestAppTargetCnt(vo);
					if(!(appTarget > 0)){
						//로그인한 신청자 정보가 대회 참가자 명단에 존재하지 않습니다.
						data.put("ResultCode", "T");
						request.setAttribute("result_code", "607");
						return SendMiPlatform.SendString(response, data);
					}
				}
				if(svo.getCt_process().equals("E") || svo.getCt_process().equals("F")){	// 대회플래그 : 신청마감, 대회종료
					data.put("ResultCode", "F");
					request.setAttribute("result_code", "604");
					return SendMiPlatform.SendString(response, data);
				}
				vo.setCt_type(svo.getCt_type());
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
				String start_dt = svo.getApp_start_dt().replaceAll("-", "") + svo.getApp_start_h() + svo.getApp_start_m();
				String end_dt = svo.getApp_end_dt().replaceAll("-", "") + svo.getApp_end_h() + svo.getApp_end_m();
				
				Date start_time = dateFormat.parse(start_dt);
				Date end_time = dateFormat.parse(end_dt);
				Date now = new Date();
				
				vo.setLimit_part(svo.getLimit_part());
				vo.setLimit_waiting(svo.getLimit_waiting());
				vo.setCt_group(svo.getCt_group());
				EventManageVo flagVo = new EventManageVo();
				
				if(svo.getCt_process().equals("R") || now.getTime() < start_time.getTime()){
					data.put("ResultCode", "T");
					request.setAttribute("result_code", "603");
					return SendMiPlatform.SendString(response, data);
				}
				
				if(now.getTime() >= end_time.getTime()){	//신청기간 초과
					flagVo.setCt_process("E");
					flagVo.setCt_seq(svo.getCt_seq());
					int result1 = EventManageService.updateEventContestManageFlag(flagVo);
					//model.addAttribute("msg", "접수기간이 아닙니다.\n접수시간을 확인해 주세요.");

					data.put("ResultCode", "T");
					request.setAttribute("result_code", "603");
					return SendMiPlatform.SendString(response, data);
				}else{
					
					insertFlag = true;
					/**
					 * 정원초과 로직 제거
					 * */
					/*
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
					*/
				}
				
				if(insertFlag){		//접수 가능 플래그(선정 또는 대기)
					if(svo.getCt_type().equals("K")){
						vo.setStatus("0004");
						Random ran = new Random();        
						if((ran.nextInt(10)+1)%2 == 0){
							if(svo.getPart1_app_cnt() >= svo.getLimit_part()){
								vo.setPart("2");
							}else{
								vo.setPart("1");
							}
						}else{
							if(svo.getPart2_app_cnt() >= svo.getLimit_part()){
								vo.setPart("1");
							}else{
								vo.setPart("2");
							}
						}
						
						rtnVal = EventManageService.insertEventContestApp_procedure(vo);
					}else{
						vo.setStatus("0005");
						rtnVal = EventManageService.insertEventContestApp(vo);
					}
					if(rtnVal > 0){
						if(svo.getCt_type() != null && svo.getCt_type().equals("K") && vo.getStatus().equals("0004")){
							//왕중왕전은 바로 참가기확정이기때문에 바로 문자발송
							SmsManageVo smvo = new SmsManageVo();
							smvo.setSubject("[진승무역]");
							StringBuffer sb2 = new StringBuffer();
							sb2.append(vo.getJoin_name()+"님\n");
							//sb2.append(svo.getCt_sbj()+"/"+vo.getPart()+"조");
							sb2.append(svo.getCt_sbj());
							/*
							if(vo.getLane() != null && !vo.getLane().equals("") && svo.getLane_sms_send_yn() != null && svo.getLane_sms_send_yn().equals("Y")){
								sb2.append(" / "+vo.getLane()+"레인");
							}
							*/
							sb2.append(" 참가 확정\n");
							sb2.append("-계좌정보-\n");
							sb2.append(svo.getCt_bank() + "/" + svo.getCt_acchholder() + "/" + svo.getCt_account() + "/" + svo.getCt_price() +"\n");
							//sb2.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
							sb2.append(svo.getCt_deposit_eddt()+" " + svo.getCt_deposit_edh()+":"+svo.getCt_deposit_edm()+"까지 입금 요망\n");
							sb2.append("(반드시 참가자명으로 입금 바랍니다.)\n");
							sb2.append("*입금 후 취소 시 환불은 불가합니다.");
							 
							
							smvo.setMsg(sb2.toString());
							smvo.setPhone(vo.getTelno());
							
							smvo.setEtc1(Integer.toString(vo.getCt_seq()));				//대회 고유번호
							smvo.setEtc2(vo.getPart());									//신청 조
							smvo.setEtc3(Integer.toString(rtnVal));						//신청 고유번호
							smvo.setEtc5("kok_auto"); 									//전체 발송 플래그
							smvo.setEtc6(vo.getReg_id());								//신청자 ID
							smvo.setEtc7(vo.getJoin_name());							//신청자 명
							smvo.setEtc8(vo.getBirth());								//신청자 생년월일
							smvo.setEtc9("0004");										//신청 상태
							
							SmsManageService.insertMmsList(smvo);
							
						}
						data.put("ResultCode", "Y");	//접수완료
						request.setAttribute("result_code", "200");
						return SendMiPlatform.SendString(response, data);
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
			e.printStackTrace();
			data.put("ResultCode", "D");	//PK 오류 DUPLICATE 이미신청건이 존재함.
			request.setAttribute("result_code", "602");
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			//model.addAttribute("msg", "실패하였습니다.");
			data.put("ResultCode", "E");
			request.setAttribute("result_code", "606");
			return SendMiPlatform.SendString(response, data);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 *  왕중왕전, 이벤트대회 공통 사용
	 * 
	 * **/

	
	/**
	 * 대회 신청 시작 체크
	 *
	 * @param multiRequest,vo,request,response,session,model,RedirectAttributes
	 * @return String
	 */
	@RequestMapping(value="/event/appPermissionCheckJson.do")
	public @ResponseBody ModelAndView appPermissionCheckJson(@ModelAttribute("vo") EventManageVo vo
			,HttpServletRequest request
			,HttpServletResponse response) throws Exception {

		Map<String,Object> data 	= new HashMap<String,Object>();
		try {
			
			EventManageVo rvo = EventManageService.lb_getEventContestView(vo);
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
				
				System.out.println("현재 시간 : " + dateFormat.format(calendar.getTime()));

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
	@RequestMapping(value = "/event/chkMberAppJson.do")
	public @ResponseBody ModelAndView chkMberAppJson(@ModelAttribute("vo") EventManageVo vo, 
						  HttpServletRequest request, 
						  HttpServletResponse response, 
						  HttpSession session) throws Exception {

		
		Map<String,Object> data 	= new HashMap<String,Object>();
		try {
			
			String mber_id = (String)session.getAttribute("mber_id");
			vo.setReg_id(mber_id);
			
			int result = EventManageService.lb_chkMberApp_Event(vo);
			if (result > 0) {
				data.put("resultCode", "D");	//이미 신청
			}else{
				EventManageVo rvo = EventManageService.lb_getEventContestView(vo);
				if(rvo != null){
					if(rvo.getCt_type().equals("K")){
						int appTarget = EventManageService.lb_getEventContestAppTargetCnt(vo);
						if(appTarget > 0){
							data.put("resultCode", "Y");	//신청 가능
						}else{
							data.put("resultCode", "C");	//참가자 리스트 중 포함되어있지 않음
						}
					}else{
						data.put("resultCode", "Y");	//신청 가능
					}
					
				}else {
					data.put("ResultCode", "N");	//대회정보없음 
				}
			}
			
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			data.put("ResultCode", "E");
			return SendMiPlatform.SendString(response, data);
		}
	}
	
	
	
	/**
	 * 
	 * 이벤트 대회 신청자 리스트페이지
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/event/eventContestAppResult.do")
	public String eventContestAppResult(@ModelAttribute("vo") EventManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session
			, ModelMap model) throws Exception {
		
		
		EventManageVo svo = EventManageService.lb_getEventContestView(vo);
		
		//조 정보
		if(svo != null && svo.getCt_type() != null && svo.getCt_type().equals("L")){
			List<EventManageVo> pList= EventManageService.lb_selectEventContestPartList(svo);
			model.addAttribute("pList", pList);
		}
		
		model.addAttribute("menuDepth1", "7");
		model.addAttribute("menuDepth2", "3");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/event/eventContestAppResult";
	}
		
	
	
//	ontestAppResultJson.do
	/**
	 * 
	 * 대회신청자 리스트JSON
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/event/eventContestAppResultJson.do")
	@ResponseBody
	public ModelAndView eventContestAppResultJson(@ModelAttribute("vo") EventManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
		try{
	
			Map<String,Object> data 	= new HashMap<String,Object>();
	
			
			
			EventManageVo contestVo = EventManageService.lb_getEventContestView(vo);
			if(contestVo != null && contestVo.getCt_type() != null){
				vo.setCt_type(contestVo.getCt_type());
			}
			List<EventManageVo> list = EventManageService.lb_selectClientEventContestAppList(vo);
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
	
	

	
	//왕중왕전 대회 신청 페이지
	@RequestMapping(value="/event/kokContestAppWrite.do")
	public String kokContestAppWrite(@ModelAttribute("vo") EventManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		
		model.addAttribute("jm_param", request.getParameter("jm_param"));
		
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			request.setAttribute("result_code", "502");
			return "forward:/event/kokContestAppList.do";
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
		
		EventManageVo svo= EventManageService.lb_getEventContestView(vo);

		
		String mber_id = (String)session.getAttribute("mber_id");
		vo.setReg_id(mber_id);
		
		int appTarget = EventManageService.lb_getEventContestAppTargetCnt(vo);
		if(!(appTarget > 0)){
			model.addAttribute("msg", "로그인한 신청자 정보가 대회 참가자 명단에 존재하지 않습니다.");
			request.setAttribute("result_code", "504");
			return "forward:/event/kokContestAppList.do";
		}
		
		
		if(svo != null){
			if(svo.getCt_process().equals("E") || svo.getCt_process().equals("F")){
				model.addAttribute("msg", "대회 신청 기간이 아닙니다.");
				request.setAttribute("result_code", "503");
				return "forward:/event/kokContestAppList.do";
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
				return "forward:/event/kokContestAppList.do";
			}
			
			
			request.setAttribute("result_code", "200");
			model.addAttribute("limitPart", svo.getLimit_part());
			
			model.addAttribute("menuDepth1", "7");
			model.addAttribute("menuDepth2", "2");
			model.addAttribute("contestView", svo);
			
			
			model.addAttribute("currPage", vo.getCurrRow());
			model.addAttribute("Srch_input", vo.getSrch_input());
			model.addAttribute("scType", vo.getScType());
			
			
			//조 정보
			List<EventManageVo> pList= EventManageService.lb_selectEventContestPartList(svo);
			model.addAttribute("pList", pList);
			
			return "/client/event/kokContestAppWrite";
		}else{
			request.setAttribute("result_code", "501");
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "forward:/event/kokContestAppList.do";
		}
		
	}	
	
	
	/**
	 * 
	 * 이벤트 대회 신청자 리스트페이지
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/event/kokContestAppResult.do")
	public String kokContestAppResult(@ModelAttribute("vo") EventManageVo vo
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session
			, ModelMap model) throws Exception {
		
		
		EventManageVo svo = EventManageService.lb_getEventContestView(vo);
		
		
		model.addAttribute("menuDepth1", "7");
		model.addAttribute("menuDepth2", "2");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		return "/client/event/kokContestAppResult";
	}
		

	//대회 레인배치 결과페이지
	@RequestMapping(value="/event/kokContestLaneResult.do")
	public String kokContestLaneResult(@ModelAttribute("vo") BoardManageVo vo
												, ModelMap model
												, HttpServletRequest request
												, HttpServletResponse response) throws Exception {
		
		
		
		//조회수 증가
		//BoardManageService.BoardUpdateCnt(vo);
		BoardManageVo svo= BoardManageService.lb_getBoardView(vo);
		
		model.addAttribute("menuDepth1", "7");
		model.addAttribute("menuDepth2", "2");
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("boardView", svo);
		
		List<List<EventManageVo>> resultList = new ArrayList<List<EventManageVo>>();
		
		if(svo != null && svo.getCt_seq()>0){
			EventManageVo evo = new EventManageVo();
			evo.setCt_seq(svo.getCt_seq());
			
			EventManageVo contestVo = EventManageService.lb_getEventContestView(evo);
			if(contestVo != null){
				for(int j = 1; j <= 2; j++){
					evo.setPart(Integer.toString(j));
					List<EventManageVo> list = EventManageService.lb_getEventContestSelectResultExcel(evo);		
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
		

		model.addAttribute("resultList",resultList);
		
		
		return "/client/event/kokContestLaneResult";
	}	
	
	
	
	
	
}
