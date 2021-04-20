package egovframework.client.network.web;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.apage.system.service.apageSystemManageService;
import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.client.network.service.NetworkManageService;
import egovframework.client.network.service.NetworkManageVo;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.utils.CommonUtil;
import egovframework.common.utils.EgovFileMngUtil;
import egovframework.common.utils.PageVo;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class NetworkManageController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name = "CommonUtil")
	private CommonUtil CommonUtil;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	@Resource(name = "FileManageService")
	private FileManageService fileManageService;
	
	@Resource(name = "NetworkManageService")
	private NetworkManageService NetworkManageService;
	
	@Resource(name = "apageSystemManageService")
	private apageSystemManageService apageSystemManageService;

	
	//강사정보 리스트
	@RequestMapping(value="/network/teacherList.do")
	public String teacherList(@ModelAttribute("vo") NetworkManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		List<NetworkManageVo> list = NetworkManageService.selectTeacherList(vo);		
		int totCnt = NetworkManageService.selectTeacherListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("teacherList", list);
		model.addAttribute("totalNum", totCnt);
		
		//1deps code 
		model.addAttribute("deps_cd1", "3");
		//2deps code
		model.addAttribute("deps_cd2", "1");
		//3deps code
		model.addAttribute("deps_cd3", "2");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/client/network/teacherList";
	}
	
	
	// 강사정보 상세
	@RequestMapping(value="/network/teacherDetail.do")
	public String teacherDetail(@ModelAttribute("vo") NetworkManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		NetworkManageVo svo= NetworkManageService.getTeacherView(vo);
		
		//자격증
		List<NetworkManageVo> LicenseList = NetworkManageService.getcLicenseList(vo);
		model.addAttribute("LicenseList", LicenseList);
		//자격증
		List<NetworkManageVo> CareerList = NetworkManageService.getcCareerList(vo);
		model.addAttribute("CareerList", CareerList);
		//자격증
		List<NetworkManageVo> HistoryList = NetworkManageService.getchistoryList(vo);
		model.addAttribute("HistoryList", HistoryList);
				
		model.addAttribute("currPage", vo.getCurrRow());
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		//1deps code 
		model.addAttribute("deps_cd1", "3");
		//2deps code
		model.addAttribute("deps_cd2", "1");
		//3deps code
		model.addAttribute("deps_cd3", "2");
		
		model.addAttribute("boardView", svo);
		
		return "/client/network/teacherView";
	}
	
	//강사정보 입력페이지
	@RequestMapping(value = "/network/teacherWrite.do")
	public String teacherWrite(@ModelAttribute("vo") NetworkManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		//최종학력 리스트 
		apageSystemManageVo Cvo = new apageSystemManageVo();
		Cvo.setCl_code("U0009");
		List<apageSystemManageVo> ability_type = apageSystemManageService.getCodeNameList(Cvo);
		model.addAttribute("ability_type", ability_type);
		
		//1deps code 
		model.addAttribute("deps_cd1", "3");
		//2deps code
		model.addAttribute("deps_cd2", "1");
		//3deps code
		model.addAttribute("deps_cd3", "2");

		return "/client/network/teacherWrite";
	}
	
	//강사정보 등록
	@RequestMapping(value="/network/teacherReg.do")
	public String teacherReg(final MultipartHttpServletRequest multiRequest
							, @ModelAttribute("vo") NetworkManageVo vo
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
	  			result = fileUtil.parseFileInf3(request,files, "FILE_", "", 0, "teacher"); 	
	  			if(result.size() != 0){
		  			fileManageService.insertFileInfs2(result);	
		  			
		  			String file_id = result.get(0).atch_file_id;			
		  			vo.setAtch_file_id(file_id.substring(0, 22));
	  			}
			}	
			String nm = vo.getReg_nm();
			
			String email = vo.getEmail1() + "@" + vo.getEmail2();
			
			vo.setEmail(email);
			rtnVal= NetworkManageService.insertTeacher(vo);
			
			if(rtnVal > 0) {
				vo.setTeacher_seq(rtnVal);
				vo.setReg_nm(nm);
				//자격증 
				NetworkManageService.insertLicense(vo);
				//경력 
				NetworkManageService.insertCareer(vo);
				//이력 
				NetworkManageService.insertHistory(vo);
				
				model.addAttribute("msg", "등록되었습니다.");
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/client/network/teacherWrite";				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
    		model.addAttribute("msg", "잘못된 접근 또는 오류 입니다. 다시 입력해 주세요.");
    		return "/client/network/teacherWrite";
		}
		
		return "forward:/network/teacherList.do";
	}
	
	
	//학습동아리 리스트
	@RequestMapping(value="/network/clubList.do")
	public String clubList(@ModelAttribute("vo") NetworkManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		List<NetworkManageVo> list = NetworkManageService.selectClubList(vo);		
		int totCnt = NetworkManageService.selectClubListCnt(vo);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		model.addAttribute("clubList", list);
		model.addAttribute("totalNum", totCnt);
		
		//1deps code 
		model.addAttribute("deps_cd1", "3");
		//2deps code
		model.addAttribute("deps_cd2", "2");
		//3deps code
		model.addAttribute("deps_cd3", "2");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/client/network/clubList";
	}
	
	//학습동아리 입력페이지
	@RequestMapping(value = "/network/clubWrite.do")
	public String clubWrite(@ModelAttribute("vo") NetworkManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		
		
		//1deps code 
		model.addAttribute("deps_cd1", "3");
		//2deps code
		model.addAttribute("deps_cd2", "2");
		//3deps code
		model.addAttribute("deps_cd3", "2");

		return "/client/network/clubWrite";
	}
	
	
	//학습동아리 입력
	@RequestMapping(value = "/network/clubReg.do")
	public String clubReg(@ModelAttribute("vo") NetworkManageVo vo,
								HttpServletRequest request, ModelMap model,
								HttpServletResponse response, HttpSession session) throws Exception {		

		int rtnVal = 0;
		try {
			
			String email = vo.getEmail1() + "@" + vo.getEmail2();
			
			vo.setClub_email(email);
			rtnVal= NetworkManageService.insertClub(vo);
			
			if(rtnVal > 0) {
				model.addAttribute("msg", "등록되었습니다.");		
			} else {
				model.addAttribute("msg", "insert.fail");
				return "/client/network/clubWrite";				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
    		model.addAttribute("msg", "insert.fail");
    		return "/client/network/clubWrite";
		}
		return "forward:/network/clubList.do";
	}
	
	// 학습동아리 상세
	@RequestMapping(value="/network/clubDetail.do")
	public String clubDetail(@ModelAttribute("vo") NetworkManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		NetworkManageVo svo= NetworkManageService.getClubView(vo);
		
		model.addAttribute("currPage", vo.getCurrRow());
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		
		//1deps code 
		model.addAttribute("deps_cd1", "3");
		//2deps code
		model.addAttribute("deps_cd2", "2");
		//3deps code
		model.addAttribute("deps_cd3", "2");
		
		model.addAttribute("boardView", svo);
		
		return "/client/network/clubView";
	}
	
	
	//관내학습기관 리스트
	@RequestMapping(value="/network/insList.do")
	public String adminInsList(@ModelAttribute("vo") NetworkManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		if(pVo.getCurrPage() == 0){
			pVo.setCntPerPage(5);
		}
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		List<NetworkManageVo> list = NetworkManageService.selectInsList(vo);		
		int totCnt = NetworkManageService.selectInsListCnt(vo);
		
		//학습기관 유형 리스트 
		apageSystemManageVo Cvo = new apageSystemManageVo();
		Cvo.setCl_code("U0001");
		List<apageSystemManageVo> ins_type = apageSystemManageService.getCodeNameList(Cvo);
		model.addAttribute("ins_type", ins_type);
		
		//학습기관위치 유형 리스트 
		Cvo.setCl_code("U0002");
		List<apageSystemManageVo> addr_type = apageSystemManageService.getCodeNameList(Cvo);
		model.addAttribute("addr_type", addr_type);
		
		//검색입력값
		model.addAttribute("Srch_input", vo.getSrch_input());
		//검색 분류1
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("scType2", vo.getScType2());
		
		model.addAttribute("insList", list);
		model.addAttribute("totalNum", totCnt);
		
		//1deps code 
		model.addAttribute("deps_cd1", "3");
		//2deps code
		model.addAttribute("deps_cd2", "3");
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		return "/client/network/insList";
	}
	

}
