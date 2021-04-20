package egovframework.common.service;

public class SmsManageVo {

	private String log_table_name;
	//SMS 기록(sms_manage)
	private int seq;
	private String msg_type;
	private String msg_content;
	private String send_date;
	private String send_id;
	
	//SMS 전송 테이블(SMS_MSG)
	private int tr_num;						//고유번호(자동증가)								->NOT NULL
	private String tr_senddate;				//메세지를 전송할 시간(미래시간을 넣으면 예약발송 됨.)		->NOT NULL
	private int tr_serialnum;				//고객이 발급한 번호
	private String tr_id;					//고객이 발급한 subID
	private String tr_sendstat;				//발송상태(0:발송대기, 1:전송완료, 2:결과수신완료)		->NOT NULL
	private String tr_rsltstat;				//발송결과 수신값(결과코드 표 참조)
	private String tr_msgtype;				//문자전송형태(0:일반메세지, 1:콜백URL메세지)			->NOT NULL
	private String tr_phone;				//수신자 핸드폰번호								->NOT NULL
	private String tr_callback;				//송신자 전화번호								->NOT NULL
	private String tr_org_callback;			//송신자 원 발신번호
	private String tr_bill_id;				//드림라인이 발급한 분리 과금 ID만 입력
	private String tr_rsltdate;				//이동통신사로부터 결과를 통보받은 시간
	private String modified;				
	private String tr_msg;					//전송 메세지									->NOT NULL
	private String tr_net;					//전송 완료 후 이동통신사 정보(SKT, KT, LGT)
	private String tr_etc1;					//dummy field
	private String tr_etc2;
	private String tr_etc3;
	private String tr_etc4;
	private String tr_etc5;
	private String tr_etc6;
	private String tr_etc7;
	private String tr_etc8;
	private String tr_etc9;
	private String tr_etc10;
	private String tr_realsenddate;			//실제 모듈이 발송한 시간
	
	//SMS 로그 테이블(SMS_MSG_YYYYMM)
	
	//LMS,MMS 전송 테이블(MMS_MSG)
	private int msgkey;						//고유번호(자동증가)								->NOT NULL
	private String subject;					//제목 - 40바이트 까지만, 특수기호 사용x
	private String phone;					//수신할 핸드폰 번호								->NOT NULL
	private String callback;				//송신자 전화번호
	private String org_callback;			//송신자 원 발신번호
	private String bill_id;					//드림라인이 발급한 분리 과금 ID만 입력
	private String status;					//상태(0:발송대기, 2:전송완료, 3:결과수신완료)		->NOT NULL
	private String reqdate;					//메세지를 전송할 시간(미래시간을 넣으면 예약발송 됨.)		->NOT NULL
	private String reqdate_ym;
	private String msg;						//전송 메세지
	private int file_cnt;					//전송 파일 개수(개수에 비례하는 file_path 설정되야 함)
	private int file_cnt_real;				//실제로 체크한 전송 파일 개수
	private String file_path1;				//전송 파일 위치(해상도 및 권장 크기:320*240,300KB 미만)
	private int file_path1_siz;				//전송 파일 크기
	private String file_path2;
	private int file_path2_siz;
	private String file_path3;
	private int file_path3_siz;
	private String file_path4;
	private int file_path4_siz;
	private String file_path5;
	private int file_path5_siz;
	private String expiretime;				
	private String sentdate;				//전송완료 시간
	private String rsltdate;				//agent가 결과를 수신 받은 시간
	private String reportdate;				//G/W로부터 결과를 통보받은 시간
	private String terminateddate;			//메세지 처리가 완료된 시간
	private String rslt;					//결과값
	private int repcnt;						//사용하지 않음!!!!!(default = 0)				->NOT NULL
	private String type;					//메세지 타임(0:LMS,MMS /1:MMS URL /7:HTML)		->NOT NULL
	private String telcoinfo;				//이동사 구분코드(SKT, KT, LGT)
	private String id;						//송신자 ID
	private String post;					//송신자 부서
	private String etc1;					//dummy field
	private String etc2;
	private String etc3;
	private String etc4;
	private String etc5;
	private String etc6;
	private String etc7;
	private String etc8;
	private String etc9;
	private String etc10;
	
	private String temp1;
	private String temp2;
	private String temp3;
	private String temp4;
	private String temp5;
	private String temp6;
	private String temp7;
	private String temp8;
	private String temp9;
	private String temp10;
	
	private String Tables_in_gcfac_sms;
	private String table_name;
	private String beforeMonth;
	private String afterMonth;
	private String log_tables[];
	//MMS 로그 테이블(MMS_MSG_YYYYMM)
	
	
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
	private int ascnum;
	private int rownum;
	
	
	
	//getter and setter
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
	
	
	
	
	public int getTr_num() {
		return tr_num;
	}
	public void setTr_num(int tr_num) {
		this.tr_num = tr_num;
	}
	public String getTr_senddate() {
		return tr_senddate;
	}
	public void setTr_senddate(String tr_senddate) {
		this.tr_senddate = tr_senddate;
	}
	public int getTr_serialnum() {
		return tr_serialnum;
	}
	public void setTr_serialnum(int tr_serialnum) {
		this.tr_serialnum = tr_serialnum;
	}
	public String getTr_id() {
		return tr_id;
	}
	public void setTr_id(String tr_id) {
		this.tr_id = tr_id;
	}
	public String getTr_sendstat() {
		return tr_sendstat;
	}
	public void setTr_sendstat(String tr_sendstat) {
		this.tr_sendstat = tr_sendstat;
	}
	public String getTr_rsltstat() {
		return tr_rsltstat;
	}
	public void setTr_rsltstat(String tr_rsltstat) {
		this.tr_rsltstat = tr_rsltstat;
	}
	public String getTr_msgtype() {
		return tr_msgtype;
	}
	public void setTr_msgtype(String tr_msgtype) {
		this.tr_msgtype = tr_msgtype;
	}
	public String getTr_phone() {
		return tr_phone;
	}
	public void setTr_phone(String tr_phone) {
		this.tr_phone = tr_phone;
	}
	public String getTr_callback() {
		return tr_callback;
	}
	public void setTr_callback(String tr_callback) {
		this.tr_callback = tr_callback;
	}
	public String getTr_org_callback() {
		return tr_org_callback;
	}
	public void setTr_org_callback(String tr_org_callback) {
		this.tr_org_callback = tr_org_callback;
	}
	public String getTr_bill_id() {
		return tr_bill_id;
	}
	public void setTr_bill_id(String tr_bill_id) {
		this.tr_bill_id = tr_bill_id;
	}
	public String getTr_rsltdate() {
		return tr_rsltdate;
	}
	public void setTr_rsltdate(String tr_rsltdate) {
		this.tr_rsltdate = tr_rsltdate;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getTr_msg() {
		return tr_msg;
	}
	public void setTr_msg(String tr_msg) {
		this.tr_msg = tr_msg;
	}
	public String getTr_net() {
		return tr_net;
	}
	public void setTr_net(String tr_net) {
		this.tr_net = tr_net;
	}
	public String getTr_etc1() {
		return tr_etc1;
	}
	public void setTr_etc1(String tr_etc1) {
		this.tr_etc1 = tr_etc1;
	}
	public String getTr_etc2() {
		return tr_etc2;
	}
	public void setTr_etc2(String tr_etc2) {
		this.tr_etc2 = tr_etc2;
	}
	public String getTr_etc3() {
		return tr_etc3;
	}
	public void setTr_etc3(String tr_etc3) {
		this.tr_etc3 = tr_etc3;
	}
	public String getTr_etc4() {
		return tr_etc4;
	}
	public void setTr_etc4(String tr_etc4) {
		this.tr_etc4 = tr_etc4;
	}
	public String getTr_etc5() {
		return tr_etc5;
	}
	public void setTr_etc5(String tr_etc5) {
		this.tr_etc5 = tr_etc5;
	}
	public String getTr_etc6() {
		return tr_etc6;
	}
	public void setTr_etc6(String tr_etc6) {
		this.tr_etc6 = tr_etc6;
	}
	public String getTr_etc7() {
		return tr_etc7;
	}
	public void setTr_etc7(String tr_etc7) {
		this.tr_etc7 = tr_etc7;
	}
	public String getTr_etc8() {
		return tr_etc8;
	}
	public void setTr_etc8(String tr_etc8) {
		this.tr_etc8 = tr_etc8;
	}
	public String getTr_etc9() {
		return tr_etc9;
	}
	public void setTr_etc9(String tr_etc9) {
		this.tr_etc9 = tr_etc9;
	}
	public String getTr_etc10() {
		return tr_etc10;
	}
	public void setTr_etc10(String tr_etc10) {
		this.tr_etc10 = tr_etc10;
	}
	public String getTr_realsenddate() {
		return tr_realsenddate;
	}
	public void setTr_realsenddate(String tr_realsenddate) {
		this.tr_realsenddate = tr_realsenddate;
	}
	
	
	
	
	
	
	
	
	
	public int getMsgkey() {
		return msgkey;
	}
	public void setMsgkey(int msgkey) {
		this.msgkey = msgkey;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getOrg_callback() {
		return org_callback;
	}
	public void setOrg_callback(String org_callback) {
		this.org_callback = org_callback;
	}
	public String getBill_id() {
		return bill_id;
	}
	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReqdate() {
		return reqdate;
	}
	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getFile_cnt() {
		return file_cnt;
	}
	public void setFile_cnt(int file_cnt) {
		this.file_cnt = file_cnt;
	}
	public int getFile_cnt_real() {
		return file_cnt_real;
	}
	public void setFile_cnt_real(int file_cnt_real) {
		this.file_cnt_real = file_cnt_real;
	}
	public String getFile_path1() {
		return file_path1;
	}
	public void setFile_path1(String file_path1) {
		this.file_path1 = file_path1;
	}
	public int getFile_path1_siz() {
		return file_path1_siz;
	}
	public void setFile_path1_siz(int file_path1_siz) {
		this.file_path1_siz = file_path1_siz;
	}
	public String getFile_path2() {
		return file_path2;
	}
	public void setFile_path2(String file_path2) {
		this.file_path2 = file_path2;
	}
	public int getFile_path2_siz() {
		return file_path2_siz;
	}
	public void setFile_path2_siz(int file_path2_siz) {
		this.file_path2_siz = file_path2_siz;
	}
	public String getFile_path3() {
		return file_path3;
	}
	public void setFile_path3(String file_path3) {
		this.file_path3 = file_path3;
	}
	public int getFile_path3_siz() {
		return file_path3_siz;
	}
	public void setFile_path3_siz(int file_path3_siz) {
		this.file_path3_siz = file_path3_siz;
	}
	public String getFile_path4() {
		return file_path4;
	}
	public void setFile_path4(String file_path4) {
		this.file_path4 = file_path4;
	}
	public int getFile_path4_siz() {
		return file_path4_siz;
	}
	public void setFile_path4_siz(int file_path4_siz) {
		this.file_path4_siz = file_path4_siz;
	}
	public String getFile_path5() {
		return file_path5;
	}
	public void setFile_path5(String file_path5) {
		this.file_path5 = file_path5;
	}
	public int getFile_path5_siz() {
		return file_path5_siz;
	}
	public void setFile_path5_siz(int file_path5_siz) {
		this.file_path5_siz = file_path5_siz;
	}
	public String getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}
	public String getSentdate() {
		return sentdate;
	}
	public void setSentdate(String sentdate) {
		this.sentdate = sentdate;
	}
	public String getRsltdate() {
		return rsltdate;
	}
	public void setRsltdate(String rsltdate) {
		this.rsltdate = rsltdate;
	}
	public String getReportdate() {
		return reportdate;
	}
	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}
	public String getTerminateddate() {
		return terminateddate;
	}
	public void setTerminateddate(String terminateddate) {
		this.terminateddate = terminateddate;
	}
	public String getRslt() {
		return rslt;
	}
	public void setRslt(String rslt) {
		this.rslt = rslt;
	}
	public int getRepcnt() {
		return repcnt;
	}
	public void setRepcnt(int repcnt) {
		this.repcnt = repcnt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTelcoinfo() {
		return telcoinfo;
	}
	public void setTelcoinfo(String telcoinfo) {
		this.telcoinfo = telcoinfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
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
	public String getEtc4() {
		return etc4;
	}
	public void setEtc4(String etc4) {
		this.etc4 = etc4;
	}
	public String getEtc5() {
		return etc5;
	}
	public void setEtc5(String etc5) {
		this.etc5 = etc5;
	}
	public String getEtc6() {
		return etc6;
	}
	public void setEtc6(String etc6) {
		this.etc6 = etc6;
	}
	public String getEtc7() {
		return etc7;
	}
	public void setEtc7(String etc7) {
		this.etc7 = etc7;
	}
	public String getEtc8() {
		return etc8;
	}
	public void setEtc8(String etc8) {
		this.etc8 = etc8;
	}
	public String getEtc9() {
		return etc9;
	}
	public void setEtc9(String etc9) {
		this.etc9 = etc9;
	}
	public String getEtc10() {
		return etc10;
	}
	public void setEtc10(String etc10) {
		this.etc10 = etc10;
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
	public String getLog_table_name() {
		return log_table_name;
	}
	public void setLog_table_name(String log_table_name) {
		this.log_table_name = log_table_name;
	}
	public String getTables_in_gcfac_sms() {
		return Tables_in_gcfac_sms;
	}
	public void setTables_in_gcfac_sms(String tables_in_gcfac_sms) {
		Tables_in_gcfac_sms = tables_in_gcfac_sms;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
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
	public String[] getLog_tables() {
		return log_tables;
	}
	public void setLog_tables(String[] log_tables) {
		this.log_tables = log_tables;
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
	public String getTemp5() {
		return temp5;
	}
	public void setTemp5(String temp5) {
		this.temp5 = temp5;
	}
	public String getTemp6() {
		return temp6;
	}
	public void setTemp6(String temp6) {
		this.temp6 = temp6;
	}
	public String getTemp7() {
		return temp7;
	}
	public void setTemp7(String temp7) {
		this.temp7 = temp7;
	}
	public String getTemp8() {
		return temp8;
	}
	public void setTemp8(String temp8) {
		this.temp8 = temp8;
	}
	public String getTemp9() {
		return temp9;
	}
	public void setTemp9(String temp9) {
		this.temp9 = temp9;
	}
	public String getTemp10() {
		return temp10;
	}
	public void setTemp10(String temp10) {
		this.temp10 = temp10;
	}
	public String getReqdate_ym() {
		return reqdate_ym;
	}
	public void setReqdate_ym(String reqdate_ym) {
		this.reqdate_ym = reqdate_ym;
	}
	
	
	
}
