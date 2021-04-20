package egovframework.client.shop.service;

public class ShopManageVo {
	
	 private int no;
	 private String hash;
	 private String code;
	 private String prd_type;
	 private String name;
	 private String seller;
	 private int seller_idx;
	 private String origin_name;
	 private String stat;
	 private int sell_prc;
	 private int m_sell_prc;
	 private int  weight;
	 private int  normal_prc;
	 private int  m_normal_prc;
	 private int  milage;
	 private int  origin_prc;
	  
	 private String updir;
	 private String upfile1;
	 private String upfile2;
	 private String upfile3;
	 
	 private int w1;
	 private int w2;
	 private int w3;
	 private int h1;
	 private int h2;
	 private int h3;
	 
	 private String content;
	 private String content1;
	 private String content2;
	 private String content3;
	 private String content4;
	 private String content5;
	 
	 private String icons;
	 private String ref_prd;
	 private String reg_date;
	 private String edt_date;
	 
	 private String big;
	 private String mid;
	 private String small;
	 private String depth4;

	 private String mbig;
	 private String xbig;
	 private String xmid;
	 
	 private int qna_cnt;
	 private int hit_view;
	 private int hit_order;                                                                                                                                                                                                                                                                                                                                                                                                                                             
	 private int hit_sales;
	
	 private int sortbig;
	 private int sortmid;
	 private int sortsmall;
	 private int sortdepth4;
	 private String ep_stat;
	 
	 private String hs_code;
	 private String name_referer;
	 
	 private int del_stat;
	 private int del_date;
	 private String del_admin;
	 
	 private int pno;
	 private String filename;
	 private String ofilename;
	 private int width;
	 private int height;
	 
	 private String menuDepth1;
	 private String menuDepth2;
	 private String menuDepth3;
	 
	 //상품 이미지 sort
	 private String sort;
	 
	 //상품 제조사
	 private String value;
	 
	 //상품 옵션 관련
	 private String items;
	 
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
	 
	 
	 private String options;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrd_type() {
		return prd_type;
	}
	public void setPrd_type(String prd_type) {
		this.prd_type = prd_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public int getSeller_idx() {
		return seller_idx;
	}
	public void setSeller_idx(int seller_idx) {
		this.seller_idx = seller_idx;
	}
	public String getOrigin_name() {
		return origin_name;
	}
	public void setOrigin_name(String origin_name) {
		this.origin_name = origin_name;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public int getSell_prc() {
		return sell_prc;
	}
	public void setSell_prc(int sell_prc) {
		this.sell_prc = sell_prc;
	}
	public int getM_sell_prc() {
		return m_sell_prc;
	}
	public void setM_sell_prc(int m_sell_prc) {
		this.m_sell_prc = m_sell_prc;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getNormal_prc() {
		return normal_prc;
	}
	public void setNormal_prc(int normal_prc) {
		this.normal_prc = normal_prc;
	}
	public int getM_normal_prc() {
		return m_normal_prc;
	}
	public void setM_normal_prc(int m_normal_prc) {
		this.m_normal_prc = m_normal_prc;
	}
	public int getMilage() {
		return milage;
	}
	public void setMilage(int milage) {
		this.milage = milage;
	}
	public int getOrigin_prc() {
		return origin_prc;
	}
	public void setOrigin_prc(int origin_prc) {
		this.origin_prc = origin_prc;
	}
	public String getUpdir() {
		return updir;
	}
	public void setUpdir(String updir) {
		this.updir = updir;
	}
	public String getUpfile1() {
		return upfile1;
	}
	public void setUpfile1(String upfile1) {
		this.upfile1 = upfile1;
	}
	public String getUpfile2() {
		return upfile2;
	}
	public void setUpfile2(String upfile2) {
		this.upfile2 = upfile2;
	}
	public String getUpfile3() {
		return upfile3;
	}
	public void setUpfile3(String upfile3) {
		this.upfile3 = upfile3;
	}
	public int getW1() {
		return w1;
	}
	public void setW1(int w1) {
		this.w1 = w1;
	}
	public int getW2() {
		return w2;
	}
	public void setW2(int w2) {
		this.w2 = w2;
	}
	public int getW3() {
		return w3;
	}
	public void setW3(int w3) {
		this.w3 = w3;
	}
	public int getH1() {
		return h1;
	}
	public void setH1(int h1) {
		this.h1 = h1;
	}
	public int getH2() {
		return h2;
	}
	public void setH2(int h2) {
		this.h2 = h2;
	}
	public int getH3() {
		return h3;
	}
	public void setH3(int h3) {
		this.h3 = h3;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent1() {
		return content1;
	}
	public void setContent1(String content1) {
		this.content1 = content1;
	}
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
	public String getContent3() {
		return content3;
	}
	public void setContent3(String content3) {
		this.content3 = content3;
	}
	public String getContent4() {
		return content4;
	}
	public void setContent4(String content4) {
		this.content4 = content4;
	}
	public String getContent5() {
		return content5;
	}
	public void setContent5(String content5) {
		this.content5 = content5;
	}
	public String getIcons() {
		return icons;
	}
	public void setIcons(String icons) {
		this.icons = icons;
	}
	public String getRef_prd() {
		return ref_prd;
	}
	public void setRef_prd(String ref_prd) {
		this.ref_prd = ref_prd;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getEdt_date() {
		return edt_date;
	}
	public void setEdt_date(String edt_date) {
		this.edt_date = edt_date;
	}
	public String getBig() {
		return big;
	}
	public void setBig(String big) {
		this.big = big;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getSmall() {
		return small;
	}
	public void setSmall(String small) {
		this.small = small;
	}
	public String getDepth4() {
		return depth4;
	}
	public void setDepth4(String depth4) {
		this.depth4 = depth4;
	}
	public String getMbig() {
		return mbig;
	}
	public void setMbig(String mbig) {
		this.mbig = mbig;
	}
	public String getXbig() {
		return xbig;
	}
	public void setXbig(String xbig) {
		this.xbig = xbig;
	}
	public String getXmid() {
		return xmid;
	}
	public void setXmid(String xmid) {
		this.xmid = xmid;
	}
	public int getQna_cnt() {
		return qna_cnt;
	}
	public void setQna_cnt(int qna_cnt) {
		this.qna_cnt = qna_cnt;
	}
	public int getHit_view() {
		return hit_view;
	}
	public void setHit_view(int hit_view) {
		this.hit_view = hit_view;
	}
	public int getHit_order() {
		return hit_order;
	}
	public void setHit_order(int hit_order) {
		this.hit_order = hit_order;
	}
	public int getHit_sales() {
		return hit_sales;
	}
	public void setHit_sales(int hit_sales) {
		this.hit_sales = hit_sales;
	}
	public int getSortbig() {
		return sortbig;
	}
	public void setSortbig(int sortbig) {
		this.sortbig = sortbig;
	}
	public int getSortmid() {
		return sortmid;
	}
	public void setSortmid(int sortmid) {
		this.sortmid = sortmid;
	}
	public int getSortsmall() {
		return sortsmall;
	}
	public void setSortsmall(int sortsmall) {
		this.sortsmall = sortsmall;
	}
	public int getSortdepth4() {
		return sortdepth4;
	}
	public void setSortdepth4(int sortdepth4) {
		this.sortdepth4 = sortdepth4;
	}
	public String getEp_stat() {
		return ep_stat;
	}
	public void setEp_stat(String ep_stat) {
		this.ep_stat = ep_stat;
	}
	public String getHs_code() {
		return hs_code;
	}
	public void setHs_code(String hs_code) {
		this.hs_code = hs_code;
	}
	public String getName_referer() {
		return name_referer;
	}
	public void setName_referer(String name_referer) {
		this.name_referer = name_referer;
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
	public String getMenuDepth1() {
		return menuDepth1;
	}
	public void setMenuDepth1(String menuDepth1) {
		this.menuDepth1 = menuDepth1;
	}
	public String getMenuDepth2() {
		return menuDepth2;
	}
	public void setMenuDepth2(String menuDepth2) {
		this.menuDepth2 = menuDepth2;
	}
	public int getDel_stat() {
		return del_stat;
	}
	public void setDel_stat(int del_stat) {
		this.del_stat = del_stat;
	}
	public int getDel_date() {
		return del_date;
	}
	public void setDel_date(int del_date) {
		this.del_date = del_date;
	}
	public String getDel_admin() {
		return del_admin;
	}
	public void setDel_admin(String del_admin) {
		this.del_admin = del_admin;
	}
	public String getMenuDepth3() {
		return menuDepth3;
	}
	public void setMenuDepth3(String menuDepth3) {
		this.menuDepth3 = menuDepth3;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getOfilename() {
		return ofilename;
	}
	public void setOfilename(String ofilename) {
		this.ofilename = ofilename;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
	
}
