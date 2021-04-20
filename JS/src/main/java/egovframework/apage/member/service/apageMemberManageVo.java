package egovframework.apage.member.service;

public class apageMemberManageVo {

	//SMS 기록(sms_manage)
	private int seq;
	private String msg_type;
	private String msg_content;
	private String sms_send_date_ym;
	private String send_date;
	private String send_id;
		
	private int mber_seq;
	private String mber_id;
	private String mber_pw;
	private String mber_name;
	private String mber_gender;
	private String mber_birth;
	private String mber_tel;
	private String mber_email;
	private String mailing_yn;
	private String agree_yn;
	private String sms_yn;
	private String zipcode;
	private String addr;
	private String addr_detail;
	private String reg_dt;
	private String reg_id;
	private String updt_dt;
	private String updt_id;
	private String last_login_dt;
	private String reg_ip;
	private String mber_dupinfo;
	private String mber_status;
	
	private String mber_type;
	private String auth_code;
	private String mber_gubun; //로그인 정보 회원구분
	
	private int ascnum;
	private int rownum;
	
	//검색
	private String srch_input;
	private String scType;
	private String scType2;
	private String scType3;
	private String scType4;
	private String srch_date;
	private String srch_date2;
		
	//페이징
	private int page_index = 1; // 현재페이지
	private int page_unit = 5; // 페이지갯수
	private int page_size = 10; // 페이지사이즈
	private int first_index = 1;// 첫페이지 인덱스
	private int last_index = 1; // 마지막페이지 인덱스
	private int recordcountperpage = 5;// 페이지당 레코드 개수
	private int rowno = 0;// 레코드 번호
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
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public String getSms_send_date_ym() {
		return sms_send_date_ym;
	}
	public void setSms_send_date_ym(String sms_send_date_ym) {
		this.sms_send_date_ym = sms_send_date_ym;
	}
	public String getSend_date() {
		return send_date;
	}
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	public String getSend_id() {
		return send_id;
	}
	public void setSend_id(String send_id) {
		this.send_id = send_id;
	}
	
	
	
	
	public int getMber_seq() {
		return mber_seq;
	}
	public void setMber_seq(int mber_seq) {
		this.mber_seq = mber_seq;
	}
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
	}
	public String getMber_pw() {
		return mber_pw;
	}
	public void setMber_pw(String mber_pw) {
		this.mber_pw = mber_pw;
	}
	public String getMber_name() {
		return mber_name;
	}
	public void setMber_name(String mber_name) {
		this.mber_name = mber_name;
	}
	public String getMber_gender() {
		return mber_gender;
	}
	public void setMber_gender(String mber_gender) {
		this.mber_gender = mber_gender;
	}
	public String getMber_birth() {
		return mber_birth;
	}
	public void setMber_birth(String mber_birth) {
		this.mber_birth = mber_birth;
	}
	public String getMber_tel() {
		return mber_tel;
	}
	public void setMber_tel(String mber_tel) {
		this.mber_tel = mber_tel;
	}
	public String getMber_email() {
		return mber_email;
	}
	public void setMber_email(String mber_email) {
		this.mber_email = mber_email;
	}
	public String getMailing_yn() {
		return mailing_yn;
	}
	public void setMailing_yn(String mailing_yn) {
		this.mailing_yn = mailing_yn;
	}
	public String getAgree_yn() {
		return agree_yn;
	}
	public void setAgree_yn(String agree_yn) {
		this.agree_yn = agree_yn;
	}
	public String getSms_yn() {
		return sms_yn;
	}
	public void setSms_yn(String sms_yn) {
		this.sms_yn = sms_yn;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddr_detail() {
		return addr_detail;
	}
	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
	public String getUpdt_dt() {
		return updt_dt;
	}
	public void setUpdt_dt(String updt_dt) {
		this.updt_dt = updt_dt;
	}
	public String getUpdt_id() {
		return updt_id;
	}
	public void setUpdt_id(String updt_id) {
		this.updt_id = updt_id;
	}
	public String getLast_login_dt() {
		return last_login_dt;
	}
	public void setLast_login_dt(String last_login_dt) {
		this.last_login_dt = last_login_dt;
	}
	public String getMber_dupinfo() {
		return mber_dupinfo;
	}
	public void setMber_dupinfo(String mber_dupinfo) {
		this.mber_dupinfo = mber_dupinfo;
	}
	public String getMber_status() {
		return mber_status;
	}
	public void setMber_status(String mber_status) {
		this.mber_status = mber_status;
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
	public String getScType4() {
		return scType4;
	}
	public void setScType4(String scType4) {
		this.scType4 = scType4;
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
	public String getReg_ip() {
		return reg_ip;
	}
	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}
	public String getMber_type() {
		return mber_type;
	}
	public void setMber_type(String mber_type) {
		this.mber_type = mber_type;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getMber_gubun() {
		return mber_gubun;
	}
	public void setMber_gubun(String mber_gubun) {
		this.mber_gubun = mber_gubun;
	}
}
