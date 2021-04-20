package egovframework.common.interceptor;

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
import java.net.URLDecoder;
//import egovframework.main.service.MainManageService;
//import egovframework.main.service.MainVo;
//import egovframework.member.service.MemberManageVo;


public class adminAgentInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = Logger.getLogger(this.getClass());
    
	//@Resource(name = "mainManageService")
	//private MainManageService mainManageService;

    public void postHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler, ModelAndView modeAndView) throws Exception {
   /*
    	MainVo mvo = new MainVo();

    	String param = "";
		Map mp = request.getParameterMap();
		
		Set keys = (Set) mp.keySet();
		Iterator iter = keys.iterator();
		
		while (iter.hasNext()) {
			String key = iter.next().toString();
			Object[] value = (Object[]) mp.get(key);
			param = param + key + ":";
			for (int i = 0; i < value.length; i++) {
				param = param + value[i] + ",";
			}
		}
		if(!param.equals("")){
			param = param.substring(0, param.length() - 1);
		}
		
		int rtnVal = 0;

		request.setCharacterEncoding("UTF-8");
		mvo.setPage_url(URLDecoder.decode(String.valueOf(request.getRequestURL()), "UTF-8"));
		mvo.setFull_page_url(request.getRequestURI());
		mvo.setIp(request.getRemoteAddr());
		mvo.setMethod(request.getMethod());		
		mvo.setAgent(request.getHeader("User-Agent"));
		mvo.setParam(param);
		
		HttpSession session =  request.getSession();
		MemberManageVo mv = (MemberManageVo) session.getAttribute("adminInfo");		
		if (null != mv && !"".equals(mv.getUser_id())) {
			mvo.setId(mv.getUser_id());
		}else{
			mvo.setId("unknown");
		}
		rtnVal = mainManageService.insertAgentLog(mvo);
		*/
    }
}
