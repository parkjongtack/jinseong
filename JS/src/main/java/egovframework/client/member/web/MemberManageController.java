package egovframework.client.member.web;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import egovframework.apage.board.service.apageBoardManageService;
import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.apage.member.service.apageMemberManageService;
import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.apage.system.service.apageSystemManageService;
import egovframework.client.board.service.BoardManageService;
import egovframework.client.board.service.BoardManageVo;
import egovframework.client.event.service.EventManageService;
import egovframework.client.event.service.EventManageVo;
import egovframework.client.member.service.MemberManageVo;
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
import egovframework.common.utils.ShaEncryption;
import egovframework.common.utils.StringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class MemberManageController {

private Logger logger = Logger.getLogger(this.getClass());
	
//	@Resource(name = "MemberManageService")
//	private MemberManageService MemberManageService;
	
	@Resource(name = "shopMemberManageService")
	private ShopMemberManageService shopMemberManageService;
	
	@Resource(name = "BoardManageService")
	private BoardManageService BoardManageService;

	@Resource(name = "apageMemberManageService")
	private apageMemberManageService apageMemberManageService;
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name = "CommonUtil")
	private CommonUtil CommonUtil;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	@Resource(name = "apageSystemManageService")
	private apageSystemManageService apageSystemManageService;
	
	@Resource(name = "FileManageService")
	private FileManageService fileManageService;
	
	@Resource(name = "SmsManageService")
	private SmsManageService SmsManageService;
	
	@Autowired
	private ShaEncryption sha;
	
	@Resource(name = "apageBoardManageService")
	private apageBoardManageService apageBoardManageService;
	
	@Resource(name = "EventManageService")
	private EventManageService EventManageService;

	
	
	
	//로그인 페이지
	@RequestMapping("/membership/login.do")
	public String login(@ModelAttribute("vo") ShopMemberManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response) throws Exception{
		
		model.addAttribute("ct_seq", request.getParameter("ct_seq"));
		model.addAttribute("currPage", request.getParameter("currRow"));
		model.addAttribute("returnUrl", request.getParameter("returnUrl"));
		
		model.addAttribute("menuDepth2", "1");
		model.addAttribute("menuDepth1", "6");
		

		model.addAttribute("mobileFlag", mobileCheck(request));
		
		return "/client/member/login";
	}
	
	/**
	 *  로그인
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/membership/loginJson.do")
	public @ResponseBody ModelAndView loginJson(@ModelAttribute("vo") ShopMemberManageVo vo, 
						  @ModelAttribute("vo2") apageMemberManageVo vo2,
						  HttpServletRequest request, 
						  HttpServletResponse response, 
						  HttpSession session) throws Exception {
		
		
		Map<String,Object> data 	= new HashMap<String,Object>();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean loginFlag = false;
		
		//2018.11.11 진승무역 비번 암호화 로직을 변경해야함
		//진승무역 비번 로직은 mysql password 함수 사용해서 임시로 주석으로 막음
		//vo.setMber_pw(sha.encryption(vo.getMber_pw()));
		
		int result = shopMemberManageService.loginAction(vo);
		vo2.setMber_pw(sha.encryption(vo2.getMber_pw()));
		int result2 = apageMemberManageService.adminLoginAction(vo2);
	/*	
		System.out.println("==============================");
		System.out.println("result : " + result);
		System.out.println("==============================");
	*/	
		
		if(session != null){
			session.invalidate();
			session = request.getSession();
		}
		
		if (result > 0) {
			data.put("resultCode", "Y");
			
			shopMemberManageService.updateLastLoginDt(vo);
			ShopMemberManageVo memberVo = shopMemberManageService.selectMemberInfo(vo);
			
			session.setAttribute("mberInfo", memberVo);
			session.setAttribute("mber_id", memberVo.getMber_id());
			session.setAttribute("mber_name", memberVo.getMber_name());				
			session.setAttribute("mber_diyn", memberVo.getDi_yn());				
			data.put("di_yn", memberVo.getDi_yn());
			data.put("rejection_cnt", memberVo.getDi_rejection_cnt());
			request.setAttribute("result_code", "200");
			
			EgovHttpSessionBindingListener listener = new EgovHttpSessionBindingListener();
			request.getSession().setAttribute(memberVo.getMber_id(), listener);
			
			//세션 타임아웃 2시간 설정
			session.setMaxInactiveInterval(120*60) ;
			
			String mobileFlag = mobileCheck(request);
			if(mobileFlag != null && mobileFlag.equals("Y")){
				/**
				 * 자동로그인 토큰 발급
				 * */
				//한달후 날짜
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, +1);
				long timeStamp = (cal.getTimeInMillis()/1000);
				
				String result_token = jwtCreate(vo.getMber_id(), Long.toString(timeStamp));
				
				data.put("result_token",result_token);
			}

		}else{
			request.setAttribute("result_code", "400");
		}
		
		if (result2 > 0) {			
			
			MemberManageVo mvo = new MemberManageVo();
			mvo.setMber_id(vo.getMber_id());
			//MemberManageService.updateLastLoginDt(mvo);
			apageMemberManageVo memberVo = apageMemberManageService.selectAdminMemberInfo(vo2);				
			session.setAttribute("adminInfo", memberVo);
			session.setAttribute("user_id", memberVo.getMber_id());
			session.setAttribute("user_name", memberVo.getMber_name());		
			data.put("resultCode", "Y");
		}
		
		return SendMiPlatform.SendString(response, data);
	}
	
	/**
	 *  로그인(로그인이 필요한 페이지에서 이동했을 때)
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/membership/loginReturnUrl.do")
	public String loginReturnUrl(@ModelAttribute("vo") ShopMemberManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session) throws Exception{
		
		boolean loginFlag = false;
		
		//2018.11.11 진승무역 비번 암호화 로직을 변경해야함
		//진승무역 비번 로직은 mysql password 함수 사용해서 임시로 주석으로 막음
		//vo.setMber_pw(sha.encryption(vo.getMber_pw()));
		
		int result = shopMemberManageService.loginAction(vo);
		/*
		System.out.println("==============================");
		System.out.println("result : " + result);
		System.out.println("==============================");
		*/
		if (result > 0) {			
			loginFlag = true;
			
			shopMemberManageService.updateLastLoginDt(vo);
			ShopMemberManageVo memberVo = shopMemberManageService.selectMemberInfo(vo);			
			session.setAttribute("mberInfo", memberVo);
			session.setAttribute("mber_id", memberVo.getMber_id());
			session.setAttribute("mber_name", memberVo.getMber_name());		
			
			if(request.getParameter("returnUrl").equals("contestAppWrite")) { //대회신청 상세 페이지로 이동 필요
				/*
				model.addAttribute("ct_seq", request.getParameter("ct_seq"));
				System.out.println("-------------------------------------------------------------> "+request.getParameter("currRow"));
				model.addAttribute("currPage", request.getParameter("currRow"));
				
				Map<String, Object> map = new HashMap<String,Object>();
			    map.put("key1", "value1");
			    map.put("key2", "value2");
			    redirectAttributes.addFlashAttribute("vo", map);
				
				return "redirect:/contest/contestAppView.do"; 
				*/
				return "redirect:/contest/contestAppList.do"; 
				
				
			}else if(request.getParameter("returnUrl").equals("asList")) { //AS게시판 목록 페이지로 이동 필요
				return "redirect:/board/asList.do";
			}else if(request.getParameter("returnUrl").equals("consultList")) { //고객상담 목록 페이지로 이동 필요
				return "redirect:/board/consultList.do";
			}
		}
		model.addAttribute("msg","fail");
		model.addAttribute("ct_seq", request.getParameter("ct_seq"));
		model.addAttribute("currPage", request.getParameter("currRow"));
		model.addAttribute("returnUrl", request.getParameter("returnUrl"));
		return "/client/member/login";
	}
	
	
	
	
	
	/* 관리자 로그아웃
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/membership/logout.do")
	public String adminLogout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		
		RequestContextHolder.getRequestAttributes().removeAttribute((String)request.getSession().getAttribute("mber_id"), RequestAttributes.SCOPE_SESSION);
		session.invalidate();
		return "redirect:/main.do";
		
	}
	
	//회원가입 스탭1
	@RequestMapping("/membership/joinStep1.do")
	public String meberJoinStep1(@ModelAttribute("vo") ShopMemberManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response) throws Exception{
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "2");
		
		return "/client/member/joinStep1";
	}
	
	//회원가입 스탭2
	@RequestMapping("/membership/joinStep2.do")
	public String meberJoinStep2(@ModelAttribute("vo") ShopMemberManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response) throws Exception{
		
		if(vo.getAgree_yn() == null || "".equals(vo.getAgree_yn())){
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "/client/member/joinStep2";
		}
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "2");
		
		return "/client/member/joinStep2";
	}
	
	//회원가입 스탭3
	@RequestMapping("/membership/joinStep3.do")
	public String meberJoinStep3(@ModelAttribute("vo") ShopMemberManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response) throws Exception {
		
		//회원 중복 가입 방지 등록된 DI값을 비교하여 일치하면 등록된 회원으로 alert
		logger.debug("DI : " + vo.getReg_code());
		int result = shopMemberManageService.mberDIOverlapCheck(vo);
		if(result > 0){
			model.addAttribute("msg", "등록된 회원 정보가 있습니다.");
			return "/client/member/joinStep2";
		} 
		
		model.addAttribute("mberInfo",vo);
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "2");
		
		return "/client/member/joinStep3";
	}
	
	/**
	 * 회원테이블 아이디 중복체크(사용자,관리자 페이지 AJAX)
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="/membership/mberIdOverlapCheck.do")
	public @ResponseBody ModelAndView getContents(@ModelAttribute("vo") ShopMemberManageVo vo
															,HttpServletRequest request
															,HttpServletResponse response) throws Exception {
		
		try {
			Map<String,Object> data 	= new HashMap<String,Object>();
			int result	= shopMemberManageService.mberIdOverlapCheck(vo);
			
			/*
			System.out.println(request.getParameter("mber_id"));
			System.out.println(result);
			*/
			
			if(result > 0){
				data.put("ResultCode","Y");	//중복
			}else{
				data.put("ResultCode","N");	//중복아님 
			}
			return SendMiPlatform.SendString(response, data);
			
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
	}
	
	
	/**
	 * 회원테이블 이메일 중복체크(사용자,관리자 페이지 AJAX)
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="/membership/mberEmailOverlapCheck.do")
	public @ResponseBody ModelAndView getContents2(@ModelAttribute("vo") ShopMemberManageVo vo
															,HttpServletRequest request
															,HttpServletResponse response) throws Exception {
				
		try {
			Map<String,Object> data 	= new HashMap<String,Object>();
			int result	= shopMemberManageService.mberEmailOverlapCheck(vo);
			
			/*
			System.out.println(request.getParameter("mber_email"));
			System.out.println(result);
			*/
			
			if(result > 0){
				data.put("ResultCode","Y");	//중복
			}else{
				data.put("ResultCode","N");	//중복아님 
			}
			return SendMiPlatform.SendString(response, data);
			
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
	}
	
		
	//회원가입 처리
	@RequestMapping("/membership/joinOk.do")
	public String joinOk(@ModelAttribute("vo") ShopMemberManageVo vo 
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response, HttpSession session) throws Exception{
		
		
		vo.setReg_ip(StringUtil.null2Space(request.getRemoteAddr()));
		vo.setReg_id(vo.getMber_id());
		//2018.11.11 진승무역 비번 암호화 로직을 변경해야함
		//진승무역 비번 로직은 mysql password 함수 사용해서 임시로 주석으로 막음
		//vo.setMber_pw(sha.encryption(vo.getMber_pw()));
		
		if("M".equals(vo.getMber_gender())) {
			vo.setMber_gender("남");
		} else if("F".equals(vo.getMber_gender())) {
			vo.setMber_gender("여");
		}
		
		int check = shopMemberManageService.memberCheck(vo);
		if(check > 0 ) {
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "2");
			model.addAttribute("msg","checkFail");
			return "/client/member/joinStep3";
		}else {
			vo.setJoin_ref("Hompage");
			int result = shopMemberManageService.insertMember(vo);
			if(result > 0){
				//로그인 처리 하지 않음
//			session.setAttribute("mberInfo", vo);
//			session.setAttribute("mber_id", vo.getMber_id());
				session.setAttribute("mber_name", vo.getMber_name());
				return "redirect:/membership/joinStep4.do";
				
			}else{ //오류 페이지로 이동
				model.addAttribute("menuDepth1", "6");
				model.addAttribute("menuDepth2", "2");
				model.addAttribute("msg","fail");
				return "/client/member/joinStep3";			
			}	
			
		} 

	}
	
	//회원가입 스탭4
	@RequestMapping("/membership/joinStep4.do")
	public String meberJoinStep4(@ModelAttribute("vo") ShopMemberManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response, HttpSession session) throws Exception{
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "2");
		
		return "/client/member/joinStep4";
	}
	
	//마이페이지 - 대회신청내역 목록페이지	
	@RequestMapping("/membership/myContestAppList.do")
	public String myContestAppList(@ModelAttribute("vo") BoardManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		String mber_id = (String)session.getAttribute("mber_id");
		
		vo.setReg_id(mber_id);
		/*
		List<BoardManageVo> list = BoardManageService.selectMyContestList(vo);		
		int totCnt = BoardManageService.selectMyContestListCnt(vo);
		*/
		
		List<BoardManageVo> list = BoardManageService.lb_selectMyContestList_union(vo);		
		int totCnt = BoardManageService.lb_selectMyContestListCnt_union(vo);
	
		model.addAttribute("contestList", list);
		model.addAttribute("totalNum", totCnt);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());

		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "8");
		
		return "/client/member/myContestAppList";
	}
	
	//마이페이지 - 대회신청내역 상세페이지
	@RequestMapping("/membership/myContestAppView.do")
	public String myContestAppView(@ModelAttribute("vo") BoardManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) throws Exception {
		
		FileManageVo fvo = new FileManageVo();
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "forward:/membership/login.do";
		}else{
			model.addAttribute("memberinfo",mv);
		}
		
		String mber_id = (String)session.getAttribute("mber_id");
		model.addAttribute("mber_id", mber_id);
		
		//정보
		BoardManageVo svo= BoardManageService.lb_getContestView(vo);
		
		
		model.addAttribute("svo", svo);
		
		
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
				
		//내용
		vo.setReg_id(mber_id);
		BoardManageVo contestView = BoardManageService.selectMyContestView(vo);	
		
		if(contestView != null){
			if(contestView.getBirth() != null){
				String birth_arr[] = contestView.getBirth().split("-");
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
			if(contestView.getTelno() != null){
				String tel_arr[] = contestView.getTelno().split("-");
				for(int i = 0; i < tel_arr.length; i ++){
					model.addAttribute("tel_arr"+i, tel_arr[i]);
				}
			}
			if(contestView.getEmail() != null){
				String email_arr[] = contestView.getEmail().split("@");
				for(int i = 0; i < email_arr.length; i ++){
					model.addAttribute("email_arr"+i, email_arr[i]);
				}
			}
		}
		
		model.addAttribute("contestView", contestView);
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "8");
		
		return "/client/member/myContestAppView";
	}
	
	//마이페이지 - 대회신청 취소
	@RequestMapping("/membership/myContestAppCancel.do")
	public String myContestAppCancel(@ModelAttribute("vo") BoardManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) throws Exception {
		
		int app_seq = vo.getApp_seq();
		String status = vo.getStatus();
		
		BoardManageVo contestVo = BoardManageService.lb_getContestView(vo);
		
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		BoardManageVo nowAppVo  = null;
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			request.setAttribute("result_code", "700");
			return "redirect:/membership/login.do";
		}else{
			String mber_id = (String)session.getAttribute("mber_id");
			vo.setReg_id(mber_id);
			nowAppVo = BoardManageService.selectMyContestView(vo);
			if(nowAppVo == null){
				model.addAttribute("msg", "잘못된 접근입니다.");
				request.setAttribute("result_code", "701");
				return "redirect:/membership/login.do";
			}else if(nowAppVo.getStatus().equals("0003")){
				model.addAttribute("msg","alreadyCancel");
				request.setAttribute("result_code", "702");
				return "redirect:/membership/myContestAppList.do";
			}
		}
		
		
		
		String finish_dt = contestVo.getRefund_finish_date().replaceAll("-", "") + contestVo.getRefund_finish_h() + contestVo.getRefund_finish_m();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Date finish_time = dateFormat.parse(finish_dt);
		Date now = new Date();
		if(!(now.getTime() <= finish_time.getTime()) || !contestVo.getCt_process().equals("E")){	//취소불가능
			model.addAttribute("msg","timeover");
			request.setAttribute("result_code", "703");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}
		
		int result = 0;
		
		if(status.equals("0001") || status.equals("0002") || status.equals("0005")) { //[입금대기 or 입금완료 or 대기]일 때 -> status = 0003으로
			
			result = BoardManageService.cancelMyContest(vo);
			if(result > 0){
				SmsManageVo smv = new SmsManageVo();
				smv.setSubject("[진승무역]");
				smv.setPhone(vo.getTelno());
				StringBuffer sb = new StringBuffer();
				
				// ETC INFO 
				smv.setEtc1(Integer.toString(nowAppVo.getCt_seq()));		//대회 고유번호
				smv.setEtc2(nowAppVo.getPart());							//신청 조
				smv.setEtc3(Integer.toString(nowAppVo.getApp_seq()));		//신청 고유번호
				smv.setEtc5("auto"); 										//전체 발송 플래그
				smv.setEtc6(nowAppVo.getReg_id());							//신청자 ID
				smv.setEtc7(nowAppVo.getJoin_name());						//신청자 명
				smv.setEtc8(nowAppVo.getBirth());							//신청자 생년월일
				smv.setEtc9("0003");										//신청취소
				
				if(vo.getPay_flag().equals("Y")){
					sb.append(vo.getJoin_name()+"님\n");
					sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조"+" / 취소완료\n");
					sb.append("입금사항 확인 후 기입하신 환불계좌로\n");
					sb.append(contestVo.getCt_price()+" 환불 해드리겠습니다.");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}else{
					sb.append(vo.getJoin_name()+"님\n");
					sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조 / 취소완료");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}
			}
			
		}else if(status.equals("0004")) { //[선정]일 때 -> status = 0003 + 대기명단에서 한명 채우기
			
			//취소
			result = BoardManageService.cancelMyContest(vo);
			if(result > 0){
				SmsManageVo smv = new SmsManageVo();
				smv.setSubject("[진승무역]");
				smv.setPhone(vo.getTelno());
				
				
				// ETC INFO 
				smv.setEtc1(Integer.toString(nowAppVo.getCt_seq()));		//대회 고유번호
				smv.setEtc2(nowAppVo.getPart());							//신청 조
				smv.setEtc3(Integer.toString(nowAppVo.getApp_seq()));		//신청 고유번호
				smv.setEtc5("auto"); 										//전체 발송 플래그
				smv.setEtc6(nowAppVo.getReg_id());							//신청자 ID
				smv.setEtc7(nowAppVo.getJoin_name());						//신청자 명
				smv.setEtc8(nowAppVo.getBirth());							//신청자 생년월일
				smv.setEtc9("0003");										//신청취소
				
				
				StringBuffer sb = new StringBuffer();
				if(vo.getPay_flag().equals("Y")){
					sb.append(vo.getJoin_name()+"님\n");
					sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조 / 취소완료\n");
					sb.append("입금사항 확인 후 기입하신 환불계좌로\n");
					sb.append(contestVo.getCt_price()+" 환불 해드리겠습니다.");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}else{
					sb.append(vo.getJoin_name()+"님\n");
					sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조 / 취소완료");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}
			}
			
			//조 대기자 명단에서 1명 추출하기
			BoardManageVo bvo = BoardManageService.selectBackupMember(vo); 
			//추출한 대기자 넣기
			if(bvo != null) {
				vo.setApp_seq(bvo.getApp_seq());
				int result2 = BoardManageService.updateNewMember(vo);
				
				if(result2 > 0 && contestVo.getResult_sms_send_yn() != null && contestVo.getResult_sms_send_yn().equals("Y")){
					
					//대기 ==> 선정 MMS 전송
					SmsManageVo smv = new SmsManageVo();
					StringBuffer sb = new StringBuffer();
					smv.setSubject("[진승무역]");
					sb.append(bvo.getJoin_name()+"님\n");
					sb.append(contestVo.getCt_sbj() +"/" + vo.getPart() + "조");
					if(vo.getLane() != null && !vo.getLane().equals("") && contestVo.getLane_sms_send_yn() != null && contestVo.getLane_sms_send_yn().equals("Y")){
						sb.append("/"+vo.getLane() +"레인 ");
					}
					sb.append("/ 배정 확정\n");
					sb.append("-계좌정보-\n");
					sb.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
					//sb.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망");
					sb.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망\n");
					sb.append("(반드시 참가자명으로 입금 바랍니다.)");
					smv.setMsg(sb.toString());
					smv.setPhone(bvo.getTelno());
					
					
					// ETC INFO 
					smv.setEtc1(Integer.toString(bvo.getCt_seq()));			//대회 고유번호
					smv.setEtc2(bvo.getPart());								//신청 조
					smv.setEtc3(Integer.toString(bvo.getApp_seq()));		//신청 고유번호
					smv.setEtc5("auto"); 									//전체 발송 플래그
					smv.setEtc6(bvo.getReg_id());							//신청자 ID
					smv.setEtc7(bvo.getJoin_name());						//신청자 명
					smv.setEtc8(bvo.getBirth());							//신청자 생년월일
					smv.setEtc9("0004");				
					
					
					SmsManageService.insertMmsList(smv);
				}
			}
			

			apageBoardManageVo avo = new apageBoardManageVo();
			avo.setPart(vo.getPart());
			avo.setCt_seq(vo.getCt_seq());
			if(contestVo.getCt_process() != null && contestVo.getCt_process().equals("E")){
				int a = apageBoardManageService.apageContestAppResultOrderStatusChange(avo);
			}
		}
		
		//result
		if(result > 0){
			model.addAttribute("msg","success");
			request.setAttribute("result_code", "200");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}else{
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "8");
			model.addAttribute("msg","fail");
			request.setAttribute("result_code", "704");
			return "/client/member/myContestAppView";			
		}
	}
	
	//마이페이지 - 대회신청정보 수정
	@RequestMapping("/membership/myContestAppUpdate.do")
	public String myContestAppUpdate(@ModelAttribute("vo") BoardManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session) throws Exception {
					
		
		int result = 0;
		result = BoardManageService.cpageUpdateMyContestAppInfo(vo);
		
		//result
		if(result > 0){
			model.addAttribute("msg","updtsuccess");
			request.setAttribute("result_code", "200");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}else{
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "8");
			model.addAttribute("msg","fail");
			request.setAttribute("result_code", "800");
			return "/client/member/myContestAppView";			
		}
	}
	
	//마이페이지 - 비밀번호 재확인 페이지
	@RequestMapping("/membership/myPwdCheckPage.do")
	public String myPwdCheckPage(@ModelAttribute("vo") ShopMemberManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) {
		
		String mber_id = (String)session.getAttribute("mber_id");
		model.addAttribute("mber_id", mber_id);
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "3");
		
		return "/client/member/myPwdCheck";
	}
	
	//마이페이지 - 비밀번호 재확인 처리
	@RequestMapping("/membership/myPwdCheck.do")
	public String myPwdCheck(@ModelAttribute("vo") ShopMemberManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) throws Exception {
		
		//vo.setMber_pw(sha.encryption(vo.getMber_pw()));
		int result = shopMemberManageService.myPwdCheck(vo);
		
		if(result > 0){  					
			model.addAttribute("msg","success");
			return "redirect:/membership/memberUpdt.do";			
		}else{ 
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "3");
			model.addAttribute("msg","fail");
			return "/client/member/myPwdCheck";
			//return "redirect:/membership/myPwdCheckPage.do";
		}
	}
	
	//마이페이지 - 회원정보수정 페이지
	@RequestMapping("/membership/memberUpdt.do")
	public String memberUpdt(@ModelAttribute("vo") ShopMemberManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) {
		try {
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "/client/member/memberUpdt";
		}else{
			model.addAttribute("memberinfo",mv);	
		}
		logger.debug("mber_id : " + mv.getMber_id());
		vo.setMber_id(mv.getMber_id());
		ShopMemberManageVo memberVo = shopMemberManageService.selectMemberInfo(vo);		
		if(memberVo.getMber_tel() != null){
			String tel_arr[] = memberVo.getMber_tel().split("-");
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
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "3");
		model.addAttribute("memberVo", memberVo);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/client/member/memberUpdt";
	}
	
	
	//회원정보 수정처리
	@RequestMapping("/membership/updateMember.do")
	public String joinOk2(@ModelAttribute("vo") ShopMemberManageVo vo 
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session)throws Exception{
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		vo.setUpdt_id(mv.getMber_id());
		//2018.11.11 진승무역 비번 암호화 로직을 변경해야함
		//진승무역 비번 로직은 mysql password 함수 사용해서 임시로 주석으로 막음
		//vo.setMber_pw(sha.encryption(vo.getMber_pw()));
		
		if("M".equals(vo.getMber_gender())) {
			vo.setMber_gender("남");			
		} else if ("F".equals(vo.getMber_gender())) {
			vo.setMber_gender("여");
		}
		
		int result = shopMemberManageService.updateMember(vo);
		
		if(result > 0){
			model.addAttribute("msg","success");
			return "/client/member/memberUpdt";			
		}else{
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "3");
			model.addAttribute("msg","fail");
			return "/client/member/memberUpdt";			
		}	
	}
	
	//마이페이지 - 회원탈퇴 페이지
	@RequestMapping("/membership/memberLeavePage.do")
	public String memberLeavePage(@ModelAttribute("vo") ShopMemberManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) {
		
		String mber_id = (String)session.getAttribute("mber_id");
		model.addAttribute("mber_id", mber_id);
		
		String mber_name = (String)session.getAttribute("mber_name");
		model.addAttribute("mber_name", mber_name);
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "10");
		
		return "/client/member/memberLeavePage";
	}
	
	//마이페이지 - 회원탈퇴 비밀번호 확인 AJAX
	@RequestMapping("/membership/memberLeave_pwdCheck.do")
	public @ResponseBody ModelAndView memberLeave_pwdCheck(@ModelAttribute("vo") ShopMemberManageVo vo 
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session)throws Exception{
		
		try {
			
			String mber_id = (String)session.getAttribute("mber_id");
			
			 Map<String,Object> data = new HashMap<String,Object>();
			 
			 ShopMemberManageVo svo = shopMemberManageService.getMyInfoForLeave(vo);
			 
			 if(svo != null && svo.getMber_id().equals(mber_id)) {
				 data.put("ResultCode","Y");	//일치
			 }else {
				 data.put("ResultCode","N");	//불일치
			 }
			 return SendMiPlatform.SendString(response, data);
			 
		} catch (Exception e) {
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	//마이페이지 - 회원탈퇴 처리
	@RequestMapping("/membership/memberLeave.do")
	public String memberLeave(@ModelAttribute("vo") ShopMemberManageVo vo 
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session)throws Exception{
		
		ShopMemberManageVo svo = shopMemberManageService.getMyInfoForLeave(vo);
		if(svo == null){
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "10");
			model.addAttribute("msg","pwfail");
			return "/client/member/memberLeavePage";
		}
		
		int update_result = shopMemberManageService.updateMyWithdrawInfo(vo);

		vo.setMber_email(svo.getMber_email());
		vo.setMber_phone(svo.getMber_phone());
		vo.setMber_tel(svo.getMber_tel());
		vo.setZipcode(svo.getZipcode());
		vo.setAddr(svo.getAddr());
		vo.setAddr_detail(svo.getAddr_detail());
		vo.setMber_birth(svo.getMber_birth());
		vo.setMber_gender(svo.getMber_gender());
		vo.setMber_milage(svo.getMber_milage());
		vo.setMber_emoney(svo.getMber_emoney());
		int insert_result = shopMemberManageService.insertMyWithdrawInfo(vo);
		
		
//		System.out.println("---------------------------------------------------->"+update_result);
//		System.out.println("---------------------------------------------------->"+insert_result);
		
		
		if(update_result > 0 && insert_result > 0){  					
			model.addAttribute("msg","success");
			//return "redirect:/main.do";
			//return "redirect:/membership/logout.do";
			return "/client/member/memberLeavePage";
		}else{ 
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "10");
			model.addAttribute("msg","fail");
			return "/client/member/memberLeavePage";
		}
	}
	
	//id 찾기 페이지로
	@RequestMapping("/membership/idFind.do")
	public String id(@ModelAttribute("vo") ShopMemberManageVo vo
					,Model model
					,HttpServletRequest request
					,HttpServletResponse response) throws Exception{

			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "4");
			return "/client/member/idFind";
			
	}
	
	// 회원 정보로 id 찾기
	@RequestMapping("/membership/idFind2.do")
	public String idFind(@ModelAttribute("vo") ShopMemberManageVo vo
					,Model model
					,HttpServletRequest request
					,HttpServletResponse response) throws Exception{
			
		String result = shopMemberManageService.idFind(vo);
		
		if(result == null){
			model.addAttribute("msg", "fail");
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "4");
			return "/client/member/idFind";			
		}else{
			model.addAttribute("memberId", result);
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "4");
			return "/client/member/idFind2";			
		}
	}
	
	// 휴대폰으로 id 찾기
	@RequestMapping("/membership/idFind3.do")
	public String midFind(@ModelAttribute("vo") ShopMemberManageVo vo
					,Model model
					,HttpServletRequest request
					,HttpServletResponse response) throws Exception{
		
		String result = shopMemberManageService.midFind(vo);

		if(result == null){
			model.addAttribute("msg", "fail2");
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "4");
			return "/client/member/idFind";			
		}else{
			model.addAttribute("memberId", result);
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "4");
			return "/client/member/idFind2";			
		}
	}
	
	

	//pw 찾기페이지로
	@RequestMapping("/membership/pwFind.do")
	public String pw(@ModelAttribute("vo") ShopMemberManageVo vo
					,Model model
					,HttpServletRequest request
					,HttpServletResponse response) throws Exception{

			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "7");
			return "/client/member/pwFind";
			
	}
	
	
	// 회원정보로 pw 찾기(수정페이지로)
	@RequestMapping("/membership/pwFind2.do")
	public String pwFind(@ModelAttribute("vo") ShopMemberManageVo vo
			,Model model
			,HttpServletRequest request
			,HttpServletResponse response) throws Exception{
		
		int result = shopMemberManageService.pwFind(vo);
		if(result > 0) {
			model.addAttribute("memberId", vo.getMber_id());
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "7");
				return "/client/member/pwFind2";										
		}else{
			model.addAttribute("msg", "fail");
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "7");
			return "/client/member/pwFind";		
		}
	}
	
	
	// 휴대폰으로 pw 찾기 (수정페이지로)
	@RequestMapping("/membership/mpwFind.do")
	public String mpwFind(@ModelAttribute("vo") ShopMemberManageVo vo
			,Model model
			,HttpServletRequest request
			,HttpServletResponse response) throws Exception{
		
		int result = shopMemberManageService.mpwFind(vo);
		if(result > 0) {
			model.addAttribute("rc", vo.getReg_code());
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "7");
			return "/client/member/pwFind2";										
		}else{
			model.addAttribute("msg", "fail2");
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "7");
			return "/client/member/pwFind";		
		}
		
	}
					
	//pw 수정
	@RequestMapping("/membership/updatePw.do")
	public String updatePw(@ModelAttribute("vo") ShopMemberManageVo vo 
			,Model model
			,HttpServletRequest request
			,HttpServletResponse response
			,HttpSession session)throws Exception{
		
		//2018.11.11 진승무역 비번 암호화 로직을 변경해야함
		//진승무역 비번 로직은 mysql password 함수 사용해서 임시로 주석으로 막음
		//vo.setMber_pw(sha.encryption(vo.getMber_pw()));
			
		if (vo.getHp_chck().equals("Y")) {
			//reg 코드 값으로 업뎃
			int result = shopMemberManageService.mupdatePw(vo);
							
			if(result > 0){
				model.addAttribute("msg","success");
				return "/client/member/pwFind2";			
					
			}else{
				model.addAttribute("menuDepth1", "6");
				model.addAttribute("menuDepth2", "3");
				model.addAttribute("msg","fail");
				return "/client/member/pwFind2";			
			}
			
			}else {
				// id 값으로 업데이트
				int result = shopMemberManageService.updatePw(vo);
				if(result > 0){
					model.addAttribute("msg","success");
					return "/client/member/pwFind2";
					
				}else{
					model.addAttribute("menuDepth1", "6");
					model.addAttribute("menuDepth2", "3");
					model.addAttribute("msg","fail");
					return "/client/member/pwFind2";			
				}
				
			}
			
		}
	
	
	

	//마이페이지 - 대회신청 취소가능 여부 확인
	@RequestMapping("/membership/myContestAppCancelCheckYn.do")
	public @ResponseBody ModelAndView myContestAppCancelCheckYn(@ModelAttribute("vo") BoardManageVo vo 
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session)throws Exception{
		
		try {
			
			String mber_id = (String)session.getAttribute("mber_id");
			
			Map<String,Object> data = new HashMap<String,Object>();
			BoardManageVo contestVo = BoardManageService.lb_getContestView(vo);
 
			if(contestVo != null){
				if(contestVo.getRefund_finish_date() == null || contestVo.getRefund_finish_h() == null || contestVo.getRefund_finish_m() == null){
					data.put("ResultCode","N");	//불일치
				}else{
					String finish_dt = contestVo.getRefund_finish_date().replaceAll("-", "") + contestVo.getRefund_finish_h() + contestVo.getRefund_finish_m();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
					Date finish_time = dateFormat.parse(finish_dt);
					Date now = new Date();
					if(now.getTime() <= finish_time.getTime() && contestVo.getCt_process().equals("E")){	//신청가능
						data.put("ResultCode","Y");	//일치
					}else{
						data.put("ResultCode","N");	//불일치
					}
				}
			}else{
				data.put("ResultCode","N");	//불일치
			}
			
			return SendMiPlatform.SendString(response, data);
			 
		} catch (Exception e) {
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	
	/**
	 * 모바일 자동로그인 처리
	 * */
	@RequestMapping("/membership/mobileAutoLogin.do")
	public @ResponseBody ModelAndView mobileAutoLogin(@ModelAttribute("vo") ShopMemberManageVo vo
			,Model model
			,HttpServletRequest request
			,HttpServletResponse response
			,HttpSession session) throws Exception{
		
		
		
		try {
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			if(session != null){
				session.invalidate();
				session = request.getSession();
			}
			
			String access_token = request.getParameter("access_token");
			String token_result = jwtCertification(access_token);
			if(token_result != null && !token_result.equals("fail")){
				vo.setMber_id(token_result);
				ShopMemberManageVo memberVo = shopMemberManageService.selectMemberInfo(vo);
				if(memberVo != null){
					session.setAttribute("mberInfo", memberVo);
					session.setAttribute("mber_id", memberVo.getMber_id());
					session.setAttribute("mber_name", memberVo.getMber_name());				
					request.setAttribute("result_code", "200");
					
					EgovHttpSessionBindingListener listener = new EgovHttpSessionBindingListener();
					//request.getSession().setAttribute(memberVo.getMber_id(), listener);
					session.setAttribute(memberVo.getMber_id(), listener);
					
					data.put("resultCode","Y");	//중복
				}else{
					data.put("resultCode","N");	//중복
				}
			}else{
				data.put("resultCode","N");	//중복
			}
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
		
	}
	

	
	/**
	 * 모바일 확인
	 * */
	private static String mobileCheck(HttpServletRequest request) throws Exception{
		String userAgent = request.getHeader("user-agent");
		boolean mobile1 = userAgent.matches(".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
		boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
//		System.out.println("=========================================");
//		System.out.println("request.getHeader(\"user-agent\") ==> " + request.getHeader("user-agent"));
//		System.out.println("mobile1==> " + mobile1 + " / " + "mobile2 ==> " + mobile2);
//		System.out.println("=========================================");
		
		if(mobile1 || mobile2) {
			return "Y";
		}else{
			return "N";
		}
	}
	
	/**
	 * JWT 토큰 생성
	 * */
	public String jwtCreate(String id, String expireDt) throws Exception{
		try{
			
			String tokenKey = propertyService.getString("Globals.jwtTokenKey");
			Algorithm algorithm = Algorithm.HMAC256(tokenKey);
			
			String token = JWT.create().withClaim("user_id", id).withClaim("expireDt", expireDt).sign(algorithm);

			return token;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("1305 LINE" + e.getMessage());
			return "fail";
		}
	}
	
	
	/**
	 * JWT 토큰 유효성 검사
	 * */
	public String jwtCertification(String token) throws Exception{
		try {
			String[] token_arr = token.split("\\.");
			if(token != null && token_arr.length == 3){
				String datas = token_arr[1];
				
				
				Decoder decoder = Base64.getDecoder(); 
				String json_data = new String(decoder.decode(datas));
				
				JsonParser jpa = new JsonParser();
				JsonElement jel = jpa.parse(json_data);
				JsonObject job = (JsonObject)jel.getAsJsonObject();
				String expire_dt = job.get("expireDt").toString().replaceAll("\"", "");
				String request_token = jwtCreate(job.get("user_id").toString().replaceAll("\"", ""), expire_dt);
				
				Calendar cal = Calendar.getInstance();
				long now_timeStamp = (cal.getTimeInMillis()/1000);
				
				/*
				System.out.println("================== RESULT =====================");
				System.out.println("request_token ==> " + request_token);
				System.out.println("token ==> " + token);
				System.out.println("now_timeStamp ==> " + now_timeStamp);
				System.out.println("Long.valueOf(expire_dt) ==> " + expire_dt);
				System.out.println("====================RESULT END ================");
				*/
				if(request_token.equals(token) && (now_timeStamp <= Long.valueOf(expire_dt))){
					return job.get("user_id").toString().replaceAll("\"", "");
				}else{
					return "fail";
				}
			}else{
				return "fail";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("1352 LINE " + e.getMessage());
			return "fail";
		}
	}
	

	
	/**
	 * 본인인증 거절
	 **/
	@RequestMapping("/membership/diReject.do")
	public @ResponseBody ModelAndView diReject(@ModelAttribute("vo") ShopMemberManageVo vo
			,Model model
			,HttpServletRequest request
			,HttpServletResponse response
			,HttpSession session) throws Exception{
		
		
		
		try {
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			String mber_id = (String)session.getAttribute("mber_id");
			if(mber_id == null || mber_id.equals("")){
				data.put("resultCode", "N");
			}else{
				vo.setMber_id(mber_id);
				int result = shopMemberManageService.updateMemberDiRejectCnt(vo);
				if(result > 0 ){
					data.put("resultCode", "Y");
				}else{
					data.put("resultCode", "N");
				}
			}
			
			return SendMiPlatform.SendString(response, data);
			
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
		
	}
	
	
	/**
	 * 본인인증 코드 중복확인
	 **/
	@RequestMapping("/membership/diOverLapCheck.do")
	public @ResponseBody ModelAndView diOverLapCheck(@ModelAttribute("vo") ShopMemberManageVo vo
			,Model model
			,HttpServletRequest request
			,HttpServletResponse response
			,HttpSession session) throws Exception{
		
		
		
		Map<String,Object> data 	= new HashMap<String,Object>();
		try {
			
			String mber_id = (String)session.getAttribute("mber_id");
			if(mber_id == null || mber_id.equals("")){
				data.put("resultCode", "L");
			}else{
				vo.setMber_id(mber_id);
				int result = shopMemberManageService.mpwFind(vo);
				if(result > 0 ){
					data.put("resultCode", "Y");
				}else{
					data.put("resultCode", "N");
				}
			}
			
			return SendMiPlatform.SendString(response, data);
			
			
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode", "E");
			return SendMiPlatform.SendString(response, data);
		}	
		
	}
	
	
	/**
	 * 본인인증 코드 UPDATE
	 **/
	@RequestMapping("/membership/diUpdate.do")
	public @ResponseBody ModelAndView diUpdate(@ModelAttribute("vo") ShopMemberManageVo vo
			,Model model
			,HttpServletRequest request
			,HttpServletResponse response
			,HttpSession session) throws Exception{
		
		
		
		try {
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			String mber_id = (String)session.getAttribute("mber_id");
			if(mber_id == null || mber_id.equals("")){
				data.put("resultCode", "N");
			}else{
				vo.setMber_id(mber_id);
				if("M".equals(vo.getMber_gender())) {
					vo.setMber_gender("남");
				} else if("F".equals(vo.getMber_gender())) {
					vo.setMber_gender("여");
				}
				
				vo.setMber_birth(vo.getBirth_year()+"-"+vo.getBirth_month()+"-"+vo.getBirth_day());
				
				int result = shopMemberManageService.updateMemberDiInfo(vo);
				if(result > 0 ){
					data.put("resultCode", "Y");
				}else{
					data.put("resultCode", "N");
				}
			}
			
			return SendMiPlatform.SendString(response, data);
			
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
		
	}
	
	
	/**
	 * 본인인증 거절 카운팅 2로 변경
	 **/
	@RequestMapping("/membership/rejectLastCnt.do")
	public @ResponseBody ModelAndView rejectLastCnt(@ModelAttribute("vo") ShopMemberManageVo vo
			,Model model
			,HttpServletRequest request
			,HttpServletResponse response
			,HttpSession session) throws Exception{
		
		
		
		try {
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			String mber_id = (String)session.getAttribute("mber_id");
			if(mber_id == null || mber_id.equals("")){
				data.put("resultCode", "N");
			}else{
				vo.setMber_id(mber_id);
				int result = shopMemberManageService.updateMemberDiRejectLastCount(vo);
				if(result > 0 ){
					data.put("resultCode", "Y");
				}else{
					data.put("resultCode", "N");
				}
			}
			
			return SendMiPlatform.SendString(response, data);
			
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
		
	}
	
	
	
	
	/**
	* 본인인증 거절 처리
	**/
	@RequestMapping("/membership/notCertificateUpdate.do")
	public @ResponseBody ModelAndView notCertificateUpdate(@ModelAttribute("vo") ShopMemberManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session) throws Exception{

		try {
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			
			String mber_id = (String)session.getAttribute("mber_id");
			if(mber_id == null || mber_id.equals("")){
				data.put("resultCode", "N");
			}else{
				vo.setMber_id(mber_id);
				int result = shopMemberManageService.updateNotCertificateMember(vo);
				if(result > 0){
					data.put("resultCode","Y");	//
				}else{
					data.put("resultCode","N");	//
				}
			}
		
			
			return SendMiPlatform.SendString(response, data);
			
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
		
	}
	
	
	
	
	
	/**
	 * 마이페이지 이벤트 대회 신청내역 상세페이지
	 *
	 * 
	 * */
	//마이페이지 - 대회신청내역 상세페이지
	@RequestMapping("/membership/myEventContestAppView.do")
	public String myEventContestAppView(@ModelAttribute("vo") EventManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) throws Exception {
		
		FileManageVo fvo = new FileManageVo();
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "forward:/membership/login.do";
		}else{
			model.addAttribute("memberinfo",mv);
		}
		
		String mber_id = (String)session.getAttribute("mber_id");
		model.addAttribute("mber_id", mber_id);
		
		//대회 정보
		EventManageVo svo = EventManageService.lb_getEventContestView(vo);
		
		model.addAttribute("svo", svo);
		
		
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
				
		//내용
		vo.setReg_id(mber_id);
		EventManageVo contestView = EventManageService.lb_selectMyEventContestView(vo);
		//BoardManageVo contestView = BoardManageService.selectMyContestView(vo);	
		
		if(contestView != null){
			if(contestView.getBirth() != null){
				String birth_arr[] = contestView.getBirth().split("-");
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
			if(contestView.getTelno() != null){
				String tel_arr[] = contestView.getTelno().split("-");
				for(int i = 0; i < tel_arr.length; i ++){
					model.addAttribute("tel_arr"+i, tel_arr[i]);
				}
			}
			if(contestView.getEmail() != null){
				String email_arr[] = contestView.getEmail().split("@");
				for(int i = 0; i < email_arr.length; i ++){
					model.addAttribute("email_arr"+i, email_arr[i]);
				}
			}
		}
		
		model.addAttribute("contestView", contestView);
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "8");
		
		if("S".equals(contestView.getCt_type())){
			return "/client/member/myEventContestAppView_shop"+"_"+contestView.getCt_seq();
		}else{
			return "/client/member/myEventContestAppView";
		}
	}
	
	
	//마이페이지 - 대회신청정보 수정
	@RequestMapping("/membership/myEventContestAppUpdate.do")
	public String myEventContestAppUpdate(@ModelAttribute("vo") EventManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session) throws Exception {
					
		
		int result = 0;
		result = EventManageService.cpageUpdateMyEventContestAppInfo(vo);
		
		//result
		if(result > 0){
			model.addAttribute("msg","updtsuccess");
			request.setAttribute("result_code", "200");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}else{
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "8");
			model.addAttribute("msg","fail");
			request.setAttribute("result_code", "800");
			return "/client/member/myEventContestAppView";			
		}
	}
	
	

	//마이페이지 - 이벤트 대회 신청 취소가능 여부 확인
	@RequestMapping("/membership/myEventContestAppCancelCheckYn.do")
	public @ResponseBody ModelAndView myEventContestAppCancelCheckYn(@ModelAttribute("vo") EventManageVo vo 
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session)throws Exception{
		
		try {
			
			String mber_id = (String)session.getAttribute("mber_id");
			
			Map<String,Object> data = new HashMap<String,Object>();
			EventManageVo contestVo = EventManageService.lb_getEventContestView(vo);
 
			if(contestVo != null){
				if(contestVo.getRefund_finish_date() == null || contestVo.getRefund_finish_h() == null || contestVo.getRefund_finish_m() == null){
					data.put("ResultCode","N");	//불일치
				}else{
					String finish_dt = contestVo.getRefund_finish_date().replaceAll("-", "") + contestVo.getRefund_finish_h() + contestVo.getRefund_finish_m();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
					Date finish_time = dateFormat.parse(finish_dt);
					Date now = new Date();
					if(now.getTime() <= finish_time.getTime() && !contestVo.getCt_process().equals("F")){	//신청가능
						data.put("ResultCode","Y");	//일치
					}else{
						data.put("ResultCode","N");	//불일치
					}
				}
			}else{
				data.put("ResultCode","N");	//불일치
			}
			
			return SendMiPlatform.SendString(response, data);
			 
		} catch (Exception e) {
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	
	
	//마이페이지 - 이벤트 대회 신청 취소
	@RequestMapping("/membership/myEventContestAppCancel.do")
	public String myEventContestAppCancel(@ModelAttribute("vo") EventManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) throws Exception {
		
		int app_seq = vo.getApp_seq();
		String status = vo.getStatus();

		EventManageVo contestVo = EventManageService.lb_getEventContestView(vo);
		
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		EventManageVo nowAppVo  = null;
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			request.setAttribute("result_code", "700");
			return "redirect:/membership/login.do";
		}else{
			String mber_id = (String)session.getAttribute("mber_id");
			vo.setReg_id(mber_id);
			nowAppVo = EventManageService.lb_selectMyEventContestView(vo);
			//nowAppVo = BoardManageService.selectMyContestView(vo);
			if(nowAppVo == null){
				model.addAttribute("msg", "잘못된 접근입니다.");
				request.setAttribute("result_code", "701");
				return "redirect:/membership/login.do";
			}else if(nowAppVo.getStatus().equals("0003")){
				model.addAttribute("msg","alreadyCancel");
				request.setAttribute("result_code", "702");
				return "redirect:/membership/myContestAppList.do";
			}
		}
		
		
		
		String finish_dt = contestVo.getRefund_finish_date().replaceAll("-", "") + contestVo.getRefund_finish_h() + contestVo.getRefund_finish_m();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Date finish_time = dateFormat.parse(finish_dt);
		Date now = new Date();
		if(!(now.getTime() <= finish_time.getTime()) || contestVo.getCt_process().equals("F")){	//취소불가능
			model.addAttribute("msg","timeover");
			request.setAttribute("result_code", "703");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}
		
		int result = 0;
		
		if(status.equals("0001") || status.equals("0002") || status.equals("0005")) { //[입금대기 or 입금완료 or 대기]일 때 -> status = 0003으로
			
//			result = BoardManageService.cancelMyContest(vo);
			result = EventManageService.cancelMyEventContest(vo);
			if(result > 0){
				SmsManageVo smv = new SmsManageVo();
				smv.setSubject("[진승무역]");
				smv.setPhone(vo.getTelno());
				StringBuffer sb = new StringBuffer();
				
				// ETC INFO 
				smv.setEtc1(Integer.toString(nowAppVo.getCt_seq()));		//대회 고유번호
				smv.setEtc2(nowAppVo.getPart());							//신청 조
				smv.setEtc3(Integer.toString(nowAppVo.getApp_seq()));		//신청 고유번호
				smv.setEtc5("event_auto"); 									//전체 발송 플래그
				smv.setEtc6(nowAppVo.getReg_id());							//신청자 ID
				smv.setEtc7(nowAppVo.getJoin_name());						//신청자 명
				smv.setEtc8(nowAppVo.getBirth());							//신청자 생년월일
				smv.setEtc9("0003");										//신청취소
				
				if(vo.getPay_flag().equals("Y")){
					sb.append(vo.getJoin_name()+"님\n");
					
					if(vo.getCt_type().equals("L")){
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart_ord()+"조"+" / 취소완료\n");
					}else{
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조"+" / 취소완료\n");
					}
					sb.append("입금사항 확인 후 기입하신 환불계좌로\n");
					sb.append(contestVo.getCt_price()+" 환불 해드리겠습니다.");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}else{
					sb.append(vo.getJoin_name()+"님\n");
					if(vo.getCt_type().equals("L")){
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart_ord()+"조"+" / 취소완료\n");
					}else{
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조"+" / 취소완료\n");
					}
					//sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조 / 취소완료");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}
			}
			
		}else if(status.equals("0004")) { //[선정]일 때 -> status = 0003 + 대기명단에서 한명 채우기
			
			//취소
			//result = BoardManageService.cancelMyContest(vo);
			result = EventManageService.cancelMyEventContest(vo);
			if(result > 0){
				SmsManageVo smv = new SmsManageVo();
				smv.setSubject("[진승무역]");
				smv.setPhone(vo.getTelno());
				
				
				// ETC INFO 
				smv.setEtc1(Integer.toString(nowAppVo.getCt_seq()));		//대회 고유번호
				smv.setEtc2(nowAppVo.getPart());							//신청 조
				smv.setEtc3(Integer.toString(nowAppVo.getApp_seq()));		//신청 고유번호
				smv.setEtc5("event_auto");									//전체 발송 플래그
				smv.setEtc6(nowAppVo.getReg_id());							//신청자 ID
				smv.setEtc7(nowAppVo.getJoin_name());						//신청자 명
				smv.setEtc8(nowAppVo.getBirth());							//신청자 생년월일
				smv.setEtc9("0003");										//신청취소
				
				
				StringBuffer sb = new StringBuffer();
				if("Y".equals(vo.getPay_flag())){
					sb.append(vo.getJoin_name()+"님\n");
					
					if("L".equals(vo.getCt_type())){
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart_ord()+"조"+" / 취소완료\n");
					}else{
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조"+" / 취소완료\n");
					}
					//sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조 / 취소완료\n");
					sb.append("입금사항 확인 후 기입하신 환불계좌로\n");
					sb.append(contestVo.getCt_price()+" 환불 해드리겠습니다.");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}else{
					sb.append(vo.getJoin_name()+"님\n");
					if("L".equals(vo.getCt_type())){
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart_ord()+"조"+" / 취소완료\n");
					}else{
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조"+" / 취소완료\n");
					}
					//sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조 / 취소완료");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}
			}
			
			//조 대기자 명단에서 1명 추출하기
			EventManageVo bvo = EventManageService.lb_selectEventContstBackupMember(vo); 
			//BoardManageVo bvo = BoardManageService.selectBackupMember(vo); 
			//추출한 대기자 넣기
			if(bvo != null) {
				vo.setApp_seq(bvo.getApp_seq());
				int result2 = EventManageService.updateEventContstNewMember(vo);
				
				if(result2 > 0 && contestVo.getResult_sms_send_yn() != null && contestVo.getResult_sms_send_yn().equals("Y")){
					
					//대기 ==> 선정 MMS 전송
					SmsManageVo smv = new SmsManageVo();
					StringBuffer sb = new StringBuffer();
					smv.setSubject("[진승무역]");
					sb.append(bvo.getJoin_name()+"님\n");
					if(vo.getCt_type().equals("L")){
						sb.append(contestVo.getCt_sbj() +"/" + vo.getPart_ord() + "조");
					}else{
						sb.append(contestVo.getCt_sbj() +"/" + vo.getPart() + "조");
					}
					//sb.append(contestVo.getCt_sbj() +"/" + vo.getPart() + "조");
					if(vo.getLane() != null && !vo.getLane().equals("") && contestVo.getLane_sms_send_yn() != null && contestVo.getLane_sms_send_yn().equals("Y")){
						sb.append("/"+vo.getLane() +"레인 ");
					}
					sb.append("/ 배정 확정\n");
					/*
					sb.append("-계좌정보-\n");
					sb.append(contestVo.getCt_bank() + "/" + contestVo.getCt_acchholder() + "/" + contestVo.getCt_account() + "/" + contestVo.getCt_price() +"\n");
					//sb.append(contestVo.getCt_deposit_stdt()+" " +contestVo.getCt_deposit_sth()+":"+contestVo.getCt_deposit_stm() + "~" +contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망");
					sb.append(contestVo.getCt_deposit_eddt()+" " + contestVo.getCt_deposit_edh()+":"+contestVo.getCt_deposit_edm()+"까지 입금 요망");
					sb.append("(반드시 참가자명으로 입금 바랍니다.)");
					*/
					smv.setMsg(sb.toString());
					smv.setPhone(bvo.getTelno());
					
					
					// ETC INFO 
					smv.setEtc1(Integer.toString(bvo.getCt_seq()));			//대회 고유번호
					smv.setEtc2(bvo.getPart());								//신청 조
					smv.setEtc3(Integer.toString(bvo.getApp_seq()));		//신청 고유번호
					smv.setEtc5("event_auto"); 								//전체 발송 플래그
					smv.setEtc6(bvo.getReg_id());							//신청자 ID
					smv.setEtc7(bvo.getJoin_name());						//신청자 명
					smv.setEtc8(bvo.getBirth());							//신청자 생년월일
					smv.setEtc9("0004");				
					
					
					SmsManageService.insertMmsList(smv);
				}
			}
			

			EventManageVo avo = new EventManageVo();
			avo.setPart(vo.getPart());
			avo.setCt_seq(vo.getCt_seq());
			if(contestVo.getCt_process() != null && contestVo.getCt_process().equals("E")){
				int a = EventManageService.eventContestAppResultOrderStatusChange(avo);
				//int a = EventManageService.apageContestAppResultOrderStatusChange(avo);
			}
		}
		
		//result
		if(result > 0){
			model.addAttribute("msg","success");
			request.setAttribute("result_code", "200");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}else{
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "8");
			model.addAttribute("msg","fail");
			request.setAttribute("result_code", "704");
			return "/client/member/myEventContestAppView";			
		}
	}
	
	
	
	
	

	/**
	 * 마이페이지 이벤트 대회 신청내역 상세페이지
	 *
	 * 
	 * */
	//마이페이지 - 대회신청내역 상세페이지
	@RequestMapping("/membership/myKokContestAppView.do")
	public String myKokContestAppView(@ModelAttribute("vo") EventManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) throws Exception {
		
		FileManageVo fvo = new FileManageVo();
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "forward:/membership/login.do";
		}else{
			model.addAttribute("memberinfo",mv);
		}
		
		String mber_id = (String)session.getAttribute("mber_id");
		model.addAttribute("mber_id", mber_id);
		
		//대회 정보
		EventManageVo svo = EventManageService.lb_getEventContestView(vo);
		
		model.addAttribute("svo", svo);
		
		
		
		//첨부파일 리스트 가져오기
		if(svo != null && svo.getAtch_file_id() != null && !svo.getAtch_file_id().equals("")){
			fvo.setAtch_file_id(svo.getAtch_file_id().substring(0, 22));
			List<FileManageVo> contestFile	= fileManageService.getFileAttachList(fvo);
			model.addAttribute("contestFile", contestFile);
		}
				
		//내용
		vo.setReg_id(mber_id);
		EventManageVo contestView = EventManageService.lb_selectMyEventContestView(vo);
		//BoardManageVo contestView = BoardManageService.selectMyContestView(vo);	
		
		if(contestView != null){
			if(contestView.getBirth() != null){
				String birth_arr[] = contestView.getBirth().split("-");
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
			if(contestView.getTelno() != null){
				String tel_arr[] = contestView.getTelno().split("-");
				for(int i = 0; i < tel_arr.length; i ++){
					model.addAttribute("tel_arr"+i, tel_arr[i]);
				}
			}
			if(contestView.getEmail() != null){
				String email_arr[] = contestView.getEmail().split("@");
				for(int i = 0; i < email_arr.length; i ++){
					model.addAttribute("email_arr"+i, email_arr[i]);
				}
			}
		}
		
		model.addAttribute("contestView", contestView);
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "8");
		
		return "/client/member/myKokContestAppView";
	}
	
	
	//마이페이지 - 대회신청정보 수정
	@RequestMapping("/membership/myKokContestAppUpdate.do")
	public String myKokContestAppUpdate(@ModelAttribute("vo") EventManageVo vo
						,Model model
						,HttpServletRequest request
						,HttpServletResponse response
						,HttpSession session) throws Exception {
					
		
		int result = 0;
		result = EventManageService.cpageUpdateMyEventContestAppInfo(vo);
		
		//result
		if(result > 0){
			model.addAttribute("msg","updtsuccess");
			request.setAttribute("result_code", "200");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}else{
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "8");
			model.addAttribute("msg","fail");
			request.setAttribute("result_code", "800");
			return "/client/member/myKokContestAppView";			
		}
	}
	
	
	
	
	
	//마이페이지 - 왕중왕전 대회 신청 취소
	@RequestMapping("/membership/myKokContestAppCancel.do")
	public String myKokContestAppCancel(@ModelAttribute("vo") EventManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) throws Exception {
		
		int app_seq = vo.getApp_seq();
		String status = vo.getStatus();

		EventManageVo contestVo = EventManageService.lb_getEventContestView(vo);
		
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		EventManageVo nowAppVo  = null;
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			request.setAttribute("result_code", "700");
			return "redirect:/membership/login.do";
		}else{
			String mber_id = (String)session.getAttribute("mber_id");
			vo.setReg_id(mber_id);
			nowAppVo = EventManageService.lb_selectMyEventContestView(vo);
			//nowAppVo = BoardManageService.selectMyContestView(vo);
			if(nowAppVo == null){
				model.addAttribute("msg", "잘못된 접근입니다.");
				request.setAttribute("result_code", "701");
				return "redirect:/membership/login.do";
			}else if(nowAppVo.getStatus().equals("0003")){
				model.addAttribute("msg","alreadyCancel");
				request.setAttribute("result_code", "702");
				return "redirect:/membership/myContestAppList.do";
			}
		}
		
		
		
		String finish_dt = contestVo.getRefund_finish_date().replaceAll("-", "") + contestVo.getRefund_finish_h() + contestVo.getRefund_finish_m();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Date finish_time = dateFormat.parse(finish_dt);
		Date now = new Date();
		if(!(now.getTime() <= finish_time.getTime()) || contestVo.getCt_process().equals("F")){	//취소불가능
			model.addAttribute("msg","timeover");
			request.setAttribute("result_code", "703");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}
		
		int result = 0;
		
		if(status.equals("0001") || status.equals("0002") || status.equals("0005")) { //[입금대기 or 입금완료 or 대기]일 때 -> status = 0003으로
			
//			result = BoardManageService.cancelMyContest(vo);
			result = EventManageService.cancelMyEventContest(vo);
			if(result > 0){
				SmsManageVo smv = new SmsManageVo();
				smv.setSubject("[진승무역]");
				smv.setPhone(vo.getTelno());
				StringBuffer sb = new StringBuffer();
				
				// ETC INFO 
				smv.setEtc1(Integer.toString(nowAppVo.getCt_seq()));		//대회 고유번호
				smv.setEtc2(nowAppVo.getPart());							//신청 조
				smv.setEtc3(Integer.toString(nowAppVo.getApp_seq()));		//신청 고유번호
				smv.setEtc5("kok_auto"); 									//전체 발송 플래그
				smv.setEtc6(nowAppVo.getReg_id());							//신청자 ID
				smv.setEtc7(nowAppVo.getJoin_name());						//신청자 명
				smv.setEtc8(nowAppVo.getBirth());							//신청자 생년월일
				smv.setEtc9("0003");										//신청취소
				
				if("Y".equals(vo.getPay_flag())){
					sb.append(vo.getJoin_name()+"님\n");
					if("Y".equals(contestVo.getCt_result())){
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조"+" / 취소완료\n");
					}else{
						sb.append(contestVo.getCt_sbj() +" / 취소완료\n");
					}
					
					sb.append("입금사항 확인 후 기입하신 환불계좌로\n");
					sb.append(contestVo.getCt_price()+" 환불 해드리겠습니다.");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}else{
					sb.append(vo.getJoin_name()+"님\n");
					if("Y".equals(contestVo.getCt_result())){
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조"+" / 취소완료\n");
					}else{
						sb.append(contestVo.getCt_sbj() +" / 취소완료\n");
					}
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}
			}
			
		}else if(status.equals("0004")) { //[선정]일 때 -> status = 0003 + 대기명단에서 한명 채우기
			
			//취소
			//result = BoardManageService.cancelMyContest(vo);
			result = EventManageService.cancelMyEventContest(vo);
			if(result > 0){
				SmsManageVo smv = new SmsManageVo();
				smv.setSubject("[진승무역]");
				smv.setPhone(vo.getTelno());
				
				
				// ETC INFO 
				smv.setEtc1(Integer.toString(nowAppVo.getCt_seq()));		//대회 고유번호
				smv.setEtc2(nowAppVo.getPart());							//신청 조
				smv.setEtc3(Integer.toString(nowAppVo.getApp_seq()));		//신청 고유번호
				smv.setEtc5("kok_auto");									//전체 발송 플래그
				smv.setEtc6(nowAppVo.getReg_id());							//신청자 ID
				smv.setEtc7(nowAppVo.getJoin_name());						//신청자 명
				smv.setEtc8(nowAppVo.getBirth());							//신청자 생년월일
				smv.setEtc9("0003");										//신청취소
				
				
				StringBuffer sb = new StringBuffer();
				if("Y".equals(vo.getPay_flag())){
					sb.append(vo.getJoin_name()+"님\n");
					
					if("Y".equals(contestVo.getCt_result())){
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조"+" / 취소완료\n");
					}else{
						sb.append(contestVo.getCt_sbj() +" / 취소완료\n");
					}
					//sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조 / 취소완료\n");
					sb.append("입금사항 확인 후 기입하신 환불계좌로\n");
					sb.append(contestVo.getCt_price()+" 환불 해드리겠습니다.");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}else{
					sb.append(vo.getJoin_name()+"님\n");
					if("Y".equals(contestVo.getCt_result())){
						sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조"+" / 취소완료\n");
					}else{
						sb.append(contestVo.getCt_sbj() +" / 취소완료\n");
					}
					//sb.append(contestVo.getCt_sbj() +" / " + vo.getPart()+"조 / 취소완료");
					smv.setMsg(sb.toString());
					SmsManageService.insertMmsList(smv);
				}
			}
		}
		
		//result
		if(result > 0){
			model.addAttribute("msg","success");
			request.setAttribute("result_code", "200");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}else{
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "8");
			model.addAttribute("msg","fail");
			request.setAttribute("result_code", "704");
			return "/client/member/myKokContestAppView";			
		}
	}
	
	
	
	
	
	
	
	

	//마이페이지 - 이벤트 대회 신청 취소
	@RequestMapping("/membership/myEventContestAppCancel_shop.do")
	public String myEventContestAppCancel_shop(@ModelAttribute("vo") EventManageVo vo
								,Model model
								,HttpServletRequest request
								,HttpServletResponse response
								,HttpSession session) throws Exception {
		
		int app_seq = vo.getApp_seq();
		String status = vo.getStatus();

		EventManageVo contestVo = EventManageService.lb_getEventContestView(vo);
		
		
		ShopMemberManageVo mv = (ShopMemberManageVo) session.getAttribute("mberInfo");
		EventManageVo nowAppVo  = null;
		if (null == mv){			
			model.addAttribute("msg", "잘못된 접근입니다.");
			request.setAttribute("result_code", "700");
			return "redirect:/membership/login.do";
		}else{
			String mber_id = (String)session.getAttribute("mber_id");
			vo.setReg_id(mber_id);
			nowAppVo = EventManageService.lb_selectMyEventContestView(vo);
			//nowAppVo = BoardManageService.selectMyContestView(vo);
			if(nowAppVo == null){
				model.addAttribute("msg", "잘못된 접근입니다.");
				request.setAttribute("result_code", "701");
				return "redirect:/membership/login.do";
			}else if(nowAppVo.getStatus().equals("0003")){
				model.addAttribute("msg","alreadyCancel");
				request.setAttribute("result_code", "702");
				return "redirect:/membership/myContestAppList.do";
			}
		}
		
		
		
		String finish_dt = contestVo.getRefund_finish_date().replaceAll("-", "") + contestVo.getRefund_finish_h() + contestVo.getRefund_finish_m();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Date finish_time = dateFormat.parse(finish_dt);
		Date now = new Date();
		if(!(now.getTime() <= finish_time.getTime()) || contestVo.getCt_process().equals("F")){	//취소불가능
			model.addAttribute("msg","timeover");
			request.setAttribute("result_code", "703");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}
		
		int result = 0;
		
		result = EventManageService.cancelMyEventContest(vo);
		
		//result
		if(result > 0){
			model.addAttribute("msg","success");
			request.setAttribute("result_code", "200");
			return "redirect:/membership/myContestAppList.do";
			//return "/client/member/myContestAppList";			
		}else{
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "8");
			model.addAttribute("msg","fail");
			request.setAttribute("result_code", "704");
			return "/client/member/myEventContestAppView";			
		}
	}
	
	
	
	
}
