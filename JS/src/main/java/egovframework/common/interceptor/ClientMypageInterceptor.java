package egovframework.common.interceptor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.apage.system.service.apageSystemManageVo;
import egovframework.rte.fdl.property.EgovPropertyService;

public class ClientMypageInterceptor extends HandlerInterceptorAdapter {

	private final Logger log = Logger.getLogger(this.getClass());

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
//		System.out.println("----------------------------------");
//		System.out.println("request.getRequestURI() ===> " + request.getRequestURI());
//		System.out.println("request.getRequestURL() ===> " + request.getRequestURL());
		HttpSession session = request.getSession();
		String browser = getBrowser(request);
		String result_log = "";
		
		//System.out.println("post HANDLE ---------------------------------");
		
		String result_code = (String)request.getAttribute("result_code");
		
		//현재시간
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String str = dayTime.format(new Date(time));

		//String clientIp = request.getRemoteAddr();			
		String clientIp = getClientIpAddr(request);
		
		String param = returnParamInfo(request);
		result_log = "["+result_code+"]["+clientIp+"]["+request.getRequestURI()+"]["+session.getAttribute("mber_id")+"]["+browser+"]["+str+"]["+param+"]";
		
		String log_path = propertyService.getString("Globals.contestAccessLogPath");
		dayTime = new SimpleDateFormat("yyyyMMdd");
		String log_dirDate = dayTime.format(new Date(time));
		
		String file_path = log_path+"/contestAccess_"+log_dirDate+".log";
		File log_file = new File(file_path);
		if(log_file.exists() && log_file.isFile() && log_file.canWrite()){
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(log_file,true)); //이어쓰기
			bufferedWriter.newLine(); 			//줄바꿈
			bufferedWriter.write(result_log);
			bufferedWriter.close();
		}else{
		    // 파일 객체 생성
			FileWriter fw = new FileWriter(log_file, true); //파일 생성
			fw.write(indexText());
            fw.write(result_log);
            fw.flush();
            fw.close();             // 객체 닫기
		}
		
		
//		System.out.println("result_log === > " + result_log);
//		System.out.println("----------------------------------");
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		return super.preHandle(request, response, handler);
	}
	
	
	
	//브러우저 구분
	private String getBrowser(HttpServletRequest request){
		String header = request.getHeader("User-Agent");
		if(header.indexOf("MSIE") > -1){
			return "MSIE";
		}else if(header.indexOf("Chrome") > -1){
			return "Chrome";
		}else if(header.indexOf("Opera") > -1){
			return "Opera";
		}else if(header.indexOf("Firefox") > -1){
			return "Firefox";
		}else if(header.indexOf("rv:") > -1){
			return "MSIE";
		}
		return "MSIE";
	}
	
	
	//Result Code Guide Text
	private String indexText(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("=============================== Result Code Guide ==============================="+System.getProperty("line.separator"));
		sb.append("Page : Login"+System.getProperty("line.separator"));
		sb.append("URL : /membership/loginJson.do"+System.getProperty("line.separator"));
		sb.append("action : login success, code : 200"+System.getProperty("line.separator"));
		sb.append("action : login fail, code : 400"+System.getProperty("line.separator"));
		sb.append("================================================================================="+System.getProperty("line.separator"));
		sb.append("Page : Contest Write"+System.getProperty("line.separator"));
		sb.append("URL : /contest/contestAppWrite.do"+System.getProperty("line.separator"));
		sb.append("action : application possible , code : 200"+System.getProperty("line.separator"));
		sb.append("action : application closed , code : 500"+System.getProperty("line.separator"));
		sb.append("action : contest param null , code : 501"+System.getProperty("line.separator"));
		sb.append("action : session null , code : 502"+System.getProperty("line.separator"));
		sb.append("action : already apply in the group , code : 503"+System.getProperty("line.separator"));
		sb.append("================================================================================="+System.getProperty("line.separator"));
		sb.append("Page : Contest application"+System.getProperty("line.separator"));
		sb.append("URL : /contest/contestAppRegAjax.do"+System.getProperty("line.separator"));
		sb.append("action : application success , code : 200"+System.getProperty("line.separator"));
		sb.append("action : application error , code : 600"+System.getProperty("line.separator"));
		sb.append("action : exceeding capacity , code : 601"+System.getProperty("line.separator"));
		sb.append("action : application  overlap , code : 602"+System.getProperty("line.separator"));
		sb.append("action : not available time , code : 603"+System.getProperty("line.separator"));
		sb.append("action : apply is closed , code : 604"+System.getProperty("line.separator"));
		sb.append("action : session null , code : 605"+System.getProperty("line.separator"));
		sb.append("action : unknown error , code : 606"+System.getProperty("line.separator"));
		sb.append("action : direct access error , code : 607"+System.getProperty("line.separator"));
		sb.append("================================================================================="+System.getProperty("line.separator"));
		sb.append("Page : MyPage Contest application Cancel"+System.getProperty("line.separator"));
		sb.append("URL : /membership/myContestAppCancel.do"+System.getProperty("line.separator"));
		sb.append("action : application cancel success , code : 200"+System.getProperty("line.separator"));
		sb.append("action : session null , code : 700"+System.getProperty("line.separator"));
		sb.append("action : application param null , code : 701"+System.getProperty("line.separator"));
		sb.append("action : already cancel , code : 702"+System.getProperty("line.separator"));
		sb.append("action : not available time , code : 703"+System.getProperty("line.separator"));
		sb.append("action : application cancel fail , code : 703"+System.getProperty("line.separator"));
		sb.append("================================================================================="+System.getProperty("line.separator"));
		sb.append("Page : MyPage Contest application Update"+System.getProperty("line.separator"));
		sb.append("URL : /membership/myContestAppUpdate.do"+System.getProperty("line.separator"));
		sb.append("action : application Update success , code : 200"+System.getProperty("line.separator"));
		sb.append("action : application Update fail , code : 800"+System.getProperty("line.separator"));
		sb.append("================================================================================="+System.getProperty("line.separator"));

		return sb.toString();
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
	
	
	
	//파라미터 Return;
	public static String returnParamInfo(HttpServletRequest request) {
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
		return param;
	}
}


