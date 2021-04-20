package egovframework.client.lb.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.common.GpAbstractDAO;
import egovframework.common.GpAbstractDAO_lb;
import egovframework.common.service.FileManageVo;

@Repository("LbFileManageDAO")
public class LbFileManageDAO extends GpAbstractDAO_lb {

    Logger log = Logger.getLogger(this.getClass());

    /**
     * 파일에 대한 목록을 조회한다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<FileManageVo> selectFileList(FileManageVo vo) throws Exception {
	return list("fileManageDAO.selectFileList", vo);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public FileManageVo selectFileDetail(FileManageVo fvo) throws Exception {
	return (FileManageVo)selectByPk("fileManageDAO.selectFileDetail", fvo);
    }
    
    /**
     * 파일에 대한 목록을 조회한다.(NewBoard)
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<FileManageVo> selectFileListNewBoard(FileManageVo vo) throws Exception {
    	return list("fileManageDAO.selectFileListNewBoard", vo);
    }
    
    /**
     * 여러 개의 파일에 대한 정보(마스터)를 등록한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
	public void insertFileMaster(FileManageVo fvo) throws Exception {
	    insert("insertFileMaster", fvo);
    }
	
	/**
     * 여러 개의 파일에 대한 정보(디테일)를 등록한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
	public void insertFileDetail(FileManageVo fvo) throws Exception {
	    insert("insertFileDetail", fvo);
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @param fileList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String insertFileInfs(List fileList) throws Exception {
    	FileManageVo vo = (FileManageVo)fileList.get(0);
	String atchFileId = vo.getAtch_file_id();
	
	insert("insertFileMaster", vo);

	Iterator iter = fileList.iterator();
	while (iter.hasNext()) {
	    vo = (FileManageVo)iter.next();
	    
	    insert("insertFileDetail", vo);
		
	}
	
	return atchFileId;
    }
    
    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @param fileList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String insertFileInfs2(List fileList) throws Exception {
    	FileManageVo vo = (FileManageVo)fileList.get(0);
	String atchFileId = vo.getAtch_file_id();
	
	

	Iterator iter = fileList.iterator();
	while (iter.hasNext()) {
	    vo = (FileManageVo)iter.next();
	    insert("insertFileMaster", vo);
	    
	    insert("insertFileDetail", vo);
		
	}
	
	return atchFileId;
    }
    
    /**
     * 파일 구분자에 대한 최대값을 구한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public int getMaxFileSN(FileManageVo fvo) throws Exception {
	return (Integer)getSqlMapClientTemplate().queryForObject("getMaxFileSN", fvo);
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     * 
     * @param fileList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void updateFileInfs(List fileList) throws Exception {
    	FileManageVo vo;
	Iterator iter = fileList.iterator();
	while (iter.hasNext()) {
	    vo = (FileManageVo)iter.next();
	    
	    insert("insertFileDetail", vo);
	}
    }

    /**
     * 하나의 파일을 삭제한다.
     * 
     * @param fvo
     * @throws Exception
     */
    public void deleteFileInf(FileManageVo fvo) throws Exception {
    	delete("deleteFileDetail", fvo);
    }
    
    @SuppressWarnings("unchecked")
    public int newInsertFileInfsClass(List fileList, String seq) throws Exception {
    	FileManageVo vo = null;
	int cnt = 0;
	Iterator iter = fileList.iterator();
	while (iter.hasNext()) {
		vo = (FileManageVo)iter.next();
	    vo.setSeq(seq);
	    insert("insertFileMasterClass", vo);
	    cnt =+ Integer.parseInt((String)getSqlMapClientTemplate().insert("newInsertFileDetailClass", vo));
		
	}
	return cnt;
    }
    
    
    
    // nun 파일 목록
    public List<FileManageVo> getNunFileAttachList(FileManageVo fvo)throws Exception {
    	return list("getFileAttachList", fvo);    	
    }
    
    // nun 파일 상세
    public FileManageVo getNunFileAttachView(FileManageVo fvo)throws Exception {
    	return (FileManageVo)selectByPk("getFileAttachView", fvo);    	
    }
    
    // nun 파일 저장
    @SuppressWarnings("unchecked")
    public int setNunFileApply(List fileList,int parent_seq) throws Exception {
    	
    	int resultInt		= 0;
    	if (fileList.size() > 0) {
    		FileManageVo vo 	= new FileManageVo();
    		Iterator iter		= fileList.iterator();
    		
	    	getSqlMapClientTemplate().getSqlMapClient().startBatch() ;
	    	
	    	while(iter.hasNext()) {
	    		vo	= (FileManageVo)iter.next();
	    		vo.setParent_seq(parent_seq);
	    		getSqlMapClientTemplate().getSqlMapClient().insert("setFileAttachInsert", vo);
	    		resultInt++;
	    	}
	    	getSqlMapClientTemplate().getSqlMapClient().executeBatch(); 
    	}
    	return resultInt;
    }
    
    // nun 파일 삭제
    public int setNunFileAttachDelete(FileManageVo fvo) throws Exception {
    	return delete("setFileAttachDelete", fvo);
    }

    //원하는 파일 리스트 가져오기
	public List<FileManageVo> getNunFileAttachViewList(FileManageVo fileVO) {
		return list("getNunFileAttachViewList", fileVO);  
	}

	// 리스트 삭제 후 첨부파일 정보 삭제
	public int setNunFileAttachDelete2(FileManageVo fileVO) {
    	return delete("setNunFileAttachDelete2", fileVO);
	}

	//첨부파일 리스트
	public List<FileManageVo> getFileAttachList(FileManageVo fvo) {
		// TO-DO, 
		List<FileManageVo> list	= list("getFileAttachList", fvo);
		
		return list;
	}

	//첨부파일 상세
	public FileManageVo getFileAttachView(FileManageVo fileVO) {
		return (FileManageVo)selectByPk("getFileAttachView", fileVO);  
	}

	//첨부파일 삭제
	public int setFileAttachDelete(FileManageVo fileVO) {
		return delete("setFileAttachDelete", fileVO);
	}

	//상위 파일테이블 데이터 삭제
	public int setFileAttachDelete2(FileManageVo fileVO) {
		return delete("setFileAttachDelete2", fileVO);
	}

	public List<FileManageVo> lb_getFileAttachList(FileManageVo fvo) {
		// TODO Auto-generated method stub
		return list("lb_getFileAttachList", fvo);
	}    
    
}
