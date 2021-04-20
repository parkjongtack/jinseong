package egovframework.client.network.service;

public class NetworkManageVo {
	
	//관내 학습기관
	private int seq;
	private String ins_type;
	private String ins_type_nm;
	private String ins_name;
	private String ins_number;
	private String zipcode;
	private String addr1;
	private String addr2;
	private String addr_type;
	private String addr_type_nm;
	private String ins_url;
	private String reg_nm;
	private String reg_dt;
	private String up_nm;
	private String up_dt;
	private String check_yn;
	
	//동아리
	private String club_nm;
	private String leader_nm;
	private String found_dt;
	private String member_cnt;
	private String club_number;
	private String club_email;
	private String club_place;
	private String club_cycle;
	private String club_url;
	private String club_intro;
	private String club_act;
	
	
	//강사정보관리
	private String han_nm;
	private String ch_nm;
	private String us_nm;
	private String phone1;
	private String phone2;
	private String email;
	private String ability_type;
	private String ability_type_nm;
	private String affi_nm;
	private String study;
	private String subject;
	private String license;
	private String career;
	private String history;
	private String str;
	private String use_yn;
	private String atch_file_id;
	
	private String email1;
	private String email2;
	
	// 강사테이블의 하위테이블
	private int teacher_seq;
	private String[] license_arr;
	private String[] career_arr;
	private String[] history_arr;
	
	
	private String atch_file_id2;
	
	private int ascnum;
	private int rownum;
	private String jobFlag;
	
	//첨부파일 조인
	private String parent_name;
	private String input_name;
	private int file_attach_seq;
	
	//코드
	private int com_code_sq;
	private String group_cd;
	private String group_nm;
	private String code;
	private String code_nm;
	private String code_use_yn;
	private String ad_use_yn;
	
	//검색
	private String srch_input;
	private String scType;
	private String scType2;
	private String scType3;
	private String srch_date;
	private String srch_date2;
	
	
	//페이징
    private int page_index = 1;             // 현재페이지
    private int page_unit = 5;             // 페이지갯수
    private int page_size = 10;             // 페이지사이즈
    private int first_index = 1;            // 첫페이지 인덱스
    private int last_index = 1;             // 마지막페이지 인덱스
    private int recordcountperpage = 5;    // 페이지당 레코드 개수
    private int rowno = 0;                  // 레코드 번호
	private int currRow;
	private int endRow;
	private String strPaging;
	private int currRow2;
	private int currPage;
	private int endPage;
	private int prevPage;
	private int nextPage;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getIns_type() {
		return ins_type;
	}
	public void setIns_type(String ins_type) {
		this.ins_type = ins_type;
	}
	public String getIns_type_nm() {
		return ins_type_nm;
	}
	public void setIns_type_nm(String ins_type_nm) {
		this.ins_type_nm = ins_type_nm;
	}
	public String getIns_name() {
		return ins_name;
	}
	public void setIns_name(String ins_name) {
		this.ins_name = ins_name;
	}
	public String getIns_number() {
		return ins_number;
	}
	public void setIns_number(String ins_number) {
		this.ins_number = ins_number;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr_type() {
		return addr_type;
	}
	public void setAddr_type(String addr_type) {
		this.addr_type = addr_type;
	}
	public String getAddr_type_nm() {
		return addr_type_nm;
	}
	public void setAddr_type_nm(String addr_type_nm) {
		this.addr_type_nm = addr_type_nm;
	}
	public String getIns_url() {
		return ins_url;
	}
	public void setIns_url(String ins_url) {
		this.ins_url = ins_url;
	}
	public String getReg_nm() {
		return reg_nm;
	}
	public void setReg_nm(String reg_nm) {
		this.reg_nm = reg_nm;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getUp_nm() {
		return up_nm;
	}
	public void setUp_nm(String up_nm) {
		this.up_nm = up_nm;
	}
	public String getUp_dt() {
		return up_dt;
	}
	public void setUp_dt(String up_dt) {
		this.up_dt = up_dt;
	}
	public String getClub_nm() {
		return club_nm;
	}
	public void setClub_nm(String club_nm) {
		this.club_nm = club_nm;
	}
	public String getLeader_nm() {
		return leader_nm;
	}
	public void setLeader_nm(String leader_nm) {
		this.leader_nm = leader_nm;
	}
	public String getFound_dt() {
		return found_dt;
	}
	public void setFound_dt(String found_dt) {
		this.found_dt = found_dt;
	}
	public String getMember_cnt() {
		return member_cnt;
	}
	public void setMember_cnt(String member_cnt) {
		this.member_cnt = member_cnt;
	}
	public String getClub_number() {
		return club_number;
	}
	public void setClub_number(String club_number) {
		this.club_number = club_number;
	}
	public String getClub_email() {
		return club_email;
	}
	public void setClub_email(String club_email) {
		this.club_email = club_email;
	}
	public String getClub_place() {
		return club_place;
	}
	public void setClub_place(String club_place) {
		this.club_place = club_place;
	}
	public String getClub_cycle() {
		return club_cycle;
	}
	public void setClub_cycle(String club_cycle) {
		this.club_cycle = club_cycle;
	}
	public String getClub_url() {
		return club_url;
	}
	public void setClub_url(String club_url) {
		this.club_url = club_url;
	}
	public String getClub_intro() {
		return club_intro;
	}
	public void setClub_intro(String club_intro) {
		this.club_intro = club_intro;
	}
	public String getClub_act() {
		return club_act;
	}
	public void setClub_act(String club_act) {
		this.club_act = club_act;
	}
	public String getHan_nm() {
		return han_nm;
	}
	public void setHan_nm(String han_nm) {
		this.han_nm = han_nm;
	}
	public String getCh_nm() {
		return ch_nm;
	}
	public void setCh_nm(String ch_nm) {
		this.ch_nm = ch_nm;
	}
	public String getUs_nm() {
		return us_nm;
	}
	public void setUs_nm(String us_nm) {
		this.us_nm = us_nm;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAbility_type() {
		return ability_type;
	}
	public void setAbility_type(String ability_type) {
		this.ability_type = ability_type;
	}
	public String getAbility_type_nm() {
		return ability_type_nm;
	}
	public void setAbility_type_nm(String ability_type_nm) {
		this.ability_type_nm = ability_type_nm;
	}
	public String getAffi_nm() {
		return affi_nm;
	}
	public void setAffi_nm(String affi_nm) {
		this.affi_nm = affi_nm;
	}
	public String getStudy() {
		return study;
	}
	public void setStudy(String study) {
		this.study = study;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getAtch_file_id() {
		return atch_file_id;
	}
	public void setAtch_file_id(String atch_file_id) {
		this.atch_file_id = atch_file_id;
	}
	public int getTeacher_seq() {
		return teacher_seq;
	}
	public void setTeacher_seq(int teacher_seq) {
		this.teacher_seq = teacher_seq;
	}
	public String[] getLicense_arr() {
		return license_arr;
	}
	public void setLicense_arr(String[] license_arr) {
		this.license_arr = license_arr;
	}
	public String[] getCareer_arr() {
		return career_arr;
	}
	public void setCareer_arr(String[] career_arr) {
		this.career_arr = career_arr;
	}
	public String[] getHistory_arr() {
		return history_arr;
	}
	public void setHistory_arr(String[] history_arr) {
		this.history_arr = history_arr;
	}
	public String getAtch_file_id2() {
		return atch_file_id2;
	}
	public void setAtch_file_id2(String atch_file_id2) {
		this.atch_file_id2 = atch_file_id2;
	}
	public int getAscnum() {
		return ascnum;
	}
	public void setAscnum(int ascnum) {
		this.ascnum = ascnum;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getJobFlag() {
		return jobFlag;
	}
	public void setJobFlag(String jobFlag) {
		this.jobFlag = jobFlag;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getInput_name() {
		return input_name;
	}
	public void setInput_name(String input_name) {
		this.input_name = input_name;
	}
	public int getFile_attach_seq() {
		return file_attach_seq;
	}
	public void setFile_attach_seq(int file_attach_seq) {
		this.file_attach_seq = file_attach_seq;
	}
	public int getCom_code_sq() {
		return com_code_sq;
	}
	public void setCom_code_sq(int com_code_sq) {
		this.com_code_sq = com_code_sq;
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
	public String getCode_use_yn() {
		return code_use_yn;
	}
	public void setCode_use_yn(String code_use_yn) {
		this.code_use_yn = code_use_yn;
	}
	public String getAd_use_yn() {
		return ad_use_yn;
	}
	public void setAd_use_yn(String ad_use_yn) {
		this.ad_use_yn = ad_use_yn;
	}
	public String getSrch_input() {
		return srch_input;
	}
	public void setSrch_input(String srch_input) {
		this.srch_input = srch_input;
	}
	public String getScType() {
		return scType;
	}
	public void setScType(String scType) {
		this.scType = scType;
	}
	public String getScType2() {
		return scType2;
	}
	public void setScType2(String scType2) {
		this.scType2 = scType2;
	}
	public String getScType3() {
		return scType3;
	}
	public void setScType3(String scType3) {
		this.scType3 = scType3;
	}
	public String getSrch_date() {
		return srch_date;
	}
	public void setSrch_date(String srch_date) {
		this.srch_date = srch_date;
	}
	public String getSrch_date2() {
		return srch_date2;
	}
	public void setSrch_date2(String srch_date2) {
		this.srch_date2 = srch_date2;
	}
	public int getPage_index() {
		return page_index;
	}
	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}
	public int getPage_unit() {
		return page_unit;
	}
	public void setPage_unit(int page_unit) {
		this.page_unit = page_unit;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public int getFirst_index() {
		return first_index;
	}
	public void setFirst_index(int first_index) {
		this.first_index = first_index;
	}
	public int getLast_index() {
		return last_index;
	}
	public void setLast_index(int last_index) {
		this.last_index = last_index;
	}
	public int getRecordcountperpage() {
		return recordcountperpage;
	}
	public void setRecordcountperpage(int recordcountperpage) {
		this.recordcountperpage = recordcountperpage;
	}
	public int getRowno() {
		return rowno;
	}
	public void setRowno(int rowno) {
		this.rowno = rowno;
	}
	public int getCurrRow() {
		return currRow;
	}
	public void setCurrRow(int currRow) {
		this.currRow = currRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public String getStrPaging() {
		return strPaging;
	}
	public void setStrPaging(String strPaging) {
		this.strPaging = strPaging;
	}
	public int getCurrRow2() {
		return currRow2;
	}
	public void setCurrRow2(int currRow2) {
		this.currRow2 = currRow2;
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getCheck_yn() {
		return check_yn;
	}
	public void setCheck_yn(String check_yn) {
		this.check_yn = check_yn;
	}
	
	
	

}
