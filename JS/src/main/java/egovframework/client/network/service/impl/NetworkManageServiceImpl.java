package egovframework.client.network.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.apage.member.service.apageMemberManageVo;
import egovframework.client.network.service.NetworkManageService;
import egovframework.client.network.service.NetworkManageVo;
import egovframework.client.network.service.impl.NetworkManageDAO;


@Repository("NetworkManageService")
public class NetworkManageServiceImpl implements NetworkManageService {
	
	private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="NetworkManageDAO")
	NetworkManageDAO NetworkManageDAO;

	//강사정보 리스트
	@Override
	public List<NetworkManageVo> selectTeacherList(NetworkManageVo vo) throws Exception {
		List<NetworkManageVo> list = NetworkManageDAO.selectTeacherList(vo);
		return list;
	}

	//강사정보 리스트 카운트
	@Override
	public int selectTeacherListCnt(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.selectTeacherListCnt(vo);
	}

	//강사정보 상세
	@Override
	public NetworkManageVo getTeacherView(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.getTeacherView(vo);
	}

	//자격증 리스트
	@Override
	public List<NetworkManageVo> getcLicenseList(NetworkManageVo vo) throws Exception {
		List<NetworkManageVo> list = NetworkManageDAO.getcLicenseList(vo);
		return list;
	}

	//경력 리스트
	@Override
	public List<NetworkManageVo> getcCareerList(NetworkManageVo vo) throws Exception {
		List<NetworkManageVo> list = NetworkManageDAO.getcCareerList(vo);
		return list;
	}

	//이력 리스트
	@Override
	public List<NetworkManageVo> getchistoryList(NetworkManageVo vo) throws Exception {
		List<NetworkManageVo> list = NetworkManageDAO.getchistoryList(vo);
		return list;
	}

	//동아리 리스트
	@Override
	public List<NetworkManageVo> selectClubList(NetworkManageVo vo) throws Exception {
		List<NetworkManageVo> list = NetworkManageDAO.selectClubList(vo);
		return list;
	}

	//동아리 리스트 카운트
	@Override
	public int selectClubListCnt(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.selectClubListCnt(vo);
	}

	//동아리 등록
	@Override
	public int insertClub(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.insertClub(vo);
	}

	//동아리 상세
	@Override
	public NetworkManageVo getClubView(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.getClubView(vo);
	}
	
	//관내 학습기관 리스트
	@Override
	public List<NetworkManageVo> selectInsList(NetworkManageVo vo) throws Exception {
		List<NetworkManageVo> list = NetworkManageDAO.selectInsList(vo);
		return list;
	}

	//관내 학습기관 리스트 카운트
	@Override
	public int selectInsListCnt(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.selectInsListCnt(vo);
	}

	//강사 등록
	@Override
	public int insertTeacher(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.insertTeacher(vo);
	}

	//자격증 등록
	@Override
	public int insertLicense(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.insertLicense(vo);
	}

	//경력 등록
	@Override
	public int insertCareer(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.insertCareer(vo);
	}

	//이력 등록
	@Override
	public int insertHistory(NetworkManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return NetworkManageDAO.insertHistory(vo);
	}

}
