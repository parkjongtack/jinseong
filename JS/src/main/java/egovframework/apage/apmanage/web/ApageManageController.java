package egovframework.apage.apmanage.web;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.apage.member.service.apageMemberManageService;
import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.common.utils.ShaEncryption;

@Controller
public class ApageManageController {

	
	@Autowired
	private ShaEncryption sha;

	
	/**
	 * 관리자 Header
	 */
	@RequestMapping(value = "/apage/inc/header.do")
	public String adminHeader(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) throws Exception {

		boolean isLogin = false;
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		if (null != mv && !"".equals(mv.getMber_id())){			
			isLogin=true;
			/*if (mv.getMember_type().equals("adm")){			
				member_type = "adm";
			}else{
				member_type = "cst";
			}*/
		}
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("memberinfo",mv);

		return "/apage/inc/header";
	}

	/**
	 * 관리자 Footer (사용자 권한별 메뉴)
	 */
	@RequestMapping(value = "/apage/inc/footer.do")
	public String adminFooter() throws Exception {
		return "/apage/inc/footer";
	}
	
	
	/**
	 * 관리자 Main
	 */
	@RequestMapping(value = "/apage/index.do")
	public String adminIndex(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		apageMemberManageVo mv = (apageMemberManageVo) session.getAttribute("adminInfo");
		
		if(mv.getAuth_code().equals("board")){
			return "redirect:/apage/board/adminNoticeList.do";
		}else{
			return "/apage/index";
		}
		
		
		

	}
	
}
