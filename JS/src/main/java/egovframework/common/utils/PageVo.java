package egovframework.common.utils;

public class PageVo {
	private int totalRowCnt;
	private int cntPerPage		= 10;
	private int cntPerNav		= 10;
	private int currPage;
	private int pageCnt;
	private int pageGroupNum;
	private int startPageGrp;
	private int endPageGrp;
	private int startPageNum;
	private int endPageNum;
	
	public PageVo() {
		// TODO Auto-generated constructor stub
	}
	
	public PageVo(int pageCnt, int navCnt) {
		this.cntPerPage		= pageCnt;
		this.cntPerNav		= navCnt;
	}
	
	public int getTotalRowCnt() {
		return totalRowCnt;
	}
	
	public void setTotalRowCnt(int totalRowCnt) {
		this.totalRowCnt = totalRowCnt;
	}
	
	public int getCntPerPage() {
		return cntPerPage;
	}
	
	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}
	
	public int getCntPerNav() {
		return cntPerNav;
	}
	
	public void setCntPerNav(int cntPerNav) {
		this.cntPerNav = cntPerNav;
	}
	
	public int getCurrPage() {
		return currPage;
	}
	
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
	public int getPageCnt() {
		return pageCnt;
	}
	
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
	
	public int getPageGroupNum() {
		return pageGroupNum;
	}
	
	public void setPageGroupNum(int pageGroupNum) {
		this.pageGroupNum = pageGroupNum;
	}
	
	public int getStartPageGrp() {
		return startPageGrp;
	}
	
	public void setStartPageGrp(int startPageGrp) {
		this.startPageGrp = startPageGrp;
	}
	
	public int getEndPageGrp() {
		return endPageGrp;
	}
	
	public void setEndPageGrp(int endPageGrp) {
		this.endPageGrp = endPageGrp;
	}
	
	public int getStartPageNum() {
		return startPageNum;
	}
	
	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}
	
	public int getEndPageNum() {
		return endPageNum;
	}
	
	public void setEndPageNum(int endPageNum) {
		this.endPageNum = endPageNum;
	}
}

