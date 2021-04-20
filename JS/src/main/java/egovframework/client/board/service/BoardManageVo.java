package egovframework.client.board.service;

public class BoardManageVo {
	
	private int ntt_id;
	private String bbs_id;
	private String ntt_sj;
	private String ntt_cn;
	private String answer_at;
	private int parntsctt_no;
	private int sort_ordr;
	private int rdcnt;
	private String use_at;
	private String expose_yn;
	private String cut_yn;
	private String ntce_bgnde;
	private String ntce_endde;
	private String ntcr_id;
	private String ntcr_nm;
	private String ntup_id;
	private String ntup_nm;
	private String password;
	private String atch_file_id;
	private String reg_dt;
	private String updt_dt;
	private String org_reg_dt;
	private String org_updt_dt;
	private String check_yn;
	private String atch_file_id2;
	private String memo;	
	private String open_yn;	
	private String as_type;
	private String as_tel;
	private String as_tel1;
	private String as_tel2;
	private String as_tel3;
	private String as_email;
	private String as_email1;
	private String as_email2;
	private String as_status;
	private String updt_yn;
	private String ct_result;
	private int comment_cnt;
	
	private String result_sms_send_yn;
	private String lane_sms_send_yn;
	
	private String refund_finish_date;
	private String refund_finish_h;
	private String refund_finish_m;
	
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String option5;
	private String option6;
	private String zipcode;
	private String addr;
	private String addr_detail;
	private String eng_name;
	private String eng_last_name;
	private String eng_first_name;
	
	//대회신청
	private int app_cnt;		//총 신청자 수
	private int part1_app_cnt;	//1조 선정자 수
	private int part2_app_cnt;	//2조 선정자 수
	private int part3_app_cnt;	//3조 선정자 수
	private int part4_app_cnt;	//4조 선정자 수
	
	private int part1_wait_cnt;	//1조 대기자 수
	private int part2_wait_cnt;	//2조 대기자 수
	private int part3_wait_cnt;	//3조 대기자 수
	private int part4_wait_cnt;	//4조 대기자 수
	private int limit_waiting;		//대기자정원
	private String ct_group;	//대회 그루핑
	private String ct_seq_arr[];
	private String ct_type;
	
	private String refund_bank;
	private String refund_accholder;
	private String refund_account;
	
	//캡차
	private String captcha_key;
	private String captcha_answer;
	//음성캡차
	private String captcha_key_audio;
	private String captcha_answer_audio;
	
	private String captcha_type;
	
	//staff	
	private int seq;
	private String staff_name;
	private String asso_type;
	private String staff_type;
	private String team;
	private String pro_no;
	private String career;
	private String use_hand;
	private String point_rank;
	private String staff_info;
	private int hit;
	private String reg_id;
	private String updt_id;
	
	//대회관련
	private int ct_seq;	
	private String ct_sbj;
	private String ct_content;
	private String ct_place;
	private String ct_dt;
	private String ct_process;
	
	private int app_seq;	
	private String mber_id;
	private String join_name;
	private String deposit_name;
	private String birth;
	private String gender;
	private String telno;
	private String email;
	private String disable_yn;
	private String handicap;
	private String part;
	private String lane;
	private String status;
	private int waiting_num;
	private int limit_part;
	private String situation_show_yn;
	
	//대회신청 접수 기간
	private String app_start_dt;
	private String app_start_h;
	private String app_start_m;
	private String app_end_dt;
	private String app_end_h;
	private String app_end_m;
	private String pay_flag;
	
	//쇼핑몰 이벤트 신청접수 기간
	private int shop_event_seq;
	private String shop_event_process;
	private String shop_event_start_dt;
	private String shop_event_start_h;
	private String shop_event_start_m;
	private String shop_event_end_dt;
	private String shop_event_end_h;
	private String shop_event_end_m;
	private String shop_event_memo;
	
	private String temp1;
	private String temp2;
	private String temp3;
	private String temp4;
	
	

	private String ct_bank;
	private String ct_acchholder;
	private String ct_account;
	private String ct_price;
	private String ct_deposit_stdt;
	private String ct_deposit_sth;
	private String ct_deposit_stm;
	private String ct_deposit_eddt;
	private String ct_deposit_edh;
	private String ct_deposit_edm;
	
	private int ascnum;
	private int rownum;
	private String jobFlag;
	
	//검색
	private String srch_input;
	private String scType;
	private String scType2;
	private String scType3;
	private String scType4;
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
	
	
	public int getNtt_id() {
		return ntt_id;
	}
	public void setNtt_id(int ntt_id) {
		this.ntt_id = ntt_id;
	}
	public String getBbs_id() {
		return bbs_id;
	}
	public void setBbs_id(String bbs_id) {
		this.bbs_id = bbs_id;
	}
	public String getNtt_sj() {
		return ntt_sj;
	}
	public void setNtt_sj(String ntt_sj) {
		this.ntt_sj = ntt_sj;
	}
	public String getNtt_cn() {
		return ntt_cn;
	}
	public void setNtt_cn(String ntt_cn) {
		this.ntt_cn = ntt_cn;
	}
	public String getAnswer_at() {
		return answer_at;
	}
	public void setAnswer_at(String answer_at) {
		this.answer_at = answer_at;
	}
	public int getParntsctt_no() {
		return parntsctt_no;
	}
	public void setParntsctt_no(int parntsctt_no) {
		this.parntsctt_no = parntsctt_no;
	}
	public int getSort_ordr() {
		return sort_ordr;
	}
	public void setSort_ordr(int sort_ordr) {
		this.sort_ordr = sort_ordr;
	}
	public int getRdcnt() {
		return rdcnt;
	}
	public void setRdcnt(int rdcnt) {
		this.rdcnt = rdcnt;
	}
	public String getUse_at() {
		return use_at;
	}
	public void setUse_at(String use_at) {
		this.use_at = use_at;
	}
	public String getExpose_yn() {
		return expose_yn;
	}
	public void setExpose_yn(String expose_yn) {
		this.expose_yn = expose_yn;
	}
	public String getCut_yn() {
		return cut_yn;
	}
	public void setCut_yn(String cut_yn) {
		this.cut_yn = cut_yn;
	}
	public String getNtce_bgnde() {
		return ntce_bgnde;
	}
	public void setNtce_bgnde(String ntce_bgnde) {
		this.ntce_bgnde = ntce_bgnde;
	}
	public String getNtce_endde() {
		return ntce_endde;
	}
	public void setNtce_endde(String ntce_endde) {
		this.ntce_endde = ntce_endde;
	}
	public String getNtcr_id() {
		return ntcr_id;
	}
	public void setNtcr_id(String ntcr_id) {
		this.ntcr_id = ntcr_id;
	}
	public String getNtcr_nm() {
		return ntcr_nm;
	}
	public void setNtcr_nm(String ntcr_nm) {
		this.ntcr_nm = ntcr_nm;
	}
	public String getNtup_id() {
		return ntup_id;
	}
	public void setNtup_id(String ntup_id) {
		this.ntup_id = ntup_id;
	}
	public String getNtup_nm() {
		return ntup_nm;
	}
	public void setNtup_nm(String ntup_nm) {
		this.ntup_nm = ntup_nm;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAtch_file_id() {
		return atch_file_id;
	}
	public void setAtch_file_id(String atch_file_id) {
		this.atch_file_id = atch_file_id;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getUpdt_dt() {
		return updt_dt;
	}
	public void setUpdt_dt(String updt_dt) {
		this.updt_dt = updt_dt;
	}
	public String getCheck_yn() {
		return check_yn;
	}
	public void setCheck_yn(String check_yn) {
		this.check_yn = check_yn;
	}
	public String getAtch_file_id2() {
		return atch_file_id2;
	}
	public void setAtch_file_id2(String atch_file_id2) {
		this.atch_file_id2 = atch_file_id2;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOpen_yn() {
		return open_yn;
	}
	public void setOpen_yn(String open_yn) {
		this.open_yn = open_yn;
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
	public String getAs_type() {
		return as_type;
	}
	public void setAs_type(String as_type) {
		this.as_type = as_type;
	}
	public String getAs_tel() {
		return as_tel;
	}
	public void setAs_tel(String as_tel) {
		this.as_tel = as_tel;
	}
	public String getAs_email() {
		return as_email;
	}
	public void setAs_email(String as_email) {
		this.as_email = as_email;
	}
	public String getCaptcha_key() {
		return captcha_key;
	}
	public void setCaptcha_key(String captcha_key) {
		this.captcha_key = captcha_key;
	}
	public String getCaptcha_answer() {
		return captcha_answer;
	}
	public void setCaptcha_answer(String captcha_answer) {
		this.captcha_answer = captcha_answer;
	}
	public String getCaptcha_key_audio() {
		return captcha_key_audio;
	}
	public void setCaptcha_key_audio(String captcha_key_audio) {
		this.captcha_key_audio = captcha_key_audio;
	}
	public String getCaptcha_answer_audio() {
		return captcha_answer_audio;
	}
	public void setCaptcha_answer_audio(String captcha_answer_audio) {
		this.captcha_answer_audio = captcha_answer_audio;
	}
	public String getCaptcha_type() {
		return captcha_type;
	}
	public void setCaptcha_type(String captcha_type) {
		this.captcha_type = captcha_type;
	}
	public String getAs_status() {
		return as_status;
	}
	public void setAs_status(String as_status) {
		this.as_status = as_status;
	}
	public String getAs_tel1() {
		return as_tel1;
	}
	public void setAs_tel1(String as_tel1) {
		this.as_tel1 = as_tel1;
	}
	public String getAs_tel2() {
		return as_tel2;
	}
	public void setAs_tel2(String as_tel2) {
		this.as_tel2 = as_tel2;
	}
	public String getAs_tel3() {
		return as_tel3;
	}
	public void setAs_tel3(String as_tel3) {
		this.as_tel3 = as_tel3;
	}
	public String getAs_email1() {
		return as_email1;
	}
	public void setAs_email1(String as_email1) {
		this.as_email1 = as_email1;
	}
	public String getAs_email2() {
		return as_email2;
	}
	public void setAs_email2(String as_email2) {
		this.as_email2 = as_email2;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getStaff_name() {
		return staff_name;
	}
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	public String getAsso_type() {
		return asso_type;
	}
	public void setAsso_type(String asso_type) {
		this.asso_type = asso_type;
	}
	public String getStaff_type() {
		return staff_type;
	}
	public void setStaff_type(String staff_type) {
		this.staff_type = staff_type;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getPro_no() {
		return pro_no;
	}
	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getUse_hand() {
		return use_hand;
	}
	public void setUse_hand(String use_hand) {
		this.use_hand = use_hand;
	}
	public String getPoint_rank() {
		return point_rank;
	}
	public void setPoint_rank(String point_rank) {
		this.point_rank = point_rank;
	}
	public String getStaff_info() {
		return staff_info;
	}
	public void setStaff_info(String staff_info) {
		this.staff_info = staff_info;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
	public String getUpdt_id() {
		return updt_id;
	}
	public void setUpdt_id(String updt_id) {
		this.updt_id = updt_id;
	}
	public int getCt_seq() {
		return ct_seq;
	}
	public void setCt_seq(int ct_seq) {
		this.ct_seq = ct_seq;
	}
	public String getCt_sbj() {
		return ct_sbj;
	}
	public void setCt_sbj(String ct_sbj) {
		this.ct_sbj = ct_sbj;
	}
	public String getCt_content() {
		return ct_content;
	}
	public void setCt_content(String ct_content) {
		this.ct_content = ct_content;
	}
	public String getCt_place() {
		return ct_place;
	}
	public void setCt_place(String ct_place) {
		this.ct_place = ct_place;
	}
	public String getCt_dt() {
		return ct_dt;
	}
	public void setCt_dt(String ct_dt) {
		this.ct_dt = ct_dt;
	}
	public String getCt_process() {
		return ct_process;
	}
	public void setCt_process(String ct_process) {
		this.ct_process = ct_process;
	}
	public int getApp_seq() {
		return app_seq;
	}
	public void setApp_seq(int app_seq) {
		this.app_seq = app_seq;
	}
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
	}
	public String getDisable_yn() {
		return disable_yn;
	}
	public void setDisable_yn(String disable_yn) {
		this.disable_yn = disable_yn;
	}
	public String getHandicap() {
		return handicap;
	}
	public void setHandicap(String handicap) {
		this.handicap = handicap;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getLane() {
		return lane;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getWaiting_num() {
		return waiting_num;
	}
	public void setWaiting_num(int waiting_num) {
		this.waiting_num = waiting_num;
	}
	public String getJoin_name() {
		return join_name;
	}
	public String getCt_group() {
		return ct_group;
	}
	public void setCt_group(String ct_group) {
		this.ct_group = ct_group;
	}
	public void setJoin_name(String join_name) {
		this.join_name = join_name;
	}
	public String getDeposit_name() {
		return deposit_name;
	}
	public void setDeposit_name(String deposit_name) {
		this.deposit_name = deposit_name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getLimit_part() {
		return limit_part;
	}
	public void setLimit_part(int limit_part) {
		this.limit_part = limit_part;
	}
	public String getApp_start_dt() {
		return app_start_dt;
	}
	public void setApp_start_dt(String app_start_dt) {
		this.app_start_dt = app_start_dt;
	}
	public String getApp_start_h() {
		return app_start_h;
	}
	public void setApp_start_h(String app_start_h) {
		this.app_start_h = app_start_h;
	}
	public String getApp_start_m() {
		return app_start_m;
	}
	public void setApp_start_m(String app_start_m) {
		this.app_start_m = app_start_m;
	}
	public String getApp_end_dt() {
		return app_end_dt;
	}
	public void setApp_end_dt(String app_end_dt) {
		this.app_end_dt = app_end_dt;
	}
	public String getApp_end_h() {
		return app_end_h;
	}
	public void setApp_end_h(String app_end_h) {
		this.app_end_h = app_end_h;
	}
	public String getApp_end_m() {
		return app_end_m;
	}
	public void setApp_end_m(String app_end_m) {
		this.app_end_m = app_end_m;
	}
	public String getPay_flag() {
		return pay_flag;
	}
	public void setPay_flag(String pay_flag) {
		this.pay_flag = pay_flag;
	}
	public int getApp_cnt() {
		return app_cnt;
	}
	public void setApp_cnt(int app_cnt) {
		this.app_cnt = app_cnt;
	}
	public int getPart1_app_cnt() {
		return part1_app_cnt;
	}
	public void setPart1_app_cnt(int part1_app_cnt) {
		this.part1_app_cnt = part1_app_cnt;
	}
	public int getPart2_app_cnt() {
		return part2_app_cnt;
	}
	public void setPart2_app_cnt(int part2_app_cnt) {
		this.part2_app_cnt = part2_app_cnt;
	}
	public int getPart3_app_cnt() {
		return part3_app_cnt;
	}
	public void setPart3_app_cnt(int part3_app_cnt) {
		this.part3_app_cnt = part3_app_cnt;
	}
	public int getPart4_app_cnt() {
		return part4_app_cnt;
	}
	public void setPart4_app_cnt(int part4_app_cnt) {
		this.part4_app_cnt = part4_app_cnt;
	}
	public int getPart1_wait_cnt() {
		return part1_wait_cnt;
	}
	public void setPart1_wait_cnt(int part1_wait_cnt) {
		this.part1_wait_cnt = part1_wait_cnt;
	}
	public int getPart2_wait_cnt() {
		return part2_wait_cnt;
	}
	public void setPart2_wait_cnt(int part2_wait_cnt) {
		this.part2_wait_cnt = part2_wait_cnt;
	}
	public int getPart3_wait_cnt() {
		return part3_wait_cnt;
	}
	public void setPart3_wait_cnt(int part3_wait_cnt) {
		this.part3_wait_cnt = part3_wait_cnt;
	}
	public int getPart4_wait_cnt() {
		return part4_wait_cnt;
	}
	public void setPart4_wait_cnt(int part4_wait_cnt) {
		this.part4_wait_cnt = part4_wait_cnt;
	}
	public int getLimit_waiting() {
		return limit_waiting;
	}
	public void setLimit_waiting(int limit_waiting) {
		this.limit_waiting = limit_waiting;
	}
	public String[] getCt_seq_arr() {
		return ct_seq_arr;
	}
	public void setCt_seq_arr(String[] ct_seq_arr) {
		this.ct_seq_arr = ct_seq_arr;
	}
	public String getUpdt_yn() {
		return updt_yn;
	}
	public void setUpdt_yn(String updt_yn) {
		this.updt_yn = updt_yn;
	}
	public String getCt_bank() {
		return ct_bank;
	}
	public void setCt_bank(String ct_bank) {
		this.ct_bank = ct_bank;
	}
	public String getCt_acchholder() {
		return ct_acchholder;
	}
	public void setCt_acchholder(String ct_acchholder) {
		this.ct_acchholder = ct_acchholder;
	}
	public String getCt_account() {
		return ct_account;
	}
	public void setCt_account(String ct_account) {
		this.ct_account = ct_account;
	}
	public String getCt_price() {
		return ct_price;
	}
	public void setCt_price(String ct_price) {
		this.ct_price = ct_price;
	}
	public String getCt_deposit_stdt() {
		return ct_deposit_stdt;
	}
	public void setCt_deposit_stdt(String ct_deposit_stdt) {
		this.ct_deposit_stdt = ct_deposit_stdt;
	}
	public String getCt_deposit_sth() {
		return ct_deposit_sth;
	}
	public void setCt_deposit_sth(String ct_deposit_sth) {
		this.ct_deposit_sth = ct_deposit_sth;
	}
	public String getCt_deposit_stm() {
		return ct_deposit_stm;
	}
	public void setCt_deposit_stm(String ct_deposit_stm) {
		this.ct_deposit_stm = ct_deposit_stm;
	}
	public String getCt_deposit_eddt() {
		return ct_deposit_eddt;
	}
	public void setCt_deposit_eddt(String ct_deposit_eddt) {
		this.ct_deposit_eddt = ct_deposit_eddt;
	}
	public String getCt_deposit_edh() {
		return ct_deposit_edh;
	}
	public void setCt_deposit_edh(String ct_deposit_edh) {
		this.ct_deposit_edh = ct_deposit_edh;
	}
	public String getCt_deposit_edm() {
		return ct_deposit_edm;
	}
	public void setCt_deposit_edm(String ct_deposit_edm) {
		this.ct_deposit_edm = ct_deposit_edm;
	}
	public String getRefund_bank() {
		return refund_bank;
	}
	public void setRefund_bank(String refund_bank) {
		this.refund_bank = refund_bank;
	}
	public String getRefund_accholder() {
		return refund_accholder;
	}
	public void setRefund_accholder(String refund_accholder) {
		this.refund_accholder = refund_accholder;
	}
	public String getRefund_account() {
		return refund_account;
	}
	public void setRefund_account(String refund_account) {
		this.refund_account = refund_account;
	}
	public String getCt_result() {
		return ct_result;
	}
	public void setCt_result(String ct_result) {
		this.ct_result = ct_result;
	}
	public String getRefund_finish_date() {
		return refund_finish_date;
	}
	public void setRefund_finish_date(String refund_finish_date) {
		this.refund_finish_date = refund_finish_date;
	}
	public String getRefund_finish_h() {
		return refund_finish_h;
	}
	public void setRefund_finish_h(String refund_finish_h) {
		this.refund_finish_h = refund_finish_h;
	}
	public String getRefund_finish_m() {
		return refund_finish_m;
	}
	public void setRefund_finish_m(String refund_finish_m) {
		this.refund_finish_m = refund_finish_m;
	}
	public String getSituation_show_yn() {
		return situation_show_yn;
	}
	public void setSituation_show_yn(String situation_show_yn) {
		this.situation_show_yn = situation_show_yn;
	}
	public String getResult_sms_send_yn() {
		return result_sms_send_yn;
	}
	public void setResult_sms_send_yn(String result_sms_send_yn) {
		this.result_sms_send_yn = result_sms_send_yn;
	}
	public String getLane_sms_send_yn() {
		return lane_sms_send_yn;
	}
	public void setLane_sms_send_yn(String lane_sms_send_yn) {
		this.lane_sms_send_yn = lane_sms_send_yn;
	}
	public int getComment_cnt() {
		return comment_cnt;
	}
	public void setComment_cnt(int comment_cnt) {
		this.comment_cnt = comment_cnt;
	}
	public String getCt_type() {
		return ct_type;
	}
	public void setCt_type(String ct_type) {
		this.ct_type = ct_type;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	public String getOption5() {
		return option5;
	}
	public void setOption5(String option5) {
		this.option5 = option5;
	}
	public String getOption6() {
		return option6;
	}
	public void setOption6(String option6) {
		this.option6 = option6;
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
	public String getEng_name() {
		return eng_name;
	}
	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}
	public String getEng_last_name() {
		return eng_last_name;
	}
	public void setEng_last_name(String eng_last_name) {
		this.eng_last_name = eng_last_name;
	}
	public String getEng_first_name() {
		return eng_first_name;
	}
	public void setEng_first_name(String eng_first_name) {
		this.eng_first_name = eng_first_name;
	}
	public int getShop_event_seq() {
		return shop_event_seq;
	}
	public void setShop_event_seq(int shop_event_seq) {
		this.shop_event_seq = shop_event_seq;
	}
	public String getShop_event_process() {
		return shop_event_process;
	}
	public void setShop_event_process(String shop_event_process) {
		this.shop_event_process = shop_event_process;
	}
	public String getShop_event_start_dt() {
		return shop_event_start_dt;
	}
	public void setShop_event_start_dt(String shop_event_start_dt) {
		this.shop_event_start_dt = shop_event_start_dt;
	}
	public String getShop_event_start_h() {
		return shop_event_start_h;
	}
	public void setShop_event_start_h(String shop_event_start_h) {
		this.shop_event_start_h = shop_event_start_h;
	}
	public String getShop_event_start_m() {
		return shop_event_start_m;
	}
	public void setShop_event_start_m(String shop_event_start_m) {
		this.shop_event_start_m = shop_event_start_m;
	}
	public String getShop_event_end_dt() {
		return shop_event_end_dt;
	}
	public void setShop_event_end_dt(String shop_event_end_dt) {
		this.shop_event_end_dt = shop_event_end_dt;
	}
	public String getShop_event_end_h() {
		return shop_event_end_h;
	}
	public void setShop_event_end_h(String shop_event_end_h) {
		this.shop_event_end_h = shop_event_end_h;
	}
	public String getShop_event_end_m() {
		return shop_event_end_m;
	}
	public void setShop_event_end_m(String shop_event_end_m) {
		this.shop_event_end_m = shop_event_end_m;
	}
	public String getOrg_reg_dt() {
		return org_reg_dt;
	}
	public void setOrg_reg_dt(String org_reg_dt) {
		this.org_reg_dt = org_reg_dt;
	}
	public String getOrg_updt_dt() {
		return org_updt_dt;
	}
	public void setOrg_updt_dt(String org_updt_dt) {
		this.org_updt_dt = org_updt_dt;
	}
	public String getShop_event_memo() {
		return shop_event_memo;
	}
	public void setShop_event_memo(String shop_event_memo) {
		this.shop_event_memo = shop_event_memo;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getTemp3() {
		return temp3;
	}
	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}
	public String getTemp4() {
		return temp4;
	}
	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}
	
	
}
