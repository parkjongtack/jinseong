package egovframework.client.member.service;

public class ShopMemberManageVo {
	private int mber_seq; 			//회원번호
	private String mber_id; 		//회원아이디
	private String mber_pw;			//회원 비번
	private String mber_name;		//회원명
	private String mber_gender;		//성별
	private String mber_birth;		//생일
	private String mber_tel;		//휴대폰번호
	private String mber_email;		//이메일
	private String mailing_yn;		//이메일 수신여부		
	private String agree_yn;		//동의여부 (사용 안함) 
	private String sms_yn;			//sms 수신여부
	private String zipcode;			//우편번호
	private String addr;			//주소1
	private String addr_detail;		//주소2
	private String reg_dt;			//가입일시 timestamp(등록 일시)
	private String reg_id;			//등록자 id
	private String updt_dt;			//수정일시 (테이블 추가 필요)
	private String updt_id;			//수정자 id (테이블 추가필요)
	private String last_login_dt;	//최근 접속일 timestamp
	private String reg_ip;			//가입시 ip
	private String mber_dupinfo;	//?
	private String mber_status;		//탈퇴 요청 여부 (Y,N) : 기본값 N
	private String withdraw_content;		//탈퇴 사유
	private String mber_phone;
	private int mber_milage;
	private int mber_emoney;
	private String join_ref; 		//가입경로
	private String hp_chck;			// 휴대폰 본인인증 분기
	private String di_yn;			//본인인증코드 필요유무(인증완료:Y, 인증필요:N, 인증안함:C)
	private String di_date;			//본인인증 일자
	private int di_rejection_cnt;	//본인인증 거절 카운팅
	
	

	//쇼핑몰 timestamp 필드 추가
	private int reg_dt_ts;
	private int updt_dt_ts;
	private int last_login_dt_ts;
	
	//20181120 본인인증추가
	private String reg_sms;	//휴대폰 인증여부 (Y,N) default N
	private String reg_code; //휴대폰 본인인증 DI값	
	private String birth_year; //jsp에서 사용
	private String birth_month;
	private String birth_day;
	
	
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
		private int nextPage;
		
		private int ascnum;
		private int rownum;
	
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
	public String getBirth_year() {
		return birth_year;
	}
	public void setBirth_year(String birth_year) {
		this.birth_year = birth_year;
	}
	public String getBirth_month() {
		return birth_month;
	}
	public void setBirth_month(String birth_month) {
		this.birth_month = birth_month;
	}
	public String getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(String birth_day) {
		this.birth_day = birth_day;
	}
	public String getReg_sms() {
		return reg_sms;
	}
	public void setReg_sms(String reg_sms) {
		this.reg_sms = reg_sms;
	}
	public String getReg_code() {
		return reg_code;
	}
	public void setReg_code(String reg_code) {
		this.reg_code = reg_code;
	}
	public int getReg_dt_ts() {
		return reg_dt_ts;
	}
	public void setReg_dt_ts(int reg_dt_ts) {
		this.reg_dt_ts = reg_dt_ts;
	}
	public int getUpdt_dt_ts() {
		return updt_dt_ts;
	}
	public void setUpdt_dt_ts(int updt_dt_ts) {
		this.updt_dt_ts = updt_dt_ts;
	}
	public int getLast_login_dt_ts() {
		return last_login_dt_ts;
	}
	public void setLast_login_dt_ts(int last_login_dt_ts) {
		this.last_login_dt_ts = last_login_dt_ts;
	}
	public String getJoin_ref() {
		return join_ref;
	}
	public void setJoin_ref(String join_ref) {
		this.join_ref = join_ref;
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
	public String getReg_ip() {
		return reg_ip;
	}
	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
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
	public String getWithdraw_content() {
		return withdraw_content;
	}
	public void setWithdraw_content(String withdraw_content) {
		this.withdraw_content = withdraw_content;
	}
	public String getMber_phone() {
		return mber_phone;
	}
	public void setMber_phone(String mber_phone) {
		this.mber_phone = mber_phone;
	}
	public int getMber_milage() {
		return mber_milage;
	}
	public void setMber_milage(int mber_milage) {
		this.mber_milage = mber_milage;
	}
	public int getMber_emoney() {
		return mber_emoney;
	}
	public void setMber_emoney(int mber_emoney) {
		this.mber_emoney = mber_emoney;
	}
	public String getHp_chck() {
		return hp_chck;
	}
	public void setHp_chck(String hp_chck) {
		this.hp_chck = hp_chck;
	}
	public String getDi_yn() {
		return di_yn;
	}
	public void setDi_yn(String di_yn) {
		this.di_yn = di_yn;
	}
	public int getDi_rejection_cnt() {
		return di_rejection_cnt;
	}
	public void setDi_rejection_cnt(int di_rejection_cnt) {
		this.di_rejection_cnt = di_rejection_cnt;
	}
	public String getDi_date() {
		return di_date;
	}
	public void setDi_date(String di_date) {
		this.di_date = di_date;
	}
	
	
	
	
//	private String mber_type;		//사용자 구분 (? 어떻게 사용 할지
//	private String auth_code;
//	private String mber_gubun; //로그인 정보 회원구분
//	
//	private int ascnum;
//	private int rownum;
	
	
	

}
