package egovframework.client.contents.web;

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

import egovframework.apage.system.service.apageSystemManageService;
import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.client.board.service.BoardManageService;
import egovframework.client.board.service.BoardManageVo;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.utils.CommonUtil;
import egovframework.common.utils.EgovFileMngUtil;
import egovframework.common.utils.PageVo;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class ContentsManageController {
	
	private Logger logger = Logger.getLogger(this.getClass());

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
	

	
	//인사말
	@RequestMapping(value="/contents/greeting.do")
	public String greeting(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		vo.setMenu_cd("C0001-01");
		apageSystemManageVo supportContent	= apageSystemManageService.getContents(vo);
		model.addAttribute("content", supportContent.getContents());
		model.addAttribute("menuDepth1", "4");
		model.addAttribute("menuDepth2", "1");
		return "/client/contents/contentView";
	}
	
	//연혁
	@RequestMapping(value="/contents/history.do")
	public String history(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		vo.setMenu_cd("C0001-02");
		apageSystemManageVo supportContent	= apageSystemManageService.getContents(vo);
		model.addAttribute("content", supportContent.getContents());
		model.addAttribute("menuDepth1", "4");
		model.addAttribute("menuDepth2", "2");
		return "/client/contents/contentView";
	}
	
	//찾아오시는길
	@RequestMapping(value="/contents/mapinfo.do")
	public String mapinfo(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		vo.setMenu_cd("C0001-04");
		apageSystemManageVo supportContent	= apageSystemManageService.getContents(vo);
		model.addAttribute("content", supportContent.getContents());
		model.addAttribute("menuDepth1", "4");
		model.addAttribute("menuDepth2", "5");
		return "/client/contents/contentView";
	}

	//개인정보취급방침
	@RequestMapping(value="/contents/privacy.do")
	public String privacy(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		vo.setMenu_cd("C0001-05");
		apageSystemManageVo supportContent	= apageSystemManageService.getContents(vo);
		model.addAttribute("content", supportContent.getContents());
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "5");
		return "/client/contents/contentView";
	}
	
	//이용약관
	@RequestMapping(value="/contents/rule.do")
	public String rule(@ModelAttribute("vo") apageSystemManageVo vo
									, ModelMap model
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		vo.setMenu_cd("C0001-06");
		apageSystemManageVo supportContent	= apageSystemManageService.getContents(vo);
		model.addAttribute("content", supportContent.getContents());
		model.addAttribute("menuDepth1", "6");
		model.addAttribute("menuDepth2", "6");
		return "/client/contents/contentView";
	}	
}
