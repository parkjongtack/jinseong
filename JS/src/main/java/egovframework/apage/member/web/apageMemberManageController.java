package egovframework.apage.member.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import egovframework.apage.member.service.apageMemberManageService;
import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.apage.system.service.apageSystemManageService;
import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.client.member.service.MemberManageService;
import egovframework.client.member.service.MemberManageVo;
import egovframework.client.member.service.ShopMemberManageService;
import egovframework.client.member.service.ShopMemberManageVo;
import egovframework.common.core.ResultDataManager;
import egovframework.common.core.SendMiPlatform;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.service.SmsManageService;
import egovframework.common.service.SmsManageVo;
import egovframework.common.utils.CommonUtil;
import egovframework.common.utils.EgovFileMngUtil;
import egovframework.common.utils.PageVo;
import egovframework.common.utils.ShaEncryption;
import egovframework.common.utils.StringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class apageMemberManageController {

private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "MemberManageService")
	private MemberManageService MemberManageService;
	
	@Resource(name = "shopMemberManageService")
	private ShopMemberManageService shopMemberManageService;
	
	@Resource(name = "apageMemberManageService")
	private apageMemberManageService apageMemberManageService;
	
	@Resource(name = "apageBoardManageService")
	private apageBoardManageService apageBoardManageService;
	
	@Resource(name = "SmsManageService")
	private SmsManageService smsManageService; //181211

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
	
	
	
	/**
	 * 
	 * ????????? ??????????????????
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/apage.do")
	public String adminLogin(@ModelAttribute("vo") apageMemberManageVo vo, HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) throws Exception {
		
		System.out.println("1234");
		System.out.println(sha.encryption("1234"));
		
		
		
		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		if (null != mv && !"".equals(mv.getMber_id())){			
			isLogin=true;
		}
		model.addAttribute("isLogin", isLogin);	
		return "/apage/apageLogin";
	}
	
	/**
	 * ????????? ????????? (?????? ?????? ??????????????? ????????? ?????????)
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/apage/adminLoginJson.do")
	public void adminLoginJson(@ModelAttribute("vo") apageMemberManageVo vo, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean loginFlag = false;
		vo.setMber_pw(sha.encryption(vo.getMber_pw()));
		
		int result = apageMemberManageService.adminLoginAction(vo);
		
		System.out.println("==============================");
		System.out.println("result : " + result);
		System.out.println("==============================");
		
		if (result > 0) {			
			loginFlag = true;
			
			MemberManageVo mvo = new MemberManageVo();
			mvo.setMber_id(vo.getMber_id());
			MemberManageService.updateLastLoginDt(mvo);
			apageMemberManageVo memberVo = apageMemberManageService.selectAdminMemberInfo(vo);				
			session.setAttribute("adminInfo", memberVo);
			session.setAttribute("user_id", memberVo.getMber_id());
			session.setAttribute("user_name", memberVo.getMber_name());				
			
			//?????? ???????????? 2?????? ??????
			session.setMaxInactiveInterval(120*60) ;
			
		}
		response.setContentType("text/html");		
		PrintWriter writer = response.getWriter();
		writer.print(loginFlag);
		writer.flush();
		writer.close();	
	}
	
	/* ????????? ????????????
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/apage/adminLogout.do")
	public String adminLogout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		
		session.invalidate();
		return "redirect:/apage.do";
		
	}
	
	//???????????? ????????? [?????? ????????? ???????????? ?????? ????????? ????????? table ??????. ???????????? ???????????? table ??????]
	@RequestMapping(value="/apage/member/adminMemberList.do")
	public String adminMemberList(@ModelAttribute("vo") ShopMemberManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		//vo.setMber_type("M");
		List<ShopMemberManageVo> list = shopMemberManageService.selectadminMemberList(vo);		
		int totCnt = shopMemberManageService.selectadminMemberListCnt(vo);
		

		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("mber_status", vo.getMber_status());
		model.addAttribute("srch_date", vo.getSrch_date());
		model.addAttribute("srch_date2", vo.getSrch_date2());
		model.addAttribute("memberList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/member/adminMemberList";
	}
	
	//???????????? - ?????? ??????????????????
	@RequestMapping(value="/apage/member/adminMemberContestList.do")
	public String adminMemberContestList(@ModelAttribute("vo") ShopMemberManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		//vo.setMber_type("M");
		List<ShopMemberManageVo> list = shopMemberManageService.selectadminMemberList(vo);		
		int totCnt = shopMemberManageService.selectadminMemberListCnt(vo);
		
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("mber_status", vo.getMber_status());
		model.addAttribute("srch_date", vo.getSrch_date());
		model.addAttribute("srch_date2", vo.getSrch_date2());
		model.addAttribute("memberList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/member/adminMemberContestList";
	}
	
	//????????? ?????????
	@RequestMapping(value="/apage/member/adminList.do")
	public String adminList(@ModelAttribute("vo") apageMemberManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		vo.setMber_type("A");
		List<apageMemberManageVo> list = apageMemberManageService.selectadminMemberList(vo);		
		int totCnt = apageMemberManageService.selectadminMemberListCnt(vo);
		

		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("mber_status", vo.getMber_status());
		model.addAttribute("srch_date", vo.getSrch_date());
		model.addAttribute("srch_date2", vo.getSrch_date2());
		model.addAttribute("memberList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/member/adminList";
	}
	
	//??????????????? ???????????????
	@RequestMapping(value = "/apage/member/adminWrite.do")
	public String adminWrite(@ModelAttribute("vo") apageMemberManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		

		return "/apage/member/adminWrite";
	}
	
	// ????????? ????????? ????????????
	@RequestMapping(value="/apage/member/idChk.do")
	public @ResponseBody ModelAndView getcodeNameChk(@ModelAttribute("vo") apageMemberManageVo vo
												,HttpServletRequest request
												,HttpServletResponse response) throws Exception {
		try {
			
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8"); // ?????? ????????? ?????? 
			Map<String,Object> data 	= new HashMap<String,Object>();
			int resultInt				= apageMemberManageService.getadminIdChk(vo);
			
			System.out.println(request.getParameter("mber_id"));
			System.out.println(resultInt);
			
			if(resultInt > 0){
				data.put("ResultCode","Y"); // ??????
			}else{
				data.put("ResultCode","N"); // ????????????
			}
				
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	//??????????????? ??????
	@RequestMapping(value = "/apage/member/adminReg.do")
	public String adminReg(@ModelAttribute("vo") apageMemberManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {	
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		vo.setReg_ip(StringUtil.null2Space(request.getRemoteAddr()));
		vo.setReg_id(mv.getMber_id());
		vo.setMber_pw(sha.encryption(vo.getMber_pw()));
		int result = apageMemberManageService.insertAdminMember(vo);
		
		if(result > 0){
			model.addAttribute("msg","success");
			return "/apage/member/adminWrite";			
		}else{
			model.addAttribute("menuDepth1", "6");
			model.addAttribute("menuDepth2", "2");
			model.addAttribute("msg","fail");
			return "/apage/member/adminWrite";			
		}

	}
	
	// ?????? ??????
	@RequestMapping(value="/apage/member/adminMemberDetail.do")
	public String adminMemberView(@ModelAttribute("vo") ShopMemberManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		ShopMemberManageVo svo= shopMemberManageService.selectMemberInfo(vo);
		
		model.addAttribute("currPage", vo.getCurrRow());
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());
		//?????? ??????
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("scType2", vo.getScType2());
		model.addAttribute("scType3", vo.getScType3());
		
		model.addAttribute("memberVo", svo);
		
		return "/apage/member/adminMemberView";
	}
	
	// ???????????? - ?????? ?????????????????? ??????
	@RequestMapping(value="/apage/member/adminMemberContestDetail.do")
	public String adminMemberContestView(@ModelAttribute("vo") apageBoardManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		List<apageBoardManageVo> list = apageBoardManageService.apageGetContestInfoOfEachMember(vo);
		
		model.addAttribute("contestList", list);
		model.addAttribute("join_name", request.getParameter("join_name"));
		
		model.addAttribute("currPage", vo.getCurrRow());
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());
		//?????? ??????
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("scType2", vo.getScType2());
		model.addAttribute("scType3", vo.getScType3());
		
		
		return "/apage/member/adminMemberContestView";
	}
	
	// ???????????? - ?????? ?????????????????? - ????????? ?????? ?????? AJAX
	@RequestMapping(value = "/apage/member/adminMemberContestInfo.do")
	public @ResponseBody ModelAndView adminMemberContestInfo(@ModelAttribute("vo") apageBoardManageVo vo
																, ModelMap model
																, HttpServletRequest request
																, HttpServletResponse response) throws Exception {
		
		try {
			
			Map<String,Object> data	= new HashMap<String,Object>();
			
			//????????? ?????? ??????
			apageBoardManageVo contestDetail = apageBoardManageService.apageGetContestInfoDetailOfEachMember(vo);
			
			if(contestDetail != null){
				data.put("contestDetail", contestDetail);
				data.put("resultCode","Y");
			}else{
				data.put("resultCode","N");
			}
			return SendMiPlatform.SendString(response, data);
			
		} catch (Exception e) {
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	// ????????? ??????
	@RequestMapping(value="/apage/member/adminDetail.do")
	public String adminDetail(@ModelAttribute("vo") apageMemberManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		apageMemberManageVo svo= apageMemberManageService.getAdminMemberView(vo);
		
		model.addAttribute("currPage", vo.getCurrRow());
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());
		//?????? ??????
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("scType2", vo.getScType2());
		model.addAttribute("scType3", vo.getScType3());
		
		model.addAttribute("memberVo", svo);
		
		return "/apage/member/adminView";
	}
	
	// ?????? ???????????????
	@RequestMapping(value="/apage/member/adminMemberModify.do")
	public String adminMemberModify(@ModelAttribute("vo") ShopMemberManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		ShopMemberManageVo svo= shopMemberManageService.selectMemberInfo(vo);
		
		model.addAttribute("currPage", vo.getCurrRow());
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());
		//?????? ??????
		model.addAttribute("scType", vo.getScType());
		
		model.addAttribute("memberVo", svo);
		
		
		return "/apage/member/adminMemberUpdt";
	}
	
	// ????????? ?????? ???????????????
	@RequestMapping(value="/apage/member/adminModify.do")
	public String adminModify(@ModelAttribute("vo") apageMemberManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		apageMemberManageVo svo= apageMemberManageService.getAdminMemberView(vo);
		
		model.addAttribute("currPage", vo.getCurrRow());
		
		//???????????????
		model.addAttribute("Srch_input", vo.getSrch_input());
		//?????? ??????
		model.addAttribute("scType", vo.getScType());

		model.addAttribute("memberVo", svo);
		
		
		return "/apage/member/adminUpdt";
	}
	
	// ?????? ?????? ??????
	@RequestMapping(value = "/apage/member/adminMemberUpdate.do")
	public String adminMemberUpdate(@ModelAttribute("vo") ShopMemberManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		

		if("M".equals(vo.getMber_gender())) {
			vo.setMber_gender("???");			
		} else if ("F".equals(vo.getMber_gender())) {
			vo.setMber_gender("???");
		} else {
			vo.setMber_gender("");
		}
		
		int rtnVal= shopMemberManageService.updateMember(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "update.success.");	
		} else {
			model.addAttribute("msg", "update.fail");
		}
			
		return "/apage/member/adminMemberUpdt";
	}
	
	//????????? ??????
	@RequestMapping(value = "/apage/member/adminUpdate.do")
	public String adminUpdate(@ModelAttribute("vo") apageMemberManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		

		int rtnVal = 0;
		
			
		if(!"".equals(vo.getMber_pw()) && vo.getMber_pw() != null){
			vo.setMber_pw(sha.encryption(vo.getMber_pw()));	
		}
		
		rtnVal= apageMemberManageService.updateAdminMember(vo);
		
		if(rtnVal > 0) {
			model.addAttribute("msg", "update.success.");	
		} else {
			model.addAttribute("msg", "update.fail");
		}
			
		return "/apage/member/adminUpdt";
	}	
	
	// ?????? ??????
	@RequestMapping(value="/apage/member/adminMemberExcel.do")
	public String adminMemberExcel(@ModelAttribute("vo") ShopMemberManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		
		List<ShopMemberManageVo> list = shopMemberManageService.selectadminMemberListExcel(vo);
		
		model.addAttribute("memberList", list);
		
		return "/apage/member/memberListExcel";
	}

	//SMS ?????????
	@RequestMapping(value="/apage/member/smsList.do")
	public String smsList(@ModelAttribute("vo") ShopMemberManageVo vo
						, @ModelAttribute("vo2") apageMemberManageVo vo2
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo2.setCurrRow(startPageNum);
		vo2.setEndRow(endPageNum);
		
		//?????? ?????????
		List<apageBoardManageVo> contestList = apageBoardManageService.selectAdminContestListNoLimit();
		model.addAttribute("contestList", contestList);
		
		//?????? ?????? ?????? ?????? ?????????
		List<ShopMemberManageVo> list = shopMemberManageService.selectSmsAgreeMemberList(vo);
		model.addAttribute("smsAgreeList", list);
		
		//?????? ?????? ?????????
		List<apageMemberManageVo> smsList = apageMemberManageService.selectSmsHistoryList(vo2);
		int totCnt = apageMemberManageService.selectSmsHistoryListCnt(vo2);
		
		List<SmsManageVo> smsHistory = new ArrayList<SmsManageVo>();
		if(smsList.size() != 0) {
			System.out.println("----------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>      "+smsList.get(0).getSend_date());
			
			SmsManageVo smsLogVo = new SmsManageVo();
			String date = smsList.get(0).getSend_date();
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
				smsLogVo.setEtc5("normal"); //???????????? ?????????
				smsHistory = SmsManageService.getMmsLogTableUnionList(smsLogVo);
				
				model.addAttribute("smsHistory", smsHistory);
			}
		}
		model.addAttribute("msgCnt", smsHistory.size());
		
		//???????????????
		model.addAttribute("Srch_input", vo2.getSrch_input());
		model.addAttribute("srch_date", vo2.getSrch_date());
		model.addAttribute("srch_date2", vo2.getSrch_date2());
		
		model.addAttribute("smsList", smsList);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/member/smsList";
	}
	
	//SMS ?????? ?????? ?????? ?????????(??????) AJAX
	@RequestMapping(value = "/apage/member/smsMberList.do")
	public @ResponseBody ModelAndView smsMberList(@ModelAttribute("vo") ShopMemberManageVo vo
																, ModelMap model
																, HttpServletRequest request
																, HttpServletResponse response) throws Exception {
		
		try {
			
			Map<String,Object> data	= new HashMap<String,Object>();
			
			//?????? ?????? ?????? ?????? ?????????
			List<ShopMemberManageVo> resultList = shopMemberManageService.selectSmsAgreeMemberList(vo);
			
			if(resultList.size() > 0){
				data.put("resultList", resultList);
				data.put("resultCode","Y");
			}else{
				data.put("resultCode","N");
			}
			return SendMiPlatform.SendString(response, data);
			
		} catch (Exception e) {
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
			
	//SMS ??????
	@RequestMapping(value="/apage/member/insertSmsList.do")
	public String insertSmsList(@ModelAttribute("vo") SmsManageVo vo
							  , @ModelAttribute("vo2") apageBoardManageVo vo2
							  , @ModelAttribute("vo3") apageMemberManageVo vo3
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		try{
			int result = 0; //?????? ?????? table
			
			String msg_type = vo.getMsg_type();
			String[] phone = null;
			String[] name = null;
			String[] id = null;
			
			//?????? ?????? ??????
			vo3.setMsg_content(vo.getMsg());
			vo3.setSend_id((String)session.getAttribute("user_id"));
			int result2 = apageMemberManageService.insertSmsHistoryForList(vo3); //sms_manage table
			
			//int msg_size = vo.getMsg().length();
			int msg_size = byteCheck(vo.getMsg());
			
			if(msg_type.equals("1")) { //???????????? ??? ??????
				
				System.out.println("ct_seq ---------------------------------------------------------------------> "+vo2.getCt_seq());
				
				String contest_name_group = apageBoardManageService.selectAllContestAppMemberName(vo2);
				String contest_id_group = apageBoardManageService.selectAllContestAppMemberID(vo2);
				String contest_phone_group = apageBoardManageService.selectAllContestAppMemberPhone(vo2);
				
				name = contest_name_group.split(",");
				id = contest_id_group.split(",");
				phone = contest_phone_group.split(",");
				
			}else if(msg_type.equals("2")) { //????????????
				
				String all_name_group = shopMemberManageService.selectAllSmsAgreeMemberName();
				String all_id_group = shopMemberManageService.selectAllSmsAgreeMemberID();
				String all_phone_group = shopMemberManageService.selectAllSmsAgreeMemberPhone();
				
				name = all_name_group.split(",");
				id = all_id_group.split(",");
				phone = all_phone_group.split(",");
				
			}else if(msg_type.equals("3")) { //????????????
				
				name = request.getParameterValues("tr_name");
				id = request.getParameterValues("tr_id");
				phone = vo.getTr_phone().split(",");
			}
			
			
			//?????? ??????
			List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
			List<SmsManageVo> mmsList = new ArrayList<SmsManageVo>();
			for(int i = 0;i < phone.length;i++) {
				
				SmsManageVo smv = new SmsManageVo();
				
				
				
				if(msg_size <= 90){	//SMS
					if(msg_type.equals("1")) { //???????????? ??? ??????
						smv.setTr_etc1(Integer.toString(vo2.getCt_seq()));				//?????? ????????????
						smv.setTr_etc10("endContestMember"); 							//???????????? ?????????
						smv.setTr_etc5("normal"); 										//???????????? ?????????
						
					}else if(msg_type.equals("2")) { //????????????
											
						smv.setTr_etc10("allMember"); 									//???????????? ?????????
						smv.setTr_etc5("normal"); 										//???????????? ?????????
					}else if(msg_type.equals("3")) { //????????????
						
						smv.setTr_etc10("selectMember"); 								//???????????? ?????????
						smv.setTr_etc5("normal"); 										//???????????? ?????????
					}
					
					//smv.setSubject("[????????????]");
					smv.setTr_phone(phone[i]);
					smv.setTr_msg(vo.getMsg());
					smv.setTr_etc3(Integer.toString(result2));							//sms_manage sequence
					smv.setTr_etc6(id[i]);												//?????????
					smv.setTr_etc7(name[i]);											//??????
					smsList.add(smv);
					
					
				}else{				//MMS
					
					if(msg_type.equals("1")) { //???????????? ??? ??????
						
						smv.setEtc1(Integer.toString(vo2.getCt_seq()));				//?????? ????????????
						smv.setEtc10("endContestMember"); 							//???????????? ?????????
						smv.setEtc5("normal"); 										//???????????? ?????????
						
					}else if(msg_type.equals("2")) { //????????????
											
						smv.setEtc10("allMember"); 									//???????????? ?????????
						smv.setEtc5("normal"); 										//???????????? ?????????
					}else if(msg_type.equals("3")) { //????????????
						
						smv.setEtc10("selectMember"); 								//???????????? ?????????
						smv.setEtc5("normal"); 										//???????????? ?????????
					}
					
					
					smv.setSubject("[????????????]");
					smv.setPhone(phone[i]);
					smv.setMsg(vo.getMsg());
					smv.setEtc3(Integer.toString(result2));							//sms_manage sequence
					smv.setEtc6(id[i]);												//?????????
					smv.setEtc7(name[i]);											//??????
					mmsList.add(smv);
				}
				
			}
			
			if(mmsList.size() > 0){
				result = smsManageService.insertMmsBatch(mmsList);
				//result = smsManageService.insertMmsList(vo);
			}
			
			if(smsList.size() > 0){
				result = smsManageService.insertSmsBatch(smsList);
				//result = smsManageService.insertMmsList(vo);
			}
		
			//??????
			if(result > 0 && result2 > 0) {
				model.addAttribute("msg", "insert.success");
			}else {
				model.addAttribute("msg", "insert.fail");
				return "forward:/apage/member/smsList.do";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "forward:/apage/member/smsList.do";				
		}
		return "redirect:/apage/member/smsList.do";
	}
	
	//SMS ?????? ????????? ??????
	@RequestMapping(value="/apage/member/smsDetail.do")
	public String smsDetail(@ModelAttribute("vo") ShopMemberManageVo vo
								     , @ModelAttribute("vo2") apageMemberManageVo vo2
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		
		String seq = request.getParameter("seq");
		String sms_send_date_ym = request.getParameter("sms_send_date_ym");
		
		SmsManageVo smv = new SmsManageVo();
		
		smv.setLog_table_name("mms_log_"+sms_send_date_ym);
		smv.setEtc3(seq);
		List<SmsManageVo> smsLogList = SmsManageService.selectSmsLogListAboutGeneralAndExcel(smv);
		
		model.addAttribute("smsLogList", smsLogList);
		
		//???????????????
		model.addAttribute("Srch_input", vo2.getSrch_input());
		model.addAttribute("srch_date", vo2.getSrch_date());
		model.addAttribute("srch_date2", vo2.getSrch_date2());
		model.addAttribute("currPage", vo2.getCurrRow());
		
		return "/apage/member/smsView";
	}
	
	//SMS ?????? ????????? ?????? ?????????
	@RequestMapping(value="/apage/member/smsExcelUploadList.do")
	public String smsExcelUploadList(@ModelAttribute("vo") ShopMemberManageVo vo
								   , @ModelAttribute("vo2") apageMemberManageVo vo2
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);
		vo2.setCurrRow(startPageNum);
		vo2.setEndRow(endPageNum);
		

		//?????? ????????? ?????? ?????? ?????????
		vo2.setMsg_type("4");
		List<apageMemberManageVo> smsList = apageMemberManageService.selectSmsHistoryList(vo2);
		int totCnt = apageMemberManageService.selectSmsHistoryListCnt(vo2);
		int msgCnt = 0;
		List<SmsManageVo> smsHistory = new ArrayList<SmsManageVo>();
		if(smsList.size() != 0) {
			System.out.println("----------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>      "+smsList.get(0).getSend_date());
			
			SmsManageVo smsLogVo = new SmsManageVo();
			
			smsLogVo.setCurrRow(startPageNum-1);
			smsLogVo.setEndRow((endPageNum-startPageNum)+1);
			
			
			String date = smsList.get(0).getSend_date();
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
				smsLogVo.setEtc5("excelMember"); //????????????????????? ?????????
				//smsHistory = SmsManageService.getMmsLogTableUnionList(smsLogVo);
				smsHistory = SmsManageService.getMmsLogTableUnionPagingList(smsLogVo);
				model.addAttribute("smsHistory", smsHistory);
				
				msgCnt = SmsManageService.getMmsLogTableUnionPagingListCnt(smsLogVo);
			}
		}
		
		model.addAttribute("msgCnt", msgCnt);
		model.addAttribute("totalNum", msgCnt);
//		model.addAttribute("msgCnt", smsHistory.size());
		
		//???????????????
		model.addAttribute("Srch_input", vo2.getSrch_input());
		model.addAttribute("srch_date", vo2.getSrch_date());
		model.addAttribute("srch_date2", vo2.getSrch_date2());
		model.addAttribute("currPage", vo2.getCurrRow());
		
		model.addAttribute("smsList", smsList);
		pVo.setTotalRowCnt(msgCnt);
		//model.addAttribute("totalNum", totCnt);
		
		//pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getadminPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		
		return "/apage/member/smsExcelUploadList";
	}
	
	//SMS ?????? ????????? ??????
	@RequestMapping(value="/apage/member/insertSmsExcelList.do")
	public String insertSmsExcelList(@ModelAttribute("vo") SmsManageVo vo
							  , @ModelAttribute("vo2") apageBoardManageVo vo2
							  , @ModelAttribute("vo3") apageMemberManageVo vo3
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
		
		try{
			int result = 0; //?????? ?????? table
			
			//String msg_type = vo.getMsg_type();
			//String[] name = request.getParameter("mber_name").split(","); 
			//String[] phone = request.getParameter("mber_tel").split(",");
			String[] name = request.getParameterValues("name_arr"); 
			String[] phone = request.getParameterValues("cell_arr");
			
			//?????? ?????? ??????
			vo3.setMsg_content(vo.getMsg());
			vo3.setSend_id((String)session.getAttribute("user_id"));
			int result2 = apageMemberManageService.insertSmsHistoryForList(vo3); //sms_manage table
			
			//?????? ??????
			List<SmsManageVo> smsList = new ArrayList<SmsManageVo>();
			for(int i = 0;i < phone.length;i++) {
				
				SmsManageVo smv = new SmsManageVo();
				
				smv.setSubject("[????????????]");
				smv.setPhone(phone[i]);
				smv.setMsg(vo.getMsg());
				smv.setEtc3(Integer.toString(result2));						//sms_manage sequence
				smv.setEtc5("excelMember");									//???????????? ?????????
				smv.setEtc7(name[i]);										//?????? ????????? ?????? ??? ?????? ??????
				smsList.add(smv);
			}
			if(smsList.size() > 0){
				result = smsManageService.insertMmsBatch(smsList);
				//result = smsManageService.insertMmsList(vo);
			}
			
			//??????
			if(result > 0 && result2 > 0) {
				model.addAttribute("msg", "insert.success");
			}else {
				model.addAttribute("msg", "insert.fail");
				return "forward:/apage/member/smsExcelUploadList.do";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "forward:/apage/member/smsExcelUploadList.do";				
		}
		return "redirect:/apage/member/smsExcelUploadList.do";
	}
	
	//SMS ?????? ????????? ?????? ????????? ??????
	@RequestMapping(value="/apage/member/smsExcelUploadDetail.do")
	public String smsExcelUploadDetail(@ModelAttribute("vo") ShopMemberManageVo vo
								     , @ModelAttribute("vo2") apageMemberManageVo vo2
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		
		String seq = request.getParameter("seq");
		String sms_send_date_ym = request.getParameter("sms_send_date_ym");
		
		SmsManageVo smv = new SmsManageVo();
		
		smv.setLog_table_name("mms_log_"+sms_send_date_ym);
		smv.setEtc3(seq);
		List<SmsManageVo> smsLogList = SmsManageService.selectSmsLogListAboutGeneralAndExcel(smv);
		
		model.addAttribute("smsLogList", smsLogList);
		
		//???????????????
		model.addAttribute("Srch_input", vo2.getSrch_input());
		model.addAttribute("srch_date", vo2.getSrch_date());
		model.addAttribute("srch_date2", vo2.getSrch_date2());
		model.addAttribute("currPage", vo2.getCurrRow());
		
		return "/apage/member/smsExcelUploadView";
	}
	
	//???????????? ??????
	@RequestMapping(value="/apage/member/smsExcelRead.do")
	@ResponseBody
	public ModelAndView smsExcelRead(final MultipartHttpServletRequest multiRequest
											, @ModelAttribute("vo") ShopMemberManageVo vo
											, @ModelAttribute("vo2") apageMemberManageVo vo2
											, ModelMap model
											, HttpServletRequest request
											, HttpServletResponse response
											, HttpSession session) throws Exception {
		
		try {

			// ?????? ?????? ??????(.xlsx)
			Map<String, MultipartFile> files = multiRequest.getFileMap();		
	  		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
	  		MultipartFile file = null;

	  		List<ShopMemberManageVo> resultList = new ArrayList<ShopMemberManageVo>();
	  		
	  		
	  		while(itr.hasNext()) {
	  		    Entry<String, MultipartFile> entry = itr.next();
	  		    file = entry.getValue();
	  		}
	  		if(file != null){
	  			File convFile = new File(file.getOriginalFilename()); 
	  			file.transferTo(convFile); 
	  		    
	  			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(convFile));
	  		    Cell cell = null;
	            
	            //????????? sheet ?????? ??????
	  		    List<String> columnName = new ArrayList<String>();
	  		    StringBuffer sb = new StringBuffer();
	  		    StringBuffer jsonSb = new StringBuffer(); //???????????? JSON??????..!
	  		    sb.append("{\"data\" : [");
	  		    
	  		    int cnt = 0;
	  		    for(Row row : wb.getSheetAt(0)) {
	  		    	
	  		    	if(row.getRowNum() < 2) { //3??? ?????? ??????
	                    continue;
	                }
	                
	                jsonSb = new StringBuffer();
	                if(row.getRowNum() != 2){
	                	jsonSb.append("{");
	                }
	                for(int i = 0; i < Integer.parseInt(vo.getScType()); i++){ //2??????_??????????????? ???????????? ????????????(??????????????? ?????????)
	                	if(row.getRowNum() == 2){
		            		columnName.add(row.getCell(i).toString()); //?????? ????????? ????????? ?????? ????????? ???????????? add
		            	}else{
		            		String json = "";
		            		String data = "";
		            				            		
		            		if(row.getCell(i) != null){
		            			row.getCell(i).setCellType(row.getCell(i).CELL_TYPE_STRING); //????????? ????????? ?????? ????????? String?????? ??????
		            			data = row.getCell(i).getStringCellValue().toString();
	  		    				if(data != null && !data.equals("")){ //&& data.indexOf("\"") > 0
	  		    					//JSON ????????? ??????
	  			            		if(i == Integer.parseInt(vo.getScType())-1){
	  		  		    				json= "\""+columnName.get(i)+"\":\""+data+"\"";
	  		  		    			}else{
	  		  		    				json= "\""+columnName.get(i)+"\":\""+data+"\",";
	  		  		    			}
	  		  		    			jsonSb.append(json);
	  		    				}
		            		}
		            	} //end //if(row.getRowNum() == 2){}else{}
	                }// end //for
	                
	                if(row.getRowNum() != 2){
	                	
	  		    		if(wb.getSheetAt(0).getLastRowNum()-2 == cnt){
	  		    			jsonSb.append("}");
	  		    		}else{
	  		    			jsonSb.append("},");
	  		    		}
	  		    		
	  		    	}
	  		    	sb.append(jsonSb.toString());
	  		    	cnt++;
	            } //end //for(Row row : wb.getSheetAt(0))
	  		    
	  		    sb.append("]}");

	            System.out.println(sb.toString());
	            JsonParser parser = new JsonParser();
	            Object obj = parser.parse(sb.toString());
	            JsonObject jsonObj = (JsonObject) obj;
	            JsonArray ja = (JsonArray)jsonObj.get("data");
	            for(int i = 0; i < ja.size(); i++){
	            	Gson gson = new Gson(); 
	            	resultList.add(gson.fromJson(ja.get(i), ShopMemberManageVo.class));
	            }
	  		} //end //if(file != null)
	  		
			
			Map<String,Object> data	= new HashMap<String,Object>();
			if(resultList.size() > 0 ){
				for(int i = 0; i < resultList.size(); i ++){
					//resultList.get(i).setHit_excel("0");
				}
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
	
	
	
	
	
	
	//????????? ?????? ???????????????
	@RequestMapping(value = "/apage/member/adminMemberWrite.do")
	public String adminMemberWrite(@ModelAttribute("vo") ShopMemberManageVo vo
			 , HttpServletRequest request
			 , ModelMap model
			 , HttpServletResponse response
			 , HttpSession session) throws Exception {

		
		
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("mber_status", vo.getMber_status());
		model.addAttribute("srch_date", vo.getSrch_date());
		model.addAttribute("srch_date2", vo.getSrch_date2());
		model.addAttribute("currPage", vo.getCurrRow());

		return "/apage/member/adminMemberWrite";
	}
	
	//????????? ?????? ????????????
	@RequestMapping(value="/apage/member/adminMemberIdChk.do")
	public @ResponseBody ModelAndView adminMemberIdChk(@ModelAttribute("vo") ShopMemberManageVo vo
												,HttpServletRequest request
												,HttpServletResponse response) throws Exception {
		try {
			
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8"); // ?????? ????????? ?????? 
			Map<String,Object> data 	= new HashMap<String,Object>();
			int resultInt				= shopMemberManageService.mberIdOverlapCheck(vo);
			
			if(resultInt > 0){
				data.put("ResultCode","Y"); // ??????
			}else{
				data.put("ResultCode","N"); // ????????????
			}
				
			return SendMiPlatform.SendString(response, data);
			
		}catch(Exception e){
			e.printStackTrace();
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	}
	
	
	
	
	
	//?????????????????? ??????????????? ????????????
	@RequestMapping(value = "/apage/member/adminMemberReg.do")
	public String adminMemberReg(@ModelAttribute("vo") ShopMemberManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {	
		
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		vo.setReg_ip(StringUtil.null2Space(request.getRemoteAddr()));
		vo.setReg_id(mv.getMber_id());
		// vo.setMber_pw(sha.encryption(vo.getMber_pw()));
		
		if("M".equals(vo.getMber_gender())) {
			vo.setMber_gender("???");
		} else if("F".equals(vo.getMber_gender())) {
			vo.setMber_gender("???");
		}
		vo.setJoin_ref("paper");
		
		int result = shopMemberManageService.insertMember(vo);
		if(result > 0){
			model.addAttribute("msg","success");
			return "redirect:/apage/member/adminMemberList.do";
				
		}else{ //?????? ???????????? ??????
			model.addAttribute("msg","fail");
			return "forward:/apage/member/adminMemberWrite.do";			
		}	
		

	}
	
	
	
	
	 /**
     * ???????????? ????????????. ???????????? ?????? false, ????????? ????????? true
     * 
     * @param txt ????????? text
     * @param standardByte ?????? ????????? ???
     * @return 
     */
    public int byteCheck(String txt) {
 
        // ????????? ?????? (?????? 1, ?????? 2, ?????? 1)
        int en = 0;
        int ko = 0;
        int etc = 0;
 
        char[] txtChar = txt.toCharArray();
        for (int j = 0; j < txtChar.length; j++) {
            if (txtChar[j] >= 'A' && txtChar[j] <= 'z') {
                en++;
            } else if (txtChar[j] >= '\uAC00' && txtChar[j] <= '\uD7A3') {
                ko++;
                ko++;
            } else {
                etc++;
            }
        }
 
        int txtByte = en + ko + etc;
        return txtByte;
    }


	
}
