package egovframework.common.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.common.service.FileManageVo;
 
/**
 * @Class Name  : EgovFileMngUtil.java
 * @Description : 메시지 처리 관련 유틸리티
 * @Modification Information
 * 
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   2009.02.13       이삼섭                  최초 생성
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 02. 13
 * @version 1.0 
 * @see 
 * 
 */
@Component("EgovFileMngUtil")
public class EgovFileMngUtil {

    public static final int BUFF_SIZE = 2048;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @Resource(name="egovFileIdGnrService")    
    private EgovIdGnrService egovIdGnrService;

    Logger log = Logger.getLogger(this.getClass());

    /**
     * 첨부파일에 대한 목록 정보를 취득한다.
     * 
     * @param files
     * @return
     * @throws Exception
     */
    public List<FileManageVo> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath) throws Exception {
	int fileKey = fileKeyParam;

	String storePathString = "";
	String atchFileIdString = "";
	
	if ("".equals(storePath) || storePath == null) {
	    storePathString = "/portal/container1/upload/board/";   /*propertyService.getString("Globals.fileStorePath");*/
	} else {
	    storePathString = storePath;
	}

	if ("".equals(atchFileId) || atchFileId == null) {
	    atchFileIdString = egovIdGnrService.getNextStringId();
	} else {
	    atchFileIdString = atchFileId;
	}

	File saveFolder = new File(storePathString);
	
	if (!saveFolder.exists() || saveFolder.isFile()) {
	    saveFolder.mkdirs();
	}

	Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
	MultipartFile file;
	String filePath = "";
	List<FileManageVo> result  = new ArrayList<FileManageVo>();
	FileManageVo fvo;

	while (itr.hasNext()) {
	    Entry<String, MultipartFile> entry = itr.next();

	    file = entry.getValue();
	    String orginFileName = file.getOriginalFilename();
	    
	    //--------------------------------------
	    // 원 파일명이 없는 경우 처리
	    // (첨부가 되지 않은 input file type)
	    //--------------------------------------
	    if ("".equals(orginFileName)) {
		continue;
	    }
	    ////------------------------------------

	    int index = orginFileName.lastIndexOf(".");
	    //String fileName = orginFileName.substring(0, index);
	    String fileExt = orginFileName.substring(index + 1);
	    String newName = KeyStr + EgovStringUtil.getTimeStamp() + fileKey;
	    long _size = file.getSize();

	    if (!"".equals(orginFileName)) {
		filePath = storePathString + File.separator + newName; //파일확장자를 빼고 저장 
		file.transferTo(new File(filePath+"."+fileExt));
	    }
	    fvo = new FileManageVo();
	    fvo.setFile_extsn(fileExt);
	    fvo.setFile_stre_cours(storePathString);
	    fvo.setFile_mg(Long.toString(_size));
	    fvo.setOrignl_file_nm(orginFileName);
	    fvo.setStre_file_nm(newName);
	    fvo.setAtch_file_id(atchFileIdString);
	    fvo.setFile_sn(String.valueOf(fileKey));

	    //writeFile(file, newName, storePathString);
	    result.add(fvo);
	    
	    fileKey++;
	}

	return result;
    }

	/**
	 * 첨부파일 ID채번
	 * Prefix + 현재시간 + msTime
	 * 
	 * @param strPrefix
	 * @return sb
	 */
	public String createKey(String strPrefix) {
		
		StringBuilder sb		= new StringBuilder();
		String currTime			= new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		String msTime			= Long.toString(System.currentTimeMillis()%100000000);
		
		sb.append(strPrefix != null ? strPrefix : "").append(currTime).append(msTime);
		
		return sb.toString();
	}
	
	/**
	 * 에디터 위젯에 사용되는 이미지 업로드
	 */
	public String saveImgFile(MultipartFile mpf
			, HttpServletRequest request
			, String type) throws Exception {

		String orgFileName		= mpf.getOriginalFilename(); //원본 파일 이름 
		if(orgFileName==null || orgFileName=="") return null;
		String fileExt			= orgFileName.substring(orgFileName.lastIndexOf(".") +1, orgFileName.length()); //확장자
		String strFileRealNm	= createKey("inno_"); //파일 이름 생성
		String filePath 		= null;
		
//		if(type.equals("editor")) filePath = "C:\\portal\\portal\\src\\main\\webapp\\upload\\";
		if(type.equals("editor")) filePath = "/upload/innoFiles/editor/";
		
//		String absolutePath 	= "C:\\portal\\portal\\src\\main\\webapp\\upload\\"; //파일 업로드될 절대 경로 
		String absolutePath 	= request.getSession().getServletContext().getRealPath(filePath); //파일 업로드될 절대 경로 
		
		
		String fileNm = strFileRealNm+"."+fileExt; //파일 이름 
				
		byte[] bytes 			= mpf.getBytes();
		FileOutputStream fos	= null;
		File file				= new File(filePath + "/" + fileNm);
		fos						= new FileOutputStream(file);
		fos.write(bytes);		
		fos.close();

		return filePath+fileNm;
	}	

    
	/**
	 * 에디터 위젯에 사용되는 이미지 업로드 
	 */
	public String saveFile(MultipartFile mpf
			, HttpServletRequest request
			, String storePath, String type) throws Exception {

		String orgFileName		= mpf.getOriginalFilename(); //원본 파일 이름 
		if(orgFileName==null || orgFileName=="") return null;
		String fileExt			= orgFileName.substring(orgFileName.lastIndexOf(".") +1, orgFileName.length()); //확장자
		String strFileRealNm	= createKey("inno_"); //파일 이름 생성
		String filePath 		= null;
		
//		if(type.equals("editor")) filePath = "C:\\portal\\portal\\src\\main\\webapp\\upload\\";
		if(storePath == null){
			filePath = "/upload/nanum/" + type;
		}
		else
		{
			filePath = storePath;
		}
		
		
//		String absolutePath 	= "C:\\portal\\portal\\src\\main\\webapp\\upload\\"; //파일 업로드될 절대 경로 
		String absolutePath 	= request.getSession().getServletContext().getRealPath(filePath); //파일 업로드될 절대 경로 
		
		
		String fileNm = strFileRealNm+"."+fileExt; //파일 이름 
				
		byte[] bytes 			= mpf.getBytes();
		FileOutputStream fos	= null;
		File file				= new File(filePath + "/" + fileNm);
		fos						= new FileOutputStream(file);
		fos.write(bytes);		
		fos.close();

		return filePath+fileNm;
	}	
	
	//첨부파일 구분이 2개 미만일경우
	public List<FileManageVo> parseFileInf2(HttpServletRequest request,Map<String, MultipartFile> files, String KeyStr, String atch_id, int fileKeyParam, String storePath) throws Exception {
		int fileKey = fileKeyParam;

		String storePathString = "";
		String atchFileIdString = "";
		String path = propertyService.getString("Globals.fileStorePath");
		storePathString = storePath;

		File saveFolder = new File(path + "/" + storePathString);
		
		if (!saveFolder.exists() || saveFolder.isFile()) {
		    saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<FileManageVo> result  = new ArrayList<FileManageVo>();
		FileManageVo fvo;
		String middle  = "";
		if(!atch_id.equals(null) && !atch_id.equals("")){
			middle = atch_id;
		}else{
			middle = EgovStringUtil.getTimeStamp();
		}

		while (itr.hasNext()) {
		    Entry<String, MultipartFile> entry = itr.next();
		    String input_name	= entry.getKey();
		    System.out.println("======================" + input_name);
		    file = entry.getValue();
		    String orginFileName = file.getOriginalFilename();
	
		    //--------------------------------------
		    // 원 파일명이 없는 경우 처리
		    // (첨부가 되지 않은 input file type)
		    //--------------------------------------
		    if ("".equals(orginFileName)) {
			continue;
		    }
		    ////------------------------------------

		    int index = orginFileName.lastIndexOf(".");
		    //String fileName = orginFileName.substring(0, index);
		    String fileExt = orginFileName.substring(index + 1);
		   /* String[] names = entry.getKey().split("#");*/
		    String newName = UUID.randomUUID().toString().replaceAll("-", "");//KeyStr + EgovStringUtil.getTimeStamp() + fileKey;
		    atchFileIdString = KeyStr + middle + fileKey;
		    long _size = file.getSize();

		    if (!"".equals(orginFileName)) {
			  filePath = storePathString + File.separator + newName; //파일확장자를 빼고 저장 
			  file.transferTo(new File(path + "/"+ filePath+"."+fileExt));
		    }
		    fvo = new FileManageVo();
		    fvo.setFile_extsn(fileExt);
		    fvo.setFile_stre_cours(storePathString);
		    fvo.setFile_mg(Long.toString(_size));
		    fvo.setOrignl_file_nm(orginFileName);
		    fvo.setStre_file_nm(newName);
		    fvo.setAtch_file_id(atchFileIdString);
		    fvo.setFile_sn(String.valueOf(fileKey));
            /* fvo.setAtch_file_id(newName);*/
		    //writeFile(file, newName, storePathString);
		    if(input_name.indexOf("img") >= 0){
		    	
		    	fvo.setFile_gu("I");
		    	
		    	//원본이미지파일의 경로+파일명
		    	File origin_file_name = new File(path + "/"+ filePath+"."+fileExt);
		    	//생성할 썸네일파일의 경로+썸네일파일명
		    	File thumb_file_name = new File(path + "/"+ filePath+"_thumb"+"."+fileExt);

		    	//썸네일 유동 비율 생성
		    	BufferedImage buffer_original_image = ImageIO.read(origin_file_name);
		    	
		    	int maxThumbnail = 500; //300
		    	
		    	double scale = (double)maxThumbnail/(double)buffer_original_image.getHeight();
		    	if (buffer_original_image.getWidth() > buffer_original_image.getHeight()) {
		    		scale = (double)maxThumbnail/(double)buffer_original_image.getWidth();
		    	}
		    	
		    	int maxW = (int)(scale*buffer_original_image.getWidth());
		    	int maxH = (int)(scale*buffer_original_image.getHeight());
		    	
		    	if(storePath.equals("banner")){
		    		maxW = 380;
		    		maxH = 240;
		    	}
		    	BufferedImage buffer_thumbnail_image = new BufferedImage(maxW, maxH, BufferedImage.TYPE_3BYTE_BGR);
		    	
			    
			    System.out.println("maxW : " + maxW + " maxH : " + maxH );
			    
	            Graphics2D graphic = buffer_thumbnail_image.createGraphics();
	            graphic.drawImage(buffer_original_image, 0, 0, maxW, maxH, null);
	            ImageIO.write(buffer_thumbnail_image, fileExt, thumb_file_name);
	            
		    }
		    else{
		    	
		    	fvo.setFile_gu("F");
		    }
		    result.add(fvo);
		    
		    fileKey++;
		}

		return result;
	}
	
	//첨부파일 구분이 2개 이상일경우
	public List<FileManageVo> parseFileInf3(HttpServletRequest request,Map<String, MultipartFile> files, String KeyStr, String atch_id, int fileKeyParam, String storePath) throws Exception {
		int fileKey = fileKeyParam;

		String storePathString = "";
		String atchFileIdString = "";
		String path = propertyService.getString("Globals.fileStorePath");
		storePathString = storePath;

		File saveFolder = new File(path + "/" + storePathString);
		
		if (!saveFolder.exists() || saveFolder.isFile()) {
		    saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<FileManageVo> result  = new ArrayList<FileManageVo>();
		FileManageVo fvo;
		String middle  = "";
		if(!atch_id.equals(null) && !atch_id.equals("")){
			middle = atch_id;
		}else{
			middle = EgovStringUtil.getTimeStamp();
		}

		while (itr.hasNext()) {
		    Entry<String, MultipartFile> entry = itr.next();
		    String input_name	= entry.getKey();
		    file = entry.getValue();
		    String orginFileName = file.getOriginalFilename();
	
		    //--------------------------------------
		    // 원 파일명이 없는 경우 처리
		    // (첨부가 되지 않은 input file type)
		    //--------------------------------------
		    if ("".equals(orginFileName)) {
			continue;
		    }
		    ////------------------------------------

		    int index = orginFileName.lastIndexOf(".");
		    //String fileName = orginFileName.substring(0, index);
		    String fileExt = orginFileName.substring(index + 1);
		   /* String[] names = entry.getKey().split("#");*/
		    String newName = UUID.randomUUID().toString().replaceAll("-", "");//KeyStr + EgovStringUtil.getTimeStamp() + fileKey;
		    atchFileIdString = KeyStr + middle + fileKey;
		    long _size = file.getSize();

		    if (!"".equals(orginFileName)) {
			  filePath = storePathString + File.separator + newName; //파일확장자를 빼고 저장 
			  file.transferTo(new File(path + "/"+ filePath+"."+fileExt));
		    }
		    fvo = new FileManageVo();
		    fvo.setFile_extsn(fileExt);
		    fvo.setFile_stre_cours(storePathString);
		    fvo.setFile_mg(Long.toString(_size));
		    fvo.setOrignl_file_nm(orginFileName);
		    fvo.setStre_file_nm(newName);
		    fvo.setAtch_file_id(atchFileIdString);
		    fvo.setFile_sn(String.valueOf(fileKey));
		    if(input_name.indexOf("img") >= 0){
		    	
		    	fvo.setFile_gu("I");
		    	
		    	//원본이미지파일의 경로+파일명
		    	File origin_file_name = new File(path + "/"+ filePath+"."+fileExt);
		    	//생성할 썸네일파일의 경로+썸네일파일명
		    	File thumb_file_name = new File(path + "/"+ filePath+"_thumb"+"."+fileExt);

		    	//썸네일 유동 비율 생성
		    	BufferedImage buffer_original_image = ImageIO.read(origin_file_name);
		    	
		    	int maxThumbnail = 500; //300
		    	
		    	double scale = (double)maxThumbnail/(double)buffer_original_image.getHeight();
		    	if (buffer_original_image.getWidth() > buffer_original_image.getHeight()) {
		    		scale = (double)maxThumbnail/(double)buffer_original_image.getWidth();
		    	}
		    	
		    	int maxW = (int)(scale*buffer_original_image.getWidth());
		    	int maxH = (int)(scale*buffer_original_image.getHeight());
		    	
		    	if(storePath.equals("banner")){
		    		maxW = 380;
		    		maxH = 240;
		    	}
		    	BufferedImage buffer_thumbnail_image = new BufferedImage(maxW, maxH, BufferedImage.TYPE_3BYTE_BGR);
		    	
			    
			    System.out.println("maxW : " + maxW + " maxH : " + maxH );
			    
	            Graphics2D graphic = buffer_thumbnail_image.createGraphics();
	            graphic.drawImage(buffer_original_image, 0, 0, maxW, maxH, null);
	            ImageIO.write(buffer_thumbnail_image, fileExt, thumb_file_name);
	            
		    }
		    else{
		    	
		    	fvo.setFile_gu("F");
		    }
            /* fvo.setAtch_file_id(newName);*/
		    //writeFile(file, newName, storePathString);
		    result.add(fvo);
		    
		    fileKey++;
		}

		return result;
	}
	
	public List<FileManageVo> nunFileApply(ModelMap model
											,Map<String, MultipartFile> files
											,String path) throws IOException{

		Date today 				= new Date();
		SimpleDateFormat sdf	= new SimpleDateFormat("yyyyMM");
		String day				= sdf.format(today);
		String max				= "maxThumbnail";
		String min				= "minThumbnail";
		String real				= propertyService.getString("Globals.fileStorePath");
		String uploadPath		= "/file_upload/";
		uploadPath				= uploadPath+path+"/"+day+"/";
		File uploadFolder 		= new File(real+uploadPath);
		
		if(!uploadFolder.exists() || uploadFolder.isFile()) {	// 폴더없을때 만들기
		uploadFolder.mkdirs();
		}
		
		Iterator<Entry<String, MultipartFile>> itr	= files.entrySet().iterator();
		List<FileManageVo> result  					= new ArrayList<FileManageVo>();
		MultipartFile file;
		FileManageVo fvo;
		
		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry	= itr.next();
			file 								= entry.getValue();
			String orginFileName 				= file.getOriginalFilename();
			
			if("".equals(orginFileName)) continue;
			
			int index 							= orginFileName.lastIndexOf(".");
			String ext_name						= orginFileName.substring(index + 1).toLowerCase();
			String fake_name 					= UUID.randomUUID().toString().replaceAll("-", "")+"."+ext_name;
			
			String input_name					= entry.getKey();
			long file_size 						= file.getSize();
			fvo									= new FileManageVo();
			String not_ext_name					= "java,exe,bat,class,jsp,php,asp,html,js";
			
			if(file_size >= 20971520){	//20M 용량 제한
				model.addAttribute("msg","20M 이상 등록 할 수 없습니다.");
				result	= new ArrayList<FileManageVo>();
				break;
			}
			if(not_ext_name.indexOf(ext_name) >= 0){
				model.addAttribute("msg","허용되지 않은 파일입니다.");
				result	= new ArrayList<FileManageVo>();
				break;
			}			
			try {
			
				file.transferTo(new File(real+uploadPath,fake_name));
				
				fvo.setParent_name(path);						// 부모 이름(구분)
				fvo.setInput_name(input_name);					// 파일구분(INPUT NAME)
				fvo.setReal_name(orginFileName);				// 진짜 파일 이름
				fvo.setFake_name(fake_name);					// 가짜 파일 이름
				fvo.setFile_route(uploadPath);					// 파일경로
				fvo.setExt_name(ext_name);						// 확장자 이름
				fvo.setFile_size(Float.toString(file_size));	// 파일 용량
				result.add(fvo);
			
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
			return result;
		}
	
	
	
	
	
	
	/***
	 * 왕중왕전 대회 신청자 엑셀 업로드
	 * */
	//첨부파일 구분이 2개 미만일경우
	public List<FileManageVo> fileEventContestAppTargetExcel(HttpServletRequest request,Map<String, MultipartFile> files, String KeyStr, String atch_id, int fileKeyParam, String storePath) throws Exception {
		int fileKey = fileKeyParam;

		String storePathString = "";
		String atchFileIdString = "";
		String path = propertyService.getString("Globals.fileStorePath");
		storePathString = storePath;

		File saveFolder = new File(path + "/" + storePathString);
		
		if (!saveFolder.exists() || saveFolder.isFile()) {
		    saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<FileManageVo> result  = new ArrayList<FileManageVo>();
		FileManageVo fvo;
		String middle  = "";
		if(!atch_id.equals(null) && !atch_id.equals("")){
			middle = atch_id;
		}else{
			middle = EgovStringUtil.getTimeStamp();
		}

		while (itr.hasNext()) {
		    Entry<String, MultipartFile> entry = itr.next();
		    String input_name	= entry.getKey();
		    file = entry.getValue();
		    String orginFileName = file.getOriginalFilename();
	
		    //--------------------------------------
		    // 원 파일명이 없는 경우 처리
		    // (첨부가 되지 않은 input file type)
		    //--------------------------------------
		    if ("".equals(orginFileName)) {
			continue;
		    }
		    ////------------------------------------

		    int index = orginFileName.lastIndexOf(".");
		    //String fileName = orginFileName.substring(0, index);
		    String fileExt = orginFileName.substring(index + 1);
		   /* String[] names = entry.getKey().split("#");*/
		    //String newName = UUID.randomUUID().toString().replaceAll("-", "");//KeyStr + EgovStringUtil.getTimeStamp() + fileKey;
		    String newName = orginFileName.substring(0,orginFileName.lastIndexOf(".")); 
		    atchFileIdString = KeyStr + middle + fileKey;
		    long _size = file.getSize();

		    if (!"".equals(orginFileName)) {
			  filePath = storePathString + File.separator + newName; //파일확장자를 빼고 저장 
			  file.transferTo(new File(path + "/"+ filePath+"."+fileExt));
		    }
		    fvo = new FileManageVo();
		    fvo.setFile_extsn(fileExt);
		    fvo.setFile_stre_cours(storePathString);
		    fvo.setFile_mg(Long.toString(_size));
		    fvo.setOrignl_file_nm(orginFileName);
		    fvo.setStre_file_nm(newName);
		    fvo.setAtch_file_id(atchFileIdString);
		    fvo.setFile_sn(String.valueOf(fileKey));
		    fvo.setFile_gu("F");
		    result.add(fvo);
		    
		    fileKey++;
		}

  		return result;
	}
		

}
