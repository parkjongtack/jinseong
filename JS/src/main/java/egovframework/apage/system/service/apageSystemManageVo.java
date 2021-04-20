package egovframework.apage.system.service;

public class apageSystemManageVo {
	
	//컨텐츠
	private int seq;
	private String menu_cd;
	private String menu_nm;
	private String contents;
	private String reg_id;
	private String reg_date;
	private String updt_id;
	private String updt_date;
	private String parent_cd;
	
	private String menu;
	
	//팝업
	private int pop_seq;
	private String pop_subject;
	private String pop_width;
	private String pop_height;
	private String pop_position_x;
	private String pop_position_y;
	private String pop_url;
	private String pop_st_dt;
	private String pop_ed_dt;
	private String pop_content;
	private String pop_state;
	private String pop_set;
	private String pop_reg_dt;
	private String pop_up_dt;
	private String pop_reg_nm;
	private String pop_up_nm;
	
	//배너
	private int ban_seq;
	private String ban_subject;
	private String ban_use;
	private String ban_gubun;
	private String ban_url;
	private String ban_con;
	private String ban_reg_dt;
	private String ban_up_dt;
	private String ban_reg_nm;
	private String ban_up_nm;
	private String bannerUpload1;
	private String atch_file_id;
	private String atch_file_id2;
	
	private String[] chkList;			//체크박스 선택삭제
	
	//분류코드
	private String cl_code;
	private String cl_code_nm;
	private String reg_nm;
	private String reg_dt;
	private String code_gb;
	
	//공통코드 관리
	private String code_id_seq;
	private String code_id_seq2;
	private String code_id_nm;
	private String use_yn;
	
	private String posblAtchFileNumber;
	private String user_name;
	
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
	
	
	//접근제어
	private String ip;
	private String memo;
	
	//로그
	private String id;
	private String method;
	private String full_page_url;
	private String browser;
	private String page_url;
	private String param;
	private String agent;
	
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
	
	
	public String[] getChkList() {
		return chkList;
	}
	public void setChkList(String[] chkList) {
		this.chkList = chkList;
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
	public int getPop_seq() {
		return pop_seq;
	}
	public void setPop_seq(int pop_seq) {
		this.pop_seq = pop_seq;
	}
	public String getPop_subject() {
		return pop_subject;
	}
	public void setPop_subject(String pop_subject) {
		this.pop_subject = pop_subject;
	}
	public String getPop_width() {
		return pop_width;
	}
	public void setPop_width(String pop_width) {
		this.pop_width = pop_width;
	}
	public String getPop_height() {
		return pop_height;
	}
	public void setPop_height(String pop_height) {
		this.pop_height = pop_height;
	}
	public String getPop_position_x() {
		return pop_position_x;
	}
	public void setPop_position_x(String pop_position_x) {
		this.pop_position_x = pop_position_x;
	}
	public String getPop_position_y() {
		return pop_position_y;
	}
	public void setPop_position_y(String pop_position_y) {
		this.pop_position_y = pop_position_y;
	}
	public String getPop_url() {
		return pop_url;
	}
	public void setPop_url(String pop_url) {
		this.pop_url = pop_url;
	}
	public String getPop_st_dt() {
		return pop_st_dt;
	}
	public void setPop_st_dt(String pop_st_dt) {
		this.pop_st_dt = pop_st_dt;
	}
	public String getPop_ed_dt() {
		return pop_ed_dt;
	}
	public void setPop_ed_dt(String pop_ed_dt) {
		this.pop_ed_dt = pop_ed_dt;
	}
	public String getPop_content() {
		return pop_content;
	}
	public void setPop_content(String pop_content) {
		this.pop_content = pop_content;
	}
	public String getPop_state() {
		return pop_state;
	}
	public void setPop_state(String pop_state) {
		this.pop_state = pop_state;
	}
	public String getPop_set() {
		return pop_set;
	}
	public void setPop_set(String pop_set) {
		this.pop_set = pop_set;
	}
	public String getPop_reg_dt() {
		return pop_reg_dt;
	}
	public void setPop_reg_dt(String pop_reg_dt) {
		this.pop_reg_dt = pop_reg_dt;
	}
	public String getPop_up_dt() {
		return pop_up_dt;
	}
	public void setPop_up_dt(String pop_up_dt) {
		this.pop_up_dt = pop_up_dt;
	}
	public String getPop_reg_nm() {
		return pop_reg_nm;
	}
	public void setPop_reg_nm(String pop_reg_nm) {
		this.pop_reg_nm = pop_reg_nm;
	}
	public String getPop_up_nm() {
		return pop_up_nm;
	}
	public void setPop_up_nm(String pop_up_nm) {
		this.pop_up_nm = pop_up_nm;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getBan_seq() {
		return ban_seq;
	}
	public void setBan_seq(int ban_seq) {
		this.ban_seq = ban_seq;
	}
	public String getBan_subject() {
		return ban_subject;
	}
	public void setBan_subject(String ban_subject) {
		this.ban_subject = ban_subject;
	}
	public String getBan_use() {
		return ban_use;
	}
	public void setBan_use(String ban_use) {
		this.ban_use = ban_use;
	}
	public String getBan_gubun() {
		return ban_gubun;
	}
	public void setBan_gubun(String ban_gubun) {
		this.ban_gubun = ban_gubun;
	}
	public String getBan_url() {
		return ban_url;
	}
	public void setBan_url(String ban_url) {
		this.ban_url = ban_url;
	}
	public String getBan_con() {
		return ban_con;
	}
	public void setBan_con(String ban_con) {
		this.ban_con = ban_con;
	}
	public String getBan_reg_dt() {
		return ban_reg_dt;
	}
	public void setBan_reg_dt(String ban_reg_dt) {
		this.ban_reg_dt = ban_reg_dt;
	}
	public String getBan_up_dt() {
		return ban_up_dt;
	}
	public void setBan_up_dt(String ban_up_dt) {
		this.ban_up_dt = ban_up_dt;
	}
	public String getBan_reg_nm() {
		return ban_reg_nm;
	}
	public void setBan_reg_nm(String ban_reg_nm) {
		this.ban_reg_nm = ban_reg_nm;
	}
	public String getBan_up_nm() {
		return ban_up_nm;
	}
	public void setBan_up_nm(String ban_up_nm) {
		this.ban_up_nm = ban_up_nm;
	}
	public String getBannerUpload1() {
		return bannerUpload1;
	}
	public void setBannerUpload1(String bannerUpload1) {
		this.bannerUpload1 = bannerUpload1;
	}
	public String getAtch_file_id() {
		return atch_file_id;
	}
	public void setAtch_file_id(String atch_file_id) {
		this.atch_file_id = atch_file_id;
	}
	public String getPosblAtchFileNumber() {
		return posblAtchFileNumber;
	}
	public void setPosblAtchFileNumber(String posblAtchFileNumber) {
		this.posblAtchFileNumber = posblAtchFileNumber;
	}
	public String getAtch_file_id2() {
		return atch_file_id2;
	}
	public void setAtch_file_id2(String atch_file_id2) {
		this.atch_file_id2 = atch_file_id2;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getMenu_cd() {
		return menu_cd;
	}
	public void setMenu_cd(String menu_cd) {
		this.menu_cd = menu_cd;
	}
	public String getMenu_nm() {
		return menu_nm;
	}
	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUpdt_id() {
		return updt_id;
	}
	public void setUpdt_id(String updt_id) {
		this.updt_id = updt_id;
	}
	public String getUpdt_date() {
		return updt_date;
	}
	public void setUpdt_date(String updt_date) {
		this.updt_date = updt_date;
	}
	public String getParent_cd() {
		return parent_cd;
	}
	public void setParent_cd(String parent_cd) {
		this.parent_cd = parent_cd;
	}
	public String getCl_code() {
		return cl_code;
	}
	public void setCl_code(String cl_code) {
		this.cl_code = cl_code;
	}
	public String getCl_code_nm() {
		return cl_code_nm;
	}
	public void setCl_code_nm(String cl_code_nm) {
		this.cl_code_nm = cl_code_nm;
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
	public String getCode_gb() {
		return code_gb;
	}
	public void setCode_gb(String code_gb) {
		this.code_gb = code_gb;
	}
	public String getCode_id_nm() {
		return code_id_nm;
	}
	public void setCode_id_nm(String code_id_nm) {
		this.code_id_nm = code_id_nm;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getCode_id_seq() {
		return code_id_seq;
	}
	public void setCode_id_seq(String code_id_seq) {
		this.code_id_seq = code_id_seq;
	}
	public String getCode_id_seq2() {
		return code_id_seq2;
	}
	public void setCode_id_seq2(String code_id_seq2) {
		this.code_id_seq2 = code_id_seq2;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFull_page_url() {
		return full_page_url;
	}
	public void setFull_page_url(String full_page_url) {
		this.full_page_url = full_page_url;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getPage_url() {
		return page_url;
	}
	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	
	
}
