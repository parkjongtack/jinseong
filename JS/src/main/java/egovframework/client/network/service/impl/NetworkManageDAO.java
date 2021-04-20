package egovframework.client.network.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.client.network.service.NetworkManageVo;
import egovframework.common.GpAbstractDAO;

@Repository("NetworkManageDAO")
public class NetworkManageDAO extends GpAbstractDAO {
	
	private Logger logger	= Logger.getLogger(this.getClass());

	//강사정보 리스트
	public List<NetworkManageVo> selectTeacherList(NetworkManageVo vo) {
		// TO-DO, 
		List<NetworkManageVo> list	= list("selectTeacherList", vo);
		
		return list;
	}

	//강사정보 리스트 카운트
	public int selectTeacherListCnt(NetworkManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectTeacherListCnt", vo);		
		return list;
	}

	//강사정보 상세
	public NetworkManageVo getTeacherView(NetworkManageVo vo) {
		// TODO Auto-generated method stub
		return (NetworkManageVo)selectByPk("getTeacherView", vo);
	}

	//자격증 리스트
	public List<NetworkManageVo> getcLicenseList(NetworkManageVo vo) {
		// TO-DO, 
		List<NetworkManageVo> list	= list("getcLicenseList", vo);
		
		return list;
	}

	//경력 리스트
	public List<NetworkManageVo> getcCareerList(NetworkManageVo vo) {
		// TO-DO, 
		List<NetworkManageVo> list	= list("getcCareerList", vo);
		
		return list;
	}

	//이력 리스트
	public List<NetworkManageVo> getchistoryList(NetworkManageVo vo) {
		// TO-DO, 
		List<NetworkManageVo> list	= list("getchistoryList", vo);
		
		return list;
	}

	//동아리 리스트
	public List<NetworkManageVo> selectClubList(NetworkManageVo vo) {
		// TO-DO, 
		List<NetworkManageVo> list	= list("selectClubList", vo);
		
		return list;
	}
	
	//동아리 리스트 카운트
	public int selectClubListCnt(NetworkManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectClubListCnt", vo);		
		return list;
	}

	//동아리 등록
	public int insertClub(NetworkManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertClub", vo);
	}

	//동아리 상세
	public NetworkManageVo getClubView(NetworkManageVo vo) {
		// TODO Auto-generated method stub
		return (NetworkManageVo)selectByPk("getClubView", vo);
	}

	//관내 학습기관 리스트
	public List<NetworkManageVo> selectInsList(NetworkManageVo vo) {
		// TO-DO, 
		List<NetworkManageVo> list	= list("selectInsList", vo);
		
		return list;
	}

	//관내 학습기관 리스트 카운트
	public int selectInsListCnt(NetworkManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectInsListCnt", vo);		
		return list;
	}

	//강사 등록
	public int insertTeacher(NetworkManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertTeacher", vo);
	}

	//자격증 등록
	public int insertLicense(NetworkManageVo vo) throws Exception {
		int resultInt	= 0;
		int arrtLength	= 0;
		if(vo.getLicense_arr() != null && vo.getLicense_arr().length > 0){
			arrtLength	= vo.getLicense_arr().length;
			getSqlMapClientTemplate().getSqlMapClient().startBatch() ;
			for(int i = 0; i < vo.getLicense_arr().length; i++){
				
				vo.setSubject(vo.getLicense_arr()[i]);
				
				getSqlMapClientTemplate().getSqlMapClient().insert("insertLicense", vo);
				resultInt++;
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();  
		}
		return resultInt;
	}

	//경력 등록
	public int insertCareer(NetworkManageVo vo) throws Exception {
		int resultInt	= 0;
		int arrtLength	= 0;
		if(vo.getCareer_arr() != null && vo.getCareer_arr().length > 0){
			arrtLength	= vo.getCareer_arr().length;
			getSqlMapClientTemplate().getSqlMapClient().startBatch() ;
			for(int i = 0; i < vo.getCareer_arr().length; i++){
				
				vo.setSubject(vo.getCareer_arr()[i]);
				
				getSqlMapClientTemplate().getSqlMapClient().insert("insertCareer", vo);
				resultInt++;
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();  
		}
		return resultInt;
	}

	//이력 등록
	public int insertHistory(NetworkManageVo vo) throws Exception {
		int resultInt	= 0;
		int arrtLength	= 0;
		if(vo.getHistory_arr() != null && vo.getHistory_arr().length > 0){
			arrtLength	= vo.getHistory_arr().length;
			getSqlMapClientTemplate().getSqlMapClient().startBatch() ;
			for(int i = 0; i < vo.getHistory_arr().length; i++){
				
				vo.setSubject(vo.getHistory_arr()[i]);
				
				getSqlMapClientTemplate().getSqlMapClient().insert("insertHistory", vo);
				resultInt++;
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();  
		}
		return resultInt;
	}

}
