package egovframework.apage.contest.web;

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

import egovframework.apage.board.service.apageBoardManageService;
import egovframework.apage.board.service.apageBoardManageVo;
import egovframework.apage.contest.service.apageContestManageService;
import egovframework.common.core.ResultDataManager;
import egovframework.common.core.SendMiPlatform;

/**
 * 대회 신청 관련 Controller
 * 
 * 신청자 선정, 기타 신청자 관련된 내용만 있음 
 * 다른 내용(대회안내, 대회신청관리, 자동배치결과, 대회결과)은 apageBoardManageController 
 * @author eomhs
 *
 */
@Controller
public class apageContestManageController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "apageBoardManageService")
	private apageBoardManageService apageBoardManageService;

	@Resource(name = "apageContestManageService")
	private apageContestManageService apageContestManageService;
	
	
	//신청자 목록페이지
	@RequestMapping(value="/apage/contest/adminContestPlayerMng.do")
	public String adminContestPlayerMng(@ModelAttribute("vo") apageBoardManageVo vo
									 , HttpServletRequest request
									 , ModelMap model
									 , HttpServletResponse response
									 , HttpSession session) throws Exception {	
		
		//대회 신청 게시글 조회
		apageBoardManageVo svo= apageBoardManageService.getAdminContestView(vo);
		
		model.addAttribute("currPage", vo.getCurrRow());
		model.addAttribute("Srch_input", vo.getSrch_input());
		model.addAttribute("contestView", svo);
		
		return "/apage/contest/contestPlayerMngView";		
	}
	
	//신청자 랜덤 추첨
	@RequestMapping(value="/apage/contest/createRandomSelect.do")
	@ResponseBody
	public ModelAndView createRandomSelect(@ModelAttribute("vo") apageBoardManageVo vo
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session) throws Exception {
	
				
	try{

		ResultDataManager rm = new ResultDataManager();		
		vo.setCt_seq(Integer.parseInt(request.getParameter("ct_seq")));
		
		//대회 신청 게시글 조회
		apageBoardManageVo svo = apageBoardManageService.getAdminContestView(vo);
		int count = 0;
		int result_cnt = 0;
		if(svo != null){
			//updt_id
			int part1_app_cnt	= svo.getPart1_app_cnt();	//1조 선정자 수
			int part2_app_cnt	= svo.getPart2_app_cnt();	//2조 선정자 수
			int part3_app_cnt	= svo.getPart3_app_cnt();	//3조 선정자 수
			int part4_app_cnt	= svo.getPart4_app_cnt();	//4조 선정자 수
			
			int limit_app 		= svo.getLimit_part();		//조별 최대 선정자 수
			
			System.out.println("===============================");
			System.out.println("part1_app_cnt ===> " + part1_app_cnt);
			System.out.println("part2_app_cnt ===> " + part2_app_cnt);
			System.out.println("part3_app_cnt ===> " + part3_app_cnt);
			System.out.println("part4_app_cnt ===> " + part4_app_cnt);
			System.out.println("limit_app ===> " + limit_app);
			System.out.println("===============================");
			
			//선정자 랜덤 선택
			if(part1_app_cnt < limit_app){
				count++;
				vo.setPart("1");
				vo.setLimit_part(limit_app - part1_app_cnt);
				int result = apageContestManageService.createRandomSelect(vo);
				if(result > 0){
					result_cnt ++;
				}
			}
			if(part2_app_cnt < limit_app){
				count++;
				vo.setPart("2");
				vo.setLimit_part(limit_app - part2_app_cnt);
				int result = apageContestManageService.createRandomSelect(vo);
				if(result > 0){
					result_cnt ++;
				}
			}
			if(part3_app_cnt < limit_app){
				count++;
				vo.setPart("3");
				vo.setLimit_part(limit_app - part3_app_cnt);
				int result = apageContestManageService.createRandomSelect(vo);
				if(result > 0){
					result_cnt ++;
				}
			}
			if(part4_app_cnt < limit_app){
				count++;
				vo.setPart("4");
				vo.setLimit_part(limit_app - part4_app_cnt);
				int result = apageContestManageService.createRandomSelect(vo);
				if(result > 0){
					result_cnt ++;
				}
			}
			
			
			//대기자 랜덤 선택

			int part1_wait_cnt	= svo.getPart1_wait_cnt();	//1조 대기자 수
			int part2_wait_cnt	= svo.getPart2_wait_cnt();	//2조 대기자 수
			int part3_wait_cnt	= svo.getPart3_wait_cnt();	//3조 대기자 수
			int part4_wait_cnt	= svo.getPart4_wait_cnt();	//4조 대기자 수
			int limit_waiting		= svo.getLimit_waiting();	//조별 대기자 정원
			
			System.out.println("===============================");
			System.out.println("part1_wait_cnt ===> " + part1_wait_cnt);
			System.out.println("part2_wait_cnt ===> " + part2_wait_cnt);
			System.out.println("part3_wait_cnt ===> " + part3_wait_cnt);
			System.out.println("part4_wait_cnt ===> " + part4_wait_cnt);
			System.out.println("limit_waiting ===> " + limit_waiting);
			System.out.println("===============================");
			
			if(part1_wait_cnt < limit_waiting){
				count++;
				vo.setPart("1");
				vo.setLimit_waiting(limit_waiting - part1_wait_cnt);
				int result = apageContestManageService.createRandomWaitingSelect(vo);
				if(result > 0){
					result_cnt ++;
				}
			}
			
			if(part2_wait_cnt < limit_waiting){
				count++;
				vo.setPart("2");
				vo.setLimit_waiting(limit_waiting - part2_wait_cnt);
				int result = apageContestManageService.createRandomWaitingSelect(vo);
				if(result > 0){
					result_cnt ++;
				}
			}
			
			if(part3_wait_cnt < limit_waiting){
				count++;
				vo.setPart("3");
				vo.setLimit_waiting(limit_waiting - part3_wait_cnt);
				int result = apageContestManageService.createRandomWaitingSelect(vo);
				if(result > 0){
					result_cnt ++;
				}
			}
			
			if(part4_wait_cnt < limit_waiting){
				count++;
				vo.setPart("4");
				vo.setLimit_waiting(limit_waiting - part4_wait_cnt);
				int result = apageContestManageService.createRandomWaitingSelect(vo);
				if(result > 0){
					result_cnt ++;
				}
			}
			
			
			
		}
		Map<String,Object> data 	= new HashMap<String,Object>();

		if(count == result_cnt/2){
			data.put("ResultCode","Y");	//랜덤 추첨 성공
		}else{
			data.put("ResultCode","N");	//실패 
		}

		return SendMiPlatform.SendString(response, data);
		
		}catch(Exception e){
			
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}	
	}	
	
	
}
