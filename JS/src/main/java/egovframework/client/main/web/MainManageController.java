package egovframework.client.main.web;

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
import org.springframework.web.servlet.ModelAndView;

import egovframework.apage.system.service.apageSystemManageService;
import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.client.board.service.BoardManageService;
import egovframework.client.board.service.BoardManageVo;
import egovframework.client.shop.service.ShopManageService;
import egovframework.client.shop.service.ShopManageVo;
import egovframework.common.core.SendMiPlatform;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.utils.CommonUtil;
import egovframework.common.utils.PageVo;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class MainManageController {
	
	@Resource(name = "apageSystemManageService")
	private apageSystemManageService apageSystemManageService;
	
	@Resource(name = "ShopManageService")
	private ShopManageService ShopManageService;
	
	@Resource(name = "BoardManageService")
	private BoardManageService BoardManageService;
	
	@Resource(name = "FileManageService")
	private FileManageService fileManageService;
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name = "CommonUtil")
	private CommonUtil CommonUtil;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	//HEADER영역
	@RequestMapping(value = "/client/header.do")
	public String mainHeader() throws Exception {

		return "/client/inc/header";
	}
	
	//서브메뉴영역
	@RequestMapping(value = "/client/snb.do")
	public String mainSnb() throws Exception {

		return "/client/inc/snb";
	}
	
	//FOOTER영역
	@RequestMapping(value = "/client/footer.do")
	public String mainFooterView() throws Exception {
		
		return "/client/inc/footer";
	}
	
	//Main페이지
	@RequestMapping(value = "/main.do")
	public String main(@ModelAttribute("vo") BoardManageVo vo,
					   @ModelAttribute("svo") apageSystemManageVo svo
						 , ModelMap model
						 , HttpServletRequest request
						 , HttpServletResponse response) throws Exception {
		/*
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		*/
		//팝업
		List<apageSystemManageVo> popupList = apageSystemManageService.lb_selectClientPopupList(svo);
		
		//배너
		svo.setCurrRow(1);
		svo.setEndRow(100);
		svo.setScType("Y");
		List<apageSystemManageVo> bannerList = apageSystemManageService.lb_selectadminBannerList(svo);
		
		/*
		//볼링볼 및 용품(new -> ebig = 1015 기준)
		List<ShopManageVo> newList = ShopManageService.selectShopListNew();
		//볼링볼 및 용품(new -> ebig = 1015 기준) 옵션
		List<ShopManageVo> newListOptions = ShopManageService.selectShopListNewOptions();
		for(int i = 0;i < newListOptions.size();i++) {
			String items = newListOptions.get(i).getItems();
			if(items != null) {
				String str = items.substring(0, items.length()-1);
				newListOptions.get(i).setItems(str);
			}
		}
		*/
		/*
		//볼링볼 및 용품(hot -> ebig = 1016 기준)
		List<ShopManageVo> hotList = ShopManageService.selectShopListHot();
		//볼링볼 및 용품(hot -> ebig = 1016 기준) 옵션
		List<ShopManageVo> hotListOptions = ShopManageService.selectShopListHotOptions();
		for(int i = 0;i < hotListOptions.size();i++) {
			String items = hotListOptions.get(i).getItems();
			if(items != null) {
				String str = items.substring(0, items.length()-1);
				hotListOptions.get(i).setItems(str);
			}
		}
		*/
		
		//게시판 구분값
		//vo.setBbs_id("contest");
		vo.setBbs_id("contestRst");
		List<BoardManageVo> contestlist1 = BoardManageService.lb_selectMain(vo);
		//첨부파일(대표이미지) 가져오기
		FileManageVo fvo			= new FileManageVo();
		
		String atch_file_id = contestlist1.get(0).getAtch_file_id();
		if(atch_file_id != null && atch_file_id != ""){
			fvo.setAtch_file_id(atch_file_id.substring(0, 22));
			fvo.setFile_gu("I");
			List<FileManageVo> imgFile	= fileManageService.lb_getFileAttachList(fvo);
			
			if(imgFile.size() != 0) {
				contestlist1.get(0).setAtch_file_id(imgFile.get(0).getAtch_file_id());
			}else {
				contestlist1.get(0).setAtch_file_id("");
			}
		}
		vo.setEndRow(4);
		List<BoardManageVo> contestlist2 = BoardManageService.lb_selectMainList(vo);
		
		//게시판 구분값
		vo.setBbs_id("notice");		
		List<BoardManageVo> noticelist1 = BoardManageService.lb_selectMain(vo);		
		vo.setEndRow(5);
		List<BoardManageVo> noticelist2 = BoardManageService.lb_selectMainList(vo);
		
		model.addAttribute("popupList", popupList);
		model.addAttribute("bannerList", bannerList);
		//model.addAttribute("newList", newList);
		//model.addAttribute("newListOptions", newListOptions);
		//model.addAttribute("hotList", hotList);
		model.addAttribute("contestList1", contestlist1);		
		model.addAttribute("contestList2", contestlist2);
		model.addAttribute("noticeList1", noticelist1);
		model.addAttribute("noticeList2", noticelist2);
		
		
		
		
		List<ShopManageVo> list = ShopManageService.selectShopListNew();
		List<ShopManageVo> optionlist =  ShopManageService.selectShopListNewOptions();
		model.addAttribute("productList", list);
		model.addAttribute("optionlist", optionlist);
		
		return "/client/index";
	}

	//Main페이지 - 볼링볼 및 용품 AJAX
	@RequestMapping(value = "/productList_search.do")
	public @ResponseBody ModelAndView productList_search(@ModelAttribute("vo") BoardManageVo vo
						 , ModelMap model
						 , HttpServletRequest request
						 , HttpServletResponse response) throws Exception {
		

		Map<String,Object> data 	= new HashMap<String,Object>();

		try {
			String gubun = request.getParameter("gubun");
			List<ShopManageVo> list = null;
			List<ShopManageVo> optionlist = null;
			if(gubun.equals("new")) {
				
				list = ShopManageService.selectShopListNew();
				optionlist = ShopManageService.selectShopListNewOptions();
				for(int i = 0;i < optionlist.size();i++) {
					String items = optionlist.get(i).getItems();
					if(items != null) {
						String str = items.substring(0, items.length()-1);
						optionlist.get(i).setItems(str);
					}
				}
				
			}else {
				
				list = ShopManageService.selectShopListHot();
				optionlist = ShopManageService.selectShopListHotOptions();
				for(int i = 0;i < optionlist.size();i++) {
					String items = optionlist.get(i).getItems();
					if(items != null) {
						String str = items.substring(0, items.length()-1);
						optionlist.get(i).setItems(str);
					}
				}
			}
			model.addAttribute("productList", list);
			model.addAttribute("optionlist", optionlist);

			data.put("resultCode", "Y");
			
			data.put("productList", list);
			data.put("optionlist", optionlist);
			
			return SendMiPlatform.SendString(response, data);
		} catch (Exception e) {
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
	
	}
	
	//팝업 페이지
	@RequestMapping(value="/main/popup.do")
	public String getWrite(@ModelAttribute("aForm") apageSystemManageVo svo
								,HttpServletRequest request
								,HttpServletResponse response
								,ModelMap model) throws Exception {

		
		apageSystemManageVo popupVo = apageSystemManageService.getAdminPopupView(svo);
		model.addAttribute("mainPopup", popupVo);
		
		return "/client/popup/popupView";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
