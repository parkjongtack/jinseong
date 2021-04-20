package egovframework.common.service;

import java.util.List;

/**
 * @Class Name : FileDownMngService.java
 * @Description : 파일정보의 관리를 위한 서비스 인터페이스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2014.01.10         최초생성
 *
 * @since 2014.01.10
 * @version
 * @see
 *
 */
public interface FileManageService {

    /**
     * 파일에 대한 목록을 조회한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public List<FileManageVo> selectFileList(FileManageVo fvo) throws Exception;

    /**
     * 파일에 대한 상세정보를 조회한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public FileManageVo selectFileDetail(FileManageVo fvo) throws Exception;
    
    /**
     * 파일에 대한 목록을 조회한다.(NewBoard)
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public List<FileManageVo> selectFileListNewBoard(FileManageVo fvo) throws Exception;
    
    /**
     * 파일에 대한 정보(마스터)를 등록한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public void insertFileMaster(FileManageVo fvo) throws Exception;
    
    /**
     * 파일에 대한 정보(디테일)를 등록한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public void insertFileDetail(FileManageVo fvo) throws Exception;

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @param fvoList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String insertFileInfs(List fvoList) throws Exception;
    
    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @param fvoList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String insertFileInfs2(List fvoList) throws Exception;
    
    
    /**
     * 파일 구분자에 대한 최대값을 구한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public int getMaxFileSN(FileManageVo fvo) throws Exception;

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     * 
     * @param fvoList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void updateFileInfs(List fvoList) throws Exception;

    /**
     * 하나의 파일을 삭제한다.
     * 
     * @param fvo
     * @throws Exception
     */
    public void deleteFileInf(FileManageVo fvo) throws Exception;
   
 
    public int newInsertFileInfsClass(List fileList, String seq) throws Exception;
    
    
    // nun 파일 목록
    public List<FileManageVo> getNunFileAttachList(FileManageVo fvo) throws Exception;
    
    // nun 파일 목록
    public FileManageVo getNunFileAttachView(FileManageVo fvo) throws Exception;
    
    // nun 파일 저장
    public int setNunFileApply(List fvoList,int parent_seq) throws Exception;
    
    // nun 파일 삭제
    public int setNunFileAttachDelete(FileManageVo fvo) throws Exception;

    // 원하는 파일 리스트 불러오기
	public List<FileManageVo> getNunFileAttachViewList(FileManageVo fileVO) throws Exception;

	// 리스트 삭제 후 첨부파일 정보 삭제
	public int setNunFileAttachDelete2(FileManageVo fileVO) throws Exception;

	//첨부파일 리스트 
	public List<FileManageVo> getFileAttachList(FileManageVo fvo) throws Exception;

	//첨부파일 상세
	public FileManageVo getFileAttachView(FileManageVo fileVO) throws Exception;

	//첨부파일 삭제
	public int setFileAttachDelete(FileManageVo fileVO) throws Exception;

	//상위 파일테이블 데이터 삭제
	public int setFileAttachDelete2(FileManageVo fileVO) throws Exception;
	
	/**
	 * 로드밸런싱용
	 * */
	//첨부파일 리스트 
	public List<FileManageVo> lb_getFileAttachList(FileManageVo fvo) throws Exception;
}
