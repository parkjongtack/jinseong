package egovframework.apage.logs.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import egovframework.common.core.SendMiPlatform;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class ApageLogsManageController {

	private Logger logger = Logger.getLogger(this.getClass());
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
    
    
    //로그분석 페이지
  	@RequestMapping(value="/apage/log/logList.do")
  	public String logList(
  									   HttpServletRequest request
  									 , HttpServletResponse response
  									 , ModelMap model
  									 , HttpSession session) throws Exception {
  		
  		return "/apage/log/adminLogList";
  	}
  	
  	
  	//로그파일 read
  	@RequestMapping(value="/apage/log/logReadAjax.do")
  	@ResponseBody
  	public ModelAndView logReadAjax(
					  			HttpServletRequest request
					  			, HttpServletResponse response
					  			, ModelMap model
					  			, HttpSession session) throws Exception {
  		 
  		Map<String,Object> data 	= new HashMap<String,Object>();
  		try{
	  		String log_file_date = request.getParameter("log_file_date") != null ? request.getParameter("log_file_date").trim() : "";
	  		if(log_file_date != null && !log_file_date.equals("")){
	  			String log_file_name = "contestAccess_"+log_file_date.replaceAll("-", "")+".log";
	  			ArrayList<String> sb = getReadLogFile(log_file_name);
	  			data.put("result", "Y");
	  			data.put("resultLine", sb);
	  		}
			return SendMiPlatform.SendString(response, data);
		}catch(Exception e){
			e.printStackTrace();
			data.put("resultCode" , "E");
			return SendMiPlatform.SendString(response, data);
		}
  		
  	}
  	
  	
  	
  	
  	/**
  	 * Log File Read Method
  	 * @param String
  	 * @return String
  	 * @throws Exception
  	 * */
  	public ArrayList<String> getReadLogFile(String file_name) throws Exception{
  		StringBuffer sb = new StringBuffer();
  		
  		ArrayList<String> arr = new ArrayList<String>();
  		try {
  			String path =  propertyService.getString("Globals.contestAccessLogPath");
  			File log_file 					= new File(path, file_name);
  			int logListCnt = 0;
  			JsonObject analyticsVo = new JsonObject();
  			
  			
  			if(log_file.exists()){
  				JsonElement je = new JsonObject();
  				analyticsVo.add("result", je);
  				
  				Scanner scan = new Scanner(log_file);
  	            while(scan.hasNextLine()){
  	            	String readLine = scan.nextLine();
  	            	System.out.println(readLine);
  	            	System.out.println(readLine.charAt(0));
  	            	if(readLine.substring(0, 1).equals("[")){	//상태값이 존재할때만 Read
  	            		sb.append(readLine + System.getProperty("line.separator"));
  	            		arr.add(readLine);
  	            		//System.out.println(scan.nextLine());
  	            		/*
  	            		String readOpt[] = readLine.split("]");
  	            		System.out.println("[ IF IN");
  	            		System.out.println(readOpt.length);
  	            		System.out.println();
  	            		for(int i = 0; i < readOpt.length; i ++){
  	            			System.out.println(readOpt[i]);
  	            		}
  	            		*/
  	            		logListCnt++;	//로그 라인수 ++
  	            	}
  	            }
  			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
  		return arr;
  	}
  	
}
