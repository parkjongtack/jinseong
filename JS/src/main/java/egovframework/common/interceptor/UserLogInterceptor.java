package egovframework.common.interceptor;

import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.apage.apmanage.service.ApageManageVo;
import egovframework.apage.system.service.apageSystemManageService;
import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.apage.member.service.apageMemberManageVo;

public class UserLogInterceptor extends HandlerInterceptorAdapter {

	private final Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "apageSystemManageService")
	private apageSystemManageService apageSystemManageService;
	
   	private Set<String> needlessURL;
   	
	public void setNeedlessURL(Set<String> needlessURL) {
		this.needlessURL = needlessURL;
	}
	
	private Set<String> restrictURL;
	
	public void setRestrictURL(Set<String> restrictURL) {
		this.restrictURL = restrictURL;
	}
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		boolean urlFlag = true;
		for (Iterator<String> it = this.needlessURL.iterator(); it.hasNext();) {
            String urlPattern = request.getContextPath() + (String) it.next();
            if(request.getRequestURI().contains(urlPattern)){
            	urlFlag = false;
            	break;
            }
        }

		if (urlFlag) {

			apageSystemManageVo mvo = new apageSystemManageVo();

			String param = "";
			Map mp = request.getParameterMap();

			Set keys = (Set) mp.keySet();
			Iterator iter = keys.iterator();

			while (iter.hasNext()) {
				String key = iter.next().toString();
				Object[] value = (Object[]) mp.get(key);
				if(key.equals("mber_pw") || key.equals("password")){
					continue;
				}
				param = param + key + ":";
				for (int i = 0; i < value.length; i++) {
					param = param + value[i] + ",";
				}
			}
			if (!param.equals("")) {
				param = param.substring(0, param.length() - 1);
			}

			int rtnVal = 0;

			request.setCharacterEncoding("UTF-8");
			mvo.setPage_url(request.getRequestURI());
			mvo.setFull_page_url(URLDecoder.decode(String.valueOf(request.getRequestURL()), "UTF-8"));
			//mvo.setIp(request.getRemoteAddr());
			mvo.setIp(getClientIpAddr(request));
			mvo.setMethod(request.getMethod());
			mvo.setAgent(request.getHeader("User-Agent"));
			mvo.setParam(param);

			HttpSession session = request.getSession();
			/*MembershipManageVo mv = (MembershipManageVo) session.getAttribute("clientInfo");*/
			String mber_id = (String)session.getAttribute("mber_id");
			
			apageMemberManageVo av = (apageMemberManageVo) session.getAttribute("adminInfo");
			if (null != mber_id && !"".equals(mber_id)) {
				mvo.setId(mber_id);
			} else if (null != av && !"".equals(av.getMber_id())) {
				mvo.setId(av.getMber_id());
			} else {
				mvo.setId("unknown");
			}

			rtnVal = apageSystemManageService.insertAgentLog(mvo);
		}
	}

	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		apageMemberManageVo vo = (apageMemberManageVo)session.getAttribute("adminInfo");
		if(vo == null && !request.getRequestURI().contains("adminLoginJson")){
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print("<script>alert('잘못된 접근입니다.');location.href='"+request.getContextPath()+"/apage.do';</script>");
			return false;
		}
		
		for (Iterator<String> it = this.restrictURL.iterator(); it.hasNext();) {
            String urlPattern = request.getContextPath() + (String) it.next();
            if(request.getRequestURI().contains(urlPattern)){
            	if(vo == null || !vo.getAuth_code().equals("super")){
            		if(vo != null && vo.getAuth_code() != null && (vo.getAuth_code().equals("board")||vo.getAuth_code().equals("admin"))){
            			if(vo.getAuth_code().equals("board")){
            				if(!request.getRequestURI().toLowerCase().contains("member") && !request.getRequestURI().toLowerCase().contains("board")){
            					response.setContentType("text/html; charset=utf-8");
            					response.getWriter().print("<script>alert('잘못된 접근입니다.');location.href='"+request.getContextPath()+"/apage.do';</script>");
            					return false;
            				}
            			}else if(vo.getAuth_code().equals("admin")){
            				if(!request.getRequestURI().toLowerCase().contains("member") && !request.getRequestURI().toLowerCase().contains("contest")){
            					response.setContentType("text/html; charset=utf-8");
            					response.getWriter().print("<script>alert('잘못된 접근입니다.');location.href='"+request.getContextPath()+"/apage.do';</script>");
            					return false;
            				}
            			}
                		
            		}else{
            			response.setContentType("text/html; charset=utf-8");
            			response.getWriter().print("<script>alert('잘못된 접근입니다.');location.href='"+request.getContextPath()+"/apage.do';</script>");
            			return false;
            		}
            	}
            }
        }
		
		return super.preHandle(request, response, handler);
	}
	
	
	
	
	/**
	 * 로드 밸런서로 인한 IP체크 불가할때
	 * */
	public static String getClientIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }


	
	
	
}
