package egovframework.common.filter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import egovframework.rte.fdl.property.EgovPropertyService;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Spring Inner Exception Try Catching Filter exam) 500, 501 ...
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			StringBuffer result_log = new StringBuffer();

			String param = "";
			Map mp = request.getParameterMap();

			Set keys = (Set) mp.keySet();
			Iterator iter = keys.iterator();

			while (iter.hasNext()) {
				String key = iter.next().toString();
				Object[] value = (Object[]) mp.get(key);
				if (key.equals("mber_pw") || key.equals("password")) {
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

			//e.printStackTrace();
			
			request.setCharacterEncoding("UTF-8");
			
			
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			Date time2 = new Date();
			String time1 = format1.format(time2);
			
			
			logger.error("===================================================================================");
			logger.error("e.time ==> " + time1);
			logger.error("e.getMessage() ==> " + e.getMessage());
			logger.error("request.getRequestURI() ==> " + request.getRequestURI());
			logger.error("request.getMethod() ==> " + request.getMethod());
			logger.error("request.getRequestURL() ==> " + URLDecoder.decode(String.valueOf(request.getRequestURL()), "UTF-8"));
			logger.error("request.getHeader(\"User-Agent\") ==> " + request.getHeader("User-Agent"));
			logger.error("request.getHeader(\"referer\"); ==> " + request.getHeader("referer"));
			logger.error("request IP ==> " + getClientIpAddr(request));
			logger.error("param ==> " + param);
			logger.error("===================================================================================");
			
			

			result_log.append("====================================================="+System.getProperty("line.separator"));
			result_log.append("e.time ==> " + time1 +System.getProperty("line.separator"));
			result_log.append("e.getMessage() ==> " + e.getMessage() +System.getProperty("line.separator"));
			result_log.append("request.getRequestURI() ==> " + request.getRequestURI()+System.getProperty("line.separator"));
			result_log.append("request.getMethod() ==> " + request.getMethod()+System.getProperty("line.separator"));
			result_log.append("request.getRequestURL() ==> " + URLDecoder.decode(String.valueOf(request.getRequestURL()), "UTF-8")+System.getProperty("line.separator"));
			result_log.append("request.getHeader(\"User-Agent\") ==> " + request.getHeader("User-Agent")+System.getProperty("line.separator"));
			result_log.append("request.getHeader(\"referer\"); ==> " + request.getHeader("referer")+System.getProperty("line.separator"));
			result_log.append("request IP ==> " + getClientIpAddr(request)+System.getProperty("line.separator"));
			result_log.append("param ==> " + param+System.getProperty("line.separator"));
			result_log.append("====================================================="+System.getProperty("line.separator"));
			
		
			
			//필터 로그 파일 생성
			//현재시간
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			
			
			//String log_path = "D:\\project_logs\\js";
			//String log_path = "/mnt/nas/bowlingkorea/logs";
			String log_path = "D:\\project\\jinseong\\JS\\src\\main\\resources\\mnt\\nas\\bowlingkorea\\logs";
			
			dayTime = new SimpleDateFormat("yyyyMMdd");
			String log_dirDate = dayTime.format(new Date(time));
			
			String file_path = log_path+"/filter_"+log_dirDate+".log";
			File log_file = new File(file_path);
			if(log_file.exists() && log_file.isFile() && log_file.canWrite()){
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(log_file,true)); //이어쓰기
				bufferedWriter.newLine(); 			//줄바꿈
				bufferedWriter.write(result_log.toString());
				bufferedWriter.close();
			}else{
			    // 파일 객체 생성
				FileWriter fw = new FileWriter(log_file, true); //파일 생성
				//fw.write(indexText());
	            fw.write(result_log.toString());
	            fw.flush();
	            fw.close();             // 객체 닫기
			}
			
			/*
			if(request.getHeader("referer") == null || "".equals(request.getHeader("referer"))){
				//System.out.println("nnnnnnnnnnnnnnnnnnnnn");
				response.sendRedirect("/main.do");
			}else{
				//System.out.println("not noull");
				//response.sendRedirect(request.getHeader("referer").replace("http://localhost:8080", ""));
			}
			*/
	
			// mvo.setIp(request.getRemoteAddr());
			// custom error response class used across my project
			/*
			 * response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			 * response.getWriter().write(convertObjectToJson(errorResponse));
			 */
		}
	}

	/**
	 * 로드 밸런서로 인한 IP체크 불가할때
	 */
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
