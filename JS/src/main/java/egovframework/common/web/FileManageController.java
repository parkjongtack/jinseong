package egovframework.common.web;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.common.core.ResultDataManager;
import egovframework.common.core.SendMiPlatform;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;
import egovframework.common.utils.EgovFileMngUtil;
import egovframework.common.service.CmmnCodeManageVo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 파일 다운로드를 위한 컨트롤러 클래스
 */

@Controller
public class FileManageController {
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @Resource(name = "FileManageService")
    private FileManageService fileManageService;

    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil EgovFileMngUtil;
    
    Logger log = Logger.getLogger(this.getClass());
    
    /**
     * 브라우저 구분 얻기.
     * 
     * @param request
     * @return
     */
    private String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Trident") > -1) {
        	return "Trident";            
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        }
        return "Firefox";
    }
    
    /**
     * Disposition 지정하기.
     * 
     * @param filename
     * @param request
     * @param response
     * @throws Exception
     */
    private void setDisposition(String filename
					    		, HttpServletRequest request
					    		, HttpServletResponse response) throws Exception {
	String browser = getBrowser(request);
	
	String dispositionPrefix = "attachment; filename=";
	String encodedFilename = null;

	if (browser.equals("MSIE")) {
	    encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
	} else if (browser.equals("Trident")) {
    	encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");	    
	} else if (browser.equals("Firefox")) {
	    encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
	} else if (browser.equals("Opera")) {
	    encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
	} else if (browser.equals("Chrome")) {
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < filename.length(); i++) {
		char c = filename.charAt(i);
		if (c > '~') {
		    sb.append(URLEncoder.encode("" + c, "UTF-8"));
		} else {
		    sb.append(c);
		}
	    }
	    encodedFilename = sb.toString();
	} else {
	    //throw new RuntimeException("Not supported browser");
	    throw new IOException("Not supported browser");
	}
	
	response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

	if ("Opera".equals(browser)){
	    response.setContentType("application/octet-stream;charset=UTF-8");
	}
    }

    /**
     * 첨부파일로 등록된 파일 목록
     * 
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/common/fileDownList.do")
    public String fileDownList(@ModelAttribute("searchVO") FileManageVo fileVO
					    		, Map<String, Object> commandMap
					    		, ModelMap model) throws Exception {
    	
    String atch_file_id = fileVO.atch_file_id;
    
	fileVO.setAtch_file_id(atch_file_id);
	List<FileManageVo> result = fileManageService.selectFileList(fileVO);

	model.addAttribute("fileList", result);
	model.addAttribute("updateFlag", "N");
	model.addAttribute("fileListCnt", result.size());
	model.addAttribute("atch_file_id", atch_file_id);
	
	return "/common/fileDown";
    }
    
    /**
     * 첨부파일로 등록된 파일 목록 Json
     * 
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/common/fileDownListJson.do")
    public ModelAndView fileDownListJson(@ModelAttribute("searchVO") FileManageVo fileVO
					    		, Map<String, Object> commandMap
					    		, ModelMap model, HttpServletResponse response , HttpServletRequest request) throws Exception {
    	
    String atch_file_id = fileVO.atch_file_id;
    
	fileVO.setAtch_file_id(atch_file_id);
	
	
	
	String callback = request.getParameter("callback");
	try{
		//client에서 받은 DemoVo 에 선언된 값들
		//아래는 받은 값을 출력 테스트
		//ResultDataManager 여러 LIST를 별도의 LIST2에 추가
		ResultDataManager rm = new ResultDataManager();
		List<FileManageVo> result = fileManageService.selectFileList(fileVO);
		rm.setData(FileManageVo.class, result);
		
		//SendMiPlatform.SendData 에서 LIST2의 값을 JSON으로 변경 및 출력
		return SendMiPlatform.SendDataByJsonp(response, rm, callback);
	}catch(Exception e){
		return SendMiPlatform.ErrorDataByJsonp(response, e.getMessage(), callback);
	}
	//return "/common/fileDown";
    }
    
    /**
     * 첨부파일로 등록된 파일 목록 팝업
     * 
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/common/fileListPop.do")
    public String fileListPop(@ModelAttribute("searchVO") FileManageVo fileVO
						    		, Map<String, Object> commandMap
						    		, ModelMap model) throws Exception {
    	
    	String atch_file_id = fileVO.atch_file_id;
    	
    	fileVO.setAtch_file_id(atch_file_id);
    	List<FileManageVo> result = fileManageService.selectFileList(fileVO);
    	
    	model.addAttribute("fileList", result);
    	model.addAttribute("updateFlag", "N");
    	model.addAttribute("fileListCnt", result.size());
    	model.addAttribute("atch_file_id", atch_file_id);
    	
    	return "/common/fileListPop";
    }

    /**
     * 첨부파일로 등록된 파일에 대하여 다운로드를 제공한다.
     * 
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/common/fileDown.do")    
    public void fileDown(Map<String, Object> commandMap
    					, FileManageVo fileVO
			    		, HttpServletRequest request
			    		, HttpServletResponse response) throws Exception {
    	
   
	}
    
    /**
     * 첨부파일로 등록된 파일 목록(NewBoard)
     * 
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/common/fileDownListNewBoard.do")
    public String fileDownListNewBoard(@ModelAttribute("fileVO") FileManageVo fileVO
							    		, Map<String, Object> commandMap
							    		, ModelMap model) throws Exception {

        String seq = fileVO.seq;
        fileVO.setSeq(seq);
    	List<FileManageVo> result =   fileManageService.selectFileListNewBoard(fileVO);
		model.addAttribute("fileList" ,result);
    	
    	return "/common/fileDown";
    }
    
    /**
	 * 
	 * 업로드파일 정보 등록 Ajax
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/common/insertFileInfo.do")
//	@ResponseBody
	public ModelAndView insertFileInfo(@ModelAttribute("vo") FileManageVo vo
										, HttpServletRequest request
										, HttpServletResponse response) throws Exception {
		
		try {
//			List<FileManageVo> result = null;
//			String atchFileId = "";

//			final Map<String, MultipartFile> files = multiRequest.getFileMap();
//			if(!files.isEmpty()){
//	  			result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
//	  			atchFileId = fileManageService.insertFileInfs(result);
//			}
			
			
			
			ResultDataManager rm = new ResultDataManager();
			vo.setFile_sn("0");
			//1.파일정보 마스터 등록
			fileManageService.insertFileMaster(vo);
			
			//2.파일정보 디테일 등록			
			String data[] = vo.getOrignl_file_nm().split("\\.");
			vo.setFile_extsn(data[1]);
			vo.setFile_stre_cours("/portal/container1/upload/board/");
			
			fileManageService.insertFileDetail(vo);
			
			return SendMiPlatform.SendData(response, rm);
		} catch(Exception e) {
			return SendMiPlatform.ErrorData(response, e.getMessage());
		}
		
	}

	/**
	 * 에디터 이미지 업로드
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/common/imgUpload.do")
	public ModelAndView imgUpload(	HttpServletRequest request
									, HttpServletResponse response
									, MultipartHttpServletRequest mrequest
									, HttpSession session) throws Exception {
		//파일 업로드
		String type = request.getParameter("uploadType");
		String imgName = null;
		MultipartFile mpf = mrequest.getFile("fileUpload");	
		if(mpf != null) {
			imgName = EgovFileMngUtil.saveImgFile(mpf, request, type);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("imgName",imgName);
		mav.setViewName("/common/imgUpload");
		return mav;		
		
	}		

    /**
     * 첨부파일 변경을 위한 수정페이지로 이동한다.
     * 
     * @param fileVO
     * @param atchFileId
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/common/selectFileInfsForUpdate.do")
    public String selectFileInfsForUpdate(@ModelAttribute("searchVO") FileManageVo fileVO
								    		, Map<String, Object> commandMap
								    		, ModelMap model) throws Exception {

	String atchFileId = fileVO.getAtch_file_id();
	String seq = fileVO.getSeq();
	fileVO.setAtch_file_id(atchFileId);

	List<FileManageVo> result = fileManageService.selectFileList(fileVO);
	
	model.addAttribute("fileList", result);
	model.addAttribute("updateFlag", "Y");
	model.addAttribute("fileListCnt", result.size());
	model.addAttribute("atchFileId", atchFileId);
	model.addAttribute("seq", seq);
	return "/common/fileList";
	
    }

    /**
     * 첨부파일 삭제.
     * 
     */
    @RequestMapping("/common/deleteFileData.do")
    public String deleteFileData(@ModelAttribute("searchVO") FileManageVo fileVO
						    		, @RequestParam("returnUrl") String returnUrl
						    		, HttpServletRequest request
						    		, ModelMap model) throws Exception {

    	String seq = fileVO.getSeq();
    	fileManageService.deleteFileInf(fileVO);
    	
	     return "forward:/cs/qnaUpdt.do?seq=" + seq;
    }
    
	/**
	 * 에디터 이미지 업로드(다중)
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/common/imgUploadSe2.do")
	public void imgUploadSe2(	HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		try{
			
			String sFileInfo = "";
			String filename = request.getHeader("file-name");
			String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
			filename_ext = filename_ext.toLowerCase();
			//String dftFilePath = request.getSession().getServletContext().getRealPath("/");
			String filePath = "/upload/se2/";
			File file = new File(filePath);
			if(!file.exists()){
				file.mkdirs();
			}
			String realFileNm = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String today= formatter.format(new java.util.Date());
			realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
			String rlFileNm = filePath + realFileNm;
			InputStream is = request.getInputStream();
			OutputStream os = new FileOutputStream(rlFileNm);
			int numRead;
			byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
			while((numRead = is.read(b,0,b.length)) !=-1){
				os.write(b,0,numRead);
			}
			if(is !=null){
				is.close();
			}
			os.flush();
			os.close();
			
			sFileInfo +="&bNewLine=true";
			sFileInfo += "&sFileName="+ filename;
			sFileInfo += "&sFileURL="+"/upload/se2/"+realFileNm;
			PrintWriter print = response.getWriter();
			print.print(sFileInfo);
			print.flush();
			print.close();
			} catch (Exception e){
			e.printStackTrace();
		}
	}	
	
	/**
	 * 에디터 이미지 업로드(단일)
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/common/imgUploadSe2IE.do")
	public void imgUploadSe2IE(	HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
			String return1="";
			String return2="";
			String return3="";
			String name = "";
			if(ServletFileUpload.isMultipartContent(request)){
				
				ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
				uploadHandler.setHeaderEncoding("UTF-8");
				List<FileItem> items = uploadHandler.parseRequest(request);
				for(FileItem item : items) {
					if(item.getFieldName().equals("callback")){
						return1 = item.getString("UTF-8");
					}
					else if(item.getFieldName().equals("callback_func")){
						return2 = "?callback_func="+item.getString("UTF-8");
					}
					else if(item.getFieldName().equals("Filedata")){
						if(item.getSize()>0){
							String ext = item.getName().substring(item.getName().lastIndexOf(".")+1);
							ServletContext context = request.getSession().getServletContext();
							String defaultPath = context.getRealPath("/");
							String path = defaultPath + "upload" + File.separator;
							File file = new File(path);
							
							if(!file.exists()){
								file.mkdirs();
							}
							
							
							String realname = UUID.randomUUID().toString() + "." + ext;
							InputStream is = item.getInputStream();
							OutputStream os=new FileOutputStream(path + realname);
							int numRead;
							byte b[] = new byte[(int)item.getSize()];
							while((numRead = is.read(b,0,b.length)) != -1){
								os.write(b,0,numRead);
							}
							if(is != null)
							is.close();
							os.flush();
							os.close();
							///////////////// 서버에 파일쓰기 /////////////////
							return3 += "&bNewLine=true&sFileName="+name+"&sFileURL=/upload/se2/"+realname;	
						}else{
							return3 += "&errstr=error";
						}
					}
				}		
			}
			response.sendRedirect(return1+return2+return3);
		}
	
	
	 @RequestMapping(value = "/common/nunFileDown.do")    
	 public void nunFileDown(Map<String, Object> commandMap, FileManageVo fileVO ,HttpServletRequest request ,HttpServletResponse response) throws Exception {

		 String atch_file_id 	= fileVO.atch_file_id;
		 String sum				= fileVO.sum;
		 
		 fileVO.setAtch_file_id(atch_file_id);
		 
		 FileManageVo fvo		= fileManageService.getFileAttachView(fileVO);
		 
		 String real			= propertyService.getString("Globals.fileStorePath");
		 
		 String file_rute 		= "/"+fvo.getFile_stre_cours()+"/";
		 String fake_nm 		= fvo.getStre_file_nm()+"."+fvo.getFile_extsn();
		 
		 File uFile 			= new File(real+file_rute, fake_nm);
		 int fSize 				= (int)uFile.length();
			 
		 if(fSize > 0){
			 String mimetype 	= "application/x-msdownload";
			 response.setBufferSize(fSize);
			 response.setContentType(mimetype);
			 setDisposition(fvo.getOrignl_file_nm(), request, response);
			 response.setContentLength(fSize);

			 BufferedInputStream in 	= null;
			 BufferedOutputStream out 	= null;

			 try{
				 in 	= new BufferedInputStream(new FileInputStream(uFile));
				 out	= new BufferedOutputStream(response.getOutputStream());
				 FileCopyUtils.copy(in, out);
				 out.flush();
				 
			} catch (Exception ex) {
			    log.debug("IGNORED: " + ex.getMessage());
			} finally {
			    if(in != null){try{in.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
			    if(out != null){try{out.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
			}

		 }else{
			 response.setContentType("application/x-msdownload");
			 PrintWriter printwriter = response.getWriter();
			 printwriter.println("<html>");
			 printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fvo.getOrignl_file_nm() + "</h2>");
			 printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			 printwriter.println("<br><br><br>&copy; webAccess");
			 printwriter.println("</html>");
			 printwriter.flush();
			 printwriter.close();
		 }
	 }
	 
	 
	 @RequestMapping(value = "/commonfile/nunFileDown.do")    
	 public void nunFileDown2(Map<String, Object> commandMap, FileManageVo fileVO ,HttpServletRequest request ,HttpServletResponse response) throws Exception {
		 
		 String atch_file_id 	= fileVO.atch_file_id;
		 String sum				= fileVO.sum;
		 
		 fileVO.setAtch_file_id(atch_file_id);
		 
		 FileManageVo fvo		= fileManageService.getFileAttachView(fileVO);
		 
		 String real			= propertyService.getString("Globals.fileStorePath");
		 
		 String file_rute 		= "/"+fvo.getFile_stre_cours()+"/";
		 String fake_nm 		= fvo.getStre_file_nm()+"."+fvo.getFile_extsn();
		 
		 File uFile 			= new File(real+file_rute, fake_nm);
		 int fSize 				= (int)uFile.length();
		 
		 if(fSize > 0){
			 String mimetype 	= "application/x-msdownload";
			 response.setBufferSize(fSize);
			 response.setContentType(mimetype);
			 setDisposition(fvo.getOrignl_file_nm(), request, response);
			 response.setContentLength(fSize);
			 
			 BufferedInputStream in 	= null;
			 BufferedOutputStream out 	= null;
			 
			 try{
				 in 	= new BufferedInputStream(new FileInputStream(uFile));
				 out	= new BufferedOutputStream(response.getOutputStream());
				 FileCopyUtils.copy(in, out);
				 out.flush();
				 
			 } catch (Exception ex) {
				 log.debug("IGNORED: " + ex.getMessage());
			 } finally {
				 if(in != null){try{in.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
				 if(out != null){try{out.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
			 }
			 
		 }else{
			 response.setContentType("application/x-msdownload");
			 PrintWriter printwriter = response.getWriter();
			 printwriter.println("<html>");
			 printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fvo.getOrignl_file_nm() + "</h2>");
			 printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			 printwriter.println("<br><br><br>&copy; webAccess");
			 printwriter.println("</html>");
			 printwriter.flush();
			 printwriter.close();
		 }
	 }
	 
	 //썸네일 
	 @RequestMapping(value = "/common/thumbFileDown.do")    
	 public void thumbFileDown(Map<String, Object> commandMap, FileManageVo fileVO ,HttpServletRequest request ,HttpServletResponse response) throws Exception {
		 
		 String atch_file_id 	= fileVO.atch_file_id;
		 String sum				= fileVO.sum;
		 
		 fileVO.setAtch_file_id(atch_file_id);
		 
		 FileManageVo fvo		= fileManageService.getFileAttachView(fileVO);
		 
		 String real			= propertyService.getString("Globals.fileStorePath");
		 
		 String file_rute 		= "/"+fvo.getFile_stre_cours()+"/";
		 String fake_nm 		= fvo.getStre_file_nm()+"_thumb."+fvo.getFile_extsn();
		 
		 File uFile 			= new File(real+file_rute, fake_nm);
		 int fSize 				= (int)uFile.length();
		 
		 if(fSize > 0){
			 String mimetype 	= "application/x-msdownload";
			 response.setBufferSize(fSize);
			 response.setContentType(mimetype);
			 setDisposition(fvo.getOrignl_file_nm(), request, response);
			 response.setContentLength(fSize);
			 
			 BufferedInputStream in 	= null;
			 BufferedOutputStream out 	= null;
			 
			 try{
				 in 	= new BufferedInputStream(new FileInputStream(uFile));
				 out	= new BufferedOutputStream(response.getOutputStream());
				 FileCopyUtils.copy(in, out);
				 out.flush();
				 
			 } catch (Exception ex) {
				 log.debug("IGNORED: " + ex.getMessage());
			 } finally {
				 if(in != null){try{in.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
				 if(out != null){try{out.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
			 }
			 
		 }else{
			 response.setContentType("application/x-msdownload");
			 PrintWriter printwriter = response.getWriter();
			 printwriter.println("<html>");
			 printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fvo.getOrignl_file_nm() + "</h2>");
			 printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			 printwriter.println("<br><br><br>&copy; webAccess");
			 printwriter.println("</html>");
			 printwriter.flush();
			 printwriter.close();
		 }
	 }
	 
	 
	 //썸네일2 
	 @RequestMapping(value = "/commonfile/thumbFileDown.do")    
	 public void thumbFileDown2(Map<String, Object> commandMap, FileManageVo fileVO ,HttpServletRequest request ,HttpServletResponse response) throws Exception {
		 
		 String atch_file_id 	= fileVO.atch_file_id;
		 String sum				= fileVO.sum;
		 
		 fileVO.setAtch_file_id(atch_file_id);
		 
		 FileManageVo fvo		= fileManageService.getFileAttachView(fileVO);
		 
		 String real			= propertyService.getString("Globals.fileStorePath");
		 
		 String file_rute 		= "/"+fvo.getFile_stre_cours()+"/";
		 String fake_nm 		= fvo.getStre_file_nm()+"_thumb."+fvo.getFile_extsn();
		 
		 File uFile 			= new File(real+file_rute, fake_nm);
		 int fSize 				= (int)uFile.length();
		 
		 if(fSize > 0){
			 String mimetype 	= "application/x-msdownload";
			 response.setBufferSize(fSize);
			 response.setContentType(mimetype);
			 setDisposition(fvo.getOrignl_file_nm(), request, response);
			 response.setContentLength(fSize);
			 
			 BufferedInputStream in 	= null;
			 BufferedOutputStream out 	= null;
			 
			 try{
				 in 	= new BufferedInputStream(new FileInputStream(uFile));
				 out	= new BufferedOutputStream(response.getOutputStream());
				 FileCopyUtils.copy(in, out);
				 out.flush();
				 
			 } catch (Exception ex) {
				 log.debug("IGNORED: " + ex.getMessage());
			 } finally {
				 if(in != null){try{in.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
				 if(out != null){try{out.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
			 }
			 
		 }else{
			 response.setContentType("application/x-msdownload");
			 PrintWriter printwriter = response.getWriter();
			 printwriter.println("<html>");
			 printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fvo.getOrignl_file_nm() + "</h2>");
			 printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			 printwriter.println("<br><br><br>&copy; webAccess");
			 printwriter.println("</html>");
			 printwriter.flush();
			 printwriter.close();
		 }
	 }
	 
	 @RequestMapping(value = "/common/nunFileDelete.do")    
	 public @ResponseBody ModelAndView nunFileDel(Map<String, Object> commandMap, 
			 										FileManageVo fileVO 
			 										,HttpServletRequest request 
			 										,HttpServletResponse response) throws Exception {
	
		 try{
			fileVO.setFile_attach_seq(fileVO.file_attach_seq);
			String real					= propertyService.getString("Globals.fileStorePath");
			FileManageVo fvo			= fileManageService.getNunFileAttachView(fileVO);
			File uFile 					= new File(real+fvo.getFile_route(), fvo.getFake_name());
			if(uFile.exists()) uFile.delete();
			Map<String,Object> data 	= new HashMap<String,Object>();
			int resultInt				= fileManageService.setNunFileAttachDelete(fvo);
			if(resultInt > 0){
				data.put("ResultCode","1");
				data.put("ResultMsg","성공");
			}else{
				data.put("ResultCode","0");
				data.put("ResultMsg","삭제가 취소되었습니다.");
			}
			return SendMiPlatform.SendString(response, data);
			
		 }catch(Exception e){
			 e.printStackTrace();
			 return SendMiPlatform.ErrorData(response, e.getMessage());
		 }
		
	 }
	 
	@RequestMapping(value="/commonFile/editorImgUpload.do")
	public void setNunEditorFileApply(	HttpServletRequest request
									, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		apageMemberManageVo vo = (apageMemberManageVo)session.getAttribute("adminInfo");
		if(vo == null){
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print("<script>alert('잘못된 접근입니다.');location.href='"+request.getContextPath()+"/main.do';</script>");
		} else {

			try{
				Date todate 				= new Date();
				SimpleDateFormat sdf		= new SimpleDateFormat("yyyyMM");
				String day					= sdf.format(todate);
				String sFileInfo 			= "";
				String filename 			= request.getHeader("file-name");
				String filename_ext 		= filename.substring(filename.lastIndexOf(".")+1);
				filename_ext 				= filename_ext.toLowerCase();

				String dftFilePath1			= propertyService.getString("Globals.fileStorePathEdit");
				String dftFilePath2			= File.separator + "naverEditor"+File.separator+day+File.separator;
				String filePath 			= dftFilePath1+dftFilePath2;
				File file 					= new File(filePath);
				if(!file.exists()){
					file.mkdirs();
				}
				String realFileNm 			= "";
				SimpleDateFormat formatter	= new SimpleDateFormat("yyyyMMddHHmmss");
				String today				= formatter.format(new java.util.Date());
				realFileNm 					= today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
				String rlFileNm 			= filePath + realFileNm;
				InputStream is 				= request.getInputStream();
				OutputStream os 			= new FileOutputStream(rlFileNm);
				int numRead;
				byte b[] 					= new byte[Integer.parseInt(request.getHeader("file-size"))];
				while((numRead = is.read(b,0,b.length)) !=-1){
					os.write(b,0,numRead);
				}
				if(is !=null){
					is.close();
				}
				os.flush();
				os.close();
				
				String sFileURL = request.getContextPath()+"/commonfile/nunEditFileDown.do?fileDay="+day+"&fileName="+realFileNm;
				sFileInfo +="&bNewLine=true";
				sFileInfo += "&sFileName="+ filename;
				//sFileInfo += "&sFileURL="+dftFilePath2+realFileNm;
				sFileInfo += "&sFileURL="+sFileURL;
				PrintWriter print = response.getWriter();
				print.print(sFileInfo);
				print.flush();
				print.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
	}
	
	@RequestMapping(value="/commonFile/editorImgUploadEi.do")
	public String setNunEditorFileApplyIe(HttpServletRequest request
										,HttpServletResponse response
										,CmmnCodeManageVo editor) throws Exception {
		
	
		

		
		
		 String return1		= request.getParameter("callback");
		 String return2		= "?callback_func=" + request.getParameter("callback_func");
		 String return3		= "";
		 String name 		= "";
		 try {
			 
			HttpSession session = request.getSession();
			apageMemberManageVo vo = (apageMemberManageVo)session.getAttribute("adminInfo");
			if(vo == null){
				response.setContentType("text/html; charset=utf-8");
				response.getWriter().print("<script>alert('잘못된 접근입니다.');location.href='"+request.getContextPath()+"/main.do';</script>");
			}else{
				
				if(editor.getFiledata() != null && editor.getFiledata().getOriginalFilename() != null && !editor.getFiledata().getOriginalFilename().equals("")) {

		            name 				= editor.getFiledata().getOriginalFilename().substring(editor.getFiledata().getOriginalFilename().lastIndexOf(File.separator)+1);
					String filename_ext = name.substring(name.lastIndexOf(".")+1);
					filename_ext 		= filename_ext.toLowerCase();
				   	String[] allow_file = {"jpg","png","bmp","gif"};
				   	int cnt 			= 0;
				   	
				   	for(int i=0; i<allow_file.length; i++) {
				   		if(filename_ext.equals(allow_file[i])){
				   			cnt++;
				   		}
				   	}
				   	if(cnt == 0) {
				   		return3 = "&errstr="+name;
				   	} else {
			    		
						Date todate 				= new Date();
						SimpleDateFormat sdf		= new SimpleDateFormat("yyyyMM");
						String day					= sdf.format(todate);
						String dftFilePath1			= propertyService.getString("Globals.fileStorePathEdit");
						String dftFilePath2			= File.separator+"naverEditor"+File.separator+day+File.separator;
						String filePath 			= dftFilePath1+dftFilePath2;
						
						File file 					= new File(filePath);
			    		
			    		if(!file.exists()) {
			    			file.mkdirs();
			    		}
			    		
			    		String realFileNm 			= "";
			    		SimpleDateFormat formatter 	= new SimpleDateFormat("yyyyMMddHHmmss");
						String today				= formatter.format(new java.util.Date());
						realFileNm 					= today+UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
						String rlFileNm 			= filePath + realFileNm;

						editor.getFiledata().transferTo(new File(rlFileNm));
						
						String sFileURL = request.getContextPath()+"/commonfile/nunEditFileDown.do?fileDay="+day+"&fileName="+realFileNm;
			    		return3 += "&bNewLine=true";
			    		return3 += "&sFileName="+ name;
			    		//return3 += "&sFileURL="+dftFilePath2+realFileNm;		    		
			    		return3 += "&sFileURL="+sFileURL;		
				   	}
				}else {
					  return3 += "&errstr=error";
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return "redirect:"+return1+return2+return3;
		
	}
	

	@RequestMapping(value="/common/downloadFile.do")
    public void downLoadFile(FileManageVo fileVO,HttpServletRequest request,HttpServletResponse response) throws Exception {
	    	
		String filePath 		= propertyService.getString("Globals.fileStorePathEdit")+"/file_upload/";
		String file_stre_cours 	= fileVO.file_stre_cours;
	    String orignl_file_nm 	= fileVO.orignl_file_nm;
		File uFile 				= new File(filePath+file_stre_cours, orignl_file_nm);

		    if (uFile.exists()) {
				String mimetype 	= "application/x-msdownload";
				int fSize 			= (int)uFile.length();
		        if(fSize < 1){
		        	fSize = 0;
		        }
				response.setBufferSize(fSize);
				response.setContentType(mimetype);
				setDisposition(orignl_file_nm, request, response);
				response.setContentLength(fSize);
				
				BufferedInputStream in 		= null;
				BufferedOutputStream out 	= null;
				FileInputStream	instr		= null;

				try{
					instr	= new FileInputStream(uFile);
				    in 		= new BufferedInputStream(instr);
				    out 	= new BufferedOutputStream(response.getOutputStream());
				    FileCopyUtils.copy(in, out);
				    out.flush();
				    
				}catch(Exception ex){
				    log.debug("IGNORED: " + ex.getMessage());
				    
				}finally{
					if(instr != null){
						try {instr.close();}catch(Exception ignore){}
					}
				    if(in != null){
				    	try{in.close();}catch(Exception ignore){}
				    }
				    if(out != null){
				    	try{out.close();}catch(Exception ignore){}
				    }
				}
				
		    }
	}
	
	
	 // 파일 다운로드
	 @RequestMapping(value = "/apage/common/aFileDown.do")    
	 public void aFileDown(Map<String, Object> commandMap, FileManageVo fileVO ,HttpServletRequest request ,HttpServletResponse response) throws Exception {

		 int file_attach_seq 	= fileVO.file_attach_seq;
		 String sum				= fileVO.sum;
		 
		 fileVO.setFile_attach_seq(file_attach_seq);
		 
		 FileManageVo fvo		= fileManageService.getNunFileAttachView(fileVO);
		 String real			= propertyService.getString("Globals.fileStorePath");
		 
		 if(fvo == null){
			 return;
		 }
		 
		 if("2".equals(sum)){
			int index 				= fvo.getFake_name().indexOf(".");
			String min_name			= fvo.getFake_name().substring(1,index ).toLowerCase();
			String min_name2 		= min_name+"_minThumbnail"+"."+fvo.getExt_name();
			
			File uFile 				= new File(real+fvo.getFile_route(), min_name2);
			int fSize 				= (int)uFile.length();
			
			if((int)uFile.length() <= 0){
				uFile 				= new File(real+fvo.getFile_route(), fvo.getFake_name());
				fSize 				= (int)uFile.length();
			}
			
			if(fSize > 0){
				 String mimetype 	= "application/x-msdownload";
				 response.setBufferSize(fSize);
				 response.setContentType(mimetype);
				 setDisposition(fvo.getReal_name(), request, response);
				 response.setContentLength(fSize);

				 BufferedInputStream in 	= null;
				 BufferedOutputStream out 	= null;
				 FileInputStream fs			= null;

				 try{
					 fs		= new FileInputStream(uFile);
					 in 	= new BufferedInputStream(fs);
					 out	= new BufferedOutputStream(response.getOutputStream());
					 FileCopyUtils.copy(in, out);
					 out.flush();
					 
				} catch (Exception ex) {
				    log.debug("IGNORED: " + ex.getMessage());
				} finally {
					if(fs != null){try{fs.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
				    if(in != null){try{in.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
				    if(out != null){try{out.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
				}

			 }else{
				 response.setContentType("application/x-msdownload");
				 PrintWriter printwriter = response.getWriter();
				 printwriter.println("<html>");
				 printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fvo.getOrignl_file_nm() + "</h2>");
				 printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
				 printwriter.println("<br><br><br>&copy; webAccess");
				 printwriter.println("</html>");
				 printwriter.flush();
				 printwriter.close();
			 }
		 }else{
			 File uFile 			= new File(real+fvo.getFile_route(), fvo.getFake_name());
			 int fSize 				= (int)uFile.length();
			 
			 if(fSize > 0){
				 String mimetype 	= "application/x-msdownload";
				 response.setBufferSize(fSize);
				 response.setContentType(mimetype);
				 setDisposition(fvo.getReal_name(), request, response);
				 response.setContentLength(fSize);

				 BufferedInputStream in 	= null;
				 BufferedOutputStream out 	= null;
				 FileInputStream fs			= null;
				 try{
					 fs		= new FileInputStream(uFile);
					 in 	= new BufferedInputStream(fs);
					 out	= new BufferedOutputStream(response.getOutputStream());
					 FileCopyUtils.copy(in, out);
					 out.flush();
					 
				} catch (Exception ex) {
				    log.debug("IGNORED: " + ex.getMessage());
				} finally {
					if(fs != null){try{fs.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
				    if(in != null){try{in.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
				    if(out != null){try{out.close();}catch(Exception ignore){log.debug("IGNORED: " + ignore.getMessage());}}
				}

			 }else{
				 response.setContentType("application/x-msdownload");
				 PrintWriter printwriter = response.getWriter();
				 printwriter.println("<html>");
				 printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fvo.getOrignl_file_nm() + "</h2>");
				 printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
				 printwriter.println("<br><br><br>&copy; webAccess");
				 printwriter.println("</html>");
				 printwriter.flush();
				 printwriter.close();
			 }
		 }
	 }
	 
	 
	 // 에디터 이미지 파일 다운로드
	 @RequestMapping(value = "/commonfile/nunEditFileDown.do")    
	 public void nunEditFileDown(Map<String, Object> commandMap, FileManageVo fileVO ,HttpServletRequest request ,HttpServletResponse response) throws Exception {
		 	// 넘버 세팅
			String fileDay = (request.getParameter("fileDay") == null) ? "202007" : request.getParameter("fileDay");

			String fileName = (request.getParameter("fileName") == null) ? "" : request.getParameter("fileName");
			
			String path = propertyService.getString("Globals.fileStorePathEdit") 
					+ File.separator + "naverEditor" + File.separator + fileDay + File.separator;
			
			
			boolean pathCheck = true;
			if(fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")){
				pathCheck = false;
			} 
			if(fileDay.contains("..") || fileDay.contains("/") || fileDay.contains("\\")){
				pathCheck = false;
			} 
			
			File uFile = new File(path + fileName);
			int fSize = (int) uFile.length();
			if (fSize > 0 && pathCheck) {
				String mimetype = "application/x-msdownload";
				response.setBufferSize(fSize);
				response.setContentType(mimetype);
				response.setContentLength(fSize);

				BufferedInputStream in = null;
				BufferedOutputStream out = null;

				try {
					in = new BufferedInputStream(new FileInputStream(uFile));
					out = new BufferedOutputStream(response.getOutputStream());
					FileCopyUtils.copy(in, out);
					out.flush();

				} catch (Exception ex) {
					log.debug("IGNORED: " + ex.getMessage());
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception ignore) {
							log.debug("IGNORED: " + ignore.getMessage());
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (Exception ignore) {
							log.debug("IGNORED: " + ignore.getMessage());
						}
					}
				}

			} else {

				response.setContentType("application/x-msdownload");
				PrintWriter printwriter = response.getWriter();
				printwriter.println("<html>");
				printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
				printwriter.println("<br><br><br>&copy; webAccess");
				printwriter.println("</html>");
				printwriter.flush();
				printwriter.close();
			}
	 }
	 
	 
	 // 파일 삭제
	 @RequestMapping(value = "/apage/common/aFileDelete.do")    
	 public @ResponseBody ModelAndView aFileDelete(Map<String, Object> commandMap, 
			 										FileManageVo fileVO 
			 										,HttpServletRequest request 
			 										,HttpServletResponse response) throws Exception {
	
		 try{
			fileVO.setFile_attach_seq(fileVO.file_attach_seq);
			String real					= propertyService.getString("Globals.fileStorePath");
			FileManageVo fvo			= fileManageService.getNunFileAttachView(fileVO);
			File uFile 					= new File(real+fvo.getFile_route(), fvo.getFake_name());
			if(uFile.exists()) uFile.delete();
			Map<String,Object> data 	= new HashMap<String,Object>();
			int resultInt				= fileManageService.setNunFileAttachDelete(fvo);
			if(resultInt > 0){
				data.put("ResultCode","1");
				data.put("ResultMsg","성공");
			}else{
				data.put("ResultCode","0");
				data.put("ResultMsg",real);
			}
			return SendMiPlatform.SendString(response, data);
			
		 }catch(Exception e){
			 e.printStackTrace();
			 return SendMiPlatform.ErrorData(response, e.getMessage());
		 }
		
	 }
}

