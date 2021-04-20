package egovframework.client.network.service;

import java.util.List;

public interface NetworkManageService {

	//강사정보 리스트
	public List<NetworkManageVo> selectTeacherList(NetworkManageVo vo) throws Exception;

	//강사정보 리스트 카운트
	public int selectTeacherListCnt(NetworkManageVo vo) throws Exception;

	//강사정보 상세
	public NetworkManageVo getTeacherView(NetworkManageVo vo) throws Exception;

	//자격증 리스트
	public List<NetworkManageVo> getcLicenseList(NetworkManageVo vo) throws Exception;

	//경력 리스트
	public List<NetworkManageVo> getcCareerList(NetworkManageVo vo) throws Exception;

	//이력 리스트
	public List<NetworkManageVo> getchistoryList(NetworkManageVo vo) throws Exception;

	//동아리 리스트
	public List<NetworkManageVo> selectClubList(NetworkManageVo vo) throws Exception;

	//동아리 리스트 카운트
	public int selectClubListCnt(NetworkManageVo vo) throws Exception;

	//동아리 등록
	public int insertClub(NetworkManageVo vo) throws Exception;

	//동아리 상세
	public NetworkManageVo getClubView(NetworkManageVo vo) throws Exception;

	//관내 학습기관 리스트
	public List<NetworkManageVo> selectInsList(NetworkManageVo vo) throws Exception;

	//관내 학습기관 리스트 카운트
	public int selectInsListCnt(NetworkManageVo vo) throws Exception;

	//강사 등록
	public int insertTeacher(NetworkManageVo vo) throws Exception;

	//자격증 등록
	public int insertLicense(NetworkManageVo vo) throws Exception;

	//경력 등록
	public int insertCareer(NetworkManageVo vo) throws Exception;

	//이력 등록
	public int insertHistory(NetworkManageVo vo) throws Exception;

}
