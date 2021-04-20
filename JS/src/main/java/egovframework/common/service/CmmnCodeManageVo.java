package egovframework.common.service;

import org.springframework.web.multipart.MultipartFile;


public class CmmnCodeManageVo {
	
	private int com_code_seq;
	private String group_cd;
	private String group_nm;
	private String code;
	private String code_nm;
	
	private String code_dc;               // 코드설명
	private String use_at;                // 사용여부
	private String code_id;               // 코드ID
	private String etc1;
	private String etc2;
	private String etc3;
	private String frst_register_pnttm;   // 최초등록시점
	private String frst_register_id;      // 최초등록자ID
	private String last_updusr_pnttm;     // 최종수정시점
	private String last_updusr_id;        // 최종수정자ID
	private String lv;
	private String parent_code_nm;
	private String parent_code;
	private String code_path;
	private String cl_code;
	private String code_id_nm; 
	private String forth_code_id;
	private String forth_code_nm;
	private String forth_code_dc;
	
	private MultipartFile Filedata;
	
	public MultipartFile getFiledata() {
		return Filedata;
	}
	public void setFiledata(MultipartFile filedata) {
		Filedata = filedata;
	}
	
	public int getCom_code_seq() {
		return com_code_seq;
	}
	public void setCom_code_seq(int com_code_seq) {
		this.com_code_seq = com_code_seq;
	}
	public String getGroup_cd() {
		return group_cd;
	}
	public void setGroup_cd(String group_cd) {
		this.group_cd = group_cd;
	}
	public String getGroup_nm() {
		return group_nm;
	}
	public void setGroup_nm(String group_nm) {
		this.group_nm = group_nm;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode_nm() {
		return code_nm;
	}
	public void setCode_nm(String code_nm) {
		this.code_nm = code_nm;
	}
	public String getCode_dc() {
		return code_dc;
	}
	public void setCode_dc(String code_dc) {
		this.code_dc = code_dc;
	}
	public String getUse_at() {
		return use_at;
	}
	public void setUse_at(String use_at) {
		this.use_at = use_at;
	}
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public String getEtc1() {
		return etc1;
	}
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}
	public String getEtc2() {
		return etc2;
	}
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}
	public String getEtc3() {
		return etc3;
	}
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}
	public String getFrst_register_pnttm() {
		return frst_register_pnttm;
	}
	public void setFrst_register_pnttm(String frst_register_pnttm) {
		this.frst_register_pnttm = frst_register_pnttm;
	}
	public String getFrst_register_id() {
		return frst_register_id;
	}
	public void setFrst_register_id(String frst_register_id) {
		this.frst_register_id = frst_register_id;
	}
	public String getLast_updusr_pnttm() {
		return last_updusr_pnttm;
	}
	public void setLast_updusr_pnttm(String last_updusr_pnttm) {
		this.last_updusr_pnttm = last_updusr_pnttm;
	}
	public String getLast_updusr_id() {
		return last_updusr_id;
	}
	public void setLast_updusr_id(String last_updusr_id) {
		this.last_updusr_id = last_updusr_id;
	}
	public String getLv() {
		return lv;
	}
	public void setLv(String lv) {
		this.lv = lv;
	}
	public String getParent_code_nm() {
		return parent_code_nm;
	}
	public void setParent_code_nm(String parent_code_nm) {
		this.parent_code_nm = parent_code_nm;
	}
	public String getParent_code() {
		return parent_code;
	}
	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}
	public String getCode_path() {
		return code_path;
	}
	public void setCode_path(String code_path) {
		this.code_path = code_path;
	}
	public String getCl_code() {
		return cl_code;
	}
	public void setCl_code(String cl_code) {
		this.cl_code = cl_code;
	}
	public String getCode_id_nm() {
		return code_id_nm;
	}
	public void setCode_id_nm(String code_id_nm) {
		this.code_id_nm = code_id_nm;
	}
	public String getForth_code_id() {
		return forth_code_id;
	}
	public void setForth_code_id(String forth_code_id) {
		this.forth_code_id = forth_code_id;
	}
	public String getForth_code_nm() {
		return forth_code_nm;
	}
	public void setForth_code_nm(String forth_code_nm) {
		this.forth_code_nm = forth_code_nm;
	}
	public String getForth_code_dc() {
		return forth_code_dc;
	}
	public void setForth_code_dc(String forth_code_dc) {
		this.forth_code_dc = forth_code_dc;
	}
	
	
	
}
