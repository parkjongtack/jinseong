package egovframework.client.shop.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import egovframework.client.shop.service.ShopManageService;
import egovframework.client.shop.service.ShopManageVo;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.core.SendMiPlatform;
import egovframework.common.utils.CommonUtil;
import egovframework.common.utils.EgovFileMngUtil;
import egovframework.common.utils.PageVo;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class ShopManageController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
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
	
	//상품 리스트
	@RequestMapping(value="/shop/shopList.do")
	public String noticeList(@ModelAttribute("vo") ShopManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		
		/*
		if("".equals(vo.getMenuDepth1()) || vo.getMenuDepth1() == null){
			model.addAttribute("menuDepth1", "1");
		}else{
			model.addAttribute("menuDepth1", vo.getMenuDepth1());	
		}
		
		if("".equals(vo.getMenuDepth2()) || vo.getMenuDepth2() == null){
			model.addAttribute("menuDepth2", "1");
			vo.setBig("1001");
		}else{
			model.addAttribute("menuDepth2", vo.getMenuDepth2());
			String d2 = vo.getMenuDepth2();
			
			//대분류 셋팅
			if(d2.equals("1")){
				vo.setBig("1001");
				vo.setMid("");
			}else if(d2.equals("2")){
				vo.setBig("1002");
			}else if(d2.equals("3")){
				vo.setBig("1003");
			}else if(d2.equals("4")){
				vo.setBig("1004");
			}else if(d2.equals("5")){
				vo.setBig("1006");
			}else if(d2.equals("6")){
				vo.setBig("1005");
			}
		}
		
		if("".equals(vo.getMenuDepth3()) || vo.getMenuDepth3() == null){
			model.addAttribute("menuDepth3", "1");
		}else{
			model.addAttribute("menuDepth3", vo.getMenuDepth3());	
			String d3 = vo.getMenuDepth3();
		}
		
		*/
		
		
		if(vo.getMenuDepth1() == null || "".equals(vo.getMenuDepth1())){
			vo.setMenuDepth1("1");
		}
		if(vo.getMenuDepth2() == null || "".equals(vo.getMenuDepth2())){
			vo.setMenuDepth2("1");
		}
		if(vo.getMenuDepth3() == null || "".equals(vo.getMenuDepth3())){
			vo.setMenuDepth3("1");
		}
		if(vo.getBig() == null || "".equals(vo.getBig())){
			vo.setBig("1001");
		}
		if(vo.getMid() == null || "".equals(vo.getMid())){
			vo.setMid("1012");
		}
		
		
		model.addAttribute("menuDepth1", vo.getMenuDepth1());
		model.addAttribute("menuDepth2", vo.getMenuDepth2());
		model.addAttribute("menuDepth3", vo.getMenuDepth3());
		
		String d2 = vo.getMenuDepth2();
		
		String title = "";
		if(d2.equals("1")){
			title = "볼링볼";
		}else if(d2.equals("2")){
			title = "볼링백";
		}else if(d2.equals("3")){
			title = "볼링의류";
		}else if(d2.equals("4")){
			title = "볼링아대";
		}else if(d2.equals("5")){
			title = "볼링슈즈";
		}else if(d2.equals("6")){
			title = "볼링 악세사리";
		}
		
		model.addAttribute("title", title);
		
		PageVo pVo = new PageVo(CommonUtil.__PAGECNT, CommonUtil.__NAVCNT);
		
		pVo.setCntPerPage(8);
		
		int startPageNum			= CommonUtil.getStartNum(request, pVo);
		int endPageNum				= CommonUtil.getEndNum(pVo, startPageNum);		
		vo.setCurrRow(startPageNum);
		vo.setEndRow(endPageNum);
		
		List<ShopManageVo> list = ShopManageService.selectShopList(vo);		
		int totCnt = ShopManageService.selectShopListCnt(vo);
		
		
		model.addAttribute("searchVo", vo);
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("scType", vo.getScType());
		model.addAttribute("shopList", list);
		model.addAttribute("totalNum", totCnt);
		
		pVo.setTotalRowCnt(totCnt);
		model.addAttribute("pageNav", CommonUtil.getclientPageNav(request, pVo));
		model.addAttribute("currPage", pVo.getCurrPage());
		//페이징 정보
		Map<String, Integer> pageMap = CommonUtil.getPagingInfo(request, pVo);
		model.addAttribute("currPageInfo",pageMap.get("currPageInfo"));
		model.addAttribute("pageCntInfo",pageMap.get("pageCntInfo"));
		
		return "/client/shop/shopList";
	}
	
	
	//상품 상세
	@RequestMapping(value="/shop/shopView.do")
	public String shopView(@ModelAttribute("vo") ShopManageVo vo
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception {
		
		
		if(vo.getHash() == null || "".equals(vo.getHash())){
			return "redirect:/shop/shopList.do";
		}
		
		if(vo.getMenuDepth1() == null || "".equals(vo.getMenuDepth1())){
			vo.setMenuDepth1("1");
		}
		if(vo.getMenuDepth2() == null || "".equals(vo.getMenuDepth2())){
			vo.setMenuDepth2("1");
		}
		if(vo.getMenuDepth3() == null || "".equals(vo.getMenuDepth3())){
			vo.setMenuDepth3("1");
		}
		if(vo.getBig() == null || "".equals(vo.getBig())){
			vo.setBig("1001");
		}
		if(vo.getMid() == null || "".equals(vo.getMid())){
			vo.setMid("1012");
		}
		
		model.addAttribute("menuDepth1", vo.getMenuDepth1());
		model.addAttribute("menuDepth2", vo.getMenuDepth2());
		model.addAttribute("menuDepth3", vo.getMenuDepth3());
		
		
		String d2 = vo.getMenuDepth2();
		String title = "";
		if(d2.equals("1")){
			title = "볼링볼";
		}else if(d2.equals("2")){
			title = "볼링백";
		}else if(d2.equals("3")){
			title = "볼링의류";
		}else if(d2.equals("4")){
			title = "볼링아대";
		}else if(d2.equals("5")){
			title = "볼링슈즈";
		}else if(d2.equals("6")){
			title = "볼링 악세사리";
		}
		
		ShopManageVo svo = ShopManageService.selectShopView(vo);
		List<ShopManageVo> optionlist = ShopManageService.selectShopOptionsView(vo); //옵션
		for(int i = 0;i < optionlist.size();i++) {
			String items = optionlist.get(i).getItems();
			String str = items.substring(0, items.length()-1);
			optionlist.get(i).setItems(str);
		}
		
		if(svo != null){
			if(svo.getNo() > 0){
				vo.setPno(svo.getNo());
				List<ShopManageVo> imglist = ShopManageService.selectShopViewImg(vo);
				model.addAttribute("imglist", imglist);
			}
			
			if(svo.getContent2() != null && !svo.getContent2().equals("")){
				if(!svo.getContent2().contains("<img src=\"http://bowlingkoreamall.com")){
					svo.setContent2(svo.getContent2().replaceAll("<img src=\"", "<img src=\"https://bowlingkoreamall.com:446"));
				}
			}
		}
		
		
		model.addAttribute("searchVo", vo);
		model.addAttribute("title", title);
		model.addAttribute("shopView", svo);
		model.addAttribute("optionlist", optionlist);
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("currPage", vo.getCurrRow());
		
		return "/client/shop/shopView";
	}
}
