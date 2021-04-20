package egovframework.common.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

public class SendMiPlatform{
	public static ModelAndView SendData(HttpServletResponse response, ResultDataManager rm) throws Exception{

		try{
			Gson json = new Gson();
			
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "X-PINGARUNER");
			response.setHeader("Access-Control-Max-Age", "1728000");
			response.setContentType("text/json; charset=utf-8");
			
			Map<String,Object> root 	= new HashMap<String,Object>();
			Map<String,Object> data 	= new HashMap<String,Object>();
		
			data.put("ResultCode","1");
			data.put("ResultMsg","success");
			
			for(int i=0;i<rm.getDataCount();i++){
				data.put("dataset"+i, rm.getData(i).getDataList());
			}
			
			root.put("root",data);
			response.getWriter().print(json.toJson(root));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ModelAndView SendString (HttpServletResponse response, Map<String,Object> data) throws Exception{
		
		try{
			Gson json = new Gson();
			
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "X-PINGARUNER");
			response.setHeader("Access-Control-Max-Age", "1728000");
			response.setContentType("text/json; charset=utf-8");
			
			Map<String,Object> root 	= new HashMap<String,Object>();
			
			root.put("root",data);
			
			response.getWriter().print(json.toJson(root));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ModelAndView ErrorData(HttpServletResponse response, String ErrMsg){
		try {
			Gson json = new Gson();
			
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "X-PINGARUNER");
			response.setHeader("Access-Control-Max-Age", "1728000");
			response.setContentType("text/json; charset=utf-8");
			
			Map<String,Object> root 	= new HashMap<String,Object>();
			Map<String,Object> data 	= new HashMap<String,Object>();
			
			data.put("ResultCode","0");
			data.put("ResultMsg",ErrMsg);
			
			root.put("root",data);
			response.getWriter().print(json.toJson(root));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ModelAndView SendDataByJsonp(HttpServletResponse response, ResultDataManager rm,String callback) throws Exception{
		
		Gson json = new Gson();
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-PINGARUNER");
		response.setHeader("Access-Control-Max-Age", "1728000");
		response.setContentType("application/json; charset=utf-8");
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ErrorCode","0");
		params.put("ErrorMsg","SUCC");
		params.put("EmpnoUpdate","UpdateValue");
		params.put("DeptCodeUpdate","UpdateValue");
		params.put("AppUpdate","UpdateValue");
		int datasetCount = rm.getDataCount();
		params.put("DatasetCount",datasetCount);
		
		Map<String,Object> colinfo = new HashMap<String,Object>();
		colinfo.put("skin1","skin1");
		colinfo.put("skin2","skin2");
		colinfo.put("skin3","skin3");
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("colinfo", colinfo);
		
		for(int i=0;i<rm.getDataCount();i++){
			data.put("dataset"+i, rm.getData(i).getDataList());
		}
		
		data.put("params", params);
		
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("root",data);
		
		//response.getWriter().print(json.toJson(root));
		response.getWriter().print(callback + "(" + json.toJson(root) + ")");
		return null;
	}
	public static ModelAndView ErrorDataByJsonp(HttpServletResponse response, String ErrMsg, String callback){
		
		try {
			Gson json = new Gson();
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "X-PINGARUNER");
			response.setHeader("Access-Control-Max-Age", "1728000");
			response.setContentType("text/json; charset=utf-8");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("ErrorCode","-1");
			params.put("ErrorMsg",ErrMsg);
			
			Map<String,Object> colinfo = new HashMap<String,Object>();
			colinfo.put("skin1","skin1");
			colinfo.put("skin2","skin2");
			colinfo.put("skin3","skin3");
			
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("colinfo", colinfo);
			data.put("params", params);
		
			
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("root",data);
		
			//response.getWriter().print(json.toJson(root));
			response.getWriter().print(callback + "(" + json.toJson(root) + ")");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}