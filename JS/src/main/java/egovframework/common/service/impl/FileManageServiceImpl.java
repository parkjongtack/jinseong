package egovframework.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import egovframework.client.lb.service.impl.LbFileManageDAO;
import egovframework.common.service.FileManageService;
import egovframework.common.service.FileManageVo;

@Service("FileManageService")
public class FileManageServiceImpl implements FileManageService {

    @Resource(name = "fileManageDAO")
    private FileManageDAO fileManageDAO;

    @Resource(name = "LbFileManageDAO")
    private LbFileManageDAO LbFileManageDAO;
    
    
    
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 파일에 대한 목록을 조회한다.
     * 
     */
    public List<FileManageVo> selectFileList(FileManageVo fvo) throws Exception {
	return fileManageDAO.selectFileList(fvo);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     * 
     */
    public FileManageVo selectFileDetail(FileManageVo fvo) throws Exception {
	return fileManageDAO.selectFileDetail(fvo);
    }
    
    /**
     * 파일에 대한 목록을 조회한다.(NewBoard)
     * 
     */
    public List<FileManageVo> selectFileListNewBoard(FileManageVo fvo) throws Exception {
    	return fileManageDAO.selectFileListNewBoard(fvo);
    }
    
    /**
     * 파일에 대한 정보(마스터)를 등록한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public void insertFileMaster(FileManageVo fvo) throws Exception {
		fileManageDAO.insertFileMaster(fvo);
    }
    
    /**
     * 파일에 대한 정보(디테일)를 등록한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public void insertFileDetail(FileManageVo fvo) throws Exception {
		fileManageDAO.insertFileDetail(fvo);
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     */
    @SuppressWarnings("unchecked")
    public String insertFileInfs(List fvoList) throws Exception {
	String atchFileId = "";
	
	if (fvoList.size() != 0) {
	    atchFileId = fileManageDAO.insertFileInfs(fvoList);
	}
	
	return atchFileId;
    }
    
    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     */
    @SuppressWarnings("unchecked")
    public String insertFileInfs2(List fvoList) throws Exception {
	String atchFileId = "";
	
	if (fvoList.size() != 0) {
	    atchFileId = fileManageDAO.insertFileInfs2(fvoList);
	}
	
	return atchFileId;
    }

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#getMaxFileSN(egovframework.com.cmm.service.FileVO)
     */
    public int getMaxFileSN(FileManageVo fvo) throws Exception {
	return fileManageDAO.getMaxFileSN(fvo);
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#updateFileInfs(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public void updateFileInfs(List fvoList) throws Exception {
	//Delete & Insert
    	fileManageDAO.updateFileInfs(fvoList);
    }

    /**
     * 하나의 파일을 삭제한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#deleteFileInf(egovframework.com.cmm.service.FileVO)
     */
    public void deleteFileInf(FileManageVo fvo) throws Exception {
    	fileManageDAO.deleteFileInf(fvo);
    }

	
	public int newInsertFileInfsClass(List fileList, String seq)
			throws Exception {
		// TODO Auto-generated method stub
		return fileManageDAO.newInsertFileInfsClass(fileList, seq);
	}

    // nun 파일 목록
    @SuppressWarnings("unchecked")
    public List<FileManageVo> getNunFileAttachList(FileManageVo fvo) throws Exception {
    	return fileManageDAO.getNunFileAttachList(fvo);
    }
    // nun 파일 상세
    @SuppressWarnings("unchecked")
    public FileManageVo getNunFileAttachView(FileManageVo fvo) throws Exception {
    	return fileManageDAO.getNunFileAttachView(fvo);
    }
    // nun 파일 저장
    @SuppressWarnings("unchecked")
    public int setNunFileApply(List fvoList,int parent_seq) throws Exception {
    	return fileManageDAO.setNunFileApply(fvoList,parent_seq);
    }
    // nun 파일 삭제
    @SuppressWarnings("unchecked")
    public int setNunFileAttachDelete(FileManageVo fvo) throws Exception {
    	return fileManageDAO.setNunFileAttachDelete(fvo);
    }

	//원하는 파일 리스트 가져오기
	@SuppressWarnings("unchecked")
	public List<FileManageVo> getNunFileAttachViewList(FileManageVo fileVO) throws Exception {
		return fileManageDAO.getNunFileAttachViewList(fileVO);
	}

	// 리스트 삭제 후 첨부파일 정보 삭제
	@SuppressWarnings("unchecked")
	public int setNunFileAttachDelete2(FileManageVo fileVO) throws Exception {
    	return fileManageDAO.setNunFileAttachDelete2(fileVO);
	}

	//첨부파일 리스트 
	@Override
	public List<FileManageVo> getFileAttachList(FileManageVo fvo) throws Exception {
		return fileManageDAO.getFileAttachList(fvo);
	}

	//첨부파일 상세
	@Override
	public FileManageVo getFileAttachView(FileManageVo fileVO) throws Exception {
		return fileManageDAO.getFileAttachView(fileVO);
	}

	//첨부파일 삭제
	@Override
	public int setFileAttachDelete(FileManageVo fileVO) throws Exception {
		// TODO Auto-generated method stub
		return fileManageDAO.setFileAttachDelete(fileVO);
	}

	//상위 파일테이블 데이터 삭제
	@Override
	public int setFileAttachDelete2(FileManageVo fileVO) throws Exception {
		// TODO Auto-generated method stub
		return fileManageDAO.setFileAttachDelete2(fileVO);
	}

	/**
	 * 로드밸런싱용
	 * */
	@Override
	public List<FileManageVo> lb_getFileAttachList(FileManageVo fvo) throws Exception {
		// TODO Auto-generated method stub
		return LbFileManageDAO.lb_getFileAttachList(fvo);
	}
	
}
