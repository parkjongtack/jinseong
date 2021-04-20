package egovframework.common.service;

/**
 * @Class Name : FileDownMngVO.java
 * @Description : 파일정보 처리를 위한 VO 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2014.01.10                  최초생성
 *
 * @since 2014.01.10
 * @version
 * @see
 *
 */
public class FileManageVo {

    /**
     * 첨부파일 아이디
     */
    public String atch_file_id = "";
    /**
     * 생성일자
     */
    public String creat_dt = "";
    /**
     * 파일내용
     */
    public String file_cn = "";
    /**
     * 파일확장자
     */
    public String file_extsn = "";
    /**
     * 파일크기
     */
    public String file_mg = "";
    /**
     * 파일연번
     */
    public String file_sn = "";
    /**
     * 파일저장경로
     */
    public String file_stre_cours = "";
    /**
     * 원파일명
     */
    public String orignl_file_nm = "";
    /**
     * 저장파일명
     */
    public String stre_file_nm = "";
    /**
     * 파일구분
     */
    public String file_gu = "";
    
    
    public String returnUrl = "";
    
    public String seq = "";
    
    // nun add
    public int file_attach_seq;	// 고유번호
    public int parent_seq;		// 부모 고유 번호
    public String parent_name;	// 부모 이름(구분)
    public String input_name;	// 파일구분(INPUT NAME)
    public String real_name;	// 진짜 파일 이름
    public String fake_name;	// 가짜 파일 이름
    public String file_route;	// 파일경로
    public String file_route_full;	// 파일경로
    public String ext_name;		// 확장자 이름
    public String file_size;	// 파일 용량
    public String reg_dt;		// 등록일
    public String sum;			//썸네일
    
    
	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getFile_route_full() {
		return file_route_full;
	}

	public void setFile_route_full(String file_route_full) {
		this.file_route_full = file_route_full;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public int getFile_attach_seq() {
		return file_attach_seq;
	}

	public void setFile_attach_seq(int file_attach_seq) {
		this.file_attach_seq = file_attach_seq;
	}

	public int getParent_seq() {
		return parent_seq;
	}

	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}

	public String getInput_name() {
		return input_name;
	}

	public void setInput_name(String input_name) {
		this.input_name = input_name;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getFake_name() {
		return fake_name;
	}

	public void setFake_name(String fake_name) {
		this.fake_name = fake_name;
	}

	public String getFile_route() {
		return file_route;
	}

	public void setFile_route(String file_route) {
		this.file_route = file_route;
	}

	public String getExt_name() {
		return ext_name;
	}

	public void setExt_name(String ext_name) {
		this.ext_name = ext_name;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}

	public String getAtch_file_id() {
		return atch_file_id;
	}

	public void setAtch_file_id(String atch_file_id) {
		this.atch_file_id = atch_file_id;
	}

	public String getCreat_dt() {
		return creat_dt;
	}

	public void setCreat_dt(String creat_dt) {
		this.creat_dt = creat_dt;
	}

	public String getFile_cn() {
		return file_cn;
	}

	public void setFile_cn(String file_cn) {
		this.file_cn = file_cn;
	}

	public String getFile_extsn() {
		return file_extsn;
	}

	public void setFile_extsn(String file_extsn) {
		this.file_extsn = file_extsn;
	}

	public String getFile_mg() {
		return file_mg;
	}

	public void setFile_mg(String file_mg) {
		this.file_mg = file_mg;
	}

	public String getFile_sn() {
		return file_sn;
	}

	public void setFile_sn(String file_sn) {
		this.file_sn = file_sn;
	}

	public String getFile_stre_cours() {
		return file_stre_cours;
	}

	public void setFile_stre_cours(String file_stre_cours) {
		this.file_stre_cours = file_stre_cours;
	}

	public String getOrignl_file_nm() {
		return orignl_file_nm;
	}

	public void setOrignl_file_nm(String orignl_file_nm) {
		this.orignl_file_nm = orignl_file_nm;
	}

	public String getStre_file_nm() {
		return stre_file_nm;
	}

	public void setStre_file_nm(String stre_file_nm) {
		this.stre_file_nm = stre_file_nm;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getFile_gu() {
		return file_gu;
	}

	public void setFile_gu(String file_gu) {
		this.file_gu = file_gu;
	}

    
}
