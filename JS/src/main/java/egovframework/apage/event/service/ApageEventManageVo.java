package egovframework.apage.event.service;

import java.util.List;

public class ApageEventManageVo {

	//기타 파트정보
	private int ecp_seq;
	private String part_name;
	private int part_ord;
	private int ecp_seq_arr[];
	private int reg_turn;
	private int lane_num;

	//옵션정보 
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String option5;
	private String option6;
	private String option7;
	private String option8;
	private String option9;
	private String option10;
	private String zipcode; 		//참가자주소
	private String addr; 		//참가자주소
	private String addr_detail; 		//참가자주소
	private String eng_last_name;	//참가자 영문 성
	private String eng_first_name;	//참가자 영문 이름
	private int part_cnt;
	private String expose_yn;

	private String cut_yn;
	//게시판
	private int ntt_id;
	private String bbs_id;
	private String ntt_sj;
	private String ntt_cn;
	private String answer_at;
	private int parntsctt_no;
	private int sort_ordr;
	private int rdcnt;
	private String ntce_bgnde;
	private String ntce_endde;
	private String ntcr_id;
	private String ntcr_nm;
	private String ntup_id;
	
	
	
	
	private String reg_dt;
	private String password;
	private String atch_file_id;
	private String updt_dt;
	private String use_at;
	private String check_yn;
	private String updt_yn;
	private String atch_file_id2;
	private String memo;	
	private String open_yn;
	private String prepare_yn;
	private String ct_type;
	private String ct_type_arr[];
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
	private String ct_sms_send_dt;
	private String ct_sms_send_dt2;
	private String ct_lane_sms_send_dt;
	private String ct_lane_sms_send_dt2;
	private String ct_result;
	private String result_sms_send_yn;
	private String lane_sms_send_yn;
	
	private String beforeMonth;
	private String afterMonth;
	
	private String cancel_date; 			//신청취소일자
	private String refund_finish_date;		//환불마감일자
	private String refund_finish_h;			//환불마감시간
	private String refund_finish_m;			//환불마감분
	
	private String ct_group;
	private String ct_seq_arr[];
	private String app_seq_arr[];
	
	//대회신청 접수 기간
	private String app_start_dt;
	private String app_start_h;
	private String app_start_m;
	private String app_end_dt;
	private String app_end_h;
	private String app_end_m;
	private String pay_flag;
	private String app_sms_date;
	private String app_sms_flag;
	private String app_lane_sms_flag;
	private String app_lane_sms_date;
	
	
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
	private int limit_part;
	
	//대회신청가능자
	private int t_seq;
	private String ct_title;
	private String t_rank;
	private String t_name;
	private String t_gender;
	private String t_reg_id;
	private String t_memo;
	private String t_birth;
	private String t_tel;
	
	private int app_seq;	
	private String mber_id;
	private String join_name;
	private String deposit_name;
	private String birth;
	private String gender;
	private String telno;
	private String telno_string;
	private String email;
	private String disable_yn;
	private String handicap;
	private String part;
	private String lane;
	private String status;
	private String autoLaneResult;
	private String pickCnt; //선정 인원수
	private int waiting_num; 		//대기번호
	private int limit_waiting;		//대기자정원
	private String origin_status;
	private String refund_bank;
	private String refund_accholder;
	private String refund_account;
	private String situation_show_yn;
	
	//제품관련
	private String big;
	
	private int ascnum;
	private int rownum;
	private String jobFlag;
	
	//댓글
	private int parent_seq;
	private String content;
	
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
	
	private String lane_f;
	private String lane_l;
	
	private List<ApageEventManageVo> list;
	public int getNtt_id() {
		return ntt_id;
	}
	public void setNtt_id(int ntt_id) {
		this.ntt_id = ntt_id;
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
	public String getUse_at() {
		return use_at;
	}
	public void setUse_at(String use_at) {
		this.use_at = use_at;
	}
	public void setCt_acchholder(String ct_acchholder) {
		this.ct_acchholder = ct_acchholder;
	}
	public String getUpdt_yn() {
		return updt_yn;
	}
	public void setUpdt_yn(String updt_yn) {
		this.updt_yn = updt_yn;
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
	public String getCt_sms_send_dt() {
		return ct_sms_send_dt;
	}
	public void setCt_sms_send_dt(String ct_sms_send_dt) {
		this.ct_sms_send_dt = ct_sms_send_dt;
	}
	public String getCt_sms_send_dt2() {
		return ct_sms_send_dt2;
	}
	public void setCt_sms_send_dt2(String ct_sms_send_dt2) {
		this.ct_sms_send_dt2 = ct_sms_send_dt2;
	}
	public String getBeforeMonth() {
		return beforeMonth;
	}
	public void setBeforeMonth(String beforeMonth) {
		this.beforeMonth = beforeMonth;
	}
	public String getAfterMonth() {
		return afterMonth;
	}
	public void setAfterMonth(String afterMonth) {
		this.afterMonth = afterMonth;
	}
	public String getCancel_date() {
		return cancel_date;
	}
	public void setCancel_date(String cancel_date) {
		this.cancel_date = cancel_date;
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
	public String getCt_group() {
		return ct_group;
	}
	public void setCt_group(String ct_group) {
		this.ct_group = ct_group;
	}
	public String[] getCt_seq_arr() {
		return ct_seq_arr;
	}
	public void setCt_seq_arr(String[] ct_seq_arr) {
		this.ct_seq_arr = ct_seq_arr;
	}
	public String[] getApp_seq_arr() {
		return app_seq_arr;
	}
	public void setApp_seq_arr(String[] app_seq_arr) {
		this.app_seq_arr = app_seq_arr;
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
	public String getApp_sms_date() {
		return app_sms_date;
	}
	public void setApp_sms_date(String app_sms_date) {
		this.app_sms_date = app_sms_date;
	}
	public String getApp_sms_flag() {
		return app_sms_flag;
	}
	public void setApp_sms_flag(String app_sms_flag) {
		this.app_sms_flag = app_sms_flag;
	}
	public String getApp_lane_sms_flag() {
		return app_lane_sms_flag;
	}
	public void setApp_lane_sms_flag(String app_lane_sms_flag) {
		this.app_lane_sms_flag = app_lane_sms_flag;
	}
	public String getApp_lane_sms_date() {
		return app_lane_sms_date;
	}
	public void setApp_lane_sms_date(String app_lane_sms_date) {
		this.app_lane_sms_date = app_lane_sms_date;
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
	public int getLimit_part() {
		return limit_part;
	}
	public void setLimit_part(int limit_part) {
		this.limit_part = limit_part;
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
	public String getJoin_name() {
		return join_name;
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
	public String getTelno_string() {
		return telno_string;
	}
	public void setTelno_string(String telno_string) {
		this.telno_string = telno_string;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getAutoLaneResult() {
		return autoLaneResult;
	}
	public void setAutoLaneResult(String autoLaneResult) {
		this.autoLaneResult = autoLaneResult;
	}
	public String getPickCnt() {
		return pickCnt;
	}
	public void setPickCnt(String pickCnt) {
		this.pickCnt = pickCnt;
	}
	public int getWaiting_num() {
		return waiting_num;
	}
	public void setWaiting_num(int waiting_num) {
		this.waiting_num = waiting_num;
	}
	public int getLimit_waiting() {
		return limit_waiting;
	}
	public void setLimit_waiting(int limit_waiting) {
		this.limit_waiting = limit_waiting;
	}
	public String getOrigin_status() {
		return origin_status;
	}
	public void setOrigin_status(String origin_status) {
		this.origin_status = origin_status;
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
	public String getSituation_show_yn() {
		return situation_show_yn;
	}
	public void setSituation_show_yn(String situation_show_yn) {
		this.situation_show_yn = situation_show_yn;
	}
	public String getBig() {
		return big;
	}
	public void setBig(String big) {
		this.big = big;
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
	public int getParent_seq() {
		return parent_seq;
	}
	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getLane_f() {
		return lane_f;
	}
	public void setLane_f(String lane_f) {
		this.lane_f = lane_f;
	}
	public String getLane_l() {
		return lane_l;
	}
	public void setLane_l(String lane_l) {
		this.lane_l = lane_l;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getCt_type() {
		return ct_type;
	}
	public void setCt_type(String ct_type) {
		this.ct_type = ct_type;
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
	public String getPrepare_yn() {
		return prepare_yn;
	}
	public void setPrepare_yn(String prepare_yn) {
		this.prepare_yn = prepare_yn;
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
	public List<ApageEventManageVo> getList() {
		return list;
	}
	public void setList(List<ApageEventManageVo> list) {
		this.list = list;
	}
	public int getEcp_seq() {
		return ecp_seq;
	}
	public void setEcp_seq(int ecp_seq) {
		this.ecp_seq = ecp_seq;
	}
	public String getPart_name() {
		return part_name;
	}
	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
	public int getPart_ord() {
		return part_ord;
	}
	public void setPart_ord(int part_ord) {
		this.part_ord = part_ord;
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
	public String getOption7() {
		return option7;
	}
	public void setOption7(String option7) {
		this.option7 = option7;
	}
	public String getOption8() {
		return option8;
	}
	public void setOption8(String option8) {
		this.option8 = option8;
	}
	public String getOption9() {
		return option9;
	}
	public void setOption9(String option9) {
		this.option9 = option9;
	}
	public String getOption10() {
		return option10;
	}
	public void setOption10(String option10) {
		this.option10 = option10;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int[] getEcp_seq_arr() {
		return ecp_seq_arr;
	}
	public void setEcp_seq_arr(int[] ecp_seq_arr) {
		this.ecp_seq_arr = ecp_seq_arr;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddr_detail() {
		return addr_detail;
	}
	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
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
	public String getCt_lane_sms_send_dt() {
		return ct_lane_sms_send_dt;
	}
	public void setCt_lane_sms_send_dt(String ct_lane_sms_send_dt) {
		this.ct_lane_sms_send_dt = ct_lane_sms_send_dt;
	}
	public String getCt_lane_sms_send_dt2() {
		return ct_lane_sms_send_dt2;
	}
	public void setCt_lane_sms_send_dt2(String ct_lane_sms_send_dt2) {
		this.ct_lane_sms_send_dt2 = ct_lane_sms_send_dt2;
	}
	public String getCt_result() {
		return ct_result;
	}
	public void setCt_result(String ct_result) {
		this.ct_result = ct_result;
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
	public int getPart_cnt() {
		return part_cnt;
	}
	public void setPart_cnt(int part_cnt) {
		this.part_cnt = part_cnt;
	}
	public String getCut_yn() {
		return cut_yn;
	}
	public void setCut_yn(String cut_yn) {
		this.cut_yn = cut_yn;
	}
	public String getExpose_yn() {
		return expose_yn;
	}
	public void setExpose_yn(String expose_yn) {
		this.expose_yn = expose_yn;
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
	public String[] getCt_type_arr() {
		return ct_type_arr;
	}
	public void setCt_type_arr(String[] ct_type_arr) {
		this.ct_type_arr = ct_type_arr;
	}
	public int getT_seq() {
		return t_seq;
	}
	public void setT_seq(int t_seq) {
		this.t_seq = t_seq;
	}
	public String getCt_title() {
		return ct_title;
	}
	public void setCt_title(String ct_title) {
		this.ct_title = ct_title;
	}
	public String getT_rank() {
		return t_rank;
	}
	public void setT_rank(String t_rank) {
		this.t_rank = t_rank;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getT_gender() {
		return t_gender;
	}
	public void setT_gender(String t_gender) {
		this.t_gender = t_gender;
	}
	public String getT_reg_id() {
		return t_reg_id;
	}
	public void setT_reg_id(String t_reg_id) {
		this.t_reg_id = t_reg_id;
	}
	public String getT_memo() {
		return t_memo;
	}
	public void setT_memo(String t_memo) {
		this.t_memo = t_memo;
	}
	public String getT_birth() {
		return t_birth;
	}
	public void setT_birth(String t_birth) {
		this.t_birth = t_birth;
	}
	public String getT_tel() {
		return t_tel;
	}
	public void setT_tel(String t_tel) {
		this.t_tel = t_tel;
	}
	public int getReg_turn() {
		return reg_turn;
	}
	public void setReg_turn(int reg_turn) {
		this.reg_turn = reg_turn;
	}
	public int getLane_num() {
		return lane_num;
	}
	public void setLane_num(int lane_num) {
		this.lane_num = lane_num;
	}
	
	
}
